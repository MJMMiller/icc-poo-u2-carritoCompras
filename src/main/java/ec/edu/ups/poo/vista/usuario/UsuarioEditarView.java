package ec.edu.ups.poo.vista.usuario;

import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;

import javax.swing.*;

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
    private MensajeInternacionalizacionHandler i18n;

    public UsuarioEditarView(MensajeInternacionalizacionHandler i18n) {
        this.i18n = i18n;
        setContentPane(panelAll);
        setTitle("Editar Usuario");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 250);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        aplicarIdioma();
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

    public JLabel getTxtSettingsProduc() {
        return lblTitulo;
    }

    public void setTxtSettingsProduc(JLabel txtSettingsProduc) {
        this.lblTitulo = txtSettingsProduc;
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

    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }

    public int mostrarMensajeConfirmacion(String mensaje, String titulo, int tipo) {
        Object[] botones = {"Confirmar", "Cancelar"};
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
    }
}
