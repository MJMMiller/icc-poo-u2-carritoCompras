package ec.edu.ups.poo.vista;

import ec.edu.ups.poo.modelo.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class ProductoListarView extends JInternalFrame{

    private JPanel panelCenter;
    private JLabel txtNameProduct;
    private JTextField lblNameProdcutSearch;
    private JButton btnSearch;
    private JButton btnListProducts;
    private JPanel panelButtom;
    private JTable tableProdcuts;
    private JPanel panelTop;
    private JLabel txtSearchProdcut;
    private JPanel panelAll;
    private JScrollPane scroll;
    private DefaultTableModel modelo;

    public ProductoListarView() {
        setContentPane(panelAll);
        setTitle("List Products");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 400);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        modelo = new DefaultTableModel(new Object[]{"Code", "Name", "Price"}, 0);
        tableProdcuts.setModel(modelo);

        Color fondo = new Color(29, 30, 32);
        Color letras = Color.WHITE;

        if (scroll == null) {
            scroll = (JScrollPane) tableProdcuts.getParent().getParent();
        }
        scroll.getViewport().setBackground(fondo);
        scroll.setBackground(fondo);

        tableProdcuts.setBackground(fondo);
        tableProdcuts.setForeground(letras);
        tableProdcuts.setSelectionBackground(new Color(50, 50, 60));
        tableProdcuts.setSelectionForeground(Color.WHITE);
        tableProdcuts.setGridColor(fondo);

        JTableHeader header = tableProdcuts.getTableHeader();
        header.setBackground(fondo);
        header.setForeground(letras);
        header.setFont(header.getFont().deriveFont(Font.BOLD));
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        centerRenderer.setForeground(letras);
        centerRenderer.setBackground(fondo);
        for (int i = 0; i < tableProdcuts.getColumnCount(); i++) {
            tableProdcuts.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

    }

    public JTextField getLblNameProdcutSearch() {
        return lblNameProdcutSearch;
    }

    public JButton getBtnSearch() {
        return btnSearch;
    }

    public JButton getBtnListProducts() {
        return btnListProducts;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
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
}