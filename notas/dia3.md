
# Versión 1: 

        Local
    -------------------------------------------------------------------------------------------------------------
        Aplicación -> ProcesadorDePeticiones -> SuministradorDeDiccionarios -> Diccionarios <- (Impl a ficheros)
                                             -> ComunicadorConUsuario 

# Versión 2: 

        Local                                                               Servidor
    ------------------------------------------------------------------   -------------------------------------------------------------------
        Aplicación -> ProcesadorDePeticiones -----------------------------> SuministradorDeDiccionarios -> Diccionarios <- (Impl a ficheros)
                                             -> ComunicadorConUsuario                                             

Queremos pasar de una aplicación que se ejecuta completamente en local a una aplicación que se ejecute parte en un servidor y parte en local.
Es decir una arquitectura cliente-servidor (distribuida) en la que el cliente es la aplicación y el servidor es el suministrador de diccionarios.

Qué problema GRANDE tenemos es esto?
Problemas asociados a la comunicación!

Antes, el procesadorDePeticiones y el suministradorDeDiccionarios estaban en la misma máquina física y virtual (JVM), por lo que la comunicación entre ellos era muy rápida y confiable. Ahora, al estar separados, tenemos que lidiar con problemas de latencia, pérdida de paquetes, desconexiones, y la necesidad de manejar errores de comunicación.

Esto nos va a pasar ahora que vamos a meter un SuministradorDeDiccionariosRemotoRest... pero con que otra implementación de Suministrador nos podría haber pasado también?
La implementación a BBDD.
De hecho, incluso en local (al trabajar con ficheros) también podía pasar, ya que el acceso a ficheros puede ser lento y propenso a errores si no se maneja correctamente.
Y en código teníamos pruebas de ello... Y una guarrada... que medio resolvimos pero que dejó marca en el código.


---

NOTA: GRAN CRISIS DEL SOFTWARE

Ocurrió a finales de los años 60.
Después de 2 décadas creando software, el sistema COLAPSÓ. 
Tenía legiones de desarrolladores creando aplicaciones SIN CONTROL, SIN METODO, SIN PROCEDIMIENTO.
LLEGO UN MOMENTO QUE LOS SISTEMAS SE VOLVIERON INMANTENIBLES, INCOMPRENSIBLES, INESTABLES, INEFICIENTES, INSEGURAS, Y NO SE PODÍA GARANTIZAR SU FUNCIONAMIENTO.

De hecho esto dió origen a la INGENIERÍA DE SOFTWARE.
La Ingeniería de Software como disciplina tuvo su origen en la CRISIS DEL SOFTWARE como una respuesta a la necesidad de crear software de manera más controlada, metódica y profesional. Se buscaba establecer principios, metodologías y buenas prácticas para el desarrollo de software, con el objetivo de mejorar la calidad, la mantenibilidad y la eficiencia de los sistemas.

Gente como Edgar Dijkstra, Tom DeMarco, Barry Boehm, y otros contribuyeron a la formalización de la Ingeniería de Software, promoviendo enfoques estructurados, diseño modular, pruebas rigurosas y documentación adecuada.

---

# SOLID

Son 5 principios. Si los respeto (que no tengo por qué) me garantizan que mi código va a ser más mantenible, más comprensible, más estable, más eficiente y más seguro.

S = SRP = Single Responsibility Principle (Principio de Responsabilidad Única)
O = OCP = Open/Closed Principle (Principio de Abierto/Cerrado)
L = LSP = Liskov Substitution Principle (Principio de Sustitución de Liskov)
I = ISP = Interface Segregation Principle (Principio de Segregación de Interfaces)
D = DIP = Dependency Inversion Principle (Principio de Inversión de Dependencias)


## Principio de Sustituición de Liskov (LSP)

Un principio que nos dice que si tenemos una clase base y una clase derivada, la clase derivada debe poder sustituir a la clase base sin alterar el comportamiento esperado del programa. En otras palabras, los objetos de la clase derivada deben poder ser utilizados en lugar de los objetos de la clase base sin que el programa falle o se comporte de manera inesperada.


