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
import java.util.List;


public class Memoria {
    private String tipo;
    private int capacidad; // en gb
    private int megas; // capacidad en mb
    private int bloques; // 1 bloque =  mb
    private int megasUsadas = 0;
    private Programas p;
    final int[] memorias = {4, 8, 12, 16, 32, 64};
    List<String> lineas;
    
    public Memoria(String tipo, int capacidad){
        if(tipo.equals("DDR")){ // DDR
            this.tipo = tipo;
            this.capacidad = 4;
            megas = this.capacidad * 1024;
            bloques = megas / 64;
            p = new Programas();
            crearArchivoMemoria();
        }else{ // SDR
            this.tipo = "SDR";
            boolean encontrado = false;
            for (int version : memorias) {
                if(!encontrado){
                    if(version == capacidad){
                        this.capacidad = capacidad;
                        megas = capacidad * 1024;
                        bloques = megas / 64;
                        p = new Programas();
                        crearArchivoMemoria();
                        encontrado = true;
                    }
                }
            } // Si aun no se encontro
            if(!encontrado){
                this.capacidad = 8;
                megas = this.capacidad * 1024;
                bloques = megas / 64;
                p = new Programas();
                crearArchivoMemoria();
            }
            
        }
    }

    private void crearArchivoMemoria(){
        try {
            File f = new File("Memoria.txt");
            FileWriter w = new FileWriter("Memoria.txt");
            if (f.createNewFile()) {
                for(int i = 0; i <= bloques; i++){
                    w.write("vacio\n");
                }
            }else{
                for(int i = 0; i < bloques; i++){
                    w.write("vacio\n");
                }
            }
            w.close();
        }catch (IOException ie) {
            System.out.println("Ocurrio un error, no se ha podido crear el archivo Memoria.txt");
        }
    }
    private void cambiarLineaMemoria(int numLinea, String datos){
        try{
            Path path = Paths.get("Memoria.txt");
            List<String> lines = Files.readAllLines(path);
            lines.set(numLinea, datos);
            Files.write(path, lines);
        }catch (IOException ie) {
            crearArchivoMemoria();
        }
    }
    private void leerDatos(){
        try{
            Path path = Paths.get("Memoria.txt");
            lineas = Files.readAllLines(path);
        }catch(IOException e){
            crearArchivoMemoria();
        }
    }
    

    public boolean ingresarPrograma(String programa){ // En el archivo Memoria se vera como linea,nombre,ciclos
        leerDatos();
        boolean exitoso = false;

        String[] info = p.getPrograma(programa).split(","); // nombre,espacio,ciclos
        String nombre = info[0];
        double espacio = Math.ceil(Double.parseDouble(info[1]) / 64); // mb → bloques
        String ciclos = info[2];
        boolean escrito = false;

        //Verifica si ingreso un programa valido
        if(!nombre.equals("xd")){
            megasUsadas += (int)(64 * espacio); 
            for(int i = 0; i < lineas.size() && !escrito; i++){
                String lineaActual = lineas.get(i);
                if(lineaActual.equals("vacio")){
                    // Verifica si el espacio que se necesita es > 1
                    if(espacio > 1){
                        boolean posible = true;
                        // Ve j espacios adelante para ver si es posible ingresarlo ahi
                        for(int j = i; j <= i + espacio && posible; j++){  
                            String jAdelante = lineas.get(j);
                            if(!jAdelante.equals("vacio")){
                                posible = false;
                            }
                        }
                        // Si si fue posible, lo escribe.
                        if(posible){
                            for(int j = i; j < espacio + i; j++){
                                String datos = j + "," + nombre + "," + ciclos;
                                cambiarLineaMemoria(j, datos);
                            }
                            escrito = true;
                            exitoso = true;
                        }
                    }else{ // Escribe en una linea el programa
                        String datos = i + "," + nombre + "," + ciclos;
                        cambiarLineaMemoria(i, datos);
                        escrito = true;
                        exitoso = true;
                    }
                    
                }
            }
        }
        return exitoso;
    }

    public String[] getDatosRam(){ //tipo,gb totales, mb usados, mb disponibles
        String[] temp = new String[4];
        temp[0] = tipo;
        temp[1] = capacidad + "";
        temp[2] = megasUsadas + "";
        temp[3] = (megas - megasUsadas) + "";
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
                i++;
            }
        }
    }
}
