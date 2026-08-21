package com.curso.diccionarios.gestion.impl.ficheros;
import java.util.Optional;
import java.util.WeakHashMap;

import com.curso.diccionarios.gestion.Diccionario;
import com.curso.diccionarios.gestion.SuministradorDeDiccionarios;
import com.curso.diccionarios.gestion.respuesta.diccionario.DiccionarioEncontrado;
import com.curso.diccionarios.gestion.respuesta.diccionario.DiccionarioNoEncontrado;
import com.curso.diccionarios.gestion.respuesta.diccionario.ErrorAlObtenerDiccionario;
import com.curso.diccionarios.gestion.respuesta.diccionario.RespuestaDiccionario;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.rmi.server.ExportException;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

// Nos puede interesar montar una cache de diccionarios.
// Es decir, si me piden 2 veces el mismo diccionario, no lo leo 2 veces del HDD.
// La primera vez, lo leo del HDD y lo guardo en memoria. La segunda vez, lo leo de memoria.
// La cache es CACHE!
// Y una cache debe siempre, por definición tener un mecanismo de VACIADO
// Una forma MUY SENCILLA de montar esto sería usando un WeakHashMap.
public class CargadorDiccionariosEnBBDD implements SuministradorDeDiccionarios { // Librería, Amazón (tienda online...)

    // Los diccionarios estarán en ficheros de texto.
    // dentro de unba carpeta parametrizable
    private final String carpetaDeLosDiccionarios;
    private final Map<String,Diccionario> cache;

    public CargadorDiccionariosEnBBDD(String carpetaDeLosDiccionarios) {
        this.carpetaDeLosDiccionarios = carpetaDeLosDiccionarios;
        cache = new WeakHashMap<>();
    }

    public boolean tienesDiccionarioDe(String idioma) {
        if(cache.containsKey(idioma)){
            return true;
        } else {
            return getClassLoader().getResource(rutaDelFicheroDeDiccionario(idioma)) != null;
        }
        //return cache.containsKey(idioma) || getClassLoader().getResource(rutaDelFicheroDeDiccionario(idioma)) != null;  
    }

    public Optional<Diccionario> dameDiccionario(String idioma) {
        try{
            if(tienesDiccionarioDe(idioma)){
            //   Si no está en cache, lo subo a cache
                if(!cache.containsKey(idioma)){
                    // Carga en la cache del diccionario del idioma indicado
                    cargarDiccionarioEnCache(idioma);
                }
                return Optional.of(cache.get(idioma));
            } else {
                return Optional.empty();
            }
        }catch(Exception e){
            // Meter entrada en el log de errores
            System.out.println("Error al obtener el diccionario de idioma " + idioma + ": " + e.getMessage());
            return Optional.empty();
        }
    }

    public RespuestaDiccionario getDiccionario(String idioma) {
        try{
            Optional<Diccionario> diccionarioOptional = dameDiccionario(idioma);
            if(diccionarioOptional.isPresent()){
                return new DiccionarioEncontrado(diccionarioOptional.get());
            } else {
                return new DiccionarioNoEncontrado(idioma);
            }
        }catch(Exception e){
            return new ErrorAlObtenerDiccionario(e.getMessage());
        }
    }
    

    private String rutaDelFicheroDeDiccionario(String idioma){
        //return carpetaDeLosDiccionarios + File.separator + idioma + ".txt";
        // Estaría bien... pero no.
        // en nuestro caso, vamos a incluir los ficheros de diccionario en el JAR, y no en el HDD.
        // Y dentro de un jar, el separador de carpetas no es File.separator, sino "/"
        return carpetaDeLosDiccionarios + "/" + idioma + ".txt";
    }

    private ClassLoader getClassLoader(){
        return this.getClass().getClassLoader();
    }

    private void cargarDiccionarioEnCache(String idioma) throws Exception{
        String rutaDelFichero = rutaDelFicheroDeDiccionario(idioma);
        // Puedo hacer aquí la lectura del fichero... y pasarle al diccionario el contenido (palabras y significados)
        Map<String, List<String>> palabrasYSignificados = leerFicheroDeDiccionario(rutaDelFichero);
        DiccionarioEnFichero diccionario = new DiccionarioEnFichero(palabrasYSignificados);
        cache.put(idioma, diccionario);
    }

    private Map<String, List<String>> leerFicheroDeDiccionario(String rutaDelFichero) throws Exception{
        Map<String, List<String>> palabrasYSignificados = new HashMap<>();
        // Leer el fichero
        // Lo haremos mediante el cargador de clases... para que lo encuentre en classpath.
        InputStream inputStream = getClassLoader().getResourceAsStream(rutaDelFichero);
        // Además usaremos un BufferedReader para leer el fichero línea a línea.
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String linea;
        try{
            while((linea = bufferedReader.readLine()) != null){
                // Procesar la línea
                String[] partes = linea.split("=");
                String palabra = partes[0];
                String[] significadosArray = partes[1].split("\\|");
                palabrasYSignificados.put(palabra, List.of(significadosArray));
            }
            // Cerrar el BufferedReader
        } finally {
            try {
                bufferedReader.close();
            } catch (Exception e) {
                System.out.println("Error al cerrar el BufferedReader: " + e.getMessage());
            }
        }
        // Una vez que tenga una linea, parto por "=" y obtengo la palabra y los significados.
        // Una vez que tenga los significados, parto por "|" y obtengo la lista de significados.
        
        return palabrasYSignificados;
    }

}

// Nomenclatura de los ficheros: <idioma>.txt

// Estructura de los ficheros:

// palabra=significado1|significado2|significado3
// palabra2=significado1