> Caso tipico de Liskov

```java
public class Rectangulo {

    private int ancho;
    private int alto;

    public Rectangulo(int ancho, int alto) {
        this.ancho = ancho;
        this.alto = alto;
    }

    public int getAncho() {
        return ancho;
    }
    public int getAlto() {
        return alto;
    }
    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }
    public int area() {
        return ancho * alto;
    }
    public int perimetro() {
        return 2 * (ancho + alto);
    }

}
public class Cuadrado extends Rectangulo {

    public Cuadrado(int lado) {
        super(lado, lado);
    }

    public void setAncho(int ancho) {
        super.setAncho(ancho);
        super.setAlto(ancho);// El problema es esta restriccion que acabamos de meter aqui! // Esto rompe Liskov
    }
    public void setAlto(int alto) {
        super.setAlto(alto);
        super.setAncho(alto);// El problema es esta restriccion que acabamos de meter aqui! // Esto rompe Liskov
    }

}


Rectangulo r = new Rectangulo(10, 10);
r.setAlto(10);
r.setAncho(20);

int area = r.area(); // 400


// Otro ejemplo de Liskov

public interface GeneradorDeAlgo {

    public Object generar();

}

// Esa función puede devolver nulo o no? NO LO HE CERRADO EN EL API.
// Podría tener una implementación que no genere nulos
// Y otra que si
// PROBLEMON!

public interface ConsumidorDeAlgo {

    public void consumir(@NonNull Object algo);

}

// Podría tener una implementación que no acepte nulos (Genere un NullPointerException) y otra que si los acepte.

// LAS IMPLEMENTACIONES SIEMPRE DEBEN GARANTIZAR EL CONTRATO DE LA INTERFAZ, NO PUEDEN ROMPERLO. SI LO ROMPEN, ROMPEN LISKOV.
// A veces lo pueden romper devolviendo cosas que no se esperan (Como nuestro caso de RespuestaPalabra si no pongo el sealed)
// A veces lo pueden romper aceptando cosas que no se esperan (Como nuestro caso de ConsumidorDeAlgo si no pongo el @NonNull)
// A veces es cambios internos que rompen el contrato (Como nuestro caso de Cuadrado que rompe Liskov con Rectangulo)

// Y aquí vienen muchos problemas luego.

// El objetivo es BLINDAR el contrato para que así las implementaciones no puedan romperlo. Y para eso tenemos el sealed y el @NonNull.
// La implementación DEFINE como se comporta, y el contrato DEFINE como se debe comportar y cómo comunicarse (datos de entrada y salida) con ella.
```


---

# Version 1
        Local
    -------------------------------------------------------------------------------------------------------------
        Aplicación -> ProcesadorDePeticiones => SuministradorDeDiccionariosDesdeFicheros -> DiccionarioDesdeFichero
                                             -> ComunicadorConUsuarioDesdeTerminal 


# Version 2
        Local
    -------------------------------------------------------------------------------------------------------------
        Aplicación -> ProcesadorDePeticiones -> ???
                                             -> ComunicadorConUsuarioDesdeTerminal 

        Servidor
    -------------------------------------------------------------------------------------------------------------
        Aplicación -> ?????                  -> SuministradorDeDiccionariosDesdeFicheros -> DiccionarioDesdeFichero


        => Es la que queremos transformar.. y que ahora no sea una comunicación local, sino remota vía HTTP REST.

                                                                        Proxy
                                                -------------------------------------------------------
                                                    Adaptador                                 Adaptador
                                                -----------------------------                 ---------
        Aplicación -> ProcesadorDePeticiones -> SuministradorDeDiccionario??? => HTTP/REST =>  ??? -> SuministradorDeDiccionariosDesdeFicheros -> DiccionarioDesdeFichero
                                               ¿Datos/JSON?

