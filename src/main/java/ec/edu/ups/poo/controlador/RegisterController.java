package ec.edu.ups.poo.controlador;

import ec.edu.ups.poo.dao.CarritoDAO;
import ec.edu.ups.poo.dao.ProductoDAO;
import ec.edu.ups.poo.dao.UsuarioDAO;
import ec.edu.ups.poo.dao.PreguntaDAO;
import ec.edu.ups.poo.modelo.Rol;
import ec.edu.ups.poo.modelo.Usuario;
import ec.edu.ups.poo.modelo.Pregunta;
import ec.edu.ups.poo.modelo.PreguntaUsuario;
import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.vista.inicio.RegisterView;
import ec.edu.ups.poo.vista.inicio.LogInView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * Controlador para el registro de nuevos usuarios.
 * Permite validar los datos ingresados, asignar preguntas de seguridad aleatorias,
 * guardar el usuario en el sistema y gestionar la internacionalización de la vista.
 */
public class RegisterController {
    private final UsuarioDAO usuarioDAO;
    private final PreguntaDAO preguntaDAO;
    private final ProductoDAO productoDAO;
    private final RegisterView registerView;
    private final CarritoDAO carritoDAO;
    private final List<Pregunta> preguntasRandom;
    private final MensajeInternacionalizacionHandler i18n;

    // NUEVO: para recordar la ruta y el tipo de almacenamiento
    private final String rutaCarpetaDatos;
    private final int tipoAlmacenamientoIndex;

    /**
     * Constructor de RegisterController.
     * Inicializa DAOs, vista, manejador de internacionalización, ruta y tipo de almacenamiento.
     * Configura los eventos y muestra las preguntas de seguridad en la vista.
     *
     * @param usuarioDAO DAO para operaciones de usuario.
     * @param preguntaDAO DAO para operaciones de preguntas de seguridad.
     * @param productoDAO DAO para operaciones de producto.
     * @param carritoDAO DAO para operaciones de carrito.
     * @param registerView Vista de registro de usuario.
     * @param i18n Manejador de internacionalización de mensajes.
     * @param rutaCarpetaDatos Ruta de la carpeta de datos.
     * @param tipoAlmacenamientoIndex Índice del tipo de almacenamiento.
     */
    public RegisterController(
            UsuarioDAO usuarioDAO,
            PreguntaDAO preguntaDAO,
            ProductoDAO productoDAO,
            CarritoDAO carritoDAO,
            RegisterView registerView,
            MensajeInternacionalizacionHandler i18n,
            String rutaCarpetaDatos,
            int tipoAlmacenamientoIndex
    ) {
        if (usuarioDAO == null) throw new IllegalArgumentException("usuarioDAO no puede ser nulo");
        if (preguntaDAO == null) throw new IllegalArgumentException("preguntaDAO no puede ser nulo");
        if (productoDAO == null) throw new IllegalArgumentException("productoDAO no puede ser nulo");
        if (carritoDAO == null) throw new IllegalArgumentException("carritoDAO no puede ser nulo");
        if (registerView == null) throw new IllegalArgumentException("registerView no puede ser nulo");
        if (i18n == null) throw new IllegalArgumentException("i18n no puede ser nulo");

        this.usuarioDAO = usuarioDAO;
        this.preguntaDAO = preguntaDAO;
        this.productoDAO = productoDAO;
        this.carritoDAO = carritoDAO;
        this.registerView = registerView;
        this.i18n = i18n;
        this.rutaCarpetaDatos = rutaCarpetaDatos;
        this.tipoAlmacenamientoIndex = tipoAlmacenamientoIndex;

        this.preguntasRandom = getPreguntasRandom();
        configurarEventos();
        mostrarPreguntasEnVista();
        registerView.aplicarIdioma();
    }

