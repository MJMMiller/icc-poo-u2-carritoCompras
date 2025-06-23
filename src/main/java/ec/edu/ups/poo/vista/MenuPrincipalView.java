package ec.edu.ups.poo.vista;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipalView extends JFrame {
    private JMenuBar menuBar;
    private JMenu menuProducto;
    private JMenu menuCarrito;
    private JMenu menuUsuarios;

    private JMenuItem menuItemCrearProducto;
    private JMenuItem menuItemEliminarProducto;
    private JMenuItem menuItemEditarProducto;
    private JMenuItem menuItemBuscarProducto;

    private JMenuItem menuItemCrearCarrito;
    private JMenuItem menuItemEditarCarrito;
    private JMenuItem menuItemEliminarCarrito;
    private JMenuItem menuItemListarCarritos;

    private JMenuItem menuItemCrearUsuario;
    private JMenuItem menuItemEditarUsuario;
    private JMenuItem menuItemEliminarUsuario;
    private JMenuItem menuItemListarUsuarios;

    private JDesktopPane jDesktopPane;
    private JButton btnLogout;

    public MenuPrincipalView() {
        setLayout(new BorderLayout());

        jDesktopPane = new JDesktopPane() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setBackground(new Color(40, 44, 52));
            }
        };

        JPanel panelNorth = new JPanel(new BorderLayout());
        panelNorth.setBackground(new Color(33, 37, 43));

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        centerPanel.setOpaque(false);

        JLabel lblBienvenida = new JLabel("Welcome to the Shopping Cart System", SwingConstants.CENTER);
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 26));
        lblBienvenida.setForeground(Color.WHITE);
        lblBienvenida.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        lblBienvenida.setOpaque(false);
        centerPanel.add(lblBienvenida);

        btnLogout = new JButton("Logout");
        btnLogout.setBackground(Color.WHITE);
        btnLogout.setForeground(Color.BLACK);
        btnLogout.setFocusPainted(false);
        btnLogout.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogout.setPreferredSize(new Dimension(90, 32));
        btnLogout.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        panelNorth.add(centerPanel, BorderLayout.CENTER);
        panelNorth.add(btnLogout, BorderLayout.EAST);

        menuBar = new JMenuBar();
        menuBar.setBackground(new Color(33, 37, 43));
        menuBar.setForeground(Color.WHITE);

        menuProducto = new JMenu("Product");
        menuProducto.setForeground(Color.WHITE);

        menuCarrito = new JMenu("Cart");
        menuCarrito.setForeground(Color.WHITE);

        menuUsuarios = new JMenu("Users");
        menuUsuarios.setForeground(Color.WHITE);

        menuItemCrearProducto = new JMenuItem("Create Product");
        menuItemEditarProducto = new JMenuItem("Edit Product");
        menuItemBuscarProducto = new JMenuItem("Search Product");
        menuItemEliminarProducto = new JMenuItem("Delete Product");

        menuItemCrearCarrito = new JMenuItem("Create Cart");
        menuItemEditarCarrito = new JMenuItem("Edit Cart");
        menuItemEliminarCarrito = new JMenuItem("Delete Cart");
        menuItemListarCarritos = new JMenuItem("List Carts");

        menuItemCrearUsuario = new JMenuItem("Create User");
        menuItemEditarUsuario = new JMenuItem("Edit User");
        menuItemEliminarUsuario = new JMenuItem("Delete User");
        menuItemListarUsuarios = new JMenuItem("List Users");

        menuBar.add(menuProducto);
        menuBar.add(menuCarrito);
        menuBar.add(menuUsuarios);

        menuProducto.add(menuItemCrearProducto);
        menuProducto.add(menuItemEditarProducto);
        menuProducto.add(menuItemBuscarProducto);
        menuProducto.add(menuItemEliminarProducto);

        menuCarrito.add(menuItemCrearCarrito);
        menuCarrito.add(menuItemEditarCarrito);
        menuCarrito.add(menuItemListarCarritos);
        menuCarrito.add(menuItemEliminarCarrito);

        menuUsuarios.add(menuItemCrearUsuario);
        menuUsuarios.add(menuItemEditarUsuario);
        menuUsuarios.add(menuItemListarUsuarios);
        menuUsuarios.add(menuItemEliminarUsuario);

        setJMenuBar(menuBar);

        add(panelNorth, BorderLayout.NORTH);
        add(jDesktopPane, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Online Shopping Cart System");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    public JMenuItem getMenuItemCrearProducto() { return menuItemCrearProducto; }
    public void setMenuItemCrearProducto(JMenuItem menuItemCrearProducto) { this.menuItemCrearProducto = menuItemCrearProducto; }
    public JMenuItem getMenuItemEditarProducto() { return menuItemEditarProducto; }
    public void setMenuItemEditarProducto(JMenuItem menuItemEditarProducto) { this.menuItemEditarProducto = menuItemEditarProducto; }
    public JMenuItem getMenuItemBuscarProducto() { return menuItemBuscarProducto; }
    public void setMenuItemBuscarProducto(JMenuItem menuItemBuscarProducto){ this.menuItemBuscarProducto = menuItemBuscarProducto; }
    public JMenuItem getMenuItemEliminarProducto() { return menuItemEliminarProducto; }
    public void setMenuItemEliminarProducto(JMenuItem menuItemEliminarProducto) { this.menuItemEliminarProducto = menuItemEliminarProducto;}

    public JMenuItem getMenuItemCrearCarrito() { return menuItemCrearCarrito; }
    public void setMenuItemCrearCarrito(JMenuItem menuItemCrearCarrito) { this.menuItemCrearCarrito = menuItemCrearCarrito; }
    public JMenuItem getMenuItemEditarCarrito() { return menuItemEditarCarrito; }
    public void setMenuItemEditarCarrito(JMenuItem menuItemEditarCarrito) { this.menuItemEditarCarrito = menuItemEditarCarrito; }
    public JMenuItem getMenuItemEliminarCarrito() { return menuItemEliminarCarrito; }
    public void setMenuItemEliminarCarrito(JMenuItem menuItemEliminarCarrito) { this.menuItemEliminarCarrito = menuItemEliminarCarrito; }
    public JMenuItem getMenuItemListarCarritos() { return menuItemListarCarritos; }
    public void setMenuItemListarCarritos(JMenuItem menuItemListarCarritos) { this.menuItemListarCarritos = menuItemListarCarritos; }

    public JMenu getMenuUsuarios() { return menuUsuarios; }
    public void setMenuUsuarios(JMenu menuUsuarios) { this.menuUsuarios = menuUsuarios; }
    public JMenuItem getMenuItemCrearUsuario() { return menuItemCrearUsuario; }
    public void setMenuItemCrearUsuario(JMenuItem menuItemCrearUsuario) { this.menuItemCrearUsuario = menuItemCrearUsuario; }
    public JMenuItem getMenuItemEditarUsuario() { return menuItemEditarUsuario; }
    public void setMenuItemEditarUsuario(JMenuItem menuItemEditarUsuario) { this.menuItemEditarUsuario = menuItemEditarUsuario; }
    public JMenuItem getMenuItemEliminarUsuario() { return menuItemEliminarUsuario; }
    public void setMenuItemEliminarUsuario(JMenuItem menuItemEliminarUsuario) { this.menuItemEliminarUsuario = menuItemEliminarUsuario; }
    public JMenuItem getMenuItemListarUsuarios() { return menuItemListarUsuarios; }
    public void setMenuItemListarUsuarios(JMenuItem menuItemListarUsuarios) { this.menuItemListarUsuarios = menuItemListarUsuarios; }

    public JMenu getMenuProducto() { return menuProducto; }
    public void setMenuProducto(JMenu menuProducto) { this.menuProducto = menuProducto; }
    public JMenu getMenuCarrito() { return menuCarrito; }
    public void setMenuCarrito(JMenu menuCarrito) { this.menuCarrito = menuCarrito; }
    public JDesktopPane getjDesktopPane() { return jDesktopPane; }
    public void setjDesktopPane(JDesktopPane jDesktopPane) { this.jDesktopPane = jDesktopPane; }

    public JButton getBtnLogout() { return btnLogout; }
    public void setBtnLogout(JButton btnLogout) { this.btnLogout = btnLogout; }
}