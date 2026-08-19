package com.curso.diccionarios.restv1.dto;

public record Idioma(
    String texto,
    Boolean encontrado
) {

    public Idioma(String texto) {
        this(texto, null) ;
    }

}
