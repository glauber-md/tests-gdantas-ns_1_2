/**
 * 
 */
package com.gdantas.util;

import java.net.URI;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * @author Glauber M. Dantas (glauber.dantas@tvftecnologia.com.br) TVF Tecnologia
 *
 */
public class ResponseBuilder {

	public static Response success(Object data) {
		return Response.status(Status.OK).entity(data).build();
	}
	
	public static Response notFound(Object data) {
		return Response.status(Status.NOT_FOUND).entity(data).build();
	}
	
	public static Response badRequest(Object data) {
		return Response.status(Status.BAD_REQUEST).entity(data).build();
	}
	
	public static Response created(URI location) {
		return Response.status(Status.CREATED).location(location).build();
	}
	
	public static Response created(URI location, Object data) {
		return Response.status(Status.CREATED).entity(data).location(location).build();
	}
	
	public static Response error(Object data) {
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(data).build();
	}
}