package ec.edu.ups.poo.vista.producto;

import ec.edu.ups.poo.modelo.Producto;

import javax.swing.*;
import java.util.List;

public class ProductDelateView extends JInternalFrame {
    private JPanel panelTop;
    private JLabel txtSettingsProduc;
    private JPanel panelCenter;
    private JLabel txtCodeTitle;
    private JTextField lblCodeProductSearch;
    private JButton btnSearch;
    private JPanel panelMenor;
    private JLabel txtNameTitle;
    private JLabel txtPriceTitle;
    private JTextField lblNameProduct;
    private JTextField lblPriceProduct;
    private JButton btnDelate;
    private JPanel panelAll;

    public ProductDelateView() {
        setContentPane(panelAll);
        setTitle("Settings Products");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 250);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
    }

    public JTextField getLblCodeProductSearch() {
        return lblCodeProductSearch;
    }

    public JTextField getLblNameProduct() {
        return lblNameProduct;
    }

    public JTextField getLblPriceProduct() {
        return lblPriceProduct;
    }

    public JButton getBtnSearch() {
        return btnSearch;
    }

    public JButton getBtnDelate() {
        return btnDelate;
    }

    public void mostrarProductos(List<Producto> productos) {
        lblNameProduct.setText(productos.get(0).getNombre());
        lblPriceProduct.setText(String.valueOf(productos.get(0).getPrecio()));
    }

    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }

    public int mostrarMensajeDelate(String mensaje, String titulo, int tipo) {
        Object[] botones = {"Confirm", "Cancel"};
        return JOptionPane.showOptionDialog(
                this, mensaje, titulo,
                JOptionPane.DEFAULT_OPTION, tipo,
                null, botones, botones[0]);
    }

    public void limpiarCampos() {
        lblCodeProductSearch.setText("");
        lblNameProduct.setText("");
        lblPriceProduct.setText("");
    }
}
