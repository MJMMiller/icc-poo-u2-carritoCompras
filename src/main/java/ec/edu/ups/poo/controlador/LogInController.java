package ec.edu.ups.poo.controlador;

import ec.edu.ups.poo.dao.UsuarioDAO;
import ec.edu.ups.poo.modelo.Usuario;
import ec.edu.ups.poo.vista.inicio.LogInView;
import ec.edu.ups.poo.vista.inicio.RegisterView;
import ec.edu.ups.poo.vista.preguntas.PreguntasValidacionView;
import ec.edu.ups.poo.vista.MenuPrincipalView;
import ec.edu.ups.poo.modelo.Rol;
import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;

import javax.swing.*;

public class LogInController {

    private final UsuarioDAO usuarioDAO;
    private final LogInView logInView;
    private final MensajeInternacionalizacionHandler i18n;
    private final MainAppCallback mainAppCallback;

    public interface MainAppCallback {
        void mostrarMenuPrincipal(Usuario usuarioAutenticado);
        void mostrarLogin();
    }

    public LogInController(UsuarioDAO usuarioDAO, LogInView logInView, MensajeInternacionalizacionHandler i18n, MainAppCallback callback) {
        this.usuarioDAO = usuarioDAO;
        this.logInView = logInView;
        this.i18n = i18n;
        this.mainAppCallback = callback;
        setupListeners();
    }

    private void setupListeners() {
        logInView.actualizarOpcionesIdioma();

        logInView.getCbxIdioma().addActionListener(e -> {
            int selectedIndex = logInView.getCbxIdioma().getSelectedIndex();
            switch (selectedIndex) {
                case 0:
                    i18n.setLenguaje("es", "EC");
                    break;
                case 1:
                    i18n.setLenguaje("en", "US");
                    break;
                case 2:
                    i18n.setLenguaje("fr", "FR");
                    break;
                default:
                    i18n.setLenguaje("en", "US");
            }
            logInView.aplicarIdioma();
            logInView.actualizarOpcionesIdioma();
        });

        logInView.getBtnLogIn().addActionListener(e -> {
            String user = logInView.getTxtUserName().getText();
            String pass = logInView.getTxtContrasena().getText();
            Usuario usuario = usuarioDAO.autenticarUsuario(user, pass);
            if (usuario == null) {
                logInView.mostrarMensaje("Usuario o contraseña incorrecta.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (usuario.getPreguntaValidacion() == null || usuario.getPreguntaValidacion().isEmpty()) {
                int res = logInView.mostrarMensajeAlert(
                        "Por favor, llene sus preguntas de validación.", "Atención", JOptionPane.WARNING_MESSAGE
                );
                if (res == 0) {
                    PreguntasValidacionView preguntasView = new PreguntasValidacionView(usuario, usuarioDAO);
                    new PreguntasValidacionController(usuario, usuarioDAO, preguntasView);
                    preguntasView.setVisible(true);
                    preguntasView.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosed(java.awt.event.WindowEvent evt) {
                            logInView.dispose();
                            mainAppCallback.mostrarLogin();
                        }
                    });
                }
                return;
            }

            mainAppCallback.mostrarMenuPrincipal(usuario);
            logInView.dispose();
        });

        logInView.getBtnRegister().addActionListener(e -> {
            RegisterView registerView = new RegisterView();
            new RegisterController(usuarioDAO, registerView);
            registerView.setVisible(true);
        });

        logInView.getBtnRecuContra().addActionListener(e -> {
            String username = logInView.getTxtUserName().getText().trim();
            if (username.isEmpty()) {
                logInView.mostrarMensaje("Ingrese su usuario en el campo correspondiente.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Usuario usuario = usuarioDAO.buscarUsuario(username);
            if (usuario == null) {
                logInView.mostrarMensaje("Usuario no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (usuario.getPreguntaValidacion() == null || usuario.getPreguntaValidacion().isEmpty()) {
                logInView.mostrarMensaje("El usuario no tiene preguntas de validación registradas.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            PreguntasValidacionView preguntasView = new PreguntasValidacionView(usuario, usuarioDAO);
            new PreguntasRecuperacionController(usuario, usuarioDAO, preguntasView);
            preguntasView.setVisible(true);
        });

        logInView.getBtnExit().addActionListener(e -> {
            System.exit(0);
        });
    }
}