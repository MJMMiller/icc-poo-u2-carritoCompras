package ec.edu.ups.poo.vista;

import ec.edu.ups.poo.modelo.Usuario;
import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.util.TipoIcono;

import javax.swing.*;
import java.awt.*;

/**
 * Vista principal del sistema, que muestra el menú de navegación y el área de trabajo.
 * Permite gestionar productos, carritos, usuarios, sesión e idioma, y personaliza la interfaz según el usuario autenticado.
 */
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

    private JLabel lblBienvenida;

    private MiJDesktopPane desktop;

    private MensajeInternacionalizacionHandler i18n;

    private Usuario usuarioAutenticado;

    /**
     * Constructor de MenuPrincipalView.
     * Inicializa la interfaz gráfica, los menús, los ítems y aplica idioma e iconos.
     *
     * @param usuarioAutenticado Usuario autenticado en sesión.
     * @param i18n Manejador de internacionalización de mensajes.
     */
    public MenuPrincipalView(Usuario usuarioAutenticado, MensajeInternacionalizacionHandler i18n) {
        this.usuarioAutenticado = usuarioAutenticado;
        this.i18n = i18n;
        setLayout(new BorderLayout());

        desktop = new MiJDesktopPane();
        desktop.setBackground(new Color(33, 37, 43));

        JPanel panelNorth = new JPanel(new BorderLayout());
        panelNorth.setBackground(new Color(35, 38, 43));

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        centerPanel.setOpaque(false);


        lblBienvenida = new JLabel();
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

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);

        aplicarIdioma();
        aplicarIconos();
    }

    /**
     * Aplica los textos traducidos a todos los menús y etiquetas de la vista según el idioma actual.
     * No recibe parámetros ni retorna valores.
     */
    public void aplicarIdioma(){

        setTitle(i18n.get("menu.titulo"));
        lblBienvenida.setText(i18n.get("menu.lblBienvenida") + ": " + usuarioAutenticado.getCedula());

        menuProducto.setText(i18n.get("menu.producto"));
        menuCarrito.setText(i18n.get("menu.carrito"));
        menuUsuarios.setText(i18n.get("menu.usuarios"));
        menuSesion.setText(i18n.get("menu.sesion"));
        menuIdioma.setText(i18n.get("menu.idioma"));

        menuItemCrearProducto.setText(i18n.get("menu.item.crear.producto"));
        menuItemEditarProducto.setText(i18n.get("menu.item.editar.producto"));
        menuItemBuscarProducto.setText(i18n.get("menu.item.listar.productos"));
        menuItemEliminarProducto.setText(i18n.get("menu.item.eliminar.producto"));

        menuItemCrearCarrito.setText(i18n.get("menu.item.crear.carrito"));
        menuItemEditarCarrito.setText(i18n.get("menu.item.editar.carrito"));
        menuItemListarCarritos.setText(i18n.get("menu.item.listar.carritos"));
        menuItemEliminarCarrito.setText(i18n.get("menu.item.eliminar.carrito"));

        menuItemCrearUsuario.setText(i18n.get("menu.item.crear.usuario"));
        menuItemEditarUsuario.setText(i18n.get("menu.item.editar.usuario"));
        menuItemListarUsuarios.setText(i18n.get("menu.item.listar.usuarios"));
        menuItemEliminarUsuario.setText(i18n.get("menu.item.eliminar.usuario"));

        menuItemEspanol.setText(i18n.get("menu.idioma.espanol"));
        menuItemIngles.setText(i18n.get("menu.idioma.ingles"));
        menuItemFrances.setText(i18n.get("menu.idioma.frances"));

        menuItemLogout.setText(i18n.get("menu.sesion.logout"));

    }

    /**
     * Aplica los iconos correspondientes a cada menú y elemento de la vista.
     * No recibe parámetros ni retorna valores.
     */
    public void aplicarIconos() {

        setIconImage(((ImageIcon) ec.edu.ups.poo.util.Direccion.icono(TipoIcono.POO_LOGO)).getImage());

        menuItemCrearProducto.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.CREAR));
        menuItemEditarProducto.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.EDITAR));
        menuItemBuscarProducto.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.LISTAR_MENU));
        menuItemEliminarProducto.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.BORRAR));

        menuItemCrearCarrito.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.CREAR));
        menuItemEditarCarrito.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.EDITAR));
        menuItemListarCarritos.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.LISTAR_MENU));
        menuItemEliminarCarrito.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.BORRAR));

        menuItemCrearUsuario.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.CREAR));
        menuItemEditarUsuario.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.EDITAR));
        menuItemListarUsuarios.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.LISTAR_MENU));
        menuItemEliminarUsuario.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.BORRAR));

        menuItemEspanol.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.ECUADOR));
        menuItemIngles.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.USA));
        menuItemFrances.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.FRANCES));

        menuItemLogout.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.LOGOUT));

        menuProducto.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.PRODUCTOM));
        menuCarrito.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.CARRITOM));
        menuUsuarios.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.USUARIOM));
        menuSesion.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.SESIONM));
        menuIdioma.setIcon(ec.edu.ups.poo.util.Direccion.icono(TipoIcono.IDIOMAM));
    }

    /**
     * Obtiene el DesktopPane principal de la vista.
     * @return Instancia de MiJDesktopPane.
     */
    public JDesktopPane getjDesktopPane() { return desktop; }

    /**
     * Establece el DesktopPane principal de la vista.
     * @param desktop Instancia de MiJDesktopPane.
     */
    public void setjDesktopPane(MiJDesktopPane desktop) { this.desktop = desktop; }

    /**
     * Obtiene el menú item para crear productos.
     * @return JMenuItem para crear productos.
     */
    public JMenuItem getMenuItemCrearProducto() { return menuItemCrearProducto; }

    /**
     * Establece el menú item para crear productos.
     * @param menuItemCrearProducto JMenuItem para crear productos.
     */
    public void setMenuItemCrearProducto(JMenuItem menuItemCrearProducto) { this.menuItemCrearProducto = menuItemCrearProducto; }

    /**
     * Obtiene el menú item para editar productos.
     * @return JMenuItem para editar productos.
     */
    public JMenuItem getMenuItemEditarProducto() { return menuItemEditarProducto; }

    /**
     * Establece el menú item para editar productos.
     * @param menuItemEditarProducto JMenuItem para editar productos.
     */
    public void setMenuItemEditarProducto(JMenuItem menuItemEditarProducto) { this.menuItemEditarProducto = menuItemEditarProducto; }

    /**
     * Obtiene el menú item para buscar productos.
     * @return JMenuItem para buscar productos.
     */
    public JMenuItem getMenuItemBuscarProducto() { return menuItemBuscarProducto; }

    /**
     * Establece el menú item para buscar productos.
     * @param menuItemBuscarProducto JMenuItem para buscar productos.
     */
    public void setMenuItemBuscarProducto(JMenuItem menuItemBuscarProducto){ this.menuItemBuscarProducto = menuItemBuscarProducto; }

    /**
     * Obtiene el menú item para eliminar productos.
     * @return JMenuItem para eliminar productos.
     */
    public JMenuItem getMenuItemEliminarProducto() { return menuItemEliminarProducto; }

    /**
     * Establece el menú item para eliminar productos.
     * @param menuItemEliminarProducto JMenuItem para eliminar productos.
     */
    public void setMenuItemEliminarProducto(JMenuItem menuItemEliminarProducto) { this.menuItemEliminarProducto = menuItemEliminarProducto;}

    /**
     * Obtiene el menú item para crear carritos.
     * @return JMenuItem para crear carritos.
     */
    public JMenuItem getMenuItemCrearCarrito() { return menuItemCrearCarrito; }

    /**
     * Establece el menú item para crear carritos.
     * @param menuItemCrearCarrito JMenuItem para crear carritos.
     */
    public void setMenuItemCrearCarrito(JMenuItem menuItemCrearCarrito) { this.menuItemCrearCarrito = menuItemCrearCarrito; }

    /**
     * Obtiene el menú item para editar carritos.
     * @return JMenuItem para editar carritos.
     */
    public JMenuItem getMenuItemEditarCarrito() { return menuItemEditarCarrito; }

    /**
     * Establece el menú item para editar carritos.
     * @param menuItemEditarCarrito JMenuItem para editar carritos.
     */
    public void setMenuItemEditarCarrito(JMenuItem menuItemEditarCarrito) { this.menuItemEditarCarrito = menuItemEditarCarrito; }

    /**
     * Obtiene el menú item para eliminar carritos.
     * @return JMenuItem para eliminar carritos.
     */
    public JMenuItem getMenuItemEliminarCarrito() { return menuItemEliminarCarrito; }

    /**
     * Establece el menú item para eliminar carritos.
     * @param menuItemEliminarCarrito JMenuItem para eliminar carritos.
     */
    public void setMenuItemEliminarCarrito(JMenuItem menuItemEliminarCarrito) { this.menuItemEliminarCarrito = menuItemEliminarCarrito; }

    /**
     * Obtiene el menú item para listar carritos.
     * @return JMenuItem para listar carritos.
     */
    public JMenuItem getMenuItemListarCarritos() { return menuItemListarCarritos; }

    /**
     * Establece el menú item para listar carritos.
     * @param menuItemListarCarritos JMenuItem para listar carritos.
     */
    public void setMenuItemListarCarritos(JMenuItem menuItemListarCarritos) { this.menuItemListarCarritos = menuItemListarCarritos; }

    /**
     * Obtiene el menú de usuarios.
     * @return JMenu de usuarios.
     */
    public JMenu getMenuUsuarios() { return menuUsuarios; }

    /**
     * Establece el menú de usuarios.
     * @param menuUsuarios JMenu de usuarios.
     */
    public void setMenuUsuarios(JMenu menuUsuarios) { this.menuUsuarios = menuUsuarios; }

    /**
     * Obtiene el menú item para crear usuarios.
     * @return JMenuItem para crear usuarios.
     */
    public JMenuItem getMenuItemCrearUsuario() { return menuItemCrearUsuario; }

    /**
     * Establece el menú item para crear usuarios.
     * @param menuItemCrearUsuario JMenuItem para crear usuarios.
     */
    public void setMenuItemCrearUsuario(JMenuItem menuItemCrearUsuario) { this.menuItemCrearUsuario = menuItemCrearUsuario; }

    /**
     * Obtiene el menú item para editar usuarios.
     * @return JMenuItem para editar usuarios.
     */
    public JMenuItem getMenuItemEditarUsuario() { return menuItemEditarUsuario; }

    /**
     * Establece el menú item para editar usuarios.
     * @param menuItemEditarUsuario JMenuItem para editar usuarios.
     */
    public void setMenuItemEditarUsuario(JMenuItem menuItemEditarUsuario) { this.menuItemEditarUsuario = menuItemEditarUsuario; }

    /**
     * Obtiene el menú item para eliminar usuarios.
     * @return JMenuItem para eliminar usuarios.
     */
    public JMenuItem getMenuItemEliminarUsuario() { return menuItemEliminarUsuario; }

    /**
     * Establece el menú item para eliminar usuarios.
     * @param menuItemEliminarUsuario JMenuItem para eliminar usuarios.
     */
    public void setMenuItemEliminarUsuario(JMenuItem menuItemEliminarUsuario) { this.menuItemEliminarUsuario = menuItemEliminarUsuario; }

    /**
     * Obtiene el menú item para listar usuarios.
     * @return JMenuItem para listar usuarios.
     */
    public JMenuItem getMenuItemListarUsuarios() { return menuItemListarUsuarios; }

    /**
     * Establece el menú item para listar usuarios.
     * @param menuItemListarUsuarios JMenuItem para listar usuarios.
     */
    public void setMenuItemListarUsuarios(JMenuItem menuItemListarUsuarios) { this.menuItemListarUsuarios = menuItemListarUsuarios; }

    /**
     * Obtiene el menú de productos.
     * @return JMenu de productos.
     */
    public JMenu getMenuProducto() { return menuProducto; }

    /**
     * Establece el menú de productos.
     * @param menuProducto JMenu de productos.
     */
    public void setMenuProducto(JMenu menuProducto) { this.menuProducto = menuProducto; }

    /**
     * Obtiene el menú de carritos.
     * @return JMenu de carritos.
     */
    public JMenu getMenuCarrito() { return menuCarrito; }

    /**
     * Establece el menú de carritos.
     * @param menuCarrito JMenu de carritos.
     */
    public void setMenuCarrito(JMenu menuCarrito) { this.menuCarrito = menuCarrito; }

    /**
     * Obtiene el menú de idioma.
     * @return JMenu de idioma.
     */
    public JMenu getMenuIdioma() { return menuIdioma; }

    /**
     * Establece el menú de idioma.
     * @param menuIdioma JMenu de idioma.
     */
    public void setMenuIdioma(JMenu menuIdioma) { this.menuIdioma = menuIdioma; }

    /**
     * Obtiene el menú item para idioma español.
     * @return JMenuItem para idioma español.
     */
    public JMenuItem getMenuItemEspanol() { return menuItemEspanol; }

    /**
     * Establece el menú item para idioma español.
     * @param menuItemEspanol JMenuItem para idioma español.
     */
    public void setMenuItemEspanol(JMenuItem menuItemEspanol) { this.menuItemEspanol = menuItemEspanol; }

    /**
     * Obtiene el menú item para idioma inglés.
     * @return JMenuItem para idioma inglés.
     */
    public JMenuItem getMenuItemIngles() { return menuItemIngles; }

    /**
     * Establece el menú item para idioma inglés.
     * @param menuItemIngles JMenuItem para idioma inglés.
     */
    public void setMenuItemIngles(JMenuItem menuItemIngles) { this.menuItemIngles = menuItemIngles; }

    /**
     * Obtiene el menú item para idioma francés.
     * @return JMenuItem para idioma francés.
     */
    public JMenuItem getMenuItemFrances() { return menuItemFrances; }

    /**
     * Establece el menú item para idioma francés.
     * @param menuItemFrances JMenuItem para idioma francés.
     */
    public void setMenuItemFrances(JMenuItem menuItemFrances) { this.menuItemFrances = menuItemFrances; }

    /**
     * Obtiene el menú item para cerrar sesión.
     * @return JMenuItem para cerrar sesión.
     */
    public JMenuItem getMenuItemLogout() { return menuItemLogout; }

    /**
     * Establece el menú item para cerrar sesión.
     * @param menuItemLogout JMenuItem para cerrar sesión.
     */
    public void setMenuItemLogout(JMenuItem menuItemLogout) { this.menuItemLogout = menuItemLogout; }

}