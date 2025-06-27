package ec.edu.ups.poo.modelo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Carrito {

    private List<ItemCarrito> items;
    private int id;
    private double subtotal;
    private double iva;
    private double total;
    private Date fecha;
    private Usuario usuario;

    public Carrito(int id, List<ItemCarrito> items, double subtotal, double iva, double total, Date fecha, Usuario usuario) {
        this.id = id;
        this.items = items;
        this.subtotal = subtotal;
        this.iva = iva;
        this.total = total;
        this.fecha = fecha;
        this.usuario = usuario;
    }

    public List<ItemCarrito> getItems() {
        return items;
    }

    public void setItems(List<ItemCarrito> items) {
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getFecha() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        return sdf.format(fecha);
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = dateFormat.format(fecha);
        return "Carrito [id=" + id +
                ", usuario=" + usuario +
                ", fecha=" + formattedDate +
                ", subtotal=" + subtotal +
                ", iva=" + iva +
                ", total=" + total +
                ", items=" + items + "]";
    }
}