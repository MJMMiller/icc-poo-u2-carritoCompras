package ec.edu.ups.poo.vista.producto;

import ec.edu.ups.poo.modelo.Producto;
import ec.edu.ups.poo.util.FormateadorUtils;
import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.util.enums.TipoIcono;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;
import java.util.Locale;

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
    private MensajeInternacionalizacionHandler i18n;

    public ProductoListarView(MensajeInternacionalizacionHandler i18n) {
        this.i18n = i18n;
        setContentPane(panelAll);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        cargarTabla();
        aplicarIdioma();
        aplicarIconos();
    }

    public void mostrarProductos(List<Producto> productos) {
        modelo.setRowCount(0);
        Locale locale = i18n.getLocale();
        for (Producto producto : productos) {
            String precioFormateado = FormateadorUtils.formatearMoneda(producto.getPrecio(), locale);
            modelo.addRow(new Object[]{
                    producto.getCodigo(),
                    producto.getNombre(),
                    precioFormateado
            });
        }
    }

    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }

    public void aplicarIdioma() {
        setTitle(i18n.get("producto.listar.titulo"));
        lblTitulo.setText(i18n.get("producto.listar.lbl.titulo"));
        lblNombre.setText(i18n.get("producto.listar.lbl.nombre"));
        btnBuscar.setText(i18n.get("producto.listar.btn.buscar"));
        btnListar.setText(i18n.get("producto.listar.btn.listar"));
        tableProdcuts.getColumnModel().getColumn(0).setHeaderValue(i18n.get("producto.listar.columna.codigo"));
        tableProdcuts.getColumnModel().getColumn(1).setHeaderValue(i18n.get("producto.listar.columna.nombre"));
        tableProdcuts.getColumnModel().getColumn(2).setHeaderValue(i18n.get("producto.listar.columna.precio"));
        tableProdcuts.getTableHeader().repaint();
    }

    public void aplicarIconos() {
        setFrameIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.PRODUCTO));
        btnBuscar.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.BUSCAR));
        btnListar.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.LISTAR));
    }

    public void cargarTabla(){
        modelo = new DefaultTableModel(new Object[]{"Codigo", "Nombre", "Precio"}, 0) {
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
    public JButton getBtnListProducts() {return btnListar;}
    public void setBtnListProducts(JButton btnListProducts) {this.btnListar = btnListProducts;}
    public JPanel getPanelButtom() {return panelButtom;}
    public void setPanelButtom(JPanel panelButtom) {this.panelButtom = panelButtom;}
    public JTable getTableProdcuts() {return tableProdcuts;}
    public void setTableProdcuts(JTable tableProdcuts) {this.tableProdcuts = tableProdcuts;}
    public JPanel getPanelTop() {return panelTop;}
    public void setPanelTop(JPanel panelTop) {this.panelTop = panelTop;}
    public JLabel getTxtSearchProdcut() {return lblTitulo;}
    public void setTxtSearchProdcut(JLabel txtSearchProdcut) {this.lblTitulo = txtSearchProdcut;}
    public JPanel getPanelAll() {return panelAll;}
    public void setPanelAll(JPanel panelAll) {this.panelAll = panelAll;}
    public JScrollPane getScroll() {return scroll;}
    public void setScroll(JScrollPane scroll) {this.scroll = scroll;}
    public DefaultTableModel getModelo() {return modelo;}
    public void setModelo(DefaultTableModel modelo) {this.modelo = modelo;}
}