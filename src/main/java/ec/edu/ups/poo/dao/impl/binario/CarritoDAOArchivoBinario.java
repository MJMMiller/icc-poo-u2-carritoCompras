package ec.edu.ups.poo.dao.impl.binario;

import ec.edu.ups.poo.dao.CarritoDAO;
import ec.edu.ups.poo.modelo.Carrito;
import ec.edu.ups.poo.modelo.ItemCarrito;
import ec.edu.ups.poo.modelo.Producto;
import ec.edu.ups.poo.modelo.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CarritoDAOArchivoBinario implements CarritoDAO {

    private final List<ItemCarrito> items;
    private final List<Carrito> carritos;
    private final String rutaArchivo;
    // Se requiere acceso al listado de productos y usuarios para reconstruir los objetos al leer del archivo
    private final List<Producto> productos;
    private final List<Usuario> usuarios;

    public CarritoDAOArchivoBinario(String rutaArchivo, List<Producto> productos, List<Usuario> usuarios) {
        this.rutaArchivo = rutaArchivo;
        this.items = new ArrayList<>();
        this.productos = productos;
        this.usuarios = usuarios;
        this.carritos = cargarCarritos();

        if (carritos.isEmpty() && !usuarios.isEmpty() && !productos.isEmpty()) {
            int id = 1;
            List<ItemCarrito> itemsDefecto = new ArrayList<>();
            itemsDefecto.add(new ItemCarrito(productos.get(0), 1));
            double subtotal = productos.get(0).getPrecio();
            double iva = subtotal * 0.12; // suponiendo IVA del 12%
            double total = subtotal + iva;
            java.util.Date fecha = new java.util.Date();
            Usuario usuario = usuarios.get(0);

            Carrito carritoDefecto = new Carrito(id, itemsDefecto, subtotal, iva, total, fecha, usuario);
            carritos.add(carritoDefecto);
            guardarCarritosEnArchivo();
        }
    }

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

    @Override
    public void vaciarCarrito() {
        items.clear();
    }

    @Override
    public double calcularTotal() {
        double total = 0;
        for (ItemCarrito item : items) {
            total += item.getProducto().getPrecio() * item.getCantidad();
        }
        return total;
    }

    @Override
    public List<ItemCarrito> obtenerItems() {
        return new ArrayList<>(items);
    }

    @Override
    public boolean estaVacio() {
        return items.isEmpty();
    }

    @Override
    public void guardarCarrito(Carrito carrito) {
        carritos.add(carrito);
        guardarCarritosEnArchivo();
    }

    @Override
    public Carrito obtenerCarrito(int idCarrito) {
        for (Carrito carrito : carritos) {
            if (carrito.getId() == idCarrito) {
                return carrito;
            }
        }
        return null;
    }

    @Override
    public void eliminarCarrtio(int idCarrito) {
        Iterator<Carrito> iterator = carritos.iterator();
        while (iterator.hasNext()) {
            Carrito carrito = iterator.next();
            if (carrito.getId() == idCarrito) {
                iterator.remove();
                guardarCarritosEnArchivo();
                break;
            }
        }
    }

    @Override
    public List<Carrito> listarCarritos() {
        return new ArrayList<>(carritos);
    }

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

    @Override
    public void actualizarCarrito(Carrito carrito) {
        for (int i = 0; i < carritos.size(); i++) {
            if (carritos.get(i).getId() == carrito.getId()) {
                carritos.set(i, carrito);
                guardarCarritosEnArchivo();
                return;
            }
        }
        carritos.add(carrito);
        guardarCarritosEnArchivo();
    }

    private void guardarCarritosEnArchivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            oos.writeObject(carritos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Carrito> cargarCarritos() {
        List<Carrito> lista = new ArrayList<>();
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) return lista;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaArchivo))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                List<?> rawList = (List<?>) obj;
                for (Object o : rawList) {
                    if (o instanceof Carrito) {
                        lista.add((Carrito) o);
                    }
                }
                // Reasignar productos y usuarios desde las listas proporcionadas
                for (Carrito carrito : lista) {
                    Usuario usuario = buscarUsuarioPorCedula(carrito.getUsuario().getCedula());
                    carrito.setUsuario(usuario);
                    List<ItemCarrito> nuevosItems = new ArrayList<>();
                    for (ItemCarrito item : carrito.getItems()) {
                        Producto producto = buscarProductoPorCodigo(item.getProducto().getCodigo());
                        nuevosItems.add(new ItemCarrito(producto, item.getCantidad()));
                    }
                    carrito.setItems(nuevosItems);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    private Producto buscarProductoPorCodigo(int codigo) {
        for (Producto producto : productos) {
            if (producto.getCodigo() == codigo) {
                return producto;
            }
        }
        return null;
    }

    private Usuario buscarUsuarioPorCedula(String cedula) {
        for (Usuario usuario : usuarios) {
            if (usuario.getCedula().equals(cedula)) {
                return usuario;
            }
        }
        return null;
    }
}