package ec.edu.ups.poo;

import ec.edu.ups.poo.controlador.*;
import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.vista.inicio.LogInView;
/*
 * Universidad Politécnica Salesiana
 * Carrera de Ingeniería en Sistemas Computacionales
 * Materia: Programación Orientada a Objetos
 * Proyecto: Sistema de Compras y Ventas
 *
 * Autor:
 * - Mateo Miller
 */
/**
 * Clase principal del sistema de compras y ventas.
 * Inicializa la internacionalización y la vista de login, y arranca la aplicación.
 */
public class Main {
    /**
     * Manejador de internacionalización de mensajes para la aplicación.
     */
    public static MensajeInternacionalizacionHandler i18n;

    /**
     * Método principal que inicia la aplicación.
     * Configura el idioma por defecto, crea la vista de login y su controlador, y muestra la interfaz.
     *
     * @param args Argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            i18n = new MensajeInternacionalizacionHandler("es", "EC");
            LogInView logInView = new LogInView(i18n);
            new LogInController(logInView, i18n);
            logInView.setVisible(true);
        });
    }
}