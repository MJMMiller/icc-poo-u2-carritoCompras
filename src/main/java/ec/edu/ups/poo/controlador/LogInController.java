package ec.edu.ups.poo.controlador;

import ec.edu.ups.poo.dao.CarritoDAO;
import ec.edu.ups.poo.dao.ProductoDAO;
import ec.edu.ups.poo.dao.UsuarioDAO;
import ec.edu.ups.poo.dao.PreguntaDAO;
import ec.edu.ups.poo.dao.impl.DAODireccion;
import ec.edu.ups.poo.modelo.Pregunta;
import ec.edu.ups.poo.modelo.Producto;
import ec.edu.ups.poo.modelo.Usuario;
import ec.edu.ups.poo.vista.inicio.LogInView;
import ec.edu.ups.poo.vista.inicio.RegisterView;
import ec.edu.ups.poo.vista.preguntas.PreguntasValidacionView;
import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.util.List;

public class LogInController {

    private UsuarioDAO usuarioDAO;
    private PreguntaDAO preguntaDAO;
    private ProductoDAO productoDAO;
    private CarritoDAO carritoDAO;
    private final LogInView logInView;
    private final MensajeInternacionalizacionHandler i18n;
    private String rutaCarpetaDatos = "";

    public LogInController(LogInView logInView, MensajeInternacionalizacionHandler i18n) {
        this.logInView = logInView;
        this.i18n = i18n;
        configurarEventos();
        logInView.aplicarIdioma();
    }

    public LogInController(UsuarioDAO usuarioDAO, PreguntaDAO preguntaDAO, ProductoDAO productoDAO, CarritoDAO carritoDAO, LogInView logInView, MensajeInternacionalizacionHandler i18n) {
        this.usuarioDAO = usuarioDAO;
        this.preguntaDAO = preguntaDAO;
        this.productoDAO = productoDAO;
        this.carritoDAO = carritoDAO;
        this.logInView = logInView;
        this.i18n = i18n;
        configurarEventos();
        logInView.aplicarIdioma();
    }

    private void configurarEventos() {
        logInView.getBtnExit().addActionListener(e -> configurarSalir());
        logInView.getBtnRecuContra().addActionListener(e -> configurarRecuperacion());
        logInView.getBtnRegister().addActionListener(e -> configurarRegistro());
        logInView.getBtnLogIn().addActionListener(e -> configurarLogin());
        logInView.getCbxIdioma().addActionListener(e -> cambioDeIdiomaDesdeCbx());
        logInView.getBtnUbicacion().addActionListener(e -> abrirUbicacionGuardar());
    }

    private void cambioDeIdiomaDesdeCbx() {
        int selectedIndex = logInView.getCbxIdioma().getSelectedIndex();
        switch (selectedIndex) {
            case 0: i18n.setLenguaje("es", "EC"); break;
            case 1: i18n.setLenguaje("en", "US"); break;
            case 2: i18n.setLenguaje("fr", "FR"); break;
            default: i18n.setLenguaje("es", "EC");
        }
        logInView.aplicarIdioma();
    }

