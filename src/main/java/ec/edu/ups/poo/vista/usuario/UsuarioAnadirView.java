package ec.edu.ups.poo.vista.usuario;

import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.util.TipoIcono;

import javax.swing.*;
import java.util.Calendar;
import java.util.Date;

/**
 * Vista interna para añadir usuarios al sistema.
 * Permite ingresar datos personales, credenciales, rol, fecha de nacimiento, teléfono y correo.
 * Aplica internacionalización e iconos a los componentes de la interfaz.
 */
public class UsuarioAnadirView extends JInternalFrame {
    private JPanel panelAll;
    private JPanel panelSuperior;
    private JLabel lblTitulo;
    private JPanel panelCenter;
    private JTextField txtCedula;
    private JLabel lblCedula;
    private JTextField txtContrasena;
    private JLabel lblContrasena;
    private JComboBox cbxRol;
    private JButton btnRegistrar;
    private JButton btnClean;
    private JPanel panelInferior;
    private JLabel lblRol;
    private JTextField txtNombreCompleto;
    private JLabel lblNombreCompleto;
    private JLabel lblFechaNacimiento;
    private JComboBox cbxDia;
    private JComboBox cbxMes;
    private JComboBox cbxAnio;
    private JLabel lblTelefono;
    private JTextField txtTelefono;
    private JTextField txtCorreo;
    private JLabel lblCorreo;
    private MensajeInternacionalizacionHandler i18n;

    /**
     * Constructor de UsuarioAnadirView.
     * Inicializa la vista con el manejador de internacionalización, configura la interfaz y aplica idioma e iconos.
     *
     * @param i18n Manejador de internacionalización de mensajes.
     */
    public UsuarioAnadirView(MensajeInternacionalizacionHandler i18n) {
        this.i18n = i18n;
        setContentPane(panelAll);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 500);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        aplicarIdioma();
        aplicarIconos();
        inicializarCombosFechaNacimiento();
    }

    /**
     * Aplica los iconos correspondientes a los botones y al frame de la vista.
     * No recibe parámetros ni retorna valores.
     */
    public void aplicarIconos() {
        setFrameIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.USUARIO));
        btnClean.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.CLEAN));
        btnRegistrar.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.GUARDAR));
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
    public void aplicarIdioma() {
        setTitle(i18n.get("usuario.anadir.titulo"));
        lblTitulo.setText(i18n.get("usuario.anadir.lbl.titulo"));
        lblCedula.setText(i18n.get("usuario.anadir.lbl.usuario"));
        lblContrasena.setText(i18n.get("usuario.anadir.lbl.contrasena"));
        lblRol.setText(i18n.get("usuario.anadir.lbl.rol"));
        btnRegistrar.setText(i18n.get("usuario.anadir.btn.registrar"));
        btnClean.setText(i18n.get("usuario.anadir.btn.limpiar"));
        lblNombreCompleto.setText(i18n.get("usuario.anadir.lbl.nombre.completo"));
        lblFechaNacimiento.setText(i18n.get("usuario.anadir.lbl.fecha.nacimiento"));
        lblTelefono.setText(i18n.get("usuario.anadir.lbl.telefono"));
        lblCorreo.setText(i18n.get("usuario.anadir.lbl.correo"));

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
     * Obtiene el panel principal de la vista.
     * @return JPanel principal.
     */
    public JPanel getPanelAll() { return panelAll; }
    /**
     * Establece el panel principal de la vista.
     * @param panelAll JPanel principal.
     */
    public void setPanelAll(JPanel panelAll) { this.panelAll = panelAll; }
    /**
     * Obtiene el panel superior de la vista.
     * @return JPanel superior.
     */
    public JPanel getPanelSuperior() { return panelSuperior; }
    /**
     * Establece el panel superior de la vista.
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
     * Obtiene el panel central de la vista.
     * @return JPanel central.
     */
    public JPanel getPanelCenter() { return panelCenter; }
    /**
     * Establece el panel central de la vista.
     * @param panelCenter JPanel central.
     */
    public void setPanelCenter(JPanel panelCenter) { this.panelCenter = panelCenter; }
    /**
     * Obtiene el campo de texto para la cédula del usuario.
     * @return JTextField de usuario.
     */
    public JTextField getTxtUsuario() { return txtCedula; }
    /**
     * Establece el campo de texto para la cédula del usuario.
     * @param txtUsuario JTextField de usuario.
     */
    public void setTxtUsuario(JTextField txtUsuario) { this.txtCedula = txtUsuario; }
    /**
     * Obtiene la etiqueta de usuario.
     * @return JLabel de usuario.
     */
    public JLabel getLblUsuario() { return lblCedula; }
    /**
     * Establece la etiqueta de usuario.
     * @param lblUsuario JLabel de usuario.
     */
    public void setLblUsuario(JLabel lblUsuario) { this.lblCedula = lblUsuario; }
    /**
     * Obtiene el campo de texto para la contraseña del usuario.
     * @return JTextField de contraseña.
     */
    public JTextField getTxtContrasena() { return txtContrasena; }
    /**
     * Establece el campo de texto para la contraseña del usuario.
     * @param txtContrasena JTextField de contraseña.
     */
    public void setTxtContrasena(JTextField txtContrasena) { this.txtContrasena = txtContrasena; }
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
     * Obtiene el ComboBox de roles.
     * @return JComboBox de roles.
     */
    public JComboBox getCbxRol() { return cbxRol; }
    /**
     * Establece el ComboBox de roles.
     * @param cbxRol JComboBox de roles.
     */
    public void setCbxRol(JComboBox cbxRol) { this.cbxRol = cbxRol; }
    /**
     * Obtiene el botón para registrar usuario.
     * @return JButton para registrar usuario.
     */
    public JButton getBtnRegistrar() { return btnRegistrar; }
    /**
     * Establece el botón para registrar usuario.
     * @param btnRegistrar JButton para registrar usuario.
     */
    public void setBtnRegistrar(JButton btnRegistrar) { this.btnRegistrar = btnRegistrar; }
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
     * Obtiene el panel inferior de la vista.
     * @return JPanel inferior.
     */
    public JPanel getPanelInferior() { return panelInferior; }
    /**
     * Establece el panel inferior de la vista.
     * @param panelInferior JPanel inferior.
     */
    public void setPanelInferior(JPanel panelInferior) { this.panelInferior = panelInferior; }
    /**
     * Obtiene la etiqueta de rol.
     * @return JLabel de rol.
     */
    public JLabel getLblRol() { return lblRol; }
    /**
     * Establece la etiqueta de rol.
     * @param lblRol JLabel de rol.
     */
    public void setLblRol(JLabel lblRol) { this.lblRol = lblRol; }
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
     * Obtiene el campo de texto para el correo electrónico.
     * @return JTextField de correo electrónico.
     */
    public JTextField getTxtCorreo() { return txtCorreo; }
    /**
     * Establece el campo de texto para el correo electrónico.
     * @param txtCorreo JTextField de correo electrónico.
     */
    public void setTxtCorreo(JTextField txtCorreo) { this.txtCorreo = txtCorreo; }
    /**
     * Obtiene la etiqueta de correo electrónico.
     * @return JLabel de correo electrónico.
     */
    public JLabel getLblCorreo() { return lblCorreo; }
    /**
     * Establece la etiqueta de correo electrónico.
     * @param lblCorreo JLabel de correo electrónico.
     */
    public void setLblCorreo(JLabel lblCorreo) { this.lblCorreo = lblCorreo; }
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
