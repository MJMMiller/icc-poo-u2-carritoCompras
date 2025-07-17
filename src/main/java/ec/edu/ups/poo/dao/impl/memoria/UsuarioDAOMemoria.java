package ec.edu.ups.poo.dao.impl.memoria;

import ec.edu.ups.poo.dao.UsuarioDAO;
import ec.edu.ups.poo.modelo.Pregunta;
import ec.edu.ups.poo.modelo.PreguntaUsuario;
import ec.edu.ups.poo.modelo.Rol;
import ec.edu.ups.poo.modelo.Usuario;
import ec.edu.ups.poo.excepciones.ContrasenaInvalidaException;
import ec.edu.ups.poo.excepciones.CedulaInvalidaException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class UsuarioDAOMemoria implements UsuarioDAO {

    private final List<Usuario> usuarios;

    public UsuarioDAOMemoria(List<Pregunta> preguntas) {
        usuarios = new ArrayList<>();

        String nombreAdmin = "Administrador General";
        Date fechaAdmin = getDate(2006, 6, 16);
        String correoAdmin = "admin@admin.com";
        String telefonoAdmin = "0999999999";
        try {
            Usuario usuarioAdmin = new Usuario(
                    "0150303923",
                    "Admin@1",
                    Rol.ADMINISTRADOR,
                    nombreAdmin,
                    fechaAdmin,
                    correoAdmin,
                    telefonoAdmin
            );
            List<PreguntaUsuario> preguntasAdmin = new ArrayList<>();
            preguntasAdmin.add(new PreguntaUsuario(preguntas.get(0), "Rocko"));
            preguntasAdmin.add(new PreguntaUsuario(preguntas.get(1), "Cuenca"));
            preguntasAdmin.add(new PreguntaUsuario(preguntas.get(2), "Pizza"));
            usuarioAdmin.setPreguntaValidacion(preguntasAdmin);
            crearUsuario(usuarioAdmin);
        } catch (ContrasenaInvalidaException | CedulaInvalidaException e) {
            e.printStackTrace();
        }

        String nombreUser = "Usuario de Prueba";
        Date fechaUser = getDate(1990, 5, 15);
        String correoUser = "user@user.com";
        String telefonoUser = "0888888888";
        try {
            Usuario usuarioNormal = new Usuario(
                    "0150303923",
                    "User_2a",
                    Rol.USUARIO,
                    nombreUser,
                    fechaUser,
                    correoUser,
                    telefonoUser
            );
            crearUsuario(usuarioNormal);
        } catch (ContrasenaInvalidaException | CedulaInvalidaException e) {
            e.printStackTrace();
        }
    }

    private Date getDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    @Override
    public Usuario autenticarUsuario(String userName, String contrasena) {
        for (Usuario usuario : usuarios) {
            if (usuario.getCedula().equals(userName) && usuario.getContrasena().equals(contrasena)) {
                return usuario;
            }
        }
        return null;
    }

    @Override
    public void crearUsuario(Usuario usuario) {
        if (buscarUsuario(usuario.getCedula()) == null) {
            usuarios.add(usuario);
        }
    }

    @Override
    public Usuario buscarUsuario(String username) {
        for (Usuario u : usuarios) {
            if (u.getCedula().equalsIgnoreCase(username)) {
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
            if (usuario.getCedula().equals(userName)) {
                iterator.remove();
                break;
            }
        }
    }

    @Override
    public void actualizar(String userName, String contrasena, Rol rol) {
        Usuario usuario = buscarUsuario(userName);
        if (usuario != null) {
            try {
                usuario.setContrasena(contrasena);
            } catch (ContrasenaInvalidaException e) {
            }
            usuario.setRol(rol);
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