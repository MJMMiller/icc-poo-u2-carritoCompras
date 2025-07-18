package ec.edu.ups.poo.vista.inicio;

import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.util.TipoIcono;

import javax.swing.*;
import java.util.Calendar;
import java.util.Date;

/**
 * Vista de registro de usuario.
 * Permite al usuario ingresar sus datos personales, credenciales y respuestas a preguntas de seguridad,
 * así como seleccionar idioma y fecha de nacimiento.
 */
public class RegisterView extends JFrame {
    private JPanel panelAll;
    private JPanel panelSuperior;
    private JLabel lblTitulo;
    private JPanel panelCentral;
    private JTextField txtCedula;
    private JTextField txtContrasena;
    private JButton btnRegistro;
    private JButton btnClean;
    private JButton btnSalir;
    private JPanel panelInferior;
    private JLabel lblCedula;
    private JLabel lblContrasena;
    private JPanel panelForm;
    private JLabel lblPregunta1;
    private JTextField txtPregunta1;
    private JTextField txtPregunta2;
    private JTextField txtPregunta3;
    private JLabel lblPregunta2;
    private JLabel lblPregunta3;
    private JTextField txtNombreCompleto;
    private JLabel lblNombreCompleto;
    private JLabel lblFechaNacimiento;
    private JComboBox cbxDia;
    private JComboBox cbxMes;
    private JComboBox cbxAnio;
    private JTextField txtCorreo;
    private JLabel lblCorreo;
    private JTextField txtTelefono;
    private JLabel lblTelefono;
    private JLabel lblIdioma;
    private JComboBox cbxIdioma;
    private MensajeInternacionalizacionHandler i18n;

