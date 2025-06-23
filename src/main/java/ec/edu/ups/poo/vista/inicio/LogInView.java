package ec.edu.ups.poo.vista.inicio;

import javax.swing.*;

public class LogInView extends JFrame {
    private JPanel panelAll;
    private JTextField lblUserName;
    private JLabel txtUserName;
    private JLabel txtPassword;
    private JButton btnLogIn;
    private JButton btnExit;
    private JPasswordField lblPassword;
    private JButton btnRegister;
    private JPanel panelFinal;
    private JPanel panelCentro;
    private JPanel panelArriba;
    private JLabel txtSettingsProduc;

    public LogInView() {
        setTitle("Log In");
        setContentPane(panelAll);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

    public JTextField getLblUserName() {
        return lblUserName;
    }

    public void setLblUserName(JTextField lblUserName) {
        this.lblUserName = lblUserName;
    }

    public JLabel getTxtUserName() {
        return txtUserName;
    }

    public void setTxtUserName(JLabel txtUserName) {
        this.txtUserName = txtUserName;
    }

    public JLabel getTxtPassword() {
        return txtPassword;
    }

    public void setTxtPassword(JLabel txtPassword) {
        this.txtPassword = txtPassword;
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

    public JPasswordField getLblPassword() {
        return lblPassword;
    }

    public void setLblPassword(JPasswordField lblPassword) {
        this.lblPassword = lblPassword;
    }

    public JButton getBtnRegister() {
        return btnRegister;
    }

    public void setBtnRegister(JButton btnRegister) {
        this.btnRegister = btnRegister;
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

    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }
}
