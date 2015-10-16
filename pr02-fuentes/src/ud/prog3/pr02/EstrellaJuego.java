package ud.prog3.pr02;

import java.util.Date;
import java.util.Random;

public class EstrellaJuego {

	private JLabelEstrella miGrafico;  // Etiqueta gráfica del coche
	private int posX;
	private int posY;
	//HORA CREACION DE ESTRELLA --> jakiteko noiz sortu deun izarra, atributo berri bat jarri. Sortzeanen zero jarri, eta 40 milisegundoko gehitzen jun (setTiempo)
		private Date fechaCreada;
	
	
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

	public Date getfechaCreada() {
		return fechaCreada;
	}

	public void setfechaCreada(Date fechaCreada) {
		this.fechaCreada = fechaCreada;
	}
	
	// giro
			private double miGiro = Math.PI/2;
			/** Cambia el giro del JLabel
			 * @param gradosGiro	Grados a los que tiene que "apuntar" el coche,
			 * 						considerados con el 0 en el eje OX positivo,
			 * 						positivo en sentido antihorario, negativo horario.
			 */
			public void setGiro( double gradosGiro ) {
				// De grados a radianes...
				miGiro = gradosGiro/180*Math.PI;
				// El giro en la pantalla es en sentido horario (inverso):
				miGiro = -miGiro;  // Cambio el sentido del giro
				// Y el gráfico del coche apunta hacia arriba (en lugar de derecha OX)
				miGiro = miGiro + Math.PI/2; // Sumo 90º para que corresponda al origen OX
			}

}
