package ec.edu.ups.poo.vista.carrito;

import ec.edu.ups.poo.modelo.ItemCarrito;
import ec.edu.ups.poo.modelo.Producto;
import ec.edu.ups.poo.util.FormateadorUtils;
import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.util.TipoIcono;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;
import java.util.Locale;

/**
 * Vista para editar los ítems de un carrito de compras.
 * Permite agregar, eliminar y actualizar productos en el carrito, así como visualizar el resumen de totales.
 */
public class CarritoEditarView extends JInternalFrame {
    private JPanel panelAll;
    private JTextField txtCodigoProducto;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JTable tblProducts;
    private JButton btnAnadir;
    private JComboBox cbxCantidad;
    private JButton btnActualizar;
    private JLabel txtTotal;
    private JLabel txtShoppingCart;
    private JLabel lblCodigoProducto;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private JLabel lblCantidad;
    private JButton btnBuscarProducto;
    private JLabel txtTax;
    private JTextField txtIva;
    private JPanel panelInferior;
    private JPanel panelItems;
    private JPanel panelProduct;
    private JPanel panelTitle;
    private JScrollPane scroll;
    private JButton btnEliminarItem;
    private JLabel lblTitulo;
    private DefaultTableModel modelo;
    private JTextField txtCodigoCarrito;
    private JButton btnBuscarCarrito;
    private JButton btnClean;
    private JPanel panelFinal;
    private JLabel lblItemsCarrito;
    private JLabel lblCordigocCarrito;
    private JTextField txtSub;
    private JLabel lblTot;
    private JTextField txtTot;
    private JLabel lblIva;
    private JLabel lblSubTot;
    private JButton btnDelateItem;
    private MensajeInternacionalizacionHandler i18n;
    private double subtotal = 0.0;
    private double iva = 0.0;
    private double total = 0.0;

