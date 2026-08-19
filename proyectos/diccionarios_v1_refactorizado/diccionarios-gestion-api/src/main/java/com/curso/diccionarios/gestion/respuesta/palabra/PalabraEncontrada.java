package com.curso.diccionarios.gestion.respuesta.palabra;

import java.util.List;

public record PalabraEncontrada(String palabra, List<String> significados) implements RespuestaPalabra {}
