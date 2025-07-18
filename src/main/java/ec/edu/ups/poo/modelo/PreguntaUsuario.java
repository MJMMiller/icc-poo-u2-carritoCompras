/**
 * Clase que representa la relación entre una pregunta de seguridad y la respuesta dada por el usuario.
 * Se utiliza para asociar preguntas de seguridad a usuarios junto con sus respuestas.
 */
package ec.edu.ups.poo.modelo;

public class PreguntaUsuario {
    private Pregunta pregunta;
    private String respuesta;

    /**
     * Constructor por defecto.
     * Inicializa la pregunta y la respuesta como nulas.
     */
    public PreguntaUsuario() {}

    /**
     * Constructor con parámetros.
     *
     * @param pregunta Pregunta de seguridad asociada.
     * @param respuesta Respuesta dada por el usuario.
     */
    public PreguntaUsuario(Pregunta pregunta, String respuesta) {
        this.pregunta = pregunta;
        this.respuesta = respuesta;
    }

    /**
     * Obtiene la pregunta de seguridad asociada.
     * @return Instancia de Pregunta.
     */
    public Pregunta getPregunta() { return pregunta; }

    /**
     * Establece la pregunta de seguridad asociada.
     * @param pregunta Instancia de Pregunta.
     */
    public void setPregunta(Pregunta pregunta) { this.pregunta = pregunta; }

    /**
     * Obtiene la respuesta dada por el usuario.
     * @return Respuesta como String.
     */
    public String getRespuesta() { return respuesta; }

    /**
     * Establece la respuesta dada por el usuario.
     * @param respuesta Respuesta como String.
     */
    public void setRespuesta(String respuesta) { this.respuesta = respuesta; }

    /**
     * Devuelve una representación en cadena de la relación pregunta-respuesta.
     * @return Cadena con los datos de la pregunta y la respuesta.
     */
    @Override
    public String toString() {
        return "PreguntaUsuario{" +
                "pregunta=" + pregunta +
                ", respuesta='" + respuesta + '\'' +
                '}';
    }
}