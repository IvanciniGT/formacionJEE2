package com.curso.diccionarios.gestion.impl.rest;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.URI;
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
import com.curso.diccionarios.bd.entity.DiccionarioEnBD;

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
                return new PalabraNoEncontrada(palabra);
            }
        } catch (Exception e) {
            return new ErrorAlObtenerPalabra(e.getMessage());
        }
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
    }

}