    /**
     * Configura los eventos de la vista de registro.
     * Asocia acciones a los botones de registro, limpiar, salir y cambio de idioma.
     * No recibe parámetros ni retorna valores.
     */
    private void configurarEventos() {
        registerView.getBtnRegistro().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarUsuario();
            }
        });
        registerView.getBtnClean().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });
        registerView.getBtnSalir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salir();
            }
        });
        registerView.getCbxIdioma().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambioDeIdiomaDesdeCbx();
            }
        });
    }

    /**
     * Cierra la vista de registro y abre la vista de login.
     * No recibe parámetros ni retorna valores.
     */
    private void salir() {
        registerView.dispose();
        abrirLogin();
    }

    /**
     * Realiza el proceso de registro de usuario.
     * Valida los campos, verifica unicidad, asigna preguntas de seguridad y guarda el usuario.
     * Muestra mensajes de éxito o error según corresponda.
     * No recibe parámetros ni retorna valores.
     */
    private void registrarUsuario() {
        String username = registerView.getTxtUsuario().getText().trim();
        String password = registerView.getTxtContrasena().getText().trim();
        String nombreCompleto = registerView.getTxtNombreCompleto().getText().trim();
        Date fechaNacimiento = registerView.getFechaNacimiento();
        String correo = registerView.getTxtCorreo().getText().trim();
        String telefono = registerView.getTxtTelefono().getText().trim();
        String respuesta1 = registerView.getTxtPregunta1().getText().trim();
        String respuesta2 = registerView.getTxtPregunta2().getText().trim();
        String respuesta3 = registerView.getTxtPregunta3().getText().trim();

        // Validaciones
        if (camposVacios(username, password, respuesta1, respuesta2, respuesta3)) {
            registerView.mostrarMensaje(
                    i18n.get("register.error.llenar_todos"),
                    i18n.get("global.error"),
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        if (!correo.contains("@")) {
            registerView.mostrarMensaje(
                    i18n.get("register.error.correo"),
                    i18n.get("global.error"),
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        if (!telefono.matches("\\d{10}")) {
            registerView.mostrarMensaje(
                    i18n.get("register.error.telefono"),
                    i18n.get("global.error"),
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        if (usuarioDAO.buscarUsuario(username) != null) {
            registerView.mostrarMensaje(
                    i18n.get("register.error.usuario_existe"),
                    i18n.get("global.error"),
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        try {
            Usuario usuario = new Usuario(username, password, Rol.USUARIO, nombreCompleto, fechaNacimiento, correo, telefono);

            List<PreguntaUsuario> preguntasUsuario = new ArrayList<>();
            preguntasUsuario.add(new PreguntaUsuario(preguntasRandom.get(0), respuesta1));
            preguntasUsuario.add(new PreguntaUsuario(preguntasRandom.get(1), respuesta2));
            preguntasUsuario.add(new PreguntaUsuario(preguntasRandom.get(2), respuesta3));
            usuario.setPreguntaValidacion(preguntasUsuario);

            usuarioDAO.crearUsuario(usuario);

            registerView.mostrarMensaje(
                    i18n.get("register.exito.usuario_registrado"),
                    i18n.get("global.success"),
                    JOptionPane.INFORMATION_MESSAGE
            );
            registerView.dispose();
            abrirLogin();
        } catch (Exception ex) {
            registerView.mostrarMensaje(ex.getMessage(), i18n.get("global.error"), JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Muestra las preguntas de seguridad aleatorias en la vista de registro.
     * No recibe parámetros ni retorna valores.
     */
    private void mostrarPreguntasEnVista() {
        registerView.getLblPregunta1().setText(i18n.get(preguntasRandom.get(0).getTexto()));
        registerView.getLblPregunta2().setText(i18n.get(preguntasRandom.get(1).getTexto()));
        registerView.getLblPregunta3().setText(i18n.get(preguntasRandom.get(2).getTexto()));
    }

    /**
     * Limpia todos los campos del formulario de registro.
     * No recibe parámetros ni retorna valores.
     */
    private void limpiarCampos() {
        registerView.getTxtUsuario().setText("");
        registerView.getTxtContrasena().setText("");
        registerView.getTxtPregunta1().setText("");
        registerView.getTxtPregunta2().setText("");
        registerView.getTxtPregunta3().setText("");
        registerView.getTxtNombreCompleto().setText("");
        registerView.getCbxDia().setSelectedIndex(0);
        registerView.getCbxMes().setSelectedIndex(0);
        registerView.getCbxAnio().setSelectedIndex(0);
        registerView.getTxtCorreo().setText("");
        registerView.getTxtTelefono().setText("");
    }

    /**
     * Verifica si alguno de los campos proporcionados está vacío.
     *
     * @param campos Lista de cadenas a verificar.
     * @return true si algún campo está vacío, false en caso contrario.
     */
    private boolean camposVacios(String... campos) {
        for (String campo : campos) {
            if (campo == null || campo.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Obtiene una lista de tres preguntas de seguridad aleatorias.
     *
     * @return Lista de tres preguntas aleatorias.
     * @throws IllegalStateException si no hay suficientes preguntas disponibles.
     */
    private List<Pregunta> getPreguntasRandom() {
        List<Pregunta> lista = preguntaDAO.listarTodas();
        if (lista.size() < 3) throw new IllegalStateException("No hay suficientes preguntas para el registro");
        Collections.shuffle(lista);
        return lista.subList(0, 3);
    }

    /**
     * Cambia el idioma de la vista de registro según la selección del usuario.
     * Aplica el idioma y actualiza las preguntas mostradas.
     * No recibe parámetros ni retorna valores.
     */
    private void cambioDeIdiomaDesdeCbx() {
        int selectedIndex = registerView.getCbxIdioma().getSelectedIndex();
        switch (selectedIndex) {
            case 0: i18n.setLenguaje("es", "EC"); break;
            case 1: i18n.setLenguaje("en", "US"); break;
            case 2: i18n.setLenguaje("fr", "FR"); break;
            default: i18n.setLenguaje("es", "EC");
        }
        registerView.aplicarIdioma();
        mostrarPreguntasEnVista();
    }

    /**
     * Abre la vista de login y aplica la configuración de ruta y tipo de almacenamiento.
     * No recibe parámetros ni retorna valores.
     */
    private void abrirLogin() {
        SwingUtilities.invokeLater(() -> {
            LogInView logInView = new LogInView(i18n);
            logInView.getTxtRuta().setText(rutaCarpetaDatos);
            logInView.getCbxUbicacionGuardar().setSelectedIndex(tipoAlmacenamientoIndex);
            logInView.setVisible(true);
            new LogInController(logInView, i18n);
        });
    }
}