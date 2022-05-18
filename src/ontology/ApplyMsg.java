package ontology;

/**
* Protege name: ApplyMsg
* @author ontology bean generator
* @version 2022/05/18, 14:02:20
*/
public class ApplyMsg implements ApplyMsgIf {

  private static final long serialVersionUID = -8993252316815084752L;

  private String _internalInstanceName = null;

  public ApplyMsg() {
    this._internalInstanceName = "";
  }

  public ApplyMsg(String instance_name) {
    this._internalInstanceName = instance_name;
  }

  public String toString() {
    return _internalInstanceName;
  }

   /**
   * Protege name: darbuotojas
   */
   private Darbuotojas darbuotojas;
   public void setDarbuotojas(Darbuotojas value) { 
    this.darbuotojas=value;
   }
   public Darbuotojas getDarbuotojas() {
     return this.darbuotojas;
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

}
