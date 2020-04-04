package com.stefanini.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import com.stefanini.dto.ErroDto;
import com.stefanini.dto.PessoaDto;
import com.stefanini.exception.NegocioException;
import com.stefanini.model.Perfil;
import com.stefanini.model.Pessoa;
import com.stefanini.model.PessoaPerfil;
import com.stefanini.servico.PessoaPerfilServico;
import com.stefanini.servico.PessoaServico;

@Path("pessoas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PessoaResource {

	private static Logger log = Logger.getLogger(PessoaResource.class.getName());

	/**
	 * Classe de servico da Pessoa
	 */
	@Inject
	private PessoaServico pessoaServico;
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
		Optional<List<Pessoa>> listPessoa = pessoaServico.getList();
		return listPessoa.map(pessoas -> Response.ok(pessoas).build()).orElseGet(() -> Response.status(Status.NOT_FOUND).build());

	}
	
	/**
	 *
	 * @param pessoaDto
	 * @return
	 */
	@POST
	public Response adicionarPessoa(@Valid PessoaDto pessoaDto) {
		
		Pessoa pessoa = new Pessoa(pessoaDto.getNome(), pessoaDto.getEmail(), pessoaDto.getDataNascimento(), pessoaDto.getSituacao());
		pessoa.setEnderecos(pessoaDto.getEnderecos());
		
		if(pessoaServico.validarPessoa(pessoa)){
			pessoa = pessoaServico.salvar(pessoa);
			
			Set<Perfil> conjunto = pessoaDto.getPerfils();
			for (Perfil perfil : conjunto) {
				
				//Criando Objeto PessoaPerfil
				PessoaPerfil pessoaPerfil = new PessoaPerfil();
				
				long chave = Long.parseLong(pessoa.getId()+""+perfil.getId());
				pessoaPerfil.setId(chave);
				
				pessoaPerfil.setPerfil(perfil);
				pessoaPerfil.setPessoa(pessoa);
				pessoaPerfilServico.salvar(pessoaPerfil);
						
			}
			return Response.ok(pessoa).build();
		}
		return Response.status(Status.METHOD_NOT_ALLOWED).entity(new ErroDto("email","email já existe", pessoa.getEmail())).build();
	}
	
	/**
	 *
	 * @param pessoaDto
	 * @return
	 */
	@PUT
	public Response atualizarPessoa(@Valid PessoaDto pessoaDto) {
		
		Pessoa pessoa = new Pessoa(pessoaDto.getNome(), pessoaDto.getEmail(), pessoaDto.getDataNascimento(), pessoaDto.getSituacao());
		pessoa.setId(pessoaDto.getId());
		pessoa.setEnderecos(pessoaDto.getEnderecos());
		
		if(pessoaServico.validarPessoa(pessoa)){
			pessoa = pessoaServico.atualizar(pessoa);
			
			Set<Perfil> conjunto = pessoaDto.getPerfils();
			for (Perfil perfil : conjunto) {
				
				//Criando Objeto PessoaPerfil
				PessoaPerfil pessoaPerfil = new PessoaPerfil();
				
				long chave = Long.parseLong(pessoa.getId()+""+perfil.getId());
				pessoaPerfil.setId(chave);
				pessoaPerfil.setPerfil(perfil);
				pessoaPerfil.setPessoa(pessoa);
				pessoaPerfilServico.salvar(pessoaPerfil);
						
			}
			
			return Response.ok(pessoa).build();
		}
		return Response.status(Status.METHOD_NOT_ALLOWED).entity(new ErroDto("email","email já existe", pessoa.getEmail())).build();
	}

	/**
	 *
	 * @param id
	 * @return
	 */
	@DELETE
	@Path("{id}")
	public Response deletarPessoa(@PathParam("id") Long id) {
		try{
			if(pessoaServico.encontrar(id).isPresent()){
				pessoaServico.remover(id);
				return Response.status(Response.Status.OK).build();
			}else {
				return Response.status(Response.Status.NOT_FOUND).build();
			}
		} catch (NegocioException e) {
			return Response.status(Response.Status.METHOD_NOT_ALLOWED).entity(new ErroDto(null,e.getMensagem(),id)).build();
		}
	}

	/**
	 *
	 * @param id
	 * @return
	 */
	@GET
	@Path("{id}")
	public Response obterPessoa(@PathParam("id") Long id) {
		return pessoaServico.encontrar(id).map(pessoas -> Response.ok(pessoas).build()).orElseGet(() -> Response.status(Status.NOT_FOUND).build());
	}
	
	/**
	 *
	 * @param id
	 * @return
	 */
	@GET
	@Path("existePerfil/{id}")
	public Response possuiPerfil(@PathParam("id") Long id) {
		Pessoa pessoa = pessoaServico.encontrar(id).get();
		List<Perfil> list = new ArrayList<>();
		list.addAll(pessoa.getPerfils());
		return Response.ok(!list.isEmpty()).build();
	}

}