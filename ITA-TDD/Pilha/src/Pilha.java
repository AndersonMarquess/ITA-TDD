public class Pilha {
	
	private Object[] elementos;
	private int quantidade;

	public Pilha(int tamanho) {
		this.elementos = new Object[tamanho];
	}

	public boolean isEmpty() {
		return quantidade == 0;
	}

	public int size() {
		return quantidade;
	}

	public void empilhar(Object elemento) {
		this.elementos[quantidade++] = elemento;
	}

	public Object topo() {
		return elementos[quantidade - 1];
	}

	public Object desempilhar() {
		if(isEmpty()) {
			throw new PilhaVaziaException("Não é possível desempilha uma pilha vazia.");
		}
		
		Object resultado = topo();
		quantidade--;
		return resultado;
	}
	
}
