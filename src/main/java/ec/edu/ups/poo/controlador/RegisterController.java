package ec.edu.ups.poo.controlador;

import ec.edu.ups.poo.dao.UsuarioDAO;
import ec.edu.ups.poo.modelo.Rol;
import ec.edu.ups.poo.modelo.Usuario;
import ec.edu.ups.poo.modelo.PreguntaUsuario;
import ec.edu.ups.poo.modelo.BancoPreguntaValidacion;
import ec.edu.ups.poo.vista.inicio.RegisterView;

import javax.swing.*;
import java.util.*;

public class RegisterController {

    private final UsuarioDAO usuarioDAO;
    private final RegisterView registerView;
    private final List<BancoPreguntaValidacion> preguntasRandom;

    public RegisterController(UsuarioDAO usuarioDAO, RegisterView registerView) {
        this.usuarioDAO = usuarioDAO;
        this.registerView = registerView;

        preguntasRandom = getPreguntasRandom();
        registerView.getLblPregunta1().setText(preguntasRandom.get(0).getPregunta());
        registerView.getLblPregunta2().setText(preguntasRandom.get(1).getPregunta());
        registerView.getLblPregunta3().setText(preguntasRandom.get(2).getPregunta());

        registerView.getTxtPregunta1().setText("");
        registerView.getTxtPregunta2().setText("");
        registerView.getTxtPregunta3().setText("");

        registerView.getBtnRegistro().addActionListener(e -> {
            String username = registerView.getTxtUsuario().getText();
            String password = registerView.getTxtContrasena().getText();
            String respuesta1 = registerView.getTxtPregunta1().getText();
            String respuesta2 = registerView.getTxtPregunta2().getText();
            String respuesta3 = registerView.getTxtPregunta3().getText();

            if (username.isEmpty() || password.isEmpty() ||
                    respuesta1.isEmpty() || respuesta2.isEmpty() || respuesta3.isEmpty()) {
                registerView.mostrarMensaje("Por favor, llena todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (usuarioDAO.buscarUsuario(username) != null) {
                registerView.mostrarMensaje("El usuario ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Usuario usuario = new Usuario(username, password, Rol.USUARIO);

            List<PreguntaUsuario> preguntasUsuario = new ArrayList<>();
            preguntasUsuario.add(new PreguntaUsuario(preguntasRandom.get(0), respuesta1));
            preguntasUsuario.add(new PreguntaUsuario(preguntasRandom.get(1), respuesta2));
            preguntasUsuario.add(new PreguntaUsuario(preguntasRandom.get(2), respuesta3));
            usuario.setPreguntaValidacion(preguntasUsuario);

            usuarioDAO.crearUsuario(usuario);

            registerView.mostrarMensaje("Usuario registrado exitosamente.", "Registro", JOptionPane.INFORMATION_MESSAGE);
            System.out.println(usuario);
            registerView.dispose();
        });

        registerView.getBtnClean().addActionListener(e -> {
            registerView.getTxtUsuario().setText("");
            registerView.getTxtContrasena().setText("");
            registerView.getTxtPregunta1().setText("");
            registerView.getTxtPregunta2().setText("");
            registerView.getTxtPregunta3().setText("");
        });

        registerView.getBtnSalir().addActionListener(e -> {
            registerView.dispose();
        });
    }

    private List<BancoPreguntaValidacion> getPreguntasRandom() {
        List<BancoPreguntaValidacion> lista = new ArrayList<>(Arrays.asList(BancoPreguntaValidacion.values()));
        Collections.shuffle(lista);
        return lista.subList(0, 3);
    }
}