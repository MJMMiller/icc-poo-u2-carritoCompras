package ec.edu.ups.poo.dao;

import ec.edu.ups.poo.modelo.Pregunta;
import java.util.List;

public interface PreguntaDAO {
    void agregarPregunta(Pregunta pregunta);
    Pregunta buscarPorId(int id);
    List<Pregunta> listarTodas();
    void eliminarPregunta(int id);
    void actualizarPregunta(Pregunta pregunta);
}