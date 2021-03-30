package modelo;

import vista.Vista;

import javax.swing.table.AbstractTableModel;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ModeloCuentas implements Modelo {

    private Vista vista;

    private NuestroTableModel tableModel;

    public ModeloCuentas() {
        tableModel = new NuestroTableModel();
    }

    @Override
    public void setVista(Vista vista) {
        this.vista = vista;
    }

    @Override
    public BigDecimal getTotal() {
        return tableModel.getTotal();
    }

    @Override
    public BigDecimal getCuota() {
        return tableModel.getCuota();
    }


    @Override
    public void insertarPago(String nombre, String concepto, BigDecimal cantidad) {
        if (nombre.isEmpty())
            throw new IllegalArgumentException("El nombre no puede ser vacío");
        if (concepto.isEmpty())
            throw new IllegalArgumentException("El concepto no puede ser vacío");
        if (cantidad.doubleValue() < 0.0d)
            throw new IllegalArgumentException("La cantidad tiene que ser cero o mayor");

        Persona persona;
        int posicion = -1;
        for (int i = 0; i < tableModel.personas.size(); i++)
            if (tableModel.personas.get(i).getNombre().equals(nombre)) {
                posicion = i;
                break;
            }
        if (posicion != -1)
            persona = tableModel.personas.get(posicion);
        else {
            persona = new Persona(nombre);
            tableModel.personas.add(persona);
        }
        persona.insertarPago(concepto, cantidad);
        tableModel.actualizaReparto();
        realizarPagos();
        vista.datosCambiados();
    }

    @Override
    public AbstractTableModel getModelo() {
        return tableModel;
    }

    public void realizarPagos() {
        List<BigDecimal> saldos = new ArrayList<>();

        for (Persona persona : tableModel.personas) {
            BigDecimal result = tableModel.getCuota().subtract(persona.getPagado());
            saldos.add(result);
        }

        String s = "";
        while (true) {
            int personaDeudor = -1;
            int personaAcreedor = -1;
            BigDecimal maximoDeudor = BigDecimal.ZERO;
            BigDecimal maximoAcreedor = BigDecimal.ZERO;

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
            s = s.concat(tableModel.personas.get(personaAcreedor) + " paga " + cantidad + " € a " + tableModel.personas.get(personaDeudor) + "\n");
            saldos.set(personaAcreedor, saldos.get(personaAcreedor).subtract(cantidad));
            saldos.set(personaDeudor, saldos.get(personaDeudor).add(cantidad));
        }
        vista.ponerMensaje(s);
    }

}
