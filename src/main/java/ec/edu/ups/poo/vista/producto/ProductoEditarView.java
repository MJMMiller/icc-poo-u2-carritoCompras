package ec.edu.ups.poo.vista.producto;

import ec.edu.ups.poo.modelo.Producto;
import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.util.TipoIcono;

import javax.swing.*;
import java.util.List;

/**
 * Vista interna para editar productos del sistema.
 * Permite modificar datos de productos como código, nombre y precio, y aplicar internacionalización e iconos.
 */
public class ProductoEditarView extends JInternalFrame {
    private JPanel panelAll;
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JTextField txtNombre;
    private JLabel lblCodigo;
    private JPanel panelCenter;
    private JPanel panelTop;
    private JTextField txtPrecio;
    private JButton btnActualizar;
    private JLabel lblPrecio;
    private JLabel lblNombre;
    private JPanel panelMenor;
    private JLabel lblTitulo;
    private JScrollPane scroll;
    private MensajeInternacionalizacionHandler i18n;

    /**
     * Constructor de ProductoEditarView.
     * Inicializa la vista con el manejador de internacionalización, configura la interfaz y aplica idioma e iconos.
     *
     * @param i18n Manejador de internacionalización de mensajes.
     */
    public ProductoEditarView(MensajeInternacionalizacionHandler i18n) {
        this.i18n = i18n;
        setContentPane(panelAll);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 250);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        aplicarIdiomas();
        aplicarIconos();
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
     * Muestra un cuadro de diálogo de confirmación con botones personalizados.
     *
     * @param mensaje Mensaje a mostrar.
     * @param titulo Título de la ventana de diálogo.
     * @param tipo Tipo de mensaje (JOptionPane).
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
     * Muestra los datos del producto seleccionado en los campos de la vista.
     *
     * @param productos Lista de productos (se utiliza el primero para mostrar).
     */
    public void mostrarProductos(List<Producto> productos) {
        txtNombre.setText(productos.get(0).getNombre());
        txtPrecio.setText(String.valueOf(productos.get(0).getPrecio()));
    }

