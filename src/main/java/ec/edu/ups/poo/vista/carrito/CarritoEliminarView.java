package ec.edu.ups.poo.vista.carrito;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class CarritoEliminarView extends JInternalFrame {
    private JPanel panelTitle;
    private JLabel txtSettingsProduc;
    private JPanel panelProduct;
    private JTextField lblCodeSerachCart;
    private JButton btnCodeSearchCart;
    private JScrollPane scroll;
    private JTable tblProducts;
    private JTextField lblTax;
    private JTextField lblTotal;
    private JButton btnDelateItem;
    private JTextField lblSubTotal;
    private JButton btnDelate;
    private JPanel panelAll;

    public CarritoEliminarView() {
        setContentPane(panelAll);
        setTitle("Delete Cart");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 400);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        Color fondoOscuro = new Color(29,30,32);

        panelAll.setBackground(fondoOscuro);

        tblProducts.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Código", "Nombre", "Precio", "Cantidad", "Total Item"}
        ));
        tblProducts.setBackground(fondoOscuro);
        tblProducts.setForeground(Color.WHITE);
        scroll.getViewport().setBackground(fondoOscuro);
        scroll.setBackground(fondoOscuro);

        JTableHeader header = tblProducts.getTableHeader();
        header.setBackground(Color.BLACK);
        header.setForeground(Color.WHITE);
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
    }

    public JPanel getPanelTitle() { return panelTitle; }
    public void setPanelTitle(JPanel panelTitle) { this.panelTitle = panelTitle; }
    public JLabel getTxtSettingsProduc() { return txtSettingsProduc; }
    public void setTxtSettingsProduc(JLabel txtSettingsProduc) { this.txtSettingsProduc = txtSettingsProduc; }
    public JPanel getPanelProduct() { return panelProduct; }
    public void setPanelProduct(JPanel panelProduct) { this.panelProduct = panelProduct; }
    public JTextField getLblCodeSerachCart() { return lblCodeSerachCart; }
    public void setLblCodeSerachCart(JTextField lblCodeSerachCart) { this.lblCodeSerachCart = lblCodeSerachCart; }
    public JButton getBtnCodeSearchCart() { return btnCodeSearchCart; }
    public void setBtnCodeSearchCart(JButton btnCodeSearchCart) { this.btnCodeSearchCart = btnCodeSearchCart; }
    public JScrollPane getScroll() { return scroll; }
    public void setScroll(JScrollPane scroll) { this.scroll = scroll; }
    public JTable getTblProducts() { return tblProducts; }
    public void setTblProducts(JTable tblProducts) { this.tblProducts = tblProducts; }
    public JTextField getLblTax() { return lblTax; }
    public void setLblTax(JTextField lblTax) { this.lblTax = lblTax; }
    public JTextField getLblTotal() { return lblTotal; }
    public void setLblTotal(JTextField lblTotal) { this.lblTotal = lblTotal; }
    public JButton getBtnDelateItem() { return btnDelateItem; }
    public void setBtnDelateItem(JButton btnDelateItem) { this.btnDelateItem = btnDelateItem; }
    public JTextField getLblSubTotal() { return lblSubTotal; }
    public void setLblSubTotal(JTextField lblSubTotal) { this.lblSubTotal = lblSubTotal; }
    public JButton getBtnDelate() { return btnDelate; }
    public void setBtnDelate(JButton btnDelate) { this.btnDelate = btnDelate; }
    public JPanel getPanelAll() { return panelAll; }
    public void setPanelAll(JPanel panelAll) { this.panelAll = panelAll; }

    public int mostrarMensajeDelate(String mensaje, String titulo, int tipo) {
        Object[] botones = {"Confirm", "Cancel"};
        return JOptionPane.showOptionDialog(
                this, mensaje, titulo,
                JOptionPane.DEFAULT_OPTION, tipo,
                null, botones, botones[0]);
    }
}