package com.stefanini.servico;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.time.LocalDateTime;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.stefanini.dao.PerfilDao;
import com.stefanini.exception.NegocioException;
import com.stefanini.model.Perfil;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Verifications;

public class PerfilServicoTeste {

	private static final long ID = 1;

	@Injectable
	PerfilDao perfilDAO;

	@Injectable
	private PerfilServico perfilServico;

	@Before
	public void setUp() {

	}

	@After
	public void tearDown() {
		perfilServico = null;
	}

	@Test
	public void isSavePerfilRunningDAOOneTime(@Mocked Perfil perfil) {

		perfil.setNome("ADM");
		perfil.setDescricao("Perfil ADM");
		perfil.setDataHoraInclusao(LocalDateTime.now());
		perfil.setDataHoraAlteracao(LocalDateTime.now());

		new Expectations() {
			{
				perfilServico.salvar(perfil);
				result = perfil;

			}
		};
		Perfil retorno = perfil;
		new Verifications() {
			{
				assertEquals(perfil, retorno);
			}
		};

	}

	@Test
	public void isUpdatePerfilRunningDAOOneTime() {
		Perfil perfil = new Perfil();
		perfil.setId(ID);
		perfil.setNome("ADM");
		perfil.setDescricao("Perfil ADM");
		perfil.setDataHoraInclusao(LocalDateTime.now());

		String nomeAntigo = perfil.getNome();
		perfil.setNome("ADMIN");
		new Expectations() {
			{
				perfilServico.atualizar(perfil);
				result = perfil;

			}
		};
		Perfil retorno = perfil;
		new Verifications() {
			{
				assertNotEquals(perfil.getNome(), nomeAntigo);
			}
		};

	}

	@Test
	public void isGetAllPerfilByIdRunningDAOOneTime() {

		perfilServico.encontrar(ID);

		new Verifications() {
			{
				perfilDAO.encontrar(anyLong);
				times = 1;
			}
		};
	}

	@Test
	public void isAllPerfilRunningDAOOneTime() {
		perfilServico.getList();

		new Verifications() {
			{
				perfilDAO.getList();
				times = 1;
			}
		};
	}

	@Test
	public void isDeletePerfilRunningDAOOneTime() throws NegocioException {
		perfilServico.remover(ID);
		;

		new Verifications() {
			{
				perfilDAO.remover(anyLong);
				times = 1;
			}
		};
	}

}
