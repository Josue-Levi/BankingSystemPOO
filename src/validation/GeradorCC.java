package validation;

import java.util.Random;

public class GeradorCC {
    private String numeroConta;
    private final String agencia = "1430";
    private final String codigoBanco = "345";
    private final String codigoOperar = "001";
    private static final Random random = new Random();

    //Getters
    public String getNumeroConta(){
        return numeroConta;
    }

    public String getAgencia(){
        return agencia;
    }

    public String getCodigoBanco(){
        return codigoBanco;
    }

    public String getCodigoOperar(){
        return codigoOperar;
    }

    private String gerarConta(){
        StringBuilder contaGerar = new StringBuilder();
        for (int i = 0; i < 8; i++){
            contaGerar.append(random.nextInt(10));
        }
        String numeroContaGerar = contaGerar.toString();

        String digito = agencia + codigoOperar + numeroContaGerar;

        int somaDigitos = 0;

        for (int i = 0; i < digito.length(); i++){
            somaDigitos += Character.getNumericValue(digito.charAt(i));
        }

        int digitoVerificador = somaDigitos % 10;

        return numeroContaGerar + "-" + digitoVerificador;
    }

    @Override
    public String toString() {
        return "\n===== CONTA GERADA =====\n" +
                "Banco: " + this.codigoBanco + "\nAgência: " + this.agencia +
                "\nOperação: " + this.codigoOperar + "\nNúmero da Conta: " + this.numeroConta;
    }
}
