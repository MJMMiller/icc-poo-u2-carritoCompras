# 🛒 Carrito de Compras Empresarial/Educativo

---

**Proyecto desarrollado como parte del período 66 de la asignatura Programación Orientada a Objetos en la Universidad Politécnica Salesiana.**  
Su objetivo es demostrar el uso de patrones de diseño aplicados en una aplicación Java con interfaz gráfica (Swing), orientada tanto a un contexto educativo como empresarial para la gestión de inventario, usuarios y ventas.

---

## 📄 Datos del Proyecto

- **Asignatura:** Programación Orientada a Objetos  
- **Periodo:** 66  
- **Docente:** Ing. Gabriel Leon  
- **Estudiante:** Mateo Miller
- **Fecha de entrega:** 6 de julio de 2025  

---

## 🎯 Objetivo del Proyecto

Implementar un sistema educativo y empresarial que simula un **carrito de compras**, aplicando buenas prácticas de diseño y una arquitectura desacoplada basada en **MVC**, **DAO** y los principios de **POO** y **SOLID**.  
Permite la gestión completa de inventario, usuarios, ventas, autenticación y recuperación segura de contraseñas, con soporte multi lenguaje e interfaz gráfica personalizable.

---

## 🛠️ Tecnologías Utilizadas

- 💻 **Java 8+** (desarrollado y probado en Java 21)
- 🧰 **IntelliJ IDEA** (recomendado con plugin de diseñador gráfico de interfaces Swing)
- ☕ **Swing** para la interfaz gráfica, con personalización avanzada `JDesktopPane`
- 🗃️ **DAO en memoria** para persistencia (no requiere base de datos)
- 📦 **Estructura modular** basada en paquetes:
    - modelo
    - dao (e impl/)
    - controlador
    - vista (y sub paquetes)
    - util (y enums)
- 🌍 **Internacionalización:** Español, Inglés y Francés (archivos `.properties`)
- 🧱 **Patrones de Diseño aplicados:**
    - **MVC** (Modelo - Vista - Controlador)
    - **DAO** (Data Access Object)
    - **SRP** y **DIP** de los principios **SOLID**

---

## 📐 Diagrama UML

Este es un **resumen visual** del diagrama UML del sistema:

