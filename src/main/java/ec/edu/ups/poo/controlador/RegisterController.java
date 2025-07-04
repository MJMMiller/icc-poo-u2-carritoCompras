package ec.edu.ups.poo.controlador;

import ec.edu.ups.poo.dao.UsuarioDAO;
import ec.edu.ups.poo.dao.PreguntaDAO;
import ec.edu.ups.poo.modelo.enums.Rol;
import ec.edu.ups.poo.modelo.Usuario;
import ec.edu.ups.poo.modelo.Pregunta;
import ec.edu.ups.poo.modelo.PreguntaUsuario;
import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.vista.inicio.RegisterView;

import javax.swing.*;
import java.util.*;

public class RegisterController {

    private final UsuarioDAO usuarioDAO;
    private final PreguntaDAO preguntaDAO;
    private final RegisterView registerView;
    private final List<Pregunta> preguntasRandom;
    private final MensajeInternacionalizacionHandler i18n;

    public RegisterController(
            UsuarioDAO usuarioDAO,
            PreguntaDAO preguntaDAO,
            RegisterView registerView,
            MensajeInternacionalizacionHandler i18n
    ) {
        this.usuarioDAO = usuarioDAO;
        this.preguntaDAO = preguntaDAO;
        this.registerView = registerView;
        this.i18n = i18n;

        preguntasRandom = getPreguntasRandom();
        mostrarPreguntasEnVista();
        limpiarCampos();

        configurarEventos();
    }

    private void configurarEventos() {
        configurarRegistro();
        configurarLimpieza();
        configurarSalir();
    }

    private void configurarRegistro() {
        registerView.getBtnRegistro().addActionListener(e -> registrarUsuario());
    }

    private void configurarLimpieza() {
        registerView.getBtnClean().addActionListener(e -> limpiarCampos());
    }

    private void configurarSalir() {
        registerView.getBtnSalir().addActionListener(e -> registerView.dispose());
    }

    private void registrarUsuario() {
        String username = registerView.getTxtUsuario().getText().trim();
        String password = registerView.getTxtContrasena().getText().trim();
        String nombreCompleto = registerView.getTxtNombreCompleto().getText().trim();
        Date fechaNacimiento = registerView.getFechaNacimiento();
        String correo = registerView.getTxtCorreo().getText().trim();
        String telefono = registerView.getTxtTelefono().getText().trim();
        String respuesta1 = registerView.getTxtPregunta1().getText().trim();
        String respuesta2 = registerView.getTxtPregunta2().getText().trim();
        String respuesta3 = registerView.getTxtPregunta3().getText().trim();

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

        Usuario usuario = new Usuario(username, password, Rol.USUARIO, nombreCompleto, fechaNacimiento, correo, telefono);

        List<PreguntaUsuario> preguntasUsuario = new ArrayList<>();
        preguntasUsuario.add(new PreguntaUsuario(preguntasRandom.get(0), respuesta1));
        preguntasUsuario.add(new PreguntaUsuario(preguntasRandom.get(1), respuesta2));
        preguntasUsuario.add(new PreguntaUsuario(preguntasRandom.get(2), respuesta3));
        usuario.setPreguntaValidacion(preguntasUsuario);

        usuarioDAO.crearUsuario(usuario);

        registerView.mostrarMensaje(
                i18n.get("register.exito.usuario_registrado"),
                i18n.get("global.success"),
                JOptionPane.INFORMATION_MESSAGE
        );
        System.out.println(usuario);
        registerView.dispose();
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
        List<Pregunta> lista = new ArrayList<>(preguntaDAO.listarTodas());
        Collections.shuffle(lista);
        return lista.subList(0, 3);
    }
}