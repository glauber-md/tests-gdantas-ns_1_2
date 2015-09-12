/**
 * 
 */
package com.gdantas.data.enums;

/**
 * @author Glauber M. Dantas glauber.md@gmail.com
 *
 */
public enum DefaultReplyCodes {

	FORMATO_ENTRADA_INVALIDO(3,"Formato de parâmetro de entrada inválido"),
	NAO_ENCONTRADO(2,"Registro não encontrado para parâmetro fornecido"),
	SUCESSO(1,"Operação realizada com sucesso"),
	FALHA_GENERICA(0,"Erro no processamento da solicitação");
	
	int code;
	String description;
	
	private DefaultReplyCodes(int code, String desc) {
		this.code = code;
		this.description = desc;
	}
	
	public int getCode() {
		return code;
	}
	
	public String getDescription() {
		return description;
	}
}
