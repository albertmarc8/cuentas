package vista;

import controlador.Controlador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PestanaPago extends JPanel {
    private Controlador controlador;

    private JTextField nombre;
    private JTextField concepto;
    private JTextField cantidad;

    public PestanaPago(Controlador controlador) {
        this.controlador = controlador;
        setLayout(new BorderLayout());

        JPanel datos = new JPanel();
        datos.setLayout(new BoxLayout(datos, BoxLayout.PAGE_AXIS));
        nombre = creaEntrada(datos, "Nombre:");
        concepto = creaEntrada(datos, "Concepto:");
        cantidad = creaEntrada(datos, "Cantidad:");
        add(datos, BorderLayout.PAGE_START);

        JButton boton = new JButton("Pagar");
        add(boton,BorderLayout.PAGE_END);

        final ActionListener pagoListener = e -> controlador.insertarPago();

        nombre.addActionListener(e -> concepto.requestFocusInWindow());
        concepto.addActionListener(e -> cantidad.requestFocusInWindow());
        cantidad.addActionListener(pagoListener);
        boton.addActionListener(pagoListener);
    }

    private JTextField creaEntrada(JPanel datos, String mensaje) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(4, 0));
        panel.add(new JLabel(mensaje), BorderLayout.LINE_START);
        JTextField entrada = new JTextField();
        panel.add(entrada, BorderLayout.CENTER);
        datos.add(panel);
        return entrada;
    }

    public String getNombre() {
        return nombre.getText();
    }

    public String getConcepto() {
        return concepto.getText();
    }

    public void setConcepto(String s) {
        concepto.setText(s);
    }

    public String getCantidad() {
        return cantidad.getText();
    }

    public void setCantidad(String s) {
        cantidad.setText(s);
    }

    public void focoEnNombre() {
        nombre.requestFocusInWindow();
    }
}
