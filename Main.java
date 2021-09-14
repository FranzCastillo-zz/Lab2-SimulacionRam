import javax.lang.model.util.ElementScanner14;

/*

    NOMBRE DEL ARCHIVO: Main.java
    PROGRAMADOR: Francisco Castillo
    HISTORIAL DE MODIFICACION:
        -Se agrego la propiedad de memorias

*/

public class Main{
    public static void main(String[] args) {
        Vista v = new Vista();
        Memoria m;
        int opcion;
        v.saludo();
        if(v.pedirTipoRam().equals("SDR")){
            int tamanio = v.pedirTamanioMemoria();
            m = new Memoria("SDR", tamanio);
        }else{
            m = new Memoria("DDR", -1);
        }

        while(true){
            opcion = v.mostrarMenu();
            switch(opcion){
                case 1: //Reiniciar
                    if(v.pedirTipoRam().equals("SDR")){
                        int tamanio = v.pedirTamanioMemoria();
                        m = new Memoria("SDR", tamanio);
                        v.tipoInvalido();
                    }else{
                        m = new Memoria("DDR", -1);
                    }
                break;
                case 2: //Ejecutar un programa
                    if(m.ingresarPrograma(v.pedirPrograma())){
                        v.ingresadoConExito();
                    }else{
                        v.programaNoValidO();
                    }
                break;
                case 3: // Mostrar total, en uso y disponible
                    v.mostrarRamTotal(m.getDatosRam());
                break;
                case 4: // Programas en ejecucion
                    v.mostrarProgramasEnEjecucion(m.getProgramasEnEjecucion());
                break;
                case 5: //Programas en cola

                break;
                case 6: // Espacios de X programa
                    String nombre = v.pedirPrograma();
                    v.mostrarBloquesDelPrograma(nombre, m.getBloques(nombre));
                break;
                case 7: // Ver estado
                break;
                case 8: // Ciclo de reloj
                    m.hacerCiclo();
                break;
                case 9: // Salir
                    System.exit(1);
                break;
                default:
                    v.opcionINvalida();
                break;
            }
        }
    }
}