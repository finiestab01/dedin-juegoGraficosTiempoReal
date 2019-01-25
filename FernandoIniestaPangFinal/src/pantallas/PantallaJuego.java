package pantallas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import base.PanelJuego;
import base.Pantalla;
import base.Sprite;

public class PantallaJuego implements Pantalla{
	private static final int ANCHO_GRANPOMPA = 80;
	private static final int ANCHO_MEDIOPOMPA = 64;
	private static final int ANCHO_PEQUEPOMPA = 32;
	private static final int ANCHO_MINIPOMPA = 16;
	private static final int ANCHO_PERSONAJE = 50;
	private static final int ANCHO_DISPARO = 10;
	private static final int VELOCIDAD_DISPARO = -10;
	private static final int VELOCIDAD_GRANPOMPA = 5;
	private static final int VELOCIDAD_MEDIOPOMPA = 6;
	private static final int VELOCIDAD_PEQUEPOMPA = 7;
	private static final int VELOCIDAD_MINIPOMPA = 8;
	private static final Color COLOR_PUNTUACION = Color.YELLOW;
	private static final Color COLOR_DISPARO = Color.CYAN;
	static final int ANIMACION_COMILLAS = 5;
	static final int ANIMACION_EXPLOTA = 11;

	PanelJuego panelJuego;

	// Array que almacena todos los cuadrados que se moverÃ¡n por pantalla.
	ArrayList<Sprite> pompas;
	BufferedImage imagenOriginal;
	Image imagenReescalada;

	Sprite player;
	Sprite disparo;
	Sprite player2;
	Sprite disparo2;

	// Variables para el contador de tiempo
	double tiempoInicial;
	double tiempoDeJuego;
	private DecimalFormat formatoDecimal; // Formatea la salida.
	Font fuenteTiempo;
	
	Sprite bloque = null;
	Sprite explo = null;
	Sprite buffoEngancho = null;
	Sprite buffoPistola = null;

	Random powerUp = new Random();
	int vidaBloque = 3;
	int contadorFrames = 0;
	boolean anima = false;
	boolean animaBlock = false;
	boolean pistola = false;
	boolean pistola2 = false;

