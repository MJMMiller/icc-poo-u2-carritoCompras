package ec.edu.ups.poo.controlador;

import ec.edu.ups.poo.dao.CarritoDAO;
import ec.edu.ups.poo.dao.ProductoDAO;
import ec.edu.ups.poo.dao.UsuarioDAO;
import ec.edu.ups.poo.dao.PreguntaDAO;
import ec.edu.ups.poo.dao.impl.texto.UsuarioDAOArchivoTexto;
import ec.edu.ups.poo.modelo.Usuario;
import ec.edu.ups.poo.modelo.Pregunta;
import ec.edu.ups.poo.modelo.PreguntaUsuario;
import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.vista.preguntas.PreguntasValidacionView;
import ec.edu.ups.poo.vista.inicio.LogInView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * Controlador para la validación de preguntas de seguridad durante el registro o configuración de usuario.
 * Permite seleccionar preguntas, registrar respuestas y guardar la configuración en el sistema.
 */
public class PreguntaValidacionController {

    private final Usuario usuario;
    private final UsuarioDAO usuarioDAO;
    private final PreguntaDAO preguntaDAO;
    private final ProductoDAO productoDAO;
    private final CarritoDAO carritoDAO;
    private final PreguntasValidacionView preguntasView;
    private final MensajeInternacionalizacionHandler i18n;
    private List<Pregunta> listaPreguntas;
    private final List<PreguntaUsuario> preguntasRespondidas = new ArrayList<>();

    private final String rutaCarpetaDatos;
    private final int tipoAlmacenamientoIndex;

    /**
     * Constructor de PreguntaValidacionController.
     * Inicializa DAOs, vistas, usuario y configuración de almacenamiento.
     * Configura la vista y los eventos, y muestra las preguntas disponibles.
     *
     * @param usuario Usuario que responde las preguntas de validación.
     * @param usuarioDAO DAO para operaciones de usuario.
     * @param preguntaDAO DAO para operaciones de preguntas.
     * @param productoDAO DAO para operaciones de producto.
     * @param carritoDAO DAO para operaciones de carrito.
     * @param preguntasView Vista de validación de preguntas.
     * @param i18n Manejador de internacionalización de mensajes.
     * @param rutaCarpetaDatos Ruta de la carpeta de datos.
     * @param tipoAlmacenamientoIndex Índice del tipo de almacenamiento.
     */
    public PreguntaValidacionController(
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
        this.rutaCarpetaDatos = rutaCarpetaDatos;
        this.tipoAlmacenamientoIndex = tipoAlmacenamientoIndex;

        mostrarPreguntasEnVista();
        configurarEventos();
        inicializarVista();
        preguntasView.aplicarIdiomas();
    }

