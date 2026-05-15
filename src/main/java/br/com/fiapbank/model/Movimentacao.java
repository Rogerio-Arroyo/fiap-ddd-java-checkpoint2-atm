package br.com.fiapbank.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Movimentacao {

    private LocalDateTime dataHora;
    private Dinheiro valor;
    private TipoMovimentacao tipo;

    public Movimentacao(LocalDateTime dataHora, Dinheiro valor, TipoMovimentacao tipo) {
        this.dataHora = dataHora;
        this.valor = valor;
        this.tipo = tipo;
    }

    public LocalDateTime getDataHora() {
        return this.dataHora;
    }

    public Dinheiro getValor() {
        return this.valor;
    }

    public TipoMovimentacao getTipo() {
        return this.tipo;
    }

    public String getDataHoraFormatada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return this.dataHora.format(formatter);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Movimentacao outro = (Movimentacao) obj;
        return this.dataHora.equals(outro.dataHora) && this.tipo.equals(outro.tipo);
    }
}
