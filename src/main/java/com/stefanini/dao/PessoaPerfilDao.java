package com.stefanini.dao;

import com.stefanini.dao.abstracao.GenericDao;
import com.stefanini.model.PessoaPerfil;
/**
 * O Unico objetivo da Dao 
 * @author samukaAlves007
 *
 */
public class PessoaPerfilDao extends GenericDao<PessoaPerfil, Long>{
	public PessoaPerfilDao() {
		super(PessoaPerfil.class);
	}
}
