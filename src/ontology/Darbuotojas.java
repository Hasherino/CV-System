package ontology;

/**
* Protege name: Darbuotojas
* @author ontology bean generator
* @version 2022/05/18, 14:02:20
*/
public class Darbuotojas implements DarbuotojasIf {

  private static final long serialVersionUID = -8993252316815084752L;

  private String _internalInstanceName = null;

  public Darbuotojas() {
    this._internalInstanceName = "";
  }

  public Darbuotojas(String instance_name) {
    this._internalInstanceName = instance_name;
  }

  public String toString() {
    return _internalInstanceName;
  }

   /**
   * Protege name: vardas
   */
   private String vardas;
   public void setVardas(String value) { 
    this.vardas=value;
   }
   public String getVardas() {
     return this.vardas;
   }

   /**
   * Protege name: pavarde
   */
   private String pavarde;
   public void setPavarde(String value) { 
    this.pavarde=value;
   }
   public String getPavarde() {
     return this.pavarde;
   }

}
