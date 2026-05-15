package br.com.fiapbank.model;

import java.time.LocalDate;
import java.util.UUID;

public abstract class BaseEntity {

    protected UUID id;
    protected LocalDate dataCriacao;

    public BaseEntity() {
        this.id = UUID.randomUUID();
        this.dataCriacao = LocalDate.now();
    }

    public UUID getId() {
        return this.id;
    }

    public LocalDate getDataCriacao() {
        return this.dataCriacao;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BaseEntity outro = (BaseEntity) obj;
        return this.id.equals(outro.id);
    }
}
