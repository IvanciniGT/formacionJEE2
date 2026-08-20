package com.curso.diccionarios.gestion.impl.rest;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.URI;
import java.util.Optional;

import com.curso.diccionarios.gestion.respuesta.diccionario.DiccionarioEncontrado;
import com.curso.diccionarios.gestion.respuesta.diccionario.DiccionarioNoEncontrado;
import com.curso.diccionarios.gestion.respuesta.diccionario.ErrorAlObtenerDiccionario;
import com.curso.diccionarios.gestion.respuesta.diccionario.RespuestaDiccionario;
import com.curso.diccionarios.gestion.SuministradorDeDiccionarios;
import com.curso.diccionarios.gestion.Diccionario;
public class SuministradorDeDiccionariosBBDD implements SuministradorDeDiccionarios {

    private String rutaServidor;

    public SuministradorDeDiccionariosBBDD(String rutaServidor) {
        this.rutaServidor = rutaServidor;
    }

    public boolean tienesDiccionarioDe(String idioma){
        return switch(getDiccionario(idioma)) {
            case DiccionarioEncontrado encontrado -> true;
            default                               -> false;
        };

    }

    public Optional<Diccionario> dameDiccionario(String idioma){
        return switch(getDiccionario(idioma)) {
            case DiccionarioEncontrado encontrado -> Optional.of(new DiccionarioRest( rutaServidor, idioma ));
            default                               -> Optional.empty();
        };
    }

    public RespuestaDiccionario getDiccionario(String idioma) {
        // Aqui va la llamada al servidor.
        // A la ruta /v1/diccionarios/{idioma}
        // Necesitamos mirar el código de estado:
        // - 200 -> DiccionarioEncontrado
        // - 404 -> DiccionarioNoEncontrado
        // - 500 -> ErrorAlObtenerDiccionario
        // Vamos a hacer la petición con el aAPI nueva de Java 11, HttpClient / HttpRequest 
        // Manejados con patrones builder y switch expressions.
        HttpClient client = HttpClient.newHttpClient(); // Como si me abro una pestaña en el navegador. O como si preparo el comando curl, o si abro el postman o el boomerang
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(rutaServidor + "/v1/diccionario/" + idioma))
                .HEAD()
                .build();
        // Lanzo el request usando mi cliente y obtengo la respuesta
        try {
            var response = client.send(request, BodyHandlers.discarding()); // Paso del body
            int statusCode = response.statusCode();
            return switch (statusCode) {
                case 200 -> new DiccionarioEncontrado( new DiccionarioRest( rutaServidor, idioma ) );
                case 404 -> new DiccionarioNoEncontrado(idioma);
                default -> new ErrorAlObtenerDiccionario("Error interno del servidor");
            };
        } catch (Exception e) {
            e.printStackTrace();
            return new ErrorAlObtenerDiccionario(e.getMessage());
        }
    }

}

