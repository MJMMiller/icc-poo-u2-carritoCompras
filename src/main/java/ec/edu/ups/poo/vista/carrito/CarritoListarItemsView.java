package ec.edu.ups.poo.vista.carrito;

import ec.edu.ups.poo.modelo.ItemCarrito;
import ec.edu.ups.poo.modelo.Rol;
import ec.edu.ups.poo.util.FormateadorUtils;
import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.util.TipoIcono;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Locale;

/**
 * Vista para listar los ítems de un carrito de compras.
 * Permite visualizar los detalles de los productos agregados al carrito, junto con información de usuario, fecha y totales.
 */
public class CarritoListarItemsView extends JInternalFrame {
    private JPanel panelTitle;
    private JLabel lblTitulo;
    private JPanel panelProduct;
    private JLabel lblCodigo;
    private JTextField txtCodigo;
    private JLabel lblUsuario;
    private JTextField txtRolUser;
    private JTextField txtFecha;
    private JLabel lblFecha;
    private JPanel panelItems;
    private JScrollPane scroll;
    private JTable tblProducts;
    private JLabel lblSubtotal;
    private JLabel lblva;
    private JTextField txtIva;
    private JLabel lblTotal;
    private JTextField txtTotal;
    private JTextField txtSubTotal;
    private JPanel panelInferior;
    private JButton btnSalir;
    private JPanel panelAll;
    private JTextField txtUsuario;
    private JLabel lblItemsCarrito;
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler i18n;
    private double subtotal = 0.0;
    private double iva = 0.0;
    private double total = 0.0;
    private List<ItemCarrito> currentItems;


    /**
     * Constructor de la vista para listar ítems de un carrito.
     * Inicializa la interfaz gráfica y muestra los datos del carrito.
     * @param carritoId Identificador del carrito.
     * @param items Lista de ítems del carrito.
     * @param subtotal Subtotal del carrito.
     * @param iva IVA aplicado al carrito.
     * @param total Total del carrito.
     * @param username Nombre de usuario asociado al carrito.
     * @param rol Rol del usuario.
     * @param fecha Fecha de creación del carrito.
     * @param i18n Manejador de internacionalización para los textos de la interfaz.
     */
    public CarritoListarItemsView(
            int carritoId,
            List<ItemCarrito> items,
            double subtotal,
            double iva,
            double total,
            String username,
            Rol rol,
            String fecha,
            MensajeInternacionalizacionHandler i18n){

        this.i18n = i18n;
        this.subtotal = subtotal;
        this.iva = iva;
        this.total = total;
        this.currentItems = items;

        setContentPane(panelAll);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        mostrarDatos(carritoId, items, subtotal, iva, total, username, rol, fecha);
        cargarTabla();
        aplicarIdioma();
    }

    /**
     * Muestra los datos del carrito en la interfaz.
     * @param carritoId Identificador del carrito.
     * @param items Lista de ítems del carrito.
     * @param subtotal Subtotal del carrito.
     * @param iva IVA aplicado al carrito.
     * @param total Total del carrito.
     * @param username Nombre de usuario asociado al carrito.
     * @param rol Rol del usuario.
     * @param fecha Fecha de creación del carrito.
     */
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
        this.subtotal = subtotal;
        this.iva = iva;
        this.total = total;
        this.currentItems = items;
        Locale locale = i18n.getLocale();

