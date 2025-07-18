package ec.edu.ups.poo.util;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Clase de utilidad para formatear valores de moneda y fecha según la configuración regional.
 */
public class FormateadorUtils {

    /**
     * Formatea una cantidad numérica como moneda según el locale especificado.
     *
     * @param cantidad Valor numérico a formatear.
     * @param locale Configuración regional para el formato de moneda.
     * @return Cadena formateada como moneda.
     */
    public static String formatearMoneda(double cantidad, Locale locale) {
        NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(locale);
        return formatoMoneda.format(cantidad);
    }

    /**
     * Formatea una fecha según el locale especificado.
     *
     * @param fecha Fecha a formatear.
     * @param locale Configuración regional para el formato de fecha.
     * @return Cadena formateada como fecha.
     */
    public static String formatearFecha(Date fecha, Locale locale) {
        DateFormat formato = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
        return formato.format(fecha);
    }
}
