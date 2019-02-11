import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestePilha {

	@Test
	void pilhaVazia() {
		Pilha pilha = new Pilha();
		
		assertTrue(pilha.isEmpty());
		assertEquals(0, pilha.size());
	}
	
	

}
