import java.util.Optional;
import java.util.List;

public class ComunicadorConUsuarioDesdeTerminal implements ComunicadorConUsuario {

    public Optional<String> getPalabraDelUsuario(){

    }
    public Optional<String> getIdiomaDelUsuario(){

    }

    public void mostrarSignificadosAlUsuario(String palabra, String idioma, List<String> significados){
        System.out.println("La palabra " + palabra + " en el idioma " + idioma + " tiene los siguientes significados:");
        /*
        for (String significado : significados) {
            System.out.println("    - " + significado);
        }
        */
       // Desde java 1.8 usando programación funcional, puedo imprimir los resultados con otra sintaxis:
       significados.forEach(significado -> System.out.println("    - " + significado));
    }
    public void mostrarPalabraNoExisteAlUsuario(String palabra, String idioma){
        System.out.println("La palabra " + palabra + " no existe en el idioma " + idioma + ".");
    }
    public void mostrarErrorDeUsoDelProgramaAlUsuario(){
        System.out.println("Lo siento, pero no has indicado los datos necesarios para realizar la búsqueda.");
        System.out.println("Debes indicar la palabra y el idioma.");
        System.out.println("Ejemplo:");
        System.out.println("    buscarPalabra melón es");
    }
    public void mostrarErrorDeIdiomaNoContempladoAlUsuario(String idioma){
        System.out.println("Lo siento, pero no tengo diccionario para el idioma " + idioma + ".");
    }
    public void mostrarErrorInternoDelSistemaAlUsuario(String mensajeErrorInternoDelSistema){
        System.out.println("Se ha producido un error interno en el sistema: ");
        System.out.println(mensajeErrorInternoDelSistema);
    }

}