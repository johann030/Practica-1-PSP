package compilarFichero;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

//Proceso padre

public class CompiladorLanzador {

	public static void main(String[] args) {
		// Instrucciones para dar informacion al hijo(System.in)
		ProcessBuilder pb;
		Process proceso;
		InputStream is;
		InputStreamReader isr;
		BufferedReader br;

		// Instrucciones para leer la informacion del hijo(System.out)
		OutputStream os;
		OutputStreamWriter osw;
		PrintWriter pw;

		Scanner sc;
		String lectura;

		try {
			pb = new ProcessBuilder(args);
			proceso = pb.start();
			is = proceso.getInputStream();
			isr = new InputStreamReader(is);
			br = new BufferedReader(isr);

			os = proceso.getOutputStream();
			osw = new OutputStreamWriter(os);
			pw = new PrintWriter(osw);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}