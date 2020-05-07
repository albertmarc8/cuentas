package controlador;

import modelo.Modelo;
import vista.Vista;

import java.math.BigDecimal;

public class ControladorCuentas implements Controlador {
    Modelo modelo;
    Vista vista;

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public void setVista(Vista vista) {
        this.vista = vista;
    }

    @Override
    public void insertarPago() {
        String nombre = vista.getNombre();
        String concepto = vista.getConcepto();

        BigDecimal cantidad;
        try {
            cantidad = new BigDecimal(vista.getCantidad()).setScale(2,BigDecimal.ROUND_DOWN);
        } catch (NumberFormatException e) {
            vista.mensajeError("Cantidad mal escrita");
            return;
        }

        try {
            modelo.insertarPago(nombre, concepto, cantidad);
        } catch (IllegalArgumentException e) {
            vista.mensajeError(e.getMessage());
            return;
        }

        vista.setConcepto("");
        vista.setCantidad("");
        vista.focoEnNombre();
    }
}
