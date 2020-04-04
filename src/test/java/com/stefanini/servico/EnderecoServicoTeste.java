package com.stefanini.servico;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.stefanini.dao.EnderecoDao;
import com.stefanini.model.Endereco;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Verifications;

public class EnderecoServicoTeste {
	
	private static final long ID = 1;

	@Injectable
	EnderecoDao enderecoDao;

	@Injectable
	private EnderecoServico enderecoServico;

	@Before
	public void setUp() {

	}

	@After
	public void tearDown() {
		enderecoServico = null;
	}

	@Test
	public void isSaveEnderecoRunningDAOOneTime(@Mocked Endereco endereco) {

		new Expectations() {
			{
				enderecoServico.salvar(endereco);
				result = endereco;

			}
		};
		Endereco retorno = endereco;
		new Verifications() {
			{
				assertEquals(endereco, retorno);
			}
		};

	}
	
	@Test
	public void isEnderecoByIdRunningDAOOneTime() {

		enderecoServico.encontrar(ID);

		new Verifications() {
			{
				enderecoDao.encontrar(anyLong);
				times = 1;
			}
		};
	}
	
}
