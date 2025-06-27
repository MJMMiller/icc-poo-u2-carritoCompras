package ec.edu.ups.poo.modelo;

import java.util.ArrayList;
import java.util.List;

public class Usuario {

    private String userName;
    private String contrasena;
    private Rol rol;
    private List<Carrito> carritos;

    public Usuario(String userName, String contrasena, Rol rol) {
        this.userName = userName;
        this.contrasena = contrasena;
        this.rol = rol;
        this.carritos = new ArrayList<>();
    }

    public Usuario() {
        this.carritos = new ArrayList<>();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public List<Carrito> getCarritos() {
        return carritos;
    }

    public void agregarCarrito(Carrito carrito) {
        carritos.add(carrito);
    }

    public void eliminarCarrito(Carrito carrito) {
        carritos.remove(carrito);
    }

    public void eliminarUsuario() {
        carritos.clear();
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombreDeUsuario='" + userName + '\'' +
                ", contrasena='" + contrasena + '\'' +
                ", rol=" + rol +
                '}';
    }
}