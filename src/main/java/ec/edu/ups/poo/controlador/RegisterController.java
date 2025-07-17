package ec.edu.ups.poo.controlador;

import ec.edu.ups.poo.dao.CarritoDAO;
import ec.edu.ups.poo.dao.ProductoDAO;
import ec.edu.ups.poo.dao.UsuarioDAO;
import ec.edu.ups.poo.dao.PreguntaDAO;
import ec.edu.ups.poo.modelo.Rol;
import ec.edu.ups.poo.modelo.Usuario;
import ec.edu.ups.poo.modelo.Pregunta;
import ec.edu.ups.poo.modelo.PreguntaUsuario;
import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.vista.inicio.RegisterView;
import ec.edu.ups.poo.vista.inicio.LogInView;

import javax.swing.*;
import java.util.*;

public class RegisterController {
    private final UsuarioDAO usuarioDAO;
    private final PreguntaDAO preguntaDAO;
    private final ProductoDAO productoDAO;
    private final RegisterView registerView;
    private final CarritoDAO carritoDAO;
    private final List<Pregunta> preguntasRandom;
    private final MensajeInternacionalizacionHandler i18n;

    public RegisterController(
            UsuarioDAO usuarioDAO,
            PreguntaDAO preguntaDAO,
            ProductoDAO productoDAO,
            CarritoDAO carritoDAO,
            RegisterView registerView,
            MensajeInternacionalizacionHandler i18n
    ) {
        // Validar DAOs no nulos
        if (usuarioDAO == null) throw new IllegalArgumentException("usuarioDAO no puede ser nulo");
        if (preguntaDAO == null) throw new IllegalArgumentException("preguntaDAO no puede ser nulo");
        if (productoDAO == null) throw new IllegalArgumentException("productoDAO no puede ser nulo");
        if (carritoDAO == null) throw new IllegalArgumentException("carritoDAO no puede ser nulo");
        if (registerView == null) throw new IllegalArgumentException("registerView no puede ser nulo");
        if (i18n == null) throw new IllegalArgumentException("i18n no puede ser nulo");

        this.usuarioDAO = usuarioDAO;
        this.preguntaDAO = preguntaDAO;
        this.productoDAO = productoDAO;
        this.carritoDAO = carritoDAO;
        this.registerView = registerView;
        this.i18n = i18n;

        this.preguntasRandom = getPreguntasRandom();
        configurarEventos();
        mostrarPreguntasEnVista();
        registerView.aplicarIdioma();
    }

    private void configurarEventos() {
        registerView.getBtnRegistro().addActionListener(e -> registrarUsuario());
        registerView.getBtnClean().addActionListener(e -> limpiarCampos());
        registerView.getBtnSalir().addActionListener(e -> salir());
        registerView.getCbxIdioma().addActionListener(e -> cambioDeIdiomaDesdeCbx());
    }

    private void salir() {
        registerView.dispose();
        abrirLogin();
    }

