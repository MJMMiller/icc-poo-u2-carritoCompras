package ec.edu.ups.poo.vista.usuario;

import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;

import javax.swing.*;

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
    private MensajeInternacionalizacionHandler i18n;

    public UsuarioAnadirView(MensajeInternacionalizacionHandler i18n) {
        this.i18n = i18n;
        setContentPane(panelAll);
        setTitle("Crear Usuario");
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
        setTitle(i18n.get("usuario.anadir.titulo"));
        lblTitulo.setText(i18n.get("usuario.anadir.lbl.titulo"));
        lblUsuario.setText(i18n.get("usuario.anadir.lbl.usuario"));
        lblContrasena.setText(i18n.get("usuario.anadir.lbl.contrasena"));
        lblRol.setText(i18n.get("usuario.anadir.lbl.rol"));
        btnRegistrar.setText(i18n.get("usuario.anadir.btn.registrar"));
        btnClean.setText(i18n.get("usuario.anadir.btn.limpiar"));
    }
}
