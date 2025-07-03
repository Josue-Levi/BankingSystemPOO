package clients;

public class PessoaJuridica extends PessoaFisica{

    protected String CNPJ;
    protected String RazaoSocial;

    public PessoaJuridica(String NomePessoa, String Nascimento, String CPF, String Email, String Telefone, String CNPJ, String RazaoSocial){
        super(NomePessoa, Nascimento, CPF, Email, Telefone);
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
        System.out.println("===== INFORMAÇÕES PESSOAIS =====");
        System.out.println("Nome Cliente: " + NomePessoa);
        System.out.println("CPF: " + CPF);
        System.out.println("Data de Nascimento: " + Nascimento);
        System.out.println("Email: " + Email);
        System.out.println("Telefone: " + Telefone);
        System.out.println("===== INFORMAÇÕES DA EMPRESA =====");
        System.out.println("Responsável: " + NomePessoa);
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
}
