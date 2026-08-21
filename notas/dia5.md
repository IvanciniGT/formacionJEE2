# S de SOLID

SRP => Single Responsibility Principle (Principio de Responsabilidad Única)

Los principios SOLID los racopiló y les dió nombre fué el tio Bob (Robert Cecil Martin) en 2000. Son principios de diseño de software orientado a objetos. (CLEAN CODE)

Ahí dió una definición del SRP: 
- Una clase o un módulo de un sistema debe asumir una única responsabilidad.
  NO PODEMOS APLICAR ESTO. Esto es ambiguo. QUE SIGNIFICA UNA RESPONSABILIDAD?
  COMO DEFINES RESPONSABILIDAD?
- Años más tarde 3/4 dió una segunda definición: 
  Una clase se toca solo por un motivo de cambio
  Solo de haber un motivo para cambiar una clase.
  NO PODEMOS APLICAR ESTO. Esto es ambiguo. QUE SIGNIFICA UN ÚNICO MOTIVO DE CAMBIO?
  COMO DEFINES MOTIVO DE CAMBIO?
- Más años... hasta que dió la tercera y definitiva definición en CLEAN ARCHITECTURE:
  El SRP no resulelve un problema TECNICO. VA SOBRE PERSONAS!
  DEFINICIÓN: Una clase debe atender a un único ACTOR!
  Un actor es cualquier persoa (o tipo de persona, departamento en una empresa) que puede requerir un cambio en el sistema. 
  Y una clase no debería tener funciones que puedan ser modificadas a petición/como consecuencia de más de un actor.

Que una clase haga solo una cosa... Asuma una resonsabilidad...
- Una clase hace muchas cosas (tiene muchas funciones)
- Otra cosa es que las cosas que haga una clase esén relacionadas entre sí -> COHESION / ACOPLAMIENTO

    COHESION Y ACOPLAMIENTO (Mirar el paper STRUCTURED DESIGN) de Yourdon y Constantine (1979)


---
                                                                                                                                                    Script de carga
                                                                                                                                                    ETL
                                                                                                                                                    Un tio a mano!
Frontal de una App web                                                                                              Backend                             vvv
-----------------------------------------                            -------------------------------------------------------------------------------------------
FORMULARIO WEB    -> Servicio Frontal ->    http/rest           ->                ControladorRestV1      ->         Servicio ->        Repositorio ->  BBDD
CAPTURAR DATOS DE UNA PERSONA                                                     ControladorRestV2      ->
                                                                                  ControladorSOAP        ->
                                                                                  ControladorWS          ->

  Captura de datos    Gestión de comunicaciones                                   Exponer la funcionalidad          Logica de negocio   Gestionar la persistencia
                              con backend                                         vía un determinado protocolo                                          Garante del dato
   ^^^^^^^^^^^^
   Usuario
   El usuario siempre decide sobre el UI

Hay que validar el DNI del usuario.
RESTRICCIÓN: Me dicen que SOLO PUEDO PONER ESA VALIDACION EN UN SITIO : BBDD

No puedo permitir que un dato llegue podrido a la BBDD.
La BBDD no debe permitir que entre un DNI INVALIDO.

Si hubiera dicho que he de validar que la fecha de nacimiento de la persona sea una fecha -> BBDD

Quiero validar que la fecha de nacimiento sea tal que la persona tenga al menos 18 años -> SERVICIO BACKEND: LOGICA DE NOGOCIO

CREATE TABLE USUARIOS (
    ID INT PRIMARY KEY,
    NOMBRE VARCHAR(100),
    APELLIDOS VARCHAR(100),
    DNI CHAR(9) NOT NULL CHECK (DNI ~ '^[0-9]{8}[A-Z]$'),
    FECHA_NACIMIENTO DATE NOT NULL CHECK (FECHA_NACIMIENTO <= CURRENT_DATE)
);

