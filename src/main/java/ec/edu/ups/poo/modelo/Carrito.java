package ec.edu.ups.poo.modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Clase que representa un carrito de compras.
 * Contiene los productos seleccionados, totales, fecha y el usuario asociado.
 */
public class Carrito {
    private int id;
    private List<ItemCarrito> items;
    private double subtotal;
    private double iva;
    private double total;
    private Date fecha;
    private Usuario usuario;

    /**
     * Constructor por defecto.
     * Inicializa la lista de items vacía.
     */
    public Carrito() {
        this.items = new ArrayList<>();
    }

    /**
     * Constructor con parámetros.
     *
     * @param id Identificador único del carrito.
     * @param items Lista de items en el carrito.
     * @param subtotal Subtotal del carrito.
     * @param iva Valor del IVA aplicado.
     * @param total Total del carrito.
     * @param fecha Fecha de creación del carrito.
     * @param usuario Usuario asociado al carrito.
     */
    public Carrito(int id, List<ItemCarrito> items, double subtotal, double iva, double total, Date fecha, Usuario usuario) {
        this.id = id;
        this.items = items;
        this.subtotal = subtotal;
        this.iva = iva;
        this.total = total;
        this.fecha = fecha;
        this.usuario = usuario;
    }

    /**
     * Obtiene el identificador único del carrito.
     * @return ID del carrito.
     */
    public int getId() { return id; }

    /**
     * Establece el identificador único del carrito.
     * @param id ID del carrito.
     */
    public void setId(int id) { this.id = id; }

    /**
     * Obtiene la lista de items del carrito.
     * @return Lista de ItemCarrito.
     */
    public List<ItemCarrito> getItems() { return items; }

    /**
     * Establece la lista de items del carrito.
     * @param items Lista de ItemCarrito.
     */
    public void setItems(List<ItemCarrito> items) { this.items = items; }

    /**
     * Obtiene el subtotal del carrito.
     * @return Subtotal como double.
     */
    public double getSubtotal() { return subtotal; }

    /**
     * Establece el subtotal del carrito.
     * @param subtotal Subtotal como double.
     */
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }

    /**
     * Obtiene el valor del IVA aplicado al carrito.
     * @return IVA como double.
     */
    public double getIva() { return iva; }

    /**
     * Establece el valor del IVA aplicado al carrito.
     * @param iva IVA como double.
     */
    public void setIva(double iva) { this.iva = iva; }

    /**
     * Obtiene el total del carrito.
     * @return Total como double.
     */
    public double getTotal() { return total; }

    /**
     * Establece el total del carrito.
     * @param total Total como double.
     */
    public void setTotal(double total) { this.total = total; }

    /**
     * Obtiene la fecha de creación del carrito.
     * @return Fecha como Date.
     */
    public Date getFecha() { return fecha; }

    /**
     * Establece la fecha de creación del carrito.
     * @param fecha Fecha como Date.
     */
    public void setFecha(Date fecha) { this.fecha = fecha; }

    /**
     * Obtiene el usuario asociado al carrito.
     * @return Usuario asociado.
     */
    public Usuario getUsuario() { return usuario; }

    /**
     * Establece el usuario asociado al carrito.
     * @param usuario Usuario asociado.
     */
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    /**
     * Devuelve una representación en cadena del carrito.
     * @return Cadena con los datos del carrito.
     */
    @Override
    public String toString() {
        return "Carrito{" +
                "id=" + id +
                ", items=" + items +
                ", subtotal=" + subtotal +
                ", iva=" + iva +
                ", total=" + total +
                ", fecha=" + fecha +
                ", usuario=" + (usuario != null ? usuario.getCedula() : "null") +
                '}';
    }
}