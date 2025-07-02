package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

public abstract class Conta {
    
    //Formatador de Data
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    
    public static DateTimeFormatter getFormatter() {
    return FORMATTER;
    }

    //Atributos
    protected String numeroDaConta;
    protected String titular;
    protected double saldo;
    protected LocalDate dataCriacao;

    //Construtores
    public Conta(String numeroDaConta, String titular, double saldo){
        this.numeroDaConta = numeroDaConta;
        this.titular = titular;
        this.saldo = saldo;
        this.dataCriacao = LocalDate.now();
    }

    //Getters
    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public String getNumeroDaConta(){
        return numeroDaConta;
    }

    public String getTitular(){
        return titular;
    }

    public double getSaldo(){
        return saldo;
    }

    //Setters
    public void setNumeroDaConta(String numeroDaConta){
        this.numeroDaConta = numeroDaConta;
    }

    public void setTitular(String titular){
        this.titular = titular;
    }

    public void setSaldo(double saldo){
        this.saldo = saldo;
    }

    //Método de Depósito
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

    //Método de Saque
    public void sacar(double valor){
        if(valor > 0 && this.saldo >= valor){
            LocalDateTime agora = LocalDateTime.now();
            String dataHoraFormatada = agora.format(FORMATTER);
            this.saldo -= valor;
            System.out.printf("Saque de R$ %.2f realizado. Novo saldo: R$ %.2f.\n", valor, this.saldo);
            System.out.printf("Data e hora do saque: %s.\n", dataHoraFormatada);
        } else {
            System.out.println("Valor de saque inválido!\n");
        }
    }

    //Método Abstrato de Extrato
    public abstract void gerarExtrato();

}