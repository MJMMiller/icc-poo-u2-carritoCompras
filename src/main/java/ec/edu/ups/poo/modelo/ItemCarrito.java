package ec.edu.ups.poo.modelo;

public class ItemCarrito {
    private Producto producto;
    private int cantidad;

    public ItemCarrito() {}

    public ItemCarrito(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public double getTotalItem() {
        return producto != null ? producto.getPrecio() * cantidad : 0.0;
    }

    @Override
    public String toString() {
        return "ItemCarrito{" +
                "producto=" + producto +
                ", cantidad=" + cantidad +
                '}';
    }
}