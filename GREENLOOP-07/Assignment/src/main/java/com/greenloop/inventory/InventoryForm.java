package com.greenloop.inventory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class InventoryForm extends JFrame {

    JTextField txtName;
    JTextField txtQuantity;
    JTextField txtReorder;

    JButton btnAdd;
    JButton btnUpdate;
    JButton btnDelete;

    JTable table;
    DefaultTableModel model;

    public InventoryForm() {

        setTitle("GREENLOOP INVENTORY MANAGEMENT SYSTEM");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 245, 245));


        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(220, 235, 225));
        topPanel.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(Color.GRAY, 1),
                        "Product Details"
                )
        );
        topPanel.setLayout(new GridLayout(4, 2));

        topPanel.add(new JLabel("Product Name"));
        txtName = new JTextField();
        topPanel.add(txtName);

        topPanel.add(new JLabel("Quantity"));
        txtQuantity = new JTextField();
        topPanel.add(txtQuantity);

        topPanel.add(new JLabel("Reorder Level"));
        txtReorder = new JTextField();
        topPanel.add(txtReorder);

        btnAdd = new JButton("Add");
        btnAdd.setPreferredSize(new Dimension(100,30));
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");

        btnAdd.setBackground(Color.GREEN);
        btnAdd.setForeground(Color.WHITE);

        btnUpdate.setBackground(Color.BLUE);
        btnUpdate.setForeground(Color.WHITE);

        btnDelete.setBackground(Color.RED);
        btnDelete.setForeground(Color.WHITE);

        topPanel.add(new JLabel(""));

        JPanel addPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addPanel.add(btnAdd);

        topPanel.add(addPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(220, 235, 225));
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);

        add(topPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Product");
        model.addColumn("Quantity");
        model.addColumn("Reorder Level");
        model.addColumn("Status");

        table = new JTable(model);
        table.setRowHeight(30);
        table.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 16)
        );
        table.getColumnModel().getColumn(4).setCellRenderer(
                new javax.swing.table.DefaultTableCellRenderer() {
                    @Override
                    public java.awt.Component getTableCellRendererComponent(
                            JTable table,
                            Object value,
                            boolean isSelected,
                            boolean hasFocus,
                            int row,
                            int column) {

                        JLabel label = (JLabel) super.getTableCellRendererComponent(
                                table, value, isSelected, hasFocus, row, column);

                        label.setFont(new Font("Arial", Font.BOLD, 14));

                        if ("LOW STOCK".equals(value)) {
                            label.setForeground(Color.RED);
                        } else {
                            label.setForeground(new Color(0, 128, 0));
                        }

                        return label;
                    }
                }
        );



        table.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 14));

        table.setFont(
                new Font("Arial", Font.PLAIN, 13));

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        btnAdd.addActionListener(e -> {

            try {

                if (txtName.getText().isEmpty()
                        || txtQuantity.getText().isEmpty()
                        || txtReorder.getText().isEmpty()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Please fill all fields"
                    );

                    return;
                }

                String name = txtName.getText();

                int quantity =
                        Integer.parseInt(txtQuantity.getText());

                int reorderLevel =
                        Integer.parseInt(txtReorder.getText());

                Inventory product = new Inventory();

                product.setProductName(name);
                product.setQuantity(quantity);
                product.setReorderLevel(reorderLevel);

                InventoryDAO dao = new InventoryDAO();
                dao.addProduct(product);
                loadProducts();
                txtName.setText("");
                txtQuantity.setText("");
                txtReorder.setText("");

                JOptionPane.showMessageDialog(
                        this,
                        "Product Added Successfully"
                );


            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        btnDelete.addActionListener(e -> {

            int row = table.getSelectedRow();

            if (row >= 0) {

                int productId =
                        (int) model.getValueAt(row, 0);

                InventoryDAO dao = new InventoryDAO();

                dao.deleteProduct(productId);

                loadProducts();

                JOptionPane.showMessageDialog(
                        this,
                        "Product Deleted Successfully"
                );
            }
        });
        table.getSelectionModel().addListSelectionListener(e -> {

            int row = table.getSelectedRow();

            if (row >= 0) {

                txtName.setText(
                        model.getValueAt(row, 1).toString());

                txtQuantity.setText(
                        model.getValueAt(row, 2).toString());

                txtReorder.setText(
                        model.getValueAt(row, 3).toString());
            }
        });
        btnUpdate.addActionListener(e -> {

            int row = table.getSelectedRow();

            if (row >= 0) {

                int productId =
                        (int) model.getValueAt(row, 0);

                Inventory product = new Inventory();

                product.setProductId(productId);
                product.setProductName(txtName.getText());

                product.setQuantity(
                        Integer.parseInt(txtQuantity.getText()));

                product.setReorderLevel(
                        Integer.parseInt(txtReorder.getText()));

                InventoryDAO dao = new InventoryDAO();

                dao.updateProduct(product);

                loadProducts();

                JOptionPane.showMessageDialog(
                        this,
                        "Product Updated Successfully"
                );
            }
        });


        loadProducts();
        setVisible(true);
    }

    private void loadProducts() {

        model.setRowCount(0);

        InventoryDAO dao = new InventoryDAO();

        for (Inventory product : dao.getAllProducts()) {

            String status = "OK";

            if(product.getQuantity() <= product.getReorderLevel()){
                status = "LOW STOCK";
            }

            model.addRow(new Object[]{
                    product.getProductId(),
                    product.getProductName(),
                    product.getQuantity(),
                    product.getReorderLevel(),
                    status
            });
        }
    }
}