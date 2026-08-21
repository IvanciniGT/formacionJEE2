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
public class CargadorDiccionariosEnBBDD { // Librería, Amazón (tienda online...)

    // Los diccionarios estarán en ficheros de texto.
    // dentro de unba carpeta parametrizable
    private final String carpetaDeLosDiccionarios;

    public CargadorDiccionariosEnBBDD(String carpetaDeLosDiccionarios) {
        this.carpetaDeLosDiccionarios = carpetaDeLosDiccionarios;
    }

    public void cargarFicheros(){
        // Debe de ir leyendo CADA FICHERO de diccionario que exista en la carpeta y solicitando su carga en BBDD
    }

    private void cargarFichero(String rutaDelFichero) throws Exception{
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
