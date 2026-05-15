package br.com.fiapbank.application;

import br.com.fiapbank.model.Conta;

public class AutorizacaoService {

    private Conta conta;

    public AutorizacaoService(Conta conta) {
        this.conta = conta;
    }

    public Boolean autorizar(String senha) {
        return this.conta.getContaAcesso().validarSenha(senha);
    }

    public Boolean isBloqueada() {
        return this.conta.getContaAcesso().isBloqueada();
    }

    public Integer getTentativasRestantes() {
        return this.conta.getContaAcesso().getTentativasRestantes();
    }
}
