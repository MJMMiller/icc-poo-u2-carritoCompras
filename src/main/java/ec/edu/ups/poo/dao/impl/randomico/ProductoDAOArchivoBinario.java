package ec.edu.ups.poo.dao.impl.randomico;

import ec.edu.ups.poo.dao.ProductoDAO;
import ec.edu.ups.poo.modelo.Producto;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Implementación de ProductoDAO que almacena los productos en un archivo binario.
 * Permite crear, buscar, actualizar, eliminar y listar productos de forma persistente.
 */
public class ProductoDAOArchivoBinario implements ProductoDAO {
    private final List<Producto> productos;
    private final String rutaArchivo;

    /**
     * Constructor de ProductoDAOArchivoBinario.
     * Inicializa la ruta del archivo y carga los productos desde el archivo binario.
     * Si el archivo está vacío, agrega productos de ejemplo.
     *
     * @param rutaArchivo Ruta del archivo binario donde se almacenan los productos.
     */
    public ProductoDAOArchivoBinario(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        productos = new ArrayList<>();
        cargarProductos();
        if (productos.isEmpty()) {
            crear(new Producto(1, "Laptop", 1200.00));
            crear(new Producto(2, "Manzana", 0.50));
            crear(new Producto(3, "Tablet", 400.00));
            crear(new Producto(4, "Zapatos", 125.00));
        }
    }

    /**
     * Carga los productos almacenados en el archivo binario a la lista interna.
     * No recibe parámetros ni retorna valores.
     */
    private void cargarProductos() {
        productos.clear();
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) return;
        try (DataInputStream dis = new DataInputStream(new FileInputStream(archivo))) {
            while (dis.available() > 0) {
                int codigo = dis.readInt();
                String nombre = dis.readUTF();
                double precio = dis.readDouble();
                productos.add(new Producto(codigo, nombre, precio));
            }
        } catch (EOFException eof) {
            // fin de archivo
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Guarda todos los productos de la lista interna en el archivo binario.
     * No recibe parámetros ni retorna valores.
     */
    private void guardarProductos() {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(rutaArchivo, false))) {
            for (Producto producto : productos) {
                dos.writeInt(producto.getCodigo());
                dos.writeUTF(producto.getNombre());
                dos.writeDouble(producto.getPrecio());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Crea un nuevo producto y lo agrega a la lista interna y al archivo binario.
     *
     * @param producto Producto a agregar.
     */
    @Override
    public void crear(Producto producto) {
        productos.add(producto);
        guardarProductos();
    }

    /**
     * Busca un producto por su código.
     *
     * @param codigo Código del producto a buscar.
     * @return Producto encontrado o null si no existe.
     */
    @Override
    public Producto buscarPorCodigo(int codigo) {
        for (Producto producto : productos) {
            if (producto.getCodigo() == codigo) {
                return producto;
            }
        }
        return null;
    }

    /**
     * Busca productos por su nombre (ignorando mayúsculas/minúsculas).
     *
     * @param nombre Nombre del producto a buscar.
     * @return Lista de productos que coinciden con el nombre.
     */
    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        List<Producto> encontrados = new ArrayList<>();
        for (Producto producto : productos) {
            if (producto.getNombre().equalsIgnoreCase(nombre)) {
                encontrados.add(producto);
            }
        }
        return encontrados;
    }

    /**
     * Actualiza los datos de un producto existente en la lista y en el archivo binario.
     *
     * @param productoActualizado Producto con los datos actualizados.
     */
    @Override
    public void actualizar(Producto productoActualizado) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo() == productoActualizado.getCodigo()) {
                productos.set(i, productoActualizado);
                guardarProductos();
                break;
            }
        }
    }

    /**
     * Elimina un producto de la lista y del archivo binario por su código.
     *
     * @param codigo Código del producto a eliminar.
     */
    @Override
    public void eliminar(int codigo) {
        Iterator<Producto> iterator = productos.iterator();
        while (iterator.hasNext()) {
            Producto producto = iterator.next();
            if (producto.getCodigo() == codigo) {
                iterator.remove();
                guardarProductos();
                break;
            }
        }
    }

    /**
     * Lista todos los productos almacenados en el archivo binario.
     *
     * @return Lista de todos los productos.
     */
    @Override
    public List<Producto> listarTodos() {
        return new ArrayList<>(productos);
    }
}