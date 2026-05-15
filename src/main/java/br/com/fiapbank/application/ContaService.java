package br.com.fiapbank.application;

import br.com.fiapbank.model.Conta;
import br.com.fiapbank.model.Dinheiro;
import br.com.fiapbank.model.Movimentacao;

import java.util.List;

public class ContaService {

    private Conta conta;

    public ContaService(Conta conta) {
        this.conta = conta;
    }

    public void realizarDeposito(Dinheiro valor) {
        this.conta.realizarDeposito(valor);
    }

    public void realizarSaque(Dinheiro valor) {
        this.conta.realizarSaque(valor);
    }

    public Dinheiro obterSaldo() {
        return this.conta.getSaldo();
    }

    public List<Movimentacao> obterMovimentacoes() {
        return this.conta.getMovimentacoes();
    }
}
