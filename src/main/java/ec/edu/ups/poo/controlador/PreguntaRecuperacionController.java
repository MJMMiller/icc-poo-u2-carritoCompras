package ec.edu.ups.poo.controlador;

import ec.edu.ups.poo.dao.CarritoDAO;
import ec.edu.ups.poo.dao.PreguntaDAO;
import ec.edu.ups.poo.dao.ProductoDAO;
import ec.edu.ups.poo.dao.UsuarioDAO;
import ec.edu.ups.poo.modelo.Usuario;
import ec.edu.ups.poo.modelo.PreguntaUsuario;
import ec.edu.ups.poo.vista.inicio.LogInView;
import ec.edu.ups.poo.vista.preguntas.PreguntasValidacionView;
import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Controlador para la recuperación de contraseña mediante preguntas de seguridad.
 * Permite validar respuestas, gestionar intentos y cambiar la contraseña del usuario.
 */
public class PreguntaRecuperacionController {

    private final Usuario usuario;
    private final UsuarioDAO usuarioDAO;
    private final PreguntaDAO preguntaDAO;
    private final ProductoDAO productoDAO;
    private final CarritoDAO carritoDAO;
    private final PreguntasValidacionView preguntasView;
    private final MensajeInternacionalizacionHandler i18n;
    private final List<PreguntaUsuario> preguntasGuardadas;

    // Para recordar el modo y ruta de almacenamiento
    private final String rutaCarpetaDatos;
    private final int tipoAlmacenamientoIndex;

    private int preguntaActual = 0;
    private int cicloIntentos = 0;
    private final int intentos = 3;
    private boolean puedeCambiarContrasena = false;

    /**
     * Constructor de PreguntaRecuperacionController.
     * Inicializa DAOs, vistas, usuario y configuración de almacenamiento.
     * Configura la vista y los eventos, y muestra la primera pregunta.
     *
     * @param usuario Usuario que recupera la contraseña.
     * @param usuarioDAO DAO para operaciones de usuario.
     * @param preguntaDAO DAO para operaciones de preguntas.
     * @param productoDAO DAO para operaciones de producto.
     * @param carritoDAO DAO para operaciones de carrito.
     * @param preguntasView Vista de validación de preguntas.
     * @param i18n Manejador de internacionalización de mensajes.
     * @param rutaCarpetaDatos Ruta de la carpeta de datos.
     * @param tipoAlmacenamientoIndex Índice del tipo de almacenamiento.
     */
    public PreguntaRecuperacionController(
            Usuario usuario,
            UsuarioDAO usuarioDAO,
            PreguntaDAO preguntaDAO,
            ProductoDAO productoDAO,
            CarritoDAO carritoDAO,
            PreguntasValidacionView preguntasView,
            MensajeInternacionalizacionHandler i18n,
            String rutaCarpetaDatos,
            int tipoAlmacenamientoIndex
    ) {
        this.usuario = usuario;
        this.usuarioDAO = usuarioDAO;
        this.preguntaDAO = preguntaDAO;
        this.productoDAO = productoDAO;
        this.carritoDAO = carritoDAO;
        this.preguntasView = preguntasView;
        this.i18n = i18n;
        this.preguntasGuardadas = usuario.getPreguntaValidacion();
        this.rutaCarpetaDatos = rutaCarpetaDatos;
        this.tipoAlmacenamientoIndex = tipoAlmacenamientoIndex;

        inicializarVista();
        configurarEventos();
        mostrarPreguntaActual();
        preguntasView.aplicarIdiomas();
    }

