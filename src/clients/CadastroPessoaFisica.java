package clients;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

import validation.GeradorCC;
import model.ContaCorrente;
import model.ContaPoupanca;

public class CadastroPessoaFisica {
    //metodo
    public static void CadastrarPessoaFisica(){

        Scanner scanner = new Scanner(System.in);
        System.out.println("===== CADASTRO DE PESSOA FÍSICA =====");
        // entrada dos dados pessoais
        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Data de nascimento (dd/mm/aaaa): ");
        String nascimento = scanner.nextLine();

        System.out.print("CPF: ");
        String cpf = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        // criação do objeto com dados pessoais
        PessoaFisica pessoa = new PessoaFisica(nome, nascimento, cpf, email, senha, telefone);

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

        System.out.println("\n===== TIPOS DE CONTA =====");
        System.out.println("[1] Conta Corrente");
        System.out.println("[2] Conta Poupança");
        int opcao = scanner.nextInt();
        switch (opcao){
            case 1:
                ContaCorrente cc = new ContaCorrente(pessoa, (double) 0.0, (double) 500.0);
                pessoa.setCC(cc);
                break;
            case 2:
                ContaPoupanca cp = new ContaPoupanca(pessoa, 0.0);
                pessoa.setCP(cp);
        }
        // gerar conta 
        GeradorCC conta1 = new GeradorCC();
        System.out.println(conta1);

        pessoa.setConta(conta1);

        //Salva em .Json
        try {
            File file = new File("src/clients/CadastrosFisica.json");
            List<PessoaFisica> pessoas = new ArrayList<>();

            if (file.exists() && file.length() > 0) {
                FileReader reader = new FileReader(file);
                Type listType = new TypeToken<List<PessoaFisica>>() {}.getType();
                pessoas = new Gson().fromJson(reader, listType);
                reader.close();
            }

            pessoas.add(pessoa);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            FileWriter writer = new FileWriter(file);
            gson.toJson(pessoas, writer);
            writer.close();

            System.out.println("Dados salvos com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados: " + e.getMessage());
        }


        scanner.close();
    }
}

