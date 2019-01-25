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
 * Pantalla que muestra la victoria al usuario y los segundos que ha tardado en pasarse el juego
 * ademas si pulsas en ella vuelves a la pantalla inicial
 *
 */
public class PantallaFinalVictoria implements Pantalla {

	PanelJuego panelJuego;
	String tiempoFinal;

	public PantallaFinalVictoria(PanelJuego panelJuego, String tiempoFinal) {
		this.panelJuego = panelJuego;
		this.tiempoFinal = tiempoFinal;
	}

	static final int CAMBIO_COLOR_INICIO = 10;
	Color colorAl = Color.WHITE;
	int contadorFrames;
	BufferedImage imagenOriginalFinal;
	Image imagenReescaladaFinal;
	Font fuenteVictoria;
	Font fuenteTiempo;

	@Override
	public void inicializarPantalla() {
		try {
			imagenOriginalFinal = ImageIO.read(new File("Imagenes/fondoGameOver.jpg"));
			imagenReescaladaFinal = imagenOriginalFinal.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(),
					Image.SCALE_SMOOTH);
		} catch (IOException e) {
			e.printStackTrace();
		}
		fuenteVictoria = new Font("Arial", Font.BOLD, 120);
		fuenteTiempo = new Font("Arial", Font.ITALIC, 40);
	}

	@Override
	public void pintarPantalla(Graphics g) {
		g.drawImage(imagenReescaladaFinal, 0, 0, null);
		g.setColor(Color.GREEN);
		g.setFont(fuenteVictoria);
		g.drawString("Victoria", panelJuego.getWidth() / 2 - 220, panelJuego.getHeight() / 10);
		g.setFont(fuenteTiempo);
		g.setColor(colorAl);
		g.drawString("Tiempo Final: " + tiempoFinal + "'s", panelJuego.getWidth() / 2 - 200,
				panelJuego.getHeight() / 2 + panelJuego.getHeight() / 3);

	}

	@Override
	public void ejecutarFrame() {
		contadorFrames++;
		if (contadorFrames % CAMBIO_COLOR_INICIO == 0) {
			int r = (int) Math.floor(Math.random() * 256);
			int g = (int) Math.floor(Math.random() * 256);
			int b = (int) Math.floor(Math.random() * 256);
			colorAl = new Color(r, g, b);
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
