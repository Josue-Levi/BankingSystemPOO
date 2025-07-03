package clients;
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

        scanner.close();
    }
}