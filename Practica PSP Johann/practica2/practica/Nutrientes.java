package practica;

public class Nutrientes {
	private int id;
	private boolean ocupado = false;

	public Nutrientes(int id) {
		this.id = id;
	}

	public synchronized void tomar() {
		while (ocupado) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		ocupado = true;
	}

	public synchronized void soltar() {
		ocupado = false;
		notifyAll();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isOcupado() {
		return ocupado;
	}

	public void setOcupado(boolean ocupado) {
		this.ocupado = ocupado;
	}
}