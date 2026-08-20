
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

Pruebas automatizadas!
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

---

En general los desarrolladores sabemos muy muy poco de testing. Y es un problema GRANDE!

# Vocabulario en el mundo del testing

- Causa raíz    La causa raíz es el motivo por el que el humano cometió el error.
                    > Me despisté mirando a otra persona.
                    Puede ser: Falta de atención, falta de conocimiento, presión excesiva, malos requisitos, mal diseño... 
- Error         Los humanos cometemos errores (errar es humano)
                Las máquinas cometen errores? NO, hacen lo que les pido.
                    > Error al medir una pata de una mesa (60cms en lugar de 70cms)
- Defecto (BUG) Al cometer un humano un error, puede ser que introduzcamos un DEFECTO en nuestro producto.
                    > La mesa ahora tiene una pata más corta
                El defecto es la cicatriz que perdura en el producto debido a mi error.
- Fallo         Un fallo es la manifestación de ese defecto cuando se usa el producto.
                Una desviación del comportamiento esperado.
                    > La mesa cojea y no tiene estabilidad cuando le pongo platos encim o me apoyo en ella.

# Para qué sirven las pruebas?

- Para asegurar el cumplimiento de unos requisitos.
- Para tratar de garantizar que mi aplicación está libre de defecto antes del paso de producción.
  Y lo hacen de 2 formas:
  - Tratando de provocar fallos al usar el producto.                            PRUEBAS DINAMICAS
    Una vez identificado un fallo, debemos arreglar el defecto que lo provoca.
    Y para ello lo primero es identificar ese defecto (DEBUGGING)
  - Buscamos defectos directamente en el código fuente. Revisión del código.    PRUEBAS ESTATICAS
- Proporcionar información para la rápida identificación de defectos desde los fallos que se manifiestan al usar el producto (logs, capturas de pantalla, trazas de ejecución, etc.)
- Aprender de mi producto.. quizás para sacar datos que no voy siquiera a usar en este proyecto.
- Análisis de causas raíces. Tomar acciones preventivas, que eviten nuevos errores-> defectos->fallos en el futuro.
    - Vamos a hacer un curso
    - Vamos a replantear el diseño de esta parte del sistema
    - Vamos a coger a alguien que sepa de tal tecnología y le vamos a pedir que nos ayude a revisar el código de esa parte del sistema
- Guiar el desarrollo y ayudarme a hacer un mejor diseño del sistema
- ....

# Tipos de pruebas

Hay muchas formas diferentes (paralelas entre si) de clasificar las pruebas.

## En base al procedimiento de ejecución de la prueba:
- Dinámicas     Las que necesitan ejecutar el producto para poder comprobar si hay fallos o no.
- Estáticas     Las que no necesitan ejecutar el producto para comprobar si contiene defectos o no.

## En base al conocimiento previo del objeto de prueba:
- Caja blanca   Conozco y uso el conocimiento que tengo respeto al sistema para desarrollar la prueba.
- Caja negra    No conozco o decido no usar el conocimiento que tengo respecto al sistema para desarrollar 
                la prueba. Pruebas que defino a nivel de API.

Imaginad que buscamos una palabra en un idioma.. La palabra sé que existe en ese idioma. Y sé que tengo el idioma registrado en el sistema. Me aseguro que salen los significados registrados. NEGRA!

Qué podría ser una prueba de caja BLANCA?
Imaginad que en la implementación que yo he decidido hacer, para que vaya más rápido he implementado una caché!
Cuando hago la prueba 1 vez, entra en funcionamiento la cache? NO
Y si solo hago la prueba de caja NEGRA, me pierdo el comprobar la cache.

Es porque sé que tengo implementada una cache (que podría no haberla implementado -el api no me lo exige-) es por lo que haré una prueba de caja BLANCA para comprobar que la cache funciona correctamente.
Y Buscaré la misma palabra 2 veces seguidas... Y me aseguraré que la segunda vez (que está respondiendo la caché) el resultado siga siendo correcto.

