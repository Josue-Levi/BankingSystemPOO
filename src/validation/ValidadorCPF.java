package validation;

public class ValidadorCPF {
	
	public static boolean validarCPF(String cpf) {
		boolean CPFvalido;
		String cpfDigitos = cpf.replaceAll("[^0-9]", "");
		if (cpfDigitos.length() != 11) System.out.println("CPF ÍNVALIDO!");
			
		int[] digitos = new int[11];
		for (int i = 0; i < 11; i++){
			digitos[i] = Character.getNumericValue(cpfDigitos.charAt(i));
		}
			
		int soma = 0, peso = 10;
			
		for (int i = 0; i < 9; i++){
			soma += digitos[i] * peso;
			peso--;
		}
			
		int resto = soma % 11, divisao1;
			
		if (resto < 2) divisao1 = 0;
		else divisao1 = 11 - resto;
		
		soma = 0;
		peso = 11;
			
		for (int i = 0; i < 10; i++){
			soma += digitos[i] * peso;
			peso--;
		}
			
		resto = soma % 11;
		int divisao2;
			
		if (resto < 2) divisao2 = 0;
		else divisao2 = 11 - resto;
			
		String digitoCPF = cpfDigitos.substring(9, 11);
			
		int digitoCPF1 = Character.getNumericValue(digitoCPF.charAt(0));
		int digitoCPF2 = Character.getNumericValue(digitoCPF.charAt(1));
			
		if (divisao1 == digitoCPF1 && divisao2 == digitoCPF2) {
			System.out.println("CPF válido.");
			CPFvalido = true;
		} else {
			System.out.println("CPF inválido. Tente novamente.");
			CPFvalido = false;
		}
		return CPFvalido;
	}
}
