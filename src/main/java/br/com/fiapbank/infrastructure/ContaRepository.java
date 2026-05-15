package br.com.fiapbank.infrastructure;

import br.com.fiapbank.model.Conta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Repositório em memória para armazenamento temporário de contas.
 * Simula a camada de persistência de dados (Infrastructure Layer - DDD).
 */
public class ContaRepository {

    private List<Conta> contas;

    public ContaRepository() {
        this.contas = new ArrayList<>();
    }

    public void salvar(Conta conta) {
        this.contas.add(conta);
    }

    public Conta buscarPorId(UUID id) {
        for (Integer i = 0; i < contas.size(); i++) {
            Conta conta = contas.get(i);
            if (conta.getId().equals(id)) {
                return conta;
            }
        }
        return null;
    }

    public Conta buscarPorNome(String nomeCompleto) {
        for (Integer i = 0; i < contas.size(); i++) {
            Conta conta = contas.get(i);
            if (conta.getCliente().getNomeCompleto().equalsIgnoreCase(nomeCompleto.trim())) {
                return conta;
            }
        }
        return null;
    }

    public List<Conta> listarTodas() {
        return this.contas;
    }
}
