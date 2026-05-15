package br.com.fiapbank.model;

public class ContaAcesso {

    public static final Integer MAXIMO_TENTATIVAS = 3;

    private String senha;
    private Integer tentativas;
    private Boolean bloqueado;

    public ContaAcesso(String senha) {
        this.senha = senha;
        this.tentativas = 0;
        this.bloqueado = false;
    }

    public Boolean validarSenha(String senha) {
        if (this.bloqueado) {
            return false;
        }

        if (this.senha.equals(senha)) {
            resetarTentativas();
            return true;
        }

        this.tentativas++;
        if (this.tentativas >= MAXIMO_TENTATIVAS) {
            this.bloqueado = true;
        }
        return false;
    }

    public Boolean isBloqueada() {
        return this.bloqueado;
    }

    public void resetarTentativas() {
        this.tentativas = 0;
    }

    public Integer getTentativasRestantes() {
        return MAXIMO_TENTATIVAS - this.tentativas;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ContaAcesso outro = (ContaAcesso) obj;
        return this.senha.equals(outro.senha);
    }
}
