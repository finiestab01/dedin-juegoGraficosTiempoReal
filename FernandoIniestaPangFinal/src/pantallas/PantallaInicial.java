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

import javax.imageio.ImageIO;

import base.PanelJuego;
import base.Pantalla;

public class PantallaInicial implements Pantalla {

	PanelJuego panelJuego;
	
	BufferedImage imagenOriginalInicial;
	Image imagenReescaladaInicial;
	Font fuenteInicial;
	//Inicio pantalla
	Color colorLetras = Color.YELLOW;
	int contadorColorFrames = 0;
	static final int CAMBIO_COLOR_INICIO = 5;
	
	public PantallaInicial(PanelJuego panelJuego) {
		this.panelJuego = panelJuego;
	}
	
	
	@Override
	public void inicializarPantalla() {
		try {
			imagenOriginalInicial = ImageIO.read(new File("Sprites/logoPang.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		fuenteInicial = new Font("Arial", Font.BOLD, 50); 
	}

	@Override
	public void pintarPantalla(Graphics g) {
		g.drawImage(imagenReescaladaInicial, 0,0, null);
		g.setColor(Color.RED);
		g.setFont(fuenteInicial);
		g.drawString("1Jugador",20,70);
		g.setColor(Color.BLUE);
		g.drawString("2Jugadores",panelJuego.getWidth()-310,70);
	}

	@Override
	public void ejecutarFrame() {
		contadorColorFrames++;
		if(contadorColorFrames % CAMBIO_COLOR_INICIO == 0) {			
			if(colorLetras.equals(Color.YELLOW)) {
				colorLetras = Color.RED;
			}else {
				colorLetras = Color.YELLOW;
			}
		}

	}

	@Override
	public void moverRaton(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pulsarRaton(MouseEvent e) {
		if(e.getX()<panelJuego.getWidth()/2) {
			panelJuego.setJugadores(1);
		}else {
			panelJuego.setJugadores(2);
		}
		PantallaJuego pantallaJuego = new PantallaJuego(panelJuego);
		pantallaJuego.inicializarPantalla();
		panelJuego.setPantallaActual(pantallaJuego);

	}

	@Override
	public void redimensionarPantalla(ComponentEvent e) {
		imagenReescaladaInicial = imagenOriginalInicial.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(), Image.SCALE_SMOOTH);
	

	}


	@Override
	public void pulsarTecla(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
