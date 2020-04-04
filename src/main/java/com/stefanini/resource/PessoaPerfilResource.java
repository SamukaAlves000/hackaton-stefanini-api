package com.stefanini.resource;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import com.stefanini.model.Endereco;
import com.stefanini.model.Perfil;
import com.stefanini.model.Pessoa;
import com.stefanini.model.PessoaPerfil;
import com.stefanini.servico.PessoaPerfilServico;

@Path("pessoasPerfils")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PessoaPerfilResource {
	
	private static Logger log = Logger.getLogger(PessoaResource.class.getName());
	
	@Inject
	private PessoaPerfilServico pessoaPerfilServico;
	/**
	 *
	 */
	@Context
	private UriInfo uriInfo;
	
	/**
	 *
	 * @return
	 */
	@GET
	public Response obterPessoas() {
		log.info("Obtendo lista de pessoas");
		MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
		Optional<List<PessoaPerfil>> listPessoa = pessoaPerfilServico.getList();
		return listPessoa.map(pessoas -> Response.ok(pessoas).build()).orElseGet(() -> Response.status(Status.NOT_FOUND).build());

	}
	
	@POST
    public Response adicionarPessoaPerfil(@Valid PessoaPerfil pessoaPerfil) {
        return Response.ok(pessoaPerfilServico.salvar(pessoaPerfil)).build();
    }
	
	
   @PUT
   public Response atualizarPerfil(@Valid PessoaPerfil pessoaPerfil) {
       log.info("Atualizando perfil");
       return Response.ok(pessoaPerfilServico.atualizar(pessoaPerfil)).build();
   }
   
   /**
	 *
	 * @param id
	 * @return
	 */
	@GET
	@Path("{id}")
	public Response obterPessoa(@PathParam("id") Long id) {
		return pessoaPerfilServico.encontrar(id).map(pessoasPerfils -> Response.ok(pessoasPerfils).build()).orElseGet(() -> Response.status(Status.NOT_FOUND).build());
	}


}
