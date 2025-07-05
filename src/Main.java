import clients.CadastroPessoaFisica;
import clients.CadastroPessoaJuridica;
import clients.PessoaFisica;
import clients.PessoaJuridica;
import model.Conta;
import model.ContaCorrente;
import model.ContaPoupanca;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    private static List<PessoaFisica> clientesPF = new ArrayList<>();
    private static List<PessoaJuridica> clientesPJ = new ArrayList<>();
    private static List<Conta> contas = new ArrayList<>();
    public static void main(String[] args){
        int opcaoPrincipal;
        do {
            exibirMenuPrincipal();
            opcaoPrincipal = escolherOpcao();
            switch (opcaoPrincipal) {
                case 1:
                    criarConta();
                    break;
                case 2:
                    entrarConta();
                    break;
                case 0:
                    System.out.println("Saindo do simulador... Adeus.");
                    break;
                default:
                    System.out.println("Opção inválida. Digite uma das opções listadas.");
                    break;
            }
        } while(opcaoPrincipal != 0);
        scanner.close();
    }

public static void exibirMenuPrincipal() {
    System.out.println("\n===== MENU PRINCIPAL =====");
    System.out.println("[1] Abrir Nova Conta");
    System.out.println("[2] Entrar em Conta Existente");
    System.out.println("[0] Sair");
    System.out.print("Escolha uma opção: ");
}

public static void exibirTiposDeConta(){
    System.out.println("\n===== TIPOS DE CONTA =====");
    System.out.println("[1] Conta Corrente");
    System.out.println("[2] Conta Poupança");
}

public static void exibirTipoDeCliente(){
    System.out.println("\n===== TIPOS DE CLIENTE =====");
    System.out.println("[1] Pessoa Física");
    System.out.println("[2] Pessoa Jurídica");
}

public static int escolherOpcao(){
    try {
        int opcao = scanner.nextInt();
        scanner.nextLine();
        return opcao;
    } catch (InputMismatchException exception){
        System.out.println("Entrada inválida. Por favor, digite um número.\n");
        scanner.nextLine();
        return -1;
    }
}

public static void criarConta(){
    PessoaFisica pessoaFisica = null;
    PessoaJuridica pessoaJuridica = null;
    Object titularConta = null;

    exibirTipoDeCliente();
    int tipoCliente = escolherOpcao();

    switch (tipoCliente) {
        case 1:
            pessoaFisica = CadastroPessoaFisica.CadastrarPessoaFisica(scanner);
            if (pessoaFisica != null){
                clientesPF.add(pessoaFisica);
                titularConta = pessoaFisica;
            }
            break;
        case 2:
            pessoaJuridica = CadastroPessoaJuridica.CadastrarPessoaJuridica(scanner);
            if (pessoaJuridica != null){
                clientesPJ.add(pessoaJuridica);
                titularConta = pessoaJuridica;
            }
            break;
        default:
            System.out.println("Opção inválida. Digite uma das opções listadas.\n");
            break;
    }

    if (titularConta != null){
        exibirTiposDeConta();
        int tipoConta = escolherOpcao();

        Conta novaConta = null;
        
        switch (tipoConta) {
            case 1:
                System.out.println("Digite o limite do cheque especial: R$ ");
                double limiteChequeEspecial = scanner.nextDouble();
                scanner.nextLine();
                if (titularConta instanceof PessoaFisica){
                    novaConta = new ContaCorrente((PessoaFisica) titularConta , 0.0, limiteChequeEspecial);
                } else if (titularConta instanceof PessoaJuridica){
                    novaConta = new ContaCorrente((PessoaJuridica) titularConta, 0.0, limiteChequeEspecial);
                }
                break;
            case 2:
                if (titularConta instanceof PessoaFisica) {
                    novaConta = new ContaPoupanca((PessoaFisica) titularConta, 0.0);
                } else if (titularConta instanceof PessoaJuridica) {
                    novaConta = new ContaPoupanca((PessoaJuridica) titularConta, 0.0);
                }
                break;
            default:
                System.out.println("Opção inválida. Digite uma das opções listadas.\n");
                return;
        }

        if (novaConta != null){
            contas.add(novaConta);
            novaConta.gerarExtrato();
        }
    } else {
        System.out.println("Não é possível criar a conta sem um cliente válido.\n");
    }
}

