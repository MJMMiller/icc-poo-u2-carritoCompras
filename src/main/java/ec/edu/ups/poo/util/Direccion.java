package ec.edu.ups.poo.util;

import ec.edu.ups.poo.util.enums.TipoIcono;
import javax.swing.*;

public class Direccion {

    public static ImageIcon icono(TipoIcono tipo) {
        switch (tipo) {
            case USUARIO:
                return new ImageIcon(Direccion.class.getClassLoader().getResource("imagenes/usuario.png"));
            case BUSCAR:
                return new ImageIcon(Direccion.class.getClassLoader().getResource("imagenes/buscar.png"));
            case LISTAR:
                return new ImageIcon(Direccion.class.getClassLoader().getResource("imagenes/listar.png"));
            case ElIMINAR:
                return new ImageIcon(Direccion.class.getClassLoader().getResource("imagenes/eliminar.png"));
            case CLEAN:
                return new ImageIcon(Direccion.class.getClassLoader().getResource("imagenes/limpiar.png"));
            case ACTUALIZAR:
                return new ImageIcon(Direccion.class.getClassLoader().getResource("imagenes/actualizar.png"));
            case REGISTRAR:
                return new ImageIcon(Direccion.class.getClassLoader().getResource("imagenes/register.png"));
            case PRODUCTO:
                return new ImageIcon(Direccion.class.getClassLoader().getResource("imagenes/producto.png"));
            case GUARDAR:
                return new ImageIcon(Direccion.class.getClassLoader().getResource("imagenes/save.png"));
            case PREGUNTA:
                return new ImageIcon(Direccion.class.getClassLoader().getResource("imagenes/preguntas.png"));
            case REGISTRO_USUARIO:
                return new ImageIcon(Direccion.class.getClassLoader().getResource("imagenes/registro-usuario.png"));
            case LOGIN:
                return new ImageIcon(Direccion.class.getClassLoader().getResource("imagenes/logIn.png"));
            case BLOGIN:
                return new ImageIcon(Direccion.class.getClassLoader().getResource("imagenes/olvideContrasena.png"));
            case X:
                return new ImageIcon(Direccion.class.getClassLoader().getResource("imagenes/exit.png"));
            case RECUPERAR_CONTRASENA:
                return new ImageIcon(Direccion.class.getClassLoader().getResource("imagenes/recuContra.png"));
            case CARRITO:
                return new ImageIcon(Direccion.class.getClassLoader().getResource("imagenes/carrito.png"));
            case EYE:
                return new ImageIcon(Direccion.class.getClassLoader().getResource("imagenes/ver.png"));
            case CREAR_USUARIO:
                return new ImageIcon(Direccion.class.getClassLoader().getResource("imagenes/crearUsuario.png"));
            case GUARDAR_TODO:
                return new ImageIcon(Direccion.class.getClassLoader().getResource("imagenes/guardarTodo.png"));
            case CREAR:
                return new ImageIcon(Direccion.class.getClassLoader().getResource("imagenes/crear.png"));
            case EDITAR:
                return new ImageIcon(Direccion.class.getClassLoader().getResource("imagenes/editar.png"));
            case LISTAR_MENU:
                return new ImageIcon(Direccion.class.getClassLoader().getResource("imagenes/listarMenu.png"));
            case BORRAR:
                return new ImageIcon(Direccion.class.getClassLoader().getResource("imagenes/borrar.png"));
            case LOGOUT:
                return new ImageIcon(Direccion.class.getClassLoader().getResource("imagenes/log-out.png"));
            case USA:
                return new ImageIcon(Direccion.class.getClassLoader().getResource("imagenes/USA.png"));
            case FRANCES:
                return new ImageIcon(Direccion.class.getClassLoader().getResource("imagenes/FRANCES.png"));
            case ECUADOR:
                return new ImageIcon(Direccion.class.getClassLoader().getResource("imagenes/ECUADOR.png"));
            case USUARIOM:
                return new ImageIcon(Direccion.class.getClassLoader().getResource("imagenes/usuarioM.png"));
            case CARRITOM:
                return new ImageIcon(Direccion.class.getClassLoader().getResource("imagenes/carritoM.png"));
            case PRODUCTOM:
                return new ImageIcon(Direccion.class.getClassLoader().getResource("imagenes/productoM.png"));
            case IDIOMAM:
                return new ImageIcon(Direccion.class.getClassLoader().getResource("imagenes/idiomaM.png"));
            case SESIONM:
                return new ImageIcon(Direccion.class.getClassLoader().getResource("imagenes/sesionM.png"));
            case POO_LOGO:
                return new ImageIcon(Direccion.class.getClassLoader().getResource("imagenes/poo-logo.png"));
            default:
                System.err.println("No se pudo cargar el icono: tipo no reconocido.");
                return null;
        }
    }
}