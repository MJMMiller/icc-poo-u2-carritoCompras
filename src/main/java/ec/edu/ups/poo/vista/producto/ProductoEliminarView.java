package ec.edu.ups.poo.vista.producto;

import ec.edu.ups.poo.modelo.Producto;
import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.util.TipoIcono;

import javax.swing.*;
import java.util.List;

/**
 * Vista interna para eliminar productos del sistema.
 * Permite buscar un producto por código, mostrar sus datos y eliminarlo.
 * Aplica internacionalización e iconos a los componentes de la interfaz.
 */
public class ProductoEliminarView extends JInternalFrame {
    private JPanel panelTop;
    private JLabel lblTitulo;
    private JPanel panelCenter;
    private JLabel lblCodigo;
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JPanel panelMenor;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JButton btnEliminar;
    private JPanel panelAll;
    private MensajeInternacionalizacionHandler i18n;

    /**
     * Constructor de ProductoEliminarView.
     * Inicializa la vista con el manejador de internacionalización, configura la interfaz y aplica idioma e iconos.
     *
     * @param i18n Manejador de internacionalización de mensajes.
     */
    public ProductoEliminarView(MensajeInternacionalizacionHandler i18n) {
        this.i18n = i18n;
        setContentPane(panelAll);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 250);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        aplicarIdioma();
        aplicarIconos();
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
     * Limpia los campos de la vista de eliminación de producto.
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
    public void aplicarIdioma() {

        setTitle(i18n.get("producto.eliminar.titulo"));
        lblTitulo.setText(i18n.get("producto.eliminar.lbltitulo"));
        lblCodigo.setText(i18n.get("producto.eliminar.lbl.codigo"));
        lblNombre.setText(i18n.get("producto.eliminar.lbl.nombre"));
        lblPrecio.setText(i18n.get("producto.eliminar.lbl.precio"));
        btnBuscar.setText(i18n.get("producto.eliminar.btn.buscar"));
        btnEliminar.setText(i18n.get("producto.eliminar.btn.eliminar"));
    }

    /**
     * Aplica los iconos correspondientes a los botones y al frame de la vista.
     * No recibe parámetros ni retorna valores.
     */
    public void aplicarIconos() {
        setFrameIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.PRODUCTO));
        btnBuscar.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.BUSCAR));
        btnEliminar.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.ElIMINAR));
    }

    // Métodos getter y setter para los componentes de la vista.

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
     * Obtiene la etiqueta del título.
     * @return JLabel del título.
     */
    public JLabel getLblTitulo() { return lblTitulo; }
    /**
     * Establece la etiqueta del título.
     * @param lblTitulo JLabel del título.
     */
    public void setLblTitulo(JLabel lblTitulo) { this.lblTitulo = lblTitulo; }
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
     * Obtiene la etiqueta de código de producto.
     * @return JLabel de código.
     */
    public JLabel getLblCodigo() { return lblCodigo; }
    /**
     * Establece la etiqueta de código de producto.
     * @param lblCodigo JLabel de código.
     */
    public void setLblCodigo(JLabel lblCodigo) { this.lblCodigo = lblCodigo; }
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
     * Obtiene la etiqueta de nombre de producto.
     * @return JLabel de nombre.
     */
    public JLabel getLblNombre() { return lblNombre; }
    /**
     * Establece la etiqueta de nombre de producto.
     * @param lblNombre JLabel de nombre.
     */
    public void setLblNombre(JLabel lblNombre) { this.lblNombre = lblNombre; }
    /**
     * Obtiene la etiqueta de precio de producto.
     * @return JLabel de precio.
     */
    public JLabel getLblPrecio() { return lblPrecio; }
    /**
     * Establece la etiqueta de precio de producto.
     * @param lblPrecio JLabel de precio.
     */
    public void setLblPrecio(JLabel lblPrecio) { this.lblPrecio = lblPrecio; }
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
     * Obtiene el botón para eliminar producto.
     * @return JButton para eliminar producto.
     */
    public JButton getBtnEliminar() { return btnEliminar; }
    /**
     * Establece el botón para eliminar producto.
     * @param btnEliminar JButton para eliminar producto.
     */
    public void setBtnEliminar(JButton btnEliminar) { this.btnEliminar = btnEliminar; }
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
}
