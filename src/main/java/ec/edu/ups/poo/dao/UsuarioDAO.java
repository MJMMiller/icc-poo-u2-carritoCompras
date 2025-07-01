package ec.edu.ups.poo.dao;

import ec.edu.ups.poo.modelo.enums.Rol;
import ec.edu.ups.poo.modelo.Usuario;

import java.util.List;

public interface UsuarioDAO {
    Usuario autenticarUsuario(String userName, String contrasena);
    void crearUsuario(Usuario usuario);
    Usuario buscarUsuario(String userName);
    void eliminarUsuario(String userName);
    void actualizar(String userName, String contrasena, Rol rol);
    List<Usuario> listarUsuariosTodos();
    List<Usuario> buscarUsuariosPorRol(Rol rol);
}