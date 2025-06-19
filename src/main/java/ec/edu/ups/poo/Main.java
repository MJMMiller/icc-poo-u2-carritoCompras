package ec.edu.ups.poo;

import ec.edu.ups.poo.controlador.CarritoController;
import ec.edu.ups.poo.controlador.ProductoController;
import ec.edu.ups.poo.dao.CarritoDAO;
import ec.edu.ups.poo.dao.ProductoDAO;
import ec.edu.ups.poo.dao.impl.CarritoDAOMemoria;
import ec.edu.ups.poo.dao.impl.ProductoDAOMemoria;
import ec.edu.ups.poo.vista.*;

public class Main{
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {

            ProductoDAO productoDAO = new ProductoDAOMemoria();
            CarritoDAO carritoDAO = new CarritoDAOMemoria();

            MenuPrincipalView principalView = new MenuPrincipalView();

            ProductoAnadirView productoAnadirView = new ProductoAnadirView();
            ProductoListarView productoListaView = new ProductoListarView();
            ProducUpdateView productoGestionView = new ProducUpdateView();
            ProductDelateView productDelateView = new ProductDelateView();

            CarritoAnadirView carritoAnadirView = new CarritoAnadirView();

            new ProductoController(productoDAO, productoAnadirView, productoListaView, productoGestionView, productDelateView ,carritoAnadirView);
            new CarritoController(carritoDAO, productoDAO, carritoAnadirView);

            principalView.getMenuItemCrearProducto().addActionListener(e -> {
                if(!productoAnadirView.isVisible()){
                    productoAnadirView.setVisible(true);
                    principalView.getjDesktopPane().add(productoAnadirView);
                }
            });

            principalView.getMenuItemBuscarProducto().addActionListener(e -> {
                if(!productoListaView.isVisible()){
                    productoListaView.setVisible(true);
                    principalView.getjDesktopPane().add(productoListaView);
                }
            });

            principalView.getMenuItemCrearCarrito().addActionListener(e -> {
                if(!carritoAnadirView.isVisible()){
                    carritoAnadirView.setVisible(true);
                    principalView.getjDesktopPane().add(carritoAnadirView);
                }
            });

            principalView.getMenuItemEditarProducto().addActionListener(e -> {
                if(!productoGestionView.isVisible()){
                    productoGestionView.setVisible(true);
                    principalView.getjDesktopPane().add(productoGestionView);
                }
            });

            principalView.getMenuItemEliminarProducto().addActionListener(e -> {
                if(!productDelateView.isVisible()){
                    productDelateView.setVisible(true);
                    principalView.getjDesktopPane().add(productDelateView);
                }
            });
        });
    }
}