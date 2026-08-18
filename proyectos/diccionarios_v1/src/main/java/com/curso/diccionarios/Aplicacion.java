package com.curso.diccionarios;

import com.curso.diccionarios.comunicador.ComunicadorConUsuario;
import com.curso.diccionarios.comunicador.impl.terminal.ComunicadorConUsuarioDesdeTerminal;
import com.curso.diccionarios.gestion.SuministradorDeDiccionarios;
import com.curso.diccionarios.gestion.impl.ficheros.SuministradorDeDiccionariosEnFicheros;
import com.curso.diccionarios.procesador.ProcesadorDePeticiones;

public class Aplicacion {

    public static void main(String[] args) {
        SuministradorDeDiccionarios suministradorDeDiccionarios = new SuministradorDeDiccionariosEnFicheros("./diccionarios");
        ComunicadorConUsuario comunicadorConUsuario = new ComunicadorConUsuarioDesdeTerminal(args);
        ProcesadorDePeticiones procesadorDePeticiones = new ProcesadorDePeticiones(suministradorDeDiccionarios, comunicadorConUsuario);
        procesadorDePeticiones.procesarPeticion();
    }

}