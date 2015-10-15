package ud.prog3.pr02;

import java.util.Random;

public class EstrellaJuego {

	private JLabelEstrella miGrafico;  // Etiqueta gr�fica del coche
	private int posX;
	private int posY;
	
	
	public EstrellaJuego() {
		miGrafico = new JLabelEstrella();
		posX = 400;
		posY = 400;
	}
	
	/** Devuelve el JLabel gr�fico asociado al coche de juego
	 * @return	Etiqueta gr�fica del coche
	 */
	public JLabelEstrella getGrafico() {
		return miGrafico;
	}
	
	public double getPosX() {
		return posX;
	}

	public double getPosY() {
		return posY;
	}

	public void setPosisicion( double posX, double posY ) {
		miGrafico.setLocation( (int)posX, (int)posY );
		miGrafico.repaint();
	}

	

}
