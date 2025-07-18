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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Controlador para la gestión de inicio de sesión, registro y recuperación de usuarios.
 * Administra la inicialización de DAOs y la interacción con las vistas de autenticación.
 */
public class LogInController {

    private UsuarioDAO usuarioDAO;
    private PreguntaDAO preguntaDAO;
    private ProductoDAO productoDAO;
    private CarritoDAO carritoDAO;
    private final LogInView logInView;
    private final MensajeInternacionalizacionHandler i18n;
    private String rutaCarpetaDatos = "";
    private int tipoAlmacenamientoIndex = 0;

    /**
     * Constructor de LogInController.
     * Inicializa la vista de login y el manejador de internacionalización.
     * Configura los eventos y aplica el idioma seleccionado.
     *
     * @param logInView Vista de inicio de sesión.
     * @param i18n Manejador de internacionalización de mensajes.
     */
    public LogInController(LogInView logInView, MensajeInternacionalizacionHandler i18n) {
        this.logInView = logInView;
        this.i18n = i18n;
        tipoAlmacenamientoIndex = logInView.getCbxUbicacionGuardar().getSelectedIndex();
        rutaCarpetaDatos = logInView.getTxtRuta().getText().trim();

        configurarEventos();
        logInView.aplicarIdioma();
    }

    /**
     * Configura los eventos de la vista de login, como botones y selección de idioma.
     * No recibe parámetros ni retorna valores.
     */
    private void configurarEventos() {
        logInView.getBtnExit().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                configurarSalir();
            }
        });
        logInView.getBtnRecuContra().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                configurarRecuperacion();
            }
        });
        logInView.getBtnRegister().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                configurarRegistro();
            }
        });
        logInView.getBtnLogIn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                configurarLogin();
            }
        });
        logInView.getCbxIdioma().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambioDeIdiomaDesdeCbx();
            }
        });
        logInView.getBtnUbicacion().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirUbicacionGuardar();
            }
        });

        logInView.getCbxUbicacionGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tipoAlmacenamientoIndex = logInView.getCbxUbicacionGuardar().getSelectedIndex();
            }
        });
    }

    /**
     * Cambia el idioma de la aplicación según la selección del usuario.
     * No recibe parámetros ni retorna valores.
     */
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

    /**
     * Inicializa los DAOs necesarios para la autenticación, registro y recuperación.
     * Configura las rutas y tipos de almacenamiento según la selección del usuario.
     * No recibe parámetros ni retorna valores.
     */
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

    /**
     * Configura el proceso de inicio de sesión.
     * Autentica al usuario y redirige según el estado de sus preguntas de validación.
     * No recibe parámetros ni retorna valores.
     */
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

    /**
     * Configura el proceso de registro de un nuevo usuario.
     * Inicializa la vista y el controlador de registro.
     * No recibe parámetros ni retorna valores.
     */
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

    /**
     * Configura el proceso de recuperación de contraseña.
     * Verifica la existencia del usuario y sus preguntas de validación.
     * No recibe parámetros ni retorna valores.
     */
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

    /**
     * Configura la acción de salir de la aplicación.
     * Muestra un mensaje de confirmación antes de cerrar.
     * No recibe parámetros ni retorna valores.
     */
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

    /**
     * Abre el selector de ubicación para guardar los datos según el tipo de almacenamiento.
     * Crea los archivos necesarios en la ruta seleccionada.
     * No recibe parámetros ni retorna valores.
     */
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

    /**
     * Crea los archivos necesarios en la ruta especificada según el tipo de almacenamiento.
     *
     * @param ruta Ruta donde se crearán los archivos.
     * @param tipo Tipo de almacenamiento (1: texto, 2: binario).
     */
    private void crearArchivosEnRuta(String ruta, int tipo) {
        try {
            if (ruta == null || ruta.isEmpty()) return;
            java.io.File carpeta = new java.io.File(ruta);
            if (!carpeta.exists()) carpeta.mkdirs();

            if (tipo == 1) { // Texto
                crearArchivoSiNoExiste(ruta + "/usuarios.txt");
                crearArchivoSiNoExiste(ruta + "/productos.txt");
                crearArchivoSiNoExiste(ruta + "/carritos.txt");
                crearArchivoSiNoExiste(ruta + "/preguntas.txt");
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

    /**
     * Crea un archivo en la ruta especificada si no existe.
     *
     * @param path Ruta completa del archivo.
     * @throws java.io.IOException Si ocurre un error al crear el archivo.
     */
    private void crearArchivoSiNoExiste(String path) throws java.io.IOException {
        java.io.File archivo = new java.io.File(path);
        if (!archivo.exists()) {
            archivo.createNewFile();
        }
    }

    /**
     * Obtiene la ruta de la carpeta de datos seleccionada.
     *
     * @return Ruta de la carpeta de datos.
     */
    public String getRutaCarpetaDatos() { return rutaCarpetaDatos; }

    /**
     * Obtiene el índice del tipo de almacenamiento seleccionado.
     *
     * @return Índice del tipo de almacenamiento.
     */
    public int getTipoAlmacenamientoIndex() { return tipoAlmacenamientoIndex; }
}