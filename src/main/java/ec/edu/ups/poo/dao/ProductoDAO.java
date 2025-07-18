package ec.edu.ups.poo.dao;

import ec.edu.ups.poo.modelo.Producto;
import java.util.List;

/**
 * Interfaz para la gestión de productos.
 * Define los métodos para crear, buscar, actualizar, eliminar y listar productos en el sistema.
 */
public interface ProductoDAO {

    /**
     * Crea un nuevo producto y lo agrega al sistema.
     *
     * @param producto Producto a agregar.
     */
    void crear(Producto producto);

    /**
     * Busca un producto por su código.
     *
     * @param codigo Código del producto a buscar.
     * @return Producto encontrado o null si no existe.
     */
    Producto buscarPorCodigo(int codigo);

    /**
     * Busca productos por su nombre (ignorando mayúsculas/minúsculas).
     *
     * @param nombre Nombre del producto a buscar.
     * @return Lista de productos que coinciden con el nombre.
     */
    List<Producto> buscarPorNombre(String nombre);

    /**
     * Actualiza los datos de un producto existente en el sistema.
     *
     * @param producto Producto con los datos actualizados.
     */
    void actualizar(Producto producto);

    /**
     * Elimina un producto del sistema por su código.
     *
     * @param codigo Código del producto a eliminar.
     */
    void eliminar(int codigo);

    /**
     * Lista todos los productos almacenados en el sistema.
     *
     * @return Lista de todos los productos.
     */
    List<Producto> listarTodos();
}