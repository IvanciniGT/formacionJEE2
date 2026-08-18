import java.util.Optional;
import java.util.List;

public interface ComunicadorConUsuario {

    // Entrada
    Optional<String> getPalabraDelUsuario();
    Optional<String> getIdiomaDelUsuario();
    // Salida
    void mostrarSignificadosAlUsuario(String palabra, String idioma, List<String> significados);
    void mostrarPalabraNoExisteAlUsuario(String palabra, String idioma);
    void mostrarErrorDeUsoDelProgramaAlUsuario();
    void mostrarErrorDeIdiomaNoContempladoAlUsuario(String idioma);

    void mostrarErrorInternoDelSistemaAlUsuario(String mensajeErrorInternoDelSistema);

}