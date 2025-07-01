package validation;

import java.util.Random;

public class GeradorCC {
    private String numeroConta;
    private final String agencia;
    private final String codigoBanco;
    private final String codigoOperar;
    private static final Random random = new Random();

    public GeradorCC (){
        this.agencia = "1430";
        this.codigoBanco = "345";
        this.codigoOperar = "001";
        this.numeroConta = gerarConta();
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
        return "Banco: " + this.codigoBanco + "\nAgência: " + this.agencia +
                "\nOperação: " + this.codigoOperar + "\nConta Corrente: " + this.numeroConta;
    }
}
