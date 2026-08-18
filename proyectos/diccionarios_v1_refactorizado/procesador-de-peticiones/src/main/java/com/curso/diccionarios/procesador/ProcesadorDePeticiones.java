package com.curso.diccionarios.procesador;
import java.util.List;
import java.util.Optional;

import com.curso.diccionarios.comunicador.ComunicadorConUsuario;
import com.curso.diccionarios.gestion.Diccionario;
import com.curso.diccionarios.gestion.SuministradorDeDiccionarios;

public class ProcesadorDePeticiones { 

    private SuministradorDeDiccionarios suministradorDeDiccionarios;
    private ComunicadorConUsuario comunicadorConUsuario;

    public ProcesadorDePeticiones(SuministradorDeDiccionarios suministradorDeDiccionarios, ComunicadorConUsuario comunicadorConUsuario) {
        this.suministradorDeDiccionarios = suministradorDeDiccionarios;
        this.comunicadorConUsuario = comunicadorConUsuario;
    }

    public void procesarPeticion(){
        Optional<String> palabra = comunicadorConUsuario.getPalabraDelUsuario();
        Optional<String> idioma  = comunicadorConUsuario.getIdiomaDelUsuario();
        if( validarDatosUsuario(palabra, idioma) ) {
            if( suministradorDeDiccionarios.tienesDiccionarioDe( idioma.get() ) ){
                procesarDiccionarioExistente(palabra, idioma);
            } else {
                comunicadorConUsuario.mostrarErrorDeIdiomaNoContempladoAlUsuario( idioma.get() );
            }
        }
    }

    private boolean  validarDatosUsuario(Optional<String> palabra, Optional<String> idioma){
        if( palabra.isEmpty() || idioma.isEmpty() ){
            comunicadorConUsuario.mostrarErrorDeUsoDelProgramaAlUsuario();
            return false;
        }
        return true;
    }

    private void procesarDiccionarioExistente(Optional<String> palabra, Optional<String> idioma) {
        Optional<Diccionario> diccionario = suministradorDeDiccionarios.dameDiccionario( idioma.get() );
        if( diccionario.isPresent() ){
            Diccionario diccionarioDelIdioma = diccionario.get();
            if( diccionarioDelIdioma.existe( palabra.get() ) ){
                procesarPalabraExistente(palabra, idioma, diccionarioDelIdioma);
            } else {
                comunicadorConUsuario.mostrarPalabraNoExisteAlUsuario( palabra.get(), idioma.get() );
            }
        } else {
            comunicadorConUsuario.mostrarErrorInternoDelSistemaAlUsuario( "Error interno del sistema: La implementación del componente de gestión de diccionarios no me ha devuelto un diccionario para el idioma indicado. A pesar de haberme dicho que sí que tenía diccionario para ese idioma. Contacte con soporte técnico" );
        }
    }

    private void procesarPalabraExistente(Optional<String> palabra, Optional<String> idioma, Diccionario diccionarioDelIdioma) {
        Optional<List<String>> significados = diccionarioDelIdioma.getSignificados( palabra.get() );
        if( significados.isPresent() ){
            comunicadorConUsuario.mostrarSignificadosAlUsuario( palabra.get(), idioma.get(), significados.get() );
        } else {
            comunicadorConUsuario.mostrarErrorInternoDelSistemaAlUsuario( "Error interno del sistema: La implementación del componente de gestión de diccionarios me ha devuelto un diccionario para el idioma indicado, pero no me ha devuelto los significados de la palabra que supuestamente SI EXISTE. Contacte con soporte técnico" );
        }
    }

}