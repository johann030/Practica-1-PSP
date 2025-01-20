package practica;

import java.util.concurrent.ThreadLocalRandom;

public class Microorganismos extends Thread {
	private Nutrientes[] nutriente;
	private int id;

	public Microorganismos(int id, Nutrientes[] nutriente) {
		this.id = id;
		this.nutriente = nutriente;
	}

	@Override
	public void run() {
		Nutrientes izquierdo = nutriente[id];
		Nutrientes derecho = nutriente[(id + 1) % nutriente.length];
		while (true) {
			try {
				// En reposo
				System.out.println("Microorganismo " + id + " en reposo.");
				Thread.sleep(ThreadLocalRandom.current().nextInt(500, 1000));

				// Quieren alimentarse
				System.out.println("Microorganismo " + id + " quiere absorber nutrientes.");

				if (id % 2 == 0) {
					synchronized (derecho) {
						derecho.tomar();
						synchronized (izquierdo) {
							izquierdo.tomar();

							// Alimentandose
							System.out.println("Microorganismo " + id + " absorbiendo.");
							Thread.sleep(ThreadLocalRandom.current().nextInt(500, 1000));
							izquierdo.soltar();
						}
						derecho.soltar();
					}
				} else {
					synchronized (izquierdo) {
						izquierdo.tomar();
						synchronized (derecho) {
							derecho.tomar();

							// Alimentándose
							System.out.println("Microorganismo " + id + " absorbiendo.");
							Thread.sleep(ThreadLocalRandom.current().nextInt(500, 1000));
							derecho.soltar();
						}
						izquierdo.soltar();
					}
				}
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				return;
			}
		}
	}
}