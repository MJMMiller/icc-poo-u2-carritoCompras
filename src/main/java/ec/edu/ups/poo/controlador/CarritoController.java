package ec.edu.ups.poo.controlador;

import ec.edu.ups.poo.dao.CarritoDAO;
import ec.edu.ups.poo.dao.ProductoDAO;
import ec.edu.ups.poo.dao.UsuarioDAO;
import ec.edu.ups.poo.modelo.Carrito;
import ec.edu.ups.poo.modelo.ItemCarrito;
import ec.edu.ups.poo.modelo.Producto;
import ec.edu.ups.poo.modelo.Rol;
import ec.edu.ups.poo.modelo.Usuario;
import ec.edu.ups.poo.util.FormateadorUtils;
import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.vista.carrito.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Controlador para la gestión de carritos de compras.
 * Permite agregar, editar, listar y eliminar carritos e items.
 */
public class CarritoController {

    private final CarritoDAO carritoDAO;
    private final ProductoDAO productoDAO;
    private final CarritoAnadirView carritoView;
    private final CarritoEditarView carritoEditarView;
    private final Usuario usuarioAutenticado;
    private final MensajeInternacionalizacionHandler i18n;
    private final UsuarioDAO usuarioDAO;
    private static int contadorCarrito = 1;
    private Carrito carritoCargado = null;
    private List<ItemCarrito> copiaItemsEdit = null;
    public CarritoListarItemsView itemsView = null;

    /**
     * Constructor de CarritoController.
     * Inicializa los DAOs, vistas y usuario autenticado, y configura los eventos.
     *
     * @param carritoDAO DAO para operaciones de carrito.
     * @param productoDAO DAO para operaciones de producto.
     * @param carritoView Vista para añadir carritos.
     * @param carritoEditarView Vista para editar carritos.
     * @param usuarioAutenticado Usuario autenticado en sesión.
     * @param i18n Manejador de internacionalización de mensajes.
     * @param usuarioDAO DAO para operaciones de usuario.
     */
    public CarritoController(
            CarritoDAO carritoDAO,
            ProductoDAO productoDAO,
            CarritoAnadirView carritoView,
            CarritoEditarView carritoEditarView,
            Usuario usuarioAutenticado,
            MensajeInternacionalizacionHandler i18n,
            UsuarioDAO usuarioDAO
    ) {
        this.i18n = i18n;
        this.carritoDAO = carritoDAO;
        this.productoDAO = productoDAO;
        this.carritoView = carritoView;
        this.carritoEditarView = carritoEditarView;
        this.usuarioAutenticado = usuarioAutenticado;
        this.usuarioDAO = usuarioDAO;
        configurarEventos();
    }

