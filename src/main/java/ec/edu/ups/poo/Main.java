package ec.edu.ups.poo;

import ec.edu.ups.poo.controlador.*;
import ec.edu.ups.poo.dao.CarritoDAO;
import ec.edu.ups.poo.dao.ProductoDAO;
import ec.edu.ups.poo.dao.UsuarioDAO;
import ec.edu.ups.poo.dao.impl.CarritoDAOMemoria;
import ec.edu.ups.poo.dao.impl.ProductoDAOMemoria;
import ec.edu.ups.poo.dao.impl.UsuarioDAOMemoria;
import ec.edu.ups.poo.modelo.Rol;
import ec.edu.ups.poo.modelo.Usuario;
import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.vista.MenuPrincipalView;
import ec.edu.ups.poo.vista.carrito.*;
import ec.edu.ups.poo.vista.inicio.LogInView;
import ec.edu.ups.poo.vista.producto.*;
import ec.edu.ups.poo.vista.usuario.*;

public class Main {
    public static UsuarioDAO usuarioDAO;
    public static ProductoDAO productoDAO;
    public static CarritoDAO carritoDAO;
    private static UsuarioController usuarioController;
    private static MensajeInternacionalizacionHandler i18n;

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            usuarioDAO = new UsuarioDAOMemoria();
            productoDAO = new ProductoDAOMemoria();
            carritoDAO = new CarritoDAOMemoria();
            i18n = new MensajeInternacionalizacionHandler("es", "EC");
            mostrarLogin();
        });
    }

    public static void mostrarLogin() {
        LogInView logInView = new LogInView(i18n);

        new LogInController(usuarioDAO, logInView, i18n, new LogInController.MainAppCallback() {
            @Override
            public void mostrarMenuPrincipal(Usuario usuarioAutenticado) {
                Main.mostrarMenuPrincipal(usuarioAutenticado);
            }

            @Override
            public void mostrarLogin() {
                Main.mostrarLogin();
            }
        });

        logInView.setVisible(true);
    }

    public static void mostrarMenuPrincipal(Usuario usuarioAutenticado) {
        MenuPrincipalView principalView = new MenuPrincipalView(usuarioAutenticado,i18n);

        // PRODUCTO
        ProductoAnadirView productoAnadirView = new ProductoAnadirView(i18n);
        ProductoListarView productoListaView = new ProductoListarView(i18n);
        ProductoEditarView productoGestionView = new ProductoEditarView(i18n);
        ProductoEliminarView productoEliminarView = new ProductoEliminarView(i18n);

        // CARRITO
        CarritoAnadirView carritoAnadirView = new CarritoAnadirView(i18n);
        CarritoEditarView carritoEditarView = new CarritoEditarView(i18n);
        CarritoEliminarView carritoEliminarView = new CarritoEliminarView(i18n);
        CarritoListarView carritoListarView = new CarritoListarView(i18n);

        // USUARIO
        UsuarioAnadirView usuarioAnadirView = new UsuarioAnadirView(i18n);
        UsuarioListarView usuarioListarView = new UsuarioListarView(i18n);
        UsuarioEditarView usuarioEditarView = new UsuarioEditarView(i18n);
        UsuarioElimiarView usuarioElimiarView = new UsuarioElimiarView(i18n);

        usuarioController = new UsuarioController(usuarioDAO, null,i18n);
        new ProductoController(productoDAO, productoAnadirView, productoListaView, productoGestionView, productoEliminarView, carritoAnadirView, i18n);
        CarritoController carritoController = new CarritoController(carritoDAO, productoDAO, carritoAnadirView, carritoEditarView, usuarioAutenticado,i18n);

        if (usuarioAutenticado.getRol() == Rol.USUARIO) {
            principalView.getMenuItemCrearProducto().setEnabled(false);
            principalView.getMenuItemEditarProducto().setEnabled(false);
            principalView.getMenuItemEliminarProducto().setEnabled(false);

            principalView.getMenuItemCrearCarrito().setEnabled(true);
            principalView.getMenuItemEditarCarrito().setEnabled(true);
            principalView.getMenuItemEliminarCarrito().setEnabled(true);
            principalView.getMenuItemListarCarritos().setEnabled(false);

            principalView.getMenuItemCrearUsuario().setEnabled(false);
            principalView.getMenuItemEditarUsuario().setEnabled(true);
            principalView.getMenuItemEliminarUsuario().setEnabled(false);
            principalView.getMenuItemListarUsuarios().setEnabled(false);

            principalView.getMenuIdioma().setEnabled(true);
        }

        // PRODUCTOS
        principalView.getMenuItemCrearProducto().addActionListener(event -> {
            if (!productoAnadirView.isVisible()) {
                productoAnadirView.setVisible(true);
                principalView.getjDesktopPane().add(productoAnadirView);
            }
        });

        principalView.getMenuItemBuscarProducto().addActionListener(event -> {
            if (!productoListaView.isVisible()) {
                productoListaView.setVisible(true);
                principalView.getjDesktopPane().add(productoListaView);
            }
        });

        principalView.getMenuItemEditarProducto().addActionListener(event -> {
            if (!productoGestionView.isVisible()) {
                productoGestionView.setVisible(true);
                principalView.getjDesktopPane().add(productoGestionView);
            }
        });

        principalView.getMenuItemEliminarProducto().addActionListener(event -> {
            if (!productoEliminarView.isVisible()) {
                productoEliminarView.setVisible(true);
                principalView.getjDesktopPane().add(productoEliminarView);
            }
        });

        // CARRITO
        principalView.getMenuItemCrearCarrito().addActionListener(event -> {
            if (!carritoAnadirView.isVisible()) {
                carritoAnadirView.setVisible(true);
                principalView.getjDesktopPane().add(carritoAnadirView);
            }
        });

        principalView.getMenuItemEditarCarrito().addActionListener(event -> {
            if (!carritoEditarView.isVisible()) {
                principalView.getjDesktopPane().add(carritoEditarView);
                carritoEditarView.setVisible(true);
            }
        });

        principalView.getMenuItemEliminarCarrito().addActionListener(event -> {
            if (!carritoEliminarView.isVisible()) {
                carritoController.configurarEventosEliminar(carritoEliminarView);
                carritoEliminarView.setVisible(true);
                principalView.getjDesktopPane().add(carritoEliminarView);
            }
        });

        principalView.getMenuItemListarCarritos().addActionListener(event -> {
            if (!carritoListarView.isVisible()) {
                principalView.getjDesktopPane().add(carritoListarView);
                carritoListarView.setVisible(true);
                carritoController.configurarEventosListar(carritoListarView);
            }
        });

        // USUARIOS
        principalView.getMenuItemCrearUsuario().addActionListener(event -> {
            if (!usuarioAnadirView.isVisible()) {
                usuarioController.configurarUsuarioCrearView(usuarioAnadirView);
                usuarioAnadirView.setVisible(true);
                principalView.getjDesktopPane().add(usuarioAnadirView);
            }
        });
        principalView.getMenuItemEditarUsuario().addActionListener(event -> {
            if (!usuarioEditarView.isVisible()) {
                usuarioController.configurarUsuarioEditarView(usuarioEditarView);
                usuarioEditarView.setVisible(true);
                principalView.getjDesktopPane().add(usuarioEditarView);
            }
        });
        principalView.getMenuItemEliminarUsuario().addActionListener(event -> {
            if (!usuarioElimiarView.isVisible()) {
                usuarioController.configurarUsuarioEliminarView(usuarioElimiarView);
                usuarioElimiarView.setVisible(true);
                principalView.getjDesktopPane().add(usuarioElimiarView);
            }
        });
        principalView.getMenuItemListarUsuarios().addActionListener(event -> {
            if (!usuarioListarView.isVisible()) {
                usuarioController.configurarUsuarioListarView(usuarioListarView);
                usuarioListarView.setVisible(true);
                principalView.getjDesktopPane().add(usuarioListarView);
            }
        });

        principalView.getMenuItemLogout().addActionListener(ev -> {
            principalView.dispose();
            mostrarLogin();
        });

        principalView.getMenuItemEspanol().addActionListener(event -> {
            i18n.setLenguaje("es", "EC");
            principalView.aplicarIdioma();
            productoAnadirView.aplicarIdiomas();
            productoListaView.aplicarIdioma();
            productoGestionView.aplicarIdiomas();
            productoEliminarView.aplicarIdioma();
            carritoAnadirView.aplicarIdioma();
            carritoEditarView.aplicarIdioma();
            carritoEliminarView.aplicarIdioma();
            carritoListarView.aplicarIdioma();
            usuarioAnadirView.aplicarIdioma();
            usuarioListarView.aplicaraIdioma();
            usuarioEditarView.aplicarIdioma();
            usuarioElimiarView.aplicarIdioma();

            carritoAnadirView.refrescarResumenValores(i18n.getLocale());
            carritoEditarView.refrescarResumenValores(i18n.getLocale());
            carritoEliminarView.refrescarResumenValores(i18n.getLocale());
            carritoListarView.refrescarResumenValores(i18n.getLocale());
        });
        principalView.getMenuItemIngles().addActionListener(event -> {
            i18n.setLenguaje("en", "US");
            principalView.aplicarIdioma();
            productoAnadirView.aplicarIdiomas();
            productoListaView.aplicarIdioma();
            productoGestionView.aplicarIdiomas();
            productoEliminarView.aplicarIdioma();
            carritoAnadirView.aplicarIdioma();
            carritoEditarView.aplicarIdioma();
            carritoEliminarView.aplicarIdioma();
            carritoListarView.aplicarIdioma();
            usuarioAnadirView.aplicarIdioma();
            usuarioListarView.aplicaraIdioma();
            usuarioEditarView.aplicarIdioma();
            usuarioElimiarView.aplicarIdioma();

            carritoAnadirView.refrescarResumenValores(i18n.getLocale());
            carritoEditarView.refrescarResumenValores(i18n.getLocale());
            carritoEliminarView.refrescarResumenValores(i18n.getLocale());
            carritoListarView.refrescarResumenValores(i18n.getLocale());
        });
        principalView.getMenuItemFrances().addActionListener(event -> {
            i18n.setLenguaje("fr", "FR");
            principalView.aplicarIdioma();
            productoAnadirView.aplicarIdiomas();
            productoListaView.aplicarIdioma();
            productoGestionView.aplicarIdiomas();
            productoEliminarView.aplicarIdioma();
            carritoAnadirView.aplicarIdioma();
            carritoEditarView.aplicarIdioma();
            carritoEliminarView.aplicarIdioma();
            carritoListarView.aplicarIdioma();
            usuarioAnadirView.aplicarIdioma();
            usuarioListarView.aplicaraIdioma();
            usuarioEditarView.aplicarIdioma();
            usuarioElimiarView.aplicarIdioma();

            carritoAnadirView.refrescarResumenValores(i18n.getLocale());
            carritoEditarView.refrescarResumenValores(i18n.getLocale());
            carritoEliminarView.refrescarResumenValores(i18n.getLocale());
            carritoListarView.refrescarResumenValores(i18n.getLocale());
        });

        principalView.setVisible(true);
    }
}