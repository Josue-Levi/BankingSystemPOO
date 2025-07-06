package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import clients.PessoaFisica;
import clients.PessoaJuridica;
import validation.Gerador;

public abstract class Conta {

    //Formatador de Data
    // Tornar TRANSIENT para Gson não tentar serializar o formatador diretamente
    private transient static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static DateTimeFormatter getFormatter() {
        return FORMATTER;
    }

    //Atributos
    protected String numeroDaConta;
    protected String agencia;
    protected String codigoBanco;
    protected String codigoOperacao;
    protected Object titular; // Cuidado com Object para Gson. Funciona com RuntimeTypeAdapterFactory
    protected double saldo;
    protected LocalDateTime dataCriacao;

    // Construtores
    public Conta(Object titular, double saldoInicial) {
        this.titular = titular;
        this.saldo = saldoInicial;
        this.dataCriacao = LocalDateTime.now();


        Gerador gerador = new Gerador();
        this.numeroDaConta = gerador.getNumeroConta();
        this.agencia = gerador.getAgencia();
        this.codigoBanco = gerador.getCodigoBanco();
        this.codigoOperacao = gerador.getCodigoOperar();

        System.out.println("\n===== CONTA CRIADA COM SUCESSO! =====");
        if (titular instanceof PessoaFisica) {
            System.out.println("Titular: " + ((PessoaFisica) titular).getNomePessoa());
        } else if (titular instanceof PessoaJuridica) {
            System.out.println("Titular (Responsável): " + ((PessoaJuridica) titular).getNomePessoa());
            System.out.println("Razão Social: " + ((PessoaJuridica) titular).getRazaoSocial());
        }
        System.out.println("Número da Conta: " + this.numeroDaConta);
        System.out.println("Agência: " + this.agencia);
        System.out.println("Banco: " + this.codigoBanco);
        System.out.println("Operação: " + this.codigoOperacao);
        System.out.printf("Saldo Inicial: R$ %.2f\n", this.saldo);
        System.out.println("---------------------------------");
    }

    // Getters
    public Object getTitular(){
        return titular;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public String getNumeroDaConta(){
        return numeroDaConta;
    }

    public double getSaldo(){
        return saldo;
    }

    public String getAgencia() {
        return agencia;
    }

    public String getCodigoBanco() {
        return codigoBanco;
    }

    public String getCodigoOperacao() {
        return codigoOperacao;
    }

    // Setters (considerar se todos são realmente necessários)
    // Para Gson, setters são úteis, mas alguns atributos (como número da conta)
    // normalmente não seriam alterados após a criação.
    public void setNumeroDaConta(String numeroDaConta){
        this.numeroDaConta = numeroDaConta;
    }

    public void setTitular(Object titular){ // Mudado para Object para consistência
        this.titular = titular;
    }

    public void setSaldo(double saldo){
        this.saldo = saldo;
    }

    // Método de Depósito
    public void depositar(double valor){
        if(valor > 0){
            LocalDateTime agora = LocalDateTime.now();
            String dataHoraFormatada = agora.format(FORMATTER);
            this.saldo += valor;
            System.out.printf("Depósito de R$ %.2f realizado. Novo saldo: R$ %.2f.\n", valor, this.saldo);
            System.out.printf("Data e hora do depósito: %s.\n", dataHoraFormatada);
        } else {
            System.out.println("Valor de depósito inválido!\n");
        }
    }

    // Método de Saque
    public void sacar(double valor){
        if(valor > 0 && this.saldo >= valor){
            LocalDateTime agora = LocalDateTime.now();
            String dataHoraFormatada = agora.format(FORMATTER);
            this.saldo -= valor;
            System.out.printf("Saque de R$ %.2f realizado. Novo saldo: R$ %.2f.\n", valor, this.saldo);
            System.out.printf("Data e hora do saque: %s.\n", dataHoraFormatada);
        } else {
            System.out.println("Valor de saque inválido ou saldo insuficiente!\n"); // Adicionado saldo insuficiente
        }
    }

    // Método Abstrato de Extrato
    public abstract void gerarExtrato();

}