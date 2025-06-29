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

        if(this.ultimoAnoCobrancaTaxaManutencao < anoAtual || (anoAtual == this.ultimoAnoCobrancaTaxaManutencao && this.ultimoMesCobrancaTaxaManutencao < mesAtual)){
            System.out.println("\n===== COBRANÇA MENSAL DA CONTA CORRENTE =====");
            this.saldo -= TAXA_MANUTENCAO_MENSAL;
            System.out.printf("Taxa de manutenção mensal de R$ %.2f cobrada. Novo saldo: R$ %.2f.\n", TAXA_MANUTENCAO_MENSAL, this.saldo);
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
            String dataHoraFormatada = agora.format(getFormatter());
            this.saldo -= valor;
            System.out.printf("Saque de R$ %.2f realizado. Novo saldo: R$ %.2f.\n", valor, this.saldo);
            System.out.printf("Data e hora do saque: %s.\n", dataHoraFormatada);
            this.contadorTransacoesMes += 1;
            if(TRANSACOES_GRATUITAS_MES < this.contadorTransacoesMes){
                this.saldo -= TAXA_POR_TRANSACAO_EXTRA;
            }
        }
        else {
            System.out.println("Saldo insuficiente ou valor inválido!\n");
        }
    }

    @Override
    public void gerarExtrato(){
        System.out.println("\nExtrato: \n");
        System.out.printf("Número da Conta: %s\n", this.numeroDaConta);
        System.out.printf("Titular: %s\n", this.titular);
        System.out.printf("Saldo: R$ %.2f\n", this.saldo);
        System.out.printf("Limite de Cheque Especial: R$ %.2f\n", this.limiteChequeEspecial);
        System.out.println("Data de Criação: " + this.dataCriacao.format(getFormatter()));
        System.out.printf("Número de Transações Efetuadas: %d\n", this.contadorTransacoesMes);
        if(TRANSACOES_GRATUITAS_MES > this.contadorTransacoesMes){
            System.out.printf("Número de Transações Gratuítas restantes: %d\n", TRANSACOES_GRATUITAS_MES - this.contadorTransacoesMes);
        } else {
            System.out.println("Número de Transações Gratuítas restantes: 0\n");
        }
    }
}