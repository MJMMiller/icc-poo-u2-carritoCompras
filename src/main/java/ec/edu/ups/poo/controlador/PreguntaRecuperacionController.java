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
import java.util.List;

public class PreguntaRecuperacionController {

    private final Usuario usuario;
    private final UsuarioDAO usuarioDAO;
    private final PreguntaDAO preguntaDAO;
    private final ProductoDAO productoDAO;
    private final CarritoDAO carritoDAO;
    private final PreguntasValidacionView preguntasView;
    private final MensajeInternacionalizacionHandler i18n;
    private final List<PreguntaUsuario> preguntasGuardadas;

    private int preguntaActual = 0;
    private int cicloIntentos = 0;
    private final int intentos = 3;
    private boolean puedeCambiarContrasena = false;

    public PreguntaRecuperacionController(
            Usuario usuario,
            UsuarioDAO usuarioDAO,
            PreguntaDAO preguntaDAO,
            ProductoDAO productoDAO,
            CarritoDAO carritoDAO,
            PreguntasValidacionView preguntasView,
            MensajeInternacionalizacionHandler i18n
    ) {
        this.usuario = usuario;
        this.usuarioDAO = usuarioDAO;
        this.preguntaDAO = preguntaDAO;
        this.productoDAO = productoDAO;
        this.carritoDAO = carritoDAO;
        this.preguntasView = preguntasView;
        this.i18n = i18n;
        this.preguntasGuardadas = usuario.getPreguntaValidacion();

        inicializarVista();
        configurarEventos();
        mostrarPreguntaActual();
        preguntasView.aplicarIdiomas();
    }

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

    private void configurarEventos() {
        preguntasView.getBtnsiguientePregunta().addActionListener(e -> comprobarRespuesta());
        preguntasView.getBtnEnviar().addActionListener(e -> cambiarContrasena());
        preguntasView.getBtnClean().addActionListener(e -> limpiarCampos());
        preguntasView.getCbxIdioma().addActionListener(e -> cambioDeIdiomaDesdeCbx());
        preguntasView.getBtnExit().addActionListener(e -> regresarAlLogin());
    }

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
                        preguntasView.dispose();
                        SwingUtilities.invokeLater(() -> {
                            LogInView logInView = new LogInView(i18n);
                            new LogInController(usuarioDAO, preguntaDAO, productoDAO, carritoDAO, logInView, i18n);
                            logInView.setVisible(true);
                        });
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
            preguntasView.dispose();
            SwingUtilities.invokeLater(() -> {
                LogInView logInView = new LogInView(i18n);
                new LogInController(usuarioDAO, preguntaDAO, productoDAO, carritoDAO, logInView, i18n);
                logInView.setVisible(true);
            });
        } catch (ec.edu.ups.poo.excepciones.ContrasenaInvalidaException ex) {
            preguntasView.mostrarMensaje(
                    ex.getMessage(),
                    i18n.get("global.error"),
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void limpiarCampos() {
        if (!puedeCambiarContrasena) {
            preguntasView.getTxtRespuestComparar().setText("");
        } else {
            preguntasView.getTxtNuevaContra().setText("");
        }
    }

    private void cambioDeIdiomaDesdeCbx() {
        int selectedIndex = preguntasView.getCbxIdioma().getSelectedIndex();
        switch (selectedIndex) {
            case 0: i18n.setLenguaje("es", "EC"); break;
            case 1: i18n.setLenguaje("en", "US"); break;
            case 2: i18n.setLenguaje("fr", "FR"); break;
            default: i18n.setLenguaje("es", "EC");
        }
        preguntasView.aplicarIdiomas();     // Actualiza todo, incluyendo el ComboBox
        mostrarPreguntaActual();            // Actualiza la pregunta mostrada
    }

    private void regresarAlLogin(){
        preguntasView.dispose();
        SwingUtilities.invokeLater(() -> {
            LogInView logInView = new LogInView(i18n);
            new LogInController(usuarioDAO, preguntaDAO, productoDAO, carritoDAO, logInView, i18n);
            logInView.setVisible(true);
        });
    }
}