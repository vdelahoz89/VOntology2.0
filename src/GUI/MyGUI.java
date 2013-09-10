package GUI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URI;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

import org.semanticweb.owl.align.Alignment;
import org.semanticweb.owl.align.AlignmentException;

import FileManager.AlignmentManager;
import FileManager.Concept;
import FileManager.Map;
import FileManager.OntologyManager;


@SuppressWarnings("serial")
public class MyGUI  extends JPanel  implements ActionListener {

	/*
	 ******************************************
	 * COMPONENTES GRï¿½FICAS - DECLARACIï¿½N Sï¿½LO.
	 ******************************************
	 */

	/*
	 * JPANELS - UNO PARA CADA ZONA DE LA PANTALLA
	 */
	private JPanel panelN;
	private JPanel panelS = new JPanel(new FlowLayout());
	private FlowLayout botoneraVisualizaciones;

	/*
	 * JMENUS - MENï¿½S PARA MANEJAR ARCHIVOS
	 */
	private JMenuBar barraMenu;
	private JMenu menuArchivo;
	private JMenuItem opcionAbrir;
	private JMenu menuOpciones;
	private JMenu menuOntologia1;
	private JMenuItem opcionMostrarOnto1;
	private JMenuItem opcionMostrarJerarquiaO1;
	private JMenuItem opcionMostrarConceptosO1;
	private JMenu menuOntologia2;
	private JMenuItem opcionMostrarOnto2;
	private JMenuItem opcionMostrarJerarquiaO2;
	private JMenuItem opcionMostrarConceptosO2;
	private JMenu menuAlignment;
	private JMenuItem opcionMostrarMatches;
	private JMenuItem opcionMostrarEstadisticas;
	private JMenu menuAyuda;
	private JMenuItem opcionMostrarAlignment;
	//private JMenuItem opcionUso;
	private JMenuItem opcionVersion;


	/*
	 * JBUTTONS - PARA CADA UNA DE LAS FUNCIONALIDADES
	 */
	private JButton botonLista, botonMatriz, botonRelaciones, botonResultados;			

	/*
	 * VARIOS.
	 */

	// Para pintar figuras y texto.
	private MyCanvas miCanvas;
	// Para abrir archivos.
	private JFileChooser archivo;	
	// Para escribir texto de archivos que se abran.
	private JTextPane txp;
	// Para resaltar palabras.
	Highlighter hilit;
	Highlighter.HighlightPainter painter;
	// Para informaciï¿½n en la parte superior
	private JLabel info;
	// Para los pop up. Mejor se crea aquï¿½ para poder usarse donde se quiera.
	private JFrame ventanaPopUp = new JFrame();
	// Para buscar conceptos
	private JTextField campoBusqueda;
	/*
	 * Variables para laas rutas del alignment y sus ontolgï¿½as asociadas.
	 */
	// Para las rutas al cargar los ficheros.
	private String rutaAlignment;
	private URI rutaOnto1;
	private URI rutaOnto2;
	// Para el manejo de cada uno de ellos.
	private AlignmentManager gestorAlignment = new AlignmentManager();
	private OntologyManager gestorOnto1 = new OntologyManager();
	private OntologyManager gestorOnto2 = new OntologyManager();
	// Para mi alignment.
	private Alignment miAlignment = null;
	private JScrollPane jsp;
	private JScrollPane scrollerList;
	//Para la matriz de conceptos
	private JPanel[][] thePanels;
	private JPanel thePane; 

	//Para la lista ordenada con colores
	private JPanel[][] listOfConcepts;
	private JPanel concept;  

	// Constructor de la clase.
	public MyGUI() { 		 		
		miCanvas = new MyCanvas();
		archivo = new javax.swing.JFileChooser();
		initComponent();
		miCanvas.repaint();
	}