        if (modelo != null) {
            modelo.setRowCount(0);
            if (items != null) {
                for (ItemCarrito item : items) {
                    modelo.addRow(new Object[]{
                            item.getProducto().getCodigo(),
                            item.getProducto().getNombre(),
                            FormateadorUtils.formatearMoneda(item.getProducto().getPrecio(), locale),
                            item.getCantidad(),
                            FormateadorUtils.formatearMoneda(item.getTotalItem(), locale)
                    });
                }
            }
        }
        if (txtSubTotal != null) txtSubTotal.setText(FormateadorUtils.formatearMoneda(subtotal, locale));
        if (txtIva != null) txtIva.setText(FormateadorUtils.formatearMoneda(iva, locale));
        if (txtTotal != null) txtTotal.setText(FormateadorUtils.formatearMoneda(total, locale));
        if (txtCodigo != null) txtCodigo.setText(String.valueOf(carritoId));
        if (txtUsuario != null) txtUsuario.setText(username);
        if (txtRolUser != null && rol != null) txtRolUser.setText(rol.toString());
        if (txtFecha != null) txtFecha.setText(fecha);
    }

    /**
     * Configura y carga la tabla de productos del carrito.
     * Establece el modelo y el formato visual de la tabla.
     */
    public void cargarTabla() {
        modelo = new DefaultTableModel(new Object[]{"Item", "Nombre", "Precio", "Cantidad", "Total Item"}, 0) {
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
    }

    /**
     * Muestra un mensaje en un cuadro de diálogo.
     * @param mensaje Mensaje a mostrar.
     * @param titulo Título del cuadro de diálogo.
     * @param tipo Tipo de mensaje (por ejemplo, JOptionPane.INFORMATION_MESSAGE).
     */
    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }

    /**
     * Aplica los textos internacionalizados a los componentes de la interfaz.
     */
    public void aplicarIdioma() {
        setTitle(i18n.get("carrito.listar.item.titulo"));
        lblTitulo.setText(i18n.get("carrito.listar.item.lbl.titulo"));
        lblCodigo.setText(i18n.get("carrito.listar.item.lbl.codigo"));
        lblUsuario.setText(i18n.get("carrito.listar.item.lbl.usuario"));
        btnSalir.setText(i18n.get("carrito.listar.item.btn.salir"));
        lblItemsCarrito.setText(i18n.get("carrito.listar.items"));
        lblFecha.setText(i18n.get("carrito.listar.item.lbl.fecha"));

        tblProducts.getColumnModel().getColumn(0).setHeaderValue(i18n.get("carrito.listar.item..tbl.codigo"));
        tblProducts.getColumnModel().getColumn(1).setHeaderValue(i18n.get("carrito.listar.item..tbl.nombre"));
        tblProducts.getColumnModel().getColumn(2).setHeaderValue(i18n.get("carrito.listar.item.tbl.precio"));
        tblProducts.getColumnModel().getColumn(3).setHeaderValue(i18n.get("carrito.listar.item.tbl.cantidad"));
        tblProducts.getColumnModel().getColumn(4).setHeaderValue(i18n.get("carrito.listar.item.tbl.totalItem"));
        tblProducts.getTableHeader().repaint();

        refrescarFormatoMoneda();
    }

    /**
     * Refresca el formato de moneda en los campos y la tabla según la configuración regional.
     */
    public void refrescarFormatoMoneda() {
        Locale locale = i18n.getLocale();
        if (modelo != null) {
            modelo.setRowCount(0);
            if (currentItems != null) {
                for (ItemCarrito item : currentItems) {
                    modelo.addRow(new Object[]{
                            item.getProducto().getCodigo(),
                            item.getProducto().getNombre(),
                            FormateadorUtils.formatearMoneda(item.getProducto().getPrecio(), locale),
                            item.getCantidad(),
                            FormateadorUtils.formatearMoneda(item.getTotalItem(), locale)
                    });
                }
            }
        }
        if (txtSubTotal != null) txtSubTotal.setText(FormateadorUtils.formatearMoneda(subtotal, locale));
        if (txtIva != null) txtIva.setText(FormateadorUtils.formatearMoneda(iva, locale));
        if (txtTotal != null) txtTotal.setText(FormateadorUtils.formatearMoneda(total, locale));
    }

    /**
     * Aplica los iconos a los componentes de la interfaz.
     */
    public void aplicarIconos(){
        setFrameIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.CARRITO));
        btnSalir.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.X));
    }

    /**
     * Obtiene el panel del título.
     * @return El panel del título.
     */
    public JPanel getPanelTitle() {
        return panelTitle;
    }

    /**
     * Establece el panel del título.
     * @param panelTitle El panel del título.
     */
    public void setPanelTitle(JPanel panelTitle) {
        this.panelTitle = panelTitle;
    }

    /**
     * Obtiene la etiqueta del título.
     * @return La etiqueta del título.
     */
    public JLabel getLblTitulo() {
        return lblTitulo;
    }

    /**
     * Establece la etiqueta del título.
     * @param lblTitulo La etiqueta del título.
     */
    public void setLblTitulo(JLabel lblTitulo) {
        this.lblTitulo = lblTitulo;
    }

    /**
     * Obtiene el panel de producto.
     * @return El panel de producto.
     */
    public JPanel getPanelProduct() {
        return panelProduct;
    }

    /**
     * Establece el panel de producto.
     * @param panelProduct El panel de producto.
     */
    public void setPanelProduct(JPanel panelProduct) {
        this.panelProduct = panelProduct;
    }

    /**
     * Obtiene la etiqueta del código.
     * @return La etiqueta del código.
     */
    public JLabel getLblCodigo() {
        return lblCodigo;
    }

    /**
     * Establece la etiqueta del código.
     * @param lblCodigo La etiqueta del código.
     */
    public void setLblCodigo(JLabel lblCodigo) {
        this.lblCodigo = lblCodigo;
    }

    /**
     * Obtiene el campo de texto para el código.
     * @return El campo de texto del código.
     */
    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    /**
     * Establece el campo de texto para el código.
     * @param txtCodigo El campo de texto del código.
     */
    public void setTxtCodigo(JTextField txtCodigo) {
        this.txtCodigo = txtCodigo;
    }

    /**
     * Obtiene la etiqueta del usuario.
     * @return La etiqueta del usuario.
     */
    public JLabel getLblUsuario() {
        return lblUsuario;
    }

    /**
     * Establece la etiqueta del usuario.
     * @param lblUsuario La etiqueta del usuario.
     */
    public void setLblUsuario(JLabel lblUsuario) {
        this.lblUsuario = lblUsuario;
    }

    /**
     * Obtiene el campo de texto para el rol del usuario.
     * @return El campo de texto del rol del usuario.
     */
    public JTextField getTxtRolUser() {
        return txtRolUser;
    }

    /**
     * Establece el campo de texto para el rol del usuario.
     * @param txtRolUser El campo de texto del rol del usuario.
     */
    public void setTxtRolUser(JTextField txtRolUser) {
        this.txtRolUser = txtRolUser;
    }

    /**
     * Obtiene el campo de texto para la fecha.
     * @return El campo de texto de la fecha.
     */
    public JTextField getTxtFecha() {
        return txtFecha;
    }

    /**
     * Establece el campo de texto para la fecha.
     * @param txtFecha El campo de texto de la fecha.
     */
    public void setTxtFecha(JTextField txtFecha) {
        this.txtFecha = txtFecha;
    }

    /**
     * Obtiene la etiqueta de la fecha.
     * @return La etiqueta de la fecha.
     */
    public JLabel getLblFecha() {
        return lblFecha;
    }

    /**
     * Establece la etiqueta de la fecha.
     * @param lblFecha La etiqueta de la fecha.
     */
    public void setLblFecha(JLabel lblFecha) {
        this.lblFecha = lblFecha;
    }

    /**
     * Obtiene el panel de ítems.
     * @return El panel de ítems.
     */
    public JPanel getPanelItems() {
        return panelItems;
    }

    /**
     * Establece el panel de ítems.
     * @param panelItems El panel de ítems.
     */
    public void setPanelItems(JPanel panelItems) {
        this.panelItems = panelItems;
    }

    /**
     * Obtiene el scroll de la tabla de productos.
     * @return El scroll de la tabla.
     */
    public JScrollPane getScroll() {
        return scroll;
    }

    /**
     * Establece el scroll de la tabla de productos.
     * @param scroll El scroll de la tabla.
     */
    public void setScroll(JScrollPane scroll) {
        this.scroll = scroll;
    }

    /**
     * Obtiene la tabla de productos.
     * @return La tabla de productos.
     */
    public JTable getTblProducts() {
        return tblProducts;
    }

    /**
     * Establece la tabla de productos.
     * @param tblProducts La tabla de productos.
     */
    public void setTblProducts(JTable tblProducts) {
        this.tblProducts = tblProducts;
    }

    /**
     * Obtiene la etiqueta del subtotal.
     * @return La etiqueta del subtotal.
     */
    public JLabel getLblSubtotal() {
        return lblSubtotal;
    }

    /**
     * Establece la etiqueta del subtotal.
     * @param lblSubtotal La etiqueta del subtotal.
     */
    public void setLblSubtotal(JLabel lblSubtotal) {
        this.lblSubtotal = lblSubtotal;
    }

    /**
     * Obtiene la etiqueta del IVA.
     * @return La etiqueta del IVA.
     */
    public JLabel getLblva() {
        return lblva;
    }

    /**
     * Establece la etiqueta del IVA.
     * @param lblva La etiqueta del IVA.
     */
    public void setLblva(JLabel lblva) {
        this.lblva = lblva;
    }

    /**
     * Obtiene el campo de texto para el IVA.
     * @return El campo de texto del IVA.
     */
    public JTextField getTxtIva() {
        return txtIva;
    }

    /**
     * Establece el campo de texto para el IVA.
     * @param txtIva El campo de texto del IVA.
     */
    public void setTxtIva(JTextField txtIva) {
        this.txtIva = txtIva;
    }

    /**
     * Obtiene la etiqueta del total.
     * @return La etiqueta del total.
     */
    public JLabel getLblTotal() {
        return lblTotal;
    }

    /**
     * Establece la etiqueta del total.
     * @param lblTotal La etiqueta del total.
     */
    public void setLblTotal(JLabel lblTotal) {
        this.lblTotal = lblTotal;
    }

    /**
     * Obtiene el campo de texto para el total.
     * @return El campo de texto del total.
     */
    public JTextField getTxtTotal() {
        return txtTotal;
    }

    /**
     * Establece el campo de texto para el total.
     * @param txtTotal El campo de texto del total.
     */
    public void setTxtTotal(JTextField txtTotal) {
        this.txtTotal = txtTotal;
    }

    /**
     * Obtiene el campo de texto para el subtotal.
     * @return El campo de texto del subtotal.
     */
    public JTextField getTxtSubTotal() {
        return txtSubTotal;
    }

    /**
     * Establece el campo de texto para el subtotal.
     * @param txtSubTotal El campo de texto del subtotal.
     */
    public void setTxtSubTotal(JTextField txtSubTotal) {
        this.txtSubTotal = txtSubTotal;
    }

    /**
     * Obtiene el panel inferior.
     * @return El panel inferior.
     */
    public JPanel getPanelInferior() {
        return panelInferior;
    }

    /**
     * Establece el panel inferior.
     * @param panelInferior El panel inferior.
     */
    public void setPanelInferior(JPanel panelInferior) {
        this.panelInferior = panelInferior;
    }

    /**
     * Obtiene el botón para salir de la vista.
     * @return El botón de salir.
     */
    public JButton getBtnSalir() {
        return btnSalir;
    }

    /**
     * Establece el botón para salir de la vista.
     * @param btnSalir El botón de salir.
     */
    public void setBtnSalir(JButton btnSalir) {
        this.btnSalir = btnSalir;
    }

    /**
     * Obtiene el panel principal.
     * @return El panel principal.
     */
    public JPanel getPanelAll() {
        return panelAll;
    }

    /**
     * Establece el panel principal.
     * @param panelAll El panel principal.
     */
    public void setPanelAll(JPanel panelAll) {
        this.panelAll = panelAll;
    }

    /**
     * Obtiene el campo de texto para el usuario.
     * @return El campo de texto del usuario.
     */
    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    /**
     * Establece el campo de texto para el usuario.
     * @param txtUsuario El campo de texto del usuario.
     */
    public void setTxtUsuario(JTextField txtUsuario) {
        this.txtUsuario = txtUsuario;
    }

    /**
     * Obtiene la etiqueta de los ítems del carrito.
     * @return La etiqueta de los ítems del carrito.
     */
    public JLabel getLblItemsCarrito() {
        return lblItemsCarrito;
    }

    /**
     * Establece la etiqueta de los ítems del carrito.
     * @param lblItemsCarrito La etiqueta de los ítems del carrito.
     */
    public void setLblItemsCarrito(JLabel lblItemsCarrito) {
        this.lblItemsCarrito = lblItemsCarrito;
    }

    /**
     * Obtiene el modelo de la tabla.
     * @return El modelo de la tabla.
     */
    public DefaultTableModel getModelo() {
        return modelo;
    }

    /**
     * Establece el modelo de la tabla.
     * @param modelo El modelo de la tabla.
     */
    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
    }

}

