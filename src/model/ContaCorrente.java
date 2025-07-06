package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

import clients.PessoaFisica;
import clients.PessoaJuridica;

public class ContaCorrente extends Conta {

    //Atributos
    private final double TAXA_MANUTENCAO_MENSAL = 10.00;
    private final double TAXA_POR_TRANSACAO_EXTRA = 2.50;
    private final int TRANSACOES_GRATUITAS_MES = 20;

    private double limiteChequeEspecial;
    private int contadorTransacoesMes = 0;
    private int ultimoMesCobrancaTaxaManutencao;
    private int ultimoAnoCobrancaTaxaManutencao;
    private int diaAniversarioCobranca;

    // Construtor para Pessoa Física
    public ContaCorrente(PessoaFisica titular, double saldoInicial, double limiteChequeEspecial){
        super(titular, saldoInicial);
        this.limiteChequeEspecial = limiteChequeEspecial;
        // Se a conta for criada no dia 31, e o próximo mês tem menos dias (ex: Fev),
        // a cobrança será no último dia do mês.
        this.diaAniversarioCobranca = this.dataCriacao.getDayOfMonth();
        this.ultimoMesCobrancaTaxaManutencao = this.dataCriacao.getMonthValue();
        this.ultimoAnoCobrancaTaxaManutencao = this.dataCriacao.getYear();
        System.out.println("Tipo de Conta: Corrente\n");
        System.out.printf("Limite de Cheque Especial: R$ %.2f\n", this.limiteChequeEspecial);
    }

    // Construtor para Pessoa Jurídica
    public ContaCorrente(PessoaJuridica titular, double saldoInicial, double limiteChequeEspecial){
        super(titular, saldoInicial);
        this.limiteChequeEspecial = limiteChequeEspecial;
        this.diaAniversarioCobranca = this.dataCriacao.getDayOfMonth();
        this.ultimoMesCobrancaTaxaManutencao = this.dataCriacao.getMonthValue();
        this.ultimoAnoCobrancaTaxaManutencao = this.dataCriacao.getYear();
        System.out.println("Tipo de Conta: Corrente\n");
        System.out.printf("Limite de Cheque Especial: R$ %.2f\n", this.limiteChequeEspecial);
    }

    // Getters
    public double getLimiteChequeEspecial() {
        return limiteChequeEspecial;
    }

    // Setters
    public void setLimiteChequeEspecial(double limiteChequeEspecial) {
        this.limiteChequeEspecial = limiteChequeEspecial;
    }

    // Método de Cobrança de Taxa de Manutenção Mensal
    public void cobrarTaxaManutencaoMensal(){
        LocalDate dataAtualSimulada = LocalDate.now();
        int mesAtualSimulado = dataAtualSimulada.getMonthValue();
        int anoAtualSimulado = dataAtualSimulada.getYear();

        LocalDate dataCobrancaEsteMes;
        // Determina o dia de cobrança para o mês atual, ajustando para o último dia do mês se o dia de aniversário for maior
        if (this.diaAniversarioCobranca > dataAtualSimulada.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth()) {
            dataCobrancaEsteMes = dataAtualSimulada.with(TemporalAdjusters.lastDayOfMonth());
        } else {
            dataCobrancaEsteMes = LocalDate.of(anoAtualSimulado, mesAtualSimulado, diaAniversarioCobranca);
        }

        // Verifica se a data atual já passou ou é igual à data de cobrança para este mês
        if (dataAtualSimulada.isAfter(dataCobrancaEsteMes) || dataAtualSimulada.isEqual(dataCobrancaEsteMes)) {
            // Verifica se a última cobrança foi em um mês/ano anterior
            if (this.ultimoAnoCobrancaTaxaManutencao < anoAtualSimulado ||
                    (this.ultimoAnoCobrancaTaxaManutencao == anoAtualSimulado && this.ultimoMesCobrancaTaxaManutencao < mesAtualSimulado)) {

                // Calcula a data da última cobrança "esperada" para o cálculo da diferença de meses
                LocalDate ultimaDataCobrancaEfetiva = LocalDate.of(this.ultimoAnoCobrancaTaxaManutencao, this.ultimoMesCobrancaTaxaManutencao, this.diaAniversarioCobranca);
                if (this.diaAniversarioCobranca > ultimaDataCobrancaEfetiva.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth()) {
                    ultimaDataCobrancaEfetiva = ultimaDataCobrancaEfetiva.with(TemporalAdjusters.lastDayOfMonth());
                }

                long quantidadeDeMesesParaCobrar = ChronoUnit.MONTHS.between(ultimaDataCobrancaEfetiva, dataCobrancaEsteMes);

                // Garante que haja pelo menos 1 mês a ser cobrado
                if (quantidadeDeMesesParaCobrar >= 1) {
                    System.out.println("\n===== COBRANÇA DA TAXA DE MANUTENÇÃO MENSAL =====");
                    double saldoAntesDaCobranca = this.saldo;
                    // Cobra para todos os meses que se passaram desde a última cobrança
                    this.saldo -= TAXA_MANUTENCAO_MENSAL * quantidadeDeMesesParaCobrar;
                    System.out.printf("Saldo Anterior: R$ %.2f\n", saldoAntesDaCobranca);
                    System.out.printf("Taxa de Manutenção Mensal (x%d): R$ %.2f\n", quantidadeDeMesesParaCobrar, TAXA_MANUTENCAO_MENSAL * quantidadeDeMesesParaCobrar);
                    System.out.printf("Novo Saldo: R$ %.2f\n", this.saldo);
                    this.ultimoMesCobrancaTaxaManutencao = mesAtualSimulado;
                    this.ultimoAnoCobrancaTaxaManutencao = anoAtualSimulado;
                    this.contadorTransacoesMes = 0; // Reseta o contador de transações após a cobrança mensal
                }
            }
        }
    }

