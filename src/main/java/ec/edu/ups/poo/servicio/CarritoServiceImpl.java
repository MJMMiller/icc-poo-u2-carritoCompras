package ec.edu.ups.poo.servicio;

import ec.edu.ups.poo.modelo.ItemCarrito;
import ec.edu.ups.poo.modelo.Producto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CarritoServiceImpl implements CarritoService {

    private final List<ItemCarrito> items;

    public CarritoServiceImpl() {
        this.items = new ArrayList<>();
    }

    @Override
    public void agregarProducto(Producto producto, int cantidad) {
        // Verificar si el producto ya está en el carrito y actualizar la cantidad
        for (ItemCarrito item : items) {
            if (item.getProducto().getCodigo() == producto.getCodigo()) {
                item.setCantidad(item.getCantidad() + cantidad);
                return;
            }
        }
        // Si no está, lo agrega
        items.add(new ItemCarrito(producto, cantidad));
    }

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

    @Override
    public void vaciarCarrito() {
        items.clear();
    }

    @Override
    public double calcularTotal() {
        double total = 0;
        for (ItemCarrito item : items) {
            total += item.getProducto().getPrecio() * item.getCantidad();
        }
        return total;
    }

    @Override
    public List<ItemCarrito> obtenerItems() {
        return new ArrayList<>(items); // Protección contra modificaciones externas
    }

    @Override
    public boolean estaVacio() {
        return items.isEmpty();
    }
}