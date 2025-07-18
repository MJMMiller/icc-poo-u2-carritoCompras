package ec.edu.ups.poo.util;

/**
 * Clase de utilidad para validar contraseñas según criterios de seguridad.
 * Proporciona un método estático para verificar si una contraseña cumple con los requisitos mínimos.
 */
public class ValidadorContrasena {

    /**
     * Verifica si la contraseña proporcionada es válida.
     * Debe tener al menos 6 caracteres, incluir una mayúscula, una minúscula y un carácter especial (@, _ o -).
     *
     * @param contrasena Cadena que representa la contraseña a validar.
     * @return true si la contraseña es válida, false en caso contrario.
     */
    public static boolean esContrasenaValida(String contrasena) {
        if (contrasena == null || contrasena.length() < 6) return false;
        boolean tieneMayuscula = false;
        boolean tieneMinuscula = false;
        boolean tieneEspecial = false;
        for (char c : contrasena.toCharArray()) {
            if (Character.isUpperCase(c)) tieneMayuscula = true;
            else if (Character.isLowerCase(c)) tieneMinuscula = true;
            else if (c == '@' || c == '_' || c == '-') tieneEspecial = true;
        }
        return tieneMayuscula && tieneMinuscula && tieneEspecial;
    }
}