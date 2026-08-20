# Examen · Módulo 4 – Persistencia con JPA e Hibernate

**1.** ¿Qué anotación de JPA 3.1 se aplica sobre una clase Java para indicarle al proveedor de persistencia (Hibernate) que esa clase es una entidad mapeada a una tabla de base de datos?
- a) `@Table`
- **✓ b) `@Entity`**
- c) `@Mapped`

**2.** ¿Qué combinación de anotaciones JPA 3.1 se usa para declarar una clave primaria que se genera automáticamente por la base de datos (autoincrement)?
- a) `@PrimaryKey` + `@AutoGenerate`
- b) `@Key` + `@Sequence`
- **✓ c) `@Id` + `@GeneratedValue(strategy = GenerationType.IDENTITY)`**

**3.** En una relación JPA donde un Pedido contiene muchas Líneas de pedido, ¿qué anotación se coloca en la entidad Pedido sobre la colección de líneas?
- a) `@ManyToOne`
- **✓ b) `@OneToMany`**
- c) `@ManyToMany`

**4.** ¿Qué significa que una relación JPA sea LAZY (`fetch = FetchType.LAZY`)?
- **✓ a) Los datos relacionados se cargan solo cuando se accede a ellos por primera vez en el código.**
- b) Los datos relacionados se cargan en el mismo instante en que se carga la entidad principal.
- c) La relación no se guarda en base de datos.

**5.** ¿Qué es JPQL (Jakarta Persistence Query Language) y en qué se diferencia de SQL?
- a) Es un lenguaje que opera sobre tablas y columnas de la base de datos.
- **✓ b) Es un lenguaje de consulta orientado a entidades y atributos Java, independiente de la base de datos subyacente.**
- c) Es el mismo que HQL de Hibernate.

**6.** ¿Qué método de `EntityManager` se usa para persistir una nueva entidad (insertar un registro) en la base de datos?
- a) `save()`
- **✓ b) `persist()`**
- c) `insert()`

**7.** En WildFly 36, ¿qué gestiona las transacciones de JPA de forma automática cuando se usa la anotación `@Transactional`?
- a) El desarrollador debe llamar manualmente a `begin()` y `commit()`.
- b) JDBC gestiona las transacciones directamente.
- **✓ c) JTA (Jakarta Transaction API) gestionada por el contenedor WildFly.**

**8.** ¿Qué archivo de configuración define el nombre de la unidad de persistencia (`persistence-unit`) y el datasource JTA a usar en una aplicación Jakarta EE?
- a) `web.xml`
- **✓ b) `persistence.xml`**
- c) `beans.xml`

**9.** ¿Qué es el problema N+1 en JPA y cuándo suele ocurrir?
- **✓ a) Cuando se hacen más consultas de las necesarias: 1 consulta para la entidad raíz y N consultas adicionales para cada elemento relacionado.**
- b) Cuando se tienen más de N tablas en la base de datos.
- c) Cuando el `EntityManager` lanza N excepciones antes de confirmar.

**10.** Al implementar el patrón DAO con CDI en Jakarta EE 10, ¿cómo se obtiene el `EntityManager` gestionado por WildFly dentro del bean DAO?
- a) `EntityManagerFactory.createEntityManager()` manualmente.
- **✓ b) Inyectándolo con `@PersistenceContext`.**
- c) Creando una nueva instancia con `new EntityManager()`.
