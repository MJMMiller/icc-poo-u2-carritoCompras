package ec.edu.ups.poo.modelo;

public class Pregunta {
    private int id;
    private String texto;

    public Pregunta() {}

    public Pregunta(int id, String texto) {
        this.id = id;
        this.texto = texto;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }

    @Override
    public String toString() {
        return texto;
    }
}