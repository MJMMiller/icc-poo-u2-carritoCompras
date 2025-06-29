package ec.edu.ups.poo.modelo;

import java.util.ArrayList;
import java.util.List;

public class Usuario {

    private String userName;
    private String contrasena;
    private Rol rol;
    private List<Carrito> carritos;
    private List<PreguntaUsuario> preguntaValidacion = new ArrayList<>();

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

    public void setCarritos(List<Carrito> carritos) {
        this.carritos = carritos;
    }

    public List<PreguntaUsuario> getPreguntaValidacion() {
        return preguntaValidacion;
    }

    public void setPreguntaValidacion(List<PreguntaUsuario> preguntaValidacion) {
        this.preguntaValidacion = preguntaValidacion;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "userName='" + userName + '\'' +
                ", contrasena='" + contrasena + '\'' +
                ", rol=" + rol +
                ", carritos=" + carritos +
                ", preguntaValidacion=" + preguntaValidacion +
                '}';
    }
}