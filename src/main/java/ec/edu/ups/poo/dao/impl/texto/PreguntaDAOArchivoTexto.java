package ec.edu.ups.poo.dao.impl.texto;

import ec.edu.ups.poo.dao.PreguntaDAO;
import ec.edu.ups.poo.modelo.Pregunta;
import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de PreguntaDAO que almacena las preguntas de seguridad en un archivo de texto.
 * Permite cargar, guardar, listar y modificar preguntas de seguridad persistidas en disco.
 */
public class PreguntaDAOArchivoTexto implements PreguntaDAO {

    private final List<Pregunta> preguntas = new ArrayList<>();
    private final String rutaArchivo;
    private final MensajeInternacionalizacionHandler i18n;

    /**
     * Constructor de PreguntaDAOArchivoTexto.
     * Inicializa el manejador de internacionalización y la ruta del archivo.
     * Carga las preguntas desde el archivo de texto y, si está vacío, agrega preguntas por defecto y las guarda.
     *
     * @param i18n Manejador de internacionalización de mensajes.
     * @param rutaArchivo Ruta del archivo de texto donde se almacenan las preguntas.
     */
    public PreguntaDAOArchivoTexto(MensajeInternacionalizacionHandler i18n, String rutaArchivo) {
        this.i18n = i18n;
        this.rutaArchivo = rutaArchivo;
        cargarPreguntas();
        if (preguntas.isEmpty()) {
            agregarPreguntasPorDefecto();
            guardarPreguntas();
        }
    }

    /**
     * Carga las preguntas almacenadas en el archivo de texto a la lista interna.
     * No recibe parámetros ni retorna valores.
     */
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

    /**
     * Guarda todas las preguntas de la lista interna en el archivo de texto.
     * No recibe parámetros ni retorna valores.
     */
    private void guardarPreguntas() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (Pregunta pregunta : preguntas) {
                bw.write(pregunta.getId() + "|" + pregunta.getTexto());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Agrega preguntas de seguridad por defecto a la lista interna.
     * No recibe parámetros ni retorna valores.
     */
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

    /**
     * Lista todas las preguntas de seguridad almacenadas.
     * Si modificas la lista retornada, llama a guardarPreguntas() manualmente después.
     *
     * @return Lista de preguntas de seguridad.
     */
    @Override
    public List<Pregunta> listarTodas() {
        // Si modificas la lista retornada, llama a guardarPreguntas() manualmente DESPUÉS.
        return preguntas;
    }

    /**
     * Guarda los cambios realizados en la lista de preguntas en el archivo de texto.
     * Si editas/agregas/eliminas preguntas desde fuera usando la lista, llama a este método luego.
     * No recibe parámetros ni retorna valores.
     */
    public void guardarCambios() {
        guardarPreguntas();
    }
}