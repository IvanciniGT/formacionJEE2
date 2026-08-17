# Día 1

## JAVA: Lenguaje de programación

Caraterísticas relevantes:

### Tipado débil (dinámico) vs tipado fuerte (estático)

Variable? Un espacio de memoria que puede almacenar un valor.
ME TEMO QUE NO... NI PARECIDO... Al menos en JAVA... o SI... un poco...

El concepto de variable CAMBIA DE LENGUAJE A LENGUAJE DE PROGRAMACIÓN.
En C, C++, FORTRAN, ADA, una variable es un espacio de memoria que puede almacenar un valor.

Pero... en JAVA, JS, TS, PYTHON, C# una variable es otra cosa.
Una variable en estos lenguajes es una referencia a un dato que tengo en RAM.

Cualquier lenguaje de programación me permite manipular datos. Y esos datos tendrán distinta naturaleza (TIPO DE DATO).
Tendré datos de tipo TEXTO, NUMERO ENTERO, DECIMAL, FECHAS, BOOLEANS (SI/NO), etc.

```java
String texto = "hola";
```

Esa línea hace 3 cosas:
- "hola"                Crear un dato de tipo TEXTO (String) en memoria RAM, con el valor "hola".
                        Imaginad la memoria RAM como un cuaderno de cuadrícula. Hemos abierto el cuaderno por alguna página (NPI de por donde) y hemos escrito allí la palabra "hola".
- String texto          Crear una variable llamada "texto" que es de tipo String.
                        La variable tiene un tipo de dato. NO SOLO EL DATO TIENE UN TIPO DE DATO. 
                        LA PROPIA VARIABLE TIENE UN TIPO DE DATO (en algunos lenguajes... en otros no) 
                        Una variable en JAVA es como un POSTIT. 
                        He tomado un postit del taco de postits de color AZUL (LOS DE TEXTOS) y he escrito en el postit la palabra "texto".
                        En el postit (la variable) NO ESCRIBO la palabra "hola". 
- =                     Pego el postit en la página del cuaderno donde he escrito "hola". 
                        La variable (el postit) apunta al dato (la palabra "hola" escrita en la página del cuaderno).
  = (OPERADOR ASIGNACIÓN) 

  Estoy asignando la variable "texto" al dato "hola".
  NO ESTOY ASIGNANDO EL DATO "hola" A LA VARIABLE "texto".

```java
texto = "adios";
```
- "adios"               Crear un dato de tipo TEXTO (String) en memoria RAM, con el valor "adios".
                        Dónde? En el mismo sitio donde estaba escrito "hola" o en otro sitio?
                        EN OTRO SITIO DISTINTO.
                        En este punto, tengo en RAM 2 datos de tipo String: "hola" y "adios"....al menos en JAVA y JS y PYTHON.
                        En ADA, FORTRAN solo tendría un dato de tipo String: "adios". El dato "hola" habría sido sobreescrito por el dato "adios".
- texto =               Reasigno la variable "texto" al nuevo dato "adios". 
                        El postit azul que tenía escrito "texto" lo despego de la página donde estaba escrito "hola" y lo pego en la página del cuaderno donde he escrito "adios".
                        Y llegados a este punto, en RAM ahora tengo 2 datos, pero el dato "hola" ha quedado huérfano, sin ninguna variable que lo apunte. EN JAVA ese dato es irrecuperable y será marcado como basura. Y QUIZÁS O QUIZÁS NO, en algún momento el recolector de basura de JAVA (un programa que corre en segundo plano, paralelo a nuestra aplicación) y lo borre de la RAM.

