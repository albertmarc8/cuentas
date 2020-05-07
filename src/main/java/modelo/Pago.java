package modelo;

import java.math.BigDecimal;

public class Pago {
    private String concepto;
    private BigDecimal total;

    public Pago(String concepto, BigDecimal total) {
        this.concepto = concepto;
        this.total = total;
    }

    public String getConcepto() {
        return concepto;
    }

    public BigDecimal getTotal() {
        return total;
    }
}
