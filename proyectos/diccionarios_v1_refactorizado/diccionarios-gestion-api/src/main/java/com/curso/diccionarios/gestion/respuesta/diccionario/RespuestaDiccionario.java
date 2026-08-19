package com.curso.diccionarios.gestion.respuesta.diccionario;

public sealed interface RespuestaDiccionario permits DiccionarioEncontrado, DiccionarioNoEncontrado, ErrorAlObtenerDiccionario {}



// sealed: JAVA 21
// Nos permite declarar una interfaz o clase que solo puede ser implementada o extendida por un conjunto específico de clases.

// En paralelo, dado que DiccionarioEncontrado, DiccionarioNoEncontrado y ErrorAlObtenerDiccionario
// Solamente transportan datos: SON INMUTABLES, en lugar de clases, podemos declararlas como RECORD (JAVA 15)
// Los records nos dan:
// - Una sintaxis más sencilla para declarar clases inmutables que solo transportan datos.
// - Una sintaxis más sencilla para verificar el tipo de un objeto (evitando el instanceof) y hacer un cast al tipo correcto.



// Que problema tiene esta implementación?
// Tal y como está escrita, ROMPE con otro de los principios SOLID de desarrollo de Software.
// Estamos pisoteando el principio de sustitución de Barbara Liskov (Liskov Substitution Principle).
// Si creamos el API de esta forma, El procesador de peticiones llamará a la función getDiccionario() 
// y esperará que le devuelvan un DiccionarioEncontrado, un DiccionarioNoEncontrado o un ErrorAlObtenerDiccionario.
// En base a lo que sea que devuelva se comportará de una forma u otra (mostrará una información u otra al usuario).
// Pero... que pasa si el día de mañana, alguien implementa un SuministradorDeDiccionarios 
// que devuelva otro objeto distinto de otra clase que implemente RespuestaDiccionario?
// Nada ganrantiza que una futura implementación de SuministradorDeDiccionarios deba devueler SOLO 
// un DiccionarioEncontrado, 
// un DiccionarioNoEncontrado 
// o un ErrorAlObtenerDiccionario.
// Podría crear algo como:
// public class DiccionarioEnProcesoDeCarga implements RespuestaDiccionario {}
// Con la mejor intención...
// Pero al hacerlo, El Procesador de peticiones no sabría como tratarlo, y se rompería el sistema.