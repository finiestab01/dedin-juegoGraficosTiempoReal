package base;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import pantallas.PantallaInicial;


/**
 * 
 * @author fernandoiniestabermejo
 * Clase PanelJuego.
 * Extiende de JPanel. Y llama a las pantallas en su turno
 * Implementa Runnable porque en el constructor se lanza un hilo que permite actualizar el Juego periódicamente.
 * Implementa MouseListener para que pueda capturar las pulsaciones del ratón.
 * Implementa MouseMotionListener para que pueda capturar los movimientos del ratón.
 */
public class PanelJuego extends JPanel implements Runnable, MouseListener, ComponentListener, MouseMotionListener,KeyListener {
	private static final long serialVersionUID = 1L;
	Pantalla pantallaActual;
	int puntuacion=0;
	int puntuacion2=0;
	int dificultad=1;
	int jugadores;
	boolean izq=false;
	boolean der=false;
	boolean space=false;
	boolean teclaA=false;
	boolean teclaD=false;
	/**
	 * Constructor de PanelJuego.
	 * - Asigna los listeners que implementan las propia pantallas.
	 * - Inicia un hilo para actualizar el juego periódicamente.
	 */
	public PanelJuego(){	

		this.addMouseListener(this);
		this.addComponentListener(this);
		this.setFocusable(true);
		this.addKeyListener(this);
		this.addMouseMotionListener(this);	
		new Thread(this).start();
		
		pantallaActual = new PantallaInicial(this);
		pantallaActual.inicializarPantalla();
		
      }

	
	public Pantalla getPantallaActual() {
		return pantallaActual;
	}


	public void setPantallaActual(Pantalla pantallaActual) {
		this.pantallaActual = pantallaActual;
	}


	/**
	 * Sobreescritura del método paintComponent. Este método se llama automáticamente cuando se inicia el componente,
	 *  se redimensiona o bien cuando se llama al método "repaint()". Nunca llamarlo directamente.
	 * @param g Es un Graphics que nos provee JPanel para poder pintar el componente a nuestro antojo.
	 */
	@Override
	public void paintComponent(Graphics g){
		pantallaActual.pintarPantalla(g);
	}

	@Override
	public void run() {
		while(true){
			repaint();
			try {	Thread.sleep(25);	} catch (InterruptedException e) {e.printStackTrace();}
			pantallaActual.ejecutarFrame();
			Toolkit.getDefaultToolkit().sync();
		}		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}


	@Override
	public void mousePressed(MouseEvent e) {
		pantallaActual.pulsarRaton(e);	
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void componentResized(ComponentEvent e) {
		pantallaActual.redimensionarPantalla(e);
	}


	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		pantallaActual.moverRaton(e);
	}


	public int getDificultad() {
		return dificultad;
	}


	public void setDificultad(int dificultad) {
		this.dificultad = dificultad;
	}


	public int getPuntuacion() {
		return puntuacion;
	}


	public void setPuntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}

	/**
	 * Evento que comprueba la tecla pulsada
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_LEFT) {
			izq=true;
		}
		if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
			der=true;
		}
		
		if(e.getKeyCode()==KeyEvent.VK_A) {
			teclaA=true;
		}
		if(e.getKeyCode()==KeyEvent.VK_D) {
			teclaD=true;
		}
		if(e.getKeyCode()==KeyEvent.VK_SPACE) {
			space=true;
		}
		pantallaActual.pulsarTecla(e);
		
	}

	/**
	 * Evento que comprueba la tecla despulsada
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		int id=e.getKeyCode();
		if(id==KeyEvent.VK_LEFT) {
			izq=false;
		}
		if(id==KeyEvent.VK_RIGHT) {
			der=false;
		}
		
		if(e.getKeyCode()==KeyEvent.VK_A) {
			teclaA=false;
		}
		if(e.getKeyCode()==KeyEvent.VK_D) {
			teclaD=false;
		}
		if(id==KeyEvent.VK_SPACE) {
			space=false;
		}
		pantallaActual.pulsarTecla(e);
		
		
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	public boolean isIzq() {
		return izq;
	}


	public void setIzq(boolean izq) {
		this.izq = izq;
	}


	public boolean isDer() {
		return der;
	}


	public void setDer(boolean der) {
		this.der = der;
	}


	public boolean isSpace() {
		return space;
	}


	public void setSpace(boolean space) {
		this.space = space;
	}


	public boolean isTeclaA() {
		return teclaA;
	}


	public void setTeclaA(boolean teclaA) {
		this.teclaA = teclaA;
	}


	public boolean isTeclaD() {
		return teclaD;
	}


	public void setTeclaD(boolean teclaD) {
		this.teclaD = teclaD;
	}


	public int getJugadores() {
		return jugadores;
	}


	public void setJugadores(int jugadores) {
		this.jugadores = jugadores;
	}


	public int getPuntuacion2() {
		return puntuacion2;
	}


	public void setPuntuacion2(int puntuacion2) {
		this.puntuacion2 = puntuacion2;
	}
	
	
   }










