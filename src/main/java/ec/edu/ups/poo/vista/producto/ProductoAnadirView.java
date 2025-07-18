package ec.edu.ups.poo.vista.producto;

import ec.edu.ups.poo.modelo.Producto;
import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.util.TipoIcono;

import javax.swing.*;
import java.util.List;

/**
 * Vista para añadir productos en la aplicación.
 * Permite gestionar la interfaz gráfica para el registro de productos.
 */
public class ProductoAnadirView extends JInternalFrame {

    private JPanel panelAll;
    private JTextField txtCodigo;
    private JButton btnGuardar;
    private JButton btnNuevo;
    private JLabel lblCodigo;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JPanel panelTop;
    private JPanel panelCenter;
    private JPanel panelBottom;
    private JLabel lblTitulo;
    private MensajeInternacionalizacionHandler i18n;

    /**
     * Constructor de la vista para añadir productos.
     * @param i18n Manejador de internacionalización para los mensajes de la interfaz.
     */
    public ProductoAnadirView(MensajeInternacionalizacionHandler i18n) {
        this.i18n = i18n;
        setContentPane(panelAll);
        setSize(500, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        aplicarIdiomas();
        aplicarIconos();
    }

    /**
     * Limpia los campos de texto del formulario de producto.
     */
    public void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
    }

