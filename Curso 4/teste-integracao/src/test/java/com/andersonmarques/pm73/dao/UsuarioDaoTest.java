package com.andersonmarques.pm73.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.andersonmarques.pm73.curso.CriaTabelas;
import com.andersonmarques.pm73.dominio.Usuario;

import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UsuarioDaoTest {
	private Session session;
	private UsuarioDao usuarioDao;

	/**
	 * Abre uma conexão com o banco de dados e cria uma transação.
	 * É necessário rodar a classe {@link CriaTabelas} para criar a estrutura inicial do banco de dados.
	 */
	@BeforeEach
	public void abrirConexaoComBanco() {
		session = new CriadorDeSessao().getSession();
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
	public void recuperaUsuarioPeloNomeEEmail() {
		Usuario usuario = new Usuario("João da Silva", "joaodasilva@email.com");

		usuarioDao.salvar(usuario);
		usuario = usuarioDao.porNomeEEmail("João da Silva", "joaodasilva@email.com");

		assertEquals("João da Silva", usuario.getNome());
		assertEquals("joaodasilva@email.com", usuario.getEmail());
	}

	@Test
	public void retornaNullAoNaoEncontrarUsuarioPeloNomeEEmail() {
		Usuario usuario = usuarioDao.porNomeEEmail("Inexistente", "inexistente@email.com");
		assertNull(usuario);
	}

	@Test
	public void deveDeletarUsuario() {
		Usuario usuario = new Usuario("João da Silva", "joaodasilva@email.com");
		usuarioDao.salvar(usuario);
		usuarioDao.deletar(usuario);

		//envia o comando para o banco 
		session.flush();
		// limpa o cache
		session.clear();

		usuario = usuarioDao.porNomeEEmail("João da Silva", "joaodasilva@email.com");
		assertNull(usuario);
	}

	@Test
	public void deveAtualizarUsuario() {
		Usuario usuario = new Usuario("João da Silva", "joaodasilva@email.com");
		usuarioDao.salvar(usuario);
		
		usuario.setNome("nome");
		usuario.setEmail("email");
		usuarioDao.atualizar(usuario);

		this.session.flush();

		Usuario usuarioAntigo = usuarioDao.porNomeEEmail("João da Silva", "joaodasilva@email.com");
		assertNull(usuarioAntigo);
		usuario = usuarioDao.porNomeEEmail("nome", "email");
		assertNotNull(usuario);
		assertEquals("nome", usuario.getNome());
	}

}