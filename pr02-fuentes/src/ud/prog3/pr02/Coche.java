package ud.prog3.pr02;

/** Clase para definir instancias l�gicas de coches con posici�n, direcci�n y velocidad.
 * @author Andoni Egu�luz
 * Facultad de Ingenier�a - Universidad de Deusto (2014)
 */
public class Coche {
	protected double miVelocidad;  // Velocidad en pixels/segundo
	protected double miDireccionActual;  // Direcci�n en la que estoy mirando en grados (de 0 a 360)
	protected double posX;  // Posici�n en X (horizontal)
	protected double posY;  // Posici�n en Y (vertical)
	protected String piloto;  // Nombre de piloto
	protected double masa; // en kg
	protected double coefSuelo;
	protected double coefAire;
	protected double coefRozamiento;
	protected double FuerzaAdelante; //Fuerza de rozamiento = masa*coefSuelo + velocidad*coefAire
	protected double FuerzaAtras;
	
	// Constructores
	
	public Coche() {
		miVelocidad = 0;
		miDireccionActual = 0;
		posX = 300;
		posY = 300;
		masa = 1.0;
		coefSuelo = 15.5;
		coefAire = 0.35;
	}
	
	/** Devuelve la velocidad actual del coche en p�xeles por segundo
	 * @return	velocidad
	 */
	public double getVelocidad() {
		return miVelocidad;
	}

	/** Cambia la velocidad actual del coche
	 * @param miVelocidad
	 */
	public void setVelocidad( double miVelocidad ) {
		this.miVelocidad = miVelocidad;
	}

	public double getDireccionActual() {
		return miDireccionActual;
	}

	public void setDireccionActual( double dir ) {
		// if (dir < 0) dir = 360 + dir;
		if (dir > 360) dir = dir - 360;
		miDireccionActual = dir;
	}

	public double getPosX() {
		return posX;
	}

	public double getPosY() {
		return posY;
	}

	public void setPosicion( double posX, double posY ) {
		setPosX( posX );
		setPosY( posY );
	}
	
	public void setPosX( double posX ) {
		this.posX = posX; 
	}
	
	public void setPosY( double posY ) {
		this.posY = posY; 
	}
	
	public String getPiloto() {
		return piloto;
	}

	public void setPiloto(String piloto) {
		this.piloto = piloto;
	}


	public double getCoefSuelo() {
		return coefSuelo;
	}

	public void setCoefSuelo(double coefSuelo) {
		this.coefSuelo = coefSuelo;
	}

	public double getCoefRozamiento() {
		return coefRozamiento;
	}

	public void setCoefRozamiento(double coefRozamiento) {
		this.coefRozamiento = coefRozamiento;
	}

	public double getMasa() {
		return masa;
	}

	public void setMasa(double masa) {
		this.masa = masa;
	}

	public double getCoefAire() {
		return coefAire;
	}

	public void setCoefAire(double coefAire) {
		this.coefAire = coefAire;
	}

	/** Cambia la velocidad actual del coche
	 * @param aceleracion	Incremento/decremento de la velocidad en pixels/segundo
	 * @param tiempo	Tiempo transcurrido en segundos
	 */
	public void acelera( double aceleracion, double tiempo ) {
		miVelocidad = MundoJuego.calcVelocidadConAceleracion( miVelocidad, aceleracion, tiempo );
	}
	
	/** Cambia la direcci�n actual del coche
	 * @param giro	Angulo de giro a sumar o restar de la direcci�n actual, en grados (-180 a +180)
	 * 				Considerando positivo giro antihorario, negativo giro horario
	 */
	public void gira( double giro ) {
		setDireccionActual( miDireccionActual + giro );
	}
	
	/** Cambia la posici�n del coche dependiendo de su velocidad y direcci�n
	 * @param tiempoDeMovimiento	Tiempo transcurrido, en segundos
	 */
	public void mueve( double tiempoDeMovimiento ) {
		setPosX( posX + MundoJuego.calcMovtoX( miVelocidad, miDireccionActual, tiempoDeMovimiento ) );
		setPosY( posY + MundoJuego.calcMovtoY( miVelocidad, miDireccionActual, tiempoDeMovimiento ) );
	}
	
	@Override
	public String toString() {
		return piloto + " (" + posX + "," + posY + ") - " +
			   "Velocidad: " + miVelocidad + " ## Direcci�n: " + miDireccionActual; 
	}
	
	/** Devuelve la fuerza de aceleraci�n del coche, de acuerdo al motor definido en la pr�ctica 2
	* @return Fuerza de aceleraci�n en Newtixels
	*/
	public double fuerzaAceleracionAdelante() {
		if (miVelocidad<=-150) 
			return FuerzaAdelante;

		else if (miVelocidad<=0)
			return FuerzaAdelante*(-miVelocidad/150*0.5+0.5);
		
		else if (miVelocidad<=250)
			return FuerzaAdelante*(miVelocidad/250*0.5+0.5);
		
		else if (miVelocidad<=750)
			return FuerzaAdelante;
		
		else 
			return FuerzaAdelante*(-(miVelocidad-1000)/250);
	}
	
	public double FuerzaAceleraionAtras (){
		if (miVelocidad>=-150) return FuerzaAtras;

		else if (miVelocidad>=0)
			return FuerzaAtras*(-miVelocidad/150*0.5+0.5);
		
		else if (miVelocidad>=250)
			return FuerzaAtras*(miVelocidad/250*0.5+0.5);
		
		else if (miVelocidad>=750)
			return FuerzaAtras;
		
		else return FuerzaAtras*(-(miVelocidad-1000)/250);
	}
}
