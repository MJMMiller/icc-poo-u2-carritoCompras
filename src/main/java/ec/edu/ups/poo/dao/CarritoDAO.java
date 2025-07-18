package ec.edu.ups.poo.dao;

import ec.edu.ups.poo.modelo.Carrito;
import ec.edu.ups.poo.modelo.ItemCarrito;
import ec.edu.ups.poo.modelo.Producto;
import java.util.List;

/**
 * Interfaz para la gestión de carritos de compras.
 * Define los métodos para agregar, eliminar, listar, actualizar y persistir carritos y sus productos.
 */
public interface CarritoDAO {

    /**
     * Agrega un producto al carrito. Si el producto ya existe, suma la cantidad.
     *
     * @param producto Producto a agregar.
     * @param cantidad Cantidad del producto.
     */
    void agregarProducto(Producto producto, int cantidad);

    /**
     * Elimina un producto del carrito por su código.
     *
     * @param codigoProducto Código del producto a eliminar.
     */
    void eliminarProducto(int codigoProducto);

    /**
     * Vacía todos los productos del carrito.
     */
    void vaciarCarrito();

    /**
     * Calcula el total del carrito sumando el precio por cantidad de cada producto.
     *
     * @return Total del carrito como double.
     */
    double calcularTotal();

    /**
     * Obtiene la lista de items del carrito.
     *
     * @return Lista de ItemCarrito.
     */
    List<ItemCarrito> obtenerItems();

    /**
     * Verifica si el carrito está vacío.
     *
     * @return true si el carrito no tiene items, false en caso contrario.
     */
    boolean estaVacio();

    /**
     * Guarda un carrito en el sistema.
     *
     * @param carrito Carrito a guardar.
     */
    void guardarCarrito(Carrito carrito);

    /**
     * Obtiene un carrito por su ID.
     *
     * @param idCarrito ID del carrito a buscar.
     * @return Carrito encontrado o null si no existe.
     */
    Carrito obtenerCarrito(int idCarrito);

    /**
     * Elimina un carrito por su ID.
     *
     * @param idCarrito ID del carrito a eliminar.
     */
    void eliminarCarrtio(int idCarrito);

    /**
     * Lista todos los carritos almacenados.
     *
     * @return Lista de Carrito.
     */
    List<Carrito> listarCarritos();

    /**
     * Lista los carritos asociados a un usuario por su cédula.
     *
     * @param cedulaUsuario Cédula del usuario.
     * @return Lista de carritos del usuario.
     */
    List<Carrito> listarPorUsuario(String cedulaUsuario);

    /**
     * Actualiza un carrito existente.
     *
     * @param carrito Carrito a actualizar.
     */
    void actualizarCarrito(Carrito carrito);
}