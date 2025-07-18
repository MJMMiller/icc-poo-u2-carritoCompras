package ec.edu.ups.poo.dao.impl.memoria;

import ec.edu.ups.poo.dao.ProductoDAO;
import ec.edu.ups.poo.modelo.Producto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Implementación en memoria del DAO de Producto.
 * Permite crear, buscar, actualizar, eliminar y listar productos en una lista interna.
 */
public class ProductoDAOMemoria implements ProductoDAO {
    private final List<Producto> productos;

    /**
     * Constructor de ProductoDAOMemoria.
     * Inicializa la lista de productos y agrega algunos productos de ejemplo.
     */
    public ProductoDAOMemoria() {
        productos = new ArrayList<>();
        crear(new Producto(1, "Laptop", 1200.00));
        crear(new Producto(2, "Manzana", 0.50));
        crear(new Producto(3, "Tablet", 400.00));
        crear(new Producto(4, "Zapatos", 125.00));
    }

    /**
     * Crea un nuevo producto y lo agrega a la lista interna.
     *
     * @param producto Producto a agregar.
     */
    @Override
    public void crear(Producto producto) {
        productos.add(producto);
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
        List<Producto> productosEncontrados = new ArrayList<>();
        for (Producto producto : productos) {
            if (producto.getNombre().equalsIgnoreCase(nombre)) {
                productosEncontrados.add(producto);
            }
        }
        return productosEncontrados;
    }

    /**
     * Actualiza los datos de un producto existente en la lista.
     *
     * @param producto Producto con los datos actualizados.
     */
    @Override
    public void actualizar(Producto producto) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo() == producto.getCodigo()) {
                productos.set(i, producto);
                break;
            }
        }
    }

    /**
     * Elimina un producto de la lista por su código.
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
                break;
            }
        }
    }

    /**
     * Lista todos los productos almacenados en memoria.
     *
     * @return Lista de todos los productos.
     */
    @Override
    public List<Producto> listarTodos() {
        return new ArrayList<>(productos);
    }
}