    /**
     * Configura los eventos de la vista de preguntas de validación.
     * Asocia acciones a los botones y controles de la vista.
     * No recibe parámetros ni retorna valores.
     */
    private void configurarEventos() {
        preguntasView.getBtnEnviar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                procesarEnvio();
            }
        });
        preguntasView.getBtnClean().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });
        preguntasView.getCbxPreguntas().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarTextoPregunta();
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
     * Muestra las preguntas de seguridad disponibles en la vista.
     * Filtra las preguntas ya respondidas y actualiza el ComboBox.
     * No recibe parámetros ni retorna valores.
     */
    private void mostrarPreguntasEnVista() {
        JComboBox<String> cbxPreguntas = preguntasView.getCbxPreguntas();
        cbxPreguntas.removeAllItems();

        List<Pregunta> todasLasPreguntas = preguntaDAO.listarTodas();

        listaPreguntas = new ArrayList<>();
        for (Pregunta p : todasLasPreguntas) {
            boolean yaRespondida = false;
            for (PreguntaUsuario pu : preguntasRespondidas) {
                if (pu.getPregunta().equals(p)) {
                    yaRespondida = true;
                    break;
                }
            }
            if (!yaRespondida) {
                listaPreguntas.add(p);
            }
        }

        for (int i = 0; i < listaPreguntas.size(); i++) {
            cbxPreguntas.addItem(i18n.get("preguntas.validacion.nombre.cbx") + " " + (i + 1));
        }

        if (!listaPreguntas.isEmpty()) {
            Pregunta primeraPregunta = listaPreguntas.get(0);
            preguntasView.getLblQuestion().setText(i18n.get(
                    primeraPregunta.getTexto() != null ? primeraPregunta.getTexto() : primeraPregunta.getTexto()
            ));
        } else {
            preguntasView.getLblQuestion().setText("");
        }
    }

    /**
     * Actualiza el texto de la pregunta seleccionada en la vista.
     * No recibe parámetros ni retorna valores.
     */
    private void actualizarTextoPregunta() {
        int index = preguntasView.getCbxPreguntas().getSelectedIndex();
        if (index >= 0 && index < listaPreguntas.size()) {
            Pregunta preguntaSeleccionada = listaPreguntas.get(index);
            preguntasView.getLblQuestion().setText(i18n.get(
                    preguntaSeleccionada.getTexto() != null ? preguntaSeleccionada.getTexto() : preguntaSeleccionada.getTexto()
            ));
        }
    }

    /**
     * Procesa el envío de la respuesta a la pregunta seleccionada.
     * Valida la respuesta, evita duplicados y gestiona el flujo de preguntas.
     * No recibe parámetros ni retorna valores.
     */
    private void procesarEnvio() {
        String respuesta = preguntasView.getTxtRespuestaSeguidad().getText().trim();
        int index = preguntasView.getCbxPreguntas().getSelectedIndex();

        if (respuesta.isEmpty()) {
            if (preguntasRespondidas.size() < 3) {
                preguntasView.mostrarMensaje(
                        i18n.get("preguntas.validacion.error.minimo_tres"),
                        i18n.get("global.error"),
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            } else {
                confirmarFinalizarPreguntas();
                return;
            }
        }

        Pregunta preguntaSeleccionada = listaPreguntas.get(index);
        for (PreguntaUsuario pu : preguntasRespondidas) {
            if (pu.getPregunta().equals(preguntaSeleccionada)) {
                preguntasView.mostrarMensaje(
                        i18n.get("preguntas.validacion.error.preguntas_repetidas"),
                        i18n.get("global.error"),
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }
        }

        preguntasRespondidas.add(new PreguntaUsuario(preguntaSeleccionada, respuesta));
        preguntasView.getTxtRespuestaSeguidad().setText("");

        preguntasView.getCbxPreguntas().removeItemAt(index);
        listaPreguntas.remove(index);

        if (listaPreguntas.size() > 0) {
            preguntasView.getCbxPreguntas().setSelectedIndex(0);
            Pregunta sigPregunta = listaPreguntas.get(0);
            preguntasView.getLblQuestion().setText(i18n.get(
                    sigPregunta.getTexto() != null ? sigPregunta.getTexto() : sigPregunta.getTexto()
            ));
        } else {
            preguntasView.getLblQuestion().setText("");
        }

        if (preguntasRespondidas.size() >= 3) {
            int opcion = JOptionPane.showConfirmDialog(
                    preguntasView,
                    i18n.get("preguntas.validacion.confirmar.mas_o_finalizar"),
                    i18n.get("preguntas.validacion.confirmar.titulo"),
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );
            if (opcion == JOptionPane.NO_OPTION) {
                guardarPreguntasValidacion();
            }
        }
    }

    /**
     * Guarda las preguntas de validación y sus respuestas en el usuario y en el sistema.
     * Muestra mensaje de éxito y regresa a la vista de login.
     * No recibe parámetros ni retorna valores.
     */
    private void guardarPreguntasValidacion() {
        usuario.setPreguntaValidacion(new ArrayList<>(preguntasRespondidas));
        usuarioDAO.agregarPreguntasAUsuario(usuario.getCedula(), preguntasRespondidas);
        UsuarioDAOArchivoTexto.limpiarDuplicadosUsuarios(rutaCarpetaDatos + "/usuarios.txt");

        preguntasView.mostrarMensaje(
                i18n.get("preguntas.validacion.exito.guardado"),
                i18n.get("global.success"),
                JOptionPane.INFORMATION_MESSAGE
        );
        preguntasView.dispose();

        SwingUtilities.invokeLater(() -> {
            LogInView logInView = new LogInView(i18n);
            logInView.getTxtRuta().setText(rutaCarpetaDatos);
            logInView.getCbxUbicacionGuardar().setSelectedIndex(tipoAlmacenamientoIndex);
            logInView.setVisible(true);
            new LogInController(logInView, i18n);
        });
    }

    /**
     * Limpia el campo de respuesta en la vista.
     * No recibe parámetros ni retorna valores.
     */
    private void limpiarCampos() {
        preguntasView.getTxtRespuestaSeguidad().setText("");
    }

    /**
     * Confirma si el usuario desea finalizar el proceso de preguntas.
     * Si acepta, guarda las preguntas de validación.
     * No recibe parámetros ni retorna valores.
     */
    private void confirmarFinalizarPreguntas() {
        int opcion = JOptionPane.showConfirmDialog(
                preguntasView,
                i18n.get("preguntas.validacion.confirmar.finalizar"),
                i18n.get("preguntas.validacion.confirmar.titulo"),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        if (opcion == JOptionPane.YES_OPTION) {
            guardarPreguntasValidacion();
        }
    }

    /**
     * Inicializa la vista de preguntas de validación, ocultando y mostrando los campos necesarios.
     * No recibe parámetros ni retorna valores.
     */
    private void inicializarVista() {
        preguntasView.getBtnsiguientePregunta().setVisible(false);
        preguntasView.getLblPregunta().setVisible(false);
        preguntasView.getTxtRespuestComparar().setVisible(false);
    }

    /**
     * Cambia el idioma de la vista según la selección del usuario.
     * Aplica el idioma y actualiza las preguntas mostradas.
     * No recibe parámetros ni retorna valores.
     */
    private void cambioDeIdiomaDesdeCbx() {
        int selectedIndex = preguntasView.getCbxIdioma().getSelectedIndex();
        switch (selectedIndex) {
            case 0: i18n.setLenguaje("es", "EC"); break;
            case 1: i18n.setLenguaje("en", "US"); break;
            case 2: i18n.setLenguaje("fr", "FR"); break;
            default: i18n.setLenguaje("en", "US");
        }
        preguntasView.aplicarIdiomas();
        mostrarPreguntasEnVista();
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