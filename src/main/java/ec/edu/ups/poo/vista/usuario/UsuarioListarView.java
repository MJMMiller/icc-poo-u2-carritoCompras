package ec.edu.ups.poo.vista.usuario;

import ec.edu.ups.poo.modelo.Rol;
import ec.edu.ups.poo.modelo.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class UsuarioListarView extends JInternalFrame {

    private JPanel panelSuperio;
    private JButton btnListar;
    private JPanel panelCentral;
    private JTable tableUsers;
    private JPanel panelAll;
    private JScrollPane scroll;
    private JPanel panelItulo;
    private JLabel txtSettingsProduc;
    private JButton btnSearch;
    private JTextField lblNameSearch;
    private JComboBox<Object> cbxRol;
    private DefaultTableModel modelo;

    public UsuarioListarView() {
        setContentPane(panelAll);
        setTitle("List Users");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 400);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        modelo = new DefaultTableModel(new Object[]{"User", "Password", "Rol"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableUsers.setModel(modelo);

        Color fondo = new Color(29, 30, 32);
        Color letras = Color.WHITE;

        if (scroll == null && tableUsers != null) {
            scroll = (JScrollPane) tableUsers.getParent().getParent();
        }
        if (scroll != null) {
            scroll.getViewport().setBackground(fondo);
            scroll.setBackground(fondo);
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
    }

    public JComboBox<Object> getCbxRol() {
        return cbxRol;
    }

    public void setCbxRol(JComboBox<Object> cbxRol) {
        this.cbxRol = cbxRol;
    }

    public JTextField getLblNameSearch() {
        return lblNameSearch;
    }

    public void setLblNameSearch(JTextField lblNameSearch) {
        this.lblNameSearch = lblNameSearch;
    }

    public JButton getBtnSearch() {
        return btnSearch;
    }

    public void setBtnSearch(JButton btnSearch) {
        this.btnSearch = btnSearch;
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

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
    }

    public void mostrarUsuarios(List<Usuario> usuarios) {
        modelo.setRowCount(0);
        for (Usuario usuario : usuarios) {
            modelo.addRow(new Object[]{
                    usuario.getUserName(),
                    usuario.getContrasena(),
                    usuario.getRol().name()
            });
        }
    }

    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }
}