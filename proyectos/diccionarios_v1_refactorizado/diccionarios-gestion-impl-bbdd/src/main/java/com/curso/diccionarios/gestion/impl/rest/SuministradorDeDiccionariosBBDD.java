package com.curso.diccionarios.gestion.impl.rest;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.curso.diccionarios.gestion.respuesta.diccionario.DiccionarioEncontrado;
import com.curso.diccionarios.gestion.respuesta.diccionario.DiccionarioNoEncontrado;
import com.curso.diccionarios.gestion.respuesta.diccionario.ErrorAlObtenerDiccionario;
import com.curso.diccionarios.gestion.respuesta.diccionario.RespuestaDiccionario;
import com.curso.diccionarios.gestion.respuesta.palabra.ErrorAlObtenerPalabra;
import com.curso.diccionarios.gestion.respuesta.palabra.PalabraEncontrada;
import com.curso.diccionarios.gestion.respuesta.palabra.PalabraNoEncontrada;
import com.curso.diccionarios.gestion.respuesta.palabra.RespuestaPalabra;

import com.curso.diccionarios.gestion.SuministradorDeDiccionarios;
import com.curso.diccionarios.gestion.Diccionario;

import com.curso.diccionarios.bd.repository.DiccionarioRepository;
import com.curso.diccionarios.bd.repository.PalabraRepository;

import com.curso.diccionarios.bd.entity.PalabraEnBD;
import com.curso.diccionarios.bd.entity.SignificadoEnBD;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@RequiredArgsConstructor
//@Component // Esto me permite borrar la factoria (@Configuration+@Bean) que tenia en la aplicación-servidor
// Lo normal sería usar otra anotación:
@Service // Un @Services en un @Component que contiene lógica de negocio.
         // La anotación no cambia el comportamniento .. Solo aporta valor SEMANTICO a quien lea este fichero.
public class SuministradorDeDiccionariosBBDD implements SuministradorDeDiccionarios {

    private final DiccionarioRepository diccionarioRepository;
    private final PalabraRepository palabraRepository;

    public boolean tienesDiccionarioDe(String idioma){
        return switch(getDiccionario(idioma)) {
            case DiccionarioEncontrado encontrado -> true;
            default                               -> false;
        };
    }

    public Optional<Diccionario> dameDiccionario(String idioma){
        return switch(getDiccionario(idioma)) {
            case DiccionarioEncontrado encontrado -> Optional.of(encontrado.diccionario());
            default                               -> Optional.empty();
        };
    }

    public RespuestaDiccionario getDiccionario(String idioma) {
        // Debo preguntar al repositorio si existe un diccionario con ese idioma, ignorando mayúsculas y minúsculas.
        try{
            if (diccionarioRepository.existsByIdiomaIgnoringCase(idioma)) {
                return new DiccionarioEncontrado( new DiccionarioBBDD(idioma) );
            } else {
                return new DiccionarioNoEncontrado(idioma);
            }
        } catch (Exception e) {
            return new ErrorAlObtenerDiccionario(e.getMessage());
        }
    }

    public boolean existe(String idioma,String palabra){
        return switch(dameSignificados(idioma, palabra)) {
            case PalabraEncontrada palabraEncontrada -> true;
            default                                  -> false;
        };
    }

    public Optional<List<String>> getSignificados(String idioma,String palabra) {
        return switch(dameSignificados(idioma, palabra)) {
            case PalabraEncontrada palabraEncontrada -> Optional.of(palabraEncontrada.significados());
            default                                  -> Optional.empty();
        };
    }

    public RespuestaPalabra dameSignificados(String idioma,String palabra) {
        try{
            Optional<PalabraEnBD> palabraEnBD = palabraRepository.findByPalabraIgnoringCaseAndDiccionario_IdiomaIgnoringCase(palabra, idioma);
            if (palabraEnBD.isPresent()) {
                List<SignificadoEnBD> significados = palabraEnBD.get().getSignificados();
                List<String> significadosComoTexto = significados.stream() // JAVA 1.8
                // Es como una lista, pero cuyas funciones son FUNCIONALES. Y me permiten aplicar un modelo de programación llamado map-reduce. 
                // El modelo map-reduce es ideal para trabajar/manipular colecciones de datos, como listas, arrays, etc.    
                                                                 .map( significadoEnBD -> significadoEnBD.getTexto() ) // map es una función que transforma cada elemento de la lista en otro elemento. En este caso, transforma cada SignificadoEnBD en un String (su significado)
                                                                 .toList(); // toList() es una función que convierte el stream en una lista.
                // Podríamos hacer lo mismo con programación imperativa:
                // List<String> significadosComoTexto = new ArrayList<>();
                // for (SignificadoDB significadoDB : significados) {
                //     significadosComoTexto.add(significadoDB.getTexto());
                // }
                return new PalabraEncontrada(palabra, significadosComoTexto);
            } else {
                return new PalabraNoEncontrada(palabra, new DiccionarioBBDD(idioma)); // Modo lazy para las sugerencias
                // Podría calcularlas de antemano.. YO LO HARIA Y las sugerencias si no se encuentra palabra LAS DOY SIEMPRE
                // Y SOLO LOS SIGNIFICADOS SI LA PALABRA SE ENCUENTRA LOS DOY DESDE ESTA FUNCION y no desde EXISTE.
                // En EXISTE es donde implementaria el modo lazy para los significados.
            }
        } catch (Exception e) {
            return new ErrorAlObtenerPalabra(e.getMessage());
        }
    }

