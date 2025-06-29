package clients;

public class PessoaFisica {
    // atributos
    private String CPF;
    private String Nascimento;
    private String NomePessoa;
    private String Email;
    private String Telefone;
    private String Estado;
    private String Cidade;
    private String Bairro;
    private String Rua;
    private int Numero;
    private String Complemento;
    private String CEP;

    
    //construtores;
    public PessoaFisica(String NomePessoa, String Nascimento, String CPF, String Email, String Telefone){
        this.CPF = CPF;
        this.Nascimento = Nascimento;
        this.NomePessoa = NomePessoa;
        this.Email = Email;
        this.Telefone = Telefone;
    }
    public void setEndereco(String Estado, String Cidade, String Bairro, String Rua, int Numero, String Complemento, String CEP){
        this.Estado = Estado;
        this.Cidade = Cidade;
        this.Bairro = Bairro;
        this.Rua = Rua;
        this.Numero = Numero;
        this.Complemento = Complemento;
        this.CEP = CEP;
    }

    //metodos
    public void CadastroPessoa(){
        System.out.println("DADOS CADASTRADOS:");
        System.out.println("===== INFORMAÇÕES PESSOAIS =====");
        System.out.println("Nome Cliente: " + NomePessoa);
        System.out.println("Email: " + Email);
        System.out.println("Telefone: " + Telefone);
        System.out.println("Data de Nascimento: " + Nascimento);
        System.out.println("CPF: " + CPF);
        System.out.println("=====INFORMAÇÕES RESIDENCIAL =====");
        System.out.println("Estado: " + Estado);
        System.out.println("Cidade: " + Cidade);
        System.out.println("Bairro: " + Bairro);
        System.out.println("Rua: "+ Rua +"Numero: " + Numero);
        System.out.println("Complemento: " + Complemento);
        System.out.println("CEP: " + CEP);
    }
}
