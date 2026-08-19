
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