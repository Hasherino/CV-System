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

public class Employer extends GuiAgent {
    public ArrayList<Darbas> jobList = new ArrayList<>();
    AID rec;
    AID stats;
    public static final int ADDJOB = 1;
    public static final int ACCEPT = 2;
    public static final int REJECT = 3;
    public static final int GETSTATS = 4;
    
    EmployerGUI myGui = null;
    
    @Override 
    public void setup() { 
        System.out.println("A["+getLocalName()+"] GUI starting");
        
        try {
            if (SearchForService("employer") != null)
                doDelete();
        } catch (FIPAException ex) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        myGui = new EmployerGUI(this);
        myGui.setVisible(true);
        

        
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName( getAID() );    
        
        ServiceDescription sd  = new ServiceDescription();
        sd.setType("employer");
        sd.setName("employer-"+getLocalName() );
        dfd.addServices(sd);
        
        try 
        {  
            DFService.register( this, dfd );  
        }
        catch (FIPAException fe) {}   
        
        try {
            rec = SearchForService("employee");
            if (rec == null) {
                addBehaviour(new SubscribeServiceProviders("employee"));
                addBehaviour(new ServiceRegistrationNotification());
            }
            stats = SearchForService("stats");
            if (stats == null) {
                addBehaviour(new SubscribeServiceProviders("stats"));
                addBehaviour(new ServiceRegistrationNotification());
            }
        } catch (FIPAException ex) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
        msg.addReceiver(rec);
        
        boolean send = true;
        
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
                
                ACLMessage inf = new ACLMessage(ACLMessage.INFORM);
                inf.clearAllReceiver();
                inf.addReceiver(stats);
                inf.setContent("new job;"+job.getAtlyginimas());
                send(inf);
                
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
                
                ACLMessage inf = new ACLMessage(ACLMessage.INFORM);
                inf.clearAllReceiver();
                inf.addReceiver(stats);
                inf.setContent("acceptance");
                send(inf);
                
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
            case Employer.GETSTATS: {
                ACLMessage req = new ACLMessage(ACLMessage.REQUEST);
                req.clearAllReceiver();
                req.addReceiver(stats);
                req.setContent("stats");
                send(req);
                send = false;
                
                break;
            } 
            default: {
                break;
            }
        }
        
        if (send)
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
                if (message.getContent().startsWith("<html>")) {
                    myGui.statsLabel.setText(message.getContent());
                } else {
                    try {
                        ContentElement c = cm.extractContent(message);

                        if (c instanceof ApplyMsg) {
                            if (myGui.FindJobByID(((ApplyMsg) c).getId()) != null) {
                                myGui.AddApplication((ApplyMsg) c);

                                ACLMessage inf = new ACLMessage(ACLMessage.INFORM);
                                inf.clearAllReceiver();
                                inf.addReceiver(stats);
                                inf.setContent("application");
                                send(inf);
                            } else {
                                System.out.println("A["+getLocalName()+"] Job does not exist: "+((ApplyMsg) c).getId());
                            }
                        }
                    }
                    catch (Codec.CodecException | OntologyException ex) {
                        System.out.println("A["+getLocalName()+"] Ontology parsing error: "+ex.getMessage());
                    }
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
                                if (sd.getType().equals("stats"))
                                    stats = dfd.getName();
                                else
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