/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gui;

import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Component;
import java.io.File;
import java.nio.file.Files;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import model.MySQL;

/**
 *
 * @author Administrator
 */
public class UserRegistration extends javax.swing.JPanel {

    JDialog j;
    UserAttendanceManagment ua;
    MonthlyPayment mp;

    public void LoadUsers() {
        try {
            ResultSet rs = MySQL.search("SELECT `user`.`id`,`user`.`fname`,`user`.`lname`,`user`.`username`,`user`.`mobile`,`user`.`password`,`user`.`address_line1`,`user`.`address_line2`, `city`.`name` AS `city_name`,`user_status`.`name` AS `user_status`,`user_type`.`name` AS `user_type`, `gender`.`name` AS `gender` FROM `user` INNER JOIN `city` ON `user`.`city_id`=`city`.`id` INNER JOIN `user_type` ON `user`.`user_type_id` = `user_type`.`id` INNER JOIN `user_status` ON `user`.`user_status_id` = `user_status`.`id` INNER JOIN `gender` ON `gender`.`id`=`user`.`gender_id` ORDER BY `user`.`id` ASC ");

            DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
            dtm.setRowCount(0);

            while (rs.next()) {
                Vector v = new Vector();
                v.add(rs.getString("id"));
                v.add(rs.getString("fname") + " " + rs.getString("lname"));
                v.add(rs.getString("username"));
                v.add(rs.getString("password"));
                v.add(rs.getString("gender"));
                v.add(rs.getString("mobile"));
                v.add(rs.getString("user_type"));
                v.add(rs.getString("address_line1") + ", " + rs.getString("address_line2") + ", " + rs.getString("city_name") + ".");
                ResultSet rs1 = MySQL.search("SELECT `amount` FROM `payment_rate_per_hour` WHERE `user_id` = " + Integer.parseInt(rs.getString("id")) + "");
                rs1.next();
                v.add(rs1.getString("amount"));
                v.add(rs.getString("user_status"));
                dtm.addRow(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void LoadUsers(String text) {
        try {
            ResultSet rs = MySQL.search("SELECT `user`.`id`,`user`.`fname`,`user`.`lname`,`user`.`username`,`user`.`mobile`,`user`.`password`,`user`.`address_line1`,`user`.`address_line2`, `city`.`name` AS `city_name`,`user_status`.`name` AS `user_status`,`user_type`.`name` AS `user_type`, `gender`.`name` AS `gender`  FROM `user` INNER JOIN `city` ON `user`.`city_id`=`city`.`id` INNER JOIN `user_type` ON `user`.`user_type_id` = `user_type`.`id` INNER JOIN `user_status` ON `user`.`user_status_id` = `user_status`.`id`  INNER JOIN `gender` ON `gender`.`id`=`user`.`gender_id`  WHERE `user`.`fname` LIKE '" + text + "%' OR `user`.`lname` LIKE '" + text + "%' OR `user`.`mobile` LIKE '" + text + "%' ORDER BY `user`.`id` ASC ");

            DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
            dtm.setRowCount(0);

            while (rs.next()) {
                Vector v = new Vector();
                v.add(rs.getString("id"));
                v.add(rs.getString("fname") + " " + rs.getString("lname"));
                v.add(rs.getString("username"));
                v.add(rs.getString("password"));
                v.add(rs.getString("gender"));
                v.add(rs.getString("mobile"));
                v.add(rs.getString("user_type"));
                v.add(rs.getString("address_line1") + ", " + rs.getString("address_line2") + ", " + rs.getString("city_name") + ".");
                ResultSet rs1 = MySQL.search("SELECT `amount` FROM `payment_rate_per_hour` WHERE `user_id` = " + Integer.parseInt(rs.getString("id")) + "");
                rs1.next();
                v.add(rs1.getString("amount"));
                v.add(rs.getString("user_status"));
                dtm.addRow(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void LoadUserTypes() {
        try {
            ResultSet rs = MySQL.search("SELECT * FROM `user_type`");

            Vector v = new Vector();
            v.add("--  S E L E C T  --");

            while (rs.next()) {
                v.add(rs.getString("name"));
            }

            DefaultComboBoxModel dcm = new DefaultComboBoxModel(v);
            jComboBox1.setModel(dcm);

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
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField5.setText("");
        jTextField6.setText("");
        jTextField7.setText("");
        jTextField8.setText("");
        jTextField9.setText("");
        jRadioButton1.setEnabled(true);
        jRadioButton2.setEnabled(true);
        jRadioButton1.setSelected(true);
        jComboBox1.setSelectedIndex(0);
        jComboBox2.setSelectedIndex(0);
        jLabel14.setText("None");
        jTextField9.setText("");
        jTextField1.grabFocus();
    }

    public void setStatusButtonListener() {

        ListSelectionListener lsl = (ListSelectionEvent arg0) -> {
            int selectedRow = jTable1.getSelectedRow();

            if (selectedRow != -1) {

                jButton1.setText("Update User");

                String id = jTable1.getValueAt(selectedRow, 0).toString();

                try {
                    ResultSet rs = MySQL.search("SELECT `user`.`id`,`user`.`user_type_id`,`user`.`fname`,`user`.`lname`,`user`.`username`,`user`.`mobile`,`user`.`password`,`user`.`address_line1`,`user`.`address_line2`, `city`.`name` AS `city_name`,`user_status`.`name` AS `user_status`,`user_type`.`name` AS `user_type`,`user`.`city_id`, `gender`.`name` AS `gender`,`user`.`gender_id`  FROM `user` INNER JOIN `city` ON `user`.`city_id`=`city`.`id` INNER JOIN `user_type` ON `user`.`user_type_id` = `user_type`.`id` INNER JOIN `user_status` ON `user`.`user_status_id` = `user_status`.`id` INNER JOIN `gender` ON `gender`.`id` =`user`.`gender_id` WHERE `user`.`id` = '" + Integer.parseInt(id) + "'");
                    rs.next();
                    jTextField1.setText(rs.getString("fname"));
                    jTextField5.setText(rs.getString("lname"));
                    jTextField2.setText(rs.getString("username"));
                    jTextField8.setText(rs.getString("password"));
                    jTextField3.setText(rs.getString("mobile"));
                    jTextField6.setText(rs.getString("address_line1"));
                    jTextField7.setText(rs.getString("address_line2"));
                    ResultSet rs6 = MySQL.search("SELECT `amount` FROM `payment_rate_per_hour` WHERE `user_id` = " + Integer.parseInt(rs.getString("id")) + "");
                    rs6.next();
                    jTextField9.setText(rs6.getString("amount"));
                    jComboBox1.setSelectedItem(rs.getString("user_type"));

                    if (rs.getString("gender_id").equals("1")) {
                        jRadioButton1.setSelected(true);
                        jRadioButton1.setEnabled(false);
                        jRadioButton2.setEnabled(false);
                    } else if (rs.getString("gender_id").equals("2")) {
                        jRadioButton2.setSelected(true);
                        jRadioButton1.setEnabled(false);
                        jRadioButton2.setEnabled(false);
                    }

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
                    jComboBox4.setSelectedItem(rs.getString("city_name"));

                    if (rs.getString("user_type_id").equals("1")) {
                        jButton2.setEnabled(false);
                        jComboBox1.setEnabled(false);
                    } else {
                        jButton2.setEnabled(true);
                        jComboBox1.setEnabled(true);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                resetFields();
                jButton1.setText("Create Account");
            }
            if (SignIn.userType != 1) {
                jComboBox1.setEnabled(false);
                jButton2.setEnabled(false);
            }
        };

        jTable1.getSelectionModel().addListSelectionListener(lsl);
    }

    /**
     * Creates new form UserRegistration
     */
    public UserRegistration() {
        initComponents();
        if (SignIn.userType != 1) {
            jRadioButton1.setEnabled(false);
            jRadioButton2.setEnabled(false);
            jButton1.setEnabled(false);
            jButton2.setEnabled(false);
            jComboBox1.setEnabled(false);
            jComboBox2.setEnabled(false);
            jComboBox3.setEnabled(false);
            jComboBox4.setEnabled(false);
            jTextField1.setEnabled(false);
            jTextField2.setEnabled(false);
            jTextField3.setEnabled(false);
            jTextField5.setEnabled(false);
            jTextField8.setEnabled(false);
            jTextField6.setEnabled(false);
            jTextField7.setEnabled(false);
            jTextField9.setEnabled(false);

        }
        setStatusButtonListener();
        LoadUsers();
        LoadProvince();
        LoadUserTypes();
    }

    public UserRegistration(JDialog j, UserAttendanceManagment ua) {
        initComponents();
        this.j = j;
        this.ua = ua;
        setStatusButtonListener();
        LoadUsers();
        LoadProvince();
        LoadUserTypes();
    }

    public UserRegistration(JDialog j, MonthlyPayment mp) {
        initComponents();
        this.j = j;
        this.mp = mp;
        setStatusButtonListener();
        LoadUsers();
        LoadProvince();
        LoadUserTypes();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton1 = new javax.swing.JRadioButton();
        jLabel16 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
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
        jLabel1.setText("First Name");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Username");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Password");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Contact No");

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jTextField2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

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

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton2.setText("Change Status");
        jButton2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 0, 0), 3, true));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
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

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Last Name");

        jTextField5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jTextField6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextField6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField6MouseClicked(evt);
            }
        });
        jTextField6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField6KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField6KeyTyped(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Address Line 1");

        jTextField7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextField7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField7MouseClicked(evt);
            }
        });
        jTextField7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField7KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField7KeyTyped(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Address Line 2");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText("Postal Code:");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText("None");

        jTextField8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel18.setText("Salary per hour:");

        jTextField9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextField9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField9KeyTyped(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Type");

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jRadioButton2.setText("Female");

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("Male");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setText("Gender");

        jComboBox1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel17.setText("( Can't change gender after create account. )");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(11, 11, 11)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel1)
                                .addComponent(jLabel2)
                                .addComponent(jLabel4)
                                .addComponent(jLabel3)
                                .addComponent(jLabel6)
                                .addComponent(jLabel10)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jComboBox4, javax.swing.GroupLayout.Alignment.LEADING, 0, 225, Short.MAX_VALUE)
                                .addComponent(jComboBox3, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jComboBox2, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField7, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextField6, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextField3, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextField8, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextField5, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextField1)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(31, 31, 31)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addGap(0, 0, Short.MAX_VALUE))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel16)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jRadioButton1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jRadioButton2)
                                    .addGap(67, 67, 67)))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel18)
                                    .addGap(10, 10, 10)
                                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                "ID", "Name", "Username", "Password", "Gender", "Contact No", "Type", "Address", "Salary per hour", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
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
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMaxWidth(30);
            jTable1.getColumnModel().getColumn(1).setMinWidth(150);
            jTable1.getColumnModel().getColumn(7).setMinWidth(160);
            jTable1.getColumnModel().getColumn(8).setMinWidth(100);
        }

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Search User");

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
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
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
                .addComponent(jScrollPane1)
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
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
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

        String username = jTextField2.getText();
        String fname = jTextField1.getText();
        String lname = jTextField5.getText();
        String mobile = jTextField3.getText();
        String password = jTextField8.getText();
        String type = jComboBox1.getSelectedItem().toString();
        String address_line1 = jTextField6.getText();
        String address_line2 = jTextField7.getText();
        String postal_code = jLabel14.getText();
        int gender = 1;
        String city = jComboBox4.getSelectedItem().toString();
        String salary = jTextField9.getText();

        if (fname.isBlank()) {
            JOptionPane.showMessageDialog(this, "Please enter first name.", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (lname.isBlank()) {
            JOptionPane.showMessageDialog(this, "Please enter last name.", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (username.isBlank()) {
            JOptionPane.showMessageDialog(this, "Please enter username.", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (password.isBlank()) {
            JOptionPane.showMessageDialog(this, "Please enter gender.", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (!Pattern.compile("[0][7][1|2|4|5|6|7|8][0-9]{7}").matcher(mobile).matches()) {
            JOptionPane.showMessageDialog(this, "Please enter invalid mobile.", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (type.equals("--  S E L E C T  --")) {
            JOptionPane.showMessageDialog(this, "Please select user type.", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (address_line1.isBlank()) {
            JOptionPane.showMessageDialog(this, "Please enter Address number name.", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (address_line2.isBlank()) {
            JOptionPane.showMessageDialog(this, "Please enter Address name.", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (postal_code.equals("None")) {
            JOptionPane.showMessageDialog(this, "Please select city.", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (!Pattern.compile("(0)|([1-9][0-9]*)|(([1-9][0-9]*)[.]([0]*[1-9][0-9]*))|([0][.]([0]*[1-9][0-9]*)|(([1-9][0-9]*)[.]([0]*)))").matcher(salary).matches()) {
            JOptionPane.showMessageDialog(this, "Invalid payment amount.", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            if (!jRadioButton1.isSelected()) {
                gender = 2;
            }
            try {
                ResultSet rs1 = MySQL.search("SELECT `id` From `city` WHERE `name`='" + city + "'");
                rs1.next();

                ResultSet rs2 = MySQL.search("SELECT `id` From `user_type` WHERE `name`='" + type + "'");
                rs2.next();

                String city_id = rs1.getString("id");
                String type_id = rs2.getString("id");

                if (jButton1.getText().equals("Create Account")) {

                    ResultSet rs0 = MySQL.search("SELECT * FROM `user` WHERE `mobile`= '" + mobile + "' OR `username`='" + username + "' ");

                    if (rs0.next()) {
                        JOptionPane.showMessageDialog(this, "This mobile number or username already exixts.", "Warning", JOptionPane.WARNING_MESSAGE);
                    } else {

                        MySQL.iud("INSERT INTO `user`(`fname`,`lname`,`username`,`password`,`mobile`,`user_type_id`,`address_line1`,`address_line2`,`city_id`,`gender_id`) VALUES('" + fname + "','" + lname + "','" + username + "','" + password + "','" + mobile + "'," + Integer.parseInt(type_id) + ",'" + address_line1 + "','" + address_line2 + "'," + Integer.parseInt(city_id) + "," + gender + ") ");

                        ResultSet rs3 = MySQL.search("SELECT `id` FROM `user` WHERE `username` = '" + username + "'");
                        rs3.next();

                        MySQL.iud("INSERT INTO `payment_rate_per_hour`(`amount`,`user_id`) VALUES('" + salary + "','" + rs3.getString("id") + "')");

                        resetFields();
                        LoadUsers();

                        JOptionPane.showMessageDialog(this, "New user account created.", "Succsess", JOptionPane.INFORMATION_MESSAGE);
                    }

                } else if (jButton1.getText().equals("Update User")) {

                    ResultSet rs0 = MySQL.search("SELECT * FROM `user` WHERE `mobile`= '" + mobile + "' OR `username`='" + username + "' ");

                    if (rs0.next()) {
                        JOptionPane.showMessageDialog(this, "This mobile number or username already exixts.", "Warning", JOptionPane.WARNING_MESSAGE);
                    } else {
                        MySQL.iud("UPDATE `user` SET `fname` = '" + fname + "',`lname`='" + lname + "',`username`='" + username + "',`password`='" + password + "',`mobile`='" + mobile + "',`user_type_id`=" + Integer.parseInt(type_id) + ",`address_line1`='" + address_line1 + "',`address_line2`='" + address_line2 + "',`city_id`=" + Integer.parseInt(city_id) + " WHERE `id` = " + Integer.parseInt(jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString()) + "");

                        ResultSet rs3 = MySQL.search("SELECT `id` FROM `user` WHERE `username` = '" + username + "'");
                        rs3.next();

                        MySQL.iud("UPDATE `payment_rate_per_hour` SET `amount`='" + salary + "' , `user_id`='" + rs3.getString("id") + "' WHERE `user_id` = " + Integer.parseInt(rs3.getString("id")) + " ");

                        resetFields();
                        LoadUsers();

                        JOptionPane.showMessageDialog(this, "User account Updated.", "Succsess", JOptionPane.INFORMATION_MESSAGE);

                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        int selectedRow = jTable1.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user in table", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            String id = jTable1.getValueAt(selectedRow, 0).toString();
            String currentStatus = jTable1.getValueAt(selectedRow, 9).toString();

            int status = 1;

            if (currentStatus.equals("Active")) {
                status = 2;
            } else {
                status = 1;
            }
            
            MySQL.iud("UPDATE `user` SET `user_status_id`=" + status + " WHERE `id` = " + Integer.parseInt(id) + " ");

            LoadUsers();

            JOptionPane.showMessageDialog(this, "User status updated.", "Success", JOptionPane.INFORMATION_MESSAGE);

        }
    }//GEN-LAST:event_jButton2ActionPerformed

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

    private void jTextField6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField6MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6MouseClicked

    private void jTextField6KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField6KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6KeyReleased

    private void jTextField6KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField6KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6KeyTyped

    private void jTextField7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField7MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField7MouseClicked

    private void jTextField7KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField7KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField7KeyReleased

    private void jTextField7KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField7KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField7KeyTyped

    private void jTextField4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyReleased
        // TODO add your handling code here:
        String text = jTextField4.getText();
        LoadUsers(text);
    }//GEN-LAST:event_jTextField4KeyReleased

    private void jTextField9KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField9KeyTyped
        // TODO add your handling code here:
        String price = jTextField9.getText();
        String text = price + evt.getKeyChar();

        if (!Pattern.compile("0|0[.]|0[.][1-9]*|[1-9]|[1-9][0-9]*|[1-9][0-9]*[.]|[1-9][0-9]*[.][0-9]*").matcher(text).matches()) {
            evt.consume();
        }
    }//GEN-LAST:event_jTextField9KeyTyped

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        int selectedRow = jTable1.getSelectedRow();

        if (evt.getClickCount() == 2 && ua != null) {
            ua.jLabel5.setText(jTable1.getValueAt(selectedRow, 1).toString());
            ua.jLabel6.setText(jTable1.getValueAt(selectedRow, 2).toString());
            ua.jLabel7.setText(jTable1.getValueAt(selectedRow, 7).toString());
            ua.jLabel8.setText(jTable1.getValueAt(selectedRow, 8).toString());

            ua.jButton4.setEnabled(true);

            ua.loadTable(Integer.parseInt(jTable1.getValueAt(selectedRow, 0).toString()));

            j.dispose();
        }
        if (evt.getClickCount() == 2 && mp != null) {
            mp.jLabel5.setText(jTable1.getValueAt(selectedRow, 1).toString());
            mp.jLabel6.setText(jTable1.getValueAt(selectedRow, 2).toString());
            mp.jLabel7.setText(jTable1.getValueAt(selectedRow, 7).toString());
            mp.jLabel8.setText(jTable1.getValueAt(selectedRow, 8).toString());

            mp.jButton4.setEnabled(true);

//            mp.userid = Integer.parseInt(jTable1.getValueAt(selectedRow, 0).toString());
            mp.paymentSetDate(Integer.parseInt(jTable1.getValueAt(selectedRow, 0).toString()));

            j.dispose();
        }
    }//GEN-LAST:event_jTable1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    public javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables
}
