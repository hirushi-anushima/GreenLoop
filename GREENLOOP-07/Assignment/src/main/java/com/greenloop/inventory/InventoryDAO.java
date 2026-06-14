package com.greenloop.inventory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class InventoryDAO {

    // Add Product
    public void addProduct(Inventory product) {

        try {
            Connection con = DBConnection.getConnection();

            String sql =
                    "INSERT INTO inventory(product_name, quantity, reorder_level) VALUES(?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, product.getProductName());
            ps.setInt(2, product.getQuantity());
            ps.setInt(3, product.getReorderLevel());

            ps.executeUpdate();

            System.out.println("Product Added");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // View All Products
    public List<Inventory> getAllProducts() {

        List<Inventory> products = new ArrayList<>();

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM inventory";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Inventory product = new Inventory();

                product.setProductId(rs.getInt("product_id"));
                product.setProductName(rs.getString("product_name"));
                product.setQuantity(rs.getInt("quantity"));
                product.setReorderLevel(rs.getInt("reorder_level"));

                products.add(product);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }

    // Update Product
    public void updateProduct(Inventory product) {

        try {

            Connection con = DBConnection.getConnection();

            String sql =
                    "UPDATE inventory SET product_name=?, quantity=?, reorder_level=? WHERE product_id=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, product.getProductName());
            ps.setInt(2, product.getQuantity());
            ps.setInt(3, product.getReorderLevel());
            ps.setInt(4, product.getProductId());

            ps.executeUpdate();

            System.out.println("Product Updated");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Delete Product
    public void deleteProduct(int productId) {

        try {

            Connection con = DBConnection.getConnection();

            String sql =
                    "DELETE FROM inventory WHERE product_id=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, productId);

            ps.executeUpdate();

            System.out.println("Product Deleted");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}