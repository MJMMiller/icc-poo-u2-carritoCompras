package ec.edu.ups.poo.vista.usuario;

import ec.edu.ups.poo.util.TipoIcono;
import ec.edu.ups.poo.modelo.Rol;
import ec.edu.ups.poo.modelo.Usuario;
import ec.edu.ups.poo.util.FormateadorUtils;
import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;
import java.util.Locale;

/**
 * Vista interna para listar usuarios en el sistema.
 * Permite mostrar, buscar y filtrar usuarios por rol, y visualizar sus datos en una tabla.
 */
public class UsuarioListarView extends JInternalFrame {

    private JPanel panelSuperio;
    private JButton btnListar;
    private JPanel panelCentral;
    private JTable tableUsers;
    private JPanel panelAll;
    private JScrollPane scroll;
    private JPanel panelItulo;
    private JLabel lblTitulo;
    private JButton btnBuscar;
    private JTextField txtUsuario;
    private JComboBox<Object> cbxRol;
    private JLabel lblUsuario;
    private JLabel lblRol;
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler i18n;

    /**
     * Constructor de UsuarioListarView.
     * Inicializa la vista, configura la tabla, el ComboBox de roles, idioma e iconos.
     *
     * @param i18n Manejador de internacionalización de mensajes.
     */
    public UsuarioListarView(MensajeInternacionalizacionHandler i18n) {
        this.i18n = i18n;
        setContentPane(panelAll);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1350, 500);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);


        cargarTabla();
        cargarRol();
        aplicaraIdioma();
        aplicarIconos();

    }

    /**
     * Muestra una lista de usuarios en la tabla de la vista.
     *
     * @param usuarios Lista de usuarios a mostrar.
     */
    public void mostrarUsuarios(List<Usuario> usuarios) {
        modelo.setRowCount(0);
        Locale locale = i18n.getLocale();
        for (Usuario usuario : usuarios) {
            String fechaFormateada = "";
            if (usuario.getFechaNacimiento() != null) {
                fechaFormateada = FormateadorUtils.formatearFecha(usuario.getFechaNacimiento(), locale);
            }
            modelo.addRow(new Object[]{
                    usuario.getCedula(),
                    usuario.getContrasena(),
                    usuario.getNombreCompleto(),
                    fechaFormateada,
                    usuario.getCorreo(),
                    usuario.getTelefono(),
                    usuario.getRol().name()
            });
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
     * Aplica los textos traducidos a los componentes de la vista según el idioma actual.
     * No recibe parámetros ni retorna valores.
     */
    public void aplicaraIdioma() {
        setTitle(i18n.get("usuario.listar.title"));
        lblTitulo.setText(i18n.get("usuario.listar.titulo"));
        lblUsuario.setText(i18n.get("usuario.listar.lbl.usuario"));
        lblRol.setText(i18n.get("usuario.listar.lbl.rol"));
        btnBuscar.setText(i18n.get("usuario.listar.btn.buscar"));
        btnListar.setText(i18n.get("usuario.listar.btn.listar"));
        tableUsers.getColumnModel().getColumn(0).setHeaderValue(i18n.get("usuario.listar.table.usuario"));
        tableUsers.getColumnModel().getColumn(1).setHeaderValue(i18n.get("usuario.listar.table.contrasena"));
        tableUsers.getColumnModel().getColumn(2).setHeaderValue(i18n.get("usuario.listar.table.nombreCompleto"));
        tableUsers.getColumnModel().getColumn(3).setHeaderValue(i18n.get("usuario.listar.table.fechaNacimiento"));
        tableUsers.getColumnModel().getColumn(4).setHeaderValue(i18n.get("usuario.listar.table.correo"));
        tableUsers.getColumnModel().getColumn(5).setHeaderValue(i18n.get("usuario.listar.table.telefono"));
        tableUsers.getColumnModel().getColumn(6).setHeaderValue(i18n.get("usuario.listar.table.rol"));
        tableUsers.getTableHeader().repaint();
    }

    /**
     * Aplica los iconos correspondientes a los botones de la vista.
     * No recibe parámetros ni retorna valores.
     */
    public void aplicarIconos() {
        setFrameIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.USUARIO));
        btnBuscar.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.BUSCAR));
        btnListar.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.LISTAR));
    }

    /**
     * Carga los roles disponibles en el ComboBox de roles.
     * No recibe parámetros ni retorna valores.
     */
    public void cargarRol(){
        if (cbxRol != null) {
            cbxRol.removeAllItems();
            cbxRol.addItem("Todos");
            for (Rol rol : Rol.values()) {
                cbxRol.addItem(rol);
            }
        }
    }

    /**
     * Configura y personaliza la tabla de usuarios, incluyendo colores, alineación y renderizado.
     * No recibe parámetros ni retorna valores.
     */
    public void cargarTabla() {
        modelo = new DefaultTableModel(
                new Object[]{
                        "Usuario",
                        "Contrasena",
                        "Nombre Completo",
                        "Fecha Nacimiento",
                        "Correo",
                        "Telefono",
                        "Rol"
                }, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableUsers.setModel(modelo);

        tableUsers.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        Color fondo = new Color(29, 30, 32);
        Color letras = Color.WHITE;

        if (scroll == null && tableUsers != null) {
            scroll = (JScrollPane) tableUsers.getParent().getParent();
        }
        if (scroll != null) {
            scroll.getViewport().setBackground(fondo);
            scroll.setBackground(fondo);
            scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        }

        if (tableUsers != null) {
            tableUsers.setBackground(fondo);
            tableUsers.setForeground(letras);
            tableUsers.setSelectionBackground(new Color(50, 50, 60));
            tableUsers.setSelectionForeground(Color.WHITE);
            tableUsers.setGridColor(fondo);

            JTableHeader header = tableUsers.getTableHeader();
            header.setBackground(fondo);
            header.setForeground(letras);
            header.setFont(header.getFont().deriveFont(Font.BOLD));
            ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            centerRenderer.setForeground(letras);
            centerRenderer.setBackground(fondo);
            for (int i = 0; i < tableUsers.getColumnCount(); i++) {
                tableUsers.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }

    }

    // Métodos getter y setter para los componentes de la vista.

    /**
     * Obtiene el panel superior de la vista.
     * @return JPanel superior.
     */
    public JPanel getPanelSuperio() {
        return panelSuperio;
    }
    /**
     * Establece el panel superior de la vista.
     * @param panelSuperio JPanel superior.
     */
    public void setPanelSuperio(JPanel panelSuperio) {
        this.panelSuperio = panelSuperio;
    }
    /**
     * Obtiene el botón para listar usuarios.
     * @return JButton para listar usuarios.
     */
    public JButton getBtnListar() {
        return btnListar;
    }
    /**
     * Establece el botón para listar usuarios.
     * @param btnListar JButton para listar usuarios.
     */
    public void setBtnListar(JButton btnListar) {
        this.btnListar = btnListar;
    }
    /**
     * Obtiene el panel central de la vista.
     * @return JPanel central.
     */
    public JPanel getPanelCentral() {
        return panelCentral;
    }
    /**
     * Establece el panel central de la vista.
     * @param panelCentral JPanel central.
     */
    public void setPanelCentral(JPanel panelCentral) {
        this.panelCentral = panelCentral;
    }
    /**
     * Obtiene la tabla de usuarios.
     * @return JTable de usuarios.
     */
    public JTable getTableUsers() {
        return tableUsers;
    }
    /**
     * Establece la tabla de usuarios.
     * @param tableUsers JTable de usuarios.
     */
    public void setTableUsers(JTable tableUsers) {
        this.tableUsers = tableUsers;
    }
    /**
     * Obtiene el panel principal de la vista.
     * @return JPanel principal.
     */
    public JPanel getPanelAll() {
        return panelAll;
    }
    /**
     * Establece el panel principal de la vista.
     * @param panelAll JPanel principal.
     */
    public void setPanelAll(JPanel panelAll) {
        this.panelAll = panelAll;
    }
    /**
     * Obtiene el JScrollPane de la tabla.
     * @return JScrollPane de la tabla.
     */
    public JScrollPane getScroll() {
        return scroll;
    }
    /**
     * Establece el JScrollPane de la tabla.
     * @param scroll JScrollPane de la tabla.
     */
    public void setScroll(JScrollPane scroll) {
        this.scroll = scroll;
    }
    /**
     * Obtiene el panel del título.
     * @return JPanel del título.
     */
    public JPanel getPanelItulo() {
        return panelItulo;
    }
    /**
     * Establece el panel del título.
     * @param panelItulo JPanel del título.
     */
    public void setPanelItulo(JPanel panelItulo) {
        this.panelItulo = panelItulo;
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
     * Obtiene el botón para buscar usuarios.
     * @return JButton para buscar usuarios.
     */
    public JButton getBtnBuscar() {
        return btnBuscar;
    }
    /**
     * Establece el botón para buscar usuarios.
     * @param btnBuscar JButton para buscar usuarios.
     */
    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }
    /**
     * Obtiene el campo de texto para el nombre de usuario.
     * @return JTextField de usuario.
     */
    public JTextField getTxtUsuario() {
        return txtUsuario;
    }
    /**
     * Establece el campo de texto para el nombre de usuario.
     * @param txtUsuario JTextField de usuario.
     */
    public void setTxtUsuario(JTextField txtUsuario) {
        this.txtUsuario = txtUsuario;
    }
    /**
     * Obtiene el ComboBox de roles.
     * @return JComboBox de roles.
     */
    public JComboBox<Object> getCbxRol() {
        return cbxRol;
    }
    /**
     * Establece el ComboBox de roles.
     * @param cbxRol JComboBox de roles.
     */
    public void setCbxRol(JComboBox<Object> cbxRol) {
        this.cbxRol = cbxRol;
    }
    /**
     * Obtiene la etiqueta de usuario.
     * @return JLabel de usuario.
     */
    public JLabel getLblUsuario() {
        return lblUsuario;
    }
    /**
     * Establece la etiqueta de usuario.
     * @param lblUsuario JLabel de usuario.
     */
    public void setLblUsuario(JLabel lblUsuario) {
        this.lblUsuario = lblUsuario;
    }
    /**
     * Obtiene la etiqueta de rol.
     * @return JLabel de rol.
     */
    public JLabel getLblRol() {
        return lblRol;
    }
    /**
     * Establece la etiqueta de rol.
     * @param lblRol JLabel de rol.
     */
    public void setLblRol(JLabel lblRol) {
        this.lblRol = lblRol;
    }
    /**
     * Obtiene el modelo de la tabla de usuarios.
     * @return DefaultTableModel de la tabla.
     */
    public DefaultTableModel getModelo() {
        return modelo;
    }
    /**
     * Establece el modelo de la tabla de usuarios.
     * @param modelo DefaultTableModel de la tabla.
     */
    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
    }
}

