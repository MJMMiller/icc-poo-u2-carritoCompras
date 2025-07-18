package ec.edu.ups.poo.vista.carrito;

import ec.edu.ups.poo.modelo.Carrito;
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
 * Vista para listar y visualizar carritos de compras.
 * Permite mostrar, buscar y visualizar detalles de carritos en una tabla.
 */
public class CarritoListarView extends JInternalFrame {
    private JPanel panelAll;
    private JPanel panelSuperior;
    private JLabel lblTitulo;
    private JPanel panelCenter;
    private JLabel lblCodigo;
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JButton btnListar;
    private JTable tblCarritos;
    private JButton btnVerCarrito;
    private JPanel panelTabla;
    private JScrollPane scroll;
    private JPanel panelInferior;
    private JLabel lblSubtotalResumen;
    private JLabel lblIvaResumen;
    private JLabel lblTotalResumen;
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler i18n;
    private double subtotal = 0.0;
    private double iva = 0.0;
    private double total = 0.0;
    private List<Carrito> carritosMostrados;

    /**
     * Constructor de la vista para listar carritos.
     * Inicializa la interfaz gráfica y aplica idioma e iconos.
     * @param i18n Manejador de internacionalización para los textos de la interfaz.
     */
    public CarritoListarView(MensajeInternacionalizacionHandler i18n) {
        this.i18n = i18n;
        setContentPane(panelAll);
        setSize(600, 500);
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

        aplicarIdioma();
        aplicarIconos();
    }

    /**
     * Muestra los carritos en la tabla y actualiza los valores de resumen.
     * @param carritos Lista de carritos a mostrar.
     * @param locale Configuración regional para formatear fechas y monedas.
     */
    public void mostrarCarritos(List<Carrito> carritos, Locale locale) {
        modelo.setRowCount(0);
        subtotal = 0;
        iva = 0;
        total = 0;
        if (carritos != null) {
            for (Carrito carrito : carritos) {
                modelo.addRow(new Object[]{
                        carrito.getId(),
                        carrito.getUsuario() != null ? carrito.getUsuario().getCedula() : "N/A",
                        FormateadorUtils.formatearFecha(carrito.getFecha(), locale),
                        FormateadorUtils.formatearMoneda(carrito.getSubtotal(), locale),
                        FormateadorUtils.formatearMoneda(carrito.getIva(), locale),
                        FormateadorUtils.formatearMoneda(carrito.getTotal(), locale)
                });
                subtotal += carrito.getSubtotal();
                iva += carrito.getIva();
                total += carrito.getTotal();
            }
        }
        this.carritosMostrados = carritos;
        refrescarResumenValores(locale);
    }

    /**
     * Refresca la tabla mostrando los carritos actualmente listados.
     */
    public void refrescarTabla() {
        if (carritosMostrados != null) {
            mostrarCarritos(carritosMostrados, i18n.getLocale());
        }
    }

    /**
     * Actualiza los valores de subtotal, IVA y total en el resumen.
     * @param locale Configuración regional para formatear los valores.
     */
    public void refrescarResumenValores(Locale locale) {
        if (lblSubtotalResumen != null) lblSubtotalResumen.setText(FormateadorUtils.formatearMoneda(subtotal, locale));
        if (lblIvaResumen != null) lblIvaResumen.setText(FormateadorUtils.formatearMoneda(iva, locale));
        if (lblTotalResumen != null) lblTotalResumen.setText(FormateadorUtils.formatearMoneda(total, locale));
    }

