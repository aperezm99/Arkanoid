package pantallas;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

import base.PanelJuego;
import base.Pantalla;
import base.Sprite;

/**
 * 
 * @author Agustin Perez
 *
 *         Pantalla que se encarga de mostrar y permitir al usuario toda la
 *         interfaz y movilidad del juego
 */
public class PantallaJuego implements Pantalla {

	PanelJuego panelJuego;

	// CONSTANTES ESTATICAS
	private static final int ALTO_BARRA = 30;
	private static final int ANCHO_BARRA = 100;

	private static final int ALTO_BOLA = 15;
	private static final int ANCHO_BOLA = 15;

	private static final int ALTO_CUBO = 40;
	private static final int ANCHO_CUBO = 60;

	private static final int ALTO_ITEM = 25;
	private static final int ANCHO_ITEM = 25;

	// CONJUNTOS DE OBJETOS
	ArrayList<Sprite> cubos;
	ArrayList<Sprite> items = new ArrayList<>();
	String[] rutasCubos = { "Imagenes/cuboAmarillo.png", "Imagenes/cuboRojo.png", "Imagenes/cuboLila.png" };
	String[] rutasItems = { "Imagenes/ItemHacerBolaGrande.png", "Imagenes/ItemHacerseGrande.png",
			"Imagenes/ItemHacersePequeño.png", "Imagenes/ItemIman.png", "Imagenes/ItemProtector.png" };
	String[] idsItems = { "bigball", "big", "small", "iman", "wall" };
	Sprite[] vidas;

	// BUFFERS
	BufferedImage imagenOriginal;
	Image imagenReescalada;
	BufferedImage imagenCAmarillo;
	BufferedImage imagenCRojo;
	BufferedImage imagenCLila;

	// SPRITES
	Sprite barraPlayer;
	Sprite bola;
	Sprite muro;// efecto de un item

	// BOOLEANOS
	boolean bolaEnInicio = true;
	boolean imanActivo = false;
	boolean muroActivo = false;
	boolean bigball = false;

	int cubosDestruidosporBigball = 0;
	int numeroVidas = 3;

	// VARIABLES PARA MOSTRAR EL TIEMPO
	double tiempoInicial;
	double tiempoDeJuego;
	private DecimalFormat formatoDecimal; // Formatea la salida.
	Font fuenteTiempo;

	Clip sonido;// VARIABLE QUE ALMACENARA LA MUSICA DE FONDO

	public PantallaJuego(PanelJuego panelJuego) {
		this.panelJuego = panelJuego;

		tiempoInicial = System.nanoTime();// inicializamos el tiempo
	}