Patrón adaptador es un componente que permite que dos interfaces incompatibles trabajen juntas. Actúa como un puente entre ellas, traduciendo las llamadas de una interfaz a otra.
Por un lado tenemos una interfaz JAVA (SuministradorDeDiccionarios) y por otro lado tenemos una interfaz HTTP/REST (que es la que entiende el servidor). 
    El adaptador se encarga de traducir las llamadas de la interfaz JAVA a la interfaz HTTP/REST y viceversa.


Además en el lado del servidor también necesitamos un adaptador que haga la traducción de la interfaz HTTP/REST a la interfaz JAVA (SuministradorDeDiccionariosDesdeFicheros). 
    Este adaptador se encarga de recibir las peticiones HTTP/REST, traducirlas a llamadas a la interfaz JAVA y devolver las respuestas en formato HTTP/REST.

Proxy (Remoto) = Patrón de diseño que proporciona un sustituto (una puerta de acceso) para otro objeto para controlar el acceso a él. En este caso, el proxy actúa como un intermediario entre la aplicación y el suministrador de diccionarios remoto, gestionando la comunicación y las posibles fallas de red.
El proxy actua de intermediario para permitir la comunicación entre la aplicación y el suministrador de diccionarios remoto. Por ser remoto, y necesitar de protocolo HTTP/REST, el proxy lo implementamos mediante 2 adaptadores, uno en el lado del cliente y otro en el lado del servidor.

En un ejemplo posterior os enseñaré lo que propiamente es un patrón PROXY (SPRING -> AOP)

Necesitamos:
- Del lado del cliente: Una implementación de la interfaz SuministradorDeDiccionarios que haga peticiones a un servicio REST remoto.
- Del lado del servidor: Una implementación de un Controlador REST que reciba las peticiones, las traduzca a llamadas a la interfaz SuministradorDeDiccionariosDesdeFicheros y devuelva las respuestas en formato HTTP/REST.
- Además, ese controlador Rest en el servidor necesita ejecutarse dentro de una aplicación web / Servidor de aplicaciones.

> Por donde empezamos?
Tengo un cliente y un servidor.. que establecen COMUNICACION.
Lo primero que debo hacer es definir el API de esa comunicación (en ese caso un API REST).

En nuestro caso vamos a usar SpringBoot. Vamos a usar SpringBoot para definir el API REST en el servidor y para implementar el API (crear propiamente el controlador)


# Rutas HTTP que vamos a definir en el servidor

# Saber si tengo un idioma

    HEAD https://miservidor:PUERTO/v1/diccionario/{idioma}
    HEAD es como un GET pero sin BODY. Solo devuelve los HEADERS (StatusCode)
        Esa función devolverá:
        200 si el idioma está disponible
        404 (NOT_FOUND) si el idioma no está disponible 
        500 si hay un error en el servidor

# Saber si tengo una palabra en un idioma

    HEAD https://miservidor:PUERTO/v1/diccionario/{idioma}/{palabra}
    HEAD es como un GET pero sin BODY. Solo devuelve los HEADERS (StatusCode)
        Esa función devolverá:
        200 si la palabra está disponible en el idioma
        404 (NOT_FOUND) si la palabra o idioma no están disponibles
        500 si hay un error en el servidor

# Obtener los significados de una palabra en un idioma

    GET https://miservidor:PUERTO/v1/diccionario/{idioma}/{palabra}
        Esa función devolverá:
            200 si la palabra está disponible en el idioma
                JSON: Listado de significados de la palabra en el idioma
                    {
                        "idioma": {
                            "texto": "es",
                            "encontrado": true
                        }
                        "palabra": {
                            "texto": "casa",
                            "encontrado": true
                        },
                        "significados": [
                            "edificio para habitar",
                            "hogar",
                        ]
                    }
            404 (NOT_FOUND) si la palabra o idioma no están disponibles
                    {
                        "idioma": {
                            "texto": "es",
                            "encontrado": false
                        },
                        "palabra": {
                            "texto": "casa",
                            "encontrado": false
                        },
                    }
            500 si hay un error en el servidor
                    {
                        "idioma": {
                            "texto": "es"
                        },
                        "palabra": {
                            "texto": "casa"
                        },
                        "error": "Error interno del servidor"
                    }


