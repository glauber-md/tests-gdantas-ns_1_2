/**
 * 
 */
package com.gdantas.util;



/**
 * @author  Glauber M. Dantas (glauber.dantas@tvftecnologia.com.br) TVF Tecnologia
 */
public class InputDataUtil {
	
	public static String getAlternativeCep(final String cep) {
		if(isValidCep(cep)) {
			String prefixo = cep.substring(0, cep.length()-3);
			char[] sufixo = cep.substring(prefixo.length(), cep.length()).toCharArray();
			
			if(String.valueOf(sufixo).equals("000"))
				return null;
			
			StringBuilder altCep = new StringBuilder();
			altCep.append(prefixo);
			
			for(int idx = sufixo.length-1; idx >= 0; idx--) {
				if(sufixo[idx] != '0') {
					sufixo[idx] = '0';
					altCep.append(sufixo);
					break;
				} else {
					continue;
				}
			}
			return altCep.toString();
		} else {
			return null;
		}
	}
	
	public static boolean isValidCep(final String cep) {
		return cep != null && cep.matches("[0-9]{8}");
	}
	
}