    /**
     * Muestra un cuadro de diálogo de confirmación.
     * @param mensaje Mensaje a mostrar en el cuadro de diálogo.
     * @param titulo Título del cuadro de diálogo.
     * @param tipo Tipo de mensaje (por ejemplo, JOptionPane.QUESTION_MESSAGE).
     * @return Índice del botón seleccionado por el usuario.
     */
    public int mostrarMensajeConfirmacion(String mensaje, String titulo, int tipo) {
        Object[] botones = {i18n.get("mensaje.confirmacion"), i18n.get("mensaje.cancelacion")};
        return JOptionPane.showOptionDialog(
                this, mensaje, titulo,
                JOptionPane.DEFAULT_OPTION, tipo,
                null, botones, botones[0]);
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
        setTitle(i18n.get("carrito.listar.title"));
        lblTitulo.setText(i18n.get("carrito.listar.lbl.title"));
        lblCodigo.setText(i18n.get("carrito.listar.lbl.codigo"));
        btnBuscar.setText(i18n.get("carrito.listar.btn.buscar"));
        btnListar.setText(i18n.get("carrito.listar.btn.listar"));
        btnVerCarrito.setText(i18n.get("carrito.listarbtn..ver"));
        tblCarritos.getColumnModel().getColumn(0).setHeaderValue(i18n.get("carrito.listar.tbl.codigo"));
        tblCarritos.getColumnModel().getColumn(1).setHeaderValue(i18n.get("carrito.listar.tbl.usuario"));
        tblCarritos.getColumnModel().getColumn(2).setHeaderValue(i18n.get("carrito.listar.tbl.fecha"));
        tblCarritos.getColumnModel().getColumn(3).setHeaderValue(i18n.get("carrito.listar.tbl.subtotal"));
        tblCarritos.getColumnModel().getColumn(4).setHeaderValue(i18n.get("carrito.listar.tbl.iva"));
        tblCarritos.getColumnModel().getColumn(5).setHeaderValue(i18n.get("carrito.listar.tbl.total"));
        tblCarritos.getTableHeader().repaint();
        refrescarResumenValores(i18n.getLocale());
    }

