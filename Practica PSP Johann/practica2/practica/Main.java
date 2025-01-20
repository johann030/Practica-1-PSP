package practica;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		System.out.println("¿Cuantos microorganismos hay en la placa petri?");
		int micro = sc.nextInt();
		sc.nextLine();
		System.out.println("¿Y cuantos nutrientes hay en la placa petri?");
		int nutri = sc.nextInt();
		sc.nextLine();
		// Número de microorganismos y nutrientes
		Nutrientes[] nutrientes = new Nutrientes[nutri];

		// Inicializar nutrientes
		for (int i = 0; i < nutri; i++) {
			nutrientes[i] = new Nutrientes(i);
		}

		// Crear e iniciar hilos de microorganismos
		for (int i = 0; i < micro; i++) {
			new Thread(new Microorganismos(i, nutrientes)).start();
		}

		sc.close();
	}
}
