package ec.edu.ups.poo.vista;

import ec.edu.ups.poo.modelo.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class ProducEditarView extends JFrame {
    private JPanel panelAll;
    private JTextField lblCodeProductSearch;
    private JButton btnSearch;
    private JTable tblProducts;
    private JTextField lblNameProduct;
    private JLabel txtCodeTitle;
    private JPanel panelCenter;
    private JPanel panelTable;
    private JPanel panelTop;
    private JTextField lblPriceProduct;
    private JButton btnDelate;
    private JButton btnUpdate;
    private JLabel txtPriceTitle;
    private JLabel txtNameTitle;
    private JPanel panelMenor;
    private JLabel txtSettingsProduc;
    private JScrollPane scroll;

    private DefaultTableModel modelo;

    public ProducEditarView() {
        setContentPane(panelAll);
        setTitle("Settings Products");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);

        modelo = new DefaultTableModel(new Object[]{"Code", "Name", "Price"}, 0);
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

        setVisible(true);
    }

    public JTextField getLblCodeProductSearch() {
        return lblCodeProductSearch;
    }

    public JTable getTblProducts() {
        return tblProducts;
    }

    public JTextField getLblNameProduct() {
        return lblNameProduct;
    }

    public JTextField getLblPriceProduct() {
        return lblPriceProduct;
    }

    public JButton getBtnSearch() {
        return btnSearch;
    }

    public JButton getBtnDelate() {
        return btnDelate;
    }

    public JButton getBtnUpdate() {
        return btnUpdate;
    }

    public void mostrarProductos(List<Producto> productos) {
        modelo.setRowCount(0);
        for (Producto producto : productos) {
            modelo.addRow(new Object[]{
                    producto.getCodigo(),
                    producto.getNombre(),
                    producto.getPrecio()
            });
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

    public int getSelectedRow() {
        return tblProducts.getSelectedRow();
    }
}