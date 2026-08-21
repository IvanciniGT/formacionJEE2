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
                                                    