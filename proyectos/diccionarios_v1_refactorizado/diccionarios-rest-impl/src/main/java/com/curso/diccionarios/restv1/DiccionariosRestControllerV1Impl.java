package com.curso.diccionarios.restv1;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.curso.diccionarios.restv1.dto.RespuestaPalabraDTO;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.curso.diccionarios.gestion.respuesta.diccionario.RespuestaDiccionario;
import com.curso.diccionarios.gestion.respuesta.diccionario.DiccionarioEncontrado;
import com.curso.diccionarios.gestion.respuesta.diccionario.DiccionarioNoEncontrado;
import com.curso.diccionarios.gestion.respuesta.diccionario.ErrorAlObtenerDiccionario;
import com.curso.diccionarios.gestion.respuesta.palabra.RespuestaPalabra;
import com.curso.diccionarios.gestion.respuesta.palabra.PalabraEncontrada;
import com.curso.diccionarios.gestion.respuesta.palabra.PalabraNoEncontrada;
import com.curso.diccionarios.gestion.respuesta.palabra.ErrorAlObtenerPalabra;

import com.curso.diccionarios.restv1.dto.Idioma;
import com.curso.diccionarios.restv1.dto.Palabra;
import com.curso.diccionarios.gestion.SuministradorDeDiccionarios;

@RestController // Esta anotación no se hereda. El resto de las que tenemos a nivel de la clase si.
public class DiccionariosRestControllerV1Impl implements DiccionariosRestControllerV1 {
    
    private final SuministradorDeDiccionarios suministradorDeDiccionarios;

    public DiccionariosRestControllerV1Impl(SuministradorDeDiccionarios suministradorDeDiccionarios) { // Inyección de dependencias!
        this.suministradorDeDiccionarios = suministradorDeDiccionarios;
    }

    public ResponseEntity<Void> existeIdioma(@PathVariable("idioma") String idioma){
       RespuestaDiccionario respuesta = suministradorDeDiccionarios.getDiccionario(idioma);
       return switch (respuesta) {
            case DiccionarioEncontrado diccionarioEncontrado -> ResponseEntity.ok().build();       // 200
            case DiccionarioNoEncontrado diccionarioNoEncontrado -> ResponseEntity.notFound().build(); // 404
            default -> ResponseEntity.internalServerError().build(); // 500
        };
        /*
       switch (respuesta) {
            case DiccionarioEncontrado diccionarioEncontrado -> {
                return ResponseEntity.ok().build();       // 200
            }
            case DiccionarioNoEncontrado diccionarioNoEncontrado -> {
                return ResponseEntity.notFound().build(); // 404
            }
            default -> {
                return ResponseEntity.internalServerError().build(); // 500
            }
        }*/
    }

    public ResponseEntity<Void> existePalabra(@PathVariable("idioma") String idioma, @PathVariable("palabra") String palabra){
        RespuestaDiccionario respuestaDiccionario = suministradorDeDiccionarios.getDiccionario(idioma);
        switch (respuestaDiccionario) {
            case DiccionarioEncontrado diccionarioEncontrado -> {
                // El diccionario existe, ahora verificamos si la palabra existe en ese diccionario
                RespuestaPalabra respuestaPalabra = diccionarioEncontrado.diccionario().dameSignificados(palabra);
                switch (respuestaPalabra) {
                    case PalabraEncontrada palabraEncontrada -> {
                        return ResponseEntity.ok().build();       // 200
                    }
                    case PalabraNoEncontrada palabraNoEncontrada -> {
                        return ResponseEntity.notFound().build(); // 404
                    }
                    default -> {
                        return ResponseEntity.internalServerError().build(); // 500
                    }
                }
            }
            case DiccionarioNoEncontrado diccionarioNoEncontrado -> {
                return ResponseEntity.notFound().build(); // 404
            }
            default -> {
                return ResponseEntity.internalServerError().build(); // 500
            }
        }
    }

    public ResponseEntity<RespuestaPalabraDTO> obtenerSignificados(@PathVariable("idioma") String idioma, @PathVariable("palabra") String palabra){
        RespuestaDiccionario respuestaDiccionario = suministradorDeDiccionarios.getDiccionario(idioma);
        switch (respuestaDiccionario) {
            case DiccionarioEncontrado diccionarioEncontrado -> {
                // El diccionario existe, ahora verificamos si la palabra existe en ese diccionario
                RespuestaPalabra respuestaPalabra = diccionarioEncontrado.diccionario().dameSignificados(palabra);
                switch (respuestaPalabra) {
                    case PalabraEncontrada palabraEncontrada -> {
                        return ResponseEntity.ok(new RespuestaPalabraDTO(
                                new Idioma(idioma, true),
                                new Palabra(palabra, true),
                                palabraEncontrada.significados()
                            )
                        );
                    }
                    case PalabraNoEncontrada palabraNoEncontrada -> {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                            new RespuestaPalabraDTO(
                                new Idioma(idioma, true),
                                new Palabra(palabra, false)
                            )
                        ); // 404
                    }
                    case ErrorAlObtenerPalabra error -> {
                        return ResponseEntity.status(500).body(
                            new RespuestaPalabraDTO(
                                new Idioma(idioma, true),
                                new Palabra(palabra),
                                error.mensajeError()
                            )
                        ); // 500
                    }
                }
            }
            case DiccionarioNoEncontrado diccionarioNoEncontrado -> {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new RespuestaPalabraDTO(
                        new Idioma(idioma, false),
                        new Palabra(palabra, false)
                    )
                ); // 404
            }
            case ErrorAlObtenerDiccionario error -> {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new RespuestaPalabraDTO(
                        new Idioma(idioma),
                        new Palabra(palabra),
                        error.mensajeError()
                    )
                ); // 500
            }
        }
    }
}

// el objetivo de este controlador (PATRON ADAPTADOR) es convertir llamadas HTTP a llamadas JAVA 
// al SuministradorDeDiccionarios que se esté usando en el servidor.
