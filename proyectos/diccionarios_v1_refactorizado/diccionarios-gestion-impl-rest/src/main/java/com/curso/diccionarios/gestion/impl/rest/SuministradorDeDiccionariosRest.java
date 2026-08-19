package com.curso.diccionarios.gestion.impl.rest;
import java.util.Optional;

import com.curso.diccionarios.gestion.respuesta.diccionario.RespuestaDiccionario;
import com.curso.diccionarios.gestion.SuministradorDeDiccionarios;
import com.curso.diccionarios.gestion.impl.rest.DiccionarioRest;
import com.curso.diccionarios.gestion.Diccionario;
public class SuministradorDeDiccionariosRest implements SuministradorDeDiccionarios {

    private String rutaServidor;

    public SuministradorDeDiccionariosRest(String rutaServidor) {
        this.rutaServidor = rutaServidor;
    }

    public boolean tienesDiccionarioDe(String idioma){
        return false;

    }

    public Optional<Diccionario> dameDiccionario(String idioma){
        return Optional.empty();
    }

    public RespuestaDiccionario getDiccionario(String idioma) {
        return null;
    }

}

