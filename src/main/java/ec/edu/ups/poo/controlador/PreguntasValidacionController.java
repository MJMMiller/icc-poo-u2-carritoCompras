package ec.edu.ups.poo.controlador;

import ec.edu.ups.poo.dao.UsuarioDAO;
import ec.edu.ups.poo.modelo.Usuario;
import ec.edu.ups.poo.modelo.PreguntaUsuario;
import ec.edu.ups.poo.modelo.BancoPreguntaValidacion;
import ec.edu.ups.poo.vista.preguntas.PreguntasValidacionView;

import javax.swing.*;
import java.util.*;

public class PreguntasValidacionController {

    private final Usuario usuario;
    private final UsuarioDAO usuarioDAO;
    private final PreguntasValidacionView preguntasView;
    private final List<BancoPreguntaValidacion> preguntasRandom;

    public PreguntasValidacionController(Usuario usuario, UsuarioDAO usuarioDAO, PreguntasValidacionView preguntasView) {
        this.usuario = usuario;
        this.usuarioDAO = usuarioDAO;
        this.preguntasView = preguntasView;

        preguntasRandom = getPreguntasRandom();

        preguntasView.getLblPregunta1().setText(preguntasRandom.get(0).getPregunta());
        preguntasView.getLblPregunta2().setText(preguntasRandom.get(1).getPregunta());
        preguntasView.getLblPregunta3().setText(preguntasRandom.get(2).getPregunta());

        preguntasView.getTxtPregunta1().setText("");
        preguntasView.getTxtPregunta2().setText("");
        preguntasView.getTxtPregunta3().setText("");

        preguntasView.getBtnEnviar().addActionListener(e -> {
            String respuesta1 = preguntasView.getTxtPregunta1().getText().trim();
            String respuesta2 = preguntasView.getTxtPregunta2().getText().trim();
            String respuesta3 = preguntasView.getTxtPregunta3().getText().trim();

            if (respuesta1.isEmpty() || respuesta2.isEmpty() || respuesta3.isEmpty()) {
                preguntasView.mostrarMensaje("Por favor, llene todas las respuestas.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            List<PreguntaUsuario> preguntasUsuario = new ArrayList<>();
            preguntasUsuario.add(new PreguntaUsuario(preguntasRandom.get(0), respuesta1));
            preguntasUsuario.add(new PreguntaUsuario(preguntasRandom.get(1), respuesta2));
            preguntasUsuario.add(new PreguntaUsuario(preguntasRandom.get(2), respuesta3));
            usuario.setPreguntaValidacion(preguntasUsuario);

            usuarioDAO.actualizar(usuario.getUserName(), usuario.getContrasena(), usuario.getRol());

            preguntasView.mostrarMensaje("Preguntas de validación guardadas correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            preguntasView.dispose();
        });

        preguntasView.getBtnClean().addActionListener(e -> {
            preguntasView.getTxtPregunta1().setText("");
            preguntasView.getTxtPregunta2().setText("");
            preguntasView.getTxtPregunta3().setText("");
        });
    }

    private List<BancoPreguntaValidacion> getPreguntasRandom() {
        List<BancoPreguntaValidacion> lista = new ArrayList<>(Arrays.asList(BancoPreguntaValidacion.values()));
        Collections.shuffle(lista);
        return lista.subList(0, 3);
    }
}