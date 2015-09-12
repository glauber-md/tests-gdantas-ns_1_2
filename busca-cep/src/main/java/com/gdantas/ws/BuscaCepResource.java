/**
 * 
 */
package com.gdantas.ws;

import java.text.MessageFormat;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import com.gdantas.data.access.EnderecoDao;
import com.gdantas.data.domain.Endereco;
import com.gdantas.data.enums.DefaultReplyCodes;
import com.gdantas.data.ws.input.ReplyMessage;
import com.gdantas.util.InputDataUtil;
import com.gdantas.util.ResponseBuilder;

/**
 * JAXB + JSON
 * @author Glauber M. Dantas glauber.md@gmail.com
 *
 */
@Path("busca")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class BuscaCepResource {

	private @Autowired EnderecoDao enderecoDao;
	
	@GET
	@Path("/ceps/{cep}")
	public Response get(@PathParam("cep") final String cep) {
		Endereco endereco = null;
		String cepAtual = cep;
		boolean encontrado = false;
		
		if(InputDataUtil.isValidCep(cepAtual)) {
			endereco = enderecoDao.getByCep(cepAtual);
			if(endereco == null) {
				// Não encontrado; tentar alternativas
				while(!encontrado && (cepAtual = InputDataUtil.getAlternativeCep(cepAtual)) != null) {
					try {
						endereco = enderecoDao.getByCep(cepAtual);
					} catch (DataAccessException e) {
						return ResponseBuilder.error(new ReplyMessage(DefaultReplyCodes.FALHA_GENERICA, "Falha na consulta"));
					}
					// Retorna ao encontrar endereço
					if(endereco != null)
						encontrado = true;
				}
			} else {
				encontrado = true;
			}
		} else {
			return ResponseBuilder.badRequest(new ReplyMessage(DefaultReplyCodes.FORMATO_ENTRADA_INVALIDO, MessageFormat.format("CEP inválido: {0}", cep)));
		}
		return encontrado ?
				ResponseBuilder.success(new ReplyMessage(DefaultReplyCodes.SUCESSO, endereco))
				: ResponseBuilder.notFound(new ReplyMessage(DefaultReplyCodes.NAO_ENCONTRADO, MessageFormat.format("Endereço não encontrado para o CEP informado: {0}", cep)));
	}
	
}