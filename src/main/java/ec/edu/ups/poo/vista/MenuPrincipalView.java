package ec.edu.ups.poo.vista;

import ec.edu.ups.poo.modelo.Usuario;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipalView extends JFrame {
    private JMenuBar menuBar;
    private JMenu menuProducto;
    private JMenu menuCarrito;
    private JMenu menuUsuarios;
    private JMenu menuSesion;
    private JMenu menuIdioma;
    private JMenuItem menuItemLogout;

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

    private JMenuItem menuItemEspanol;
    private JMenuItem menuItemIngles;
    private JMenuItem menuItemFrances;

    private MiJDesktopPane desktop;

    public MenuPrincipalView(Usuario usuarioAutenticado) {
        setLayout(new BorderLayout());

        desktop = new MiJDesktopPane();
        desktop.setBackground(new Color(33, 37, 43));

        JPanel panelNorth = new JPanel(new BorderLayout());
        panelNorth.setBackground(new Color(35, 38, 43));

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        centerPanel.setOpaque(false);

        JLabel lblBienvenida = new JLabel("Bienvenido al sistema de compras: " + usuarioAutenticado.getUserName(), SwingConstants.CENTER);
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 26));
        lblBienvenida.setForeground(Color.WHITE);
        lblBienvenida.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        lblBienvenida.setOpaque(false);
        centerPanel.add(lblBienvenida);

        panelNorth.add(centerPanel, BorderLayout.CENTER);

        menuBar = new JMenuBar();
        menuBar.setBackground(new Color(29, 30, 32));
        menuBar.setForeground(Color.WHITE);

        menuProducto = new JMenu("Producto");
        menuProducto.setForeground(Color.WHITE);

        menuCarrito = new JMenu("Carrito");
        menuCarrito.setForeground(Color.WHITE);

        menuUsuarios = new JMenu("Usuario");
        menuUsuarios.setForeground(Color.WHITE);

        menuSesion = new JMenu("Sesión");
        menuSesion.setForeground(Color.WHITE);

        menuIdioma = new JMenu("Idioma");
        menuIdioma.setForeground(Color.WHITE);

        menuItemCrearProducto = new JMenuItem("Crear Producto");
        menuItemEditarProducto = new JMenuItem("Editar Producto");
        menuItemBuscarProducto = new JMenuItem("Listar Productos");
        menuItemEliminarProducto = new JMenuItem("Borrar Producto");

        menuItemCrearCarrito = new JMenuItem("Crear Carrito");
        menuItemEditarCarrito = new JMenuItem("Editar Carrito");
        menuItemEliminarCarrito = new JMenuItem("Borrar Carrito");
        menuItemListarCarritos = new JMenuItem("Listar Carrtitos");

        menuItemCrearUsuario = new JMenuItem("Crear Usuario");
        menuItemEditarUsuario = new JMenuItem("Editar  Usuario");
        menuItemEliminarUsuario = new JMenuItem("Borrar Usuario");
        menuItemListarUsuarios = new JMenuItem("Listar Usuarios");

        menuItemEspanol = new JMenuItem("Espanol");
        menuItemIngles = new JMenuItem("Ingles");
        menuItemFrances = new JMenuItem("Frances");

        menuItemLogout = new JMenuItem("Cerrar Sesión");
        menuSesion.add(menuItemLogout);

        menuBar.add(menuProducto);
        menuBar.add(menuCarrito);
        menuBar.add(menuUsuarios);
        menuBar.add(menuIdioma);
        menuBar.add(menuSesion);

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

        menuIdioma.add(menuItemEspanol);
        menuIdioma.add(menuItemIngles);
        menuIdioma.add(menuItemFrances);

        setJMenuBar(menuBar);

        add(panelNorth, BorderLayout.NORTH);
        add(desktop, BorderLayout.CENTER);

        setTitle("Sistema de Carrito de Compras");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    public JDesktopPane getjDesktopPane() { return desktop; }
    public void setjDesktopPane(MiJDesktopPane desktop) { this.desktop = desktop; }

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

    public JMenu getMenuIdioma() { return menuIdioma; }
    public void setMenuIdioma(JMenu menuIdioma) { this.menuIdioma = menuIdioma; }
    public JMenuItem getMenuItemEspanol() { return menuItemEspanol; }
    public void setMenuItemEspanol(JMenuItem menuItemEspanol) { this.menuItemEspanol = menuItemEspanol; }
    public JMenuItem getMenuItemIngles() { return menuItemIngles; }
    public void setMenuItemIngles(JMenuItem menuItemIngles) { this.menuItemIngles = menuItemIngles; }
    public JMenuItem getMenuItemFrances() { return menuItemFrances; }
    public void setMenuItemFrances(JMenuItem menuItemFrances) { this.menuItemFrances = menuItemFrances; }

    public JMenuItem getMenuItemLogout() { return menuItemLogout; }
    public void setMenuItemLogout(JMenuItem menuItemLogout) { this.menuItemLogout = menuItemLogout; }
}