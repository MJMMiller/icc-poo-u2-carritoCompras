package ec.edu.ups.poo.dao;

import ec.edu.ups.poo.modelo.PreguntaUsuario;
import ec.edu.ups.poo.modelo.Rol;
import ec.edu.ups.poo.modelo.Usuario;

import java.util.List;

/**
 * Interfaz para la gestión de usuarios.
 * Define los métodos para autenticar, crear, buscar, eliminar, actualizar y listar usuarios,
 * así como para gestionar preguntas de seguridad asociadas.
 */
public interface UsuarioDAO {

    /**
     * Auténtica un usuario por nombre de usuario y contraseña.
     *
     * @param userName Nombre de usuario (cédula).
     * @param contrasena Contraseña del usuario.
     * @return Usuario autenticado si las credenciales son correctas, null en caso contrario.
     */
    Usuario autenticarUsuario(String userName, String contrasena);

    /**
     * Crea un nuevo usuario en el sistema.
     *
     * @param usuario Usuario a crear.
     */
    void crearUsuario(Usuario usuario);

    /**
     * Busca un usuario por su nombre de usuario (cédula).
     *
     * @param userName Nombre de usuario (cédula).
     * @return Usuario encontrado o null si no existe.
     */
    Usuario buscarUsuario(String userName);

    /**
     * Elimina un usuario por su nombre de usuario (cédula).
     *
     * @param userName Nombre de usuario (cédula) a eliminar.
     */
    void eliminarUsuario(String userName);

    /**
     * Actualiza la contraseña y el rol de un usuario.
     *
     * @param userName Nombre de usuario (cédula).
     * @param contrasena Nueva contraseña.
     * @param rol Nuevo rol.
     */
    void actualizar(String userName, String contrasena, Rol rol);

    /**
     * Lista todos los usuarios almacenados en el sistema.
     *
     * @return Lista de todos los usuarios.
     */
    List<Usuario> listarUsuariosTodos();

    /**
     * Busca usuarios por su rol.
     *
     * @param rol Rol a buscar.
     * @return Lista de usuarios que tienen el rol especificado.
     */
    List<Usuario> buscarUsuariosPorRol(Rol rol);

    /**
     * Actualiza todos los datos de un usuario existente.
     *
     * @param usuario Usuario con los datos actualizados.
     */
    void actualizarTodo(Usuario usuario);

    /**
     * Agrega preguntas de seguridad a un usuario.
     * Reemplaza las preguntas existentes por las nuevas.
     *
     * @param cedula Cédula del usuario.
     * @param nuevasPreguntas Lista de nuevas preguntas a agregar.
     */
    void agregarPreguntasAUsuario(String cedula, List<PreguntaUsuario> nuevasPreguntas);
}