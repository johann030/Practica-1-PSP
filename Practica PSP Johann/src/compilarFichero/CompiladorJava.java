package compilarFichero;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

public class CompiladorJava {
	private Process proceso;
	private long tiempo;

	public static void main(String[] args) {

		if (args.length < 2) {
			System.out.println("Uso: CompiladorJava <ruta archivo .java> <ruta destino .class>");
			return;
		}

		String rutaJava = args[0];
		String copiaClass = args[1];

		CompiladorJava compilador = new CompiladorJava();
		compilador.compilacion(rutaJava, copiaClass);
	}

	public void compilacion(String rutaJava, String copiaClass) {
		ProcessBuilder pb;
		BufferedReader leerError;
		StringBuilder salidaError;
		String linea;
		String cambioJava;
		File archivo;
		int salida;
		long tiempoAcabado;
		long duracion;

		try {
			// Crear y ejecutar el proceso
			pb = new ProcessBuilder("javac", rutaJava);
			proceso = pb.start();
			tiempo = System.currentTimeMillis();

			// Iniciar hilo para terminar forzosamente el proceso
			Thread terminarForzado = new Thread(this::terminar);
			terminarForzado.start();

			// Captura de errores de la compilacion
			leerError = new BufferedReader(new InputStreamReader(proceso.getErrorStream()));
			salidaError = new StringBuilder();

			while ((linea = leerError.readLine()) != null) {
				salidaError.append(linea).append(System.lineSeparator());
			}

			// Esperar a que termine el proceso y calcularlo
			salida = proceso.waitFor();
			tiempoAcabado = System.currentTimeMillis();
			duracion = tiempoAcabado - tiempo;

			if (salida == 0) {
				// Obtener el archivo .class generado
				cambioJava = rutaJava.replace(".java", ".class");
				archivo = new File(cambioJava);

				// Copiar el archivo .class a la ubicacion destino
				if (archivo.exists()) {
					Files.copy(archivo.toPath(), Path.of(copiaClass, archivo.getName()),
							StandardCopyOption.REPLACE_EXISTING);
					System.out.println("Archivo .class copiado a " + copiaClass);
					System.out.println("La compilacion ha durado " + duracion + " ms");
				} else {

					System.out.println("Error: el archivo .class no fue encontrado.");
				}
			} else {
				System.out.println("Compilación fallida.");
				System.out.println("La compilacion ha durado " + duracion + " ms");
				System.out.println(salidaError.toString());
			}

		} catch (IOException e) {
			System.out.println("Error al iniciar o durante el proceso de compilacion " + e.getMessage());
		} catch (InterruptedException e) {
			System.out.println("El proceso de compilacion fue interrumpido " + e.getMessage());
		} finally {
			// Asegurar que el proceso sea destruido en caso de excepción o finalización
			if (proceso != null && proceso.isAlive()) {
				proceso.destroy();
			}
		}
	}

	private void terminar() {
		Scanner sc = null;
		try {
			sc = new Scanner(System.in);
			System.out.println("Escriba 'exit' para terminar forzosamente el proceso");
			while (proceso != null && proceso.isAlive()) {
				String salida = sc.nextLine();
				if (salida.equalsIgnoreCase("exit")) {
					proceso.destroy();
					System.out.println("Proceso terminado forzosamente.");
					break;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			sc.close();
		}
	}
}