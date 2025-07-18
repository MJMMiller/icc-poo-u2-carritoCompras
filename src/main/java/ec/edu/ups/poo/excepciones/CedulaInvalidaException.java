package ec.edu.ups.poo.excepciones;

/**
 * Excepción que indica que la cédula proporcionada es inválida.
 * Se utiliza para señalar errores de validación en el campo de cédula de usuario.
 */
public class CedulaInvalidaException extends Exception {

  /**
   * Constructor de CedulaInvalidaException.
   *
   * @param mensaje Mensaje descriptivo del error de cédula inválida.
   */
  public CedulaInvalidaException(String mensaje) {
    super(mensaje);
  }
}