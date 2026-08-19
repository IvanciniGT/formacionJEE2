package com.curso.diccionarios.gestion;
import java.util.Optional;

import com.curso.diccionarios.gestion.respuesta.diccionario.RespuestaDiccionario;

public interface SuministradorDeDiccionarios { 

    @Deprecated(
            since = "1.1.0",
            forRemoval = true
    )
    boolean tienesDiccionarioDe(String idioma);

    //boolean tienesDiccionarioDe(String idioma) throws SuministradorDeDiccionariosException;
    //Optional<Boolean> tienesDiccionarioDe(String idioma);
        // Optional tiene un problema... Si no me entrega nada (Optiona.empty()) no puedo saber el problema.
        // En ese sentido, la exception me da más información.
        // Pero ya dijimos que las excepciones SON MUY AGRESIVAS.
        // Los Optional están muy bien... cuando no hay que dar información adicional.
        // Pero si tengo que dar información adicional, los optional NO SIRVEN.

    @Deprecated(
            since = "1.1.0",
            forRemoval = true
    )
    Optional<Diccionario> dameDiccionario(String idioma);

    // Desde java 9 podemos meter código por defecto (default) en las interfaces.
    // Esto NO ESTA PENSADO PARA METER LOGICA REUTILIZABLE!
    // SI QUIERO METER LOGICA REUTILIZABLE POR VARIAS CLASES QUE IMPLEMENTEN ESTA INTERFAZ QUE USARIA EN SU LUGAR?
    // UNA CLASE ABSTRACTA!
    // Los default en interfaces están para otro escenario de uso: ASEGURAR LA COMPATIBILIDAD HACIA ATRÁS (BACKWARD COMPATIBILITY)
    default RespuestaDiccionario getDiccionario(String idioma) {
        throw new UnsupportedOperationException("getDiccionario is not implemented");
    }
    // Cuando se va a lanzar esa excepción a día de hoy?
    // NUNCA: NADIE USA ESTA FUNCION. LA ACABO DE CREAR!
    // Entonces para qué la he puesto? PARA TENER UN CODIGO POR DEFECTO Y QUE 
    // LAS IMPLEMENTACIONES ACTUALES SIGAN FUNCIONANDO. (BACKWARD COMPATIBILITY)

}

