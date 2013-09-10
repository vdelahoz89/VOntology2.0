package GUI;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JScrollPane;


@SuppressWarnings("serial")
public class MyWindow extends JFrame {
		
		
		private MyGUI miVentana;
		
		/**
		 * Constructor del canvas donde vamos a pintar.
		 */
		public MyWindow() {
			super()	;	
			miVentana= new MyGUI();
			// Aï¿½adimos a la ventana un MyGUI (donde estarï¿½n todas las componentes instanciadas).
		
			//setContentPane(miVentana);
			getContentPane().add(new JScrollPane( miVentana ));
			
			// Tamaï¿½o en largo x ancho
			//setSize(1000,700);
			// De esta forma vamos a obtener el tamaï¿½o de la pantalla.
			//setSize( Toolkit.getDefaultToolkit().getScreenSize() );
			
			/**
			 * Para ajustar el tamaï¿½o de la pantalla,
			 */
			float escalar = 0.9F;
			int ancho = (int)(Toolkit.getDefaultToolkit().getScreenSize().width*escalar); 
			int alto = (int)(Toolkit.getDefaultToolkit().getScreenSize().height*escalar); 
			this.setSize(ancho,alto);
			
			setLocationRelativeTo(null);
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);	
		}
		
		
	
	public static void main(String[] args) {

			MyWindow marcoApp = new MyWindow();
			marcoApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			marcoApp.setTitle("V Ontologías");
			marcoApp.setResizable(true);
			marcoApp.setVisible(true); 
			

	}

}
