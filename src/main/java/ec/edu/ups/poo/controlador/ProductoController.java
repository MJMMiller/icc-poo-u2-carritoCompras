package ec.edu.ups.poo.controlador;

import ec.edu.ups.poo.dao.ProductoDAO;
import ec.edu.ups.poo.modelo.Producto;
import ec.edu.ups.poo.vista.carrito.CarritoAnadirView;
import ec.edu.ups.poo.vista.producto.ProducUpdateView;
import ec.edu.ups.poo.vista.producto.ProductDelateView;
import ec.edu.ups.poo.vista.producto.ProductoAnadirView;
import ec.edu.ups.poo.vista.producto.ProductoListarView;

import javax.swing.*;
import java.util.List;

public class ProductoController extends JInternalFrame {

    private final ProductoDAO productoDAO;
    private final ProducUpdateView productoGestionView;
    private final ProductoAnadirView productoAnadirView;
    private final ProductoListarView productoListaView;
    private final ProductDelateView productDelateView;
    private final CarritoAnadirView carritoAnadirView;

    public ProductoController(
            ProductoDAO productoDAO,
            ProductoAnadirView productoAnadirView,
            ProductoListarView productoListaView,
            ProducUpdateView productoGestionView,
            ProductDelateView productDelateView,
            CarritoAnadirView carritoAnadirView
    ) {
        this.productoDAO = productoDAO;
        this.productoAnadirView = productoAnadirView;
        this.productoListaView = productoListaView;
        this.productoGestionView = productoGestionView;
        this.productDelateView = productDelateView;
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
        productoGestionView.getBtnSearch().addActionListener(e -> buscarProductoGestion());
        productoGestionView.getBtnUpdate().addActionListener(e -> actualizarProducto());

        productDelateView.getBtnSearch().addActionListener(e -> buscarProductoGestionDelate());
        productDelateView.getBtnDelate().addActionListener(e -> eliminarProductoDelate());

        carritoAnadirView.getBtnSearchProduct().addActionListener(e -> buscarProductoEnCarrito());

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
        String txtCod = productDelateView.getLblCodeProductSearch().getText();
        if (!txtCod.isEmpty()) {
            int codigo = Integer.parseInt(txtCod);
            Producto producto = productoDAO.buscarPorCodigo(codigo);
            if (producto != null) {
                productDelateView.mostrarProductos(List.of(producto));
            } else {
                productDelateView.mostrarMensaje("Producto no encontrado", "Información", 1);
                productDelateView.limpiarCampos();
            }
        } else {
            productDelateView.mostrarMensaje("Ingresa un código para buscar", "Advertencia", 2);
        }
    }

    private void buscarProductoGestion() {
        String txtCod = productoGestionView.getLblCodeProductSearch().getText();
        if (!txtCod.isEmpty()) {
            int codigo = Integer.parseInt(txtCod);
            Producto producto = productoDAO.buscarPorCodigo(codigo);
            if (producto != null) {
                productoGestionView.mostrarProductos(List.of(producto));
            } else {
                productoGestionView.mostrarMensaje("Producto no encontrado", "Información", 1);
                productoGestionView.limpiarCampos();
            }
        } else {
            productoGestionView.mostrarMensaje("Ingresa un código para buscar", "Advertencia", 2);
        }
    }

    private void buscarProductoEnCarrito() {
        String txtCod = carritoAnadirView.getLblCodeProductSearch().getText();
        if (!txtCod.isEmpty()) {
            int codigo = Integer.parseInt(txtCod);
            Producto producto = productoDAO.buscarPorCodigo(codigo);
            if (producto != null) {
                carritoAnadirView.mostrarProductos(List.of(producto));
            } else {
                carritoAnadirView.mostrarMensaje("Producto no encontrado", "Información", 1);
                carritoAnadirView.mostrarProductos(List.of());
                carritoAnadirView.limpiarCampos();
            }
        } else {
            carritoAnadirView.mostrarMensaje("Ingresa un código para buscar", "Advertencia", 2);
        }
    }

    private void actualizarProducto() {
        String txtCod = productoGestionView.getLblCodeProductSearch().getText();
        if (!txtCod.isEmpty()) {
            int codigo = Integer.parseInt(txtCod);
            String nombre = productoGestionView.getLblNameProduct().getText();
            double precio = Double.parseDouble(productoGestionView.getLblPriceProduct().getText());

            Producto producto = new Producto(codigo, nombre, precio);
            productoDAO.actualizar(producto);
            productoGestionView.mostrarMensaje("Producto actualizado correctamente", "Información", 1);
            productoGestionView.limpiarCampos();
        } else {
            productoGestionView.mostrarMensaje("Ingresa un código para actualizar", "Advertencia", 2);
        }
    }

    private void eliminarProductoDelate() {
        String txtCod = productDelateView.getLblCodeProductSearch().getText();
        if (!txtCod.isEmpty()) {
            int codigo = Integer.parseInt(txtCod);
            int respuesta = productDelateView.mostrarMensajeDelate("¿Estás seguro de eliminar el producto?", "Confirmación", 3);
            if (respuesta == 0) {
                productoDAO.eliminar(codigo);
                productDelateView.mostrarMensaje("Producto eliminado correctamente", "Información", 1);
                productDelateView.limpiarCampos();
            }
        } else {
            productDelateView.mostrarMensaje("Ingresa un código para eliminar", "Advertencia", 2);
        }
    }
}