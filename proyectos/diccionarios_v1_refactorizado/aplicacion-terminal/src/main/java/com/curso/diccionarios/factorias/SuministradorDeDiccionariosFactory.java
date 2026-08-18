package com.curso.diccionarios;

import com.curso.diccionarios.comunicador.ComunicadorConUsuario;
import com.curso.diccionarios.comunicador.impl.terminal.ComunicadorConUsuarioDesdeTerminal;
import com.curso.diccionarios.gestion.SuministradorDeDiccionarios;
import com.curso.diccionarios.gestion.impl.ficheros.SuministradorDeDiccionariosEnFicheros;
import com.curso.diccionarios.procesador.ProcesadorDePeticiones;

public class SuministradorDeDiccionariosFactory {

    private static final String RUTA_DE_DICCIONARIOS = "./diccionarios";

    public static SuministradorDeDiccionarios getInstance(){
         return new SuministradorDeDiccionariosEnFicheros(RUTA_DE_DICCIONARIOS);
    }

}