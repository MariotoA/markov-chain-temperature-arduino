package markov;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class MatrizMarkov {
	private final static double MIN = Math.pow(10, -6);
	private double[][] matMarkovSub,matMarkovBaj,matMarkovIgual ;
		public MatrizMarkov(String dir) throws FileNotFoundException {
			Scanner sc = new Scanner(new File(dir));
			matMarkovSub = new double[40][40];
			matMarkovBaj =new double[40][40];
			matMarkovIgual =new double[40][40];
			int before = Integer.parseInt(sc.next());
			int current = Integer.parseInt(sc.next());
			int currentDifference = current - before,beforeDifference;
			Double [] totalesSub = null,totalesBaj=null,totalesIgual=null;
			while (sc.hasNext()) {
				before = current;
				current = Integer.parseInt(sc.next());
				beforeDifference = currentDifference;
				currentDifference = current - before;
				if (currentDifference > 0) {
					matMarkovSub[beforeDifference+20][currentDifference+20]++;
				} else if (currentDifference  < 0) {
					matMarkovBaj[beforeDifference+20][currentDifference+20]++;
				} else {
					matMarkovIgual[beforeDifference+20][currentDifference+20]++;
				}
			}
			for (int i = 0; i < matMarkovSub.length;i++) {
				totalesSub = 
						Arrays.stream(matMarkovSub)
						.map(fila -> Arrays.stream(fila).sum())
						.toArray(Double[]::new);

				totalesBaj = 
						Arrays.stream(matMarkovBaj)
						.map(fila -> Arrays.stream(fila).sum())
						.toArray(Double[]::new);
				totalesIgual = 
						Arrays.stream(matMarkovIgual)
						.map(fila -> Arrays.stream(fila).sum())
						.toArray(Double[]::new);
			}
			for (int i=0; i<matMarkovSub.length;i++) {
				for (int j=0; j<matMarkovSub[i].length;j++) {
					matMarkovSub[i][j]=matMarkovSub[i][j]==0?
							MIN:
								matMarkovSub[i][j]/totalesSub[i];
					matMarkovBaj[i][j]=matMarkovBaj[i][j]==0?
							MIN:
								matMarkovBaj[i][j]/totalesBaj[i];
					matMarkovIgual[i][j]=matMarkovIgual[i][j]==0?
							MIN:
								matMarkovIgual[i][j]/totalesIgual[i];
				}
			}
			sc.close();
		}
		public static String toStringEach(double[][] miMat) {
			StringBuilder sb1= new StringBuilder();
			for (int i = 0; i < miMat.length;i++) {
				for (int j = 0; j < miMat[i].length; j++) {
					sb1.append(miMat[i][j]).append(" ");
				}
				sb1.append("\n");
			}
			return sb1.toString();
		}
		
		public String toString() {
			return toStringEach(this.matMarkovSub);
		}
		public void write(String st) throws IOException {
			PrintWriter pw = new PrintWriter(new FileWriter(st));
			pw.println(toStringEach(this.matMarkovSub));
			pw.println(toStringEach(this.matMarkovBaj));
			pw.println(toStringEach(this.matMarkovIgual));
			pw.close();
			
		}
}
