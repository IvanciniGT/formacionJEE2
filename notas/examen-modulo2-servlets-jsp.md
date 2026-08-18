# Examen · Módulo 2 – Servlets y Jakarta Pages (JSP)

**1.** ¿Cuál es la anotación de Jakarta EE 10 que permite registrar un Servlet sin necesidad de declararlo en el descriptor web.xml?
- **✓ a) `@WebServlet`**
- b) `@Controller`
- c) `@RestController`

**2.** ¿Qué métodos del ciclo de vida de un Servlet llama el contenedor para tratar las peticiones HTTP GET y POST respectivamente?
- a) `processGet()` y `processPost()`
- **✓ b) `doGet()` y `doPost()`**
- c) `handleGet()` y `handlePost()`

**3.** ¿Qué objeto de Jakarta Servlet 6.0 permite leer los parámetros enviados por un formulario HTML?
- a) `HttpServletResponse`
- b) `ServletContext`
- **✓ c) `HttpServletRequest`**

**4.** ¿Para qué sirve la anotación `@WebFilter` en Jakarta Servlet 6.0?
- a) Para validar formularios HTML automáticamente.
- **✓ b) Para interceptar peticiones y respuestas HTTP antes o después de que lleguen al Servlet destino.**
- c) Para gestionar la caché del navegador.

**5.** En una página JSP 3.1, ¿cómo se accede al valor de un atributo llamado `usuario` guardado en el request usando Expression Language (EL) 5.0?
- a) `<%= request.getAttribute("usuario") %>`
- **✓ b) `${usuario}`**
- c) `<%@ include usuario %>`

**6.** ¿Qué biblioteca de etiquetas estándar de Jakarta EE permite iterar una colección en una página JSP sin escribir código Java?
- **✓ a) JSTL con `<c:forEach>`**
- b) Una etiqueta `<loop>` de HTML5
- c) La directiva `<%@ page loop %>`

**7.** En el patrón MVC aplicado con Servlets y JSP, ¿qué elemento hace las veces de Controlador?
- a) La página JSP
- b) La base de datos
- **✓ c) El Servlet**

**8.** ¿Qué método de `RequestDispatcher` transfiere el control de un Servlet a una página JSP manteniendo la misma petición y respuesta HTTP?
- a) `sendRedirect()`
- **✓ b) `forward()`**
- c) `include()`

**9.** ¿Dónde se almacena la sesión del usuario en Jakarta Servlet 6.0 para que los datos persistan entre múltiples peticiones HTTP?
- a) En una cookie de sesión temporal en el navegador (solo el ID de sesión).
- **✓ b) En el objeto `HttpSession` gestionado por el servidor.**
- c) En parámetros ocultos en cada formulario HTML.

**10.** ¿Qué directiva JSP se usa para importar clases Java, de forma similar al `import` de Java?
- **✓ a) `<%@ page import="java.util.List" %>`**
- b) `<import class="java.util.List" />`
- c) `${import 'java.util.List'}`
