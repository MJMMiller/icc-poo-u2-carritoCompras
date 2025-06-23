package ec.edu.ups.poo.vista.carrito;

import ec.edu.ups.poo.modelo.ItemCarrito;
import ec.edu.ups.poo.modelo.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class CarritoAnadirView extends JInternalFrame{
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

    public CarritoAnadirView() {
        setContentPane(panelAll);
        setTitle("Carrito de Compras");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 400);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

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

        cargarDatosCombobox();
    }

    public JPanel getPanelAll() {
        return panelAll;
    }

    public void setPanelAll(JPanel panelAll) {
        this.panelAll = panelAll;
    }

    public JTextField getLblCodeProductSearch() {
        return lblCodeProductSearch;
    }

    public void setLblCodeProductSearch(JTextField lblCodeProductSearch) {
        this.lblCodeProductSearch = lblCodeProductSearch;
    }

    public JTextField getLblNameProduct() {
        return lblNameProduct;
    }

    public void setLblNameProduct(JTextField lblNameProduct) {
        this.lblNameProduct = lblNameProduct;
    }

    public JTextField getLblPriceProduct() {
        return lblPriceProduct;
    }

    public void setLblPriceProduct(JTextField lblPriceProduct) {
        this.lblPriceProduct = lblPriceProduct;
    }

    public JTable getTblItems() {
        return tblProducts;
    }

    public void setTblItems(JTable tblProducts) {
        this.tblProducts = tblProducts;
    }

    public JButton getBtnAddProduct() {
        return btnAddProduct;
    }

    public void setBtnAddProduct(JButton btnAddProduct) {
        this.btnAddProduct = btnAddProduct;
    }

    public JTextField getLblSubTotal() {
        return lblSubTotal;
    }

    public void setLblSubTotal(JTextField lblSubTotal) {
        this.lblSubTotal = lblSubTotal;
    }

    public JComboBox getCbxAmountProduct() {
        return cbxAmountProduct;
    }

    public void setCbxAmountProduct(JComboBox cbxAmountProduct) {
        this.cbxAmountProduct = cbxAmountProduct;
    }

    public JButton getBtnSave() {
        return btnSave;
    }

    public void setBtnSave(JButton btnSave) {
        this.btnSave = btnSave;
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

    public JLabel getTxtCodeProduct() {
        return txtCodeProduct;
    }

    public void setTxtCodeProduct(JLabel txtCodeProduct) {
        this.txtCodeProduct = txtCodeProduct;
    }

    public JLabel getTxtNameProduct() {
        return txtNameProduct;
    }

    public void setTxtNameProduct(JLabel txtNameProduct) {
        this.txtNameProduct = txtNameProduct;
    }

    public JLabel getTxtPriceProduct() {
        return txtPriceProduct;
    }

    public void setTxtPriceProduct(JLabel txtPriceProduct) {
        this.txtPriceProduct = txtPriceProduct;
    }

    public JLabel getTxtAmountProduct() {
        return txtAmountProduct;
    }

    public void setTxtAmountProduct(JLabel txtAmountProduct) {
        this.txtAmountProduct = txtAmountProduct;
    }

    public JButton getBtnSearchProduct() {
        return btnSearchProduct;
    }

    public void setBtnSearchProduct(JButton btnSearchProduct) {
        this.btnSearchProduct = btnSearchProduct;
    }

    public JLabel getTxtSubTotal() {
        return txtSubTotal;
    }

    public void setTxtSubTotal(JLabel txtSubTotal) {
        this.txtSubTotal = txtSubTotal;
    }

    public JLabel getTxtTax() {
        return txtTax;
    }

    public void setTxtTax(JLabel txtTax) {
        this.txtTax = txtTax;
    }

    public JTextField getLblTax() {
        return lblTax;
    }

    public void setLblTax(JTextField lblTax) {
        this.lblTax = lblTax;
    }

    public JTextField getLblTotal() {
        return lblTotal;
    }

    public void setLblTotal(JTextField lblTotal) {
        this.lblTotal = lblTotal;
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

    public JTable getTblProducts() {
        return tblProducts;
    }

    public void setTblProducts(JTable tblProducts) {
        this.tblProducts = tblProducts;
    }

    public JButton getBtnDeleteItem() {
        return btnDeleteItem;
    }

    public void setBtnDeleteItem(JButton btnDeleteItem) {
        this.btnDeleteItem = btnDeleteItem;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
    }

    public void cargarDatosCombobox(){
        cbxAmountProduct.removeAllItems();
        for (int i = 1; i <= 20; i++) {
            cbxAmountProduct.addItem(i);
        }
    }

    public void mostrarProductos(List<Producto> productos) {
        if (productos != null && !productos.isEmpty()) {
            lblNameProduct.setText(productos.get(0).getNombre());
            lblPriceProduct.setText(String.valueOf(productos.get(0).getPrecio()));
        } else {
            lblNameProduct.setText("");
            lblPriceProduct.setText("");
            JOptionPane.showMessageDialog(this, "No products found.", "Info", JOptionPane.INFORMATION_MESSAGE);
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

    public int mostrarMensajeDelate(String mensaje, String titulo, int tipo) {
        Object[] botones = {"Confirm", "Cancel"};
        return JOptionPane.showOptionDialog(
                this, mensaje, titulo,
                JOptionPane.DEFAULT_OPTION, tipo,
                null, botones, botones[0]);
    }

    public int mostrarMensajeGuardar(String mesaje, String titulo, int tipo){
        Object[] botones = {"Confirm", "Cancelar"};
        return JOptionPane.showOptionDialog(
                this, mesaje, titulo,
                JOptionPane.DEFAULT_OPTION, tipo,
                null, botones, botones[0]);
    };
}
