package vista;

import controlador.Controlador;
import modelo.Modelo;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

public class VistaCuentas implements Vista {

    private Modelo modelo;
    private Controlador controlador;

    private JFrame ventana;

    private PestanaPago pestañaPago;
    private PestanaResumen pestañaResumen;

    private JTextArea pagosARealizar;

    public void crearGUI() {
        ventana = new JFrame("cuentas");

        pestañaPago = new PestanaPago(controlador);
        pestañaResumen = new PestanaResumen(controlador, modelo);
        JPanel realizarPagos = crearPestanyaRealizarPagos();

        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Pago", pestañaPago);
        tabs.add("Resumen", pestañaResumen);
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
        modelo.getTableModel().fireTableDataChanged();
        pestañaResumen.setCuota(modelo.getCuota());
        pestañaResumen.setTotal(modelo.getTotal());
    }

    @Override
    public void focoEnNombre() {
        pestañaPago.focoEnNombre();
    }

    @Override
    public String getCantidad() {
        return pestañaPago.getCantidad();
    }

    @Override
    public String getConcepto() {
        return pestañaPago.getConcepto();
    }

    @Override
    public String getNombre() {
        return pestañaPago.getNombre();
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
        pestañaPago.setCantidad(s);
    }

    @Override
    public void setConcepto(String s) {
        pestañaPago.setConcepto(s);
    }

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }
}