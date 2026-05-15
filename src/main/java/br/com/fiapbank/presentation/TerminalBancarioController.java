package br.com.fiapbank.presentation;

import br.com.fiapbank.application.AutorizacaoService;
import br.com.fiapbank.application.ContaService;
import br.com.fiapbank.model.Dinheiro;
import br.com.fiapbank.model.Movimentacao;

import java.util.List;
import java.util.Scanner;

/**
 * Controlador da camada de apresentação (Presentation Layer - DDD).
 * Responsável por toda a interação com o usuário via terminal.
 * Gerencia o fluxo de cadastro, login e operações bancárias.
 */
public class TerminalBancarioController {

    private ContaService contaService;
    private AutorizacaoService autorizacaoService;
    private static Scanner leitor = new Scanner(System.in);

    public TerminalBancarioController(ContaService contaService, AutorizacaoService autorizacaoService) {
        this.contaService = contaService;
        this.autorizacaoService = autorizacaoService;
    }

    // =========================================================
    // MENU INICIAL
    // =========================================================

    public Integer exibirMenuInicial() {
        System.out.println();
        System.out.println("========================================");
        System.out.println("      BEM-VINDO AO FIAP BANK - ATM");
        System.out.println("========================================");
        System.out.println("  [ 1 ] Cadastrar nova conta");
        System.out.println("  [ 2 ] Fazer login");
        System.out.println("  [ 3 ] Sair");
        System.out.println("========================================");
        System.out.print("Escolha uma opção: ");

        Integer opcao = 0;
        while (opcao != 1 && opcao != 2 && opcao != 3) {
            String entrada = leitor.nextLine().trim();
            try {
                opcao = Integer.parseInt(entrada);
                if (opcao != 1 && opcao != 2 && opcao != 3) {
                    System.out.println("Opção inválida! Digite 1, 2 ou 3.");
                    System.out.print("Escolha uma opção: ");
                }
            } catch (Exception e) {
                System.out.println("Opção inválida! Digite 1, 2 ou 3.");
                System.out.print("Escolha uma opção: ");
            }
        }
        return opcao;
    }

    // =========================================================
    // CADASTRO
    // =========================================================

    public String solicitarNomeCompleto() {
        System.out.println();
        System.out.println("--- Cadastro de Nova Conta ---");
        String nome = "";
        while (nome.isEmpty()) {
            System.out.print("Digite seu nome completo: ");
            nome = leitor.nextLine().trim();
            if (nome.isEmpty() || !nome.matches("[a-zA-ZÀ-ú ]+")) {
                System.out.println("Nome inválido! Digite apenas letras.");
                nome = "";
            } else if (!nome.contains(" ")) {
                System.out.println("Digite o nome completo (nome e sobrenome).");
                nome = "";
            }
        }
        return nome;
    }

