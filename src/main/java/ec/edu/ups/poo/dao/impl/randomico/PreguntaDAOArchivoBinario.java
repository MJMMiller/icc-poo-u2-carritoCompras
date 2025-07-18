package ec.edu.ups.poo.dao.impl.randomico;

import ec.edu.ups.poo.dao.PreguntaDAO;
import ec.edu.ups.poo.modelo.Pregunta;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PreguntaDAOArchivoBinario implements PreguntaDAO {
    private final List<Pregunta> preguntas = new ArrayList<>();
    private final String rutaArchivo;

    public PreguntaDAOArchivoBinario(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        crearArchivoSiNoExiste();
        cargarPreguntas();
        if (preguntas.isEmpty()) {
            agregarPreguntasPorDefecto();
            guardarPreguntas();
        }
    }

    private void crearArchivoSiNoExiste() {
        File f = new File(rutaArchivo);
        if (!f.exists()) {
            try { f.createNewFile(); } catch (IOException e) { e.printStackTrace(); }
        }
    }

    private void cargarPreguntas() {
        preguntas.clear();
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) return;
        try (DataInputStream dis = new DataInputStream(new FileInputStream(archivo))) {
            while (dis.available() > 0) {
                int id = dis.readInt();
                String texto = dis.readUTF();
                preguntas.add(new Pregunta(id, texto));
            }
        } catch (EOFException eof) {
            // fin de archivo
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void guardarPreguntas() {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(rutaArchivo, false))) {
            for (Pregunta pregunta : preguntas) {
                dos.writeInt(pregunta.getId());
                dos.writeUTF(pregunta.getTexto());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void agregarPreguntasPorDefecto() {
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
        // Devuelve una copia para evitar modificación externa directa
        return new ArrayList<>(preguntas);
    }

    // Si editas/agregas/eliminas preguntas desde fuera usando la lista, llama a este método luego
    public void guardarCambios() {
        guardarPreguntas();
    }

    // Opcional: Permite recargar preguntas desde el archivo (útil si otro proceso las modifica)
    public void recargarPreguntas() {
        cargarPreguntas();
    }
}