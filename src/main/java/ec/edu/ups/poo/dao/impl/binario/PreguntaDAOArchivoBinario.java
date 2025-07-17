package ec.edu.ups.poo.dao.impl.binario;

import ec.edu.ups.poo.dao.PreguntaDAO;
import ec.edu.ups.poo.modelo.Pregunta;
import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;

import java.util.ArrayList;
import java.util.List;

public class PreguntaDAOArchivoBinario implements PreguntaDAO {

    private final List<Pregunta> preguntas = new ArrayList<>();
    private MensajeInternacionalizacionHandler i18n;
    private final String rutaArchivo;


    public PreguntaDAOArchivoBinario(MensajeInternacionalizacionHandler i18n, String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        this.i18n = i18n;
        preguntas.add(new Pregunta(1, "pregunta.primer_mascota"));
        preguntas.add(new Pregunta(2, "pregunta.ciudad_nacimiento"));
        preguntas.add(new Pregunta(3, "pregunta.comida_favorita"));
        preguntas.add(new Pregunta(4, "pregunta.mejor_amigo_infancia"));
        preguntas.add(new Pregunta(5, "pregunta.escuela_primaria"));
        preguntas.add(new Pregunta(6, "pregunta.color_favorito"));
        preguntas.add(new Pregunta(7, "pregunta.segundo_apellido_madre"));
        preguntas.add(new Pregunta(8, "pregunta.primer_profesor"));
        preguntas.add(new Pregunta(9, "pregunta.primer_trabajo"));
        preguntas.add(new Pregunta(10, "pregunta.pelicula_favorita"));
    }

    @Override
    public List<Pregunta> listarTodas() {
        return new ArrayList<>(preguntas);
    }
}