package ec.edu.ups.poo.modelo;

public enum BancoPreguntaValidacion {

    PRIMER_MASCOTA("pregunta.primer_mascota"),
    CIUDAD_NACIMIENTO("pregunta.ciudad_nacimiento"),
    COMIDA_FAVORITA("pregunta.comida_favorita"),
    MEJOR_AMIGO_INFANCIA("pregunta.mejor_amigo_infancia"),
    ESCUELA_PRIMARIA("pregunta.escuela_primaria"),
    COLOR_FAVORITO("pregunta.color_favorito"),
    SEGUNDO_APELLIDO_MADRE("pregunta.segundo_apellido_madre"),
    PRIMER_PROFESOR("pregunta.primer_profesor"),
    PRIMER_TRABAJO("pregunta.primer_trabajo"),
    PELICULA_FAVORITA("pregunta.pelicula_favorita");

    private final String key;
    BancoPreguntaValidacion(String key) { this.key = key; }
    public String getKey() { return key; }
}
