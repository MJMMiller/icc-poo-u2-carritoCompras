package ec.edu.ups.poo.vista.producto;

import ec.edu.ups.poo.modelo.Producto;
import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.util.enums.TipoIcono;

import javax.swing.*;
import java.util.List;

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

    public void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
    }

    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }

    public int mostrarMensajeConfirmacion(String mensaje, String titulo, int tipo) {
        Object[] botones = {i18n.get("mensaje.confirmacion"), i18n.get("mensaje.cancelacion")};
        return JOptionPane.showOptionDialog(
                this, mensaje, titulo,
                JOptionPane.DEFAULT_OPTION, tipo,
                null, botones, botones[0]);
    }

    public void mostrarProductos(List<Producto> productos) {
        for (Producto producto : productos) {
            System.out.println(producto);
        }
    }

    public void inhabilitarCampos() {
        txtCodigo.setEnabled(false);
        txtNombre.setEnabled(false);
        txtPrecio.setEnabled(false);
        btnGuardar.setEnabled(false);
    }

    public void habilitarCampos() {
        txtNombre.setEnabled(true);
        txtPrecio.setEnabled(true);
        btnGuardar.setEnabled(true);
    }

    public void aplicarIdiomas() {
        setTitle(i18n.get("producto.anadir.titulo"));
        lblTitulo.setText(i18n.get("producto.anadir.lbl.titulo"));
        lblCodigo.setText(i18n.get("producto.anadir.lbl.codigo"));
        lblNombre.setText(i18n.get("producto.anadir.lbl.nombre"));
        lblPrecio.setText(i18n.get("producto.anadir.lbl.precio"));
        btnGuardar.setText(i18n.get("producto.anadir.btn.guardar"));
        btnNuevo.setText(i18n.get("producto.anadir.btn.nuevo"));
    }

    public void aplicarIconos() {

        setFrameIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.PRODUCTO));
        btnGuardar.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.GUARDAR));
        btnNuevo.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.GUARDAR_TODO));

    }

    public JPanel getPanelAll() {
        return panelAll;
    }
    public void setPanelAll(JPanel panelAll) {
        this.panelAll = panelAll;
    }
    public JTextField getLblCodeProduct() {
        return txtCodigo;
    }
    public void setLblCodeProduct(JTextField lblCodeProduct) {
        this.txtCodigo = lblCodeProduct;
    }
    public JButton getBtnRegisterNewProduct() {
        return btnGuardar;
    }
    public void setBtnRegisterNewProduct(JButton btnRegisterNewProduct) {
        this.btnGuardar = btnRegisterNewProduct;
    }
    public JButton getBtnCleanInputs() {
        return btnNuevo;
    }
    public void setBtnCleanInputs(JButton btnCleanInputs) {
        this.btnNuevo = btnCleanInputs;
    }
    public JLabel getTxtCodeProduct() {
        return lblCodigo;
    }
    public void setTxtCodeProduct(JLabel txtCodeProduct) {
        this.lblCodigo = txtCodeProduct;
    }
    public JLabel getTxtNameProduct() {
        return lblNombre;
    }
    public void setTxtNameProduct(JLabel txtNameProduct) {
        this.lblNombre = txtNameProduct;
    }
    public JLabel getTxtPriceProduct() {
        return lblPrecio;
    }
    public void setTxtPriceProduct(JLabel txtPriceProduct) {
        this.lblPrecio = txtPriceProduct;
    }
    public JTextField getLblNameProduct() {
        return txtNombre;
    }
    public void setLblNameProduct(JTextField lblNameProduct) {
        this.txtNombre = lblNameProduct;
    }
    public JTextField getLblPriceProduct() {
        return txtPrecio;
    }
    public void setLblPriceProduct(JTextField lblPriceProduct) {
        this.txtPrecio = lblPriceProduct;
    }
    public JPanel getPanelTop() {
        return panelTop;
    }
    public void setPanelTop(JPanel panelTop) {
        this.panelTop = panelTop;
    }
    public JPanel getPanelCenter() {
        return panelCenter;
    }
    public void setPanelCenter(JPanel panelCenter) {
        this.panelCenter = panelCenter;
    }
    public JPanel getPanelBottom() {
        return panelBottom;
    }
    public void setPanelBottom(JPanel panelBottom) {
        this.panelBottom = panelBottom;
    }
    public JLabel getTxtRegisterNewProduct() {
        return lblTitulo;
    }
    public void setTxtRegisterNewProduct(JLabel txtRegisterNewProduct) {
        this.lblTitulo = txtRegisterNewProduct;
    }

}