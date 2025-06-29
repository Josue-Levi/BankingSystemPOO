package model;

public class ContaPoupanca extends Conta{
    private double taxaRendimentoMensal;

    public ContaPoupanca(int numeroDaConta, String titular, double saldo, double taxaRendimentoMensal){
        super(numeroDaConta, titular, saldo);
        this.taxaRendimentoMensal = taxaRendimentoMensal;
    }
    
    public double getTaxaRendimentoMensal() {
        return taxaRendimentoMensal;
    }

    public void setTaxaRendimentoMensal(double taxaRendimentoMensal) {
        this.taxaRendimentoMensal = taxaRendimentoMensal;
    }

    

    
}