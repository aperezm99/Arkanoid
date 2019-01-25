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
 * Pantalla que se muestra al inicio de cada partida , que invita al usuario a iniciar el juego pulsando click
 * ademas muestra el titulo del juego
 */
public class PantallaInicial implements Pantalla {

	PanelJuego panelJuego;
	
	BufferedImage imagenOriginalInicial;
	Image imagenReescaladaInicial;
	Font fuenteInicial;
	Font fuentevolverAJugar;
	//Inicio pantalla
	Color colorLetrasIniciarPartida = Color.WHITE;
	Color colorLetrasArkanoid = Color.GREEN;
	int contadorColorFrames = 0;
	static final int CAMBIO_COLOR_INICIO = 5;
	
	public PantallaInicial(PanelJuego panelJuego) {
		this.panelJuego = panelJuego;
	}
	
	
	@Override
	public void inicializarPantalla() {
		try {
			imagenOriginalInicial = ImageIO.read(new File("Imagenes/fondo.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		fuenteInicial = new Font("Arial", Font.BOLD, 180);
		fuentevolverAJugar = new Font("Arial", Font.ITALIC, 35);
	}

	@Override
	public void pintarPantalla(Graphics g) {
		g.drawImage(imagenReescaladaInicial, 0,0, null);
		g.setColor(colorLetrasArkanoid);
		g.setFont(fuenteInicial);
		g.drawString("ARKANOID",panelJuego.getWidth() / 2-450, panelJuego.getHeight() / 2);
		g.setFont(fuentevolverAJugar);
		g.setColor(colorLetrasIniciarPartida);
		g.drawString("CLICK PARA EMPEZAR LA PARTIDA !!!",panelJuego.getWidth() / 2-300, panelJuego.getHeight() / 2+100);
		if(contadorColorFrames==1) {
			imagenReescaladaInicial = imagenOriginalInicial.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(), Image.SCALE_SMOOTH);
		}

	}

	@Override
	public void ejecutarFrame() {
		contadorColorFrames++;
		if(contadorColorFrames % CAMBIO_COLOR_INICIO == 0) {
			
			if(colorLetrasIniciarPartida.equals(Color.WHITE)) {
				colorLetrasIniciarPartida = Color.RED;
			}else {
				colorLetrasIniciarPartida = Color.WHITE;
			}
			
			
		}
		
		if(contadorColorFrames % 10 == 0) {
			int r=(int) Math.floor(Math.random()*256);
			int g=(int) Math.floor(Math.random()*256);
			int b=(int) Math.floor(Math.random()*256);
			colorLetrasArkanoid= new Color(r, g, b);
		}
		
		

	}

	@Override
	public void moverRaton(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pulsarRaton(MouseEvent e) {
		PantallaJuego pantallaJuego = new PantallaJuego(panelJuego);
		pantallaJuego.inicializarPantalla();
		panelJuego.setPantallaActual(pantallaJuego);
		
		
		

	}

	@Override
	public void redimensionarPantalla(ComponentEvent e) {
		imagenReescaladaInicial = imagenOriginalInicial.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(), Image.SCALE_SMOOTH);
	

	}

}
