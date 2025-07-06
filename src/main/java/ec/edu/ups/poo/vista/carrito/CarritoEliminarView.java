package ec.edu.ups.poo.vista.carrito;

import ec.edu.ups.poo.util.FormateadorUtils;
import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.util.enums.TipoIcono;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.Locale;

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

    public int mostrarMensajeConfirmacion(String mensaje, String titulo, int tipo) {
        Object[] botones = {i18n.get("mensaje.confirmacion"), i18n.get("mensaje.cancelacion")};
        return JOptionPane.showOptionDialog(
                this, mensaje, titulo,
                JOptionPane.DEFAULT_OPTION, tipo,
                null, botones, botones[0]);
    }

    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }

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

    public void aplicarIconos(){
        setFrameIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.CARRITO));
        btnBuscar.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.BUSCAR));
        btnEliminar.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.ElIMINAR));
    }

    public void setResumenValores(double subtotal, double iva, double total, Locale locale) {
        this.subtotal = subtotal;
        this.iva = iva;
        this.total = total;
        txtIva.setText(FormateadorUtils.formatearMoneda(iva, locale));
        txtTotal.setText(FormateadorUtils.formatearMoneda(total, locale));
        txtSubTotal.setText(FormateadorUtils.formatearMoneda(subtotal, locale));
    }

    public void refrescarResumenValores(Locale locale) {
        txtIva.setText(FormateadorUtils.formatearMoneda(iva, locale));
        txtTotal.setText(FormateadorUtils.formatearMoneda(total, locale));
        txtSubTotal.setText(FormateadorUtils.formatearMoneda(subtotal, locale));
    }

    public void refrescarTabla() {
        ((DefaultTableModel) tblProducts.getModel()).fireTableDataChanged();
        tblProducts.getTableHeader().repaint();
    }

    public JPanel getPanelTitle() { return panelTitle; }
    public void setPanelTitle(JPanel panelTitle) { this.panelTitle = panelTitle; }
    public JLabel getLblTitulo() { return lblTitulo; }
    public void setLblTitulo(JLabel lblTitulo) { this.lblTitulo = lblTitulo; }
    public JPanel getPanelProduct() { return panelProduct; }
    public void setPanelProduct(JPanel panelProduct) { this.panelProduct = panelProduct; }
    public JTextField getTxtCodigo() { return txtCodigo; }
    public void setTxtCodigo(JTextField txtCodigo) { this.txtCodigo = txtCodigo; }
    public JButton getBtnBuscar() { return btnBuscar; }
    public void setBtnBuscar(JButton btnBuscar) { this.btnBuscar = btnBuscar; }
    public JScrollPane getScroll() { return scroll; }
    public void setScroll(JScrollPane scroll) { this.scroll = scroll; }
    public JTable getTblProducts() { return tblProducts; }
    public void setTblProducts(JTable tblProducts) { this.tblProducts = tblProducts; }
    public JTextField getTxtIva() { return txtIva; }
    public void setTxtIva(JTextField txtIva) { this.txtIva = txtIva; }
    public JTextField getTxtTotal() { return txtTotal; }
    public void setTxtTotal(JTextField txtTotal) { this.txtTotal = txtTotal; }
    public JTextField getTxtSubTotal() { return txtSubTotal; }
    public void setTxtSubTotal(JTextField txtSubTotal) { this.txtSubTotal = txtSubTotal; }
    public JButton getBtnEliminar() { return btnEliminar; }
    public void setBtnEliminar(JButton btnEliminar) { this.btnEliminar = btnEliminar; }
    public JPanel getPanelAll() { return panelAll; }
    public void setPanelAll(JPanel panelAll) { this.panelAll = panelAll; }
    public JLabel getLblCodigo() { return lblCodigo; }
    public void setLblCodigo(JLabel lblCodigo) { this.lblCodigo = lblCodigo; }
    public JLabel getLblSubTotal() { return lblSubTotal; }
    public void setLblSubTotal(JLabel lblSubTotal) { this.lblSubTotal = lblSubTotal; }
    public JLabel getLblIva() { return lblIva; }
    public void setLblIva(JLabel lblIva) { this.lblIva = lblIva; }
    public JLabel getLblTotal() { return lblTotal; }
    public void setLblTotal(JLabel lblTotal) { this.lblTotal = lblTotal; }
    public JLabel getLblItemsCarrito() { return lblItemsCarrito; }
    public void setLblItemsCarrito(JLabel lblItemsCarrito) { this.lblItemsCarrito = lblItemsCarrito; }
    public JPanel getPanelFinal() { return panelFinal; }
    public void setPanelFinal(JPanel panelFinal) { this.panelFinal = panelFinal; }

}