    // Sobrescrição do Método de Saque
    @Override
    public void sacar(double valor){
        cobrarTaxaManutencaoMensal(); // Cobrar taxa antes do saque
        if(valor <= 0){
            System.out.println("Valor de saque inválido!\n");
        } else {
            // Verifica se a transação é extra e aplica a taxa
            if(this.contadorTransacoesMes >= TRANSACOES_GRATUITAS_MES){
                if ((this.saldo + this.limiteChequeEspecial) < (valor + TAXA_POR_TRANSACAO_EXTRA)) {
                    System.out.println("Saldo e limite do cheque especial insuficientes para o saque e a taxa de transação extra.\n");
                    return;
                }
                this.saldo -= TAXA_POR_TRANSACAO_EXTRA;
                System.out.printf("Taxa de transação extra de R$ %.2f aplicada. Saldo atual: R$ %.2f.\n", TAXA_POR_TRANSACAO_EXTRA, this.saldo);
            }

            if((this.saldo + this.limiteChequeEspecial) >= valor){
                if(this.saldo >= valor){
                    this.saldo -= valor;
                    System.out.printf("Saque de R$ %.2f realizado na conta %s. Novo saldo: R$ %.2f\n", valor, getNumeroDaConta(), this.saldo);
                } else {
                    double valorUsadoDoLimite = valor - this.saldo;
                    this.saldo = 0;
                    this.limiteChequeEspecial -= valorUsadoDoLimite;
                    System.out.printf("Saque de R$ %.2f realizado (parte do limite de cheque especial). Saldo atual: R$ %.2f. Limite restante: R$ %.2f.\n", valor, this.saldo, this.limiteChequeEspecial);
                }
                this.contadorTransacoesMes += 1; // Incrementa o contador de transações

                // Informa sobre transações gratuitas restantes
                if(TRANSACOES_GRATUITAS_MES <= contadorTransacoesMes){
                    System.out.println("Você possui 0 transações gratuitas restantes no mês. Próximas transações terão custo.\n");
                } else {
                    System.out.printf("Você possui %d transações gratuitas restantes este mês.\n", TRANSACOES_GRATUITAS_MES - contadorTransacoesMes);
                }
            } else {
                System.out.println("Saldo e limite do cheque especial insuficientes. Não é possível realizar o saque.\n");
            }
        }
    }

    @Override
    public void depositar(double valor){
        cobrarTaxaManutencaoMensal(); // Cobrar taxa antes do depósito
        super.depositar(valor);
        // Após o depósito, você pode querer incrementar o contador de transações se desejar que depósitos também contem.
        // No seu código original, apenas o saque incrementava. Mantenho a lógica original por agora.
    }

    // Definição do Método de Geração de Extrato
    @Override
    public void gerarExtrato(){
        System.out.println("\n===== EXTRATO CONTA CORRENTE ===== \n");
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
        System.out.printf("Limite de Cheque Especial: R$ %.2f\n", this.limiteChequeEspecial);
        System.out.println("Data de Criação: " + this.dataCriacao.format(getFormatter()));
        System.out.printf("Número de Transações Efetuadas neste mês: %d\n", this.contadorTransacoesMes);
        if(TRANSACOES_GRATUITAS_MES > this.contadorTransacoesMes){
            System.out.printf("Número de Transações Gratuitas restantes neste mês: %d\n", TRANSACOES_GRATUITAS_MES - this.contadorTransacoesMes);
        } else {
            System.out.println("Número de Transações Gratuitas restantes neste mês: 0\n");
        }
    }
}