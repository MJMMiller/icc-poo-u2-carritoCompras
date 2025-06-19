package ec.edu.ups.poo.controlador;

import ec.edu.ups.poo.dao.CarritoDAO;
import ec.edu.ups.poo.dao.ProductoDAO;
import ec.edu.ups.poo.dao.impl.CarritoDAOMemoria;
import ec.edu.ups.poo.modelo.Carrito;
import ec.edu.ups.poo.modelo.ItemCarrito;
import ec.edu.ups.poo.modelo.Producto;
import ec.edu.ups.poo.vista.CarritoAnadirView;

import java.util.List;

public class CarritoController {

    private final CarritoDAO carritoDAO;
    private final ProductoDAO productoDAO;
    private final CarritoAnadirView carritoView;

    public CarritoController(CarritoDAO carritoDAO, ProductoDAO productoDAO, CarritoAnadirView carritoView) {
        this.carritoDAO = carritoDAO;
        this.productoDAO = productoDAO;
        this.carritoView = carritoView;
        configurarEventos();
    }

    private void configurarEventos() {
        carritoView.getBtnAddProduct().addActionListener(e -> agregarAlCarrito());
        carritoView.getBtnDeleteItem().addActionListener(e -> eliminarItemSeleccionado());
        carritoView.getBtnCancel().addActionListener(e -> vaciarCarrito());
        carritoView.getBtnSave().addActionListener(e -> guardarCarrito());
    }

    private void agregarAlCarrito() {
        try {
            int codigo = Integer.parseInt(carritoView.getLblCodeProductSearch().getText());
            int cantidad = (int) carritoView.getCbxAmountProduct().getSelectedItem();
            Producto producto = productoDAO.buscarPorCodigo(codigo);

            if (producto == null) {
                carritoView.mostrarMensaje("Producto no encontrado", "Error", 0);
                return;
            }
            carritoDAO.agregarProducto(producto, cantidad);
            actualizarTabla();
            carritoView.limpiarCampos();
        } catch (Exception ex) {
            carritoView.mostrarMensaje("Datos incorrectos: " + ex.getMessage(), "Error", 0);
        }
    }

    private void eliminarItemSeleccionado() {
        int fila = carritoView.getTblItems().getSelectedRow();
        if (fila != -1) {
            int codigo = (int) carritoView.getTblItems().getValueAt(fila, 0);

            int respuesta = carritoView.mostrarMensajeDelate(
                    "¿Estás seguro de eliminar este item del carrito?",
                    "Confirmación",2
            );

            if (respuesta == 0) {
                carritoDAO.eliminarProducto(codigo);
                actualizarTabla();
            }
        } else {
            carritoView.mostrarMensaje("Selecciona un item para eliminar.", "Advertencia", 2);
        }
    }

    private void vaciarCarrito() {
        carritoDAO.vaciarCarrito();
        actualizarTabla();
    }

    private void actualizarTabla() {
        List<ItemCarrito> items = carritoDAO.obtenerItems();
        carritoView.mostrarItemsCarrito(items);

        double subtotal = carritoDAO.calcularTotal();
        carritoView.getLblSubTotal().setText(String.format("%.2f", subtotal));
        double iva = subtotal * 0.15;
        carritoView.getLblTax().setText(String.format("%.2f", iva));
        carritoView.getLblTotal().setText(String.format("%.2f", subtotal + iva));
    }

    public void guardarCarrito() {
        if (carritoDAO.estaVacio()) {
            carritoView.mostrarMensaje("Carrito está vacío", "Error", 0);
        } else {
            int respuesta = carritoView.mostrarMensajeGuardar("¿Estás seguro de guardar este carrito?", "Confirmación", 2);
            if (respuesta == 0) {
                List<ItemCarrito> items = carritoDAO.obtenerItems();
                double subtotal = carritoDAO.calcularTotal();
                double iva = subtotal * 0.15;
                double total = subtotal + iva;
                Carrito carrito = new Carrito(items, subtotal, iva, total, new java.util.Date());
                ((CarritoDAOMemoria) carritoDAO).guardarCarrito(carrito);

                carritoView.mostrarMensaje("Carrito guardado exitosamente", "Éxito", 1);
                vaciarCarrito();

                System.out.println(carrito);
            }
        }
    }

}