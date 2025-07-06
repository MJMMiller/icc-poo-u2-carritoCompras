package ec.edu.ups.poo;

import ec.edu.ups.poo.controlador.*;
import ec.edu.ups.poo.dao.CarritoDAO;
import ec.edu.ups.poo.dao.ProductoDAO;
import ec.edu.ups.poo.dao.PreguntaDAO;
import ec.edu.ups.poo.dao.UsuarioDAO;
import ec.edu.ups.poo.dao.impl.CarritoDAOMemoria;
import ec.edu.ups.poo.dao.impl.ProductoDAOMemoria;
import ec.edu.ups.poo.dao.impl.PreguntaDAOMemoria;
import ec.edu.ups.poo.dao.impl.UsuarioDAOMemoria;
import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.vista.inicio.LogInView;

public class Main {
    public static UsuarioDAO usuarioDAO;
    public static ProductoDAO productoDAO;
    public static CarritoDAO carritoDAO;
    public static PreguntaDAO preguntaDAO;
    public static MensajeInternacionalizacionHandler i18n;

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            i18n = new MensajeInternacionalizacionHandler("es", "EC");
            preguntaDAO = new PreguntaDAOMemoria(i18n);
            usuarioDAO = new UsuarioDAOMemoria(preguntaDAO.listarTodas());
            productoDAO = new ProductoDAOMemoria();
            carritoDAO = new CarritoDAOMemoria();

            LogInView logInView = new LogInView(i18n);
            new LogInController(
                    usuarioDAO, preguntaDAO, productoDAO, carritoDAO, logInView, i18n
            );
            logInView.setVisible(true);
        });
    }
}