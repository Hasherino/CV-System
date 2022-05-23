package cv.sistema;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Stats extends Agent {
    int applicationCount = 0;
    int employeesAcceptedCount = 0;
    float averageSalary = 0;
    int totalJobsRegistered = 0;
    
    @Override 
    public void setup() { 
        System.out.println("A["+getLocalName()+"] GUI starting");
        
        try {
            if (SearchForService("stats") != null)
                doDelete();
        } catch (FIPAException ex) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName( getAID() );    
        
        ServiceDescription sd  = new ServiceDescription();
        sd.setType("stats");
        sd.setName("stats-"+getLocalName() );
        dfd.addServices(sd);
        
        try 
        {  
            DFService.register( this, dfd );  
        }
        catch (FIPAException fe) {}   
        
        addBehaviour(new WaitForMessages(this));
    } 
    
    private class WaitForMessages extends CyclicBehaviour {
        public WaitForMessages(Agent a) {
            super(a);
        }        
        
        @Override
        public void action() {
            ACLMessage message = myAgent.receive();
            
            if (message != null) {
                String content = message.getContent();
                if (message.getPerformative() == ACLMessage.INFORM) {
                    if (content.equals("application")) {
                        applicationCount++;
                    } else if (content.equals("acceptance")) {
                        employeesAcceptedCount++;
                    } else if (content.startsWith("new job")) {
                        float salary = Float.parseFloat(content.split(";")[1]);
                        averageSalary = (averageSalary * totalJobsRegistered + salary) / (totalJobsRegistered + 1);
                        totalJobsRegistered++;
                    }
                } else {
                    AID rec = message.getSender();
                    
                    String stats = "<html>Aplikacijų kiekis: "+applicationCount+
                                   "<br/>Priimtų darbuotojų kiekis: "+employeesAcceptedCount+
                                   "<br/>Užregistruotų darbų kiekis: "+totalJobsRegistered+
                                   "<br/>Vidutinis atlyginimas: "+averageSalary;
                    
                    ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                    msg.clearAllReceiver();
                    msg.addReceiver(rec);
                    msg.setContent(stats);
                    send(msg);
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
    
    @Override  
    public void takeDown() { 
        System.out.println("A["+getLocalName()+"] is being removed"); 
        
        try { DFService.deregister(this); }
        catch (FIPAException e) {}
    }
}