package ec.edu.ups.poo.vista.producto;

import ec.edu.ups.poo.modelo.Producto;
import ec.edu.ups.poo.util.FormateadorUtils;
import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.util.TipoIcono;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;
import java.util.Locale;

/**
 * Vista interna para listar productos en el sistema.
 * Permite mostrar, buscar y filtrar productos, y visualizar sus datos en una tabla.
 */
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

    /**
     * Constructor de ProductoListarView.
     * Inicializa la vista, configura la tabla, idioma e iconos.
     *
     * @param i18n Manejador de internacionalización de mensajes.
     */
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

    /**
     * Muestra una lista de productos en la tabla de la vista.
     *
     * @param productos Lista de productos a mostrar.
     */
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

    /**
     * Muestra un mensaje en un cuadro de diálogo.
     *
     * @param mensaje Mensaje a mostrar.
     * @param titulo Título de la ventana de diálogo.
     * @param tipo Tipo de mensaje (JOptionPane).
     */
    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }

    /**
     * Aplica los textos traducidos a los componentes de la vista según el idioma actual.
     * No recibe parámetros ni retorna valores.
     */
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

    /**
     * Aplica los iconos correspondientes a los botones de la vista.
     * No recibe parámetros ni retorna valores.
     */
    public void aplicarIconos() {
        setFrameIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.PRODUCTO));
        btnBuscar.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.BUSCAR));
        btnListar.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.LISTAR));
    }

    /**
     * Configura y personaliza la tabla de productos, incluyendo colores, alineación y renderizado.
     * No recibe parámetros ni retorna valores.
     */
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

    // Métodos getter y setter para los componentes de la vista.

    /**
     * Obtiene el panel central de la vista.
     * @return JPanel central.
     */
    public JPanel getPanelCenter() { return panelCenter; }
    /**
     * Establece el panel central de la vista.
     * @param panelCenter JPanel central.
     */
    public void setPanelCenter(JPanel panelCenter) { this.panelCenter = panelCenter; }
    /**
     * Obtiene la etiqueta de nombre del producto.
     * @return JLabel de nombre de producto.
     */
    public JLabel getTxtNameProduct() { return lblNombre; }
    /**
     * Establece la etiqueta de nombre del producto.
     * @param txtNameProduct JLabel de nombre de producto.
     */
    public void setTxtNameProduct(JLabel txtNameProduct) { this.lblNombre = txtNameProduct; }
    /**
     * Obtiene el campo de texto para buscar producto por nombre.
     * @return JTextField de búsqueda de producto.
     */
    public JTextField getLblNameProdcutSearch() { return txtNombre; }
    /**
     * Establece el campo de texto para buscar producto por nombre.
     * @param lblNameProdcutSearch JTextField de búsqueda de producto.
     */
    public void setLblNameProdcutSearch(JTextField lblNameProdcutSearch) { this.txtNombre = lblNameProdcutSearch; }
    /**
     * Obtiene el botón para buscar productos.
     * @return JButton para buscar productos.
     */
    public JButton getBtnSearch() { return btnBuscar; }
    /**
     * Establece el botón para buscar productos.
     * @param btnSearch JButton para buscar productos.
     */
    public void setBtnSearch(JButton btnSearch) { this.btnBuscar = btnSearch; }
    /**
     * Obtiene el botón para listar productos.
     * @return JButton para listar productos.
     */
    public JButton getBtnListProducts() {return btnListar;}
    /**
     * Establece el botón para listar productos.
     * @param btnListProducts JButton para listar productos.
     */
    public void setBtnListProducts(JButton btnListProducts) {this.btnListar = btnListProducts;}
    /**
     * Obtiene el panel inferior de la vista.
     * @return JPanel inferior.
     */
    public JPanel getPanelButtom() {return panelButtom;}
    /**
     * Establece el panel inferior de la vista.
     * @param panelButtom JPanel inferior.
     */
    public void setPanelButtom(JPanel panelButtom) {this.panelButtom = panelButtom;}
    /**
     * Obtiene la tabla de productos.
     * @return JTable de productos.
     */
    public JTable getTableProdcuts() {return tableProdcuts;}
    /**
     * Establece la tabla de productos.
     * @param tableProdcuts JTable de productos.
     */
    public void setTableProdcuts(JTable tableProdcuts) {this.tableProdcuts = tableProdcuts;}
    /**
     * Obtiene el panel superior de la vista.
     * @return JPanel superior.
     */
    public JPanel getPanelTop() {return panelTop;}
    /**
     * Establece el panel superior de la vista.
     * @param panelTop JPanel superior.
     */
    public void setPanelTop(JPanel panelTop) {this.panelTop = panelTop;}
    /**
     * Obtiene la etiqueta del título de la vista.
     * @return JLabel del título.
     */
    public JLabel getTxtSearchProdcut() {return lblTitulo;}
    /**
     * Establece la etiqueta del título de la vista.
     * @param txtSearchProdcut JLabel del título.
     */
    public void setTxtSearchProdcut(JLabel txtSearchProdcut) {this.lblTitulo = txtSearchProdcut;}
    /**
     * Obtiene el panel principal de la vista.
     * @return JPanel principal.
     */
    public JPanel getPanelAll() {return panelAll;}
    /**
     * Establece el panel principal de la vista.
     * @param panelAll JPanel principal.
     */
    public void setPanelAll(JPanel panelAll) {this.panelAll = panelAll;}
    /**
     * Obtiene el JScrollPane de la tabla.
     * @return JScrollPane de la tabla.
     */
    public JScrollPane getScroll() {return scroll;}
    /**
     * Establece el JScrollPane de la tabla.
     * @param scroll JScrollPane de la tabla.
     */
    public void setScroll(JScrollPane scroll) {this.scroll = scroll;}
    /**
     * Obtiene el modelo de la tabla de productos.
     * @return DefaultTableModel de la tabla.
     */
    public DefaultTableModel getModelo() {return modelo;}
    /**
     * Establece el modelo de la tabla de productos.
     * @param modelo DefaultTableModel de la tabla.
     */
    public void setModelo(DefaultTableModel modelo) {this.modelo = modelo;}
}