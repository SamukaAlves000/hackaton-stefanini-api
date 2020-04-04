package com.stefanini.servico;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.stefanini.dao.PessoaDao;
import com.stefanini.exception.NegocioException;
import com.stefanini.model.Pessoa;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Verifications;

public class PessoaServicoTeste {
	


	private static final long ID = 1;

	@Injectable
	PessoaDao PessoaDAO;

	@Injectable
	private PessoaServico pessoaServico;

	@Before
	public void setUp() {

	}

	@After
	public void tearDown() {
		pessoaServico = null;
	}

	@Test
	public void isSavePessoaRunningDAOOneTime(@Mocked Pessoa pessoa) {

		

		new Expectations() {
			{
				pessoaServico.salvar(pessoa);
				result = pessoa;

			}
		};
		Pessoa retorno = pessoa;
		new Verifications() {
			{
				assertEquals(pessoa, retorno);
			}
		};

	}



	@Test
	public void isGetAllPessoaByIdRunningDAOOneTime() {

		pessoaServico.encontrar(ID);

		new Verifications() {
			{
				PessoaDAO.encontrar(anyLong);
				times = 1;
			}
		};
	}

	@Test
	public void isAllPessoaRunningDAOOneTime() {
		pessoaServico.getList();

		new Verifications() {
			{
				PessoaDAO.getList();
				times = 1;
			}
		};
	}

	@Test
	public void isDeletePessoaRunningDAOOneTime() throws NegocioException {
		pessoaServico.remover(ID);
		;

		new Verifications() {
			{
				PessoaDAO.remover(anyLong);
				times = 1;
			}
		};
	}



}
