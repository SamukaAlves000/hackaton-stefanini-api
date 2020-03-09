package com.stefanini.resource;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.validation.Valid;
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

import com.stefanini.model.Perfil;
import com.stefanini.servico.PerfilServico;



@Path("perfils")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PerfilResource extends ApplicationResource{

	@Inject
	private PerfilServico perfilServico;

	@GET
	public Response findAll() {
		return Response.ok(perfilServico.getList().get()).build();
	}
	
	@GET
	@Path("{id}")
	public Response findById(@PathParam("id") Long id) {
//		return Response.status(Status.INTERNAL_SERVER_ERROR).entity("deu ruim").build();
		return Response.ok(perfilServico.encontrar(id).get()).build();
	}
	
	@POST
	public Response save(@Valid Perfil endereco) {
		return Response.ok(perfilServico.salvar(endereco)).build();
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@PUT
	public Response update(@Valid Perfil endereco) {
		return Response.ok(perfilServico.atualizar(endereco)).build();
	}
	
	@DELETE
	@Path("{id}")
	public Response delete(@PathParam("id") Long id) {
		perfilServico.remover(id);
		return Response.ok().build();
	}
}
