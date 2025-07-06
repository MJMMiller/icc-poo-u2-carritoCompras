package ec.edu.ups.poo.controlador;

import ec.edu.ups.poo.dao.CarritoDAO;
import ec.edu.ups.poo.dao.ProductoDAO;
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
    private final ProductoDAO productoDAO;
    private final CarritoDAO carritoDAO;
    private final LogInView logInView;
    private final MensajeInternacionalizacionHandler i18n;

    public LogInController(UsuarioDAO usuarioDAO, PreguntaDAO preguntaDAO, ProductoDAO productoDAO, CarritoDAO carritoDAO, LogInView logInView, MensajeInternacionalizacionHandler i18n) {
        this.usuarioDAO = usuarioDAO;
        this.preguntaDAO = preguntaDAO;
        this.productoDAO = productoDAO;
        this.carritoDAO = carritoDAO;
        this.logInView = logInView;
        this.i18n = i18n;
        configurarEventos();
        logInView.aplicarIdioma();
    }

    private void configurarEventos() {
        logInView.getBtnExit().addActionListener(e -> configurarSalir());
        logInView.getBtnRecuContra().addActionListener(e -> configurarRecuperacion());
        logInView.getBtnRegister().addActionListener(e -> configurarRegistro());
        logInView.getBtnLogIn().addActionListener(e -> configurarLogin());
        logInView.getCbxIdioma().addActionListener(e -> cambioDeIdiomaDesdeCbx());
    }

    private void cambioDeIdiomaDesdeCbx() {
        int selectedIndex = logInView.getCbxIdioma().getSelectedIndex();
        switch (selectedIndex) {
            case 0: i18n.setLenguaje("es", "EC"); break;
            case 1: i18n.setLenguaje("en", "US"); break;
            case 2: i18n.setLenguaje("fr", "FR"); break;
            default: i18n.setLenguaje("es", "EC");
        }
        logInView.aplicarIdioma();
    }

    private void configurarLogin() {
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
                new PreguntasValidacionController(usuario, usuarioDAO, preguntaDAO, productoDAO, carritoDAO, preguntasView, i18n);
                preguntasView.setVisible(true);
            }
            return;
        }

        new MenuPrincipalController(usuario, usuarioDAO, preguntaDAO, productoDAO, carritoDAO, i18n);
        logInView.dispose();
    }

    private void configurarRegistro() {
        RegisterView registerView = new RegisterView(i18n);
        new RegisterController(usuarioDAO, preguntaDAO, productoDAO, carritoDAO, registerView, i18n);
        registerView.setVisible(true);
        logInView.dispose();
    }

    private void configurarRecuperacion() {
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
        new PreguntasRecuperacionController(usuario, usuarioDAO, preguntaDAO, productoDAO, carritoDAO, preguntasView, i18n);
        preguntasView.setVisible(true);
        logInView.dispose();
    }

    private void configurarSalir() {
        int respuesta = logInView.mostrarMensajeAlert("\n\t" +
                        i18n.get("mensaje.confirmacion.salir"),
                i18n.get("global.info"),
                JOptionPane.QUESTION_MESSAGE
        );
        if (respuesta == JOptionPane.OK_OPTION || respuesta == 0) {
            System.out.println(i18n.get("login.gracias.salir"));
            System.exit(0);
        }
    }
}