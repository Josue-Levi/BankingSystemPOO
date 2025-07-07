package model;

import clients.PessoaFisica;
import clients.PessoaJuridica;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

public class ContaCorrente extends Conta {

    // atributos 
    private final double TAXA_MANUTENCAO_MENSAL = 10.00;
    private final double TAXA_POR_TRANSACAO_EXTRA = 2.50;
    private final int TRANSACOES_GRATUITAS_MES = 20;
    private double limiteChequeEspecial;
    private int contadorTransacoesMes = 0;
    // variaveis para controlar a cobrança da taxa de manutenção
    private int ultimoMesCobrancaTaxaManutencao;
    private int ultimoAnoCobrancaTaxaManutencao;
    private int diaAniversarioCobranca;

    // construtor
    public ContaCorrente(PessoaFisica titular, double saldoInicial, double limiteChequeEspecial) {
        super(titular, saldoInicial);
        this.limiteChequeEspecial = limiteChequeEspecial;
        this.diaAniversarioCobranca = this.dataCriacao.getDayOfMonth();
        this.ultimoMesCobrancaTaxaManutencao = this.dataCriacao.getMonthValue();
        this.ultimoAnoCobrancaTaxaManutencao = this.dataCriacao.getYear();
        System.out.println("Tipo de Conta: Corrente");
        System.out.printf("Limite de Cheque Especial: R$ %.2f\n", this.limiteChequeEspecial);
    }
    
    // getters para acessar aos atributos
    public double getLimiteChequeEspecial() {
        return limiteChequeEspecial;
    }

    public void setLimiteChequeEspecial(double limiteChequeEspecial) {
        this.limiteChequeEspecial = limiteChequeEspecial;
    }
    

    // sobrescrição do metodo de gerar extrato
    @Override
    public void gerarExtrato() {
        System.out.println("\n===== EXTRATO CONTA CORRENTE ===== \n");
        System.out.printf("Número da Conta: %s\n", this.numeroDaConta);
        System.out.printf("Agência: %s\n", this.agencia);
        System.out.printf("Banco: %s\n", this.codigoBanco);
        System.out.printf("Operação: %s\n", this.codigoOperacao);

        if (this.titular instanceof PessoaJuridica) {
            PessoaJuridica pj = (PessoaJuridica) this.titular;
            System.out.printf("Razão Social: %s (CNPJ %s)\n", pj.getRazaoSocial(), pj.getCNPJ());
            System.out.printf("Responsável: %s\n", pj.getNomePessoa());
        } else if (this.titular instanceof PessoaFisica) {
            System.out.printf("Titular: %s (CPF %s)\n", this.titular.getNomePessoa(), this.titular.getCPF());
        }

        System.out.printf("Saldo: R$ %.2f\n", this.saldo);
        System.out.printf("Limite de Cheque Especial: R$ %.2f\n", this.limiteChequeEspecial);
        System.out.println("Data de Criação: " + this.dataCriacao.format(getFormatter()));
        System.out.printf("Número de Transações Efetuadas neste mês: %d\n", this.contadorTransacoesMes);
        if (TRANSACOES_GRATUITAS_MES > this.contadorTransacoesMes) {
            System.out.printf("Número de Transações Gratuitas restantes neste mês: %d\n", TRANSACOES_GRATUITAS_MES - this.contadorTransacoesMes);
        } else {
            System.out.println("Número de Transações Gratuitas restantes neste mês: 0\n");
        }
    }
}
