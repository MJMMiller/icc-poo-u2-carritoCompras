package ec.edu.ups.poo.dao.impl;

import ec.edu.ups.poo.dao.CarritoDAO;
import ec.edu.ups.poo.dao.PreguntaDAO;
import ec.edu.ups.poo.dao.ProductoDAO;
import ec.edu.ups.poo.dao.UsuarioDAO;
import ec.edu.ups.poo.dao.impl.randomico.CarritoDAOArchivoBinario;
import ec.edu.ups.poo.dao.impl.randomico.PreguntaDAOArchivoBinario;
import ec.edu.ups.poo.dao.impl.randomico.ProductoDAOArchivoBinario;
import ec.edu.ups.poo.dao.impl.randomico.UsuarioDAOArchivoBinario;
import ec.edu.ups.poo.dao.impl.memoria.CarritoDAOMemoria;
import ec.edu.ups.poo.dao.impl.memoria.PreguntaDAOMemoria;
import ec.edu.ups.poo.dao.impl.memoria.ProductoDAOMemoria;
import ec.edu.ups.poo.dao.impl.memoria.UsuarioDAOMemoria;
import ec.edu.ups.poo.dao.impl.texto.CarritoDAOArchivoTexto;
import ec.edu.ups.poo.dao.impl.texto.PreguntaDAOArchivoTexto;
import ec.edu.ups.poo.dao.impl.texto.ProductoDAOArchivoTexto;
import ec.edu.ups.poo.dao.impl.texto.UsuarioDAOArchivoTexto;
import ec.edu.ups.poo.modelo.Pregunta;
import ec.edu.ups.poo.modelo.Usuario;
import ec.edu.ups.poo.modelo.Producto;
import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;

import java.util.List;

/**
 * Clase de utilidad para obtener instancias de DAOs según el tipo de almacenamiento.
 * Permite seleccionar entre almacenamiento en memoria, texto o binario para usuarios, productos, carritos y preguntas.
 */
public class DAODireccion {

    /**
     * Obtiene una instancia de UsuarioDAO según el tipo de almacenamiento.
     *
     * @param tipo Tipo de almacenamiento (0: memoria, 1: texto, 2: binario).
     * @param ruta Ruta base para los archivos.
     * @param preguntas Lista de preguntas de seguridad disponibles.
     * @return Instancia de UsuarioDAO correspondiente.
     * @throws IllegalArgumentException si el tipo es incorrecto.
     */
    public static UsuarioDAO getUsuarioDAO(int tipo, String ruta, List<Pregunta> preguntas) {
        switch (tipo) {
            case 0: return new UsuarioDAOMemoria(preguntas);
            case 1: return new UsuarioDAOArchivoTexto(preguntas, ruta + "/usuarios.txt");
            case 2: return new UsuarioDAOArchivoBinario(preguntas, ruta + "/usuarios.dat");
            default: throw new IllegalArgumentException("Tipo incorrecto");
        }
    }

    /**
     * Obtiene una instancia de ProductoDAO según el tipo de almacenamiento.
     *
     * @param tipo Tipo de almacenamiento (0: memoria, 1: texto, 2: binario).
     * @param ruta Ruta base para los archivos.
     * @return Instancia de ProductoDAO correspondiente.
     * @throws IllegalArgumentException si el tipo es incorrecto.
     */
    public static ProductoDAO getProductoDAO(int tipo, String ruta) {
        switch (tipo) {
            case 0: return new ProductoDAOMemoria();
            case 1: return new ProductoDAOArchivoTexto(ruta + "/productos.txt");
            case 2: return new ProductoDAOArchivoBinario(ruta + "/productos.dat");
            default: throw new IllegalArgumentException("Tipo incorrecto");
        }
    }

    /**
     * Obtiene una instancia de CarritoDAO según el tipo de almacenamiento.
     *
     * @param tipo Tipo de almacenamiento (0: memoria, 1: texto, 2: binario).
     * @param ruta Ruta base para los archivos.
     * @param productos Lista de productos disponibles.
     * @param usuarios Lista de usuarios disponibles.
     * @return Instancia de CarritoDAO correspondiente.
     * @throws IllegalArgumentException si el tipo es incorrecto.
     */
    public static CarritoDAO getCarritoDAO(int tipo, String ruta, List<Producto> productos, List<Usuario> usuarios) {
        switch (tipo) {
            case 0: return new CarritoDAOMemoria();
            case 1: return new CarritoDAOArchivoTexto(ruta + "/carritos.txt", productos, usuarios);
            case 2: return new CarritoDAOArchivoBinario(ruta + "/carritos.dat", productos, usuarios);
            default: throw new IllegalArgumentException("Tipo incorrecto");
        }
    }


    /**
     * Obtiene una instancia de PreguntaDAO según el tipo de almacenamiento.
     *
     * @param tipo Tipo de almacenamiento (0: memoria, 1: texto, 2: binario).
     * @param ruta Ruta base para los archivos.
     * @param i18n Manejador de internacionalización de mensajes.
     * @return Instancia de PreguntaDAO correspondiente.
     * @throws IllegalArgumentException si el tipo es incorrecto.
     */
    public static PreguntaDAO getPreguntaDAO(int tipo, String ruta, MensajeInternacionalizacionHandler i18n) {
        switch (tipo) {
            case 0: return new PreguntaDAOMemoria(i18n);
            case 1: return new PreguntaDAOArchivoTexto(i18n, ruta + "/preguntas.txt");
            case 2: return new PreguntaDAOArchivoBinario(ruta + "/preguntas.dat");
            default: throw new IllegalArgumentException("Tipo incorrecto");
        }
    }
}