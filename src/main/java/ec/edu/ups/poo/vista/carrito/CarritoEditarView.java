package ec.edu.ups.poo.vista.carrito;

import ec.edu.ups.poo.modelo.ItemCarrito;
import ec.edu.ups.poo.modelo.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class CarritoEditarView extends JInternalFrame{
    private JPanel panelAll;
    private JTextField txtCodigoProducto;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JTable tblProducts;
    private JButton btnAnadir;
    private JComboBox cbxCantidad;
    private JButton btnActualizar;
    private JButton btnCancel;
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

    public CarritoEditarView() {
        setContentPane(panelAll);
        setTitle("Carrito de Compras");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 400);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
    }

    public void init() {
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

        cargarDatosCombobox();
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

    public JButton getBtnCancel() {
        return btnCancel;
    }

    public void setBtnCancel(JButton btnCancel) {
        this.btnCancel = btnCancel;
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
        txtCodigoProducto.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
    }

    public void mostrarItemsCarrito(List<ItemCarrito> items) {
        modelo.setRowCount(0);
        for (ItemCarrito item : items) {
            Producto p = item.getProducto();
            modelo.addRow(new Object[]{
                    p.getCodigo(),
                    p.getNombre(),
                    p.getPrecio(),
                    item.getCantidad(),
                    item.getTotalItem()
            });
        }
    }

    public int mostrarMensajeConfirmacion(String mensaje, String titulo, int tipo) {
        Object[] botones = {"Confirmar", "Cancelar"};
        return JOptionPane.showOptionDialog(
                this, mensaje, titulo,
                JOptionPane.DEFAULT_OPTION, tipo,
                null, botones, botones[0]);
    }
}