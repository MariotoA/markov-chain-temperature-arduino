package markov;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.stream.*;

public class FitMarkov {
	 Double min;
	 MatrizMarkov m;
	 private int tamVentana;
	
	public FitMarkov(MatrizMarkov m,int tamVentana) throws FileNotFoundException {
		this.tamVentana = tamVentana;
		this.m = m;
		double res =1,mini=2;
		for(int i=0; i<m.variaciones.size()-this.tamVentana; i++) {
			for(int j=i; j<i+this.tamVentana; j++) {
				 res *= devuelveProp(j);
			}
			mini = Math.min(mini, res);
			res=1;
		}
		this.min = mini;
		
	}
	
	public void buscaAnomalia (String dir) throws FileNotFoundException {
		double res =1;
		List<Integer> variaciones = new ArrayList<>();
		Scanner sc = new Scanner(new File(dir));
		int before = Integer.parseInt(sc.next());
		int current = Integer.parseInt(sc.next());
		int currentDifference = current - before;
		variaciones.add(currentDifference);
		while (sc.hasNext()) {
			before = current;
			current = Integer.parseInt(sc.next());
			currentDifference = current - before;
			variaciones.add(currentDifference);
		}
		sc.close();
		for(int i=0; i<variaciones.size()-tamVentana; i++) {
			for(int j=i; j<i+tamVentana; j++) {
				 res *= devuelveProp(j,variaciones);
			}
			if(res<this.min) {
				System.out.println("Anomalía "+(i+1));
			}
			res=1;
		}
		
	}
	
	private double devuelveProp(int var) {
		int i=var,j=var+1,varActual = m.variaciones.get(j),varAnterior = m.variaciones.get(i);
		if (varActual < varAnterior){
			return m.matMarkovBaj[varAnterior+20][varActual+20];
		}else if(varActual > varAnterior) {
			return m.matMarkovSub[varAnterior+20][varActual+20];
		}else {
			return m.matMarkovIgual[varAnterior+20][varActual+20];
		}
	}
	
	private double devuelveProp(int var, List<Integer> variaciones) {
		int i=var,j=var+1,varActual = variaciones.get(j),varAnterior = variaciones.get(i);
		if (varActual < varAnterior){
			return m.matMarkovBaj[varAnterior+20][varActual+20];
		}else if(varActual > varAnterior) {
			return m.matMarkovSub[varAnterior+20][varActual+20];
		}else {
			return m.matMarkovIgual[varAnterior+20][varActual+20];
		}
	}
	

	public void compruebaAnomalias(Queue<Integer> ventanas, byte[] buffer) {
		//Pasa byte a entero
		List<Integer> next = IntStream.range(0, buffer.length/2) // [0,1,2,3,4] ->
		.map(i->i*2) // [0,2,4,6,8] ->
		.map(i -> createInt(buffer[i], buffer[i+1])) // [i01,i23,i45,i67,i89]
		.boxed()
		.collect(Collectors.toList());
		//Comprueba anomalías para cada entero
		next.stream().forEach(i -> compruebaAnomalias(ventanas,i));
		
	}


	private Boolean compruebaAnomalias(Queue<Integer> ventanas, Integer i) {
		boolean hayAnomalia = false;
		if (ventanas.size()==this.tamVentana) {
			hayAnomalia = compruebaAnomalias(ventanas);
			ventanas.poll();
		}
		ventanas.offer(i);
		return hayAnomalia;
	}

	private Boolean compruebaAnomalias(Queue<Integer> ventanas) {
		Iterator<Integer> iter = ventanas.iterator();
		int before = -1;
		int current = -1;
		List<Integer> variaciones = new ArrayList<>();
		double res=1;
		if (iter.hasNext()) {
			current = iter.next();
		}
		
		while (iter.hasNext()) {
			before = current;
			current = iter.next();
			variaciones.add(current - before);
		}
		
		for (int i = 0; i < variaciones.size(); i++) {
			res*=devuelveProp(i,variaciones);
		}
		
		if(res<this.min) {
			System.out.println("Anomalía "+ventanas);
		}
		
		return res<this.min;
	}

	private static int createInt(byte most, byte least) {
		int a = most;
		a = a<<8;
		int b = least;
		return a + b;
	}
}