    /**
     * Inicializa la vista de preguntas de recuperación, ocultando y mostrando los campos necesarios.
     * No recibe parámetros ni retorna valores.
     */
    private void inicializarVista() {
        preguntaActual = 0;
        cicloIntentos = 0;
        puedeCambiarContrasena = false;

        preguntasView.getLblNuevaContra().setVisible(false);
        preguntasView.getTxtNuevaContra().setVisible(false);

        preguntasView.getLblPreguntaSeguridad().setVisible(false);
        preguntasView.getCbxPreguntas().setVisible(false);
        preguntasView.getLblQuestion().setVisible(false);
        preguntasView.getTxtRespuestaSeguidad().setVisible(false);

        preguntasView.getLblPregunta().setVisible(true);
        preguntasView.getLblPregunta().setEnabled(true);
        preguntasView.getTxtRespuestComparar().setVisible(true);
        preguntasView.getBtnsiguientePregunta().setVisible(true);

        preguntasView.getTxtRespuestComparar().setEnabled(true);
        preguntasView.getTxtRespuestComparar().setEditable(true);
    }

    /**
     * Configura los eventos de la vista de preguntas de recuperación.
     * Asocia acciones a los botones y controles de la vista.
     * No recibe parámetros ni retorna valores.
     */
    private void configurarEventos() {
        preguntasView.getBtnsiguientePregunta().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comprobarRespuesta();
            }
        });
        preguntasView.getBtnEnviar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarContrasena();
            }
        });
        preguntasView.getBtnClean().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });
        preguntasView.getCbxIdioma().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambioDeIdiomaDesdeCbx();
            }
        });
        preguntasView.getBtnExit().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                regresarAlLogin();
            }
        });
    }

    /**
     * Muestra la pregunta de seguridad actual en la vista.
     * No recibe parámetros ni retorna valores.
     */
    private void mostrarPreguntaActual() {
        if (preguntaActual < preguntasGuardadas.size()) {
            String claveI18n = preguntasGuardadas.get(preguntaActual).getPregunta().getTexto();
            preguntasView.getLblPregunta().setText(i18n.get(
                    claveI18n != null ? claveI18n : preguntasGuardadas.get(preguntaActual).getPregunta().getTexto()
            ));
        } else {
            preguntasView.getLblPregunta().setText(i18n.get("preguntas.recuperacion.info.sin_preguntas"));
        }
    }

    /**
     * Comprueba la respuesta ingresada por el usuario para la pregunta actual.
     * Gestiona los intentos y habilita el cambio de contraseña si la respuesta es correcta.
     * No recibe parámetros ni retorna valores.
     */
    private void comprobarRespuesta() {
        if (puedeCambiarContrasena) return;

        String respuestaUsuario = preguntasView.getTxtRespuestComparar().getText().trim();
        if (respuestaUsuario.isEmpty()) {
            preguntasView.mostrarMensaje(
                    i18n.get("preguntas.recuperacion.error.responder"),
                    i18n.get("global.error"),
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        PreguntaUsuario pregunta = preguntasGuardadas.get(preguntaActual);
        if (pregunta.getRespuesta().equalsIgnoreCase(respuestaUsuario)) {
            habilitarCambioContrasena();
        } else {
            preguntaActual++;
            if (preguntaActual >= preguntasGuardadas.size()) {
                cicloIntentos++;
                if (cicloIntentos < intentos) {
                    int intentosRestantes = intentos - cicloIntentos;
                    preguntasView.mostrarMensaje(
                            i18n.get("preguntas.recuperacion.error.tres_mal") + " " +
                                    i18n.get("preguntas.recuperacion.info.intentos_restantes") + ": " + intentosRestantes,
                            i18n.get("global.warning"),
                            JOptionPane.WARNING_MESSAGE
                    );
                    preguntaActual = 0;
                    preguntasView.getTxtRespuestComparar().setText("");
                    mostrarPreguntaActual();
                } else {
                    int opcion = JOptionPane.showConfirmDialog(
                            preguntasView,
                            i18n.get("preguntas.recuperacion.error.intentos_agotados"),
                            i18n.get("preguntas.recuperacion.confirmar.titulo"),
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.ERROR_MESSAGE
                    );
                    if (opcion == JOptionPane.YES_OPTION) {
                        regresarAlLogin();
                    } else {
                        System.exit(0);
                    }
                }
            } else {
                preguntasView.mostrarMensaje(
                        i18n.get("preguntas.recuperacion.error.respuesta_incorrecta"),
                        i18n.get("global.warning"),
                        JOptionPane.WARNING_MESSAGE
                );
                preguntasView.getTxtRespuestComparar().setText("");
                mostrarPreguntaActual();
            }
        }
    }

    /**
     * Habilita los campos para el cambio de contraseña si la respuesta es correcta.
     * No recibe parámetros ni retorna valores.
     */
    private void habilitarCambioContrasena() {
        preguntasView.getBtnsiguientePregunta().setVisible(false);
        puedeCambiarContrasena = true;
        preguntasView.mostrarMensaje(
                i18n.get("preguntas.recuperacion.exito.respuesta_correcta"),
                i18n.get("global.success"),
                JOptionPane.INFORMATION_MESSAGE
        );
        preguntasView.getLblNuevaContra().setVisible(true);
        preguntasView.getTxtNuevaContra().setVisible(true);
        preguntasView.getTxtNuevaContra().setEnabled(true);
        preguntasView.getTxtNuevaContra().setEditable(true);

        preguntasView.getBtnEnviar().setEnabled(true);

        preguntasView.getTxtRespuestComparar().setEnabled(false);
        preguntasView.getBtnsiguientePregunta().setEnabled(false);
    }

    /**
     * Cambia la contraseña del usuario si la nueva contraseña es válida.
     * Muestra mensajes de éxito o error según corresponda.
     * No recibe parámetros ni retorna valores.
     */
    private void cambiarContrasena() {
        String nuevaContrasena = preguntasView.getTxtNuevaContra().getText().trim();
        if (nuevaContrasena.isEmpty()) {
            preguntasView.mostrarMensaje(
                    i18n.get("preguntas.recuperacion.error.nueva_contrasena_vacia"),
                    i18n.get("global.warning"),
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        try {
            usuario.setContrasena(nuevaContrasena);
            usuarioDAO.actualizar(usuario.getCedula(), usuario.getContrasena(), usuario.getRol());
            preguntasView.mostrarMensaje(
                    i18n.get("preguntas.recuperacion.exito.cambio_contrasena"),
                    i18n.get("global.success"),
                    JOptionPane.INFORMATION_MESSAGE
            );
            regresarAlLogin();
        } catch (ec.edu.ups.poo.excepciones.ContrasenaInvalidaException ex) {
            preguntasView.mostrarMensaje(
                    ex.getMessage(),
                    i18n.get("global.error"),
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Limpia los campos de respuesta o nueva contraseña en la vista, según el estado actual.
     * No recibe parámetros ni retorna valores.
     */
    private void limpiarCampos() {
        if (!puedeCambiarContrasena) {
            preguntasView.getTxtRespuestComparar().setText("");
        } else {
            preguntasView.getTxtNuevaContra().setText("");
        }
    }

    /**
     * Cambia el idioma de la vista según la selección del usuario.
     * Aplica el idioma y actualiza la pregunta mostrada.
     * No recibe parámetros ni retorna valores.
     */
    private void cambioDeIdiomaDesdeCbx() {
        int selectedIndex = preguntasView.getCbxIdioma().getSelectedIndex();
        switch (selectedIndex) {
            case 0: i18n.setLenguaje("es", "EC"); break;
            case 1: i18n.setLenguaje("en", "US"); break;
            case 2: i18n.setLenguaje("fr", "FR"); break;
            default: i18n.setLenguaje("es", "EC");
        }
        preguntasView.aplicarIdiomas();
        mostrarPreguntaActual();
    }

    /**
     * Regresa a la vista de login y restablece la configuración de almacenamiento.
     * No recibe parámetros ni retorna valores.
     */
    private void regresarAlLogin(){
        preguntasView.dispose();
        SwingUtilities.invokeLater(() -> {
            LogInView logInView = new LogInView(i18n);
            logInView.getTxtRuta().setText(rutaCarpetaDatos);
            logInView.getCbxUbicacionGuardar().setSelectedIndex(tipoAlmacenamientoIndex);
            logInView.setVisible(true);
            new LogInController(logInView, i18n);
        });
    }
}