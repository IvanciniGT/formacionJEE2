package com.curso.diccionarios.restv1.dto;

import java.util.List;

public record RespuestaPalabra(
    Idioma idioma,
    Palabra palabra,
    List<String> significados,
    String error
) {}