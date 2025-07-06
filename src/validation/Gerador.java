 package validation;

import java.util.Random;

public class Gerador {
    private String numeroConta;
    private final String agencia;
    private final String codigoBanco;
    private final String codigoOperar;
    private static final Random random = new Random();

    public Gerador(){
        this.agencia = "1430";
        this.codigoBanco = "345";
        this.codigoOperar = "001";
        this.numeroConta = gerarConta();
    }
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

        // O dígito verificador é calculado com base na agência, operação e número da conta
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