package ec.edu.ups.poo.controlador;

import ec.edu.ups.poo.dao.UsuarioDAO;
import ec.edu.ups.poo.dao.PreguntaDAO;
import ec.edu.ups.poo.modelo.Usuario;
import ec.edu.ups.poo.modelo.Pregunta;
import ec.edu.ups.poo.modelo.PreguntaUsuario;
import ec.edu.ups.poo.modelo.enums.Rol;
import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.vista.usuario.*;

import javax.swing.*;
import java.util.*;

public class UsuarioController {

    private final UsuarioDAO usuarioDAO;
    private final PreguntaDAO preguntaDAO;
    private final MensajeInternacionalizacionHandler i18n;

    public UsuarioController(UsuarioDAO usuarioDAO, PreguntaDAO preguntaDAO, MensajeInternacionalizacionHandler i18n) {
        this.usuarioDAO = usuarioDAO;
        this.preguntaDAO = preguntaDAO;
        this.i18n = i18n;
    }

    // --- Login ---
    public Usuario autenticarUsuario(String username, String contrasena) {
        return usuarioDAO.autenticarUsuario(username, contrasena);
    }

    // --- Registro de usuario con preguntas de seguridad y nuevos atributos ---
    public boolean registrarUsuario(
            String username,
            String password,
            Rol rol,
            String nombreCompleto,
            Date fechaNacimiento,
            String correo,
            String telefono,
            List<String> respuestas
    ) {
        if (usuarioDAO.buscarUsuario(username) != null)
            return false;
        List<Pregunta> preguntas = obtenerPreguntasRandom();
        List<PreguntaUsuario> preguntasUsuario = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            preguntasUsuario.add(new PreguntaUsuario(preguntas.get(i), respuestas.get(i)));
        }
        Usuario usuario = new Usuario(username, password, rol, nombreCompleto, fechaNacimiento, correo, telefono);
        usuario.setPreguntaValidacion(preguntasUsuario);
        usuarioDAO.crearUsuario(usuario);
        return true;
    }

    // --- Obtener 3 preguntas aleatorias del banco ---
    public List<Pregunta> obtenerPreguntasRandom() {
        List<Pregunta> preguntas = preguntaDAO.listarTodas();
        Collections.shuffle(preguntas);
        return preguntas.subList(0, 3);
    }

    // --- Métodos para vistas CRUD de usuario ---
    public void configurarUsuarioCrearView(UsuarioAnadirView view) {
        JComboBox cbxRol = view.getCbxRol();
        llenarComboRoles(cbxRol);

        view.getBtnRegistrar().addActionListener(e -> {
            String username = view.getTxtUsuario().getText().trim();
            String password = view.getTxtContrasena().getText().trim();
            Rol rol = (Rol) cbxRol.getSelectedItem();
            String nombreCompleto = view.getTxtNombreCompleto().getText().trim();
            Date fechaNacimiento = view.getFechaNacimiento(); // Asegúrate de que es un Date o ajusta el getter
            String correo = view.getTxtCorreo().getText().trim();
            String telefono = view.getTxtTelefono().getText().trim();

            if (camposVacios(username, password, nombreCompleto, fechaNacimiento, correo, telefono, rol)) {
                view.mostrarMensaje(i18n.get("usuario.error.campos_obligatorios"), i18n.get("global.error"), JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (usuarioDAO.buscarUsuario(username) != null) {
                view.mostrarMensaje(i18n.get("usuario.error.existe"), i18n.get("global.error"), JOptionPane.ERROR_MESSAGE);
                return;
            }
            Usuario nuevoUsuario = new Usuario(username, password, rol, nombreCompleto, fechaNacimiento, correo, telefono);
            usuarioDAO.crearUsuario(nuevoUsuario);
            view.mostrarMensaje(i18n.get("usuario.exito.creado"), i18n.get("global.success"), JOptionPane.INFORMATION_MESSAGE);
            limpiarCamposCrear(view, cbxRol);
        });

        view.getBtnClean().addActionListener(e -> limpiarCamposCrear(view, cbxRol));
    }

    public void configurarUsuarioEditarView(UsuarioEditarView view) {
        JComboBox cbxRol = view.getCbxRol();
        llenarComboRoles(cbxRol);
        view.getTxtUsuario().setEditable(true);

        view.getBtnBuscar().addActionListener(e -> {
            String usernameBuscar = view.getTxtUsuario().getText().trim();
            if (usernameBuscar.isEmpty()) {
                view.mostrarMensaje(i18n.get("usuario.error.ingrese_usuario_buscar"), i18n.get("global.error"), JOptionPane.ERROR_MESSAGE);
                return;
            }
            Usuario usuarioEncontrado = usuarioDAO.buscarUsuario(usernameBuscar);
            if (usuarioEncontrado == null) {
                view.mostrarMensaje(i18n.get("usuario.error.no_encontrado"), i18n.get("global.error"), JOptionPane.ERROR_MESSAGE);
                limpiarCamposEditar(view, cbxRol);
            } else {
                view.getTxtUsuario().setText(usuarioEncontrado.getUserName());
                view.getTxtContrasena().setText(usuarioEncontrado.getContrasena());
                view.getTxtCorreo().setText(usuarioEncontrado.getCorreo());
                view.getTxtTelefono().setText(usuarioEncontrado.getTelefono());
                cbxRol.setSelectedItem(usuarioEncontrado.getRol());
                view.getTxtUsuario().setEditable(false);
            }
        });

        view.getBtnActualizar().addActionListener(e -> {
            String username = view.getTxtUsuario().getText().trim();
            String password = view.getTxtContrasena().getText().trim();
            String correo = view.getTxtCorreo().getText().trim();
            String telefono = view.getTxtTelefono().getText().trim();
            String nombreCompleto = view.getTxtNombreCompleto().getText().trim();
            Date fechaNacimiento = view.getFechaNacimiento();
            Rol rol = (Rol) cbxRol.getSelectedItem();

            if (camposVacios(username, password, nombreCompleto, fechaNacimiento, correo, telefono, rol)) {
                view.mostrarMensaje(i18n.get("usuario.error.campos_obligatorios"), i18n.get("global.error"), JOptionPane.ERROR_MESSAGE);
                return;
            }
            Usuario usuarioExistente = usuarioDAO.buscarUsuario(username);
            if (usuarioExistente == null) {
                view.mostrarMensaje(i18n.get("usuario.error.no_existe"), i18n.get("global.error"), JOptionPane.ERROR_MESSAGE);
                return;
            }
            usuarioExistente.setContrasena(password);
            usuarioExistente.setCorreo(correo);
            usuarioExistente.setTelefono(telefono);
            usuarioExistente.setRol(rol);
            usuarioDAO.actualizar(username, password, rol);
            view.mostrarMensaje(i18n.get("usuario.exito.actualizado"), i18n.get("global.success"), JOptionPane.INFORMATION_MESSAGE);
        });

        view.getBtnClean().addActionListener(e -> limpiarCamposEditar(view, cbxRol));
    }

    public void configurarUsuarioEliminarView(UsuarioElimiarView view) {
        view.getBtnBuscar().addActionListener(e -> {
            String username = view.getTxtUsuario().getText().trim();
            if (username.isEmpty()) {
                view.mostrarMensaje(i18n.get("usuario.error.ingrese_usuario_eliminar"), i18n.get("global.error"), JOptionPane.ERROR_MESSAGE);
                return;
            }
            Usuario usuarioEncontrado = usuarioDAO.buscarUsuario(username);
            if (usuarioEncontrado == null) {
                view.mostrarMensaje(i18n.get("usuario.error.no_encontrado"), i18n.get("global.error"), JOptionPane.ERROR_MESSAGE);
                view.getTxtContrasena().setText("");
                view.getTxtCorreo().setText("");
                view.getTxtTelefono().setText("");
                view.getTxtRol().setText("");
                view.getBtnEliminar().setEnabled(false);
            } else {
                view.getTxtContrasena().setText(usuarioEncontrado.getContrasena());
                view.getTxtCorreo().setText(usuarioEncontrado.getCorreo());
                view.getTxtTelefono().setText(usuarioEncontrado.getTelefono());
                view.getTxtRol().setText(usuarioEncontrado.getRol().toString());
                view.getTxtUsuario().setEditable(false);
                view.getBtnEliminar().setEnabled(true);
            }
        });

        view.getBtnEliminar().addActionListener(e -> {
            String username = view.getTxtUsuario().getText().trim();
            if (username.isEmpty()) {
                view.mostrarMensaje(i18n.get("usuario.error.no_usuario_eliminar"), i18n.get("global.error"), JOptionPane.ERROR_MESSAGE);
                return;
            }
            int confirmacion = view.mostrarMensajeConfirmacion(i18n.get("usuario.confirmacion.eliminar"), i18n.get("global.confirm"), JOptionPane.WARNING_MESSAGE);
            if (confirmacion == 0) {
                usuarioDAO.eliminarUsuario(username);
                view.mostrarMensaje(i18n.get("usuario.exito.eliminado"), i18n.get("global.success"), JOptionPane.INFORMATION_MESSAGE);
                view.getTxtUsuario().setText("");
                view.getTxtContrasena().setText("");
                view.getTxtCorreo().setText("");
                view.getTxtTelefono().setText("");
                view.getTxtRol().setText("");
                view.getTxtUsuario().setEditable(true);
                view.getBtnEliminar().setEnabled(false);
            }
        });
    }

    public void configurarUsuarioListarView(UsuarioListarView view) {
        view.getBtnListar().addActionListener(e -> {
            List<Usuario> usuarios = usuarioDAO.listarUsuariosTodos();
            view.mostrarUsuarios(usuarios);
        });
        view.getBtnBuscar().addActionListener(e -> {
            String username = view.getTxtUsuario().getText().trim();
            Rol rol = (Rol) view.getCbxRol().getSelectedItem();
            if (username.isEmpty()) {
                List<Usuario> usuarios = rol == null ? usuarioDAO.listarUsuariosTodos() : usuarioDAO.buscarUsuariosPorRol(rol);
                view.mostrarUsuarios(usuarios);
            } else {
                Usuario usuario = usuarioDAO.buscarUsuario(username);
                if (usuario != null && (rol == null || usuario.getRol().equals(rol))) {
                    view.mostrarUsuarios(List.of(usuario));
                } else {
                    view.mostrarUsuarios(List.of());
                }
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
        view.getTxtCorreo().setText("");
        view.getTxtTelefono().setText("");
        if (cbxRol.getItemCount() > 0) cbxRol.setSelectedIndex(0);
    }

    private void limpiarCamposEditar(UsuarioEditarView view, JComboBox cbxRol) {
        view.getTxtUsuario().setText("");
        view.getTxtContrasena().setText("");
        view.getTxtCorreo().setText("");
        view.getTxtTelefono().setText("");
        if (cbxRol.getItemCount() > 0) cbxRol.setSelectedIndex(0);
        view.getTxtUsuario().setEditable(true);
    }

    private boolean camposVacios(String username, String pass, String nombreCompleto, Date fechaNacimiento, String correo, String telefono, Rol rol) {
        return username.isEmpty()
                || pass.isEmpty()
                || nombreCompleto.isEmpty()
                || fechaNacimiento == null
                || correo.isEmpty()
                || telefono.isEmpty()
                || rol == null;
    }
}