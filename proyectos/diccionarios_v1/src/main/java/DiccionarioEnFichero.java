import java.util.List;
import java.util.Optional;

import java.util.Map;

public class DiccionarioEnFichero implements Diccionario {

    private final Map<String, List<String>> palabrasYSignificados;

    public DiccionarioEnFichero(Map<String, List<String>> palabrasYSignificados) {
        this.palabrasYSignificados = palabrasYSignificados;
    }

    public boolean existe(String palabra){
        return palabrasYSignificados.containsKey(palabra);
    }

    public Optional<List<String>> getSignificados(String palabra){
        /*
        if( existe(palabra) ){
            return Optional.of(palabrasYSignificados.get(palabra));
        } else {
            return Optional.empty();
        }
        */
       /*
       List<String> significados = palabrasYSignificados.get(palabra);
       if( significados != null ){
           return Optional.of(significados);
       } else {
           return Optional.empty();
       }
       */
       return Optional.ofNullable(palabrasYSignificados.get(palabra));
    }
}