Para eso tenemos lenguajes como PLSQL... poder meter lógica en BBDD. QUE SI DEBEMOS HACERLO
Hay gente que dice NO SE DEBE METER LOGICA EN BBDD = FALSO
NO SE DEBE METER LOLGICA DE NEGOCIO EN BBDD = CIERTO

Otra cosa es que por cortesía meta esa validación en más sitios:
- FRONTAL: FORMULARIO
Puede haber muchos frontales, y no me fío (yo oh! creador del BACKEND)... y meto mi validación en el servicio!

Pero el Eh! yo ! creador de la BBDD debe garantizar la calidad del dato. Y si el usuario mete un DNI inválido, la BBDD no lo va a permitir. Y si el usuario mete un DNI válido, pero que no es de una persona real, la BBDD no lo va a poder saber. Pero al menos me aseguro que el dato que entra en la BBDD es correcto.




App terminal -> Controlador Diccionarios Rest -> ServicioSuministradorDeDiccionariosBBDD -> RepositorioDiccionarios -> BBDD
                                                        |
                                                        v
                                                    Servicio -> RepositorioEstadisticas -> BBDD2 donde guardo en una tabla cada busqueda de palabra que se hace -> ESTADISTICAS
                                                    

Si tuvieramos una BBDD de verdad (y la tendremos en un entorno de producción), sus datos los configuro en el archivo application.properties. 


```properties

# Configuración de la BBDD
spring.datasource.url=jdbc:postgresql://localhost:5432/diccionarios
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```


En nuestro caso, que vamos a usar la BBDD en Memoria H2, solo necesito poner el JAR de H2 en el classpath y Spring Boot se encarga de todo. No necesito poner nada en el application.properties. Pero si quiero, puedo ponerlo:

```properties

# Configuración de la BBDD H2 en memoria
spring.datasource.url=jdbc:h2:mem:diccionarios;DB_CLOSE_DELAY=-1
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect
```


---

Ampliación de la funcionalidad:
- Queremos que si una palabra no existe, ofrezca las sugerencias!
- Si busco manana, me diga que no existe, y me ofrezca sugerencias: manzana, manaña, manada, manzano etc.

Quedarnos con las palabras que sean similares a la objetivo.

Hay un algoritmo que se llama Distancia de Levenshtein, que nos dice cuantas operaciones (inserciones, borrados, sustituciones) hay que hacer para pasar de una palabra a otra.
Necesitamos APLICAR ESTE ALGORITMO SOBRE TODAS LAS PALABRAS DEL DICCIONARIO, Y QUEDARNOS CON LAS MAS CERCANAS A LA PALABRA OBJETIVO. PARA ESO NECESITARE ORDENAR POR DISTANCIA, Y QUEDARNOS CON LAS 10 PRIMERAS.. como mucho. SOLO Incluiremos en esa lista de sugerencias las palabras que tengan una distancia de Levenshtein menor o igual a 2. (Si la distancia es mayor, no tiene sentido ofrecerla como sugerencia).


---

# MAP-REDUCE

Esto es un modelo de programación (basado en el paradigma funcional) que nos permite procesar colecciones de datos de manera eficiente y paralela. 

Básicamente:
  COLECCION DE DATOS -> FUNCION MAP -> COLECCION DE DATOS V2
                                        -> APLICAR OTRA FUNCION MAP -> COLECCION DE DATOS V3
                                                              -> APLICAR OTRA FUNCION MAP -> COLECCION DE DATOS V4  
                                                                                              -> APLICAR FUNCION REDUCE -> RESULTADO

En java, hay una clase que es la que implementa este modelo de programación Stream<T> (java.util.stream.Stream desde Java 8).

Un Stream es una secuencia de elementos que soporta operaciones map-reduce.
Como is fuera una lista o un Set... o un Array, pero sus funciones, en lugar de ser del tipo:
- Meter uno
- Quita uno
- Dame uno
Son del tipo:
- Tansforma todos los elementos de la colección
- Filtra elementos de la lista
- ...
Cualquier colección (List, Map, Set..) puede convertirse a un Stream, y cualquier Stream puede convertirse a una colección (List, Set, Map..)
Para convertir una coleccion en un Stream :       .stream()
Para convertir un Stream en una coleccion:       .toList()  .toSet()  .toMap()

