package ec.edu.ups.poo.util;

public class ValidadorContrasena {

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