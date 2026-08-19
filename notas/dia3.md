
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
