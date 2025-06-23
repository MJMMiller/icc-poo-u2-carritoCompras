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
    private JTextField lblCodeProductSearch;
    private JTextField lblNameProduct;
    private JTextField lblPriceProduct;
    private JTable tblProducts;
    private JButton btnAddProduct;
    private JTextField lblSubTotal;
    private JComboBox cbxAmountProduct;
    private JButton btnSave;
    private JButton btnCancel;
    private JLabel txtTotal;
    private JLabel txtShoppingCart;
    private JLabel txtCodeProduct;
    private JLabel txtNameProduct;
    private JLabel txtPriceProduct;
    private JLabel txtAmountProduct;
    private JButton btnSearchProduct;
    private JLabel txtSubTotal;
    private JLabel txtTax;
    private JTextField lblTax;
    private JTextField lblTotal;
    private JPanel panelInferior;
    private JPanel panelItems;
    private JPanel panelProduct;
    private JPanel panelTitle;
    private JScrollPane scroll;
    private JButton btnDeleteItem;
    private JLabel txtSettingsProduc;
    private DefaultTableModel modelo;
    private JTextField lblCodeSerachCart;
    private JButton btnCodeSearchCart;
    private JButton btnClean;
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

        // Establecer fondo en todos los paneles principales
        if (panelAll != null) panelAll.setBackground(fondo);
        if (panelInferior != null) panelInferior.setBackground(fondo);
        if (panelItems != null) panelItems.setBackground(fondo);
        if (panelProduct != null) panelProduct.setBackground(fondo);
        if (panelTitle != null) panelTitle.setBackground(fondo);

        // Fondo de la tabla y scroll
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

        // Opcional: Cambiar color de labels y botones principales si quieres
        setColorComponent(lblCodeProductSearch, fondo, letras);
        setColorComponent(lblNameProduct, fondo, letras);
        setColorComponent(lblPriceProduct, fondo, letras);
        setColorComponent(lblSubTotal, fondo, letras);
        setColorComponent(lblTax, fondo, letras);
        setColorComponent(lblTotal, fondo, letras);
        setColorComponent(lblCodeSerachCart, fondo, letras);

        // Labels
        setColorLabel(txtTotal, letras);
        setColorLabel(txtShoppingCart, letras);
        setColorLabel(txtCodeProduct, letras);
        setColorLabel(txtNameProduct, letras);
        setColorLabel(txtPriceProduct, letras);
        setColorLabel(txtAmountProduct, letras);
        setColorLabel(txtSubTotal, letras);
        setColorLabel(txtTax, letras);
        setColorLabel(txtSettingsProduc, letras);

        cargarDatosCombobox();
    }

    // Métodos para cambiar colores de componentes
    private void setColorComponent(JComponent comp, Color fondo, Color letras) {
        if (comp != null) {
            comp.setBackground(fondo);
            comp.setForeground(letras);
        }
    }
    private void setColorLabel(JLabel label, Color letras) {
        if (label != null) label.setForeground(letras);
    }

    public JButton getBtnDelateItem() {
        return btnDelateItem;
    }

    public void setBtnDelateItem(JButton btnDelateItem) {
        this.btnDelateItem = btnDelateItem;
    }

    public JButton getBtnClean() {
        return btnClean;
    }

    public void setBtnClean(JButton btnClean) {
        this.btnClean = btnClean;
    }

    public JButton getBtnCodeSearchCart() {
        return btnCodeSearchCart;
    }

    public void setBtnCodeSearchCart(JButton btnCodeSearchCart) {
        this.btnCodeSearchCart = btnCodeSearchCart;
    }

    public JTextField getLblCodeSerachCart() {
        return lblCodeSerachCart;
    }

    public void setLblCodeSerachCart(JTextField lblCodeSerachCart) {
        this.lblCodeSerachCart = lblCodeSerachCart;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
    }

    public JLabel getTxtSettingsProduc() {
        return txtSettingsProduc;
    }

    public void setTxtSettingsProduc(JLabel txtSettingsProduc) {
        this.txtSettingsProduc = txtSettingsProduc;
    }

    public JButton getBtnDeleteItem() {
        return btnDeleteItem;
    }

    public void setBtnDeleteItem(JButton btnDeleteItem) {
        this.btnDeleteItem = btnDeleteItem;
    }

    public JScrollPane getScroll() {
        return scroll;
    }

    public void setScroll(JScrollPane scroll) {
        this.scroll = scroll;
    }

    public JPanel getPanelTitle() {
        return panelTitle;
    }

    public void setPanelTitle(JPanel panelTitle) {
        this.panelTitle = panelTitle;
    }

    public JPanel getPanelProduct() {
        return panelProduct;
    }

    public void setPanelProduct(JPanel panelProduct) {
        this.panelProduct = panelProduct;
    }

    public JPanel getPanelItems() {
        return panelItems;
    }

    public void setPanelItems(JPanel panelItems) {
        this.panelItems = panelItems;
    }

    public JPanel getPanelInferior() {
        return panelInferior;
    }

    public void setPanelInferior(JPanel panelInferior) {
        this.panelInferior = panelInferior;
    }

    public JTextField getLblTotal() {
        return lblTotal;
    }

    public void setLblTotal(JTextField lblTotal) {
        this.lblTotal = lblTotal;
    }

    public JTextField getLblTax() {
        return lblTax;
    }

    public void setLblTax(JTextField lblTax) {
        this.lblTax = lblTax;
    }

    public JLabel getTxtTax() {
        return txtTax;
    }

    public void setTxtTax(JLabel txtTax) {
        this.txtTax = txtTax;
    }

    public JLabel getTxtSubTotal() {
        return txtSubTotal;
    }

    public void setTxtSubTotal(JLabel txtSubTotal) {
        this.txtSubTotal = txtSubTotal;
    }

    public JButton getBtnSearchProduct() {
        return btnSearchProduct;
    }

    public void setBtnSearchProduct(JButton btnSearchProduct) {
        this.btnSearchProduct = btnSearchProduct;
    }

    public JLabel getTxtAmountProduct() {
        return txtAmountProduct;
    }

    public void setTxtAmountProduct(JLabel txtAmountProduct) {
        this.txtAmountProduct = txtAmountProduct;
    }

    public JLabel getTxtPriceProduct() {
        return txtPriceProduct;
    }

    public void setTxtPriceProduct(JLabel txtPriceProduct) {
        this.txtPriceProduct = txtPriceProduct;
    }

    public JLabel getTxtNameProduct() {
        return txtNameProduct;
    }

    public void setTxtNameProduct(JLabel txtNameProduct) {
        this.txtNameProduct = txtNameProduct;
    }

    public JLabel getTxtCodeProduct() {
        return txtCodeProduct;
    }

    public void setTxtCodeProduct(JLabel txtCodeProduct) {
        this.txtCodeProduct = txtCodeProduct;
    }

    public JLabel getTxtShoppingCart() {
        return txtShoppingCart;
    }

    public void setTxtShoppingCart(JLabel txtShoppingCart) {
        this.txtShoppingCart = txtShoppingCart;
    }

    public JLabel getTxtTotal() {
        return txtTotal;
    }

    public void setTxtTotal(JLabel txtTotal) {
        this.txtTotal = txtTotal;
    }

    public JButton getBtnCancel() {
        return btnCancel;
    }

    public void setBtnCancel(JButton btnCancel) {
        this.btnCancel = btnCancel;
    }

    public JButton getBtnSave() {
        return btnSave;
    }

    public void setBtnSave(JButton btnSave) {
        this.btnSave = btnSave;
    }

    public JComboBox getCbxAmountProduct() {
        return cbxAmountProduct;
    }

    public void setCbxAmountProduct(JComboBox cbxAmountProduct) {
        this.cbxAmountProduct = cbxAmountProduct;
    }

    public JTextField getLblSubTotal() {
        return lblSubTotal;
    }

    public void setLblSubTotal(JTextField lblSubTotal) {
        this.lblSubTotal = lblSubTotal;
    }

    public JButton getBtnAddProduct() {
        return btnAddProduct;
    }

    public void setBtnAddProduct(JButton btnAddProduct) {
        this.btnAddProduct = btnAddProduct;
    }

    public JTable getTblProducts() {
        return tblProducts;
    }

    public void setTblProducts(JTable tblProducts) {
        this.tblProducts = tblProducts;
    }

    public JTextField getLblPriceProduct() {
        return lblPriceProduct;
    }

    public void setLblPriceProduct(JTextField lblPriceProduct) {
        this.lblPriceProduct = lblPriceProduct;
    }

    public JTextField getLblNameProduct() {
        return lblNameProduct;
    }

    public void setLblNameProduct(JTextField lblNameProduct) {
        this.lblNameProduct = lblNameProduct;
    }

    public JTextField getLblCodeProductSearch() {
        return lblCodeProductSearch;
    }

    public void setLblCodeProductSearch(JTextField lblCodeProductSearch) {
        this.lblCodeProductSearch = lblCodeProductSearch;
    }

    public JPanel getPanelAll() {
        return panelAll;
    }

    public void setPanelAll(JPanel panelAll) {
        this.panelAll = panelAll;
    }

    public void cargarDatosCombobox(){
        cbxAmountProduct.removeAllItems();
        for (int i = 1; i <= 20; i++) {
            cbxAmountProduct.addItem(i);
        }
    }

    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }

    public void limpiarCampos() {
        lblCodeProductSearch.setText("");
        lblNameProduct.setText("");
        lblPriceProduct.setText("");
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

    public int mostrarMensajeDelateItem(String mensaje, String titulo, int tipo) {
        Object[] botones = {"Confirm", "Cancel"};
        return JOptionPane.showOptionDialog(
                this, mensaje, titulo,
                JOptionPane.DEFAULT_OPTION, tipo,
                null, botones, botones[0]);
    }

    public int mostrarMensajeGuardarCambios(String mesaje, String titulo, int tipo){
        Object[] botones = {"Confirm", "Cancelar"};
        return JOptionPane.showOptionDialog(
                this, mesaje, titulo,
                JOptionPane.DEFAULT_OPTION, tipo,
                null, botones, botones[0]);
    };
}