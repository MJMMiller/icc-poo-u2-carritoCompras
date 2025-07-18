package ec.edu.ups.poo.modelo;

/**
 * Clase que representa un ítem dentro de un carrito de compras.
 * Contiene el producto y la cantidad seleccionada.
 */
public class ItemCarrito {
    private Producto producto;
    private int cantidad;

    /**
     * Constructor por defecto.
     * Inicializa el ítem sin producto ni cantidad.
     */
    public ItemCarrito() {}

    /**
     * Constructor con parámetros.
     *
     * @param producto Producto asociado al ítem.
     * @param cantidad Cantidad del producto.
     */
    public ItemCarrito(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    /**
     * Obtiene el producto asociado al ítem.
     * @return Producto del ítem.
     */
    public Producto getProducto() { return producto; }

    /**
     * Establece el producto asociado al ítem.
     * @param producto Producto a asociar.
     */
    public void setProducto(Producto producto) { this.producto = producto; }

    /**
     * Obtiene la cantidad del producto en el ítem.
     * @return Cantidad del producto.
     */
    public int getCantidad() { return cantidad; }

    /**
     * Establece la cantidad del producto en el ítem.
     * @param cantidad Cantidad a establecer.
     */
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    /**
     * Calcula el total del ítem (precio del producto por cantidad).
     * @return Total del ítem como double.
     */
    public double getTotalItem() {
        return producto != null ? producto.getPrecio() * cantidad : 0.0;
    }

    /**
     * Devuelve una representación en cadena del ítem de carrito.
     * @return Cadena con los datos del ítem.
     */
    @Override
    public String toString() {
        return "ItemCarrito{" +
                "producto=" + producto +
                ", cantidad=" + cantidad +
                '}';
    }
}