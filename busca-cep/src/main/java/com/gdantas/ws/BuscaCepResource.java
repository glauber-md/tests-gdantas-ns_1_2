/**
 * 
 */
package com.gdantas.ws;

import java.text.MessageFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.gdantas.data.access.EnderecoDao;
import com.gdantas.data.domain.Endereco;
import com.gdantas.data.enums.DefaultReplyCodes;
import com.gdantas.data.ws.input.InCep;
import com.gdantas.data.ws.input.ReplyMessage;
import com.gdantas.util.InputDataUtil;
import com.gdantas.util.ResponseBuilder;

/**
 * JAXB + JSON
 * @author Glauber M. Dantas glauber.md@gmail.com
 *
 */
@Path("testes/ws-ns1")
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
					endereco = enderecoDao.getByCep(cepAtual);
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
	
	@DELETE
	@Path("/ceps/{cep}")
	public Response delete(@PathParam("cep") final String cep) {
		int dbUpdated = enderecoDao.delete(0);
		
		if(dbUpdated <= 0)
			return ResponseBuilder.notFound(new ReplyMessage(DefaultReplyCodes.NAO_ENCONTRADO, "Exclusão não realizada"));
		
		return ResponseBuilder.success(new ReplyMessage(DefaultReplyCodes.SUCESSO, "Registro excluído com sucesso"));
	}
	
	@PUT
	@Path("/ceps/{cep}")
	public Response update(@NotNull @Valid final InCep in) {
		int dbUpdated = enderecoDao.update(null);
		
		if(dbUpdated <= 0)
			return ResponseBuilder.notFound(new ReplyMessage(DefaultReplyCodes.NAO_ENCONTRADO, "Atualização não realizada"));
		
		return ResponseBuilder.success(new ReplyMessage(DefaultReplyCodes.SUCESSO, "Registro atualizado com sucesso"));
	}
	
	@POST
	@Path("/ceps")
	public Response add(@NotNull @Valid final InCep in) {
		int dbUpdated = enderecoDao.add(null);
		
		if(dbUpdated <= 0)
			return ResponseBuilder.notFound(new ReplyMessage(DefaultReplyCodes.NAO_ENCONTRADO, "Inserção não realizada"));
		
		return ResponseBuilder.success(new ReplyMessage(DefaultReplyCodes.SUCESSO, "Registro inserido com sucesso"));
	}
}