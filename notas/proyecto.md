# Aplicación de búsqueda de palabras en diccionarios.

## Versión 1

Programa (Comando que poder ejecutar desde una terminal):

Casos de uso:

- Buscar una palabra que existe en un diccionario de un idioma determinado que está contemplado.

    c:\Users\Usuario\Desktop\Proyecto> buscarPalabra melón es
    La palabra melón existe en el diccionario de Español.
    Significados:
    - Fruta comestible de la familia de las cucurbitáceas, de forma redonda u ovalada, con corteza dura y pulpa jugosa y dulce.
    - Persona con pocas luces: Eres un melón.

- Buscar una palabra que NO existe en un diccionario de un idioma determinado que está contemplado.

    c:\Users\Usuario\Desktop\Proyecto> buscarPalabra melón en
    La palabra melón NO existe en el diccionario de Inglés.

- Buscar una palabra en un diccionario de un idioma NO contemplado.

    c:\Users\Usuario\Desktop\Proyecto> buscarPalabra melón elf
    Lo siento, pero no tengo diccionario para el idioma élfico.

- Usar el programa sin indicar los datos necesarios para realizar la búsqueda.
  
    c:\Users\Usuario\Desktop\Proyecto> buscarPalabra
    c:\Users\Usuario\Desktop\Proyecto> buscarPalabra melón
    c:\Users\Usuario\Desktop\Proyecto> buscarPalabra es
    Lo siento, pero no has indicado los datos necesarios para realizar la búsqueda. 
    Debes indicar la palabra y el idioma.
    Ejemplo:
        buscarPalabra melón es

NOTAS: 
- Los diccionarios los tendremos en ficheros de texto.
- Solamente tendremos 1 diccionario por idioma.

## COMPONENTES?

Esta es la pregunta clave. Vamos a aplicar un concepto (PRINCIPIO DE DESARROLLO) para ayudarnos a responderla: SoC
SoC = Separation of Concerns (Separación de Preocupaciones).

Ese principio lo enunció un famoso desarrollador llamado Edsger Dijkstra.
En 1970 ganbó un premio (muy respetado) llamado Turing Award, que es el equivalente al Nobel de la Informática.
Pronunció un discurso. Ese discurso se publió posteriormente bajo el título: THE HUMBLE PROGRAMMER. De ese discurso sale el Principio SOC.

Edsger Dijkstra decía que un programador tiene un cerebro LIMITADO (como cualquier humano) y que ser consciente de ello es clave para poder acometer un buen desarrollo. Debemos ser humildes. Cuando estamos trabajando en un componente, debemos centrarnos en ESE componente y no preocuparnos de lo que hacen los demás componentes. Debemos centrarnos en una única preocupación en cada momento.
Si intento mirar la foto global está bien... pero sin entrar en detalle. Si entro en detalle, debo ya centrarme (mis recursos, mi atención, mi cerebro) en ese componente y no preocuparme de lo que hacen los demás componentes.

### Qué componentes debe tener nuestro sistema/producto/aplicación?

Para analizar esos componentes, vamos a fijarnos en LAS RESPONSABILIDADES Que tendrá cada uno de ellos.. que irán asociadas a tareas de más bajo nivel.

Qué tareas de más bajo nivel realiza nuestro sistema/producto/aplicación?:
- Leer los datos del usuario (palabra, idioma)
- Mostrar al usuario el resultado de la búsqueda (si existe o no la palabra en el diccionario, y si existe, mostrar los significados)
- Consultar datos de palabras en diccionarios asociados a un idioma determinado.
- Consultar si tenemos diccionario para un idioma determinado.
- ...

Esas tareas, las vamos agrupando... COHESION: Agrupamos tareas que están relacionadas entre sí. 
Cada grupo de tareas relacionadas entre sí, lo llamaremos RESPONSABILIDAD.
Vamnos a crear componentes que tengan una única responsabilidad.

RESPONSABILIDADES:
- Gestión de diccionarios. Tareas: 
  - Consultar datos de palabras en diccionarios asociados a un idioma determinado.
  - Consultar si tenemos diccionario para un idioma determinado.
- Comunicación con el usuario. Tareas:
  - Leer los datos del usuario (palabra, idioma)
  - Mostrar al usuario el resultado de la búsqueda (si existe o no la palabra en el diccionario, y si existe, mostrar los significados)

