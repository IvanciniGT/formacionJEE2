package com.curso.diccionarios.gestion;

import java.util.List;
import java.util.Optional;

import com.curso.diccionarios.gestion.respuesta.palabra.RespuestaPalabra;

public interface Diccionario {

    default RespuestaPalabra dameSignificados(String palabra) {
        throw new UnsupportedOperationException("Not implemented");
    }

    default RespuestaPalabra existePalabra(String palabra) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Deprecated(
            since = "1.1.0",
            forRemoval = true
    )
    boolean existe(String palabra);

    @Deprecated(
            since = "1.1.0",
            forRemoval = true
    )
    Optional<List<String>> getSignificados(String palabra) ;
}

/*

public interface Diccionario {
    List<String> getPalabrasSimilares(String palabra);
    RespuestaPalabra dameSignificados(String palabra);
}

Cuando planteamos un API... y es un trabajo que hay que hacer despacio (la implementación es fácil, lo complejo es diseñar un buen api)
lo primero es pensar en la funcionalidad que quiero dar.. y en para qué y cómo se va a usar.


Para qué y cómo se va a usar nuestro API?
Casos de uso:
- CASO 1: Buscar los significados de una palabra.
    Mi api ofrece una forma cómoda de sacar eso? SI: dameSignificados(String palabra) -> RespuestaPalabra (que contiene los significados)
    Con 1 llamada al API saco esa información = COMODO!
- CASO 2: Para ver si existe una palabra
    Mi api ofrece una forma cómoda de sacar eso?
        ANTIGUAMENTE TENIAMOS: existe(String palabra) -> boolean // PERO LA DEPRECAMOS!
        HOY tengo: dameSignificados(String palabra) -> RespuestaPalabra (recibo un PalabraNoEncontrada si no existe la palabra)
        Resultado: De nuevo podemos hacer esto con 1 llamada al API = COMODO!
        PERO.... INEFICIENTE!
        Porque si si existe la palabra, y no necesito los significados, no quiero que me los devuelva, 
        porque me va a costar tiempo y recursos: Queries adicionales a BBDD, JSONS más grandes por http...
        Esa función es cómoda, pero ineficiente.
- CASO 3: Para ver la grafía exacta de una palabra (por ejemplo, para ver si está bien escrita) -> Palabras similares: Quería decir....
    Mi api ofrece una forma cómoda de sacar eso? SI -> List<String> getPalabrasSimilares(String palabra)
    Con 1 llamada al API saco esa información = COMODO!
    A priori parece que está bien... Aunque aquí veo huecos!
        - Y si la BBDD no está disponible? Qué devuelvo? null? Excepción? Lista vacía? UPS!!! YA estamos! Es verdad... que puede no contestar.
          En la otra función creamos el caso: ErrorAlObtenerPalabra, para contemplar esto.
          Aquí no tenemos nada parecido = PROBLEMA !
        - Imaginad que alguien usa esto para un corrector ortográfico.
            - De cada palabra que escriba, tengo que:
                - Hacer una petición al API para ver si existe la palabra (dameSignificados)
                - Si no existe, hacer otra petición al API para ver las palabras similares (getPalabrasSimilares)
                2 peticiones = RUINA!
        - Mismo escenario, otra utilidad: App web que busca significados de una palabra:
            FORMULARIO: PALABRA A BUSCAR [             | BUSCAR ]
            Ponen "manana" -> Y le dan a ok
            Busco los significados.. no los encuentro -> 1 llamada al api: dameSignificados("manana") -> PalabraNoEncontrada
            Lo ideal es que esa aplciación muestre entonces las palabras similares, para que el usuario pueda ver si se ha
                                     equivocado al escribir la palabra. -> getPalabrasSimilares("manana") -> ["mañana", "manana", "manana"]
                2 llamadas al api = RUINA!
Yo creo que podríamos dejar el API mejor.
Esta decisión NOS VA A ACOMPAÑAR AÑOS!
Las implementaciones van y vienen! El API es lo que va a permanecer y lo que van a usar los clientes de nuestro API.
SOLUCIONES/PROPUESTAS:
- Podemos hacer que dameSignificados, si no encuentra la palabra, devuelva también las palabras similares. 
  PalabraNoEncontrada -> palabra + List<String> palabrasSimilares
  ESTA ES BUENA.. pero SOLO RESUELVE 1 CASO DE USO: Buscar los significados de una palabra que no existe y que me dé las palabras similares.
  Pero no resuelve el caso de uso de un corrector ortográfico, que no necesita los significados.. solo si la palabra existe o no.
  Y Ahora mismo si alguien quisiera montar un corrector ortográfico tendria que hacer 2 llamadas:
  - dameSignificados("manana") -> PalabraNoEncontrada (QUE SI EXISTE ADEMAS TREA LOS SIGNIFICADOS = INEFICIENTE)
  - getPalabrasSimilares("manana") -> ["mañana", "manana", "manana"]


    List<String> getPalabrasSimilares(String palabra);

    ExistenciaDePalabra existe(String palabra);
        ExistenciaDePalabra: 
            - ExisteLaPalabra (Sin significados) / Es la diferencia con respecto a PalabraEncontrada
            - NoExisteLaPalabra (Con palabras similares) = PalabraNoEncontrada
            - ErrorAlObtenerPalabra (Con mensaje de error)

            Pues a mi esto NO ME GUSTA NADA!
            Voy a acabar con 400 clasecitas de respuestas... -> COPMPLICAR EL API... El que lo use necesita leer / Mirar 400 clases para ver qué le devuelve cada una de ellas.

        Se os ocurre otra cosa?
        Qué os parece si mantenemos solo las clases que tenemos.

    RespuestaPalabra existe(String palabra);
    RespuestaPalabra dameSignificados(String palabra);
        RespuestaPalabra:
            - PalabraEncontrada (Con significados)
            - PalabraNoEncontrada (Con palabras similares)
            - ErrorAlObtenerPalabra (Con mensaje de error)
        PERO... hacemos esas funciones más inteligentes!
            Podemos hacer que dameSignificados, si no encuentra la palabra, devuelva un PalabraNoEncontrada,
            cuya función getPalabrasSimilares() se ejecute en modo lazy (en modo perezoso)
                Es decir, que a priori no incluya las palabras similares, pero que si alguien llama a getPalabrasSimilares() se haga la consulta a la BBDD y se devuelvan.
            Por contra, existe, si devuelve PalabraNoEncontrada, ya incluirá las palabras similares, porque es lo que se espera de esa función.
                Se ejecuta en modo EAGER (ansioso) y no en modo lazy (perezoso)
            Y hacemos lo mismo con PalabraEncontrada pero al revés.
            Si alguien llama a dameSignificados y encuentra la palabra, devuelvo los significados
            Pero si alguien llama a existe() y se devuelve PalabraEncontrada, devuelvo los significados en modo lazy (a priori no los cargo)
            Pero si alguien los quiere, ahí estarán.

    ESTO SERIA UNA BRUTALIDAD DE API! PERO SERÍA UN API MUY CÓMODO DE USAR Y MUY EFICIENTE!
    Opciones hay muchas! Y habrá que vivir con la que tomemos.

    Estas decisiones no las puede tomar una IA por mi. 
    Entre otras cosas... porque una cosa es la mejor decisión posible, y otra la que puedo tomar.
    TENEMOS UNA HIPOTECA FIRMADA CON EL CODIGO LEGACY!

    La pregunta que me hago ahora es COMO ENCAJA ESTO CON MI API ACTUAL!
    Y aquí son muchos factores los que debo tener en cuenta:
    - Cuántas implementaciones hay de esto?
    - Cuántos sistemas lo usan
    - Cuántos clientes consumen ese API?

    Dependiendo de la respuesta a esas preguntas así la solución que acepte.
    Si el API es un proyecto Opensource que lo usan decenas de miles de personas = TENGO MUY POCO MARGEN DE MANIOBRA
    Si solo lo uso yo, y solo tengo 1 implementación. Y 1 cliente... TENGO TODO EL MARGEN DE MANIOBRA DEL MUNDO!
    Y Luego están los casos intermedios!
    En definitiva, GENERO MAJOR o GENERO MINOR!
    Puedo ir a MAJOR -> DEJAR MI API NUEVO y que lo que esté con el viejo.. ya no evolucione.. a no ser que pase al nuevo MAJOR DEL API.
    Lo que implicará grandes cambios! Y PUEDO ACEPTARLO
    Puedo ir a minor -> Y asegurar compatibilidad del cambio que haga.
    UNA IA no va a poder decidir eso por mi. LE FALTA MUCHA INFORMACION.

    NUNCA UNA IA PODRA HACER ESTO!
*/