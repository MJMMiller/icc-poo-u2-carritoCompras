package ec.edu.ups.poo.vista.usuario;

import javax.swing.*;

public class UsuarioElimiarView extends JInternalFrame {
    private JPanel panelAll;
    private JLabel txtSettingsProduc;
    private JPanel panelSuperioir;
    private JPanel panelCentral;
    private JTextField lblUsername;
    private JTextField lblPassword;
    private JComboBox cbxRol;
    private JButton delateButton;
    private JPanel panelInferioir;
    private JButton btnSearch;

    public UsuarioElimiarView() {
        setContentPane(panelAll);
        setTitle("Delate Products");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 250);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
    }

    public JButton getBtnSearch() {return btnSearch;}

    public void setBtnSearch(JButton btnSearch) {this.btnSearch = btnSearch;}

    public JPanel getPanelAll() {
        return panelAll;
    }

    public void setPanelAll(JPanel panelAll) {
        this.panelAll = panelAll;
    }

    public JLabel getTxtSettingsProduc() {
        return txtSettingsProduc;
    }

    public void setTxtSettingsProduc(JLabel txtSettingsProduc) {
        this.txtSettingsProduc = txtSettingsProduc;
    }

    public JPanel getPanelSuperioir() {
        return panelSuperioir;
    }

    public void setPanelSuperioir(JPanel panelSuperioir) {
        this.panelSuperioir = panelSuperioir;
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

    public JComboBox getCbxRol() {return cbxRol;}

    public void setCbxRol(JComboBox cbxRol) {this.cbxRol = cbxRol;}

    public JButton getDelateButton() {
        return delateButton;
    }

    public void setDelateButton(JButton delateButton) {
        this.delateButton = delateButton;
    }

    public JPanel getPanelInferioir() {
        return panelInferioir;
    }

    public void setPanelInferioir(JPanel panelInferioir) {
        this.panelInferioir = panelInferioir;
    }

    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }

    public int mostrarMensajeDelate(String mensaje, String titulo, int tipo) {
        Object[] botones = {"Confirm", "Cancel"};
        return JOptionPane.showOptionDialog(
                this, mensaje, titulo,
                JOptionPane.DEFAULT_OPTION, tipo,
                null, botones, botones[0]);
    }
}
