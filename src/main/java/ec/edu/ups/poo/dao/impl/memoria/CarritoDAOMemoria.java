package ec.edu.ups.poo.dao.impl.memoria;

import ec.edu.ups.poo.dao.CarritoDAO;
import ec.edu.ups.poo.modelo.Carrito;
import ec.edu.ups.poo.modelo.ItemCarrito;
import ec.edu.ups.poo.modelo.Producto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Implementación en memoria del DAO de Carrito.
 * Permite gestionar productos en el carrito, guardar, listar y eliminar carritos.
 */
public class CarritoDAOMemoria implements CarritoDAO {

    private final List<ItemCarrito> items;
    private final List<Carrito> carritos = new ArrayList<>();

    /**
     * Constructor de CarritoDAOMemoria.
     * Inicializa las listas internas de items y carritos.
     */
    public CarritoDAOMemoria() {
        this.items = new ArrayList<>();
    }

    /**
     * Agrega un producto al carrito. Si el producto ya existe, suma la cantidad.
     *
     * @param producto Producto a agregar.
     * @param cantidad Cantidad del producto.
     */
    @Override
    public void agregarProducto(Producto producto, int cantidad) {
        for (ItemCarrito item : items) {
            if (item.getProducto().getCodigo() == producto.getCodigo()) {
                item.setCantidad(item.getCantidad() + cantidad);
                return;
            }
        }
        items.add(new ItemCarrito(producto, cantidad));
    }

    /**
     * Elimina un producto del carrito por su código.
     *
     * @param codigoProducto Código del producto a eliminar.
     */
    @Override
    public void eliminarProducto(int codigoProducto) {
        Iterator<ItemCarrito> iterator = items.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getProducto().getCodigo() == codigoProducto) {
                iterator.remove();
                break;
            }
        }
    }

    /**
     * Vacía todos los productos del carrito.
     */
    @Override
    public void vaciarCarrito() {
        items.clear();
    }

    /**
     * Calcula el total del carrito sumando el precio por cantidad de cada producto.
     *
     * @return Total del carrito como double.
     */
    @Override
    public double calcularTotal() {
        double total = 0;
        for (ItemCarrito item : items) {
            total += item.getProducto().getPrecio() * item.getCantidad();
        }
        return total;
    }

    /**
     * Obtiene la lista de items del carrito.
     *
     * @return Lista de ItemCarrito.
     */
    @Override
    public List<ItemCarrito> obtenerItems() {
        return new ArrayList<>(items);
    }

    /**
     * Verifica si el carrito está vacío.
     *
     * @return true si el carrito no tiene items, false en caso contrario.
     */
    @Override
    public boolean estaVacio() {
        return items.isEmpty();
    }

    /**
     * Guarda un carrito en la lista de carritos.
     *
     * @param carrito Carrito a guardar.
     */
    @Override
    public void guardarCarrito(Carrito carrito) {
        carritos.add(carrito);
    }

    /**
     * Obtiene un carrito por su ID.
     *
     * @param idCarrito ID del carrito a buscar.
     * @return Carrito encontrado o null si no existe.
     */
    @Override
    public Carrito obtenerCarrito(int idCarrito) {
        for (Carrito carrito : carritos) {
            if (carrito.getId() == idCarrito) {
                return carrito;
            }
        }
        return null;
    }

    /**
     * Elimina un carrito por su ID.
     *
     * @param idCarrito ID del carrito a eliminar.
     */
    @Override
    public void eliminarCarrtio(int idCarrito) {
        Iterator<Carrito> iterator = carritos.iterator();
        while (iterator.hasNext()) {
            Carrito carrito = iterator.next();
            if (carrito.getId() == idCarrito) {
                iterator.remove();
                break;
            }
        }
    }

    /**
     * Lista todos los carritos almacenados.
     *
     * @return Lista de Carrito.
     */
    @Override
    public List<Carrito> listarCarritos() {
        return new ArrayList<>(carritos);
    }

    /**
     * Lista los carritos asociados a un usuario por su nombre de usuario (cedula).
     *
     * @param username Nombre de usuario (cedula).
     * @return Lista de carritos del usuario.
     */
    @Override
    public List<Carrito> listarPorUsuario(String username) {
        List<Carrito> carritosUsuario = new ArrayList<>();
        for (Carrito carrito : carritos) {
            if (carrito.getUsuario().getCedula().equals(username)) {
                carritosUsuario.add(carrito);
            }
        }
        return carritosUsuario;
    }

    /**
     * Actualiza un carrito existente. Si no existe, lo agrega.
     *
     * @param carrito Carrito a actualizar.
     */
    @Override
    public void actualizarCarrito(Carrito carrito) {
        for (int i = 0; i < carritos.size(); i++) {
            if (carritos.get(i).getId() == carrito.getId()) {
                carritos.set(i, carrito);
                return;
            }
        }
        carritos.add(carrito);
    }
}