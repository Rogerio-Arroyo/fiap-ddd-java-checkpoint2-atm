package br.com.fiapbank.model;

import java.math.BigDecimal;

public class ContaPoupanca extends Conta {

    public static final Double RENDIMENTO_MENSAL = 1.0;

    public ContaPoupanca(Cliente cliente, ContaAcesso contaAcesso, Dinheiro saldo) {
        super(cliente, contaAcesso, saldo, 0.0);
    }

    @Override
    protected void aplicarRegraDeTaxa() {
        BigDecimal percentual = BigDecimal.valueOf(RENDIMENTO_MENSAL).divide(BigDecimal.valueOf(100));
        BigDecimal valorRendimento = this.saldo.getValor().multiply(percentual);
        Dinheiro rendimento = new Dinheiro(valorRendimento.doubleValue());
        this.saldo = this.saldo.soma(rendimento);
        registrarMovimentacao(TipoMovimentacao.RENDIMENTO, rendimento);
    }
}
