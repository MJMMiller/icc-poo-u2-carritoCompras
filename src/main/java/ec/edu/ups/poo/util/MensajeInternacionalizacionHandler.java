package ec.edu.ups.poo.util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Clase para gestionar la internacionalización de mensajes en la aplicación.
 * Permite obtener textos traducidos según el idioma y país configurados.
 */
public class MensajeInternacionalizacionHandler {

    private ResourceBundle bundle;
    private Locale locale;

    /**
     * Constructor que inicializa el manejador con el idioma y país especificados.
     *
     * @param lenguaje Código de idioma (por ejemplo, "es" para español).
     * @param pais Código de país (por ejemplo, "EC" para Ecuador).
     */
    public MensajeInternacionalizacionHandler(String lenguaje, String pais) {
        this.locale = new Locale(lenguaje, pais);
        this.bundle = ResourceBundle.getBundle("mensajes", locale);
    }

    /**
     * Obtiene el mensaje traducido correspondiente a la clave proporcionada.
     *
     * @param key Clave del mensaje en el archivo de recursos.
     * @return Mensaje traducido como String.
     */
    public String get(String key) {
        return bundle.getString(key);
    }

    /**
     * Cambia el idioma y país del manejador, actualizando el ResourceBundle.
     *
     * @param lenguaje Código de idioma.
     * @param pais Código de país.
     */
    public void setLenguaje(String lenguaje, String pais) {
        this.locale = new Locale(lenguaje, pais);
        this.bundle = ResourceBundle.getBundle("mensajes", locale);
    }

    /**
     * Obtiene el objeto Locale actual configurado.
     *
     * @return Instancia de Locale.
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * Obtiene el código de idioma actual configurado.
     *
     * @return Código de idioma como String.
     */
    public String getCodigoIdioma() {
        return locale.getLanguage();
    }

}