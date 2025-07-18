package ec.edu.ups.poo.dao.impl;

import ec.edu.ups.poo.dao.CarritoDAO;
import ec.edu.ups.poo.dao.PreguntaDAO;
import ec.edu.ups.poo.dao.ProductoDAO;
import ec.edu.ups.poo.dao.UsuarioDAO;
import ec.edu.ups.poo.dao.impl.binario.CarritoDAOArchivoBinario;
import ec.edu.ups.poo.dao.impl.binario.ProductoDAOArchivoBinario;
import ec.edu.ups.poo.dao.impl.binario.UsuarioDAOArchivoBinario;
import ec.edu.ups.poo.dao.impl.memoria.CarritoDAOMemoria;
import ec.edu.ups.poo.dao.impl.memoria.PreguntaDAOMemoria;
import ec.edu.ups.poo.dao.impl.memoria.ProductoDAOMemoria;
import ec.edu.ups.poo.dao.impl.memoria.UsuarioDAOMemoria;
import ec.edu.ups.poo.dao.impl.texto.CarritoDAOArchivoTexto;
import ec.edu.ups.poo.dao.impl.texto.ProductoDAOArchivoTexto;
import ec.edu.ups.poo.dao.impl.texto.UsuarioDAOArchivoTexto;
import ec.edu.ups.poo.modelo.Pregunta;
import ec.edu.ups.poo.modelo.Usuario;
import ec.edu.ups.poo.modelo.Producto;
import ec.edu.ups.poo.util.MensajeInternacionalizacionHandler;

import java.util.List;

public class DAODireccion {

    public static UsuarioDAO getUsuarioDAO(int tipo, String ruta, List<Pregunta> preguntas) {
        switch (tipo) {
            case 0: return new UsuarioDAOMemoria(preguntas);
            case 1: return new UsuarioDAOArchivoTexto(preguntas, ruta + "/usuarios.txt");
            case 2: return new UsuarioDAOArchivoBinario(preguntas, ruta + "/usuarios.dat");
            default: throw new IllegalArgumentException("Tipo incorrecto");
        }
    }

    public static ProductoDAO getProductoDAO(int tipo, String ruta) {
        switch (tipo) {
            case 0: return new ProductoDAOMemoria();
            case 1: return new ProductoDAOArchivoTexto(ruta + "/productos.txt");
            case 2: return new ProductoDAOArchivoBinario(ruta + "/productos.dat");
            default: throw new IllegalArgumentException("Tipo incorrecto");
        }
    }

    public static CarritoDAO getCarritoDAO(int tipo, String ruta, List<Producto> productos, List<Usuario> usuarios) {
        switch (tipo) {
            case 0: return new CarritoDAOMemoria();
            case 1: return new CarritoDAOArchivoTexto(ruta + "/carritos.txt", productos, usuarios);
            case 2: return new CarritoDAOArchivoBinario(ruta + "/carritos.dat", productos, usuarios);
            default: throw new IllegalArgumentException("Tipo incorrecto");
        }
    }

    public static PreguntaDAO getPreguntaDAO(int tipo, String ruta, MensajeInternacionalizacionHandler i18n) {
        return new PreguntaDAOMemoria(i18n);
    }
}