package ec.edu.ups.poo;

import ec.edu.ups.poo.controlador.CarritoController;
import ec.edu.ups.poo.controlador.ProductoController;
import ec.edu.ups.poo.controlador.RegisterController;
import ec.edu.ups.poo.controlador.UsuarioController;
import ec.edu.ups.poo.dao.CarritoDAO;
import ec.edu.ups.poo.dao.ProductoDAO;
import ec.edu.ups.poo.dao.UsuarioDAO;
import ec.edu.ups.poo.dao.impl.CarritoDAOMemoria;
import ec.edu.ups.poo.dao.impl.ProductoDAOMemoria;
import ec.edu.ups.poo.dao.impl.UsuarioDAOMemoria;
import ec.edu.ups.poo.modelo.Usuario;
import ec.edu.ups.poo.modelo.Rol;
import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.vista.*;
import ec.edu.ups.poo.vista.carrito.CarritoAnadirView;
import ec.edu.ups.poo.vista.carrito.CarritoEditarView;
import ec.edu.ups.poo.vista.carrito.CarritoEliminarView;
import ec.edu.ups.poo.vista.carrito.CarritoListarView;
import ec.edu.ups.poo.vista.inicio.LogInView;
import ec.edu.ups.poo.vista.inicio.RegisterView;
import ec.edu.ups.poo.vista.producto.ProducUpdateView;
import ec.edu.ups.poo.vista.producto.ProductDelateView;
import ec.edu.ups.poo.vista.producto.ProductoAnadirView;
import ec.edu.ups.poo.vista.producto.ProductoListarView;
import ec.edu.ups.poo.vista.usuario.UsuarioCrearView;
import ec.edu.ups.poo.vista.usuario.UsuarioEditarView;
import ec.edu.ups.poo.vista.usuario.UsuarioElimiarView;
import ec.edu.ups.poo.vista.usuario.UsuarioListarView;

public class Main {
    private static UsuarioController usuarioController;
    public static UsuarioDAO usuarioDAO;
    public static ProductoDAO productoDAO;
    public static CarritoDAO carritoDAO;

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            usuarioDAO = new UsuarioDAOMemoria();
            productoDAO = new ProductoDAOMemoria();
            carritoDAO = new CarritoDAOMemoria();
            mostrarLogin();
        });
    }

    public static void mostrarLogin() {
        MensajeInternacionalizacionHandler i18n = new MensajeInternacionalizacionHandler("es", "EC");
        LogInView logInView = new LogInView(i18n);
        usuarioController = new UsuarioController(usuarioDAO, logInView);

        logInView.actualizarOpcionesIdioma();

        logInView.getCbxIdioma().addActionListener(e -> {
            int selectedIndex = logInView.getCbxIdioma().getSelectedIndex();
            switch (selectedIndex) {
                case 0:
                    i18n.setLenguaje("es", "EC");
                    break;
                case 1:
                    i18n.setLenguaje("en", "US");
                    break;
                case 2:
                    i18n.setLenguaje("fr", "FR");
                    break;
                default:
                    i18n.setLenguaje("en", "US");
            }
            logInView.aplicarIdioma();
            logInView.actualizarOpcionesIdioma();
        });

        logInView.getBtnRegister().addActionListener(e -> {
            RegisterView registerView = new RegisterView();
            new RegisterController(usuarioDAO, registerView);
            registerView.setVisible(true);
        });

        logInView.getBtnExit().addActionListener(e -> {
            System.exit(0);
        });

        logInView.setVisible(true);
    }

    public static void mostrarMenuPrincipal(Usuario usuarioAutenticado) {
        MenuPrincipalView principalView = new MenuPrincipalView(usuarioAutenticado);

        // PRODUCTO
        ProductoAnadirView productoAnadirView = new ProductoAnadirView();
        ProductoListarView productoListaView = new ProductoListarView();
        ProducUpdateView productoGestionView = new ProducUpdateView();
        ProductDelateView productDelateView = new ProductDelateView();

        // CARRITO
        CarritoAnadirView carritoAnadirView = new CarritoAnadirView();
        CarritoEditarView carritoEditarView = new CarritoEditarView();
        carritoEditarView.init();
        CarritoEliminarView carritoEliminarView = new CarritoEliminarView();
        CarritoListarView carritoListarView = new CarritoListarView();

        // USUARIO
        UsuarioCrearView usuarioCrearView = new UsuarioCrearView();
        UsuarioListarView usuarioListarView = new UsuarioListarView();
        UsuarioEditarView usuarioEditarView = new UsuarioEditarView();
        UsuarioElimiarView usuarioElimiarView = new UsuarioElimiarView();

        new ProductoController(productoDAO, productoAnadirView, productoListaView, productoGestionView, productDelateView, carritoAnadirView);
        CarritoController carritoController = new CarritoController(carritoDAO, productoDAO, carritoAnadirView, carritoEditarView, usuarioAutenticado);

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
            if (!productDelateView.isVisible()) {
                productDelateView.setVisible(true);
                principalView.getjDesktopPane().add(productDelateView);
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
            if (!usuarioCrearView.isVisible()) {
                usuarioController.configurarUsuarioCrearView(usuarioCrearView);
                usuarioCrearView.setVisible(true);
                principalView.getjDesktopPane().add(usuarioCrearView);
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

        principalView.setVisible(true);
    }
}