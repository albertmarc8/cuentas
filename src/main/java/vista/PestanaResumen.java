package vista;

import controlador.Controlador;
import modelo.Modelo;
import modelo.ModeloCuentas;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

public class PestanaResumen extends JPanel {
    private Controlador mControlador;
    private Modelo mModelo;

    private JLabel total;
    private JLabel cuota;

    public PestanaResumen(Controlador controlador, Modelo modelo) {
        this.mControlador = controlador;
        this.mModelo = modelo;

        this.setLayout(new BorderLayout());
        JPanel superior = new JPanel();
        superior.setLayout(new BoxLayout(superior, BoxLayout.PAGE_AXIS));
        total = new JLabel("Total: 0€");
        superior.add(total);
        cuota = new JLabel("Cuota: 0€");
        superior.add(cuota);
        add(superior, BorderLayout.PAGE_START);

        JTable tabla = new JTable(mModelo.getModelo());
        JScrollPane scrollPane = new JScrollPane(tabla);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void setCuota(BigDecimal cuota) {
        this.cuota.setText("Cuota: " + cuota + "€");
    }

    public void setTotal(BigDecimal total) {
        this.total.setText("Cuota: " + total + "€");
    }


}
