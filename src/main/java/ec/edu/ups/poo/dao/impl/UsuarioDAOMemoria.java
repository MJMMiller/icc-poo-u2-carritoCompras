package ec.edu.ups.poo.dao.impl;

import ec.edu.ups.poo.dao.UsuarioDAO;
import ec.edu.ups.poo.modelo.BancoPreguntaValidacion;
import ec.edu.ups.poo.modelo.PreguntaUsuario;
import ec.edu.ups.poo.modelo.Rol;
import ec.edu.ups.poo.modelo.Usuario;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UsuarioDAOMemoria implements UsuarioDAO {

    private final List<Usuario> usuarios;

    public UsuarioDAOMemoria() {
        usuarios = new ArrayList<>();
        Usuario usuarioAdmin = new Usuario("admin", "admin123", Rol.ADMINISTRADOR);
        usuarioAdmin.setPreguntaValidacion(List.of(
                new PreguntaUsuario(BancoPreguntaValidacion.COLOR_FAVORITO, "Rojo"),
                new PreguntaUsuario(BancoPreguntaValidacion.CIUDAD_NACIMIENTO, "Cuenca"),
                new PreguntaUsuario(BancoPreguntaValidacion.COMIDA_FAVORITA, "Pizza")
        ));
        crearUsuario(usuarioAdmin);
        crearUsuario(new Usuario("user", "user123", Rol.USUARIO));
    }

    @Override
    public Usuario autenticarUsuario(String userName, String contrasena) {
        for (Usuario usuario : usuarios) {
            if (usuario.getUserName().equals(userName) && usuario.getContrasena().equals(contrasena)) {
                return usuario;
            }
        }
        return null;
    }

    @Override
    public void crearUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    @Override
    public Usuario buscarUsuario(String username) {
        for (Usuario u : usuarios) {
            if (u.getUserName().equalsIgnoreCase(username)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public void eliminarUsuario(String userName) {
        Iterator<Usuario> iterator = usuarios.iterator();
        while (iterator.hasNext()) {
            Usuario usuario = iterator.next();
            if (usuario.getUserName().equals(userName)) {
                iterator.remove();
                break;
            }
        }
    }

    @Override
    public void actualizar(String userName, String contrasena, Rol rol) {
        Usuario usuario = buscarUsuario(userName);
        if (usuario != null) {
            usuario.setContrasena(contrasena);
            usuario.setRol(rol);
        }
    }

    public void actualizarUsuario(Usuario usuarioActualizado) {
        Usuario usuario = buscarUsuario(usuarioActualizado.getUserName());
        if (usuario != null) {
            usuario.setContrasena(usuarioActualizado.getContrasena());
            usuario.setRol(usuarioActualizado.getRol());
            usuario.setPreguntaValidacion(usuarioActualizado.getPreguntaValidacion());
        }
    }

    @Override
    public List<Usuario> listarUsuariosTodos() {
        return new ArrayList<>(usuarios);
    }

    @Override
    public List<Usuario> buscarUsuariosPorRol(Rol rol) {
        List<Usuario> usuariosRol = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            if (usuario.getRol() == rol) {
                usuariosRol.add(usuario);
            }
        }
        return usuariosRol;
    }
}