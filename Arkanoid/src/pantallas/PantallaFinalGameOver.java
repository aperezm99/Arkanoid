package pantallas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import base.PanelJuego;
import base.Pantalla;

/**
 * 
 * @author Agustin Perez
 * 
 *         Pantalla que muestra el game over y permite al usuario volver a la pantalla inicial
 */
public class PantallaFinalGameOver implements Pantalla {

	PanelJuego panelJuego;

	public PantallaFinalGameOver(PanelJuego panelJuego) {
		this.panelJuego = panelJuego;
	}

	static final int CAMBIO_COLOR_INICIO = 10;
	Color color2 = Color.WHITE;
	int contadorFrames;
	BufferedImage imagenOriginalFinal;
	Image imagenReescaladaFinal;
	Font fuenteGameOver;
	Font fuentevolverAJugar;

	@Override
	public void inicializarPantalla() {
		try {
			imagenOriginalFinal = ImageIO.read(new File("Imagenes/fondoGameOver.jpg"));
			imagenReescaladaFinal = imagenOriginalFinal.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(),
					Image.SCALE_SMOOTH);
		} catch (IOException e) {
			e.printStackTrace();
		}
		fuenteGameOver = new Font("Arial", Font.BOLD, 90);
		fuentevolverAJugar = new Font("Arial", Font.ITALIC, 35);
	}

	@Override
	/**
	 * pintamos los mensajes de game over y pelsar para volver a jugar en pantalla
	 */
	public void pintarPantalla(Graphics g) {
		g.drawImage(imagenReescaladaFinal, 0, 0, null);
		g.setColor(Color.RED);
		g.setFont(fuenteGameOver);
		g.drawString("Game Over", panelJuego.getWidth() / 2 - 220, panelJuego.getHeight() / 10);
		g.setFont(fuentevolverAJugar);
		g.setColor(color2);
		g.drawString("Haz Click para volver a Jugar", panelJuego.getWidth() / 2 - 200,
				panelJuego.getHeight() / 2 + panelJuego.getHeight() / 3);
	}

	@Override
	/**
	 * Cambiamos el color para que el texto se vea dinamico en pantalla
	 */
	public void ejecutarFrame() {
		contadorFrames++;
		if (contadorFrames % CAMBIO_COLOR_INICIO == 0) {
			if (color2 == Color.WHITE) {
				color2 = Color.RED;
			} else {
				color2 = Color.WHITE;
			}
		}
	}

	@Override
	public void moverRaton(MouseEvent e) {

	}

	@Override
	public void pulsarRaton(MouseEvent e) {
		PantallaInicial pantallaInicial = new PantallaInicial(panelJuego);
		pantallaInicial.inicializarPantalla();
		panelJuego.setPantallaActual(pantallaInicial);
	}

	@Override
	public void redimensionarPantalla(ComponentEvent e) {
		imagenReescaladaFinal = imagenOriginalFinal.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(),
				Image.SCALE_SMOOTH);
	}

}
