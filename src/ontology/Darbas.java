package ontology;

/**
* Protege name: Darbas
* @author ontology bean generator
* @version 2022/05/18, 14:02:20
*/
public class Darbas implements DarbasIf {

  private static final long serialVersionUID = -8993252316815084752L;

  private String _internalInstanceName = null;

  public Darbas() {
    this._internalInstanceName = "";
  }

  public Darbas(String instance_name) {
    this._internalInstanceName = instance_name;
  }

  public String toString() {
    return _internalInstanceName;
  }

   /**
   * Protege name: patirtis
   */
   private int patirtis;
   public void setPatirtis(int value) { 
    this.patirtis=value;
   }
   public int getPatirtis() {
     return this.patirtis;
   }

   /**
   * Protege name: id
   */
   private int id;
   public void setId(int value) { 
    this.id=value;
   }
   public int getId() {
     return this.id;
   }

   /**
   * Protege name: atlyginimas
   */
   private float atlyginimas;
   public void setAtlyginimas(float value) { 
    this.atlyginimas=value;
   }
   public float getAtlyginimas() {
     return this.atlyginimas;
   }

   /**
   * Protege name: pavadinimas
   */
   private String pavadinimas;
   public void setPavadinimas(String value) { 
    this.pavadinimas=value;
   }
   public String getPavadinimas() {
     return this.pavadinimas;
   }

}
