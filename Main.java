/*

    NOMBRE DEL ARCHIVO: Main.java
    PROGRAMADOR: Francisco Castillo
    HISTORIAL DE MODIFICACION:


*/

public class Main{
    public static void main(String[] args) {
        Vista v = new Vista();
        Memoria m = new Memoria();
        int opcion;
        v.saludo();
        while(true){
            opcion = v.mostrarMenu();
            switch(opcion){
                case 1: //Reiniciar
                break;
                case 2: //Ejecutar un programa
                break;
                case 3: // Mostrar total, en uso y disponible
                break;
                case 4: // Programas en ejecucion
                break;
                case 5: //Programas en cola
                break;
                case 6: // Espacios de X programa
                break;
                case 7: // Ver estado
                break;
                case 8: // Ciclo de relok
                break;
                case 9: // Salir
                break;
            }
        }
    }
}