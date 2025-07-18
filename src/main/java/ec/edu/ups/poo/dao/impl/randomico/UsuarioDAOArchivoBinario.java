package ec.edu.ups.poo.dao.impl.randomico;

import ec.edu.ups.poo.dao.UsuarioDAO;
import ec.edu.ups.poo.excepciones.CedulaInvalidaException;
import ec.edu.ups.poo.excepciones.ContrasenaInvalidaException;
import ec.edu.ups.poo.modelo.*;

import java.io.*;
import java.util.*;

/**
 * Implementación de UsuarioDAO que almacena los usuarios en un archivo binario.
 * Permite crear, buscar, eliminar, actualizar y autenticar usuarios, así como gestionar preguntas de seguridad y carritos asociados.
 */
public class UsuarioDAOArchivoBinario implements UsuarioDAO {
    private final String rutaArchivo;
    private final List<Pregunta> preguntas;

    /**
     * Constructor de UsuarioDAOArchivoBinario.
     * Inicializa la lista de preguntas y la ruta del archivo binario.
     * Crea el archivo si no existe y agrega usuarios por defecto si está vacío.
     *
     * @param preguntas Lista de preguntas de seguridad disponibles.
     * @param rutaArchivo Ruta del archivo binario donde se almacenan los usuarios.
     */
    public UsuarioDAOArchivoBinario(List<Pregunta> preguntas, String rutaArchivo) {
        this.preguntas = preguntas;
        this.rutaArchivo = rutaArchivo;
        crearArchivoSiNoExiste();
        if (listarUsuariosTodos().isEmpty()) {
            crearUsuariosPorDefecto();
        }
    }

