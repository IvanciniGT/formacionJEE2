package com.curso.diccionarios.gestion.respuesta.palabra;

import java.util.List;

public record PalabraEncontrada(String palabra, List<String> significados) implements RespuestaPalabra {

    public PalabraEncontrada(String palabra) {
        this(palabra, List.of());
    }

}
