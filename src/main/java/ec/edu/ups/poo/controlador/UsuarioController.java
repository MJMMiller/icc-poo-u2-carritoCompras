package ec.edu.ups.poo.controlador;

import ec.edu.ups.poo.Main;
import ec.edu.ups.poo.dao.UsuarioDAO;
import ec.edu.ups.poo.modelo.Rol;
import ec.edu.ups.poo.modelo.Usuario;
import ec.edu.ups.poo.vista.inicio.LogInView;
import ec.edu.ups.poo.vista.usuario.UsuarioCrearView;
import ec.edu.ups.poo.vista.usuario.UsuarioEditarView;
import ec.edu.ups.poo.vista.usuario.UsuarioElimiarView;
import ec.edu.ups.poo.vista.usuario.UsuarioListarView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class UsuarioController {

    private Usuario usuarioAutenticado;
    private final UsuarioDAO usuarioDAO;
    private final LogInView logInView;

    public UsuarioController(UsuarioDAO usuarioDAO, LogInView logInView) {
        this.usuarioDAO = usuarioDAO;
        this.logInView = logInView;
        if (logInView != null) {
            configurarEventosEnVista();
        }
    }

    private void configurarEventosEnVista(){
        logInView.getBtnLogIn().addActionListener(e -> {
            autenticarUsuario();
        });
    }

    private void autenticarUsuario() {
        String userName = logInView.getLblUserName().getText();
        String contrasena = logInView.getLblPassword().getText();

        usuarioAutenticado = usuarioDAO.autenticarUsuario(userName, contrasena);

        if (usuarioAutenticado != null) {
            logInView.dispose();
            logInView.mostrarMensaje("Welcome " + userName, "Success", JOptionPane.INFORMATION_MESSAGE);
            Main.mostrarMenuPrincipal(usuarioDAO, usuarioAutenticado);
        } else {
            logInView.mostrarMensaje("User or password incorrect", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Usuario getUsuarioAutenticado() {
        return usuarioAutenticado;
    }

    public void configurarUsuarioCrearView(UsuarioCrearView usuarioCrearView) {
        JComboBox cbxRol = usuarioCrearView.getCbxRol();
        if (cbxRol.getItemCount() == 0) {
            for (Rol rol : Rol.values()) {
                cbxRol.addItem(rol);
            }
        }

        usuarioCrearView.getBtnSave().addActionListener(e -> {
            String username = usuarioCrearView.getLblUsername().getText().trim();
            String password = usuarioCrearView.getLblPassword().getText().trim();
            Rol rol = (Rol) usuarioCrearView.getCbxRol().getSelectedItem();

            if (username.isEmpty() || password.isEmpty() || rol == null) {
                usuarioCrearView.mostrarMensaje("Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (usuarioDAO.buscarUsuario(username) != null) {
                usuarioCrearView.mostrarMensaje("El usuario ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            usuarioDAO.crearUsuario(username, password, rol);
            usuarioCrearView.mostrarMensaje("Usuario creado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            usuarioCrearView.getLblUsername().setText("");
            usuarioCrearView.getLblPassword().setText("");
            if (cbxRol.getItemCount() > 0) {
                cbxRol.setSelectedIndex(0);
            }
        });

        usuarioCrearView.getBtnClean().addActionListener(e -> {
            usuarioCrearView.getLblUsername().setText("");
            usuarioCrearView.getLblPassword().setText("");
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

        if (usuarioAutenticado != null && usuarioAutenticado.getRol() == Rol.USUARIO) {
            cbxRol.setEnabled(false);
        } else {
            cbxRol.setEnabled(true);
        }

        usuarioEditarView.getLblUsername().setEditable(true);

        usuarioEditarView.getBtnBuscar().addActionListener(e -> {
            String usernameBuscar = usuarioEditarView.getLblUsername().getText().trim();
            if (usernameBuscar.isEmpty()) {
                usuarioEditarView.mostrarMensaje("Ingrese el nombre de usuario para buscar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Usuario usuario = usuarioDAO.buscarUsuario(usernameBuscar);
            if (usuario == null) {
                usuarioEditarView.mostrarMensaje("Usuario no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                usuarioEditarView.getLblUsername().setText("");
                usuarioEditarView.getLblPassword().setText("");
                if (cbxRol.getItemCount() > 0) {
                    cbxRol.setSelectedIndex(0);
                }
                usuarioEditarView.getLblUsername().setEditable(true);
            } else {
                usuarioEditarView.getLblUsername().setText(usuario.getUserName());
                usuarioEditarView.getLblPassword().setText(usuario.getContrasena());
                cbxRol.setSelectedItem(usuario.getRol());
                usuarioEditarView.getLblUsername().setEditable(false);
            }
        });

        usuarioEditarView.getBtnSave().addActionListener(e -> {
            String username = usuarioEditarView.getLblUsername().getText().trim();
            String password = usuarioEditarView.getLblPassword().getText().trim();
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

            if (usuarioAutenticado != null && usuarioAutenticado.getRol() != Rol.USUARIO) {
                usuarioExistente.setRol(rol);
                usuarioDAO.actualizar(username, password, rol);
            } else {
                usuarioDAO.actualizar(username, password, usuarioExistente.getRol());
            }
            usuarioEditarView.mostrarMensaje("Usuario actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        });

        usuarioEditarView.getBtnClean().addActionListener(e -> {
            usuarioEditarView.getLblUsername().setText("");
            usuarioEditarView.getLblPassword().setText("");
            if (cbxRol.getItemCount() > 0) {
                cbxRol.setSelectedIndex(0);
            }
            usuarioEditarView.getLblUsername().setEditable(true);
        });
    }

    public void configurarUsuarioEliminarView(UsuarioElimiarView usuarioElimiarView) {
        usuarioElimiarView.getLblUsername().setEditable(true);
        usuarioElimiarView.getLblPassword().setEditable(false);
        usuarioElimiarView.getCbxRol().setEnabled(false);
        usuarioElimiarView.getDelateButton().setEnabled(false);

        usuarioElimiarView.getBtnSearch().addActionListener(e -> {
            String username = usuarioElimiarView.getLblUsername().getText().trim();
            if (username.isEmpty()) {
                usuarioElimiarView.mostrarMensaje("Ingrese el nombre de usuario a buscar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Usuario usuario = usuarioDAO.buscarUsuario(username);
            if (usuario == null) {
                usuarioElimiarView.mostrarMensaje("Usuario no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                usuarioElimiarView.getLblPassword().setText("");
                usuarioElimiarView.getCbxRol().removeAllItems();
                usuarioElimiarView.getDelateButton().setEnabled(false);
            } else {
                usuarioElimiarView.getLblPassword().setText(usuario.getContrasena());
                usuarioElimiarView.getCbxRol().removeAllItems();
                usuarioElimiarView.getCbxRol().addItem(usuario.getRol());
                usuarioElimiarView.getLblUsername().setEditable(false);
                usuarioElimiarView.getLblPassword().setEditable(false);
                usuarioElimiarView.getCbxRol().setEnabled(false);
                usuarioElimiarView.getDelateButton().setEnabled(true);
            }
        });

        usuarioElimiarView.getDelateButton().addActionListener(e -> {
            String username = usuarioElimiarView.getLblUsername().getText().trim();
            if (username.isEmpty()) {
                usuarioElimiarView.mostrarMensaje("No hay usuario para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int confirmacion = usuarioElimiarView.mostrarMensajeDelate(
                    "¿Está seguro que desea eliminar el usuario?", "Confirmar eliminación", JOptionPane.WARNING_MESSAGE);
            if (confirmacion == 0) {
                usuarioDAO.eliminarUsuario(username);
                usuarioElimiarView.mostrarMensaje("Usuario eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                usuarioElimiarView.getLblUsername().setText("");
                usuarioElimiarView.getLblPassword().setText("");
                usuarioElimiarView.getCbxRol().removeAllItems();
                usuarioElimiarView.getLblUsername().setEditable(true);
                usuarioElimiarView.getDelateButton().setEnabled(false);
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

        usuarioListarView.getBtnSearch().addActionListener(e -> {
            String username = usuarioListarView.getLblNameSearch().getText().trim();
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
                usuarioListarView.getLblNameSearch().setText("");
                usuarioListarView.getCbxRol().setSelectedIndex(0);
            } else {
                Usuario usuario = usuarioDAO.buscarUsuario(username);
                if (usuario == null) {
                    usuarioListarView.mostrarMensaje("Usuario no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (buscarPorRol && !usuario.getRol().equals((Rol) rolSeleccionado)) {
                        usuarioListarView.mostrarMensaje("El usuario no tiene el rol seleccionado.", "Información", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        modelo.addRow(new Object[]{
                                usuario.getUserName(),
                                usuario.getContrasena(),
                                usuario.getRol().name()
                        });
                    }
                }
                usuarioListarView.getLblNameSearch().setText("");
                usuarioListarView.getCbxRol().setSelectedIndex(0);
            }
        });
    }
}