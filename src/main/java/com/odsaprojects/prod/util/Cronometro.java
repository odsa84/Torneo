package com.odsaprojects.prod.util;

import java.util.Observable;

public class Cronometro extends Observable implements Runnable {
	
	private int minutos;
	private int segundos;

	public Cronometro(int minutos, int segundos) {
		this.minutos = minutos;
		this.segundos = segundos;
	}

	@Override
	public void run() {
		String tiempo;
		
		try {
			while (true) {
				
				tiempo = "";
				
				if(minutos < 10) {
					tiempo += "0" + minutos;
				}
				else {
					tiempo += minutos;
				}
				
				tiempo += ":";
				
				if(segundos < 10) {
					tiempo += "0" + segundos;
				}
				else  {
					tiempo += segundos;
				}
				
				this.setChanged();
				this.notifyObservers(tiempo);
				this.clearChanged();
				Thread.sleep(1000);
				
				segundos++;
				
				if(segundos == 60) {
					minutos++;
					segundos = 0;
				}
			}
		} catch (Exception e) {
			// TODO: handle exceptionsy
			System.out.println(e);
		}
		
	}

}
