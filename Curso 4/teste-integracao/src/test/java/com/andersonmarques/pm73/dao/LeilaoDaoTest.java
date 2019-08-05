package com.andersonmarques.pm73.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Calendar;
import java.util.List;

import com.andersonmarques.pm73.builders.LeilaoBuilder;
import com.andersonmarques.pm73.dominio.Leilao;
import com.andersonmarques.pm73.dominio.Usuario;

import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LeilaoDaoTest {

	private Session session;
	private LeilaoDao leilaoDao;
	private UsuarioDao usuarioDao;

	@BeforeEach
	public void abrirConexaoComBanco() {
		// Rodar a classe de criar sessão/tabelas antes;
		session = new CriadorDeSessao().getSession();
		leilaoDao = new LeilaoDao(session);
		usuarioDao = new UsuarioDao(session);

		// inicia a transação antes de cada teste
		session.beginTransaction();
	}

	@AfterEach
	private void fecharConexaoComBanco() {
		// faz o rollback da transação após cada teste
		session.getTransaction().rollback();
		session.close();
	}

	@Test
	public void deveContarLeiloesNaoEncerrados() {
		Usuario usuario = new Usuario("João da Silva", "joaodasilva@email.com");
		this.usuarioDao.salvar(usuario);

		Leilao leilaoAtivo = new LeilaoBuilder()
			.comDono(usuario)
			.construir();

		Leilao leilaoEncerrado = new LeilaoBuilder()
			.comDono(usuario)
			.encerrado()
			.construir();

		this.leilaoDao.salvar(leilaoAtivo);
		this.leilaoDao.salvar(leilaoEncerrado);

		assertEquals(1, leilaoDao.total());
	}

	@Test
	public void soRetornarLeilaoDeProdutosNovos() {
		Usuario usuario = new Usuario("João da Silva", "joaodasilva@email.com");
		this.usuarioDao.salvar(usuario);

		Leilao leilaoProdutoNovo = new LeilaoBuilder()
			.comDono(usuario)
			.construir();
			
		Leilao leilaoProdutoVelho = new LeilaoBuilder()
			.comDono(usuario)
			.usado()
			.construir();

		this.leilaoDao.salvar(leilaoProdutoNovo);
		this.leilaoDao.salvar(leilaoProdutoVelho);

		List<Leilao> produtosNovos = leilaoDao.novos();
		assertNotNull(produtosNovos);
		assertEquals(1, produtosNovos.size());
	}

	@Test
	public void soRetornarLeilaoCriadosHaExtadosSeteDiasOuAnterior() {
		Usuario usuario = new Usuario("João da Silva", "joaodasilva@email.com");
		this.usuarioDao.salvar(usuario);

		Leilao leilaoNovo = new LeilaoBuilder()
			.comDono(usuario)
			.construir();
			
		Leilao leilaoComSeteDias = new LeilaoBuilder()
			.comDono(usuario)
			.diasAtras(7)
			.construir();

		Leilao leilaoComMaisDeSeteDias = new LeilaoBuilder()
			.comDono(usuario)
			.diasAtras(8)
			.construir();

		this.leilaoDao.salvar(leilaoNovo);
		this.leilaoDao.salvar(leilaoComSeteDias);
		this.leilaoDao.salvar(leilaoComMaisDeSeteDias);

		List<Leilao> leilaoCriadoHaMaisDeSeteDias = leilaoDao.antigos();
		assertNotNull(leilaoCriadoHaMaisDeSeteDias);
		assertEquals(2, leilaoCriadoHaMaisDeSeteDias.size());
	}

	@Test
	public void soRetornarLeilaoCriadoNoPeriodoEspecificado() {
		Usuario usuario = new Usuario("João da Silva", "joaodasilva@email.com");
		this.usuarioDao.salvar(usuario);

		Leilao leilaoNovo = new LeilaoBuilder()
			.comDono(usuario)
			.construir();
			
		Leilao leilaoComSeteDias = new LeilaoBuilder()
			.comDono(usuario)
			.diasAtras(5)
			.construir();

		Leilao leilaoComMaisDeSeteDias = new LeilaoBuilder()
			.comDono(usuario)
			.diasAtras(8)
			.construir();

		this.leilaoDao.salvar(leilaoNovo);
		this.leilaoDao.salvar(leilaoComSeteDias);
		this.leilaoDao.salvar(leilaoComMaisDeSeteDias);

		Calendar inicio = Calendar.getInstance();
		inicio.add(Calendar.DAY_OF_MONTH, -6);
		Calendar fim = Calendar.getInstance();

		List<Leilao> leiloesNoPeriodo = leilaoDao.porPeriodo(inicio, fim);
		assertNotNull(leiloesNoPeriodo);
		assertEquals(2, leiloesNoPeriodo.size());
	}

	@Test
	public void soRetornarLeilaoNaoEncerradosCriadoNoPeriodoEspecificado() {
		Usuario usuario = new Usuario("João da Silva", "joaodasilva@email.com");
		this.usuarioDao.salvar(usuario);

		Leilao leilaoNovo = new LeilaoBuilder()
			.comDono(usuario)
			.encerrado()
			.construir();
			
		Leilao leilaoComSeteDias = new LeilaoBuilder()
			.comDono(usuario)
			.diasAtras(5)
			.construir();

		Leilao leilaoComMaisDeSeteDias = new LeilaoBuilder()
			.comDono(usuario)
			.diasAtras(8)
			.construir();

		this.leilaoDao.salvar(leilaoNovo);
		this.leilaoDao.salvar(leilaoComSeteDias);
		this.leilaoDao.salvar(leilaoComMaisDeSeteDias);

		Calendar inicio = Calendar.getInstance();
		inicio.add(Calendar.DAY_OF_MONTH, -6);
		Calendar fim = Calendar.getInstance();

		List<Leilao> leiloesNaoEncerradosNoPeriodo = leilaoDao.porPeriodo(inicio, fim);
		assertNotNull(leiloesNaoEncerradosNoPeriodo);
		assertEquals(1, leiloesNaoEncerradosNoPeriodo.size());
	}

	@Test
	public void retornaLeiloesComMaisDeTresLancesComValorInicialNoIntervalo() {
		Usuario usuario = new Usuario("João da Silva", "joaodasilva@email.com");
		this.usuarioDao.salvar(usuario);
		Usuario usuario2 = new Usuario("João só João", "joaosojoao@email.com");
		this.usuarioDao.salvar(usuario2);

		Leilao leilaoBarato = new LeilaoBuilder()
			.comDono(usuario)
			.comValor(500d)
			.comLance(usuario, 500d)
			.comLance(usuario2, 530d)
			.comLance(usuario, 570d)
			.comLance(usuario2, 590d)
			.construir();
			
		Leilao leilaoJusto = new LeilaoBuilder()
			.comDono(usuario)
			.comValor(1000d)
			.construir();

		Leilao leilaoCaro = new LeilaoBuilder()
			.comDono(usuario)
			.comValor(1500d)
			.construir();

		this.leilaoDao.salvar(leilaoBarato);
		this.leilaoDao.salvar(leilaoJusto);
		this.leilaoDao.salvar(leilaoCaro);

		List<Leilao> leiloesComValoresNoIntervalo = leilaoDao.disputadosEntre(500d, 1200d);
		assertNotNull(leiloesComValoresNoIntervalo);
		assertEquals(1, leiloesComValoresNoIntervalo.size());
		assertEquals(500d, leiloesComValoresNoIntervalo.get(0).getLances().get(0).getValor(), 0.00001d);
	}

	@Test
	public void retornaLeiloesQueOUsuarioDeuLanceSemRepetir() {
		Usuario usuario = new Usuario("João da Silva", "joaodasilva@email.com");
		this.usuarioDao.salvar(usuario);
		Usuario usuario2 = new Usuario("João só João", "joaosojoao@email.com");
		this.usuarioDao.salvar(usuario2);

		Leilao leilaoBarato = new LeilaoBuilder()
			.comDono(usuario)
			.comValor(500d)
			.comLance(usuario, 500d)
			.comLance(usuario2, 510d)
			.comLance(usuario, 530d)
			.construir();
			
			Leilao leilaoJusto = new LeilaoBuilder()
			.comDono(usuario)
			.comValor(1000d)
			.comLance(usuario, 1000d)
			.construir();

		Leilao leilaoCaro = new LeilaoBuilder()
			.comDono(usuario)
			.comValor(1500d)
			.construir();

		this.leilaoDao.salvar(leilaoBarato);
		this.leilaoDao.salvar(leilaoJusto);
		this.leilaoDao.salvar(leilaoCaro);

		List<Leilao> leiloes = leilaoDao.listaLeiloesDoUsuario(usuario);
		assertNotNull(leiloes);
		assertEquals(2, leiloes.size());
		assertEquals(500d, leiloes.get(0).getValorInicial(), 0.00001d);
		assertEquals(1000d, leiloes.get(1).getValorInicial(), 0.00001d);
	}

	@Test
	public void verificarValorMedioInicialDosLeiloesQueOUsuarioParticipou(){
		Usuario usuario = new Usuario("João da Silva", "joaodasilva@email.com");
		this.usuarioDao.salvar(usuario);

		Leilao leilaoBarato = new LeilaoBuilder()
			.comDono(usuario)
			.comValor(500d)
			.comLance(usuario, 500d)
			.comLance(usuario, 500d)
			.construir();
			
			Leilao leilaoJusto = new LeilaoBuilder()
			.comDono(usuario)
			.comValor(1000d)
			.comLance(usuario, 1000d)
			.construir();

		Leilao leilaoCaro = new LeilaoBuilder()
			.comDono(usuario)
			.comValor(1500d)
			.construir();

		this.leilaoDao.salvar(leilaoBarato);
		this.leilaoDao.salvar(leilaoJusto);
		this.leilaoDao.salvar(leilaoCaro);

		Double valorInicialMedio = leilaoDao.getValorInicialMedioDoUsuario(usuario);
		assertEquals(750d, valorInicialMedio, 0.00001d);
	}

	@Test
	public void deveDeletarLeilao() {	
		Usuario usuario = new Usuario("João da Silva", "joaodasilva@email.com");
		this.usuarioDao.salvar(usuario);
		
		Leilao leilao = new LeilaoBuilder()
			.comDono(usuario)
			.comValor(1500d)
			.construir();

		this.leilaoDao.salvar(leilao);
		this.leilaoDao.deleta(leilao);

		this.session.flush();
		this.session.clear();

		assertNull(this.leilaoDao.porId(leilao.getId()));
	}
}