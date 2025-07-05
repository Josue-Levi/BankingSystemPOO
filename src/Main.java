import clients.CadastroPessoaFisica;
import clients.CadastroPessoaJuridica;
import clients.PessoaFisica;
import clients.PessoaJuridica;
import model.Conta;
import model.ContaPoupanca;
import model.EntraConta;

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
        int opcao;
        do {
            exibirMenuPrincipal();
            opcao = scanner.nextInt();
            scanner.nextLine();
            switch (opcao) {
                case 1:
                    criarConta();
                    break;
                case 2:
                    EntraConta.EntrarConta();
                    break;
                case 0:
                    System.out.println("Saindo da simulação... Adeus.\n");
                    break;
                default:
                    System.out.println("Opção inválida. Digite uma das opções listadas.\n");
                    break;
            }
        } while(opcao != 0);
        
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
    exibirTipoDeCliente();
    int tipoDeCliente = scanner.nextInt();
    scanner.nextLine();
    switch (tipoDeCliente) {
        case 1:
            CadastroPessoaFisica.CadastrarPessoaFisica();
            exibirTiposDeConta();
            break;
        case 2:
            CadastroPessoaJuridica.CadastrarPessoaJuridica();
            exibirTiposDeConta();
            break;
        default:
            System.out.println("Opção inválida. Digite uma das opções listadas.\n");
            break;
    }
}

}