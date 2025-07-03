package ec.edu.ups.poo.dao;

import ec.edu.ups.poo.modelo.Carrito;
import ec.edu.ups.poo.modelo.ItemCarrito;
import ec.edu.ups.poo.modelo.Producto;
import java.util.List;

public interface CarritoDAO {
    void agregarProducto(Producto producto, int cantidad);
    void eliminarProducto(int codigoProducto);
    void vaciarCarrito();
    double calcularTotal();
    List<ItemCarrito> obtenerItems();
    boolean estaVacio();
    void guardarCarrito(Carrito carrito);
    Carrito obtenerCarrito(int idCarrito);
    void eliminarCarrtio(int idCarrito);
    List<Carrito> listarCarritos();
    List<Carrito> listarPorUsuario(String username);
}