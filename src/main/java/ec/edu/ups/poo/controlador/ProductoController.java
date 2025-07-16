package ec.edu.ups.poo.controlador;

import ec.edu.ups.poo.dao.ProductoDAO;
import ec.edu.ups.poo.modelo.Producto;
import ec.edu.ups.poo.vista.carrito.CarritoAnadirView;
import ec.edu.ups.poo.vista.producto.ProductoEditarView;
import ec.edu.ups.poo.vista.producto.ProductoEliminarView;
import ec.edu.ups.poo.vista.producto.ProductoAnadirView;
import ec.edu.ups.poo.vista.producto.ProductoListarView;
import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.util.List;

public class ProductoController {

    private final ProductoDAO productoDAO;
    private final ProductoEditarView productoEditarView;
    private final ProductoAnadirView productoAnadirView;
    private final ProductoListarView productoListarView;
    private final ProductoEliminarView productoEliminarView;
    private final CarritoAnadirView carritoAnadirView;
    private final MensajeInternacionalizacionHandler i18n;

    public ProductoController(
            ProductoDAO productoDAO,
            ProductoAnadirView productoAnadirView,
            ProductoListarView productoListarView,
            ProductoEditarView productoEditarView,
            ProductoEliminarView productoEliminarView,
            CarritoAnadirView carritoAnadirView,
            MensajeInternacionalizacionHandler i18n
    ) {
        this.productoDAO = productoDAO;
        this.productoAnadirView = productoAnadirView;
        this.productoListarView = productoListarView;
        this.productoEditarView = productoEditarView;
        this.productoEliminarView = productoEliminarView;
        this.carritoAnadirView = carritoAnadirView;
        this.i18n = i18n;
        configurarEventos();
    }

    private void configurarEventos() {
        configurarEventosAnadir();
        configurarEventosListar();
        configurarEventosEditar();
        configurarEventosEliminar();
        configurarEventosCarrito();
    }

    private void configurarEventosAnadir() {
        productoAnadirView.getBtnRegisterNewProduct().addActionListener(e -> guardarProducto());
        productoAnadirView.getBtnCleanInputs().addActionListener(e -> {
            productoAnadirView.limpiarCampos();
            productoAnadirView.habilitarCampos();
            setearNuevoIdProducto();
        });
        setearNuevoIdProducto();
    }

    private void configurarEventosListar() {
        productoListarView.getBtnSearch().addActionListener(e ->
                buscarProducto(productoListarView.getLblNameProdcutSearch().getText())
        );
        productoListarView.getBtnListProducts().addActionListener(e -> listarProductos());
    }

    private void configurarEventosEditar() {
        productoEditarView.getBtnBuscar().addActionListener(e -> buscarProductoGestion());
        productoEditarView.getBtnActualizar().addActionListener(e -> actualistreamzarProducto());
    }

    private void configurarEventosEliminar() {
        productoEliminarView.getBtnBuscar().addActionListener(e -> buscarProductoGestionDelete());
        productoEliminarView.getBtnEliminar().addActionListener(e -> eliminarProductoDelete());
    }

    private void configurarEventosCarrito() {
        carritoAnadirView.getBtnBuscar().addActionListener(e -> buscarProductoEnCarrito());
    }

