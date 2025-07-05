package model;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import clients.PessoaFisica;
import clients.PessoaJuridica;



public class EntraConta {

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("===== LOGIN CONTA =====");
        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        if(verificarlogin(email, senha)) {
            System.out.println("Login sucedido!");
        } else {
            System.out.println("Email ou senha inválidos.");
        }
    }

    private static boolean verificarlogin(String email, String senha) {
    Gson gson = new Gson();
    Type listTypeFisica = new TypeToken<List<PessoaFisica>>() {}.getType();
    Type listTypeJuridica = new TypeToken<List<PessoaJuridica>>() {}.getType();

        try {
            // Verifica pessoa física
            FileReader readerFisica = new FileReader("src/clients/CadastrosFisica.json");
            List<PessoaFisica> pessoasFisicas = gson.fromJson(readerFisica, listTypeFisica);
            readerFisica.close();

            for (PessoaFisica pf : pessoasFisicas) {
                if (pf.getEmail().equals(email) && pf.getSenha().equals(senha)) {
                    System.out.println("Usuário pessoa física logado.");
                    return true;
                }
            }

            // Verifica pessoa jurídica
            FileReader readerJuridica = new FileReader("src/clients/CadastrosJuridico.json");
            List<PessoaJuridica> pessoasJuridicas = gson.fromJson(readerJuridica, listTypeJuridica);
            readerJuridica.close();

            for (PessoaJuridica pj : pessoasJuridicas) {
                if (pj.getEmail().equals(email) && pj.getSenha().equals(senha)) {
                    System.out.println("Usuário pessoa jurídica logado.");
                    return true;
                }
            }

        } catch (Exception e) {
            System.out.println("Erro ao ler arquivos JSON: " + e.getMessage());
        }

        return false;
    }
}
