package validation;

public class ValidadorEmail {

    public static boolean ValidarEmail(String Email){
        if(Email !=  null && Email.toLowerCase().endsWith("@gmail.com")){
            return true;
        } else {
            return false;
        }
    }  
}
