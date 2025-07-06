package clients;

import java.util.Scanner;

// Esta classe serve como um ponto de entrada para testar o cadastro de Pessoa Física isoladamente.
// No programa principal (Main.java), você já chama CadastroPessoaFisica.CadastrarPessoaFisica.
public class PessoaFisicaMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Chama o método de cadastro completo da classe CadastroPessoaFisica
        // que já lida com todas as entradas, validações e salvamento em JSON.
        PessoaFisica novaPessoaFisica = CadastroPessoaFisica.CadastrarPessoaFisica(scanner);

        if (novaPessoaFisica != null) {
            System.out.println("\n--- Cadastro de Pessoa Física concluído através de PessoaFisicaMain ---");
            novaPessoaFisica.ExibirDadosPessoaFisica();
        } else {
            System.out.println("\n--- Cadastro de Pessoa Física não concluído ---");
        }

        scanner.close();
    }
}