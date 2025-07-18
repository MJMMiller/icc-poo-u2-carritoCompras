package ec.edu.ups.poo.vista.preguntas;

import ec.edu.ups.poo.dao.UsuarioDAO;
import ec.edu.ups.poo.modelo.Usuario;
import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.util.TipoIcono;

import javax.swing.*;

/**
 * Vista para la validación de preguntas de seguridad y recuperación de contraseña.
 * Permite al usuario responder preguntas de seguridad, cambiar la contraseña y seleccionar idioma.
 */
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

    /**
     * Constructor de PreguntasValidacionView.
     * Inicializa la vista con el usuario, DAO de usuario y manejador de internacionalización.
     * Configura la interfaz, aplica idioma e iconos y oculta los campos de nueva contraseña.
     *
     * @param usuario Usuario que responde las preguntas.
     * @param usuarioDAO DAO para operaciones de usuario.
     * @param i18n Manejador de internacionalización de mensajes.
     */
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

    /**
     * Muestra un mensaje en un cuadro de diálogo.
     *
     * @param mensaje Mensaje a mostrar.
     * @param titulo Título de la ventana de diálogo.
     * @param tipo Tipo de mensaje (JOptionPane).
     */
    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }

    /**
     * Muestra un cuadro de diálogo de confirmación con botones personalizados.
     *
     * @param mensaje Mensaje a mostrar.
     * @param titulo Título de la ventana de diálogo.
     * @param tipo Tipo de mensaje (JOptionPane).
     * @return Índice del botón seleccionado por el usuario.
     */
    public int mostrarMensajeConfirmacion(String mensaje, String titulo, int tipo) {
        Object[] botones = {i18n.get("mensaje.confirmacion"), i18n.get("mensaje.cancelacion")};
        return JOptionPane.showOptionDialog(
                this, mensaje, titulo,
                JOptionPane.DEFAULT_OPTION, tipo,
                null, botones, botones[0]);
    }

    /**
     * Aplica los textos traducidos a todos los componentes de la vista según el idioma actual.
     * No recibe parámetros ni retorna valores.
     */
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

    /**
     * Aplica los iconos correspondientes a los botones de la vista.
     * No recibe parámetros ni retorna valores.
     */
    public void aplicarIconos() {
        setIconImage(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.PREGUNTA).getImage());
        btnExit.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.X));
        btnClean.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.CLEAN));
        btnEnviar.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.GUARDAR));
    }

    /**
     * Actualiza las opciones del ComboBox de idioma según el idioma actual.
     * No recibe parámetros ni retorna valores.
     */
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


    // Métodos getter y setter para los componentes de la vista.

    /**
     * Obtiene el botón para salir.
     * @return JButton para salir.
     */
    public JButton getBtnExit() {return btnExit;}
    /**
     * Establece el botón para salir.
     * @param btnExit JButton para salir.
     */
    public void setBtnExit(JButton btnExit) {this.btnExit = btnExit;}
    /**
     * Obtiene el ComboBox de idioma.
     * @return JComboBox de idioma.
     */
    public JComboBox getCbxIdioma() {return cbxIdioma;}
    /**
     * Establece el ComboBox de idioma.
     * @param cbxIdioma JComboBox de idioma.
     */
    public void setCbxIdioma(JComboBox cbxIdioma) {this.cbxIdioma = cbxIdioma;}
    /**
     * Obtiene la etiqueta de idioma.
     * @return JLabel de idioma.
     */
    public JLabel getLblIdioma() {return lblIdioma;}
    /**
     * Establece la etiqueta de idioma.
     * @param lblIdioma JLabel de idioma.
     */
    public void setLblIdioma(JLabel lblIdioma) {this.lblIdioma = lblIdioma;}
    /**
     * Obtiene el panel principal.
     * @return JPanel principal.
     */
    public JPanel getPanelAll() {
        return panelAll;
    }
    /**
     * Establece el panel principal.
     * @param panelAll JPanel principal.
     */
    public void setPanelAll(JPanel panelAll) {
        this.panelAll = panelAll;
    }
    /**
     * Obtiene el panel superior.
     * @return JPanel superior.
     */
    public JPanel getPanelSuperior() {
        return panelSuperior;
    }
    /**
     * Establece el panel superior.
     * @param panelSuperior JPanel superior.
     */
    public void setPanelSuperior(JPanel panelSuperior) {
        this.panelSuperior = panelSuperior;
    }
    /**
     * Obtiene la etiqueta de pregunta de seguridad.
     * @return JLabel de pregunta de seguridad.
     */
    public JLabel getLblPreguntaSeguridad() {
        return lblPreguntaSeguridad;
    }
    /**
     * Establece la etiqueta de pregunta de seguridad.
     * @param lblPreguntaSeguridad JLabel de pregunta de seguridad.
     */
    public void setLblPreguntaSeguridad(JLabel lblPreguntaSeguridad) {this.lblPreguntaSeguridad = lblPreguntaSeguridad;}
    /**
     * Obtiene el campo de texto de la pregunta.
     * @return JTextField de pregunta.
     */
    public JTextField getTxtPregunta() {
        return txtPregunta;
    }
    /**
     * Establece el campo de texto de la pregunta.
     * @param txtPregunta JTextField de pregunta.
     */
    public void setTxtPregunta(JTextField txtPregunta) {
        this.txtPregunta = txtPregunta;
    }
    /**
     * Obtiene el panel del formulario.
     * @return JPanel del formulario.
     */
    public JPanel getPanelForm() {
        return panelForm;
    }
    /**
     * Establece el panel del formulario.
     * @param panelForm JPanel del formulario.
     */
    public void setPanelForm(JPanel panelForm) {
        this.panelForm = panelForm;
    }
    /**
     * Obtiene el botón para enviar respuesta.
     * @return JButton para enviar respuesta.
     */
    public JButton getBtnEnviar() {return btnEnviar;}
    /**
     * Establece el botón para enviar respuesta.
     * @param btnEnviar JButton para enviar respuesta.
     */
    public void setBtnEnviar(JButton btnEnviar) {this.btnEnviar = btnEnviar;}
    /**
     * Obtiene el botón para limpiar campos.
     * @return JButton para limpiar campos.
     */
    public JButton getBtnClean() {return btnClean;}
    /**
     * Establece el botón para limpiar campos.
     * @param btnClean JButton para limpiar campos.
     */
    public void setBtnClean(JButton btnClean) {this.btnClean = btnClean;}
    /**
     * Obtiene el campo de texto para la nueva contraseña.
     * @return JTextField de nueva contraseña.
     */
    public JTextField getTxtNuevaContra() {return txtNuevaContra;}
    /**
     * Establece el campo de texto para la nueva contraseña.
     * @param txtNuevaContra JTextField de nueva contraseña.
     */
    public void setTxtNuevaContra(JTextField txtNuevaContra) {this.txtNuevaContra = txtNuevaContra;}
    /**
     * Obtiene la etiqueta de nueva contraseña.
     * @return JLabel de nueva contraseña.
     */
    public JLabel getLblNuevaContra() {return lblNuevaContra;}
    /**
     * Establece la etiqueta de nueva contraseña.
     * @param lblNuevaContra JLabel de nueva contraseña.
     */
    public void setLblNuevaContra(JLabel lblNuevaContra) {this.lblNuevaContra = lblNuevaContra;}
    /**
     * Obtiene la etiqueta del título.
     * @return JLabel del título.
     */
    public JLabel getLblTitulo() {return lblTitulo;}
    /**
     * Establece la etiqueta del título.
     * @param lblTitulo JLabel del título.
     */
    public void setLblTitulo(JLabel lblTitulo) {this.lblTitulo = lblTitulo;}
    /**
     * Obtiene el ComboBox de preguntas.
     * @return JComboBox de preguntas.
     */
    public JComboBox getCbxPreguntas() {return cbxPreguntas;}
    /**
     * Establece el ComboBox de preguntas.
     * @param cbxPreguntas JComboBox de preguntas.
     */
    public void setCbxPreguntas(JComboBox cbxPreguntas) {this.cbxPreguntas = cbxPreguntas;}
    /**
     * Obtiene el campo de texto para la respuesta de seguridad.
     * @return JTextField de respuesta de seguridad.
     */
    public JTextField getTxtRespuestaSeguidad() {return txtRespuestaSeguidad;}
    /**
     * Establece el campo de texto para la respuesta de seguridad.
     * @param txtRespuestaSeguidad JTextField de respuesta de seguridad.
     */
    public void setTxtRespuestaSeguidad(JTextField txtRespuestaSeguidad) {this.txtRespuestaSeguidad = txtRespuestaSeguidad;}
    /**
     * Obtiene la etiqueta de pregunta.
     * @return JLabel de pregunta.
     */
    public JLabel getLblQuestion() {return lblQuestion;}
    /**
     * Establece la etiqueta de pregunta.
     * @param lblQuestion JLabel de pregunta.
     */
    public void setLblQuestion(JLabel lblQuestion) {this.lblQuestion = lblQuestion;}
    /**
     * Obtiene el botón para pasar a la siguiente pregunta.
     * @return JButton para siguiente pregunta.
     */
    public JButton getBtnsiguientePregunta() {return btnsiguientePregunta;}
    /**
     * Establece el botón para pasar a la siguiente pregunta.
     * @param btnsiguientePregunta JButton para siguiente pregunta.
     */
    public void setBtnsiguientePregunta(JButton btnsiguientePregunta) {this.btnsiguientePregunta = btnsiguientePregunta;}
    /**
     * Obtiene el campo de texto para comparar respuesta.
     * @return JTextField para comparar respuesta.
     */
    public JTextField getTxtRespuestComparar() {return txtRespuestComparar;}
    /**
     * Establece el campo de texto para comparar respuesta.
     * @param txtRespuestComparar JTextField para comparar respuesta.
     */
    public void setTxtRespuestComparar(JTextField txtRespuestComparar) {this.txtRespuestComparar = txtRespuestComparar;}
    /**
     * Obtiene la etiqueta de pregunta.
     * @return JLabel de pregunta.
     */
    public JLabel getLblPregunta() {return lblPregunta;}
    /**
     * Establece la etiqueta de pregunta.
     * @param lblPregunta JLabel de pregunta.
     */
    public void setLblPregunta(JLabel lblPregunta) {this.lblPregunta = lblPregunta;}
    /**
     * Obtiene el manejador de internacionalización.
     * @return MensajeInternacionalizacionHandler.
     */
    public MensajeInternacionalizacionHandler getI18n() {return i18n;}
    /**
     * Establece el manejador de internacionalización.
     * @param i18n MensajeInternacionalizacionHandler.
     */
    public void setI18n(MensajeInternacionalizacionHandler i18n) {this.i18n = i18n;}
}
