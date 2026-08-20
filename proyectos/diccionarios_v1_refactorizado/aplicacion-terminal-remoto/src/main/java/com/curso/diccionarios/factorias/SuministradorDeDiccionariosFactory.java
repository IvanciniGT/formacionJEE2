package com.curso.diccionarios.factorias;

import com.curso.diccionarios.gestion.SuministradorDeDiccionarios;
import com.curso.diccionarios.gestion.impl.rest.SuministradorDeDiccionariosRest;

public class SuministradorDeDiccionariosFactory {

    private static final String RUTA_SERVIDOR = "http://localhost:8080";

    public static SuministradorDeDiccionarios getInstance(){
         //return new SuministradorDeDiccionariosEnFicheros(RUTA_DE_DICCIONARIOS);
         return new SuministradorDeDiccionariosRest(RUTA_SERVIDOR); // TODO: Implementar la obtención de un suministrador de diccionarios remoto
    }

}