## En base al objeto de prueba:
- Funcionales           Las que tienen que ver con requisitos funcionales del sistema.
- No funcionales        Las que tienen que ver con requisitos no funcionales del sistema:
  - De carga
  - De estrés
  - De rendimiento
  - De seguridad
  - De experiencia de usuario
  - De alta disponibilidad
  - ...

## En base al contexto de ejecución de la prueba (scope):

- Unitarias         Se centra en una característica de un componente AISLADO de mi sistema.
                    Y ojo, si tengo un mal diseño del sistema, será imposible por definición hacer pruebas unitarias.

> Soy una empresa que fabrica bicicletas: DECATHLON -> BTWIN

- Fabrico yo las ruedas? Claramente NO
- Fabrico yo el sistema de frenos? NO
- Fabrico el sillín? TAMPOCO
- Y NADA

Entonces, qué pinto yo en todo esto?
Diseño e integro componentes.

Encargo a una empresa la fabricación de los sillines.
Compro a otra empresa las ruedas que cumplan con una especificación (API) que yo defina.
Pido a otra empresa un sistema de freno que cumpla con una especificación (API) que yo defina.
Y luego integro esos componenes.

> Me llegan los sillines. 500 cajas.. 5000 sillines. Qué hago?

Los pruebo... Cómo los pruebo?
- Lo puedo revisar (medidas) PRUEBA ESTATICA
- Lo pudo montar en un BASTIDOR (4 hierros mal soldaos... pero que aguanten suficiente peso) y :
  - Subo a una persona de 150kgs.. a ver si el sillín aguanta.
      - CARGA
  - Me pongo a frotar el sillín con una lija por 3 horas, simulando el desgaste que tendría en 3 años de uso.
      - ESTRES
  - Bascúlo el bastidor, con una persona encima, para ver si el sillín agunanta a la persona o si la persona se resbala del sillín.
      - SEGURIDAD
  - Siento a una persona 5 horas a ver si al levantarse no le duele mucho el culo:
      - EXPERIENCIA DE USUARIO

> Me llegan los sistemas de frenos.

Monto uno en un bastidor. 
Aprieto la palanca de freno.                   sistemaDeFrenos.apretarPalanca();
Qué miro?
Miraré que las pinzas de freno cierran.
Incluso que cierran con la fuerza suficiente. <- Cómo mido esto? Poniendo un sensor de presión en medio de las pinzas (es un aparato en el que confío)

Hago todas estas pruebas. Me garantizan que la bicicleta va a funcionar guay? QUE BICICLETA? SI NO HAY BICICLETA.
Qué gano? CONFIANZA + 1
Voy bien.

Me preguntan , como vas con el proyecto? VOY BIEN!

- Integración       Se centran en la COMUNICACION entre 2 componentes.

Me llega las ruedas y el sistema de frenos.
Los pruebo juntos.

Cojo y monto en el BASTIDOR el sistema de frenos.. pero ahora en medio de las pinzas pongo la rueda.
Y le pego un viaje a la rueda.... Y la pongo a girar.
Y Aprieto la palanca de frenos.                        sistemaDeFrenos.apretarPalanca();
Qué miro ahora?
    - Que las pinzas cieeren? NO... eso ya lo sé
    - Que cierren con la fuerza suficiente? NO... eso ya lo sé
    - Que la rueda pare.
      Y MIRA QUE NO HA PARADO!
      Resulta que las pinzas cierran, y cierran con fuerza.. pero no cierran lo suficiente para llegar a tocar la llanta de la rueda  (es muy estrecha para ese sistema de frenos).
      - Tengo un defecto en el sistema de frenos?   NO funciona guay!
      - Tengo un defecto en la rueda?               NO funciona guay!
      - Tengo un problema en la comunicación entre ambos.
        El sistema de frenos no llega a transmitir energía de rozamiento suficiente a la rueda para que esta se detenga. 

- End2End/Sistema

Cojo la bicilceta (aquí ya hay bicileta) y subo a una persona.. y la pongo a 30kms / hora.
Y sistemaDeFrenos.apretarPalanca();
Y miro si la bici se detiene en menos de 5 segundos.
Aquí hay otros factores afectando.. El propio peso de la bici, la presión de los neumáticos,...