COMPONENTES:
- Gestión de diccionarios. 
- Comunicación con el usuario.

Pregunta:
- Dónde guardamos los diccionarios?     En ficheros de texto.
- Va a ser siempre así?                 No necesariamente... Quizás cambie a futuro:
                                        - BBDD
                                        - Servicio centralizado en un servidor
                                        - ...

Dado que esto puedo ocurrir... y no suena a disparate... VAMOS A PREPARAR UNA ESPECIFICACIÓN DE ESTE COMPONENTE.
De forma que inicialmente crearemos una IMPLEMENTACIÓN del componente (de esa especificación) que lea los diccionarios de ficheros de texto, pero que en el futuro podamos cambiar la implementación del componente sin afectar al resto de componentes.

    Es lo mismo que si al diseñar un coche, digo que las ruedas a futuro pueden cambiar (de hecho cambiarán)... y diseño una especificación de las RUEDAS: 215 R17 W
    El día de hoy (primera versión del coche... según sale de fábrica)montará unas Michelín... Pero el día de mañana (v2), quizás las cambié por unas Pirelli... y el resto del coche no se verá afectado. El resto del coche no necesita saber qué ruedas monta. Solo necesita saber que las ruedas cumplen con la especificación 215 R17 W.

En nuestro caso (desarrollo de software orientado a objetos), vamos a crear una INTERFAZ (especificación) de un componente que gestione diccionarios. Tendremos 1 o más interfaces con distintos métodos/funciones: TAREAS

#### Componente de gestión de diccionarios

```java
import java.util.List;
import java.util.Optional;

public interface Diccionario {

    boolean existe(String palabra);
    // List<String> sugerencias(String palabra);

    // "manana" en ESPAÑOL: mañana, manzana, manada
    // "$$//.,-FEDERICO":   NINGUNA SUGERENCIA
    Optional<List<String>> getSignificados(String palabra); // throws NoSuchWordException; // DESASTRE!
    // No tengo npi de cómo comunicarme con esta función!
    // Qué le tengo que pasar a la función? La palabra de la que quiero los significados.
    // Qué devuelve la función? Y AQUI ESTA EL PROBLEMON!
    // A priori devuelve una lista de textos (Strings) que son los significados de la palabra.
    // Eso cuela si lo que busco es la palabra "melón" en "es"...
    // Y si busco la palabra ARCHILOCOCO en "Español"? NO EXISTE!
    // Qué devuelve entonces? NPI!
    // - null
    // - Lista vacía
    // - Lanzar una excepción: NoSuchWordException
    // Cuando nos encontramos con estos casos... vemos estas 3 opciones habitualmente.
    // Y sabéis por qué se vienen usando estas 3 opciones en la industria? PORQUE NINGUNA ES BUENA:
    // - EXCEPTION:
    //    - Ventajas: Es explicita
    //    - Inconvenientes: Nunca debería usar Excepciones para controlar LOGICA de negocio. 
    //      Las excepciones son MUY CARAS DE GENERAR (computacionalmente hablando) y solo deberían usarse para casos EXCEPCIONALES
    //      Cuando no hay alternativa. 
    //      Cuando hasta que no hago la operación NO HAY FORMA DE SABER SI LA PODRé HACER O NO EXITOSAMENTE HASTA QUE LO INTENTE (TRY)!
    // - null           SON AMBIGUAS!
    // - Lista vacía    SON AMBIGUAS! NO EXPLICITAS . No hay nada en la FIRMA (SIGNATURE) de la función que indique si devolvemos una cosa u otra.
    // Para saber si devolverá una lista vacia o un null nos toca mirar código o documentación... En el año 2026? EN SERIO?
    // Desde JAVA 1.8 tenemos una alternativa que es mucho mejor: Optional<T>
    // Desde JAVA 1.8 está considerado una muy mala práctica que una función devuelva null. Se considera que es un error de diseño.
    // Por la ambigüedad que genera. Y es un error de diseño porque no hay nada en la firma de la función que indique si devuelve null o no.
    // Un Optional es como una caja. Siempre se me devuelve una caja... Que puede estar vacía (isEmpty()) o contener un valor (isPresent()). Y 
    // si contiene un valor, puedo obtenerlo (get()).
    // Casi todas las empresas hoy en día usan herramientas de calidad de código: SONARQUBE
    // Estas herramientas revisan código, a ver si está escrito de forma correcta. 
    // Este trabajo antiguamente lo hacían los desarrolladores senior, revisando el código de los juniors. Hoy en día lo hace SONARQUBE.
    // Las empresas tienen este tipo de herramientas. Y SI LA HERRAMIENTA DETECTA QUE EL CODIGO no tiene una buena calidad, no lo deja pasar a producción. No lo deja pasar a la siguiente fase del desarrollo.
    // Esto está automatizado en las empresas.
    // SONARQUBE detecta un problema como este... Y LO MARCA COMO DEFECTO (SMELL CODE).
    // Y el código no pasa a producción hasta que no se arregla el defecto.
}

Resumiendo:

```java

