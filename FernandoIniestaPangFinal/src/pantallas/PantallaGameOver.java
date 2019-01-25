package pantallas;

import java.awt.Button;
import java.awt.Color;
import java.awt.Cursor;
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

import base.PanelJuego;
import base.Pantalla;
import base.Sprite;

public class PantallaGameOver implements Pantalla{

	PanelJuego panelJuego;
	
	BufferedImage imagenOriginalGameOver;
	Image imagenReescaladaGameOver;
	Font fuenteInicial;
	Font fuenteBoton;
	//Inicio pantalla
	Color colorLetras = Color.RED;
	int contadorColorFrames = 0;
	static final int CAMBIO_COLOR_INICIO = 11;
	Color colorButton = Color.RED;
	
	Sprite bVolver;
	Sprite bSalir;
	
	Random rd=new Random();
	private static Color[] ColorArray = {Color.blue, Color.cyan,Color.green,Color.magenta, Color.orange, Color.pink, Color.red, Color.yellow};
	
	public PantallaGameOver(PanelJuego panelJuego, BufferedImage imagenOriginal) {
		super();
		this.panelJuego = panelJuego;
		this.imagenOriginalGameOver=imagenOriginal;
		bVolver=new Sprite(80, 80, panelJuego.getWidth()-120,panelJuego.getHeight()-190, "Sprites/baloon1.png");		
		bSalir=new Sprite(80, 80, 40,panelJuego.getHeight()-190, "Sprites/baloon1.png");
	}

	@Override
	public void inicializarPantalla() {
		fuenteInicial = new Font("Arial", Font.BOLD, 50); 
		fuenteBoton = new Font("Arial", Font.BOLD, 15); 
	}

	@Override
	public void pintarPantalla(Graphics g) {
		g.drawImage(imagenReescaladaGameOver, 0,0, null);
		g.setColor(colorLetras);
		g.setFont(fuenteInicial);
		g.drawString("GAME OVER",panelJuego.getWidth()/2-145,panelJuego.getHeight()/2-15);
		g.setColor(Color.RED);
		g.drawString("P1: "+panelJuego.getPuntuacion(),panelJuego.getWidth()/2-80,panelJuego.getHeight()/2+30);
		g.setColor(Color.CYAN);
		g.drawString("P2: "+panelJuego.getPuntuacion2(),panelJuego.getWidth()/2-80,panelJuego.getHeight()/2+80);
		bVolver.pintarSpriteEnMundo(g);
		bSalir.pintarSpriteEnMundo(g);
		
		g.setFont(fuenteBoton);
		g.setColor(colorButton);
		g.drawString("RENDIRSE",45,panelJuego.getHeight()-95);
		g.drawString("VOLVER A JUGAR",panelJuego.getWidth()-150,panelJuego.getHeight()-95);
		
	}

	@Override
	public void ejecutarFrame() {
		contadorColorFrames++;
		if(contadorColorFrames % CAMBIO_COLOR_INICIO == 0) {
			colorButton=ColorArray[rd.nextInt(ColorArray.length)];
			colorLetras=ColorArray[rd.nextInt(ColorArray.length)];
		}
		
	}

	@Override
	public void moverRaton(MouseEvent e) {
				
	}

	@Override
	public void pulsarRaton(MouseEvent e) {
		if(e.getX()>panelJuego.getWidth()/2) {
			panelJuego.setDificultad(1);
			panelJuego.setPuntuacion(0);
			panelJuego.setPuntuacion2(0);
			PantallaJuego pantallaJuego = new PantallaJuego(panelJuego);
			pantallaJuego.inicializarPantalla();
			panelJuego.setPantallaActual(pantallaJuego);
		}else {
			System.exit(0);
		}
	}

	@Override
	public void redimensionarPantalla(ComponentEvent e) {
		bVolver.setPosX(panelJuego.getWidth()-120);
		bVolver.setPosY(panelJuego.getHeight()-190);
		bSalir.setPosX(40);
		bSalir.setPosY(panelJuego.getHeight()-190);
		imagenReescaladaGameOver = imagenOriginalGameOver.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(), Image.SCALE_SMOOTH);
		
	}
	private void reescalarImagen() {
		imagenReescaladaGameOver = imagenOriginalGameOver.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(), Image.SCALE_SMOOTH);
	}

	@Override
	public void pulsarTecla(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
