package com.curso.diccionarios.gestion.impl.rest;

import java.util.List;
import java.util.Optional;

import com.curso.diccionarios.gestion.respuesta.palabra.PalabraEncontrada;
import com.curso.diccionarios.gestion.respuesta.palabra.RespuestaPalabra;
import com.curso.diccionarios.gestion.Diccionario;

public class DiccionarioRest implements Diccionario {

    private String rutaServidor;

    public DiccionarioRest(String rutaServidor) {
        this.rutaServidor = rutaServidor;
    }

    public boolean existe(String palabra){
        return switch(dameSignificados(palabra)) {
            case PalabraEncontrada palabraEncontrada -> true;
            default                                  -> false;
        };
    }

    public Optional<List<String>> getSignificados(String palabra) {
        return switch(dameSignificados(palabra)) {
            case PalabraEncontrada palabraEncontrada -> Optional.of(palabraEncontrada.significados());
            default                                  -> Optional.empty();
        };
    }


    public RespuestaPalabra dameSignificados(String palabra) {
        return null;
        // TODO
    }

}