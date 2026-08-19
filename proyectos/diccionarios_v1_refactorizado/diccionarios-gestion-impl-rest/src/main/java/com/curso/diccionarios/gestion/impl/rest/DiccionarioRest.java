package com.curso.diccionarios.gestion.impl.rest;

import java.util.List;
import java.util.Optional;

import com.curso.diccionarios.gestion.respuesta.palabra.RespuestaPalabra;
import com.curso.diccionarios.gestion.Diccionario;

public class DiccionarioRest implements Diccionario {

    public RespuestaPalabra dameSignificados(String palabra) {
        return null;
    }

    public boolean existe(String palabra){
        return false;

    }

    public Optional<List<String>> getSignificados(String palabra) {
            return Optional.empty();

    }
}