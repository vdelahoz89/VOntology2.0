package FileManager;

public class Concept {

	public String entity = "";
	
	public int posX;
	
	public int posY;

	public Concept(String entity, int posX, int posY) {
		super();
		this.entity = entity;
		this.posX = posX;
		this.posY = posY;
	}

	public Concept() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the entity
	 */
	public String getEntity() {
		return entity;
	}

	/**
	 * @param entity the entity to set
	 */
	public void setEntity(String entity) {
		this.entity = entity;
	}

	/**
	 * @return the posX
	 */
	public int getPosX() {
		return posX;
	}

	/**
	 * @param posX the posX to set
	 */
	public void setPosX(int posX) {
		this.posX = posX;
	}

	/**
	 * @return the posY
	 */
	public int getPosY() {
		return posY;
	}

	/**
	 * @param posY the posY to set
	 */
	public void setPosY(int posY) {
		this.posY = posY;
	}
	
	
	
}