    public RespuestaPalabra existePalabra(String idioma,String palabra) {
        try{
            Optional<PalabraEnBD> palabraEnBD = palabraRepository.findByPalabraIgnoringCaseAndDiccionario_IdiomaIgnoringCase(palabra, idioma);
            if (palabraEnBD.isPresent()) {
                return new PalabraEncontrada(palabra); // Deberíamos inyectar el diccionario... para que se puedan buscar en modo lazy
            } else {
                List<String> sugerencias = getSugerencias(idioma, palabra);
                return new PalabraNoEncontrada(palabra, sugerencias);
            }
        } catch (Exception e) {
            return new ErrorAlObtenerPalabra(e.getMessage());
        }
    }

    private List<String> getSugerencias(String idioma, String palabra) {
        // ESTO SE COMPLICA. Necesito todas las plabras de la BBDD... al menos las que tengan un tamaño similar.
        // Esto debería implementarlo a nivel de repo... pero ahora no lo está
        List<PalabraEnBD> palabrasSimilares = palabraRepository.findAll(); // Estoy jodido.. porque no tengo ni capacidad para buscar / filtrar por idioma. OTRA COSA QUE METER AL REPO
        // Ahora me toca hacerlo aqui:
        List<String> palabrasDelMismoIdioma = palabrasSimilares.stream()
                .filter(p -> p.getDiccionario().getIdioma().equalsIgnoreCase(idioma)) // Filtramos por idioma
                .map(PalabraEnBD::getPalabra) // Obtenemos la palabra como String
                .toList();

        // Esa lista es la que tengo que procesar.
        return palabrasDelMismoIdioma.parallelStream()
                                      // MAP-REDUCE
                                     .filter( palabraDeLaBBDD -> Math.abs(palabraDeLaBBDD.length() - palabra.length()) <= 2               ) // Descarto las de longitud muy diferente
                                     .map(    palabraDeLaBBDD -> new PalabraPuntuada(palabraDeLaBBDD, distance(palabra, palabraDeLaBBDD)) ) // Calculo la distancia de Levenshtein entre la palabra buscada y la palabra de la BBDD
                                     .filter( palabraPuntuada -> palabraPuntuada.distancia <= 2                                           ) // Descarto las que tienen una distancia de Levenshtein mayor a 2
                                     .sorted( Comparator.comparingInt(palabraPuntuada -> palabraPuntuada.distancia)                       ) // Ordeno por distancia de Levenshtein
                                     .limit( 10                                                                                  ) // Limito a 5 sugerencias    
                                     .map(    palabraPuntuada -> palabraPuntuada.palabra                                                  ) // Descartando la puntuación, me quedo solo con la palabra
                                     .toList();

        // Este código va a poner la cpu al 100% mientras se ejecuta. ESTA ES LA REALIDAD.
        // Pero va a poner al 100% 1 CORE de mi CPU
        // Quién lleva el código a la CPU? UN HILO -> THREAD
        // Cuántos hilos tiene mi programa? 1
        // He abierto hilos? NO
        // Mi máquina tiene 18 cores x 2 hilos /core -> 36 hilos (36 cores virtuales)
        // 1/36 = 2,77% de mi CPU
        // al cambiar .stream() por .parallelStream() -> 
        // Java va a abrir tantos hilos como cores virtuales tenga mi máquina disponibles.
        // Y va a repartir el trabajo entre todos esos hilos.
        // Se encarga dde todo. Me olvido de abrir hilos, de repartir el trabajo, de recoger los resultados, sincronizar los hilos, etc. Java lo hace todo por mi.
    }

    private static class PalabraPuntuada {
        public String palabra;
        public int distancia;
        public PalabraPuntuada(String palabra, int distancia) {
            this.palabra = palabra;
            this.distancia = distancia;
        }
    }

    private static int distance(String a, String b) {
        a = a.toLowerCase();
        b = b.toLowerCase();
        // i == 0
        int [] costs = new int [b.length() + 1];
        for (int j = 0; j < costs.length; j++)
            costs[j] = j;
        for (int i = 1; i <= a.length(); i++) {
            // j == 0; nw = lev(i - 1, j)
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[b.length()];
    }


    @RequiredArgsConstructor
    private class DiccionarioBBDD implements Diccionario {
        private final String idioma;

        public boolean existe(String palabra){
            return SuministradorDeDiccionariosBBDD.this.existe(idioma, palabra);
        }
        public Optional<List<String>> getSignificados(String palabra) {
            return SuministradorDeDiccionariosBBDD.this.getSignificados(idioma, palabra);
        }
        public RespuestaPalabra dameSignificados(String palabra) {
            return SuministradorDeDiccionariosBBDD.this.dameSignificados(idioma, palabra);
        }
        public RespuestaPalabra existePalabra(String palabra) {
            return SuministradorDeDiccionariosBBDD.this.existePalabra(idioma, palabra);
        }
    }

}
