package ec.edu.ups.poo.dao.impl.texto;

import ec.edu.ups.poo.dao.PreguntaDAO;
import ec.edu.ups.poo.modelo.Pregunta;
import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PreguntaDAOArchivoTexto implements PreguntaDAO {

    private final List<Pregunta> preguntas = new ArrayList<>();
    private final String rutaArchivo;
    private final MensajeInternacionalizacionHandler i18n;

    public PreguntaDAOArchivoTexto(MensajeInternacionalizacionHandler i18n, String rutaArchivo) {
        this.i18n = i18n;
        this.rutaArchivo = rutaArchivo;
        cargarPreguntas();
        if (preguntas.isEmpty()) {
            agregarPreguntasPorDefecto();
            guardarPreguntas();
        }
    }

    private void cargarPreguntas() {
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("\\|");
                if (partes.length == 2) {
                    int id = Integer.parseInt(partes[0]);
                    String clave = partes[1];
                    preguntas.add(new Pregunta(id, clave));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void guardarPreguntas() {
        try {
            File archivo = new File(rutaArchivo);
            if (archivo.getParentFile() != null) archivo.getParentFile().mkdirs();
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
                for (Pregunta pregunta : preguntas) {
                    bw.write(pregunta.getTexto() + "|" + pregunta.getId());
                    bw.newLine();
                }
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
        return new ArrayList<>(preguntas);
    }
}