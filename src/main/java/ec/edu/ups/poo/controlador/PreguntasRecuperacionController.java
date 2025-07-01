package ec.edu.ups.poo.controlador;

import ec.edu.ups.poo.dao.UsuarioDAO;
import ec.edu.ups.poo.modelo.Usuario;
import ec.edu.ups.poo.modelo.PreguntaUsuario;
import ec.edu.ups.poo.vista.preguntas.PreguntasValidacionView;
import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.List;

public class PreguntasRecuperacionController {

    private final Usuario usuario;
    private final UsuarioDAO usuarioDAO;
    private final PreguntasValidacionView preguntasView;
    private final MensajeInternacionalizacionHandler i18n;

    public PreguntasRecuperacionController(
            Usuario usuario,
            UsuarioDAO usuarioDAO,
            PreguntasValidacionView preguntasView,
            MensajeInternacionalizacionHandler i18n
    ) {
        this.usuario = usuario;
        this.usuarioDAO = usuarioDAO;
        this.preguntasView = preguntasView;
        this.i18n = i18n;

        inicializarVista();
        configurarEventos();
    }

    private void inicializarVista() {
        List<PreguntaUsuario> preguntasGuardadas = usuario.getPreguntaValidacion();

        preguntasView.getLblPregunta1().setText(i18n.get(preguntasGuardadas.get(0).getPregunta().getKey()));
        preguntasView.getLblPregunta2().setText(i18n.get(preguntasGuardadas.get(1).getPregunta().getKey()));
        preguntasView.getLblPregunta3().setText(i18n.get(preguntasGuardadas.get(2).getPregunta().getKey()));

        preguntasView.getTxtPregunta1().setText("");
        preguntasView.getTxtPregunta2().setText("");
        preguntasView.getTxtPregunta3().setText("");

        preguntasView.getLblNuevaContra().setVisible(false);
        preguntasView.getTxtNuevaContra().setVisible(false);
        preguntasView.getTxtNuevaContra().setEnabled(false);
    }

    private void configurarEventos() {
        preguntasView.getBtnEnviar().addActionListener(e -> procesarPreguntas());
    }

    private void procesarPreguntas() {
        List<PreguntaUsuario> preguntasGuardadas = usuario.getPreguntaValidacion();

        String respuesta1 = preguntasView.getTxtPregunta1().getText().trim();
        String respuesta2 = preguntasView.getTxtPregunta2().getText().trim();
        String respuesta3 = preguntasView.getTxtPregunta3().getText().trim();

        if (respuesta1.isEmpty() || respuesta2.isEmpty() || respuesta3.isEmpty()) {
            preguntasView.mostrarMensaje(
                    i18n.get("preguntas.recuperacion.error.responder_todas"),
                    i18n.get("global.error"),
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        boolean correcto =
                preguntasGuardadas.get(0).getRespuesta().equalsIgnoreCase(respuesta1) &&
                        preguntasGuardadas.get(1).getRespuesta().equalsIgnoreCase(respuesta2) &&
                        preguntasGuardadas.get(2).getRespuesta().equalsIgnoreCase(respuesta3);

        if (correcto) {
            mostrarCambioContrasena();
        } else {
            preguntasView.mostrarMensaje(
                    i18n.get("preguntas.recuperacion.error.respuesta_incorrecta"),
                    i18n.get("global.error"),
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void mostrarCambioContrasena() {
        preguntasView.getLblNuevaContra().setVisible(true);
        preguntasView.getLblNuevaContra().setEnabled(true);
        preguntasView.getTxtNuevaContra().setVisible(true);
        preguntasView.getTxtNuevaContra().setEnabled(true);
        preguntasView.getTxtNuevaContra().setEditable(true);

        preguntasView.getBtnEnviar().setText(i18n.get("preguntas.recuperacion.btn.cambiar_contrasena"));

        for (ActionListener al : preguntasView.getBtnEnviar().getActionListeners()) {
            preguntasView.getBtnEnviar().removeActionListener(al);
        }

        preguntasView.getBtnEnviar().addActionListener(ev -> cambiarContrasena());
    }

    private void cambiarContrasena() {
        String nuevaContrasena = preguntasView.getTxtNuevaContra().getText().trim();
        if (nuevaContrasena.isEmpty()) {
            preguntasView.mostrarMensaje(
                    i18n.get("preguntas.recuperacion.error.nueva_contrasena_vacia"),
                    i18n.get("global.warning"),
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        usuario.setContrasena(nuevaContrasena);
        usuarioDAO.actualizar(usuario.getUserName(), usuario.getContrasena(), usuario.getRol());
        preguntasView.mostrarMensaje(
                i18n.get("preguntas.recuperacion.exito.cambio_contrasena"),
                i18n.get("global.success"),
                JOptionPane.INFORMATION_MESSAGE
        );
        preguntasView.dispose();
    }
}