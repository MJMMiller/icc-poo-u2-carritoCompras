package ec.edu.ups.poo.vista;

import javax.swing.*;

public class LogInView extends JFrame {

    private JPanel panelPrincipal;
    private JButton btnEmpezar;
    private JButton btnSalir;

    public static void main(String[] args) {
        LogInView ventana = new LogInView();
    }

    public LogInView() {
        setTitle("Log In");
        setSize(500,200);
        setContentPane(panelPrincipal);
        setLocationRelativeTo(null);
        setVisible(true);

        btnEmpezar.addActionListener(e -> {
            new ProductoView();
            dispose();
        });

        btnSalir.addActionListener(e -> {
            System.exit(0);
        });
    }
}
