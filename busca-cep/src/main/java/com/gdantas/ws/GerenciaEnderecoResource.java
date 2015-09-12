
/**
 * 
 */
package com.gdantas.ws;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

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
@Path("crud")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class GerenciaEnderecoResource {

	private @Autowired EnderecoDao enderecoDao;
	private static final Logger log = LoggerFactory.getLogger(GerenciaEnderecoResource.class);
	
	@GET
	@Path("/enderecos/{id}")
	public Response get(@PathParam("id") final Integer id) {
		Endereco endereco = null;
		try {
			endereco = enderecoDao.get(id);
		} catch (DataAccessException e) {
			log.error("SELECT não executado: {}", e.getMessage());
			return ResponseBuilder.error(new ReplyMessage(DefaultReplyCodes.FALHA_GENERICA, "Falha na consulta"));
		}
		return (endereco != null) ?
				ResponseBuilder.success(new ReplyMessage(DefaultReplyCodes.SUCESSO, endereco))
				: ResponseBuilder.notFound(new ReplyMessage(DefaultReplyCodes.NAO_ENCONTRADO, MessageFormat.format("Registro não encontrado para o ID {0}", id)));
	}
	
	@DELETE
	@Path("/enderecos/{id}")
	public Response delete(@PathParam("id") final Integer id) {
		
		int dbUpdated = 0;
		try {
			dbUpdated = enderecoDao.delete(id);
		} catch (DataAccessException e) {
			log.error("DELETE não executado: {}", e.getMessage());
			return ResponseBuilder.error(new ReplyMessage(DefaultReplyCodes.FALHA_GENERICA, "Falha na exclusão do registro"));
		}
		
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
		
		int dbUpdated = 0;
		try {
			in.setId(id);
			dbUpdated = enderecoDao.update(in);
		} catch (DataAccessException e) {
			log.error("UPDATE não executado: {}", e.getMessage());
			return ResponseBuilder.error(new ReplyMessage(DefaultReplyCodes.FALHA_GENERICA, "Falha na atualização do registro"));
		}
		
		if(dbUpdated <= 0)
			return ResponseBuilder.notFound(new ReplyMessage(DefaultReplyCodes.NAO_ENCONTRADO, "Atualização não realizada"));
		
		return ResponseBuilder.success(new ReplyMessage(DefaultReplyCodes.SUCESSO, "Registro atualizado com sucesso"));
	}
	
	@POST
	@Path("/enderecos")
	public Response add(final Endereco in, @Context UriInfo ui) {
		// Valida input
		if(in == null || (in != null && !in.isValid()))
			return ResponseBuilder.badRequest(new ReplyMessage(DefaultReplyCodes.FORMATO_ENTRADA_INVALIDO, "Dados fornecidos inválidos"));
		
		int recordKey = 0;
		try {
			recordKey = enderecoDao.add(in);
		} catch (DataAccessException e) {
			log.error("INSERT não executado: {}", e.getMessage());
			return ResponseBuilder.error(new ReplyMessage(DefaultReplyCodes.FALHA_GENERICA, "Falha na inserção do registro"));
		}
		
		if(recordKey <= 0)
			return ResponseBuilder.notFound(new ReplyMessage(DefaultReplyCodes.NAO_ENCONTRADO, "Inserção não realizada"));
		
		URI newItemLocation = null;
		try {
			newItemLocation = new URI(MessageFormat.format("{0}/{1,number,#}", ui.getAbsolutePath().toASCIIString(), recordKey));
		} catch (URISyntaxException e) {
			log.error("Erro ao obter URL do recurso: {}", e.getMessage());
			return ResponseBuilder.error(new ReplyMessage(DefaultReplyCodes.FALHA_GENERICA, "Falha na obtenção da URL do recurso criado"));
		}
		
		return ResponseBuilder.created(newItemLocation, new ReplyMessage(DefaultReplyCodes.SUCESSO, "Registro inserido com sucesso"));
	}
}