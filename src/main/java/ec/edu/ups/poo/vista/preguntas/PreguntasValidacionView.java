package ec.edu.ups.poo.vista.preguntas;

import ec.edu.ups.poo.dao.UsuarioDAO;
import ec.edu.ups.poo.modelo.Usuario;
import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.util.enums.TipoIcono;

import javax.swing.*;

public class PreguntasValidacionView extends JFrame{


    private JPanel panelAll;
    private JPanel panelSuperior;
    private JLabel lblPreguntaSeguridad;
    private JTextField txtPregunta;
    private JPanel panelForm;
    private JButton btnEnviar;
    private JButton btnClean;
    private JTextField txtNuevaContra;
    private JLabel lblNuevaContra;
    private JLabel lblTitulo;
    private JComboBox cbxPreguntas;
    private JTextField txtRespuestaSeguidad;
    private JLabel lblQuestion;
    private JButton btnsiguientePregunta;
    private JTextField txtRespuestComparar;
    private JLabel lblPregunta;
    private JComboBox cbxIdioma;
    private JLabel lblIdioma;
    private JButton btnExit;

    private MensajeInternacionalizacionHandler i18n;

    public PreguntasValidacionView(Usuario usuario, UsuarioDAO usuarioDAO, MensajeInternacionalizacionHandler i18n) {
        this.i18n = i18n;
        setContentPane(panelAll);
        setSize(400, 500);
        setLocationRelativeTo(null);
        lblNuevaContra.setVisible(false);
        txtNuevaContra.setVisible(false);

        aplicarIdiomas();
        aplicarIconos();
    }

    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }

    public int mostrarMensajeConfirmacion(String mensaje, String titulo, int tipo) {
        Object[] botones = {i18n.get("mensaje.confirmacion"), i18n.get("mensaje.cancelacion")};
        return JOptionPane.showOptionDialog(
                this, mensaje, titulo,
                JOptionPane.DEFAULT_OPTION, tipo,
                null, botones, botones[0]);
    }

    public void aplicarIdiomas() {
        setTitle(i18n.get("producto.aplicar.idiomas"));
        btnExit.setText(i18n.get("register.btnSalir"));
        lblIdioma.setText(i18n.get("login.lblIdioma"));
        lblTitulo.setText(i18n.get("preguntas.validacion.lbl.titulo"));
        lblPreguntaSeguridad.setText(i18n.get("preguntas.validacion.lbll.pregunta"));
        btnsiguientePregunta.setText(i18n.get("preguntas.validacion.btn.siguiente"));
        btnEnviar.setText(i18n.get("preguntas.validacion.btn.enviar"));
        btnClean.setText(i18n.get("preguntas.validacion.btn.limpiar"));
        lblNuevaContra.setText(i18n.get("preguntas.validacion.lbl.nueva.contra"));
        actualizarOpcionesIdioma();
    }

    public void aplicarIconos() {
        setIconImage(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.PREGUNTA).getImage());
        btnExit.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.X));
        btnClean.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.CLEAN));
        btnEnviar.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.GUARDAR));
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


    public JButton getBtnExit() {return btnExit;}
    public void setBtnExit(JButton btnExit) {this.btnExit = btnExit;}
    public JComboBox getCbxIdioma() {return cbxIdioma;}
    public void setCbxIdioma(JComboBox cbxIdioma) {this.cbxIdioma = cbxIdioma;}
    public JLabel getLblIdioma() {return lblIdioma;}
    public void setLblIdioma(JLabel lblIdioma) {this.lblIdioma = lblIdioma;}
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
    public JLabel getLblPreguntaSeguridad() {
        return lblPreguntaSeguridad;
    }
    public void setLblPreguntaSeguridad(JLabel lblPreguntaSeguridad) {this.lblPreguntaSeguridad = lblPreguntaSeguridad;}
    public JTextField getTxtPregunta() {
        return txtPregunta;
    }
    public void setTxtPregunta(JTextField txtPregunta) {
        this.txtPregunta = txtPregunta;
    }
    public JPanel getPanelForm() {
        return panelForm;
    }
    public void setPanelForm(JPanel panelForm) {
        this.panelForm = panelForm;
    }
    public JButton getBtnEnviar() {return btnEnviar;}
    public void setBtnEnviar(JButton btnEnviar) {this.btnEnviar = btnEnviar;}
    public JButton getBtnClean() {return btnClean;}
    public void setBtnClean(JButton btnClean) {this.btnClean = btnClean;}
    public JTextField getTxtNuevaContra() {return txtNuevaContra;}
    public void setTxtNuevaContra(JTextField txtNuevaContra) {this.txtNuevaContra = txtNuevaContra;}
    public JLabel getLblNuevaContra() {return lblNuevaContra;}
    public void setLblNuevaContra(JLabel lblNuevaContra) {this.lblNuevaContra = lblNuevaContra;}
    public JLabel getLblTitulo() {return lblTitulo;}
    public void setLblTitulo(JLabel lblTitulo) {this.lblTitulo = lblTitulo;}
    public JComboBox getCbxPreguntas() {return cbxPreguntas;}
    public void setCbxPreguntas(JComboBox cbxPreguntas) {this.cbxPreguntas = cbxPreguntas;}
    public JTextField getTxtRespuestaSeguidad() {return txtRespuestaSeguidad;}
    public void setTxtRespuestaSeguidad(JTextField txtRespuestaSeguidad) {this.txtRespuestaSeguidad = txtRespuestaSeguidad;}
    public JLabel getLblQuestion() {return lblQuestion;}
    public void setLblQuestion(JLabel lblQuestion) {this.lblQuestion = lblQuestion;}
    public JButton getBtnsiguientePregunta() {return btnsiguientePregunta;}
    public void setBtnsiguientePregunta(JButton btnsiguientePregunta) {this.btnsiguientePregunta = btnsiguientePregunta;}
    public JTextField getTxtRespuestComparar() {return txtRespuestComparar;}
    public void setTxtRespuestComparar(JTextField txtRespuestComparar) {this.txtRespuestComparar = txtRespuestComparar;}
    public JLabel getLblPregunta() {return lblPregunta;}
    public void setLblPregunta(JLabel lblPregunta) {this.lblPregunta = lblPregunta;}
    public MensajeInternacionalizacionHandler getI18n() {return i18n;}
    public void setI18n(MensajeInternacionalizacionHandler i18n) {this.i18n = i18n;}
}
