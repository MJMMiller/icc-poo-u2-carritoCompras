package ec.edu.ups.poo.dao.impl;

import ec.edu.ups.poo.dao.PreguntaDAO;
import ec.edu.ups.poo.modelo.Pregunta;

import java.util.ArrayList;
import java.util.List;

public class PreguntaDAOMemoria implements PreguntaDAO {

    private final List<Pregunta> preguntas = new ArrayList<>();

    public PreguntaDAOMemoria() {
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
    public void agregarPregunta(Pregunta pregunta) {
        pregunta.setId(preguntas.size() + 1);
        preguntas.add(pregunta);
    }

    @Override
    public Pregunta buscarPorId(int id) {
        return preguntas.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    @Override
    public List<Pregunta> listarTodas() {
        return new ArrayList<>(preguntas);
    }

    @Override
    public void eliminarPregunta(int id) {
        preguntas.removeIf(p -> p.getId() == id);
    }

    @Override
    public void actualizarPregunta(Pregunta pregunta) {
        for (int i = 0; i < preguntas.size(); i++) {
            if (preguntas.get(i).getId() == pregunta.getId()) {
                preguntas.set(i, pregunta);
                return;
            }
        }
    }
}