    /**
     * Muestra un mensaje en un cuadro de diálogo.
     * @param mensaje El mensaje a mostrar.
     * @param titulo El título del cuadro de diálogo.
     * @param tipo El tipo de mensaje (por ejemplo, JOptionPane.INFORMATION_MESSAGE).
     */
    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }

    /**
     * Muestra un cuadro de diálogo de confirmación.
     * @param mensaje El mensaje a mostrar.
     * @param titulo El título del cuadro de diálogo.
     * @param tipo El tipo de mensaje (por ejemplo, JOptionPane.QUESTION_MESSAGE).
     * @return El índice del botón seleccionado por el usuario.
     */
    public int mostrarMensajeConfirmacion(String mensaje, String titulo, int tipo) {
        Object[] botones = {i18n.get("mensaje.confirmacion"), i18n.get("mensaje.cancelacion")};
        return JOptionPane.showOptionDialog(
                this, mensaje, titulo,
                JOptionPane.DEFAULT_OPTION, tipo,
                null, botones, botones[0]);
    }

    /**
     * Muestra la lista de productos en la consola.
     * @param productos Lista de productos a mostrar.
     */
    public void mostrarProductos(List<Producto> productos) {
        for (Producto producto : productos) {
            System.out.println(producto);
        }
    }

    /**
     * Inhabilita los campos de entrada y el botón de guardar.
     */
    public void inhabilitarCampos() {
        txtCodigo.setEnabled(false);
        txtNombre.setEnabled(false);
        txtPrecio.setEnabled(false);
        btnGuardar.setEnabled(false);
    }

    /**
     * Habilita los campos de entrada y el botón de guardar.
     */
    public void habilitarCampos() {
        txtNombre.setEnabled(true);
        txtPrecio.setEnabled(true);
        btnGuardar.setEnabled(true);
    }

    /**
     * Aplica los textos internacionalizados a los componentes de la interfaz.
     */
    public void aplicarIdiomas() {
        setTitle(i18n.get("producto.anadir.titulo"));
        lblTitulo.setText(i18n.get("producto.anadir.lbl.titulo"));
        lblCodigo.setText(i18n.get("producto.anadir.lbl.codigo"));
        lblNombre.setText(i18n.get("producto.anadir.lbl.nombre"));
        lblPrecio.setText(i18n.get("producto.anadir.lbl.precio"));
        btnGuardar.setText(i18n.get("producto.anadir.btn.guardar"));
        btnNuevo.setText(i18n.get("producto.anadir.btn.nuevo"));
    }

    /**
     * Aplica los iconos a los componentes de la interfaz.
     */
    public void aplicarIconos() {

        setFrameIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.PRODUCTO));
        btnGuardar.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.GUARDAR));
        btnNuevo.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.GUARDAR_TODO));

    }

    /**
     * Obtiene el panel principal de la vista.
     * @return El panel principal.
     */
    public JPanel getPanelAll() {
        return panelAll;
    }

    /**
     * Establece el panel principal de la vista.
     * @param panelAll El panel principal.
     */
    public void setPanelAll(JPanel panelAll) {
        this.panelAll = panelAll;
    }

    /**
     * Obtiene el campo de texto para el código del producto.
     * @return El campo de texto del código del producto.
     */
    public JTextField getLblCodeProduct() {
        return txtCodigo;
    }

    /**
     * Establece el campo de texto para el código del producto.
     * @param lblCodeProduct El campo de texto del código del producto.
     */
    public void setLblCodeProduct(JTextField lblCodeProduct) {
        this.txtCodigo = lblCodeProduct;
    }

    /**
     * Obtiene el botón para guardar el nuevo producto.
     * @return El botón de guardar.
     */
    public JButton getBtnRegisterNewProduct() {
        return btnGuardar;
    }

    /**
     * Establece el botón para guardar el nuevo producto.
     * @param btnRegisterNewProduct El botón de guardar.
     */
    public void setBtnRegisterNewProduct(JButton btnRegisterNewProduct) {
        this.btnGuardar = btnRegisterNewProduct;
    }

    /**
     * Obtiene el botón para limpiar los campos de entrada.
     * @return El botón de limpiar.
     */
    public JButton getBtnCleanInputs() {
        return btnNuevo;
    }

    /**
     * Establece el botón para limpiar los campos de entrada.
     * @param btnCleanInputs El botón de limpiar.
     */
    public void setBtnCleanInputs(JButton btnCleanInputs) {
        this.btnNuevo = btnCleanInputs;
    }

    /**
     * Obtiene la etiqueta del código del producto.
     * @return La etiqueta del código del producto.
     */
    public JLabel getTxtCodeProduct() {
        return lblCodigo;
    }

    /**
     * Establece la etiqueta del código del producto.
     * @param txtCodeProduct La etiqueta del código del producto.
     */
    public void setTxtCodeProduct(JLabel txtCodeProduct) {
        this.lblCodigo = txtCodeProduct;
    }

    /**
     * Obtiene la etiqueta del nombre del producto.
     * @return La etiqueta del nombre del producto.
     */
    public JLabel getTxtNameProduct() {
        return lblNombre;
    }

    /**
     * Establece la etiqueta del nombre del producto.
     * @param txtNameProduct La etiqueta del nombre del producto.
     */
    public void setTxtNameProduct(JLabel txtNameProduct) {
        this.lblNombre = txtNameProduct;
    }

    /**
     * Obtiene la etiqueta del precio del producto.
     * @return La etiqueta del precio del producto.
     */
    public JLabel getTxtPriceProduct() {
        return lblPrecio;
    }

    /**
     * Establece la etiqueta del precio del producto.
     * @param txtPriceProduct La etiqueta del precio del producto.
     */
    public void setTxtPriceProduct(JLabel txtPriceProduct) {
        this.lblPrecio = txtPriceProduct;
    }

    /**
     * Obtiene el campo de texto para el nombre del producto.
     * @return El campo de texto del nombre del producto.
     */
    public JTextField getLblNameProduct() {
        return txtNombre;
    }

    /**
     * Establece el campo de texto para el nombre del producto.
     * @param lblNameProduct El campo de texto del nombre del producto.
     */
    public void setLblNameProduct(JTextField lblNameProduct) {
        this.txtNombre = lblNameProduct;
    }

    /**
     * Obtiene el campo de texto para el precio del producto.
     * @return El campo de texto del precio del producto.
     */
    public JTextField getLblPriceProduct() {
        return txtPrecio;
    }

    /**
     * Establece el campo de texto para el precio del producto.
     * @param lblPriceProduct El campo de texto del precio del producto.
     */
    public void setLblPriceProduct(JTextField lblPriceProduct) {
        this.txtPrecio = lblPriceProduct;
    }

    /**
     * Obtiene el panel superior de la vista.
     * @return El panel superior.
     */
    public JPanel getPanelTop() {
        return panelTop;
    }

    /**
     * Establece el panel superior de la vista.
     * @param panelTop El panel superior.
     */
    public void setPanelTop(JPanel panelTop) {
        this.panelTop = panelTop;
    }

    /**
     * Obtiene el panel central de la vista.
     * @return El panel central.
     */
    public JPanel getPanelCenter() {
        return panelCenter;
    }

    /**
     * Establece el panel central de la vista.
     * @param panelCenter El panel central.
     */
    public void setPanelCenter(JPanel panelCenter) {
        this.panelCenter = panelCenter;
    }

    /**
     * Obtiene el panel inferior de la vista.
     * @return El panel inferior.
     */
    public JPanel getPanelBottom() {
        return panelBottom;
    }

    /**
     * Establece el panel inferior de la vista.
     * @param panelBottom El panel inferior.
     */
    public void setPanelBottom(JPanel panelBottom) {
        this.panelBottom = panelBottom;
    }

    /**
     * Obtiene la etiqueta del título para registrar un nuevo producto.
     * @return La etiqueta del título.
     */
    public JLabel getTxtRegisterNewProduct() {
        return lblTitulo;
    }

    /**
     * Establece la etiqueta del título para registrar un nuevo producto.
     * @param txtRegisterNewProduct La etiqueta del título.
     */
    public void setTxtRegisterNewProduct(JLabel txtRegisterNewProduct) {
        this.lblTitulo = txtRegisterNewProduct;
    }

}