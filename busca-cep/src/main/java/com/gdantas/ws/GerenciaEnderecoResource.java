
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
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
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
import com.sun.jersey.api.client.UniformInterfaceException;

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
	
	/**
	 * Obtem o endereço cadastrado.
	 * @param id o ID do endereço a obter.
	 * @return <code>ReplyMessage</code> no formato JSON.  
	 */
	@GET
	@Path("/enderecos/{id}")
	public Response get(@PathParam("id") final Integer id) {
		Endereco endereco = null;
		try {
			// Efetua SELECT pelo ID
			endereco = enderecoDao.get(id);
		} catch (DataAccessException e) {
			log.error("SELECT não executado: {}", e.getMessage());
			return ResponseBuilder.error(new ReplyMessage(DefaultReplyCodes.FALHA_GENERICA, "Falha na consulta"));
		}
		return (endereco != null) ?
				ResponseBuilder.success(new ReplyMessage(DefaultReplyCodes.SUCESSO, endereco))
				: ResponseBuilder.notFound(new ReplyMessage(DefaultReplyCodes.NAO_ENCONTRADO, MessageFormat.format("Registro não encontrado para o ID {0}", id)));
	}
	
	/**
	 * Apaga o endereço cadastrado.
	 * @param id o ID do endereço a apagar.
	 * @return <code>ReplyMessage</code> no formato JSON.  
	 */
	@DELETE
	@Path("/enderecos/{id}")
	public Response delete(@PathParam("id") final Integer id) {
		
		int dbUpdated = 0;
		try {
			// Efetua DELETE pelo ID
			dbUpdated = enderecoDao.delete(id);
		} catch (DataAccessException e) {
			log.error("DELETE não executado: {}", e.getMessage());
			return ResponseBuilder.error(new ReplyMessage(DefaultReplyCodes.FALHA_GENERICA, "Falha na exclusão do registro"));
		}
		
		if(dbUpdated <= 0)
			return ResponseBuilder.notFound(new ReplyMessage(DefaultReplyCodes.NAO_ENCONTRADO, "Exclusão não realizada"));
		
		return ResponseBuilder.success(new ReplyMessage(DefaultReplyCodes.SUCESSO, "Registro excluído com sucesso"));
	}
	
	/**
	 * Atualiza o endereço cadastrado.
	 * @param id o ID do endereço a atualizar.
	 * @param in o payload JSON informado no request, representando o Endereço. 
	 * @param ui <code>UriInfo</code> fornecido pelo framework. 
	 * @return <code>ReplyMessage</code> no formato JSON.  
	 */
	@PUT
	@Path("/enderecos/{id}")
	public Response update(@PathParam("id") final Integer id, final Endereco in, @Context UriInfo ui) {
		// Valida input
		if(id == null || in == null || (in != null && !in.isValid()))
			return ResponseBuilder.badRequest(new ReplyMessage(DefaultReplyCodes.FORMATO_ENTRADA_INVALIDO, "Dados fornecidos inválidos"));
		
		int dbUpdated = 0;
		try {
			// Invoca WS BuscaCEP e em caso de erro retorna a mensagem produzida pelo WS 
			ReplyMessage wsBuscaCepReply = verificaCep(in.getCep(), ui);
			if(wsBuscaCepReply.getCode() != DefaultReplyCodes.SUCESSO.getCode().getStatusCode())
				return ResponseBuilder.error(wsBuscaCepReply);
			
			// Efetua UPDATE pelo ID utilizando payload
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
	
	/**
	 * Adiciona um endereço ao cadastro.
	 * @param in o payload JSON informado no request, representando o Endereço.
	 * @param ui <code>UriInfo</code> fornecido pelo framework. 
	 * @return <code>ReplyMessage</code> no formato JSON.  
	 */
	@POST
	@Path("/enderecos")
	public Response add(final Endereco in, @Context UriInfo ui) {
		// Valida input
		if(in == null || (in != null && !in.isValid()))
			return ResponseBuilder.badRequest(new ReplyMessage(DefaultReplyCodes.FORMATO_ENTRADA_INVALIDO, "Dados fornecidos inválidos"));
		
		int recordKey = 0;
		try {
			// Invoca WS BuscaCEP e em caso de erro retorna a mensagem produzida pelo WS 
			ReplyMessage wsBuscaCepReply = verificaCep(in.getCep(), ui);
			if(wsBuscaCepReply.getCode() != DefaultReplyCodes.SUCESSO.getCode().getStatusCode())
				return ResponseBuilder.error(wsBuscaCepReply);
			
			// Efetua INSERT utilizando payload 
			recordKey = enderecoDao.add(in);
		} catch (DataAccessException e) {
			log.error("INSERT não executado: {}", e.getMessage());
			return ResponseBuilder.error(new ReplyMessage(DefaultReplyCodes.FALHA_GENERICA, "Falha na inserção do registro"));
		}
		
		if(recordKey <= 0)
			return ResponseBuilder.notFound(new ReplyMessage(DefaultReplyCodes.NAO_ENCONTRADO, "Inserção não realizada"));
		
		/*
		 *  Gera o campo 'Location' do Header do Response, seguindo boas práticas REST para
		 *  novos recusrsos criados. 
		 */
		URI newItemLocation = null;
		try {
			newItemLocation = new URI(MessageFormat.format("{0}/{1,number,#}", ui.getAbsolutePath().toASCIIString(), recordKey));
		} catch (URISyntaxException e) {
			log.error("Erro ao obter URL do recurso: {}", e.getMessage());
			return ResponseBuilder.error(new ReplyMessage(DefaultReplyCodes.FALHA_GENERICA, "Falha na obtenção da URL do recurso criado"));
		}
		
		return ResponseBuilder.created(newItemLocation, new ReplyMessage(DefaultReplyCodes.SUCESSO, "Registro inserido com sucesso"));
	}

	/**
	 * Invoca WS de busca de CEP.
	 * @param cep
	 * @return <code>ReplyMessage</code> encapsulando a resposta do webservice.
	 */
	private ReplyMessage verificaCep(String cep, UriInfo ui) {
		WebTarget target = ClientBuilder.newClient()
				.target(MessageFormat.format("{0}", ui.getBaseUri()))
				.path(MessageFormat.format("/busca/ceps/{0}", cep));
	
		ReplyMessage retornoWs = null;
		try {
			retornoWs = target
					.request(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.get(ReplyMessage.class);
		} catch (UniformInterfaceException ue) {
			retornoWs = new ReplyMessage(ue.getResponse().getStatus(), "Erro ao invocar webservice", ue.getMessage());
	    }
		
		return retornoWs;
	}
}