    private void guardarProducto() {
        try {
            int codigo = Integer.parseInt(productoAnadirView.getLblCodeProduct().getText());
            String nombre = productoAnadirView.getLblNameProduct().getText();
            double precio = Double.parseDouble(productoAnadirView.getLblPriceProduct().getText());

            if (codigo <= 0) {
                productoAnadirView.mostrarMensaje(
                        i18n.get("producto.error.codigo_entero_positivo"),
                        i18n.get("global.error"),
                        0
                );
                setearNuevoIdProducto();
                return;
            }
            if (precio <= 0) {
                productoAnadirView.mostrarMensaje(
                        i18n.get("producto.error.precio_positivo"),
                        i18n.get("global.error"),
                        0
                );
                return;
            }

            Producto existente = productoDAO.buscarPorCodigo(codigo);
            if (existente != null) {
                productoAnadirView.mostrarMensaje(
                        i18n.get("producto.error.id_existente"),
                        i18n.get("global.error"),
                        0
                );
                setearNuevoIdProducto();
                return;
            }

            int opcion = productoAnadirView.mostrarMensajeConfirmacion(
                    i18n.get("producto.confirmacion.guardar"),
                    i18n.get("global.confirm"),
                    JOptionPane.QUESTION_MESSAGE
            );
            if (opcion != 0) return;

            productoDAO.crear(new Producto(codigo, nombre, precio));
            productoAnadirView.mostrarMensaje(
                    i18n.get("producto.exito.guardado"),
                    i18n.get("global.success"),
                    1
            );
            productoAnadirView.inhabilitarCampos();
            productoAnadirView.mostrarProductos(productoDAO.listarTodos());
            setearNuevoIdProducto();
        } catch (NumberFormatException ex) {
            productoAnadirView.mostrarMensaje(
                    i18n.get("producto.error.precio_numerico"),
                    i18n.get("global.error"),
                    0
            );
        }
    }

    private void buscarProducto(String nombre) {
        if (!nombre.isEmpty()) {
            List<Producto> productos = productoDAO.buscarPorNombre(nombre);
            if (!productos.isEmpty()) {
                productoListarView.mostrarProductos(productos);
            } else {
                productoListarView.mostrarMensaje(
                        i18n.get("producto.info.no_encontrado_nombre"),
                        i18n.get("global.info"),
                        1
                );
                productoListarView.mostrarProductos(List.of());
            }
        } else {
            productoListarView.mostrarMensaje(
                    i18n.get("producto.warning.ingrese_nombre_buscar"),
                    i18n.get("global.warning"),
                    2
            );
        }
    }

    public void listarProductos() {
        List<Producto> productos = productoDAO.listarTodos();
        productoListarView.mostrarProductos(productos);
    }

    private void buscarProductoGestionDelete() {
        String txtCod = productoEliminarView.getTxtCodigo().getText();
        if (!txtCod.isEmpty()) {
            try {
                int codigo = Integer.parseInt(txtCod);
                if (codigo <= 0) throw new NumberFormatException();
                Producto producto = productoDAO.buscarPorCodigo(codigo);
                if (producto != null) {
                    productoEliminarView.mostrarProductos(List.of(producto));
                } else {
                    productoEliminarView.mostrarMensaje(
                            i18n.get("producto.info.no_encontrado"),
                            i18n.get("global.info"),
                            1
                    );
                    productoEliminarView.limpiarCampos();
                }
            } catch (NumberFormatException ex) {
                productoEliminarView.mostrarMensaje(
                        i18n.get("producto.error.codigo_entero_positivo"),
                        i18n.get("global.error"),
                        0
                );
                productoEliminarView.limpiarCampos();
            }
        } else {
            productoEliminarView.mostrarMensaje(
                    i18n.get("producto.warning.ingrese_codigo_buscar"),
                    i18n.get("global.warning"),
                    2
            );
        }
    }

