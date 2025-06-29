package ec.edu.ups.poo.modelo;

public enum BancoPreguntaValidacion {

    PRIMER_MASCOTA("¿Cuál es el nombre de tu primera mascota?"),
    CIUDAD_NACIMIENTO("¿En qué ciudad naciste?"),
    COMIDA_FAVORITA("¿Cuál es tu comida favorita?"),
    MEJOR_AMIGO_INFANCIA("¿Cuál es el nombre de tu mejor amigo de la infancia?"),
    ESCUELA_PRIMARIA("¿Cuál es el nombre de tu escuela primaria?"),
    COLOR_FAVORITO("¿Cuál es tu color favorito?"),
    SEGUNDO_APELLIDO_MADRE("¿Cuál es el segundo apellido de tu madre?"),
    PRIMER_PROFESOR("¿Cómo se llamaba tu primer profesor?"),
    PRIMER_TRABAJO("¿Cuál fue tu primer trabajo?"),
    PELICULA_FAVORITA("¿Cuál es el nombre de tu película favorita?");

    private final String pregunta;

    BancoPreguntaValidacion(String pregunta) {
        this.pregunta = pregunta;
    }
    public String getPregunta() {
        return pregunta;
    }
}
