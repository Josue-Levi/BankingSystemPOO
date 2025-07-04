package clients;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import validation.GeradorCC;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class CadastroPessoaJuridica {
    public static void CadastrarPessoaJuridica() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("===== CADASTRO DE PESSOA JURIDICA =====");
        //entrada dos dados pessoais
        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Data de Nascimento (dd/mm/ano): ");
        String nascimento = scanner.nextLine();

        System.out.print("CPF: ");
        String cpf = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        // entrada dos dados da empresa
        System.out.println("===== CADASTRO DE INFORMAÇÕES DA EMPRESA =====");
        System.out.print("Razão Social: ");
        String razaosocial = scanner.nextLine();
        System.out.print("CNPJ: ");
        String CNPJ = scanner.nextLine();

        // criação do objeto com dados pessoais
        PessoaJuridica pessoa = new PessoaJuridica(nome, nascimento, cpf, email, senha, telefone, CNPJ, razaosocial);

        // entrada dos dados de endereço
        System.out.println("\n===== CADASTRO DE LOCALIZAÇÃO DA EMPRESA =====");

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

        // criação do objeto com dados da localização da empresa
        pessoa.setEndereco(estado, cidade, bairro, rua, numero, complemento, cep);

        // exibir dados cadastrado pela pessoa juridica
        pessoa.ExibirDadosPessoaJuridica();

        //gerar conta 
        GeradorCC conta1 = new GeradorCC();
        System.out.println(conta1);

        //Salvar em Json
        try {
            File file = new File("src/clients/CadastrosJuridico.json");
            List<PessoaFisica> pessoas = new ArrayList<>();

            if (file.exists() && file.length() > 0) {
                FileReader reader = new FileReader(file);
                Type listType = new TypeToken<List<PessoaJuridica>>() {}.getType();
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