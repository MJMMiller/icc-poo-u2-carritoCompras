package ec.edu.ups.poo.vista.producto;

import ec.edu.ups.poo.modelo.Producto;
import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.util.TipoIcono;

import javax.swing.*;
import java.util.List;

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
        txtNombre.setText(productos.get(0).getNombre());
        txtPrecio.setText(String.valueOf(productos.get(0).getPrecio()));
    }

    public void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
    }

    public void aplicarIdiomas() {
        setTitle(i18n.get("producto.editar.titulo"));
        lblTitulo.setText(i18n.get("producto.editar.lbl.titulo"));
        lblCodigo.setText(i18n.get("producto.editar.lbl.codigo"));
        lblNombre.setText(i18n.get("producto.editar.lbl.nombre"));
        lblPrecio.setText(i18n.get("producto.editar.lbl.precio"));
        btnBuscar.setText(i18n.get("producto.editar.btn.buscar"));
        btnActualizar.setText(i18n.get("producto.editar.btn.actualizar"));
    }

    public void aplicarIconos() {
        setFrameIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.PRODUCTO));
        btnBuscar.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.BUSCAR));
        btnActualizar.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.ACTUALIZAR));
    }

    public JPanel getPanelAll() {
        return panelAll;
    }
    public void setPanelAll(JPanel panelAll) {
        this.panelAll = panelAll;
    }
    public JTextField getTxtCodigo() {
        return txtCodigo;
    }
    public void setTxtCodigo(JTextField txtCodigo) {
        this.txtCodigo = txtCodigo;
    }
    public JButton getBtnBuscar() {
        return btnBuscar;
    }
    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }
    public JTextField getTxtNombre() {
        return txtNombre;
    }
    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }
    public JLabel getTxtCodeTitle() {
        return lblCodigo;
    }
    public void setTxtCodeTitle(JLabel txtCodeTitle) {
        this.lblCodigo = txtCodeTitle;
    }
    public JPanel getPanelCenter() {
        return panelCenter;
    }
    public void setPanelCenter(JPanel panelCenter) {
        this.panelCenter = panelCenter;
    }
    public JPanel getPanelTop() {
        return panelTop;
    }
    public void setPanelTop(JPanel panelTop) {
        this.panelTop = panelTop;
    }
    public JTextField getTxtPrecio() {
        return txtPrecio;
    }
    public void setTxtPrecio(JTextField txtPrecio) {
        this.txtPrecio = txtPrecio;
    }
    public JButton getBtnActualizar() {
        return btnActualizar;
    }
    public void setBtnActualizar(JButton btnActualizar) {
        this.btnActualizar = btnActualizar;
    }
    public JLabel getTxtPriceTitle() {
        return lblPrecio;
    }
    public void setTxtPriceTitle(JLabel txtPriceTitle) {
        this.lblPrecio = txtPriceTitle;
    }
    public JLabel getTxtNameTitle() {return lblNombre;}
    public void setTxtNameTitle(JLabel txtNameTitle) {
        this.lblNombre = txtNameTitle;
    }
    public JPanel getPanelMenor() {
        return panelMenor;
    }
    public void setPanelMenor(JPanel panelMenor) {
        this.panelMenor = panelMenor;
    }
    public JLabel getTxtSettingsProduc() {
        return lblTitulo;
    }
    public void setTxtSettingsProduc(JLabel txtSettingsProduc) {
        this.lblTitulo = txtSettingsProduc;
    }
    public JScrollPane getScroll() {
        return scroll;
    }
    public void setScroll(JScrollPane scroll) {
        this.scroll = scroll;
    }


}