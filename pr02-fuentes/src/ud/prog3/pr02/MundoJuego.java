package ud.prog3.pr02;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import javax.swing.JPanel;

/** "Mundo" del juego del coche.
 * Incluye las f�sicas para el movimiento y los choques de objetos.
 * Representa un espacio 2D en el que se mueven el coche y los objetos de puntuaci�n.
 * @author Andoni Egu�luz Mor�n
 * Facultad de Ingenier�a - Universidad de Deusto
 */
public class MundoJuego {
	private JPanel panel;  // panel visual del juego
	CocheJuego miCoche;    // Coche del juego
	EstrellaJuego estrella;
	JLabelCoche coche;
	ArrayList<EstrellaJuego> estrellas = new ArrayList<EstrellaJuego>();
	int estrellasQuitadas = 0;
	int choques = 0;
	
	
	/** Construye un mundo de juego
	 * @param panel	Panel visual del juego
	 */
	public MundoJuego( JPanel panel ) {
		this.panel = panel;
	}

	
	//------------------------ COCHE ---------------------------------//
	/** Crea un coche nuevo y lo a�ade al mundo y al panel visual
	 * @param posX	Posici�n X de pixel del nuevo coche
	 * @param posY	Posici�n Y de p�xel del nuevo coche
	 */
	public void creaCoche( int posX, int posY ) {
		// Crear y a�adir el coche a la ventana
		miCoche = new CocheJuego();
		miCoche.setPosicion( posX, posY );
		panel.add( miCoche.getGrafico() );  // A�ade al panel visual
		miCoche.getGrafico().repaint();  // Refresca el dibujado del coche
	}
	
	/** Devuelve el coche del mundo
	 * @return	Coche en el mundo. Si no lo hay, devuelve null
	 */
	public CocheJuego getCoche() {
		return miCoche;
	}

	/** Calcula si hay choque en horizontal con los l�mites del mundo
	 * @param coche	Coche cuyo choque se comprueba con su posici�n actual
	 * @return	true si hay choque horizontal, false si no lo hay
	 */
	public boolean hayChoqueHorizontal( CocheJuego coche ) {
		return (coche.getPosX() < JLabelCoche.RADIO_ESFERA_COCHE-JLabelCoche.TAMANYO_COCHE/2 
				|| coche.getPosX()>panel.getWidth()-JLabelCoche.TAMANYO_COCHE/2-JLabelCoche.RADIO_ESFERA_COCHE );
	}
	
	/** Calcula si hay choque en vertical con los l�mites del mundo
	 * @param coche	Coche cuyo choque se comprueba con su posici�n actual
	 * @return	true si hay choque vertical, false si no lo hay
	 */
	public boolean hayChoqueVertical( CocheJuego coche ) {
		return (coche.getPosY() < JLabelCoche.RADIO_ESFERA_COCHE-JLabelCoche.TAMANYO_COCHE/2 
				|| coche.getPosY()>panel.getHeight()-JLabelCoche.TAMANYO_COCHE/2-JLabelCoche.RADIO_ESFERA_COCHE );
	}

	/** Realiza un rebote en horizontal del objeto de juego indicado
	 * @param coche	Objeto que rebota en horizontal
	 */
	public void rebotaHorizontal( CocheJuego coche ) {
		// System.out.println( "Choca X");
		double dir = coche.getDireccionActual();
		dir = 180-dir;   // Rebote espejo sobre OY (complementario de 180)
		if (dir < 0) dir = 360+dir;  // Correcci�n para mantenerlo en [0,360)
		coche.setDireccionActual( dir );
	}
	
	/** Realiza un rebote en vertical del objeto de juego indicado
	 * @param coche	Objeto que rebota en vertical
	 */
	public void rebotaVertical( CocheJuego coche ) {
		// System.out.println( "Choca Y");
		double dir = miCoche.getDireccionActual();
		dir = 360 - dir;  // Rebote espejo sobre OX (complementario de 360)
		miCoche.setDireccionActual( dir );
	}
	
	/** Calcula y devuelve la posici�n X de un movimiento
	 * @param vel    	Velocidad del movimiento (en p�xels por segundo)
	 * @param dir    	Direcci�n del movimiento en grados (0� = eje OX positivo. Sentido antihorario)
	 * @param tiempo	Tiempo del movimiento (en segundos)
	 * @return
	 */
	public static double calcMovtoX( double vel, double dir, double tiempo ) {
		return vel * Math.cos(dir/180.0*Math.PI) * tiempo;
	}
	
	/** Calcula y devuelve la posici�n X de un movimiento
	 * @param vel    	Velocidad del movimiento (en p�xels por segundo)
	 * @param dir    	Direcci�n del movimiento en grados (0� = eje OX positivo. Sentido antihorario)
	 * @param tiempo	Tiempo del movimiento (en segundos)
	 * @return
	 */
	public static double calcMovtoY( double vel, double dir, double tiempo ) {
		return vel * -Math.sin(dir/180.0*Math.PI) * tiempo;
		// el negativo es porque en pantalla la Y crece hacia abajo y no hacia arriba
	}
	
	/** Calcula el cambio de velocidad en funci�n de la aceleraci�n
	 * @param vel		Velocidad original
	 * @param acel		Aceleraci�n aplicada (puede ser negativa) en pixels/sg2
	 * @param tiempo	Tiempo transcurrido en segundos
	 * @return	Nueva velocidad
	 */
	public static double calcVelocidadConAceleracion( double vel, double acel, double tiempo ) {
		return vel + (acel*tiempo);
	}
	
