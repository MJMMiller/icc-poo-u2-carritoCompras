package ec.edu.ups.poo.dao.impl.randomico;

import ec.edu.ups.poo.dao.ProductoDAO;
import ec.edu.ups.poo.modelo.Producto;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProductoDAOArchivoBinario implements ProductoDAO {
    private final List<Producto> productos;
    private final String rutaArchivo;

    public ProductoDAOArchivoBinario(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        productos = new ArrayList<>();
        cargarProductos();
        if (productos.isEmpty()) {
            crear(new Producto(1, "Laptop", 1200.00));
            crear(new Producto(2, "Manzana", 0.50));
            crear(new Producto(3, "Tablet", 400.00));
            crear(new Producto(4, "Zapatos", 125.00));
        }
    }

    private void cargarProductos() {
        productos.clear();
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) return;
        try (DataInputStream dis = new DataInputStream(new FileInputStream(archivo))) {
            while (dis.available() > 0) {
                int codigo = dis.readInt();
                String nombre = dis.readUTF();
                double precio = dis.readDouble();
                productos.add(new Producto(codigo, nombre, precio));
            }
        } catch (EOFException eof) {
            // fin de archivo
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void guardarProductos() {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(rutaArchivo, false))) {
            for (Producto producto : productos) {
                dos.writeInt(producto.getCodigo());
                dos.writeUTF(producto.getNombre());
                dos.writeDouble(producto.getPrecio());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void crear(Producto producto) {
        productos.add(producto);
        guardarProductos();
    }

    @Override
    public Producto buscarPorCodigo(int codigo) {
        for (Producto producto : productos) {
            if (producto.getCodigo() == codigo) {
                return producto;
            }
        }
        return null;
    }

    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        List<Producto> encontrados = new ArrayList<>();
        for (Producto producto : productos) {
            if (producto.getNombre().equalsIgnoreCase(nombre)) {
                encontrados.add(producto);
            }
        }
        return encontrados;
    }

    @Override
    public void actualizar(Producto productoActualizado) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo() == productoActualizado.getCodigo()) {
                productos.set(i, productoActualizado);
                guardarProductos();
                break;
            }
        }
    }

    @Override
    public void eliminar(int codigo) {
        Iterator<Producto> iterator = productos.iterator();
        while (iterator.hasNext()) {
            Producto producto = iterator.next();
            if (producto.getCodigo() == codigo) {
                iterator.remove();
                guardarProductos();
                break;
            }
        }
    }

    @Override
    public List<Producto> listarTodos() {
        return new ArrayList<>(productos);
    }
}