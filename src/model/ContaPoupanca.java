package model;

import clients.PessoaFisica;
import clients.PessoaJuridica;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

public class ContaPoupanca extends Conta {

    private final double TAXA_RENDIMENTO_MENSAL = 0.005; // 0.5%
    private int diaAniversarioRendimento;
    private int ultimoMesRendimentoAplicado;
    private int ultimoAnoRendimentoAplicado;


    public ContaPoupanca(PessoaFisica titular, double saldoInicial) {
        super(titular, saldoInicial);
        this.diaAniversarioRendimento = this.dataCriacao.getDayOfMonth();
        this.ultimoMesRendimentoAplicado = this.dataCriacao.getMonthValue();
        this.ultimoAnoRendimentoAplicado = this.dataCriacao.getYear();
        System.out.println("Tipo de Conta: Poupança");
    }

    @Override
    public void gerarExtrato() {
        System.out.println("\n===== EXTRATO CONTA POUPANÇA ===== \n");
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
        System.out.println("Data de Criação: " + this.dataCriacao.format(getFormatter()));
        System.out.printf("Taxa de Rendimento Mensal: %.1f%%\n", TAXA_RENDIMENTO_MENSAL * 100);
    }
}
