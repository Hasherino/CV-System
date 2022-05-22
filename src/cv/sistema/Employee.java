package cv.sistema;

import jade.content.ContentElement;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import java.util.ArrayList;
import ontology.AddJobMsg;
import ontology.AnswerMsg;
import ontology.ApplyMsg;
import ontology.Darbas;
import ontology.JobOntology;

public class Employee extends GuiAgent {
    public ArrayList<Darbas> jobList = new ArrayList<>();
    
    EmployeeGUI myGui = null;
    
    @Override 
    public void setup() { 
        System.out.println("A["+getLocalName()+"] GUI starting");
        
        myGui = new EmployeeGUI(this);
        myGui.setVisible(true);
        
        addBehaviour(new WaitForMessages(this));
    } 
    
    @Override
    protected void onGuiEvent(GuiEvent ge) {        
        Ontology onto = JobOntology.getInstance();
        Codec codec = new SLCodec();

        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.setLanguage(codec.getName());
        msg.setOntology(onto.getName());
        ContentManager cm = getContentManager();
        cm.registerLanguage(codec);
        cm.registerOntology(onto);

        msg.clearAllReceiver();
        msg.addReceiver(new AID("Employer", AID.ISLOCALNAME));
        
        ApplyMsg content = (ApplyMsg) ge.getParameter(0);
        
        try {
            cm.fillContent(msg, content);
        }
        catch (Codec.CodecException | OntologyException ex) {
            System.out.println("A["+getLocalName()+"] Error while building message: "+ex.getMessage());
        }

        send(msg);
    }
    
    private class WaitForMessages extends CyclicBehaviour {
        public WaitForMessages(Agent a) {
            super(a);
        }        
        
        @Override
        public void action() {
            Ontology onto = JobOntology.getInstance();
            Codec codec = new SLCodec();
            ContentManager cm = getContentManager();
            cm.registerLanguage(codec);
            cm.registerOntology(onto);
            
            ACLMessage message = myAgent.receive();
            
            if (message != null) {
                try {
                    ContentElement c = cm.extractContent(message);
                    
                    if (c instanceof AddJobMsg) {
                        myGui.AddJob(((AddJobMsg) c).getDarbas());
                    } if (c instanceof AnswerMsg) {
                        AnswerMsg answer = (AnswerMsg) c;
                        Darbas job = myGui.FindJobByID(answer.getId());
                        if (answer.getAccepted()) {
                            myGui.availableJobs.remove(job);
                            myGui.UpdateAvailableJobs();
                            myGui.infoLabel.setText("<html>Sveikiname, jūs esate priimtas į: <br/>"+job.getPavadinimas());
                        } else {
                            myGui.infoLabel.setText("Jūsų CV buvo atmestas: "+job.getPavadinimas());
                        }
                    }
                }
                catch (Codec.CodecException | OntologyException ex) {
                    System.out.println("A["+getLocalName()+"] Ontology parsing error: "+ex.getMessage());
                }
            }
            else {
                block(); 
            }
        }
    }
}