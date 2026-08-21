package com.curso.diccionarios.restv1.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL) // Le indico a Jackson que cuando genere el JSON no incluya los campos que sean null. Esto es útil para que el JSON sea más limpio y no tenga campos innecesarios.
public record RespuestaPalabraDTO(
    Idioma idioma,
    Palabra palabra,
    List<String> significados,
    List<String> sugerencias,
    String error
) {

    public RespuestaPalabraDTO(Idioma idioma, Palabra palabra, List<String> significados) {
        this(idioma, palabra, significados, null, null);
    }

    public RespuestaPalabraDTO(List<String> sugerencias, Idioma idioma, Palabra palabra) {
        this(idioma, palabra, null, sugerencias, null);
    }

    public RespuestaPalabraDTO(Idioma idioma, Palabra palabra, String error) {
        this(idioma, palabra, null, null, error);
    }

}