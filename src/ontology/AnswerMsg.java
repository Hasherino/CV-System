package ontology;

/**
* Protege name: AnswerMsg
* @author ontology bean generator
* @version 2022/05/18, 14:02:20
*/
public class AnswerMsg implements AnswerMsgIf {

  private static final long serialVersionUID = -8993252316815084752L;

  private String _internalInstanceName = null;

  public AnswerMsg() {
    this._internalInstanceName = "";
  }

  public AnswerMsg(String instance_name) {
    this._internalInstanceName = instance_name;
  }

  public String toString() {
    return _internalInstanceName;
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
   * Protege name: accepted
   */
   private boolean accepted;
   public void setAccepted(boolean value) { 
    this.accepted=value;
   }
   public boolean getAccepted() {
     return this.accepted;
   }

}
