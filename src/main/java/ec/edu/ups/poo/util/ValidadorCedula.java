package ec.edu.ups.poo.util;

/**
 * Clase de utilidad para validar cédulas ecuatorianas.
 * Proporciona un método estático para verificar si una cédula es válida según el algoritmo oficial.
 */
public class ValidadorCedula {

    /**
     * Verifica si la cédula proporcionada es válida según el algoritmo de validación ecuatoriano.
     *
     * @param cedula Cadena que representa la cédula a validar.
     * @return true si la cédula es válida, false en caso contrario.
     */
    public static boolean esCedulaValida(String cedula) {
        if (cedula == null || !cedula.matches("\\d{10}")) return false;

        int suma = 0;
        int[] coef = {2,1,2,1,2,1,2,1,2};
        try {
            for (int i = 0; i < 9; i++) {
                int num = Character.getNumericValue(cedula.charAt(i));
                int prod = num * coef[i];
                if (prod > 9) prod -= 9;
                suma += prod;
            }
            int ultimoDigito = Character.getNumericValue(cedula.charAt(9));
            int decenaSuperior = ((suma / 10) + 1) * 10;
            int digitoValidador = decenaSuperior - suma;
            if (digitoValidador == 10) digitoValidador = 0;
            return digitoValidador == ultimoDigito;
        } catch (Exception e) {
            return false;
        }
    }
}