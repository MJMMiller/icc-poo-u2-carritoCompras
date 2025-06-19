package ec.edu.ups.poo.modelo;

import java.util.Date;
import java.util.List;

public class Carrito {

    private List<ItemCarrito> items;
    private double subtotal;
    private double iva;
    private double total;

    private Date fecha;

    public Carrito(List<ItemCarrito> items, double subtotal, double iva, double total, Date fecha) {
        this.items = items;
        this.subtotal = subtotal;
        this.iva = iva;
        this.total = total;
        this.fecha = fecha;
    }

    public List<ItemCarrito> getItems() {
        return items;
    }

    public void setItems(List<ItemCarrito> items) {
        this.items = items;
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Carrito [fecha=").append(fecha)
                .append(", subtotal=").append(subtotal)
                .append(", iva=").append(iva)
                .append(", total=").append(total)
                .append(", items=[");
        for (ItemCarrito item : items) {
            sb.append("\n  ").append(item);
        }
        sb.append("\n]]");
        return sb.toString();
    }
}