    private void buscarProductoGestion() {
        String txtCod = productoEditarView.getTxtCodigo().getText();
        if (!txtCod.isEmpty()) {
            try {
                int codigo = Integer.parseInt(txtCod);
                if (codigo <= 0) throw new NumberFormatException();
                Producto producto = productoDAO.buscarPorCodigo(codigo);
                if (producto != null) {
                    productoEditarView.mostrarProductos(List.of(producto));
                } else {
                    productoEditarView.mostrarMensaje(
                            i18n.get("producto.info.no_encontrado"),
                            i18n.get("global.info"),
                            1
                    );
                    productoEditarView.limpiarCampos();
                }
            } catch (NumberFormatException ex) {
                productoEditarView.mostrarMensaje(
                        i18n.get("producto.error.codigo_entero_positivo"),
                        i18n.get("global.error"),
                        0
                );
                productoEditarView.limpiarCampos();
            }
        } else {
            productoEditarView.mostrarMensaje(
                    i18n.get("producto.warning.ingrese_codigo_buscar"),
                    i18n.get("global.warning"),
                    2
            );
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
                    carritoAnadirView.mostrarMensaje(
                            i18n.get("producto.info.no_encontrado"),
                            i18n.get("global.info"),
                            1
                    );
                    carritoAnadirView.mostrarProductos(List.of());
                    carritoAnadirView.limpiarCampos();
                }
            } catch (NumberFormatException ex) {
                carritoAnadirView.mostrarMensaje(
                        i18n.get("producto.error.codigo_entero_positivo"),
                        i18n.get("global.error"),
                        0
                );
                carritoAnadirView.limpiarCampos();
            }
        } else {
            carritoAnadirView.mostrarMensaje(
                    i18n.get("producto.warning.ingrese_codigo_buscar"),
                    i18n.get("global.warning"),
                    2
            );
        }
    }

    private void actualistreamzarProducto() {
        String txtCod = productoEditarView.getTxtCodigo().getText();
        if (!txtCod.isEmpty()) {
            try {
                int codigo = Integer.parseInt(txtCod);
                if (codigo <= 0) throw new NumberFormatException();
                String nombre = productoEditarView.getTxtNombre().getText();
                double precio = Double.parseDouble(productoEditarView.getTxtPrecio().getText());

                if (precio <= 0) {
                    productoEditarView.mostrarMensaje(
                            i18n.get("producto.error.precio_positivo"),
                            i18n.get("global.error"),
                            0
                    );
                    return;
                }

                int opcion = productoEditarView.mostrarMensajeConfirmacion(
                        i18n.get("producto.confirmacion.actualizar"),
                        i18n.get("global.confirm"),
                        JOptionPane.QUESTION_MESSAGE
                );
                if (opcion != 0) return;

                Producto producto = new Producto(codigo, nombre, precio);
                productoDAO.actualizar(producto);
                productoEditarView.mostrarMensaje(
                        i18n.get("producto.exito.actualizado"),
                        i18n.get("global.success"),
                        1
                );
                productoEditarView.limpiarCampos();
            } catch (NumberFormatException ex) {
                productoEditarView.mostrarMensaje(
                        i18n.get("producto.error.codigo_precio_numerico"),
                        i18n.get("global.error"),
                        0
                );
                productoEditarView.limpiarCampos();
            }
        } else {
            productoEditarView.mostrarMensaje(
                    i18n.get("producto.warning.ingrese_codigo_actualizar"),
                    i18n.get("global.warning"),
                    2
            );
        }
    }

    private void eliminarProductoDelete() {
        String txtCod = productoEliminarView.getTxtCodigo().getText();
        if (!txtCod.isEmpty()) {
            try {
                int codigo = Integer.parseInt(txtCod);
                if (codigo <= 0) throw new NumberFormatException();
                int respuesta = productoEliminarView.mostrarMensajeConfirmacion(
                        i18n.get("producto.confirmacion.eliminar"),
                        i18n.get("global.confirm"),
                        3
                );
                if (respuesta == 0) {
                    productoDAO.eliminar(codigo);
                    productoEliminarView.mostrarMensaje(
                            i18n.get("producto.exito.eliminado"),
                            i18n.get("global.success"),
                            1
                    );
                    productoEliminarView.limpiarCampos();
                }
            } catch (NumberFormatException ex) {
                productoEliminarView.mostrarMensaje(
                        i18n.get("producto.error.codigo_entero_positivo"),
                        i18n.get("global.error"),
                        0
                );
                productoEliminarView.limpiarCampos();
            }
        } else {
            productoEliminarView.mostrarMensaje(
                    i18n.get("producto.warning.ingrese_codigo_eliminar"),
                    i18n.get("global.warning"),
                    2
            );
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
}