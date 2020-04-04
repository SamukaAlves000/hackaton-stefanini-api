package com.stefanini.servico;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import javax.validation.Valid;

import com.stefanini.dao.PessoaPerfilDao;
import com.stefanini.model.PessoaPerfil;

/**
 * 
 * Classe de servico, as regras de negocio devem estar nessa classe
 * 
 * @author joaopedromilhome
 *
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class PessoaPerfilServico implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private PessoaPerfilDao dao;

	/**
	 * Salvar os dados de uma Perfil
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public PessoaPerfil salvar(@Valid PessoaPerfil pessoaPerfil) {
		return dao.salvar(pessoaPerfil);
	}
	
	/**
	 * Atualizar os dados de uma Perfil
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public PessoaPerfil atualizar(@Valid PessoaPerfil pessoaPerfil) {
		return dao.atualizar(pessoaPerfil);
	}
	
	/**
	 * 
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void remover(Long id) {
		dao.remover(id);
	}
	
	public Stream<PessoaPerfil> buscarPessoaPerfil(Long idPessoa, Long idPerfil) {
		return dao.buscarPessoaPerfil(idPessoa,idPerfil);
	}
	
	/**
	 * Buscar uma lista de Pessoa
	 */
	public Optional<List<PessoaPerfil>> getList() {
		
		CriteriaBuilder builder = dao.getEntityManager().getCriteriaBuilder();
	    CriteriaQuery<PessoaPerfil> criteriaQuery = builder.createQuery(PessoaPerfil.class);
	     
	    Root<PessoaPerfil> pessoaPerfil = criteriaQuery.from(PessoaPerfil.class);
	    
	    pessoaPerfil.fetch("perfil");
	    pessoaPerfil.fetch("pessoa").fetch("enderecos",JoinType.LEFT);
	    pessoaPerfil.fetch("pessoa").fetch("perfils");
	    criteriaQuery.select(pessoaPerfil).distinct(true);
	    
	    
	    TypedQuery<PessoaPerfil> query = dao.getEntityManager().createQuery(criteriaQuery);
	    
	    return Optional.of(query.getResultList());
		//return dao.getList();
	}
	
	public Optional<PessoaPerfil> encontrar(Long id) {
		
		CriteriaBuilder builder = dao.getEntityManager().getCriteriaBuilder();
	    CriteriaQuery<PessoaPerfil> criteriaQuery = builder.createQuery(PessoaPerfil.class);
	     
	    Root<PessoaPerfil> pessoaPerfil = criteriaQuery.from(PessoaPerfil.class);
	    
	    pessoaPerfil.fetch("perfil");
	    pessoaPerfil.fetch("pessoa").fetch("enderecos",JoinType.LEFT);
	    pessoaPerfil.fetch("pessoa").fetch("perfils");
	    criteriaQuery.select(pessoaPerfil).distinct(true);
	    
	    criteriaQuery.where(builder.equal(pessoaPerfil.get("id"),id));
	    
	    TypedQuery<PessoaPerfil> query = dao.getEntityManager().createQuery(criteriaQuery);
		
		return Optional.of(query.getSingleResult());
	}
	}