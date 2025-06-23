package ec.edu.ups.poo.vista.inicio;

import javax.swing.*;

public class RegisterView extends JFrame {
    private JPanel panelAll;
    private JPanel panelSuperior;
    private JLabel txtSettingsProduc;
    private JPanel panelCentral;
    private JTextField lblUsername;
    private JTextField lblPassword;
    private JButton btnRegister;
    private JButton btnClean;
    private JButton btnExit;
    private JPanel panelInferior;

    public RegisterView() {
        setTitle("Log In");
        setContentPane(panelAll);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 225);
        setLocationRelativeTo(null);
        setResizable(false);
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

    public JTextField getLblUsername() {
        return lblUsername;
    }

    public void setLblUsername(JTextField lblUsername) {
        this.lblUsername = lblUsername;
    }

    public JTextField getLblPassword() {
        return lblPassword;
    }

    public void setLblPassword(JTextField lblPassword) {
        this.lblPassword = lblPassword;
    }

    public JButton getBtnRegister() {
        return btnRegister;
    }

    public void setBtnRegister(JButton btnRegister) {
        this.btnRegister = btnRegister;
    }

    public JButton getBtnClean() {
        return btnClean;
    }

    public void setBtnClean(JButton btnClean) {
        this.btnClean = btnClean;
    }

    public JButton getBtnExit() {
        return btnExit;
    }

    public void setBtnExit(JButton btnExit) {
        this.btnExit = btnExit;
    }

    public JPanel getPanelInferior() {
        return panelInferior;
    }

    public void setPanelInferior(JPanel panelInferior) {
        this.panelInferior = panelInferior;
    }

    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }

    public void limpiarCampos() {
        lblUsername.setText("");
        lblPassword.setText("");
    }

}
