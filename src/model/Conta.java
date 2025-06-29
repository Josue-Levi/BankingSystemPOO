package model;

import java.time.LocalDateTime;

public abstract class Conta {
    
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    //Atributos
    protected int numeroDaConta;
    protected String titular;
    protected double saldo;

    public Conta(int numeroDaConta, String titular, double saldo){
        this.numeroDaConta = numeroDaConta;
        this.titular = titular;
        this.saldo = saldo;
    }

    public int getNumeroDaConta(){
        return numeroDaConta;
    }

    public String getTitular(){
        return titular;
    }

    public double getSaldo(){
        return saldo;
    }

    public int setNumeroDaConta(int numeroDaConta){
        this.numeroDaConta = numeroDaConta;
    }

    public String setTitular(String titular){
        this.titular = titular;
    }

    public double setSaldo(double saldo){
        this.saldo = saldo;
    }

    //Métodos
    public void depositar(double valor){
        if(valor > 0){
            String dataHoraFormatada = agora.format(formatter);
            this.saldo += valor;
            System.out.printf("Depósito de R$ %.2f realizado. Novo saldo: R$ %.2f.\n", valor, this.saldo);
            System.out.println("Data e hora do depósito: %s.", dataHoraFormatada);
        } else {
            System.out.println("Valor de depósito inválido!");
        }
    }

    public void sacar(double valor){
        if(valor > 0){
            String dataHoraFormatada = agora.format(formatter);
            this.saldo -= valor;
            System.out.println("Saque de R$ %.2f realizado. Novo saldo: R$ %.2f.\n", valor, this.saldo);
            System.out.println("Data e hora do saque: %s.", dataHoraFormatada);
        } else {
            System.out.println("Valor de saque inválido!");
        }
    }

    public void gerarExtrato(){
        
    }
}