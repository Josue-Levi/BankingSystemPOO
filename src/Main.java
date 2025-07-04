import java.util.InputMismatchException;
import java.util.Scanner;

import clients.CadastroPessoaFisica;
import clients.CadastroPessoaJuridica;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
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

public static void exibirTipoDePessoa(){
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
    exibirTiposDeConta();
    switch (escolherOpcao()){
        case 1:
            exibirTipoDePessoa();
            switch(escolherOpcao()){
                case 1:
                    CadastroPessoaFisica.CadastrarPessoaFisica();
                    break;
                case 2:
                    CadastroPessoaJuridica.CadastrarPessoaJuridica();
                    break;
                default:
                    System.out.println("Opção inválida. Digite uma das opções listadas.\n");
                    break;
            }
            break;
        case 2:
            exibirTipoDePessoa();
            switch(escolherOpcao()){
                case 1:
                    CadastroPessoaFisica.CadastrarPessoaFisica();
                    break;
                case 2:
                    CadastroPessoaJuridica.CadastrarPessoaJuridica();
                    break;
                default:
                    System.out.println("Opção inválida. Digite uma das opções listadas\n");
            }
            break;
        default:
            System.out.println("Opção inválida. Digite uma das opções listadas\n");
    }
}


}






