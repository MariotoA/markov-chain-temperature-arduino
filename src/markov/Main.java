package markov;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		String dir = "datos.txt",dirMat ="matrizmarkov.txt";
		try {
			MatrizMarkov m = new MatrizMarkov(dir);
			m.write(dirMat);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
