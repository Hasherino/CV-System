package cv.sistema;

import jade.gui.GuiEvent;
import java.util.ArrayList;
import ontology.ApplyMsg;
import ontology.Darbas;
import ontology.Darbuotojas;

public class EmployerGUI extends javax.swing.JFrame {
    private final Employer myAgent;
    private ArrayList<ApplyMsg> applicationList = new ArrayList<>();

    public EmployerGUI(Employer a) {
        myAgent = a;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        salary = new javax.swing.JTextField();
        name = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        experience = new javax.swing.JTextField();
        addJobButton = new javax.swing.JButton();
        infoLabel = new javax.swing.JLabel();
        acceptEmployeeButton = new javax.swing.JButton();
        RejectEmployeeButton = new javax.swing.JButton();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Pridėti naują darbą");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Atlyginimas");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Reikalinga patirtis (metai)");
        jLabel3.setToolTipText("");

        salary.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salaryActionPerformed(evt);
            }
        });

        name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameActionPerformed(evt);
            }
        });

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Pavadinimas");

        addJobButton.setText("Pridėti");
        addJobButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addJobButtonActionPerformed(evt);
            }
        });

        infoLabel.setFont(new java.awt.Font("DejaVu Sans", 0, 14)); // NOI18N
        infoLabel.setToolTipText("");

        acceptEmployeeButton.setText("Priimti");
        acceptEmployeeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acceptEmployeeButtonActionPerformed(evt);
            }
        });

        RejectEmployeeButton.setText("Atmesti");
        RejectEmployeeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RejectEmployeeButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(infoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(name, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(salary, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                            .addComponent(experience)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(acceptEmployeeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 146, Short.MAX_VALUE)
                        .addComponent(RejectEmployeeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(addJobButton, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(156, 156, 156))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(salary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(experience, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addJobButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(infoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(acceptEmployeeButton)
                    .addComponent(RejectEmployeeButton))
                .addGap(8, 8, 8))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addJobButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addJobButtonActionPerformed
        GuiEvent ge = new GuiEvent(this, Employer.ADDJOB);
        if (!name.getText().equals("") && isNumber(salary.getText()) && isInteger(experience.getText())) {
            ge.addParameter(name.getText());
            ge.addParameter(salary.getText());
            ge.addParameter(experience.getText());
            myAgent.postGuiEvent(ge);
            infoLabel.setText("Darbas sėkmingai pridėtas");
        }
        else {
            infoLabel.setText("Užpildykite visus laukus. (Atlyginimas turi būti skaičius, patirtis - skaičius be kablelio)");
        }
    }//GEN-LAST:event_addJobButtonActionPerformed

    public void AddApplication(ApplyMsg application) {
        applicationList.add(application);
        if (applicationList.size() == 1) {
            SetInfoText(application);
        }
    }
    
    private void SetInfoText(ApplyMsg application) {
        Darbuotojas employee = application.getDarbuotojas();
        Darbas job = FindJobByID(application.getId());
        infoLabel.setText("<html>"+employee.getVardas()+" "+employee.getPavarde()+" applied for:<br/>"+job.getPavadinimas());
    }
    
    public Darbas FindJobByID(int id) {
        for (Darbas job : myAgent.jobList) {
            if (job.getId() == id) {
                return job;
            }
        }
        return null;
    }
    
    private static boolean isNumber(String string) {
        if (string == null) {
            return false;
        }
        try {
            Float.parseFloat(string);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    
    private static boolean isInteger(String string) {
        if (string == null) {
            return false;
        }
        try {
            Integer.parseInt(string);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    
    private void salaryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salaryActionPerformed
        // Čia nieko nereikia
    }//GEN-LAST:event_salaryActionPerformed

    private void acceptEmployeeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acceptEmployeeButtonActionPerformed
        GuiEvent ge = new GuiEvent(this, Employer.ACCEPT);
        ApplyMsg application = applicationList.get(0);
        ge.addParameter(application.getId());
        myAgent.postGuiEvent(ge);
        applicationList.remove(0);
        
        if (!applicationList.isEmpty()) {
            SetInfoText(applicationList.get(0));
        }
    }//GEN-LAST:event_acceptEmployeeButtonActionPerformed

    private void RejectEmployeeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RejectEmployeeButtonActionPerformed
        GuiEvent ge = new GuiEvent(this, Employer.REJECT);
        ApplyMsg application = applicationList.get(0);
        ge.addParameter(application.getId());
        myAgent.postGuiEvent(ge);
        applicationList.remove(0);
        
        if (!applicationList.isEmpty()) {
            SetInfoText(applicationList.get(0));
        }
    }//GEN-LAST:event_RejectEmployeeButtonActionPerformed

    private void nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameActionPerformed
        // Čia irgi nieko nereikia
    }//GEN-LAST:event_nameActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton RejectEmployeeButton;
    private javax.swing.JButton acceptEmployeeButton;
    private javax.swing.JButton addJobButton;
    private javax.swing.JTextField experience;
    private javax.swing.JLabel infoLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField name;
    private javax.swing.JTextField salary;
    // End of variables declaration//GEN-END:variables
}
