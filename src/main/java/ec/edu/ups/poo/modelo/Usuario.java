package ec.edu.ups.poo.modelo;

import ec.edu.ups.poo.excepciones.CedulaInvalidaException;
import ec.edu.ups.poo.excepciones.ContrasenaInvalidaException;
import ec.edu.ups.poo.util.ValidadorCedula;
import ec.edu.ups.poo.util.ValidadorContrasena;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Usuario {

    private String cedula;
    private String contrasena;
    private Rol rol;
    private String nombreCompleto;
    private Date fechaNacimiento;
    private String correo;
    private String telefono;
    private List<Carrito> carritos;
    private List<PreguntaUsuario> preguntaValidacion = new ArrayList<>();

    public Usuario() {
        this.carritos = new ArrayList<>();
    }

    public Usuario(String cedula, String contrasena, Rol rol, String nombreCompleto, Date fechaNacimiento, String correo, String telefono)
            throws CedulaInvalidaException, ContrasenaInvalidaException {
        setCedula(cedula);
        setContrasena(contrasena);
        this.rol = rol;
        this.nombreCompleto = nombreCompleto;
        this.fechaNacimiento = fechaNacimiento;
        this.correo = correo;
        this.telefono = telefono;
        this.carritos = new ArrayList<>();
    }

    public String getCedula() { return cedula; }
    public void setCedula(String cedula) throws CedulaInvalidaException {
        if (!ValidadorCedula.esCedulaValida(cedula)) {
            throw new CedulaInvalidaException("La cédula ingresada no es válida.");
        }
        this.cedula = cedula;
    }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) throws ContrasenaInvalidaException {
        if (!ValidadorContrasena.esContrasenaValida(contrasena)) {
            throw new ContrasenaInvalidaException("La contraseña no cumple los requisitos.");
        }
        this.contrasena = contrasena;
    }
    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public Date getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(Date fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public List<Carrito> getCarritos() { return carritos; }
    public void setCarritos(List<Carrito> carritos) { this.carritos = carritos; }
    public List<PreguntaUsuario> getPreguntaValidacion() { return preguntaValidacion; }
    public void setPreguntaValidacion(List<PreguntaUsuario> preguntaValidacion) { this.preguntaValidacion = preguntaValidacion; }

    @Override
    public String toString() {
        return "Usuario{" +
                "userName='" + cedula + '\'' +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", correo='" + correo + '\'' +
                ", telefono='" + telefono + '\'' +
                ", rol=" + rol +
                ", carritos=" + carritos +
                ", preguntaValidacion=" + preguntaValidacion +
                '}';
    }
}