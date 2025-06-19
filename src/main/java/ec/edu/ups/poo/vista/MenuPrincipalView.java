package ec.edu.ups.poo.vista;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipalView extends JFrame {
    private JMenuBar menuBar;
    private JMenu menuProducto;
    private JMenu menuCarrito;
    private JMenuItem menuItemCrearProducto;
    private JMenuItem menuItemEliminarProducto;
    private JMenuItem menuItemEditarProducto;
    private JMenuItem menuItemBuscarProducto;
    private JMenuItem menuItemCrearCarrito;
    private JDesktopPane jDesktopPane;

    public MenuPrincipalView() {
        setLayout(new BorderLayout());

        jDesktopPane = new JDesktopPane() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setBackground(new Color(40, 44, 52));
            }
        };

        JLabel lblBienvenida = new JLabel("Welcome to the Shopping Cart System", SwingConstants.CENTER);
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 26));
        lblBienvenida.setForeground(Color.WHITE);
        lblBienvenida.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        lblBienvenida.setOpaque(true);
        lblBienvenida.setBackground(new Color(33, 37, 43));

        menuBar = new JMenuBar();
        menuBar.setBackground(new Color(33, 37, 43));
        menuBar.setForeground(Color.WHITE);

        menuProducto = new JMenu("Product");
        menuProducto.setForeground(Color.WHITE);

        menuCarrito = new JMenu("Cart");
        menuCarrito.setForeground(Color.WHITE);

        menuItemCrearProducto = new JMenuItem("Create Product");
        menuItemEditarProducto = new JMenuItem("Edit Product");
        menuItemBuscarProducto = new JMenuItem("Search Product");
        menuItemEliminarProducto = new JMenuItem("Delete Product");

        menuItemCrearCarrito = new JMenuItem("Create Cart");

        menuBar.add(menuProducto);
        menuBar.add(menuCarrito);
        menuProducto.add(menuItemCrearProducto);
        menuProducto.add(menuItemEditarProducto);
        menuProducto.add(menuItemBuscarProducto);
        menuCarrito.add(menuItemCrearCarrito);
        menuProducto.add(menuItemEliminarProducto);

        setJMenuBar(menuBar);

        add(lblBienvenida, BorderLayout.NORTH);
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
    public void setMenuItemBuscarProducto(JMenuItem menuItemBuscarProducto) { this.menuItemBuscarProducto = menuItemBuscarProducto; }
    public JMenu getMenuProducto() { return menuProducto; }
    public void setMenuProducto(JMenu menuProducto) { this.menuProducto = menuProducto; }
    public JMenu getMenuCarrito() { return menuCarrito; }
    public void setMenuCarrito(JMenu menuCarrito) { this.menuCarrito = menuCarrito; }
    public JMenuItem getMenuItemCrearCarrito() { return menuItemCrearCarrito; }
    public void setMenuItemCrearCarrito(JMenuItem menuItemCrearCarrito) { this.menuItemCrearCarrito = menuItemCrearCarrito; }
    public JDesktopPane getjDesktopPane() { return jDesktopPane; }
    public void setjDesktopPane(JDesktopPane jDesktopPane) { this.jDesktopPane = jDesktopPane; }

    public JMenuItem getMenuItemEliminarProducto() { return menuItemEliminarProducto; }
    public void setMenuItemEliminarProducto(JMenuItem menuItemEliminarProducto) { this.menuItemEliminarProducto = menuItemEliminarProducto;}
}