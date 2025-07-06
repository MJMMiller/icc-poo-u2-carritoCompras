package ec.edu.ups.poo.vista.inicio;

import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.util.enums.TipoIcono;

import javax.swing.*;

public class LogInView extends JFrame {
    private JPanel panelAll;
    private JTextField txtUserName;
    private JLabel lblUsuario;
    private JLabel lblContrasena;
    private JButton btnInicioSesion;
    private JButton btnSalir;
    private JPasswordField txtContrasena;
    private JButton btnRegistro;
    private JPanel panelFinal;
    private JPanel panelCentro;
    private JPanel panelArriba;
    private JLabel lblTitulo;
    private JComboBox cbxIdioma;
    private JLabel lblIdioma;
    private JButton btnRecuContra;
    private MensajeInternacionalizacionHandler i18n;

    public LogInView(MensajeInternacionalizacionHandler i18n) {
        this.i18n = i18n;
        setContentPane(panelAll);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 300);
        setLocationRelativeTo(null);

        aplicarIdioma();
        aplicarIconos();
    }

    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }

    public int mostrarMensajeAlert(String mensaje, String titulo, int tipo) {
        Object[] botones = {i18n.get("mensaje.confirmacion"), i18n.get("mensaje.cancelacion")};
        return JOptionPane.showOptionDialog(
                this, mensaje, titulo,
                JOptionPane.DEFAULT_OPTION, tipo,
                null, botones, botones[0]);
    }

    public void actualizarOpcionesIdioma() {
        String code = i18n.getCodigoIdioma();
        cbxIdioma.removeAllItems();
        cbxIdioma.addItem(i18n.get("idioma.espanol"));
        cbxIdioma.addItem(i18n.get("idioma.ingles"));
        cbxIdioma.addItem(i18n.get("idioma.frances"));

        int idx = 0;
        if (code.equals("en")) idx = 1;
        else if (code.equals("fr")) idx = 2;
        cbxIdioma.setSelectedIndex(idx);
    }

    public void aplicarIdioma() {
        setTitle(i18n.get("login.title"));
        lblTitulo.setText(i18n.get("login.lblTitulo"));
        lblIdioma.setText(i18n.get("login.lblIdioma"));
        lblUsuario.setText(i18n.get("login.lblUsuario"));
        lblContrasena.setText(i18n.get("login.lblContrasena"));
        btnInicioSesion.setText(i18n.get("login.btnInicioSesion"));
        btnSalir.setText(i18n.get("login.btnSalir"));
        btnRegistro.setText(i18n.get("login.btnRegistro"));
        btnRecuContra.setText(i18n.get("login.btnRecuContra"));
        actualizarOpcionesIdioma();
    }

    public void aplicarIconos() {
        setIconImage(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.POO_LOGO).getImage());
        btnInicioSesion.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.BLOGIN));
        btnSalir.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.X));
        btnRegistro.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.REGISTRO_USUARIO));
        btnRecuContra.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.RECUPERAR_CONTRASENA));
    }

    public JButton getBtnRecuContra() {
        return btnRecuContra;
    }
    public void setBtnRecuContra(JButton btnRecuContra) {
        this.btnRecuContra = btnRecuContra;
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
        return btnInicioSesion;
    }
    public void setBtnLogIn(JButton btnLogIn) {
        this.btnInicioSesion = btnLogIn;
    }
    public JButton getBtnExit() {
        return btnSalir;
    }
    public void setBtnExit(JButton btnExit) {
        this.btnSalir = btnExit;
    }
    public JButton getBtnRegister() {
        return btnRegistro;
    }
    public void setBtnRegister(JButton btnRegister) {
        this.btnRegistro = btnRegister;
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
        return lblUsuario;
    }
    public void setLblUsername(JLabel lblUsername) {
        this.lblUsuario = lblUsername;
    }
    public JLabel getLblContrasena() {
        return lblContrasena;
    }
    public void setLblContrasena(JLabel lblContrasena) {
        this.lblContrasena = lblContrasena;
    }
    public JLabel getTxtSettingsProduc() {
        return lblTitulo;
    }
    public void setTxtSettingsProduc(JLabel txtSettingsProduc) {
        this.lblTitulo = txtSettingsProduc;
    }
    public JComboBox getCbxIdioma() {return cbxIdioma;}
    public void setCbxIdioma(JComboBox cbxIdioma) {
        this.cbxIdioma = cbxIdioma;
    }
    public JLabel getLblIdioma() {return lblIdioma;}
    public void setLblIdioma(JLabel lblIdioma) {this.lblIdioma = lblIdioma;}

}