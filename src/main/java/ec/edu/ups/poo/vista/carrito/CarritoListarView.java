package ec.edu.ups.poo.vista.carrito;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class CarritoListarView extends JInternalFrame{
    private JPanel panelAll;
    private JPanel panelSuperior;
    private JLabel txtSettingsProduc;
    private JPanel panelCenter;
    private JLabel txtNameProduct;
    private JTextField lblNameProdcutSearch;
    private JButton btnSearch;
    private JButton btnListCarts;
    private JTable tblCarritos;
    private JButton btnVerCarrito;
    private JPanel panelTabla;
    private JScrollPane scroll;
    private JPanel panelInferior;
    private DefaultTableModel modelo;

    public CarritoListarView() {
        setContentPane(panelAll);
        setTitle("Carrito de Compras");
        setSize(800, 600);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        modelo = new DefaultTableModel(new Object[]{"Code", "User", "Fecha", "SubTotal", "Iva", "Total"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblCarritos.setModel(modelo);

        Color fondo = new Color(29, 30, 32);
        Color letras = Color.WHITE;

        if (scroll == null && tblCarritos != null) {
            scroll = (JScrollPane) tblCarritos.getParent().getParent();
        }
        if (scroll != null) {
            scroll.getViewport().setBackground(fondo);
            scroll.setBackground(fondo);
        }

        if (tblCarritos != null) {
            tblCarritos.setBackground(fondo);
            tblCarritos.setForeground(letras);
            tblCarritos.setSelectionBackground(new Color(50, 50, 60));
            tblCarritos.setSelectionForeground(Color.WHITE);
            tblCarritos.setGridColor(fondo);

            JTableHeader header = tblCarritos.getTableHeader();
            header.setBackground(fondo);
            header.setForeground(letras);
            header.setFont(header.getFont().deriveFont(Font.BOLD));
            ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            centerRenderer.setForeground(letras);
            centerRenderer.setBackground(fondo);
            for (int i = 0; i < tblCarritos.getColumnCount(); i++) {
                tblCarritos.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }
    }

    public JPanel getPanelAll() {
        return panelAll;
    }

    public void setPanelAll(JPanel panelAll) {
        this.panelAll = panelAll;
    }

    public JPanel getPanelSuperior() {
        return panelSuperior;
    }

    public void setPanelSuperior(JPanel panelSuperior) {
        this.panelSuperior = panelSuperior;
    }

    public JLabel getTxtSettingsProduc() {
        return txtSettingsProduc;
    }

    public void setTxtSettingsProduc(JLabel txtSettingsProduc) {
        this.txtSettingsProduc = txtSettingsProduc;
    }

    public JPanel getPanelCenter() {
        return panelCenter;
    }

    public void setPanelCenter(JPanel panelCenter) {
        this.panelCenter = panelCenter;
    }

    public JLabel getTxtNameProduct() {
        return txtNameProduct;
    }

    public void setTxtNameProduct(JLabel txtNameProduct) {
        this.txtNameProduct = txtNameProduct;
    }

    public JTextField getLblNameProdcutSearch() {
        return lblNameProdcutSearch;
    }

    public void setLblNameProdcutSearch(JTextField lblNameProdcutSearch) {
        this.lblNameProdcutSearch = lblNameProdcutSearch;
    }

    public JButton getBtnSearch() {
        return btnSearch;
    }

    public void setBtnSearch(JButton btnSearch) {
        this.btnSearch = btnSearch;
    }

    public JButton getBtnListCarts() {
        return btnListCarts;
    }

    public void setBtnListCarts(JButton btnListCarts) {
        this.btnListCarts = btnListCarts;
    }

    public JTable getTblCarritos() {
        return tblCarritos;
    }

    public void setTblCarritos(JTable tblCarritos) {
        this.tblCarritos = tblCarritos;
    }

    public JButton getBtnVerCarrito() {
        return btnVerCarrito;
    }

    public void setBtnVerCarrito(JButton btnVerCarrito) {
        this.btnVerCarrito = btnVerCarrito;
    }

    public JPanel getPanelTabla() {
        return panelTabla;
    }

    public void setPanelTabla(JPanel panelTabla) {
        this.panelTabla = panelTabla;
    }

    public JScrollPane getScroll() {
        return scroll;
    }

    public void setScroll(JScrollPane scroll) {
        this.scroll = scroll;
    }

    public JPanel getPanelInferior() {
        return panelInferior;
    }

    public void setPanelInferior(JPanel panelInferior) {
        this.panelInferior = panelInferior;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
    }

    public int mostrarMensajeAbrir(String mensaje, String titulo, int tipo) {
        Object[] botones = {"Confirm", "Cancel"};
        return JOptionPane.showOptionDialog(
                this, mensaje, titulo,
                JOptionPane.DEFAULT_OPTION, tipo,
                null, botones, botones[0]);
    }

    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }
}


