import clients.CadastroPessoaFisica;
import clients.CadastroPessoaJuridica;
import clients.PessoaFisica;
import clients.PessoaJuridica;
import model.Conta;
import model.ContaCorrente;
import model.ContaPoupanca;
import util.*;
import java.time.LocalDateTime;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.*;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    // Caminhos dos arquivos JSON para persistência
    private static final String FILE_PF = "src/clients/CadastrosFisica.json";
    private static final String FILE_PJ = "src/clients/CadastrosJuridico.json";
    private static final String FILE_CONTAS = "src/data/contas.json";

    // Listas em memória
    private static List<PessoaFisica> clientesPF = new ArrayList<>();
    private static List<PessoaJuridica> clientesPJ = new ArrayList<>();
    private static List<Conta> contas = new ArrayList<>();

    // Configuração do Gson para lidar com herança na classe Conta
    private static Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
        .registerTypeAdapterFactory(
            RuntimeTypeAdapterFactory.of(Conta.class, "tipoConta")
                .registerSubtype(ContaCorrente.class, "ContaCorrente")
                .registerSubtype(ContaPoupanca.class, "ContaPoupanca")
        )
        .create();

    public static void main(String[] args){
        // Garante que os diretórios existam
        new File("src/clients").mkdirs();
        new File("src/data").mkdirs();

        carregarDados(); // Carrega os dados no início do programa

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
                    salvarDados(); // Salva os dados antes de sair
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
            scanner.nextLine(); // Consumir a nova linha
            return opcao;
        } catch (InputMismatchException exception){
            System.out.println("Entrada inválida. Por favor, digite um número.\n");
            scanner.nextLine(); // Consumir a entrada inválida para evitar loop
            return -1; // Retorna um valor inválido para continuar o loop
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
                    if(!clientesPF.contains(pessoaFisica)){
                        clientesPF.add(pessoaFisica);
                    }
                    titularConta = pessoaFisica;
                }
                break;
            case 2:
                pessoaJuridica = CadastroPessoaJuridica.CadastrarPessoaJuridica(scanner);
                if (pessoaJuridica != null){
                    if(!clientesPJ.contains(pessoaJuridica)){
                        clientesPJ.add(pessoaJuridica);
                    }
                    titularConta = pessoaJuridica;
                }
                break;
            default:
                System.out.println("Opção inválida para tipo de cliente. Operação cancelada.\n");
                return;
        }

        if (titularConta != null){
            exibirTiposDeConta();
            int tipoConta = escolherOpcao();

            Conta novaConta = null;

            switch (tipoConta) {
                case 1:
                    double limiteChequeEspecial = 0.0;
                    boolean limiteValido = false;
                    while(!limiteValido) {
                        System.out.print("Digite o limite do cheque especial: R$ ");
                        try {
                            limiteChequeEspecial = scanner.nextDouble();
                            scanner.nextLine();
                            if (limiteChequeEspecial < 0) {
                                System.out.println("O limite do cheque especial não pode ser negativo.");
                            } else {
                                limiteValido = true;
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Valor inválido. Por favor, digite um número.");
                            scanner.nextLine();
                        }
                    }

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
                    System.out.println("Opção inválida para tipo de conta. Operação cancelada.\n");
                    return;
            }

            if (novaConta != null){
                contas.add(novaConta);
                novaConta.gerarExtrato();
                salvarDados();
            }
        } else {
            System.out.println("Não foi possível criar a conta. Cliente inválido.\n");
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
            System.out.println("Login bem-sucedido!");

            List<Conta> contasDoCliente = new ArrayList<>();
            for (Conta conta : contas) {
                if (conta.getTitular().equals(clienteLogado)) {
                    contasDoCliente.add(conta);
                }
            }

            if (contasDoCliente.isEmpty()) {
                System.out.println("Este cliente não possui contas cadastradas ou vinculadas no sistema.");
                return;
            }

            System.out.println("\nSuas contas:");
            for (int i = 0; i < contasDoCliente.size(); i++) {
                Conta c = contasDoCliente.get(i);
                String tipo = (c instanceof ContaCorrente) ? "Corrente" : "Poupança";
                System.out.printf("[%d] Conta %s: %s (Saldo: R$ %.2f)\n", (i + 1), tipo, c.getNumeroDaConta(), c.getSaldo());
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
            System.out.println("Email ou senha inválidos. Tente novamente.");
        }
    }

    public static Object verificarLogin(String email, String senha) {
        File fileFisica = new File(FILE_PF);
        if (fileFisica.exists() && fileFisica.length() > 0) {
            try (FileReader readerFisica = new FileReader(FILE_PF)) {
                List<PessoaFisica> pessoasFisicas = gson.fromJson(readerFisica, new TypeToken<List<PessoaFisica>>() {}.getType());
                if (pessoasFisicas != null) {
                    for (PessoaFisica pf : pessoasFisicas) {
                        if (pf.getEmail().equals(email) && pf.getSenha().equals(senha)) {
                            if (!clientesPF.contains(pf)) {
                                clientesPF.add(pf);
                            }
                            return pf;
                        }
                    }
                }
            } catch (IOException | JsonSyntaxException e) {
                System.err.println("Erro ao carregar cadastros de Pessoa Física para login: " + e.getMessage());
            }
        }

        File fileJuridica = new File(FILE_PJ);
        if (fileJuridica.exists() && fileJuridica.length() > 0) {
            try (FileReader readerJuridica = new FileReader(FILE_PJ)) {
                List<PessoaJuridica> pessoasJuridicas = gson.fromJson(readerJuridica, new TypeToken<List<PessoaJuridica>>() {}.getType());
                if (pessoasJuridicas != null) {
                    for (PessoaJuridica pj : pessoasJuridicas) {
                        if (pj.getEmail().equals(email) && pj.getSenha().equals(senha)) {
                            if (!clientesPJ.contains(pj)) {
                                clientesPJ.add(pj);
                            }
                            return pj;
                        }
                    }
                }
            } catch (IOException | JsonSyntaxException e) {
                System.err.println("Erro ao carregar cadastros de Pessoa Jurídica para login: " + e.getMessage());
            }
        }
        return null;
    }

    public static void menuOperacoes (Conta conta){
        int opcaoOperacao;
        do {
            System.out.println("\n===== OPERAÇÕES DA CONTA " + conta.getNumeroDaConta() + " =====");
            System.out.printf("Saldo Atual: R$ %.2f\n", conta.getSaldo());
            System.out.println("[1] Depositar");
            System.out.println("[2] Sacar");
            System.out.println("[3] Gerar Extrato");
            System.out.println("[0] Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            opcaoOperacao = escolherOpcao();

            switch (opcaoOperacao) {
                case 1:
                    double valorDeposito = 0.0;
                    boolean depositoValido = false;
                    while (!depositoValido) {
                        System.out.print("Digite o valor do depósito: R$ ");
                        try {
                            valorDeposito = scanner.nextDouble();
                            scanner.nextLine();
                            if (valorDeposito <= 0) {
                                System.out.println("O valor do depósito deve ser positivo.");
                            } else {
                                depositoValido = true;
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Valor inválido. Por favor, digite um número.");
                            scanner.nextLine();
                        }
                    }
                    conta.depositar(valorDeposito);
                    salvarDados();
                    break;
                case 2:
                    double valorSaque = 0.0;
                    boolean saqueValido = false;
                    while (!saqueValido) {
                        System.out.print("Digite o valor do saque: R$ ");
                        try {
                            valorSaque = scanner.nextDouble();
                            scanner.nextLine();
                            if (valorSaque <= 0) {
                                System.out.println("O valor do saque deve ser positivo.");
                            } else {
                                saqueValido = true;
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Valor inválido. Por favor, digite um número.");
                            scanner.nextLine();
                        }
                    }
                    conta.sacar(valorSaque);
                    salvarDados();
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

    private static void carregarDados() {
        System.out.println("Carregando dados existentes...");
        File filePf = new File(FILE_PF);
        if (filePf.exists() && filePf.length() > 0) {
            try (FileReader reader = new FileReader(FILE_PF)) {
                clientesPF = gson.fromJson(reader, new TypeToken<List<PessoaFisica>>() {}.getType());
                if (clientesPF == null) clientesPF = new ArrayList<>();
            } catch (IOException | JsonSyntaxException e) {
                System.err.println("Erro ao carregar clientes PF: " + e.getMessage());
                clientesPF = new ArrayList<>();
            }
        } else {
            System.out.println("Arquivo de clientes PF não encontrado ou vazio.");
        }

        File filePj = new File(FILE_PJ);
        if (filePj.exists() && filePj.length() > 0) {
            try (FileReader reader = new FileReader(FILE_PJ)) {
                clientesPJ = gson.fromJson(reader, new TypeToken<List<PessoaJuridica>>() {}.getType());
                if (clientesPJ == null) clientesPJ = new ArrayList<>();
            } catch (IOException | JsonSyntaxException e) {
                System.err.println("Erro ao carregar clientes PJ: " + e.getMessage());
                clientesPJ = new ArrayList<>();
            }
        } else {
            System.out.println("Arquivo de clientes PJ não encontrado ou vazio.");
        }

        File fileContas = new File(FILE_CONTAS);
        if (fileContas.exists() && fileContas.length() > 0) {
            try (FileReader reader = new FileReader(FILE_CONTAS)) {
                contas = gson.fromJson(reader, new TypeToken<List<Conta>>() {}.getType());
                if (contas == null) contas = new ArrayList<>();

                for (Conta conta : contas) {
                    if (conta.getTitular() instanceof PessoaFisica) {
                        PessoaFisica titularCarregado = (PessoaFisica) conta.getTitular();
                        for (PessoaFisica pf : clientesPF) {
                            if (pf.equals(titularCarregado)) {
                                conta.setTitular(pf);
                                break;
                            }
                        }
                    } else if (conta.getTitular() instanceof PessoaJuridica) {
                        PessoaJuridica titularCarregado = (PessoaJuridica) conta.getTitular();
                        for (PessoaJuridica pj : clientesPJ) {
                            if (pj.equals(titularCarregado)) {
                                conta.setTitular(pj);
                                break;
                            }
                        }
                    }
                }

            } catch (IOException | JsonSyntaxException e) {
                System.err.println("Erro ao carregar contas: " + e.getMessage());
                contas = new ArrayList<>();
            }
        } else {
            System.out.println("Arquivo de contas não encontrado ou vazio.");
        }
        System.out.println("Dados carregados com sucesso (ou arquivos criados/ignorados se vazios/corrompidos).");
    }

    private static void salvarDados() {
        System.out.println("Salvando dados...");
        try (FileWriter writer = new FileWriter(FILE_PF)) {
            gson.toJson(clientesPF, writer);
        } catch (IOException e) {
            System.err.println("Erro ao salvar clientes PF: " + e.getMessage());
        }

        try (FileWriter writer = new FileWriter(FILE_PJ)) {
            gson.toJson(clientesPJ, writer);
        } catch (IOException e) {
            System.err.println("Erro ao salvar clientes PJ: " + e.getMessage());
        }

        try (FileWriter writer = new FileWriter(FILE_CONTAS)) {
            gson.toJson(contas, writer);
        } catch (IOException e) {
            System.err.println("Erro ao salvar contas: " + e.getMessage());
        }
        System.out.println("Dados salvos.");
    }
}