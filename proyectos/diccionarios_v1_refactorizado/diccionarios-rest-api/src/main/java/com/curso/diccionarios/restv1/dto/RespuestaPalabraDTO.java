package com.curso.diccionarios.restv1.dto;

import java.util.List;

public record RespuestaPalabraDTO(
    Idioma idioma,
    Palabra palabra,
    List<String> significados,
    String error
) {

    public RespuestaPalabraDTO(Idioma idioma, Palabra palabra, List<String> significados) {
        this(idioma, palabra, significados, null);
    }

    public RespuestaPalabraDTO(Idioma idioma, Palabra palabra) {
        this(idioma, palabra, null, null);
    }

    public RespuestaPalabraDTO(Idioma idioma, Palabra palabra, String error) {
        this(idioma, palabra, null, error);
    }

}