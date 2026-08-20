package com.curso.diccionarios.bd;

import org.junit.jupiter.api.Test;

import com.curso.diccionarios.bd.entity.DiccionarioEnBD;
import com.curso.diccionarios.bd.entity.PalabraEnBD;
import com.curso.diccionarios.bd.entity.SignificadoEnBD;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class DiccionariosTest {
    
    @Test
    void tengoDiccionarios() {
        // Al definir una prueba siempre ponemos 3 cosas:
        // Contexto: DADO
        // Dado que tengo el idioma Es
        String idioma = "es";
        // Y que creo un diccionario de ese idioma
        DiccionarioEnBD diccionario = new DiccionarioEnBD();
        diccionario.setIdioma(idioma);
        // Acción: CUANDO
        // Pregunto por el idioma del diccionario
        String idiomaDelDiccionario = diccionario.getIdioma();
        // Resultado esperado: ENTONCES
        // entonces espero que el idioma del diccionario sea el mismo que el idioma que le pasé al constructor
        assertEquals(idioma, idiomaDelDiccionario); // ESTO ES LA PRUEBA. ES LA COMPROBACION
    }

    @Test
    void tengoPalabrasEnLosDiccionarios() {
        //Dado que tengo un diccionario en español
        DiccionarioEnBD diccionario = new DiccionarioEnBD();
        diccionario.setIdioma("es");
        diccionario.setId(1);
        // Y que tiene varias palabras con sus significados:
        SignificadoEnBD significado1 = new SignificadoEnBD();
        significado1.setTexto("Lugar donde vive la gente");
        SignificadoEnBD significado2 = new SignificadoEnBD();
        significado2.setTexto("Animal doméstico que ladra");
        SignificadoEnBD significado3 = new SignificadoEnBD();
        significado3.setTexto("Animal doméstico que maúlla");
        PalabraEnBD palabra1 = new PalabraEnBD();
        palabra1.setPalabra("casa");
        palabra1.setDiccionario(diccionario);
        palabra1.setSignificados(List.of(significado1));
        significado1.setPalabra(palabra1);
        PalabraEnBD palabra2 = new PalabraEnBD();
        palabra2.setPalabra("perro");
        palabra2.setDiccionario(diccionario);
        palabra2.setSignificados(List.of(significado2));
        significado2.setPalabra(palabra2);
        PalabraEnBD palabra3 = new PalabraEnBD();
        palabra3.setPalabra("gato");
        palabra3.setDiccionario(diccionario);
        palabra3.setSignificados(List.of(significado3));
        significado3.setPalabra(palabra3);
        diccionario.setPalabras(List.of(palabra1, palabra2, palabra3));
        // Cuando pregunto por las palabras que tiene me tiene que decir que tiene 3 palabras
        assertEquals(3, diccionario.getPalabras().size());
        // Y tiene que estar dentro la palabra "casa"
        assertTrue(diccionario.getPalabras().contains(palabra1));
        // Y tiene que estar dentro la palabra "perro"
        assertTrue(diccionario.getPalabras().contains(palabra2));
        // Y tiene que estar dentro la palabra "gato"
        assertTrue(diccionario.getPalabras().contains(palabra3));
    }

    // Si intento guardar algo en BBDD con más de 10 caracteres debe fallar.
    // La prueba sale del requisito. Ese 10 es un requisito.
    // QUE LUEGO SE DEBE TRADUCIR A CODIGO
    //     -----
    // Lo primero es definir el requisito! AQUI!
    // Si lo he puesto primero en el fichero JAVA.. ya que pinta la prueba?
    // Bueno.. podría venir bien el cualquier caso.. por si a a alguien se le va el dedito en el futuro
    @Test
    void noPuedoGuardarUnDiccionarioConMasDe10Caracteres() {
    }
}   
