package Repositories;

import java.sql.*;
import java.util.*;
import Models.Item;

public class ItemRepository {
    private Connection connection;

    public ItemRepository(Connection connection) {
        this.connection = connection;
    }

    public void addItem(Item item) {
        String query = "INSERT INTO items (code, name, price, stockQuantity) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, item.getCode());
            stmt.setString(2, item.getName());
            stmt.setDouble(3, item.getPrice());
            stmt.setInt(4, item.getStockQuantity());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Item getItemByCode(String code) {
        String query = "SELECT * FROM items WHERE code = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, code);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Item(rs.getString("code"), rs.getString("name"), rs.getDouble("price"),
                        rs.getInt("stockQuantity"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateStock(String code, int newStockQuantity) {
        String query = "UPDATE items SET stockQuantity = ? WHERE code = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, newStockQuantity);
            stmt.setString(2, code);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Item> getAllItems() {
        List<Item> itemsList = new ArrayList<>();
        String query = "SELECT * FROM items";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                itemsList.add(new Item(rs.getString("code"), rs.getString("name"), rs.getDouble("price"),
                        rs.getInt("stockQuantity")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemsList;
    }
}