package ec.edu.ups.poo.vista.usuario;

import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.util.TipoIcono;

import javax.swing.*;

/**
 * Vista interna para eliminar usuarios del sistema.
 * Permite buscar un usuario por cédula, mostrar sus datos y eliminarlo.
 * Aplica internacionalización e iconos a los componentes de la interfaz.
 */
public class UsuarioElimiarView extends JInternalFrame {
    private JPanel panelAll;
    private JLabel lblTitulo;
    private JPanel panelSuperioir;
    private JPanel panelCentral;
    private JTextField txtUsuario;
    private JTextField txtContrasena;
    private JButton btnEliminar;
    private JPanel panelInferioir;
    private JButton btnBuscar;
    private JLabel lblUsuario;
    private JLabel lblContrasena;
    private JLabel lblRol;
    private JTextField txtRol;
    private JLabel lblNombreCompleto;
    private JTextField txtNombreCompleto;
    private JLabel lblFechaNacimiento;
    private JTextField txtDia;
    private JTextField txtMes;
    private JTextField txtAnio;
    private JLabel lblTelefono;
    private JLabel lblCorreo;
    private JTextField txtTelefono;
    private JTextField txtCorreo;
    private MensajeInternacionalizacionHandler i18n;

    /**
     * Constructor de UsuarioElimiarView.
     * Inicializa la vista con el manejador de internacionalización, configura la interfaz y aplica idioma e iconos.
     *
     * @param i18n Manejador de internacionalización de mensajes.
     */
    public UsuarioElimiarView(MensajeInternacionalizacionHandler i18n) {
        this.i18n = i18n;
        setContentPane(panelAll);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 400);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        aplicarIdioma();
        aplicarIconos();
    }

    /**
     * Aplica los iconos correspondientes a los botones y al frame de la vista.
     * No recibe parámetros ni retorna valores.
     */
    public void aplicarIconos() {
        btnBuscar.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.BUSCAR));
        btnEliminar.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.ElIMINAR));
        setFrameIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.USUARIO));
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
        setTitle(i18n.get("usuario.listar.titulo"));
        lblTitulo.setText(i18n.get("usuario.eliminar.titulo"));
        lblUsuario.setText(i18n.get("usuario.eliminar.lbl.usuario"));
        lblContrasena.setText(i18n.get("usuario.eliminar.lbl.contrasena"));
        lblRol.setText(i18n.get("usuario.eliminar.lbl.rol"));
        btnBuscar.setText(i18n.get("usuario.eliminar.btn.buscar"));
        btnEliminar.setText(i18n.get("usuario.eliminar.btn.eliminar"));
        lblNombreCompleto.setText(i18n.get("usuario.eliminar.lbl.nombreCompleto"));
        lblFechaNacimiento.setText(i18n.get("usuario.eliminar.lbl.fechaNacimiento"));
        lblTelefono.setText(i18n.get("usuario.eliminar.lbl.telefono"));
        lblCorreo.setText(i18n.get("usuario.eliminar.lbl.correo"));

    }

    // Métodos getter y setter para los componentes de la vista.

    /**
     * Obtiene el panel principal de la vista.
     * @return JPanel principal.
     */
    public JPanel getPanelAll() {return panelAll;}
    /**
     * Establece el panel principal de la vista.
     * @param panelAll JPanel principal.
     */
    public void setPanelAll(JPanel panelAll) {this.panelAll = panelAll;}
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
     * Obtiene el panel superior de la vista.
     * @return JPanel superior.
     */
    public JPanel getPanelSuperioir() {return panelSuperioir;}
    /**
     * Establece el panel superior de la vista.
     * @param panelSuperioir JPanel superior.
     */
    public void setPanelSuperioir(JPanel panelSuperioir) {this.panelSuperioir = panelSuperioir;}
    /**
     * Obtiene el panel central de la vista.
     * @return JPanel central.
     */
    public JPanel getPanelCentral() {return panelCentral;}
    /**
     * Establece el panel central de la vista.
     * @param panelCentral JPanel central.
     */
    public void setPanelCentral(JPanel panelCentral) {this.panelCentral = panelCentral;}
    /**
     * Obtiene el campo de texto para la cédula del usuario.
     * @return JTextField de usuario.
     */
    public JTextField getTxtUsuario() {return txtUsuario;}
    /**
     * Establece el campo de texto para la cédula del usuario.
     * @param txtUsuario JTextField de usuario.
     */
    public void setTxtUsuario(JTextField txtUsuario) {this.txtUsuario = txtUsuario;}
    /**
     * Obtiene el campo de texto para la contraseña del usuario.
     * @return JTextField de contraseña.
     */
    public JTextField getTxtContrasena() {return txtContrasena;}
    /**
     * Establece el campo de texto para la contraseña del usuario.
     * @param txtContrasena JTextField de contraseña.
     */
    public void setTxtContrasena(JTextField txtContrasena) {this.txtContrasena = txtContrasena;}
    /**
     * Obtiene el botón para eliminar usuario.
     * @return JButton para eliminar usuario.
     */
    public JButton getBtnEliminar() {return btnEliminar;}
    /**
     * Establece el botón para eliminar usuario.
     * @param btnEliminar JButton para eliminar usuario.
     */
    public void setBtnEliminar(JButton btnEliminar) {this.btnEliminar = btnEliminar;}
    /**
     * Obtiene el panel inferior de la vista.
     * @return JPanel inferior.
     */
    public JPanel getPanelInferioir() {return panelInferioir;}
    /**
     * Establece el panel inferior de la vista.
     * @param panelInferioir JPanel inferior.
     */
    public void setPanelInferioir(JPanel panelInferioir) {this.panelInferioir = panelInferioir;}
    /**
     * Obtiene el botón para buscar usuario.
     * @return JButton para buscar usuario.
     */
    public JButton getBtnBuscar() {return btnBuscar;}
    /**
     * Establece el botón para buscar usuario.
     * @param btnBuscar JButton para buscar usuario.
     */
    public void setBtnBuscar(JButton btnBuscar) {this.btnBuscar = btnBuscar;}
    /**
     * Obtiene la etiqueta de usuario.
     * @return JLabel de usuario.
     */
    public JLabel getLblUsuario() {return lblUsuario;}
    /**
     * Establece la etiqueta de usuario.
     * @param lblUsuario JLabel de usuario.
     */
    public void setLblUsuario(JLabel lblUsuario) {this.lblUsuario = lblUsuario;}
    /**
     * Obtiene la etiqueta de contraseña.
     * @return JLabel de contraseña.
     */
    public JLabel getLblContrasena() {return lblContrasena;}
    /**
     * Establece la etiqueta de contraseña.
     * @param lblContrasena JLabel de contraseña.
     */
    public void setLblContrasena(JLabel lblContrasena) {this.lblContrasena = lblContrasena;}
    /**
     * Obtiene la etiqueta de rol.
     * @return JLabel de rol.
     */
    public JLabel getLblRol() {return lblRol;}
    /**
     * Establece la etiqueta de rol.
     * @param lblRol JLabel de rol.
     */
    public void setLblRol(JLabel lblRol) {this.lblRol = lblRol;}
    /**
     * Obtiene el campo de texto para el rol del usuario.
     * @return JTextField de rol.
     */
    public JTextField getTxtRol() {return txtRol;}
    /**
     * Establece el campo de texto para el rol del usuario.
     * @param txtRol JTextField de rol.
     */
    public void setTxtRol(JTextField txtRol) {this.txtRol = txtRol;}
    /**
     * Obtiene la etiqueta de nombre completo.
     * @return JLabel de nombre completo.
     */
    public JLabel getLblNombreCompleto() {return lblNombreCompleto;}
    /**
     * Establece la etiqueta de nombre completo.
     * @param lblNombreCompleto JLabel de nombre completo.
     */
    public void setLblNombreCompleto(JLabel lblNombreCompleto) {this.lblNombreCompleto = lblNombreCompleto;}
    /**
     * Obtiene el campo de texto para el nombre completo.
     * @return JTextField de nombre completo.
     */
    public JTextField getTxtNombreCompleto() {return txtNombreCompleto;}
    /**
     * Establece el campo de texto para el nombre completo.
     * @param txtNombreCompleto JTextField de nombre completo.
     */
    public void setTxtNombreCompleto(JTextField txtNombreCompleto) {this.txtNombreCompleto = txtNombreCompleto;}
    /**
     * Obtiene la etiqueta de fecha de nacimiento.
     * @return JLabel de fecha de nacimiento.
     */
    public JLabel getLblFechaNacimiento() {return lblFechaNacimiento;}
    /**
     * Establece la etiqueta de fecha de nacimiento.
     * @param lblFechaNacimiento JLabel de fecha de nacimiento.
     */
    public void setLblFechaNacimiento(JLabel lblFechaNacimiento) {this.lblFechaNacimiento = lblFechaNacimiento;}
    /**
     * Obtiene el campo de texto para el día de nacimiento.
     * @return JTextField de día.
     */
    public JTextField getTxtDia() {return txtDia;}
    /**
     * Establece el campo de texto para el día de nacimiento.
     * @param txtDia JTextField de día.
     */
    public void setTxtDia(JTextField txtDia) {this.txtDia = txtDia;}
    /**
     * Obtiene el campo de texto para el mes de nacimiento.
     * @return JTextField de mes.
     */
    public JTextField getTxtMes() {return txtMes;}
    /**
     * Establece el campo de texto para el mes de nacimiento.
     * @param txtMes JTextField de mes.
     */
    public void setTxtMes(JTextField txtMes) {this.txtMes = txtMes;}
    /**
     * Obtiene el campo de texto para el año de nacimiento.
     * @return JTextField de año.
     */
    public JTextField getTxtAnio() {return txtAnio;}
    /**
     * Establece el campo de texto para el año de nacimiento.
     * @param txtAnio JTextField de año.
     */
    public void setTxtAnio(JTextField txtAnio) {this.txtAnio = txtAnio;}
    /**
     * Obtiene la etiqueta de teléfono.
     * @return JLabel de teléfono.
     */
    public JLabel getLblTelefono() {return lblTelefono;}
    /**
     * Establece la etiqueta de teléfono.
     * @param lblTelefono JLabel de teléfono.
     */
    public void setLblTelefono(JLabel lblTelefono) {this.lblTelefono = lblTelefono;}
    /**
     * Obtiene la etiqueta de correo electrónico.
     * @return JLabel de correo electrónico.
     */
    public JLabel getLblCorreo() {return lblCorreo;}
    /**
     * Establece la etiqueta de correo electrónico.
     * @param lblCorreo JLabel de correo electrónico.
     */
    public void setLblCorreo(JLabel lblCorreo) {this.lblCorreo = lblCorreo;}
    /**
     * Obtiene el campo de texto para el teléfono.
     * @return JTextField de teléfono.
     */
    public JTextField getTxtTelefono() {return txtTelefono;}
    /**
     * Establece el campo de texto para el teléfono.
     * @param txtTelefono JTextField de teléfono.
     */
    public void setTxtTelefono(JTextField txtTelefono) {this.txtTelefono = txtTelefono;}
    /**
     * Obtiene el campo de texto para el correo electrónico.
     * @return JTextField de correo electrónico.
     */
    public JTextField getTxtCorreo() {return txtCorreo;}
    /**
     * Establece el campo de texto para el correo electrónico.
     * @param txtCorreo JTextField de correo electrónico.
     */
    public void setTxtCorreo(JTextField txtCorreo) {this.txtCorreo = txtCorreo;}
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
