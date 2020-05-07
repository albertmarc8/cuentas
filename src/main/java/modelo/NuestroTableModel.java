package modelo;

import javax.swing.table.AbstractTableModel;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NuestroTableModel extends AbstractTableModel {

    List<Persona> personas;
    private String[] nombres = {"Nombre", "Pagado", "Saldo"};
    private BigDecimal total;
    private BigDecimal cuota;

    public NuestroTableModel() {
        personas = new ArrayList<>();
    }

    public void actualizaReparto() {
        total = new BigDecimal(0);
        for (Persona persona : personas)
            total = total.add(persona.getPagado());
        if (!personas.isEmpty())
            cuota = total.divide(BigDecimal.valueOf(personas.size()), BigDecimal.ROUND_DOWN);
        else
            cuota = new BigDecimal(0);
    }

    /*
        Getters
     */
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    public String getColumnName(int column) {
        return nombres[column];
    }

    public BigDecimal getCuota() {
        return cuota;
    }

    @Override
    public int getRowCount() {
        return personas.size();
    }

    public BigDecimal getTotal() {
        return total;
    }

    @Override
    public Object getValueAt(int row, int column) {
        Persona persona = personas.get(row);
        switch (column) {
            case 0:
                return persona.getNombre();
            case 1:
                return persona.getPagado();
            case 2:
                return (cuota.subtract(persona.getPagado()));
            default:
                return null;
        }
    }

    /*
        Otros
     */
    private BigDecimal maximoDeudor(List<BigDecimal> list) {
        BigDecimal maximo = BigDecimal.ZERO;
        for (BigDecimal saldo : list) {
            if (maximo.compareTo(saldo) < 0) {
                maximo = saldo;
            }
        }
        return maximo;
    }

    private BigDecimal maximoAcreedor(List<BigDecimal> list) {
        BigDecimal maximo = BigDecimal.ZERO;
        for (BigDecimal deuda : list) {
            if (maximo.compareTo(deuda) > 0) {
                maximo = deuda;
            }
        }
        return maximo;
    }

    private boolean isTodosACero(HashMap<String, BigDecimal> values) {
        boolean todosACero = true;
        for (BigDecimal deuda : values.values()) {
            if (deuda.doubleValue() != 0) {
                return false;
            }
        }
        return todosACero;
    }
}