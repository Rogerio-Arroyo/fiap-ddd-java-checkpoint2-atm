package br.com.fiapbank.model;

public class Cliente extends BaseEntity {

    private String nomeCompleto;

    public Cliente(String nomeCompleto) {
        super();
        this.nomeCompleto = nomeCompleto.trim();
    }

    public String obterPrimeiroNome() {
        return this.nomeCompleto.split(" ")[0];
    }

    public String getNomeCompleto() {
        return this.nomeCompleto;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Cliente outro = (Cliente) obj;
        return this.getId().equals(outro.getId());
    }
}
