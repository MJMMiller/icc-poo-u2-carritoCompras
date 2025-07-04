package ec.edu.ups.poo.vista;

import javax.swing.*;
import java.awt.*;

public class MiJDesktopPane extends JDesktopPane {

    public MiJDesktopPane() {
        setOpaque(true);
        setBackground(new Color(33, 37, 43)); // Fondo gris oscuro
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int fullW = getWidth();
        int fullH = getHeight();

        // Escalado reducido
        double scale = 0.4; // Puedes bajar a 0.35 si aún lo ves muy grande
        int scaledW = (int)(fullW * scale);
        int scaledH = (int)(fullH * scale);

        int offsetX = (fullW - scaledW) / 2;
        int offsetY = (fullH - scaledH) / 2;

        g2.translate(offsetX, offsetY);
        g2.scale(scale, scale);

        drawOxxoDesign(g2, fullW, fullH);

        g2.dispose();
    }

    private void drawOxxoDesign(Graphics2D g2, int w, int h) {
        Color rojoOxxo = new Color(214, 38, 61);
        Color amarilloOxxo = new Color(253, 184, 39);
        Color sombraColor = new Color(0, 0, 0, 100);

        int bordeRadio = Math.min(w, h) / 22;

        // Fondo blanco redondeado
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(0, 0, w, h, bordeRadio * 2, bordeRadio * 2);

        // Franjas amarillas
        int franjaAmarilla = (int)(h * 0.13);
        g2.setColor(amarilloOxxo);
        g2.fillRect(0, 0, w, franjaAmarilla);
        g2.fillRect(0, h - franjaAmarilla, w, franjaAmarilla);

        // Franjas blancas
        int franjaBlanca = (int)(h * 0.04);
        g2.setColor(Color.WHITE);
        g2.fillRect(0, franjaAmarilla, w, franjaBlanca);
        g2.fillRect(0, h - franjaAmarilla - franjaBlanca, w, franjaBlanca);

        // Fondo rojo central
        int yRojo = franjaAmarilla + franjaBlanca;
        int hRojo = h - 2 * (franjaAmarilla + franjaBlanca);
        g2.setColor(rojoOxxo);
        g2.fillRect(0, yRojo, w, hRojo);

        // === TEXTO "POO" ===
        String texto = "POO";

        // Tamaño fuente para el texto
        int fontSize = (int)(hRojo * 1.0);
        Font fuente = new Font("Segoe UI", Font.BOLD, fontSize);
        g2.setFont(fuente);

        FontMetrics fm = g2.getFontMetrics();
        int textoAncho = fm.stringWidth(texto);
        int textoAlto = fm.getAscent();

        int xTexto = (w - textoAncho) / 2;

        // Aquí centramos verticalmente mejor:
        int yTexto = yRojo + (hRojo / 2) + (textoAlto / 2) - 120;

        // Sombra del texto
        int sombraDespl = fontSize / 20;
        g2.setColor(sombraColor);
        g2.drawString(texto, xTexto + sombraDespl, yTexto + sombraDespl);

        // Texto principal
        g2.setColor(Color.WHITE);
        g2.drawString(texto, xTexto, yTexto);
    }

}