    /**
     * Constructor de la vista para editar carritos.
     * Inicializa la interfaz gráfica, carga la tabla y aplica idioma e iconos.
     * @param i18n Manejador de internacionalización para los textos de la interfaz.
     */
    public CarritoEditarView(MensajeInternacionalizacionHandler i18n) {
        this.i18n = i18n;
        setContentPane(panelAll);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 600);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        cargarTabla();
        cargarDatosCombobox();
        aplicarIdioma();
        aplicarIconos();
    }

    /**
     * Actualiza los valores de subtotal, IVA y total del carrito y refresca los campos de resumen.
     * @param subtotal Subtotal del carrito.
     * @param iva IVA aplicado al carrito.
     * @param total Total del carrito.
     */
    public void actualizarResumen(double subtotal, double iva, double total) {
        this.subtotal = subtotal;
        this.iva = iva;
        this.total = total;
        refrescarResumenValores(i18n.getLocale());
    }

    /**
     * Refresca los valores de subtotal, IVA y total en los campos de resumen según la configuración regional.
     * @param locale Configuración regional para formatear los valores.
     */
    public void refrescarResumenValores(Locale locale) {
        txtSub.setText(FormateadorUtils.formatearMoneda(subtotal, locale));
        txtIva.setText(FormateadorUtils.formatearMoneda(iva, locale));
        txtTot.setText(FormateadorUtils.formatearMoneda(total, locale));
    }

    /**
     * Carga los valores posibles en el combobox de cantidad (del 1 al 20).
     */
    public void cargarDatosCombobox(){
        cbxCantidad.removeAllItems();
        for (int i = 1; i <= 20; i++) {
            cbxCantidad.addItem(i);
        }
    }

    /**
     * Muestra un mensaje en un cuadro de diálogo.
     * @param mensaje Mensaje a mostrar.
     * @param titulo Título del cuadro de diálogo.
     * @param tipo Tipo de mensaje (por ejemplo, JOptionPane.INFORMATION_MESSAGE).
     */
    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }

    /**
     * Limpia los campos de entrada de producto.
     */
    public void limpiarCampos() {
        cbxCantidad.setSelectedIndex(0);
        txtCodigoProducto.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
    }

    /**
     * Muestra los ítems del carrito en la tabla.
     * @param items Lista de ítems del carrito.
     */
    public void mostrarItemsCarrito(List<ItemCarrito> items) {
        modelo.setRowCount(0);
        Locale locale = i18n.getLocale();
        for (ItemCarrito item : items) {
            Producto p = item.getProducto();
            String precioFormateado = FormateadorUtils.formatearMoneda(p.getPrecio(), locale);
            String totalItemFormateado = FormateadorUtils.formatearMoneda(item.getTotalItem(), locale);
            modelo.addRow(new Object[]{
                    p.getCodigo(),
                    p.getNombre(),
                    precioFormateado,
                    item.getCantidad(),
                    totalItemFormateado
            });
        }
    }

    /**
     * Muestra un cuadro de diálogo de confirmación.
     * @param mensaje Mensaje a mostrar en el cuadro de diálogo.
     * @param titulo Título del cuadro de diálogo.
     * @param tipo Tipo de mensaje (por ejemplo, JOptionPane.QUESTION_MESSAGE).
     * @return Índice del botón seleccionado por el usuario.
     */
    public int mostrarMensajeConfirmacion(String mensaje, String titulo, int tipo) {
        Object[] botones = {i18n.get("mensaje.confirmacion"), i18n.get("mensaje.cancelacion")};
        return JOptionPane.showOptionDialog(
                this, mensaje, titulo,
                JOptionPane.DEFAULT_OPTION, tipo,
                null, botones, botones[0]);
    }

    /**
     * Configura y carga la tabla de productos del carrito.
     * Establece el modelo y el formato visual de la tabla.
     */
    public void cargarTabla(){
        modelo = new DefaultTableModel(new Object[]{"Código", "Nombre", "Precio", "Cantidad", "Total Item"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblProducts.setModel(modelo);

        Color fondo = new Color(29, 30, 32);
        Color letras = Color.WHITE;

        if (panelAll != null) panelAll.setBackground(fondo);
        if (panelInferior != null) panelInferior.setBackground(fondo);
        if (panelItems != null) panelItems.setBackground(fondo);
        if (panelProduct != null) panelProduct.setBackground(fondo);
        if (panelTitle != null) panelTitle.setBackground(fondo);

        if (scroll == null && tblProducts != null) {
            scroll = (JScrollPane) tblProducts.getParent().getParent();
        }
        if (scroll != null) {
            scroll.getViewport().setBackground(fondo);
            scroll.setBackground(fondo);
        }

        if (tblProducts != null) {
            tblProducts.setBackground(fondo);
            tblProducts.setForeground(letras);
            tblProducts.setSelectionBackground(new Color(50, 50, 60));
            tblProducts.setSelectionForeground(Color.WHITE);
            tblProducts.setGridColor(fondo);

            JTableHeader header = tblProducts.getTableHeader();
            header.setBackground(fondo);
            header.setForeground(letras);
            header.setFont(header.getFont().deriveFont(Font.BOLD));
            ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            centerRenderer.setForeground(letras);
            centerRenderer.setBackground(fondo);
            for (int i = 0; i < tblProducts.getColumnCount(); i++) {
                tblProducts.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }
    }

    /**
     * Aplica los textos internacionalizados a los componentes de la interfaz.
     */
    public void aplicarIdioma() {
        setTitle(i18n.get("carrito.editar.tituloVentana"));
        lblTitulo.setText(i18n.get("carrito.editar.lbl.titulo"));
        lblCodigoProducto.setText(i18n.get("carrito.editar.lbl.codigoProducto"));
        lblNombre.setText(i18n.get("carrito.editar.lbl.nombre"));
        lblPrecio.setText(i18n.get("carrito.editar.precio"));
        lblCantidad.setText(i18n.get("carrito.editar.cantidad"));
        btnAnadir.setText(i18n.get("carrito.editar.btn.anadir"));
        btnActualizar.setText(i18n.get("carrito.editar.btn.actualizar"));
        btnBuscarProducto.setText(i18n.get("producto.buscar.btn.buscar.producto"));
        btnBuscarCarrito.setText(i18n.get("carrito.buscar.buscar.btn.buscar.carrito"));
        btnClean.setText(i18n.get("carrito.limpiar.btn.limpiar"));
        btnEliminarItem.setText(i18n.get("carrito.editar.btn.eliminar.item"));
        lblItemsCarrito.setText(i18n.get("carrito.items.lbl.tituloItems"));
        lblCordigocCarrito.setText(i18n.get("carrito.items.lbl.codigoCarrito"));
        tblProducts.getColumnModel().getColumn(0).setHeaderValue(i18n.get("carrito.listar.item..tbl.codigo"));
        tblProducts.getColumnModel().getColumn(1).setHeaderValue(i18n.get("carrito.listar.item..tbl.nombre"));
        tblProducts.getColumnModel().getColumn(2).setHeaderValue(i18n.get("carrito.listar.item.tbl.precio"));
        tblProducts.getColumnModel().getColumn(3).setHeaderValue(i18n.get("carrito.listar.item.tbl.cantidad"));
        tblProducts.getColumnModel().getColumn(4).setHeaderValue(i18n.get("carrito.listar.item.tbl.totalItem"));
        tblProducts.getTableHeader().repaint();
    }

    /**
     * Aplica los iconos a los componentes de la interfaz.
     */
    public void aplicarIconos(){
        setFrameIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.CARRITO));
        btnEliminarItem.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.ElIMINAR));
        btnAnadir.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.GUARDAR_TODO));
        btnActualizar.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.ACTUALIZAR));
        btnBuscarProducto.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.BUSCAR));
        btnBuscarCarrito.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.BUSCAR));
        btnClean.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.CLEAN));
    }

    /**
     * Refresca la tabla de productos del carrito.
     */
    public void refrescarTabla() {
        ((DefaultTableModel) tblProducts.getModel()).fireTableDataChanged();
        tblProducts.getTableHeader().repaint();
    }

    /**
     * Obtiene el panel principal de la vista.
     * @return El panel principal.
     */
    public JPanel getPanelAll() {
        return panelAll;
    }

    /**
     * Establece el panel principal de la vista.
     * @param panelAll El panel principal.
     */
    public void setPanelAll(JPanel panelAll) {
        this.panelAll = panelAll;
    }

    /**
     * Obtiene el campo de texto para el código del producto.
     * @return El campo de texto del código del producto.
     */
    public JTextField getTxtCodigoProducto() {
        return txtCodigoProducto;
    }

    /**
     * Establece el campo de texto para el código del producto.
     * @param txtCodigoProducto El campo de texto del código del producto.
     */
    public void setTxtCodigoProducto(JTextField txtCodigoProducto) {
        this.txtCodigoProducto = txtCodigoProducto;
    }

    /**
     * Obtiene el campo de texto para el nombre del producto.
     * @return El campo de texto del nombre del producto.
     */
    public JTextField getTxtNombre() {
        return txtNombre;
    }

    /**
     * Establece el campo de texto para el nombre del producto.
     * @param txtNombre El campo de texto del nombre del producto.
     */
    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }

    /**
     * Obtiene el campo de texto para el precio del producto.
     * @return El campo de texto del precio del producto.
     */
    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    /**
     * Establece el campo de texto para el precio del producto.
     * @param txtPrecio El campo de texto del precio del producto.
     */
    public void setTxtPrecio(JTextField txtPrecio) {
        this.txtPrecio = txtPrecio;
    }

    /**
     * Obtiene la tabla de productos del carrito.
     * @return La tabla de productos.
     */
    public JTable getTblProducts() {
        return tblProducts;
    }

    /**
     * Establece la tabla de productos del carrito.
     * @param tblProducts La tabla de productos.
     */
    public void setTblProducts(JTable tblProducts) {
        this.tblProducts = tblProducts;
    }

    /**
     * Obtiene el botón para añadir productos al carrito.
     * @return El botón de añadir.
     */
    public JButton getBtnAnadir() {
        return btnAnadir;
    }

    /**
     * Establece el botón para añadir productos al carrito.
     * @param btnAnadir El botón de añadir.
     */
    public void setBtnAnadir(JButton btnAnadir) {
        this.btnAnadir = btnAnadir;
    }

    /**
     * Obtiene el combobox para seleccionar la cantidad de productos.
     * @return El combobox de cantidad.
     */
    public JComboBox getCbxCantidad() {
        return cbxCantidad;
    }

    /**
     * Establece el combobox para seleccionar la cantidad de productos.
     * @param cbxCantidad El combobox de cantidad.
     */
    public void setCbxCantidad(JComboBox cbxCantidad) {
        this.cbxCantidad = cbxCantidad;
    }

    /**
     * Obtiene el botón para actualizar el carrito.
     * @return El botón de actualizar.
     */
    public JButton getBtnActualizar() {
        return btnActualizar;
    }

    /**
     * Establece el botón para actualizar el carrito.
     * @param btnActualizar El botón de actualizar.
     */
    public void setBtnActualizar(JButton btnActualizar) {
        this.btnActualizar = btnActualizar;
    }

    /**
     * Obtiene la etiqueta del total del carrito.
     * @return La etiqueta del total.
     */
    public JLabel getTxtTotal() {
        return txtTotal;
    }

    /**
     * Establece la etiqueta del total del carrito.
     * @param txtTotal La etiqueta del total.
     */
    public void setTxtTotal(JLabel txtTotal) {
        this.txtTotal = txtTotal;
    }

    /**
     * Obtiene la etiqueta del carrito de compras.
     * @return La etiqueta del carrito de compras.
     */
    public JLabel getTxtShoppingCart() {
        return txtShoppingCart;
    }

    /**
     * Establece la etiqueta del carrito de compras.
     * @param txtShoppingCart La etiqueta del carrito de compras.
     */
    public void setTxtShoppingCart(JLabel txtShoppingCart) {
        this.txtShoppingCart = txtShoppingCart;
    }

    /**
     * Obtiene la etiqueta del código del producto.
     * @return La etiqueta del código del producto.
     */
    public JLabel getLblCodigoProducto() {
        return lblCodigoProducto;
    }

    /**
     * Establece la etiqueta del código del producto.
     * @param lblCodigoProducto La etiqueta del código del producto.
     */
    public void setLblCodigoProducto(JLabel lblCodigoProducto) {
        this.lblCodigoProducto = lblCodigoProducto;
    }

    /**
     * Obtiene la etiqueta del nombre del producto.
     * @return La etiqueta del nombre del producto.
     */
    public JLabel getLblNombre() {
        return lblNombre;
    }

    /**
     * Establece la etiqueta del nombre del producto.
     * @param lblNombre La etiqueta del nombre del producto.
     */
    public void setLblNombre(JLabel lblNombre) {
        this.lblNombre = lblNombre;
    }

    /**
     * Obtiene la etiqueta del precio del producto.
     * @return La etiqueta del precio del producto.
     */
    public JLabel getLblPrecio() {
        return lblPrecio;
    }

    /**
     * Establece la etiqueta del precio del producto.
     * @param lblPrecio La etiqueta del precio del producto.
     */
    public void setLblPrecio(JLabel lblPrecio) {
        this.lblPrecio = lblPrecio;
    }

    /**
     * Obtiene la etiqueta de la cantidad de productos.
     * @return La etiqueta de la cantidad.
     */
    public JLabel getLblCantidad() {
        return lblCantidad;
    }

    /**
     * Establece la etiqueta de la cantidad de productos.
     * @param lblCantidad La etiqueta de la cantidad.
     */
    public void setLblCantidad(JLabel lblCantidad) {
        this.lblCantidad = lblCantidad;
    }

    /**
     * Obtiene el botón para buscar productos.
     * @return El botón de buscar producto.
     */
    public JButton getBtnBuscarProducto() {
        return btnBuscarProducto;
    }

    /**
     * Establece el botón para buscar productos.
     * @param btnBuscarProducto El botón de buscar producto.
     */
    public void setBtnBuscarProducto(JButton btnBuscarProducto) {
        this.btnBuscarProducto = btnBuscarProducto;
    }

    /**
     * Obtiene la etiqueta del IVA.
     * @return La etiqueta del IVA.
     */
    public JLabel getTxtTax() {
        return txtTax;
    }

    /**
     * Establece la etiqueta del IVA.
     * @param txtTax La etiqueta del IVA.
     */
    public void setTxtTax(JLabel txtTax) {
        this.txtTax = txtTax;
    }

    /**
     * Obtiene el campo de texto para el IVA.
     * @return El campo de texto del IVA.
     */
    public JTextField getTxtIva() {
        return txtIva;
    }

    /**
     * Establece el campo de texto para el IVA.
     * @param txtIva El campo de texto del IVA.
     */
    public void setTxtIva(JTextField txtIva) {
        this.txtIva = txtIva;
    }

    /**
     * Obtiene el panel inferior de la vista.
     * @return El panel inferior.
     */
    public JPanel getPanelInferior() {
        return panelInferior;
    }

    /**
     * Establece el panel inferior de la vista.
     * @param panelInferior El panel inferior.
     */
    public void setPanelInferior(JPanel panelInferior) {
        this.panelInferior = panelInferior;
    }

    /**
     * Obtiene el panel de ítems del carrito.
     * @return El panel de ítems.
     */
    public JPanel getPanelItems() {
        return panelItems;
    }

    /**
     * Establece el panel de ítems del carrito.
     * @param panelItems El panel de ítems.
     */
    public void setPanelItems(JPanel panelItems) {
        this.panelItems = panelItems;
    }

    /**
     * Obtiene el panel de producto.
     * @return El panel de producto.
     */
    public JPanel getPanelProduct() {
        return panelProduct;
    }

    /**
     * Establece el panel de producto.
     * @param panelProduct El panel de producto.
     */
    public void setPanelProduct(JPanel panelProduct) {
        this.panelProduct = panelProduct;
    }

    /**
     * Obtiene el panel del título.
     * @return El panel del título.
     */
    public JPanel getPanelTitle() {
        return panelTitle;
    }

    /**
     * Establece el panel del título.
     * @param panelTitle El panel del título.
     */
    public void setPanelTitle(JPanel panelTitle) {
        this.panelTitle = panelTitle;
    }

    /**
     * Obtiene el scroll de la tabla de productos.
     * @return El scroll de la tabla.
     */
    public JScrollPane getScroll() {
        return scroll;
    }

    /**
     * Establece el scroll de la tabla de productos.
     * @param scroll El scroll de la tabla.
     */
    public void setScroll(JScrollPane scroll) {
        this.scroll = scroll;
    }

    /**
     * Obtiene el botón para eliminar ítems del carrito.
     * @return El botón de eliminar ítem.
     */
    public JButton getBtnEliminarItem() {
        return btnEliminarItem;
    }

    /**
     * Establece el botón para eliminar ítems del carrito.
     * @param btnEliminarItem El botón de eliminar ítem.
     */
    public void setBtnEliminarItem(JButton btnEliminarItem) {
        this.btnEliminarItem = btnEliminarItem;
    }

    /**
     * Obtiene la etiqueta del título de la vista.
     * @return La etiqueta del título.
     */
    public JLabel getLblTitulo() {
        return lblTitulo;
    }

    /**
     * Establece la etiqueta del título de la vista.
     * @param lblTitulo La etiqueta del título.
     */
    public void setLblTitulo(JLabel lblTitulo) {
        this.lblTitulo = lblTitulo;
    }

    /**
     * Obtiene el modelo de la tabla de productos.
     * @return El modelo de la tabla.
     */
    public DefaultTableModel getModelo() {
        return modelo;
    }

    /**
     * Establece el modelo de la tabla de productos.
     * @param modelo El modelo de la tabla.
     */
    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
    }

    /**
     * Obtiene el campo de texto para el código del carrito.
     * @return El campo de texto del código del carrito.
     */
    public JTextField getTxtCodigoCarrito() {
        return txtCodigoCarrito;
    }

    /**
     * Establece el campo de texto para el código del carrito.
     * @param txtCodigoCarrito El campo de texto del código del carrito.
     */
    public void setTxtCodigoCarrito(JTextField txtCodigoCarrito) {
        this.txtCodigoCarrito = txtCodigoCarrito;
    }

    /**
     * Obtiene el botón para buscar carritos.
     * @return El botón de buscar carrito.
     */
    public JButton getBtnBuscarCarrito() {
        return btnBuscarCarrito;
    }

    /**
     * Establece el botón para buscar carritos.
     * @param btnBuscarCarrito El botón de buscar carrito.
     */
    public void setBtnBuscarCarrito(JButton btnBuscarCarrito) {
        this.btnBuscarCarrito = btnBuscarCarrito;
    }

    /**
     * Obtiene el botón para limpiar los campos.
     * @return El botón de limpiar.
     */
    public JButton getBtnClean() {
        return btnClean;
    }

    /**
     * Establece el botón para limpiar los campos.
     * @param btnClean El botón de limpiar.
     */
    public void setBtnClean(JButton btnClean) {
        this.btnClean = btnClean;
    }

    /**
     * Obtiene el panel final de la vista.
     * @return El panel final.
     */
    public JPanel getPanelFinal() {
        return panelFinal;
    }

    /**
     * Establece el panel final de la vista.
     * @param panelFinal El panel final.
     */
    public void setPanelFinal(JPanel panelFinal) {
        this.panelFinal = panelFinal;
    }

    /**
     * Obtiene la etiqueta de los ítems del carrito.
     * @return La etiqueta de los ítems del carrito.
     */
    public JLabel getLblItemsCarrito() {
        return lblItemsCarrito;
    }

    /**
     * Establece la etiqueta de los ítems del carrito.
     * @param lblItemsCarrito La etiqueta de los ítems del carrito.
     */
    public void setLblItemsCarrito(JLabel lblItemsCarrito) {
        this.lblItemsCarrito = lblItemsCarrito;
    }

    /**
     * Obtiene la etiqueta del código del carrito.
     * @return La etiqueta del código del carrito.
     */
    public JLabel getLblCordigocCarrito() {
        return lblCordigocCarrito;
    }

    /**
     * Establece la etiqueta del código del carrito.
     * @param lblCordigocCarrito La etiqueta del código del carrito.
     */
    public void setLblCordigocCarrito(JLabel lblCordigocCarrito) {
        this.lblCordigocCarrito = lblCordigocCarrito;
    }

    /**
     * Obtiene el campo de texto para el subtotal.
     * @return El campo de texto del subtotal.
     */
    public JTextField getTxtSub() {
        return txtSub;
    }

    /**
     * Establece el campo de texto para el subtotal.
     * @param txtSub El campo de texto del subtotal.
     */
    public void setTxtSub(JTextField txtSub) {
        this.txtSub = txtSub;
    }

    /**
     * Obtiene la etiqueta del total.
     * @return La etiqueta del total.
     */
    public JLabel getLblTot() {
        return lblTot;
    }

    /**
     * Establece la etiqueta del total.
     * @param lblTot La etiqueta del total.
     */
    public void setLblTot(JLabel lblTot) {
        this.lblTot = lblTot;
    }

    /**
     * Obtiene el campo de texto para el total.
     * @return El campo de texto del total.
     */
    public JTextField getTxtTot() {
        return txtTot;
    }

    /**
     * Establece el campo de texto para el total.
     * @param txtTot El campo de texto del total.
     */
    public void setTxtTot(JTextField txtTot) {
        this.txtTot = txtTot;
    }

    /**
     * Obtiene la etiqueta del IVA.
     * @return La etiqueta del IVA.
     */
    public JLabel getLblIva() {
        return lblIva;
    }

    /**
     * Establece la etiqueta del IVA.
     * @param lblIva La etiqueta del IVA.
     */
    public void setLblIva(JLabel lblIva) {
        this.lblIva = lblIva;
    }

    /**
     * Obtiene la etiqueta del subtotal.
     * @return La etiqueta del subtotal.
     */
    public JLabel getLblSubTot() {
        return lblSubTot;
    }

    /**
     * Establece la etiqueta del subtotal.
     * @param lblSubTot La etiqueta del subtotal.
     */
    public void setLblSubTot(JLabel lblSubTot) {
        this.lblSubTot = lblSubTot;
    }

    /**
     * Obtiene el botón para eliminar ítems del carrito (alternativo).
     * @return El botón de eliminar ítem.
     */
    public JButton getBtnDelateItem() {
        return btnDelateItem;
    }

    /**
     * Establece el botón para eliminar ítems del carrito (alternativo).
     * @param btnDelateItem El botón de eliminar ítem.
     */
    public void setBtnDelateItem(JButton btnDelateItem) {
        this.btnDelateItem = btnDelateItem;
    }

    /**
     * Obtiene el manejador de internacionalización.
     * @return El manejador de internacionalización.
     */
    public MensajeInternacionalizacionHandler getI18n() {
        return i18n;
    }

    /**
     * Establece el manejador de internacionalización.
     * @param i18n El manejador de internacionalización.
     */
    public void setI18n(MensajeInternacionalizacionHandler i18n) {
        this.i18n = i18n;
    }

    /**
     * Obtiene el subtotal actual del carrito.
     * @return El subtotal.
     */
    public double getSubtotal() {
        return subtotal;
    }

    /**
     * Establece el subtotal actual del carrito.
     * @param subtotal El subtotal.
     */
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    /**
     * Obtiene el IVA actual del carrito.
     * @return El IVA.
     */
    public double getIva() {
        return iva;
    }

    /**
     * Establece el IVA actual del carrito.
     * @param iva El IVA.
     */
    public void setIva(double iva) {
        this.iva = iva;
    }

    /**
     * Obtiene el total actual del carrito.
     * @return El total.
     */
    public double getTotal() {
        return total;
    }

    /**
     * Establece el total actual del carrito.
     * @param total El total.
     */
    public void setTotal(double total) {
        this.total = total;
    }

}