    /**
     * Configura los eventos de las vistas de añadir y editar carrito.
     * No recibe parámetros ni retorna valores.
     */
    private void configurarEventos() {
        carritoView.getBtnAnadir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarAlCarrito();
            }
        });
        carritoView.getBtnEliminarItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarItemSeleccionado();
            }
        });
        carritoView.getBtnCancel().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vaciarCarrito();
            }
        });
        carritoView.getBtnSave().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarCarrito();
            }
        });

        carritoEditarView.getBtnBuscarCarrito().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarYMostrarCarritoEditar();
            }
        });
        carritoEditarView.getBtnActualizar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarCambiosEdicion();
            }
        });
        carritoEditarView.getBtnEliminarItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarItemSeleccionadoEditar();
            }
        });
        carritoEditarView.getBtnClean().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCarritoEditar();
            }
        });
        carritoEditarView.getBtnBuscarProducto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProductoParaEditar();
            }
        });
        carritoEditarView.getBtnAnadir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarProductoAlCarritoEditar();
            }
        });
    }

    /**
     * Configura los eventos para la vista de listar carritos.
     *
     * @param carritoListarView Vista para listar carritos.
     */
    public void configurarEventosListar(CarritoListarView carritoListarView) {
        carritoListarView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarYMostrarCarritoPorId(carritoListarView);
            }
        });
        carritoListarView.getBtnListar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarCarritos(carritoListarView);
            }
        });
        carritoListarView.getBtnVerCarrito().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verCarrito(carritoListarView);
            }
        });
    }

    /**
     * Configura los eventos para la vista de eliminar carritos.
     *
     * @param eliminarView Vista para eliminar carritos.
     */
    public void configurarEventosEliminar(CarritoEliminarView eliminarView) {
        eliminarView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarCarritoAEliminar(eliminarView);
            }
        });
        eliminarView.getBtnEliminar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarCarrito(eliminarView);
            }
        });
    }

    /**
     * Agrega un producto al carrito actual.
     * Valida los datos y actualiza la tabla de productos.
     * No recibe parámetros ni retorna valores.
     */
    private void agregarAlCarrito() {
        String codigoTexto = carritoView.getLblCodeProductSearch().getText();
        Object cantidadObj = carritoView.getCbxCantidad().getSelectedItem();

        if (codigoTexto == null || codigoTexto.trim().isEmpty()) {
            carritoView.mostrarMensaje(i18n.get("carrito.error.codigo_producto_vacio"), i18n.get("global.error"), JOptionPane.ERROR_MESSAGE);
            return;
        }

        int codigo;
        try {
            codigo = Integer.parseInt(codigoTexto.trim());
            if (codigo <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            carritoView.mostrarMensaje(i18n.get("carrito.error.codigo_producto_invalido"), i18n.get("global.error"), JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (cantidadObj == null) {
            carritoView.mostrarMensaje(i18n.get("carrito.error.cantidad_no_seleccionada"), i18n.get("global.error"), JOptionPane.ERROR_MESSAGE);
            return;
        }
        int cantidad = (int) cantidadObj;

        Producto producto = productoDAO.buscarPorCodigo(codigo);
        if (producto == null) {
            carritoView.mostrarMensaje(i18n.get("carrito.error.producto_no_encontrado"), i18n.get("global.error"), JOptionPane.ERROR_MESSAGE);
            return;
        }

        carritoDAO.agregarProducto(producto, cantidad);
        actualizarTabla();
        carritoView.limpiarCampos();
    }

    /**
     * Elimina el item seleccionado del carrito actual.
     * Muestra confirmación antes de eliminar.
     * No recibe parámetros ni retorna valores.
     */
    private void eliminarItemSeleccionado() {
        int fila = carritoView.getTblProducts().getSelectedRow();
        if (fila != -1) {
            int codigo = (int) carritoView.getTblProducts().getValueAt(fila, 0);
            int respuesta = carritoView.mostrarMensajeConfirmacion(
                    i18n.get("carrito.confirm.eliminar_item"),
                    i18n.get("global.confirm"), JOptionPane.WARNING_MESSAGE
            );
            if (respuesta == 0) {
                carritoDAO.eliminarProducto(codigo);
                actualizarTabla();
            }
        } else {
            carritoView.mostrarMensaje(i18n.get("carrito.error.seleccione_item_eliminar"), i18n.get("global.warning"), JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Vacía todos los items del carrito actual.
     * No recibe parámetros ni retorna valores.
     */
    private void vaciarCarrito() {
        carritoDAO.vaciarCarrito();
        actualizarTabla();
    }

    /**
     * Actualiza la tabla de productos en la vista del carrito.
     * Calcula y muestra subtotal, IVA y total.
     * No recibe parámetros ni retorna valores.
     */
    private void actualizarTabla() {
        List<ItemCarrito> items = carritoDAO.obtenerItems();
        carritoView.mostrarItemsCarrito(items);

        double subtotal = carritoDAO.calcularTotal();
        double iva = subtotal * 0.15;
        double total = subtotal + iva;
        carritoView.getTxtSubTot().setText(FormateadorUtils.formatearMoneda(subtotal, i18n.getLocale()));
        carritoView.getTxtIva().setText(FormateadorUtils.formatearMoneda(iva, i18n.getLocale()));
        carritoView.getTxtTot().setText(FormateadorUtils.formatearMoneda(total, i18n.getLocale()));
    }

    /**
     * Guarda el carrito actual en el sistema y lo asocia al usuario autenticado.
     * Muestra mensajes de éxito o error según corresponda.
     * No recibe parámetros ni retorna valores.
     */
    private void guardarCarrito() {
        if (carritoDAO.estaVacio()) {
            carritoView.mostrarMensaje(i18n.get("carrito.error.vacio"), i18n.get("global.error"), JOptionPane.ERROR_MESSAGE);
        } else {
            int respuesta = carritoView.mostrarMensajeConfirmacion(i18n.get("carrito.confirm.guardar"), i18n.get("global.confirm"), JOptionPane.QUESTION_MESSAGE);
            if (respuesta == 0) {
                List<ItemCarrito> items = carritoDAO.obtenerItems();
                double subtotal = carritoDAO.calcularTotal();
                double iva = subtotal * 0.15;
                double total = subtotal + iva;
                Carrito carrito = new Carrito(contadorCarrito++, items, subtotal, iva, total, new Date(), usuarioAutenticado);

                // Guarda el carrito en archivo de carritos
                carritoDAO.guardarCarrito(carrito);

                // Añade el carrito a la instancia REAL del usuario
                Usuario usuarioReal = usuarioDAO.buscarUsuario(usuarioAutenticado.getCedula());
                usuarioReal.getCarritos().add(carrito);
                usuarioDAO.actualizarTodo(usuarioReal);

                carritoView.mostrarMensaje(i18n.get("carrito.success.guardado"), i18n.get("global.success"), JOptionPane.INFORMATION_MESSAGE);
                vaciarCarrito();
            }
        }
    }

    /**
     * Lista los carritos disponibles en la vista correspondiente.
     * Si el usuario es normal, solo muestra sus propios carritos.
     *
     * @param carritoListarView Vista para listar carritos.
     */
    private void listarCarritos(CarritoListarView carritoListarView) {
        List<Carrito> carritos;
        if (usuarioAutenticado.getRol() == Rol.USUARIO) {
            carritos = carritoDAO.listarPorUsuario(usuarioAutenticado.getCedula());
            carritoListarView.getBtnBuscar().setEnabled(false);
        } else {
            carritos = carritoDAO.listarCarritos();
        }

        DefaultTableModel modelo = (DefaultTableModel) carritoListarView.getTblCarritos().getModel();
        modelo.setRowCount(0);
        for (Carrito carrito : carritos) {
            modelo.addRow(new Object[]{
                    carrito.getId(),
                    carrito.getUsuario() != null ? carrito.getUsuario().getCedula() : "N/A",
                    FormateadorUtils.formatearFecha(carrito.getFecha(), i18n.getLocale()),
                    FormateadorUtils.formatearMoneda(carrito.getSubtotal(), i18n.getLocale()),
                    FormateadorUtils.formatearMoneda(carrito.getIva(), i18n.getLocale()),
                    FormateadorUtils.formatearMoneda(carrito.getTotal(), i18n.getLocale())
            });
        }
    }

    /**
     * Busca y muestra un carrito por su ID en la vista de listado.
     *
     * @param carritoListarView Vista para listar carritos.
     */
    private void buscarYMostrarCarritoPorId(CarritoListarView carritoListarView) {
        String idText = carritoListarView.getTxtCodigo().getText().trim();
        if (idText.isEmpty()) {
            carritoListarView.mostrarMensaje(i18n.get("carrito.error.id_vacio"), i18n.get("global.error"), JOptionPane.ERROR_MESSAGE);
            return;
        }
        int id;
        try {
            id = Integer.parseInt(idText);
            if (id <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            carritoListarView.mostrarMensaje(i18n.get("carrito.error.id_invalido"), i18n.get("global.error"), JOptionPane.ERROR_MESSAGE);
            return;
        }
        Carrito carrito = carritoDAO.obtenerCarrito(id);
        DefaultTableModel modelo = (DefaultTableModel) carritoListarView.getTblCarritos().getModel();
        modelo.setRowCount(0);
        if (carrito != null) {
            modelo.addRow(new Object[]{
                    carrito.getId(),
                    carrito.getUsuario() != null ? carrito.getUsuario().getCedula() : "N/A",
                    FormateadorUtils.formatearFecha(carrito.getFecha(), i18n.getLocale()),
                    FormateadorUtils.formatearMoneda(carrito.getSubtotal(), i18n.getLocale()),
                    FormateadorUtils.formatearMoneda(carrito.getIva(), i18n.getLocale()),
                    FormateadorUtils.formatearMoneda(carrito.getTotal(), i18n.getLocale())
            });
        } else {
            carritoListarView.mostrarMensaje(i18n.get("carrito.info.no_encontrado"), i18n.get("global.info"), JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Muestra los detalles de un carrito seleccionado en la vista de listado.
     *
     * @param carritoListarView Vista para listar carritos.
     */
    private void verCarrito(CarritoListarView carritoListarView) {
        int fila = carritoListarView.getTblCarritos().getSelectedRow();
        if (fila == -1) {
            carritoListarView.mostrarMensaje(i18n.get("carrito.warning.seleccione_carrito_ver"), i18n.get("global.warning"), JOptionPane.WARNING_MESSAGE);
            return;
        }
        int idCarrito = (int) carritoListarView.getTblCarritos().getValueAt(fila, 0);
        Carrito carrito = carritoDAO.obtenerCarrito(idCarrito);

        if (carrito != null) {
            String fechaFormateada = FormateadorUtils.formatearFecha(carrito.getFecha(), i18n.getLocale());
            if (itemsView == null || itemsView.isClosed()) {
                itemsView = new CarritoListarItemsView(
                        carrito.getId(),
                        carrito.getItems(),
                        carrito.getSubtotal(),
                        carrito.getIva(),
                        carrito.getTotal(),
                        carrito.getUsuario() != null ? carrito.getUsuario().getCedula() : "N/A",
                        carrito.getUsuario() != null ? carrito.getUsuario().getRol() : null,
                        fechaFormateada,
                        i18n
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
                        carrito.getUsuario() != null ? carrito.getUsuario().getCedula() : "N/A",
                        carrito.getUsuario() != null ? carrito.getUsuario().getRol() : null,
                        fechaFormateada
                );
                itemsView.setVisible(true);
                itemsView.toFront();
            }
        } else {
            carritoListarView.mostrarMensaje(i18n.get("carrito.error.no_encontrado"), i18n.get("global.error"), JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Busca y muestra los datos de un carrito para editar.
     * Carga los items en una copia temporal para edición.
     * No recibe parámetros ni retorna valores.
     */
    private void buscarYMostrarCarritoEditar() {
        String codigoTexto = carritoEditarView.getTxtCodigoCarrito().getText().trim();
        if (codigoTexto.isEmpty()) {
            carritoEditarView.mostrarMensaje(i18n.get("carrito.error.codigo_carrito_vacio"), i18n.get("global.error"), JOptionPane.ERROR_MESSAGE);
            limpiarCarritoEditar();
            return;
        }
        int codigo;
        try {
            codigo = Integer.parseInt(codigoTexto);
            if (codigo <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            carritoEditarView.mostrarMensaje(i18n.get("carrito.error.codigo_carrito_invalido"), i18n.get("global.error"), JOptionPane.ERROR_MESSAGE);
            limpiarCarritoEditar();
            return;
        }
        Carrito carrito = carritoDAO.obtenerCarrito(codigo);
        if (carrito == null) {
            carritoEditarView.mostrarMensaje(i18n.get("carrito.error.carrito_no_encontrado"), i18n.get("global.error"), JOptionPane.ERROR_MESSAGE);
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
        actualizarResumenEditar(carritoCargado.getSubtotal(), carritoCargado.getIva(), carritoCargado.getTotal());
    }

    /**
     * Guarda los cambios realizados en la edición de un carrito.
     * Actualiza los totales y persiste los cambios.
     * No recibe parámetros ni retorna valores.
     */
    private void guardarCambiosEdicion() {
        if (carritoCargado == null || copiaItemsEdit == null) {
            carritoEditarView.mostrarMensaje(i18n.get("carrito.error.no_carrito_para_editar"), i18n.get("global.error"), JOptionPane.ERROR_MESSAGE);
            return;
        }
        int respuesta = carritoEditarView.mostrarMensajeConfirmacion(
                i18n.get("carrito.confirm.guardar_cambios"),
                i18n.get("global.confirm"), JOptionPane.WARNING_MESSAGE
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

        carritoDAO.actualizarCarrito(carritoCargado);

        // --- Actualiza el carrito en el usuario autenticado y guarda ---
        boolean actualizado = false;
        for (int i = 0; i < usuarioAutenticado.getCarritos().size(); i++) {
            if (usuarioAutenticado.getCarritos().get(i).getId() == carritoCargado.getId()) {
                usuarioAutenticado.getCarritos().set(i, carritoCargado);
                actualizado = true;
                break;
            }
        }
        if (!actualizado) {
            usuarioAutenticado.getCarritos().add(carritoCargado);
        }
        usuarioDAO.actualizar(usuarioAutenticado.getCedula(), usuarioAutenticado.getContrasena(), usuarioAutenticado.getRol());

        carritoEditarView.mostrarMensaje(i18n.get("carrito.success.cambios_guardados"), i18n.get("global.success"), JOptionPane.INFORMATION_MESSAGE);
        carritoEditarView.mostrarItemsCarrito(copiaItemsEdit);
        actualizarResumenEditar(carritoCargado.getSubtotal(), carritoCargado.getIva(), carritoCargado.getTotal());
    }

    /**
     * Elimina el item seleccionado de la copia temporal de items en edición.
     * Actualiza la vista y los totales.
     * No recibe parámetros ni retorna valores.
     */
    private void eliminarItemSeleccionadoEditar() {
        if (copiaItemsEdit == null) {
            carritoEditarView.mostrarMensaje(i18n.get("carrito.error.no_carrito_para_editar"), i18n.get("global.error"), JOptionPane.ERROR_MESSAGE);
            return;
        }
        int fila = carritoEditarView.getTblProducts().getSelectedRow();
        if (fila != -1) {
            int codigo = (int) carritoEditarView.getTblProducts().getValueAt(fila, 0);
            int respuesta = carritoEditarView.mostrarMensajeConfirmacion(
                    i18n.get("carrito.confirm.eliminar_item"),
                    i18n.get("global.confirm"), JOptionPane.WARNING_MESSAGE
            );
            if (respuesta == 0) {
                copiaItemsEdit.removeIf(item -> item.getProducto().getCodigo() == codigo);
                carritoEditarView.mostrarItemsCarrito(copiaItemsEdit);
                recalcularTotalesEdicionTemporal();
                double subtotal = sumarSubtotal(copiaItemsEdit);
                double iva = subtotal * 0.15;
                double total = subtotal + iva;
                actualizarResumenEditar(subtotal, iva, total);
            }
        } else {
            carritoEditarView.mostrarMensaje(i18n.get("carrito.error.seleccione_item_eliminar"), i18n.get("global.warning"), JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Limpia la copia temporal de items en edición y actualiza la vista.
     * No recibe parámetros ni retorna valores.
     */
    private void limpiarCarritoEditar() {
        if (copiaItemsEdit != null) {
            copiaItemsEdit.clear();
            carritoEditarView.mostrarItemsCarrito(copiaItemsEdit);
        }
        actualizarResumenEditar(0, 0, 0);
    }

    /**
     * Busca un producto por su código para añadirlo en la edición del carrito.
     * Muestra los datos del producto si existe.
     * No recibe parámetros ni retorna valores.
     */
    private void buscarProductoParaEditar() {
        String codigoTexto = carritoEditarView.getTxtCodigoProducto().getText().trim();
        if (codigoTexto.isEmpty()) {
            carritoEditarView.mostrarMensaje(i18n.get("carrito.error.codigo_producto_vacio"), i18n.get("global.error"), JOptionPane.ERROR_MESSAGE);
            return;
        }
        int codigo;
        try {
            codigo = Integer.parseInt(codigoTexto);
            if (codigo <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            carritoEditarView.mostrarMensaje(i18n.get("carrito.error.codigo_producto_invalido"), i18n.get("global.error"), JOptionPane.ERROR_MESSAGE);
            return;
        }
        Producto p = productoDAO.buscarPorCodigo(codigo);
        if (p == null) {
            carritoEditarView.mostrarMensaje(i18n.get("carrito.error.producto_no_encontrado"), i18n.get("global.error"), JOptionPane.ERROR_MESSAGE);
            carritoEditarView.getTxtNombre().setText("");
            carritoEditarView.getTxtPrecio().setText("");
        } else {
            carritoEditarView.getTxtNombre().setText(p.getNombre());
            carritoEditarView.getTxtPrecio().setText(FormateadorUtils.formatearMoneda(p.getPrecio(), i18n.getLocale()));
        }
    }

    /**
     * Agrega un producto a la copia temporal de items en edición.
     * Si el producto ya existe, suma la cantidad.
     * No recibe parámetros ni retorna valores.
     */
    private void agregarProductoAlCarritoEditar() {
        if (copiaItemsEdit == null) {
            carritoEditarView.mostrarMensaje(i18n.get("carrito.error.no_carrito_para_editar"), i18n.get("global.error"), JOptionPane.ERROR_MESSAGE);
            return;
        }
        String codigoTexto = carritoEditarView.getTxtCodigoProducto().getText().trim();
        Object cantidadObj = carritoEditarView.getCbxCantidad().getSelectedItem();
        if (codigoTexto.isEmpty() || cantidadObj == null) {
            carritoEditarView.mostrarMensaje(i18n.get("carrito.error.codigo_y_cantidad_vacio"), i18n.get("global.error"), JOptionPane.ERROR_MESSAGE);
            return;
        }
        int codigo;
        try {
            codigo = Integer.parseInt(codigoTexto);
            if (codigo <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            carritoEditarView.mostrarMensaje(i18n.get("carrito.error.codigo_producto_invalido"), i18n.get("global.error"), JOptionPane.ERROR_MESSAGE);
            return;
        }
        int cantidad = (int) cantidadObj;
        Producto producto = productoDAO.buscarPorCodigo(codigo);
        if (producto == null) {
            carritoEditarView.mostrarMensaje(i18n.get("carrito.error.producto_no_encontrado"), i18n.get("global.error"), JOptionPane.ERROR_MESSAGE);
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
        double subtotal = sumarSubtotal(copiaItemsEdit);
        double iva = subtotal * 0.15;
        double total = subtotal + iva;
        actualizarResumenEditar(subtotal, iva, total);
        carritoEditarView.limpiarCampos();
    }

    /**
     * Recalcula los totales (subtotal, IVA, total) de la copia temporal de items en edición.
     * No recibe parámetros ni retorna valores.
     */
    private void recalcularTotalesEdicionTemporal() {
        if (copiaItemsEdit != null && carritoEditarView != null) {
            double subtotal = sumarSubtotal(copiaItemsEdit);
            double iva = subtotal * 0.15;
            double total = subtotal + iva;
            actualizarResumenEditar(subtotal, iva, total);
        }
    }

    /**
     * Recalcula los totales (subtotal, IVA, total) del carrito cargado para edición.
     * No recibe parámetros ni retorna valores.
     */
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

    /**
     * Suma el subtotal de una lista de items de carrito.
     *
     * @param items Lista de items de carrito.
     * @return Subtotal calculado como double.
     */
    private double sumarSubtotal(List<ItemCarrito> items) {
        double subtotal = 0;
        for (ItemCarrito item : items) {
            subtotal += item.getTotalItem();
        }
        return subtotal;
    }

    /**
     * Actualiza los campos de resumen (subtotal, IVA, total) en la vista de edición de carrito.
     *
     * @param subtotal Valor del subtotal.
     * @param iva Valor del IVA.
     * @param total Valor total.
     */
    private void actualizarResumenEditar(double subtotal, double iva, double total) {
        if (carritoEditarView.getTxtSub() != null) {
            carritoEditarView.getTxtSub().setText(FormateadorUtils.formatearMoneda(subtotal, i18n.getLocale()));
        }
        if (carritoEditarView.getTxtIva() != null) {
            carritoEditarView.getTxtIva().setText(FormateadorUtils.formatearMoneda(iva, i18n.getLocale()));
        }
        if (carritoEditarView.getTxtTot() != null) {
            carritoEditarView.getTxtTot().setText(FormateadorUtils.formatearMoneda(total, i18n.getLocale()));
        }
    }

    /**
     * Muestra los datos de un carrito en la vista de eliminación.
     * Carga los items y totales del carrito.
     *
     * @param eliminarView Vista para eliminar carritos.
     */
    private void mostrarCarritoAEliminar(CarritoEliminarView eliminarView) {
        String idText = eliminarView.getTxtCodigo().getText().trim();
        if (idText.isEmpty()) {
            eliminarView.mostrarMensaje(i18n.get("carrito.error.id_vacio"), i18n.get("global.error"), JOptionPane.ERROR_MESSAGE);
            limpiarCamposEliminarCarrito(eliminarView);
            return;
        }
        int id;
        try {
            id = Integer.parseInt(idText);
            if (id <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            eliminarView.mostrarMensaje(i18n.get("carrito.error.id_invalido"), i18n.get("global.error"), JOptionPane.ERROR_MESSAGE);
            limpiarCamposEliminarCarrito(eliminarView);
            return;
        }
        Carrito carrito = carritoDAO.obtenerCarrito(id);
        if (carrito == null) {
            eliminarView.mostrarMensaje(i18n.get("carrito.error.no_encontrado"), i18n.get("global.error"), JOptionPane.ERROR_MESSAGE);
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
                    FormateadorUtils.formatearMoneda(producto.getPrecio(), i18n.getLocale()),
                    item.getCantidad(),
                    FormateadorUtils.formatearMoneda(item.getTotalItem(), i18n.getLocale())
            });
        }
        eliminarView.getTxtSubTotal().setText(FormateadorUtils.formatearMoneda(carrito.getSubtotal(), i18n.getLocale()));
        eliminarView.getTxtIva().setText(FormateadorUtils.formatearMoneda(carrito.getIva(), i18n.getLocale()));
        eliminarView.getTxtTotal().setText(FormateadorUtils.formatearMoneda(carrito.getTotal(), i18n.getLocale()));
    }

    /**
     * Elimina un carrito por su ID y actualiza la información del usuario autenticado.
     * Muestra mensajes de confirmación y éxito.
     *
     * @param eliminarView Vista para eliminar carritos.
     */
    private void eliminarCarrito(CarritoEliminarView eliminarView) {
        String idText = eliminarView.getTxtCodigo().getText().trim();
        if (idText.isEmpty()) {
            eliminarView.mostrarMensaje(i18n.get("carrito.error.id_vacio"), i18n.get("global.error"), JOptionPane.ERROR_MESSAGE);
            return;
        }
        int id;
        try {
            id = Integer.parseInt(idText);
            if (id <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            eliminarView.mostrarMensaje(i18n.get("carrito.error.id_invalido"), i18n.get("global.error"), JOptionPane.ERROR_MESSAGE);
            return;
        }
        int confirmacion = eliminarView.mostrarMensajeConfirmacion(
                i18n.get("carrito.confirm.eliminar_carrito"), i18n.get("global.confirm"), JOptionPane.WARNING_MESSAGE);
        if (confirmacion == 0) {
            carritoDAO.eliminarCarrtio(id);

            // --- Elimina el carrito del usuario y guarda ---
            usuarioAutenticado.getCarritos().removeIf(c -> c.getId() == id);
            usuarioDAO.actualizar(usuarioAutenticado.getCedula(), usuarioAutenticado.getContrasena(), usuarioAutenticado.getRol());

            eliminarView.mostrarMensaje(i18n.get("carrito.success.eliminado"), i18n.get("global.success"), JOptionPane.INFORMATION_MESSAGE);
            limpiarCamposEliminarCarrito(eliminarView);
        }
    }

    /**
     * Limpia los campos y la tabla de la vista de eliminación de carrito.
     *
     * @param eliminarView Vista para eliminar carritos.
     */
    private void limpiarCamposEliminarCarrito(CarritoEliminarView eliminarView) {
        eliminarView.getTxtCodigo().setText("");
        eliminarView.getLblSubTotal().setText("");
        eliminarView.getTxtIva().setText("");
        eliminarView.getLblTotal().setText("");
        DefaultTableModel modelo = (DefaultTableModel) eliminarView.getTblProducts().getModel();
        modelo.setRowCount(0);
    }
}

