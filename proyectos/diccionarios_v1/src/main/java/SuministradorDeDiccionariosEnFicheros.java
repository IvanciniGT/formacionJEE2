import java.util.Optional;
import java.util.WeakHashMap;
import java.io.File;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

// Nos puede interesar montar una cache de diccionarios.
// Es decir, si me piden 2 veces el mismo diccionario, no lo leo 2 veces del HDD.
// La primera vez, lo leo del HDD y lo guardo en memoria. La segunda vez, lo leo de memoria.
// La cache es CACHE!
// Y una cache debe siempre, por definición tener un mecanismo de VACIADO
// Una forma MUY SENCILLA de montar esto sería usando un WeakHashMap.
public class SuministradorDeDiccionariosEnFicheros implements SuministradorDeDiccionarios { // Librería, Amazón (tienda online...)

    // Los diccionarios estarán en ficheros de texto.
    // dentro de unba carpeta parametrizable
    private final String carpetaDeLosDiccionarios;
    private final Map<String,Diccionario> cache;

    public SuministradorDeDiccionariosEnFicheros(String carpetaDeLosDiccionarios) {
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

    private void cargarDiccionarioEnCache(String idioma){
        String rutaDelFichero = rutaDelFicheroDeDiccionario(idioma);
        // Puedo hacer aquí la lectura del fichero... y pasarle al diccionario el contenido (palabras y significados)
        Map<String, List<String>> palabrasYSignificados = leerFicheroDeDiccionario(rutaDelFichero);
        DiccionarioEnFichero diccionario = new DiccionarioEnFichero(palabrasYSignificados);
        cache.put(idioma, diccionario);
    }

    private Map<String, List<String>> leerFicheroDeDiccionario(String rutaDelFichero){
        Map<String, List<String>> palabrasYSignificados = new HashMap<>();
        
        return palabrasYSignificados;
    }

}

// Nomenclatura de los ficheros: <idioma>.txt

// Estructura de los ficheros:

// palabra=significado1|significado2|significado3
// palabra2=significado1