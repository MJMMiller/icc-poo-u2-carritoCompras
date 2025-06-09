package ec.edu.ups.poo;

import java.util.ArrayList;
import java.util.List;

public class CarritoCompras{

    private List<ItemCarrito> items;
    private double total;

    public CarritoCompras() {
        this.items = new ArrayList<>();
    }

public void addItem(Producto producto, int cantidad) {
    if (producto == null) {
        throw new IllegalArgumentException("El producto no puede ser nulo");
    }
    ItemCarrito item = new ItemCarrito(producto, cantidad);
    for (ItemCarrito existe : items) {
        if (existe.getProducto().getCodigo() == producto.getCodigo()) {
            existe.setCantidad(existe.getCantidad() + cantidad);
            return;
        }
    }
    items.add(item);
}

    public List<ItemCarrito> getItems() {
        return items;
    }
}
