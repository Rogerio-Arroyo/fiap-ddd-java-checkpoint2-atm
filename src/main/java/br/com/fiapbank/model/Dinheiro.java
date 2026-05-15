package br.com.fiapbank.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Dinheiro {

    private BigDecimal valor;

    public Dinheiro(Double valor) {
        this.valor = BigDecimal.valueOf(valor).setScale(2, RoundingMode.HALF_UP);
    }

    public Boolean menorQue(Dinheiro outro) {
        return this.valor.compareTo(outro.valor) < 0;
    }

    public Boolean maiorQue(Dinheiro outro) {
        return this.valor.compareTo(outro.valor) > 0;
    }

    public Dinheiro soma(Dinheiro outro) {
        return new Dinheiro(this.valor.add(outro.valor).doubleValue());
    }

    public Dinheiro realizar(Dinheiro outro) {
        return new Dinheiro(this.valor.subtract(outro.valor).doubleValue());
    }

    public BigDecimal getValor() {
        return this.valor;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Dinheiro outro = (Dinheiro) obj;
        return this.valor.compareTo(outro.valor) == 0;
    }

    @Override
    public String toString() {
        return "R$ " + this.valor.toPlainString();
    }
}
