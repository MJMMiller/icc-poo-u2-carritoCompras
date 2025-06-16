package ec.edu.ups.poo.vista;

import ec.edu.ups.poo.controlador.ProductoController;
import ec.edu.ups.poo.dao.ProductoDAO;
import ec.edu.ups.poo.dao.impl.ProductoDAOMemoria;

public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            ProducEditarView productoGestionView = new ProducEditarView();
            ProductoListarView productoListaView = new ProductoListarView();
            ProductoAnadirView productoView = new ProductoAnadirView();

            ProductoDAO productoDAO = new ProductoDAOMemoria();

            new ProductoController(productoDAO, productoView, productoListaView, productoGestionView);
        });
    }
}