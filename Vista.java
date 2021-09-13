/*

    NOMBRE DEL ARCHIVO: Vista.java
    PROGRAMADOR: Francisco Castillo
    HISTORIAL DE MODIFICACION:
        -Se han agregado los metodos:
            mostrarMenu()

*/
import java.util.Scanner;

public class Vista {
    private Scanner scan;

    public Vista(){
        scan = new Scanner(System.in);
    }

    public void saludo(){
        System.out.println("Bienvenido al simulador de RAM. Seleccione una de las siguientes opciones");
    }
    public void despedida(){
        System.out.println("Hasta la proxima!");
    }
    public int mostrarMenu(){
        System.out.println("Escoga una de las siguientes opciones:");
        System.out.println("1. Reiniciar simulador");
        System.out.println("2. Ejecutar un programa");
        System.out.println("3. Ver RAM (Total, disponible y en uso)");
        System.out.println("4. Ver programas en ejecucion");
        System.out.println("5. Ver programas en cola");
        System.out.println("6. Ver espacios de un programa");
        System.out.println("7. Ver estado de la RAM");
        System.out.println("8. Realizar un ciclo de reloj");
        System.out.println("9. Salir");
        int temp = scan.nextInt();
        scan.nextLine();
        return temp;
    }
    public String pedirTipoRam(){
        System.out.println("Ingrese el tipo de RAM que desea simular: (DDR o SDR)");
        return scan.nextLine();
    }
    public int pedirTamanioMemoria(){
        System.out.println("Ingrese la memoria de su SDR:");
        int temp = scan.nextInt();
        scan.nextLine();
        return temp;
    }
    public String pedirPrograma(){
        System.out.println("Ingrese el programa que desea ejecutar:");
        return scan.nextLine().toUpperCase();
    }
    public void mostrarRamTotal(int dato){
        System.out.println("Su RAM cuenta con un total de: " + dato + " GB en total");
    }
    public void mostrarRamDisponible(int dato){
        System.out.println("Su RAM cuenta con " + dato + " GB disponibles");
    }
    public void mostrarRamEnUso(int dato){
        System.out.println("Se estan utilizando " + dato + "GB de su memoria");
    }
    public void mostrarProgramasEnEjecucion(String[] datos){
        System.out.println("Se estan ejecutando:");
        for (String string : datos) {
            System.out.println("- " + string);
        }
    }
    public void mostrarProgramasEnCola(String[] datos){
        System.out.println("En la cola se encuentran actualmente:");
        for (String string : datos) {
            System.out.println("- " + string);
        }
    }
    public void mostrarBloquesDelPrograma(String programa){
        System.out.println("Los bloques que usa su programa son: " + programa);
    }
    public void mostrarRam(){
        System.out.println("xdram");
    }
    public void mostrarLinea(){
        System.out.println("xdlinea");
    }
}
