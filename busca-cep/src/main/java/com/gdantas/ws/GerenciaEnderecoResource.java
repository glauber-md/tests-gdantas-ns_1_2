
/**
 * 
 */
package com.gdantas.ws;

import java.text.MessageFormat;

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
import com.gdantas.data.ws.input.ReplyMessage;
import com.gdantas.util.ResponseBuilder;

/**
 * JAXB + JSON
 * @author Glauber M. Dantas glauber.md@gmail.com
 *
 */
@Path("testes/crud")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class GerenciaEnderecoResource {

	private @Autowired EnderecoDao enderecoDao;
	
	@GET
	@Path("/enderecos/{id}")
	public Response get(@PathParam("id") final Integer id) {
		Endereco endereco = enderecoDao.get(id);
		return (endereco != null) ?
				ResponseBuilder.success(new ReplyMessage(DefaultReplyCodes.SUCESSO, endereco))
				: ResponseBuilder.notFound(new ReplyMessage(DefaultReplyCodes.NAO_ENCONTRADO, MessageFormat.format("Registro não encontrado para o ID {0}", id)));
	}
	
	@DELETE
	@Path("/enderecos/{id}")
	public Response delete(@PathParam("id") final Integer id) {
		
		int dbUpdated = enderecoDao.delete(id);
		
		if(dbUpdated <= 0)
			return ResponseBuilder.notFound(new ReplyMessage(DefaultReplyCodes.NAO_ENCONTRADO, "Exclusão não realizada"));
		
		return ResponseBuilder.success(new ReplyMessage(DefaultReplyCodes.SUCESSO, "Registro excluído com sucesso"));
	}
	
	@PUT
	@Path("/enderecos/{id}")
	public Response update(@PathParam("id") final Integer id, final Endereco in) {
		// Valida input
		if(id == null || in == null || (in != null && !in.isValid()))
			return ResponseBuilder.badRequest(new ReplyMessage(DefaultReplyCodes.FORMATO_ENTRADA_INVALIDO, "Dados fornecidos inválidos"));
		
		in.setId(id);
		int dbUpdated = enderecoDao.update(in);
		
		if(dbUpdated <= 0)
			return ResponseBuilder.notFound(new ReplyMessage(DefaultReplyCodes.NAO_ENCONTRADO, "Atualização não realizada"));
		
		return ResponseBuilder.success(new ReplyMessage(DefaultReplyCodes.SUCESSO, "Registro atualizado com sucesso"));
	}
	
	@POST
	@Path("/enderecos")
	public Response add(final Endereco in) {
		// Valida input
		if(in == null || (in != null && !in.isValid()))
			return ResponseBuilder.badRequest(new ReplyMessage(DefaultReplyCodes.FORMATO_ENTRADA_INVALIDO, "Dados fornecidos inválidos"));
		
		int dbUpdated = enderecoDao.add(in);
		
		if(dbUpdated <= 0)
			return ResponseBuilder.notFound(new ReplyMessage(DefaultReplyCodes.NAO_ENCONTRADO, "Inserção não realizada"));
		
		return ResponseBuilder.success(new ReplyMessage(DefaultReplyCodes.SUCESSO, "Registro inserido com sucesso"));
	}
}