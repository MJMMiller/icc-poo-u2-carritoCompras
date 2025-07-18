package ec.edu.ups.poo.dao.impl.texto;

import ec.edu.ups.poo.dao.UsuarioDAO;
import ec.edu.ups.poo.excepciones.CedulaInvalidaException;
import ec.edu.ups.poo.excepciones.ContrasenaInvalidaException;
import ec.edu.ups.poo.modelo.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class UsuarioDAOArchivoTexto implements UsuarioDAO {
    private List<Usuario> usuarios;
    private final String rutaArchivo;
    private final List<Pregunta> preguntas;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public UsuarioDAOArchivoTexto(List<Pregunta> preguntas, String rutaArchivo) {
        this.preguntas = preguntas;
        this.rutaArchivo = rutaArchivo;
        this.usuarios = new ArrayList<>();
        limpiarDuplicadosUsuarios(rutaArchivo);     // Limpia duplicados primero
        cargarUsuarios();                           // Carga usuarios completos (con preguntas)
        if (usuarios.isEmpty()) {
            crearUsuariosPorDefecto();
        }
    }

    @Override
    public Usuario buscarUsuario(String cedula) {
        for (Usuario usuario : usuarios) {
            if (usuario.getCedula().trim().equals(cedula.trim())) {
                return usuario;
            }
        }
        return null;
    }

    @Override
    public void actualizarTodo(Usuario usuarioActualizado) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getCedula().equals(usuarioActualizado.getCedula())) {
                usuarios.set(i, usuarioActualizado);
                break;
            }
        }
        guardarUsuarios();
    }

    private void cargarUsuarios() {
        usuarios.clear(); // Limpia la lista antes de cargar
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("\\|");
                if (partes.length < 7) continue; // Permite que existan usuarios sin carritos/preguntas

                String cedula = partes[0].trim();
                String contrasena = partes[1].trim();
                Rol rol = Rol.valueOf(partes[2].trim());
                String nombre = partes[3].trim();
                Date fechaNacimiento = DATE_FORMAT.parse(partes[4].trim());
                String correo = partes[5].trim();
                String telefono = partes[6].trim();

                Usuario usuario;
                try {
                    usuario = new Usuario(cedula, contrasena, rol, nombre, fechaNacimiento, correo, telefono);
                } catch (CedulaInvalidaException | ContrasenaInvalidaException e) {
                    System.out.println("Error al reconstruir usuario desde archivo: " + e.getMessage());
                    continue;
                }

                // Carritos (partes[7])
                List<Carrito> carritos = new ArrayList<>();
                if (partes.length > 7 && !partes[7].isEmpty()) {
                    String[] carritosStr = partes[7].split(";");
                    for (String carritoStr : carritosStr) {
                        String[] datosCarrito = carritoStr.split(",", 6);
                        if (datosCarrito.length >= 6) {
                            int idCarrito = Integer.parseInt(datosCarrito[0]);
                            List<ItemCarrito> itemsCarrito = new ArrayList<>();
                            String itemsStr = datosCarrito[1];
                            if (itemsStr.length() > 2) {
                                String itemsSinBrackets = itemsStr.substring(1, itemsStr.length()-1);
                                String[] itemsArr = itemsSinBrackets.split("\\|");
                                for (String itemStr : itemsArr) {
                                    String[] partesItem = itemStr.split(":");
                                    if (partesItem.length == 4) {
                                        int codigoProd = Integer.parseInt(partesItem[0]);
                                        String nombreProd = partesItem[1];
                                        double precioProd = Double.parseDouble(partesItem[2]);
                                        int cantidad = Integer.parseInt(partesItem[3]);
                                        Producto prod = new Producto(codigoProd, nombreProd, precioProd);
                                        itemsCarrito.add(new ItemCarrito(prod, cantidad));
                                    }
                                }
                            }
                            double subtotal = Double.parseDouble(datosCarrito[2]);
                            double iva = Double.parseDouble(datosCarrito[3]);
                            double total = Double.parseDouble(datosCarrito[4]);
                            Date fecha = DATE_FORMAT.parse(datosCarrito[5]);
                            carritos.add(new Carrito(idCarrito, itemsCarrito, subtotal, iva, total, fecha, usuario));
                        }
                    }
                }
                usuario.setCarritos(carritos);

                // Preguntas (partes[8])
                List<PreguntaUsuario> preguntasUsuario = new ArrayList<>();
                if (partes.length > 8 && !partes[8].isEmpty()) {
                    String[] preguntasStr = partes[8].split(";");
                    for (String preg : preguntasStr) {
                        String[] pq = preg.split(",", 2);
                        if (pq.length == 2) {
                            String textoPregunta = pq[0];
                            Pregunta pregunta = buscarPreguntaPorTexto(textoPregunta);
                            if (pregunta != null) {
                                preguntasUsuario.add(new PreguntaUsuario(pregunta, pq[1]));
                            } else {
                                preguntasUsuario.add(new PreguntaUsuario(new Pregunta(textoPregunta), pq[1]));
                            }
                        }
                    }
                }
                usuario.setPreguntaValidacion(preguntasUsuario);

                usuarios.add(usuario);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void guardarUsuarios() {
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

                // Serializa carritos
                List<Carrito> carritos = usuario.getCarritos();
                if (carritos != null && !carritos.isEmpty()) {
                    for (Carrito carrito : carritos) {
                        sb.append(carrito.getId()).append(",[");
                        List<ItemCarrito> items = carrito.getItems();
                        if (items != null && !items.isEmpty()) {
                            for (int i = 0; i < items.size(); i++) {
                                ItemCarrito item = items.get(i);
                                Producto prod = item.getProducto();
                                sb.append(prod.getCodigo()).append(":")
                                        .append(prod.getNombre()).append(":")
                                        .append(prod.getPrecio()).append(":")
                                        .append(item.getCantidad());
                                if (i < items.size() - 1) sb.append("|");
                            }
                        }
                        sb.append("],")
                                .append(carrito.getSubtotal()).append(",")
                                .append(carrito.getIva()).append(",")
                                .append(carrito.getTotal()).append(",")
                                .append(DATE_FORMAT.format(carrito.getFecha()))
                                .append(";");
                    }
                }
                sb.append("|"); // Fin carritos

                // Serializa preguntas de seguridad
                List<PreguntaUsuario> preguntasUsuario = usuario.getPreguntaValidacion();
                if (preguntasUsuario != null && !preguntasUsuario.isEmpty()) {
                    for (PreguntaUsuario pu : preguntasUsuario) {
                        sb.append(pu.getPregunta().getTexto()).append(",").append(pu.getRespuesta()).append(";");
                    }
                }
                sb.append("|"); // Fin usuario

                bw.write(sb.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Pregunta buscarPreguntaPorTexto(String texto) {
        for (Pregunta pregunta : preguntas) {
            if (pregunta.getTexto().equals(texto)) return pregunta;
        }
        return null;
    }

    @Override
    public Usuario autenticarUsuario(String cedula, String contrasena) {
        for (Usuario usuario : usuarios) {
            if (usuario.getCedula().trim().equals(cedula.trim()) &&
                    usuario.getContrasena().trim().equals(contrasena.trim())) {
                return usuario;
            }
        }
        return null;
    }

    @Override
    public void crearUsuario(Usuario usuario) {
        if (buscarUsuario(usuario.getCedula()) == null) {
            usuarios.add(usuario);
            guardarUsuarios();
        }
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
            } catch (ContrasenaInvalidaException e) {
                System.out.println("Error al actualizar contraseña: " + e.getMessage());
            }
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
            crearUsuario(usuarioUser);
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

    public void asociarCarritosAUsuarios(CarritoDAOArchivoTexto carritoDAO) {
        List<Carrito> todosLosCarritos = carritoDAO.listarCarritos();
        for (Usuario usuario : usuarios) {
            List<Carrito> carritosUsuario = new ArrayList<>();
            for (Carrito carrito : todosLosCarritos) {
                if (carrito.getUsuario() != null &&
                        carrito.getUsuario().getCedula().equals(usuario.getCedula())) {
                    carritosUsuario.add(carrito);
                }
            }
            usuario.setCarritos(carritosUsuario);
        }
    }

    @Override
    public void agregarPreguntasAUsuario(String cedula, List<PreguntaUsuario> nuevasPreguntas) {
        cargarUsuarios(); // Siempre carga desde archivo para asegurar preguntas
        for (int i = 0; i < usuarios.size(); i++) {
            Usuario usuario = usuarios.get(i);
            if (usuario.getCedula().equals(cedula)) {
                usuario.setPreguntaValidacion(nuevasPreguntas);
                usuarios.set(i, usuario);
                break;
            }
        }
        guardarUsuarios();
    }

    /**
     * Borra duplicados en el archivo de usuarios, dejando solo la última línea por cédula.
     */
    public static void limpiarDuplicadosUsuarios(String rutaArchivo) {
        Map<String, String> cedulaALinea = new LinkedHashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;
                String[] partes = linea.split("\\|");
                if (partes.length < 1) continue;
                String cedula = partes[0].trim();
                cedulaALinea.put(cedula, linea); // Solo la última ocurrencia
            }
        } catch (Exception e) {
            System.out.println("Error leyendo archivo: " + e.getMessage());
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo, false))) {
            for (String lineaUsuario : cedulaALinea.values()) {
                bw.write(lineaUsuario);
                bw.newLine();
            }
        } catch (Exception e) {
            System.out.println("Error escribiendo archivo: " + e.getMessage());
        }
        System.out.println("¡Duplicados eliminados!");
    }
}