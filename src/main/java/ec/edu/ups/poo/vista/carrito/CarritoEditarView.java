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

    public void actualizarResumen(double subtotal, double iva, double total) {
        this.subtotal = subtotal;
        this.iva = iva;
        this.total = total;
        refrescarResumenValores(i18n.getLocale());
    }

    public void refrescarResumenValores(Locale locale) {
        txtSub.setText(FormateadorUtils.formatearMoneda(subtotal, locale));
        txtIva.setText(FormateadorUtils.formatearMoneda(iva, locale));
        txtTot.setText(FormateadorUtils.formatearMoneda(total, locale));
    }

    public void cargarDatosCombobox(){
        cbxCantidad.removeAllItems();
        for (int i = 1; i <= 20; i++) {
            cbxCantidad.addItem(i);
        }
    }

    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }

    public void limpiarCampos() {
        cbxCantidad.setSelectedIndex(0);
        txtCodigoProducto.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
    }

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

    public int mostrarMensajeConfirmacion(String mensaje, String titulo, int tipo) {
        Object[] botones = {i18n.get("mensaje.confirmacion"), i18n.get("mensaje.cancelacion")};
        return JOptionPane.showOptionDialog(
                this, mensaje, titulo,
                JOptionPane.DEFAULT_OPTION, tipo,
                null, botones, botones[0]);
    }

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

    public void aplicarIconos(){
        setFrameIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.CARRITO));
        btnEliminarItem.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.ElIMINAR));
        btnAnadir.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.GUARDAR_TODO));
        btnActualizar.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.ACTUALIZAR));
        btnBuscarProducto.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.BUSCAR));
        btnBuscarCarrito.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.BUSCAR));
        btnClean.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.CLEAN));
    }

    public void refrescarTabla() {
        ((DefaultTableModel) tblProducts.getModel()).fireTableDataChanged();
        tblProducts.getTableHeader().repaint();
    }

    public JPanel getPanelAll() {
        return panelAll;
    }
    public void setPanelAll(JPanel panelAll) {
        this.panelAll = panelAll;
    }
    public JTextField getTxtCodigoProducto() {
        return txtCodigoProducto;
    }
    public void setTxtCodigoProducto(JTextField txtCodigoProducto) {
        this.txtCodigoProducto = txtCodigoProducto;
    }
    public JTextField getTxtNombre() {
        return txtNombre;
    }
    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }
    public JTextField getTxtPrecio() {
        return txtPrecio;
    }
    public void setTxtPrecio(JTextField txtPrecio) {
        this.txtPrecio = txtPrecio;
    }
    public JTable getTblProducts() {
        return tblProducts;
    }
    public void setTblProducts(JTable tblProducts) {
        this.tblProducts = tblProducts;
    }
    public JButton getBtnAnadir() {
        return btnAnadir;
    }
    public void setBtnAnadir(JButton btnAnadir) {
        this.btnAnadir = btnAnadir;
    }
    public JComboBox getCbxCantidad() {
        return cbxCantidad;
    }
    public void setCbxCantidad(JComboBox cbxCantidad) {
        this.cbxCantidad = cbxCantidad;
    }
    public JButton getBtnActualizar() {
        return btnActualizar;
    }
    public void setBtnActualizar(JButton btnActualizar) {
        this.btnActualizar = btnActualizar;
    }
    public JLabel getTxtTotal() {
        return txtTotal;
    }
    public void setTxtTotal(JLabel txtTotal) {
        this.txtTotal = txtTotal;
    }
    public JLabel getTxtShoppingCart() {
        return txtShoppingCart;
    }
    public void setTxtShoppingCart(JLabel txtShoppingCart) {
        this.txtShoppingCart = txtShoppingCart;
    }
    public JLabel getLblCodigoProducto() {
        return lblCodigoProducto;
    }
    public void setLblCodigoProducto(JLabel lblCodigoProducto) {
        this.lblCodigoProducto = lblCodigoProducto;
    }
    public JLabel getLblNombre() {
        return lblNombre;
    }
    public void setLblNombre(JLabel lblNombre) {
        this.lblNombre = lblNombre;
    }
    public JLabel getLblPrecio() {
        return lblPrecio;
    }
    public void setLblPrecio(JLabel lblPrecio) {
        this.lblPrecio = lblPrecio;
    }
    public JLabel getLblCantidad() {
        return lblCantidad;
    }
    public void setLblCantidad(JLabel lblCantidad) {
        this.lblCantidad = lblCantidad;
    }
    public JButton getBtnBuscarProducto() {
        return btnBuscarProducto;
    }
    public void setBtnBuscarProducto(JButton btnBuscarProducto) {
        this.btnBuscarProducto = btnBuscarProducto;
    }
    public JLabel getTxtTax() {
        return txtTax;
    }
    public void setTxtTax(JLabel txtTax) {
        this.txtTax = txtTax;
    }
    public JTextField getTxtIva() {
        return txtIva;
    }
    public void setTxtIva(JTextField txtIva) {
        this.txtIva = txtIva;
    }
    public JPanel getPanelInferior() {
        return panelInferior;
    }
    public void setPanelInferior(JPanel panelInferior) {
        this.panelInferior = panelInferior;
    }
    public JPanel getPanelItems() {
        return panelItems;
    }
    public void setPanelItems(JPanel panelItems) {
        this.panelItems = panelItems;
    }
    public JPanel getPanelProduct() {
        return panelProduct;
    }
    public void setPanelProduct(JPanel panelProduct) {
        this.panelProduct = panelProduct;
    }
    public JPanel getPanelTitle() {
        return panelTitle;
    }
    public void setPanelTitle(JPanel panelTitle) {
        this.panelTitle = panelTitle;
    }
    public JScrollPane getScroll() {
        return scroll;
    }
    public void setScroll(JScrollPane scroll) {
        this.scroll = scroll;
    }
    public JButton getBtnEliminarItem() {
        return btnEliminarItem;
    }
    public void setBtnEliminarItem(JButton btnEliminarItem) {
        this.btnEliminarItem = btnEliminarItem;
    }
    public JLabel getLblTitulo() {
        return lblTitulo;
    }
    public void setLblTitulo(JLabel lblTitulo) {
        this.lblTitulo = lblTitulo;
    }
    public DefaultTableModel getModelo() {
        return modelo;
    }
    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
    }
    public JTextField getTxtCodigoCarrito() {
        return txtCodigoCarrito;
    }
    public void setTxtCodigoCarrito(JTextField txtCodigoCarrito) {
        this.txtCodigoCarrito = txtCodigoCarrito;
    }
    public JButton getBtnBuscarCarrito() {
        return btnBuscarCarrito;
    }
    public void setBtnBuscarCarrito(JButton btnBuscarCarrito) {
        this.btnBuscarCarrito = btnBuscarCarrito;
    }
    public JButton getBtnClean() {
        return btnClean;
    }
    public void setBtnClean(JButton btnClean) {
        this.btnClean = btnClean;
    }
    public JPanel getPanelFinal() {
        return panelFinal;
    }
    public void setPanelFinal(JPanel panelFinal) {
        this.panelFinal = panelFinal;
    }
    public JLabel getLblItemsCarrito() {
        return lblItemsCarrito;
    }
    public void setLblItemsCarrito(JLabel lblItemsCarrito) {
        this.lblItemsCarrito = lblItemsCarrito;
    }
    public JLabel getLblCordigocCarrito() {
        return lblCordigocCarrito;
    }
    public void setLblCordigocCarrito(JLabel lblCordigocCarrito) {
        this.lblCordigocCarrito = lblCordigocCarrito;
    }
    public JTextField getTxtSub() {
        return txtSub;
    }
    public void setTxtSub(JTextField txtSub) {
        this.txtSub = txtSub;
    }
    public JLabel getLblTot() {
        return lblTot;
    }
    public void setLblTot(JLabel lblTot) {
        this.lblTot = lblTot;
    }
    public JTextField getTxtTot() {
        return txtTot;
    }
    public void setTxtTot(JTextField txtTot) {
        this.txtTot = txtTot;
    }
    public JLabel getLblIva() {
        return lblIva;
    }
    public void setLblIva(JLabel lblIva) {
        this.lblIva = lblIva;
    }
    public JLabel getLblSubTot() {
        return lblSubTot;
    }
    public void setLblSubTot(JLabel lblSubTot) {
        this.lblSubTot = lblSubTot;
    }
    public JButton getBtnDelateItem() {
        return btnDelateItem;
    }
    public void setBtnDelateItem(JButton btnDelateItem) {
        this.btnDelateItem = btnDelateItem;
    }
    public MensajeInternacionalizacionHandler getI18n() {
        return i18n;
    }
    public void setI18n(MensajeInternacionalizacionHandler i18n) {
        this.i18n = i18n;
    }
    public double getSubtotal() {
        return subtotal;
    }
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
    public double getIva() {
        return iva;
    }
    public void setIva(double iva) {
        this.iva = iva;
    }
    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {
        this.total = total;
    }

}