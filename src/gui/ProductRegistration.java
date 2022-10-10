/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gui;

import java.awt.Color;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import model.MySQL;

/**
 *
 * @author Administrator
 */
public class ProductRegistration extends javax.swing.JPanel {

    GRN grn;
    Home h;
    JDialog j2;

    public void resetFields() {
        jTextField1.setText("");
        jTextField1.grabFocus();
        jComboBox1.setSelectedIndex(0);
        jComboBox2.setSelectedIndex(0);
    }

    public void LoadBrands() {

        try {

            ResultSet rs = MySQL.search("SELECT `name` FROM `brand`");

            Vector v = new Vector();
            v.add("-- S E L E C T --");

            while (rs.next()) {
                v.add(rs.getString("name"));
            }

            jComboBox1.setModel(new DefaultComboBoxModel(v));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void LoadCategories() {

        try {

            ResultSet rs = MySQL.search("SELECT `name` FROM `category`");

            Vector v = new Vector();
            v.add("-- S E L E C T --");

            while (rs.next()) {
                v.add(rs.getString("name"));
            }

            jComboBox2.setModel(new DefaultComboBoxModel(v));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void LoadProducts() {
        try {
            ResultSet rs = MySQL.search("SELECT * FROM `product` INNER JOIN `brand` ON `product`.`brand_id` = `brand`.`id` INNER JOIN `exd_mfd_availability` on `exd_mfd_availability`.`id` = `product`.`exd_mfd_availability_id` INNER JOIN `category` ON `product`.`category_id` = `category`.`id`  ORDER BY `product`.`id` ASC");

            DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
            dtm.setRowCount(0);

            while (rs.next()) {
                Vector v = new Vector();
                v.add(rs.getString("id"));
                v.add(rs.getString("name"));
                v.add(rs.getString("exd_mfd_availability.name"));
                v.add(rs.getString("brand.name"));
                v.add(rs.getString("category.name"));
                dtm.addRow(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTableListener() {

        ListSelectionListener lsl = (ListSelectionEvent arg0) -> {
            int selectedRow = jTable1.getSelectedRow();

            if (selectedRow != -1) {
                jButton3.setText("Edit Product details");

                String id = jTable1.getValueAt(selectedRow, 0).toString();

                try {
                    ResultSet rs = MySQL.search("SELECT * FROM `product` INNER JOIN `brand` ON `product`.`brand_id` = `brand`.`id` INNER JOIN `exd_mfd_availability` on `exd_mfd_availability`.`id` = `product`.`exd_mfd_availability_id` INNER JOIN `category` ON `product`.`category_id` = `category`.`id` WHERE `product`.`id`=" + Integer.parseInt(jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString()) + " ");
                    rs.next();
                    jTextField1.setText(rs.getString("product.name"));
                    jComboBox1.setSelectedItem(rs.getString("brand.name"));
                    jComboBox2.setSelectedItem(rs.getString("category.name"));
                    if (rs.getString("exd_mfd_availability.name").equals("Enable")) {
                        jCheckBox1.setSelected(true);
                    } else {
                        jCheckBox1.setSelected(false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                resetFields();
                jButton3.setText("Save");

            }
        };

        jTable1.getSelectionModel().addListSelectionListener(lsl);
    }

    /**
     * Creates new form ProductRegistration
     */
    public ProductRegistration(Home parent) {
        initComponents();
        if (SignIn.userType != 1) {
            jButton1.setEnabled(false);
            jButton2.setEnabled(false);
            jButton3.setEnabled(false);
            jTextField1.setEnabled(false);
        }
        this.h = parent;
        LoadProducts();
        LoadBrands();
        LoadCategories();
        setTableListener();
    }

    public ProductRegistration(JDialog j, GRN grn) {
        initComponents();
        this.grn = grn;
        this.j2 = j;
        LoadProducts();
        LoadBrands();
        LoadCategories();
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
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        jPanel1.setBackground(new java.awt.Color(255, 204, 102));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Product Name");

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jComboBox1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Brand");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Category");

        jComboBox2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setText("+");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton2.setText("+");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton3.setText("Save");
        jButton3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 204, 0), 3, true));
        jButton3.setMinimumSize(new java.awt.Dimension(30, 25));
        jButton3.setPreferredSize(new java.awt.Dimension(30, 25));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jCheckBox1.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jCheckBox1.setSelected(true);
        jCheckBox1.setText("This product have Manufacture Date & Expire Date.");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 590, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(92, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addGap(40, 40, 40)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addGap(28, 28, 28)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jCheckBox1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(jLabel3)
                    .addComponent(jButton1)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox2)
                    .addComponent(jComboBox1))
                .addGap(16, 16, 16))
        );

        jTable1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Exd & Mfd Availability", "Brand", "Category"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setSelectionBackground(new java.awt.Color(229, 229, 255));
        jTable1.setSelectionForeground(new java.awt.Color(0, 0, 204));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMaxWidth(200);
        }

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
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        BrandRegistration br = new BrandRegistration(h, this, true);
        br.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        CategoryRegistration cr = new CategoryRegistration(h, this, true);
        cr.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        if (jTextField1.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter product name.", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (jComboBox1.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Please select brand name.", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (jComboBox2.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Please select category name.", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            String name = jTextField1.getText();
            String brand = jComboBox1.getSelectedItem().toString();
            String category = jComboBox2.getSelectedItem().toString();
            int mfd_exd_availability = 1;

            if (jCheckBox1.isSelected()) {
                mfd_exd_availability = 1;
            } else {
                mfd_exd_availability = 2;
            }

            try {

                ResultSet rs = MySQL.search("SELECT * FROM `product` INNER JOIN `brand` ON `product`.`brand_id` = `brand`.`id` INNER JOIN `category` ON `product`.`category_id` = `category`.`id` WHERE `product`.`name` = '" + name + "' AND `brand`.`name` = '" + brand + "' AND `category`.`name` = '" + category + "'");

                if (jButton3.getText().equals("Save")) {
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(this, "Product already exist.", "Warning", JOptionPane.WARNING_MESSAGE);
                    } else {
                        ResultSet rs1 = MySQL.search("SELECT `id` From `brand` WHERE `name`='" + brand + "'");
                        rs1.next();

                        ResultSet rs2 = MySQL.search("SELECT `id` From `category` WHERE `name`='" + category + "'");
                        rs2.next();

                        String brand_id = rs1.getString("id");
                        String category_id = rs2.getString("id");

                        ResultSet rs0 = MySQL.search("SELECT * FROM `product` WHERE `name`='" + name + "' AND `category_id` = '" + category_id + "' AND `brand_id` ='" + brand_id + "' AND `exd_mfd_availability_id`='" + mfd_exd_availability + "' ");

                        if (rs0.next()) {
                            JOptionPane.showMessageDialog(this, "This product already exists.", "Warning", JOptionPane.WARNING_MESSAGE);
                        } else {
                            MySQL.iud("INSERT INTO `product`(`name`,`category_id`,`brand_id`,`exd_mfd_availability_id`) VALUES('" + name + "','" + category_id + "','" + brand_id + "'," + mfd_exd_availability + ")");

                            LoadProducts();
                            resetFields();

                            JOptionPane.showMessageDialog(this, "New product added", "Warning", JOptionPane.WARNING_MESSAGE);
                        }

                    }
                } else if (jButton3.getText().equals("Edit Product details")) {
                    ResultSet rs1 = MySQL.search("SELECT `id` From `brand` WHERE `name`='" + brand + "'");
                    rs1.next();

                    ResultSet rs2 = MySQL.search("SELECT `id` From `category` WHERE `name`='" + category + "'");
                    rs2.next();

                    String brand_id = rs1.getString("id");
                    String category_id = rs2.getString("id");

                    MySQL.iud("UPDATE `product` SET `name`='" + name + "',`category_id`='" + category_id + "',`brand_id`='" + brand_id + "',`exd_mfd_availability_id`=" + mfd_exd_availability + " WHERE `id` =" + Integer.parseInt(jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString()) + " ");

                    LoadProducts();
                    resetFields();
                    jButton3.setText("Save");
                    JOptionPane.showMessageDialog(this, "Product details updated.", "Warning", JOptionPane.WARNING_MESSAGE);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            int r = jTable1.getSelectedRow();
            if (r == -1) {
                JOptionPane.showMessageDialog(this, "Please select a supplier.", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {

                if (grn != null) {
                    String pid = jTable1.getValueAt(r, 0).toString();
                    String pname = jTable1.getValueAt(r, 1).toString();
                    String brand = jTable1.getValueAt(r, 3).toString();
                    String category = jTable1.getValueAt(r, 4).toString();

                    if (jTable1.getValueAt(r, 2).toString().equals("Disable")) {
                        grn.jDateChooser1.setDate(null);
                        grn.jDateChooser2.setDate(null);
                        grn.jDateChooser1.setEnabled(false);
                        grn.jDateChooser2.setEnabled(false);
                    } else if (jTable1.getValueAt(r, 2).toString().equals("Enable")) {
                        grn.jDateChooser1.setBackground(Color.WHITE);
                        grn.jDateChooser2.setBackground(Color.WHITE);
                        grn.jDateChooser1.setEnabled(true);
                        grn.jDateChooser2.setEnabled(true);
                    }

                    grn.jLabel9.setText(pid);
                    grn.jLabel10.setText(pname);
                    grn.jLabel11.setText(brand);
                    grn.jLabel12.setText(category);
                    grn.jTextField2.grabFocus();
                    j2.dispose();
                }

            }
        }
    }//GEN-LAST:event_jTable1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