    public String solicitarCadastroSenha() {
        System.out.println();
        System.out.println("--- Cadastro de Senha ---");
        System.out.println("Sua senha deve conter:");
        System.out.println("  - No mínimo 8 caracteres");
        System.out.println("  - Ao menos uma letra maiúscula");
        System.out.println("  - Ao menos uma letra minúscula");
        System.out.println("  - Ao menos um número");
        System.out.println("  - Ao menos um caracter especial (!@#$%^&*()-_+=?><)");

        String regexSenhaForte = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()\\-_+=?><])(?=.*\\d).{8,}$";
        String senha = "";
        Boolean senhaValida = false;

        while (!senhaValida) {
            System.out.print("Digite sua senha: ");
            senha = leitor.nextLine();

            if (senha.matches(regexSenhaForte)) {
                senhaValida = true;
            } else {
                System.out.println("Senha fraca! Tente novamente seguindo os critérios acima.");
                System.out.println();
            }
        }

        System.out.println("Senha cadastrada com sucesso!");
        System.out.println();
        return senha;
    }

    public Integer solicitarTipoConta() {
        System.out.println();
        System.out.println("--- Tipo de Conta ---");
        System.out.println("  [ 1 ] Conta Corrente (taxa de R$ 25,00 por saque)");
        System.out.println("  [ 2 ] Conta Poupança  (rendimento de 1% após cada saque)");

        Integer tipo = 0;
        while (tipo != 1 && tipo != 2) {
            System.out.print("Escolha o tipo de conta: ");
            String entrada = leitor.nextLine().trim();
            try {
                tipo = Integer.parseInt(entrada);
                if (tipo != 1 && tipo != 2) {
                    System.out.println("Opção inválida! Digite 1 ou 2.");
                }
            } catch (Exception e) {
                System.out.println("Opção inválida! Digite 1 ou 2.");
            }
        }
        return tipo;
    }

    // =========================================================
    // LOGIN
    // =========================================================

    public String solicitarLoginNome() {
        System.out.println();
        System.out.println("--- Login ---");
        System.out.print("Digite seu nome completo: ");
        return leitor.nextLine().trim();
    }

    public Boolean realizarAutenticacao() {
        System.out.println();
        System.out.println("--- Autenticação ---");

        while (!autorizacaoService.isBloqueada()) {
            System.out.print("Digite sua senha: ");
            String senhaDigitada = leitor.nextLine();

            if (autorizacaoService.autorizar(senhaDigitada)) {
                return true;
            }

            if (autorizacaoService.isBloqueada()) {
                System.out.println();
                System.out.println("ACESSO BLOQUEADO: conta bloqueada após 3 tentativas incorretas.");
                return false;
            }

            System.out.println("Senha incorreta! Tentativas restantes: " + autorizacaoService.getTentativasRestantes());
        }

        System.out.println("ACESSO BLOQUEADO: conta bloqueada após 3 tentativas incorretas.");
        return false;
    }

    // =========================================================
    // MENU BANCÁRIO
    // =========================================================

    public void exibirMenuPrincipal() {
        Integer opcao = 0;

        while (opcao != 5) {
            System.out.println();
            System.out.println("========================================");
            System.out.println("          MENU PRINCIPAL");
            System.out.println("========================================");
            System.out.println("  [ 1 ] Consultar Saldo");
            System.out.println("  [ 2 ] Fazer Depósito");
            System.out.println("  [ 3 ] Fazer Saque");
            System.out.println("  [ 4 ] Histórico de Movimentações");
            System.out.println("  [ 5 ] Sair (voltar ao menu inicial)");
            System.out.println("========================================");
            System.out.print("Escolha uma opção: ");

            String entrada = leitor.nextLine().trim();

            try {
                opcao = Integer.parseInt(entrada);
            } catch (Exception e) {
                System.out.println("Opção inválida! Digite um número de 1 a 5.");
                continue;
            }

            switch (opcao) {
                case 1:
                    exibirSaldo();
                    break;
                case 2:
                    realizarDeposito();
                    break;
                case 3:
                    realizarSaque();
                    break;
                case 4:
                    exibirMovimentacoes();
                    break;
                case 5:
                    System.out.println("Sessão encerrada. Até logo!");
                    break;
                default:
                    System.out.println("Opção inválida! Digite um número de 1 a 5.");
                    break;
            }
        }
    }

    // =========================================================
    // OPERAÇÕES BANCÁRIAS
    // =========================================================

    public void exibirSaldo() {
        Dinheiro saldo = contaService.obterSaldo();
        System.out.println("Seu saldo atual é: " + saldo);
    }

    public void exibirMovimentacoes() {
        List<Movimentacao> movimentacoes = contaService.obterMovimentacoes();

        System.out.println("========================================");
        System.out.println("    HISTÓRICO DE MOVIMENTAÇÕES");
        System.out.println("========================================");

        if (movimentacoes.isEmpty()) {
            System.out.println("  Nenhuma movimentação registrada.");
        } else {
            for (Integer i = 0; i < movimentacoes.size(); i++) {
                Movimentacao mov = movimentacoes.get(i);
                System.out.printf("  %s | %-12s | %s%n",
                        mov.getDataHoraFormatada(),
                        mov.getTipo(),
                        mov.getValor());
            }
        }

        System.out.println("========================================");
    }

    public void realizarSaque() {
        System.out.print("Digite o valor do saque: R$ ");
        String entrada = leitor.nextLine().trim();

        Double valor;
        try {
            valor = Double.parseDouble(entrada);
        } catch (Exception e) {
            System.out.println("Valor inválido!");
            return;
        }

        try {
            Dinheiro dinheiro = new Dinheiro(valor);
            contaService.realizarSaque(dinheiro);
            System.out.println("Saque de " + dinheiro + " realizado com sucesso!");
            System.out.println("Saldo atualizado: " + contaService.obterSaldo());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    public void realizarDeposito() {
        System.out.print("Digite o valor do depósito: R$ ");
        String entrada = leitor.nextLine().trim();

        Double valor;
        try {
            valor = Double.parseDouble(entrada);
        } catch (Exception e) {
            System.out.println("Valor inválido!");
            return;
        }

        try {
            Dinheiro dinheiro = new Dinheiro(valor);
            contaService.realizarDeposito(dinheiro);
            System.out.println("Depósito de " + dinheiro + " realizado com sucesso!");
            System.out.println("Saldo atualizado: " + contaService.obterSaldo());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
