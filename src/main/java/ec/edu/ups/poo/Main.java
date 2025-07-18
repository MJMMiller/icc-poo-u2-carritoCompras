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
public class Main {
    public static MensajeInternacionalizacionHandler i18n;

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            i18n = new MensajeInternacionalizacionHandler("es", "EC");
            LogInView logInView = new LogInView(i18n);
            new LogInController(logInView, i18n);
            logInView.setVisible(true);
        });
    }
}