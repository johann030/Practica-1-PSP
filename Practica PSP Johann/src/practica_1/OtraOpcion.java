package practica_1;

import java.io.*;
import java.nio.file.*;

public class OtraOpcion {

	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Uso: JavaFileCompiler <ruta archivo .java> <ruta destino .class>");
			return;
		}

		String javaFilePath = args[0];
		String destinationPath = args[1];

		try {
			// Ejecutar el compilador como proceso externo
			ProcessBuilder processBuilder = new ProcessBuilder("javac", javaFilePath);
			Process process = processBuilder.start();

			// Capturar errores de compilación
			BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			StringBuilder errorOutput = new StringBuilder();
			String line;
			while ((line = errorReader.readLine()) != null) {
				errorOutput.append(line).append(System.lineSeparator());
			}

			int exitCode = process.waitFor();
			if (exitCode == 0) {
				System.out.println("Compilación exitosa.");

				// Obtener el archivo .class generado
				String classFilePath = javaFilePath.replace(".java", ".class");
				File classFile = new File(classFilePath);

				if (classFile.exists()) {
					// Copiar el archivo .class a la ubicación de destino
					Files.copy(classFile.toPath(), Path.of(destinationPath, classFile.getName()),
							StandardCopyOption.REPLACE_EXISTING);
					System.out.println("Archivo .class copiado a " + destinationPath);
				} else {
					System.out.println("Error: el archivo .class no fue encontrado.");
				}
			} else {
				System.out.println("Compilación fallida con los siguientes errores:");
				System.out.println(errorOutput.toString());
			}
		} catch (IOException | InterruptedException e) {
			System.out.println("Error durante la compilación: " + e.getMessage());
		}
	}
}