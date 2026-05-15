package br.com.fiapbank.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class Conta extends BaseEntity {

    protected Cliente cliente;
    protected Dinheiro saldo;
    protected Double taxa;
    protected StatusConta status;
    protected LocalDate dataAbertura;
    protected ContaAcesso contaAcesso;
    protected List<Movimentacao> movimentacoes;

    public Conta(Cliente cliente, ContaAcesso contaAcesso, Dinheiro saldo, Double taxaMensal) {
        super();
        this.cliente = cliente;
        this.contaAcesso = contaAcesso;
        this.saldo = saldo;
        this.taxa = taxaMensal;
        this.status = StatusConta.ATIVA;
        this.dataAbertura = LocalDate.now();
        this.movimentacoes = new ArrayList<>();
    }

    public void realizarSaque(Dinheiro valor) {
        if (valor.getValor().doubleValue() <= 0) {
            throw new IllegalArgumentException("Valor do saque deve ser maior que zero.");
        }
        if (valor.maiorQue(this.saldo)) {
            throw new IllegalStateException("Saldo insuficiente para realizar o saque.");
        }
        sacar(valor);
        registrarMovimentacao(TipoMovimentacao.SAQUE, valor);
        aplicarRegraDeTaxa();
    }

    public void realizarDeposito(Dinheiro valor) {
        if (valor.getValor().doubleValue() <= 0) {
            throw new IllegalArgumentException("Valor do depósito deve ser maior que zero.");
        }
        depositar(valor);
        registrarMovimentacao(TipoMovimentacao.DEPOSITO, valor);
    }

    public ContaAcesso getContaAcesso() {
        return this.contaAcesso;
    }

    public Dinheiro getSaldo() {
        return this.saldo;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public LocalDate getDataAbertura() {
        return this.dataAbertura;
    }

    public StatusConta getStatus() {
        return this.status;
    }

    public List<Movimentacao> getMovimentacoes() {
        return this.movimentacoes;
    }

    protected abstract void aplicarRegraDeTaxa();

    private void depositar(Dinheiro valor) {
        this.saldo = this.saldo.soma(valor);
    }

    private void sacar(Dinheiro valor) {
        this.saldo = this.saldo.realizar(valor);
    }

    protected void registrarMovimentacao(TipoMovimentacao tipo, Dinheiro valor) {
        Movimentacao mov = new Movimentacao(LocalDateTime.now(), valor, tipo);
        this.movimentacoes.add(mov);
    }
}
