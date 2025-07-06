package model;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.ChronoUnit;

import clients.PessoaFisica;
import clients.PessoaJuridica;

public class ContaPoupanca extends Conta{

    //Atributos
    private final double TAXA_RENDIMENTO_MENSAL = 0.005; // 0.5%
    private int diaAniversarioRendimento;
    private int ultimoMesRendimentoAplicado;
    private int ultimoAnoRendimentoAplicado;

    // Construtor para Pessoa Física
    public ContaPoupanca(PessoaFisica titular, double saldoInicial){
        super(titular, saldoInicial);
        this.diaAniversarioRendimento = this.dataCriacao.getDayOfMonth();
        this.ultimoMesRendimentoAplicado = this.dataCriacao.getMonthValue();
        this.ultimoAnoRendimentoAplicado = this.dataCriacao.getYear();
        System.out.println("Tipo de Conta: Poupança\n");
    }

    // Construtor para Pessoa Jurídica
    public ContaPoupanca(PessoaJuridica titular, double saldoInicial){
        super(titular, saldoInicial);
        this.diaAniversarioRendimento = this.dataCriacao.getDayOfMonth();
        this.ultimoMesRendimentoAplicado = this.dataCriacao.getMonthValue();
        this.ultimoAnoRendimentoAplicado = this.dataCriacao.getYear(); // Correção: use this.dataCriacao.getYear()
        System.out.println("Tipo de Conta: Poupança\n");
    }

    // Método de Aplicação de Rendimento Mensal
    public void aplicarRendimento(){

        LocalDate dataAtualSimulada = LocalDate.now();
        int mesAtualSimulado = dataAtualSimulada.getMonthValue();
        int anoAtualSimulado = dataAtualSimulada.getYear();

        LocalDate dataParaAplicacaoEsteMes;
        // Determina o dia de aplicação para o mês atual, ajustando para o último dia do mês se o dia de aniversário for maior
        if (this.diaAniversarioRendimento > dataAtualSimulada.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth()) {
            dataParaAplicacaoEsteMes = dataAtualSimulada.with(TemporalAdjusters.lastDayOfMonth());
        } else {
            dataParaAplicacaoEsteMes = LocalDate.of(anoAtualSimulado, mesAtualSimulado, diaAniversarioRendimento);
        }

        // Verifica se a data atual já passou ou é igual à data de aplicação para este mês
        if (dataAtualSimulada.isAfter(dataParaAplicacaoEsteMes) || dataAtualSimulada.isEqual(dataParaAplicacaoEsteMes)) {
            // Verifica se a última aplicação foi em um mês/ano anterior
            if (this.ultimoAnoRendimentoAplicado < anoAtualSimulado ||
                    (this.ultimoAnoRendimentoAplicado == anoAtualSimulado && this.ultimoMesRendimentoAplicado < mesAtualSimulado)) {

                // Calcula a data da última aplicação "esperada" para o cálculo da diferença de meses
                LocalDate ultimaDataAplicacaoEfetiva = LocalDate.of(this.ultimoAnoRendimentoAplicado, this.ultimoMesRendimentoAplicado, this.diaAniversarioRendimento);
                if (this.diaAniversarioRendimento > ultimaDataAplicacaoEfetiva.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth()) {
                    ultimaDataAplicacaoEfetiva = ultimaDataAplicacaoEfetiva.with(TemporalAdjusters.lastDayOfMonth());
                }

                long quantidadeDeMesesParaRender = ChronoUnit.MONTHS.between(ultimaDataAplicacaoEfetiva, dataParaAplicacaoEsteMes);

                // Garante que haja pelo menos 1 mês para render
                if (quantidadeDeMesesParaRender >= 1) {
                    System.out.println("\n===== APLICAÇÃO DO RENDIMENTO MENSAL =====");
                    double saldoAntesDoRendimento = this.saldo;

                    // Aplica o rendimento para cada mês completo que passou
                    this.saldo *= Math.pow(1 + TAXA_RENDIMENTO_MENSAL, quantidadeDeMesesParaRender);

                    System.out.printf("Saldo Anterior: R$ %.2f\n", saldoAntesDoRendimento);
                    System.out.printf("Rendimento Aplicado (x%d): R$ %.2f\n", quantidadeDeMesesParaRender, this.saldo - saldoAntesDoRendimento);
                    System.out.printf("Novo Saldo: R$ %.2f\n", this.saldo);
                    this.ultimoMesRendimentoAplicado = mesAtualSimulado;
                    this.ultimoAnoRendimentoAplicado = anoAtualSimulado;
                }
            }
        }
    }

    // Sobrescrição do Método de Saque
    @Override
    public void sacar(double valor){
        aplicarRendimento(); // Aplica rendimento antes do saque
        super.sacar(valor);
    }

    // Sobrescrição do Método de Depósito
    @Override
    public void depositar(double valor){
        aplicarRendimento(); // Aplica rendimento antes do depósito
        super.depositar(valor);
    }

    // Definição do Método de Geração de Extrato
    @Override
    public void gerarExtrato(){
        System.out.println("\n===== EXTRATO CONTA POUPANÇA ===== \n");
        System.out.printf("Número da Conta: %s\n", this.numeroDaConta);
        System.out.printf("Agência: %s\n", this.agencia);
        System.out.printf("Banco: %s\n", this.codigoBanco);
        System.out.printf("Operação: %s\n", this.codigoOperacao);

        if(this.titular instanceof PessoaFisica){
            System.out.printf("Titular: %s (CPF %s)\n", ((PessoaFisica) this.titular).getNomePessoa(), ((PessoaFisica) this.titular).getCPF());
        }
        if(this.titular instanceof PessoaJuridica){
            System.out.printf("Razão Social: %s (CNPJ %s)\n", ((PessoaJuridica) this.titular).getRazaoSocial(), ((PessoaJuridica) this.titular).getCNPJ());
            System.out.printf("Responsável: %s\n", ((PessoaJuridica) this.titular).getNomePessoa());
        }

        System.out.printf("Saldo: R$ %.2f\n", this.saldo);
        System.out.println("Data de Criação: " + this.dataCriacao.format(getFormatter()));
        System.out.printf("Taxa de Rendimento Mensal: %.1f%%\n", TAXA_RENDIMENTO_MENSAL * 100);
    }

}