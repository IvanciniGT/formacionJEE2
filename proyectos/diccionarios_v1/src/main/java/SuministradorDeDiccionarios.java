import java.util.Optional;

public interface SuministradorDeDiccionarios { // Librería, Amazón (tienda online...)
    boolean tienesDiccionarioDe(String idioma);
    Optional<Diccionario> dameDiccionario(String idioma);
}