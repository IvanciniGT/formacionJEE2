package com.curso.diccionarios;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;

// Esta es mi aplicación spring que arranco en el servidor
// Y hay una anotación para ello... en Spring. 
// PAra indicar que esta es la aplciación que quiero arrancar:
@SpringBootApplication
public class AplicacionServidor {
    
    public static void main(String[] args) {
        // SuministradorDeDiccionarios suministrador = SuministradorDeDiccionariosFactory.getInstance();
        // new DiccionariosRestControllerV1Impl(suministrador)
        // En lugar de escribir yo este flujo, como SI LO ESCRIBI EN EL PROYECTO APLICACION-TERMINAL
        // Ahora, ese flujo lo va a poner Spring.
        // Yo solo voy a delegar ese trabajo a Spring:
        SpringApplication.run(AplicacionServidor.class); // Esta linea es la INVERSION DE CONTROL!
        // Ni una linea más de código para arrancar la aplicación. Spring se encarga de todo.
        // Spring va a arrancar esta aplciación.
        // Como estamos usando el starter WEB, en automático, SPRING
        // Arranca un servidor de aplicaciones por mi: TOMCAT
        // Spring monta dentro de ese TOMCAT mi aplciación.
        // Spring va a buscar dentro de los paquetes de mi proyecto, 
        // Los componentes que tiene mi aplciación 
        // (Clases marcadas con @Component o anotaciones derivadas, 
        // como por ejemplo @RestController, @Configuration, @Service, @Repository, @ControllerAdvice...)
        // Spring, al encontrar nuestra clase con anotación @RestController, 
        // va a crear un objeto de esa clase (new)
        // Y configura en automático en el tomcat todas las RUTAS HTTP definidas dentro de esa clase 
        // (con @GetMapping, @PostMapping, etc) para que cuando llegue una petición HTTP a esa ruta, 
        // Spring invoque el método correspondiente de esa clase.
        // Spring transformará los resultados de esas funciones a JSON
        // Spring leerá de las URLs los parámetros de entrada y los pasará a los métodos de la clase.
        // Todo eso lo hace Spring.. y muchas más cosas.

        // Solo hay un pequeño problema.
        // Para hacer el new del DiccionariosRestControllerV1Impl, 
        // Spring necesita un SuministradorDeDiccionarios (una clase que implemente esa interfaz)
        // Eso es lo que antes resolvía nuestra Factory. 
        // Y ahora, como Spring es el que hace el new, y de alguna forma Sporing es quien tiene 
        // que llamar a la factory, para que le devuelva un SuministradorDeDiccionarios concreto
        // Y eso se lo vamos a indicar con Más anotaciones dentro de esa clase Factoria
    }

}
