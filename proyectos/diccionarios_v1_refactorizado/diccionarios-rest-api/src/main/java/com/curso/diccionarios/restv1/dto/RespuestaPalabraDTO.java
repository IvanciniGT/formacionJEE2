package com.curso.diccionarios.restv1.dto;

import java.util.List;

public record RespuestaPalabraDTO(
    Idioma idioma,
    Palabra palabra,
    List<String> significados,
    String error
) {}