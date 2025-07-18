package ec.edu.ups.poo.vista.inicio;

import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.util.TipoIcono;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Vista de inicio de sesión del sistema.
 * Permite al usuario autenticarse, seleccionar idioma, tipo de almacenamiento y gestionar la ubicación de los datos.
 */
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
    private JLabel lblUbicacionGuardar;
    private JComboBox cbxUbicacionGuardar;
    private JButton btnUbicacion;
    private JLabel lblRuta;
    private JTextField txtRuta;
    private MensajeInternacionalizacionHandler i18n;
    private String look;


    /**
     * Constructor de LogInView.
     * Inicializa la vista con el manejador de internacionalización, configura la interfaz y aplica idioma e iconos.
     *
     * @param i18n Manejador de internacionalización de mensajes.
     */
    public LogInView(MensajeInternacionalizacionHandler i18n) {
        this.i18n = i18n;
        setContentPane(panelAll);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 300);
        setLocationRelativeTo(null);

        aplicarIdioma();
        aplicarIconos();
        actualizarOpcionesGuardado();
    }

    /**
     * Configura el renderizado del ComboBox de ubicación de guardado.
     * No recibe parámetros ni retorna valores.
     */
    public void ventanaBrowser() {
//        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//
//            UIManager.put("Button.font", UIManager.get("SystemFont"));
//            UIManager.put("Label.font", UIManager.get("SystemFont"));
//            UIManager.put("TextField.font", UIManager.get("SystemFont"));
//            UIManager.put("ComboBox.font", UIManager.get("SystemFont"));
//        } catch (Exception ex) {
//            Logger.getLogger(LogInView.class.getName()).log(Level.SEVERE, "Error al configurar Look", ex);
//        }

        cbxUbicacionGuardar.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value != null) {
                    setText(value.toString());
                }
                return this;
            }
        });
    }

    /**
     * Abre un selector de directorios para elegir la ubicación de guardado de los datos.
     * Actualiza el campo de ruta con la selección del usuario.
     * No recibe parámetros ni retorna valores.
     */
    public void seleccionarDirectorio() {
        JFileChooser fileChooser = new JFileChooser();

        String userHome = System.getProperty("user.home");
        File desktopDir = new File(userHome, "Desktop");
        fileChooser.setCurrentDirectory(desktopDir);

        fileChooser.setDialogTitle(i18n.get("select.directory"));
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);

        int resultado = fileChooser.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File directorioSeleccionado = fileChooser.getSelectedFile();
            String ruta = directorioSeleccionado.getAbsolutePath();
            txtRuta.setText(ruta);
        }
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
    public int mostrarMensajeAlert(String mensaje, String titulo, int tipo) {
        Object[] botones = {i18n.get("mensaje.confirmacion"), i18n.get("mensaje.cancelacion")};
        return JOptionPane.showOptionDialog(
                this, mensaje, titulo,
                JOptionPane.DEFAULT_OPTION, tipo,
                null, botones, botones[0]);
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
     * Actualiza las opciones del ComboBox de ubicación de guardado según el tipo seleccionado.
     * No recibe parámetros ni retorna valores.
     */
    public void actualizarOpcionesGuardado() {
        int selected = cbxUbicacionGuardar.getSelectedIndex();

        cbxUbicacionGuardar.removeAllItems();
        cbxUbicacionGuardar.addItem(i18n.get("dao.memoria"));
        cbxUbicacionGuardar.addItem(i18n.get("archivo.texto"));
        cbxUbicacionGuardar.addItem(i18n.get("archivo.binario"));

        if (selected >= 0 && selected < cbxUbicacionGuardar.getItemCount()) {
            cbxUbicacionGuardar.setSelectedIndex(selected);
        } else {
            cbxUbicacionGuardar.setSelectedIndex(0);
        }
    }


    /**
     * Aplica los textos traducidos a todos los componentes de la vista según el idioma actual.
     * No recibe parámetros ni retorna valores.
     */
    public void aplicarIdioma() {
        setTitle(i18n.get("login.title"));
        lblTitulo.setText(i18n.get("login.lblTitulo"));
        lblIdioma.setText(i18n.get("login.lblIdioma"));
        lblUsuario.setText(i18n.get("login.lblUsuario"));
        lblContrasena.setText(i18n.get("login.lblContrasena"));
        lblUbicacionGuardar.setText(i18n.get("login.lblUbicacionGuardar"));
        lblRuta.setText(i18n.get("archivo.ruta"));
        btnUbicacion.setText(i18n.get("login.btnUbicacion"));
        btnInicioSesion.setText(i18n.get("login.btnInicioSesion"));
        btnSalir.setText(i18n.get("login.btnSalir"));
        btnRegistro.setText(i18n.get("login.btnRegistro"));
        btnRecuContra.setText(i18n.get("login.btnRecuContra"));
        actualizarOpcionesIdioma();
        actualizarOpcionesGuardado();
    }

    /**
     * Aplica los iconos correspondientes a los botones de la vista.
     * No recibe parámetros ni retorna valores.
     */
    public void aplicarIconos() {
        setIconImage(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.POO_LOGO).getImage());

        btnUbicacion.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.GUARDAR));
        btnInicioSesion.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.BLOGIN));
        btnSalir.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.X));
        btnRegistro.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.REGISTRO_USUARIO));
        btnRecuContra.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.RECUPERAR_CONTRASENA));
    }

    // Métodos getter y setter para los componentes de la vista.
    /**
     * Obtiene el campo de texto de la ruta.
     * @return JTextField de la ruta.
     */
    public JTextField getTxtRuta() {return txtRuta;}
    /**
     * Establece el campo de texto de la ruta.
     * @param txtRuta JTextField de la ruta.
     */
    public void setTxtRuta(JTextField txtRuta) {this.txtRuta = txtRuta;}
    /**
     * Obtiene la etiqueta de la ruta.
     * @return JLabel de la ruta.
     */
    public JLabel getLblRuta() {return lblRuta;}
    /**
     * Establece la etiqueta de la ruta.
     * @param lblRuta JLabel de la ruta.
     */
    public void setLblRuta(JLabel lblRuta) {this.lblRuta = lblRuta;}
    /**
     * Obtiene el botón para seleccionar ubicación.
     * @return JButton de ubicación.
     */
    public JButton getBtnUbicacion() {return btnUbicacion;}
    /**
     * Establece el botón para seleccionar ubicación.
     * @param btnUbicacion JButton de ubicación.
     */
    public void setBtnUbicacion(JButton btnUbicacion) {this.btnUbicacion = btnUbicacion;}
    /**
     * Obtiene la etiqueta de ubicación de guardado.
     * @return JLabel de ubicación de guardado.
     */
    public JLabel getLblUbicacionGuardar() {return lblUbicacionGuardar;}
    /**
     * Establece la etiqueta de ubicación de guardado.
     * @param lblUbicacionGuardar JLabel de ubicación de guardado.
     */
    public void setLblUbicacionGuardar(JLabel lblUbicacionGuardar) {this.lblUbicacionGuardar = lblUbicacionGuardar;}
    /**
     * Obtiene el ComboBox de ubicación de guardado.
     * @return JComboBox de ubicación de guardado.
     */
    public JComboBox getCbxUbicacionGuardar() {return cbxUbicacionGuardar;}
    /**
     * Establece el ComboBox de ubicación de guardado.
     * @param cbxUbicacionGuardar JComboBox de ubicación de guardado.
     */
    public void setCbxUbicacionGuardar(JComboBox cbxUbicacionGuardar) {this.cbxUbicacionGuardar = cbxUbicacionGuardar;}
    /**
     * Obtiene el botón para recuperar contraseña.
     * @return JButton de recuperación de contraseña.
     */
    public JButton getBtnRecuContra() {
        return btnRecuContra;
    }
    /**
     * Establece el botón para recuperar contraseña.
     * @param btnRecuContra JButton de recuperación de contraseña.
     */
    public void setBtnRecuContra(JButton btnRecuContra) {
        this.btnRecuContra = btnRecuContra;
    }
    /**
     * Obtiene el campo de texto para el nombre de usuario.
     * @return JTextField de nombre de usuario.
     */
    public JTextField getTxtUserName() {return txtUserName;}
    /**
     * Establece el campo de texto para el nombre de usuario.
     * @param txtUserName JTextField de nombre de usuario.
     */
    public void setTxtUserName(JTextField txtUserName) {
        this.txtUserName = txtUserName;
    }
    /**
     * Obtiene el campo de contraseña.
     * @return JPasswordField de contraseña.
     */
    public JPasswordField getTxtContrasena() {
        return txtContrasena;
    }
    /**
     * Establece el campo de contraseña.
     * @param txtContrasena JPasswordField de contraseña.
     */
    public void setTxtContrasena(JPasswordField txtContrasena) {
        this.txtContrasena = txtContrasena;
    }
    /**
     * Obtiene el botón de inicio de sesión.
     * @return JButton de inicio de sesión.
     */
    public JButton getBtnLogIn() {
        return btnInicioSesion;
    }
    /**
     * Establece el botón de inicio de sesión.
     * @param btnLogIn JButton de inicio de sesión.
     */
    public void setBtnLogIn(JButton btnLogIn) {
        this.btnInicioSesion = btnLogIn;
    }
    /**
     * Obtiene el botón de salir.
     * @return JButton de salir.
     */
    public JButton getBtnExit() {
        return btnSalir;
    }
    /**
     * Establece el botón de salir.
     * @param btnExit JButton de salir.
     */
    public void setBtnExit(JButton btnExit) {
        this.btnSalir = btnExit;
    }
    /**
     * Obtiene el botón de registro.
     * @return JButton de registro.
     */
    public JButton getBtnRegister() {
        return btnRegistro;
    }
    /**
     * Establece el botón de registro.
     * @param btnRegister JButton de registro.
     */
    public void setBtnRegister(JButton btnRegister) {
        this.btnRegistro = btnRegister;
    }
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
     * Obtiene el panel final.
     * @return JPanel final.
     */
    public JPanel getPanelFinal() {
        return panelFinal;
    }
    /**
     * Establece el panel final.
     * @param panelFinal JPanel final.
     */
    public void setPanelFinal(JPanel panelFinal) {
        this.panelFinal = panelFinal;
    }
    /**
     * Obtiene el panel central.
     * @return JPanel central.
     */
    public JPanel getPanelCentro() {
        return panelCentro;
    }
    /**
     * Establece el panel central.
     * @param panelCentro JPanel central.
     */
    public void setPanelCentro(JPanel panelCentro) {
        this.panelCentro = panelCentro;
    }
    /**
     * Obtiene el panel superior.
     * @return JPanel superior.
     */
    public JPanel getPanelArriba() {
        return panelArriba;
    }
    /**
     * Establece el panel superior.
     * @param panelArriba JPanel superior.
     */
    public void setPanelArriba(JPanel panelArriba) {
        this.panelArriba = panelArriba;
    }
    /**
     * Obtiene la etiqueta de nombre de usuario.
     * @return JLabel de nombre de usuario.
     */
    public JLabel getLblUsername() {
        return lblUsuario;
    }
    /**
     * Establece la etiqueta de nombre de usuario.
     * @param lblUsername JLabel de nombre de usuario.
     */
    public void setLblUsername(JLabel lblUsername) {
        this.lblUsuario = lblUsername;
    }
    /**
     * Obtiene la etiqueta de contraseña.
     * @return JLabel de contraseña.
     */
    public JLabel getLblContrasena() {
        return lblContrasena;
    }
    /**
     * Establece la etiqueta de contraseña.
     * @param lblContrasena JLabel de contraseña.
     */
    public void setLblContrasena(JLabel lblContrasena) {
        this.lblContrasena = lblContrasena;
    }
    /**
     * Obtiene la etiqueta del título.
     * @return JLabel del título.
     */
    public JLabel getTxtSettingsProduc() {
        return lblTitulo;
    }
    /**
     * Establece la etiqueta del título.
     * @param txtSettingsProduc JLabel del título.
     */
    public void setTxtSettingsProduc(JLabel txtSettingsProduc) {
        this.lblTitulo = txtSettingsProduc;
    }
    /**
     * Obtiene el ComboBox de idioma.
     * @return JComboBox de idioma.
     */
    public JComboBox getCbxIdioma() {return cbxIdioma;}
    /**
     * Establece el ComboBox de idioma.
     * @param cbxIdioma JComboBox de idioma.
     */
    public void setCbxIdioma(JComboBox cbxIdioma) {
        this.cbxIdioma = cbxIdioma;
    }
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

}