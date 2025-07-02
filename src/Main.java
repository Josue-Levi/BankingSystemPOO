import clients.CadastroPessoaFisica;
import clients.CadastroPessoaJuridica;
import validation.GeradorCC;
import model.Conta;
import model.ContaCorrente;
import model.ContaPoupanca;

import java.util.Scanner;


public class Main {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n===== MENU =====");
        System.out.println("[1] Cadastrar Conta");
        System.out.println("[2] Entrar na Conta");
        int criarEntrar = scanner.nextInt();
        if(criarEntrar == 1){
            System.out.println("[1] Pessoa Física");
            System.out.println("[2] Pessoa Jurídica");
            int fisicaJuridica = scanner.nextInt();
            switch(fisicaJuridica){
                case 1:
                    CadastroPessoaFisica.CadastrarPessoaFisica();
                    break;
                case 2:
                    CadastroPessoaJuridica.CadastrarPessoaJuridica();
                    break;
            }
            GeradorCC conta1 = new GeradorCC();
            System.out.println(conta1);
        } else if(criarEntrar == 2){

        }


        scanner.close();
    }
}
