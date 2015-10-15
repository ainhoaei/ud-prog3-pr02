package ud.prog3.pr02;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import javax.swing.*;

/** Clase principal de minijuego de coche para Práctica 02 - Prog III
 * Ventana del minijuego.
 * @author Andoni Eguíluz
 * Facultad de Ingeniería - Universidad de Deusto (2014)
 */
public class VentanaJuego extends JFrame {
	private static final long serialVersionUID = 1L;  // Para serialización
	JPanel pPrincipal;         // Panel del juego (layout nulo)
	MundoJuego miMundo;        // Mundo del juego
	static CocheJuego miCoche;        // Coche del juego
	MiRunnable miHilo = null;  // Hilo del bucle principal de juego	
	private static boolean[] pulsaciones;
	JLabelEstrella estrella;
	EstrellaJuego miEstrella;
	JLabel mensaje;

	/** Constructor de la ventana de juego. Crea y devuelve la ventana inicializada
	 * sin coches dentro
	 */
	public VentanaJuego() {
		
		//JLabelEstrella estrella = new JLabelEstrella();
		pulsaciones = new boolean[4];
		estrella = new JLabelEstrella();
		mensaje = new JLabel();
		
		
		// Liberación de la ventana por defecto al cerrar
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		// Creación contenedores y componentes
		pPrincipal = new JPanel();
//		JPanel pBotonera = new JPanel();
//		JButton bAcelerar = new JButton( "Acelera" );
//		JButton bFrenar = new JButton( "Frena" );
//		JButton bGiraIzq = new JButton( "Gira Izq." );
//		JButton bGiraDer = new JButton( "Gira Der." );
		// Formato y layouts
		pPrincipal.setLayout( null );
		pPrincipal.setBackground( Color.white );
		// Añadido de componentes a contenedores
		add( pPrincipal, BorderLayout.CENTER );
//		pBotonera.add( bAcelerar );
//		pBotonera.add( bFrenar );
//		pBotonera.add( bGiraIzq );
//		pBotonera.add( bGiraDer );
		add( mensaje, BorderLayout.SOUTH );
		// Formato de ventana
		setSize( 1000, 750 );
		setResizable( false );
		// Escuchadores de botones
//		bAcelerar.addActionListener( new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				miCoche.acelera( +10, 1 );
//				// System.out.println( "Nueva velocidad de coche: " + miCoche.getVelocidad() );
//			}
//		});
//		bFrenar.addActionListener( new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				miCoche.acelera( -10, 1 );
//				// System.out.println( "Nueva velocidad de coche: " + miCoche.getVelocidad() );
//			}
//		});
//		bGiraIzq.addActionListener( new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				miCoche.gira( +10 );
//				// System.out.println( "Nueva dirección de coche: " + miCoche.getDireccionActual() );
//			}
//		});
//		bGiraDer.addActionListener( new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				miCoche.gira( -10 );
//				// System.out.println( "Nueva dirección de coche: " + miCoche.getDireccionActual() );
//			}
//		});
		
		// Añadido para que también se gestione por teclado con el KeyListener
		pPrincipal.addKeyListener( new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
					case KeyEvent.VK_UP: {
						//miCoche.acelera( +5, 1 );
						pulsaciones[0] = true;
						break;
					}
					case KeyEvent.VK_DOWN: {
						//miCoche.acelera( -5, 1 );
						pulsaciones[1] = true;
						break;
					}
					case KeyEvent.VK_LEFT: {
						//miCoche.gira( +10 );
						pulsaciones[2] = true;
						break;
					}
					case KeyEvent.VK_RIGHT: {
						//miCoche.gira( -10 );
						pulsaciones[3] = true;
						break;
					}
				}	
				
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
					case KeyEvent.VK_UP: {
						pulsaciones[0] = false;
						break;
					}
					case KeyEvent.VK_DOWN: {
						pulsaciones[1] = false;
						break;
					}
					case KeyEvent.VK_LEFT: {
						pulsaciones[2] = false;
						break;
					}
					case KeyEvent.VK_RIGHT: {
						pulsaciones[3] = false;
						break;
					}
				}
			}
				
		});
		pPrincipal.setFocusable(true);
		pPrincipal.requestFocus();
		pPrincipal.addFocusListener( new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				pPrincipal.requestFocus();
			}
		});
		// Cierre del hilo al cierre de la ventana
		addWindowListener( new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (miHilo!=null) miHilo.acaba();
			}
		});
	}
	
	
	/** Programa principal de la ventana de juego
	 * @param args
	 */
	public static void main(String[] args) {
		// Crea y visibiliza la ventana con el coche
	
		
		try {
			final VentanaJuego miVentana = new VentanaJuego();
			SwingUtilities.invokeAndWait( new Runnable() {
				@Override
				public void run() {
					miVentana.setVisible( true );
				}
			});
			// Crea el coche
			miVentana.miMundo = new MundoJuego( miVentana.pPrincipal );
			miVentana.miMundo.creaCoche( 150, 100 );
			miVentana.miCoche = miVentana.miMundo.getCoche();
			miVentana.miCoche.setPiloto( "Fernando Alonso" );
			
			// Crea la estrella
			miVentana.miEstrella = miVentana.miMundo.getEstrella();
			
			// Crea el hilo de movimiento del coche y lo lanza
			miVentana.miHilo = miVentana.new MiRunnable();  // Sintaxis de new para clase interna
			Thread nuevoHilo = new Thread( miVentana.miHilo );
			nuevoHilo.start();
			
		} catch (Exception e) {
			System.exit(1);  // Error anormal
		}
	}
	
	/** Clase interna para implementación de bucle principal del juego como un hilo
	 * @author Andoni Eguíluz
	 * Facultad de Ingeniería - Universidad de Deusto (2014)
	 */
	class MiRunnable implements Runnable {
		boolean sigo = true;
		
		@Override
		public void run() {
			// Bucle principal forever hasta que se pare el juego...
			double segundos = 0.0;
			long maxTiempo = 0;
			
			while (sigo) {
				
				// Chequear choques
				// (se comprueba tanto X como Y porque podría a la vez chocar en las dos direcciones (esquinas)
				if (miMundo.hayChoqueHorizontal(miCoche)) // Espejo horizontal si choca en X
					miMundo.rebotaHorizontal(miCoche);
				if (miMundo.hayChoqueVertical(miCoche)) // Espejo vertical si choca en Y
					miMundo.rebotaVertical(miCoche);
				
				//CURSORES TECLADO
				// Hau egin aurretik, botoiai eman eta segundu batzuk geldi egoten zen, ez zen segidon eta settun bueltaka hasten. 
				// Hau eginda ez da geratzen, zuzenen bueltaka hasten de milisegundo horiek geldirik egon gabe.
					if(pulsaciones[0] == true){
						miCoche.acelera( +5, 1 );
						//llamar fuerza aceleracion adelante
						double fuerza = miCoche.fuerzaAceleracionAdelante();
						//rozamiento --> fuerza total  --> Esto hace en el MundoJuego junto con aplicarFuerza
						//aplicamos la fuerza total al coche --> fuerza de la aceleracion + rozamiento
						miMundo.aplicarFuerza(fuerza, miCoche);
					}
					if(pulsaciones[1] == true){
						miCoche.acelera( -5, 1 );
						double fuerza = miCoche.FuerzaAceleraionAtras();
						miMundo.aplicarFuerza(fuerza, miCoche);
					}
					else if (pulsaciones[0] == false && pulsaciones[1] == false){
						double fuerzaRozamiento = miMundo.calcFuerzaRozamiento(miCoche.getMasa(), miCoche.getCoefSuelo(), miCoche.getCoefAire(), miCoche.getVelocidad());
						miMundo.aplicarFuerza(fuerzaRozamiento, miCoche);
					}
					
					if(pulsaciones[2] == true){
						miCoche.gira( +10 );
					}
					if(pulsaciones[3] == true){
						miCoche.gira( -10 );
					}
					
					// Mover coche
					miCoche.mueve( 0.040 );
				
				if(segundos >= 1.2){
					miMundo.creaEstrella(this.posX(), this.posY());
					segundos = 0.0;
			
				}else{
					segundos = segundos + 0.040;
				}
				
				//Que cada 6 segundos se quite la estrella
				int estrellaQuitada = miMundo.quitaYRotaEstrellas(6000);
				
				int estrellasAtrapadas = miMundo.choquesConEstrellas();		
				
				int puntos = estrellasAtrapadas*5;

				mensaje.setText( "      Comidas :                 " + estrellasAtrapadas + "                           Puntuación :               " + puntos +"                           Perdidas :               " + estrellaQuitada );
				
				if(estrellaQuitada>=10){
					acaba();
					//Cerrar el panel principal
					pPrincipal.setVisible(false);
					
					//Cerrar la ventana
					System.exit(0); 
				}
				
				// Dormir el hilo 40 milisegundos
				try {
					Thread.sleep( 40 );
				} catch (Exception e) {
				}
			
			}
		}
		
		public int posX(){
			int PosRandomX = new Random().nextInt(1000);
			return PosRandomX;
		}
		
		public int posY(){
			int PosRandomY = new Random().nextInt(750);
			return PosRandomY;
		}
		
		
		
		/** Calcula si hay choques del coche con alguna estrella (o varias). Se considera el choque si
		* se tocan las esferas lógicas del coche y la estrella. Si es así, las elimina.
		* @return Número de estrellas eliminadas
		*/
	//	public int choquesConEstrellas(JLABERESTRELLA ESTRELLA){
			
			//???????????????????????
			
			//Mirar posicion
			//ESTRELLA.GETX + TAMAÑOESTRELLA/2 - KOTXE.GETX-TAMAÑOCOCHE/2   == DISTANCIAX
			//DINTANCIA Y ==
			//DISTANCIA == RAIZ CUADRADA DE LAS DOS DISTANCIAS ELEVADAS A DOS, MATH.SQRT
			//RETURN (DISTA <= RADIOESFERACOCHE + RADIOESDERAESTRELLA)
		
		
	//	}
		
		
		/** Ordena al hilo detenerse en cuanto sea posible
		 */
		public void acaba() {
			sigo = false;
		}
	};
	
}