Hago todas estas pruebas. Me garantizan que la bicicleta va a funcionar guay? DE HECHO SI!
Para eso hago estas pruebas, para ver si la BICICLETA (QUE YA TENGO BICICLETA ) está guay!
Y si pasan estas pruebas es que la bicicleta está guay!

Quién dice que la bicicleta está guay? Lsa pruebas de sistema.
Cuidado, que a nadie se le ocurra decir EL USUARIO . MAL!
Al usuario le debe llegar un sistema sin defectos! Que funcione genial!

- Aceptación

Estas pruebas sirven para que el usuario diga si el sistema cumple con sus expectativas o no.

> Me he comprado un lamborghini. Me lo entregan. 
> Cuando me lo entregan tiene que estar GUAY! Sin defectos.
> Ahora lo uso para ir al mercadona... Y digo, VAYA MIERDA DE COCHE INCOMODO.
> Este coche NO ES PARA MI!
> No es un problema del coche. Es que el coche no se adapta bien al usuario.
> Detectan fallos en los REQUISITOS!


Hoy en día las pruebas TODA EMPRESA EXIGE que se automatice, al menos las pruebas unitarias y de integración. 
Y ESE TRABAJO ES DE LOS DESARROLLADORES. No de los testers. 
Los testers ayudan con las pruebas de sistema principalmente, y con las pruebas de aceptación.

Pero las pruebas unitarias y de integración son responsabilidad de los desarrolladores.

Y hoy en día tenemos programas que automáticamente calculan la cobertura de coódigo.
Es decir, el % de lineas de código y de caminos del código que han sido ejecutadas cuando se han ejecutado las pruebas unitarias y de integración.

Y una cifra habitual aceptada en las empresas está entre el 80-90% de cobertura de código.
Y Si no se llega a ese %, el producto NO PASA A PRODUCCION. No se acepta la entrega!

Esto lo hacen herramientas tipo JENKINS (el control).

En JAVA: JACOCO es la herramienta que calcula la cobertura de código.
JACOCO = Java Code Coverage
---

Y nos hemos dado cuenta que las pruebas hay que hacerlas antes del desarrollo, como se hacen en CUALQUIER INDUSTRIA!

Si voy a fabricar un coche, antes de fabricarlo ya sé que tengo que pasar el test EURONCAP, y que le van a pegar hostias al coche por todos los sitios.. y que pondrán u ndummy dentro y que si mueve la cabeza más de 3 cms me quitan estrellitas.
Y sé que tengo que pasar el test de contaminación de gases, y que si no paso el test de contaminación de gases no puedo vender el coche.

Y si voy a fabricar un tornillo, según una norma ISO, que al tornillo le van a congelar, a meter fuego, a retorcer, a tensionar.. y que si parte antes de lo que dice la norma, no puedo vender el tornillo.

Las pruebas se diseñan ANTES QUE EL CODIGO. Por supuesto se ejecutan después (y a veces incluso antes, aunque fallarán Y ESTA BIEN!).

TDD: Test Driven Development: Desarrollo dirigido por pruebas.
Son 3 fases:
- Hago pruebas que se ejecuten y pongan en ROJO (QUE FALLEN)
- Hago el código que haga que esas pruebas pasen (PONGAN EN VERDE)
- Refactorizo el código para que sea más legible y mantenible, sin que las pruebas dejen de pasar (SIGAN PONIENDO EN VERDE)
---

NOTA: Por cierto... ese bastidor, ese sensor que hemos usado en las pruebas... los entrego con la bicleta? NO
Pero eso no significa que no los tenga que comprar/crear/usar.

Más vale que se hayan presupuestado en el proyecto.

Como llamamos a ese sendor o a ese bastidor en el mundo de las pruebas de software? TEST-DOUBLES
Hay muchos tipos de test-doubles:
- Dummy
- Stub
- Fake
- Spy (Sensor)
- Mock

---


NOTA:

Si tengo un fichero de 50 líneas de JAVA... fácil, su fichero de pruebas asociado tendrá 500 líneas de código de pruebas.

El código de pruebas suele estar entorno a un x10 del código de producción.

Escribimos MUCHO MAS CODIGO DE PRUEBAS que de PRODUCCION.
