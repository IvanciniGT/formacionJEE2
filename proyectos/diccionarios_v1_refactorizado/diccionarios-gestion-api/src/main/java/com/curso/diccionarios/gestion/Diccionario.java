package com.curso.diccionarios.gestion;

import java.util.List;
import java.util.Optional;

import com.curso.diccionarios.gestion.respuesta.palabra.ErrorAlObtenerPalabra;
import com.curso.diccionarios.gestion.respuesta.palabra.PalabraEncontrada;
import com.curso.diccionarios.gestion.respuesta.palabra.PalabraNoEncontrada;
import com.curso.diccionarios.gestion.respuesta.palabra.RespuestaPalabra;

public interface Diccionario {

    default RespuestaPalabra getSignificados(String palabra) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Deprecated
    default boolean existe(String palabra) {
        return getSignificados(palabra) instanceof PalabraEncontrada;
    }

    @Deprecated
    default Optional<List<String>> getSignificadosLegacy(String palabra) {
        return switch (getSignificados(palabra)) {
            case PalabraEncontrada pe -> Optional.of(pe.significados());
            case PalabraNoEncontrada ignored -> Optional.empty();
            case ErrorAlObtenerPalabra ignored -> Optional.empty();
        };
    }
}