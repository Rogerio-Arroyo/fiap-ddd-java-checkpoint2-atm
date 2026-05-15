# FIAP Bank ATM - Checkpoint 2

## Evolução Orientada a Objetos (DDD)

**Disciplina:** Domain Driven Design - Java  
**Curso:** Engenharia de Software - 2ESPH  
**Professor:** Eduardo dos Santos Ramos

## Descrição

Refatoração do sistema de Caixa Eletrônico (ATM) do FIAP Bank utilizando os pilares da Orientação a Objetos e Domain-Driven Design (DDD), com separação clara de responsabilidades em camadas.

## Estrutura de Pacotes

```
src/main/java/
└── br/com/fiapbank/
    ├── Main.java
    ├── model/
    │   ├── BaseEntity.java
    │   ├── Cliente.java
    │   ├── Conta.java
    │   ├── ContaAcesso.java
    │   ├── ContaCorrente.java
    │   ├── ContaPoupanca.java
    │   ├── Dinheiro.java
    │   ├── Movimentacao.java
    │   ├── StatusConta.java
    │   └── TipoMovimentacao.java
    ├── application/
    │   ├── AutorizacaoService.java
    │   ├── ContaFactory.java
    │   └── ContaService.java
    ├── presentation/
    │   └── TerminalBancarioController.java
    └── infrastructure/
```
