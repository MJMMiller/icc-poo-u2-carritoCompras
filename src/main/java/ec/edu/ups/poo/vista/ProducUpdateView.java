package ec.edu.ups.poo.vista;

import ec.edu.ups.poo.modelo.Producto;

import javax.swing.*;
import java.util.List;

public class ProducUpdateView extends JInternalFrame {
    private JPanel panelAll;
    private JTextField lblCodeProductSearch;
    private JButton btnSearch;
    private JTextField lblNameProduct;
    private JLabel txtCodeTitle;
    private JPanel panelCenter;
    private JPanel panelTop;
    private JTextField lblPriceProduct;
    private JButton btnUpdate;
    private JLabel txtPriceTitle;
    private JLabel txtNameTitle;
    private JPanel panelMenor;
    private JLabel txtSettingsProduc;
    private JScrollPane scroll;
    private JButton btnListProduct;

    public ProducUpdateView() {
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
    public JButton getBtnUpdate() {return btnUpdate;}

    public JButton getBtnSearch() {
        return btnSearch;
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