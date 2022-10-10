/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package gui;

import java.sql.ResultSet;
import java.util.Vector;
import java.util.regex.Pattern;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import model.MySQL;
import org.apache.commons.beanutils.ResultSetDynaClass;

/**
 *
 * @author Administrator
 */
public class BranchRegistration extends javax.swing.JDialog {

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

    public void LoadCompanyBranches() {
        try {
            ResultSet rs = MySQL.search("SELECT * FROM `company_branch` INNER JOIN `company` ON `company_branch`.`company_id` = `company`.`id` INNER JOIN `company_branch_address` ON `company_branch`.`company_branch_address_id`=`company_branch_address`.`id` INNER JOIN `city` ON `city`.`id` = `company_branch_address`.`city_id` ORDER BY `company`.`id` ASC, `company_branch`.`id` ASC");

            DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
            dtm.setRowCount(0);

            while (rs.next()) {
                ResultSet rs1 = MySQL.search("SELECT * FROM `grn_payment`  INNER JOIN `grn` ON `grn`.`id` = `grn_payment`.`grn_id` INNER JOIN `supplier` ON `supplier`.`id`=`grn`.`supplier_id` INNER JOIN `company_branch` ON `company_branch`.`id` = `supplier`.`company_branch_id` WHERE `company_branch_id` = " + Integer.parseInt(rs.getString("company_branch.id")) + " ORDER BY `grn_payment`.`id` DESC");

                Vector v = new Vector();
                v.add(rs.getString("company.id"));
                v.add(rs.getString("company.name"));
                v.add(rs.getString("company_branch.id"));
                v.add(rs.getString("company_branch.name"));
                if (rs1.next()) {
                    v.add(rs1.getString("grn_payment.balance"));
                } else {
                    v.add("0.00");
                }
                v.add(rs.getString("company_branch.branch_mobile"));
                String address = rs.getString("company_branch_address.line1") + ", " + rs.getString("company_branch_address.line2") + ", " + rs.getString("city.name") + ".";
                v.add(address);
                dtm.addRow(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetFields() {
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        jLabel8.setText("");
        jComboBox2.setSelectedIndex(0);
        jLabel8.setText("None");
        jLabel9.setText("None");
        jTextField1.grabFocus();
    }

    public void setTableListener() {

        ListSelectionListener lsl = (ListSelectionEvent arg0) -> {
            int selectedRow = jTable1.getSelectedRow();

            if (selectedRow != -1) {

                jButton2.setText("Edit branch details");

                String id = jTable1.getValueAt(selectedRow, 0).toString();

                try {
                    ResultSet rs = MySQL.search("SELECT * FROM `company_branch` INNER JOIN `company` ON `company_branch`.`company_id` = `company`.`id` INNER JOIN `company_branch_address` ON `company_branch`.`company_branch_address_id`=`company_branch_address`.`id` INNER JOIN `city` ON `city`.`id` = `company_branch_address`.`city_id` WHERE `company_branch`.`id` = " + Integer.parseInt(jTable1.getValueAt(jTable1.getSelectedRow(), 2).toString()) + "");
                    rs.next();
                    jTextField1.setText(rs.getString("company_branch.name"));
                    jTextField2.setText(rs.getString("company_branch.branch_mobile"));
                    jTextField3.setText(rs.getString("company_branch_address.line1"));
                    jTextField4.setText(rs.getString("company_branch_address.line2"));
                    jLabel8.setText(rs.getString("company.id"));
                    jLabel9.setText(rs.getString("company.name"));

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

                    jComboBox2.setSelectedItem(rs3.getString("province.name"));
                    jComboBox3.setSelectedItem(rs4.getString("district.name"));
                    jComboBox4.setSelectedItem(rs.getString("city.name"));

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                resetFields();
                jButton2.setText("Create new branch");

            }
        };

        jTable1.getSelectionModel().addListSelectionListener(lsl);
    }

    /**
     * Creates new form BranchRegistration
     */
    public BranchRegistration(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        LoadProvince();
        LoadCompanyBranches();
        setTableListener();
    }

    SupplierRegistration sr;
    Home h;

    public BranchRegistration(Home h, SupplierRegistration sr, boolean modal) {
        super(h, modal);
        initComponents();
        this.h = h;
        this.sr = sr;
        LoadProvince();
        LoadCompanyBranches();
        setTableListener();
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
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jComboBox3 = new javax.swing.JComboBox<>();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Branch Registration");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 255), 6, true));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 3, true));

        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jTextField2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField2KeyTyped(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Contact Number");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Branch Name");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Address Line 1");

        jTextField3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jTextField4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Address Line 2");

        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton1.setText("Select Company");
        jButton1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 153, 0), 3, true));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Company ID");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Company Name");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("None");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("None");

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton2.setText("Create new branch");
        jButton2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 204, 0), 3, true));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
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
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });

        jComboBox4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--  S E L E C T  --" }));
        jComboBox4.setEnabled(false);
        jComboBox4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox4ItemStateChanged(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("City");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("District");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Province");

        jComboBox2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jComboBox2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox2ItemStateChanged(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText("None");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText("Postal Code:");

        jSeparator1.setForeground(new java.awt.Color(153, 153, 153));
        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField2)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(56, 56, 56)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboBox4, 0, 244, Short.MAX_VALUE)
                            .addComponent(jTextField3)
                            .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel4)
                                .addGap(5, 5, 5)
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3)
                                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4)
                                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12)
                                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11)
                                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel10)
                                .addComponent(jLabel6)
                                .addComponent(jLabel7)
                                .addComponent(jLabel8)
                                .addComponent(jLabel9))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel14))
                                .addGap(12, 12, 12)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTable1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Company id", "Company Name", "Branch id", "Branch name", "Balance", "Contact Number", "Address"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jTable1.setSelectionBackground(new java.awt.Color(229, 229, 255));
        jTable1.setSelectionForeground(new java.awt.Color(0, 0, 204));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jTable1MouseEntered(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE)
                .addContainerGap())
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

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyTyped
        // TODO add your handling code here:
        String mobile = jTextField2.getText();
        String text = mobile + evt.getKeyChar();

        if (mobile.length() == 10) {
            evt.consume();
        } else if (!Pattern.compile("[0-9]+").matcher(text).matches()) {
            evt.consume();
        }
    }//GEN-LAST:event_jTextField2KeyTyped

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        CompanyRegistration cr = new CompanyRegistration(this, true);
        cr.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        String name = jTextField1.getText();
        String mobile = jTextField2.getText();
        String company = jLabel8.getText();
        String city = jComboBox4.getSelectedItem().toString();
        String al1 = jTextField3.getText();
        String al2 = jTextField4.getText();

        if (name.isBlank()) {
            JOptionPane.showMessageDialog(this, "Please enter branch name", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (!Pattern.compile("[0][7][1|2|4|5|6|7|8][0-9]{7}").matcher(mobile).matches()) {
            JOptionPane.showMessageDialog(this, "Invalid contact number.", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (company.equals("None")) {
            JOptionPane.showMessageDialog(this, "Plaese select company.", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (al1.isBlank()) {
            JOptionPane.showMessageDialog(this, "Plaese enter address line 1.", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (al2.isBlank()) {
            JOptionPane.showMessageDialog(this, "Plaese enter address line 2.", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (city.equals("--  S E L E C T  --")) {
            JOptionPane.showMessageDialog(this, "Plaese select city.", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            try {

                ResultSet rs0 = MySQL.search("SELECT * FROM `company_branch` WHERE `name`='" + name + "' AND `branch_mobile` ='" + mobile + "'");

                if (rs0.next()) {
                    JOptionPane.showMessageDialog(this, "This mobile number or branch name already exixts.", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    ResultSet rs1 = MySQL.search("SELECT `id` From `city` WHERE `name`='" + city + "'");
                    rs1.next();

                    String city_id = rs1.getString("id");

                    ResultSet rs3 = MySQL.search("SELECT * FROM `company_branch_address` WHERE `line1`='" + al1 + "' AND `line2`='" + al2 + "' AND `city_id` = '" + city_id + "'");

                    if (jButton2.getText().equals("Create new branch")) {
                        if (rs3.next()) {
                            JOptionPane.showMessageDialog(this, "Address already used.", "Warning", JOptionPane.WARNING_MESSAGE);
                        } else {

                            MySQL.iud("INSERT INTO `company_branch_address`(`line1`,`line2`,`city_id`) VALUES('" + al1 + "','" + al2 + "'," + Integer.parseInt(city_id) + ") ");

                            ResultSet rs2 = MySQL.search("SELECT LAST_INSERT_ID()");
                            rs2.next();
                            String id = rs2.getString(1);
                            MySQL.iud("INSERT INTO `company_branch`(`company_id`,`branch_mobile`,`name`,`company_branch_address_id`) VALUES('" + company + "','" + mobile + "','" + name + "','" + id + "')");

                            resetFields();

                            LoadCompanyBranches();

                            JOptionPane.showMessageDialog(this, "New branch created.", "Success", JOptionPane.INFORMATION_MESSAGE);

                        }
                    } else if (jButton2.getText().equals("Edit branch details")) {
                        MySQL.iud("UPDATE `company_branch` SET `branch_mobile`= '" + mobile + "',`name`= '" + name + "' WHERE `id` = " + Integer.parseInt(jTable1.getValueAt(jTable1.getSelectedRow(), 2).toString()) + "");

                        ResultSet rs2 = MySQL.search("SELECT `company_branch_address_id` FROM `company_branch` WHERE `id`= " + Integer.parseInt(jTable1.getValueAt(jTable1.getSelectedRow(), 2).toString()) + " ");
                        rs2.next();
                        MySQL.iud("UPDATE `company_branch_address` SET `line1`= '" + al1 + "',`line2`= '" + al2 + "',`city_id`= " + Integer.parseInt(city_id) + " WHERE `id` = " + Integer.parseInt(rs2.getString("company_branch_address_id")) + "");
                        resetFields();

                        jButton2.setText("Create new branch");
                        LoadCompanyBranches();
                    }
                }

            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            int r = jTable1.getSelectedRow();

            if (r != -1 && sr != null) {
                String bid = jTable1.getValueAt(r, 2).toString();
                String bname = jTable1.getValueAt(r, 3).toString();
                String cid = jTable1.getValueAt(r, 0).toString();
                String cname = jTable1.getValueAt(r, 1).toString();
                String address = jTable1.getValueAt(r, 6).toString();
                String balance = jTable1.getValueAt(r, 4).toString();

                sr.resetFields();
                sr.jLabel7.setText(bid);
                sr.jLabel8.setText(bname);
                sr.jLabel9.setText(address);
                sr.jLabel11.setText(cid);
                sr.jLabel13.setText(cname);
                sr.jLabel13.setText(cname);
                sr.jLabel15.setText(balance);
                sr.LoadSuppliers();
                this.dispose();
            }
        }
    }//GEN-LAST:event_jTable1MouseClicked

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

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jTable1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1MouseEntered

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BranchRegistration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BranchRegistration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BranchRegistration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BranchRegistration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                BranchRegistration dialog = new BranchRegistration(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    public javax.swing.JLabel jLabel8;
    public javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
