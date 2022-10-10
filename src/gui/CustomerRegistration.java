/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gui;

import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import model.MySQL;

/**
 *
 * @author Administrator
 */
public class CustomerRegistration extends javax.swing.JPanel {

    public void LoadCustomers() {
        try {
            ResultSet rs = MySQL.search("SELECT * FROM `customer` INNER JOIN `city` ON `customer`.`city_id`=`city`.`id` WHERE `customer`.`id` != 0 ORDER BY `customer`.`id` ASC ");

            DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
            dtm.setRowCount(0);

            while (rs.next()) {
                Vector v = new Vector();
                v.add(rs.getString("id"));
                v.add(rs.getString("customer.name"));
                v.add(rs.getString("customer.mobile"));
                v.add(rs.getString("city.name"));
                dtm.addRow(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void LoadCustomers(String text) {
        try {
            ResultSet rs = MySQL.search("SELECT * FROM `customer` INNER JOIN `city` ON `customer`.`city_id`=`city`.`id` WHERE `customer`.`id` != 0 AND (`customer`.`name` LIKE '" + text + "%' OR `customer`.`mobile` LIKE '" + text + "%')  ORDER BY `customer`.`id` ASC ");

            DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
            dtm.setRowCount(0);

            while (rs.next()) {
                Vector v = new Vector();
                v.add(rs.getString("id"));
                v.add(rs.getString("customer.name"));
                v.add(rs.getString("customer.mobile"));
                v.add(rs.getString("city.name"));
                dtm.addRow(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void LoadProvince() {
        try {
            ResultSet rs = MySQL.search("SELECT * FROM `province` ORDER BY `name` ASC");

            Vector v = new Vector();
            v.add("--  S E L E C T  --");

            while (rs.next()) {
                v.add(rs.getString("name"));
            }

            DefaultComboBoxModel dcm = new DefaultComboBoxModel(v);
            jComboBox2.setModel(dcm);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void LoadDistrict(String province) {
        try {
            ResultSet rs = MySQL.search("SELECT * FROM `district` INNER JOIN `province` ON `province`.`id`=`district`.`province_id` WHERE `province`.`name` = '" + province + "'  ORDER BY `district`.`name` ASC");

            Vector v = new Vector();
            v.add("--  S E L E C T  --");

            while (rs.next()) {
                v.add(rs.getString("name"));
            }

            DefaultComboBoxModel dcm = new DefaultComboBoxModel(v);
            jComboBox3.setModel(dcm);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void LoadCity(String district) {
        try {
            ResultSet rs = MySQL.search("SELECT * FROM `city` INNER JOIN `district` ON `district`.`id`=`city`.`district_id` WHERE `district`.`name` = '" + district + "' ORDER BY `city`.`name` ASC");

            Vector v = new Vector();
            v.add("--  S E L E C T  --");

            while (rs.next()) {
                v.add(rs.getString("name"));
            }

            if (v.size() == 1) {

            }

            DefaultComboBoxModel dcm = new DefaultComboBoxModel(v);
            jComboBox4.setModel(dcm);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void LoadPostalCode(String city) {
        try {
            ResultSet rs = MySQL.search("SELECT `postal_code` FROM `city` WHERE `name` = '" + city + "' ");

            if (rs.next()) {
                jLabel14.setText(rs.getString("postal_code"));
            } else {
                jLabel14.setText("None");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetFields() {
        jTextField1.setText("");
        jTextField3.setEnabled(true);
        jTextField3.setText("");
        jComboBox2.setSelectedIndex(0);
        jLabel14.setText("None");
        jTextField1.grabFocus();
    }

    public void setStatusButtonListener() {

        ListSelectionListener lsl = (ListSelectionEvent arg0) -> {
            int selectedRow = jTable1.getSelectedRow();

            if (selectedRow != -1) {

                jButton1.setText("Update Customer");

                String id = jTable1.getValueAt(selectedRow, 0).toString();

                try {
                    ResultSet rs = MySQL.search("SELECT * FROM `customer` INNER JOIN `city` ON `customer`.`city_id`=`city`.`id` WHERE `customer`.`id` = '" + Integer.parseInt(id) + "'");
                    rs.next();
                    jTextField1.setText(rs.getString("name"));
                    jTextField3.setText(rs.getString("mobile"));

                    ResultSet rs1 = MySQL.search("SELECT * FROM `city` WHERE `city`.`id` = '" + rs.getString("city_id") + "' ");
                    rs1.next();
                    ResultSet rs2 = MySQL.search("SELECT * FROM `city` WHERE `district_id` = '" + rs1.getString("district_id") + "' ");

                    Vector v1 = new Vector();
                    v1.add("--  S E L E C T  --");

                    while (rs2.next()) {
                        v1.add(rs2.getString("name"));
                    }

                    DefaultComboBoxModel dcm1 = new DefaultComboBoxModel(v1);
                    jComboBox4.setModel(dcm1);

                    ResultSet rs4 = MySQL.search("SELECT * FROM `district` WHERE `id` = '" + rs1.getString("district_id") + "' ");
                    rs4.next();
                    ResultSet rs3 = MySQL.search("SELECT * FROM `province` WHERE `id` = '" + rs4.getString("province_id") + "' ");
                    rs3.next();
                    ResultSet rs5 = MySQL.search("SELECT * FROM `district` WHERE `province_id` = '" + rs3.getString("id") + "' ");

                    Vector v2 = new Vector();
                    v2.add("--  S E L E C T  --");

                    while (rs5.next()) {
                        v2.add(rs5.getString("name"));
                    }

                    DefaultComboBoxModel dcm2 = new DefaultComboBoxModel(v2);
                    jComboBox3.setModel(dcm2);

                    jComboBox2.setSelectedItem(rs3.getString("name"));
                    jComboBox3.setSelectedItem(rs4.getString("name"));
                    jComboBox4.setSelectedItem(rs.getString("city.name"));

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                resetFields();
                jButton1.setText("Create Account");
            }
        };

        jTable1.getSelectionModel().addListSelectionListener(lsl);
    }

    /**
     * Creates new form CustomerRegistration
     */
    JDialog j;
    Invoice invoice;

    public CustomerRegistration() {
        initComponents();
        LoadProvince();
        LoadCustomers();
        setStatusButtonListener();
    }

    public CustomerRegistration(JDialog j, Invoice invoice) {
        initComponents();
        this.j = j;
        this.invoice = invoice;
        LoadProvince();
        LoadCustomers();
        setStatusButtonListener();
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
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();

        jPanel1.setBackground(new java.awt.Color(255, 204, 102));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 3, true));
        jPanel2.setForeground(new java.awt.Color(153, 153, 153));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Name");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Contact No");

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jTextField3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextField3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField3MouseClicked(evt);
            }
        });
        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField3KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField3KeyTyped(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Province");

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton1.setText("Create Account");
        jButton1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 204, 0), 3, true));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jComboBox2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jComboBox2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox2ItemStateChanged(evt);
            }
        });

        jComboBox3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--  S E L E C T  --" }));
        jComboBox3.setEnabled(false);
        jComboBox3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox3ItemStateChanged(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("District");

        jComboBox4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--  S E L E C T  --" }));
        jComboBox4.setEnabled(false);
        jComboBox4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox4ItemStateChanged(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("City");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText("Postal Code:");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText("None");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jComboBox4, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox3, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel1)
                                .addComponent(jLabel4))
                            .addGap(41, 41, 41)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jTextField3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                                .addComponent(jTextField1)))))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel13))
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 3, true));
        jPanel3.setForeground(new java.awt.Color(153, 153, 153));