---

HTTP: Protocolo para envío de contenidos sobre TCP/IP.

Es un protocolo de comunicación SINCRONO UNIDIRECCIONAL.
UNIDIRECCIONAL: La peticón siempre viene del cliente. 
SINCRONO: El cliente espera respuesta del servidor.

    Cliente -> HTTPRequest -> Servidor -> HTTPResponse -> Cliente

En la comunicación es como si mandamos una CAJA!
La caja puede o no tener contenido (BODY)
La caja tiene una pegatina por fuera llamada METADATOS (HEADERS)

Tanto en Rquest como en response se manda esa caja (rellena o no)... con más o menos metadatos (headers).

Hay 2 headers importantes:
- REQUEST: METHOD/VERB: GET, POST, PUT, DELETE, PATCH, OPTIONS, HEAD
- RESPONSE: STATUS CODE: 200, 201, 400, 404, 500, etc.
    2XX: Éxito
    3XX: Redirección
    4XX: Error del cliente
    5XX: Error del servidor 


En JAVA, lo primero será definir los objetos que vamos a mandar como JSON.
En nuestro caso 1 para todos los codigos de respuesta:
```java
public class RespuestaPalabra {

    private Idioma idioma;
    private Palabra palabra;
    private List<String> significados;
    private String error;

    // Getters y Setters
}

public class Idioma {

    private String texto;
    private Boolean encontrado;

}

public class Palabra {

    private String texto;
    private Boolean encontrado;

}
```

Estas clases posteriormente habrá que SERIALIZARLAS A JSON.
Eso lo regala Spring.
Spring transforma en automático los objetos JAVA a JSON y viceversa, siempre que tengamos las dependencias necesarias en el proyecto (como Jackson).

int, char, short, double, boolean no pueden tomar valor null.
No nos sirve usar boolean en encontrados, ya que si hay error del servidor, no queremos informar ese campo. Y ubn boolean, aunque no lo informe por defecto toma el valor false. Por eso usamos Boolean, que es un objeto y puede tomar valor null.

Estas clases son solo para transporte de datos (DTO = Data Transfer Object).
SON INMUTABLES! Me interesa usar clases? Un record... así por lo menos me quito del rollo de los getters y los setters... VAYA CAGADITA QUE TIENE JAVA CON ESO!

```java

public record RespuestaPalabra(
    Idioma idioma,
    Palabra palabra,
    List<String> significados,
    String error
) {}

public record Idioma(
    String texto,
    Boolean encontrado
) {}

public record Palabra(
    String texto,
    Boolean encontrado
) {}
```

---

Tenemos claro que los getters y setters son una MALA DECISION de JAVA?
Por qué digo que son una cagada?
Para qué sirven los setters y getters?
Para modificar u obtener los valores de un objeto? NO
Porque son el único sitio donde puedo meter lógica de negocio para modificar o transformar los valores de un objeto.

