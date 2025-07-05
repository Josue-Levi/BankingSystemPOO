import clients.CadastroPessoaFisica;
import clients.CadastroPessoaJuridica;
import clients.PessoaFisica;
import clients.PessoaJuridica;
import model.Conta;
import model.ContaPoupanca;

import java.util.ArrayList;
import java.util.List;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    private static List<PessoaFisica> clientesPF = new ArrayList<>();
    private static List<PessoaJuridica> clientesPJ = new ArrayList<>();
    private static List<Conta> contas = new ArrayList<>();
    public static void main(String[] args){
        do {
            exibirMenuPrincipal();
            switch (escolherOpcao()) {
                case 1:
                    criarConta();
                    break;
                case 2:

                    break;
                default:
                    System.out.println("Opção inválida. Digite uma das opções listadas.\n");
                    break;
            }
        } while(escolherOpcao() != 0);
        
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
            CadastroPessoaFisica.CadastrarPessoaFisica();
            if (pessoaFisica != null){
                clientesPF.add(pessoaFisica);
                titularConta = pessoaFisica;
            }
            break;
        case 2:
            CadastroPessoaJuridica.CadastrarPessoaJuridica();
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

        System.out.println("Digite o saldo inicial da conta: R$ ");
        double saldoInicial = scanner.nextDouble();
        scanner.nextLine();

        Conta novaConta = null;

        switch (tipoConta) {
            case 1:
                System.out.println("Digite o limite do cheque especial: R$ ");
                double limiteChequeEspecial = scanner.nextDouble();
                scanner.nextLine();
                if (titularConta instanceof PessoaFisica){
                    novaConta = new ContaPoupanca((PessoaFisica) titularConta , saldoInicial);
                } else if (titularConta instanceof PessoaJuridica){
                    novaConta = new ContaPoupanca((PessoaJuridica) titularConta, saldoInicial);
                }
                break;
            default:
                System.out.println("Opção inválida. Digite uma das opções listadas.\n");
                break;
        }
    }
}

public static void entrarConta(){
    
}

}