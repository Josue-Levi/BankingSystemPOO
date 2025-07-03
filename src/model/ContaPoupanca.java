package model;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.ChronoUnit;

import clients.PessoaFisica;
import clients.PessoaJuridica;

public class ContaPoupanca extends Conta{

    //Atributos
    private final double TAXA_RENDIMENTO_MENSAL = 0.005;
    private int diaAniversarioRendimento; 
    private int ultimoMesRendimentoAplicado;  
    private int ultimoAnoRendimentoAplicado; 

    //Construtor para Pessoa Física
    public ContaPoupanca(PessoaFisica titular, double saldoInicial){
        super(titular, saldoInicial);
        this.diaAniversarioRendimento = this.dataCriacao.getDayOfMonth(); 
        this.ultimoMesRendimentoAplicado = this.dataCriacao.getMonthValue();
        this.ultimoAnoRendimentoAplicado = this.dataCriacao.getYear();
        System.out.println("Tipo de Conta: Poupança\n");
    }

    public ContaPoupanca(PessoaJuridica titular, double saldoInicial){
        super(titular, saldoInicial);
        this.diaAniversarioRendimento = this.dataCriacao.getDayOfMonth();
        this.ultimoMesRendimentoAplicado = this.dataCriacao.getMonthValue();
        this.dataCriacao.getYear();
        System.out.println("Tipo de Conta: Poupança\n");
    }

    //Método de Aplicação de Rendimento Mensal
    public void aplicarRendimento(){

        LocalDate dataAtualSimulada = LocalDate.now();
        int mesAtualSimulado = dataAtualSimulada.getMonthValue();
        int anoAtualSimulado = dataAtualSimulada.getYear();
        LocalDate ultimoDiaDoMes = dataAtualSimulada.with(TemporalAdjusters.lastDayOfMonth());
        LocalDate dataParaAplicacaoEsteMes;
        
        /**
         * A lógica exerce uma verificação inical que avalia se o mês ou o ano passou para dar continuidade ao código. Depois, avalia o dia de dataAtualSimulada e compara com a data de aniversário da conta, avaliando dois casos:
         * 1. Conta criada em um dia inexistente no mês (por exemplo, 30 de fevereiro)
         * 2. Conta criada em um dia existente no mês
         */
        
        //Verifica se o ano passou ou o mês passou
        if(this.ultimoAnoRendimentoAplicado < anoAtualSimulado || (anoAtualSimulado == this.ultimoAnoRendimentoAplicado && 
        this.ultimoMesRendimentoAplicado < mesAtualSimulado)){
            //Cria a data de aplicação para esse mês
            if(this.dataCriacao.getDayOfMonth() > ultimoDiaDoMes.getDayOfMonth()){
                dataParaAplicacaoEsteMes = dataAtualSimulada.with(TemporalAdjusters.lastDayOfMonth());
            } else {
                dataParaAplicacaoEsteMes = LocalDate.of(anoAtualSimulado, mesAtualSimulado, diaAniversarioRendimento);
            }
            //Registra a última data de aplicação do rendimento
            LocalDate ultimaDataDeAplicacaoPrevista = LocalDate.of(this.ultimoAnoRendimentoAplicado, this.ultimoMesRendimentoAplicado, this.diaAniversarioRendimento);
            if (this.diaAniversarioRendimento > ultimaDataDeAplicacaoPrevista.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth()) {
                ultimaDataDeAplicacaoPrevista = ultimaDataDeAplicacaoPrevista.with(TemporalAdjusters.lastDayOfMonth());
            }
            //Calcula a diferença de meses entre a data da última aplicação e a data de aplicação para esse mês
            long quantidadeDeMeses = ChronoUnit.MONTHS.between(ultimaDataDeAplicacaoPrevista, dataParaAplicacaoEsteMes);
            if(quantidadeDeMeses == 1){
                if(dataAtualSimulada.isAfter(dataParaAplicacaoEsteMes) || dataAtualSimulada.isEqual(dataParaAplicacaoEsteMes)){
                    System.out.println("===== APLICAÇÃO DO RENDIMENTO MENSAL =====");
                    double saldoAntesDoRendimento = this.saldo;
                    this.saldo *= 1 + TAXA_RENDIMENTO_MENSAL;
                    System.out.printf("Saldo Anterior: R$ %.2f\n", saldoAntesDoRendimento);
                    System.out.printf("Rendimento Aplicado: R$ %.2f\n", this.saldo - saldoAntesDoRendimento);
                    System.out.printf("Novo Saldo: R$ %.2f", this.saldo);
                    this.ultimoMesRendimentoAplicado = mesAtualSimulado;
                    this.ultimoAnoRendimentoAplicado = anoAtualSimulado;
                }  
            } else if (quantidadeDeMeses >= 2){
                double saldoAntesDoRendimento = this.saldo;
                this.saldo *= Math.pow(1 + TAXA_RENDIMENTO_MENSAL, quantidadeDeMeses - 1);
                if(dataAtualSimulada.isAfter(dataParaAplicacaoEsteMes) || dataAtualSimulada.isEqual(dataParaAplicacaoEsteMes)){
                    System.out.println("===== APLICAÇÃO DO RENDIMENTO MENSAL =====");
                    this.saldo *= 1 + TAXA_RENDIMENTO_MENSAL;
                    this.ultimoMesRendimentoAplicado = mesAtualSimulado;
                    this.ultimoAnoRendimentoAplicado = anoAtualSimulado;
                }
                System.out.printf("Saldo Anterior: R$ %.2f\n", saldoAntesDoRendimento);
                System.out.printf("Rendimento Aplicado: R$ %.2f\n", this.saldo - saldoAntesDoRendimento);
                System.out.printf("Novo Saldo: R$ %.2f", this.saldo); 
            }
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
