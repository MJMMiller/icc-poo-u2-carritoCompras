package ec.edu.ups.poo.vista.carrito;

import ec.edu.ups.poo.util.FormateadorUtils;
import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.util.TipoIcono;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.Locale;

/**
 * Vista para eliminar carritos de compras.
 * Permite visualizar los productos de un carrito, mostrar totales y eliminar el carrito seleccionado.
 */
public class CarritoEliminarView extends JInternalFrame {
    private JPanel panelTitle;
    private JLabel lblTitulo;
    private JPanel panelProduct;
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JScrollPane scroll;
    private JTable tblProducts;
    private JTextField txtIva;
    private JTextField txtTotal;
    private JTextField txtSubTotal;
    private JButton btnEliminar;
    private JPanel panelAll;
    private JLabel lblCodigo;
    private JLabel lblSubTotal;
    private JLabel lblIva;
    private JLabel lblTotal;
    private JLabel lblItemsCarrito;
    private JPanel panelFinal;
    private MensajeInternacionalizacionHandler i18n;
    private double subtotal = 0.0;
    private double iva = 0.0;
    private double total = 0.0;

    /**
     * Constructor de la vista para eliminar carritos.
     * Inicializa la interfaz gráfica y aplica idioma e iconos.
     * @param i18n Manejador de internacionalización para los textos de la interfaz.
     */
    public CarritoEliminarView(MensajeInternacionalizacionHandler i18n) {
        this.i18n = i18n;
        setContentPane(panelAll);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        Color fondoOscuro = new Color(29,30,32);

        panelAll.setBackground(fondoOscuro);

        tblProducts.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Código", "Nombre", "Precio", "Cantidad", "Total Item"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        tblProducts.setBackground(fondoOscuro);
        tblProducts.setForeground(Color.WHITE);
        scroll.getViewport().setBackground(fondoOscuro);
        scroll.setBackground(fondoOscuro);

        JTableHeader header = tblProducts.getTableHeader();
        header.setBackground(Color.BLACK);
        header.setForeground(Color.WHITE);
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        aplicarIdioma();
        aplicarIconos();
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
     * Muestra un mensaje en un cuadro de diálogo.
     * @param mensaje Mensaje a mostrar.
     * @param titulo Título del cuadro de diálogo.
     * @param tipo Tipo de mensaje (por ejemplo, JOptionPane.INFORMATION_MESSAGE).
     */
    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }

    /**
     * Aplica los textos internacionalizados a los componentes de la interfaz.
     */
    public void aplicarIdioma(){
        setTitle(i18n.get("carrito.eliminar.title"));
        lblTitulo.setText(i18n.get("carrito.eliminar.lbl.Titulo"));
        lblCodigo.setText(i18n.get("carrito.eliminar.lbl.Codigo"));

        lblItemsCarrito.setText(i18n.get("carrito.eliminar.lbl.ItemsCarrito"));
        btnBuscar.setText(i18n.get("carrito.eliminar.btn.Buscar"));
        btnEliminar.setText(i18n.get("carrito.eliminar.btn.Eliminar"));

        tblProducts.getColumnModel().getColumn(0).setHeaderValue(i18n.get("carrito.eliminar.tbl.Codigo"));
        tblProducts.getColumnModel().getColumn(1).setHeaderValue(i18n.get("carrito.eliminar.tbl.Nombre"));
        tblProducts.getColumnModel().getColumn(2).setHeaderValue(i18n.get("carrito.eliminar.tbl.Precio"));
        tblProducts.getColumnModel().getColumn(3).setHeaderValue(i18n.get("carrito.eliminar.tbl.Cantidad"));
        tblProducts.getColumnModel().getColumn(4).setHeaderValue(i18n.get("carrito.eliminar.tbl.TotalItem"));
        tblProducts.getTableHeader().repaint();
    }

