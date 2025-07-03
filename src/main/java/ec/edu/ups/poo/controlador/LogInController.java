package ec.edu.ups.poo.controlador;

import ec.edu.ups.poo.dao.UsuarioDAO;
import ec.edu.ups.poo.dao.PreguntaDAO;
import ec.edu.ups.poo.modelo.Usuario;
import ec.edu.ups.poo.vista.inicio.LogInView;
import ec.edu.ups.poo.vista.inicio.RegisterView;
import ec.edu.ups.poo.vista.preguntas.PreguntasValidacionView;
import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;

import javax.swing.*;

public class LogInController {

    private final UsuarioDAO usuarioDAO;
    private final PreguntaDAO preguntaDAO;
    private final LogInView logInView;
    private final MensajeInternacionalizacionHandler i18n;
    private final MainAppCallback mainAppCallback;

    public interface MainAppCallback {
        void mostrarMenuPrincipal(Usuario usuarioAutenticado);
        void mostrarLogin();
    }

    public LogInController(UsuarioDAO usuarioDAO, PreguntaDAO preguntaDAO, LogInView logInView, MensajeInternacionalizacionHandler i18n, MainAppCallback callback) {
        this.usuarioDAO = usuarioDAO;
        this.preguntaDAO = preguntaDAO;
        this.logInView = logInView;
        this.i18n = i18n;
        this.mainAppCallback = callback;
        configurarEventos();
    }

    private void configurarEventos() {
        configurarCambioIdioma();
        configurarLogin();
        configurarRegistro();
        configurarRecuperacion();
        configurarSalir();
    }

    private void configurarCambioIdioma() {
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
    }

    private void configurarLogin() {
        logInView.getBtnLogIn().addActionListener(e -> {
            String user = logInView.getTxtUserName().getText();
            String pass = logInView.getTxtContrasena().getText();
            Usuario usuario = usuarioDAO.autenticarUsuario(user, pass);
            if (usuario == null) {
                logInView.mostrarMensaje(
                        i18n.get("login.error.usuario_o_contrasena"),
                        i18n.get("global.error"),
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            if (usuario.getPreguntaValidacion() == null || usuario.getPreguntaValidacion().isEmpty()) {
                int res = logInView.mostrarMensajeAlert(
                        i18n.get("login.warning.llene_preguntas_validacion"),
                        i18n.get("global.warning"),
                        JOptionPane.WARNING_MESSAGE
                );
                logInView.dispose();
                if (res == 0) {
                    PreguntasValidacionView preguntasView = new PreguntasValidacionView(usuario, usuarioDAO, i18n);
                    new PreguntasValidacionController(usuario, usuarioDAO, preguntaDAO, preguntasView, i18n);
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
    }

    private void configurarRegistro() {
        logInView.getBtnRegister().addActionListener(e -> {
            RegisterView registerView = new RegisterView(i18n);
            new RegisterController(usuarioDAO, preguntaDAO, registerView, i18n);
            registerView.setVisible(true);
        });
    }

    private void configurarRecuperacion() {
        logInView.getBtnRecuContra().addActionListener(e -> {
            String username = logInView.getTxtUserName().getText().trim();
            if (username.isEmpty()) {
                logInView.mostrarMensaje(
                        i18n.get("login.warning.ingrese_usuario"),
                        i18n.get("global.warning"),
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            Usuario usuario = usuarioDAO.buscarUsuario(username);
            if (usuario == null) {
                logInView.mostrarMensaje(
                        i18n.get("login.error.usuario_no_encontrado"),
                        i18n.get("global.error"),
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            if (usuario.getPreguntaValidacion() == null || usuario.getPreguntaValidacion().isEmpty()) {
                logInView.mostrarMensaje(
                        i18n.get("login.error.usuario_sin_preguntas"),
                        i18n.get("global.error"),
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            PreguntasValidacionView preguntasView = new PreguntasValidacionView(usuario, usuarioDAO, i18n);
            new PreguntasRecuperacionController(usuario, usuarioDAO, preguntasView, i18n);
            preguntasView.setVisible(true);
        });
    }

    private void configurarSalir() {
        logInView.getBtnExit().addActionListener(e -> System.exit(0));
    }
}