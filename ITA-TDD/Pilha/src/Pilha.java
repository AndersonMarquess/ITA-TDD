public class Pilha {
	
	private Object elemento;
	private int quantidade;

	public boolean isEmpty() {
		return elemento == null;
	}

	public int size() {
		return quantidade;
	}

	public void empilhar(Object elemento) {
		this.elemento = elemento;
		quantidade++;
	}

	public Object topo() {
		return elemento;
	}
	
}
