package br.com.fiapbank.model;

public class ContaCorrente extends Conta {

    public static final Double TAXA_MANUTENCAO = 25.00;

    public ContaCorrente(Cliente cliente, ContaAcesso contaAcesso, Dinheiro saldo) {
        super(cliente, contaAcesso, saldo, TAXA_MANUTENCAO);
    }

    @Override
    protected void aplicarRegraDeTaxa() {
        Dinheiro valorTaxa = new Dinheiro(this.taxa);
        this.saldo = this.saldo.realizar(valorTaxa);
        registrarMovimentacao(TipoMovimentacao.TAXA, valorTaxa);
    }
}
