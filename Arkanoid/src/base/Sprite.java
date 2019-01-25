package base;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

/**
 * @author Agustin Perez Martin
 * 
 *         clase que representa cada una de los objetos que se pintasn en
 *         pantalla con los que se pueden interactuar EJ: bola , cubos , barra ,
 *         items etc
 */
public class Sprite {

	// atributos de un sprite (un sprite no tiene por que usarlos todos)
	private Image[] imagenes = new Image[3];
	private BufferedImage buffer;
	private Color color = Color.BLACK;
	private int ancho;
	private int alto;
	private int posX;
	private int posY;
	private int velocidadX;
	private int velocidadY;
	private String rutaImagen;

	private int vidas;// usado por los cubos

	private String tipoItem;// usado por los items

	/**
	 * Constructor para crear los cubos que ayuda a almacenar las 3
	 * imagenes(estados) por la que puede pasar un cubo dependiendo de sus vidad
	 * 
	 * @param ancho
	 * @param alto
	 * @param posX
	 * @param posY
	 * @param rutaImagen1
	 * @param rutaImagen2
	 * @param rutaImagen3
	 * @param vidas       Maximo 3 minimo 1
	 */
	public Sprite(int ancho, int alto, int posX, int posY, String rutaImagen1, String rutaImagen2, String rutaImagen3,
			int vidas) {
		this.ancho = ancho;
		this.alto = alto;
		this.posX = posX;
		this.posY = posY;
		try {
			imagenes[0] = ImageIO.read(new File(rutaImagen1)).getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
			imagenes[1] = ImageIO.read(new File(rutaImagen2)).getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
			imagenes[2] = ImageIO.read(new File(rutaImagen3)).getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.vidas = vidas;
		actualizarBuffer();
		actualizarImagenCubo();
	}

	/**
	 * Constructor usado para crear la bola y la Barra player
	 * 
	 * @param ancho
	 * @param alto
	 * @param posX
	 * @param posY
	 * @param velocidadX
	 * @param velocidadY
	 * @param rutaImagen
	 */
	public Sprite(int ancho, int alto, int posX, int posY, int velocidadX, int velocidadY, String rutaImagen) {
		this.ancho = ancho;
		this.alto = alto;
		this.posX = posX;
		this.posY = posY;
		this.velocidadX = velocidadX;
		this.velocidadY = velocidadY;
		this.rutaImagen = rutaImagen;
		actualizarBuffer();
	}

	/**
	 * Contructor usado para crear los items
	 * 
	 * @param ancho
	 * @param alto
	 * @param posX
	 * @param posY
	 * @param velocidadX
	 * @param velocidadY
	 * @param rutaImagen
	 * @param tipoItem   -> se pide el id del item para poder reconocerlo facilmente
	 */
	public Sprite(int ancho, int alto, int posX, int posY, int velocidadX, int velocidadY, String rutaImagen,
			String tipoItem) {
		this.ancho = ancho;
		this.alto = alto;
		this.posX = posX;
		this.posY = posY;
		this.velocidadX = velocidadX;
		this.velocidadY = velocidadY;
		this.rutaImagen = rutaImagen;
		this.tipoItem = tipoItem;
		actualizarBuffer();
	}

	/**
	 * Metodo encargado de actualizar el buffer del sprite , si no tiene imagen
	 * pinta un rectangulo
	 */
	public void actualizarBuffer() {
		buffer = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
		Graphics g = buffer.getGraphics();

		try {
			BufferedImage imagenSprite = ImageIO.read(new File(rutaImagen));
			// pinto en el buffer la imagen
			g.drawImage(imagenSprite.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH), 0, 0, null);

		} catch (Exception e) {

			g.setColor(color);
			g.fillRect(0, 0, ancho, alto);

		}

		g.dispose();
	}

	/**
	 * Metodo encargado de comprobar la colision entre dos sprites
	 * 
	 * @param otroSprite
	 * @return boolean
	 */
	public boolean colisionan(Sprite otroSprite) {
		boolean colisionAncho = false;
		if (posX < otroSprite.getPosX()) {
			colisionAncho = posX + ancho >= otroSprite.getPosX();
		} else {
			colisionAncho = otroSprite.getPosX() + otroSprite.getAncho() >= posX;
		}

		boolean colisionAlto = false;
		if (posY < otroSprite.getPosY()) {
			colisionAlto = alto > otroSprite.getPosY() - posY;
		} else {
			colisionAlto = otroSprite.getAlto() > posY - otroSprite.getPosY();
		}

		return colisionAncho && colisionAlto;
	}

	/**
	 * Metodo encargado de mover el sprite por el panel de juego
	 * 
	 * @param anchoMundo ancho del mundo sobre el que se mueve el Sprite
	 * @param altoMundo  alto del mundo sobre el que se mueve el Sprite
	 */
	public void moverSprite(int anchoMundo, int altoMundo) {
		if (posX >= anchoMundo - ancho) { // por la derecha
			velocidadX = -1 * Math.abs(velocidadX);
		}
		if (posX <= 0) {// por la izquierda
			velocidadX = Math.abs(velocidadX);
		}
		if (posY <= 0) { // Por arriba
			velocidadY = Math.abs(velocidadY);
		}
		posX = posX + velocidadX;
		posY = posY + velocidadY;
	}

	/**
	 * Metodo encargado de pintar el sprite en el panel de juego
	 * 
	 * @param g
	 */
	public void pintarSpriteEnMundo(Graphics g) {
		g.drawImage(buffer, posX, posY, null);
	}
/**
 * Metodo encargado de calcular si un cubo tiene o no item
 * @return boolean
 */
	public boolean tieneItem() {
		int al = (int) Math.floor(Math.random() * 5 + 1);
		if (al == 5) {
			return true;
		}
		return false;
	}
/**
 * metodo encargado de actualizar la imagen del buffer de un cubo dependiendo de las vidas que tenga
 */
	public void actualizarImagenCubo() {
		Graphics g = buffer.getGraphics();
		g.drawImage(imagenes[vidas], 0, 0, null);
		g.dispose();
	}

	/**
	 * GETTERS AND SETTERS
	 * 
	 */
	public int getAncho() {
		return ancho;
	}

	public int getAlto() {
		return alto;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public BufferedImage getBuffer() {
		return buffer;
	}

	public int getVelocidadX() {
		return velocidadX;
	}

	public int getVelocidadY() {
		return velocidadY;
	}

	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	public void setAlto(int alto) {
		this.alto = alto;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public void setBuffer(BufferedImage buffer) {
		this.buffer = buffer;
	}

	public void setVelocidadX(int velocidadX) {
		this.velocidadX = velocidadX;
	}

	public void setVelocidadY(int velocidadY) {
		this.velocidadY = velocidadY;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
		actualizarBuffer();

	}

	public int getVidas() {
		return vidas;
	}

	public void setVidas(int vidas) {
		this.vidas = vidas;
	}

	public String getRutaImagen() {
		return rutaImagen;
	}

	public void setRutaImagen(String rutaImagen) {
		this.rutaImagen = rutaImagen;
	}

	public String getTipoItem() {
		return tipoItem;
	}

	public void setTipoItem(String tipoItem) {
		this.tipoItem = tipoItem;
	}

}
