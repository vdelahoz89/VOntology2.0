package GUI;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import FileManager.Concept;
import FileManager.Map;


@SuppressWarnings("serial")
public class MyCanvas extends JPanel {	
	
//	private int alto;
//	private int largo;
	private Dimension tamanioVentana;
	boolean pintar = false;
	private ArrayList<Concept> o1;
	private ArrayList<Concept> o2;
	private ArrayList<Map> m;
	
	public MyCanvas() {
		super();
		setBackground(Color.WHITE);
	}
		
	public void update(Graphics g) { 
		paint(g);
	}
	
	/* Definiremos nuestro mï¿½todo paint. */
	public void paint(Graphics g) {		
		
		// Descomentar para cambiar el origen de coordenadas en los pintados.

//		tamanioVentana = getSize();
//		coordX = tamanioVentana.width;
//		coordY = tamanioVentana.height;
//    	g.translate(coordX,coordY);
		
		if (pintar == true){
			pintarListas(g);
			pintarLineas(g);
		}
		
    	g.dispose();
    	g = null;
	}
	
		
	private void pintarListas(Graphics g) {			
			
		for (int i=0; i < this.o1.size() ; i++){
		    g.drawString( 	o1.get(i).getEntity() , 
		    				o1.get(i).posX , 
		    				o1.get(i).getPosY()
		    			);
		}
		
		for (int i=0; i < this.o2.size() ; i++){
		    g.drawString( 	o2.get(i).getEntity() , 
		    				o2.get(i).posX , 
		    				o2.get(i).getPosY()
		    			);
		}
		
	}
	
	private void pintarLineas(Graphics g){
		
		// Ahora pintamos las conexiones entre conceptos.
		
		Map map;
		int x1 = 0,y1 = 0,x2 = 0, y2 = 0;
		for (int i=0; i< this.m.size(); i++){
			
			map = new Map();
			map = m.get(i);
			
			for (int j=0; j < this.o1.size() ; j++){
				if (map.getEntity1().equals(o1.get(j).entity)) { 
					x1 = o1.get(j).getPosX();
					y1 = o1.get(j).getPosY(); 
				}
			}
			
			for (int z=0; z < this.o2.size() ; z++){
				if (map.getEntity2().equals(o2.get(z).entity)) { 
					x2 = o2.get(z).getPosX();
					y2 = o2.get(z).getPosY(); 
				}
			}
			
			//while ( !(map.getEntity2().equals(o2.get(q).entity)) ) {
				// Pintamos la linea
			g.setColor(Color.CYAN);
			g.drawLine(x1+100,y1,x2-10,y2);	
			//}
			
		}
	}
	
	public void crearLista(ArrayList<Concept> o1, ArrayList<Concept> o2, ArrayList<Map> maps) {
		
		// flag para pintar
		pintar = true;
		
		int x = 10;
		int y = 20;
		
		//Pintamos lista 1
		
		for (int i=0; i < o1.size() ; i++){

			o1.get(i).setPosX(x);
			o1.get(i).setPosY(y);
			
		    y+=30;

		}
		
		x = 300;
		y = 20;
		
		// Pintamos lista 2
		for (int i=0; i < o2.size() ; i++){

			o2.get(i).setPosX(x);
			o2.get(i).setPosY(y);
			
		    y+=30;

		}
	
		this.o1 = o1;
		this.o2 = o2;
		this.m = maps;
	
	}
	
	public int getAncho() {	return tamanioVentana.width;	}
	
	public int  getAlto() {	return tamanioVentana.height; }

	/*
	 * PARA LAS ACCIONES DEL MOUSE
	 */
	
	
    public class ConceptPanel extends JPanel {
        private JLabel message;
        public ConceptPanel(Float weight, String concept) {
        	// Color red green black alpha- alpgha 1 todo blanco
        	Color colorito = new Color(1.0f , 0.0f , 0.0f , weight);
        	message = new JLabel(concept);
        	setBackground(colorito);
        	add(message);
        }
      }
}
	