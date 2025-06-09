package ec.edu.ups.poo;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Producto producto1 = new Producto("P001", "Laptop", 500.00);
        Producto producto2 = new Producto("P002", "Mouse", 20.00);

        CarritoCompras carrito = new CarritoCompras();
        carrito.addItem(producto1, 1);
        carrito.addItem(producto2, 2);

        System.out.println("Items en el carrito:");
        for (ItemCarrito item : carrito.getItems()) {
            System.out.println("Producto: " + item.getProducto().getNombre() +
                               ", Cantidad: " + item.getCantidad() +
                               ", Subtotal: " + item.calcularSubtotal());
        }
    }
}