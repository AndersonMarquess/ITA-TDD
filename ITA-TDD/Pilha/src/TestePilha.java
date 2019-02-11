import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestePilha {

	@Test
	void pilhaVazia() {
		Pilha pilha = new Pilha();
		
		assertTrue(pilha.isEmpty());
		assertEquals(0, pilha.size());
	}
	
	@Test
	void adicionaUmItemNaPilha() {
		Pilha pilha = new Pilha();

		pilha.empilhar("primeiro");
		assertFalse(pilha.isEmpty());
		assertEquals(1, pilha.size());
		assertEquals("primeiro", pilha.topo());
	}

}
