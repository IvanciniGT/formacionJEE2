package com.curso.diccionarios.factorias;

import com.curso.diccionarios.comunicador.ComunicadorConUsuario;
import com.curso.diccionarios.comunicador.impl.terminal.ComunicadorConUsuarioDesdeTerminal;

public class ComunicadorConUsuarioFactory {

    public static ComunicadorConUsuario getInstance(String[] args) {
        return new ComunicadorConUsuarioDesdeTerminal(args);
    }

}