    /**
     * Aplica los iconos a los componentes de la interfaz.
     */
    public void aplicarIconos() {
        setFrameIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.CARRITO));
        btnBuscar.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.BUSCAR));
        btnListar.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.LISTAR));
        btnVerCarrito.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.EYE));
    }

    /**
     * Obtiene el panel principal de la vista.
     * @return El panel principal.
     */
    public JPanel getPanelAll() { return panelAll; }

    /**
     * Establece el panel principal de la vista.
     * @param panelAll El panel principal.
     */
    public void setPanelAll(JPanel panelAll) { this.panelAll = panelAll; }

    /**
     * Obtiene el panel superior de la vista.
     * @return El panel superior.
     */
    public JPanel getPanelSuperior() { return panelSuperior; }

    /**
     * Establece el panel superior de la vista.
     * @param panelSuperior El panel superior.
     */
    public void setPanelSuperior(JPanel panelSuperior) { this.panelSuperior = panelSuperior; }

    /**
     * Obtiene la etiqueta del título.
     * @return La etiqueta del título.
     */
    public JLabel getLblTitulo() { return lblTitulo; }

    /**
     * Establece la etiqueta del título.
     * @param lblTitulo La etiqueta del título.
     */
    public void setLblTitulo(JLabel lblTitulo) { this.lblTitulo = lblTitulo; }

    /**
     * Obtiene el panel central de la vista.
     * @return El panel central.
     */
    public JPanel getPanelCenter() { return panelCenter; }

    /**
     * Establece el panel central de la vista.
     * @param panelCenter El panel central.
     */
    public void setPanelCenter(JPanel panelCenter) { this.panelCenter = panelCenter; }

    /**
     * Obtiene la etiqueta del código.
     * @return La etiqueta del código.
     */
    public JLabel getLblCodigo() { return lblCodigo; }

    /**
     * Establece la etiqueta del código.
     * @param lblCodigo La etiqueta del código.
     */
    public void setLblCodigo(JLabel lblCodigo) { this.lblCodigo = lblCodigo; }

    /**
     * Obtiene el campo de texto para el código.
     * @return El campo de texto del código.
     */
    public JTextField getTxtCodigo() { return txtCodigo; }

    /**
     * Establece el campo de texto para el código.
     * @param txtCodigo El campo de texto del código.
     */
    public void setTxtCodigo(JTextField txtCodigo) { this.txtCodigo = txtCodigo; }

    /**
     * Obtiene el botón de búsqueda.
     * @return El botón de búsqueda.
     */
    public JButton getBtnBuscar() { return btnBuscar; }

    /**
     * Establece el botón de búsqueda.
     * @param btnBuscar El botón de búsqueda.
     */
    public void setBtnBuscar(JButton btnBuscar) { this.btnBuscar = btnBuscar; }

    /**
     * Obtiene el botón para listar carritos.
     * @return El botón de listar.
     */
    public JButton getBtnListar() { return btnListar; }

    /**
     * Establece el botón para listar carritos.
     * @param btnListar El botón de listar.
     */
    public void setBtnListar(JButton btnListar) { this.btnListar = btnListar; }

    /**
     * Obtiene la tabla de carritos.
     * @return La tabla de carritos.
     */
    public JTable getTblCarritos() { return tblCarritos; }

    /**
     * Establece la tabla de carritos.
     * @param tblCarritos La tabla de carritos.
     */
    public void setTblCarritos(JTable tblCarritos) { this.tblCarritos = tblCarritos; }

    /**
     * Obtiene el botón para ver detalles del carrito.
     * @return El botón de ver carrito.
     */
    public JButton getBtnVerCarrito() { return btnVerCarrito; }

    /**
     * Establece el botón para ver detalles del carrito.
     * @param btnVerCarrito El botón de ver carrito.
     */
    public void setBtnVerCarrito(JButton btnVerCarrito) { this.btnVerCarrito = btnVerCarrito; }

    /**
     * Obtiene el panel de la tabla.
     * @return El panel de la tabla.
     */
    public JPanel getPanelTabla() { return panelTabla; }

    /**
     * Establece el panel de la tabla.
     * @param panelTabla El panel de la tabla.
     */
    public void setPanelTabla(JPanel panelTabla) { this.panelTabla = panelTabla; }

    /**
     * Obtiene el scroll de la tabla.
     * @return El scroll de la tabla.
     */
    public JScrollPane getScroll() { return scroll; }

    /**
     * Establece el scroll de la tabla.
     * @param scroll El scroll de la tabla.
     */
    public void setScroll(JScrollPane scroll) { this.scroll = scroll; }

    /**
     * Obtiene el panel inferior de la vista.
     * @return El panel inferior.
     */
    public JPanel getPanelInferior() { return panelInferior; }

    /**
     * Establece el panel inferior de la vista.
     * @param panelInferior El panel inferior.
     */
    public void setPanelInferior(JPanel panelInferior) { this.panelInferior = panelInferior; }

    /**
     * Obtiene el modelo de la tabla.
     * @return El modelo de la tabla.
     */
    public DefaultTableModel getModelo() { return modelo; }

    /**
     * Establece el modelo de la tabla.
     * @param modelo El modelo de la tabla.
     */
    public void setModelo(DefaultTableModel modelo) { this.modelo = modelo; }

    /**
     * Obtiene la etiqueta del subtotal en el resumen.
     * @return La etiqueta del subtotal.
     */
    public JLabel getLblSubtotalResumen() { return lblSubtotalResumen; }

    /**
     * Establece la etiqueta del subtotal en el resumen.
     * @param lblSubtotalResumen La etiqueta del subtotal.
     */
    public void setLblSubtotalResumen(JLabel lblSubtotalResumen) { this.lblSubtotalResumen = lblSubtotalResumen; }

    /**
     * Obtiene la etiqueta del IVA en el resumen.
     * @return La etiqueta del IVA.
     */
    public JLabel getLblIvaResumen() { return lblIvaResumen; }

    /**
     * Establece la etiqueta del IVA en el resumen.
     * @param lblIvaResumen La etiqueta del IVA.
     */
    public void setLblIvaResumen(JLabel lblIvaResumen) { this.lblIvaResumen = lblIvaResumen; }

    /**
     * Obtiene la etiqueta del total en el resumen.
     * @return La etiqueta del total.
     */
    public JLabel getLblTotalResumen() { return lblTotalResumen; }

    /**
     * Establece la etiqueta del total en el resumen.
     * @param lblTotalResumen La etiqueta del total.
     */
    public void setLblTotalResumen(JLabel lblTotalResumen) { this.lblTotalResumen = lblTotalResumen; }

}