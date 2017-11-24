package markov;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;



import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class MatrizMarkov {
	private final static double MIN = Math.pow(10, -6);
	 double[][] matMarkovSub,matMarkovBaj,matMarkovIgual ;
	List<Integer> variaciones = new ArrayList<>();
		public MatrizMarkov(String dir) throws FileNotFoundException {
			Scanner sc = new Scanner(new File(dir));
			matMarkovSub = new double[40][40];
			matMarkovBaj =new double[40][40];
			matMarkovIgual =new double[40][40];
			
			int before = Integer.parseInt(sc.next());
			int current = Integer.parseInt(sc.next());
			int currentDifference = current - before,beforeDifference;
			variaciones.add(currentDifference);
			double [] totalesSub = null,totalesBaj=null,totalesIgual=null;
			while (sc.hasNext()) {
				before = current;
				current = Integer.parseInt(sc.next());
				beforeDifference = currentDifference;
				currentDifference = current - before;
				variaciones.add(currentDifference);
				if (currentDifference > beforeDifference) {
					matMarkovSub[beforeDifference+20][currentDifference+20]++;//matMarkovSub[currentDifference+20][beforeDifference+20]++;
				} else if (currentDifference < beforeDifference) {
					matMarkovBaj[beforeDifference+20][currentDifference+20]++;
				} else {
					matMarkovIgual[beforeDifference+20][currentDifference+20]++;
				}
			}
		/*	for (int i = 0; i < matMarkovSub.length;i++) {
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
			}*/
			totalesSub = new double [40];
			totalesBaj = new double [40];
			totalesIgual = new double [40];
			for(int i=0; i<40; i++) {
				for(int j=0; j<40;j++) {
					totalesSub[i] += matMarkovSub[i][j];
					totalesBaj[i] += matMarkovBaj[i][j];
					totalesIgual[i]+= matMarkovIgual[i][j];
				}
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
			return toStringEach(this.matMarkovSub)+"\n"+toStringEach(this.matMarkovBaj)+"\n"+toStringEach(this.matMarkovIgual);
		}
		public void write(String st) throws IOException {
			PrintWriter pw = new PrintWriter(new FileWriter(st));
			pw.println(toStringEach(this.matMarkovSub));
			pw.println(toStringEach(this.matMarkovBaj));
			pw.println(toStringEach(this.matMarkovIgual));
			pw.close();
			
		}
}
