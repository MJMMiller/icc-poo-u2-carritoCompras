package ec.edu.ups.poo.vista;

import javax.swing.*;
import java.awt.*;

public class MiJDesktopPane extends JDesktopPane {

    public MiJDesktopPane() {
        super();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int padding = 40;
        int boxX = 110, boxY = 120, boxW = 420, boxH = 250, arc = 40;
        g2.setColor(new Color(255, 255, 255, 220));
        g2.fillRoundRect(boxX, boxY, boxW, boxH, arc, arc);
        g2.setStroke(new BasicStroke(4));
        g2.setColor(new Color(180, 180, 180, 220));
        g2.drawRoundRect(boxX, boxY, boxW, boxH, arc, arc);

        int offsetX = boxX + padding;
        int offsetY = boxY + padding;

        g2.setColor(new Color(255, 215, 0));
        g2.fillRoundRect(offsetX, offsetY, 80, 110, 18, 18);
        g2.setColor(new Color(190, 140, 10));
        g2.setStroke(new BasicStroke(5));
        g2.drawArc(offsetX + 25, offsetY - 20, 30, 30, 0, 180);

        g2.setColor(new Color(70, 130, 180));
        g2.fillRoundRect(offsetX + 100, offsetY + 30, 70, 90, 16, 16);
        g2.setColor(new Color(30, 80, 140));
        g2.setStroke(new BasicStroke(5));
        g2.drawArc(offsetX + 120, offsetY + 15, 28, 20, 0, 180);

        g2.setColor(new Color(255, 105, 180));
        g2.fillRoundRect(offsetX + 55, offsetY + 60, 85, 75, 14, 14);
        g2.setColor(new Color(180, 60, 120));
        g2.setStroke(new BasicStroke(5));
        g2.drawArc(offsetX + 78, offsetY + 40, 40, 25, 0, 180);

        int cajaRojaX = offsetX + 220, cajaRojaY = offsetY + 30, cajaRojaAncho = 90, cajaRojaAlto = 70;
        g2.setColor(new Color(220, 20, 60));
        g2.fillRect(cajaRojaX, cajaRojaY, cajaRojaAncho, cajaRojaAlto);
        g2.setColor(new Color(255, 255, 255));
        g2.setStroke(new BasicStroke(6));
        int rojoCentroX = cajaRojaX + cajaRojaAncho / 2;
        int rojoCentroY = cajaRojaY + cajaRojaAlto / 2;
        g2.drawLine(rojoCentroX, cajaRojaY, rojoCentroX, cajaRojaY + cajaRojaAlto);
        g2.drawLine(cajaRojaX, rojoCentroY, cajaRojaX + cajaRojaAncho, rojoCentroY);

        g2.setColor(new Color(200, 0, 60));
        g2.setStroke(new BasicStroke(3));
        g2.drawArc(rojoCentroX - 10, cajaRojaY - 12, 22, 22, 0, 180);

        int cajaVerdeX = offsetX + 180, cajaVerdeY = offsetY + 90, cajaVerdeAncho = 55, cajaVerdeAlto = 40;
        g2.setColor(new Color(60, 179, 113));
        g2.fillRect(cajaVerdeX, cajaVerdeY, cajaVerdeAncho, cajaVerdeAlto);
        g2.setColor(new Color(255, 255, 255));
        g2.setStroke(new BasicStroke(4));
        int verdeCentroX = cajaVerdeX + cajaVerdeAncho / 2;
        int verdeCentroY = cajaVerdeY + cajaVerdeAlto / 2;
        g2.drawLine(verdeCentroX, cajaVerdeY, verdeCentroX, cajaVerdeY + cajaVerdeAlto);
        g2.drawLine(cajaVerdeX, verdeCentroY, cajaVerdeX + cajaVerdeAncho, verdeCentroY);

        g2.setColor(new Color(25, 120, 60));
        g2.setStroke(new BasicStroke(2));
        g2.drawArc(verdeCentroX - 7, cajaVerdeY - 8, 15, 15, 0, 180);

        g2.setColor(new Color(255, 69, 0, 180));
        g2.fillRoundRect(offsetX + 40, offsetY + 150, 80, 28, 10, 10);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 16));
        g2.drawString("% Oferta", offsetX + 50, offsetY + 170);

        g2.setColor(new Color(72, 61, 139, 180));
        g2.fillRoundRect(offsetX + 260, offsetY + 130, 70, 28, 10, 10);
        g2.setColor(Color.WHITE);
        g2.drawString("SALE", offsetX + 275, offsetY + 150);
    }
}