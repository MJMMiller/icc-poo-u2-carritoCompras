package ec.edu.ups.poo.vista.inicio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SeleccionarArchivoView extends JDialog {
    private JTextField txtRutaArchivo;
    private JButton btnElegirArchivo, btnAceptar, btnCancelar;
    private String rutaSeleccionada = null;

    public SeleccionarArchivoView(Window parent) {
        super(parent, "Seleccionar archivo", ModalityType.APPLICATION_MODAL);
        setLayout(new BorderLayout(10,10));

        txtRutaArchivo = new JTextField(30);
        txtRutaArchivo.setEditable(false);
        btnElegirArchivo = new JButton("Elegir archivo...");
        btnAceptar = new JButton("Aceptar");
        btnCancelar = new JButton("Cancelar");

        JPanel paneCentral = new JPanel();
        paneCentral.add(txtRutaArchivo);
        paneCentral.add(btnElegirArchivo);

        JPanel paneInferior = new JPanel();
        paneInferior.add(btnAceptar);
        paneInferior.add(btnCancelar);

        add(paneCentral, BorderLayout.CENTER);
        add(paneInferior, BorderLayout.SOUTH);

        btnElegirArchivo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Selecciona el archivo de almacenamiento");
                int seleccion = fileChooser.showSaveDialog(SeleccionarArchivoView.this);
                if (seleccion == JFileChooser.APPROVE_OPTION) {
                    rutaSeleccionada = fileChooser.getSelectedFile().getAbsolutePath();
                    txtRutaArchivo.setText(rutaSeleccionada);
                }
            }
        });

        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rutaSeleccionada != null && !rutaSeleccionada.isEmpty()) {
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(SeleccionarArchivoView.this, "¡Debes seleccionar una ruta de archivo!", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rutaSeleccionada = null;
                dispose();
            }
        });

        pack();
        setLocationRelativeTo(parent);
    }

    public String getRutaSeleccionada() {
        return rutaSeleccionada;
    }
}