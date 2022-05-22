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

public class Employer extends GuiAgent {
    public ArrayList<Darbas> jobList = new ArrayList<>();

    public static final int ADDJOB = 1;
    public static final int ACCEPT = 2;
    public static final int REJECT = 3;
    
    EmployerGUI myGui = null;
    
    @Override 
    public void setup() { 
        System.out.println("A["+getLocalName()+"] GUI starting");
        
        myGui = new EmployerGUI(this);
        myGui.setVisible(true);
        
        addBehaviour(new WaitForMessages(this));
    } 
    
    @Override
    protected void onGuiEvent(GuiEvent ge) {
        int cmd = ge.getType();
        
        Ontology onto = JobOntology.getInstance();
        Codec codec = new SLCodec();

        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.setLanguage(codec.getName());
        msg.setOntology(onto.getName());
        ContentManager cm = getContentManager();
        cm.registerLanguage(codec);
        cm.registerOntology(onto);

        msg.clearAllReceiver();
        msg.addReceiver(new AID("Employee", AID.ISLOCALNAME));
        
        switch (cmd) {
            case Employer.ADDJOB: {   
                Darbas job = new Darbas();
                job.setPavadinimas((String) ge.getParameter(0));
                job.setAtlyginimas(Float.parseFloat((String) ge.getParameter(1)));
                job.setPatirtis(Integer.parseInt((String) ge.getParameter(2)));
                job.setId(jobList.size());
                
                AddJobMsg content = new AddJobMsg();
                content.setDarbas(job);
                
                jobList.add(job);
                try {
                cm.fillContent(msg, content);
                }
                catch (Codec.CodecException | OntologyException ex) {
                    System.out.println("A["+getLocalName()+"] Error while building message: "+ex.getMessage());
                }
                break;
            }
            case Employer.ACCEPT: {
                AnswerMsg content = new AnswerMsg();
                content.setAccepted(true);
                content.setId((int) ge.getParameter(0));
                try {
                cm.fillContent(msg, content);
                }
                catch (Codec.CodecException | OntologyException ex) {
                    System.out.println("A["+getLocalName()+"] Error while building message: "+ex.getMessage());
                }
                break;
            }
            case Employer.REJECT: {
                AnswerMsg content = new AnswerMsg();
                content.setAccepted(false);
                content.setId((int) ge.getParameter(0));
                try {
                cm.fillContent(msg, content);
                }
                catch (Codec.CodecException | OntologyException ex) {
                    System.out.println("A["+getLocalName()+"] Error while building message: "+ex.getMessage());
                }
                break;
            }
            default: {
                break;
            }
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
                    
                    if (c instanceof ApplyMsg) {
                        if (myGui.FindJobByID(((ApplyMsg) c).getId()) != null) {
                            myGui.AddApplication((ApplyMsg) c);
                        } else {
                            System.out.println("A["+getLocalName()+"] Job does not exist: "+((ApplyMsg) c).getId());
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