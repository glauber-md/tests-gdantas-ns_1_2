/**
 * 
 */
package com.gdantas.data.enums;

import javax.ws.rs.core.Response;

/**
 * @author Glauber M. Dantas glauber.md@gmail.com
 *
 */
public enum DefaultReplyCodes {

	FORMATO_ENTRADA_INVALIDO(Response.Status.BAD_REQUEST,"Formato de parâmetro de entrada inválido"),
	NAO_ENCONTRADO(Response.Status.NOT_FOUND,"Registro não encontrado para parâmetro fornecido"),
	SUCESSO(Response.Status.OK,"Operação realizada com sucesso"),
	FALHA_GENERICA(Response.Status.INTERNAL_SERVER_ERROR,"Erro no processamento da solicitação");
	
	Response.Status code;
	String description;
	
	private DefaultReplyCodes(Response.Status code, String desc) {
		this.code = code;
		this.description = desc;
	}
	
	public Response.Status getCode() {
		return code;
	}
	
	public String getDescription() {
		return description;
	}
}