package com.curso.diccionarios.gestion;

import java.util.List;
import java.util.Optional;

public interface Diccionario {
    boolean existe(String palabra);
    Optional<List<String>> getSignificados(String palabra); 
}

public interface SuministradorDeDiccionarios { // Librería, Amazón (tienda online...)
    boolean tienesDiccionarioDe(String idioma);
    Optional<Diccionario> dameDiccionario(String idioma);
}
```

Esto es la especificación de lo que es un diccionario.
Y cualquier Diccionario que se implemente, debe cumplir con esta especificación.
Me tiene que permitir saber si una palabra existe en el diccionario y me tiene que permitir obtener los significados de una palabra (si existe).
Importa si las palabras se guardan en ficheros de texto o en una BBDD para esta especificación? NO
Es un detalle de implementación. Que podría cambiar a futuro.

Con esos 2 interfaces, tenemos ya lo relativo a la GESTION DE DICCIONARIOS a nivel de nuestra aplicación?

Este componente está acabado. Mejor dicho SU ESPECIFICACIÓN está acabada.
Ahora crearé una implementación: Contra Ficheros de texto:

```java
package com.curso.diccionarios.gestion.impl.ficheros;

import com.curso.diccionarios.gestion.Diccionario;
import com.curso.diccionarios.gestion.SuministradorDeDiccionarios;

import java.util.List;
import java.util.Optional;

public class DiccionarioEnFichero implements Diccionario {
    public boolean existe(String palabra) {
        // Código que busca la palabra en un fichero de texto
    }
    public Optional<List<String>> getSignificados(String palabra) {
        // Código que busca los significados de la palabra en un fichero de texto
    }
}
public class SuministradorDeDiccionariosEnFicheros implements SuministradorDeDiccionarios {

    public SuministradorDeDiccionariosEnFicheros(String rutaDirectorioDiccionarios) {
        // Código que inicializa el suministrador de diccionarios con la ruta del directorio donde están los ficheros de diccionarios
    }

    public boolean tienesDiccionarioDe(String idioma) {
        // Código que comprueba si existe un fichero de texto para el idioma
    }
    public Optional<Diccionario> dameDiccionario(String idioma) {
        // Código que devuelve un DiccionarioEnFichero para el idioma
    }
}
```

Eso sí, el día de mañana podría tener otra implementaciñón del componente de gestión de diccionarios que consulte una BBDD o un servicio centralizado en un servidor remoto. Y el resto de componentes no se verían afectados.

```java
public class DiccionarioEnBBDD implements Diccionario {
    public boolean existe(String palabra) {
        // Código que busca la palabra en una BBDD
    }
    public Optional<List<String>> getSignificados(String palabra) {
        // Código que busca los significados de la palabra en una BBDD
    }
}
public class SuministradorDeDiccionariosEnBBDD implements SuministradorDeDiccionarios {
    public boolean tienesDiccionarioDe(String idioma) {
        // Código que comprueba si existe un diccionario para el idioma en la BBDD
    }
    public Optional<Diccionario> dameDiccionario(String idioma) {
        // Código que devuelve un DiccionarioEnBBDD para el idioma
    }
}   
```

Ya tenemos la especificación de un componente.
Vamos a por la del otro: Comunicación con el usuario

#### Componente de comunicación con el usuario

```java
package com.curso.diccionarios.comunicacion;

public interface ComunicadorConUsuario {

