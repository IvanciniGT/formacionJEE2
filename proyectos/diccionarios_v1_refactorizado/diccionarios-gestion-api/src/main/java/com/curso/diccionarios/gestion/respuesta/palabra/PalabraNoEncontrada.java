package com.curso.diccionarios.gestion.respuesta.palabra;

import java.util.List;
import java.util.Optional;

import com.curso.diccionarios.gestion.Diccionario;

public final class PalabraNoEncontrada implements RespuestaPalabra {

    private final String palabra;
    private List<String> palabrasSimilares;
    private Diccionario diccionario;

    public PalabraNoEncontrada(String palabra, List<String> palabrasSimilares) {
        this.palabra = palabra;
        this.palabrasSimilares = palabrasSimilares;
    }

    public PalabraNoEncontrada(String palabra, Diccionario diccionario) {
        this.diccionario = diccionario;
        this.palabra = palabra;
    }
    
    public PalabraNoEncontrada(String palabra) {
        this.palabra = palabra;
    }

    public Optional<List<String>> getPalabrasSimilares() {
        // Currándomelo un poco, si el valor es null, podría hacer en este momento una petición al Diccionario
        // Para sacar las palabras similares, y devolverlas. MODO LAZY o EAGER!
        // Dependiendo de si se han dado de antemano o no, las palabras similares, las devuelvo o hago la petición al Diccionario para obtenerlas.
        // Para esto, necesitaría en constructor una referencia al Diccionario, y en este momento no la tengo. Así que de momento, devuelvo lo que me han dado.
        if(palabrasSimilares == null && diccionario != null) { // MODO LAZY
            palabrasSimilares = switch (diccionario.existePalabra(palabra)) {
                case PalabraNoEncontrada pnf -> pnf.getPalabrasSimilares().orElse(null);
                default -> null;
            };
        }
        return Optional.ofNullable(palabrasSimilares);
    }

}

