package ud.prog3.pr02;

import java.util.Random;

public class EstrellaJuego {

	private JLabelEstrella miGrafico;  // Etiqueta gráfica del coche
	private int posX;
	private int posY;
	
	
	public EstrellaJuego() {
		miGrafico = new JLabelEstrella();
		posX = 400;
		posY = 400;
	}
	
	/** Devuelve el JLabel gráfico asociado al coche de juego
	 * @return	Etiqueta gráfica del coche
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
