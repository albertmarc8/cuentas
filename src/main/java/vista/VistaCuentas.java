package vista;

import controlador.Controlador;
import modelo.Modelo;

import javax.swing.*;
import java.awt.*;

public class VistaCuentas implements Vista {

    private Modelo modelo;
    private Controlador controlador;

    private JFrame ventana;

    private PestanaPago pestanyaPago;
    private PestanaResumen pestanyaResumen;

    private JTextArea pagosARealizar;

    public void crearGUI() {
        ventana = new JFrame("cuentas");

        pestanyaPago = new PestanaPago(controlador);
        pestanyaResumen = new PestanaResumen(controlador, modelo);
        JPanel realizarPagos = crearPestanyaRealizarPagos();

        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Pago", pestanyaPago);
        tabs.add("Resumen", pestanyaResumen);
        tabs.add("Pagos a realizar", realizarPagos);

        ventana.add(tabs);
        ventana.pack();
        ventana.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ventana.setVisible(true);
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

    @Override
    public void datosCambiados() {
        modelo.getModelo().fireTableDataChanged();
        pestanyaResumen.setCuota(modelo.getCuota());
        pestanyaResumen.setTotal(modelo.getTotal());
    }

    @Override
    public void focoEnNombre() {
        pestanyaPago.focoEnNombre();
    }

    @Override
    public String getCantidad() {
        return pestanyaPago.getCantidad();
    }

    @Override
    public String getConcepto() {
        return pestanyaPago.getConcepto();
    }

    @Override
    public String getNombre() {
        return pestanyaPago.getNombre();
    }

    @Override
    public void mensajeError(String mensaje) {
        JOptionPane.showMessageDialog(ventana, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void ponerMensaje(String s) {
        pagosARealizar.setText(s);
    }

    @Override
    public void setCantidad(String s) {
        pestanyaPago.setCantidad(s);
    }

    @Override
    public void setConcepto(String s) {
        pestanyaPago.setConcepto(s);
    }

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }
}