	//////////////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * Vamos a ir creando los componentes uno a uno y aï¿½adiendo a la GUI segï¿½n corresponda.		
	 */
	public void initComponent() {
		setLayout(new BorderLayout());

		/*
		 **********************************************************
		 * Barra de menï¿½ - aï¿½adir accion a los botones de opciones.
		 **********************************************************
		 */
		barraMenu = new JMenuBar();
		menuArchivo = new JMenu("Archivo");

		opcionAbrir = new JMenuItem("Abrir");
		opcionAbrir.setActionCommand("ABRIR");			
		opcionAbrir.addActionListener(this);

		menuOpciones = new JMenu("Opciones");
		menuOntologia1 = new JMenu("Ontología 1");

		opcionMostrarOnto1 = new JMenuItem("Ver");
		opcionMostrarOnto1.setActionCommand("ONTO1");			
		opcionMostrarOnto1.addActionListener(this);

		opcionMostrarJerarquiaO1 = new JMenuItem("Jerarquía");
		opcionMostrarJerarquiaO1.setActionCommand("JERARQUIA1");			
		opcionMostrarJerarquiaO1.addActionListener(this);

		opcionMostrarConceptosO1 = new JMenuItem("Conceptos");
		opcionMostrarConceptosO1.setActionCommand("CONCEPTOS1");			
		opcionMostrarConceptosO1.addActionListener(this);

		menuOntologia2 = new JMenu("Ontologíaa 2");

		opcionMostrarOnto2 = new JMenuItem("Ver");
		opcionMostrarOnto2.setActionCommand("ONTO2");			
		opcionMostrarOnto2.addActionListener(this);

		opcionMostrarJerarquiaO2 = new JMenuItem("Jerarquía");
		opcionMostrarJerarquiaO2.setActionCommand("JERARQUIA2");			
		opcionMostrarJerarquiaO2.addActionListener(this);

		opcionMostrarConceptosO2 = new JMenuItem("Conceptos");
		opcionMostrarConceptosO2.setActionCommand("CONCEPTOS2");			
		opcionMostrarConceptosO2.addActionListener(this);


		menuAlignment = new JMenu("Alignment");

		opcionMostrarAlignment = new JMenuItem("Ver");
		opcionMostrarAlignment.setActionCommand("ALIGNMENT");			
		opcionMostrarAlignment.addActionListener(this);

		opcionMostrarMatches = new JMenuItem("Matches");
		opcionMostrarMatches.setActionCommand("MATCHES");			
		opcionMostrarMatches.addActionListener(this);

		opcionMostrarEstadisticas = new JMenuItem("Estadísticas");
		opcionMostrarEstadisticas.setActionCommand("ESTADISTICAS");			
		opcionMostrarEstadisticas.addActionListener(this);

		menuAyuda = new JMenu("Ayuda");
		//opcionUso = new JMenuItem("Uso");
		//opcionUso.setActionCommand("USO");			
		//opcionUso.addActionListener(this);

		opcionVersion = new JMenuItem("Version");
		opcionVersion.setActionCommand("VERSION");			
		opcionVersion.addActionListener(this);
		/*
		 * Ligamos cada una de las componentes y colocamos la barra al NORTE.
		 */
		barraMenu.add(menuArchivo);
		menuArchivo.add(opcionAbrir);
		barraMenu.add(menuOpciones);
		menuOpciones.add(menuOntologia1);
		menuOntologia1.add(opcionMostrarOnto1);
		menuOntologia1.add(opcionMostrarJerarquiaO1);
		menuOntologia1.add(opcionMostrarConceptosO1);
		menuOpciones.add(menuOntologia2);
		menuOntologia2.add(opcionMostrarOnto2);
		menuOntologia2.add(opcionMostrarJerarquiaO2);
		menuOntologia2.add(opcionMostrarConceptosO2);
		menuOpciones.add(menuAlignment);
		menuAlignment.add(opcionMostrarAlignment);
		menuAlignment.add(opcionMostrarMatches);
		menuAlignment.add(opcionMostrarEstadisticas);
		barraMenu.add(menuAyuda);
		//menuAyuda.add(opcionUso);
		menuAyuda.add(opcionVersion);

		panelN = new JPanel(new BorderLayout());
		panelN.add(barraMenu);
		add(panelN,BorderLayout.NORTH);

		/*
		 ************************************************
		 * Botones para las visualizaciones del alignment. 
		 ************************************************
		 */

		botonLista = new JButton("Lista");
		botonLista.addActionListener(new ButtonListener());

		botonMatriz = new JButton("Matriz");
		botonMatriz.addActionListener(new ButtonListener());

		botonRelaciones = new JButton("Relaciones");
		botonRelaciones.addActionListener(new ButtonListener());

		botonResultados =	new JButton("Resultados");
		botonResultados.addActionListener(new ButtonListener());

		panelS = new JPanel();      
		botoneraVisualizaciones = new FlowLayout();
		botoneraVisualizaciones.setAlignment(FlowLayout.TRAILING);
		panelS.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		panelS = new JPanel(botoneraVisualizaciones);

		info = new JLabel("Seleccione primero una visualizacion");
		info.setComponentOrientation(getComponentOrientation());
		panelS.add(info);

		panelS.add(botonLista);
		panelS.add(botonMatriz);
		panelS.add(botonRelaciones);
		panelS.add(botonResultados);
		add(panelS,BorderLayout.SOUTH);

		/*
		 ******************************************************************
		 * Zona para la visualizaciï¿½n de archivos de alignment y ontologï¿½a.
		 ******************************************************************
		 */

		txp = new JTextPane();
		txp.setEditable(false);
		jsp = new JScrollPane(txp);
		//jsp.setViewportView( txp );
		jsp.setWheelScrollingEnabled(true);
		jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(jsp,BorderLayout.CENTER);

		scrollerList = new JScrollPane(miCanvas,  
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,  
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 

		// Para pintar la matriz y lista de colores
		thePane = new JPanel();
		concept = new JPanel();

		/*
		 * Para la bï¿½squeda de conceptos.
		 */




		campoBusqueda = new JTextField(30);
		hilit = new DefaultHighlighter();
		painter = new DefaultHighlighter.DefaultHighlightPainter(Color.CYAN);
		txp.setHighlighter(hilit);
		campoBusqueda.addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
					hilit.removeAllHighlights();
					campoBusqueda.setText("");
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				buscarTexto();
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}

		});


		new JPanel(new GridLayout(3,4));
		panelS.add(campoBusqueda);



	}

	// Para capturar las acciones de los botones.
	public void actionPerformed(ActionEvent e){
		String accion = e.getActionCommand();
		int seleccion;
		switch (accion){

		case "ABRIR":

			if( archivo == null ) archivo = new JFileChooser();
			//Con esto solamente podamos abrir archivos
			archivo.setFileSelectionMode( JFileChooser.FILES_ONLY );

			seleccion = archivo.showOpenDialog( this );

			if( seleccion == JFileChooser.APPROVE_OPTION )
			{
				File f = archivo.getSelectedFile();
				try{
					String path = f.getAbsolutePath();
					/*
					 * Guardo la ruta del alignment, lo obtengo y guardo las
					 * URI's de cada una de las ontologï¿½as.
					 */
					rutaAlignment = path;
					miAlignment = gestorAlignment.obtenerAlignment(rutaAlignment);
					rutaOnto1 = miAlignment.getOntology1URI();
					rutaOnto2 = miAlignment.getOntology2URI();
					gestorOnto1.setIri(rutaOnto1);
					gestorOnto2.setIri(rutaOnto2);

					String mensajePopUp = 	"Se ha abierto el alignment " + miAlignment.getClass().getName() + " \n" 
							+ "Asociado a las ontologías \n"
							+ gestorOnto1.getIri() + " \n"
							+ gestorOnto2.getIri() + " \n";
					lanzarPopUp(ventanaPopUp, mensajePopUp);


				}
				catch( Exception exp){
				}
			}

			break; // FIN ABRIR

		case "ONTO1":
			String listado1 = gestorOnto1.obtenerListaConceptos();
			txp.setText(listado1);
			break; // FIN JERARQUIA1

		case "JERARQUIA1":

			gestorOnto1.imprimirJerarquia();
			String jerarquia1 = gestorOnto1.getJerarquia();
			txp.setText(jerarquia1);

			break; // FIN JERARQUIA1

		case "CONCEPTOS1":

			String conceptos1 = gestorOnto1.listarConceptos();
			txp.setText(conceptos1);
			break; // FIN CONCEPTOS1

		case "ONTO2":
			String listado2 = gestorOnto1.obtenerListaConceptos();
			txp.setText(listado2);
			break; // FIN JERARQUIA1

		case "JERARQUIA2":

			gestorOnto2.imprimirJerarquia();
			String jerarquia2 = gestorOnto2.getJerarquia();
			txp.setText(jerarquia2);

			break; // FIN JERARQUIA2

		case "CONCEPTOS2":
			String conceptos2 = gestorOnto2.listarConceptos();
			txp.setText(conceptos2);
			break; // FIN CONCEPTOS2

		case "ALIGNMENT":

			//En el editor de texto colocamos su contenido
			String contenido = leerArchivo( rutaAlignment );
			txp.setText( contenido );

			break; // FIN JERARQUIA1

		case "MATCHES":

			String listadoMatches = "";
			try {
				listadoMatches = gestorAlignment.obtenerMatches(miAlignment);
			} catch (AlignmentException e1) {
				e1.printStackTrace();
			}
			txp.setText(listadoMatches);

			break; // FIN MATCHES

		case "ESTADISTICAS":



			break; // FIN ESTADISTICAS

		case "USO":
			break; // FIN USO

		case "VERSION":
			lanzarPopUp(ventanaPopUp,"VOntology \n Visualizador de Ontologías \n Version 1.0 \n Víctor de la Hoz \n Universidad Politécnica de Madrid");
			break; // FIN VERSION			
		} // FIN SWITCH

	}

	/*
	 *********************************************
	 * ACCIONES DE LOS BOTONES DE VISAULIZACIONES
	 *********************************************
	 */

	class ButtonListener implements ActionListener { ButtonListener() { }
	// Aqui indicaremos las acciones de los botones
	public void actionPerformed(ActionEvent eventoBoton) {
		String boton = eventoBoton.getActionCommand();
		java.util.ArrayList<Map> listaMapping = new java.util.ArrayList<Map>();
		switch(boton) {

		case "Lista":

			txp.setText("");
			gestorAlignment.ordenarMatching(miAlignment);
			listaMapping =	gestorAlignment.getMappingList();
			String imprPant = "";

			imprPant += "Se han encontrado un total de "+ listaMapping.size() + " relaciones entre conceptos.\n" 
					+ "Dichas relaciones encontradas se muestran ordenadas de mayor a menor \n\n";


			for (Map m: listaMapping){
				imprPant += "ENTIDADES\t" + m.getEntity1() + "\t" + m.getEntity2() + "\n";
				imprPant += "TIPO\t" + m.getRelation() + "\n";
				imprPant += "PESO\t" + m.getMeasure() + "\n";
				imprPant += "\n";
			}


			// Vacío la lista de map's, SI NO CONCATENA LOS MAP'S !!
			// Si damos a MATRIZ y luego LISTA, se cuenta dos veces !! si damos dos veces sobre lista no.
			listaMapping.clear();

			remove(concept);
			remove(thePane);
			remove(scrollerList);
			add(jsp,BorderLayout.CENTER);				
			txp.setText(imprPant);
			updateUI();

			break;

		case "Relaciones":

			/*
			 * Quito la pantalla para pintar texto y pongo un canvas para pintar.
			 */

			gestorOnto1.obtenerListaConceptos();
			gestorOnto2.obtenerListaConceptos();
			gestorAlignment.ordenarMatching(miAlignment);

			ArrayList<Concept> onto1 = gestorOnto1.conceptos;
			ArrayList<Concept> onto2 = gestorOnto2.conceptos;
			ArrayList<Map> mapping = gestorAlignment.getMappingList();



			miCanvas.crearLista(onto1,onto2,mapping);
			miCanvas.setBackground(Color.WHITE);

			//JPanel panel = new JPanel();
			float escalar = 0.9F;
			int ancho = (int)(Toolkit.getDefaultToolkit().getScreenSize().width*escalar); 
			@SuppressWarnings("unused")
			int alto = (int)(Toolkit.getDefaultToolkit().getScreenSize().height*escalar); 

			// Se asigna el tamaño necesario para pintar.
			// Multiplicamos por 30 porque es el espacio entre conceptos. Este
			// valor se modifica en MyCanvas.

			if (onto1.size() > onto2.size()){
				scrollerList.setPreferredSize(	new Dimension(ancho,onto1.size()*30));
			} else {
				scrollerList.setPreferredSize(	new Dimension(ancho,onto2.size()*30));

			}

			// Para pruebas
			//scroller.setPreferredSize(	new Dimension(3000 , 3000) );

			remove(concept);
			remove(thePane);
			remove(jsp);
			add(scrollerList, BorderLayout.CENTER);
			updateUI();
			break;

		case "Matriz":

			gestorAlignment.ordenarMatching(miAlignment);
			listaMapping =	gestorAlignment.getMappingList();
			// Creamos los elementos donde pondremos la matriz.
			//thePane = (JPanel)theWindow.getContentPane();
			thePanels = new JPanel[listaMapping.size() + 1][listaMapping.size() + 1];
			thePane.setLayout(new GridLayout(listaMapping.size() + 1, listaMapping.size() + 1, 0, 0));
			// Añado el esquinazo superior izquierdo de la matriz.
			thePanels[0][0] = new JPanel();
			//thePanels[0][0].setSize(new Dimension(1, 1));
			thePanels[0][0].add(new JLabel(""));
			thePanels[0][0].setBackground(new Color(1.0f , 0.0f , 0.0f , 0.0f));
			thePanels[0][0].setLayout((LayoutManager) new BoxLayout( thePanels[0][0], BoxLayout.Y_AXIS));
			thePane.add(thePanels[0][0]);

			// Rellenamos las columnas de la matriz.
			for (int j = 1; j < listaMapping.size() + 1; j++){			    
				thePanels[0][j] = new JPanel();
				//thePanels[0][j].setSize(new Dimension(1, 1));
				thePanels[0][j].add(new JLabel(listaMapping.get(j-1).getEntity1()));
				thePanels[0][j].setBackground(new Color(1.0f , 0.0f , 0.0f , 0.0f));
				thePanels[0][j].setLayout((LayoutManager) new BoxLayout( thePanels[0][j] , BoxLayout.Y_AXIS));
				thePane.add(thePanels[0][j]);	    
			}



			// Rellenamos las filas de la matriz.
			for (int i = 1; i < listaMapping.size() + 1; i++){		    
				thePanels[i][0] = new JPanel();
				thePanels[i][0].setSize(new Dimension(1, 1));
				thePanels[i][0].add(new JLabel(listaMapping.get(i-1).getEntity2()));
				thePanels[i][0].setBackground(new Color(1.0f , 0.0f , 0.0f , 0.0f));
				thePane.add(thePanels[i][0]);

				// Como nuestra matriz será una diagonal vamos colocando los pesos según
				// corresponda y rellenando los huecos entre medias.
				for (int w = 1; w < listaMapping.size() + 1; w++){
					if (w == i) {
						thePanels[i][w] = new JPanel();
						thePanels[i][w].setSize(getMinimumSize());
						thePanels[i][w].add(new JLabel("" + listaMapping.get(w-1).getMeasure() + ""));
						thePanels[i][w].setBackground(new Color(1.0f , 0.0f , 0.0f , (float)listaMapping.get(w-1).getMeasure()));
						thePane.add(thePanels[i][w]);
					} else {
						thePanels[i][w] = new JPanel();
						thePanels[i][w].setSize(new Dimension(1, 1));
						thePanels[i][w].add(new JLabel(""));
						thePanels[i][w].setBackground(new Color(1.0f , 0.0f , 0.0f , 0.0f));
						thePane.add(thePanels[i][w]);
					}
				}    
			}

			remove(concept);
			remove(jsp);
			remove(scrollerList);
			add(thePane, BorderLayout.CENTER);
			updateUI();

			break;

		case "Resultados":
			//lanzarPopUp(ventanaPopUp, "Falta implementación");
		
			remove(concept);
			concept = new JPanel();
			
			gestorAlignment.ordenarMatching(miAlignment);
			listaMapping =	gestorAlignment.getMappingList();
			// Creamos los elementos donde pondremos la matriz.			
			listOfConcepts = new JPanel[listaMapping.size() + 1][2];
			concept.setLayout(new GridLayout(listaMapping.size() + 1, 2, 5, 5));


			for (int i = 0; i < listaMapping.size() ; i++){			    
				
				listOfConcepts[i][0] = new JPanel();
				listOfConcepts[i][0].setSize(getMinimumSize());
				listOfConcepts[i][0].add( new JLabel(""+listaMapping.get(i).getEntity1()+" "+listaMapping.get(i).getRelation()+" "+listaMapping.get(i).getEntity2()+""));
				listOfConcepts[i][0].setBackground(new Color(1.0f , 0.0f , 0.0f , 0.0f));
				concept.add(listOfConcepts[i][0]);
				
				listOfConcepts[i][1] = new JPanel();
				listOfConcepts[i][1].setSize(getMinimumSize());
				listOfConcepts[i][1].add(new JLabel("" + listaMapping.get(i).getMeasure() + ""));
				listOfConcepts[i][1].setBackground(new Color(1.0f , 0.0f , 0.0f , (float)listaMapping.get(i).getMeasure()));
				concept.add(listOfConcepts[i][1]);
			}


			remove(thePane);
			remove(jsp);
			remove(scrollerList);
			add(concept, BorderLayout.WEST);
			updateUI();

			break;

		} // FIN SWITCH
	}
	} // ButtonListener


	/*
	 *********************
	 * Mï¿TODOS AUXILIARES
	 *********************
	 */

	public String leerArchivo( String ruta ){
		FileReader fr = null;
		BufferedReader br = null;
		//Cadena de texto donde se guardara el contenido del archivo
		String contenido = "";
		try{
			//ruta puede ser de tipo String o tipo File
			fr = new FileReader( ruta );
			br = new BufferedReader( fr );

			String linea;
			//Obtenemos el contenido del archivo linea por linea
			while( ( linea = br.readLine() ) != null ){ 
				contenido += linea + "\n";
			}	
		}catch( Exception e ){  
			System.out.println("No se ha podido leer el archivo o no existe.");
		}
		//finally se utiliza para que si todo ocurre correctamente o si ocurre 
		//algun error se cierre el archivo que anteriormente abrimos
		finally{
			try{
				br.close();
			}catch( Exception ex ){}
		}
		return contenido;
	}

	public static void lanzarPopUp(JFrame parent, String mensaje) {
		// create and configure a text area - fill it with exception text.
		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("Sans-Serif", Font.PLAIN, 10));
		textArea.setEditable(false);
		textArea.setText(mensaje);
		// Para excepciones; ya las capturo al lanzar pop up.
		//StringWriter writer = new StringWriter();
		//e.printStackTrace(new PrintWriter(writer));
		//textArea.setText(writer.toString());

		// stuff it in a scrollpane with a controlled size.
		JScrollPane scrollPane = new JScrollPane(textArea);		
		scrollPane.setPreferredSize(new Dimension(400, 200));
		// pass the scrollpane to the joptionpane.				
		JOptionPane.showMessageDialog(parent, scrollPane, "", JOptionPane.WARNING_MESSAGE);
	}

	/*
	 * Para resaltar un texto en la pantalla de busqueda
	 */

	public void buscarTexto() {

		hilit.removeAllHighlights();
		String s = campoBusqueda.getText();

		if (s.length() > 0) {
			String contenido = txp.getText();
			int index = contenido.indexOf(s, 0);
			if (index >= 0) {
				try {
					int end = index + s.length();
					hilit.addHighlight(index, end, painter);
					txp.setCaretPosition(end);
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
			} else {
				campoBusqueda.setBackground(Color.ORANGE);
			}
		}
	} // buscarTexto

	/*
	 * Para imprimir los resultados de un alignment.
	 */
	public String imprimirListaAlignment(){

		AlignmentManager gestorAlignment = new AlignmentManager();
		// Abrimos el alignment en cuestiï¿½n. DE MOMENTO FIJO PARA PRUEBAS !!!
		gestorAlignment.obtenerAlignment(rutaAlignment);
		Alignment al = gestorAlignment.getAlignment();
		String listaResultado = "";
		try {
			listaResultado = gestorAlignment.obtenerMatches(al);
		} catch (AlignmentException e) {
			e.printStackTrace();
		}
		return listaResultado;

	}

	/*
	 * Para imprimir lista de conceptos de una ontologia.
	 */

	public String imprimirListaConceptos(){

		String listaConceptos = "";
		OntologyManager gestorOntologia = new OntologyManager();
		listaConceptos = gestorOntologia.listarConceptos();

		return listaConceptos;
	}

	/*
	 *********
	 * OTROS *
	 *********
	 */



}


