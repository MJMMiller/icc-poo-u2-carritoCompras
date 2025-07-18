/**
 * Clase que representa un producto disponible para la venta.
 * Contiene el código, nombre y precio del producto.
 */
package ec.edu.ups.poo.modelo;

public class Producto {
    private int codigo;
    private String nombre;
    private double precio;

    /**
     * Constructor por defecto.
     * Inicializa el producto sin valores.
     */
    public Producto() {}

    /**
     * Constructor con parámetros.
     *
     * @param codigo Código único del producto.
     * @param nombre Nombre del producto.
     * @param precio Precio del producto.
     */
    public Producto(int codigo, String nombre, double precio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
    }

    /**
     * Obtiene el código único del producto.
     * @return Código del producto.
     */
    public int getCodigo() { return codigo; }

    /**
     * Establece el código único del producto.
     * @param codigo Código del producto.
     */
    public void setCodigo(int codigo) { this.codigo = codigo; }

    /**
     * Obtiene el nombre del producto.
     * @return Nombre del producto.
     */
    public String getNombre() { return nombre; }

    /**
     * Establece el nombre del producto.
     * @param nombre Nombre del producto.
     */
    public void setNombre(String nombre) { this.nombre = nombre; }

    /**
     * Obtiene el precio del producto.
     * @return Precio del producto.
     */
    public double getPrecio() { return precio; }

    /**
     * Establece el precio del producto.
     * @param precio Precio del producto.
     */
    public void setPrecio(double precio) { this.precio = precio; }

    /**
     * Devuelve una representación en cadena del producto.
     * @return Cadena con los datos del producto.
     */
    @Override
    public String toString() {
        return "Producto{" +
                "codigo=" + codigo +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                '}';
    }
}