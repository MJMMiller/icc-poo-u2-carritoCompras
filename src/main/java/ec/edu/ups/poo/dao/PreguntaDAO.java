package ec.edu.ups.poo.dao;

import ec.edu.ups.poo.modelo.Pregunta;
import java.util.List;

/**
 * Interfaz para la gestión de preguntas de seguridad.
 * Define el método para listar todas las preguntas disponibles en el sistema.
 */
public interface PreguntaDAO {

    /**
     * Lista todas las preguntas de seguridad almacenadas.
     *
     * @return Lista de preguntas de seguridad.
     */
    List<Pregunta> listarTodas();
}