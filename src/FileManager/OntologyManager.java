package FileManager;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;
import org.semanticweb.owlapi.util.AutoIRIMapper;

public class OntologyManager {

	/*
	 * Para gestionar la ontologia.
	 */
	//public OWLOntologyManager m ;
	
	public OWLOntology o;
	
	/*
	 *  	LabelExtractor
	 */
	public static LabelExtractor le = new LabelExtractor();
	
	public IRI iri;
	
	public String jerarquia = "";
	
	public ArrayList<Concept> conceptos = new ArrayList<Concept>();
	/*
	 * Asignamos la IRI mediante una URI
	 */
	public void setIri(URI rutaOnto){
		this.iri = IRI.create(rutaOnto);	
	}
	
	/*
	 * Obtenemos la IRI asignada.
	 */
	public IRI getIri(){
		return this.iri;
	}
	
	
	/**
	 * @return the jerarquia
	 */
	public String getJerarquia() {
		return jerarquia;
	}

	/**
	 * @param jerarquia the jerarquia to set
	 */
	public void setJerarquia(String jerarquia) {
		this.jerarquia = jerarquia;
	}

	
	
	/**
	 * @return the o
	 */
	public OWLOntology getO() {
		return o;
	}

	/**
	 * @param o the o to set
	 */
	public void setO(OWLOntology o) {
		this.o = o;
	}

	/**
	 * @return the conceptos
	 */
	public ArrayList<Concept> getConceptos() {
		return conceptos;
	}

	/**
	 * @param conceptos the conceptos to set
	 */
	public void setConceptos(ArrayList<Concept> conceptos) {
		this.conceptos = conceptos;
	}

	/*
	 * Constructor
	 */
	public OntologyManager() {
		OWLOntologyManager m =	OWLManager.createOWLOntologyManager();
		m.addIRIMapper	(	new AutoIRIMapper (
									new File("materializedOntologies"), true
								)
							);
	}
	
	
	
	private static String labelFor(OWLEntity clazz, OWLOntology o) {
		
		Set<OWLAnnotation> annotations = clazz.getAnnotations(o);
		for (OWLAnnotation anno : annotations) {
			String result = anno.accept(le);
			if (result != null) {
				return result;
			}
		}
		return clazz.getIRI().toString();
	}
	

	/*
	 * Para imprimir la jerarquía
	 */
	
	public void imprimirJerarquia(){
		
		try {
			
			
			OWLOntologyManager m =	OWLManager.createOWLOntologyManager();
			m.addIRIMapper	(	new AutoIRIMapper (
										new File("materializedOntologies"), true
									)
								);
			// Se carga desde la IRI del gestor.
			this.o = m.loadOntologyFromOntologyDocument(this.iri);
			OWLDataFactory df = OWLManager.getOWLDataFactory();
			
			OWLClass clazz = df.getOWLThing();
			// CAMBIAR EL NIVEL PARA VER EN QUÉ VARÍA.
			int i = 10;
			// Parece que siempre se crea así el razonador
			OWLReasonerFactory reasonerFactory = new StructuralReasonerFactory();
			OWLReasoner reasoner = reasonerFactory.createReasoner(o);
			
			/*
			 * Llamada a un método auxiliar que ejecuta el bucle.
			 */
			imprimirJerarquiaAux(reasoner , clazz, i, new HashSet<OWLClass>());
			
			
		} catch (OWLException e) {
			e.printStackTrace();
		}


	} // imprimirJerarquia
	
	public void imprimirJerarquiaAux(OWLReasoner r, OWLClass clazz, int level, Set<OWLClass> visited) throws OWLException {
		if (!visited.contains(clazz) && r.isSatisfiable(clazz)) {
			visited.add(clazz);
			for (int i = 0; i < level * 4; i++) {
				this.jerarquia += (" ");
			}
			this.jerarquia += (labelFor(clazz, r.getRootOntology())) + "\n";
			NodeSet<OWLClass> classes = r.getSubClasses(clazz, true);
			for (OWLClass child : classes.getFlattened()) {
				imprimirJerarquiaAux(r, child, level + 1,visited);
			}
		}

	}// imprimirJerarquiaAux
	
	
	public String listarConceptos(){

		String listaConceptos = "";
		OWLOntologyManager m =	OWLManager.createOWLOntologyManager();
		m.addIRIMapper	(	new AutoIRIMapper (
									new File("materializedOntologies"), true
								)
							);
		// Se carga desde la IRI del gestor.
		//OWLOntology o ;
		try {
			this.o = m.loadOntologyFromOntologyDocument(this.iri);
			// Imprimir clases
			for (OWLClass cls : this.o.getClassesInSignature()){
				listaConceptos += "IRI : " + cls.getIRI() + " \n";
				listaConceptos += "Entidad" + cls.getIRI().getFragment() + "\n";
				listaConceptos += "Tipo : " + cls.getEntityType() + " \n";
				listaConceptos += "-----------------------\n";
			}

		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listaConceptos;
		
	}
	
	
	
	public String obtenerListaConceptos(){

		OWLOntologyManager m =	OWLManager.createOWLOntologyManager();
		m.addIRIMapper	(	new AutoIRIMapper (
									new File("materializedOntologies"), true
								)
							);
		String listaConceptos = "";
		
		this.conceptos = new ArrayList<Concept>();
		
		try {
			this.o = m.loadOntologyFromOntologyDocument(this.iri);
			
			for (OWLClass cls : this.o.getClassesInSignature()){
				
				this.conceptos.add( new Concept((String) cls.getIRI().getFragment(), 0,0) );
				listaConceptos += cls.getIRI().getFragment() + "\n";
			}

		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listaConceptos;
	}
	
}
