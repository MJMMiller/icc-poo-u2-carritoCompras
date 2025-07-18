package ec.edu.ups.poo.dao.impl.randomico;

import ec.edu.ups.poo.dao.CarritoDAO;
import ec.edu.ups.poo.excepciones.CedulaInvalidaException;
import ec.edu.ups.poo.excepciones.ContrasenaInvalidaException;
import ec.edu.ups.poo.modelo.Carrito;
import ec.edu.ups.poo.modelo.ItemCarrito;
import ec.edu.ups.poo.modelo.Producto;
import ec.edu.ups.poo.modelo.Usuario;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Implementación de CarritoDAO que almacena los carritos en un archivo binario.
 * Permite agregar, eliminar, listar, actualizar y persistir carritos y sus items.
 */
public class CarritoDAOArchivoBinario implements CarritoDAO {
    private static final int MAX_ITEMS = 10;
    private final List<ItemCarrito> items;
    private final List<Carrito> carritos;
    private final String rutaArchivo;
    private final List<Producto> productos;
    private final List<Usuario> usuarios;
    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Constructor de CarritoDAOArchivoBinario.
     * Inicializa la ruta del archivo, las listas de productos y usuarios, y carga los carritos desde el archivo.
     *
     * @param rutaArchivo Ruta del archivo binario donde se almacenan los carritos.
     * @param productos Lista de productos disponibles.
     * @param usuarios Lista de usuarios disponibles.
     */
    public CarritoDAOArchivoBinario(String rutaArchivo, List<Producto> productos, List<Usuario> usuarios) {
        this.rutaArchivo = rutaArchivo;
        this.productos = productos;
        this.usuarios = usuarios;
        this.items = new ArrayList<>();
        this.carritos = cargarCarritos();
    }

    /**
     * Agrega un producto al carrito actual. Si el producto ya existe, suma la cantidad.
     *
     * @param producto Producto a agregar.
     * @param cantidad Cantidad del producto.
     */
    @Override
    public void agregarProducto(Producto producto, int cantidad) {
        for (ItemCarrito item : items) {
            if (item.getProducto().getCodigo() == producto.getCodigo()) {
                item.setCantidad(item.getCantidad() + cantidad);
                return;
            }
        }
        if (items.size() < MAX_ITEMS) {
            items.add(new ItemCarrito(producto, cantidad));
        }
    }

