package ud.prog3.pr02;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class JLabelEstrella extends JLabel{
	
	private static final long serialVersionUID = 1L;  // Para serializaci�n
	public static final int TAMANYO_ESTRELLA = 80;  // p�xels (igual ancho que algo)
	public static final int RADIO_ESFERA_ESTRELLA = 17;  // Radio en p�xels del bounding circle del coche (para choques)
	private Calendar horaCrear;
	
	
	/** Construye y devuelve el JLabel del coche con su gr�fico y tama�o
	 */
	public JLabelEstrella() {
		// Esto se har�a para acceder por sistema de ficheros
		// 		super( new ImageIcon( "bin/ud/prog3/pr00/coche.png" ) );
		// Esto se hace para acceder tanto por recurso (jar) como por fichero
		
		try {
			setIcon( new ImageIcon( JLabelCoche.class.getResource( "img/estrella.png" ).toURI().toURL() ) );
			horaCrear = new GregorianCalendar();
			long horaCrearEstrella= System.currentTimeMillis();
			
		} catch (Exception e) {
			System.err.println( "Error en carga de recurso: estrella.png no encontrado" );
			e.printStackTrace();
		}
		setBounds( 0, 0, TAMANYO_ESTRELLA, TAMANYO_ESTRELLA );
		// Esto ser�a �til cuando hay alg�n problema con el gr�fico: borde de color del JLabel
		// setBorder( BorderFactory.createLineBorder( Color.yellow, 4 ));
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
			// Y el gr�fico del coche apunta hacia arriba (en lugar de derecha OX)
			miGiro = miGiro + Math.PI/2; // Sumo 90� para que corresponda al origen OX
		}

}
