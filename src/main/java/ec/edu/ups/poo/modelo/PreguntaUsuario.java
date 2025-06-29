package ec.edu.ups.poo.modelo;

public class PreguntaUsuario {
    private BancoPreguntaValidacion pregunta;
    private String respuesta;

    public PreguntaUsuario(BancoPreguntaValidacion pregunta, String respuesta) {
        this.pregunta = pregunta;
        this.respuesta = respuesta;
    }
    public BancoPreguntaValidacion getPregunta() {
        return pregunta;
    }
    public void setPregunta(BancoPreguntaValidacion pregunta) {
        this.pregunta = pregunta;
    }
    public String getRespuesta() {
        return respuesta;
    }
    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
    @Override
    public String toString() {
        return "PreguntaUsuario{" +
                "pregunta=" + pregunta +
                ", respuesta='" + respuesta + '\'' +
                '}';
    }
}