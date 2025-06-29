package ec.edu.ups.poo.vista.carrito;

import ec.edu.ups.poo.modelo.ItemCarrito;
import ec.edu.ups.poo.modelo.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

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
    private JLabel txtAmountProduct;
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

    public JTextField getLblSubTotal() {
        return lblSubTotal;
    }

    public void setLblSubTotal(JTextField lblSubTotal) {
        this.lblSubTotal = lblSubTotal;
    }

    public JComboBox getCbxCantidad() {
        return cbxCantidad;
    }

    public void setCbxCantidad(JComboBox cbxCantidad) {
        this.cbxCantidad = cbxCantidad;
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

    public JLabel getTxtAmountProduct() {
        return txtAmountProduct;
    }

    public void setTxtAmountProduct(JLabel txtAmountProduct) {
        this.txtAmountProduct = txtAmountProduct;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    public JLabel getTxtSubTotal() {
        return txtSubTotal;
    }

    public void setTxtSubTotal(JLabel txtSubTotal) {
        this.txtSubTotal = txtSubTotal;
    }

    public JLabel getLblIva() {
        return lblIva;
    }

    public void setLblIva(JLabel lblIva) {
        this.lblIva = lblIva;
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

    public JLabel getLblItemsCarrito() {
        return lblItemsCarrito;
    }

    public void setLblItemsCarrito(JLabel lblItemsCarrito) {
        this.lblItemsCarrito = lblItemsCarrito;
    }

    public JTextField getTxtSubTot() {
        return txtSubTot;
    }

    public void setTxtSubTot(JTextField txtSubTot) {
        this.txtSubTot = txtSubTot;
    }

    public JTextField getTxtIva() {
        return txtIva;
    }

    public void setTxtIva(JTextField txtIva) {
        this.txtIva = txtIva;
    }

    public JTextField getTxtTot() {
        return txtTot;
    }

    public void setTxtTot(JTextField txtTot) {
        this.txtTot = txtTot;
    }

    public JLabel getLblTot() {
        return lblTot;
    }

    public void setLblTot(JLabel lblTot) {
        this.lblTot = lblTot;
    }

    public JLabel getLblSubTot() {
        return lblSubTot;
    }

    public void setLblSubTot(JLabel lblSubTot) {
        this.lblSubTot = lblSubTot;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
    }

    public void cargarDatosCombobox() {
        cbxCantidad.removeAllItems();
        for (int i = 1; i <= 20; i++) {
            cbxCantidad.addItem(i);
        }
    }

    public void mostrarProductos(List<Producto> productos) {
        if (productos != null && !productos.isEmpty()) {
            txtNombre.setText(productos.get(0).getNombre());
            txtPrecio.setText(String.valueOf(productos.get(0).getPrecio()));
        } else {
            txtNombre.setText("");
            txtPrecio.setText("");
        }
    }

    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }

    public void limpiarCampos() {
        cbxCantidad.setSelectedIndex(0);
        lblCodeProductSearch.setText("");
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
