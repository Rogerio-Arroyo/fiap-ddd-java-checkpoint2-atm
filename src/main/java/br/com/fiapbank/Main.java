package br.com.fiapbank;

import br.com.fiapbank.application.AutorizacaoService;
import br.com.fiapbank.application.ContaFactory;
import br.com.fiapbank.application.ContaService;
import br.com.fiapbank.infrastructure.ContaRepository;
import br.com.fiapbank.model.Cliente;
import br.com.fiapbank.model.Conta;
import br.com.fiapbank.model.ContaAcesso;
import br.com.fiapbank.model.Dinheiro;
import br.com.fiapbank.presentation.TerminalBancarioController;

/**
 * Nova funcionalidade para dar mias contexto as classes:
 *   [1] Cadastrar nova conta → salva no ContaRepository
 *   [2] Fazer login → busca no ContaRepository → autentica → abre menu bancário
 *   [3] Sair
 */
public class Main {

    public static void main(String[] args) {

        // Repositório em memória compartilhado por toda a sessão (infrastructure)
        ContaRepository repository = new ContaRepository();

        // Factory Singleton para criação de contas (application)
        ContaFactory factory = ContaFactory.getInstance();

        // Controller temporário para o menu inicial (presentation)
        TerminalBancarioController terminal = new TerminalBancarioController(null, null);

        Integer opcaoInicial = 0;

        // Loop principal: permanece até o usuário escolher Sair
        while (opcaoInicial != 3) {

            opcaoInicial = terminal.exibirMenuInicial();

            // -----------------------------------------------
            // OPÇÃO 1 - CADASTRAR NOVA CONTA
            // -----------------------------------------------
            if (opcaoInicial.equals(1)) {

                // Coleta dados via presentation
                String nomeCompleto = terminal.solicitarNomeCompleto();

                // Verifica se já existe conta com esse nome
                Conta contaExistente = repository.buscarPorNome(nomeCompleto);
                if (contaExistente != null) {
                    System.out.println();
                    System.out.println("Já existe uma conta cadastrada com o nome \"" + nomeCompleto + "\".");
                    System.out.println("Utilize a opção de login para acessar sua conta.");
                    continue;
                }

                String senha = terminal.solicitarCadastroSenha();
                Integer tipoConta = terminal.solicitarTipoConta();

                // Cria entidades no modelo (model)
                Cliente cliente = new Cliente(nomeCompleto);
                ContaAcesso contaAcesso = new ContaAcesso(senha);
                Dinheiro saldoInicial = new Dinheiro(0.0);

                // Cria conta via Factory (Singleton + Factory Method)
                Conta conta;
                if (tipoConta.equals(1)) {
                    conta = factory.criarContaCorrente(cliente, contaAcesso, saldoInicial);
                } else {
                    conta = factory.criarContaPoupanca(cliente, contaAcesso, saldoInicial);
                }

                // Persiste no repositório em memória (infrastructure)
                repository.salvar(conta);

                System.out.println();
                System.out.println("Conta criada com sucesso! Bem-vindo(a), " + cliente.obterPrimeiroNome() + ".");
                System.out.println("Tipo: " + (tipoConta.equals(1) ? "Conta Corrente" : "Conta Poupança"));
                System.out.println("Use o login para acessar sua conta.");
            }

            // -----------------------------------------------
            // OPÇÃO 2 - FAZER LOGIN
            // -----------------------------------------------
            else if (opcaoInicial.equals(2)) {

                // Solicita nome para busca no repositório
                String nomeLogin = terminal.solicitarLoginNome();

                Conta conta = repository.buscarPorNome(nomeLogin);

                if (conta == null) {
                    System.out.println();
                    System.out.println("Conta não encontrada para o nome \"" + nomeLogin + "\".");
                    System.out.println("Verifique o nome ou cadastre uma nova conta.");
                    continue;
                }

                // Cria serviços com a conta encontrada (application)
                ContaService contaService = new ContaService(conta);
                AutorizacaoService autorizacaoService = new AutorizacaoService(conta);

                // Cria controller com serviços configurados (presentation)
                TerminalBancarioController terminalLogado =
                        new TerminalBancarioController(contaService, autorizacaoService);

                // Autenticação
                Boolean autenticado = terminalLogado.realizarAutenticacao();

                if (!autenticado) {
                    continue;
                }

                System.out.println();
                System.out.println("Acesso autorizado! Bem-vindo(a), " + conta.getCliente().obterPrimeiroNome() + ".");

                // Abre menu bancário
                terminalLogado.exibirMenuPrincipal();
            }

            // -----------------------------------------------
            // OPÇÃO 3 - SAIR
            // -----------------------------------------------
            else if (opcaoInicial.equals(3)) {
                System.out.println();
                System.out.println("O FIAP Bank agradece sua preferência. Até logo!");
            }
        }
    }
}
