package ec.edu.ups.poo.vista.carrito;

import ec.edu.ups.poo.modelo.ItemCarrito;
import ec.edu.ups.poo.modelo.Rol;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CarritoListarItemsView extends JInternalFrame {
    private JPanel panelTitle;
    private JLabel txtSettingsProduc;
    private JPanel panelProduct;
    private JLabel txtCodeProduct;
    private JTextField txtCodeCart;
    private JLabel txtNameProduct;
    private JTextField txtRolUser;
    private JTextField txtFecha;
    private JLabel txtPriceProduct;
    private JPanel panelItems;
    private JScrollPane scroll;
    private JTable tblProducts;
    private JLabel txtSubTotal;
    private JLabel txtTax;
    private JTextField lblTax;
    private JLabel txtTotal;
    private JTextField lblTotal;
    private JTextField lblSubTotal;
    private JPanel panelInferior;
    private JButton btnExit;
    private JPanel panelAll;
    private JTextField txtUsername;
    private DefaultTableModel modelo;

    public CarritoListarItemsView(
            int carritoId,
            List<ItemCarrito> items,
            double subtotal,
            double iva,
            double total,
            String username,
            Rol rol,
            String fecha) {

        setContentPane(panelAll);
        setTitle("Carrito - Listar Items");
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        modelo = new DefaultTableModel(new Object[]{"Item", "Name", "Price", "Amount", "Total Item"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblProducts.setModel(modelo);

        Color fondo = new Color(29, 30, 32);
        Color letras = Color.WHITE;

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

        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        mostrarDatos(carritoId, items, subtotal, iva, total, username, rol, fecha);
    }

    public JPanel getPanelTitle() {
        return panelTitle;
    }

    public void setPanelTitle(JPanel panelTitle) {
        this.panelTitle = panelTitle;
    }

    public JLabel getTxtSettingsProduc() {
        return txtSettingsProduc;
    }

    public void setTxtSettingsProduc(JLabel txtSettingsProduc) {
        this.txtSettingsProduc = txtSettingsProduc;
    }

    public JPanel getPanelProduct() {
        return panelProduct;
    }

    public void setPanelProduct(JPanel panelProduct) {
        this.panelProduct = panelProduct;
    }

    public JLabel getTxtCodeProduct() {
        return txtCodeProduct;
    }

    public void setTxtCodeProduct(JLabel txtCodeProduct) {
        this.txtCodeProduct = txtCodeProduct;
    }

    public JTextField getLblCodeProductSearch() {
        return txtCodeCart;
    }

    public void setLblCodeProductSearch(JTextField lblCodeProductSearch) {
        this.txtCodeCart = lblCodeProductSearch;
    }

    public JLabel getTxtNameProduct() {
        return txtNameProduct;
    }

    public void setTxtNameProduct(JLabel txtNameProduct) {
        this.txtNameProduct = txtNameProduct;
    }

    public JTextField getLblNameProduct() {
        return txtRolUser;
    }

    public void setLblNameProduct(JTextField lblNameProduct) {
        this.txtRolUser = lblNameProduct;
    }

    public JTextField getLblPriceProduct() {
        return txtFecha;
    }

    public void setLblPriceProduct(JTextField lblPriceProduct) {
        this.txtFecha = lblPriceProduct;
    }

    public JLabel getTxtPriceProduct() {
        return txtPriceProduct;
    }

    public void setTxtPriceProduct(JLabel txtPriceProduct) {
        this.txtPriceProduct = txtPriceProduct;
    }

    public JPanel getPanelItems() {
        return panelItems;
    }

    public void setPanelItems(JPanel panelItems) {
        this.panelItems = panelItems;
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

    public JLabel getTxtTotal() {
        return txtTotal;
    }

    public void setTxtTotal(JLabel txtTotal) {
        this.txtTotal = txtTotal;
    }

    public JTextField getLblTotal() {
        return lblTotal;
    }

    public void setLblTotal(JTextField lblTotal) {
        this.lblTotal = lblTotal;
    }

    public JTextField getLblSubTotal() {
        return lblSubTotal;
    }

    public void setLblSubTotal(JTextField lblSubTotal) {
        this.lblSubTotal = lblSubTotal;
    }

    public JPanel getPanelInferior() {
        return panelInferior;
    }

    public void setPanelInferior(JPanel panelInferior) {
        this.panelInferior = panelInferior;
    }

    public JButton getBtnSave() {
        return btnExit;
    }

    public void setBtnSave(JButton btnSave) {
        this.btnExit = btnSave;
    }

    public JPanel getPanelAll() {
        return panelAll;
    }

    public void setPanelAll(JPanel panelAll) {
        this.panelAll = panelAll;
    }

    public JTextField getTextField1() {
        return txtUsername;
    }

    public void setTextField1(JTextField textField1) {
        this.txtUsername = textField1;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
    }

    public void mostrarDatos(
            int carritoId,
            List<ItemCarrito> items,
            double subtotal,
            double iva,
            double total,
            String username,
            Rol rol,
            String fecha
    ) {
        if (modelo != null) {
            modelo.setRowCount(0);
            for (ItemCarrito item : items) {
                modelo.addRow(new Object[]{
                        item.getProducto().getCodigo(),
                        item.getProducto().getNombre(),
                        item.getProducto().getPrecio(),
                        item.getCantidad(),
                        item.getTotalItem()
                });
            }
        }
        if (lblSubTotal != null) lblSubTotal.setText(String.format("%.2f", subtotal));
        if (lblTax != null) lblTax.setText(String.format("%.2f", iva));
        if (lblTotal != null) lblTotal.setText(String.format("%.2f", total));
        if (txtCodeCart != null) txtCodeCart.setText(String.valueOf(carritoId));
        if (txtUsername != null) txtUsername.setText(username);
        if (txtRolUser != null && rol != null) txtRolUser.setText(rol.toString());
        if (txtFecha != null) txtFecha.setText(fecha);
    }
}