	public PantallaJuego(PanelJuego panelJuego) {
		this.panelJuego = panelJuego;
		tiempoInicial = System.nanoTime();
	}
	/**
	 * Método para iniciar los sprites que habrá en la pantalla y el fondo
	 */
	@Override
	public void inicializarPantalla() {
		pompas = new ArrayList<Sprite>();
		// Inicializo el array con tantas pompas random como nivel sea:
		for (int i = 0; i < 1 * panelJuego.getDificultad(); i++) {
			Sprite creador;
			int posX = 10;
			int poxY = 10;
			if (pompas.size() > 0) {
				posX = (pompas.get(i - 1).getPosX()) + 130;
				poxY = 10;
			}
			int velocidadX = VELOCIDAD_GRANPOMPA - 2;
			int velocidadY = VELOCIDAD_GRANPOMPA;
			creador = new Sprite(ANCHO_GRANPOMPA, ANCHO_GRANPOMPA, posX, poxY, velocidadX, velocidadY,
					"Sprites/baloon1.png");
			pompas.add(creador);
		}
		// Switch para elegir imagen según el nivel
		try {
			switch (panelJuego.getDificultad()) {
			case 1:
				imagenOriginal = ImageIO.read(new File("Sprites/stage1.jpg"));
				break;
			case 2:
				imagenOriginal = ImageIO.read(new File("Sprites/stage2.jpg"));
				bloque = new Sprite(128, 32, (panelJuego.getWidth()/2)-64, panelJuego.getHeight() - 150, "Sprites/bloque_completo.png");
				break;
			case 3:
				imagenOriginal = ImageIO.read(new File("Sprites/stage3.jpg"));
				break;
			case 4:
				imagenOriginal = ImageIO.read(new File("Sprites/stage4.jpg"));
				bloque = new Sprite(128, 32, (panelJuego.getWidth()/2)-64, panelJuego.getHeight() - 150, "Sprites/bloque_completo.png");
				break;

			default:
				imagenOriginal = ImageIO.read(new File("Sprites/sunset_Homer_Simpson-75856.jpg"));
				break;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		if(panelJuego.getJugadores()==2) {
			player2 = new Sprite(ANCHO_PERSONAJE, ANCHO_PERSONAJE, panelJuego.getWidth()/2+100, panelJuego.getHeight() - 51, "Sprites/parado2.png");

		}
		player = new Sprite(ANCHO_PERSONAJE, ANCHO_PERSONAJE, panelJuego.getWidth()/2-100, panelJuego.getHeight() - 51, "Sprites/parado.png");

		fuenteTiempo = new Font("Arial", Font.BOLD, 20);

		tiempoInicial = System.nanoTime();
		tiempoDeJuego = 0;
		formatoDecimal = new DecimalFormat("#.##");
		reescalarImagen();
	}

	/**
	 * Método para iniciar los graficos en pantalla
	 */
	@Override
	public void pintarPantalla(Graphics g) {
		rellenarFondo(g);
		// Pintamos los sprites:
		for (Sprite cuadrado : pompas) {
			cuadrado.pintarSpriteEnMundo(g);
		}
		if (disparo != null) {
			disparo.pintarSpriteEnMundo(g);
		}
		if (disparo2 != null) {
			disparo2.pintarSpriteEnMundo(g);
		}
		if (buffoEngancho != null) {
			buffoEngancho.pintarSpriteEnMundo(g);
		}
		if (buffoPistola != null) {
			buffoPistola.pintarSpriteEnMundo(g);
		}

		if (explo != null) {
			explo.pintarSpriteEnMundo(g);
		}
		if (bloque != null) {
			bloque.pintarSpriteEnMundo(g);
		}
		if(player2!=null) {
			player2.pintarSpriteEnMundo(g);
		}
		player.pintarSpriteEnMundo(g);
		pintarTiempo(g);

	}

	/**
	 * Método para generar un cronómetro y pintarlo por pantalla
	 * 
	 * @param g
	 */
	private void pintarTiempo(Graphics g) {
		Font f = g.getFont();
		Color c = g.getColor();

		g.setColor(COLOR_PUNTUACION);
		g.setFont(fuenteTiempo);
		actualizarTiempo();
		g.drawString(formatoDecimal.format(tiempoDeJuego / 1000000000d), 25, 25);

		g.setColor(Color.RED);
		g.drawString("P1: " + panelJuego.getPuntuacion(), panelJuego.getWidth() - 80, 25);
		g.setColor(Color.CYAN);
		g.drawString("P2: " + panelJuego.getPuntuacion2(), panelJuego.getWidth() - 150, 25);
		g.setColor(Color.GREEN);
		g.drawString("Nv: " + panelJuego.getDificultad(), panelJuego.getWidth() - 220, 25);
		g.setFont(f);
	}

	/**
	 * Método que actualiza el tiempo de juego transcurrido.
	 */
	private void actualizarTiempo() {
		tiempoDeJuego = System.nanoTime() - tiempoInicial;
	}

	/**
	 * Método que se utiliza para rellenar el fondo del JPanel.
	 * 
	 * @param g Es el gráfico sobre el que vamos a pintar el fondo.
	 */
	private void rellenarFondo(Graphics g) {
		// Pintar la imagen de fondo reescalada:
		g.drawImage(imagenReescalada, 0, 0, null);
	}

	/**
	 * Método para mover todos los Sprites del juego. Si es una pompa va más rápido
	 * hacia abajo que hacia arriba
	 */
	private void moverSprites() {
		for (int i = 0; i < pompas.size(); i++) {
			Sprite aux = pompas.get(i);
			if (aux.getVelocidadY() < 0 && aux.getPosY() >= panelJuego.getHeight() - (aux.getAlto() + 3)) {
				pompas.get(i).setVelocidadY(-3);
			}
			if (aux.getVelocidadY() > 0 && aux.getPosY() <= panelJuego.getHeight() + 110) {
				pompas.get(i).setVelocidadY(VELOCIDAD_GRANPOMPA);
			}
			aux.moverSprite(panelJuego.getWidth(), panelJuego.getHeight());
		}
		if (disparo != null) {
			disparo.moverSprite();
			if (disparo.getPosY() + disparo.getAlto() <= 0) {
				disparo = null;
			}
		}
		if (disparo2 != null) {
			disparo2.moverSprite();
			if (disparo2.getPosY() + disparo2.getAlto() <= 0) {
				disparo2 = null;
			}
		}
		if (buffoEngancho != null) {
			buffoEngancho.moverSprite();
			if (buffoEngancho.getPosY() >= panelJuego.getHeight() - 31) {// por abajo
				buffoEngancho.setVelocidadY(0);
			}
		}
		if (buffoPistola != null) {
			buffoPistola.moverSprite();
			if (buffoPistola.getPosY() >= panelJuego.getHeight() - 31) {
				buffoPistola.setVelocidadY(0);
			}
		}
	}

	/**
	 * Método para comprobar colisiones entre los sprites.
	 * Si es una pompa genera más 2 pompas si colisiona con el disparo.
	 * También controla el tipo de disparo al colisionar con el límite superior
	 */
	private void comprobarColisiones() {
		boolean colision=false;
		// Comprobar colisiones con el disparo
		for (int i = 0; i < pompas.size(); i++) {
			if(disparo!=null && pompas.get(i).colisionan(disparo)) {
				disparo = null;
				panelJuego.setPuntuacion(panelJuego.getPuntuacion() + 1);
				colision=true;
			}else if(disparo2!=null && pompas.get(i).colisionan(disparo2)) {
				disparo2 = null;
				panelJuego.setPuntuacion2(panelJuego.getPuntuacion2() + 1);
				colision=true;
			}
			if (colision) {
				explo = new Sprite(pompas.get(i).getAncho(), pompas.get(i).getAlto(), pompas.get(i).getPosX(),
						pompas.get(i).getPosY(), "Sprites/explota1.png");
				
				switch (pompas.get(i).getAncho()) {
				//La pompa grande genera dos pompas medianas
				case ANCHO_GRANPOMPA:
					pompas.add(new Sprite(ANCHO_MEDIOPOMPA, ANCHO_MEDIOPOMPA, pompas.get(i).getPosX(),
							pompas.get(i).getPosY(), -VELOCIDAD_MEDIOPOMPA, VELOCIDAD_MEDIOPOMPA,
							"Sprites/baloon2.png"));
					pompas.add(new Sprite(ANCHO_MEDIOPOMPA, ANCHO_MEDIOPOMPA, pompas.get(i).getPosX(),
							pompas.get(i).getPosY(), VELOCIDAD_MEDIOPOMPA, VELOCIDAD_MEDIOPOMPA,
							"Sprites/baloon2.png"));
					int prob = powerUp.nextInt(3);
					if (prob == 0 || prob == 1) {
						if (prob == 1) {
							buffoEngancho = new Sprite(30, 30, pompas.get(i).getPosX() + 10,
									pompas.get(i).getPosY() + 10, 0, 4, "Sprites/buffo_gancho.png");
						} else {
							buffoPistola = new Sprite(30, 30, pompas.get(i).getPosX() + 10,
									pompas.get(i).getPosY() + 10, 0, 4, "Sprites/pistol01.png");
						}
					}
					break;
				//La pompa mediana genera dos pompas pequeñas	
				case ANCHO_MEDIOPOMPA:
					pompas.add(new Sprite(ANCHO_PEQUEPOMPA, ANCHO_PEQUEPOMPA, pompas.get(i).getPosX(),
							pompas.get(i).getPosY(), -VELOCIDAD_PEQUEPOMPA, VELOCIDAD_PEQUEPOMPA,
							"Sprites/baloon2.png"));
					pompas.add(new Sprite(ANCHO_PEQUEPOMPA, ANCHO_PEQUEPOMPA, pompas.get(i).getPosX(),
							pompas.get(i).getPosY(), VELOCIDAD_PEQUEPOMPA, VELOCIDAD_PEQUEPOMPA,
							"Sprites/baloon2.png"));
					int proba = powerUp.nextInt(3);
					if (proba == 0 || proba == 1) {
						if (proba == 1) {
							buffoEngancho = new Sprite(30, 30, pompas.get(i).getPosX() + 10,
									pompas.get(i).getPosY() + 10, 0, 4, "Sprites/buffo_gancho.png");
						} else {
							buffoPistola = new Sprite(30, 30, pompas.get(i).getPosX() + 10,
									pompas.get(i).getPosY() + 10, 0, 4, "Sprites/pistol01.png");
						}
					}
					break;
				//La pompa pequeña genera dos pompas minis	
				case ANCHO_PEQUEPOMPA:
					pompas.add(new Sprite(ANCHO_MINIPOMPA, ANCHO_MINIPOMPA, pompas.get(i).getPosX(),
							pompas.get(i).getPosY(), -VELOCIDAD_MINIPOMPA, VELOCIDAD_MINIPOMPA, "Sprites/baloon2.png"));
					pompas.add(new Sprite(ANCHO_MINIPOMPA, ANCHO_MINIPOMPA, pompas.get(i).getPosX(),
							pompas.get(i).getPosY(), VELOCIDAD_MINIPOMPA, VELOCIDAD_MINIPOMPA, "Sprites/baloon2.png"));
					break;
				default:
					break;
				}
				pompas.remove(i);
			}
			colision=false;
		}
		disparos();
		
		//Llama a pantalla ganar si has destruido todas las pompas
		if (pompas.size() == 0) {
			PantallaGanar pantallaGanar = new PantallaGanar(panelJuego, imagenOriginal);
			pantallaGanar.inicializarPantalla();
			panelJuego.setPantallaActual(pantallaGanar);
		}
		//Si el jugador colisiona con una pompa llama a GAME OVER
		for (int i = 0; i < pompas.size(); i++) {
			if (pompas.get(i).colisionan(player)) {
				PantallaGameOver pantallaGameOver = new PantallaGameOver(panelJuego, imagenOriginal);
				pantallaGameOver.inicializarPantalla();
				panelJuego.setPantallaActual(pantallaGameOver);
			}
			if(player2!=null) {
			if (pompas.get(i).colisionan(player2)) {
				PantallaGameOver pantallaGameOver = new PantallaGameOver(panelJuego, imagenOriginal);
				pantallaGameOver.inicializarPantalla();
				panelJuego.setPantallaActual(pantallaGameOver);
			}
			}
		}
		//Control para que la pompa "rebote" al tocar el bloque
		for (int i = 0; i < pompas.size(); i++) {
			if (!animaBlock) {
				if (bloque != null && pompas.get(i).colisionan(bloque) && vidaBloque > 0 && pompas.get(i).getPosY()<bloque.getPosY()) {
					pompas.get(i).setVelocidadY(-1 * Math.abs(pompas.get(i).getVelocidadY()));
				}
				if (bloque != null && pompas.get(i).colisionan(bloque) && vidaBloque > 0 && pompas.get(i).getPosY()+15>bloque.getPosY()+bloque.getAlto()) {
					pompas.get(i).setVelocidadY(Math.abs(pompas.get(i).getVelocidadY()));
				}
				
			}
		}
		//Control del tipo de disparo y "animación" del bloque
		if (bloque != null && disparo != null && vidaBloque > 0 && disparo.colisionan(bloque)) {
			if (player.getDisparos() != 2) {
				disparo = null;
				vidaBloque--;
			} else {
				disparo.setVelocidadY(0);
				disparo.actualizarBuffer("Sprites/gancho3.png");
			}
			switch (vidaBloque) {
			case 2:
				bloque.actualizarBuffer("Sprites/bloque_medio.png");
				break;
			case 1:
				bloque.actualizarBuffer("Sprites/bloque_destru.png");
				break;
			case 0:
				bloque.actualizarBuffer("Sprites/bloque_desapa1.png");
				break;

			default:
				break;
			}
		}
		if (bloque != null && disparo2 != null && vidaBloque > 0 && disparo2.colisionan(bloque)) {
			if (player2.getDisparos() != 2) {
				disparo2 = null;
				vidaBloque--;
			} else {
				disparo2.setVelocidadY(0);
				disparo2.actualizarBuffer("Sprites/gancho3.png");
			}
			switch (vidaBloque) {
			case 2:
				bloque.actualizarBuffer("Sprites/bloque_medio.png");
				break;
			case 1:
				bloque.actualizarBuffer("Sprites/bloque_destru.png");
				break;
			case 0:
				bloque.actualizarBuffer("Sprites/bloque_desapa1.png");
				break;
				
			default:
				break;
			}
		}
	}
	public void disparos() {
		//Comportamiento del disparo al colisionar con el límite superior
				switch (player.getDisparos()) {
				case 1:// gancho básico
					if (disparo != null && disparo.getPosY() < 0) {
						disparo = null;
					}
					break;
				case 2:// gancho que engancha
						// POWER UP
					if (disparo != null && disparo.getPosY() < 1) {
						disparo.setVelocidadY(0);
						disparo.actualizarBuffer("Sprites/gancho3.png");
					}
					break;
				default:
					if (disparo != null && disparo.getPosY() < 0) {
						disparo = null;
					}
					break;
				}
				//Controla cuando se coge el booster del gancho
				if (buffoEngancho != null && buffoEngancho.colisionan(player)) {
					buffoEngancho = null;
					player.setDisparos(2);
					pistola = false;
				}
				//Controla cuando se coge el booster de la pistola
				if (buffoPistola != null && buffoPistola.colisionan(player)) {
					buffoPistola = null;
					player.setDisparos(1);
					pistola = true;
				}
				if(player2!=null) {
				//Comportamiento del disparo al colisionar con el límite superior
				switch (player2.getDisparos()) {
				case 1:// gancho básico
					if (disparo2 != null && disparo2.getPosY() < 0) {
						disparo2 = null;
					}
					break;
				case 2:// gancho que engancha
					// POWER UP
					if (disparo2 != null && disparo2.getPosY() < 1) {
						disparo2.setVelocidadY(0);
						disparo2.actualizarBuffer("Sprites/gancho3.png");
					}
					break;
				default:
					if (disparo2 != null && disparo2.getPosY() < 0) {
						disparo2 = null;
					}
					break;
				}
				//Controla cuando se coge el booster del gancho
				if (buffoEngancho != null && buffoEngancho.colisionan(player2)) {
					buffoEngancho = null;
					player2.setDisparos(2);
					pistola2 = false;
				}
				//Controla cuando se coge el booster de la pistola
				if (buffoPistola != null && buffoPistola.colisionan(player2)) {
					buffoPistola = null;
					player2.setDisparos(1);
					pistola2 = true;
				}
				}
	}
	/**
	 * Método que ejecuta cada frame, llama a comprobarColisiones y moverSprites.
	 * También hace la "animación" de cuando un sprite se destruye
	 */
	@Override
	public void ejecutarFrame() {
		contadorFrames++;
		if (contadorFrames % ANIMACION_EXPLOTA == 0 && explo != null) {
			explo.actualizarBuffer("Sprites/explota2.png");
			if (anima) {
				explo = null;
			}
			anima = true;
		}
		if (contadorFrames % ANIMACION_EXPLOTA == 0 && bloque != null && vidaBloque == 0) {
			bloque.actualizarBuffer("Sprites/bloque_desapa2.png");
			if (animaBlock) {
				bloque = null;
			}
			animaBlock = true;
		}
		comprobarColisiones();
		moverSprites();

	}
	@Override
	public void moverRaton(MouseEvent e) {

	}
	/**
	 * Método en que el personaje dispara
	 */
	@Override
	public void pulsarRaton(MouseEvent e) {
			player.actualizarBuffer("Sprites/parado.png");
			if (disparo == null && !pistola) {
				disparo = new Sprite(ANCHO_DISPARO, panelJuego.getHeight(), player.getPosX() + 15,
						panelJuego.getHeight() + 1, 0, VELOCIDAD_DISPARO, "Sprites/gancho1.png");
			}
			if (disparo == null && pistola) {
				disparo = new Sprite(10, 15, player.getPosX() + 15, player.getPosY() - 16, 0, -20, "");
			}
	}
	/**
	 * Método que controla la redimensión de imagen y la posición de los sprites en tal caso
	 */
	@Override
	public void redimensionarPantalla(ComponentEvent e) {
		if(bloque!=null) {
		bloque.setPosY(panelJuego.getHeight() - 150);
		bloque.setPosX((panelJuego.getWidth()/2)-64);
		}
		
		player.setPosY(panelJuego.getHeight() - 51);
		if(player2!=null) {
			player2.setPosY(panelJuego.getHeight() - 51);
		}
		reescalarImagen();

	}
	/**
	 * Método que reescala la imagen
	 */
	private void reescalarImagen() {
		imagenReescalada = imagenOriginal.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(),
				Image.SCALE_SMOOTH);
	}
	
	@Override
	public void pulsarTecla(KeyEvent e) {
		//Control del primer jugador
		if(panelJuego.isIzq()) {
			player.setPosX(player.getPosX()-11);
			if (player.getPosX() % 2 == 0) {
				player.actualizarBuffer("Sprites/izquierda1.png");
			} else {
				player.actualizarBuffer("Sprites/izquierda2.png");
			}
		}
		if(panelJuego.isDer()) {
			player.setPosX(player.getPosX()+11);
			if (player.getPosX() % 2 == 0) {
				player.actualizarBuffer("Sprites/derecha1.png");
			} else {
				player.actualizarBuffer("Sprites/derecha2.png");
			}
		}
		//Control de P2
		if(player2!=null) {
		if(panelJuego.isTeclaA()) {
			player2.setPosX(player2.getPosX()-11);
			if (player2.getPosX() % 2 == 0) {
				player2.actualizarBuffer("Sprites/izq1.png");
			} else {
				player2.actualizarBuffer("Sprites/izq2.png");
			}
		}
		if(panelJuego.isTeclaD()) {
			player2.setPosX(player2.getPosX()+11);
			if (player2.getPosX() % 2 == 0) {
				player2.actualizarBuffer("Sprites/der1.png");
			} else {
				player2.actualizarBuffer("Sprites/der2.png");
			}
		}
		//P2 dispara
		if(panelJuego.isSpace()) {
			player2.actualizarBuffer("Sprites/parado2.png");
			if (disparo2 == null && !pistola2) {
				disparo2 = new Sprite(ANCHO_DISPARO, panelJuego.getHeight(), player2.getPosX() + 15,
						panelJuego.getHeight() + 1, 0, VELOCIDAD_DISPARO, "Sprites/gancho1.png");
			}
			if (disparo2 == null && pistola2) {
				disparo2 = new Sprite(10, 15, player2.getPosX() + 15, player2.getPosY() - 16, 0, -20, "");
			}
		}
	}
	}
}
