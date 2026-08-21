package com.curso.diccionarios;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.curso.diccionarios.gestion.SuministradorDeDiccionarios;

//@Configuration
// Esta clase contiene configuraciones para la aplicación .
// @Configuration es otra de esas anotaciones que extiende @Component,
//  y que hace que Spriong en automático cree una instancia de esta clase.
// además buscará dentro de ella todos los metodos que tengan la anotación @Bean
// Y los ejecuta. Y guarda lo que devuelvan
// En nuestro caso, invocará a la función: getSuministrador() y
//  guardará el objeto de tipo "SuministradorDeDiccionarios" que devuelva nuestra función.
// Y cuando alguien solicite en su constructor un SuministradorDeDiccionarios, 
// Spring le inyectará el objeto que guardó de nuestra función.
public class SuministradorDeDiccionariosConfiguration {

    private static final String RUTA_DE_DICCIONARIOS = "diccionarios";

    /*@Bean
    public SuministradorDeDiccionarios getSuministrador(){
         return new SuministradorDeDiccionariosEnFicheros(RUTA_DE_DICCIONARIOS);
    }*/

}