    private void configurarLogin() {
        int tipoAlmacenamiento = logInView.getCbxUbicacionGuardar().getSelectedIndex(); // 0=memoria, 1=texto, 2=binario
        String memoria = logInView.getTxtRuta().getText().trim();

        if ((tipoAlmacenamiento == 1 || tipoAlmacenamiento == 2) && memoria.isEmpty()) {
            memoria = "datos";
        }
        rutaCarpetaDatos = memoria;

        preguntaDAO = DAODireccion.getPreguntaDAO(tipoAlmacenamiento, rutaCarpetaDatos, i18n);
        List<Pregunta> preguntas = preguntaDAO.listarTodas();

        usuarioDAO = DAODireccion.getUsuarioDAO(tipoAlmacenamiento, rutaCarpetaDatos, preguntas);
        productoDAO = DAODireccion.getProductoDAO(tipoAlmacenamiento, rutaCarpetaDatos);

        List<Usuario> usuarios = usuarioDAO.listarUsuariosTodos();
        List<Producto> productos = productoDAO.listarTodos();

        carritoDAO = DAODireccion.getCarritoDAO(tipoAlmacenamiento, rutaCarpetaDatos, productos, usuarios);

        String user = logInView.getTxtUserName().getText();
        String pass = logInView.getTxtContrasena().getText();
        Usuario usuario = usuarioDAO.autenticarUsuario(user, pass);

        if (usuario == null) {
            logInView.mostrarMensaje(
                    i18n.get("login.error.usuario_o_contrasena"),
                    i18n.get("global.error"),
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        new MenuPrincipalController(usuario, usuarioDAO, preguntaDAO, productoDAO, carritoDAO, i18n);
        logInView.dispose();
    }

    private void configurarRegistro() {
        int tipoAlmacenamiento = logInView.getCbxUbicacionGuardar().getSelectedIndex();
        String memoria = logInView.getTxtRuta().getText().trim();
        if ((tipoAlmacenamiento == 1 || tipoAlmacenamiento == 2) && memoria.isEmpty()) {
            memoria = "datos";
        }
        rutaCarpetaDatos = memoria;

        preguntaDAO = DAODireccion.getPreguntaDAO(tipoAlmacenamiento, rutaCarpetaDatos, i18n);
        List<Pregunta> preguntas = preguntaDAO.listarTodas();

        usuarioDAO = DAODireccion.getUsuarioDAO(tipoAlmacenamiento, rutaCarpetaDatos, preguntas);
        productoDAO = DAODireccion.getProductoDAO(tipoAlmacenamiento, rutaCarpetaDatos);

        List<Usuario> usuarios = usuarioDAO.listarUsuariosTodos();
        List<Producto> productos = productoDAO.listarTodos();

        carritoDAO = DAODireccion.getCarritoDAO(tipoAlmacenamiento, rutaCarpetaDatos, productos, usuarios);

        RegisterView registerView = new RegisterView(i18n);
        new RegisterController(usuarioDAO, preguntaDAO, productoDAO, carritoDAO, registerView, i18n);
        registerView.setVisible(true);
        logInView.dispose();
    }

    private void configurarRecuperacion() {
        int tipoAlmacenamiento = logInView.getCbxUbicacionGuardar().getSelectedIndex();
        String memoria = logInView.getTxtRuta().getText().trim();
        if ((tipoAlmacenamiento == 1 || tipoAlmacenamiento == 2) && memoria.isEmpty()) {
            memoria = "datos";
        }
        rutaCarpetaDatos = memoria;

        preguntaDAO = DAODireccion.getPreguntaDAO(tipoAlmacenamiento, rutaCarpetaDatos, i18n);
        List<Pregunta> preguntas = preguntaDAO.listarTodas();

        usuarioDAO = DAODireccion.getUsuarioDAO(tipoAlmacenamiento, rutaCarpetaDatos, preguntas);
        productoDAO = DAODireccion.getProductoDAO(tipoAlmacenamiento, rutaCarpetaDatos);

        List<Usuario> usuarios = usuarioDAO.listarUsuariosTodos();
        List<Producto> productos = productoDAO.listarTodos();

        carritoDAO = DAODireccion.getCarritoDAO(tipoAlmacenamiento, rutaCarpetaDatos, productos, usuarios);

        String username = logInView.getTxtUserName().getText().trim();
        if (username.isEmpty()) {
            logInView.mostrarMensaje(
                    i18n.get("login.warning.ingrese_usuario"),
                    i18n.get("global.warning"),
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        Usuario usuario = usuarioDAO.buscarUsuario(username);
        if (usuario == null) {
            logInView.mostrarMensaje(
                    i18n.get("login.error.usuario_no_encontrado"),
                    i18n.get("global.error"),
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        if (usuario.getPreguntaValidacion() == null || usuario.getPreguntaValidacion().isEmpty()) {
            logInView.mostrarMensaje(
                    i18n.get("login.error.usuario_sin_preguntas"),
                    i18n.get("global.error"),
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        PreguntasValidacionView preguntasView = new PreguntasValidacionView(usuario, usuarioDAO, i18n);
        new PreguntaRecuperacionController(usuario, usuarioDAO, preguntaDAO, productoDAO, carritoDAO, preguntasView, i18n);
        preguntasView.setVisible(true);
        logInView.dispose();
    }

    private void configurarSalir() {
        int respuesta = logInView.mostrarMensajeAlert("\n\t" +
                        i18n.get("mensaje.confirmacion.salir"),
                i18n.get("global.info"),
                JOptionPane.QUESTION_MESSAGE
        );
        if (respuesta == JOptionPane.OK_OPTION || respuesta == 0) {
            System.out.println(i18n.get("login.gracias.salir"));
            System.exit(0);
        }
    }

    public void abrirUbicacionGuardar() {
        int selectedIndex = logInView.getCbxUbicacionGuardar().getSelectedIndex();
        switch (selectedIndex) {
            case 0: // Memoria
                logInView.mostrarMensaje(
                        i18n.get("dao.memoria.seleccionado"),
                        i18n.get("global.info"),
                        JOptionPane.INFORMATION_MESSAGE
                );
                logInView.getTxtRuta().setText("");
                break;

            case 1: // Texto
            case 2: // Binario
                logInView.seleccionarDirectorio();
                String ruta = logInView.getTxtRuta().getText().trim();
                crearArchivosEnRuta(ruta, selectedIndex);
                logInView.mostrarMensaje(
                        i18n.get("login.info.archivos_creados") + "\n" + ruta,
                        i18n.get("global.info"),
                        JOptionPane.INFORMATION_MESSAGE
                );
                break;

            default:
                logInView.mostrarMensaje(
                        i18n.get("error.opcion.no.valida"),
                        i18n.get("global.error"),
                        JOptionPane.ERROR_MESSAGE
                );
        }
    }

    private void crearArchivosEnRuta(String ruta, int tipo) {
        try {
            if (ruta == null || ruta.isEmpty()) return;
            java.io.File carpeta = new java.io.File(ruta);
            if (!carpeta.exists()) carpeta.mkdirs();

            if (tipo == 1) { // Texto
                crearArchivoSiNoExiste(ruta + "/usuarios.txt");
                crearArchivoSiNoExiste(ruta + "/productos.txt");
                crearArchivoSiNoExiste(ruta + "/carritos.txt");
            } else if (tipo == 2) { // Binario
                crearArchivoSiNoExiste(ruta + "/usuarios.dat");
                crearArchivoSiNoExiste(ruta + "/productos.dat");
                crearArchivoSiNoExiste(ruta + "/carritos.dat");
            }
        } catch (Exception ex) {
            logInView.mostrarMensaje(
                    "Error creando archivos: " + ex.getMessage(),
                    i18n.get("global.error"),
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void crearArchivoSiNoExiste(String path) throws java.io.IOException {
        java.io.File archivo = new java.io.File(path);
        if (!archivo.exists()) {
            archivo.createNewFile();
        }
    }
}