	/**A�ade un m�todo est�tico en nuestro MundoJuego para calcular la fuerza de rozamiento partiendo de la
		masa, la velocidad y los coeficientes (seg�n la f�rmula).
	*/
	public static double calcFuerzaRozamiento (double masa, double coefRozSuelo, double coefRozAire, double vel){
		double fuerzaRozamientoAire = coefRozAire*(-vel); //En contra del movimiento
		double fuerzaRozamientoSuelo = masa*coefRozSuelo*((vel>0)?(-1):1); //contra movimiento --> ??????
		return fuerzaRozamientoAire + fuerzaRozamientoSuelo;
	}
	
	/**A�ade otro m�todo est�tico para partiendo de la fuerza se calcule la aceleraci�n:
	*/	
	public static double calcAceleracionConFuerza( double fuerza, double masa ) {
	// 2� ley de Newton: F = m*a ---> a = F/m
	return fuerza/masa;
	}
	 
	/**Por �ltimo en esta clase MundoJuego, a�ade un m�todo para poder aplicar la fuerza al coche. Observa c�mo
		si no hay fuerza externa, la �nica fuerza que se aplica es la de rozamiento hasta que el coche se para:
	*/
	public void aplicarFuerza( double fuerza, Coche coche ) {
		double fuerzaRozamiento = calcFuerzaRozamiento( coche.masa , coche.coefSuelo, coche.coefAire, coche.getVelocidad() );
		double aceleracion = calcAceleracionConFuerza( fuerza+fuerzaRozamiento, coche.masa );
		
		if (fuerza==0) {
		// No hay fuerza, solo se aplica el rozamiento
			double velAntigua = coche.getVelocidad();
			coche.acelera( aceleracion, 0.04 );
			if (velAntigua>=0 && coche.getVelocidad()<0 || velAntigua<=0 && coche.getVelocidad()>0) {
				coche.setVelocidad(0); // Si se est� frenando, se para (no anda al rev�s)
			}
		} else {
			coche.acelera( aceleracion, 0.04 );
		}
	}
	
	
	
	
	//------------------------ ESTRELLAS ---------------------------------//
	public void creaEstrella(int posX, int posY ) {
		// Crear y a�adir la estrella a la ventana
		Date fechaCreada = new Date();
		EstrellaJuego estrellaJuego = new EstrellaJuego();
		estrellas.add(estrellaJuego);
		estrellaJuego.setfechaCreada(fechaCreada);
		estrellaJuego.setPosisicion(posX, posY);
		panel.add( estrellaJuego.getGrafico() );  // A�ade al panel visual
		estrellaJuego.getGrafico().repaint();  // Refresca el dibujado del coche
		
	}
	
	public EstrellaJuego getEstrella() {
		return estrella;
	}
	
	/** Quita todas las estrellas que lleven en pantalla demasiado tiempo
	* y rota 10 grados las que sigan estando
	* @param maxTiempo Tiempo m�ximo para que se mantengan las estrellas (msegs)
	* @return N�mero de estrellas quitadas */
	public int quitaYRotaEstrellas(long maxTiempo ){
		
		JLabelEstrella labelEstrella = new JLabelEstrella();
		
		for(int i=0; i<estrellas.size(); i++){
			EstrellaJuego estrella = new EstrellaJuego();
			estrella = estrellas.get(i);
			
			//Fecha de creada de la estrella
			Date fechaCreada = estrella.getfechaCreada();
			long milisegundos = fechaCreada.getTime();
			
			//Fecha de ahora
			Date ahora = new Date();
			long milisegundosAhora = ahora.getTime();
			
			if((milisegundosAhora-milisegundos) >= maxTiempo){
				estrellas.remove(estrella);
				panel.remove(estrella.getGrafico());
				panel.repaint();
				estrellasQuitadas ++;
			}
			else{
				//Codificiaci�n de giro de la estrella 
				estrella.setGiro(10);
			}
		}
		
		return estrellasQuitadas;
	}
	
	/** Calcula si hay choques del coche con alguna estrella (o varias). Se considera el choque si
	* se tocan las esferas l�gicas del coche y la estrella. Si es as�, las elimina.
	* @return N�mero de estrellas eliminadas
	*/
	public int choquesConEstrellas (){
		
		//???????????????????????
		
		coche = new JLabelCoche();
		JLabelEstrella estre = new JLabelEstrella();
		EstrellaJuego estrellaJuego = new EstrellaJuego();

		for (int i=0; i < estrellas.size(); i++){
			double distX = estre.getX() + estre.TAMANYO_ESTRELLA/2 - coche.getX() - coche.TAMANYO_COCHE/2;
			double distY = estre.getY() + estre.TAMANYO_ESTRELLA/2 - coche.getY() - coche.TAMANYO_COCHE/2;
			double distancia = Math.sqrt((distX*distX)+(distY*distY));
			
			boolean egia = (distancia == coche.RADIO_ESFERA_COCHE + estre.RADIO_ESFERA_ESTRELLA);
			
			if(egia == true){
				estrellas.remove(i);
				panel.remove(estrellas.get(i).getGrafico());
				panel.repaint();
				
				choques++;
			}
		}
		return choques;
	}
	



	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}
	
	
}
