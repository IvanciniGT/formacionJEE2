package com.curso.diccionarios.restv1;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.curso.diccionarios.restv1.dto.RespuestaPalabraDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Para definir este API, vamos a hacer uso de algunas anotaciones que nos ofrece Spring Boot, como por ejemplo @RestController, @RequestMapping, @GetMapping, @PostMapping, etc. Estas anotaciones nos permiten definir los endpoints de nuestro API de manera sencilla y clara.
@RestController
// Qué significa la anotación @RestController?
// Qué le estamos diciendo a Spring con esta anotación?
// En esta clase te estoy definiendo un API REST.
@RequestMapping("/v1/diccionario")
// Que quiero que use esta ruta como prefijo para todos los endpoints que defina en esta clase/interfaz.
@Tag(
    name = "Diccionarios REST API v1", 
    description = "API REST para consultar diccionarios de idiomas y obtener significados de palabras"
)
public interface DiccionariosRestControllerV1 {
    
    // Quiero tener en el servidor una ruta que sea invocable vía HTTP GET/HEAD
    // Qué será: GET /v1/diccionario/{idioma}
    // Y si alguien llama a esa ruta en nuestro servidor, 
    // debería invocarse el método existeIdioma() de esta clase/interfaz.
    // en este caso, el valor que pongan en la ruta en lugar de {idioma} es lo que entendemos por idioma
    @GetMapping("/{idioma}")
    @Operation(
        summary = "Verifica si existe un diccionario para el idioma especificado",
        description = "Este endpoint verifica si existe un diccionario para el idioma especificado. Devuelve un código de estado HTTP 200 si el diccionario existe, o un código de estado HTTP 404 si no existe."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Diccionario encontrado"),
        @ApiResponse(responseCode = "404", description = "Diccionario no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> existeIdioma(@PathVariable("idioma") String idioma);

    // Quiero tener en el servidor una ruta que sea invocable vía HTTP GET/HEAD
    // Qué será: GET /v1/diccionario/{idioma}/{palabra}
    // Y si alguien llama a esa ruta en nuestro servidor, 
    // debería invocarse el método existePalabra() de esta clase/interfaz.
    // En este caso, el valor que pongan en la ruta en lugar de {idioma} es lo que entendemos por idioma
    // Y el valor que pongan en la ruta en lugar de {palabra} es lo que entendemos por palabra
    @GetMapping("/{idioma}/{palabra}")
    @Operation(
        summary = "Verifica si existe una palabra en el diccionario del idioma especificado",
        description = "Este endpoint verifica si existe una palabra en el diccionario del idioma especificado. Devuelve un código de estado HTTP 200 si la palabra existe, o un código de estado HTTP 404 si no existe."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Palabra encontrada"),
        @ApiResponse(responseCode = "404", description = "Palabra no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> existePalabra(@PathVariable("idioma") String idioma, @PathVariable("palabra") String palabra);

    // Quiero tener en el servidor una ruta que sea invocable vía HTTP GET/HEAD
    // Qué será: GET /v1/diccionario/{idioma}/{palabra}/significados
    // Y si alguien llama a esa ruta en nuestro servidor, 
    // debería invocarse el método obtenerSignificados() de esta clase/interfaz.
    // En este caso, el valor que pongan en la ruta en lugar de {idioma} es lo que entendemos por idioma
    // Y el valor que pongan en la ruta en lugar de {palabra} es lo que entendemos por palabra
    // También en este caso, la función java devolverá un objeto de tipo RespuestaPalabra, 
    // Y eso habria que meterlo en el cuerpo de la respuesta HTTP que se le devuelva al cliente que ha invocado este endpoint.
    // En formato JSON
    @GetMapping("/{idioma}/{palabra}/significados")
    @Operation(
        summary = "Obtiene los significados de una palabra en el diccionario del idioma especificado",
        description = "Este endpoint obtiene los significados de una palabra en el diccionario del idioma especificado. Devuelve un objeto RespuestaPalabra que contiene la información de la palabra y sus significados."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Significados obtenidos"),
        @ApiResponse(responseCode = "404", description = "Palabra no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<RespuestaPalabraDTO> obtenerSignificados(@PathVariable("idioma") String idioma, @PathVariable("palabra") String palabra);

    // Qué paradigma de programacion acabo de usar? DECLARATIVO!
    // Quiero... Esto es lo que debe ser...

}

// nuestro API va avanzado. pero no está acabado. Qué le falta? Los códigos de respuesta HTTP.
// Spring no lleva nada para informar de esos códigos de respuesta.
// PERO... hay una librería que usamos un huevo que SI NOS PRMITE DEFINIR ESO: SPRINGDOC
// Springdoc nos permite generar en automático documentación en formato OPENAPI (antiguamente Swagger)
// Ahora si! Tenemos un API REST GENIAL!
// COMPLETO Y DOCUMENTADO!
// API = INTERFAZ!
// La interfaz de mi controlador... no es el controlador... Tendré que implementarlo. Pero eso es otra historia. Ahora ya tenemos un API REST completo y documentado.

// Cuando montemos la aplicación WEB que albergará la implementación de este API,
// Se nos generará en automatico un fichero con formato OPENAPI (antiguamente Swagger) que describe nuestro API REST.

// LO QUE PERMITIRÁ TENER UNA UNICA FUENTE DE LA VERDAD acerca de nuestro API: ESTE FICHERO!