    /**
     * Constructor de RegisterView.
     * Inicializa la vista con el manejador de internacionalización, configura la interfaz y aplica idioma e iconos.
     *
     * @param i18n Manejador de internacionalización de mensajes.
     */
    public RegisterView(MensajeInternacionalizacionHandler i18n) {
        this.i18n = i18n;
        setContentPane(panelAll);
        setSize(400, 500);
        setLocationRelativeTo(null);

        aplicarIdioma();
        aplicaIcono();
        inicializarCombosFechaNacimiento();
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
     * Aplica los iconos correspondientes a los botones de la vista.
     * No recibe parámetros ni retorna valores.
     */
    public void aplicaIcono() {
        setIconImage(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.CREAR_USUARIO).getImage());
        btnRegistro.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.GUARDAR));
        btnClean.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.CLEAN));
        btnSalir.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.X));
    }

    /**
     * Aplica los textos traducidos a todos los componentes de la vista según el idioma actual.
     * No recibe parámetros ni retorna valores.
     */
    public void aplicarIdioma() {
        setTitle(i18n.get("register.title"));
        lblIdioma.setText(i18n.get("login.lblIdioma"));
        lblTitulo.setText(i18n.get("register.title"));
        lblCedula.setText(i18n.get("register.lblUsuario"));
        lblContrasena.setText(i18n.get("register.lblContrasena"));
        lblNombreCompleto.setText(i18n.get("register.lblNombreCompleto"));
        lblFechaNacimiento.setText(i18n.get("register.lblFechaNacimiento"));
        lblCorreo.setText(i18n.get("register.lblCorreo"));
        lblTelefono.setText(i18n.get("register.lblTelefono"));
        lblTelefono.setText(i18n.get("register.lblTelefono"));
        lblPregunta1.setText(i18n.get("register.lblPregunta1"));
        lblPregunta2.setText(i18n.get("register.lblPregunta2"));
        lblPregunta3.setText(i18n.get("register.lblPregunta3"));
        btnRegistro.setText(i18n.get("register.btnRegistro"));
        btnClean.setText(i18n.get("register.btnClean"));
        btnSalir.setText(i18n.get("register.btnSalir"));
        actualizarOpcionesIdioma();

    }

    /**
     * Obtiene la fecha de nacimiento seleccionada por el usuario en los ComboBox.
     *
     * @return Fecha de nacimiento como Date, o null si hay error en la selección.
     */
    public Date getFechaNacimiento() {
        try {
            int dia = Integer.parseInt(cbxDia.getSelectedItem().toString());
            int mes = Integer.parseInt(cbxMes.getSelectedItem().toString()) - 1;
            int anio = Integer.parseInt(cbxAnio.getSelectedItem().toString());
            Calendar calendar = Calendar.getInstance();
            calendar.set(anio, mes, dia, 0, 0, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            return calendar.getTime();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Inicializa los ComboBox de día, mes y año para la fecha de nacimiento.
     * No recibe parámetros ni retorna valores.
     */
    private void inicializarCombosFechaNacimiento() {

        cbxDia.removeAllItems();
        for (int i = 1; i <= 31; i++) {
            cbxDia.addItem(String.valueOf(i));
        }

        cbxMes.removeAllItems();
        for (int i = 1; i <= 12; i++) {
            cbxMes.addItem(String.valueOf(i));
        }

        cbxAnio.removeAllItems();
        int anioActual = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = anioActual; i >= 1900; i--) {
            cbxAnio.addItem(String.valueOf(i));
        }
    }

    // Métodos getter y setter para los componentes de la vista.

    /**
     * Obtiene el ComboBox de idioma.
     * @return JComboBox de idioma.
     */
    public JComboBox getCbxIdioma() { return cbxIdioma; }
    /**
     * Establece el ComboBox de idioma.
     * @param cbxIdioma JComboBox de idioma.
     */
    public void setCbxIdioma(JComboBox cbxIdioma) { this.cbxIdioma = cbxIdioma; }
    /**
     * Obtiene el panel principal.
     * @return JPanel principal.
     */
    public JPanel getPanelAll() { return panelAll; }
    /**
     * Establece el panel principal.
     * @param panelAll JPanel principal.
     */
    public void setPanelAll(JPanel panelAll) { this.panelAll = panelAll; }
    /**
     * Obtiene el panel superior.
     * @return JPanel superior.
     */
    public JPanel getPanelSuperior() { return panelSuperior; }
    /**
     * Establece el panel superior.
     * @param panelSuperior JPanel superior.
     */
    public void setPanelSuperior(JPanel panelSuperior) { this.panelSuperior = panelSuperior; }
    /**
     * Obtiene la etiqueta del título.
     * @return JLabel del título.
     */
    public JLabel getLblTitulo() { return lblTitulo; }
    /**
     * Establece la etiqueta del título.
     * @param lblTitulo JLabel del título.
     */
    public void setLblTitulo(JLabel lblTitulo) { this.lblTitulo = lblTitulo; }
    /**
     * Obtiene el panel central.
     * @return JPanel central.
     */
    public JPanel getPanelCentral() { return panelCentral; }
    /**
     * Establece el panel central.
     * @param panelCentral JPanel central.
     */
    public void setPanelCentral(JPanel panelCentral) { this.panelCentral = panelCentral; }
    /**
     * Obtiene el campo de texto para la cédula.
     * @return JTextField de cédula.
     */
    public JTextField getTxtUsuario() { return txtCedula; }
    /**
     * Establece el campo de texto para la cédula.
     * @param txtUsuario JTextField de cédula.
     */
    public void setTxtUsuario(JTextField txtUsuario) { this.txtCedula = txtUsuario; }
    /**
     * Obtiene el campo de texto para la contraseña.
     * @return JTextField de contraseña.
     */
    public JTextField getTxtContrasena() { return txtContrasena; }
    /**
     * Establece el campo de texto para la contraseña.
     * @param txtContrasena JTextField de contraseña.
     */
    public void setTxtContrasena(JTextField txtContrasena) { this.txtContrasena = txtContrasena; }
    /**
     * Obtiene el botón de registro.
     * @return JButton de registro.
     */
    public JButton getBtnRegistro() { return btnRegistro; }
    /**
     * Establece el botón de registro.
     * @param btnRegistro JButton de registro.
     */
    public void setBtnRegistro(JButton btnRegistro) { this.btnRegistro = btnRegistro; }
    /**
     * Obtiene el botón para limpiar campos.
     * @return JButton para limpiar campos.
     */
    public JButton getBtnClean() { return btnClean; }
    /**
     * Establece el botón para limpiar campos.
     * @param btnClean JButton para limpiar campos.
     */
    public void setBtnClean(JButton btnClean) { this.btnClean = btnClean; }
    /**
     * Obtiene el botón para salir.
     * @return JButton para salir.
     */
    public JButton getBtnSalir() { return btnSalir; }
    /**
     * Establece el botón para salir.
     * @param btnSalir JButton para salir.
     */
    public void setBtnSalir(JButton btnSalir) { this.btnSalir = btnSalir; }
    /**
     * Obtiene el panel inferior.
     * @return JPanel inferior.
     */
    public JPanel getPanelInferior() { return panelInferior; }
    /**
     * Establece el panel inferior.
     * @param panelInferior JPanel inferior.
     */
    public void setPanelInferior(JPanel panelInferior) { this.panelInferior = panelInferior; }
    /**
     * Obtiene la etiqueta de usuario (cédula).
     * @return JLabel de usuario.
     */
    public JLabel getLblUsuario() { return lblCedula; }
    /**
     * Establece la etiqueta de usuario (cédula).
     * @param lblUsuario JLabel de usuario.
     */
    public void setLblUsuario(JLabel lblUsuario) { this.lblCedula = lblUsuario; }
    /**
     * Obtiene la etiqueta de contraseña.
     * @return JLabel de contraseña.
     */
    public JLabel getLblContrasena() { return lblContrasena; }
    /**
     * Establece la etiqueta de contraseña.
     * @param lblContrasena JLabel de contraseña.
     */
    public void setLblContrasena(JLabel lblContrasena) { this.lblContrasena = lblContrasena; }
    /**
     * Obtiene el panel del formulario.
     * @return JPanel del formulario.
     */
    public JPanel getPanelForm() { return panelForm; }
    /**
     * Establece el panel del formulario.
     * @param panelForm JPanel del formulario.
     */
    public void setPanelForm(JPanel panelForm) { this.panelForm = panelForm; }
    /**
     * Obtiene la etiqueta de la primera pregunta de seguridad.
     * @return JLabel de la primera pregunta.
     */
    public JLabel getLblPregunta1() { return lblPregunta1; }
    /**
     * Establece la etiqueta de la primera pregunta de seguridad.
     * @param lblPregunta1 JLabel de la primera pregunta.
     */
    public void setLblPregunta1(JLabel lblPregunta1) { this.lblPregunta1 = lblPregunta1; }
    /**
     * Obtiene el campo de texto para la respuesta de la primera pregunta.
     * @return JTextField de la primera pregunta.
     */
    public JTextField getTxtPregunta1() { return txtPregunta1; }
    /**
     * Establece el campo de texto para la respuesta de la primera pregunta.
     * @param txtPregunta1 JTextField de la primera pregunta.
     */
    public void setTxtPregunta1(JTextField txtPregunta1) { this.txtPregunta1 = txtPregunta1; }
    /**
     * Obtiene el campo de texto para la respuesta de la segunda pregunta.
     * @return JTextField de la segunda pregunta.
     */
    public JTextField getTxtPregunta2() { return txtPregunta2; }
    /**
     * Establece el campo de texto para la respuesta de la segunda pregunta.
     * @param txtPregunta2 JTextField de la segunda pregunta.
     */
    public void setTxtPregunta2(JTextField txtPregunta2) { this.txtPregunta2 = txtPregunta2; }
    /**
     * Obtiene el campo de texto para la respuesta de la tercera pregunta.
     * @return JTextField de la tercera pregunta.
     */
    public JTextField getTxtPregunta3() { return txtPregunta3; }
    /**
     * Establece el campo de texto para la respuesta de la tercera pregunta.
     * @param txtPregunta3 JTextField de la tercera pregunta.
     */
    public void setTxtPregunta3(JTextField txtPregunta3) { this.txtPregunta3 = txtPregunta3; }
    /**
     * Obtiene la etiqueta de la segunda pregunta de seguridad.
     * @return JLabel de la segunda pregunta.
     */
    public JLabel getLblPregunta2() { return lblPregunta2; }
    /**
     * Establece la etiqueta de la segunda pregunta de seguridad.
     * @param lblPregunta2 JLabel de la segunda pregunta.
     */
    public void setLblPregunta2(JLabel lblPregunta2) { this.lblPregunta2 = lblPregunta2; }
    /**
     * Obtiene la etiqueta de la tercera pregunta de seguridad.
     * @return JLabel de la tercera pregunta.
     */
    public JLabel getLblPregunta3() { return lblPregunta3; }
    /**
     * Establece la etiqueta de la tercera pregunta de seguridad.
     * @param lblPregunta3 JLabel de la tercera pregunta.
     */
    public void setLblPregunta3(JLabel lblPregunta3) { this.lblPregunta3 = lblPregunta3; }
    /**
     * Obtiene el campo de texto para el nombre completo.
     * @return JTextField de nombre completo.
     */
    public JTextField getTxtNombreCompleto() { return txtNombreCompleto; }
    /**
     * Establece el campo de texto para el nombre completo.
     * @param txtNombreCompleto JTextField de nombre completo.
     */
    public void setTxtNombreCompleto(JTextField txtNombreCompleto) { this.txtNombreCompleto = txtNombreCompleto; }
    /**
     * Obtiene la etiqueta de nombre completo.
     * @return JLabel de nombre completo.
     */
    public JLabel getLblNombreCompleto() { return lblNombreCompleto; }
    /**
     * Establece la etiqueta de nombre completo.
     * @param lblNombreCompleto JLabel de nombre completo.
     */
    public void setLblNombreCompleto(JLabel lblNombreCompleto) { this.lblNombreCompleto = lblNombreCompleto; }
    /**
     * Obtiene la etiqueta de fecha de nacimiento.
     * @return JLabel de fecha de nacimiento.
     */
    public JLabel getLblFechaNacimiento() { return lblFechaNacimiento; }
    /**
     * Establece la etiqueta de fecha de nacimiento.
     * @param lblFechaNacimiento JLabel de fecha de nacimiento.
     */
    public void setLblFechaNacimiento(JLabel lblFechaNacimiento) { this.lblFechaNacimiento = lblFechaNacimiento; }
    /**
     * Obtiene el ComboBox de día.
     * @return JComboBox de día.
     */
    public JComboBox getCbxDia() { return cbxDia; }
    /**
     * Establece el ComboBox de día.
     * @param cbxDia JComboBox de día.
     */
    public void setCbxDia(JComboBox cbxDia) { this.cbxDia = cbxDia; }
    /**
     * Obtiene el ComboBox de mes.
     * @return JComboBox de mes.
     */
    public JComboBox getCbxMes() { return cbxMes; }
    /**
     * Establece el ComboBox de mes.
     * @param cbxMes JComboBox de mes.
     */
    public void setCbxMes(JComboBox cbxMes) { this.cbxMes = cbxMes; }
    /**
     * Obtiene el ComboBox de año.
     * @return JComboBox de año.
     */
    public JComboBox getCbxAnio() { return cbxAnio; }
    /**
     * Establece el ComboBox de año.
     * @param cbxAnio JComboBox de año.
     */
    public void setCbxAnio(JComboBox cbxAnio) { this.cbxAnio = cbxAnio; }
    /**
     * Obtiene el campo de texto para el correo electrónico.
     * @return JTextField de correo.
     */
    public JTextField getTxtCorreo() { return txtCorreo; }
    /**
     * Establece el campo de texto para el correo electrónico.
     * @param txtCorreo JTextField de correo.
     */
    public void setTxtCorreo(JTextField txtCorreo) { this.txtCorreo = txtCorreo; }
    /**
     * Obtiene la etiqueta de correo electrónico.
     * @return JLabel de correo.
     */
    public JLabel getLblCorreo() { return lblCorreo; }
    /**
     * Establece la etiqueta de correo electrónico.
     * @param lblCorreo JLabel de correo.
     */
    public void setLblCorreo(JLabel lblCorreo) { this.lblCorreo = lblCorreo; }
    /**
     * Obtiene el campo de texto para el teléfono.
     * @return JTextField de teléfono.
     */
    public JTextField getTxtTelefono() { return txtTelefono; }
    /**
     * Establece el campo de texto para el teléfono.
     * @param txtTelefono JTextField de teléfono.
     */
    public void setTxtTelefono(JTextField txtTelefono) { this.txtTelefono = txtTelefono; }
    /**
     * Obtiene la etiqueta de teléfono.
     * @return JLabel de teléfono.
     */
    public JLabel getLblTelefono() { return lblTelefono; }
    /**
     * Establece la etiqueta de teléfono.
     * @param lblTelefono JLabel de teléfono.
     */
    public void setLblTelefono(JLabel lblTelefono) { this.lblTelefono = lblTelefono; }
    /**
     * Obtiene el manejador de internacionalización.
     * @return MensajeInternacionalizacionHandler.
     */
    public MensajeInternacionalizacionHandler getI18n() { return i18n; }
    /**
     * Establece el manejador de internacionalización.
     * @param i18n MensajeInternacionalizacionHandler.
     */
    public void setI18n(MensajeInternacionalizacionHandler i18n) { this.i18n = i18n; }
}
