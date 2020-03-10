package com.stefanini.servico;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.Valid;

import com.stefanini.dao.PessoaDao;
import com.stefanini.model.Endereco;
import com.stefanini.model.Pessoa;

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
public class PessoaServico implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private PessoaDao dao;

	/**
	 * Salvar os dados de uma Pessoa
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Pessoa salvar(@Valid Pessoa pessoa) {
		return dao.salvar(pessoa);
	}

	/**
	 * Atualizar o dados de uma pessoa
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Pessoa atualizar(@Valid Pessoa entity) {
		return dao.atualizar(entity);
	}

	/**
	 * Remover uma pessoa pelo id
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void remover(@Valid Long id) {
		System.out.println("Identificador:"+id);
		dao.remover(id);
	}

	/**
	 * Buscar uma lista de Pessoa
	 */
	public Optional<List<Pessoa>> getList() {
		return dao.getList();
	}

	/**
	 * Buscar uma Pessoa pelo ID
	 */
	public Optional<Pessoa> encontrar(Long id) {
		return dao.encontrar(id);
	}
	
	/**
	 * Buscar pessoas por situacao 
	 */
	public List<Pessoa> encontrar(boolean situacao) {
		
		CriteriaBuilder builder = dao.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Pessoa> criteria = builder.createQuery(Pessoa.class);
		Root<Pessoa> root = criteria.from(Pessoa.class);
		
		criteria.where(root.get("situacao").in(situacao));
		TypedQuery<Pessoa> query = dao.getEntityManager().createQuery(criteria);
		
		return query.getResultList();
	}
	
	
	/**
	 * Buscar pessoas de determinada UF
	 */
	public List<Pessoa> encontrar(String uf) {
		
		CriteriaBuilder builder = dao.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Pessoa> criteria = builder.createQuery(Pessoa.class);
		Root<Pessoa> root = criteria.from(Pessoa.class);
		
		List<Predicate> predicateList = new ArrayList<Predicate>();
		
		Join<Pessoa, Endereco> join = root.join("enderecos");
		Predicate predEnd = criteria.where(join.get("uf").in(uf)).getRestriction();
		
		predicateList.add(predEnd);
		
		Predicate[] predicates = new Predicate[predicateList.size()];
		predicateList.toArray(predicates);
		criteria.where(predicates);
		 
		TypedQuery<Pessoa> query = dao.getEntityManager().createQuery(criteria);
		
		return query.getResultList();
		
	}

}
