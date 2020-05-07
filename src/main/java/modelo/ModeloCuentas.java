package modelo;

import vista.Vista;

import javax.swing.table.AbstractTableModel;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ModeloCuentas extends AbstractTableModel implements Modelo {

    private String[] nombres =  {"Nombre", "Pagado", "Saldo"};

    private Vista vista;
    List<Persona> personas;
    private BigDecimal total;
    private BigDecimal cuota;

    public ModeloCuentas() {
        personas = new ArrayList<>();
    }

    @Override
    public void setVista(Vista vista) {
        this.vista = vista;
    }

    @Override
    public BigDecimal getTotal() {
        return total;
    }

    @Override
    public BigDecimal getCuota() {
        return cuota;
    }

    @Override
    public void insertarPago(String nombre, String concepto, BigDecimal cantidad) {
        if (nombre.isEmpty())
            throw new IllegalArgumentException("El nombre no puede ser vacío");
        if (concepto.isEmpty())
            throw new IllegalArgumentException("El concepto no puede ser vacío");
        if (cantidad.doubleValue() <= 0.0d)
            throw new IllegalArgumentException("La cantidad tiene que ser mayor que cero");

        Persona persona;
        int posicion = -1;
        for (int i = 0; i < personas.size(); i++)
            if (personas.get(i).getNombre().equals(nombre)) {
                posicion = i;
                break;
            }
        if (posicion != -1)
            persona = personas.get(posicion);
        else {
            persona = new Persona(nombre);
            personas.add(persona);
        }
        persona.insertarPago(concepto, cantidad);
        actualizaReparto();
        realizarPagos();
        vista.datosCambiados();
    }

    private void actualizaReparto() {
        total = new BigDecimal(0);
        for (Persona persona: personas)
            total = total.add(persona.getPagado());
        if (!personas.isEmpty())
            cuota = total.divide(BigDecimal.valueOf(personas.size()), BigDecimal.ROUND_DOWN);
        else
            cuota = new BigDecimal(0);
    }

    @Override
    public AbstractTableModel getModelo() {
        return this;
    }

    @Override
    public int getRowCount() {
        return personas.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
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

    @Override
    public String getColumnName(int column) {
        return nombres[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }

    public void realizarPagos() {
        List<BigDecimal> saldos = new ArrayList<>();

        for (Persona persona : personas) {
            BigDecimal result = cuota.subtract(persona.getPagado());
            saldos.add(result);
        }

        String s = "";
        while(true) { // TODO
            BigDecimal maximoDeudor = BigDecimal.ZERO;
            BigDecimal maximoAcreedor = BigDecimal.ZERO;
            int personaDeudor = -1;
            int personaAcreedor = -1;

            for (int i = 0; i < saldos.size(); i++) {
                BigDecimal saldo = saldos.get(i);
                if (maximoDeudor.compareTo(saldo) > 0) {
                    maximoDeudor = saldo;
                    personaDeudor = i;
                }
                if (maximoAcreedor.compareTo(saldo) < 0) {
                    maximoAcreedor = saldo;
                    personaAcreedor = i;
                }
            }

            if (personaAcreedor == -1 || personaDeudor == -1) {
                break;
            }

            BigDecimal cantidad = maximoAcreedor.min(maximoDeudor.abs());
            s = s.concat(personas.get(personaAcreedor) + " paga " + cantidad + " € a " + personas.get(personaDeudor) + "\n");
            saldos.set(personaAcreedor, saldos.get(personaAcreedor).subtract(cantidad));
            saldos.set(personaDeudor, saldos.get(personaDeudor).add(cantidad));
        }

        vista.ponerMensaje(s);
    }

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