public static void entrarConta(){
    System.out.println("\n===== ENTRAR EM CONTA EXISTENTE =====");
        System.out.println("===== LOGIN CONTA =====");
        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        Object clienteLogado = verificarLogin(email, senha);

        if(clienteLogado != null) {
            System.out.println("Login sucedido!");

            List<Conta> contasDoCliente = new ArrayList<>();
            for (Conta conta : contas) {
                // Compara se o titular da conta é o mesmo objeto do cliente logado
                if (conta.getTitular().equals(clienteLogado)) {
                    contasDoCliente.add(conta);
                }
            }

            if (contasDoCliente.isEmpty()) {
                System.out.println("Este cliente não possui contas cadastradas em memória.");
                return;
            }
            
            System.out.println("\nSuas contas:");
            for (int i = 0; i < contasDoCliente.size(); i++) {
                Conta c = contasDoCliente.get(i);
                System.out.printf("%dº Conta: %s (Saldo: R$ %.2f)\n", (i + 1), c.getNumeroDaConta(), c.getSaldo());
            }

            System.out.print("Selecione o número da conta que deseja acessar: ");
            int escolhaConta = escolherOpcao();

            if (escolhaConta > 0 && escolhaConta <= contasDoCliente.size()) {
                Conta contaSelecionada = contasDoCliente.get(escolhaConta - 1);
                menuOperacoes(contaSelecionada);
            } else {
                System.out.println("Seleção de conta inválida.");
            }

            
        } else {
            System.out.println("Email ou senha inválidos.");
        }
}

public static Object verificarLogin(String email, String senha) {
    Gson gson = new Gson();
    Type listTypeFisica = new TypeToken<List<PessoaFisica>>() {}.getType();
    Type listTypeJuridica = new TypeToken<List<PessoaJuridica>>() {}.getType();

    try {
        // Verifica pessoa física
        FileReader readerFisica = new FileReader("src/clients/CadastrosFisica.json");
        List<PessoaFisica> pessoasFisicas = gson.fromJson(readerFisica, listTypeFisica);
        readerFisica.close();

        for (PessoaFisica pf : pessoasFisicas) {
            if (pf.getEmail().equals(email) && pf.getSenha().equals(senha)) {
                System.out.println("Usuário pessoa física logado.");
                return pf;
            }
        }

        // Verifica pessoa jurídica
        FileReader readerJuridica = new FileReader("src/clients/CadastrosJuridico.json");
         List<PessoaJuridica> pessoasJuridicas = gson.fromJson(readerJuridica, listTypeJuridica);
        readerJuridica.close();

        for (PessoaJuridica pj : pessoasJuridicas) {
            if (pj.getEmail().equals(email) && pj.getSenha().equals(senha)) {
                System.out.println("Usuário pessoa jurídica logado.");
                return pj;
            }
        }

    } catch (Exception e) {
        System.out.println("Erro ao ler arquivos JSON: " + e.getMessage());
    }
    return false;
}

public static void menuOperacoes (Conta conta){
    int opcaoOperacao;
    do {
        System.out.println("===== OPERAÇÕES DA CONTA =====");
        System.out.println("Número da conta: " + conta.getNumeroDaConta());
        System.out.println("[1] Depositar");
        System.out.println("[2] Sacar");
        System.out.println("[3] Gerar Extrato");
        System.out.println("[0] Voltar ao Menu Principal");
        opcaoOperacao = escolherOpcao();

        switch (opcaoOperacao) {
            case 1:
                System.out.print("Digite o valor do depósito: R$ ");
                double valorDeposito = scanner.nextDouble();
                scanner.nextLine();
                conta.depositar(valorDeposito);
                break;
            case 2:
                System.out.print("Digite o valor do saque: R$ ");
                double valorSaque = scanner.nextDouble();
                scanner.nextLine();
                conta.sacar(valorSaque);
                break;
            case 3:
                conta.gerarExtrato();
                break;
            case 0:
                System.out.println("Voltando ao Menu Principal...");
                break;
            default:
                System.out.println("Opção inválida. Digite uma das opções listadas.");
                break;
        }
    } while (opcaoOperacao != 0);
}
}