    private void registrarUsuario() {
        System.out.println("Clase real de usuarioDAO: " + usuarioDAO.getClass().getName());
        System.out.println("Botón registro presionado...");
        String username = registerView.getTxtUsuario().getText().trim();
        String password = registerView.getTxtContrasena().getText().trim();
        String nombreCompleto = registerView.getTxtNombreCompleto().getText().trim();
        Date fechaNacimiento = registerView.getFechaNacimiento();
        String correo = registerView.getTxtCorreo().getText().trim();
        String telefono = registerView.getTxtTelefono().getText().trim();
        String respuesta1 = registerView.getTxtPregunta1().getText().trim();
        String respuesta2 = registerView.getTxtPregunta2().getText().trim();
        String respuesta3 = registerView.getTxtPregunta3().getText().trim();

        // Validaciones
        if (camposVacios(username, password, respuesta1, respuesta2, respuesta3)) {
            registerView.mostrarMensaje(
                    i18n.get("register.error.llenar_todos"),
                    i18n.get("global.error"),
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        if (!correo.contains("@")) {
            registerView.mostrarMensaje(
                    i18n.get("register.error.correo"),
                    i18n.get("global.error"),
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        if (!telefono.matches("\\d{10}")) {
            registerView.mostrarMensaje(
                    i18n.get("register.error.telefono"),
                    i18n.get("global.error"),
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        if (usuarioDAO.buscarUsuario(username) != null) {
            registerView.mostrarMensaje(
                    i18n.get("register.error.usuario_existe"),
                    i18n.get("global.error"),
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        try {
            Usuario usuario = new Usuario(username, password, Rol.USUARIO, nombreCompleto, fechaNacimiento, correo, telefono);

            List<PreguntaUsuario> preguntasUsuario = new ArrayList<>();
            preguntasUsuario.add(new PreguntaUsuario(preguntasRandom.get(0), respuesta1));
            preguntasUsuario.add(new PreguntaUsuario(preguntasRandom.get(1), respuesta2));
            preguntasUsuario.add(new PreguntaUsuario(preguntasRandom.get(2), respuesta3));
            usuario.setPreguntaValidacion(preguntasUsuario);

            System.out.println("Llamando a crearUsuario en el DAO...");
            usuarioDAO.crearUsuario(usuario);

            registerView.mostrarMensaje(
                    i18n.get("register.exito.usuario_registrado"),
                    i18n.get("global.success"),
                    JOptionPane.INFORMATION_MESSAGE
            );
            registerView.dispose();
            abrirLogin();
        } catch (Exception ex) {
            registerView.mostrarMensaje(ex.getMessage(), i18n.get("global.error"), JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarPreguntasEnVista() {
        registerView.getLblPregunta1().setText(i18n.get(preguntasRandom.get(0).getTexto()));
        registerView.getLblPregunta2().setText(i18n.get(preguntasRandom.get(1).getTexto()));
        registerView.getLblPregunta3().setText(i18n.get(preguntasRandom.get(2).getTexto()));
    }

    private void limpiarCampos() {
        registerView.getTxtUsuario().setText("");
        registerView.getTxtContrasena().setText("");
        registerView.getTxtPregunta1().setText("");
        registerView.getTxtPregunta2().setText("");
        registerView.getTxtPregunta3().setText("");
        registerView.getTxtNombreCompleto().setText("");
        registerView.getCbxDia().setSelectedIndex(0);
        registerView.getCbxMes().setSelectedIndex(0);
        registerView.getCbxAnio().setSelectedIndex(0);
        registerView.getTxtCorreo().setText("");
        registerView.getTxtTelefono().setText("");
    }

    private boolean camposVacios(String... campos) {
        for (String campo : campos) {
            if (campo == null || campo.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private List<Pregunta> getPreguntasRandom() {
        List<Pregunta> lista = preguntaDAO.listarTodas();
        if (lista.size() < 3) throw new IllegalStateException("No hay suficientes preguntas para el registro");
        Collections.shuffle(lista);
        return lista.subList(0, 3);
    }

    private void cambioDeIdiomaDesdeCbx() {
        int selectedIndex = registerView.getCbxIdioma().getSelectedIndex();
        switch (selectedIndex) {
            case 0: i18n.setLenguaje("es", "EC"); break;
            case 1: i18n.setLenguaje("en", "US"); break;
            case 2: i18n.setLenguaje("fr", "FR"); break;
            default: i18n.setLenguaje("es", "EC");
        }
        registerView.aplicarIdioma();
        mostrarPreguntasEnVista();
    }

    private void abrirLogin() {
        SwingUtilities.invokeLater(() -> {
            LogInView logInView = new LogInView(i18n);
            new LogInController(usuarioDAO, preguntaDAO, productoDAO, carritoDAO, logInView, i18n);
            logInView.setVisible(true);
        });
    }
}