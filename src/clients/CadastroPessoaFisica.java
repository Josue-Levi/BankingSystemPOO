package clients;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import validation.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class CadastroPessoaFisica {
    //metodo
    public static PessoaFisica CadastrarPessoaFisica(Scanner scanner){

        System.out.println("===== CADASTRO DE PESSOA FÍSICA =====");
        // entrada dos dados pessoais
        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Data de nascimento (dd/mm/aaaa): ");
        String nascimento = scanner.nextLine();

        String CPF;
        boolean CPFvalido = false;
        do {
            System.out.println("CPF (apenas números): ");
            CPF = scanner.nextLine();
            if (ValidadorCPF.validarCPF(CPF) == true){
                CPFvalido = true;
            } else {
                CPFvalido = false;
            }
        } while (!CPFvalido);

        String email;
        boolean emailvalido = false;
        do{
            System.out.println("Email:");
            email = scanner.nextLine();
            if(ValidadorEmail.ValidarEmail(email) == true){
                emailvalido = true;
            } else {
                System.out.println("Email Invalido!");
                emailvalido = false;
            }
        } while (!emailvalido);

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        // criação do objeto com dados pessoais
        PessoaFisica pessoa = new PessoaFisica(nome, nascimento, CPF, email, senha, telefone);

        // entrada dos dados de endereço
        System.out.println("\n===== CADASTRO DE ENDEREÇO =====");

        System.out.print("Estado: ");
        String estado = scanner.nextLine();

        System.out.print("Cidade: ");
        String cidade = scanner.nextLine();

        System.out.print("Bairro: ");
        String bairro = scanner.nextLine();

        System.out.print("Rua: ");
        String rua = scanner.nextLine();

        System.out.print("Número: ");
        int numero = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Complemento: ");
        String complemento = scanner.nextLine();

        System.out.print("CEP: ");
        String cep = scanner.nextLine();

        // criação do objeto com dados da residencia
        pessoa.setEndereco(estado, cidade, bairro, rua, numero, complemento, cep);

        // exibir dados inseridos pela pessoa fisica
        pessoa.ExibirDadosPessoaFisica();

        //Salva em .Json

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(pessoa);

        try {
            FileWriter writer = new FileWriter("src/clients/CadastrosFisica.json", true);
            writer.write(json + ",\n");
            writer.close();
            System.out.println("Dados salvos com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados no arquivo JSON: " + e.getMessage());
            e.printStackTrace();
        }
        return pessoa;
    }
}

