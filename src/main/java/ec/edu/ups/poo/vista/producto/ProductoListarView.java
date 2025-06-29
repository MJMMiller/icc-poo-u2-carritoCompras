package ec.edu.ups.poo.vista.producto;

import ec.edu.ups.poo.modelo.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class ProductoListarView extends JInternalFrame{

    private JPanel panelCenter;
    private JLabel lblNombre;
    private JTextField txtNombre;
    private JButton btnBuscar;
    private JButton btnListar;
    private JPanel panelButtom;
    private JTable tableProdcuts;
    private JPanel panelTop;
    private JLabel lblTitulo;
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

        modelo = new DefaultTableModel(new Object[]{"Code", "Name", "Price"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
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

    public JPanel getPanelCenter() {
        return panelCenter;
    }

    public void setPanelCenter(JPanel panelCenter) {
        this.panelCenter = panelCenter;
    }

    public JLabel getTxtNameProduct() {
        return lblNombre;
    }

    public void setTxtNameProduct(JLabel txtNameProduct) {
        this.lblNombre = txtNameProduct;
    }

    public JTextField getLblNameProdcutSearch() {
        return txtNombre;
    }

    public void setLblNameProdcutSearch(JTextField lblNameProdcutSearch) {
        this.txtNombre = lblNameProdcutSearch;
    }

    public JButton getBtnSearch() {
        return btnBuscar;
    }

    public void setBtnSearch(JButton btnSearch) {
        this.btnBuscar = btnSearch;
    }

    public JButton getBtnListProducts() {
        return btnListar;
    }

    public void setBtnListProducts(JButton btnListProducts) {
        this.btnListar = btnListProducts;
    }

    public JPanel getPanelButtom() {
        return panelButtom;
    }

    public void setPanelButtom(JPanel panelButtom) {
        this.panelButtom = panelButtom;
    }

    public JTable getTableProdcuts() {
        return tableProdcuts;
    }

    public void setTableProdcuts(JTable tableProdcuts) {
        this.tableProdcuts = tableProdcuts;
    }

    public JPanel getPanelTop() {
        return panelTop;
    }

    public void setPanelTop(JPanel panelTop) {
        this.panelTop = panelTop;
    }

    public JLabel getTxtSearchProdcut() {
        return lblTitulo;
    }

    public void setTxtSearchProdcut(JLabel txtSearchProdcut) {
        this.lblTitulo = txtSearchProdcut;
    }

    public JPanel getPanelAll() {
        return panelAll;
    }

    public void setPanelAll(JPanel panelAll) {
        this.panelAll = panelAll;
    }

    public JScrollPane getScroll() {
        return scroll;
    }

    public void setScroll(JScrollPane scroll) {
        this.scroll = scroll;
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