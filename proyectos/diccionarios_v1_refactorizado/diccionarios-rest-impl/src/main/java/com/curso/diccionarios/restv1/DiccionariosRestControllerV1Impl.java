package com.curso.diccionarios.restv1;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import com.curso.diccionarios.restv1.dto.RespuestaPalabraDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.curso.diccionarios.gestion.respuesta.diccionario.RespuestaDiccionario;
import com.curso.diccionarios.gestion.respuesta.diccionario.DiccionarioEncontrado;
import com.curso.diccionarios.gestion.respuesta.diccionario.DiccionarioNoEncontrado;
import com.curso.diccionarios.gestion.respuesta.diccionario.ErrorAlObtenerDiccionario;
import com.curso.diccionarios.gestion.respuesta.palabra.RespuestaPalabra;
import com.curso.diccionarios.gestion.respuesta.palabra.PalabraEncontrada;
import com.curso.diccionarios.gestion.respuesta.palabra.PalabraNoEncontrada;
import com.curso.diccionarios.gestion.respuesta.palabra.ErrorAlObtenerPalabra;

import com.curso.diccionarios.restv1.dto.Idioma;

import com.curso.diccionarios.gestion.SuministradorDeDiccionarios;

public class DiccionariosRestControllerV1Impl implements DiccionariosRestControllerV1 {
    
    private final SuministradorDeDiccionarios suministradorDeDiccionarios;

    public DiccionariosRestControllerV1Impl(SuministradorDeDiccionarios suministradorDeDiccionarios) { // Inyección de dependencias!
        this.suministradorDeDiccionarios = suministradorDeDiccionarios;
    }

    public ResponseEntity<Void> existeIdioma(@PathVariable String idioma){
       RespuestaDiccionario respuesta = suministradorDeDiccionarios.getDiccionario(idioma);
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
        }
    }

    public ResponseEntity<Void> existePalabra(@PathVariable String idioma, @PathVariable String palabra){
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

    public ResponseEntity<RespuestaPalabraDTO> obtenerSignificados(@PathVariable String idioma, @PathVariable String palabra){
        return null;
    }
}

// el objetivo de este controlador (PATRON ADAPTADOR) es convertir llamadas HTTP a llamadas JAVA 
// al SuministradorDeDiccionarios que se esté usando en el servidor.
