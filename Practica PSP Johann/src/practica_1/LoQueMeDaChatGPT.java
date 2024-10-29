package practica_1;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class LoQueMeDaChatGPT {

	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Uso: JavaFileCompiler <ruta archivo .java> <ruta destino .class>");
			return;
		}

		String javaFilePath = args[0];
		String destinationPath = args[1];

		// Compilar el archivo .java
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		int result = compiler.run(null, null, null, javaFilePath);

		if (result == 0) {
			System.out.println("Compilación exitosa.");

			// Obtener el archivo .class generado
			String classFilePath = javaFilePath.replace(".java", ".class");
			File classFile = new File(classFilePath);

			if (classFile.exists()) {
				try {
					// Copiar el archivo .class a la ubicación de destino
					Files.copy(classFile.toPath(), Path.of(destinationPath, classFile.getName()),
							StandardCopyOption.REPLACE_EXISTING);
					System.out.println("Archivo .class copiado a " + destinationPath);
				} catch (IOException e) {
					System.out.println("Error al copiar el archivo: " + e.getMessage());
				}
			} else {
				System.out.println("Error: el archivo .class no fue encontrado.");
			}

		} else {
			System.out.println("Compilación fallida.");
		}
	}
}