    // Entrada
    Optional<String> getPalabraDelUsuario();
    Optional<String> getIdiomaDelUsuario();
    // Salida
    void mostrarSignificadosAlUsuario(String palabra, String idioma, List<String> significados);
    void mostrarPalabraNoExisteAlUsuario(String palabra, String idioma);
    void mostrarErrorDeUsoDelProgramaAlUsuario();
    void mostrarErrorDeIdiomaNoContempladoAlUsuario(String idioma);

    void mostrarErrorInternoDelSistemaAlUsuario(String mensajeErrorInternoDelSistema);

}
```

De nuevo hemos acabado la especificación del componente de comunicación con el usuario.

Hoy en día (para la versión 1) vamos a tener un:

```java

package com.curso.diccionarios.comunicacion.impl.consola;

public class ComunicadorConUsuarioPorConsola implements ComunicadorConUsuario {

    // Entrada
    public Optional<String> getPalabraDelUsuario() {
        // Código que lee la palabra del usuario desde la consola
    }
    public Optional<String> getIdiomaDelUsuario() {
        // Código que lee el idioma del usuario desde la consola
    }
    // Salida
    public void mostrarSignificadosAlUsuario(String palabra, String idioma, List<String> significados) {
        // Código que muestra los significados al usuario por consola
    }
    public void mostrarPalabraNoExisteAlUsuario(String palabra, String idioma) {
        // Código que muestra al usuario que la palabra no existe en el diccionario por consola
    }
    public void mostrarErrorDeUsoDelProgramaAlUsuario() {
        // Código que muestra al usuario un mensaje de error de uso del programa por consola
    }
    public void mostrarErrorDeIdiomaNoContempladoAlUsuario(String idioma) {
        // Código que muestra al usuario un mensaje de error de idioma no contemplado por consola
    }
    public void mostrarErrorInternoDelSistemaAlUsuario(String mensajeErrorInternoDelSistema) {
        // Código que muestra al usuario un mensaje de error interno del sistema por consola
    }   

}
```

El día de mañana podríamos cambiarlo por un:

```java
public class ComunicadorConUsuarioPorAplicacionDesktop implements ComunicadorConUsuario {

    public Optional<String> getPalabraDelUsuario() {
        // Código que lee la palabra del usuario desde una aplicación de escritorio
    }
    public Optional<String> getIdiomaDelUsuario() {
        // Código que lee el idioma del usuario desde una aplicación de escritorio
    }
    public void mostrarSignificadosAlUsuario(String palabra, String idioma, List<String> significados) {
        // Código que muestra los significados al usuario por una aplicación de escritorio
    }
    public void mostrarPalabraNoExisteAlUsuario(String palabra, String idioma) {    
        // Código que muestra al usuario que la palabra no existe en el diccionario por una aplicación de escritorio
    }
    public void mostrarErrorDeUsoDelProgramaAlUsuario() {
        // Código que muestra al usuario un mensaje de error de uso del programa por una aplicación de escritorio
    }
    public void mostrarErrorDeIdiomaNoContempladoAlUsuario(String idioma) {
        // Código que muestra al usuario un mensaje de error de idioma no contemplado por una aplicación de escritorio
    }
}
```

Lo que haré será TIRAR el ComunicadorConUsuarioPorConsola a la basura y sustituirlo por el ComunicadorConUsuarioPorAplicacionDesktop (uno nuevo) y el resto de componentes no se verán afectados.

Hay un principio de software (OTRO), que pertenece a un grupo de principios de desarrollo llamados SOLID, que se llama: Open/closed Principle (Principio de Abierto/Cerrado). Este principio dice que un componente debe estar abierto a la extensión pero cerrado a la modificación.

Yo debo poder ampliar/cambiar funcionalidades de un componente sin tener que modificarlo. Eso afecta igual a nievl de producto/desarrollo/sistema completo.
Debo ser capaz de cambiar comportamientos de mi sistema sin tener que modificarlo: Quitando y poniendo.

## RESUMEN:

### ESPECIFICACIONES:
- gestion-diccionarios-api
- comunicacion-usuario-api

### IMPLEMENTACIONES:
- gestion-diccionarios-impl-ficheros
- comunicacion-usuario-impl-consola

Y entre esos componentes y especificaciones hay DEPENDENCIAS.

### Diagrama de dependencias entre componentes de nuestro sistema.

        gestion-diccionarios-impl-ficheros      ----->      gestion-diccionarios-api
                      ^                                             ^
                      |                                             |
              coche / aplicación  ------------------->      procesador-de-peticiones
                      |                                             |
                      v                                             v
        comunicacion-usuario-impl-consola       ----->      comunicacion-usuario-api


Ahora... nos falta LA APLICACION QUE SE EJECUTA... esa la tenemos?

Vamos a pensar en el trabajo que se debe realizar cuando se ejecuta el programa. Qué hace el programa cuando se ejecuta?:

```java