![Diagrama UML resumen](https://raw.githubusercontent.com/MJMMiller/POO_DIAGRAMS/1b21859d1ef3fbaedf51e9777d6fdc821a8c11ce/uml_CarritoCompras.jpg)

📄 **Diagrama completo en PDF**:  

Puedes consultar el diagrama UML detallado desde el siguiente enlace:  
[🔗 Ver diagrama completo (PDF)](https://github.com/MJMMiller/icc-poo-u2-carritoCompras/blob/f37371cb7a213a6dc447594ee30aedb38c670e34/src/main/resources/uml/uml_carritoCompras.pdf)


---

## 📦 Estructura del Proyecto

```
src/
 └─ main/
     ├─ java/
     │   └─ ec/edu/ups/poo/
     │        ├─ controlador/     # Lógica de negocio y controladores principales
     │        ├─ dao/             # Interfaces DAO para abstracción de datos
     │        │    └─ impl/       # Implementaciones en memoria de los DAO
     │        ├─ modelo/          # Clases de dominio: Producto, Carrito, Usuario, etc.
     │        │    └─ enums/      # Enumeraciones de dominio (roles, tipos)
     │        ├─ util/            # Utilidades y helpers
     │        │    └─ enums/      # Enumeraciones utilitarias (iconos)
     │        └─ vista/           # Vistas y formularios Swing
     │             ├─ carrito/
     │             ├─ inicio/
     │             ├─ preguntas/
     │             ├─ producto/
     │             └─ usuario/
     └─ resources/
         ├─ imagenes/             # Recursos iconos.png
         ├─ mensajes_es_EC.properties   # Español
         ├─ mensajes_en_US.properties   # Inglés
         └─ mensajes_fr_FR.properties   # Francés
```

---

## 📦 Paquetes y Funciones Principales

### 1️⃣ modelo (Modelos de Dominio)
- **Carrito:** Gestiona productos agregados, fecha de creación, usuario asociado.  
  Métodos: `agregarProducto()`, `eliminarProducto()`, `getCodigo()`, `getFechaCreacion()`, etc.
- **ItemCarrito:** Representa un producto y su cantidad en el carrito.
- **Producto:** Información de productos (código, nombre, precio, stock).
- **Usuario:** Datos de usuario (nombre, correo, contraseña, rol).
- **Rol:** Enumera los roles posibles de usuario.
- **Pregunta, PreguntaUsuario:** Para funcionalidades de seguridad o recuperación.

### 2️⃣ dao (Acceso a Datos)
- **CarritoDAO, ProductoDAO, UsuarioDAO, PreguntaDAO:** Interfaces para operaciones CRUD.
  Métodos: `crear()`, `buscarPorCodigo()`, `actualizar()`, `eliminar()`, `listarTodos()`.
- **impl/**: Implementaciones en memoria de los DAOs (ej: `CarritoDAOMemoria`).

### 3️⃣ controlador (Controladores)
- **CarritoController, ProductoController, UsuarioController, LogInController:** Gestionan la lógica de interacción entre modelo y vista.
  Métodos: manejar eventos de creación, modificación, eliminación, listado, login, recuperación, etc.

### 4️⃣ vista (Vistas Swing)
- **Sub paquetes:** carrito, producto, usuario, inicio, preguntas
- **Clases:** Formularios y ventanas para crear, modificar, eliminar, listar entidades.
  Ejemplo: `CarritoAnadirView`, `ProductoListarView`, `UsuarioAnadirView`, `LogInView`, etc.

### 5️⃣ util (Utilidades)
- **FormateadorUtils:** Métodos de formateo de datos.
- **Mensaje Internacionalización Handler:** Soporte para internacionalización y cambio de idioma.
- **TipoIcono, Url:** Gestión de iconos y rutas de recursos.

---

## 🧩 Funcionalidades Principales

- 👤 **Gestión de usuarios:** Registro, inicio de sesión, recuperación/cambio de contraseña (por preguntas de seguridad), CRUD completo.
- 🛒 **Gestión de productos:** CRUD completo, stock en tiempo real, administración desde interfaz.
- 🛍️ **Gestión de carrito de compras:** Añadir, editar, eliminar productos; ver y modificar el carrito.
- 🛡️ **Seguridad:** Autenticación, recuperación de contraseña segura mediante preguntas de validación.
- 🌐 **Internacionalización:** Cambia entre Español 🇪🇸, Inglés 🇺🇸 y Francés 🇫🇷 desde el menú.
- 🎨 **Interfaz gráfica personalizable:** Ventanas internas modernas, iconos y temas corporativos.
- 🏷️ **Datos quemados:** Productos y usuarios iniciales están en los DAO en memoria (ideal para pruebas y prototipos).
- 🔒 **Roles de usuario:** Soporte para roles, permisos y operaciones diferenciadas.

---

## 🚀 Instalación y Ejecución

### Requisitos Previos

- **Java JDK 8** o superior instalado y configurado en tu PATH
- (Opcional) **IntelliJ IDEA** para desarrollo, depuración y edición visual

### Compilación

1. Abre una terminal y navega al directorio raíz del proyecto.
2. Ejecuta:
   ```bash
   javac -d out -cp src src/main/java/ec/edu/ups/poo/Main.java
   ```
   Esto compilará todos los archivos fuente en la carpeta `out`.

### Ejecución

1. Desde la terminal, ejecuta:
   ```bash
   java -cp out ec.edu.ups.poo.Main
   ```
2. El sistema iniciará con la ventana principal. Puedes iniciar sesión, registrar un usuario nuevo, gestionar productos y carritos, y cambiar el idioma desde las opciones del menú.

---

## 🌍 Internacionalización

- Los textos de la aplicación se encuentran en:
    - `mensajes_es_EC.properties` 🇪🇸
    - `mensajes_en_US.properties` 🇺🇸
    - `mensajes_fr_FR.properties` 🇫🇷
- Agregar un nuevo idioma es tan fácil como crear un nuevo archivo `.properties` con las claves adecuadas.
- El idioma se puede cambiar dinámicamente desde el menú de la interfaz.

---

## 📝 Ejemplo de Uso

1. Ejecuta el sistema.
2. Selecciona el idioma deseado desde el menú.
3. Registra un nuevo usuario o inicia sesión con uno existente.
4. Agrega productos al carrito, edítalos, elimínalos o finaliza la compra.
5. Si olvidas tu contraseña, utiliza la opción de recuperación y responde a tus preguntas de validación.
6. Administra productos y usuarios desde las opciones correspondientes si tienes los permisos necesarios.

---

## 📚 Recomendaciones

- Ejecutar el proyecto desde `Main.java`.
- Usar **IntelliJ IDEA** para aprovechar el editor visual de formularios `.form`.
- Probar con diferentes implementaciones de DAO para observar la flexibilidad del patrón.
- Personalizar la interfaz cambiando iconos en la carpeta `imagenes`.
- Extender las funcionalidades para experimentar con patrones de diseño y arquitectura desacoplada.

---

## 👨‍💻 Autor

- **Mateo Miller**
- GitHub: [@MJMMiller](https://github.com/MJMMiller)

---