Lo normal es partir en JAVA de una colección STANDARD: Lista/Map

  List<T> lista =...;
  Stream<T> lista2 = lista.stream()   // Convertimos la lista en un Stream
  // Procesamos el Stream con operaciones map
  Stream<R> lista3 = lista2.TRANSFORMACIONES_MAP();
  Al final convertimos el Stream en una colección STANDARD: Lista/Map
  List<R> lista4 = lista3.toList();   // Convertimos el Stream en una lista

Qué son las funciones de tipo MAP y que son las funciones de tipo REDUCE? HAY MUCHAS DE AMBAS.

Funciones de tipo MAP:
  - Son funciones que al aplicarse a una colección de objetos que soporta map-reduce (Stream), devuelven otra colección de objetos que soporta map-reduce (Stream).
     Stream<T> COMO_SE_LLAME( Stram<R> );
  - Hay muchas:
    - map(FUNCION_DE_TRANSFORMACION)        FUNCION_DE_TRANSFORMACION: Función de mapeo 
        Aplica la función sobre cada elemento del conjunto original
        Guarda el resultado de esa función
        Genera un nuevo Stream (Colección) con los resultados de la función aplicada a cada elemento del Stream original

                  map
          1       x2        2
          2                 4   
          3                 6
          4                 8
    - filter(FUNCION_PREDICADO) Función que devuelve true o un false
        Genera un Stream (colección) nuevo con los elementos del Stream original que cumplen la condición de la función predicado (true)
        Se aplica la función predicado sobre cada elemento del Stream original, y si devuelve true, ese elemento se incluye en el nuevo Stream (colección) que se genera.

       C.Inicial           filter              Coleccion final
          1                 >2?       false ->      3
          2                           false         4
          3                           true ->      
          4                           true ->      
    - sorted(FUNCION_DE_ORDENACION)    FUNCION DE ORDENACION recibe 2 datos del tipo original de la colección y devuelve un int: -1, 0, 1
          Ese entero significa:
          -1 : El primer elemento suministrado va antes del segundo
           0 : El primer elemento suministrado es igual que el segundo elemento suministrado y por ende no hay que cambiar el orden de los elementos
           1 : El primer elemento suministrado va después del segundo elemento suministrado

        Genera un Stream (colección) nuevo con los elementos del Stream original ordenados según la función de ordenación.
        Se aplica la función de ordenación sobre cada par de elementos del Stream original, y se genera un nuevo Stream (colección) con los elementos ordenados según la función de ordenación.

       C.Inicial           sorted                                                                                       Coleccion final
          Hola                                                                Hola, Adios -> Adios                        adios
          adios              A, B                                             Bufifarra, Mercadona -> Butifarra           Butifarra
          Bufifarra              A.toLowerCase().compareTo(B.toLowerCase())                                               Hola  
          Mercadona                                                                                                       Mercadona

            Y así se aplica sobre todos ellos, para generar un nuevo Stream (colección) con los elementos ordenados según la función de ordenación.
    - limit(N)  Genera un Stream (colección) nuevo con los primeros N elementos del Stream original.

      Colección original: 1, 2, 3, 4, 5, 6, 7, 8, 9, 10
      limit(5) -> 1, 2, 3, 4, 5 
    - Hay más de 30 funciones tipo map... hay que mirar el API


  
Funciones de tipo REDUCE:
  - Son funciones que al aplicarse a una colección de objetos que soporta map-reduce (Stream), devuelven cualquier otra cosa que no sea una colección de objetos que soporta map-reduce (Stream).
     R COMO_SE_LLAME( Stream<T> );
  - Hay muchas:
    - reduce(FUNCION_DE_REDUCCION) FUNCION_DE_REDUCCION recibe 2 datos del tipo original de la colección y devuelve  lo que quedaría al aplicar la función:

      Coleccion inicial     reduce
      1                     a+b         1+2 -> 3
      2                                             3+7-> 10        Esto funciona como los playoffs. Como la copa del rey...  Por eliminatoria
      3                                  3+4-> 7
      4 

      Hay cosas raras:

      Coleccion inicial   reduce
      1                   return List.of(a,b) ->        1,2 ->  [1,2] ->
      2                                                                   [1,2,3,4]
      3                                                 3,4 ->  [3,4] ->
      4

