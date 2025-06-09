package ec.edu.ups.poo;

import java.util.ArrayList;
import java.util.List;

public class CarritoCompras{

    private List<ItemCarrito> items;
    private double total;

    public CarritoCompras() {
        this.items = new ArrayList<>();
    }

    public void addItem(ItemCarrito item){

        if (item == null) {
            throw new IllegalArgumentException("El item no puede ser nulo");
        }

        for (ItemCarrito existe : items) {
            if (existe.getProducto().getCodigo() == item.getProducto().getCodigo()) {
                existe.setCantidad(existe.getCantidad() + item.getCantidad());
                return;
            }
        }
        items.add(item);
    }

    public List<ItemCarrito> getItems() {
        return items;
    }
}
