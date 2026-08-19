package com.curso.diccionarios.factorias;

import com.curso.diccionarios.gestion.SuministradorDeDiccionarios;
import com.curso.diccionarios.gestion.impl.ficheros.SuministradorDeDiccionariosEnFicheros;

public class SuministradorDeDiccionariosFactory {

    private static final String RUTA_DE_DICCIONARIOS = "diccionarios";

    public static SuministradorDeDiccionarios getInstance(){
         return new SuministradorDeDiccionariosEnFicheros(RUTA_DE_DICCIONARIOS);
    }

}