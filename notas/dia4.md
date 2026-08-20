
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