	@Override
	/**
	 * Metodo encargado en inicializar todos los aspectos necesarios para poder
	 * empezar a mostrar la pantala de juego y que todo funcione correctamente
	 */
	public void inicializarPantalla() {
		establecerCursor();
		try {
			// establecemos el sonido y lo iniciamos en bucle
			sonido = AudioSystem.getClip();
			sonido.open(AudioSystem.getAudioInputStream(new File("Musica/cancion.wav")));
			sonido.start();
			sonido.loop(Clip.LOOP_CONTINUOUSLY);

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		vidas = new Sprite[numeroVidas];
		int x = panelJuego.getWidth() - 90;
		for (int i = 0; i < numeroVidas; i++) {
			Sprite vida = new Sprite(30, 30, x, 0, 0, 0, "Imagenes/vida.png");
			vidas[i] = vida;
			x += 30;
		}
		/**
		 * inicializamos todos los cubos con sus variables y los colocamos en pantalla
		 * haciendo una figura para ello usaremos una ristra de condicionales
		 */
		cubos = new ArrayList<Sprite>();
		Sprite cubo;
		int tipoDeCubo;
		int vidasDelCubo;
		int posxCubo;
		int posyCubo = panelJuego.getHeight() / 2 - panelJuego.getHeight() / 3;
		for (int i = 0; i < 10; i++) {
			posxCubo = panelJuego.getWidth() / 2 - panelJuego.getWidth() / 4;
			if (i != 0) {
				posyCubo += ALTO_CUBO;
			}

			for (int j = 0; j < 15; j++) {
				tipoDeCubo = (int) Math.floor(Math.random() * rutasCubos.length);
				vidasDelCubo = tipoDeCubo;
				cubo = new Sprite(ANCHO_CUBO, ALTO_CUBO, posxCubo, posyCubo, rutasCubos[0], rutasCubos[1],
						rutasCubos[2], tipoDeCubo);
				if (i == 0 && j == 7 || i == 0 && j == 6 || i == 0 && j == 8 || i == 9 && j == 7 || i == 9 && j == 8
						|| i == 9 && j == 6 || i == 5 || i == 4 || i == 1 && j == 7 || i == 1 && j == 6
						|| i == 1 && j == 5 || i == 1 && j == 8 || i == 1 && j == 9 || i == 8 && j == 7
						|| i == 8 && j == 6 || i == 8 && j == 5 || i == 8 && j == 8 || i == 8 && j == 9
						|| i == 2 && j == 7 || i == 2 && j == 6 || i == 2 && j == 5 || i == 2 && j == 8
						|| i == 2 && j == 9 || i == 2 && j == 4 || i == 2 && j == 10 || i == 7 && j == 7
						|| i == 7 && j == 6 || i == 7 && j == 5 || i == 7 && j == 8 || i == 7 && j == 9
						|| i == 7 && j == 4 || i == 7 && j == 10 || i == 6 && j == 7 || i == 6 && j == 6
						|| i == 6 && j == 5 || i == 6 && j == 8 || i == 6 && j == 9 || i == 6 && j == 4
						|| i == 6 && j == 10 || i == 6 && j == 11 || i == 6 && j == 12 || i == 6 && j == 3
						|| i == 6 && j == 2 || i == 2 && j == 7 || i == 2 && j == 6 || i == 2 && j == 5
						|| i == 2 && j == 8 || i == 2 && j == 9 || i == 2 && j == 4 || i == 2 && j == 10
						|| i == 7 && j == 7 || i == 7 && j == 6 || i == 7 && j == 5 || i == 7 && j == 8
						|| i == 7 && j == 9 || i == 7 && j == 4 || i == 7 && j == 10 || i == 3 && j == 7
						|| i == 3 && j == 6 || i == 3 && j == 5 || i == 3 && j == 8 || i == 3 && j == 9
						|| i == 3 && j == 4 || i == 3 && j == 10 || i == 3 && j == 11 || i == 3 && j == 12
						|| i == 3 && j == 3 || i == 3 && j == 2) {
					cubos.add(cubo);
				}

				posxCubo += ANCHO_CUBO;
			}
		}

		try {
			imagenOriginal = ImageIO.read(new File("Imagenes/fondo.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		barraPlayer = new Sprite(ANCHO_BARRA, ALTO_BARRA, 200,
				(panelJuego.getHeight() / 2) + panelJuego.getHeight() / 3, 0, 0, "Imagenes/barraNormal.png");
		bola = new Sprite(ALTO_BOLA, ANCHO_BOLA, (barraPlayer.getPosX() + ANCHO_BARRA / 2) - ANCHO_BOLA / 2,
				barraPlayer.getPosY() - ALTO_BOLA, 0, 0, "Imagenes/bolaPequeña.png");

		// INICIAMOS LAS VARIABLES PARA PINTAR EL TIEMPO
		fuenteTiempo = new Font("Arial", Font.BOLD, 20);

		tiempoInicial = System.nanoTime();
		tiempoDeJuego = 0;
		formatoDecimal = new DecimalFormat("#.##");
		reescalarImagen();
	}

	/**
	 * METODO ENCARGADO DE HACER QUE EL CURSOR NO SE VEA EN LA PANTALLA DE JUEGO
	 */
	public void establecerCursor() {
		Cursor cursor;
		ImageIcon imagen = new ImageIcon("");
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		cursor = toolkit.createCustomCursor(imagen.getImage(), new Point(), "cursor");
		panelJuego.setCursor(cursor);
	}

	@Override
	/**
	 * Metodo en el que pintamos en pantalla cada uno de los sprites del juego
	 */
	public void pintarPantalla(Graphics g) {
		rellenarFondo(g);
		// Pintamos los cuadrados:
		barraPlayer.pintarSpriteEnMundo(g);
		for (Sprite cubo : cubos) {
			cubo.pintarSpriteEnMundo(g);
		}

		for (Sprite item : items) {
			item.pintarSpriteEnMundo(g);
		}
		if (muroActivo) {
			muro.pintarSpriteEnMundo(g);
		}
		bola.pintarSpriteEnMundo(g);
		for (int i = 0; i < vidas.length; i++) {
			if (vidas[i] != null) {

				vidas[i].pintarSpriteEnMundo(g);
			}
		}

		pintarTiempo(g);

	}

	/**
	 * Metodo encargado de pintar el tiempo actual que lleva jugando el usuario
	 * desde que empezo
	 * 
	 * @param g
	 */
	private void pintarTiempo(Graphics g) {
		Font f = g.getFont();
		Color c = g.getColor();

		g.setColor(Color.YELLOW);
		g.setFont(fuenteTiempo);
		actualizarTiempo();
		g.drawString(formatoDecimal.format(tiempoDeJuego / 1000000000d), 25, 25);
		g.setColor(c);
		g.setFont(f);
	}

	/**
	 * metodo encargado de calcular el tiempo que lleva jugando el usuario
	 */
	private void actualizarTiempo() {
		tiempoDeJuego = System.nanoTime() - tiempoInicial;

	}

	/**
	 * Metodo encargado de establecer la imagen de fondo con las dimensiones
	 * correctas
	 * 
	 * @param g
	 */
	private void rellenarFondo(Graphics g) {
		// Pintar la imagen de fondo reescalada:
		g.drawImage(imagenReescalada, 0, 0, null);
	}

	/**
	 * Metodo encargado de calcular el movimiento de cada uno de los sprites que se
	 * muevan por el panel de juego
	 */
	private void moverSprites() {
		bola.moverSprite(panelJuego.getWidth(), panelJuego.getHeight());

		for (int i = 0; i < items.size(); i++) {
			items.get(i).moverSprite(panelJuego.getWidth(), panelJuego.getHeight());
			if (items.get(i).getPosY() > panelJuego.getHeight()) {
				items.remove(items.get(i));
			}

		}
		if (bola.getPosY() > panelJuego.getHeight()) {
			bola = new Sprite(ALTO_BOLA, ANCHO_BOLA, (barraPlayer.getPosX() + ANCHO_BARRA / 2) - ANCHO_BOLA / 2,
					barraPlayer.getPosY() - ALTO_BOLA, 0, 0, "Imagenes/bolaPequeña.png");
			bolaEnInicio = true;
			numeroVidas--;
			vidas[numeroVidas] = null;
			comprobarFinDeJuego();
		}

	}

	/**
	 * Metodo encargado de comprobar las colisiones de la bola con los cubos y todo
	 * lo que ello desencadena
	 */
	private void comprobarColisiones() {
		for (int i = 0; i < cubos.size(); i++) {
			if (bola.colisionan(cubos.get(i))) {
				if (bigball) {
					cubosDestruidosporBigball++;
					cubos.remove(cubos.get(i));
					if (cubosDestruidosporBigball > 10) {
						bigball = false;
						bola.setAlto(ALTO_BOLA);
						bola.setAncho(ANCHO_BOLA);
						bola.actualizarBuffer();
						cubosDestruidosporBigball = 0;
					}
				} else {

					calcularTrayectoria(cubos.get(i));
					if (cubos.get(i).getVidas() > 0) {
						cubos.get(i).setVidas(cubos.get(i).getVidas() - 1);
						cubos.get(i).actualizarImagenCubo();
					} else {
						if (cubos.get(i).tieneItem()) {
							int itemAl = (int) Math.floor(Math.random() * rutasItems.length);
							Sprite item = new Sprite(ANCHO_ITEM, ALTO_ITEM, cubos.get(i).getPosX(),
									cubos.get(i).getPosY(), 0, 6, rutasItems[itemAl], idsItems[itemAl]);
							items.add(item);
						}
						cubos.remove(cubos.get(i));
					}
				}
			}
		}

	}

	/**
	 * Metodo encargado de comprobar las colisiones entre la bola y la barra
	 */
	private void comprobarColisionBarraPlayer() {
		if (bola.colisionan(barraPlayer)) {
			calcularTrayectoria(barraPlayer);
			if (imanActivo) {
				bola.setVelocidadX(0);
				bola.setVelocidadY(0);
				bola.setPosX((barraPlayer.getPosX() + barraPlayer.getAncho() / 2));
				bola.setPosY(barraPlayer.getPosY() - ALTO_BOLA);
			}
		}
	}

	/**
	 * Metodo encargado de comprobar la colision entre la bola y e muro que es el
	 * resultado de ejecutar un item
	 */
	private void comprobarColisionMuro() {
		if (muroActivo) {
			if (bola.colisionan(muro)) {

				calcularTrayectoria(muro);
				muroActivo = false;
			}
		}

	}

	/**
	 * Metodo encargado encargado de comprobar la colision entre los items y la
	 * barra player y activar cada una de su funciones enel casa de que colisionen
	 */
	private void comprobarColisionesItems() {
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).colisionan(barraPlayer)) {
				switch (items.get(i).getTipoItem()) {

				case "bigball":
					if (!bolaEnInicio) {
						bigball = true;
						bola.setAlto(ALTO_BOLA * 2);
						bola.setAncho(ANCHO_BOLA * 2);
						bola.actualizarBuffer();
					}
					break;
				case "big":
					if (!bolaEnInicio) {

						barraPlayer.setAncho(ANCHO_BARRA * 2);
						barraPlayer.actualizarBuffer();
					}
					break;
				case "small":
					if (!bolaEnInicio) {
						barraPlayer.setAncho(ANCHO_BARRA / 2);
						barraPlayer.actualizarBuffer();
					}
					break;
				case "iman":
					if (!bolaEnInicio) {
						imanActivo = true;
					}
					break;
				case "wall":
					if (!bolaEnInicio && !muroActivo) {
						muroActivo = true;
						muro = new Sprite(panelJuego.getWidth(),
								panelJuego.getHeight() - (barraPlayer.getPosY() + ALTO_BARRA), 0,
								barraPlayer.getPosY() + 50, 0, 0, "Imagenes/muro.png");
					}
					break;

				}

				items.remove(i);
			}
		}
	}

