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
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.util.leap.Iterator;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import ontology.AddJobMsg;
import ontology.AnswerMsg;
import ontology.ApplyMsg;
import ontology.Darbas;
import ontology.JobOntology;

public class Employee extends GuiAgent {
    public ArrayList<Darbas> jobList = new ArrayList<>();
    AID rec;
    EmployeeGUI myGui = null;
    
    @Override 
    public void setup() { 
        System.out.println("A["+getLocalName()+"] GUI starting");
        
        try {
            if (SearchForService("employee") != null)
                doDelete();
        } catch (FIPAException ex) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        myGui = new EmployeeGUI(this);
        myGui.setVisible(true);
        
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName( getAID() );    
        
        ServiceDescription sd  = new ServiceDescription();
        sd.setType("employee");
        sd.setName("employee-"+getLocalName() );
        dfd.addServices(sd);
        
        try 
        {  
            DFService.register( this, dfd );  
        }
        catch (FIPAException fe) {}   
 
        try {
            rec = SearchForService("employer");
        if (rec == null) {
            addBehaviour(new SubscribeServiceProviders("employee"));
            addBehaviour(new ServiceRegistrationNotification());
        }
        } catch (FIPAException ex) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
        msg.addReceiver(rec);
        
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
    
    private AID SearchForService(String type) throws FIPAException {
        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd  = new ServiceDescription();
        sd.setType(type);        
        dfd.addServices(sd);

        DFAgentDescription[] result = DFService.search(this, dfd);

        System.out.println("A["+getLocalName()+"] found "+result.length + " results" );
        for (DFAgentDescription res : result) {
            AID a = res.getName();
            return a;
        }
        return null;
    }
    
    private class SubscribeServiceProviders extends OneShotBehaviour
    {       
        String type;
        
        public SubscribeServiceProviders(String type) {
            this.type = type;
        }
        
        @Override
        public void action()
        {
            System.out.println("A["+this.myAgent.getLocalName()+"] Requesting service providers");
            DFAgentDescription dfd = new DFAgentDescription();
            ServiceDescription sd = new ServiceDescription();
            sd.setType(type);
            dfd.addServices(sd);
            SearchConstraints sc = new SearchConstraints();
            sc.setMaxResults(Long.MAX_VALUE);

            send(DFService.createSubscriptionMessage(this.myAgent, getDefaultDF(), dfd, sc));
        }
    }
    
    private class ServiceRegistrationNotification extends CyclicBehaviour 
    {
        @Override
        public void action() 
        {
            ACLMessage msg = receive(MessageTemplate.MatchSender(getDefaultDF()));
                
            if (msg != null)
            {
                System.out.println("A["+getLocalName()+"] service provider message received.");
                try 
                {
                    DFAgentDescription[] dfds = DFService.decodeNotification(msg.getContent());
                    for (DFAgentDescription dfd : dfds) {
                        Iterator it = dfd.getAllServices();
                        if (!it.hasNext()) {
                            System.out.println("\tA["+getLocalName()+"] provider unregistered. IGNORING message ");
                        } else {
                            System.out.println("\tA["+getLocalName()+"] New provider FOUND: " + dfd.getName().getName());
                            ServiceDescription sd;
                            while (it.hasNext()) {
                                sd = (ServiceDescription) it.next();
                                System.out.println("\t\t Agent " + dfd.getName().getName() + " is providing: " + sd.getName() + " service, of type " + sd.getType());
                                rec = dfd.getName();
                            }
                        }
                    }
               }
               catch (FIPAException ex) {}
            }           
            block();   
        }  
    }
    
    @Override  
    public void takeDown() { 
        System.out.println("A["+getLocalName()+"] is being removed"); 
        
        try { DFService.deregister(this); }
        catch (FIPAException e) {}
    }
}