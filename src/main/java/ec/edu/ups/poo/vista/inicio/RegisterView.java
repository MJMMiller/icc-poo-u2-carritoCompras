package ec.edu.ups.poo.vista.inicio;

import javax.swing.*;

public class RegisterView extends JFrame {
    private JPanel panelAll;
    private JPanel panelSuperior;
    private JLabel txtSettingsProduc;
    private JPanel panelCentral;
    private JTextField txtUsuario;
    private JTextField txtContrasena;
    private JButton btnRegistro;
    private JButton btnClean;
    private JButton btnSalir;
    private JPanel panelInferior;
    private JLabel lblUsuario;
    private JLabel lblContrasena;
    private JPanel panelForm;
    private JLabel lblPregunta1;
    private JTextField txtPregunta1;
    private JTextField txtPregunta2;
    private JTextField txtPregunta3;
    private JLabel lblPregunta2;
    private JLabel lblPregunta3;

    public RegisterView() {
        setTitle("Log In");
        setContentPane(panelAll);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 500);
        setLocationRelativeTo(null);
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
        return txtSettingsProduc;
    }

    public void setTxtSettingsProduc(JLabel txtSettingsProduc) {
        this.txtSettingsProduc = txtSettingsProduc;
    }

    public JPanel getPanelCentral() {
        return panelCentral;
    }

    public void setPanelCentral(JPanel panelCentral) {
        this.panelCentral = panelCentral;
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

    public JButton getBtnRegistro() {
        return btnRegistro;
    }

    public void setBtnRegistro(JButton btnRegistro) {
        this.btnRegistro = btnRegistro;
    }

    public JButton getBtnClean() {
        return btnClean;
    }

    public void setBtnClean(JButton btnClean) {
        this.btnClean = btnClean;
    }

    public JButton getBtnSalir() {
        return btnSalir;
    }

    public void setBtnSalir(JButton btnSalir) {
        this.btnSalir = btnSalir;
    }

    public JPanel getPanelInferior() {
        return panelInferior;
    }

    public void setPanelInferior(JPanel panelInferior) {
        this.panelInferior = panelInferior;
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

    public JPanel getPanelForm() {
        return panelForm;
    }

    public void setPanelForm(JPanel panelForm) {
        this.panelForm = panelForm;
    }

    public JLabel getLblPregunta1() {
        return lblPregunta1;
    }

    public void setLblPregunta1(JLabel lblPregunta1) {
        this.lblPregunta1 = lblPregunta1;
    }

    public JTextField getTxtPregunta1() {
        return txtPregunta1;
    }

    public void setTxtPregunta1(JTextField txtPregunta1) {
        this.txtPregunta1 = txtPregunta1;
    }

    public JTextField getTxtPregunta2() {
        return txtPregunta2;
    }

    public void setTxtPregunta2(JTextField txtPregunta2) {
        this.txtPregunta2 = txtPregunta2;
    }

    public JTextField getTxtPregunta3() {
        return txtPregunta3;
    }

    public void setTxtPregunta3(JTextField txtPregunta3) {
        this.txtPregunta3 = txtPregunta3;
    }

    public JLabel getLblPregunta2() {
        return lblPregunta2;
    }

    public void setLblPregunta2(JLabel lblPregunta2) {
        this.lblPregunta2 = lblPregunta2;
    }

    public JLabel getLblPregunta3() {
        return lblPregunta3;
    }

    public void setLblPregunta3(JLabel lblPregunta3) {
        this.lblPregunta3 = lblPregunta3;
    }

    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }
}
