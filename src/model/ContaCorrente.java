package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ContaCorrente extends Conta {

    private final double TAXA_MANUTENCAO_MENSAL = 10.00;
    private final double TAXA_POR_TRANSACAO_EXTRA = 2.50;
    private final int TRANSACOES_GRATUITAS_MES = 20;

    private double limiteChequeEspecial;
    private int contadorTransacoesMes; 
    private int ultimoMesCobrancaTaxaManutencao;
    private int ultimoAnoCobrancaTaxaManutencao;

    public ContaCorrente(String numeroDaConta, String titular, double saldo, double limiteChequeEspecial){
        super(numeroDaConta, titular, saldo);
        this.limiteChequeEspecial = limiteChequeEspecial;
        this.contadorTransacoesMes = 0;
        this.ultimoMesCobrancaTaxaManutencao = LocalDate.now().getMonthValue();
        this.ultimoAnoCobrancaTaxaManutencao = LocalDate.now().getYear();
    }

    public double getLimiteChequeEspecial() {
        return limiteChequeEspecial;
    }

    public void setLimiteChequeEspecial(double limiteChequeEspecial) {
        this.limiteChequeEspecial = limiteChequeEspecial;
    }

    public void cobrarTaxaManutencaoMensal(){
        LocalDate hoje = LocalDate.now();
        int mesAtual = hoje.getMonthValue();
        int anoAtual = hoje.getYear();

        System.out.println("===== COBRANÇA MENSAL DA CONTA CORRENTE =====");
        if(this.ultimoAnoCobrancaTaxaManutencao < anoAtual || (anoAtual == this.ultimoAnoCobrancaTaxaManutencao && this.ultimoMesCobrancaTaxaManutencao < mesAtual)){
            this.saldo -= TAXA_MANUTENCAO_MENSAL;
            System.out.printf("Taxa de Cobrança Mensal de R$ %.2f", TAXA_MANUTENCAO_MENSAL);
            this.contadorTransacoesMes = 0;
            this.ultimoMesCobrancaTaxaManutencao = mesAtual; 
            this.ultimoAnoCobrancaTaxaManutencao = anoAtual;
        }
    }

    @Override
    public void sacar(double valor){
        cobrarTaxaManutencaoMensal();
        if(valor > 0 && (this.limiteChequeEspecial + this.saldo) >= valor){
            LocalDateTime agora = LocalDateTime.now();
            String dataHoraFormatada = agora.format(Conta.FORMATTER);
            this.saldo -= valor;
            System.out.printf("Saque de R$ %.2f realizado. Novo saldo: R$ %.2f.\n", valor, this.saldo);
            System.out.printf("Data e hora do saque: %s.\n", dataHoraFormatada);
            if(TRANSACOES_GRATUITAS_MES < this.contadorTransacoesMes){
                this.saldo -= TAXA_POR_TRANSACAO_EXTRA;
            }
            this.contadorTransacoesMes += 1;
        }
        else {
            System.out.println("Saldo insuficiente ou valor inválido!");
        }
    }

    @Override
    public void gerarExtrato(){
        System.out.println("\nExtrato: ");
        System.out.printf("Número da Conta: %s\n", this.numeroDaConta);
        System.out.printf("Titular: %s\n", this.titular);
        System.out.printf("Saldo: R$ %.2f\n", this.saldo);
        System.out.printf("Limite de Cheque Especial: R$ %.2f\n", this.limiteChequeEspecial);
    }


}