package com.curso.diccionarios.aplicacion;

import com.curso.diccionarios.gestion.SuministradorDeDiccionarios;
import com.curso.diccionarios.gestion.Diccionario;
import com.curso.diccionarios.comunicacion.ComunicadorConUsuario;


public class ProcesadorDePeticiones { // Esto es como el chasis de mi coche. 
// El chasis no tiene especificación... No voy a cambiar el chasis por otro chasis.... Eso es básicamente CAMBIAR EL MODELO DE COCHE.
// De un coche NO CAMBIO EL MODELO (El chasis) ... le cambio las ruedas... la radio, la bateria...NO EL CHASIS

    private SuministradorDeDiccionarios suministradorDeDiccionarios;
    private ComunicadorConUsuario comunicadorConUsuario;

    public ProcesadorDePeticiones(SuministradorDeDiccionarios suministradorDeDiccionarios, ComunicadorConUsuario comunicadorConUsuario) {
        // INYECCION DE DEPENDENCIAS: Me pasan desde fuera las dependencias/instancias que necesito para funcionar. No me preocupo (al crear este archivo) de cómo se crean esas dependencias. NO ES MI RESPONSABILIDAD. Me las pasan ya creadas.
        // Yo aplico SoC: Separación de preocupaciones. Me centro en mi preocupación / responsabilidad: Procesar peticiones. No me preocupo de qué suministrador o qué comunicador se use. QUE ME LOS DEN. ME QUITO EL MARRON DE ENCIMA!
        this.suministradorDeDiccionarios = suministradorDeDiccionarios;
        this.comunicadorConUsuario = comunicadorConUsuario;
        // Me importa a mi, CREADOR DELPROCESADOR DE PETICIONES, elsuministrador de diccionarios y el comunicador con el usuario que se usen? 
        // NO. Pues que me los pasen!
    }

