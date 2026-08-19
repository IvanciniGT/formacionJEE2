# Examen · Módulo 3 – APIs REST con JAX-RS y CDI

**1.** ¿Qué anotación de JAX-RS 3.1 indica que una clase Java es un recurso REST que responde a peticiones bajo una determinada ruta?
- a) `@RestController`
- **✓ b) `@Path`**
- c) `@Endpoint`

**2.** En JAX-RS 3.1, ¿qué anotación se usa para que un método maneje peticiones HTTP de tipo DELETE?
- a) `@Remove`
- **✓ b) `@DELETE`**
- c) `@HttpDelete`

**3.** ¿Qué tecnología incluida en WildFly 36 se encarga de serializar automáticamente los objetos Java a JSON y deserializar el JSON de entrada a objetos Java en un recurso JAX-RS?
- a) Jackson (incluido manualmente)
- **✓ b) Jakarta JSON Binding (JSON-B 3.0)**
- c) JAXB (XML Binding)

**4.** ¿Cómo se extrae el segmento variable `{id}` de la URL en un método de un recurso JAX-RS?
- a) Leyendo el `HttpServletRequest` manualmente.
- **✓ b) Con la anotación `@PathParam("id")` en el parámetro del método.**
- c) Con la anotación `@RequestParam("id")`.

**5.** ¿Para qué sirve la anotación `@Valid` de Jakarta Bean Validation 3.0 en un parámetro de un método JAX-RS?
- a) Para indicar que el parámetro es opcional.
- **✓ b) Para disparar la validación automática del objeto según las restricciones declaradas en su clase (como `@NotNull`, `@Size`).**
- c) Para convertir el JSON de entrada al formato XML.

**6.** ¿Qué interfaz de JAX-RS 3.1 permite personalizar la respuesta HTTP devuelta por un recurso REST, controlando el código de estado, cabeceras y cuerpo?
- a) `HttpServletResponse`
- **✓ b) `Response`**
- c) `ResponseEntity`

**7.** ¿Qué interfaz de JAX-RS 3.1 permite mapear excepciones Java a respuestas HTTP específicas de forma centralizada?
- **✓ a) `ExceptionMapper<E>`**
- b) `ErrorHandler`
- c) `ControllerAdvice`

**8.** En CDI 4.0 (disponible en WildFly 36), ¿qué scope se usa para que una instancia de un bean sea creada una sola vez y compartida por toda la aplicación?
- a) `@RequestScoped`
- b) `@SessionScoped`
- **✓ c) `@ApplicationScoped`**

**9.** ¿Cuál es la anotación de CDI 4.0 que se usa para inyectar una dependencia en un bean gestionado por el contenedor?
- a) `@Autowired`
- **✓ b) `@Inject`**
- c) `@Resource`

**10.** Al probar una API REST con curl, ¿qué opción permite enviar un cuerpo JSON en una petición POST?
- **✓ a) `curl -X POST http://localhost:8080/api/productos -d '{"nombre":"Teclado"}' -H 'Content-Type: application/json'`**
- b) `curl --json http://localhost:8080/api/productos`
- c) `curl -POST-JSON http://localhost:8080/api/productos`