```java

public class Usuario {
    public String nombre;
    public int edad;
    public Usuario(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
    }
}

Usuario usuario = new Usuario("Juan", 30);
usuario.nombre = "Pedro"; // Modifico el nombre directamente
usuario.edad = 25; // Modifico la edad directamente
System.out.println(usuario.nombre); // Obtengo el nombre directamente
System.out.println(usuario.edad); // Obtengo la edad directamente

// Este código funciona? SI
// Es una mala práctica? SI
// POR QUÉ?
// Porque no se deben modificar directamente los valores = FALSO!
// De hecho, la alternativa recomendada en java es:

public class Usuario {
    private String nombre;
    private int edad;

    public Usuario(String nombre, int edad) {
        this.setNombre(nombre);
        this.setEdad(edad);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) { 
        this.nombre = nombre; // Modifico el valor directamente!
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
}

// El problema es otro.
// DIA 1
public class Usuario {
    public String nombre;
    public int edad;

    public Usuario(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
    }
}
// Dia 2-100
// Tengo un montón de compañeros escribiendo código como:

Usuario usuario = new Usuario("Juan", 30);
usuario.nombre = "Pedro"; // Modifico el nombre directamente
usuario.edad = 25; // Modifico la edad directamente
System.out.println(usuario.nombre); // Obtengo el nombre directamente
System.out.println(usuario.edad); // Obtengo la edad directamente

// Día 101... Digo yo... voy a meter una restricción al campo edad... que no pueda ser negativo.
// Dónde pongo eso en mi clase? NO HAY SITIO ALGUNO PARA PONERLO.
// En JAVA esa lógica SOLO SE PUEDE PONER DENTRO DE UNA FUNCION!
// Y me tocaría hacer algo como:

public class Usuario {
    public String nombre;
    private int edad;

    public Usuario(String nombre, int edad) {
        this.nombre = nombre;
        this.setEdad(edad);
    }
    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        if (edad < 0) {
            throw new IllegalArgumentException("La edad no puede ser negativa");
        }
        this.edad = edad;
    }
}
// Día 102, que tengo?
// Al montón de compañeros kalasnikov en mano buscandome por toda la empresa por haber roto su código!
// usuario.edad = 25; // Modifico la edad directamente Este código ya no compila!
// La buena práctica en JAVA de usar getters y setter es para FACILITAR EL MANTENIMIENTO DEL CODIGO A FUTURO!

// En cualquier otro lenguaje de programación esto se resulve de otras formas
```

```csharp
// C#
public class Usuario {
    public string Nombre { get; set; }
    public int Edad { get; set; }
    public Usuario(string nombre, int edad) {
        this.Nombre = nombre;
        this.Edad = edad;
    }
}
// Si quiero meter la restriccion
public class Usuario {
    public string Nombre { get; set; }
    private int edad;
    public int Edad {
        get { return edad; }
        set {
            if (value < 0) {
                throw new ArgumentException("La edad no puede ser negativa");
            }
            edad = value;
        }
    }
    public Usuario(string nombre, int edad) {
        this.Nombre = nombre;
        this.Edad = edad;
    }
}

Usuario usuario = new Usuario("Juan", 30);
usuario.Edad = 25; // Modifico la edad directamente // Y esto llama a la función set del property Edad
// Puedo hacer ese cambio sin joder a nadie
```

```python

# Python
class Usuario:
    def __init__(self, nombre, edad):
        self.nombre = nombre
        self.edad = edad

# Si quiero meter esa restriccion
class Usuario:
    def __init__(self, nombre, edad):
        self.nombre = nombre
        self._edad = edad
    
    @property
    def edad(self):
        return self._edad
    
    @edad.setter
    def edad(self, value):
        if value < 0:
            raise ValueError("La edad no puede ser negativa")
        self._edad = value
    
usuario = Usuario("Juan", 30)
usuario.edad = 25 # Modifico la edad directamente // Y esto llama a la función setter del property edad
# Puedo hacer ese cambio sin joder a nadie
```

En JS igualk:
```js
public class Usuario {
    constructor(nombre, edad) {
        this.nombre = nombre;
        this._edad = edad;
    }

    get edad() {
        return this._edad;
    }

    set edad(value) {
        if (value < 0) {
            throw new Error("La edad no puede ser negativa");
        }
        this._edad = value;
    }
}

usuario = new Usuario("Juan", 30);
usuario.edad = 25; // Modifico la edad directamente // Y esto llama a la función setter del property edad
// Puedo hacer ese cambio sin joder a nadie
```

En todos los lenguajes esta el concepto de properties, menos en JAVA.. y por eso en JAVA lidiamos con los getters y setter... y me dice:
OYE, LA BUENA PRACTICA ES PONER GETTERS Y SETTERS DESDE EL DIA 0.. aunque no pongas restricciones dentro... 
POR SI ACASO EL DIA DE MAÑANA LAS TIENES QUE PONER???? EIN??? EN SERIO???
