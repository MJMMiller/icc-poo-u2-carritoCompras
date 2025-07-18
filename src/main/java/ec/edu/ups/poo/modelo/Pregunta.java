/**
 * Clase que representa una pregunta de seguridad.
 * Contiene el identificador y el texto de la pregunta.
 */
package ec.edu.ups.poo.modelo;

public class Pregunta {
    private int id;
    private String texto;

    /**
     * Constructor por defecto.
     * Inicializa la pregunta sin id ni texto.
     */
    public Pregunta() {}

    /**
     * Constructor con parámetros.
     *
     * @param id Identificador único de la pregunta.
     * @param texto Texto de la pregunta.
     */
    public Pregunta(int id, String texto) {
        this.id = id;
        this.texto = texto;
    }

    /**
     * Constructor que inicializa solo el texto de la pregunta.
     * El id se establece en -1 por defecto.
     *
     * @param texto Texto de la pregunta.
     */
    public Pregunta(String texto) {
        this.id = -1;
        this.texto = texto;
    }

    /**
     * Obtiene el identificador único de la pregunta.
     * @return Id de la pregunta.
     */
    public int getId() { return id; }

    /**
     * Establece el identificador único de la pregunta.
     * @param id Id de la pregunta.
     */
    public void setId(int id) { this.id = id; }

    /**
     * Obtiene el texto de la pregunta.
     * @return Texto de la pregunta.
     */
    public String getTexto() { return texto; }

    /**
     * Establece el texto de la pregunta.
     * @param texto Texto de la pregunta.
     */
    public void setTexto(String texto) { this.texto = texto; }

    /**
     * Devuelve una representación en cadena de la pregunta.
     * @return Texto de la pregunta.
     */
    @Override
    public String toString() {
        return texto;
    }
}