    public void procesarPeticion(){
        // Obtener los datos.. De dónde? Del ComunicadorConUsuario.
        Optional<String> palabra = comunicadorConUsuario.getPalabraDelUsuario();
        Optional<String> idioma  = comunicadorConUsuario.getIdiomaDelUsuario();
        // Verificar que el usuario ha dado palabra e idioma.
        if( palabra.isEmpty() || idioma.isEmpty() ){
            comunicadorConUsuario.mostrarErrorDeUsoDelProgramaAlUsuario();
        } else {
            if( suministradorDeDiccionarios.tienesDiccionarioDe( idioma.get() ) ){
                Optional<Diccionario> diccionario = suministradorDeDiccionarios.dameDiccionario( idioma.get() );
                if( diccionario.isPresent() ){
                    Diccionario diccionarioDelIdioma = diccionario.get();
                    if( diccionarioDelIdioma.existe( palabra.get() ) ){
                        Optional<List<String>> significados = diccionarioDelIdioma.getSignificados( palabra.get() );
                        if( significados.isPresent() ){
                            comunicadorConUsuario.mostrarSignificadosAlUsuario( palabra.get(), idioma.get(), significados.get() );
                        } else {
                            // La implementación del componente de gestión de diccionarios me ha devuelto un diccionario para el idioma indicado, pero no me ha devuelto los significados de la palabra que supuestamente existe.
                            // Esto es un error de implementación del componente de gestión de diccionarios. 
                            // No debería ocurrir nunca.
                            // Si ocurre es un BUG ( DEFECTO ) en la implementación del componente de gestión de diccionarios.
                            // Me fío yo del ti@ que ha creado esa implementación? NO (programación DEFENSIVA!)
                            // Esto da calidad y robustez a mi aplicación.
                            comunicadorConUsuario.mostrarErrorInternoDelSistemaAlUsuario( "Error interno del sistema: La implementación del componente de gestión de diccionarios me ha devuelto un diccionario para el idioma indicado, pero no me ha devuelto los significados de la palabra que supuestamente SI EXISTE. Contacte con soporte técnico" );
                        }
                    } else {
                        comunicadorConUsuario.mostrarPalabraNoExisteAlUsuario( palabra.get(), idioma.get() );
                    }
                } else {
                    // La implementación del componente de gestión de diccionarios no me ha devueltoun diccionario para el idioma indicado.
                    // A pesar de haberme dicho que sí que tenía diccionario para ese idioma.
                    // Esto es un error de implementación del componente de gestión de diccionarios. 
                    // No debería ocurrir nunca.
                    // Si ocurre es un BUG ( DEFECTO ) en la implementación del componente de gestión de diccionarios.
                    // Me fío yo del ti@ que ha creado esa implementación? NO (programación DEFENSIVA!)
                    // Esto da calidad y robustez a mi aplicación.
                    comunicadorConUsuario.mostrarErrorInternoDelSistemaAlUsuario( "Error interno del sistema: La implementación del componente de gestión de diccionarios no me ha devuelto un diccionario para el idioma indicado. A pesar de haberme dicho que sí que tenía diccionario para ese idioma. Contacte con soporte técnico" );
                }
            } else {
                comunicadorConUsuario.mostrarErrorDeIdiomaNoContempladoAlUsuario( idioma.get() );
            }
        }
    }

}
```
Este programa que hemos montado DEPENDE de las 2 especificaciones de componentes que hemos creado: gestion-diccionarios-api y comunicacion-usuario-api.
Pero no depende de las implementaciones concretas de esos componentes. 
Eso es lo que permitirá el día de mañana cambiar la implementación de esos componentes sin afectar a este programa.

Lo que acabamos de definir es OTRO COMPONENTE de la aplicación: LOGICA DE NEGOCIO. Lo que debe hacer nuestro sistema al ejecutarse. Y lo hemos hecho sin entrar en detalle de cómo se implementan los componentes de gestión de diccionarios y comunicación con el usuario.

Otro de los grandes principios de desarrollo de software... otro de los principios SOLID: Principio de Inversión de Dependencias (Dependency Inversion Principle). 
Este principio dice un componente de alto nivel (Por ejemplo nuestro ProcesadorDePeticiones) no debe depender de componentes de bajo nivel (Por ejemplo la implementación concreta del componente de gestión de diccionarios o la implementación concreta del componente de comunicación con el usuario). En su lugar debe depender de abstracciones/especificaciones (interfaces) de esos componentes.

Y cómo hacemos eso?
Hay un PATRON (una forma de escribir código) que nos ayuda a resolver el problema... o dicho qde otra forma, que nos ayuda a RESPETAR el principio de inversión de dependencias: PATRON DE INYECCIÓN DE DEPENDENCIAS (Dependency Injection Pattern).

Qué dice ese patrón?

Una clase NUNCA debe crear instancias de los objetos que necesita. En su lugar, debe recibir esas instancias desde fuera (inyectadas desde fuera).

Llevamos esto al ejemplo de un coche.

    COCHE MODELO XAS100 del fabricante ACME.
    
    Tenemos bastidor.
        ProcesadorDePeticiones
    Tenemos especificación de las ruedas
        gestion-diccionarios-api
    Tenemos especificación de la bateria
        comunicacion-usuario-api
    Tengo un fabricante (MICHELIN) que fabrica ruedas que cumplen con la especificación de ruedas del coche (implementaciones)
        gestion-diccionarios-impl-ficheros
    Tengo un fabricante (VARTA) que fabrica baterias que cumplen con la especificación de baterias del coche (implementaciones)
        comunicacion-usuario-impl-consola
    
    Tengo todo eso.
    Tengo coche? NO

    Un coche se monta con especificaciones? NO... se monta con componentes concretos (implementaciones).
    El coche que salga de fabrica tendrá que tomar decisiones de qué ruedas y qué batería montar. 

        COCHE MODELO XAS100 del fabricante ACME. Versión 1.
            Bastidor: ProcesadorDePeticiones
            Ruedas: gestion-diccionarios-impl-ficheros (MICHELIN)
            Batería: comunicacion-usuario-impl-consola (VARTA)
        
        El coche (PRODUCTO/SISTEMA/APLICACION) es un conjunto ensamblado de componentes concretos (implementaciones) que cumplen con las especificaciones de esos componentes.

    Wuizás el día de manaña sale la v2 del coche 
        COCHE MODELO XAS100 del fabricante ACME. Versión 2.
            Bastidor: ProcesadorDePeticiones
            Ruedas:   gestion-diccionarios-impl-bbdd (PIRELLI)
            Batería:  comunicacion-usuario-impl-aplicacion-desktop (DURACELL)


```java

