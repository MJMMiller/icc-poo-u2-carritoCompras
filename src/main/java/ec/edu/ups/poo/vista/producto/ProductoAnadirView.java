package ec.edu.ups.poo.vista.producto;

import ec.edu.ups.poo.modelo.Producto;

import javax.swing.*;
import java.util.List;

public class ProductoAnadirView extends JInternalFrame {

    private JPanel panelAll;
    private JTextField lblCodeProduct;
    private JButton btnRegisterNewProduct;
    private JButton btnCleanInputs;
    private JLabel txtCodeProduct;
    private JLabel txtNameProduct;
    private JLabel txtPriceProduct;
    private JTextField lblNameProduct;
    private JTextField lblPriceProduct;
    private JPanel panelTop;
    private JPanel panelCenter;
    private JPanel panelBottom;
    private JLabel txtRegisterNewProduct;

    public ProductoAnadirView() {
        setContentPane(panelAll);
        setTitle("Register New Product");
        setSize(500, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
    }

    public void limpiarCampos() {
        lblCodeProduct.setText("");
        lblNameProduct.setText("");
        lblPriceProduct.setText("");
    }

    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }

    public void mostrarProductos(List<Producto> productos) {
        for (Producto producto : productos) {
            System.out.println(producto);
        }
    }

    public void inhabilitarCampos() {
        lblCodeProduct.setEnabled(false);
        lblNameProduct.setEnabled(false);
        lblPriceProduct.setEnabled(false);
        btnRegisterNewProduct.setEnabled(false);
    }

    public void habilitarCampos() {
        lblCodeProduct.setEnabled(true);
        lblNameProduct.setEnabled(true);
        lblPriceProduct.setEnabled(true);
        btnRegisterNewProduct.setEnabled(true);
    }

    public JButton getBtnRegisterNewProduct() {
        return btnRegisterNewProduct;
    }

    public JButton getBtnCleanInputs() {
        return btnCleanInputs;
    }

    public JTextField getLblCodeProduct() {
        return lblCodeProduct;
    }

    public JTextField getLblNameProduct() {
        return lblNameProduct;
    }

    public JTextField getLblPriceProduct() {
        return lblPriceProduct;
    }

}