package ec.edu.ups.poo.controlador;

import ec.edu.ups.poo.dao.UsuarioDAO;
import ec.edu.ups.poo.modelo.Usuario;
import ec.edu.ups.poo.modelo.PreguntaUsuario;
import ec.edu.ups.poo.vista.preguntas.PreguntasValidacionView;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.List;

public class PreguntasRecuperacionController {

    private final Usuario usuario;
    private final UsuarioDAO usuarioDAO;
    private final PreguntasValidacionView preguntasView;

    public PreguntasRecuperacionController(Usuario usuario, UsuarioDAO usuarioDAO, PreguntasValidacionView preguntasView) {
        this.usuario = usuario;
        this.usuarioDAO = usuarioDAO;
        this.preguntasView = preguntasView;

        List<PreguntaUsuario> preguntasGuardadas = usuario.getPreguntaValidacion();

        preguntasView.getLblPregunta1().setText(preguntasGuardadas.get(0).getPregunta().getPregunta());
        preguntasView.getLblPregunta2().setText(preguntasGuardadas.get(1).getPregunta().getPregunta());
        preguntasView.getLblPregunta3().setText(preguntasGuardadas.get(2).getPregunta().getPregunta());

        preguntasView.getTxtPregunta1().setText("");
        preguntasView.getTxtPregunta2().setText("");
        preguntasView.getTxtPregunta3().setText("");

        preguntasView.getLblNuevaContra().setVisible(false);
        preguntasView.getTxtNuevaContra().setVisible(false);
        preguntasView.getTxtNuevaContra().setEnabled(false);

        preguntasView.getGuardarButton().addActionListener(e -> {
            String respuesta1 = preguntasView.getTxtPregunta1().getText().trim();
            String respuesta2 = preguntasView.getTxtPregunta2().getText().trim();
            String respuesta3 = preguntasView.getTxtPregunta3().getText().trim();

            if (respuesta1.isEmpty() || respuesta2.isEmpty() || respuesta3.isEmpty()) {
                preguntasView.mostrarMensaje("Por favor, responda todas las preguntas.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean correcto =
                    preguntasGuardadas.get(0).getRespuesta().equalsIgnoreCase(respuesta1) &&
                            preguntasGuardadas.get(1).getRespuesta().equalsIgnoreCase(respuesta2) &&
                            preguntasGuardadas.get(2).getRespuesta().equalsIgnoreCase(respuesta3);

            if (correcto) {
                preguntasView.getLblNuevaContra().setVisible(true);
                preguntasView.getLblNuevaContra().setEnabled(true);
                preguntasView.getTxtNuevaContra().setVisible(true);
                preguntasView.getTxtNuevaContra().setEnabled(true);
                preguntasView.getTxtNuevaContra().setEditable(true);

                preguntasView.getGuardarButton().setText("Cambiar contraseña");

                for (ActionListener al : preguntasView.getGuardarButton().getActionListeners()) {
                    preguntasView.getGuardarButton().removeActionListener(al);
                }

                preguntasView.getGuardarButton().addActionListener(ev -> {
                    String nuevaContrasena = preguntasView.getTxtNuevaContra().getText().trim();
                    if (nuevaContrasena.isEmpty()) {
                        preguntasView.mostrarMensaje("Por favor, ingrese una nueva contraseña.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    usuario.setContrasena(nuevaContrasena);
                    usuarioDAO.actualizar(usuario.getUserName(), usuario.getContrasena(), usuario.getRol());
                    preguntasView.mostrarMensaje("Contraseña cambiada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    preguntasView.dispose();
                });
            } else {
                preguntasView.mostrarMensaje("Al menos una respuesta es incorrecta.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}