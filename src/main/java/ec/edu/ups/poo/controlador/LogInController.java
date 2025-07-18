package ec.edu.ups.poo.controlador;

import ec.edu.ups.poo.dao.CarritoDAO;
import ec.edu.ups.poo.dao.ProductoDAO;
import ec.edu.ups.poo.dao.UsuarioDAO;
import ec.edu.ups.poo.dao.PreguntaDAO;
import ec.edu.ups.poo.dao.impl.DAODireccion;
import ec.edu.ups.poo.dao.impl.texto.CarritoDAOArchivoTexto;
import ec.edu.ups.poo.dao.impl.texto.UsuarioDAOArchivoTexto;
import ec.edu.ups.poo.modelo.Pregunta;
import ec.edu.ups.poo.modelo.Producto;
import ec.edu.ups.poo.modelo.Usuario;
import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.vista.inicio.LogInView;
import ec.edu.ups.poo.vista.inicio.RegisterView;
import ec.edu.ups.poo.vista.preguntas.PreguntasValidacionView;

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
    private int tipoAlmacenamientoIndex = 0;

    public LogInController(LogInView logInView, MensajeInternacionalizacionHandler i18n) {
        this.logInView = logInView;
        this.i18n = i18n;
        tipoAlmacenamientoIndex = logInView.getCbxUbicacionGuardar().getSelectedIndex();
        rutaCarpetaDatos = logInView.getTxtRuta().getText().trim();

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

        logInView.getCbxUbicacionGuardar().addActionListener(e -> {
            tipoAlmacenamientoIndex = logInView.getCbxUbicacionGuardar().getSelectedIndex();
        });
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

    private void inicializarDAOs() {
        int tipoAlmacenamiento = tipoAlmacenamientoIndex;
        String rutaActual = logInView.getTxtRuta().getText().trim();

        if ((tipoAlmacenamiento == 1 || tipoAlmacenamiento == 2) && rutaActual.isEmpty()) {
            rutaActual = "datos";
        }
        rutaCarpetaDatos = rutaActual;

        preguntaDAO = DAODireccion.getPreguntaDAO(tipoAlmacenamiento, rutaCarpetaDatos, i18n);
        List<Pregunta> preguntas = preguntaDAO.listarTodas();
        usuarioDAO = DAODireccion.getUsuarioDAO(tipoAlmacenamiento, rutaCarpetaDatos, preguntas);
        productoDAO = DAODireccion.getProductoDAO(tipoAlmacenamiento, rutaCarpetaDatos);

        List<Usuario> usuarios = usuarioDAO.listarUsuariosTodos();
        List<Producto> productos = productoDAO.listarTodos();
        carritoDAO = DAODireccion.getCarritoDAO(tipoAlmacenamiento, rutaCarpetaDatos, productos, usuarios);

        if (usuarioDAO instanceof UsuarioDAOArchivoTexto && carritoDAO instanceof CarritoDAOArchivoTexto) {
            ((UsuarioDAOArchivoTexto) usuarioDAO)
                    .asociarCarritosAUsuarios((CarritoDAOArchivoTexto) carritoDAO);
        }
    }

    private void configurarLogin() {
        inicializarDAOs();

        String cedula = logInView.getTxtUserName().getText().trim();
        String contrasena = logInView.getTxtContrasena().getText().trim();

        Usuario usuario = usuarioDAO.autenticarUsuario(cedula, contrasena);

        if (usuario == null) {
            logInView.mostrarMensajeAlert(
                    i18n.get("login.error.usuario_o_contrasena"),
                    i18n.get("global.warning"),
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (usuario.getPreguntaValidacion() == null || usuario.getPreguntaValidacion().isEmpty()) {
            logInView.mostrarMensaje(
                    i18n.get("login.warning.llene_preguntas_validacion"),
                    i18n.get("global.info"),
                    JOptionPane.INFORMATION_MESSAGE
            );
            PreguntasValidacionView preguntasView = new PreguntasValidacionView(usuario, usuarioDAO, i18n);
            new PreguntaValidacionController(usuario, usuarioDAO, preguntaDAO, productoDAO, carritoDAO, preguntasView, i18n, rutaCarpetaDatos, tipoAlmacenamientoIndex);
            preguntasView.setVisible(true);
            logInView.dispose();
            return;
        }

        new MenuPrincipalController(
                usuario, usuarioDAO, preguntaDAO, productoDAO, carritoDAO,
                i18n, rutaCarpetaDatos, tipoAlmacenamientoIndex
        );
        logInView.dispose();
    }

    private void configurarRegistro() {
        inicializarDAOs();
        RegisterView registerView = new RegisterView(i18n);
        new RegisterController(
                usuarioDAO, preguntaDAO, productoDAO, carritoDAO, registerView, i18n,
                rutaCarpetaDatos, tipoAlmacenamientoIndex
        );
        registerView.setVisible(true);
        logInView.dispose();
    }

    private void configurarRecuperacion() {
        inicializarDAOs();

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
        new PreguntaRecuperacionController(usuario, usuarioDAO, preguntaDAO, productoDAO, carritoDAO, preguntasView, i18n, rutaCarpetaDatos, tipoAlmacenamientoIndex);
        preguntasView.setVisible(true);
        logInView.dispose();
    }

    private void configurarSalir() {
        int respuesta = logInView.mostrarMensajeAlert(
                "\n\t" + i18n.get("mensaje.confirmacion.salir"),
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
            case 0:
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

                PreguntaDAO tempPreguntaDAO = DAODireccion.getPreguntaDAO(selectedIndex, ruta, i18n);
                List<Pregunta> preguntas = tempPreguntaDAO.listarTodas();
                UsuarioDAO tempUsuarioDAO = DAODireccion.getUsuarioDAO(selectedIndex, ruta, preguntas);

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

    public String getRutaCarpetaDatos() { return rutaCarpetaDatos; }
    public int getTipoAlmacenamientoIndex() { return tipoAlmacenamientoIndex; }
}