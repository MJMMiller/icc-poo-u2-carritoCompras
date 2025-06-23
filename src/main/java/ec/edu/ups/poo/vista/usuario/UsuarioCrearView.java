package ec.edu.ups.poo.vista.usuario;

import javax.swing.*;

public class UsuarioCrearView extends JInternalFrame {
    private JPanel panelAll;
    private JPanel panelSuperior;
    private JLabel txtSettingsProduc;
    private JPanel panelCenter;
    private JTextField lblUsername;
    private JLabel txtUsername;
    private JTextField lblPassword;
    private JLabel txtPassword;
    private JComboBox cbxRol;
    private JButton btnSave;
    private JButton btnClean;
    private JPanel panelInferior;

    public UsuarioCrearView() {
        setContentPane(panelAll);
        setTitle("Settings Products");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 250);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
    }

    public JPanel getPaneAll() {
        return panelAll;
    }

    public void setPaneAll(JPanel paneAll) {
        this.panelAll = paneAll;
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

    public JPanel getPanelCenter() {
        return panelCenter;
    }

    public void setPanelCenter(JPanel panelCenter) {
        this.panelCenter = panelCenter;
    }

    public JTextField getLblUsername() {
        return lblUsername;
    }

    public void setLblUsername(JTextField lblUsername) {
        this.lblUsername = lblUsername;
    }

    public JLabel getTxtUsername() {
        return txtUsername;
    }

    public void setTxtUsername(JLabel txtUsername) {
        this.txtUsername = txtUsername;
    }

    public JTextField getLblPassword() {
        return lblPassword;
    }

    public void setLblPassword(JTextField lblPassword) {
        this.lblPassword = lblPassword;
    }

    public JLabel getTxtPassword() {
        return txtPassword;
    }

    public void setTxtPassword(JLabel txtPassword) {
        this.txtPassword = txtPassword;
    }

    public JComboBox getCbxRol() {
        return cbxRol;
    }

    public void setCbxRol(JComboBox cbxRol) {
        this.cbxRol = cbxRol;
    }

    public JButton getBtnSave() {
        return btnSave;
    }

    public void setBtnSave(JButton btnSave) {
        this.btnSave = btnSave;
    }

    public JButton getBtnClean() {
        return btnClean;
    }

    public void setBtnClean(JButton btnClean) {
        this.btnClean = btnClean;
    }

    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }
}
