package ec.edu.ups.poo.modelo;

import ec.edu.ups.poo.excepciones.CedulaInvalidaException;
import ec.edu.ups.poo.excepciones.ContrasenaInvalidaException;
import ec.edu.ups.poo.util.ValidadorCedula;
import ec.edu.ups.poo.util.ValidadorContrasena;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Clase que representa un usuario del sistema.
 * Contiene información personal, credenciales, rol, carritos asociados y preguntas de validación.
 */
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

    /**
     * Constructor por defecto.
     * Inicializa la lista de carritos vacía.
     */
    public Usuario() {
        this.carritos = new ArrayList<>();
    }

    /**
     * Constructor con parámetros.
     * Valida la cédula y la contraseña antes de asignarlas.
     *
     * @param cedula Cédula del usuario.
     * @param contrasena Contraseña del usuario.
     * @param rol Rol del usuario.
     * @param nombreCompleto Nombre completo del usuario.
     * @param fechaNacimiento Fecha de nacimiento del usuario.
     * @param correo Correo electrónico del usuario.
     * @param telefono Teléfono del usuario.
     * @throws CedulaInvalidaException Si la cédula no es válida.
     * @throws ContrasenaInvalidaException Si la contraseña no cumple los requisitos.
     */
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

    /**
     * Obtiene la cédula del usuario.
     * @return Cédula como String.
     */
    public String getCedula() { return cedula; }

    /**
     * Establece la cédula del usuario, validando su formato.
     * @param cedula Cédula como String.
     * @throws CedulaInvalidaException Si la cédula no es válida.
     */
    public void setCedula(String cedula) throws CedulaInvalidaException {
        if (!ValidadorCedula.esCedulaValida(cedula)) {
            throw new CedulaInvalidaException("La cédula ingresada no es válida.");
        }
        this.cedula = cedula;
    }

    /**
     * Obtiene la contraseña del usuario.
     * @return Contraseña como String.
     */
    public String getContrasena() { return contrasena; }

    /**
     * Establece la contraseña del usuario, validando sus requisitos.
     * @param contrasena Contraseña como String.
     * @throws ContrasenaInvalidaException Si la contraseña no cumple los requisitos.
     */
    public void setContrasena(String contrasena) throws ContrasenaInvalidaException {
        if (!ValidadorContrasena.esContrasenaValida(contrasena)) {
            throw new ContrasenaInvalidaException("La contraseña no cumple los requisitos.");
        }
        this.contrasena = contrasena;
    }

    /**
     * Obtiene el rol del usuario.
     * @return Rol del usuario.
     */
    public Rol getRol() { return rol; }

    /**
     * Establece el rol del usuario.
     * @param rol Rol a asignar.
     */
    public void setRol(Rol rol) { this.rol = rol; }

    /**
     * Obtiene el nombre completo del usuario.
     * @return Nombre completo como String.
     */
    public String getNombreCompleto() { return nombreCompleto; }

    /**
     * Establece el nombre completo del usuario.
     * @param nombreCompleto Nombre completo como String.
     */
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    /**
     * Obtiene la fecha de nacimiento del usuario.
     * @return Fecha de nacimiento como Date.
     */
    public Date getFechaNacimiento() { return fechaNacimiento; }

    /**
     * Establece la fecha de nacimiento del usuario.
     * @param fechaNacimiento Fecha de nacimiento como Date.
     */
    public void setFechaNacimiento(Date fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    /**
     * Obtiene el correo electrónico del usuario.
     * @return Correo electrónico como String.
     */
    public String getCorreo() { return correo; }

    /**
     * Establece el correo electrónico del usuario.
     * @param correo Correo electrónico como String.
     */
    public void setCorreo(String correo) { this.correo = correo; }

    /**
     * Obtiene el teléfono del usuario.
     * @return Teléfono como String.
     */
    public String getTelefono() { return telefono; }

    /**
     * Establece el teléfono del usuario.
     * @param telefono Teléfono como String.
     */
    public void setTelefono(String telefono) { this.telefono = telefono; }

    /**
     * Obtiene la lista de carritos asociados al usuario.
     * @return Lista de Carrito.
     */
    public List<Carrito> getCarritos() { return carritos; }

    /**
     * Establece la lista de carritos asociados al usuario.
     * @param carritos Lista de Carrito.
     */
    public void setCarritos(List<Carrito> carritos) { this.carritos = carritos; }

    /**
     * Obtiene la lista de preguntas de validación asociadas al usuario.
     * @return Lista de PreguntaUsuario.
     */
    public List<PreguntaUsuario> getPreguntaValidacion() { return preguntaValidacion; }

    /**
     * Establece la lista de preguntas de validación asociadas al usuario.
     * @param preguntaValidacion Lista de PreguntaUsuario.
     */
    public void setPreguntaValidacion(List<PreguntaUsuario> preguntaValidacion) { this.preguntaValidacion = preguntaValidacion; }

    /**
     * Devuelve una representación en cadena del usuario.
     * @return Cadena con los datos del usuario.
     */
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