De hecho, este segundo ecaso es lo que hace otra función de reducción llamada:
    - .toList()  .toSet()  .toMap()  que son funciones de reducción que devuelven una colección de objetos que soporta map-reduce (Stream).
    - count()

  Colecion inicial    --> count -> 4

    1
    2
    3
    4

Un algoritmo map reduce es una secuencia de operaciones map que siempre acaba con una operación reduce. (N maps, 1 reduce)


El objetivo, IR TRANSFORMANDO LA COLECCION INICIAL EN EL DATO QUE QUIERO CONSEGUIR... PASO A PASO.


  Colección inicial: PALABRAS DE UN DICCIONARIO
  
    abanico
    amanecer
    banana
    casa
    manzana
    mañana
    membrillo
    ...
    zapato

  600k palabras

  Palabra OBJETIVO: manana

  Ej objetivo es producir:

    banana
    manzana
    mañana


List<String> todasLasPalabras;
List<String> palabrasSimilares = todasLasPalabras.stream()
                                                      // Aplico los maps que sean necesarios
                                                 .toList();
Nuestro objetivo es ir definiendo todas esas transformaciones para llegar al objetivo que tenemos: La lista de palabras similares a la palabra objetivo.

NOTA: Tenemos a nuestra disposición una función llamada distanciaLevenshtein(String palabra1, String palabra2)  -> int


                                                                              Les doy la vuelta!
                                                                            O(n·log(n))     <-> O(n)
  Coleccion inicial   *1  map                           Coleccion final      sorted   filter(distancia<=2>)    limit(2)   map             toList
                      distancia(PALABRA_OBJETIVO, ?)                      distancia                                     (solo palabra)  
  abanico               abanico, manana                abanico, 6          banana, 1          banana,1      banana, 1    banana         [banana, manzana]
  amanecer              amanecer, manana               amanecer,6          manzana, 1         manzana,1     manzana, 1   manzana
  banana                banana, manana                 banana, 1           mañana, 1          mañana,1      
  casa                  casa, manana                   casa, 4             casa 4
  manzana               manzana, manana                manzana, 1          zapato, 4
  mañana                mañana, manana                 mañana, 1           abanico, 6
  membrillo             membrillo, manana              membrillo, 7        amanecer, 6
  zapato                zapato, manana                 zapato, 4           membrillo, 7


  Quiero solo las 2 más similares

  Si 2 palabras tienen una longitud que difiere entre ellas más de 2, siempre van a dar una distancia de Levenshtein mayor que 2. Por lo tanto, no necesito ni aplicar la función distanciaLevenshtein ni incluirlas en la lista de palabras similares. Puedo filtrar por longitud antes de aplicar la función distanciaLevenshtein.

  *1 Filter
    Math.abs(a.length() - b.length() ) >= 2 Me vale la palabra.. para seguir (ya veremos cuando calculemos levenshtein si es <=2 o no)

    Si esa diferencia es > 2, siempre la distancia de Levenshtein va a ser > 2, y por lo tanto no me vale la palabra. La descarto.
    Esto me evita hacer un huevo de cálculos de distancia de Levenshtein que no me van a servir para nada.


    manzana
    manana   Su diferencia de longitud es 1. La distancia al menos va a ser 1.

    camión      En este caso, la diferencia de longitud es 0. Las dos tienen 6 letras.. podrían ser iguales.. TENGO QUE MIRARLO -> levenshtein: 6
    manana

    manana   La distancia al menos va a ser 3 (diferencia de longitud).  La descarto de partida.. no calculo levenshtein.
    mar

    manana
    archipielago  Imposible... descartada