package ec.edu.ups.poo;

import ec.edu.ups.poo.modelo.ItemCarrito;
import ec.edu.ups.poo.modelo.Producto;
import ec.edu.ups.poo.servicio.CarritoService;
import ec.edu.ups.poo.servicio.CarritoServiceImpl;

public class Main {
    public static void main(String[] args) {

        // Crear servicio de carrito
        CarritoService carrito = new CarritoServiceImpl();

        // Crear productos
        Producto p1 = new Producto(1, "Mouse", 15.0);
        Producto p2 = new Producto(2, "Teclado", 25.0);

        // Agregar productos al carrito
        carrito.agregarProducto(p1, 2);  // 2 x $15 = $30
        carrito.agregarProducto(p2, 1);  // 1 x $25 = $25

        // Mostrar los ítems
        System.out.println("Contenido del carrito:");
        for (ItemCarrito item : carrito.obtenerItems()) {
            System.out.println("- " + item.getProducto().getNombre() + " x" + item.getCantidad() + " = $" + String.format("%.2f", item.getProducto().getPrecio() * item.getCantidad()));
        }

        // Calcular total y mostrar con dos decimales
        double total = carrito.calcularTotal();
        System.out.printf("Total: $%.2f%n", total);

        // Verificar si está vacío
        System.out.println("¿Carrito vacío? " + (carrito.estaVacio() ? "Sí" : "No"));

        // Eliminar producto con código 1 y mostrar contenido
        carrito.eliminarProducto(1);
        System.out.println("Contenido del carrito después de eliminar el producto con código 1:");
        for (ItemCarrito item : carrito.obtenerItems()) {
            System.out.println("- " + item.getProducto().getNombre() + " x" + item.getCantidad() + " = $" + String.format("%.2f", item.getProducto().getPrecio() * item.getCantidad()));
        }

        // Vaciar carrito y verificar
        carrito.vaciarCarrito();
        System.out.println("Carrito vaciado. ¿Vacío ahora? " + (carrito.estaVacio() ? "Sí" : "No"));
    }
}