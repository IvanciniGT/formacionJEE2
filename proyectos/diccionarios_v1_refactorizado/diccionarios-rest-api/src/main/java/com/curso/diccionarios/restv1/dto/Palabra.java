package com.curso.diccionarios.restv1.dto;

public record Palabra(
    String texto,
    Boolean encontrado
) {

    public Palabra(String texto) {
        this(texto, null) ;
    }

}