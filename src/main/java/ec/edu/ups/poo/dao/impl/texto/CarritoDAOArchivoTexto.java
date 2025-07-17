package ec.edu.ups.poo.dao.impl.texto;

import ec.edu.ups.poo.dao.CarritoDAO;
import ec.edu.ups.poo.modelo.Carrito;
import ec.edu.ups.poo.modelo.ItemCarrito;
import ec.edu.ups.poo.modelo.Producto;
import ec.edu.ups.poo.modelo.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CarritoDAOArchivoTexto implements CarritoDAO {

    private final List<ItemCarrito> items;
    private final List<Carrito> carritos;
    private final String rutaArchivo;

    // Se requiere acceso al listado de productos y usuarios para reconstruir los objetos al leer del archivo
    private final List<Producto> productos;
    private final List<Usuario> usuarios;

    public CarritoDAOArchivoTexto(String rutaArchivo, List<Producto> productos, List<Usuario> usuarios) {
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
            double iva = subtotal * 0.12;
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
    public List<Carrito> listarPorUsuario(String cedula) {
        List<Carrito> carritosUsuario = new ArrayList<>();
        for (Carrito carrito : carritos) {
            if (carrito.getUsuario().getCedula().equals(cedula)) {
                carritosUsuario.add(carrito);
            }
        }
        return carritosUsuario;
    }

    private void guardarCarritosEnArchivo() {
        try {
            File archivo = new File(rutaArchivo);
            if (archivo.getParentFile() != null) archivo.getParentFile().mkdirs();
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
                for (Carrito carrito : carritos) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(carrito.getId()).append("|")
                            .append(carrito.getUsuario().getCedula()).append("|");
                    for (ItemCarrito item : carrito.getItems()) {
                        sb.append(item.getProducto().getCodigo())
                                .append(",")
                                .append(item.getCantidad())
                                .append(";");
                    }
                    bw.write(sb.toString());
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Carrito> cargarCarritos() {
        List<Carrito> lista = new ArrayList<>();
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) return lista;
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("\\|");
                if (partes.length >= 3) {
                    int id = Integer.parseInt(partes[0]);
                    String cedulaUsuario = partes[1];
                    Usuario usuario = buscarUsuarioPorCedula(cedulaUsuario);
                    List<ItemCarrito> items = new ArrayList<>();
                    String[] itemsStr = partes[2].split(";");
                    for (String itemStr : itemsStr) {
                        if (itemStr.isEmpty()) continue;
                        String[] itemData = itemStr.split(",");
                        int codigo = Integer.parseInt(itemData[0]);
                        int cantidad = Integer.parseInt(itemData[1]);
                        Producto producto = buscarProductoPorCodigo(codigo);
                        items.add(new ItemCarrito(producto, cantidad));
                    }
                    // Calcula subtotal, iva, total, fecha para reconstruir Carrito
                    double subtotal = 0;
                    for (ItemCarrito item : items) {
                        subtotal += item.getProducto().getPrecio() * item.getCantidad();
                    }
                    double iva = subtotal * 0.12;
                    double total = subtotal + iva;
                    java.util.Date fecha = new java.util.Date();
                    Carrito carrito = new Carrito(id, items, subtotal, iva, total, fecha, usuario);
                    lista.add(carrito);
                }
            }
        } catch (IOException e) {
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