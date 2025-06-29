package ec.edu.ups.poo.controlador;

import ec.edu.ups.poo.dao.ProductoDAO;
import ec.edu.ups.poo.modelo.Producto;
import ec.edu.ups.poo.vista.carrito.CarritoAnadirView;
import ec.edu.ups.poo.vista.producto.ProductoEditarView;
import ec.edu.ups.poo.vista.producto.ProductoEliminarView;
import ec.edu.ups.poo.vista.producto.ProductoAnadirView;
import ec.edu.ups.poo.vista.producto.ProductoListarView;

import javax.swing.*;
import java.util.List;

public class ProductoController extends JInternalFrame {

    private final ProductoDAO productoDAO;
    private final ProductoEditarView productoEdiatView;
    private final ProductoAnadirView productoAnadirView;
    private final ProductoListarView productoListaView;
    private final ProductoEliminarView productoEliminarView;
    private final CarritoAnadirView carritoAnadirView;

    public ProductoController(
            ProductoDAO productoDAO,
            ProductoAnadirView productoAnadirView,
            ProductoListarView productoListaView,
            ProductoEditarView productoEdiatView,
            ProductoEliminarView productoEliminarView,
            CarritoAnadirView carritoAnadirView
    ) {
        this.productoDAO = productoDAO;
        this.productoAnadirView = productoAnadirView;
        this.productoListaView = productoListaView;
        this.productoEdiatView = productoEdiatView;
        this.productoEliminarView = productoEliminarView;
        this.carritoAnadirView = carritoAnadirView;
        configurarEventos();
    }

    private void configurarEventos() {
        productoAnadirView.getBtnRegisterNewProduct().addActionListener(e -> guardarProducto());

        productoAnadirView.getBtnCleanInputs().addActionListener(e -> {
            productoAnadirView.limpiarCampos();
            productoAnadirView.habilitarCampos();
            setearNuevoIdProducto();
        });

        productoListaView.getBtnSearch().addActionListener(e ->
                buscarProducto(productoListaView.getLblNameProdcutSearch().getText())
        );

        productoListaView.getBtnListProducts().addActionListener(e -> listarProductos());
        productoEdiatView.getBtnBuscar().addActionListener(e -> buscarProductoGestion());
        productoEdiatView.getBtnActualizar().addActionListener(e -> actualizarProducto());

        productoEliminarView.getBtnBuscar().addActionListener(e -> buscarProductoGestionDelate());
        productoEliminarView.getBtnEliminar().addActionListener(e -> eliminarProductoDelate());

        carritoAnadirView.getBtnBuscar().addActionListener(e -> buscarProductoEnCarrito());

        if (productoAnadirView != null) {
            setearNuevoIdProducto();
        }
    }

    private void setearNuevoIdProducto() {
        int nuevoId = obtenerSiguienteIdProducto();
        productoAnadirView.getLblCodeProduct().setText(String.valueOf(nuevoId));
        productoAnadirView.getLblCodeProduct().setEditable(false);
    }

    private int obtenerSiguienteIdProducto() {
        int maxId = 0;
        for (Producto producto : productoDAO.listarTodos()) {
            if (producto.getCodigo() > maxId) {
                maxId = producto.getCodigo();
            }
        }
        return maxId + 1;
    }

    public void guardarProducto() {
        int codigo = Integer.parseInt(productoAnadirView.getLblCodeProduct().getText());
        String nombre = productoAnadirView.getLblNameProduct().getText();
        double precio = Double.parseDouble(productoAnadirView.getLblPriceProduct().getText());

        Producto existente = productoDAO.buscarPorCodigo(codigo);
        if (existente != null) {
            productoAnadirView.mostrarMensaje("ID Existente, Ingrese nuevamente ", "Error", 0);
            setearNuevoIdProducto();
            return;
        }

        int opcion = productoAnadirView.mostrarMensajeConfirmacion(
                "¿Está seguro de guardar este producto?",
                "Confirmación",
                JOptionPane.QUESTION_MESSAGE
        );
        if (opcion != 0) {
            return;
        }

        productoDAO.crear(new Producto(codigo, nombre, precio));
        productoAnadirView.mostrarMensaje("Producto guardado correctamente", "Información", 1);
        productoAnadirView.inhabilitarCampos();
        productoAnadirView.mostrarProductos(productoDAO.listarTodos());
        setearNuevoIdProducto();
    }

    private void buscarProducto(String nombre) {
        if (!nombre.isEmpty()) {
            List<Producto> productos = productoDAO.buscarPorNombre(nombre);
            if (!productos.isEmpty()) {
                productoListaView.mostrarProductos(productos);
            } else {
                productoListaView.mostrarMensaje("No se encontraron productos con ese nombre", "Información", 1);
                productoListaView.mostrarProductos(List.of());
            }
        } else {
            productoListaView.mostrarMensaje("Ingresa un nombre para buscar", "Advertencia", 2);
        }
    }

    public void listarProductos() {
        List<Producto> productos = productoDAO.listarTodos();
        productoListaView.mostrarProductos(productos);
    }