        jTable1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Contact No", "City"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setToolTipText("");
        jTable1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTable1.setSelectionBackground(new java.awt.Color(229, 229, 255));
        jTable1.setSelectionForeground(new java.awt.Color(0, 0, 204));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Search Customer");

        jTextField4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField4KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 793, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 479, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField3MouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            jTextField3.setEnabled(true);
            jTextField3.setText("");
            jTextField3.grabFocus();
        }
    }//GEN-LAST:event_jTextField3MouseClicked

    private void jTextField3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyReleased
        // TODO add your handling code here:
        if (jTextField3.getText().length() == 10) {
            jTextField3.setEnabled(false);
        }
    }//GEN-LAST:event_jTextField3KeyReleased

    private void jTextField3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyTyped
        // TODO add your handling code here:

        String mobile = jTextField3.getText();
        String text = mobile + evt.getKeyChar();

        if (text.length() == 1) {
            if (!text.equals("0")) {
                evt.consume();
            }
        } else if (text.length() == 2) {
            if (!text.equals("07")) {
                evt.consume();
            }
        } else if (text.length() == 3) {
            if (!Pattern.compile("[0][7][1|2|4|5|6|7|8]").matcher(text).matches()) {
                evt.consume();
            }
        } else if (text.length() <= 10) {
            if (!Pattern.compile("[0][7][1|2|4|5|6|7|8][0-9]+").matcher(text).matches()) {
                evt.consume();
            }
        } else {
            evt.consume();
        }
    }//GEN-LAST:event_jTextField3KeyTyped

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

        String name = jTextField1.getText();
        String mobile = jTextField3.getText();
        String city = jComboBox4.getSelectedItem().toString();
        String postal_code = jLabel14.getText();

        if (name.isBlank()) {
            JOptionPane.showMessageDialog(this, "Please enter first name.", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (!Pattern.compile("[0][7][1|2|4|5|6|7|8][0-9]{7}").matcher(mobile).matches()) {
            JOptionPane.showMessageDialog(this, "Please enter invalid mobile.", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (postal_code.equals("None")) {
            JOptionPane.showMessageDialog(this, "Please select city.", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                ResultSet rs0 = MySQL.search("SELECT * FROM `customer` WHERE `name`='" + name + "' AND `mobile` = '" + mobile + "' ");

                if (rs0.next()) {
                    JOptionPane.showMessageDialog(this, "This name or mobile number already exixts.", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    ResultSet rs1 = MySQL.search("SELECT `id` From `city` WHERE `name`='" + city + "'");
                    rs1.next();

                    String city_id = rs1.getString("id");

                    if (jButton1.getText().equals("Create Account")) {
                        MySQL.iud("INSERT INTO `customer`(`name`,`mobile`,`city_id`) VALUES('" + name + "','" + mobile + "'," + Integer.parseInt(city_id) + ") ");

                        resetFields();
                        LoadCustomers();

                        JOptionPane.showMessageDialog(this, "New customer account created.", "Succsess", JOptionPane.INFORMATION_MESSAGE);

                    } else if (jButton1.getText().equals("Update Customer")) {

                        ResultSet rs11 = MySQL.search("SELECT * FROM `customer` WHERE `name`='" + name + "' AND `mobile` = '" + mobile + "' ");

                        if (rs11.next()) {
                            JOptionPane.showMessageDialog(this, "This name or mobile number already exixts.", "Warning", JOptionPane.WARNING_MESSAGE);
                        } else {
                            MySQL.iud("UPDATE `customer` SET `name` = '" + name + "',`mobile`='" + mobile + "',`city_id`=" + Integer.parseInt(city_id) + " WHERE `id` = " + Integer.parseInt(jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString()) + "");

                            resetFields();
                            LoadCustomers();

                            JOptionPane.showMessageDialog(this, "Customer account Updated.", "Succsess", JOptionPane.INFORMATION_MESSAGE);

                        }

                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox2ItemStateChanged
        // TODO add your handling code here:
        if (!jComboBox2.getSelectedItem().toString().equals("--  S E L E C T  --")) {
            jComboBox3.setEnabled(true);
            jComboBox3.setSelectedIndex(0);
        } else {
            jComboBox3.setEnabled(false);
            jComboBox3.setSelectedIndex(0);
            jComboBox4.setEnabled(false);
            jComboBox4.setSelectedIndex(0);
        }
        LoadDistrict(jComboBox2.getSelectedItem().toString());
        LoadCity(jComboBox3.getSelectedItem().toString());
        LoadPostalCode(jComboBox4.getSelectedItem().toString());
    }//GEN-LAST:event_jComboBox2ItemStateChanged

    private void jComboBox3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox3ItemStateChanged
        // TODO add your handling code here:
        if (!jComboBox3.getSelectedItem().toString().equals("--  S E L E C T  --")) {
            jComboBox4.setEnabled(true);
            jComboBox4.setSelectedIndex(0);
            jLabel14.setText("None");
        } else {
            jComboBox4.setEnabled(false);
            jComboBox4.setSelectedIndex(0);
        }
        LoadCity(jComboBox3.getSelectedItem().toString());
        LoadPostalCode(jComboBox4.getSelectedItem().toString());
    }//GEN-LAST:event_jComboBox3ItemStateChanged

    private void jComboBox4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox4ItemStateChanged
        // TODO add your handling code here:
        LoadPostalCode(jComboBox4.getSelectedItem().toString());
    }//GEN-LAST:event_jComboBox4ItemStateChanged

    private void jTextField4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyReleased
        // TODO add your handling code here:
        String text = jTextField4.getText();
        LoadCustomers(text);
    }//GEN-LAST:event_jTextField4KeyReleased

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        int selectedRow = jTable1.getSelectedRow();

        if (evt.getClickCount() == 2 && invoice != null) {
            invoice.jLabel4.setText(jTable1.getValueAt(selectedRow, 0).toString());
            invoice.jLabel5.setText(jTable1.getValueAt(selectedRow, 1).toString());
            invoice.jLabel6.setText(jTable1.getValueAt(selectedRow, 2).toString());
            invoice.jLabel26.setText(jTable1.getValueAt(selectedRow, 3).toString());

            j.dispose();
        }
    }//GEN-LAST:event_jTable1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    public javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
