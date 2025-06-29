package ec.edu.ups.poo.controlador;

import ec.edu.ups.poo.dao.UsuarioDAO;
import ec.edu.ups.poo.modelo.Rol;
import ec.edu.ups.poo.modelo.Usuario;
import ec.edu.ups.poo.vista.inicio.LogInView;
import ec.edu.ups.poo.vista.usuario.UsuarioAnadirView;
import ec.edu.ups.poo.vista.usuario.UsuarioEditarView;
import ec.edu.ups.poo.vista.usuario.UsuarioElimiarView;
import ec.edu.ups.poo.vista.usuario.UsuarioListarView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class UsuarioController {

    private Usuario usuario;
    private final UsuarioDAO usuarioDAO;
    private final LogInView loginView;

    public UsuarioController(UsuarioDAO usuarioDAO, LogInView loginView) {
        this.usuarioDAO = usuarioDAO;
        this.loginView = loginView;
        if (loginView != null) {
            configurarEventosEnVista();
        }
    }

    private void configurarEventosEnVista() {
        loginView.getBtnLogIn().addActionListener(e -> {
            autenticar();
        });
    }

    private void autenticar() {
        String username = loginView.getTxtUserName().getText();
        String contrasenia = new String(loginView.getTxtContrasena().getPassword());
        usuario = usuarioDAO.autenticarUsuario(username, contrasenia);
        if (usuario == null) {
            loginView.mostrarMensaje("Usuario o contraseña incorrectos.", "Error de autenticación", JOptionPane.ERROR_MESSAGE);
        } else {
            loginView.dispose();
            ec.edu.ups.poo.Main.mostrarMenuPrincipal(usuario);
        }
    }

    public void configurarUsuarioCrearView(UsuarioAnadirView usuarioAnadirView) {
        JComboBox cbxRol = usuarioAnadirView.getCbxRol();
        if (cbxRol.getItemCount() == 0) {
            for (Rol rol : Rol.values()) {
                cbxRol.addItem(rol);
            }
        }

        usuarioAnadirView.getBtnRegistrar().addActionListener(e -> {
            String username = usuarioAnadirView.getTxtUsuario().getText().trim();
            String password = usuarioAnadirView.getTxtContrasena().getText().trim();
            Rol rol = (Rol) usuarioAnadirView.getCbxRol().getSelectedItem();

            if (username.isEmpty() || password.isEmpty() || rol == null) {
                usuarioAnadirView.mostrarMensaje("Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (usuarioDAO.buscarUsuario(username) != null) {
                usuarioAnadirView.mostrarMensaje("El usuario ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Usuario nuevoUsuario = new Usuario(username, password, rol);
            usuarioDAO.crearUsuario(nuevoUsuario);

            usuarioAnadirView.mostrarMensaje("Usuario creado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            usuarioAnadirView.getTxtUsuario().setText("");
            usuarioAnadirView.getTxtContrasena().setText("");
            if (cbxRol.getItemCount() > 0) {
                cbxRol.setSelectedIndex(0);
            }
        });

        usuarioAnadirView.getBtnClean().addActionListener(e -> {
            usuarioAnadirView.getTxtUsuario().setText("");
            usuarioAnadirView.getTxtContrasena().setText("");
            if (cbxRol.getItemCount() > 0) {
                cbxRol.setSelectedIndex(0);
            }
        });
    }

    public void configurarUsuarioEditarView(UsuarioEditarView usuarioEditarView) {
        JComboBox cbxRol = usuarioEditarView.getCbxRol();
        if (cbxRol.getItemCount() == 0) {
            for (Rol rol : Rol.values()) {
                cbxRol.addItem(rol);
            }
        }

        if (usuario != null && usuario.getRol() == Rol.USUARIO) {
            cbxRol.setEnabled(false);
        } else {
            cbxRol.setEnabled(true);
        }

        usuarioEditarView.getTxtUsuario().setEditable(true);

        usuarioEditarView.getBtnBuscar().addActionListener(e -> {
            String usernameBuscar = usuarioEditarView.getTxtUsuario().getText().trim();
            if (usernameBuscar.isEmpty()) {
                usuarioEditarView.mostrarMensaje("Ingrese el nombre de usuario para buscar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Usuario usuarioEncontrado = usuarioDAO.buscarUsuario(usernameBuscar);
            if (usuarioEncontrado == null) {
                usuarioEditarView.mostrarMensaje("Usuario no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                usuarioEditarView.getTxtUsuario().setText("");
                usuarioEditarView.getTxtContrasena().setText("");
                if (cbxRol.getItemCount() > 0) {
                    cbxRol.setSelectedIndex(0);
                }
                usuarioEditarView.getTxtUsuario().setEditable(true);
            } else {
                usuarioEditarView.getTxtUsuario().setText(usuarioEncontrado.getUserName());
                usuarioEditarView.getTxtContrasena().setText(usuarioEncontrado.getContrasena());
                cbxRol.setSelectedItem(usuarioEncontrado.getRol());
                usuarioEditarView.getTxtUsuario().setEditable(false);
            }
        });

        usuarioEditarView.getBtnActualizar().addActionListener(e -> {
            String username = usuarioEditarView.getTxtUsuario().getText().trim();
            String password = usuarioEditarView.getTxtContrasena().getText().trim();
            Rol rol = (Rol) usuarioEditarView.getCbxRol().getSelectedItem();

            if (username.isEmpty() || password.isEmpty() || rol == null) {
                usuarioEditarView.mostrarMensaje("Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Usuario usuarioExistente = usuarioDAO.buscarUsuario(username);
            if (usuarioExistente == null) {
                usuarioEditarView.mostrarMensaje("El usuario no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            usuarioExistente.setContrasena(password);

            if (usuario != null && usuario.getRol() != Rol.USUARIO) {
                usuarioExistente.setRol(rol);
                usuarioDAO.actualizar(username, password, rol);
            } else {
                usuarioDAO.actualizar(username, password, usuarioExistente.getRol());
            }
            usuarioEditarView.mostrarMensaje("Usuario actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        });

        usuarioEditarView.getBtnClean().addActionListener(e -> {
            usuarioEditarView.getTxtUsuario().setText("");
            usuarioEditarView.getTxtContrasena().setText("");
            if (cbxRol.getItemCount() > 0) {
                cbxRol.setSelectedIndex(0);
            }
            usuarioEditarView.getTxtUsuario().setEditable(true);
        });
    }

    public void configurarUsuarioEliminarView(UsuarioElimiarView usuarioElimiarView) {
        usuarioElimiarView.getTxtUsuario().setEditable(true);
        usuarioElimiarView.getTxtContrasena().setEditable(false);
        usuarioElimiarView.getCbxRol().setEnabled(false);
        usuarioElimiarView.getBtnEliminar().setEnabled(false);

        usuarioElimiarView.getBtnBuscar().addActionListener(e -> {
            String username = usuarioElimiarView.getTxtUsuario().getText().trim();
            if (username.isEmpty()) {
                usuarioElimiarView.mostrarMensaje("Ingrese el nombre de usuario a buscar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Usuario usuarioEncontrado = usuarioDAO.buscarUsuario(username);
            if (usuarioEncontrado == null) {
                usuarioElimiarView.mostrarMensaje("Usuario no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                usuarioElimiarView.getTxtContrasena().setText("");
                usuarioElimiarView.getCbxRol().removeAllItems();
                usuarioElimiarView.getBtnEliminar().setEnabled(false);
            } else {
                usuarioElimiarView.getTxtContrasena().setText(usuarioEncontrado.getContrasena());
                usuarioElimiarView.getCbxRol().removeAllItems();
                usuarioElimiarView.getCbxRol().addItem(usuarioEncontrado.getRol());
                usuarioElimiarView.getTxtUsuario().setEditable(false);
                usuarioElimiarView.getTxtContrasena().setEditable(false);
                usuarioElimiarView.getCbxRol().setEnabled(false);
                usuarioElimiarView.getBtnEliminar().setEnabled(true);
            }
        });

        usuarioElimiarView.getBtnEliminar().addActionListener(e -> {
            String username = usuarioElimiarView.getTxtUsuario().getText().trim();
            if (username.isEmpty()) {
                usuarioElimiarView.mostrarMensaje("No hay usuario para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int confirmacion = usuarioElimiarView.mostrarMensajeConfirmacion(
                    "¿Está seguro que desea eliminar el usuario?", "Confirmar eliminación", JOptionPane.WARNING_MESSAGE);
            if (confirmacion == 0) {
                usuarioDAO.eliminarUsuario(username);
                usuarioElimiarView.mostrarMensaje("Usuario eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                usuarioElimiarView.getTxtUsuario().setText("");
                usuarioElimiarView.getTxtContrasena().setText("");
                usuarioElimiarView.getCbxRol().removeAllItems();
                usuarioElimiarView.getTxtUsuario().setEditable(true);
                usuarioElimiarView.getBtnEliminar().setEnabled(false);
            }
        });
    }

    public void configurarUsuarioListarView(UsuarioListarView usuarioListarView) {
        DefaultTableModel modelo = usuarioListarView.getModelo();
        Runnable llenarTabla = () -> {
            modelo.setRowCount(0);
            List<Usuario> usuarios = usuarioDAO.listarUsuariosTodos();
            for (Usuario usuario : usuarios) {
                modelo.addRow(new Object[]{
                        usuario.getUserName(),
                        usuario.getContrasena(),
                        usuario.getRol().name()
                });
            }
        };

        llenarTabla.run();
        usuarioListarView.getBtnListar().addActionListener(e -> llenarTabla.run());

        usuarioListarView.getBtnBuscar().addActionListener(e -> {
            String username = usuarioListarView.getTxtUsuario().getText().trim();
            Object rolSeleccionado = usuarioListarView.getCbxRol().getSelectedItem();
            usuarioListarView.getModelo().setRowCount(0);

            boolean buscarPorRol = rolSeleccionado != null && rolSeleccionado instanceof Rol;

            if (username.isEmpty()) {
                List<Usuario> usuarios;
                if (buscarPorRol) {
                    usuarios = usuarioDAO.buscarUsuariosPorRol((Rol) rolSeleccionado);
                } else {
                    usuarios = usuarioDAO.listarUsuariosTodos();
                }
                if (usuarios.isEmpty()) {
                    usuarioListarView.mostrarMensaje("No se encontraron usuarios.", "Información", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    for (Usuario usuario : usuarios) {
                        modelo.addRow(new Object[]{
                                usuario.getUserName(),
                                usuario.getContrasena(),
                                usuario.getRol().name()
                        });
                    }
                }
                usuarioListarView.getTxtUsuario().setText("");
                usuarioListarView.getCbxRol().setSelectedIndex(0);
            } else {
                Usuario usuarioEncontrado = usuarioDAO.buscarUsuario(username);
                if (usuarioEncontrado == null) {
                    usuarioListarView.mostrarMensaje("Usuario no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (buscarPorRol && !usuarioEncontrado.getRol().equals((Rol) rolSeleccionado)) {
                        usuarioListarView.mostrarMensaje("El usuario no tiene el rol seleccionado.", "Información", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        modelo.addRow(new Object[]{
                                usuarioEncontrado.getUserName(),
                                usuarioEncontrado.getContrasena(),
                                usuarioEncontrado.getRol().name()
                        });
                    }
                }
                usuarioListarView.getTxtUsuario().setText("");
                usuarioListarView.getCbxRol().setSelectedIndex(0);
            }
        });
    }
}