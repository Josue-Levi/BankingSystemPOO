package validation;

public class ValidadorCNPJ {
	
	public static boolean validarCNPJ(String cnpj){
		boolean CNPJvalido;
		String cnpjDigitos = cnpj.replaceAll("[^0-9]", "");
		if (cnpjDigitos.length() != 14) System.out.println("CNPJ ÍNVALIDO!");
			
		int[] pesoDivisao1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
		int soma = 0;
			
		for (int i = 0; i < 12; i++){
			int digito = Character.getNumericValue(cnpjDigitos.charAt(i));
			soma += digito * pesoDivisao1[i];
		}
			
		int resto = soma % 11;
		int divisao1;
			
		if (resto < 2) divisao1 = 0;
		else divisao1 = 11 - resto;
			
		int[] pesoDivisao2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
		soma = 0;
			
		for (int i = 0; i < 13; i++){
			int digito = Character.getNumericValue(cnpjDigitos.charAt(i));
			soma += digito * pesoDivisao2[i];
		}
			
		resto = soma % 11;
		int divisao2;
			
		if (resto < 2) divisao2 = 0;
		else divisao2 = 11 - resto;
			
		int digitocnpj1 = Character.getNumericValue(cnpjDigitos.charAt(12));
		int digitocnpj2 = Character.getNumericValue(cnpjDigitos.charAt(13));
			
		if (divisao1 == digitocnpj1 && divisao2 == digitocnpj2){
			System.out.println("CNPJ válido.");
			CNPJvalido = true;
		} else {
			System.out.println("CNPJ inválido. Tente novamente.");
			CNPJvalido = false;
		}
		return CNPJvalido;
	}	
}