JAVA, PY, JS hacen un mal uso (abuso) de la memoria RAM. El mismo programa hecho en C necesitaría la mitad de memoria RAM que el mismo programa hecho en JAVA. Eso es bueno o malo? Es una feature de java. Se diseño de esta forma.
Claro... C++ hace un uso GENIAL de la RAM.... optimizadísimo. Pero a costa de horas y horas de desarrollador EXPERIMENTADO reservando memoria, liberando memoria (alloc, mallocs...) que son propensas a errores (memory leaks, dangling pointers, etc). En JAVA no hay que preocuparse de eso. El recolector de basura se encarga de eso. Pero a costa de usar más memoria RAM.
LA DECISION ES : 
- Más RAM
- Más tiempo de desarrollo (menos horas de desarrollador) 
La industria del software ha decidido que es mejor gastar más RAM y menos horas de desarrollador... al menos en algunos proyectos.


  Estoy reasignando la variable "texto" al dato "adios".
  NO ESTOY REASIGNANDO EL DATO "adios" A LA VARIABLE "texto".

HAY LENGUAJES EN LOS QUE LAS VARIABLES TIENEN TIPO DE DATO (JAVA, C#, ADA, FORTRAN, C, C++) y otros lenguajes en los que las variables NO TIENEN TIPO DE DATO (JS, PYTHON, TS).

```python
texto = "hola"
texto = "adios"
``` 

En python "hola" y "adios" son datos de tipo texto (str), pero la variable "texto" NO TIENE TIPO DE DATO.
Y por ende puede apuntar a datos de cualquier tipo

```python
texto = 3
texto = True
```

En PYTHON, JS, las variables NO TIENEN TIPO DE DATO. Es un lenguaje de tipado DEBIL.
En JAVA, C#, ADA, FORTRAN, C, C++ las variables TIENEN TIPO DE DATO. Es un lenguaje de tipado FUERTE.

El tipado DEBIL NO VALE para proyectos empresariales grandes. NO VALE! Tiene problemas IMPORTANTES!

```pseudocode
    function generaInforme(titulo, datos) {
        // CUERPO DE LA FUNCION
    }
```
Qué problema tengo? NO SE COMO COMUNICARME CON ESA FUNCION!
Qué le tengo que pasar?
- titulo... qué es eso? El título, o un booleano, indicando SI QUIERO O NO título en el informe.
- datos? qué son? Un fichero EXCELL? Una lista de números? NPI
Qué devuelve? NPI
- Un PDF?
- Una ruta de un archivo que se ha generado?

O me miro el código de dentro de la función o miro la documentación (si la han creado)... para entender cómo funciona y que datos son los que tengo que pasar.

Si el proyecto lo hago yo y mi primo y sonb 5 archivos y 6 funciones... nos las sabemos de memoria.
Si el proyecto lo hace un equipo de 10 personas y son 1000 archivos y 10000 funciones...EL TIPADO DEBIL NO SIRVE!

### Compilados / Interpretados

Nosotros vamos a escribir código en un lenguaje de programación (cómo por ejemplo JAVA).
Lo ejecutaremos en una computadora.
La computadora sabe de ese lenguaje (JAVA, C, PY, JS, etc)? NO. 
La computadora entiende SU LENGUAJE (Condicionado por el tipo de CPU que tenga, el sistema operativo que tenga, etc).

Hay que traducir el código de mi programa de MI LENGUAJE al lenguaje que entiende la computadora.

Eso se puede hacer de 2 formas... igual que con lenguajes naturales (los que hablamos los humanos):
- Pre-Traducción: COMPILACION
  - Le mando a mi amigo el chino una receta de tortilla (que yo he escrito en español) pre-traducida al chino.
- Interpretación: Traduucción en tiempo real, línea a línea, mientras se ejecuta el programa.
  - Le mando a mi amigo el chino una receta de tortilla (que yo he escrito en español). 
  - Mi amigo que contrate un intérprete que le vaya traduciendo línea a línea la receta mientras la va leyendo y ejecutando.

Qué es mejor?
DEPENDE...
- Compilación
  - Ventajas:
    - El código se ejecutará más rápido
    - Al compilar (pretraducir) estamos haciendo una relectura del código y eso nos permite detectar errores de sintaxis, errores de tipado, etc.
  - Desventajas:
    - El código debo mandarlo pretraducido. Si tengo un amigo chino, un alemán, y un italiano... tengo que traducir a 3 idiomas distintos.
      Esto me impone un sobrecoste. 
- Interpretación
  - Ventajas:
    - A todo el mundo le mando el mismo programa (en español) y me quito de líos.
  - Desventajas:
    - El código se ejecutará más lento (al ejecutarse se debe ir traduciendo bajo demanda)

JAVA ES MUY RARO.

               javac
    .java -> Compilación -> .class -> interpreta en tiempo de ejecución (JVM)
                             ^^^^^
                             Estos archivos estan escritos en lenguaje de programación BYTE-CODE

Cuando usamos la palabra "JAVA", nos referimos a 2 cosas diferentes:
- Lenguaje de programación JAVA (GRAMÁTICA: Sintaxis, morfología, semántica, etc)
- Máquina virtual de JAVA (JVM: Java Virtual Machine) que es un programa que interpreta en tiempo de ejecución los ficheros .class (que están escritos en lenguaje de programación BYTE-CODE).

De hecho, JAVA tiene tan malas decisiones en su gramática, que hoy en día han aparecido lenguajes con mejores GRAMATICAS (scala, kotlin), que también se compilan a BYTE-CODE y se ejecutan en la JVM.

    .kt -> Compilación -> .class -> interpreta en tiempo de ejecución (JVM)
                           ^^^^^
                           Estos archivos estan escritos en lenguaje de programación BYTE-CODE

    .scala -> Compilación -> .class -> interpreta en tiempo de ejecución (JVM)
                             ^^^^^
                             Estos archivos estan escritos en lenguaje de programación BYTE-CODE

    JAVA en algunos sectores (aplicaciónes) se ha reemplazado por otros lenguajes:
    - BIG DATA: SCALA
    - MOBILE (Android): KOTLIN

En cualquier caso, esos lenguajes siguen aprovechando la arquitectura de la máquina virtual de JAVA (JVM) y la gran cantidad de librerías que existen para JAVA.

Igual que JAVA como lenguaje deja mucho que desear, la JVM es una pasada.... Y hoy en día hay muchos lenguajes de programación que se ejecutan en la JVM (scala, kotlin, groovy, clojure, etc).

## PROYECTO:

### Versión 1

Programa (Comando que poder ejecutar desde una terminal):

c:\Users\Usuario\Desktop\Proyecto> buscador melón es
La palabra melón existe en el diccionario de Español.
Significados:
- Fruta comestible de la familia de las cucurbitáceas, de forma redonda u ovalada, con corteza dura y pulpa jugosa y dulce.
- Persona con pocas luces: Eres un melón.

c:\Users\Usuario\Desktop\Proyecto> buscador melón en
La palabra melón NO existe en el diccionario de Inglés.

c:\Users\Usuario\Desktop\Proyecto> buscador melón elf
Lo siento, pero no tengo diccionario para el idioma élfico.

Los diccionarios los tendremos en ficheros de texto.

#### PROBLEMAS GRAVES:

- Actualización de diccionarios (Nuevo idioma, nueva palabra).
  Esos cambios (en ficheros de texto ... incluso generados en automático) cómo los distribuyo a los usuarios? 
  Los usuarios necesitarán instalar una nueva versión del program... o al menos descargar ficheros de diccioanrios y colocarlos en alguna carpeta = MUCHAS HORAS DE TRABAJO PARA EL USUARIO.
- Nueva versión del programa (Si una palabra no se encuentra, que se ofrezcan alternativas):
  "manana" en Español:
    La palabra manana no existe... quieres decir "mañana" o "manzana"? 
  Los usuarios necesitarán instalar una nueva versión del programa = MUCHAS HORAS DE TRABAJO PARA EL USUARIO.
- Muchos de esos usuarios no serán expertos en informática y en desarrollo. Tendrán problemas = TICKETS a soporte técnico = MUCHAS HORAS DE TRABAJO PARA EL SOPORTE TÉCNICO.
- NO TENEMOS GARANTIA EN LA EMPRESA de que todos los usuarios tienen instalada la misma versión del programa. Algunos usuarios tendrán una versión antigua y otros una versión nueva. Esto es un problema de soporte técnico = MUCHAS HORAS DE TRABAJO PARA EL SOPORTE TÉCNICO.
    = DISTINTOS USUARIOS podrían tener distintas versiones del programa... Y obtener distintas respuestas a la misma consulta.

### Versión 2

Cambiar la forma de guardar las palabras. Ya no las tendremos en local en ficheros de texto, sino en un servidor central.
Vamos a llevar toda la parte de búsqueda de palabras a un servidor central. El programa cliente (el que se ejecuta en la terminal del usuario) se conectará al servidor para hacer la búsqueda de palabras. Que seguirán en ficheros de texto.

### Versión 3

Convertir el repositorio de palabras en una base de datos. El programa cliente seguirá conectándose al servidor para hacer la búsqueda de palabras. Que seguirán en una base de datos.

### Versión 4

Cambiaremos el programa que usa el usuario. Ya no será un programa de consola, sino un programa con interfaz gráfica WEB . ESTO ES LO QUE MONTAREMOS EN LA MASTERCLASS DE IAs.

---

Lo principal será que diseñemos el proyecto/producto de forma que facilitemos esos cambios que vamos a ir haciendo.
NO HAY NADA PEOR en un proyecto de software que tener que MODIFICAR COSAS. 
NO QUEREMOS MODIFICAR COSAS.
Queremos:
- Tirar cosas a la basura
- Crear cosas nuevas
PERO NO QUEREMOS MODIFICAR.

Necesito plantearme desde el principio que NO TENGO UN PRODUCTO... tengo un SISTEMA.
Y ese sistema estará compuesto por muchos componentes.... IGUAL QUE OCURRE EN UN COCHE!
TENDRÉ QUE DEFINIR ESPECIFICACIONES de los componentes.
Esas especificaciones son las que me permitirán a futuro hacer cambios de componentes SIN DOLOR! = ARQUITECTURA DE SOFTWARE.
---

Que un programa funcione SE DA POR DESCONTADO. No es relevante. Es lo de menos.
Lo importante DE VERDAD es que el programa sea MANTENIBLE Y EVOLUCIONABLE en el tiempo!

UN PRODUCTO DE SOFTWARE POR DEFINICION ES UN PRODUCTO SUJETO A CAMBIOS Y MANTENIMIENTO.

IGUAL QUE UN COCHE ES POR DEFINICION UN PRODUCTO SUJETO A MANTENIMIENTOS.

---

LCC = Life Cycle Cost = COSTE DE CICLO DE VIDA
El coste de un producto de software no es el coste de desarrollo, sino el coste de desarrollo + el coste de mantenimiento y evolución en el tiempo + el coste de operación del producto.

Lo que me interesa como empresa es minimizar el coste GLOBAL: LCC
Me da igual que hoy en día me salga el producto un poco más caro de desarrollar, si eso me permite que en el futuro me salga mucho más barato de mantener y evolucionar.

---

En paralelo con esto, vamos a ir aprendiendo:
- Patrones de diseño de software
- Arquitectura de software
- Protocolos de comunicación entre componentes de software (CORBA, RMI, REST)
- Programación funcional
- ...

---

# Versionado de software

El control de versión de los componentes/aplicaciones es CRITICO en el mundo del software.

Con caracter general (aunque hay excepciones) en el mundo del software usamos lo que se denomina el esquema de versionado SEMVER (Semantic Versioning).

Eso es una nomenclatura para versionar software de forma que sea fácil de entender para los desarrolladores y para los usuarios.

vA.B.C

                    Cuándo cambian?

    A = MAJOR       Breaking changes (cambios incompatibles con versiones anteriores)
                    Cuando se quitan funcionalidades existentes (con o sin reemplazo)
    B = MINOR       Nueva funcionalidad
                    Y/O funcionalidad marcada como obsoleta (deprecated)
                        Opcionalmente puede incluirse arreglos de errores (bug fixes)
    C = PATCH       BugFixes (corrección de errores)

Dicho esto...
Vamos a aplicarlo a nuestro proyecto de búsqueda de palabras.
Versionamos el sistema o sus componentes? TODO!!!!
    Y si tenemos en cuenta que CADA COSA QUE VERSIONEMOS LA VAMOS A TRATAR INTERNAMENTE COMO UN PROYECTO INDEPENDIENTE, cuántos proyectos tendrá nuestro sistema?
    - gestor-de-diccionarios-api                    v1.0.0
    - gestor-de-diccionarios-impl-ficheros          v1.0.0
    - comunicacion-usuario-api                      v1.0.0  
    - comunicacion-usuario-impl-consola             v1.0.0
    - procesador-de-peticiones                      v1.0.0
    - aplicacion                                    v1.1.0
    - fichero-diccionario-español                   v1.1.0
    - fichero-diccionario-ingles                    v1.0.1

Cambiamos un significado de inglés (tenía una errata)   fichero-diccionario-ingles                    v1.0.0 -> v1.0.1
Metemos nuevas palabras en el diccionario de español     fichero-diccionario-español                   v1.0.0 -> v1.1.0
Alguna otra cosa a cambiar? SI
La aplicación. Estaba en versión 1.0.0... y esa versión usaba:
    fichero-diccionario-español v1.0.0
    fichero-diccionario-ingles  v1.0.0
Ahora la aplicación usará:
    fichero-diccionario-español v1.1.0
    fichero-diccionario-ingles  v1.0.1
Debe pasar la aplicación a versión 1.1.0? SI, porque ha cambiado la funcionalidad de la aplicación (ahora puede buscar más palabras en español y trae algunas palabras arregladas en inglés).

Las dependencias entre los componentes de un sistema deben incluir la versión de los componentes de los que dependen.
Ese control es CRITICO!
Imaginas que queremos que los diccionarios estén en bbdd. Qué hay que cambiar? añadir? quitar?
Hay que tocar la gestor-de-diccionarios-impl-ficheros? NO
Necesito crear un componente nuevo: 
    - gestor-de-diccionarios-impl-bbdd              v1.0.0
    - aplicacion                                    v2.0.0
      - El cambio de BBDD rompe compatibilidad... de una forma que no veíamos a priori.
      - Sobre la misma infraestructura que teníamos funcionando la app v1.0.0 puede funcionar la nueva de BBDD? NO
        Necesito una BBDD disponible. BREAKING CHANGE. La app v2.0.0 no es compatible con la app v1.0.0
    - Cambiamos los proyectos de los ficheros?
      - Aquí hay opciones a valorar:
        - OPCION 1: Mantengo los proyectos de los ficheros... que quizás se usan para otras cosas... o se siguen usando en versiones antiguas del producto... que quizás hay usuarios con ellas.
        - OPCION 2: Quito esos proyectos si nadie ya los va a usar.
      - En cualquier caso, necesitaría popular la BBDD con las palabras y sus significados.
        - Podría hacer esto como proyectos nuevos, que definan sus propias palabras
        - Podría hacer un proyecto nuevoi que cargue los ficheros de palabras y sus significados en la BBDD.
        - Los ficheros los sigo manteniendo como fuente de las palabras y sus significados, pero ya no los uso en la app. La app ahora usa la BBDD, que se carga inicialmente con los ficheros de palabras y sus significados.
          DECISIONES!
          Posiblemente ya tenga programas en la empresa desarrollados para ir generando / editando los ficheros de palabras y sus significados. Y esos programas no los quiero tocar por ahora.
          Sería muy sensato crear proyecto nuevo que cargue los ficheros de palabras y sus significados en la BBDD. Y que la app ahora use la BBDD.

Así establecido nos damos rápido cuenta que nuestros sistema es una colección de versiones concretas de componentes que interactúan entre sí.

    Aplicación v2.0.0
        |
        |-- gestor-de-diccionarios-api v1.0.0
        |       |
        |       |-- gestor-de-diccionarios-impl-bbdd v1.0.0
        |
        |-- comunicacion-usuario-api v1.0.0
        |       |
        |       |-- comunicacion-usuario-impl-consola v1.0.0
        |-- procesador-de-peticiones v1.0.0
        |-- fichero-diccionario-español v1.1.0
        |-- fichero-diccionario-ingles v1.0.1
        |-- cargador-de-diccionarios v1.0.0

Cada uno de esos proyectos tendrá su propio control de versiones asociado.
Es decir, cada uno de ellos tendrñá su propio repositorio de control de versiones (GIT, SVN, etc) y su propio control de versiones SEMVER.

Y eso es una ventaja.
Habrá gente que trabaje solo en un proyecto...
- Por ejemplo, lingüistas de Español que solo trabajen en el proyecto de los ficheros de palabras y sus significados de Español. No les interesa el resto de proyectos.
- Tendré otro equipo de linguistas de Inglés que solo trabajen en el proyecto de los ficheros de palabras y sus significados de Inglés. No les interesa el resto de proyectos.
- Tendré otro equipo de desarrolladores que trabajarán sobre los proyectos con código java-
  Estos acceden a los proyectos de ficheros de diccionarios? PARA QUE?

Y cada persona accede a lo que debe.
NO ES SOLO CONTROL, es ORGANIZACION!
Simplifico la vida a la gente. LES DOY SOLO AQUELLO QUE ES DE SU INTERES. No les hago perderse entre 500 ficheros de los cuales 400 no les interesan. Les hago perder el tiempo y la paciencia. 

En el caso de JAVA, MAVEN es la herramienta que me ayudará un montón con esto.

---

# MAVEN

Maven NO ES SOLO UN GESTOR DE DEPENDENCIAS. Maven gestiona dependencias... es solo una de las 500k tareas que hace.

Maven es una herramienta de automatización de proyectos de software.

Nos ayuda a automatizar:
- El compilar código fuente
- El ejecutar tests
- El empaquetar el código compilado en un artefacto distribuible (jar, war, ear, etc)
- Generar documentación de mi proyecto
- A pasar mi código a herramientas como sonarqube para que analicen la calidad de mi código
- ...
- Y a gestionar dependencias.

En nuestro caso, vamos a usar MUCHO MAVEN. No es la única opción en JAVA. GRADLE se usa mucho también... aunque en las empresas sigues usando principalmente MAVEN, especialmente en proyectos de backend. GRADLE se usa mucho en proyectos de Android (mobile).

Cuando usamos maven, MAVEN impone una determinada estructura a mi carpeta de proyecto:

    mi-carpeta/
        src/               Aquí irá todo el código de mi aplicación
            main/            Irá el código distribuible de mi aplicación
                java/            Los ficheros JAVA de mi aplicación
                resources/       Los ficheros que NO SEAN JAVA de mi aplicación (ficheros de configuración, ficheros de diccionarios, etc)
            test/            Irá el código de pruebas automatizadas de mi aplicación
        target/            Esta carpeta la genera maven... y la gestiona maven.
            classes/            Aquí maven pondrá los ficheros compilados de mi aplicación + los ficheros de main/resources
            test-classes/       Aquí maven pondrá los ficheros compilados de mis tests + los ficheros de test/resources
            surefire-reports/   Aquí maven pondrá los resultados de la ejecución de mis tests
            artefacto.jar          Aquí maven pondrá el artefacto distribuible de mi aplicación (jar, war, ear, etc)
        pom.xml            Este es el archivo de configuración de maven para mi proyecto.

Maven NO SABE HACER LA "O" CON UN CANUTO.
Lo único que hace es delegar tareas a plugins!
Toda tarea que le pidamos a maven es delegada a un plugin que se encargará de hacerla.
Por defecto maven trae como unos 10 plugins preconfigurados, para las tareas más básicas.
Yo puedo añadir nuevos plugins... eso se configurará en el fichero pom.xml de mi proyecto.

A maven le puedo pedir que haga una tarea mediante un plugin. PERO NO ES HABITUAL HACERLO.
Maven define UN CICLO DE VIDA para los proyectos de software.
Y cada etapa de ese ciclo de vida tiene asociada una serie de tareas que se ejecutan en orden.

ETAPAS:
- validate: Validar que la estructura de mi proyecto es correcta y el archivo pom.xml es correcto.
      v 
- resources: Copiar los ficheros de src/main/resources a target/classes
      v
- compile: Compilar el código fuente de mi proyecto (src/main/java)
      v
- test-compile: Compilar el código fuente de mis tests (src/test/java)
      v
- test: Ejecutar los tests de mi proyecto (src/test/java)
      v
- package: Empaquetar el código compilado de mi proyecto en un artefacto
      v
- install: Hacer accesible mi proyecto para que pueda ser usado localmente (en mi máquina) como dependencia por otros proyectos.

Las etapas se ejecutan en orden e incluyen las anteriores.
Si pido a maven que ejecute la fase/etapa install, maven ejecutará todas las fases anteriores (validate, resources, compile, test-compile, test, package) y finalmente la fase install.
Si pido a maven que ejecute la fase/etapa test, maven ejecutará todas las fases anteriores (validate, resources, compile, test-compile) y finalmente la fase test.

Una cosa son las fases/etapas y otra las tareas asociadas a cada fase/etapa.
Lo que he escrito en el listado de arriba son las etapas + tarea por defecto asociada a cada etapa.
Pero en el fichero pom.xml puedo configurar tareas adicionales asociadas a cada fase/etapa.

Dentro del fichero pom.xml se define:
- Las coordenadas del proyecto: SUS DATOS IDENTIFICATIVOS. Son 3:
  - El groupId: El grupo al que pertenece el proyecto. En nuestro caso sería algo del estilo: com.curso.diccionarios
  - El artifactId: El nombre del proyecto. En nuestro caso sería algo del estilo: procesador-de-peticiones o gestor-de-diccionarios-api
  - La versión del proyecto
- Metadatos adicionales (opcionales): 
  - Nombre descriptivo
  - URL del proyecto
  - Licencia del proyecto
  - Autores del proyecto
  - ...
- Plugins que se usan en el proyecto. Si es que tengo plugins adicionales a los que vienen por defecto con maven.
- Dependencias que usa el proyecto. Es decir, otros proyectos de los que depende mi proyecto y que necesita para compilarse y ejecutarse.
- Hay un bloque adicional llamado PROPERTIES, que sirve para definir variables que luego puedo usar en el resto del fichero pom.xml.

---

# MAÑANA!

Configurar vuestros entornos de trabajo para el curso:
- Al menos JAVA 21:
  https://github.com/adoptium/temurin21-binaries/releases/download/jdk-21.0.12%2B8/OpenJDK21U-jdk_x64_windows_hotspot_21.0.12_8.msi
- MAVEN descargado (es un ZIP):
  https://dlcdn.apache.org/maven/maven-3/3.9.16/binaries/apache-maven-3.9.16-bin.zip
- VSCODE
  https://code.visualstudio.com/download?_exp_download=fb315fc982#
  https://code.visualstudio.com/download
Crear una estructura de carpetas para nuestros PROYECTOS + Ficheros pom.xml
Mover el código que he ido creando en proyecto.md a archivos JAVA dentro de la estructura de carpetas de los proyectos.
Completaremos el código que falta:
    - SuministradorDeDiccionariosEnFicheros.java
    - Diccionario.java
    - ComunicadorConUsuarioDesdeTerminal.java

Pondremos el sistema en marcha...
PERO BIEN.... Con pruebas automatizadas !
Charlar un poquito acerca de TESTING!
