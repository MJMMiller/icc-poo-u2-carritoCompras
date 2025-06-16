# Proyecto de Ejemplo - Carrito de Compras

## 📌 Información General

- **Título:** Patrones de Diseño Trabajados en Clase: MVC, DAO y Principios SOLID
- **Asignatura:** Programación Orientada a Objetos (POO)
- **Carrera:** Computación
- **Estudiante:** Mateo Miller
- **Fecha:** 16 de junio de 2025
- **Profesor:** Ing. Gabriel Leon

---

## 🛠️ Descripción

Este proyecto fue desarrollado como parte del período 66 de la asignatura Programación Orientada a Objetos en la Universidad Politécnica Salesiana.  
Su objetivo principal es demostrar el uso de patrones de diseño aplicados a una aplicación Java con interfaz gráfica construida con Swing.

El sistema simula un carrito de compras educativo, implementando buenas prácticas de diseño y una arquitectura desacoplada basada en los siguientes patrones y principios:

- **MVC (Modelo - Vista - Controlador):** Permite separar la lógica de negocio de la interfaz gráfica.
- **DAO (Data Access Object):** Desacopla el acceso a los datos, facilitando la migración a diferentes fuentes (archivos, bases de datos, etc.).
- **Principios SOLID (SRP, DIP):** Garantizan una arquitectura mantenible, extensible y fácil de testear.
- **POO:** El código hace uso de los pilares de la Programación Orientada a Objetos.

La estructura del proyecto se distribuye en paquetes: `modelo`, `dao`, `controlador`, `vista` y `servicio`.

---

## 🧰 Tecnologías Utilizadas

- 💻 **Java 21**
- 🧰 **IntelliJ IDEA** (recomendado con el plugin de diseñador gráfico de interfaces Swing)
- ☕ **Swing** para la interfaz gráfica
- 📦 **Estructura modular** basada en paquetes

---

## 🚀 Ejecución

Para ejecutar el proyecto:

1. Compila el código principal:
    ```bash
    javac Main.java
    ```
2. Ejecuta la aplicación:
    ```bash
    java Main
    ```

Se recomienda ejecutar el proyecto desde `Main.java` y utilizar IntelliJ IDEA para aprovechar el editor visual de formularios `.form`.  
Puedes probar con diferentes implementaciones del DAO para observar la flexibilidad del patrón.

---
