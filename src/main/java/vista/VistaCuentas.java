package vista;

import controlador.Controlador;
import modelo.Modelo;

import javax.swing.*;
import java.awt.*;

public class VistaCuentas implements Vista {

    private Modelo modelo;
    private Controlador controlador;

    private JFrame ventana;
    private JLabel total;
    private JLabel cuota;

    private PestanaPago pestañaPago;

    private JTextArea pagosARealizar;

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public void crearGUI() {
        ventana = new JFrame("cuentas");

        pestañaPago = new PestanaPago(controlador);
        JPanel resumen = crearPestanyaResumen();
        JPanel realizarPagos = crearPestanyaRealizarPagos();

        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Pago", pestañaPago);
        tabs.add("Resumen", resumen);
        tabs.add("Pagos a realizar", realizarPagos);

        ventana.add(tabs);
        ventana.pack();
        ventana.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ventana.setVisible(true);
    }


    @Override
    public String getNombre() {
        return pestañaPago.getNombre();
    }

    @Override
    public String getConcepto() {
        return pestañaPago.getConcepto();
    }

    @Override
    public String getCantidad() {
        return pestañaPago.getCantidad();
    }

    @Override
    public void setConcepto(String s) {
        pestañaPago.setConcepto(s);
    }

    @Override
    public void setCantidad(String s) {
        pestañaPago.setCantidad(s);
    }

    @Override
    public void mensajeError(String mensaje) {
        JOptionPane.showMessageDialog(ventana, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void focoEnNombre() {
        pestañaPago.focoEnNombre();
    }

    private JPanel crearPestanyaRealizarPagos() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel superior = new JPanel();
        superior.setLayout(new BoxLayout(superior, BoxLayout.PAGE_AXIS));

        pagosARealizar = new JTextArea("Nada que pagar");
        pagosARealizar.setEditable(false);
        superior.add(pagosARealizar);
        panel.add(superior, BorderLayout.PAGE_START);

        return panel;
    }


    private JPanel crearPestanyaResumen() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel superior = new JPanel();
        superior.setLayout(new BoxLayout(superior, BoxLayout.PAGE_AXIS));
        total = new JLabel("Total: 0€");
        superior.add(total);
        cuota = new JLabel("Cuota: 0€");
        superior.add(cuota);
        panel.add(superior, BorderLayout.PAGE_START);

        JTable tabla = new JTable(modelo.getModelo());
        JScrollPane scrollPane = new JScrollPane(tabla);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    @Override
    public void datosCambiados() {
        modelo.getModelo().fireTableDataChanged();
        total.setText("Total: " + modelo.getTotal() + "€");
        cuota.setText("Cuota: " + modelo.getCuota() + "€");
    }

    public void ponerMensaje(String s) {
        pagosARealizar.setText(s);
    }
}
