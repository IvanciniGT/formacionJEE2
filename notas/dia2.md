
# Crear una estructura inicial de carpetas para trabajar

- En vuestro PC creais una carpeta para el curso:
  c:/Usuarios/MiUsuario/Escritorio/CursoJEE


En una terminal:
    java -version               
        En principio el instalador de JAVA Debe configurar el PATH apuntando a JAVA/bin
        Si tengo otras versiones de JAVA puede salir aqui una version anterior. En este caso: EDITAR EL PATH y poner la ruta de la version correcta de JAVA al principio del PATH.
    mvn -version
        Descomprimir en algún sitio
        Añadir la ruta al PATH (Variables de sistema). LA CARPETA QUE AÑADIMOS ES LA CARPETA BIN
        Al ejecutar mvn-version puede salir una version incorrecta de JAVA, a pesar de que java -version saque una buena versión
        Eso ocurre porque haya otra variable definida llamada JAVA_HOME que apunta a otra versión de JAVA. 
        En este caso, editar la variable JAVA_HOME y poner la ruta de la version correcta de JAVA.

# Programación funcional

Es un paradigma de programación. Paradigma de programación es un nombre HORTERA que los desarrolladores ponemos a las formas en las que usamos un lenguaje para expresar un concepto. No es algo propio de los lenguajes de programaicón. En los lenguajes naturales también hay paradoigmas... no los llamamos así.

    Felipe, Pon una silla debajo de la ventana!                                 IMPERATIVA
    Felipe, Debajo de la ventana debe haber una silla. Es tu responsabilidad.   DECLARATIVO

Paradigmas en programación:
- Imperativo                Cuando el lenguaje me permite dar instrucciones que deben procesarse secuencialmente.
                            en ocasiones nos interesa romper la secuencialidad y entran los modificadores de flujo tipicos de programación imperativa: WHILE, FOR, IF, SWITCH, BREAK, CONTINUE, RETURN 
- Procedural                Cuando el lenguaje me permite AGRUPAR bajo un nombre una secuencia de instrucciones y ejecutarla
                            posteriormente usando ese nombre. (Funciones, procedimientos, subrutinas, métodos, etc)
                            Que ventajas aporta:
                            - Mejorar la estructura / mantenibilidad / legibilidad del código
                            - Reutilización de código
- Orientado a Objetos       Todo lenguaje permite manejar/manipular datos... Y viene con una serie de tipos de datos por defecto.
                            Hay lenguajes que me permiten definir mis propios tipos de datos, con sus caracteristicas y operaciones partiiculares.

                            Tipo de datos       Se caracteriza por:                    Operaciones
                            String              Una secuencia de caracteres            .toUpperCase(), .toLowerCase(), .length()
                            LocalDate           Día, Mes, Año                          .plusDays(), .minusDays(), .isBefore()
                            List<T>             Una colección de elementos de tipo T   .add(), .remove(), .get(), .size()

                            Y si quiero tener el concepto de Usuario / ProcesadorDePeticiones / Diccionario, etc... puedo definir mis propios tipos de datos y sus operaciones.

                            Usuario             nombre, apellidos, email...             .login(), .logout(), .cambiarEmail()

                            Luego hay conceptos más avanzados: Herencia, Polimorfismo, Interfaces, Clases abstractas, etc... que permiten crear jerarquías de tipos de datos y operaciones.
                            En JAVA no tenemos Herencia múltiple, tampoco alternancia de tipos.
- Funcional                 La usamos mucho. Se incluye en Java en la versión 1.8.
                            El concepto es muy simple.
                            Cuando el lenguaje me permite que una variable apunte a una función para posteriormente invocar la función desde la variable decimos que el lenguaje soporta programación funcional.
                            Y el tema no es lo que es la programaicón funcional...
                            Es lo que puedo hacer una vez que el lenguaje soporta esto:
                            - Crear funciones que reciben como parámetro otras funciones
                            - Crear funciones que devuelven otras funciones (Closures)

                            cuando tenemos programación funcional, creamos funciones para:
                            - Mejorar la estructura / mantenibilidad / legibilidad del código
                            - Reutilización de código
                            - Por el artículo 33. A veces TENGO LA NECESIDAD DE CREAR UNA FUNCION PORQUE QUIERO LLAMAR A OTRA FUNCION QUE ME OBLIGA A PASAR UNA FUNCION.

- Declarativo               Cada día lo usamos más. No digo a la computadora lo que debe hacer, sino lo que quiero tener.
                            Adoramos la programación DECLARATIVA. En java se puede usar? POR SUPUESTO: LAS ANOTACIONES SON UNA FORMA DE PROGRAMACIÓN DECLARATIVA. Spring/SpringBoot es un ejemplo de programación declarativa.
- ...