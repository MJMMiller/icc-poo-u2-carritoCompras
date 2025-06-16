package ec.edu.ups.poo.controlador;

import ec.edu.ups.poo.dao.ProductoDAO;
import ec.edu.ups.poo.modelo.Producto;
import ec.edu.ups.poo.vista.ProducEditarView;
import ec.edu.ups.poo.vista.ProductoAnadirView;
import ec.edu.ups.poo.vista.ProductoListarView;

import java.util.List;

public class ProductoController {

    private final ProductoAnadirView productoAnadirView;
    private final ProductoListarView productoListaView;
    private final ProductoDAO productoDAO;
    private final ProducEditarView productoGestionView;

    public ProductoController(ProductoDAO productoDAO,
                              ProductoAnadirView productoAnadirView,
                              ProductoListarView productoListaView,
                              ProducEditarView productoGestionView) {
        this.productoDAO = productoDAO;
        this.productoAnadirView = productoAnadirView;
        this.productoListaView = productoListaView;
        this.productoGestionView = productoGestionView;
        configurarEventos();
    }

    private void configurarEventos() {
        productoAnadirView.getBtnRegisterNewProduct().addActionListener(e -> guardarProducto());

        productoAnadirView.getBtnCleanInputs().addActionListener(e -> {
            productoAnadirView.limpiarCampos();
            productoAnadirView.habilitarCampos();
        });

        productoListaView.getBtnSearch().addActionListener(e ->
                buscarProducto(productoListaView.getLblNameProdcutSearch().getText())
        );

        productoListaView.getBtnListProducts().addActionListener(e -> listarProductos());

        productoGestionView.getBtnSearch().addActionListener(e -> buscarProductoGestion());

        productoGestionView.getTblProducts().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = productoGestionView.getSelectedRow();
                if (selectedRow != -1) {
                    int codigo = (int) productoGestionView.getTblProducts().getValueAt(selectedRow, 0);
                    Producto producto = productoDAO.buscarPorCodigo(codigo);
                    if (producto != null) {
                        productoGestionView.getLblNameProduct().setText(producto.getNombre());
                        productoGestionView.getLblPriceProduct().setText(String.valueOf(producto.getPrecio()));
                    }
                }
            }
        });

        productoGestionView.getBtnUpdate().addActionListener(e -> actualizarProducto());

        productoGestionView.getBtnDelate().addActionListener(e -> eliminarProducto());
    }

    public void guardarProducto() {
        int codigo = Integer.parseInt(productoAnadirView.getLblCodeProduct().getText());
        String nombre = productoAnadirView.getLblNameProduct().getText();
        double precio = Double.parseDouble(productoAnadirView.getLblPriceProduct().getText());

        productoDAO.crear(new Producto(codigo, nombre, precio));
        productoAnadirView.mostrarMensaje("Producto guardado correctamente", "Información", 1);
        productoAnadirView.inhabilitarCampos();
        productoAnadirView.mostrarProductos(productoDAO.listarTodos());
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

    private void buscarProductoGestion() {
        String txtCod = productoGestionView.getLblCodeProductSearch().getText();
        if (!txtCod.isEmpty()) {
            int codigo = Integer.parseInt(txtCod);
            Producto producto = productoDAO.buscarPorCodigo(codigo);
            if (producto != null) {
                productoGestionView.mostrarProductos(List.of(producto));
            } else {
                productoGestionView.mostrarMensaje("Producto no encontrado", "Información", 1);
                productoGestionView.mostrarProductos(List.of());
                productoGestionView.limpiarCampos();
            }
        } else {
            productoGestionView.mostrarMensaje("Ingresa un código para buscar", "Advertencia", 2);
        }
    }

    private void actualizarProducto() {
        int selectedRow = productoGestionView.getSelectedRow();
        if (selectedRow != -1) {
            int codigo = (int) productoGestionView.getTblProducts().getValueAt(selectedRow, 0);
            String nombre = productoGestionView.getLblNameProduct().getText();
            String txtPrecio = productoGestionView.getLblPriceProduct().getText();

            if (!nombre.isEmpty() && !txtPrecio.isEmpty()) {
                Producto producto = productoDAO.buscarPorCodigo(codigo);
                if (producto != null) {
                    double precio = Double.parseDouble(txtPrecio);
                    producto.setNombre(nombre);
                    producto.setPrecio(precio);
                    productoDAO.actualizar(producto);
                    productoGestionView.mostrarMensaje("Producto actualizado correctamente", "Información", 1);
                    productoGestionView.mostrarProductos(List.of(producto));
                }
            } else {
                productoGestionView.mostrarMensaje("Completa los datos", "Advertencia", 2);
            }
        } else {
            productoGestionView.mostrarMensaje("Selecciona un producto de la tabla", "Advertencia", 2);
        }
    }

    private void eliminarProducto() {
        int selectedRow = productoGestionView.getSelectedRow();
        if (selectedRow != -1) {
            int codigo = (int) productoGestionView.getTblProducts().getValueAt(selectedRow, 0);
            productoDAO.eliminar(codigo);
            productoGestionView.mostrarMensaje("Producto eliminado correctamente", "Información", 1);
            productoGestionView.mostrarProductos(List.of());
            productoGestionView.limpiarCampos();
        } else {
            productoGestionView.mostrarMensaje("Selecciona un producto para eliminar", "Advertencia", 2);
        }
    }
}