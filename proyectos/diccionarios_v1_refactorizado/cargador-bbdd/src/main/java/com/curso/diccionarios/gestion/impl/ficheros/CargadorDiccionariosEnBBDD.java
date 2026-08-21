package com.curso.diccionarios.gestion.impl.ficheros;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import com.curso.diccionarios.bd.entity.DiccionarioEnBD;
import com.curso.diccionarios.bd.entity.PalabraEnBD;
import com.curso.diccionarios.bd.entity.SignificadoEnBD;
import com.curso.diccionarios.bd.repository.DiccionarioRepository;
import com.curso.diccionarios.bd.repository.PalabraRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.logging.Logger;
import java.util.HashMap;
import java.util.List;

@Component
// Esta clase también es un COMPONENTE DE LA APLCIACION, Que quiero que Spring AUTODESCUBRA
// Spring, cuando lea este componente, al ver que implementa CommandLineRunner:
// Creará una instancia de él al arrancar la aplciación ( new CargadorDiccionariosEnBBDD(...) )
// Al ver que necesita un DiccionarioRepository y un PalabraRepository, Spring se los inyectará automáticamente (porque son componentes de Spring también).
// Y además, ejecutará en automático la función run() de esta clase, que es la que implementa la interfaz CommandLineRunner.
//@Slf4j
public class CargadorDiccionariosEnBBDD implements CommandLineRunner {

    private final Logger log = Logger.getLogger(CargadorDiccionariosEnBBDD.class.getName());

    // VAmos a definir un logger, para poder escribir en el log de Spring. (que se verá en la consola)
    // Podemos hacerlo con una anotación de Lombok.
    // Como tenemos lombok, ponemos la anotación @Slf4j y ya tenemos un logger disponible en la variable "log"
    // Para usarlo, simplemente hacemos log.info("mensaje") o log.error("mensaje") o log.debug("mensaje")
    // Los diccionarios estarán en ficheros de texto.
    // dentro de unba carpeta parametrizable
    @Value("${diccionarios.carpeta:diccionarios}")
    // Esta es una anotación de Spring.
    // Spring, dentro del flujo que crea para la aplicación hace muchas cosas.
    // Una de esas cosas es buscar un fichero llamado application.properties | application.yml
    // en ese fichero puedo definir propiedades de configuracion de mi aplicación, como por ejemplo:
    // "diccionarios.carpeta=mi_carpeta_de_diccionarios"
    // Spring lee en automático ese fichero. YO NO TENGO QUE HACER NADA
    // Y con esta anotación, suministrá (RELLENA) la variable con el valor que venga en el fichero de propiedades.
    // Si no viene valor, le pone como valor por defecto "diccionarios", lo que está despues de los dos puntos.
    private String carpetaDeLosDiccionarios;
    private final DiccionarioRepository diccionarioRepository;
    private final PalabraRepository palabraRepository;

    public CargadorDiccionariosEnBBDD(DiccionarioRepository diccionarioRepository, PalabraRepository palabraRepository) { // Inyección de dependencias de Spring. Spring me inyecta automáticamente el DiccionarioRepository y el PalabraRepository, porque son componentes de Spring también.
        this.diccionarioRepository = diccionarioRepository;
        this.palabraRepository = palabraRepository;
    }

    @Override
    public void run(String... args) throws Exception { // Esta es la que ejecuta Spring automáticamente al arrancar la aplicación.
        // Dijimos que cargamos ficheros SOLO si no hay datos ya en BBDD
        log.info("Comprobando si hay diccionarios en BBDD...");
        if(diccionarioRepository.count() == 0){
            log.info("No hay diccionarios en BBDD. Cargando diccionarios desde ficheros...");
            cargarFicheros();
        }else {
            log.info("Ya hay diccionarios en BBDD. No se cargan diccionarios desde ficheros.");
        }

    }

    public void cargarFicheros(){
        // Debe de ir leyendo CADA FICHERO de diccionario que exista en la carpeta y solicitando su carga en BBDD
        // Para cada fichero: lo leo -> Mapa
        // Para cada Mapa -> Lo cargo en BBDD
        try{
            PathMatchingResourcePatternResolver buscador = new PathMatchingResourcePatternResolver();
            Resource[] recursos = buscador.getResources("classpath*:" + carpetaDeLosDiccionarios + "/*.txt");
            for(Resource ficheroDiccionario: recursos){
                String nombreDelFichero = ficheroDiccionario.getFilename();
                String idioma = nombreDelFichero.substring(0, nombreDelFichero.indexOf(".txt"));
                log.info("Cargando diccionario de idioma: " + idioma + " desde fichero: " + nombreDelFichero);
                Map<String, List<String>> palabrasYSignificados = leerFicheroDeDiccionario(carpetaDeLosDiccionarios + "/" + nombreDelFichero);
                cargarDiccionarioEnBBDD(idioma, palabrasYSignificados);
                log.info("Diccionario de idioma: " + idioma + " cargado correctamente en BBDD.");
                log.info("Palabras cargadas: " + palabrasYSignificados.size());
            }
        }catch(Exception e){
            throw new RuntimeException("Error al cargar los diccionarios en BBDD: " + e.getMessage(), e);
        }
    }
  
    private ClassLoader getClassLoader(){
        return this.getClass().getClassLoader();
    }


    // Tal y como está, cada .save() hace su propio commit. 
    // eso haría que la carga fuera mucho más lenta.
    // Lo normal sería meter todos esos INSERTS a BBDD dentro de una transacción:
    // En SQL Sería algo así como:
    // BEGIN TRANSACTION
    // INSERT INTO diccionario ...
    // INSERT INTO palabra ...
    // INSERT INTO significado ...
    // INSERT INTO significado ...
    // INSERT INTO palabra ...
    // INSERT INTO significado ...
    // INSERT INTO significado ...
    // ...    
    // COMMIT
    // En Spring eso lo resolvemos muy fácil:
    @Transactional
    private void cargarDiccionarioEnBBDD(String idioma, Map<String, List<String>> palabrasYSignificados) throws Exception{
        // Crear el idioma en BBDD
        DiccionarioEnBD diccionario = new DiccionarioEnBD();
        diccionario.setIdioma(idioma);
        diccionarioRepository.save(diccionario);
        // Para cada palabr y sus significados, crear la palabra en BBDD asociada al diccionario
        for(Map.Entry<String, List<String>> entry : palabrasYSignificados.entrySet()){
            String palabra = entry.getKey();
            List<String> significados = entry.getValue();
            // Crear la palabra en BBDD
            PalabraEnBD palabraEnBD = new PalabraEnBD();
            palabraEnBD.setPalabra(palabra);
            palabraEnBD.setDiccionario(diccionario);
            palabraEnBD.setSignificados(
                significados.stream().map(significado -> {
                    SignificadoEnBD significadoEnBD = new SignificadoEnBD();
                    significadoEnBD.setTexto(significado);
                    significadoEnBD.setPalabra(palabraEnBD);
                    return significadoEnBD;
                }).toList()
            );
            palabraRepository.save(palabraEnBD);
        }
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
