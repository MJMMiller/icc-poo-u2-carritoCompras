package ec.edu.ups.poo.vista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddProductView extends JFrame {

    private JPanel panelPrincipal;
    private JLabel txtRegisterNewProduct;
    private JTextField lblIDProduct;
    private JButton btnSaveProduct;
    private JButton btnCleanLbl;
    private JButton btnExitDisplay;
    private JLabel txtIDProduct;
    private JLabel txtNameProduct;
    private JTextField lblNameProduct;
    private JTextField lblPriceProduct;
    private JLabel txtPriceProductNew;

    public AddProductView() {
        setContentPane(panelPrincipal);
        setTitle("Add Product");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setVisible(true);

        btnSaveProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = txtIDProduct.getText();
                String name = lblNameProduct.getText();
                String price = lblPriceProduct.getText();

                lblIDProduct.setEnabled(false);
                lblNameProduct.setEnabled(false);
                lblPriceProduct.setEnabled(false);

                System.out.println("Product Saved: " +
                                   "\nID: " + id +
                                   "\nName: " + name +
                                   "\nPrice: " + price);
            }
        });

        btnCleanLbl.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblIDProduct.setText("");
                lblNameProduct.setText("");
                lblPriceProduct.setText("");
                lblIDProduct.setEnabled(true);
                lblNameProduct.setEnabled(true);
                lblPriceProduct.setEnabled(true);
            }
        });

        btnExitDisplay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ProductoView();
                dispose();
            }
        });
    }

}
