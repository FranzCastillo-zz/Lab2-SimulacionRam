/*

    NOMBRE DEL ARCHIVO: Memoria.java
    PROGRAMADOR: Francisco Castillo
    HISTORIAL DE MODIFICACION:
        -Se ha modificadoa a un solo constructor
        -Se agregaron los metodos
*/
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class Memoria {
    private String tipo;
    private int capacidad; // en gb
    private int megas; // capacidad en mb
    private int bloques; // 1 bloque =  mb
    private int megasUsadas = 0;
    private Programas p;
    final int[] memorias = {4, 8, 12, 16, 32, 64};
    List<String> lineas;
    Queue<String> cola;
    
    public Memoria(String tipo, int capacidad){
        p = new Programas();
        if(tipo.equals("DDR")){ // DDR
            this.tipo = tipo;
            this.capacidad = 4;
            crearArchivoMemoria(this.capacidad * 1024 / 64);
            calcularPropiedades();
        }else{ // SDR
            this.tipo = "SDR";
            boolean encontrado = false;
            for (int version : memorias) {
                if(!encontrado){
                    if(version == capacidad){
                        this.capacidad = capacidad;
                        crearArchivoMemoria(capacidad * 1024 / 64);
                        calcularPropiedades();
                        encontrado = true;
                    }
                }
            } // Si aun no se encontro
            if(!encontrado){
                this.capacidad = 8;
                crearArchivoMemoria(capacidad * 1024 / 64);
                calcularPropiedades();
            }
            
        }
        cola = new LinkedList<>();
    }
    private void crearArchivoMemoria(int bloquesParam){
        try {
            File f = new File("Memoria.txt");
            FileWriter w = new FileWriter("Memoria.txt");
            f.createNewFile();
            for(int i = 0; i < bloquesParam; i++){
                w.write("vacio\n");
            }
            w.close();
        }catch (IOException ie) {
            System.out.println("Ocurrio un error, no se ha podido crear el archivo Memoria.txt");
        }
    }
    private void calcularPropiedades(){
        this.megas = this.capacidad * 1024;
        this.bloques = this.megas / 64;
        this.megasUsadas = 0;
        leerDatos();
        for (String linea : lineas) {
            if(!linea.equals("vacio")){
                megasUsadas += 64;
            }
        }
    }
    private void cambiarLineaMemoria(int numLinea, String datos){
        try{
            Path path = Paths.get("Memoria.txt");
            List<String> lines = Files.readAllLines(path);
            lines.set(numLinea, datos);
            Files.write(path, lines);
        }catch (IOException ie) {
            crearArchivoMemoria(bloques);
        }
    }
    private void leerDatos(){
        try{
            Path path = Paths.get("Memoria.txt");
            lineas = Files.readAllLines(path);
        }catch(IOException e){
            crearArchivoMemoria(bloques);
        }
    }
    public boolean expandirMemoria(){
        leerDatos();
        boolean posibleExpandir = false;
        int i = 0;
        for (int memoria : memorias) {
            if(memoria == capacidad && !posibleExpandir){
                if(i != memorias.length){
                    this.capacidad = memorias[i + 1];
                    calcularPropiedades();
                    try {
                        FileWriter w = new FileWriter("Memoria.txt");
                        int actual = (memorias[i] * 1024 / 64);
                        for(int j = 0; j < actual; j++) {
                            w.write(lineas.get(j) + "\n");
                        }
                        for(int j = actual; j < bloques; j++){
                            w.write("vacio\n");
                        }
                        w.close();
                        posibleExpandir = true;
                    }catch (IOException ie) {
                        System.out.println("Ocurrio un error, no se ha podido expandir el archivo Memoria.txt");
                    }
                }
            }else{
                i++;
            }
        }
        return posibleExpandir;
    }
    public boolean programaValido(String programa){
        return p.getPrograma(programa) != "xd,-1,-1"; // 'xd' indica que el programa es invalido
    }
    public boolean ingresarPrograma(String programa){ // En el archivo Memoria se vera como linea,nombre,ciclos
        leerDatos();
        String[] info = p.getPrograma(programa).split(","); // nombre,espacio,ciclos
        String nombre = info[0];
        double espacio = Math.ceil(Double.parseDouble(info[1]) / 64); // mb → bloques
        String ciclos = info[2];
        boolean escrito = false;
        for(int i = 0; i < lineas.size() && !escrito; i++){
            String lineaActual = lineas.get(i);
            if(lineaActual.equals("vacio")){
                // Verifica si el espacio que se necesita es > 1
                if(espacio > 1){
                    // Ve j espacios adelante para ver si es posible ingresarlo ahi
                    try{
                        for(int j = i; j < i + espacio; j++){  
                            lineas.get(j); //Si no existe la linea j (no hay espacio en la memoria) y da error
                        }
                    }catch(Exception e){
                        return false;
                    }
                    // SI NO HIZO RETURN SE EJECUTA ESTO  
                    for(int j = i; j < espacio + i; j++){
                        String datos = j + "," + nombre + "," + ciclos;
                        cambiarLineaMemoria(j, datos);
                    }
                    escrito = true;
                }else{ // Escribe en una linea el programa
                    String datos = i + "," + nombre + "," + ciclos;
                    cambiarLineaMemoria(i, datos);
                    escrito = true;
                }
            }
        }
        calcularPropiedades();
        return escrito;
    }
    public String[] getDatosRam(){ //tipo,gb totales, mb usados, mb disponibles
        String[] temp = new String[4];
        temp[0] = this.tipo;
        temp[1] = this.capacidad + "";
        temp[2] = this.megasUsadas + "";
        temp[3] = (this.megas - this.megasUsadas) + "";
        return temp;
    }
    public String[] getProgramasEnEjecucion(){
        leerDatos();
        List<String> programas = new ArrayList<>();
        for (String linea : lineas) {
            if(!linea.equals("vacio")){
                String nombre = linea.split(",")[1]; // Nombre del programa en la memoria
                boolean yaEstaAhi = false;
                for (String programa : programas) { //Es necesario ver que no se ha mandado ya el programa
                    if(!yaEstaAhi){
                        if(programa.equals(nombre)){
                            yaEstaAhi = true;
                        }
                    }
                }
                if(!yaEstaAhi){
                    programas.add(nombre);
                } 
            }
        }
        String[] temp;
        if(programas.size() != 0){
            temp = new String[programas.size()];
            temp = programas.toArray(temp);
        }else{
            temp = new String[1];
            temp[0] = "No se encuentran programas en ejecucion";
        }
        return temp;
    }
    public String getBloques(String programa){
        leerDatos();
        String temp = "";
        for (String linea : lineas) {
            if(!linea.equals("vacio")){
                String[] arreglo = linea.split(","); // Nombre del programa en la memoria
                if(arreglo[1].equals(programa)){
                    temp += arreglo[0] + "\n";
                }
            }
        }
        if(!temp.equals("")){
            return temp;
        }else{
            return programa +" no se encuentra en ejecucion.";
        }
    }
    private void reducirMemoria(int nuevaCapacidad){
        this.capacidad = nuevaCapacidad;
        calcularPropiedades();
        for(int i = lineas.size() - 1; i > bloques; i--){
            lineas.remove(i);
        }
        try {
            FileWriter w = new FileWriter("Memoria.txt");
            for(int i = 0; i < bloques; i++) {
                w.write(lineas.get(i) + "\n");
            }
            w.close();
        }catch (IOException ie) {
            System.out.println("Ocurrio un error, no se ha podido expandir el archivo Memoria.txt");
        }
    }
    private void verificarReduccion(){   
        int i = 0;
        boolean reducir = true;
        int desde, hasta;
        for (int memoria : memorias) {
            if(memoria == capacidad && memoria != 4){
                desde = memorias[i] * 1024 / 64;
                hasta = memorias[i - 1] * 1024 / 64;
                for(int j = desde; j < hasta && reducir; j++){
                    try{
                        if(!lineas.get(j).equals("vacio")){
                            reducir = false;
                        }
                    }catch(Exception e){
                        continue;
                    }
                }
                if(reducir){
                    reducirMemoria(memorias[i - 1]);
                }
            }else{
                i++;
            }
        }
    }
    public void hacerCiclo(){
        leerDatos();
        int i = 0;
        for (String linea : lineas) {
            if(!linea.equals("vacio")){
                String[] temp = linea.split(",");
                String nuevaLinea = temp[0] + "," + temp[1] + ",";
                int numero = Integer.parseInt(temp[2]) - 1;
                if(numero == -1){
                    nuevaLinea = "vacio";
                }else{
                    nuevaLinea += numero + "";
                }
                cambiarLineaMemoria(i, nuevaLinea);
            }
            i++;
        }
        // MANDAR A COLA
        String programa = cola.peek();
        if(programa != null){
            if(ingresarPrograma(programa)){
                cola.poll();
            }
        }
        verificarReduccion();
    }
    public String[] getEstado(){
        leerDatos();
        List<String> programas = new ArrayList<>();
        for (String linea : lineas) {
            if(!linea.equals("vacio")){
                programas.add(linea.split(",")[1]);
            }else{
                programas.add(linea);
            }
        }
        String[] temp = new String[programas.size()];
        temp = programas.toArray(temp);
        return temp;
    }
    public String getTipo(){
        return tipo;
    }
    public void mandarACola(String programa){
        cola.add(p.getPrograma(programa));
    }
    public String[] getProgramasEnCola(){
        String[] temp;
        if(cola == null){
            temp = new String[1];
            temp[0] = "No se encuentran programas en la cola";
            return temp;
        }else{
            temp = new String[cola.size()];
            int i = 0;
            for (String programa : cola) {
                String nombre = programa.split(",")[0];
                temp[i] = nombre;
            }
            return temp;
        }
    }
}
