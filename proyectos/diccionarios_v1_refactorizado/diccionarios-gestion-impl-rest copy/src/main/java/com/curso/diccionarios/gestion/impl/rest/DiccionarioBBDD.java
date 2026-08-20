package com.curso.diccionarios.gestion.impl.rest;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Optional;

import com.curso.diccionarios.gestion.respuesta.palabra.ErrorAlObtenerPalabra;
import com.curso.diccionarios.gestion.respuesta.palabra.PalabraEncontrada;
import com.curso.diccionarios.gestion.respuesta.palabra.PalabraNoEncontrada;
import com.curso.diccionarios.gestion.respuesta.palabra.RespuestaPalabra;
import com.curso.diccionarios.restv1.dto.RespuestaPalabraDTO;
import com.google.gson.Gson;
import com.curso.diccionarios.gestion.Diccionario;

public class DiccionarioBBDD implements Diccionario {

    private final String rutaServidor;
    private final String idioma;

    public DiccionarioBBDD(String rutaServidor, String idioma) {
        this.rutaServidor = rutaServidor;
        this.idioma = idioma;
    }

    public boolean existe(String palabra){
        return switch(dameSignificados(palabra)) {
            case PalabraEncontrada palabraEncontrada -> true;
            default                                  -> false;
        };
    }

    public Optional<List<String>> getSignificados(String palabra) {
        return switch(dameSignificados(palabra)) {
            case PalabraEncontrada palabraEncontrada -> Optional.of(palabraEncontrada.significados());
            default                                  -> Optional.empty();
        };
    }

    public RespuestaPalabra dameSignificados(String palabra) {
        HttpClient client = HttpClient.newHttpClient(); // Como si me abro una pestaña en el navegador. O como si preparo el comando curl, o si abro el postman o el boomerang
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(rutaServidor + "/v1/diccionario/" + idioma + "/" + palabra + "/significados"))
                .GET()
                .build();
        // Lanzo el request usando mi cliente y obtengo la respuesta
        try {
            var response = client.send(request, BodyHandlers.ofString()); // El cuerpo lleva un JSON con la lista de significados
            int statusCode = response.statusCode();
            return switch (statusCode) {
                case 200 -> {
                    String respuestaEnJSON = response.body(); // Aqui viene un objeto de tipo RespuestaPalabraDTO en JSON.
                    // Aquí podemos usar alguna librería que nos ayude a deserializar el JSON a un objeto de tipo RespuestaPalabraDTO.
                    // En mi caso usaré GSON, que es una librería de Google para trabajar con JSON en Java.
                    // Si tuvieramos la app en Spring Boot, podríamos usar Jackson, que es la librería que viene por defecto con Spring Boot para trabajar con JSON.
                    RespuestaPalabraDTO respuestaPalabraDTO = new Gson().fromJson(respuestaEnJSON, RespuestaPalabraDTO.class);
                    yield new PalabraEncontrada(palabra, respuestaPalabraDTO.significados());
                }
                case 404 -> new PalabraNoEncontrada(palabra);
                default -> new ErrorAlObtenerPalabra("Error interno del servidor");
            };
        } catch (Exception e) {
            return new ErrorAlObtenerPalabra(e.getMessage());
        }    
    }

}