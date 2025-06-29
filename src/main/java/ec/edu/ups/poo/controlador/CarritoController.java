package ec.edu.ups.poo.controlador;

import ec.edu.ups.poo.dao.CarritoDAO;
import ec.edu.ups.poo.dao.ProductoDAO;
import ec.edu.ups.poo.dao.impl.CarritoDAOMemoria;
import ec.edu.ups.poo.modelo.Carrito;
import ec.edu.ups.poo.modelo.ItemCarrito;
import ec.edu.ups.poo.modelo.Producto;
import ec.edu.ups.poo.modelo.Rol;
import ec.edu.ups.poo.modelo.Usuario;
import ec.edu.ups.poo.vista.carrito.CarritoAnadirView;
import ec.edu.ups.poo.vista.carrito.CarritoEditarView;
import ec.edu.ups.poo.vista.carrito.CarritoEliminarView;
import ec.edu.ups.poo.vista.carrito.CarritoListarItemsView;
import ec.edu.ups.poo.vista.carrito.CarritoListarView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CarritoController {

    private final CarritoDAO carritoDAO;
    private final ProductoDAO productoDAO;
    private final CarritoAnadirView carritoView;
    private final CarritoEditarView carritoEditarView;
    private final Usuario usuarioAutenticado;

    private static int contadorCarrito = 1;

    private Carrito carritoCargado = null;
    private List<ItemCarrito> copiaItemsEdit = null;
    private CarritoListarItemsView itemsView = null;

    public CarritoController(
            CarritoDAO carritoDAO,
            ProductoDAO productoDAO,
            CarritoAnadirView carritoView,
            CarritoEditarView carritoEditarView,
            Usuario usuarioAutenticado
    ) {
        this.carritoDAO = carritoDAO;
        this.productoDAO = productoDAO;
        this.carritoView = carritoView;
        this.carritoEditarView = carritoEditarView;
        this.usuarioAutenticado = usuarioAutenticado;
        if (carritoView != null) configurarEventosAnadir();
        if (carritoEditarView != null) configurarEventosEditar();
    }

    private void configurarEventosAnadir() {
        carritoView.getBtnAnadir().addActionListener(e -> agregarAlCarrito());
        carritoView.getBtnEliminarItem().addActionListener(e -> eliminarItemSeleccionado());
        carritoView.getBtnCancel().addActionListener(e -> vaciarCarrito());
        carritoView.getBtnSave().addActionListener(e -> guardarCarrito());
    }

    public void configurarEventosEditar() {
        carritoEditarView.getBtnBuscarCarrito().addActionListener(e -> buscarYMostrarCarritoEditar());
        carritoEditarView.getBtnActualizar().addActionListener(e -> guardarCambiosEdicion());
        carritoEditarView.getBtnEliminarItem().addActionListener(e -> eliminarItemSeleccionadoEditar());
        carritoEditarView.getBtnClean().addActionListener(e -> limpiarCarritoEditar());
        carritoEditarView.getBtnBuscarProducto().addActionListener(e -> buscarProductoParaEditar());
        carritoEditarView.getBtnAnadir().addActionListener(e -> agregarProductoAlCarritoEditar());
    }

    public void configurarEventosListar(CarritoListarView carritoListarView) {
        carritoListarView.getBtnBuscar().addActionListener(e -> buscarYMostrarCarritoPorId(carritoListarView));
        carritoListarView.getBtnListar().addActionListener(e -> listarCarritos(carritoListarView));
        carritoListarView.getBtnVerCarrito().addActionListener(e -> verCarrito(carritoListarView));
    }

    private void buscarYMostrarCarritoPorId(CarritoListarView carritoListarView) {
        String idText = carritoListarView.getTxtCodigo().getText().trim();
        if (idText.isEmpty()) {
            carritoListarView.mostrarMensaje("Ingrese el ID del carrito.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int id;
        try {
            id = Integer.parseInt(idText);
            if (id <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            carritoListarView.mostrarMensaje("El ID debe ser un número entero positivo.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Carrito carrito = carritoDAO.obtenerCarrito(id);
        DefaultTableModel modelo = (DefaultTableModel) carritoListarView.getTblCarritos().getModel();
        modelo.setRowCount(0);
        if (carrito != null) {
            modelo.addRow(new Object[]{
                    carrito.getId(),
                    carrito.getUsuario() != null ? carrito.getUsuario().getUserName() : "N/A",
                    carrito.getFecha(),
                    String.format("%.2f", carrito.getSubtotal()),
                    String.format("%.2f", carrito.getIva()),
                    String.format("%.2f", carrito.getTotal())
            });
        } else {
            carritoListarView.mostrarMensaje("No se encontró el carrito con ese ID.", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void listarCarritos(CarritoListarView carritoListarView) {
        List<Carrito> carritos = carritoDAO.listarCarritos();
        DefaultTableModel modelo = (DefaultTableModel) carritoListarView.getTblCarritos().getModel();
        modelo.setRowCount(0);

        for (Carrito carrito : carritos) {
            if (
                    usuarioAutenticado.getRol() == Rol.ADMINISTRADOR ||
                            (carrito.getUsuario() != null &&
                                    carrito.getUsuario().getUserName().equals(usuarioAutenticado.getUserName()))
            ) {
                modelo.addRow(new Object[]{
                        carrito.getId(),
                        carrito.getUsuario() != null ? carrito.getUsuario().getUserName() : "N/A",
                        carrito.getFecha(),
                        String.format("%.2f", carrito.getSubtotal()),
                        String.format("%.2f", carrito.getIva()),
                        String.format("%.2f", carrito.getTotal())
                });
            }
        }
    }

    private void verCarrito(CarritoListarView carritoListarView) {
        int fila = carritoListarView.getTblCarritos().getSelectedRow();
        if (fila == -1) {
            carritoListarView.mostrarMensaje("Seleccione un carrito para ver sus items.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int idCarrito = (int) carritoListarView.getTblCarritos().getValueAt(fila, 0);
        Carrito carrito = carritoDAO.obtenerCarrito(idCarrito);

        if (carrito != null) {
            if (itemsView == null || itemsView.isClosed()) {
                itemsView = new CarritoListarItemsView(
                        carrito.getId(),
                        carrito.getItems(),
                        carrito.getSubtotal(),
                        carrito.getIva(),
                        carrito.getTotal(),
                        carrito.getUsuario() != null ? carrito.getUsuario().getUserName() : "N/A",
                        carrito.getUsuario() != null ? carrito.getUsuario().getRol() : null,
                        carrito.getFecha() != null ? carrito.getFecha().toString() : "N/A"
                );
                itemsView.setVisible(true);
                if (carritoListarView.getParent() instanceof JDesktopPane) {
                    ((JDesktopPane) carritoListarView.getParent()).add(itemsView);
                }
            } else {
                itemsView.mostrarDatos(
                        carrito.getId(),
                        carrito.getItems(),
                        carrito.getSubtotal(),
                        carrito.getIva(),
                        carrito.getTotal(),
                        carrito.getUsuario() != null ? carrito.getUsuario().getUserName() : "N/A",
                        carrito.getUsuario() != null ? carrito.getUsuario().getRol() : null,
                        carrito.getFecha() != null ? carrito.getFecha().toString() : "N/A"
                );
                itemsView.setVisible(true);
                itemsView.toFront();
            }
        } else {
            carritoListarView.mostrarMensaje("No se encontró el carrito seleccionado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void configurarEventosEliminar(CarritoEliminarView eliminarView) {
        eliminarView.getBtnBuscar().addActionListener(e -> mostrarCarritoAEliminar(eliminarView));
        eliminarView.getBtnEliminar().addActionListener(e -> eliminarCarrito(eliminarView));
    }

    private void agregarAlCarrito() {
        String codigoTexto = carritoView.getLblCodeProductSearch().getText();
        Object cantidadObj = carritoView.getCbxCantidad().getSelectedItem();

        if (codigoTexto == null || codigoTexto.trim().isEmpty()) {
            carritoView.mostrarMensaje("Por favor, ingrese un código de producto.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int codigo;
        try {
            codigo = Integer.parseInt(codigoTexto.trim());
            if (codigo <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            carritoView.mostrarMensaje("El código debe ser un número entero positivo.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (cantidadObj == null) {
            carritoView.mostrarMensaje("Seleccione una cantidad.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int cantidad = (int) cantidadObj;

        Producto producto = productoDAO.buscarPorCodigo(codigo);
        if (producto == null) {
            carritoView.mostrarMensaje("Producto no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        carritoDAO.agregarProducto(producto, cantidad);
        actualizarTabla();
        carritoView.limpiarCampos();
    }

    private void eliminarItemSeleccionado() {
        int fila = carritoView.getTblProducts().getSelectedRow();
        if (fila != -1) {
            int codigo = (int) carritoView.getTblProducts().getValueAt(fila, 0);
            int respuesta = carritoView.mostrarMensajeConfirmacion(
                    "¿Estás seguro de eliminar este item del carrito?",
                    "Confirmación", JOptionPane.WARNING_MESSAGE
            );
            if (respuesta == 0) {
                carritoDAO.eliminarProducto(codigo);
                actualizarTabla();
            }
        } else {
            carritoView.mostrarMensaje("Selecciona un item para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
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
        carritoView.getTxtSubTot().setText(String.format("%.2f", subtotal));
        double iva = subtotal * 0.15;
        carritoView.getTxtIva().setText(String.format("%.2f", iva));
        carritoView.getTxtTot().setText(String.format("%.2f", subtotal + iva));
    }

    public void guardarCarrito() {
        if (carritoDAO.estaVacio()) {
            carritoView.mostrarMensaje("Carrito está vacío", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            int respuesta = carritoView.mostrarMensajeConfirmacion("¿Estás seguro de guardar este carrito?", "Confirmación", JOptionPane.QUESTION_MESSAGE);
            if (respuesta == 0) {
                List<ItemCarrito> items = carritoDAO.obtenerItems();
                double subtotal = carritoDAO.calcularTotal();
                double iva = subtotal * 0.15;
                double total = subtotal + iva;
                Carrito carrito = new Carrito(contadorCarrito++, items, subtotal, iva, total, new Date(), usuarioAutenticado);
                ((CarritoDAOMemoria) carritoDAO).guardarCarrito(carrito);

                carritoView.mostrarMensaje("Carrito guardado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                vaciarCarrito();
            }
        }
    }

    private void buscarYMostrarCarritoEditar() {
        String codigoTexto = carritoEditarView.getTxtCodigoCarrito().getText().trim();
        if (codigoTexto.isEmpty()) {
            carritoEditarView.mostrarMensaje("Ingrese el código del carrito", "Error", JOptionPane.ERROR_MESSAGE);
            limpiarCarritoEditar();
            return;
        }
        int codigo;
        try {
            codigo = Integer.parseInt(codigoTexto);
            if (codigo <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            carritoEditarView.mostrarMensaje("El código debe ser un número entero positivo.", "Error", JOptionPane.ERROR_MESSAGE);
            limpiarCarritoEditar();
            return;
        }
        Carrito carrito = carritoDAO.obtenerCarrito(codigo);
        if (carrito == null) {
            carritoEditarView.mostrarMensaje("No se encontró un carrito con ese código", "Error", JOptionPane.ERROR_MESSAGE);
            limpiarCarritoEditar();
            carritoCargado = null;
            copiaItemsEdit = null;
            return;
        }
        carritoCargado = carrito;
        copiaItemsEdit = new ArrayList<>();
        for (ItemCarrito item : carrito.getItems()) {
            copiaItemsEdit.add(new ItemCarrito(item.getProducto(), item.getCantidad()));
        }
        carritoEditarView.mostrarItemsCarrito(copiaItemsEdit);
        setResumenEditar(carritoCargado.getSubtotal(), carritoCargado.getIva(), carritoCargado.getTotal());
    }

    private void guardarCambiosEdicion() {
        if (carritoCargado == null || copiaItemsEdit == null) {
            carritoEditarView.mostrarMensaje("Primero busque un carrito para editar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int respuesta = carritoEditarView.mostrarMensajeConfirmacion(
                "¿Está seguro de guardar los cambios en el carrito?",
                "Confirmación", JOptionPane.WARNING_MESSAGE
        );
        if (respuesta != 0) return;

        for (int i = 0; i < carritoEditarView.getModelo().getRowCount(); i++) {
            int codProd = Integer.parseInt(carritoEditarView.getModelo().getValueAt(i, 0).toString());
            int nuevaCantidad = Integer.parseInt(carritoEditarView.getModelo().getValueAt(i, 3).toString());
            for (ItemCarrito item : copiaItemsEdit) {
                if (item.getProducto().getCodigo() == codProd) {
                    item.setCantidad(nuevaCantidad);
                }
            }
        }
        carritoCargado.setItems(new ArrayList<>());
        for (ItemCarrito item : copiaItemsEdit) {
            carritoCargado.getItems().add(new ItemCarrito(item.getProducto(), item.getCantidad()));
        }
        recalcularTotalesCarritoCargado();
        carritoEditarView.mostrarMensaje("Cambios guardados correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        carritoEditarView.mostrarItemsCarrito(copiaItemsEdit);
        setResumenEditar(carritoCargado.getSubtotal(), carritoCargado.getIva(), carritoCargado.getTotal());
    }

    private void eliminarItemSeleccionadoEditar() {
        if (copiaItemsEdit == null) {
            carritoEditarView.mostrarMensaje("Primero busque un carrito para editar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int fila = carritoEditarView.getTblProducts().getSelectedRow();
        if (fila != -1) {
            int codigo = (int) carritoEditarView.getTblProducts().getValueAt(fila, 0);
            int respuesta = carritoEditarView.mostrarMensajeConfirmacion(
                    "¿Estás seguro de eliminar este item del carrito?",
                    "Confirmación", JOptionPane.WARNING_MESSAGE
            );
            if (respuesta == 0) {
                copiaItemsEdit.removeIf(item -> item.getProducto().getCodigo() == codigo);
                carritoEditarView.mostrarItemsCarrito(copiaItemsEdit);
                recalcularTotalesEdicionTemporal();
                setResumenEditar(
                        sumarSubtotal(copiaItemsEdit),
                        sumarSubtotal(copiaItemsEdit) * 0.15,
                        sumarSubtotal(copiaItemsEdit) * 1.15
                );
            }
        } else {
            carritoEditarView.mostrarMensaje("Selecciona un item para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void limpiarCarritoEditar() {
        if (copiaItemsEdit != null) {
            copiaItemsEdit.clear();
            carritoEditarView.mostrarItemsCarrito(copiaItemsEdit);
        }
        setResumenEditar(0, 0, 0);
    }

    private void buscarProductoParaEditar() {
        String codigoTexto = carritoEditarView.getTxtCodigoProducto().getText().trim();
        if (codigoTexto.isEmpty()) {
            carritoEditarView.mostrarMensaje("Ingrese el código del producto", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int codigo;
        try {
            codigo = Integer.parseInt(codigoTexto);
            if (codigo <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            carritoEditarView.mostrarMensaje("El código debe ser un número entero positivo.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Producto p = productoDAO.buscarPorCodigo(codigo);
        if (p == null) {
            carritoEditarView.mostrarMensaje("Producto no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            carritoEditarView.getTxtNombre().setText("");
            carritoEditarView.getTxtPrecio().setText("");
        } else {
            carritoEditarView.getTxtNombre().setText(p.getNombre());
            carritoEditarView.getTxtPrecio().setText(String.valueOf(p.getPrecio()));
        }
    }

    private void agregarProductoAlCarritoEditar() {
        if (copiaItemsEdit == null) {
            carritoEditarView.mostrarMensaje("Primero busque un carrito para editar/agregar productos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String codigoTexto = carritoEditarView.getTxtCodigoProducto().getText().trim();
        Object cantidadObj = carritoEditarView.getCbxCantidad().getSelectedItem();
        if (codigoTexto.isEmpty() || cantidadObj == null) {
            carritoEditarView.mostrarMensaje("Ingrese el código y cantidad del producto.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int codigo;
        try {
            codigo = Integer.parseInt(codigoTexto);
            if (codigo <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            carritoEditarView.mostrarMensaje("El código debe ser un número entero positivo.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int cantidad = (int) cantidadObj;
        Producto producto = productoDAO.buscarPorCodigo(codigo);
        if (producto == null) {
            carritoEditarView.mostrarMensaje("Producto no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        boolean encontrado = false;
        for (ItemCarrito item : copiaItemsEdit) {
            if (item.getProducto().getCodigo() == codigo) {
                item.setCantidad(item.getCantidad() + cantidad);
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            copiaItemsEdit.add(new ItemCarrito(producto, cantidad));
        }
        carritoEditarView.mostrarItemsCarrito(copiaItemsEdit);
        recalcularTotalesEdicionTemporal();
        setResumenEditar(
                sumarSubtotal(copiaItemsEdit),
                sumarSubtotal(copiaItemsEdit) * 0.15,
                sumarSubtotal(copiaItemsEdit) * 1.15
        );
        carritoEditarView.limpiarCampos();
    }

    private void recalcularTotalesEdicionTemporal() {
        // Solo para actualizar la vista, los cambios en el objeto real se hacen al guardar.
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

    private double sumarSubtotal(List<ItemCarrito> items) {
        double subtotal = 0;
        for (ItemCarrito item : items) {
            subtotal += item.getTotalItem();
        }
        return subtotal;
    }

    private void setResumenEditar(double subtotal, double iva, double total) {
        if (carritoEditarView.getTxtSub() != null) {
            carritoEditarView.getTxtSub().setText(String.format("%.2f", subtotal));
        }
        if (carritoEditarView.getTxtIva() != null) {
            carritoEditarView.getTxtIva().setText(String.format("%.2f", iva));
        }
        if (carritoEditarView.getTxtTot() != null) {
            carritoEditarView.getTxtTot().setText(String.format("%.2f", total));
        }
    }

    private void mostrarCarritoAEliminar(CarritoEliminarView eliminarView) {
        String idText = eliminarView.getTxtCodigo().getText().trim();
        if (idText.isEmpty()) {
            eliminarView.mostrarMensaje("Ingrese el ID del carrito.", "Error", JOptionPane.ERROR_MESSAGE);
            limpiarCamposEliminarCarrito(eliminarView);
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idText);
            if (id <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            eliminarView.mostrarMensaje("El ID debe ser un número entero positivo.", "Error", JOptionPane.ERROR_MESSAGE);
            limpiarCamposEliminarCarrito(eliminarView);
            return;
        }
        Carrito carrito = carritoDAO.obtenerCarrito(id);
        if (carrito == null) {
            eliminarView.mostrarMensaje("Carrito no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
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
        eliminarView.getTxtIva().setText(String.format("%.2f", carrito.getIva()));
        eliminarView.getLblTotal().setText(String.format("%.2f", carrito.getTotal()));
    }

    private void eliminarCarrito(CarritoEliminarView eliminarView) {
        String idText = eliminarView.getTxtCodigo().getText().trim();
        if (idText.isEmpty()) {
            eliminarView.mostrarMensaje("Ingrese el ID del carrito.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int id;
        try {
            id = Integer.parseInt(idText);
            if (id <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            eliminarView.mostrarMensaje("El ID debe ser un número entero positivo.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int confirmacion = eliminarView.mostrarMensajeConfirmacion(
                "¿Está seguro que desea eliminar el carrito?", "Confirmar eliminación", JOptionPane.WARNING_MESSAGE);
        if (confirmacion == 0) {
            carritoDAO.eliminarCarrtio(id);
            eliminarView.mostrarMensaje("Carrito eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCamposEliminarCarrito(eliminarView);
        }
    }

    private void limpiarCamposEliminarCarrito(CarritoEliminarView eliminarView) {
        eliminarView.getTxtCodigo().setText("");
        eliminarView.getLblSubTotal().setText("");
        eliminarView.getTxtIva().setText("");
        eliminarView.getLblTotal().setText("");
        DefaultTableModel modelo = (DefaultTableModel) eliminarView.getTblProducts().getModel();
        modelo.setRowCount(0);
    }
}