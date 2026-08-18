package com.curso.diccionarios.gestion;
import java.util.Optional;

public interface SuministradorDeDiccionarios { 
    boolean tienesDiccionarioDe(String idioma);
    Optional<Diccionario> dameDiccionario(String idioma);
}