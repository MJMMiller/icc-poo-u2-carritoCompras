package ec.edu.ups.poo.vista.inicio;

import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;

import javax.swing.*;

public class LogInView extends JFrame {
    private JPanel panelAll;
    private JTextField txtUserName;
    private JLabel lblUsername;
    private JLabel lblContrasena;
    private JButton btnLogIn;
    private JButton btnExit;
    private JPasswordField txtContrasena;
    private JButton btnRegister;
    private JPanel panelFinal;
    private JPanel panelCentro;
    private JPanel panelArriba;
    private JLabel txtSettingsProduc;
    private JComboBox cbxIdioma;
    private MensajeInternacionalizacionHandler mInter;

    public LogInView() {
        setTitle("Inicio de Sesión");
        setContentPane(panelAll);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 225);
        setLocationRelativeTo(null);
        setResizable(false);

        cambiarIdioma();
    }

    public JTextField getTxtUserName() {
        return txtUserName;
    }

    public void setTxtUserName(JTextField txtUserName) {
        this.txtUserName = txtUserName;
    }

    public JPasswordField getTxtContrasena() {
        return txtContrasena;
    }

    public void setTxtContrasena(JPasswordField txtContrasena) {
        this.txtContrasena = txtContrasena;
    }

    public JButton getBtnLogIn() {
        return btnLogIn;
    }

    public void setBtnLogIn(JButton btnLogIn) {
        this.btnLogIn = btnLogIn;
    }

    public JButton getBtnExit() {
        return btnExit;
    }

    public void setBtnExit(JButton btnExit) {
        this.btnExit = btnExit;
    }

    public JButton getBtnRegister() {
        return btnRegister;
    }

    public void setBtnRegister(JButton btnRegister) {
        this.btnRegister = btnRegister;
    }

    public JPanel getPanelAll() {
        return panelAll;
    }

    public void setPanelAll(JPanel panelAll) {
        this.panelAll = panelAll;
    }

    public JPanel getPanelFinal() {
        return panelFinal;
    }

    public void setPanelFinal(JPanel panelFinal) {
        this.panelFinal = panelFinal;
    }

    public JPanel getPanelCentro() {
        return panelCentro;
    }

    public void setPanelCentro(JPanel panelCentro) {
        this.panelCentro = panelCentro;
    }

    public JPanel getPanelArriba() {
        return panelArriba;
    }

    public void setPanelArriba(JPanel panelArriba) {
        this.panelArriba = panelArriba;
    }

    public JLabel getLblUsername() {
        return lblUsername;
    }

    public void setLblUsername(JLabel lblUsername) {
        this.lblUsername = lblUsername;
    }

    public JLabel getLblContrasena() {
        return lblContrasena;
    }

    public void setLblContrasena(JLabel lblContrasena) {
        this.lblContrasena = lblContrasena;
    }

    public JLabel getTxtSettingsProduc() {
        return txtSettingsProduc;
    }

    public void setTxtSettingsProduc(JLabel txtSettingsProduc) {
        this.txtSettingsProduc = txtSettingsProduc;
    }

    public JComboBox getCbxIdioma() {
        return cbxIdioma;
    }
    public void setCbxIdioma(JComboBox cbxIdioma) {
        this.cbxIdioma = cbxIdioma;
    }
    public void cambiarIdioma() {
        cbxIdioma.removeAllItems();
        cbxIdioma.addItem("Español");
        cbxIdioma.addItem("Ingles");
        cbxIdioma.addItem("Frances");
    }

    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }
}