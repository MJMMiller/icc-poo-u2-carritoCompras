package ec.edu.ups.poo.vista.usuario;

import javax.swing.*;

public class UsuarioEditarView extends JInternalFrame {
    private JPanel panelAll;
    private JPanel panelSuperior;
    private JLabel txtSettingsProduc;
    private JTextField lblUsername;
    private JTextField lblPassword;
    private JComboBox cbxRol;
    private JButton btnSave;
    private JButton btnClean;
    private JButton btnBuscar;

    public UsuarioEditarView() {
        setContentPane(panelAll);
        setTitle("Settings Products");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 250);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
    }

    public JButton getBtnBuscar() {return btnBuscar;}

    public void setBtnBuscar(JButton btnBuscar) {this.btnBuscar = btnBuscar;}

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
