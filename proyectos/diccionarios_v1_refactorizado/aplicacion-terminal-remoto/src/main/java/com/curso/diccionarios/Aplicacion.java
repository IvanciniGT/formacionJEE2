package com.curso.diccionarios;

import com.curso.diccionarios.comunicador.ComunicadorConUsuario;
import com.curso.diccionarios.factorias.ComunicadorConUsuarioFactory;
import com.curso.diccionarios.factorias.SuministradorDeDiccionariosFactory;
import com.curso.diccionarios.gestion.SuministradorDeDiccionarios;
import com.curso.diccionarios.procesador.ProcesadorDePeticiones;

public class Aplicacion {

    public static void main(String[] args) {
        // Establecemos los componentes concretos que vamos a usar en la aplicación.
        SuministradorDeDiccionarios suministradorDeDiccionarios = SuministradorDeDiccionariosFactory.getInstance();
        ComunicadorConUsuario       comunicadorConUsuario       = ComunicadorConUsuarioFactory.getInstance(args);
        ProcesadorDePeticiones      procesadorDePeticiones      = new ProcesadorDePeticiones(suministradorDeDiccionarios, comunicadorConUsuario);
        
        // La pongo en marcha para que procese la petición del usuario.
        procesadorDePeticiones.procesarPeticion();
    }

}