package clients;

import java.util.Scanner;

// Esta classe serve como um ponto de entrada para testar o cadastro de Pessoa Jurídica isoladamente.
// No programa principal (Main.java), você já chama CadastroPessoaJuridica.CadastrarPessoaJuridica.
public class PessoaJuridicaMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Chama o método de cadastro completo da classe CadastroPessoaJuridica
        // que já lida com todas as entradas, validações e salvamento em JSON.
        PessoaJuridica novaPessoaJuridica = CadastroPessoaJuridica.CadastrarPessoaJuridica(scanner);

        if (novaPessoaJuridica != null) {
            System.out.println("\n--- Cadastro de Pessoa Jurídica concluído através de PessoaJuridicaMain ---");
            novaPessoaJuridica.ExibirDadosPessoaJuridica();
        } else {
            System.out.println("\n--- Cadastro de Pessoa Jurídica não concluído ---");
        }

        scanner.close();
    }
}