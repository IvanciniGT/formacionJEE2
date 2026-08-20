
    diccionario-español(fichero)
        ^
    aplicacion-terminal -> procesador-peticiones -> diccionarios-gestion-impl-ficheros
                                                 -> comunicador-con-usuario-terminal   


    diccionario-español(fichero)
        ^
    aplicacion-servidor -> controlador-rest -> diccionarios-gestion-impl-ficheros
                                ^
                                -------------------------------
                                                              ^  
    aplicacion-terminal-remoto -> procesador-peticiones -> diccionarios-gestion-impl-rest
                                                        -> comunicador-con-usuario-terminal   


---

JAVA ES UN LENGUAJE DE TIPADO ESTATICO!
JS ES UN LENGUAJE DE TIPADO DINAMICO!

```js
var numero = 5;
numero = "Hola"; // Esto es válido en JS
```

Qué tipo de dato tiene la variable numero en el código de arriba? 
EN JS LAS VARIABLES NO TIENEN TIPO!
NINGUNO.

Otra cosa es que la variable inicialmente apunta a un dato (5) de tipo numérico y luego apunta a un dato de tipo string ("Hola"). Pero la variable en sí no tiene tipo.

```java
var numero = 5;
//numero = "Hola"; // Esto NO es válido en Java ERROR DE COMPILACION EN JAVA
```
En java la variable numero es de tipo int!
Y no puedo luego apuntar a un String
Y ese tipo int se ha inferido en tiempo de compilación a partir del valor inicial que le he asignado (5).

La palara var es cómoda en algunos escenarios... pero no es una buena práctica usarla indiscriminadamente. Porque hace que el código sea menos legible y más difícil de mantener.
Hay veces que el tipo de dato me da un poco igual... y son de agarrate a la silla:

    Map<String,List<String>> ?? EN SERIO
    var

---

Ya con la aplicacion cliente trabajando contra el servidor, no tiene sentido que el servidor siga trabajando con los ficheros. Tiene más sentido que el servidor trabaje con una base de datos. 

Qué trabajo necesitamos hacer para esto?
- diccionarios-gestion-impl-ficheros -> BASURA
- diccionarios-gestion-impl-db -> IMPLEMENTAR
  - Implica crear un nuevo SuministradorDeDiccionariosBD y un DiccionarioBD
- aplicacion-servidor:
  - Quitar la dependencia de diccionarios-gestion-impl-ficheros 
  - Añadir la dependencia de diccionarios-gestion-impl-db
  - ~~Modificar la Configuracion de la aplicación "SuministradorDeDiccionariosConfiguration.java"
    (nuestro antiguo Factoria) para que devuelva un SuministradorDeDiccionariosBD en lugar de un SuministradorDeDiccionariosFicheros~~
    En lugar de eso, y dado que vamos a crear un SuministradorDeDiccionariosBD usando características de Spring (JPA). Vamos a poder usar otra forma diferente de configurar la inyección de dependencias... En lugar de usar una configuración y un bean (lo que antes era la factoria).
    Cuando creo un componente propiamente para Spring tenemos una forma más fácil de configurar lo que debe ser inyectado cuando alguien solicite una dependencia (BASICAMENTE al nuevo componente que hagamos: suministradorDeDiccionariosBD le vamos a poner la anotación @Component y Spring se encargará de crear la instancia de esa clase e inyectarla).
- Vamos a buscar palabras y diccionarios en BBDD...
  Tenemos palabras y diccionarios en BBDD? NO... lo que tenemos son ficheros de texto con palabras.
  Si ya tengo ficheros... y posiblemente tenga HERRAMIENTAS DESARROLLADAS DENTRO DE LA EMPRESA PARA CREAR Y MODIFICAR Y GESTIONAR ESOS FICHEROS. Y tenga gente acostumbra a operar con ellos, lo que tendría más sentido es usar esos ficheros para alimentar la BBDD.
  Querremos un COMPONENTE NUEVO:
  - cargador-diccionarios-bbdd
  Ese trabajo lo haremos cuando arranque la aplicación servidor, si es que no están ya cargados.
  NOTA: Por ahora lo vamos a hacer muy simple. Si no hay diccionarios ni palabras, pa'dentro!
  Pero si ya hay diccionarios y palabras, no cargamos.

  Esto habría que hacerlo mejor.
  Controlando QUE VERSION de los FICHEROS DE diccionario es la que se ha cargado en la BBDD. Y si los ficheros han cambiado (NUEVA VERSION), volver a cargar la BBDD.

- Hay algún componente adicional que debamos crear?
  SI.
  Quien se encarga de la gestión de las TABLAS DE BBDD necesarias para almacenar diccionarios y palabras? 
  Gestión: Insertar datos, eliminar datos, actualizar datos, consultar datos.

  Porque nuestro diccionarios-gestion-impl-db necesita hacer queries sobre las tablas.
  Pero nuestro   cargador-diccionarios-bbdd también necesita hacer queries sobre las tablas.
  Y es preferible que todo lo relativo a operaciones sobre las tablas de diccionarios y palabras esté centralizado en un único componente.
    Ese componente lo vamos a llamar: diccionario-en-db

Resumen: 
3 componentes nuevos:
- diccionario-en-db
- diccionarios-gestion-impl-db
- cargador-diccionarios-bbdd
Ajustar las dependencias de la aplicacion-servidor para que use estos nuevos componentes y no los antiguos de ficheros.

---

J2EE                    -> JEE
Java Enterprise Edition -> Jakarta Enterprise Edition

Es un conjunto de especificaciones para desarrollar aplicaciones empresariales en Java. Hay muchas:
- JPA
- JMS
- JAX-RS
- JAX-WS

JPA es un estándar de JEE para mapear objetos JAVA a bases de datos relacionales.

Una cosa es una especificación (que es algo así como un API) y otra es una implementación.
Alguien que haga eso REALIDAD.

Qué librería se usa en JAVA para implementar JPA? Hibernate

Spring incluye Hibernate de serie.
springboot-starter-data-jpa AQUI DENTRO VIENE HIBERNATE
