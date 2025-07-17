package ec.edu.ups.poo.vista.inicio;

import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.util.TipoIcono;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public void aplicarIconos() {
        setIconImage(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.POO_LOGO).getImage());

        btnUbicacion.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.GUARDAR));
        btnInicioSesion.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.BLOGIN));
        btnSalir.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.X));
        btnRegistro.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.REGISTRO_USUARIO));
        btnRecuContra.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.RECUPERAR_CONTRASENA));
    }

    public JTextField getTxtRuta() {return txtRuta;}
    public void setTxtRuta(JTextField txtRuta) {this.txtRuta = txtRuta;}
    public JLabel getLblRuta() {return lblRuta;}
    public void setLblRuta(JLabel lblRuta) {this.lblRuta = lblRuta;}
    public JButton getBtnUbicacion() {return btnUbicacion;}
    public void setBtnUbicacion(JButton btnUbicacion) {this.btnUbicacion = btnUbicacion;}
    public JLabel getLblUbicacionGuardar() {return lblUbicacionGuardar;}
    public void setLblUbicacionGuardar(JLabel lblUbicacionGuardar) {this.lblUbicacionGuardar = lblUbicacionGuardar;}
    public JComboBox getCbxUbicacionGuardar() {return cbxUbicacionGuardar;}
    public void setCbxUbicacionGuardar(JComboBox cbxUbicacionGuardar) {this.cbxUbicacionGuardar = cbxUbicacionGuardar;}
    public JButton getBtnRecuContra() {
        return btnRecuContra;
    }
    public void setBtnRecuContra(JButton btnRecuContra) {
        this.btnRecuContra = btnRecuContra;
    }
    public JTextField getTxtUserName() {return txtUserName;}
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