	/**
	 * Metodo encargado de comprobar la trayectoria de la bola y rectificarla para
	 * que al colisionar en ciertos puntos cambie a un sentido u otro
	 * 
	 * @param colisionante recive un Sprite con el que colisiona
	 */
	public void calcularTrayectoria(Sprite colisionante) {
		int posXBola = bola.getPosX() + bola.getAncho() / 2;
		int tercioBarra = colisionante.getAncho() / 3;
		int velX = 0;
		int velY = 0;
		if (posXBola >= colisionante.getPosX() && posXBola < colisionante.getPosX() + tercioBarra) {
			velX = bola.getVelocidadX() * -1;
			velY = bola.getVelocidadY() * -1;
			if (bola.getVelocidadX() == 0) {
				velX = -8;
			}
		}

		if (posXBola > colisionante.getPosX() + tercioBarra && posXBola < colisionante.getPosX() + tercioBarra * 2) {
			velX = 0;
			velY = bola.getVelocidadY() * -1;
		}

		if (posXBola > colisionante.getPosX() + tercioBarra * 2
				&& posXBola <= colisionante.getPosX() + tercioBarra * 3) {
			velX = bola.getVelocidadX();
			velY = bola.getVelocidadY() * -1;
			if (bola.getVelocidadX() == 0) {
				velX = 8;
			}
		}
		if (velY == 0) {
			velY = -21;
		}
		bola.setVelocidadX(velX);
		bola.setVelocidadY(velY);
	}

