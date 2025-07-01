package ec.edu.ups.poo.controlador;

import ec.edu.ups.poo.Main;
import ec.edu.ups.poo.dao.UsuarioDAO;
import ec.edu.ups.poo.modelo.Rol;
import ec.edu.ups.poo.modelo.Usuario;
import ec.edu.ups.poo.vista.inicio.LogInView;
import ec.edu.ups.poo.vista.usuario.UsuarioAnadirView;
import ec.edu.ups.poo.vista.usuario.UsuarioEditarView;
import ec.edu.ups.poo.vista.usuario.UsuarioElimiarView;
import ec.edu.ups.poo.vista.usuario.UsuarioListarView;
import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class UsuarioController {

    private Usuario usuario;
    private final UsuarioDAO usuarioDAO;
    private final LogInView loginView;
    private final MensajeInternacionalizacionHandler i18n;

    public UsuarioController(UsuarioDAO usuarioDAO, LogInView loginView, MensajeInternacionalizacionHandler i18n) {
        this.usuarioDAO = usuarioDAO;
        this.loginView = loginView;
        this.i18n = i18n;
        if (loginView != null) {
            configurarEventosLogin();
        }
    }

    private void configurarEventosLogin() {
        loginView.getBtnLogIn().addActionListener(e -> autenticarUsuario());
    }

    private void autenticarUsuario() {
        String username = loginView.getTxtUserName().getText();
        String contrasenia = new String(loginView.getTxtContrasena().getPassword());
        usuario = usuarioDAO.autenticarUsuario(username, contrasenia);
        if (usuario == null) {
            loginView.mostrarMensaje(
                    i18n.get("usuario.error.autenticacion"),
                    i18n.get("global.error"),
                    JOptionPane.ERROR_MESSAGE
            );
        } else {
            loginView.dispose();
            Main.mostrarMenuPrincipal(usuario);
        }
    }

    public void configurarUsuarioCrearView(UsuarioAnadirView usuarioAnadirView) {
        JComboBox cbxRol = usuarioAnadirView.getCbxRol();
        llenarComboRoles(cbxRol);

        usuarioAnadirView.getBtnRegistrar().addActionListener(e -> {
            String username = usuarioAnadirView.getTxtUsuario().getText().trim();
            String password = usuarioAnadirView.getTxtContrasena().getText().trim();
            Rol rol = (Rol) usuarioAnadirView.getCbxRol().getSelectedItem();

            if (camposVacios(username, password, rol)) {
                usuarioAnadirView.mostrarMensaje(
                        i18n.get("usuario.error.campos_obligatorios"),
                        i18n.get("global.error"),
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            if (usuarioDAO.buscarUsuario(username) != null) {
                usuarioAnadirView.mostrarMensaje(
                        i18n.get("usuario.error.existe"),
                        i18n.get("global.error"),
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            Usuario nuevoUsuario = new Usuario(username, password, rol);
            usuarioDAO.crearUsuario(nuevoUsuario);

            usuarioAnadirView.mostrarMensaje(
                    i18n.get("usuario.exito.creado"),
                    i18n.get("global.success"),
                    JOptionPane.INFORMATION_MESSAGE
            );

            limpiarCamposCrear(usuarioAnadirView, cbxRol);
        });

        usuarioAnadirView.getBtnClean().addActionListener(e -> limpiarCamposCrear(usuarioAnadirView, cbxRol));
    }

    public void configurarUsuarioEditarView(UsuarioEditarView usuarioEditarView) {
        JComboBox cbxRol = usuarioEditarView.getCbxRol();
        llenarComboRoles(cbxRol);

        cbxRol.setEnabled(!(usuario != null && usuario.getRol() == Rol.USUARIO));
        usuarioEditarView.getTxtUsuario().setEditable(true);

        usuarioEditarView.getBtnBuscar().addActionListener(e -> {
            String usernameBuscar = usuarioEditarView.getTxtUsuario().getText().trim();
            if (usernameBuscar.isEmpty()) {
                usuarioEditarView.mostrarMensaje(
                        i18n.get("usuario.error.ingrese_usuario_buscar"),
                        i18n.get("global.error"),
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            Usuario usuarioEncontrado = usuarioDAO.buscarUsuario(usernameBuscar);
            if (usuarioEncontrado == null) {
                usuarioEditarView.mostrarMensaje(
                        i18n.get("usuario.error.no_encontrado"),
                        i18n.get("global.error"),
                        JOptionPane.ERROR_MESSAGE
                );
                limpiarCamposEditar(usuarioEditarView, cbxRol);
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

            if (camposVacios(username, password, rol)) {
                usuarioEditarView.mostrarMensaje(
                        i18n.get("usuario.error.campos_obligatorios"),
                        i18n.get("global.error"),
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            Usuario usuarioExistente = usuarioDAO.buscarUsuario(username);
            if (usuarioExistente == null) {
                usuarioEditarView.mostrarMensaje(
                        i18n.get("usuario.error.no_existe"),
                        i18n.get("global.error"),
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            usuarioExistente.setContrasena(password);

            if (usuario != null && usuario.getRol() != Rol.USUARIO) {
                usuarioExistente.setRol(rol);
                usuarioDAO.actualizar(username, password, rol);
            } else {
                usuarioDAO.actualizar(username, password, usuarioExistente.getRol());
            }
            usuarioEditarView.mostrarMensaje(
                    i18n.get("usuario.exito.actualizado"),
                    i18n.get("global.success"),
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        usuarioEditarView.getBtnClean().addActionListener(e -> limpiarCamposEditar(usuarioEditarView, cbxRol));
    }

    public void configurarUsuarioEliminarView(UsuarioElimiarView usuarioElimiarView) {

        usuarioElimiarView.getBtnBuscar().addActionListener(e -> {
            String username = usuarioElimiarView.getTxtUsuario().getText().trim();
            if (username.isEmpty()) {
                usuarioElimiarView.mostrarMensaje(
                        i18n.get("usuario.error.ingrese_usuario_eliminar"),
                        i18n.get("global.error"),
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            Usuario usuarioEncontrado = usuarioDAO.buscarUsuario(username);
            if (usuarioEncontrado == null) {
                usuarioElimiarView.mostrarMensaje(
                        i18n.get("usuario.error.no_encontrado"),
                        i18n.get("global.error"),
                        JOptionPane.ERROR_MESSAGE
                );
                usuarioElimiarView.getTxtContrasena().setText("");
                usuarioElimiarView.getTxtRol().setText("");
                usuarioElimiarView.getBtnEliminar().setEnabled(false);
            } else {
                usuarioElimiarView.getTxtContrasena().setText(usuarioEncontrado.getContrasena());
                usuarioElimiarView.getTxtRol().setText(usuarioEncontrado.getRol().toString());
                usuarioElimiarView.getTxtUsuario().setEditable(false);
                usuarioElimiarView.getBtnEliminar().setEnabled(true);
            }
        });

        usuarioElimiarView.getBtnEliminar().addActionListener(e -> {
            String username = usuarioElimiarView.getTxtUsuario().getText().trim();
            if (username.isEmpty()) {
                usuarioElimiarView.mostrarMensaje(
                        i18n.get("usuario.error.no_usuario_eliminar"),
                        i18n.get("global.error"),
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            int confirmacion = usuarioElimiarView.mostrarMensajeConfirmacion(
                    i18n.get("usuario.confirmacion.eliminar"),
                    i18n.get("global.confirm"),
                    JOptionPane.WARNING_MESSAGE
            );
            if (confirmacion == 0) {
                usuarioDAO.eliminarUsuario(username);
                usuarioElimiarView.mostrarMensaje(
                        i18n.get("usuario.exito.eliminado"),
                        i18n.get("global.success"),
                        JOptionPane.INFORMATION_MESSAGE
                );
                usuarioElimiarView.getTxtUsuario().setText("");
                usuarioElimiarView.getTxtContrasena().setText("");
                usuarioElimiarView.getTxtRol().setText("");
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
                    usuarioListarView.mostrarMensaje(
                            i18n.get("usuario.info.no_hay_usuarios"),
                            i18n.get("global.info"),
                            JOptionPane.INFORMATION_MESSAGE
                    );
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
                    usuarioListarView.mostrarMensaje(
                            i18n.get("usuario.error.no_encontrado"),
                            i18n.get("global.error"),
                            JOptionPane.ERROR_MESSAGE
                    );
                } else {
                    if (buscarPorRol && !usuarioEncontrado.getRol().equals((Rol) rolSeleccionado)) {
                        usuarioListarView.mostrarMensaje(
                                i18n.get("usuario.info.no_tiene_rol"),
                                i18n.get("global.info"),
                                JOptionPane.INFORMATION_MESSAGE
                        );
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

    private void llenarComboRoles(JComboBox cbxRol) {
        cbxRol.removeAllItems();
        for (Rol rol : Rol.values()) {
            cbxRol.addItem(rol);
        }
    }

    private void limpiarCamposCrear(UsuarioAnadirView view, JComboBox cbxRol) {
        view.getTxtUsuario().setText("");
        view.getTxtContrasena().setText("");
        if (cbxRol.getItemCount() > 0) cbxRol.setSelectedIndex(0);
    }

    private void limpiarCamposEditar(UsuarioEditarView view, JComboBox cbxRol) {
        view.getTxtUsuario().setText("");
        view.getTxtContrasena().setText("");
        if (cbxRol.getItemCount() > 0) cbxRol.setSelectedIndex(0);
        view.getTxtUsuario().setEditable(true);
    }

    private boolean camposVacios(String username, String pass, Rol rol) {
        return username.isEmpty() || pass.isEmpty() || rol == null;
    }
}