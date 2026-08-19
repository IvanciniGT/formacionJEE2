package com.curso.diccionarios.gestion.impl.ficheros;

import java.util.List;
import java.util.Map;

import com.curso.diccionarios.gestion.Diccionario;
import com.curso.diccionarios.gestion.respuesta.palabra.ErrorAlObtenerPalabra;
import com.curso.diccionarios.gestion.respuesta.palabra.PalabraEncontrada;
import com.curso.diccionarios.gestion.respuesta.palabra.PalabraNoEncontrada;
import com.curso.diccionarios.gestion.respuesta.palabra.RespuestaPalabra;

public class DiccionarioEnFichero implements Diccionario {

    private final Map<String, List<String>> palabrasYSignificados;

    public DiccionarioEnFichero(Map<String, List<String>> palabrasYSignificados) {
        this.palabrasYSignificados = palabrasYSignificados;
    }

    @Override
    public RespuestaPalabra getSignificados(String palabra) {
        try {
            List<String> significados = palabrasYSignificados.get(palabra);
            if (significados != null) {
                return new PalabraEncontrada(palabra, significados);
            } else {
                return new PalabraNoEncontrada(palabra);
            }
        } catch (Exception e) {
            return new ErrorAlObtenerPalabra(e.getMessage());
        }
    }
}