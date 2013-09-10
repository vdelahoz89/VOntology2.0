package FileManager;
public class Map{

	/*
	 * Atributos para representar el mapeo entre 
	 * dos conceptos de una ontología:
	 *  - Nombre de la primera entidad relacionada
	 *  - Nombre de la segunda entidad relacionada
	 *  - Tipo de relación que existe entre ellas
	 *  - Peso de la relación en si
	 */
	
	public String entity1 = "";
	
	public String entity2 = "";
	
	public String relation = "";
	
	public double measure = 0.0;
	
	/*
	 * Constructor de la clase por defecto.
	 */
	
	public Map (){
		super();
	}
	
	/*
	 * Constructor de la clase con parámetros.
	 */
	
	public Map (String name, String entity1, String entity2, String relation, float measure){
		this.entity1 = entity1;
		this.entity2 = entity2;
		this.relation = relation;
		this.measure = measure;	
	}
	
	/*
	 * Métodos get y set.
	 */
	
	public String getEntity1() {
		return entity1;
	}

	public void setEntity1(String entity1) {
		this.entity1 = entity1;
	}

	public String getEntity2() {
		return entity2;
	}

	public void setEntity2(String entity2) {
		this.entity2 = entity2;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public double getMeasure() {
		return measure;
	}

	public void setMeasure(double d) {
		this.measure = d;
	}
	
}
