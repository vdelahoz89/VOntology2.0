package FileManager;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;

import org.semanticweb.owl.align.Alignment;
import org.semanticweb.owl.align.AlignmentException;
import org.semanticweb.owl.align.Cell;

import fr.inrialpes.exmo.align.impl.URICell;
import fr.inrialpes.exmo.align.parser.AlignmentParser;

public class AlignmentManager {

	/*
	 * String con la lista del resultado de los matches para imprimir.
	 */
	public String resultadoAlignment = "";
	
	/*
	 * Objeto Enumeration<Cell> con los resultados del alignment.
	 */
	public Enumeration<Cell> resultado;
	
	/*
	 * En principio siempre es fijo.
	 */
	public AlignmentParser aparser = new AlignmentParser(0);
	
	
	Alignment alignment = null;
	
	
	public ArrayList<Map> mappingList = new ArrayList<Map>();
	
	/*
	 * Metodo para obtener el alignment.
	 */
	public Alignment obtenerAlignment(String rutaAlignment){
		
		
		try {
			this.alignment = this.aparser.parse(new FileReader(rutaAlignment));
				
		} catch (FileNotFoundException | AlignmentException e) {
			
			e.printStackTrace();
		}
		return this.alignment;
		
		
	}
	
	public Enumeration<Cell> obtenerListaMatches(Alignment alignment){
		Alignment alignmentClon = (Alignment) alignment.clone();
		this.resultado = alignmentClon.getElements();
		return resultado;
	}
	
	/*
	 * Método para obtener la lista de relaciones.
	 */
	
	public String obtenerMatches(Alignment alignment) throws AlignmentException{
		
		Alignment alignmentClon = (Alignment) alignment.clone();
		this.resultado = alignmentClon.getElements();
		alignmentClon.getExtensions();
		this.resultadoAlignment += 	"Vamos a mostrar los resultados entre las ontologías \n" 
									+ alignmentClon.getFile1() + "\n"
									+ alignmentClon.getFile2() + "\n ### ### ### \n";
		this.resultadoAlignment += 	"ONTO 1 URI Path: " + alignmentClon.getOntology1URI().getPath() + "\n";
		this.resultadoAlignment += 	"ONTO 2 URI Path: " + alignmentClon.getOntology2URI().getPath() + "\n";
        this.resultadoAlignment += "### ### ### \n\n\n";
		
		while (this.resultado.hasMoreElements()){
		
			URICell cell = (URICell) this.resultado.nextElement();
	    	try {
	    		this.resultadoAlignment += cell.getObject1AsURI().getFragment();
	    		this.resultadoAlignment += "\n";
		        this.resultadoAlignment += cell.getObject1().toString();
		        this.resultadoAlignment += "\n";
		    	this.resultadoAlignment += cell.getObject2AsURI().getFragment();
		    	this.resultadoAlignment += "\n";
		        this.resultadoAlignment += cell.getObject2().toString();
		        this.resultadoAlignment += "\n";
		        this.resultadoAlignment += cell.getRelation().getRelation();
		        this.resultadoAlignment += "\n";
		        this.resultadoAlignment += Double.toString(cell.getStrength());
		        this.resultadoAlignment += "\n";
		        this.resultadoAlignment += "### ### ### \n";
			} catch (AlignmentException e) {
				e.printStackTrace();		
			} // try-catch
	  
		} // fin while
		
		return resultadoAlignment;
	
	}
	
	
	public void ordenarMatching (Alignment alignment){
		
		Alignment alignmentClon = (Alignment) alignment.clone();
		this.resultado = alignmentClon.getElements();
		alignmentClon.getExtensions();
			
		Map map;
		
		while (this.resultado.hasMoreElements()){
			map = new Map();
			URICell cell = (URICell) this.resultado.nextElement();
	    	try {
	    		map.setEntity1(cell.getObject1AsURI().getFragment());
	    		map.setEntity2(cell.getObject2AsURI().getFragment());
	    		map.setMeasure(cell.getStrength());
	    		map.setRelation(cell.getRelation().getRelation());
	
	    		// La añado a la lista que luego ordenaré.
	    		
	    		this.mappingList.add(map);
	    		
			} catch (AlignmentException e) {
				e.printStackTrace();		
			} // try-catch
		} // fin while
		
		// Ordenamos la lista.
		order(this.mappingList,"mesasure");
		
	}
	
	
	/*
	 * Para ordenar un objeto List según una propiedad de los elementos que almacena.
	 */
	 @SuppressWarnings("unchecked")
	public void order(List<Map> lista, final String propiedad) {
		 
		 Collections.sort(lista, new Comparator<Object>() {
	   
			 public int compare(Object obj1, Object obj2) {
	    
				 @SuppressWarnings("rawtypes")
				Class clase = obj1.getClass();
				 String getter = "get" + Character.toUpperCase(propiedad.charAt(0)) + propiedad.substring(1);
				 try {
					 Method getPropiedad = clase.getMethod(getter);
	     
					 Object propiedad1 = getPropiedad.invoke(obj1);
					 Object propiedad2 = getPropiedad.invoke(obj2);
	     
					 if(propiedad1 instanceof Comparable && propiedad2 instanceof Comparable) {
						 Comparable prop1 = (Comparable)propiedad1;
						 Comparable prop2 = (Comparable)propiedad2;
						 
						 // Ordenación menor a mayor.
						 //return prop1.compareTo(prop2);
						 
						 // Ordenación mayor a menor.
						 return prop2.compareTo(prop1);
					 }	  
				 }
				 catch(Exception e) {
					 e.printStackTrace();
				 }
				 return 0;
			 }
		 });
	 } // order

	public AlignmentManager() {
		super();
	}

	/**
	 * @return the resultadoAlignment
	 */
	public String getResultadoAlignment() {
		return resultadoAlignment;
	}

	/**
	 * @param resultadoAlignment the resultadoAlignment to set
	 */
	public void setResultadoAlignment(String resultadoAlignment) {
		this.resultadoAlignment = resultadoAlignment;
	}

	/**
	 * @return the resultado
	 */
	public Enumeration<Cell> getResultado() {
		return resultado;
	}

	/**
	 * @param resultado the resultado to set
	 */
	public void setResultado(Enumeration<Cell> resultado) {
		this.resultado = resultado;
	}

	/**
	 * @return the aparser
	 */
	public AlignmentParser getAparser() {
		return aparser;
	}

	/**
	 * @param aparser the aparser to set
	 */
	public void setAparser(AlignmentParser aparser) {
		this.aparser = aparser;
	}

	/**
	 * @return the alignment
	 */
	public Alignment getAlignment() {
		return alignment;
	}

	/**
	 * @param alignment the alignment to set
	 */
	public void setAlignment(Alignment alignment) {
		this.alignment = alignment;
	}

	/**
	 * @return the mappingList
	 */
	public ArrayList<Map> getMappingList() {
		return mappingList;
	}

	/**
	 * @param mappingList the mappingList to set
	 */
	public void setMappingList(ArrayList<Map> mappingList) {
		this.mappingList = mappingList;
	}
	
	
	

}
