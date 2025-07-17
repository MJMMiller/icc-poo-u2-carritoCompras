package ec.edu.ups.poo.dao.impl.texto;

import ec.edu.ups.poo.dao.ProductoDAO;
import ec.edu.ups.poo.modelo.Producto;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProductoDAOArchivoTexto implements ProductoDAO {
    private final List<Producto> productos;
    private final String rutaArchivo;

    public ProductoDAOArchivoTexto(String rutaArchivo) {
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
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("\\|");
                if (partes.length == 3) {
                    int codigo = Integer.parseInt(partes[0]);
                    String nombre = partes[1];
                    double precio = Double.parseDouble(partes[2]);
                    productos.add(new Producto(codigo, nombre, precio));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void guardarProductos() {
        try {
            File archivo = new File(rutaArchivo);
            if (archivo.getParentFile() != null) archivo.getParentFile().mkdirs();
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
                for (Producto producto : productos) {
                    bw.write(producto.getCodigo() + "|" + producto.getNombre() + "|" + producto.getPrecio());
                    bw.newLine();
                }
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
        List<Producto> productosEncontrados = new ArrayList<>();
        for (Producto producto : productos) {
            if (producto.getNombre().equalsIgnoreCase(nombre)) {
                productosEncontrados.add(producto);
            }
        }
        return productosEncontrados;
    }

    @Override
    public void actualizar(Producto producto) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo() == producto.getCodigo()) {
                productos.set(i, producto);
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