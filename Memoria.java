/*

    NOMBRE DEL ARCHIVO: Memoria.java
    PROGRAMADOR: Francisco Castillo
    HISTORIAL DE MODIFICACION:


*/

public class Memoria {
    private String tipo;
    private int capacidad; // en gb
    private int megas; // capacidad en mb
    private int bloques; // 1 bloque =  mb
    private Programas p;
    
    public Memoria(){ // DDR
        tipo = "DDR";
        capacidad = 4;
        megas = capacidad * 1024;
        bloques = megas / 64;
        p = new Programas();
    }

    public Memoria(int capacidad){ // SDR
        tipo = "SDR";
        this.capacidad = capacidad;
        megas = capacidad * 1024;
        bloques = megas / 64;
        p = new Programas();
    }

    private void leerDatos(){

    }
}
