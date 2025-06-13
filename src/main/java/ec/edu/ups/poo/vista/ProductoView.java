package ec.edu.ups.poo.vista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductoView extends JFrame{
    private JPanel panelPrincipal;
    private JLabel txtTituloProductosVentana;
    private JButton btnAddNew;
    private JButton btnDelate;
    private JButton btnUpdate;
    private JCheckBox checkBoxHability;
    private JSpinner spinnerQuantityProduct;
    private JLabel txtCode;
    private JLabel txtName;
    private JLabel txtPrice;
    private JLabel txtQuantity;
    private JLabel txtCodeProduct;
    private JLabel txtNameProduct;
    private JLabel txtPriceProduct;
    private JLabel txtDescount;
    private JLabel txtSubTotal;
    private JLabel txtIVA;
    private JLabel txtTotal;
    private JLabel txtPorcentDescount;
    private JLabel txtMountSubTotal;
    private JLabel txtMountIVA;
    private JLabel txtMountTotal;

    public ProductoView() {
        setTitle("Productos");
        setSize(800, 700);
        setContentPane(panelPrincipal);
        setLocationRelativeTo(null);
        setVisible(true);

        btnAddNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddProductView();
                dispose();
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new ProductoView();
            }
        });
    }
}