public class Aplicacion {

    public static void main(String[] args) {
        SuministradorDeDiccionarios suministradorDeDiccionarios = new SuministradorDeDiccionariosEnFicheros("./diccionarios");
        ComunicadorConUsuario comunicadorConUsuario = new ComunicadorConUsuarioPorConsola();
        ProcesadorDePeticiones procesadorDePeticiones = new ProcesadorDePeticiones(suministradorDeDiccionarios, comunicadorConUsuario);
        procesadorDePeticiones.procesarPeticion();
    }

}
```

Esta clase SOLO ENSAMBLA! Solo crea INSTANCIAS DE COMPONENTES CONCRETAS Y LAS JUNTA! Y LAS PONE EN MARCHA!
Nada más.

Si el día de mañana cambio el SuministradorDeDiccionariosEnFicheros por un SuministradorDeDiccionariosEnBBDD el UNICO SITIO donde tengo que hacer el cambio es en esta clase. En la clase Aplicacion. En el resto de clases no hay que tocar nada.

Si el día de mañana cambio el ComunicadorConUsuarioPorConsola por un ComunicadorConUsuarioPorAplicacionDesktop el UNICO SITIO donde tengo que hacer el cambio es en esta clase. En la clase Aplicacion. En el resto de clases no hay que tocar nada.

Esto es un buen diseño de sistema.
Y esto nos permitirá IR EVOLUCIONANDO ESTE SISTEMA en el tiempo... CON MINIMOS cambios en el código.

ESPECIFICACION  = API
    En JAVA se define principalmente mediante INTERFACES
COMPONENTE      = IMPLEMENTACION
    En JAVA se define principalmente mediante CLASES que implementan esas interfaces.

Las interfaces SOLO DEFINEN METODOS. No definen el CUERPO DE LOS METODOS. NO TIENE CODIGO. SOLO FIRMA DE LOS METODOS.
Las clases DEFINEN EL CUERPO DE LOS METODOS. TIENEN CODIGO. 

Una especificación SIRVE PARA INDICAR QUE PUEDO ESPERAR DE UN COMPONENTE (qué tareas soporta, cómo me comunico con él)
Una implementación SIRVE PARA INDICAR CÓMO SE HACE LO QUE DICE LA ESPECIFICACIÓN (cómo se implementan esas tareas, cómo se hace lo que dice la especificación).

La RUEDA DE MI COCHE tiene una ESPECIFICACION: 215 R17 W
Eso me da las medidas... Que me aseguran que la rueda encaja en el coche y soporte la velocidad y el peso del coche.
Pero no me dice cómo se fabrica la rueda. Eso es un DETALLE DE IMPLEMENTACION. Ni el material, ni si los neumáticos son de invierno o verano.

La MICHELIN XJ89 es ya UNA RUEDA CONCRETA... una implementación de la especificación de rueda 215 R17 W.
Que se fabrica con unos materiales concretos... que es para invierno.

LA PIRELLI ZY99 es ya OTRA RUEDA CONCRETA... otra implementación de la especificación de rueda 215 R17 W.

El coche, ya se monta con INSTANCIAS. Datos concretos DE CLASES (IMPLEMENTACIONES)

- ESPECIFICACION = interfaz
                   SuministradorDeDiccionarios 

                    215 R17 W

- COMPONENTE = clase que implementa la interfaz
                   SuministradorDeDiccionariosEnFicheros

                    MICHELIN XJ89 (modelo de rueda... pero un modelo NO ES UNA RUEDA... es un modelo de rueda.)
                    
- INSTANCIA = objeto de la clase que implementa la interfaz
                   new SuministradorDeDiccionariosEnFicheros("./diccionarios")

                   El coche se monta con 4 RUEDAS de ese modelo (4 instancias de la MICHELIN XJ89) y 1 BATERIA de ese modelo (1 instancia de la VARTA YZ99)

