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
 * Vista para añadir productos a un carrito de compras.
 * Permite buscar productos, agregarlos al carrito, visualizar los ítems y mostrar el resumen de totales.
 */
public class CarritoAnadirView extends JInternalFrame {
    private JPanel panelAll;
    private JTextField lblCodeProductSearch;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JTable tblProducts;
    private JButton btnAnadir;
    private JTextField lblSubTotal;
    private JComboBox cbxCantidad;
    private JButton btnSave;
    private JButton btnCancel;
    private JLabel txtTotal;
    private JLabel txtShoppingCart;
    private JLabel lblCodigoProducto;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private JLabel lblCantidad;
    private JButton btnBuscar;
    private JLabel txtSubTotal;
    private JLabel lblIva;
    private JTextField lblTax;
    private JTextField lblTotal;
    private JPanel panelInferior;
    private JPanel panelItems;
    private JPanel panelProduct;
    private JPanel panelTitle;
    private JScrollPane scroll;
    private JButton btnEliminarItem;
    private JLabel lblTitulo;
    private JLabel lblItemsCarrito;
    private JTextField txtSubTot;
    private JTextField txtIva;
    private JTextField txtTot;
    private JLabel lblTot;
    private JLabel lblSubTot;
    private DefaultTableModel modelo;

    private MensajeInternacionalizacionHandler i18n;

    private double subtotal = 0.0;
    private double iva = 0.0;
    private double total = 0.0;

    /**
     * Constructor de la vista para añadir productos al carrito.
     * Inicializa la interfaz gráfica, carga la tabla y aplica idioma e iconos.
     * @param i18n Manejador de internacionalización para los textos de la interfaz.
     */
    public CarritoAnadirView(MensajeInternacionalizacionHandler i18n) {
        this.i18n = i18n;
        setContentPane(panelAll);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        cargarTabla();
        cargarDatosCombobox();
        aplicarIdioma();
        aplicarIconos();
    }

