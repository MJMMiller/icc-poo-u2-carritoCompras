package ec.edu.ups.poo.dao.impl.texto;

import ec.edu.ups.poo.dao.UsuarioDAO;
import ec.edu.ups.poo.excepciones.CedulaInvalidaException;
import ec.edu.ups.poo.excepciones.ContrasenaInvalidaException;
import ec.edu.ups.poo.modelo.Pregunta;
import ec.edu.ups.poo.modelo.PreguntaUsuario;
import ec.edu.ups.poo.modelo.Rol;
import ec.edu.ups.poo.modelo.Usuario;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class UsuarioDAOArchivoTexto implements UsuarioDAO {
    private final List<Usuario> usuarios;
    private final String rutaArchivo;
    private final List<Pregunta> preguntas;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public UsuarioDAOArchivoTexto(List<Pregunta> preguntas, String rutaArchivo) {
        System.out.println("Creando instancia de UsuarioDAOArchivoTexto");
        this.preguntas = preguntas;
        this.rutaArchivo = rutaArchivo;
        this.usuarios = new ArrayList<>();
        cargarUsuarios();
        if (usuarios.isEmpty()) {
            crearUsuariosPorDefecto();
        }
    }

    private void cargarUsuarios() {
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("\\|");
                if (partes.length < 7) continue;
                String cedula = partes[0];
                String contrasena = partes[1];
                Rol rol = Rol.valueOf(partes[2]);
                String nombre = partes[3];
                Date fechaNacimiento = DATE_FORMAT.parse(partes[4]);
                String correo = partes[5];
                String telefono = partes[6];
                Usuario usuario = new Usuario(cedula, contrasena, rol, nombre, fechaNacimiento, correo, telefono);
                if (partes.length > 7 && !partes[7].isEmpty()) {
                    String[] preguntasStr = partes[7].split(";");
                    List<PreguntaUsuario> preguntasUsuario = new ArrayList<>();
                    for (String preg : preguntasStr) {
                        String[] pq = preg.split(",", 2);
                        if (pq.length == 2) {
                            int idPregunta = Integer.parseInt(pq[0]);
                            Pregunta pregunta = buscarPreguntaPorId(idPregunta);
                            if (pregunta != null) {
                                preguntasUsuario.add(new PreguntaUsuario(pregunta, pq[1]));
                            }
                        }
                    }
                    usuario.setPreguntaValidacion(preguntasUsuario);
                }
                usuarios.add(usuario);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void guardarUsuarios() {
        System.out.println("Guardando en archivo: " + rutaArchivo);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (Usuario usuario : usuarios) {
                StringBuilder sb = new StringBuilder();
                sb.append(usuario.getCedula()).append("|")
                        .append(usuario.getContrasena()).append("|")
                        .append(usuario.getRol().name()).append("|")
                        .append(usuario.getNombreCompleto()).append("|")
                        .append(DATE_FORMAT.format(usuario.getFechaNacimiento())).append("|")
                        .append(usuario.getCorreo()).append("|")
                        .append(usuario.getTelefono()).append("|");
                List<PreguntaUsuario> preguntasUsuario = usuario.getPreguntaValidacion();
                if (preguntasUsuario != null && !preguntasUsuario.isEmpty()) {
                    for (PreguntaUsuario pu : preguntasUsuario) {
                        sb.append(pu.getPregunta().getId()).append(",").append(pu.getRespuesta()).append(";");
                    }
                }
                bw.write(sb.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Pregunta buscarPreguntaPorId(int id) {
        for (Pregunta pregunta : preguntas) {
            if (pregunta.getId() == id) return pregunta;
        }
        return null;
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
        System.out.println("Entrando a crearUsuario DAO...");
        usuarios.add(usuario);
        guardarUsuarios();
    }

    public void actualizarPreguntasUsuario(String cedula, List<PreguntaUsuario> preguntasUsuario) {
        Usuario usuario = buscarUsuario(cedula);
        if (usuario != null) {
            usuario.setPreguntaValidacion(preguntasUsuario);
            guardarUsuarios();
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
                guardarUsuarios();
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
            } catch (ContrasenaInvalidaException e) {}
            usuario.setRol(rol);
            guardarUsuarios();
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

    private void crearUsuariosPorDefecto() {
        System.out.println("Creando usuarios por defecto...");
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
            if (preguntas.size() >= 3) {
                preguntasAdmin.add(new PreguntaUsuario(preguntas.get(0), "Rocko"));
                preguntasAdmin.add(new PreguntaUsuario(preguntas.get(1), "Cuenca"));
                preguntasAdmin.add(new PreguntaUsuario(preguntas.get(2), "Pizza"));
            }
            usuarioAdmin.setPreguntaValidacion(preguntasAdmin);
            crearUsuario(usuarioAdmin);
        } catch (ContrasenaInvalidaException | CedulaInvalidaException e) {
            e.printStackTrace();
        }

        String nombreUser = "Usuario General";
        Date fechaUser = getDate(2000, 1, 1);
        String correoUser = "user@user.com";
        String telefonoUser = "0987654321";
        try{
            Usuario usuarioUser = new Usuario(
                    "0706338340",
                    "User@1",
                    Rol.USUARIO,
                    nombreUser,
                    fechaUser,
                    correoUser,
                    telefonoUser
            );
        } catch (ContrasenaInvalidaException | CedulaInvalidaException e) {
        e.printStackTrace();
    }
    }

    private Date getDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}