package base;

import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;

/**
 * 
 * @author Agustin Perez Martin Intefaz usada como plantilla para crear cada una
 *         de las pantallas del juego
 *         
 */
public interface Pantalla {
	public void inicializarPantalla();

	public void pintarPantalla(Graphics g);

	public void ejecutarFrame();

	public void moverRaton(MouseEvent e);

	public void pulsarRaton(MouseEvent e);

	public void redimensionarPantalla(ComponentEvent e);

}
