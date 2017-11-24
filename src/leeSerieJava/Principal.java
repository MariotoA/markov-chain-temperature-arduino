

package leeSerieJava;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jssc.SerialPort;
import jssc.SerialPortException;
import markov.FitMarkov;
import markov.MatrizMarkov;

import java.util.Queue;
import java.util.LinkedList;

public class Principal {


	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String dir = "datos.txt";
		SerialPort serialPort = new SerialPort("COM4");
	    try {
	    	MatrizMarkov m = new MatrizMarkov(dir);
			FitMarkov f = new FitMarkov(m,5);
	        serialPort.openPort();//Open serial port
	        serialPort.setParams(9600, 8, 1, 0);//Set params.
	        
	        byte[] buffer;
	        Queue<Integer> ventanas = new LinkedList<>();
	        while (true) {
	        	buffer= serialPort.readBytes(1);//Read 10 bytes from serial port
	        	f.compruebaAnomalias(ventanas,buffer);
	        }
	      //  serialPort.closePort();//Close serial port
	    }
	    catch (SerialPortException ex) {
	        System.out.println(ex);
	    } catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}



}
