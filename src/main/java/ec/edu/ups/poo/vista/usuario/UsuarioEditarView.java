package ec.edu.ups.poo.vista.usuario;

import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.util.TipoIcono;

import javax.swing.*;
import java.util.Calendar;
import java.util.Date;

public class UsuarioEditarView extends JInternalFrame {
    private JPanel panelAll;
    private JPanel panelSuperior;
    private JLabel lblTitulo;
    private JTextField txtUsuario;
    private JTextField txtContrasena;
    private JComboBox cbxRol;
    private JButton btnActualizar;
    private JButton btnClean;
    private JButton btnBuscar;
    private JLabel lblUsuario;
    private JLabel lblContrasena;
    private JLabel lblRol;
    private JComboBox cbxDia;
    private JComboBox cbxMes;
    private JComboBox cbxAnio;
    private JLabel lblNombreCompleto;
    private JTextField txtNombreCompleto;
    private JLabel lblFechaNacimiento;
    private JLabel lblCorreo;
    private JTextField txtCorreo;
    private JLabel lblTelefono;
    private JTextField txtTelefono;
    private MensajeInternacionalizacionHandler i18n;

    public UsuarioEditarView(MensajeInternacionalizacionHandler i18n) {
        this.i18n = i18n;
        setContentPane(panelAll);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        aplicarIdioma();
        inicializarCombosFechaNacimiento();
        aplicarIconos();

    }

    public void aplicarIconos() {
        setFrameIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.USUARIO));
        btnBuscar.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.BUSCAR));
        btnClean.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.CLEAN));
        btnActualizar.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.ACTUALIZAR));
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
        setTitle(i18n.get("usuario.editar.titulo"));
        lblTitulo.setText(i18n.get("usuario.editar.lbl.titulo"));
        lblUsuario.setText(i18n.get("usuario.editar.lbl.usuario"));
        lblContrasena.setText(i18n.get("usuario.editar.lblcontrasena"));
        lblRol.setText(i18n.get("usuario.editar.lbl.rol"));
        btnActualizar.setText(i18n.get("usuario.editar.btn.actualizar"));
        btnClean.setText(i18n.get("usuario.editar.btn.limpiar"));
        btnBuscar.setText(i18n.get("usuario.editar.btn.buscar"));
        lblNombreCompleto.setText(i18n.get("usuario.editar.lbl.nombrecompleto"));
        lblFechaNacimiento.setText(i18n.get("usuario.editar.lbl.fechanacimiento"));
        lblCorreo.setText(i18n.get("usuario.editar.lbl.correo"));
        lblTelefono.setText(i18n.get("usuario.editar.lbl.telefono"));

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

    public JPanel getPanelAll() {return panelAll;}
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
    public JTextField getTxtUsuario() {
        return txtUsuario;
    }
    public void setTxtUsuario(JTextField txtUsuario) {
        this.txtUsuario = txtUsuario;
    }
    public JTextField getTxtContrasena() {
        return txtContrasena;
    }
    public void setTxtContrasena(JTextField txtContrasena) {
        this.txtContrasena = txtContrasena;
    }
    public JComboBox getCbxRol() {
        return cbxRol;
    }
    public void setCbxRol(JComboBox cbxRol) {
        this.cbxRol = cbxRol;
    }
    public JButton getBtnActualizar() {
        return btnActualizar;
    }
    public void setBtnActualizar(JButton btnActualizar) {
        this.btnActualizar = btnActualizar;
    }
    public JButton getBtnClean() {
        return btnClean;
    }
    public void setBtnClean(JButton btnClean) {
        this.btnClean = btnClean;
    }
    public JButton getBtnBuscar() {
        return btnBuscar;
    }
    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }
    public JLabel getLblUsuario() {
        return lblUsuario;
    }
    public void setLblUsuario(JLabel lblUsuario) {
        this.lblUsuario = lblUsuario;
    }
    public JLabel getLblContrasena() {
        return lblContrasena;
    }
    public void setLblContrasena(JLabel lblContrasena) {
        this.lblContrasena = lblContrasena;
    }
    public JLabel getLblRol() {
        return lblRol;
    }
    public void setLblRol(JLabel lblRol) {
        this.lblRol = lblRol;
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
    public JLabel getLblNombreCompleto() {
        return lblNombreCompleto;
    }
    public void setLblNombreCompleto(JLabel lblNombreCompleto) {
        this.lblNombreCompleto = lblNombreCompleto;
    }
    public JTextField getTxtNombreCompleto() {
        return txtNombreCompleto;
    }
    public void setTxtNombreCompleto(JTextField txtNombreCompleto) {
        this.txtNombreCompleto = txtNombreCompleto;
    }
    public JLabel getLblFechaNacimiento() {
        return lblFechaNacimiento;
    }
    public void setLblFechaNacimiento(JLabel lblFechaNacimiento) {
        this.lblFechaNacimiento = lblFechaNacimiento;
    }
    public JLabel getLblCorreo() {
        return lblCorreo;
    }
    public void setLblCorreo(JLabel lblCorreo) {
        this.lblCorreo = lblCorreo;
    }
    public JTextField getTxtCorreo() {
        return txtCorreo;
    }
    public void setTxtCorreo(JTextField txtCorreo) {
        this.txtCorreo = txtCorreo;
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
    public MensajeInternacionalizacionHandler getI18n() {
        return i18n;
    }
    public void setI18n(MensajeInternacionalizacionHandler i18n) {
        this.i18n = i18n;
    }
}
