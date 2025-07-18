package ec.edu.ups.poo.dao.impl.texto;

import ec.edu.ups.poo.dao.CarritoDAO;
import ec.edu.ups.poo.excepciones.CedulaInvalidaException;
import ec.edu.ups.poo.excepciones.ContrasenaInvalidaException;
import ec.edu.ups.poo.modelo.Carrito;
import ec.edu.ups.poo.modelo.ItemCarrito;
import ec.edu.ups.poo.modelo.Producto;
import ec.edu.ups.poo.modelo.Usuario;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Implementación de CarritoDAO que almacena los carritos en un archivo de texto.
 * Permite agregar, eliminar, listar, actualizar y persistir carritos y sus items.
 */
public class CarritoDAOArchivoTexto implements CarritoDAO {

    private final List<ItemCarrito> items;
    private final List<Carrito> carritos;
    private final String rutaArchivo;

    private final List<Producto> productos;
    private final List<Usuario> usuarios;

    private FileReader fileReader;
    private BufferedReader bufferedReader;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Constructor de CarritoDAOArchivoTexto.
     * Inicializa la ruta del archivo, las listas de productos y usuarios, y carga los carritos desde el archivo.
     *
     * @param rutaArchivo Ruta del archivo de texto donde se almacenan los carritos.
     * @param productos Lista de productos disponibles.
     * @param usuarios Lista de usuarios disponibles.
     */
    public CarritoDAOArchivoTexto(String rutaArchivo, List<Producto> productos, List<Usuario> usuarios) {
        this.rutaArchivo = rutaArchivo;
        this.items = new ArrayList<>();
        this.productos = productos;
        this.usuarios = usuarios;
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
        items.add(new ItemCarrito(producto, cantidad));
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
     * Guarda un carrito en la lista y lo persiste en el archivo de texto.
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
     * Elimina un carrito por su ID y actualiza el archivo de texto.
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
     * Actualiza un carrito existente y lo persiste en el archivo de texto.
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
     * Guarda todos los carritos en el archivo de texto.
     * No recibe parámetros ni retorna valores.
     */
    private void guardarCarritosEnArchivo() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (Carrito carrito : carritos) {
                StringBuilder sb = new StringBuilder();
                sb.append(carrito.getId()).append("|")
                        .append(carrito.getSubtotal()).append("|")
                        .append(carrito.getIva()).append("|")
                        .append(carrito.getTotal()).append("|")
                        .append(DATE_FORMAT.format(carrito.getFecha())).append("|")
                        .append(carrito.getUsuario().getCedula()).append("|")
                        .append(carrito.getUsuario().getNombreCompleto()).append("|")
                        .append(carrito.getUsuario().getCorreo()).append("|")
                        .append(carrito.getUsuario().getTelefono()).append("|");
                for (ItemCarrito item : carrito.getItems()) {
                    Producto p = item.getProducto();
                    sb.append(p.getCodigo()).append(",")
                            .append(p.getNombre()).append(",")
                            .append(p.getPrecio()).append(",")
                            .append(item.getCantidad()).append(";");
                }
                bw.write(sb.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carga los carritos almacenados en el archivo de texto.
     *
     * @return Lista de carritos cargados desde el archivo.
     */
    private List<Carrito> cargarCarritos() {
        List<Carrito> lista = new ArrayList<>();
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) return lista;
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                Carrito carrito = aCarritoDeString(linea);
                if (carrito != null) {
                    lista.add(carrito);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Convierte una línea de texto en una instancia de Carrito.
     *
     * @param s Línea de texto que representa un carrito.
     * @return Instancia de Carrito o null si la conversión falla.
     */
    private Carrito aCarritoDeString(String s) {
        String[] partes = s.split("\\|");
        if (partes.length < 10) return null;
        int id = Integer.parseInt(partes[0]);
        double subtotal = Double.parseDouble(partes[1]);
        double iva = Double.parseDouble(partes[2]);
        double total = Double.parseDouble(partes[3]);
        Date fecha;
        try { fecha = DATE_FORMAT.parse(partes[4]); }
        catch (Exception e) { return null; }
        String cedulaUsuario = partes[5];

        // Usa la instancia real del usuario
        Usuario usuario = buscarUsuarioPorCedula(cedulaUsuario);

        List<ItemCarrito> items = new ArrayList<>();
        String[] itemsStr = partes[9].split(";");
        for (String itemStr : itemsStr) {
            if (itemStr.isEmpty()) continue;
            String[] itemData = itemStr.split(",");
            if (itemData.length < 4) continue;
            int codigo = Integer.parseInt(itemData[0]);
            String nombre = itemData[1];
            double precio = Double.parseDouble(itemData[2]);
            int cantidad = Integer.parseInt(itemData[3]);
            Producto producto = new Producto(codigo, nombre, precio);
            items.add(new ItemCarrito(producto, cantidad));
        }
        if (usuario == null) return null;
        return new Carrito(id, items, subtotal, iva, total, fecha, usuario);
    }

    /**
     * Busca un usuario por su cédula en la lista de usuarios.
     *
     * @param cedula Cédula del usuario.
     * @return Usuario encontrado o null si no existe.
     */
    private Usuario buscarUsuarioPorCedula(String cedula) {
        for (Usuario usuario : usuarios) {
            if (usuario.getCedula().equals(cedula)) {
                return usuario;
            }
        }
        return null;
    }
}