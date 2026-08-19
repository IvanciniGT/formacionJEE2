package com.curso.diccionarios.gestion;

import java.util.List;
import java.util.Optional;

import com.curso.diccionarios.gestion.respuesta.palabra.ErrorAlObtenerPalabra;
import com.curso.diccionarios.gestion.respuesta.palabra.PalabraEncontrada;
import com.curso.diccionarios.gestion.respuesta.palabra.PalabraNoEncontrada;
import com.curso.diccionarios.gestion.respuesta.palabra.RespuestaPalabra;

public interface Diccionario {

    default RespuestaPalabra dameSignificados(String palabra) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Deprecated(
            since = "1.1.0",
            forRemoval = true
    )
    boolean existe(String palabra);

    @Deprecated(
            since = "1.1.0",
            forRemoval = true
    )
    Optional<List<String>> getSignificados(String palabra) ;
}