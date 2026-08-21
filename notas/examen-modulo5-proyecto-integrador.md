# Examen · Módulo 5 – Proyecto integrador: JSP, JAX-RS y JPA en WildFly

**1.** En el proyecto integrador, ¿cuál es la responsabilidad de la capa DAO (Data Access Object) dentro de la arquitectura de la aplicación?
- **✓ a) Encapsular toda la lógica de acceso a datos (JPQL, `EntityManager`) y exponerla mediante métodos simples al resto de la aplicación.**
- b) Renderizar las páginas JSP y gestionar la sesión del usuario.
- c) Gestionar las peticiones HTTP entrantes y devolver respuestas JSON.

**2.** ¿Qué significa empaquetar una aplicación Jakarta EE como archivo WAR y cuál es la estructura mínima requerida?
- a) Web ARchive: un ZIP con el código fuente `.java` y los `.class` sueltos.
- **✓ b) Web ARchive: un archivo ZIP con `WEB-INF/classes`, `WEB-INF/lib`, `WEB-INF/web.xml` (opcional) y los recursos web (JSP, HTML, JS, CSS) en la raíz.**
- c) Web ARchive: equivalente a un JAR ejecutable con `main()`.

**3.** ¿Qué plugin de Maven permite desplegar el WAR directamente en un servidor WildFly en ejecución con un solo comando `mvn wildfly:deploy`?
- a) `maven-war-plugin`
- b) `tomcat7-maven-plugin`
- **✓ c) `wildfly-maven-plugin`**

**4.** ¿Qué es CORS y por qué es necesario configurarlo en WildFly cuando una página JSP hace llamadas AJAX `fetch()` a la API JAX-RS?
- a) Es un protocolo de cifrado necesario para HTTPS.
- **✓ b) Es una política de seguridad del navegador que bloquea peticiones a un origen distinto; hay que habilitarlo en el servidor para que el navegador permita las llamadas.**
- c) Es el sistema de caché de WildFly para respuestas REST.

**5.** En el proyecto integrador, una página JSP necesita mostrar una lista de productos obtenida desde la API REST JAX-RS. ¿Qué técnica se recomienda para consumir la API desde el JSP?
- a) Hacer una llamada directa al `EntityManager` desde el scriptlet JSP.
- b) Usar el patrón Singleton para compartir datos entre JSP y Servlet.
- **✓ c) Usar JavaScript `fetch()` o AJAX en el cliente para llamar al endpoint JAX-RS y renderizar el resultado dinámicamente.**

**6.** ¿Cuál es el antipatrón de rendimiento más frecuente en JPA cuando se muestran listas con datos relacionados en la capa de presentación?
- a) Usar `@ApplicationScoped` en el DAO.
- **✓ b) El problema N+1: cargar entidades en una consulta y disparar N consultas adicionales al acceder a relaciones LAZY en el bucle de renderizado.**
- c) Tener más de un `persistence.xml` en el proyecto.

**7.** ¿Qué ocurre si se intenta acceder a una colección LAZY de una entidad JPA fuera del contexto de persistencia activo (fuera de la transacción)?
- **✓ a) Se lanza una `LazyInitializationException` porque el `EntityManager` ya está cerrado.**
- b) Hibernate carga los datos automáticamente abriendo una nueva conexión.
- c) La colección se devuelve vacía sin lanzar excepción.

**8.** ¿Cómo se configura un datasource en WildFly 36 para que la aplicación JPA pueda conectarse a la base de datos?
- a) Añadiendo el driver JDBC en el `WEB-INF/lib` del WAR.
- **✓ b) Registrando el datasource en WildFly (consola o CLI) con el driver JDBC instalado como módulo, y referenciándolo en `persistence.xml` con su nombre JNDI.**
- c) Configurando la URL JDBC directamente en el `pom.xml`.

**9.** En el diseño del proyecto integrador, ¿por qué se separan la capa REST (JAX-RS) y la capa DAO en clases distintas en lugar de poner todo junto?
- a) Porque WildFly no permite clases con más de 100 líneas.
- b) Porque JPA no funciona si está en la misma clase que JAX-RS.
- **✓ c) Para aplicar el principio de responsabilidad única: el recurso JAX-RS gestiona HTTP y el DAO gestiona la persistencia, facilitando el mantenimiento y las pruebas de cada capa por separado**

**10.** Al finalizar el proyecto integrador, ¿cuál de las siguientes afirmaciones describe mejor lo que ofrece la combinación JSP (vista) + JAX-RS (API) + JPA (persistencia) desplegada en WildFly 36?
- a) Una aplicación de escritorio empaquetada como ejecutable Java.
- **✓ b) Una aplicación web empresarial CRUD completa, con una API REST consumible por el JSP (AJAX) o por clientes externos, y persistencia en base de datos gestionada por el contenedor.**
- c) Un microservicio sin servidor de aplicaciones, desplegado en la nube de forma serverless.