    private void buscarProductoGestionDelate() {
        String txtCod = productoEliminarView.getTxtCodigo().getText();
        if (!txtCod.isEmpty()) {
            try {
                int codigo = Integer.parseInt(txtCod);
                if (codigo <= 0) throw new NumberFormatException();
                Producto producto = productoDAO.buscarPorCodigo(codigo);
                if (producto != null) {
                    productoEliminarView.mostrarProductos(List.of(producto));
                } else {
                    productoEliminarView.mostrarMensaje("Producto no encontrado", "Información", 1);
                    productoEliminarView.limpiarCampos();
                }
            } catch (NumberFormatException ex) {
                productoEliminarView.mostrarMensaje("Ingrese un código entero positivo válido", "Error", 0);
                productoEliminarView.limpiarCampos();
            }
        } else {
            productoEliminarView.mostrarMensaje("Ingresa un código para buscar", "Advertencia", 2);
        }
    }

    private void buscarProductoGestion() {
        String txtCod = productoEdiatView.getTxtCodigo().getText();
        if (!txtCod.isEmpty()) {
            try {
                int codigo = Integer.parseInt(txtCod);
                if (codigo <= 0) throw new NumberFormatException();
                Producto producto = productoDAO.buscarPorCodigo(codigo);
                if (producto != null) {
                    productoEdiatView.mostrarProductos(List.of(producto));
                } else {
                    productoEdiatView.mostrarMensaje("Producto no encontrado", "Información", 1);
                    productoEdiatView.limpiarCampos();
                }
            } catch (NumberFormatException ex) {
                productoEdiatView.mostrarMensaje("Ingrese un código entero positivo válido", "Error", 0);
                productoEdiatView.limpiarCampos();
            }
        } else {
            productoEdiatView.mostrarMensaje("Ingresa un código para buscar", "Advertencia", 2);
        }
    }

    private void buscarProductoEnCarrito() {
        String txtCod = carritoAnadirView.getLblCodeProductSearch().getText();
        if (!txtCod.isEmpty()) {
            try {
                int codigo = Integer.parseInt(txtCod);
                if (codigo <= 0) throw new NumberFormatException();
                Producto producto = productoDAO.buscarPorCodigo(codigo);
                if (producto != null) {
                    carritoAnadirView.mostrarProductos(List.of(producto));
                } else {
                    carritoAnadirView.mostrarMensaje("Producto no encontrado", "Información", 1);
                    carritoAnadirView.mostrarProductos(List.of());
                    carritoAnadirView.limpiarCampos();
                }
            } catch (NumberFormatException ex) {
                carritoAnadirView.mostrarMensaje("Ingrese un código entero positivo válido", "Error", 0);
                carritoAnadirView.limpiarCampos();
            }
        } else {
            carritoAnadirView.mostrarMensaje("Ingresa un código para buscar", "Advertencia", 2);
        }
    }

    private void actualizarProducto() {
        String txtCod = productoEdiatView.getTxtCodigo().getText();
        if (!txtCod.isEmpty()) {
            try {
                int codigo = Integer.parseInt(txtCod);
                if (codigo <= 0) throw new NumberFormatException();
                String nombre = productoEdiatView.getTxtNombre().getText();
                double precio = Double.parseDouble(productoEdiatView.getTxtPrecio().getText());

                int opcion = productoEdiatView.mostrarMensajeConfirmacion(
                        "¿Está seguro de actualizar este producto?",
                        "Confirmación",
                        JOptionPane.QUESTION_MESSAGE
                );
                if (opcion != 0) {
                    return;
                }

                Producto producto = new Producto(codigo, nombre, precio);
                productoDAO.actualizar(producto);
                productoEdiatView.mostrarMensaje("Producto actualizado correctamente", "Información", 1);
                productoEdiatView.limpiarCampos();
            } catch (NumberFormatException ex) {
                productoEdiatView.mostrarMensaje("Ingrese un código entero positivo válido", "Error", 0);
                productoEdiatView.limpiarCampos();
            }
        } else {
            productoEdiatView.mostrarMensaje("Ingresa un código para actualizar", "Advertencia", 2);
        }
    }

    private void eliminarProductoDelate() {
        String txtCod = productoEliminarView.getTxtCodigo().getText();
        if (!txtCod.isEmpty()) {
            try {
                int codigo = Integer.parseInt(txtCod);
                if (codigo <= 0) throw new NumberFormatException();
                int respuesta = productoEliminarView.mostrarMensajeConfirmacion("¿Estás seguro de eliminar el producto?", "Confirmación", 3);
                if (respuesta == 0) {
                    productoDAO.eliminar(codigo);
                    productoEliminarView.mostrarMensaje("Producto eliminado correctamente", "Información", 1);
                    productoEliminarView.limpiarCampos();
                }
            } catch (NumberFormatException ex) {
                productoEliminarView.mostrarMensaje("Ingrese un código entero positivo válido", "Error", 0);
                productoEliminarView.limpiarCampos();
            }
        } else {
            productoEliminarView.mostrarMensaje("Ingresa un código para eliminar", "Advertencia", 2);
        }
    }
}