package base;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import pantallas.PantallaInicial;

/**
 * 
 * @author AgustinPerez
 * 
 *         Esta clase es la base de todas las pantallas , siempre tiene una sola
 *         pantalla pintandose encima y ejecutando sus metodos por lo que se va
 *         cambiando entre las pantallas para dar dinamismo al juego
 */
public class PanelJuego extends JPanel implements Runnable, MouseListener, ComponentListener, MouseMotionListener {
	private static final long serialVersionUID = 1L;
	Pantalla pantallaActual;//pantalla que se ejecutara en cada frame

	/**
	 * Constructor de PanelJuego.
	 */
	public PanelJuego() {

		this.addMouseListener(this);
		this.addComponentListener(this);
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

	@Override
	public void paintComponent(Graphics g) {
		pantallaActual.pintarPantalla(g);
	}

	@Override
	public void run() {
		while (true) {
			repaint();
			try {
				Thread.sleep(25);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
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
}