    /**
     * Elimina un producto del carrito actual por su código.
     *
     * @param codigoProducto Código del producto a eliminar.
     */
    @Override
    public void eliminarProducto(int codigoProducto) {
        Iterator<ItemCarrito> iterator = items.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getProducto().getCodigo() == codigoProducto) {
                iterator.remove();
                break;
            }
        }
    }

    /**
     * Vacía todos los productos del carrito actual.
     */
    @Override
    public void vaciarCarrito() {
        items.clear();
    }

    /**
     * Calcula el total del carrito sumando el precio por cantidad de cada producto.
     *
     * @return Total del carrito como double.
     */
    @Override
    public double calcularTotal() {
        double total = 0;
        for (ItemCarrito item : items) {
            total += item.getProducto().getPrecio() * item.getCantidad();
        }
        return total;
    }

    /**
     * Obtiene la lista de items del carrito actual.
     *
     * @return Lista de ItemCarrito.
     */
    @Override
    public List<ItemCarrito> obtenerItems() {
        return new ArrayList<>(items);
    }

    /**
     * Verifica si el carrito actual está vacío.
     *
     * @return true si el carrito no tiene items, false en caso contrario.
     */
    @Override
    public boolean estaVacio() {
        return items.isEmpty();
    }

    /**
     * Guarda un carrito en la lista y lo persiste en el archivo binario.
     *
     * @param carrito Carrito a guardar.
     */
    @Override
    public void guardarCarrito(Carrito carrito) {
        carritos.add(carrito);
        guardarCarritosEnArchivo();
    }

    /**
     * Obtiene un carrito por su ID.
     *
     * @param idCarrito ID del carrito a buscar.
     * @return Carrito encontrado o null si no existe.
     */
    @Override
    public Carrito obtenerCarrito(int idCarrito) {
        for (Carrito carrito : carritos) {
            if (carrito.getId() == idCarrito) {
                return carrito;
            }
        }
        return null;
    }

    /**
     * Elimina un carrito por su ID y actualiza el archivo binario.
     *
     * @param idCarrito ID del carrito a eliminar.
     */
    @Override
    public void eliminarCarrtio(int idCarrito) {
        Iterator<Carrito> iterator = carritos.iterator();
        while (iterator.hasNext()) {
            Carrito carrito = iterator.next();
            if (carrito.getId() == idCarrito) {
                iterator.remove();
                guardarCarritosEnArchivo();
                break;
            }
        }
    }

    /**
     * Lista todos los carritos almacenados.
     *
     * @return Lista de Carrito.
     */
    @Override
    public List<Carrito> listarCarritos() {
        return new ArrayList<>(carritos);
    }

    /**
     * Lista los carritos asociados a un usuario por su cédula.
     *
     * @param cedula Cédula del usuario.
     * @return Lista de carritos del usuario.
     */
    @Override
    public List<Carrito> listarPorUsuario(String cedula) {
        List<Carrito> carritosUsuario = new ArrayList<>();
        for (Carrito carrito : carritos) {
            if (carrito.getUsuario() != null && carrito.getUsuario().getCedula().equals(cedula)) {
                carritosUsuario.add(carrito);
            }
        }
        return carritosUsuario;
    }

    /**
     * Actualiza un carrito existente y lo persiste en el archivo binario.
     *
     * @param carritoActualizado Carrito a actualizar.
     */
    @Override
    public void actualizarCarrito(Carrito carritoActualizado) {
        for (int i = 0; i < carritos.size(); i++) {
            if (carritos.get(i).getId() == carritoActualizado.getId()) {
                carritos.set(i, carritoActualizado);
                guardarCarritosEnArchivo();
                return;
            }
        }
    }

    /**
     * Guarda todos los carritos en el archivo binario.
     * No recibe parámetros ni retorna valores.
     */
    private void guardarCarritosEnArchivo() {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(rutaArchivo, false))) {
            for (Carrito carrito : carritos) {
                dos.writeInt(carrito.getId());
                dos.writeDouble(carrito.getSubtotal());
                dos.writeDouble(carrito.getIva());
                dos.writeDouble(carrito.getTotal());
                dos.writeUTF(DATE_FORMAT.format(carrito.getFecha()));
                dos.writeUTF(carrito.getUsuario().getCedula());
                dos.writeUTF(carrito.getUsuario().getNombreCompleto());
                dos.writeUTF(carrito.getUsuario().getCorreo());
                dos.writeUTF(carrito.getUsuario().getTelefono());
                // Escribe items
                List<ItemCarrito> itemsCarrito = carrito.getItems();
                dos.writeInt(itemsCarrito.size());
                for (ItemCarrito item : itemsCarrito) {
                    Producto p = item.getProducto();
                    dos.writeInt(p.getCodigo());
                    dos.writeUTF(p.getNombre());
                    dos.writeDouble(p.getPrecio());
                    dos.writeInt(item.getCantidad());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carga los carritos almacenados en el archivo binario.
     *
     * @return Lista de carritos cargados desde el archivo.
     */
    private List<Carrito> cargarCarritos() {
        List<Carrito> lista = new ArrayList<>();
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) return lista;
        try (DataInputStream dis = new DataInputStream(new FileInputStream(archivo))) {
            while (dis.available() > 0) {
                int id = dis.readInt();
                double subtotal = dis.readDouble();
                double iva = dis.readDouble();
                double total = dis.readDouble();
                Date fecha = DATE_FORMAT.parse(dis.readUTF());
                String cedula = dis.readUTF();
                String nombre = dis.readUTF();
                String correo = dis.readUTF();
                String telefono = dis.readUTF();
                int itemsCount = dis.readInt();
                Usuario usuario = buscarUsuarioPorCedula(cedula, nombre, correo, telefono);
                List<ItemCarrito> itemsCarrito = new ArrayList<>();
                for (int i = 0; i < itemsCount; i++) {
                    int codigo = dis.readInt();
                    String nombreProd = dis.readUTF();
                    double precio = dis.readDouble();
                    int cantidad = dis.readInt();
                    Producto prod = buscarProductoPorCodigo(codigo, nombreProd, precio);
                    itemsCarrito.add(new ItemCarrito(prod, cantidad));
                }
                if (usuario != null) {
                    lista.add(new Carrito(id, itemsCarrito, subtotal, iva, total, fecha, usuario));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Busca un usuario por su cédula en la lista de usuarios.
     * Si no existe, crea una nueva instancia de Usuario.
     *
     * @param cedula Cédula del usuario.
     * @param nombre Nombre del usuario.
     * @param correo Correo electrónico del usuario.
     * @param telefono Teléfono del usuario.
     * @return Usuario encontrado o creado, o null si la cédula es inválida.
     */
    private Usuario buscarUsuarioPorCedula(String cedula, String nombre, String correo, String telefono) {
        for (Usuario usuario : usuarios) {
            if (usuario.getCedula().equals(cedula)) {
                return usuario;
            }
        }
        try {
            return new Usuario(cedula, "", null, nombre, null, correo, telefono);
        } catch (CedulaInvalidaException | ContrasenaInvalidaException e) {
            return null;
        }
    }

    /**
     * Busca un producto por su código en la lista de productos.
     * Si no existe, crea una nueva instancia de Producto.
     *
     * @param codigo Código del producto.
     * @param nombre Nombre del producto.
     * @param precio Precio del producto.
     * @return Producto encontrado o creado.
     */
    private Producto buscarProductoPorCodigo(int codigo, String nombre, double precio) {
        for (Producto producto : productos) {
            if (producto.getCodigo() == codigo) {
                return producto;
            }
        }
        return new Producto(codigo, nombre, precio);
    }
}