    /**
     * Crea el archivo de usuarios si no existe en la ruta especificada.
     * No recibe parámetros ni retorna valores.
     */
    private void crearArchivoSiNoExiste() {
        File f = new File(rutaArchivo);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Crea un nuevo usuario si no existe previamente.
     *
     * @param usuario Usuario a crear.
     */
    @Override
    public void crearUsuario(Usuario usuario) {
        // Solo agrega si no existe
        if (buscarUsuario(usuario.getCedula()) == null) {
            List<Usuario> usuarios = listarUsuariosTodos();
            usuarios.add(usuario);
            guardarUsuarios(usuarios);
        }
    }

    /**
     * Lista todos los usuarios almacenados en el archivo binario.
     *
     * @return Lista de todos los usuarios.
     */
    @Override
    public List<Usuario> listarUsuariosTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        try (DataInputStream dis = new DataInputStream(new FileInputStream(rutaArchivo))) {
            while (dis.available() > 0) {
                String cedula = dis.readUTF();
                String contrasena = dis.readUTF();
                Rol rol = Rol.valueOf(dis.readUTF());
                String nombre = dis.readUTF();
                Date fechaNac = new Date(dis.readLong());
                String correo = dis.readUTF();
                String telefono = dis.readUTF();

                Usuario usuario = null;
                try {
                    usuario = new Usuario(cedula, contrasena, rol, nombre, fechaNac, correo, telefono);
                } catch (CedulaInvalidaException | ContrasenaInvalidaException e) {
                    continue;
                }

                // Preguntas de validación
                int cantPreguntas = dis.readInt();
                List<PreguntaUsuario> preguntasVal = new ArrayList<>();
                for (int i = 0; i < cantPreguntas; i++) {
                    String textoPregunta = dis.readUTF();
                    String respuesta = dis.readUTF();
                    Pregunta pregunta = buscarPreguntaPorTexto(textoPregunta);
                    if (pregunta == null) pregunta = new Pregunta(textoPregunta);
                    preguntasVal.add(new PreguntaUsuario(pregunta, respuesta));
                }
                usuario.setPreguntaValidacion(preguntasVal);

                // Carritos (solo id; puedes extender si quieres más info)
                int cantCarritos = dis.readInt();
                List<Carrito> carritos = new ArrayList<>();
                for (int i = 0; i < cantCarritos; i++) {
                    int idCarrito = dis.readInt();
                    carritos.add(new Carrito(idCarrito, new ArrayList<>(), 0, 0, 0, new Date(), usuario));
                }
                usuario.setCarritos(carritos);

                usuarios.add(usuario);
            }
        } catch (EOFException eof) {
            // fin de archivo
        } catch (IOException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    /**
     * Busca una pregunta de seguridad por su texto en la lista de preguntas disponibles.
     *
     * @param texto Texto de la pregunta a buscar.
     * @return Pregunta encontrada o null si no existe.
     */
    private Pregunta buscarPreguntaPorTexto(String texto) {
        if (preguntas == null) return null;
        for (Pregunta pregunta : preguntas) {
            if (pregunta.getTexto().equals(texto)) return pregunta;
        }
        return null;
    }

    /**
     * Guarda la lista de usuarios en el archivo binario.
     *
     * @param usuarios Lista de usuarios a guardar.
     */
    private void guardarUsuarios(List<Usuario> usuarios) {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(rutaArchivo, false))) {
            for (Usuario usuario : usuarios) {
                dos.writeUTF(usuario.getCedula());
                dos.writeUTF(usuario.getContrasena());
                dos.writeUTF(usuario.getRol().name());
                dos.writeUTF(usuario.getNombreCompleto() != null ? usuario.getNombreCompleto() : "");
                dos.writeLong(usuario.getFechaNacimiento() != null ? usuario.getFechaNacimiento().getTime() : 0L);
                dos.writeUTF(usuario.getCorreo() != null ? usuario.getCorreo() : "");
                dos.writeUTF(usuario.getTelefono() != null ? usuario.getTelefono() : "");

                // Preguntas de validación
                List<PreguntaUsuario> preguntasVal = usuario.getPreguntaValidacion();
                dos.writeInt(preguntasVal != null ? preguntasVal.size() : 0);
                if (preguntasVal != null) {
                    for (PreguntaUsuario pu : preguntasVal) {
                        dos.writeUTF(pu.getPregunta().getTexto());
                        dos.writeUTF(pu.getRespuesta());
                    }
                }

                // Carritos
                List<Carrito> carritos = usuario.getCarritos();
                dos.writeInt(carritos != null ? carritos.size() : 0);
                if (carritos != null) {
                    for (Carrito c : carritos) {
                        dos.writeInt(c.getId());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Busca un usuario por su cédula.
     *
     * @param cedula Cédula del usuario a buscar.
     * @return Usuario encontrado o null si no existe.
     */
    @Override
    public Usuario buscarUsuario(String cedula) {
        for (Usuario usuario : listarUsuariosTodos()) {
            if (usuario.getCedula().equals(cedula)) {
                return usuario;
            }
        }
        return null;
    }

    /**
     * Elimina un usuario por su cédula.
     *
     * @param cedula Cédula del usuario a eliminar.
     */
    @Override
    public void eliminarUsuario(String cedula) {
        List<Usuario> usuarios = listarUsuariosTodos();
        usuarios.removeIf(u -> u.getCedula().equals(cedula));
        guardarUsuarios(usuarios);
    }

    /**
     * Actualiza todos los datos de un usuario existente.
     *
     * @param usuarioActualizado Usuario con los datos actualizados.
     */
    @Override
    public void actualizarTodo(Usuario usuarioActualizado) {
        List<Usuario> usuarios = listarUsuariosTodos();
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getCedula().equals(usuarioActualizado.getCedula())) {
                usuarios.set(i, usuarioActualizado);
                break;
            }
        }
        guardarUsuarios(usuarios);
    }

    /**
     * Autentica un usuario por cédula y contraseña.
     *
     * @param cedula Cédula del usuario.
     * @param contrasena Contraseña del usuario.
     * @return Usuario autenticado si las credenciales son correctas, null en caso contrario.
     */
    @Override
    public Usuario autenticarUsuario(String cedula, String contrasena) {
        for (Usuario usuario : listarUsuariosTodos()) {
            if (usuario.getCedula().equals(cedula) &&
                    usuario.getContrasena().equals(contrasena)) {
                return usuario;
            }
        }
        return null;
    }

    /**
     * Actualiza la contraseña y el rol de un usuario.
     *
     * @param userName Cédula del usuario.
     * @param contrasena Nueva contraseña.
     * @param rol Nuevo rol.
     */
    @Override
    public void actualizar(String userName, String contrasena, Rol rol) {
        List<Usuario> usuarios = listarUsuariosTodos();
        for (Usuario usuario : usuarios) {
            if (usuario.getCedula().equals(userName)) {
                try {
                    usuario.setContrasena(contrasena);
                } catch (ContrasenaInvalidaException e) { }
                usuario.setRol(rol);
                break;
            }
        }
        guardarUsuarios(usuarios);
    }

    /**
     * Busca usuarios por su rol.
     *
     * @param rol Rol a buscar.
     * @return Lista de usuarios que tienen el rol especificado.
     */
    @Override
    public List<Usuario> buscarUsuariosPorRol(Rol rol) {
        List<Usuario> usuariosRol = new ArrayList<>();
        for (Usuario usuario : listarUsuariosTodos()) {
            if (usuario.getRol() == rol) {
                usuariosRol.add(usuario);
            }
        }
        return usuariosRol;
    }

    /**
     * Agrega preguntas de seguridad a un usuario.
     * Reemplaza las preguntas existentes por las nuevas.
     *
     * @param cedula Cédula del usuario.
     * @param nuevasPreguntas Lista de nuevas preguntas a agregar.
     */
    @Override
    public void agregarPreguntasAUsuario(String cedula, List<PreguntaUsuario> nuevasPreguntas) {
        // Robustez: siempre lee todos los usuarios antes de actualizar
        List<Usuario> usuarios = listarUsuariosTodos();
        for (int i = 0; i < usuarios.size(); i++) {
            Usuario usuario = usuarios.get(i);
            if (usuario.getCedula().equals(cedula)) {
                usuario.setPreguntaValidacion(new ArrayList<>(nuevasPreguntas));
                usuarios.set(i, usuario);
                break;
            }
        }
        guardarUsuarios(usuarios);
    }

    /**
     * Crea usuarios por defecto (administrador y usuario general) si el archivo está vacío.
     * No recibe parámetros ni retorna valores.
     */
    private void crearUsuariosPorDefecto() {
        List<Usuario> usuarios = listarUsuariosTodos();

        String nombreAdmin = "Administrador General";
        Date fechaAdmin = new GregorianCalendar(2006, Calendar.JUNE, 16).getTime();
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
            if (preguntas != null && preguntas.size() >= 3) {
                preguntasAdmin.add(new PreguntaUsuario(preguntas.get(0), "Rocko"));
                preguntasAdmin.add(new PreguntaUsuario(preguntas.get(1), "Cuenca"));
                preguntasAdmin.add(new PreguntaUsuario(preguntas.get(2), "Pizza"));
            }
            usuarioAdmin.setPreguntaValidacion(preguntasAdmin);
            usuarios.add(usuarioAdmin);
        } catch (ContrasenaInvalidaException | CedulaInvalidaException e) { }

        String nombreUser = "Usuario General";
        Date fechaUser = new GregorianCalendar(2000, Calendar.JANUARY, 1).getTime();
        String correoUser = "user@user.com";
        String telefonoUser = "0987654321";
        try {
            Usuario usuarioUser = new Usuario(
                    "0706338340",
                    "User@1",
                    Rol.USUARIO,
                    nombreUser,
                    fechaUser,
                    correoUser,
                    telefonoUser
            );
            usuarios.add(usuarioUser);
        } catch (ContrasenaInvalidaException | CedulaInvalidaException e) { }

        guardarUsuarios(usuarios);
    }
}