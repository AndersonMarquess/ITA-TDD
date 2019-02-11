public class Pilha {
	
	private Object[] elementos = new Object[10];
	private int quantidade;

	public boolean isEmpty() {
		return quantidade == 0;
	}

	public int size() {
		return quantidade;
	}

	public void empilhar(Object elemento) {
		this.elementos[quantidade++] = elemento;
		//quantidade++;
	}

	public Object topo() {
		return elementos[quantidade - 1];
	}

	public Object desempilhar() {
		Object resultado = topo();
		quantidade--;
		return resultado;
	}
	
}
