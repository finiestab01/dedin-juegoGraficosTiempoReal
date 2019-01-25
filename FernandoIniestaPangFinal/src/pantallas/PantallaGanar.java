package pantallas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import base.PanelJuego;
import base.Pantalla;
import base.Sprite;

public class PantallaGanar extends JPanel implements Pantalla{

PanelJuego panelJuego;
	
	BufferedImage imagenOriginalWin;
	Image imagenReescaladaWin;
	Font fuenteInicial;
	Font fuenteBoton;
	//Inicio pantalla
	Color colorLetras = Color.YELLOW;
	int contadorColorFrames = 0;
	static final int CAMBIO_COLOR_INICIO = 11;
	Color colorButton = Color.RED;
	
	private Sprite cursor;
	
	int rodarContador=0;
	
	private boolean volver=false;
	private boolean salir=false;	
	Sprite bVolver;
	Sprite bSalir;
	
	Random rd=new Random();
	private static Color[] ColorArray = {Color.blue, Color.cyan,Color.green,Color.magenta, Color.orange, Color.pink, Color.red, Color.yellow};
	
	public PantallaGanar(PanelJuego panelJuego, BufferedImage imagenOriginal) {
		super();
		this.panelJuego = panelJuego;
		this.imagenOriginalWin=imagenOriginal;
		bVolver=new Sprite(80, 80, panelJuego.getWidth()-120,panelJuego.getHeight()-190, "Sprites/baloon1.png");		
		bSalir=new Sprite(80, 80, 40,panelJuego.getHeight()-190, "Sprites/baloon1.png");
		cursor=new Sprite(1, 1, panelJuego.getWidth()/2, panelJuego.getHeight()/2, "Imagenes/nave.png");
	}

	@Override
	public void inicializarPantalla() {
		fuenteInicial = new Font("Arial", Font.BOLD, 50); 
		fuenteBoton = new Font("Arial", Font.BOLD, 15); 
	}

	@Override
	public void pintarPantalla(Graphics g) {
		g.drawImage(imagenReescaladaWin, 0,0, null);
		g.setColor(colorLetras);
		g.setFont(fuenteInicial);
		g.drawString("STAGE CLEAR",panelJuego.getWidth()/2-170,panelJuego.getHeight()/2-75);
		bVolver.pintarSpriteEnMundo(g);
		bSalir.pintarSpriteEnMundo(g);
		
		g.setFont(fuenteBoton);
		g.setColor(colorButton);
		g.drawString("RENDIRSE",45,panelJuego.getHeight()-95);
		g.drawString("SIGUIENTE NIVEL",panelJuego.getWidth()-150,panelJuego.getHeight()-95);
		
		cursor.pintarSpriteEnMundo(g);
	}

	@Override
	public void ejecutarFrame() {
		rodarContador++;
		if(rodarContador==16) {
			rodarContador=0;
		}
		contadorColorFrames++;
		if(contadorColorFrames % CAMBIO_COLOR_INICIO == 0) {
			colorButton=ColorArray[rd.nextInt(ColorArray.length)];
			colorLetras=ColorArray[rd.nextInt(ColorArray.length)];
			//COLOCAR PUNTOS TOTALES POR STAGE
		}
		
	}

	@Override
	public void moverRaton(MouseEvent e) {
		cursor.setPosX(e.getX());
		cursor.setPosY(e.getY());
		if(bVolver.colisionan(cursor)) {
			volver=true;
		}else {
			volver=false;
		}
		if(bSalir.colisionan(cursor)) {
			salir=true;
		}else {
			salir=false;
		}		
	}

	@Override
	public void pulsarRaton(MouseEvent e) {
		if(salir) {
			System.exit(0);
		}
		if(volver) {
			panelJuego.setDificultad(panelJuego.getDificultad()+1);
			PantallaJuego pantallaJuego = new PantallaJuego(panelJuego);
			pantallaJuego.inicializarPantalla();
			panelJuego.setPantallaActual(pantallaJuego);
		}
	}

	@Override
	public void redimensionarPantalla(ComponentEvent e) {
		bVolver.setPosX(panelJuego.getWidth()-120);
		bVolver.setPosY(panelJuego.getHeight()-190);
		bSalir.setPosX(40);
		bSalir.setPosY(panelJuego.getHeight()-190);
		imagenReescaladaWin = imagenOriginalWin.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(), Image.SCALE_SMOOTH);
		
	}
	private void reescalarImagen() {
		//Pensar en cada caso particular
		imagenReescaladaWin = imagenOriginalWin.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(), Image.SCALE_SMOOTH);
	}

	@Override
	public void pulsarTecla(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
