package ec.edu.ups.poo.controlador;

import ec.edu.ups.poo.dao.UsuarioDAO;
import ec.edu.ups.poo.modelo.Rol;
import ec.edu.ups.poo.vista.inicio.RegisterView;

import javax.swing.*;

public class RegisterController {

    private final UsuarioDAO usuarioDAO;
    private final RegisterView registerView;

    public RegisterController(UsuarioDAO usuarioDAO, RegisterView registerView) {
        this.usuarioDAO = usuarioDAO;
        this.registerView = registerView;

        registerView.getBtnRegister().addActionListener(e -> {
            String username = registerView.getLblUsername().getText().trim();
            String password = registerView.getLblPassword().getText().trim();

            if (username.isEmpty() || password.isEmpty()) {
                registerView.mostrarMensaje("Por favor, llena todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (usuarioDAO.buscarUsuario(username) != null) {
                registerView.mostrarMensaje("El usuario ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            usuarioDAO.crearUsuario(username, password, Rol.USUARIO);
            registerView.mostrarMensaje("Usuario registrado exitosamente.", "Registro", JOptionPane.INFORMATION_MESSAGE);
            registerView.dispose();
        });

        registerView.getBtnClean().addActionListener(e -> {
            registerView.getLblUsername().setText("");
            registerView.getLblPassword().setText("");
        });

        registerView.getBtnExit().addActionListener(e -> {
            registerView.dispose();
        });
    }
}