package com.odsaprojects.prod.util;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class CronometroView extends JFrame {


	private static final long serialVersionUID = 1L;
	
	private JLabel label1;

	/* En el constructor de la clase llamamos al método heredado de la clase JFrame llamado setLayout y le pasamos como parámetro un valor null, con esto estamos informándole a la clase JFrame que utilizaremos posicionamiento absoluto para los controles visuales dentro del JFrame*/
	public CronometroView() {
		System.setProperty("java.awt.headless", "false");
		
		setLayout(null);

		/* Creamos el objeto de la clase JLabel y le pasamos como parámetro al constructor el texto a mostrar.*/
		label1=new JLabel("Hola Mundo.");

		/* Ubicamos al objeto de la clase JLabel llamando al método setBounds, este requiere como parámetros la columna, fila, ancho y alto del JLabel. Finalmente llamamos al método add (metodo heredado de la clase JFrame) que tiene como objetivo añadir el control JLabel al control JFrame */
		label1.setBounds(10,20,200,30);
		add(label1);
	}

}
