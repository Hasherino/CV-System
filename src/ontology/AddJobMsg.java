package ontology;

/**
* Protege name: AddJobMsg
* @author ontology bean generator
* @version 2022/05/18, 14:02:20
*/
public class AddJobMsg implements AddJobMsgIf {

  private static final long serialVersionUID = -8993252316815084752L;

  private String _internalInstanceName = null;

  public AddJobMsg() {
    this._internalInstanceName = "";
  }

  public AddJobMsg(String instance_name) {
    this._internalInstanceName = instance_name;
  }

  public String toString() {
    return _internalInstanceName;
  }

   /**
   * Protege name: darbas
   */
   private Darbas darbas;
   public void setDarbas(Darbas value) { 
    this.darbas=value;
   }
   public Darbas getDarbas() {
     return this.darbas;
   }

}
