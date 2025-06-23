package ec.edu.ups.poo.controlador;

import ec.edu.ups.poo.dao.CarritoDAO;
import ec.edu.ups.poo.dao.ProductoDAO;
import ec.edu.ups.poo.dao.impl.CarritoDAOMemoria;
import ec.edu.ups.poo.modelo.Carrito;
import ec.edu.ups.poo.modelo.ItemCarrito;
import ec.edu.ups.poo.modelo.Producto;
import ec.edu.ups.poo.vista.carrito.CarritoAnadirView;
import ec.edu.ups.poo.vista.carrito.CarritoEditarView;
import ec.edu.ups.poo.vista.carrito.CarritoEliminarView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class CarritoController {

    private final CarritoDAO carritoDAO;
    private final ProductoDAO productoDAO;
    private final CarritoAnadirView carritoView;
    private final CarritoEditarView carritoEditarView;

    private static int contadorCarrito = 1;

    private Carrito carritoCargado = null;

    public CarritoController(CarritoDAO carritoDAO, ProductoDAO productoDAO, CarritoAnadirView carritoView, CarritoEditarView carritoEditarView) {
        this.carritoDAO = carritoDAO;
        this.productoDAO = productoDAO;
        this.carritoView = carritoView;
        this.carritoEditarView = carritoEditarView;
        configurarEventos();
    }

    private void configurarEventos() {
        if (carritoView != null) {
            carritoView.getBtnAddProduct().addActionListener(e -> agregarAlCarrito());
            carritoView.getBtnDeleteItem().addActionListener(e -> eliminarItemSeleccionado());
            carritoView.getBtnCancel().addActionListener(e -> vaciarCarrito());
            carritoView.getBtnSave().addActionListener(e -> guardarCarrito());
        }
    }

    public void configurarEventosEditar() {
        if (carritoEditarView.getBtnCodeSearchCart() != null) {
            carritoEditarView.getBtnCodeSearchCart().addActionListener(e -> buscarYMostrarCarritoEditar());
        }
        if (carritoEditarView.getBtnSave() != null) {
            carritoEditarView.getBtnSave().addActionListener(e -> guardarCambiosEdicion());
        }
        if (carritoEditarView.getBtnDeleteItem() != null) {
            carritoEditarView.getBtnDeleteItem().addActionListener(e -> eliminarItemSeleccionadoEditar());
        }
        if (carritoEditarView.getBtnClean() != null) {
            carritoEditarView.getBtnClean().addActionListener(e -> limpiarCarritoEditar());
        }
        if (carritoEditarView.getBtnSearchProduct() != null) {
            carritoEditarView.getBtnSearchProduct().addActionListener(e -> buscarProductoParaEditar());
        }
        if (carritoEditarView.getBtnAddProduct() != null) {
            carritoEditarView.getBtnAddProduct().addActionListener(e -> agregarProductoAlCarritoEditar());
        }
    }

    private void buscarYMostrarCarritoEditar() {
        String codigoTexto = carritoEditarView.getLblCodeSerachCart().getText().trim();
        if (codigoTexto.isEmpty()) {
            carritoEditarView.mostrarMensaje("Ingrese el código del carrito", "Error", 0);
            limpiarCarritoEditar();
            return;
        }
        int codigo = Integer.parseInt(codigoTexto);
        Carrito carrito = carritoDAO.obtenerCarrito(codigo);
        if (carrito == null) {
            carritoEditarView.mostrarMensaje("No se encontró un carrito con ese código", "Error", 0);
            limpiarCarritoEditar();
            carritoCargado = null;
            return;
        }
        carritoCargado = carrito;
        carritoEditarView.mostrarItemsCarrito(carrito.getItems());
        setResumenEditar(carrito.getSubtotal(), carrito.getIva(), carrito.getTotal());
    }

    private void guardarCambiosEdicion() {
        if (carritoCargado == null) {
            carritoEditarView.mostrarMensaje("Primero busque un carrito para editar.", "Error", 0);
            return;
        }
        int respuesta = carritoEditarView.mostrarMensajeGuardarCambios(
                "¿Está seguro de guardar los cambios en el carrito?",
                "Confirmación", JOptionPane.WARNING_MESSAGE
        );
        if (respuesta != 0) return;

        for (int i = 0; i < carritoEditarView.getModelo().getRowCount(); i++) {
            int codProd = Integer.parseInt(carritoEditarView.getModelo().getValueAt(i, 0).toString());
            int nuevaCantidad = Integer.parseInt(carritoEditarView.getModelo().getValueAt(i, 3).toString());
            for (ItemCarrito item : carritoCargado.getItems()) {
                if (item.getProducto().getCodigo() == codProd) {
                    item.setCantidad(nuevaCantidad);
                }
            }
        }
        recalcularTotalesCarritoCargado();
        carritoEditarView.mostrarMensaje("Cambios guardados correctamente", "Éxito", 1);
        carritoEditarView.mostrarItemsCarrito(carritoCargado.getItems());
        setResumenEditar(carritoCargado.getSubtotal(), carritoCargado.getIva(), carritoCargado.getTotal());
    }

    private void eliminarItemSeleccionadoEditar() {
        if (carritoCargado == null) {
            carritoEditarView.mostrarMensaje("Primero busque un carrito para editar.", "Error", 0);
            return;
        }
        int fila = carritoEditarView.getTblProducts().getSelectedRow();
        if (fila != -1) {
            int codigo = (int) carritoEditarView.getTblProducts().getValueAt(fila, 0);

            int respuesta = carritoEditarView.mostrarMensajeDelateItem(
                    "¿Estás seguro de eliminar este item del carrito?",
                    "Confirmación", JOptionPane.WARNING_MESSAGE
            );

            if (respuesta == 0) {
                carritoCargado.getItems().removeIf(item -> item.getProducto().getCodigo() == codigo);
                recalcularTotalesCarritoCargado();
                carritoEditarView.mostrarItemsCarrito(carritoCargado.getItems());
                setResumenEditar(carritoCargado.getSubtotal(), carritoCargado.getIva(), carritoCargado.getTotal());
            }
        } else {
            carritoEditarView.mostrarMensaje("Selecciona un item para eliminar.", "Advertencia", 2);
        }
    }

    private void limpiarCarritoEditar() {
        if (carritoCargado != null) {
            carritoCargado.setItems(new ArrayList<>());
            recalcularTotalesCarritoCargado();
        }
        carritoEditarView.getModelo().setRowCount(0);
        setResumenEditar(0, 0, 0);
    }

    private void buscarProductoParaEditar() {
        String codigoTexto = carritoEditarView.getLblCodeProductSearch().getText().trim();
        if (codigoTexto.isEmpty()) {
            carritoEditarView.mostrarMensaje("Ingrese el código del producto", "Error", 0);
            return;
        }
        int codigo = Integer.parseInt(codigoTexto);
        Producto p = productoDAO.buscarPorCodigo(codigo);
        if (p == null) {
            carritoEditarView.mostrarMensaje("Producto no encontrado", "Error", 0);
            carritoEditarView.getLblNameProduct().setText("");
            carritoEditarView.getLblPriceProduct().setText("");
        } else {
            carritoEditarView.getLblNameProduct().setText(p.getNombre());
            carritoEditarView.getLblPriceProduct().setText(String.valueOf(p.getPrecio()));
        }
    }

    private void agregarProductoAlCarritoEditar() {
        if (carritoCargado == null) {
            carritoEditarView.mostrarMensaje("Primero busque un carrito para editar/agregar productos.", "Error", 0);
            return;
        }
        String codigoTexto = carritoEditarView.getLblCodeProductSearch().getText().trim();
        Object cantidadObj = carritoEditarView.getCbxAmountProduct().getSelectedItem();
        if (codigoTexto.isEmpty() || cantidadObj == null) {
            carritoEditarView.mostrarMensaje("Ingrese el código y cantidad del producto.", "Error", 0);
            return;
        }
        int codigo = Integer.parseInt(codigoTexto);
        int cantidad = (int) cantidadObj;
        Producto producto = productoDAO.buscarPorCodigo(codigo);
        if (producto == null) {
            carritoEditarView.mostrarMensaje("Producto no encontrado", "Error", 0);
            return;
        }
        boolean encontrado = false;
        for (ItemCarrito item : carritoCargado.getItems()) {
            if (item.getProducto().getCodigo() == codigo) {
                item.setCantidad(item.getCantidad() + cantidad);
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            carritoCargado.getItems().add(new ItemCarrito(producto, cantidad));
        }
        recalcularTotalesCarritoCargado();
        carritoEditarView.mostrarItemsCarrito(carritoCargado.getItems());
        setResumenEditar(carritoCargado.getSubtotal(), carritoCargado.getIva(), carritoCargado.getTotal());
        carritoEditarView.limpiarCampos();
    }

    private void recalcularTotalesCarritoCargado() {
        if (carritoCargado == null) return;
        double subtotal = 0;
        for (ItemCarrito item : carritoCargado.getItems()) {
            subtotal += item.getTotalItem();
        }
        double iva = subtotal * 0.15;
        double total = subtotal + iva;
        carritoCargado.setSubtotal(subtotal);
        carritoCargado.setIva(iva);
        carritoCargado.setTotal(total);
    }

    private void setResumenEditar(double subtotal, double iva, double total) {
        if (carritoEditarView.getLblSubTotal() != null) {
            carritoEditarView.getLblSubTotal().setText(String.format("%.2f", subtotal));
        }
        if (carritoEditarView.getLblTax() != null) {
            carritoEditarView.getLblTax().setText(String.format("%.2f", iva));
        }
        if (carritoEditarView.getLblTotal() != null) {
            carritoEditarView.getLblTotal().setText(String.format("%.2f", total));
        }
    }

    private void agregarAlCarrito() {
        String codigoTexto = carritoView.getLblCodeProductSearch().getText();
        Object cantidadObj = carritoView.getCbxAmountProduct().getSelectedItem();

        if (codigoTexto == null || codigoTexto.trim().isEmpty()) {
            carritoView.mostrarMensaje("Por favor, ingrese un código de producto.", "Error", 0);
            return;
        }

        boolean esNumero = true;
        for (char c : codigoTexto.trim().toCharArray()) {
            if (!Character.isDigit(c)) {
                esNumero = false;
                break;
            }
        }
        if (!esNumero) {
            carritoView.mostrarMensaje("El código debe ser un número.", "Error", 0);
            return;
        }
        int codigo = Integer.parseInt(codigoTexto.trim());

        if (cantidadObj == null) {
            carritoView.mostrarMensaje("Seleccione una cantidad.", "Error", 0);
            return;
        }
        int cantidad = (int) cantidadObj;

        Producto producto = productoDAO.buscarPorCodigo(codigo);
        if (producto == null) {
            carritoView.mostrarMensaje("Producto no encontrado", "Error", 0);
            return;
        }

        carritoDAO.agregarProducto(producto, cantidad);
        actualizarTabla();
        carritoView.limpiarCampos();
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
                Carrito carrito = new Carrito(contadorCarrito++, items, subtotal, iva, total, new java.util.Date());
                ((CarritoDAOMemoria) carritoDAO).guardarCarrito(carrito);

                carritoView.mostrarMensaje("Carrito guardado exitosamente con ID: " + carrito.getId(), "Éxito", 1);
                vaciarCarrito();

                System.out.println(carrito);
            }
        }
    }

    public void configurarEliminarCarritoView(CarritoEliminarView eliminarView) {
        eliminarView.getBtnCodeSearchCart().addActionListener(e -> {
            String idText = eliminarView.getLblCodeSerachCart().getText().trim();
            if (idText.isEmpty()) {
                JOptionPane.showMessageDialog(eliminarView, "Ingrese el ID del carrito.", "Error", JOptionPane.ERROR_MESSAGE);
                limpiarCamposEliminarCarrito(eliminarView);
                return;
            }

            int id = Integer.parseInt(idText);
            Carrito carrito = carritoDAO.obtenerCarrito(id);
            if (carrito == null) {
                JOptionPane.showMessageDialog(eliminarView, "Carrito no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                limpiarCamposEliminarCarrito(eliminarView);
                return;
            }

            DefaultTableModel modelo = (DefaultTableModel) eliminarView.getTblProducts().getModel();
            modelo.setRowCount(0);
            for (ItemCarrito item : carrito.getItems()) {
                Producto producto = item.getProducto();
                modelo.addRow(new Object[]{
                        producto.getCodigo(),
                        producto.getNombre(),
                        producto.getPrecio(),
                        item.getCantidad(),
                        item.getTotalItem()
                });
            }

            eliminarView.getLblSubTotal().setText(String.format("%.2f", carrito.getSubtotal()));
            eliminarView.getLblTax().setText(String.format("%.2f", carrito.getIva()));
            eliminarView.getLblTotal().setText(String.format("%.2f", carrito.getTotal()));
        });

        eliminarView.getBtnDelate().addActionListener(e -> {
            String idText = eliminarView.getLblCodeSerachCart().getText().trim();
            if (idText.isEmpty()) {
                JOptionPane.showMessageDialog(eliminarView, "Ingrese el ID del carrito.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int confirmacion = eliminarView.mostrarMensajeDelate(
                    "¿Está seguro que desea eliminar el carrito?", "Confirmar eliminación", JOptionPane.WARNING_MESSAGE);
            if (confirmacion == 0) {
                int id = Integer.parseInt(idText);
                carritoDAO.eliminarCarrtio(id);
                JOptionPane.showMessageDialog(eliminarView, "Carrito eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCamposEliminarCarrito(eliminarView);
            }
        });
    }

    private void limpiarCamposEliminarCarrito(CarritoEliminarView eliminarView) {
        eliminarView.getLblCodeSerachCart().setText("");
        eliminarView.getLblSubTotal().setText("");
        eliminarView.getLblTax().setText("");
        eliminarView.getLblTotal().setText("");
        DefaultTableModel modelo = (DefaultTableModel) eliminarView.getTblProducts().getModel();
        modelo.setRowCount(0);
    }
}