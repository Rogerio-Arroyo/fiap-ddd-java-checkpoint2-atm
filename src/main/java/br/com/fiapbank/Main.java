package br.com.fiapbank;

import br.com.fiapbank.application.AutorizacaoService;
import br.com.fiapbank.application.ContaFactory;
import br.com.fiapbank.application.ContaService;
import br.com.fiapbank.model.Cliente;
import br.com.fiapbank.model.Conta;
import br.com.fiapbank.model.ContaAcesso;
import br.com.fiapbank.model.Dinheiro;
import br.com.fiapbank.presentation.TerminalBancarioController;

public class Main {

    public static void main(String[] args) {

        // Cria o controller temporário para capturar nome e senha (presentation)
        TerminalBancarioController terminal = new TerminalBancarioController(null, null);

        // FASE A - Cadastro do cliente
        String nomeCompleto = terminal.solicitarNomeCompleto();
        String senha = terminal.solicitarCadastroSenha();

        // Cria entidades no modelo
        Cliente cliente = new Cliente(nomeCompleto);
        ContaAcesso contaAcesso = new ContaAcesso(senha);

        System.out.println();
        System.out.println("Olá, " + cliente.obterPrimeiroNome() + "! Seja bem-vindo(a) ao FIAP Bank.");

        // Cria conta via Factory (Singleton + Factory)
        ContaFactory factory = ContaFactory.getInstance();
        Dinheiro saldoInicial = new Dinheiro(0.0);
        Conta conta = factory.criarContaCorrente(cliente, contaAcesso, saldoInicial);

        // Cria serviços da camada application
        ContaService contaService = new ContaService(conta);
        AutorizacaoService autorizacaoService = new AutorizacaoService(conta);

        // Recria o controller com os serviços configurados
        terminal = new TerminalBancarioController(contaService, autorizacaoService);

        // FASE B - Autenticação
        Boolean autenticado = terminal.realizarAutenticacao();

        if (!autenticado) {
            return;
        }

        System.out.println("Acesso autorizado! Bem-vindo(a), " + cliente.obterPrimeiroNome() + ".");
        System.out.println();

        // FASE C - Menu Principal
        terminal.exibirMenuPrincipal();
    }
}
