package model;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class ContaPoupanca extends Conta{

    //Atributos
    private final double TAXA_RENDIMENTO_MENSAL = 0.005;
    private int diaAniversarioRendimento; 
    private int ultimoMesRendimentoAplicado;  
    private int ultimoAnoRendimentoAplicado; 

    //Construtores
    public ContaPoupanca(String numeroDaConta, String titular, double saldo){
        super(numeroDaConta, titular, saldo);
        this.diaAniversarioRendimento = this.dataCriacao.getDayOfMonth(); 
        this.ultimoMesRendimentoAplicado = this.dataCriacao.getMonthValue();
        this.ultimoAnoRendimentoAplicado = this.dataCriacao.getYear(); 
    }

    //Método de Aplicação de Rendimento Mensal
    public void aplicarRendimento(){

        LocalDate hoje = LocalDate.now();
        int mesAtual = hoje.getMonthValue();
        int anoAtual = hoje.getYear();
        LocalDate ultimoDiaDoMes = hoje.with(TemporalAdjusters.lastDayOfMonth());
        LocalDate dataParaAplicacaoEsteMes;
        
        /**
         * A lógica exerce uma verificação inical que avalia se o mês ou o ano passou para dar continuidade ao código. Depois, avalia o dia de hoje e compara com a data de aniversário da conta, avaliando dois casos:
         * 1. Conta criada em um dia inexistente no mês (por exemplo, 30 de fevereiro)
         * 2. Conta criada em um dia existente no mês
         */
        
        if(this.ultimoAnoRendimentoAplicado < anoAtual || (anoAtual == this.ultimoAnoRendimentoAplicado && 
        this.ultimoMesRendimentoAplicado < mesAtual)){
            System.out.println("===== APLICAÇÃO DO RENDIMENTO MENSAL =====");
            if(this.dataCriacao.getDayOfMonth() > ultimoDiaDoMes.getDayOfMonth()){
                dataParaAplicacaoEsteMes = hoje.with(TemporalAdjusters.lastDayOfMonth());
            } else {
                dataParaAplicacaoEsteMes = LocalDate.of(anoAtual, mesAtual, diaAniversarioRendimento);
            }
            if(hoje.isAfter(dataParaAplicacaoEsteMes) || hoje.isEqual(dataParaAplicacaoEsteMes)){
                double rendimentoMensal = saldo * TAXA_RENDIMENTO_MENSAL;
                this.saldo += rendimentoMensal;
                System.out.printf("Rendimento Mensal: R$ %.2f\n", rendimentoMensal);
                System.out.printf("Novo Saldo: R$ %.2f\n", this.saldo);
            }
            this.ultimoMesRendimentoAplicado = mesAtual;
            this.ultimoAnoRendimentoAplicado = anoAtual;
        }
    }
    
    //Sobrescrição do Método de Saque
    @Override
    public void sacar(double valor){
        aplicarRendimento();
        super.sacar(valor);
    }

    //Sobrescrição do Método de Depósito
    @Override
    public void depositar(double valor){
        aplicarRendimento();
        super.depositar(valor);
    }

    //Definição do Método de Geração de Extrato
    @Override
    public void gerarExtrato(){
        System.out.println("\n===== EXTRATO ===== \n");
        System.out.printf("Número da Conta: %s\n", this.numeroDaConta);
        System.out.printf("Titular: %s\n", this.titular);
        System.out.printf("Saldo: R$ %.2f\n", this.saldo);
        System.out.println("Data de Criação: " + this.dataCriacao.format(getFormatter()));
        System.out.printf("Taxa de Rendimento Mensal: %.1f%%\n", TAXA_RENDIMENTO_MENSAL * 100);
    }

}