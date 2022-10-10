/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui;

import com.formdev.flatlaf.FlatLightLaf;
import static gui.Home.setTime;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.MySQL;
import java.sql.ResultSet;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.TimeCalculator;

/**
 *
 * @author Administrator
 */
public class MarkAttendance extends javax.swing.JFrame {

    Date logedTime;
    Home parent;
    Thread endTime, t;
    static int attendace_status;

    SimpleDateFormat sdf1 = new SimpleDateFormat("hh:mm:ss aa  yyyy-MM-dd EEEE");
    SimpleDateFormat sdf5 = new SimpleDateFormat("HH:mm:ss  yyyy-MM-dd");
    SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
    SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sdf4 = new SimpleDateFormat("hh:mm:ss aa");

    public void loadTable() {

        try {
            ResultSet rs = MySQL.search("SELECT * FROM `attendence` WHERE `user_id`= '" + SignIn.userId + "'  AND MONTH(`working_date`) = MONTH(CURDATE()) ");

            DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
            dtm.setRowCount(0);

            while (rs.next()) {
                Vector v = new Vector();
                v.add(rs.getString("working_date"));
                v.add(sdf4.format(sdf2.parse(rs.getString("arrival_time"))));

                if (rs.getString("departure_time") != null) {
                    String[] arrOfATime = rs.getString("arrival_time").split(":");

                    String[] arrOfDTime = rs.getString("departure_time").split(":");

                    TimeCalculator start = new TimeCalculator(Integer.parseInt(arrOfATime[0]), Integer.parseInt(arrOfATime[1]), Integer.parseInt(arrOfATime[2]));
                    TimeCalculator stop = new TimeCalculator(Integer.parseInt(arrOfDTime[0]), Integer.parseInt(arrOfDTime[1]), Integer.parseInt(arrOfDTime[2]));
                    TimeCalculator def;

                    def = TimeCalculator.difference(start, stop);

                    v.add(sdf4.format(sdf2.parse(rs.getString("departure_time"))));
                    v.add(def.hours + ":" + def.minutes + ":" + def.seconds);

                } else {
                    v.add("Not Marked.");
                    v.add("--:--:--");
                }
                dtm.addRow(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        this.dispose();
        parent.dispose();
        SignIn si = new SignIn();
        si.setVisible(true);
    }

    public void cheking() {
        try {
            ResultSet rs1 = MySQL.search("SELECT * FROM `attendence` WHERE `user_id` = " + SignIn.userId + " ORDER BY `working_date` DESC ");
            if (rs1.next()) {
                if (sdf3.format(sdf3.parse(rs1.getString("working_date"))).equals(sdf3.format(logedTime))) {
                    jButton1.setEnabled(false);
                    jLabel1.setText(sdf1.format(sdf5.parse(rs1.getString("arrival_time")+"  "+rs1.getString("working_date"))));

                    if (rs1.getString("departure_time") != null) {
                        jButton2.setEnabled(false);
                        reset();
                    } else {
                        endTime = setTime(jLabel4, sdf1);
                        jButton2.setEnabled(true);
                    }

                } else {
                    jLabel1.setText(sdf1.format(logedTime).toString());
                    jButton2.setEnabled(false);
                }

            } else {
                jLabel1.setText(sdf1.format(logedTime).toString());
                jButton2.setEnabled(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Thread CalculateTimeOut() {

        Runnable rp = new Runnable() {
            @Override
            public void run() {

                for (int i = 10; i >= 0; i--) {
                    if (i == 0) {
                        close();
                    } else {
                        jLabel5.setText("Automatically Logout In " + String.valueOf(i) + " Second(s).");

                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }

            }
        };
        Thread t = new Thread(rp);
        t.start();
        return t;
    }

    public void CalculateTimeOut2() {

        Runnable rp = new Runnable() {
            @Override
            public void run() {

                for (int i = 60 * 3; i >= 0; i--) {
                    if (i == 0) {
                        close();
                    } else {
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }

            }
        };
        Thread t = new Thread(rp);
        t.start();
    }

    public void reset() {
        if (endTime != null) {
            endTime.stop();
        }
        jLabel1.setText("--:--:--");
        jLabel4.setText("--:--:--");
        jButton1.setEnabled(false);
        jButton2.setEnabled(false);
    }

    /**
     * Creates new form MarkAttendance
     */
    public MarkAttendance() {
        initComponents();
    }

    public MarkAttendance(Home parent, Date logedTime) {
        initComponents();
        parent.jLabel4.setText("Welcome");
        t = CalculateTimeOut();
        this.logedTime = logedTime;
        this.parent = parent;
        cheking();
        setTime(jLabel6, sdf1);
        loadTable();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Mark Attendance");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 255), 6, true));

        jLabel6.setFont(new java.awt.Font("Microsoft New Tai Lue", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(204, 0, 0));
        jLabel6.setText("09:45:42 AM   2022-08-13   Monday");
        jLabel6.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Loged Time ");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Start working period");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("End working period");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("-------------------");

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton1.setText("Mark");
        jButton1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 255), 3, true));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton2.setText("Mark");
        jButton2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 255), 3, true));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(204, 0, 0));
        jLabel5.setText("Auto Logout--------------------------------");

        jButton3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton3.setText("Stop");
        jButton3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 0, 0), 3, true));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton4.setText("Log Out");
        jButton4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 51), 3, true));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jTable1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "Arrival Time", "Departure Time", "Hours"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setSelectionBackground(new java.awt.Color(229, 229, 255));
        jTable1.setSelectionForeground(new java.awt.Color(0, 0, 204));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 26, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(141, 141, 141))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel6)
                .addGap(2, 2, 2)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            // TODO add your handling code here:
            ResultSet rs1 = MySQL.search("SELECT * FROM `attendence` WHERE `user_id` = '" + SignIn.userId + "' ORDER BY `working_date` DESC ");
            if (rs1.next()) {

                if (sdf3.format(sdf3.parse(rs1.getString("working_date"))).equals(sdf3.format(logedTime))) {
                    JOptionPane.showMessageDialog(this, "You marked attendents before.", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    MySQL.iud("INSERT INTO `attendence`(`working_date`,`arrival_time`,`user_id`) VALUES('" + sdf3.format(new Date()) + "','" + sdf2.format(logedTime) + "'," + SignIn.userId + ")");
                    JOptionPane.showMessageDialog(this, "Marked start working time .", "Warning", JOptionPane.WARNING_MESSAGE);
                    cheking();
                    loadTable();
                    attendace_status = 1;
                }
            } else {
                MySQL.iud("INSERT INTO `attendence`(`working_date`,`arrival_time`,`user_id`) VALUES('" + sdf3.format(new Date()) + "','" + sdf2.format(logedTime) + "'," + SignIn.userId + ")");
                JOptionPane.showMessageDialog(this, "Marked start working time .", "Warning", JOptionPane.WARNING_MESSAGE);
                cheking();
                loadTable();
                attendace_status = 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:

        t.stop();
        CalculateTimeOut2();
        jLabel5.setText("Please press logout button.");
        jButton3.setEnabled(false);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        close();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            ResultSet rs1 = MySQL.search("SELECT * FROM `attendence` WHERE `user_id` = '" + SignIn.userId + "' ORDER BY `working_date` DESC ");
            if (rs1.next()) {
                if (sdf3.format(sdf3.parse(rs1.getString("working_date"))).equals(sdf3.format(logedTime))) {
                    MySQL.iud("UPDATE `attendence` SET `departure_time` = '" + sdf2.format(new Date()) + "' WHERE `user_id` =  " + SignIn.userId + " AND `arrival_time` = '" + rs1.getString("arrival_time") + "' ");
                    JOptionPane.showMessageDialog(this, "Marked End working period .", "Warning", JOptionPane.WARNING_MESSAGE);
                    reset();
                    loadTable();
                    attendace_status = 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        t.stop();
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        FlatLightLaf.setup();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MarkAttendance().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
