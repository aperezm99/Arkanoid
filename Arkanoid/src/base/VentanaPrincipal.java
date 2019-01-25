package base;
import java.awt.GridLayout;


import javax.swing.JFrame;

/**
 * Clase VentanaPrincipal. En ella se pinta el juego.
 * 
 * @author AgustinPerez
 *
 */
public class VentanaPrincipal {

	JFrame ventana;
	PanelJuego panelJuego;

	public VentanaPrincipal() {
		ventana = new JFrame();
		ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public void inicializarComponentes() {
		ventana.setResizable(false);
		ventana.setLayout(new GridLayout(1, 1));
		panelJuego = new PanelJuego();
		ventana.add(panelJuego);
	}

	public void inicializar() {
		ventana.setVisible(true);
		inicializarComponentes();
	}
	
}