	/**
	 * Metodo encargado de comprobar el fin de juego
	 */
	public void comprobarFinDeJuego() {
		if (numeroVidas == 0) {
			PantallaFinalGameOver gameOver = new PantallaFinalGameOver(panelJuego);
			gameOver.inicializarPantalla();
			panelJuego.setPantallaActual(gameOver);
			sonido.stop();
		}
	}

	/**
	 * Metodo encargado de comprobar la victoria
	 */
	public void comprobarFinDeVictoria() {
		if (cubos.size() == 0) {
			PantallaFinalVictoria victoria = new PantallaFinalVictoria(panelJuego,
					formatoDecimal.format(tiempoDeJuego / 1000000000d));
			victoria.inicializarPantalla();
			panelJuego.setPantallaActual(victoria);
			sonido.stop();
		}
	}

	@Override
	/**
	 * Metodo encargado de ejecutar su contenido en cada frame
	 */
	public void ejecutarFrame() {

		comprobarColisiones();
		comprobarColisionBarraPlayer();
		comprobarColisionesItems();
		comprobarFinDeJuego();
		comprobarFinDeVictoria();
		comprobarColisionMuro();
		moverSprites();

	}

	@Override
	/**
	 * Metodo encargado de mover la barra player con el raton
	 */
	public void moverRaton(MouseEvent e) {
		barraPlayer.setPosX(e.getX());
		if (bolaEnInicio || bola.getVelocidadX() == 0 && bola.getVelocidadY() == 0) {
			if (bolaEnInicio) {

				barraPlayer = new Sprite(ANCHO_BARRA, ALTO_BARRA, e.getX() - ANCHO_BARRA / 2,
						(panelJuego.getHeight() / 2) + panelJuego.getHeight() / 3, 0, 0, "Imagenes/barraNormal.png");
			}
			bola = new Sprite(ALTO_BOLA, ANCHO_BOLA,
					(barraPlayer.getPosX() + barraPlayer.getAncho() / 2) - bola.getAncho() / 2,
					barraPlayer.getPosY() - bola.getAlto(), 0, 0, "Imagenes/bolaPequeña.png");
		}
		
	}

	@Override
	/**
	 * Metodo encargado de permitir al usuario poder disparar disparar la bola en 3
	 * direcciones dependiendo del boton del mouse que pulse
	 */
	public void pulsarRaton(MouseEvent e) {
		if (bolaEnInicio || bola.getVelocidadX() == 0 && bola.getVelocidadY() == 0) {

			if (SwingUtilities.isLeftMouseButton(e)) {
				bola.setVelocidadX(-21);
				bola.setVelocidadY(-21);
			}
			if (SwingUtilities.isRightMouseButton(e)) {
				bola.setVelocidadX(21);
				bola.setVelocidadY(-21);
			}
			if (SwingUtilities.isMiddleMouseButton(e)) {
				bola.setVelocidadY(-21);
			}
			if (imanActivo) {
				imanActivo = false;
			}
			bolaEnInicio = false;
		}
	}

	@Override
	public void redimensionarPantalla(ComponentEvent e) {
		reescalarImagen();

	}

	private void reescalarImagen() {
		imagenReescalada = imagenOriginal.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(),
				Image.SCALE_SMOOTH);
	}
}