    /**
     * Aplica los iconos a los componentes de la interfaz.
     */
    public void aplicarIconos(){
        setFrameIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.CARRITO));
        btnBuscar.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.BUSCAR));
        btnEliminar.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.ElIMINAR));
    }

    /**
     * Establece los valores de subtotal, IVA y total en los campos de resumen.
     * @param subtotal Subtotal del carrito.
     * @param iva IVA aplicado al carrito.
     * @param total Total del carrito.
     * @param locale Configuración regional para formatear los valores.
     */
    public void setResumenValores(double subtotal, double iva, double total, Locale locale) {
        this.subtotal = subtotal;
        this.iva = iva;
        this.total = total;
        txtIva.setText(FormateadorUtils.formatearMoneda(iva, locale));
        txtTotal.setText(FormateadorUtils.formatearMoneda(total, locale));
        txtSubTotal.setText(FormateadorUtils.formatearMoneda(subtotal, locale));
    }

    /**
     * Refresca los valores de subtotal, IVA y total en los campos de resumen según la configuración regional.
     * @param locale Configuración regional para formatear los valores.
     */
    public void refrescarResumenValores(Locale locale) {
        txtIva.setText(FormateadorUtils.formatearMoneda(iva, locale));
        txtTotal.setText(FormateadorUtils.formatearMoneda(total, locale));
        txtSubTotal.setText(FormateadorUtils.formatearMoneda(subtotal, locale));
    }

    /**
     * Refresca la tabla de productos del carrito.
     */
    public void refrescarTabla() {
        ((DefaultTableModel) tblProducts.getModel()).fireTableDataChanged();
        tblProducts.getTableHeader().repaint();
    }

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
     * Obtiene la etiqueta del título.
     * @return La etiqueta del título.
     */
    public JLabel getLblTitulo() { return lblTitulo; }

    /**
     * Establece la etiqueta del título.
     * @param lblTitulo La etiqueta del título.
     */
    public void setLblTitulo(JLabel lblTitulo) { this.lblTitulo = lblTitulo; }

    /**
     * Obtiene el panel de productos.
     * @return El panel de productos.
     */
    public JPanel getPanelProduct() { return panelProduct; }

    /**
     * Establece el panel de productos.
     * @param panelProduct El panel de productos.
     */
    public void setPanelProduct(JPanel panelProduct) { this.panelProduct = panelProduct; }

    /**
     * Obtiene el campo de texto para el código del carrito.
     * @return El campo de texto del código.
     */
    public JTextField getTxtCodigo() { return txtCodigo; }

    /**
     * Establece el campo de texto para el código del carrito.
     * @param txtCodigo El campo de texto del código.
     */
    public void setTxtCodigo(JTextField txtCodigo) { this.txtCodigo = txtCodigo; }

    /**
     * Obtiene el botón de búsqueda.
     * @return El botón de búsqueda.
     */
    public JButton getBtnBuscar() { return btnBuscar; }

    /**
     * Establece el botón de búsqueda.
     * @param btnBuscar El botón de búsqueda.
     */
    public void setBtnBuscar(JButton btnBuscar) { this.btnBuscar = btnBuscar; }

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
     * Obtiene la tabla de productos.
     * @return La tabla de productos.
     */
    public JTable getTblProducts() { return tblProducts; }

    /**
     * Establece la tabla de productos.
     * @param tblProducts La tabla de productos.
     */
    public void setTblProducts(JTable tblProducts) { this.tblProducts = tblProducts; }

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
    public JTextField getTxtTotal() { return txtTotal; }

    /**
     * Establece el campo de texto para el total.
     * @param txtTotal El campo de texto del total.
     */
    public void setTxtTotal(JTextField txtTotal) { this.txtTotal = txtTotal; }

    /**
     * Obtiene el campo de texto para el subtotal.
     * @return El campo de texto del subtotal.
     */
    public JTextField getTxtSubTotal() { return txtSubTotal; }

    /**
     * Establece el campo de texto para el subtotal.
     * @param txtSubTotal El campo de texto del subtotal.
     */
    public void setTxtSubTotal(JTextField txtSubTotal) { this.txtSubTotal = txtSubTotal; }

    /**
     * Obtiene el botón para eliminar el carrito.
     * @return El botón de eliminar.
     */
    public JButton getBtnEliminar() { return btnEliminar; }

    /**
     * Establece el botón para eliminar el carrito.
     * @param btnEliminar El botón de eliminar.
     */
    public void setBtnEliminar(JButton btnEliminar) { this.btnEliminar = btnEliminar; }

    /**
     * Obtiene el panel principal.
     * @return El panel principal.
     */
    public JPanel getPanelAll() { return panelAll; }

    /**
     * Establece el panel principal.
     * @param panelAll El panel principal.
     */
    public void setPanelAll(JPanel panelAll) { this.panelAll = panelAll; }

    /**
     * Obtiene la etiqueta del código.
     * @return La etiqueta del código.
     */
    public JLabel getLblCodigo() { return lblCodigo; }

    /**
     * Establece la etiqueta del código.
     * @param lblCodigo La etiqueta del código.
     */
    public void setLblCodigo(JLabel lblCodigo) { this.lblCodigo = lblCodigo; }

    /**
     * Obtiene la etiqueta del subtotal.
     * @return La etiqueta del subtotal.
     */
    public JLabel getLblSubTotal() { return lblSubTotal; }

    /**
     * Establece la etiqueta del subtotal.
     * @param lblSubTotal La etiqueta del subtotal.
     */
    public void setLblSubTotal(JLabel lblSubTotal) { this.lblSubTotal = lblSubTotal; }

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
     * Obtiene la etiqueta del total.
     * @return La etiqueta del total.
     */
    public JLabel getLblTotal() { return lblTotal; }

    /**
     * Establece la etiqueta del total.
     * @param lblTotal La etiqueta del total.
     */
    public void setLblTotal(JLabel lblTotal) { this.lblTotal = lblTotal; }

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
     * Obtiene el panel final.
     * @return El panel final.
     */
    public JPanel getPanelFinal() { return panelFinal; }

    /**
     * Establece el panel final.
     * @param panelFinal El panel final.
     */
    public void setPanelFinal(JPanel panelFinal) { this.panelFinal = panelFinal; }

}