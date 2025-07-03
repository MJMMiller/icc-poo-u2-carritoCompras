package ec.edu.ups.poo.vista.usuario;

import ec.edu.ups.poo.util.enums.TipoIcono;
import ec.edu.ups.poo.modelo.enums.Rol;
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

    public UsuarioListarView(MensajeInternacionalizacionHandler i18n) {
        this.i18n = i18n;
        setContentPane(panelAll);
        setTitle("Listar Usuarios");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1350, 500);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

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

        if (cbxRol != null) {
            cbxRol.removeAllItems();
            cbxRol.addItem("Todos");
            for (Rol rol : Rol.values()) {
                cbxRol.addItem(rol);
            }
        }

        aplicaraIdioma();
        aplicarIconos();

    }

    public JPanel getPanelSuperio() {
        return panelSuperio;
    }

    public void setPanelSuperio(JPanel panelSuperio) {
        this.panelSuperio = panelSuperio;
    }

    public JButton getBtnListar() {
        return btnListar;
    }

    public void setBtnListar(JButton btnListar) {
        this.btnListar = btnListar;
    }

    public JPanel getPanelCentral() {
        return panelCentral;
    }

    public void setPanelCentral(JPanel panelCentral) {
        this.panelCentral = panelCentral;
    }

    public JTable getTableUsers() {
        return tableUsers;
    }

    public void setTableUsers(JTable tableUsers) {
        this.tableUsers = tableUsers;
    }

    public JPanel getPanelAll() {
        return panelAll;
    }

    public void setPanelAll(JPanel panelAll) {
        this.panelAll = panelAll;
    }

    public JScrollPane getScroll() {
        return scroll;
    }

    public void setScroll(JScrollPane scroll) {
        this.scroll = scroll;
    }

    public JPanel getPanelItulo() {
        return panelItulo;
    }

    public void setPanelItulo(JPanel panelItulo) {
        this.panelItulo = panelItulo;
    }

    public JLabel getTxtSettingsProduc() {
        return lblTitulo;
    }

    public void setTxtSettingsProduc(JLabel txtSettingsProduc) {
        this.lblTitulo = txtSettingsProduc;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    public void setTxtUsuario(JTextField txtUsuario) {
        this.txtUsuario = txtUsuario;
    }

    public JComboBox<Object> getCbxRol() {
        return cbxRol;
    }

    public void setCbxRol(JComboBox<Object> cbxRol) {
        this.cbxRol = cbxRol;
    }

    public JLabel getLblUsuario() {
        return lblUsuario;
    }

    public void setLblUsuario(JLabel lblUsuario) {
        this.lblUsuario = lblUsuario;
    }

    public JLabel getLblRol() {
        return lblRol;
    }

    public void setLblRol(JLabel lblRol) {
        this.lblRol = lblRol;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
    }

    public void mostrarUsuarios(List<Usuario> usuarios) {
        modelo.setRowCount(0);
        Locale locale = i18n.getLocale(); // <--- ¡Esta es la forma correcta!
        for (Usuario usuario : usuarios) {
            String fechaFormateada = "";
            if (usuario.getFechaNacimiento() != null) {
                fechaFormateada = FormateadorUtils.formatearFecha(usuario.getFechaNacimiento(), locale);
            }
            modelo.addRow(new Object[]{
                    usuario.getUserName(),
                    usuario.getContrasena(),
                    usuario.getNombreCompleto(),
                    fechaFormateada,
                    usuario.getCorreo(),
                    usuario.getTelefono(),
                    usuario.getRol().name()
            });
        }
    }

    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }

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

    public void aplicarIconos() {
        setFrameIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.USUARIO));
        btnBuscar.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.BUSCAR));
        btnListar.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.LISTAR));
    }
}