package ec.edu.ups.poo.excepciones;

/**
 * Excepción que indica que la contraseña proporcionada es inválida.
 * Se utiliza para señalar errores de validación en el campo de contraseña de usuario.
 */
public class ContrasenaInvalidaException extends Exception {

    /**
     * Constructor de ContrasenaInvalidaException.
     *
     * @param mensaje Mensaje descriptivo del error de contraseña inválida.
     */
    public ContrasenaInvalidaException(String mensaje) {
        super(mensaje);
    }
}