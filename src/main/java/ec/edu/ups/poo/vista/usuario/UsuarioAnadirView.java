package ec.edu.ups.poo.vista.usuario;

import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.util.TipoIcono;

import javax.swing.*;
import java.util.Calendar;
import java.util.Date;

public class UsuarioAnadirView extends JInternalFrame {
    private JPanel panelAll;
    private JPanel panelSuperior;
    private JLabel lblTitulo;
    private JPanel panelCenter;
    private JTextField txtUsuario;
    private JLabel lblUsuario;
    private JTextField txtContrasena;
    private JLabel lblContrasena;
    private JComboBox cbxRol;
    private JButton btnRegistrar;
    private JButton btnClean;
    private JPanel panelInferior;
    private JLabel lblRol;
    private JTextField txtNombreCompleto;
    private JLabel lblNombreCompleto;
    private JLabel lblFechaNacimiento;
    private JComboBox cbxDia;
    private JComboBox cbxMes;
    private JComboBox cbxAnio;
    private JLabel lblTelefono;
    private JTextField txtTelefono;
    private JTextField txtCorreo;
    private JLabel lblCorreo;
    private MensajeInternacionalizacionHandler i18n;

    public UsuarioAnadirView(MensajeInternacionalizacionHandler i18n) {
        this.i18n = i18n;
        setContentPane(panelAll);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 500);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        aplicarIdioma();
        aplicarIconos();
        inicializarCombosFechaNacimiento();
    }

    public void aplicarIconos() {
        setFrameIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.USUARIO));
        btnClean.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.CLEAN));
        btnRegistrar.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.GUARDAR));
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

    public void aplicarIdioma() {
        setTitle(i18n.get("usuario.anadir.titulo"));
        lblTitulo.setText(i18n.get("usuario.anadir.lbl.titulo"));
        lblUsuario.setText(i18n.get("usuario.anadir.lbl.usuario"));
        lblContrasena.setText(i18n.get("usuario.anadir.lbl.contrasena"));
        lblRol.setText(i18n.get("usuario.anadir.lbl.rol"));
        btnRegistrar.setText(i18n.get("usuario.anadir.btn.registrar"));
        btnClean.setText(i18n.get("usuario.anadir.btn.limpiar"));
        lblNombreCompleto.setText(i18n.get("usuario.anadir.lbl.nombre.completo"));
        lblFechaNacimiento.setText(i18n.get("usuario.anadir.lbl.fecha.nacimiento"));
        lblTelefono.setText(i18n.get("usuario.anadir.lbl.telefono"));
        lblCorreo.setText(i18n.get("usuario.anadir.lbl.correo"));

    }

    public Date getFechaNacimiento() {
        try {
            int dia = Integer.parseInt(cbxDia.getSelectedItem().toString());
            int mes = Integer.parseInt(cbxMes.getSelectedItem().toString()) - 1;
            int anio = Integer.parseInt(cbxAnio.getSelectedItem().toString());
            Calendar calendar = Calendar.getInstance();
            calendar.set(anio, mes, dia, 0, 0, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            return calendar.getTime();
        } catch (Exception e) {
            return null;
        }
    }

    private void inicializarCombosFechaNacimiento() {

        cbxDia.removeAllItems();
        for (int i = 1; i <= 31; i++) {
            cbxDia.addItem(String.valueOf(i));
        }

        cbxMes.removeAllItems();
        for (int i = 1; i <= 12; i++) {
            cbxMes.addItem(String.valueOf(i));
        }

        cbxAnio.removeAllItems();
        int anioActual = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = anioActual; i >= 1900; i--) {
            cbxAnio.addItem(String.valueOf(i));
        }
    }

    public JPanel getPanelAll() {
        return panelAll;
    }
    public void setPanelAll(JPanel panelAll) {
        this.panelAll = panelAll;
    }
    public JPanel getPanelSuperior() {
        return panelSuperior;
    }
    public void setPanelSuperior(JPanel panelSuperior) {
        this.panelSuperior = panelSuperior;
    }
    public JLabel getLblTitulo() {
        return lblTitulo;
    }
    public void setLblTitulo(JLabel lblTitulo) {
        this.lblTitulo = lblTitulo;
    }
    public JPanel getPanelCenter() {
        return panelCenter;
    }
    public void setPanelCenter(JPanel panelCenter) {
        this.panelCenter = panelCenter;
    }
    public JTextField getTxtUsuario() {
        return txtUsuario;
    }
    public void setTxtUsuario(JTextField txtUsuario) {
        this.txtUsuario = txtUsuario;
    }
    public JLabel getLblUsuario() {
        return lblUsuario;
    }
    public void setLblUsuario(JLabel lblUsuario) {
        this.lblUsuario = lblUsuario;
    }
    public JTextField getTxtContrasena() {
        return txtContrasena;
    }
    public void setTxtContrasena(JTextField txtContrasena) {
        this.txtContrasena = txtContrasena;
    }
    public JLabel getLblContrasena() {
        return lblContrasena;
    }
    public void setLblContrasena(JLabel lblContrasena) {
        this.lblContrasena = lblContrasena;
    }
    public JComboBox getCbxRol() {
        return cbxRol;
    }
    public void setCbxRol(JComboBox cbxRol) {
        this.cbxRol = cbxRol;
    }
    public JButton getBtnRegistrar() {
        return btnRegistrar;
    }
    public void setBtnRegistrar(JButton btnRegistrar) {
        this.btnRegistrar = btnRegistrar;
    }
    public JButton getBtnClean() {
        return btnClean;
    }
    public void setBtnClean(JButton btnClean) {
        this.btnClean = btnClean;
    }
    public JPanel getPanelInferior() {
        return panelInferior;
    }
    public void setPanelInferior(JPanel panelInferior) {
        this.panelInferior = panelInferior;
    }
    public JLabel getLblRol() {
        return lblRol;
    }
    public void setLblRol(JLabel lblRol) {
        this.lblRol = lblRol;
    }
    public JTextField getTxtNombreCompleto() {
        return txtNombreCompleto;
    }
    public void setTxtNombreCompleto(JTextField txtNombreCompleto) {
        this.txtNombreCompleto = txtNombreCompleto;
    }
    public JLabel getLblNombreCompleto() {
        return lblNombreCompleto;
    }
    public void setLblNombreCompleto(JLabel lblNombreCompleto) {
        this.lblNombreCompleto = lblNombreCompleto;
    }
    public JLabel getLblFechaNacimiento() {
        return lblFechaNacimiento;
    }
    public void setLblFechaNacimiento(JLabel lblFechaNacimiento) {
        this.lblFechaNacimiento = lblFechaNacimiento;
    }
    public JComboBox getCbxDia() {
        return cbxDia;
    }
    public void setCbxDia(JComboBox cbxDia) {
        this.cbxDia = cbxDia;
    }
    public JComboBox getCbxMes() {
        return cbxMes;
    }
    public void setCbxMes(JComboBox cbxMes) {
        this.cbxMes = cbxMes;
    }
    public JComboBox getCbxAnio() {
        return cbxAnio;
    }
    public void setCbxAnio(JComboBox cbxAnio) {
        this.cbxAnio = cbxAnio;
    }
    public JLabel getLblTelefono() {
        return lblTelefono;
    }
    public void setLblTelefono(JLabel lblTelefono) {
        this.lblTelefono = lblTelefono;
    }
    public JTextField getTxtTelefono() {
        return txtTelefono;
    }
    public void setTxtTelefono(JTextField txtTelefono) {
        this.txtTelefono = txtTelefono;
    }
    public JTextField getTxtCorreo() {
        return txtCorreo;
    }
    public void setTxtCorreo(JTextField txtCorreo) {
        this.txtCorreo = txtCorreo;
    }
    public JLabel getLblCorreo() {
        return lblCorreo;
    }
    public void setLblCorreo(JLabel lblCorreo) {
        this.lblCorreo = lblCorreo;
    }
    public MensajeInternacionalizacionHandler getI18n() {
        return i18n;
    }
    public void setI18n(MensajeInternacionalizacionHandler i18n) {
        this.i18n = i18n;
    }
}
