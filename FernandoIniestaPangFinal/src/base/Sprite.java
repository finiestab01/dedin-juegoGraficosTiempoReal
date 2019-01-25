package base;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

/**
 * @author fernandoiniestabermejo
 * Clase Sprite. Representa un elemento pintable y colisionable del juego.
 */
public class Sprite {
	
	private BufferedImage buffer;
	private Color color = Color.CYAN;
	//Variables de dimensi�n
	private int ancho;
	private int alto;
	//Variables de colocaci�n
	private int posX;
	private int posY;
	//Variables para la velocidad
	private int velocidadX;
	private int velocidadY;
	//Ruta de la imagen
	private String rutaImagen;
	private int disparos=1;
	
	/**
	 * Constructor simple para un Sprite sin imagen y sin velocidad.
	 * @param ancho Ancho que ocupa el Sprite (en pixels)
	 * @param alto Altura que ocupa el Sprite (en pixels)
	 * @param posX posici�n horizontal del sprite en el mundo.
	 * @param posY posici�n vertical del Sprite en el mundo. El origen se sit�a en la parte superior.
	 */
	public Sprite(int ancho, int alto, int posX, int posY,String rutaImagen) {
		this.ancho = ancho;
		this.alto = alto;
		this.posX = posX;
		this.posY = posY;
		this.rutaImagen = rutaImagen;
		actualizarBuffer(rutaImagen);
	}
	
	/**
	 * Constructor para un Sprite.
	 * @param ancho Ancho que ocupa el Sprite (en pixels)
	 * @param alto Altura que ocupa el Sprite (en pixels)
	 * @param posX posici�n horizontal del sprite en el mundo.
	 * @param posY posici�n vertical del Sprite en el mundo. El origen se sit�a en la parte superior.
	 * @param velocidadX velocidad horizontal del Sprite.
	 * @param velocidadY velocidad vertical del Sprite. 
	 */
	public Sprite(int ancho, int alto, int posX, int posY, int velocidadX, int velocidadY,String rutaImagen) {
		this.ancho = ancho;
		this.alto = alto;
		this.posX = posX;
		this.posY = posY;
		this.velocidadX = velocidadX;
		this.velocidadY = velocidadY;
		this.rutaImagen = rutaImagen;
		actualizarBuffer(rutaImagen);
	}
	
	/**
	 * M�todo para actualizar el buffer que guarda cada Sprite.
	 */
	public void actualizarBuffer(String rutaImagen){
		
		buffer = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
		Graphics g = buffer.getGraphics();
		try {
				BufferedImage imagenSprite = ImageIO.read(new File(rutaImagen));
				//pinto en el buffer la imagen
				g.drawImage(imagenSprite.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH),0,0,null);
			
		} catch (Exception e) {
			g.setColor(color);
			g.fillRect(0, 0, ancho, alto);
			g.dispose();	
		}	
	}
	
	
	

	/**
	 * M�todo para comprobar si el Sprite colisiona con otro.
	 * @param otroSprite El otro Sprite con el que se comprueba si hay colisi�n.
	 * @return verdadero si ambos Sprites colisionan.
	 */
	public boolean colisionan(Sprite otroSprite){
		//Checkeamos si comparten alg�n espacio a lo ancho:
		boolean colisionAncho = false;
		if(posX<otroSprite.getPosX()){ //El Sprite actual se encuentra m�s cerca del eje de las X.
			colisionAncho = posX+ ancho >= otroSprite.getPosX();
		}else{ //El otro Sprite se encuentra m�s cerca del eje de las X.
			colisionAncho = otroSprite.getPosX() + otroSprite.getAncho() >= posX;
		}
		
		//Checkeamos si comparten alg�n espacio a lo alto:
		boolean colisionAlto = false;
		if(posY<otroSprite.getPosY()){
			colisionAlto = alto > otroSprite.getPosY()-posY;
		}else{
			colisionAlto = otroSprite.getAlto() > posY-otroSprite.getPosY();
		}
		
		return colisionAncho&&colisionAlto;
	}
	
	
	/**
	 * M�todo para mover el Sprite por el mundo.
	 * @param anchoMundo ancho del mundo sobre el que se mueve el Sprite
	 * @param altoMundo alto del mundo sobre el que se mueve el Sprite
	 */
	public void moverSprite(int anchoMundo, int altoMundo){
		
		if(posX >= anchoMundo - ancho) { //por la derecha
			velocidadX = -1  * Math.abs(velocidadX);
		} 
		if(posX <= 0){//por la izquierda
			velocidadX = Math.abs(velocidadX);
		}
		if(posY >= altoMundo -alto){//por abajo
			velocidadY = -1  * Math.abs(velocidadY);
		}
		if(posY <= 100){ //Por arriba
			velocidadY = Math.abs(velocidadY);
		}
		posX = posX + velocidadX;
		posY = posY + velocidadY;
	}
	

	public String getRutaImagen() {
		return rutaImagen;
	}

	public void setRutaImagen(String rutaImagen) {
		this.rutaImagen = rutaImagen;
	}

	public void moverSprite(){
		posX = posX + velocidadX;
		posY = posY + velocidadY;
	}
	
	
	/**
	 * M�todo que pinta el Sprite en el mundo teniendo en cuenta las caracter�sticas propias del Sprite.
	 * @param g Es el Graphics del mundo que se utilizar� para pintar el Sprite.
	 */
	public void pintarSpriteEnMundo(Graphics g){
		g.drawImage(buffer, posX, posY, null);
	}
	
	
	//M�todos para obtener:
	public int getAncho(){
		return ancho;
	}
	
	public int getAlto(){
		return alto;
	}
	
	public int getPosX(){
		return posX;
	}
	
	public int getPosY(){
		return posY;
	}
	
	public BufferedImage getBuffer(){
		return buffer;
	}
	
	public int getVelocidadX(){
		return velocidadX;
	}
	
	public int getVelocidadY(){
		return velocidadY;
	}
	
	
	
	//M�todos para cambiar:
	public void setAncho(int ancho){
		this.ancho = ancho;
	}
	
	public void setAlto(int alto){
		this.alto = alto;
	}
	
	public void setPosX(int posX){
		this.posX = posX;
	}
	
	public void setPosY(int posY){
		this.posY = posY;
	}
	
	public void setBuffer(BufferedImage buffer){
		this.buffer = buffer;
	}
	
	public void setVelocidadX(int velocidadX){
		this.velocidadX = velocidadX;
	}
	
	public void setVelocidadY(int velocidadY){
		this.velocidadY = velocidadY;
	}
	
	
	public Color getColor() {
		return color;
	}
	
	
	public void setColor(Color color) {
		this.color = color;
		actualizarBuffer(rutaImagen);
	
	}

	public int getDisparos() {
		return disparos;
	}

	public void setDisparos(int disparos) {
		this.disparos = disparos;
	}
	
	
	
}
