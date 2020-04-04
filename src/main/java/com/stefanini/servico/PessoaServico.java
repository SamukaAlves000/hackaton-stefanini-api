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
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import javax.validation.Valid;

import com.stefanini.dao.PessoaDao;
import com.stefanini.exception.NegocioException;
import com.stefanini.model.Perfil;
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

	@Inject
	private PessoaPerfilServico pessoaPerfilServico;

	/**
	 * Salvar os dados de uma Pessoa
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Pessoa salvar(@Valid Pessoa pessoa) {
		return dao.salvar(pessoa);
	}

	/**
	 * Validando se existe pessoa com email
	 */
	public Boolean validarPessoa(@Valid Pessoa pessoa) {
		if (pessoa.getId() != null) {
			Optional<Pessoa> encontrar = dao.encontrar(pessoa.getId());
			if (encontrar.get().getEmail().equals(pessoa.getEmail())) {
				return Boolean.TRUE;
			}
		}
		Optional<Pessoa> pessoa1 = dao.buscarPessoaPorEmail(pessoa.getEmail());
		return pessoa1.isEmpty();
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
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void remover(@Valid Long id) throws NegocioException {
		if (pessoaPerfilServico.buscarPessoaPerfil(id, null).count() == 0) {
			dao.remover(id);
			return;
		}
		throw new NegocioException("Não foi possivel remover a pessoa");
	}

	/**
	 * Buscar uma lista de Pessoa gerando varias consultas
	 */
	public Optional<List<Pessoa>> getList1() {
		return dao.getList();
	}

	/**
	 * Buscar uma lista de Pessoa e resolve o problema do N+1
	 */
	public Optional<List<Pessoa>> getList() {

		CriteriaBuilder builder = dao.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Pessoa> criteriaQuery = builder.createQuery(Pessoa.class);

		Root<Pessoa> pessoa = criteriaQuery.from(Pessoa.class);

		pessoa.fetch("perfils", JoinType.LEFT);
		pessoa.fetch("enderecos", JoinType.LEFT);
		criteriaQuery.select(pessoa).distinct(true).orderBy(builder.asc(pessoa.get("nome")));

		TypedQuery<Pessoa> query = dao.getEntityManager().createQuery(criteriaQuery);
		return Optional.of(query.getResultList());

	}

	/**
	 * Buscar uma Pessoa pelo ID
	 */

	public Optional<Pessoa> encontrar(Long id) {

		CriteriaBuilder builder = dao.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Pessoa> criteriaQuery = builder.createQuery(Pessoa.class);

		Root<Pessoa> pessoa = criteriaQuery.from(Pessoa.class);

		pessoa.fetch("perfils", JoinType.LEFT);
		pessoa.fetch("enderecos", JoinType.LEFT);
		criteriaQuery.select(pessoa).distinct(true);

		criteriaQuery.where(builder.equal(pessoa.get("id"), id));

		TypedQuery<Pessoa> query = dao.getEntityManager().createQuery(criteriaQuery);

		return Optional.of(query.getSingleResult());

	}

	/**
	 * Buscar um Perfil pelo ID da Pessoa
	 */
	public Perfil encontrarPerfilIdPessoa(Long id) {

		Pessoa pessoa = dao.encontrar(id).get();
		List<Perfil> list = new ArrayList<>();
		list.addAll(pessoa.getPerfils());
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

}
