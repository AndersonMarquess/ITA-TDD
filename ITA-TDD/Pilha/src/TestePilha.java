import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestePilha {
	
	Pilha pilha;
	
	//Roda sempre antes
	@BeforeEach
	void inicializarPilha() {
		pilha = new Pilha(10);
	}

	@Test
	void pilhaVazia() {
		assertTrue(pilha.isEmpty());
		assertEquals(0, pilha.size());
	}
	
	@Test
	void adicionaUmItemNaPilha() {
		pilha.empilhar("primeiro");
		assertFalse(pilha.isEmpty());
		assertEquals(1, pilha.size());
		assertEquals("primeiro", pilha.topo());
	}
	
	@Test
	void adicionaERemoveItem() {
		pilha.empilhar("primeiro");
		pilha.empilhar("segundo");
		assertFalse(pilha.isEmpty());
		assertEquals(2, pilha.size());
		assertEquals("segundo", pilha.topo());
		
		Object desempilhado = pilha.desempilhar();
		assertFalse(pilha.isEmpty());
		assertEquals(1, pilha.size());
		assertEquals("primeiro", pilha.topo());
		assertEquals("segundo", desempilhado);
	}
	
	@Test()
	void removeItemDaPilhaVaziaLancaUmaException() {
		assertThrows(PilhaVaziaException.class, () -> pilha.desempilhar());
	}

}
