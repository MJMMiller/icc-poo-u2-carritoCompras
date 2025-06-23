package ec.edu.ups.poo.modelo;

public class Usuario {

    private String userName;
    private String contrasena;
    private Rol rol;

    public Usuario(String userName, String contrasena, Rol rol) {
        this.userName = userName;
        this.contrasena = contrasena;
        this.rol = rol;
    }

    public Usuario() {}

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

    @Override
    public String toString() {
        return "Usuario{" +
                "nombreDeUsuario='" + userName + '\'' +
                ", contrasena='" + contrasena + '\'' +
                ", rol=" + rol +
                '}';
    }
}
