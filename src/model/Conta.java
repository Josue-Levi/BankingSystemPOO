package model;

import clients.PessoaFisica;
import clients.PessoaJuridica;
import validation.Gerador;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Conta {

    private transient static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static DateTimeFormatter getFormatter() {
        return FORMATTER;
    }

    protected PessoaFisica titular;

    protected String numeroDaConta;
    protected String agencia;
    protected String codigoBanco;
    protected String codigoOperacao;
    protected double saldo;
    protected LocalDateTime dataCriacao;

    public Conta(PessoaFisica titular, double saldoInicial) {
        this.titular = titular;
        this.saldo = saldoInicial;
        this.dataCriacao = LocalDateTime.now();


        Gerador gerador = new Gerador();
        this.numeroDaConta = gerador.getNumeroConta();
        this.agencia = gerador.getAgencia();
        this.codigoBanco = gerador.getCodigoBanco();
        this.codigoOperacao = gerador.getCodigoOperar();

        System.out.println("\n===== CONTA CRIADA COM SUCESSO! =====");
        if (titular instanceof PessoaJuridica) {
            System.out.println("Titular (Responsável): " + ((PessoaJuridica) titular).getNomePessoa());
            System.out.println("Razão Social: " + ((PessoaJuridica) titular).getRazaoSocial());
        } else if (titular instanceof PessoaFisica) {
            System.out.println("Titular: " + titular.getNomePessoa());
        }
        System.out.println("Número da Conta: " + this.numeroDaConta);
        System.out.println("Agência: " + this.agencia);
        System.out.println("Banco: " + this.codigoBanco);
        System.out.println("Operação: " + this.codigoOperacao);
        System.out.printf("Saldo Inicial: R$ %.2f\n", this.saldo);
        System.out.println("---------------------------------");
    }

    // Getters
    public PessoaFisica getTitular() {
        return titular;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public String getNumeroDaConta() {
        return numeroDaConta;
    }

    public double getSaldo() {
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

    // Setters
    public void setTitular(PessoaFisica titular) {
        this.titular = titular;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void depositar(double valor) {
        if (valor > 0) {
            LocalDateTime agora = LocalDateTime.now();
            String dataHoraFormatada = agora.format(FORMATTER);
            this.saldo += valor;
            System.out.printf("Depósito de R$ %.2f realizado. Novo saldo: R$ %.2f.\n", valor, this.saldo);
            System.out.printf("Data e hora do depósito: %s.\n", dataHoraFormatada);
        } else {
            System.out.println("Valor de depósito inválido!\n");
        }
    }

    public void sacar(double valor) {
        if (valor > 0 && this.saldo >= valor) {
            LocalDateTime agora = LocalDateTime.now();
            String dataHoraFormatada = agora.format(FORMATTER);
            this.saldo -= valor;
            System.out.printf("Saque de R$ %.2f realizado. Novo saldo: R$ %.2f.\n", valor, this.saldo);
            System.out.printf("Data e hora do saque: %s.\n", dataHoraFormatada);
        } else {
            System.out.println("Valor de saque inválido ou saldo insuficiente!\n");
        }
    }

    public abstract void gerarExtrato();
}
