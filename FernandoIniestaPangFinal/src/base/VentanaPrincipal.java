package base;
import java.awt.Button;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.RepaintManager;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Clase VentanaPrincipal. En ella se pinta el juego.
 * @author jesusredondogarcia
 *
 */
public class VentanaPrincipal {

	final int anchoLienzo = 300;
	final int altoLienzo = 300;
	
	//Sigo teniendo la ventana
	JFrame ventana;
	PanelJuego panelJuego;
	
	
	public VentanaPrincipal() {
		ventana = new JFrame();
		ventana.setBounds(100, 50, 1000, 800);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
	}
	
	/**
	 * Método que inicializa todos los componentes de la ventana
	 */
	public void inicializarComponentes(){
		//Definimos el layout:
		ventana.setLayout(new GridLayout(1,1));
		
		//PANEL JUEGO
		panelJuego = new PanelJuego();
		ventana.add(panelJuego);
	}
	
	/**
	 * Método que inicializa todos los listeners del programa.
	 */
	public void inicializarListeners(){
		
	}
		
	
	/**
	 * Método que realiza todas las llamadas necesarias para inicializar la ventana correctamente.
	 */
	public void inicializar(){
		ventana.setVisible(true);
		inicializarComponentes();	
		inicializarListeners();		
	}
}
