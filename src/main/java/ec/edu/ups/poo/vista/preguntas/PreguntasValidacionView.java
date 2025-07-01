package ec.edu.ups.poo.vista.preguntas;

import ec.edu.ups.poo.dao.UsuarioDAO;
import ec.edu.ups.poo.modelo.Usuario;
import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;

import javax.swing.*;

public class PreguntasValidacionView extends JFrame{


    private JPanel panelAll;
    private JPanel panelSuperior;
    private JLabel lblPregunta1;
    private JTextField txtPregunta1;
    private JLabel lblPregunta2;
    private JPanel panelForm;
    private JTextField txtPregunta2;
    private JTextField txtPregunta3;
    private JLabel lblPregunta3;
    private JButton btnEnviar;
    private JButton btnClean;
    private JTextField txtNuevaContra;
    private JLabel lblNuevaContra;
    private JLabel lblTitulo;

    private MensajeInternacionalizacionHandler i18n;

    public PreguntasValidacionView(Usuario usuario, UsuarioDAO usuarioDAO, MensajeInternacionalizacionHandler i18n) {
        this.i18n = i18n;
        setTitle("Registro de Preguntas de Validación");
        setContentPane(panelAll);
        setSize(400, 500);
        setLocationRelativeTo(null);
        lblNuevaContra.setVisible(false);
        txtNuevaContra.setVisible(false);

        aplicarIdiomas();
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

    public JLabel getLblPregunta2() {
        return lblPregunta2;
    }

    public void setLblPregunta2(JLabel lblPregunta2) {
        this.lblPregunta2 = lblPregunta2;
    }

    public JPanel getPanelForm() {
        return panelForm;
    }

    public void setPanelForm(JPanel panelForm) {
        this.panelForm = panelForm;
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

    public JLabel getLblPregunta3() {
        return lblPregunta3;
    }

    public void setLblPregunta3(JLabel lblPregunta3) {
        this.lblPregunta3 = lblPregunta3;
    }

    public JButton getBtnEnviar() {
        return btnEnviar;
    }

    public void setBtnEnviar(JButton btnEnviar) {
        this.btnEnviar = btnEnviar;
    }

    public JButton getBtnClean() {
        return btnClean;
    }

    public void setBtnClean(JButton btnClean) {
        this.btnClean = btnClean;
    }

    public JTextField getTxtNuevaContra() {
        return txtNuevaContra;
    }

    public void setTxtNuevaContra(JTextField txtNuevaContra) {
        this.txtNuevaContra = txtNuevaContra;
    }

    public JLabel getLblNuevaContra() {
        return lblNuevaContra;
    }

    public void setLblNuevaContra(JLabel lblNuevaContra) {
        this.lblNuevaContra = lblNuevaContra;
    }

    public JLabel getLblTitulo() {
        return lblTitulo;
    }

    public void setLblTitulo(JLabel lblTitulo) {
        this.lblTitulo = lblTitulo;
    }

    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }

    public int mostrarMensajeConfirmacion(String mensaje, String titulo, int tipo) {
        Object[] botones = {"Confirm", "Cancel"};
        return JOptionPane.showOptionDialog(
                this, mensaje, titulo,
                JOptionPane.DEFAULT_OPTION, tipo,
                null, botones, botones[0]);
    }

    public void aplicarIdiomas() {
        setTitle(i18n.get("producto.aplicar.idiomas"));
        lblTitulo.setText(i18n.get("preguntas.validacion.lbl.titulo"));
        lblPregunta1.setText(i18n.get("preguntas.validacion.lbll.pregunta1"));
        lblPregunta2.setText(i18n.get("preguntas.validacion.lbl.pregunta2"));
        lblPregunta3.setText(i18n.get("preguntas.validacion.lbl.pregunta3"));
        btnEnviar.setText(i18n.get("preguntas.validacion.btn.enviar"));
        btnClean.setText(i18n.get("preguntas.validacion.btn.limpiar"));
        lblNuevaContra.setText(i18n.get("preguntas.validacion.lbl.nueva.contra"));
    }
}
