package ec.edu.ups.poo;

import ec.edu.ups.poo.modelo.ItemCarrito;
import ec.edu.ups.poo.modelo.Producto;
import ec.edu.ups.poo.servicio.CarritoServiceImpl;
import ec.edu.ups.poo.servicio.CarritoService;

public class CarritoTest {
    public static void main(String[] args) {

        CarritoService carrito = new CarritoServiceImpl();

        Producto p1 = new Producto(1, "Mouse", 15.0);
        Producto p2 = new Producto(2, "Teclado", 25.0);

        carrito.agregarProducto(p1, 2);
        carrito.agregarProducto(p2, 1);

        System.out.println("Contenido del carrito:");
        for (ItemCarrito item : carrito.obtenerItems()) {
            System.out.println("- " + item);
        }

        /*
        // Calcular total
        double total = carrito.calcularTotal();
        System.out.println("Total: $" + total);

        // Verificar si está vacío
        System.out.println("¿Carrito vacío? " + carrito.estaVacio());

        // Eliminar producto y vaciar carrito
        carrito.eliminarProducto(1);
        carrito.vaciarCarrito();

        System.out.println("Carrito vaciado. ¿Vacío ahora? " + carrito.estaVacio());
        */
    }
}
