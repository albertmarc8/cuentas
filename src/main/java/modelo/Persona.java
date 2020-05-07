package modelo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Persona {
    private String nombre;
    private List<Pago> pagos;
    private BigDecimal pagado;

    public Persona(String nombre) {
        this.nombre = nombre;
        pagos = new ArrayList<>();
        pagado = new BigDecimal(0);
    }

    public void insertarPago(String concepto, BigDecimal cantidad) {
        pagos.add(new Pago(concepto, cantidad));
        pagado = pagado.add(cantidad);
    }

    public String getNombre() {
        return nombre;
    }

    public List<Pago> getPagos() {
        return pagos;
    }

    public BigDecimal getPagado() {
        return pagado;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
