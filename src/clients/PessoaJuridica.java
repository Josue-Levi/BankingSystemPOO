package clients;

import java.util.Objects;

public class PessoaJuridica extends PessoaFisica{

    protected String CNPJ;
    protected String RazaoSocial;

    public PessoaJuridica(String NomePessoa, String Nascimento, String CPF, String Email,String Senha, String Telefone, String CNPJ, String RazaoSocial){
        super(NomePessoa, Nascimento, CPF, Email, Senha, Telefone);
        this.CNPJ = CNPJ;
        this.RazaoSocial = RazaoSocial;
    }

    //Getters
    public String getRazaoSocial(){
        return RazaoSocial;
    }

    public String getCNPJ(){
        return CNPJ;
    }

    @Override
    public void setEndereco(String Estado, String Cidade, String Bairro, String Rua, int Numero, String Complemento, String CEP) {
        super.setEndereco(Estado, Cidade, Bairro, Rua, Numero, Complemento, CEP);
    }

    public void ExibirDadosPessoaJuridica(){
        System.out.println("\nDADOS CADASTRADOS:");
        System.out.println("===== INFORMAÇÕES PESSOAIS (RESPONSÁVEL) =====");
        System.out.println("Nome Responsável: " + NomePessoa);
        System.out.println("CPF do Responsável: " + CPF);
        System.out.println("Data de Nascimento do Responsável: " + Nascimento);
        System.out.println("Email do Responsável: " + Email);
        System.out.println("Senha do Responsável: " + Senha); // Em um sistema real, a senha não seria exibida
        System.out.println("Telefone do Responsável: " + Telefone);
        System.out.println("===== INFORMAÇÕES DA EMPRESA =====");
        System.out.println("Razão Social: "+ RazaoSocial);
        System.out.println("CNPJ: " + CNPJ);
        System.out.println("===== LOCALIZAÇÃO DA EMPRESA =====");
        System.out.println("Estado: " + Estado);
        System.out.println("Cidade: " + Cidade);
        System.out.println("Bairro: " + Bairro);
        System.out.println("Rua: "+ Rua +" Numero: " + Numero);
        System.out.println("Complemento: " + Complemento);
        System.out.println("CEP: " + CEP);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false; // Chama o equals da superclasse se necessário
        PessoaJuridica that = (PessoaJuridica) o;
        return Objects.equals(CNPJ, that.CNPJ) &&
                Objects.equals(Email, that.Email); // CNPJ e Email como identificadores únicos
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), CNPJ, Email);
    }
}