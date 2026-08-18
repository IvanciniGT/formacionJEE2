
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