    /**
     * Limpia los campos de la vista de edición de producto.
     * No recibe parámetros ni retorna valores.
     */
    public void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
    }

    /**
     * Aplica los textos traducidos a todos los componentes de la vista según el idioma actual.
     * No recibe parámetros ni retorna valores.
     */
    public void aplicarIdiomas() {
        setTitle(i18n.get("producto.editar.titulo"));
        lblTitulo.setText(i18n.get("producto.editar.lbl.titulo"));
        lblCodigo.setText(i18n.get("producto.editar.lbl.codigo"));
        lblNombre.setText(i18n.get("producto.editar.lbl.nombre"));
        lblPrecio.setText(i18n.get("producto.editar.lbl.precio"));
        btnBuscar.setText(i18n.get("producto.editar.btn.buscar"));
        btnActualizar.setText(i18n.get("producto.editar.btn.actualizar"));
    }

    /**
     * Aplica los iconos correspondientes a los botones y al frame de la vista.
     * No recibe parámetros ni retorna valores.
     */
    public void aplicarIconos() {
        setFrameIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.PRODUCTO));
        btnBuscar.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.BUSCAR));
        btnActualizar.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.ACTUALIZAR));
    }

    // Métodos getter y setter para los componentes de la vista.

    /**
     * Obtiene el panel principal de la vista.
     * @return JPanel principal.
     */
    public JPanel getPanelAll() { return panelAll; }
    /**
     * Establece el panel principal de la vista.
     * @param panelAll JPanel principal.
     */
    public void setPanelAll(JPanel panelAll) { this.panelAll = panelAll; }
    /**
     * Obtiene el campo de texto para el código de producto.
     * @return JTextField de código.
     */
    public JTextField getTxtCodigo() { return txtCodigo; }
    /**
     * Establece el campo de texto para el código de producto.
     * @param txtCodigo JTextField de código.
     */
    public void setTxtCodigo(JTextField txtCodigo) { this.txtCodigo = txtCodigo; }
    /**
     * Obtiene el botón para buscar producto.
     * @return JButton para buscar producto.
     */
    public JButton getBtnBuscar() { return btnBuscar; }
    /**
     * Establece el botón para buscar producto.
     * @param btnBuscar JButton para buscar producto.
     */
    public void setBtnBuscar(JButton btnBuscar) { this.btnBuscar = btnBuscar; }
    /**
     * Obtiene el campo de texto para el nombre de producto.
     * @return JTextField de nombre.
     */
    public JTextField getTxtNombre() { return txtNombre; }
    /**
     * Establece el campo de texto para el nombre de producto.
     * @param txtNombre JTextField de nombre.
     */
    public void setTxtNombre(JTextField txtNombre) { this.txtNombre = txtNombre; }
    /**
     * Obtiene la etiqueta de código de producto.
     * @return JLabel de código.
     */
    public JLabel getTxtCodeTitle() { return lblCodigo; }
    /**
     * Establece la etiqueta de código de producto.
     * @param txtCodeTitle JLabel de código.
     */
    public void setTxtCodeTitle(JLabel txtCodeTitle) { this.lblCodigo = txtCodeTitle; }
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
     * Obtiene el panel superior de la vista.
     * @return JPanel superior.
     */
    public JPanel getPanelTop() { return panelTop; }
    /**
     * Establece el panel superior de la vista.
     * @param panelTop JPanel superior.
     */
    public void setPanelTop(JPanel panelTop) { this.panelTop = panelTop; }
    /**
     * Obtiene el campo de texto para el precio de producto.
     * @return JTextField de precio.
     */
    public JTextField getTxtPrecio() { return txtPrecio; }
    /**
     * Establece el campo de texto para el precio de producto.
     * @param txtPrecio JTextField de precio.
     */
    public void setTxtPrecio(JTextField txtPrecio) { this.txtPrecio = txtPrecio; }
    /**
     * Obtiene el botón para actualizar producto.
     * @return JButton para actualizar producto.
     */
    public JButton getBtnActualizar() { return btnActualizar; }
    /**
     * Establece el botón para actualizar producto.
     * @param btnActualizar JButton para actualizar producto.
     */
    public void setBtnActualizar(JButton btnActualizar) { this.btnActualizar = btnActualizar; }
    /**
     * Obtiene la etiqueta de precio de producto.
     * @return JLabel de precio.
     */
    public JLabel getTxtPriceTitle() { return lblPrecio; }
    /**
     * Establece la etiqueta de precio de producto.
     * @param txtPriceTitle JLabel de precio.
     */
    public void setTxtPriceTitle(JLabel txtPriceTitle) { this.lblPrecio = txtPriceTitle; }
    /**
     * Obtiene la etiqueta de nombre de producto.
     * @return JLabel de nombre.
     */
    public JLabel getTxtNameTitle() { return lblNombre; }
    /**
     * Establece la etiqueta de nombre de producto.
     * @param txtNameTitle JLabel de nombre.
     */
    public void setTxtNameTitle(JLabel txtNameTitle) { this.lblNombre = txtNameTitle; }
    /**
     * Obtiene el panel menor de la vista.
     * @return JPanel menor.
     */
    public JPanel getPanelMenor() { return panelMenor; }
    /**
     * Establece el panel menor de la vista.
     * @param panelMenor JPanel menor.
     */
    public void setPanelMenor(JPanel panelMenor) { this.panelMenor = panelMenor; }
    /**
     * Obtiene la etiqueta del título de la vista.
     * @return JLabel del título.
     */
    public JLabel getTxtSettingsProduc() { return lblTitulo; }
    /**
     * Establece la etiqueta del título de la vista.
     * @param txtSettingsProduc JLabel del título.
     */
    public void setTxtSettingsProduc(JLabel txtSettingsProduc) { this.lblTitulo = txtSettingsProduc; }
    /**
     * Obtiene el JScrollPane de la vista.
     * @return JScrollPane de la vista.
     */
    public JScrollPane getScroll() { return scroll; }
    /**
     * Establece el JScrollPane de la vista.
     * @param scroll JScrollPane de la vista.
     */
    public void setScroll(JScrollPane scroll) { this.scroll = scroll; }
}