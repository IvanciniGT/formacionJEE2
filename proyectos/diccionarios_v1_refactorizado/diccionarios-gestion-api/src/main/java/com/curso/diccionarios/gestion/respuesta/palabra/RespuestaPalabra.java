package com.curso.diccionarios.gestion.respuesta.palabra;

public sealed interface RespuestaPalabra permits PalabraEncontrada, PalabraNoEncontrada, ErrorAlObtenerPalabra {}
