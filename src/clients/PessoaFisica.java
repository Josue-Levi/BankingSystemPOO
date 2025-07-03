package clients;
import validation.GeradorCC;

public class PessoaFisica {
    // atributos
    protected String CPF;
    protected String Nascimento;
    protected String NomePessoa;
    protected String Email;
    protected String Telefone;
    protected String Estado;
    protected String Cidade;
    protected String Bairro;
    protected String Rua;
    protected int Numero;
    protected String Complemento;
    protected String CEP;
    protected String Senha;
    private GeradorCC conta;

    
    //construtores;
    public PessoaFisica(String NomePessoa, String Nascimento, String CPF, String Email, String Senha, String Telefone){
        this.CPF = CPF;
        this.Nascimento = Nascimento;
        this.NomePessoa = NomePessoa;
        this.Email = Email;
        this.Senha = Senha;
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
    
    public void setConta(GeradorCC conta) {
    this.conta = conta;
}

    //metodos
    public void ExibirDadosPessoaFisica(){
        System.out.println("\nDADOS CADASTRADOS:");
        System.out.println("===== INFORMAÇÕES PESSOAIS =====");
        System.out.println("Nome Cliente: " + NomePessoa);
        System.out.println("CPF: " + CPF);
        System.out.println("Data de Nascimento: " + Nascimento);
        System.out.println("Email: " + Email);
        System.out.println("Senha: " + Senha);
        System.out.println("Telefone: " + Telefone);
        System.out.println("===== INFORMAÇÕES RESIDENCIAL =====");
        System.out.println("Estado: " + Estado);
        System.out.println("Cidade: " + Cidade);
        System.out.println("Bairro: " + Bairro);
        System.out.println("Rua: "+ Rua +" Numero: " + Numero);
        System.out.println("Complemento: " + Complemento);
        System.out.println("CEP: " + CEP);
    }

    public boolean ValidarEmail(String Email){
        if(Email !=  null && Email.toLowerCase().endsWith("@gmail.com")){
            return true;
        } else {
            return false;
        }
    }
}
