/*

    NOMBRE DEL ARCHIVO: Programas.java
    PROGRAMADOR: Francisco Castillo
    HISTORIAL DE MODIFICACION:
*/

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.List;

public class Programas {
    List<String> programas;
    
    public Programas(){
        crearArchivoHorario();
    }
    
    private void crearArchivoHorario(){
        try {
            File f = new File("Programas.txt");
            if (f.createNewFile()) {
                FileWriter w = new FileWriter("Programas.txt");
                w.write("GOOGLE CHROME,2070,8\n"+
                        "FIREFOX,64,16\n"+
                        "ZOOM,320,60\n"+
                        "WHATSAPP,128,8\n"+
                        "TELEGRAM,128,8\n"+
                        "VISUAL STUDIO,320,60\n"+
                        "MAIL,32,15\n"+
                        "CONTACTOS,32,8\n"+
                        "WORD,1024,10");
                w.close();
            }
        }catch (IOException ie) {
            System.out.println("Ocurrio un error, no se ha podido crear el archivo Programas.txt");
        }
    }
    
    
    /** 
     * @param programa El programa del que se desea obtener informacion
     * @return String La informacion del programa ("xd,-1,-1" en caso de programa invalido)
     */
    public String getPrograma(String programa){
        try {
            Path path = Paths.get("Programas.txt");
            programas = Files.readAllLines(path);
            for (String linea : programas) {
                String nombre = linea.split(",")[0]; //La posicion 0 es el nombre del programa
                if(programa.equals(nombre)){
                    return linea;
                }
            }
        }catch (IOException ie) {
            crearArchivoHorario();
        }
        return "xd,-1,-1";
    }
}
