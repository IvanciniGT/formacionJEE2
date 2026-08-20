package com.curso.diccionarios.restv1.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL) // Le indico a Jackson que cuando genere el JSON no incluya los campos que sean null. Esto es útil para que el JSON sea más limpio y no tenga campos innecesarios.

public record Idioma(
    String texto,
    Boolean encontrado
) {

    public Idioma(String texto) {
        this(texto, null) ;
    }

}