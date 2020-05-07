package modelo;

import vista.Vista;

import javax.swing.table.AbstractTableModel;
import java.math.BigDecimal;

public interface Modelo {
    void insertarPago(String nombre, String concepto, BigDecimal n);

    AbstractTableModel getModelo();
    void setVista(Vista vista);
    BigDecimal getTotal();
    BigDecimal getCuota();
}