    /**
     * Configura y carga la tabla de productos del carrito.
     * Establece el modelo y el formato visual de la tabla.
     */
    public void cargarTabla() {
        modelo = new DefaultTableModel(new Object[]{"Código", "Nombre", "Precio", "Cantidad", "Total Item"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblProducts.setModel(modelo);

        Color fondo = new Color(29, 30, 32);
        Color letras = Color.WHITE;

        if (scroll == null) {
            scroll = (JScrollPane) tblProducts.getParent().getParent();
        }
        scroll.getViewport().setBackground(fondo);
        scroll.setBackground(fondo);

        tblProducts.setBackground(fondo);
        tblProducts.setForeground(letras);
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
    /**
     * Carga los valores posibles en el combobox de cantidad (del 1 al 20).
     */
    public void cargarDatosCombobox() {
        cbxCantidad.removeAllItems();
        for (int i = 1; i <= 20; i++) {
            cbxCantidad.addItem(i);
        }
    }
    /**
     * Muestra los datos del producto buscado en los campos de nombre y precio.
     * @param productos Lista de productos encontrados.
     */
    public void mostrarProductos(List<Producto> productos) {
        if (productos != null && !productos.isEmpty()) {
            txtNombre.setText(productos.get(0).getNombre());
            Locale locale = i18n.getLocale();
            String precioFormateado = FormateadorUtils.formatearMoneda(productos.get(0).getPrecio(), locale);
            txtPrecio.setText(precioFormateado);
        } else {
            txtNombre.setText("");
            txtPrecio.setText("");
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
        lblCodeProductSearch.setText("");
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
     * Aplica los textos internacionalizados a los componentes de la interfaz.
     */
    public void aplicarIdioma() {
        setTitle(i18n.get("carrito.anadir.tituloVentana"));
        lblTitulo.setText(i18n.get("carrito.anadir.lbl.titulo"));
        lblItemsCarrito.setText(i18n.get("carrito.anadir.lbl.items"));
        lblCodigoProducto.setText(i18n.get("carrito.anadir.lbl.codigoProducto"));
        lblCantidad.setText(i18n.get("carrito.anadir.lbl.cantidad"));
        lblNombre.setText(i18n.get("carrito.anadir.lbl.nombre"));
        lblPrecio.setText(i18n.get("carrito.anadir.lbl.precio"));
        btnAnadir.setText(i18n.get("carrito.anadir.btn.anadir"));
        btnBuscar.setText(i18n.get("carrito.anadir.btn.buscar"));
        btnSave.setText(i18n.get("carrito.anadir.btn.guardar"));
        btnCancel.setText(i18n.get("carrito.anadir.btn.cancelar"));
        btnEliminarItem.setText(i18n.get("carrito.anadir.btn.eliminar.item"));
        tblProducts.getColumnModel().getColumn(0).setHeaderValue(i18n.get("carrito.anadir.tbl.Codigo"));
        tblProducts.getColumnModel().getColumn(1).setHeaderValue(i18n.get("carrito.anadir.tbl.Nombre"));
        tblProducts.getColumnModel().getColumn(2).setHeaderValue(i18n.get("carrito.anadir.tbl.Precio"));
        tblProducts.getColumnModel().getColumn(3).setHeaderValue(i18n.get("carrito.anadir.tbl.Cantidad"));
        tblProducts.getColumnModel().getColumn(4).setHeaderValue(i18n.get("carrito.anadir.tbl.TotalItem"));
        tblProducts.getTableHeader().repaint();
    }

    /**
     * Aplica los iconos a los componentes de la interfaz.
     */
    public void aplicarIconos(){
        setFrameIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.CARRITO));
        btnAnadir.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.GUARDAR_TODO));
        btnBuscar.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.BUSCAR));
        btnSave.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.GUARDAR));
        btnCancel.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.X));
        btnEliminarItem.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.ElIMINAR));
    }

    /**
     * Refresca los valores de subtotal, IVA y total en los campos de resumen según la configuración regional.
     * @param locale Configuración regional para formatear los valores.
     */
    public void refrescarResumenValores(Locale locale) {
        txtSubTot.setText(FormateadorUtils.formatearMoneda(subtotal, locale));
        txtIva.setText(FormateadorUtils.formatearMoneda(iva, locale));
        txtTot.setText(FormateadorUtils.formatearMoneda(total, locale));
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
    public JPanel getPanelAll() { return panelAll; }

    /**
     * Establece el panel principal de la vista.
     * @param panelAll El panel principal.
     */
    public void setPanelAll(JPanel panelAll) { this.panelAll = panelAll; }

    /**
     * Obtiene el campo de texto para el código del producto a buscar.
     * @return El campo de texto del código de producto.
     */
    public JTextField getLblCodeProductSearch() { return lblCodeProductSearch; }

    /**
     * Establece el campo de texto para el código del producto a buscar.
     * @param lblCodeProductSearch El campo de texto del código de producto.
     */
    public void setLblCodeProductSearch(JTextField lblCodeProductSearch) { this.lblCodeProductSearch = lblCodeProductSearch; }

    /**
     * Obtiene el campo de texto para el nombre del producto.
     * @return El campo de texto del nombre del producto.
     */
    public JTextField getTxtNombre() { return txtNombre; }

    /**
     * Establece el campo de texto para el nombre del producto.
     * @param txtNombre El campo de texto del nombre del producto.
     */
    public void setTxtNombre(JTextField txtNombre) { this.txtNombre = txtNombre; }

    /**
     * Obtiene el campo de texto para el precio del producto.
     * @return El campo de texto del precio del producto.
     */
    public JTextField getTxtPrecio() { return txtPrecio; }

    /**
     * Establece el campo de texto para el precio del producto.
     * @param txtPrecio El campo de texto del precio del producto.
     */
    public void setTxtPrecio(JTextField txtPrecio) { this.txtPrecio = txtPrecio; }

    /**
     * Obtiene la tabla de productos del carrito.
     * @return La tabla de productos.
     */
    public JTable getTblProducts() { return tblProducts; }

    /**
     * Establece la tabla de productos del carrito.
     * @param tblProducts La tabla de productos.
     */
    public void setTblProducts(JTable tblProducts) { this.tblProducts = tblProducts; }

    /**
     * Obtiene el botón para añadir productos al carrito.
     * @return El botón de añadir.
     */
    public JButton getBtnAnadir() { return btnAnadir; }

    /**
     * Establece el botón para añadir productos al carrito.
     * @param btnAnadir El botón de añadir.
     */
    public void setBtnAnadir(JButton btnAnadir) { this.btnAnadir = btnAnadir; }

    /**
     * Obtiene el campo de texto para el subtotal.
     * @return El campo de texto del subtotal.
     */
    public JTextField getLblSubTotal() { return lblSubTotal; }

    /**
     * Establece el campo de texto para el subtotal.
     * @param lblSubTotal El campo de texto del subtotal.
     */
    public void setLblSubTotal(JTextField lblSubTotal) { this.lblSubTotal = lblSubTotal; }

    /**
     * Obtiene el combobox para seleccionar la cantidad de productos.
     * @return El combobox de cantidad.
     */
    public JComboBox getCbxCantidad() { return cbxCantidad; }

    /**
     * Establece el combobox para seleccionar la cantidad de productos.
     * @param cbxCantidad El combobox de cantidad.
     */
    public void setCbxCantidad(JComboBox cbxCantidad) { this.cbxCantidad = cbxCantidad; }

    /**
     * Obtiene el botón para guardar el carrito.
     * @return El botón de guardar.
     */
    public JButton getBtnSave() { return btnSave; }

    /**
     * Establece el botón para guardar el carrito.
     * @param btnSave El botón de guardar.
     */
    public void setBtnSave(JButton btnSave) { this.btnSave = btnSave; }

    /**
     * Obtiene el botón para cancelar la operación.
     * @return El botón de cancelar.
     */
    public JButton getBtnCancel() { return btnCancel; }

    /**
     * Establece el botón para cancelar la operación.
     * @param btnCancel El botón de cancelar.
     */
    public void setBtnCancel(JButton btnCancel) { this.btnCancel = btnCancel; }

    /**
     * Obtiene la etiqueta del total del carrito.
     * @return La etiqueta del total.
     */
    public JLabel getTxtTotal() { return txtTotal; }

    /**
     * Establece la etiqueta del total del carrito.
     * @param txtTotal La etiqueta del total.
     */
    public void setTxtTotal(JLabel txtTotal) { this.txtTotal = txtTotal; }

    /**
     * Obtiene la etiqueta del carrito de compras.
     * @return La etiqueta del carrito de compras.
     */
    public JLabel getTxtShoppingCart() { return txtShoppingCart; }

    /**
     * Establece la etiqueta del carrito de compras.
     * @param txtShoppingCart La etiqueta del carrito de compras.
     */
    public void setTxtShoppingCart(JLabel txtShoppingCart) { this.txtShoppingCart = txtShoppingCart; }

    /**
     * Obtiene la etiqueta del código del producto.
     * @return La etiqueta del código del producto.
     */
    public JLabel getLblCodigoProducto() { return lblCodigoProducto; }

    /**
     * Establece la etiqueta del código del producto.
     * @param lblCodigoProducto La etiqueta del código del producto.
     */
    public void setLblCodigoProducto(JLabel lblCodigoProducto) { this.lblCodigoProducto = lblCodigoProducto; }

    /**
     * Obtiene la etiqueta del nombre del producto.
     * @return La etiqueta del nombre del producto.
     */
    public JLabel getLblNombre() { return lblNombre; }

    /**
     * Establece la etiqueta del nombre del producto.
     * @param lblNombre La etiqueta del nombre del producto.
     */
    public void setLblNombre(JLabel lblNombre) { this.lblNombre = lblNombre; }

    /**
     * Obtiene la etiqueta del precio del producto.
     * @return La etiqueta del precio del producto.
     */
    public JLabel getLblPrecio() { return lblPrecio; }

    /**
     * Establece la etiqueta del precio del producto.
     * @param lblPrecio La etiqueta del precio del producto.
     */
    public void setLblPrecio(JLabel lblPrecio) { this.lblPrecio = lblPrecio; }

    /**
     * Obtiene la etiqueta de la cantidad de productos.
     * @return La etiqueta de la cantidad.
     */
    public JLabel getLblCantidad() { return lblCantidad; }

    /**
     * Establece la etiqueta de la cantidad de productos.
     * @param lblCantidad La etiqueta de la cantidad.
     */
    public void setLblCantidad(JLabel lblCantidad) { this.lblCantidad = lblCantidad; }

    /**
     * Obtiene el botón para buscar productos.
     * @return El botón de buscar producto.
     */
    public JButton getBtnBuscar() { return btnBuscar; }

    /**
     * Establece el botón para buscar productos.
     * @param btnBuscar El botón de buscar producto.
     */
    public void setBtnBuscar(JButton btnBuscar) { this.btnBuscar = btnBuscar; }

    /**
     * Obtiene la etiqueta del subtotal.
     * @return La etiqueta del subtotal.
     */
    public JLabel getTxtSubTotal() { return txtSubTotal; }

    /**
     * Establece la etiqueta del subtotal.
     * @param txtSubTotal La etiqueta del subtotal.
     */
    public void setTxtSubTotal(JLabel txtSubTotal) { this.txtSubTotal = txtSubTotal; }

    /**
     * Obtiene la etiqueta del IVA.
     * @return La etiqueta del IVA.
     */
    public JLabel getLblIva() { return lblIva; }

    /**
     * Establece la etiqueta del IVA.
     * @param lblIva La etiqueta del IVA.
     */
    public void setLblIva(JLabel lblIva) { this.lblIva = lblIva; }

    /**
     * Obtiene el campo de texto para el IVA.
     * @return El campo de texto del IVA.
     */
    public JTextField getLblTax() { return lblTax; }

    /**
     * Establece el campo de texto para el IVA.
     * @param lblTax El campo de texto del IVA.
     */
    public void setLblTax(JTextField lblTax) { this.lblTax = lblTax; }

    /**
     * Obtiene el campo de texto para el total.
     * @return El campo de texto del total.
     */
    public JTextField getLblTotal() { return lblTotal; }

    /**
     * Establece el campo de texto para el total.
     * @param lblTotal El campo de texto del total.
     */
    public void setLblTotal(JTextField lblTotal) { this.lblTotal = lblTotal; }

    /**
     * Obtiene el panel inferior de la vista.
     * @return El panel inferior.
     */
    public JPanel getPanelInferior() { return panelInferior; }

    /**
     * Establece el panel inferior de la vista.
     * @param panelInferior El panel inferior.
     */
    public void setPanelInferior(JPanel panelInferior) { this.panelInferior = panelInferior; }

    /**
     * Obtiene el panel de ítems del carrito.
     * @return El panel de ítems.
     */
    public JPanel getPanelItems() { return panelItems; }

    /**
     * Establece el panel de ítems del carrito.
     * @param panelItems El panel de ítems.
     */
    public void setPanelItems(JPanel panelItems) { this.panelItems = panelItems; }

    /**
     * Obtiene el panel de producto.
     * @return El panel de producto.
     */
    public JPanel getPanelProduct() { return panelProduct; }

    /**
     * Establece el panel de producto.
     * @param panelProduct El panel de producto.
     */
    public void setPanelProduct(JPanel panelProduct) { this.panelProduct = panelProduct; }

    /**
     * Obtiene el panel del título.
     * @return El panel del título.
     */
    public JPanel getPanelTitle() { return panelTitle; }

    /**
     * Establece el panel del título.
     * @param panelTitle El panel del título.
     */
    public void setPanelTitle(JPanel panelTitle) { this.panelTitle = panelTitle; }

    /**
     * Obtiene el scroll de la tabla de productos.
     * @return El scroll de la tabla.
     */
    public JScrollPane getScroll() { return scroll; }

    /**
     * Establece el scroll de la tabla de productos.
     * @param scroll El scroll de la tabla.
     */
    public void setScroll(JScrollPane scroll) { this.scroll = scroll; }

    /**
     * Obtiene el botón para eliminar ítems del carrito.
     * @return El botón de eliminar ítem.
     */
    public JButton getBtnEliminarItem() { return btnEliminarItem; }

    /**
     * Establece el botón para eliminar ítems del carrito.
     * @param btnEliminarItem El botón de eliminar ítem.
     */
    public void setBtnEliminarItem(JButton btnEliminarItem) { this.btnEliminarItem = btnEliminarItem; }

    /**
     * Obtiene la etiqueta del título de la vista.
     * @return La etiqueta del título.
     */
    public JLabel getLblTitulo() { return lblTitulo; }

    /**
     * Establece la etiqueta del título de la vista.
     * @param lblTitulo La etiqueta del título.
     */
    public void setLblTitulo(JLabel lblTitulo) { this.lblTitulo = lblTitulo; }

    /**
     * Obtiene la etiqueta de los ítems del carrito.
     * @return La etiqueta de los ítems del carrito.
     */
    public JLabel getLblItemsCarrito() { return lblItemsCarrito; }

    /**
     * Establece la etiqueta de los ítems del carrito.
     * @param lblItemsCarrito La etiqueta de los ítems del carrito.
     */
    public void setLblItemsCarrito(JLabel lblItemsCarrito) { this.lblItemsCarrito = lblItemsCarrito; }

    /**
     * Obtiene el campo de texto para el subtotal.
     * @return El campo de texto del subtotal.
     */
    public JTextField getTxtSubTot() { return txtSubTot; }

    /**
     * Establece el campo de texto para el subtotal.
     * @param txtSubTot El campo de texto del subtotal.
     */
    public void setTxtSubTot(JTextField txtSubTot) { this.txtSubTot = txtSubTot; }

    /**
     * Obtiene el campo de texto para el IVA.
     * @return El campo de texto del IVA.
     */
    public JTextField getTxtIva() { return txtIva; }

    /**
     * Establece el campo de texto para el IVA.
     * @param txtIva El campo de texto del IVA.
     */
    public void setTxtIva(JTextField txtIva) { this.txtIva = txtIva; }

    /**
     * Obtiene el campo de texto para el total.
     * @return El campo de texto del total.
     */
    public JTextField getTxtTot() { return txtTot; }

    /**
     * Establece el campo de texto para el total.
     * @param txtTot El campo de texto del total.
     */
    public void setTxtTot(JTextField txtTot) { this.txtTot = txtTot; }

    /**
     * Obtiene la etiqueta del total.
     * @return La etiqueta del total.
     */
    public JLabel getLblTot() { return lblTot; }

    /**
     * Establece la etiqueta del total.
     * @param lblTot La etiqueta del total.
     */
    public void setLblTot(JLabel lblTot) { this.lblTot = lblTot; }

    /**
     * Obtiene la etiqueta del subtotal.
     * @return La etiqueta del subtotal.
     */
    public JLabel getLblSubTot() { return lblSubTot; }

    /**
     * Establece la etiqueta del subtotal.
     * @param lblSubTot La etiqueta del subtotal.
     */
    public void setLblSubTot(JLabel lblSubTot) { this.lblSubTot = lblSubTot; }

    /**
     * Obtiene el modelo de la tabla de productos.
     * @return El modelo de la tabla.
     */
    public DefaultTableModel getModelo() { return modelo; }

    /**
     * Establece el modelo de la tabla de productos.
     * @param modelo El modelo de la tabla.
     */
    public void setModelo(DefaultTableModel modelo) { this.modelo = modelo; }

}

