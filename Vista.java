/*

    NOMBRE DEL ARCHIVO: Vista.java
    PROGRAMADOR: Francisco Castillo
    HISTORIAL DE MODIFICACION:
        -Se han agregado los metodos:
            mostrarMenu()
            opcionInvalida()
        Se modifico la forma en la que se muestra el total, disponible y en uso de La RAM
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
    public void opcionINvalida(){
        System.out.println("Ha ingresado una opcion incorrecta.");
    }
    
    /** 
     * @return int La opcion del menu seleccionada por el usuario
     */
    public int mostrarMenu(){
        try{
            System.out.println("");
            System.out.println("Escoga una de las siguientes opciones:");
            System.out.println("1. Reiniciar simulador");
            System.out.println("2. Ejecutar un programa");
            System.out.println("3. Ver RAM (Total, disponible y en uso)");
            System.out.println("4. Ver programas en ejecucion");
            System.out.println("5. Ver programas en cola");
            System.out.println("6. Ver espacios de un programa");
            System.out.println("7. Ver estado de la RAM");
            System.out.println("8. Realizar un ciclo de reloj");
            System.out.println("9. Salir\n");
            int temp = scan.nextInt();
            scan.nextLine();
            return temp;
        }catch(Exception e){
            scan.next();
            return -1;
        }
        
    }
    
    /** 
     * @return String El tipo de RAM ingresada por el usuario (Cualquier String sin verificar)
     */
    public String pedirTipoRam(){
        System.out.println("Ingrese el tipo de RAM que desea simular: (DDR o SDR)");
        return scan.nextLine().toUpperCase();
    }
    public void tipoInvalido(){
        System.out.println("Si ha ingresado una capacidad invalida de memoria para su SDR, se ha asignado 8 GB por defecto");;
    }
    
    /** 
     * @return int El tamanio de su memoria (sin verificar)
     */
    public int pedirTamanioMemoria(){
        System.out.println("Ingrese la capacidad de su SDR en GB: (4, 8, 12, 16, 32 o 64)");
        try{
            int temp = scan.nextInt();
            scan.nextLine();
            return temp;    
        }catch(Exception e){
            System.out.println("Ha ingresado un valor no valido.");
            return -1;
        }
    }
    
    /** 
     * @return String el programa que desea ingresar
     */
    public String pedirPrograma(){
        System.out.println("Ingrese el nombre del programa:");
        return scan.nextLine().toUpperCase();
    }
    
    /** 
     * @param dato [capacidad en GB, mb en uso, mb disponibles]
     */
    public void mostrarRamTotal(String[] dato){
        System.out.println("Su RAM "+ dato[0] +" cuenta con un total de: " + dato[1] + " GB en total (" + dato[2] + " mb en uso | " + dato[3] + " mb disponibles)");
    }
    
    /** 
     * @param datos arreglo donde cada posicion es el nombre del programa
     */
    public void mostrarProgramasEnEjecucion(String[] datos){
        System.out.println("Se estan ejecutando:");
        for (String string : datos) {
            System.out.println("- " + string);
        }
    }
    
    /** 
     * @param datos arreglo donde cada posicion es el nombre del programa
     */
    public void mostrarProgramasEnCola(String[] datos){
        System.out.println("Estan en cola:");
        for (String string : datos) {
            System.out.println("- " + string);
        }
    }
    
    /** 
     * @param programa Programa al que se solicito el proceso
     * @param bloques Los bloques que ocupa
     */
    public void mostrarBloquesDelPrograma(String programa, String bloques){
        System.out.println("Los bloques que usa "+ programa + " son: " + bloques);
    }
    public void mostrarRam(){
        System.out.println("xdram");
    }
    public void mostrarLinea(){
        System.out.println("xdlinea");
    }
    public void ingresadoConExito(){
        System.out.println("Se ha ingresado correctamente!");
    }
    public void programaNoValidO(){
        System.out.println("Se ha ingresado un programa no valido.");
    }
    
    /** 
     * @param datos Los datos de los bloques ocupados
     */
    public void mostrarEsado(String[] datos){
        int i = 1;
        for (String dato : datos) {
            if(i % 16 != 0){ // 16 por la cantidad de bloques en 1 GB
                System.out.print("|" + dato + "|\t");
            }else{
                System.out.println("|" + dato + "|");
            }
        }
    }
    public void expandidoMemoria(){
        System.out.println("No se ha podido ingresar el programa por falta de memoria, por ser DDR se ha expandido. Pruebe de nuevo.");
    }
    public void programaIngresado(){
        System.out.println("Se ha ingresado exitosamente.");
    }
    public void mandadoACola(){
        System.out.println("La memoria no cuetna con suficiente almacenamiento. El programa ha sido mandado a la cola.");
    }
}
