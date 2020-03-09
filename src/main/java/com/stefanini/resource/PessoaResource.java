package com.stefanini.resource;

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

import com.stefanini.model.Pessoa;
import com.stefanini.servico.PessoaServico;

@Path("pessoas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PessoaResource extends ApplicationResource{

	@Inject
	private PessoaServico pessoaServico;

	@GET
	public Response obterListaPessoa() {
		return Response.ok(pessoaServico.getList().get()).build();
	}
	
	@GET
	@Path("{id}")
	public Response obterPessoa(@PathParam("id") Long id) {
//		return Response.status(Status.INTERNAL_SERVER_ERROR).entity("deu ruim").build();
		return Response.ok(pessoaServico.encontrar(id).get()).build();
	}

	@POST
	public Response obterListaPessoa(@Valid Pessoa pessoa) {
		return Response.ok(pessoaServico.salvar(pessoa)).build();
	}
	
	@PUT
	public Response update(@Valid Pessoa pessoa) {
		return Response.ok(pessoaServico.atualizar(pessoa)).build();
	}
	
	@DELETE
	@Path("{id}")
	public Response delete(@PathParam("id") Long id) {
		pessoaServico.remover(id);
		return Response.ok().build();
	}


}
