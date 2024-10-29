package practica_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Compilador {

	static ArrayList<String> lista = new ArrayList<>();
	static int salida;

	public static void main(String[] args) {

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String archivoClass = br.readLine();

			lista.add("javac");
			lista.add("-d");
			lista.add(archivoClass);
			lista.add(args[0]);

			ProcessBuilder pb = new ProcessBuilder(lista);
			Process proceso = pb.start();

			salida = proceso.waitFor();
			if (salida == 0) {
				System.out.println("Compilacion finalizada correctamente");
			} else {
				System.out.println("Error al compilar");
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}

	}
}
