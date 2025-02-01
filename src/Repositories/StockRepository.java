package Repositories;

import java.sql.*;
import java.util.*;
import Models.Stock;
import Models.Item;

public class StockRepository {
    private Connection connection;

    public StockRepository(Connection connection) {
        this.connection = connection;
    }

    public void addStock(Stock stock) {
        String query = "INSERT INTO stock (item_code, quantity, purchase_date, expiry_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, stock.getItem().getCode());
            stmt.setInt(2, stock.getQuantity());
            stmt.setDate(3, new java.sql.Date(stock.getPurchaseDate().getTime()));
            stmt.setDate(4, new java.sql.Date(stock.getExpiryDate().getTime()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Stock> getStockList() {
        List<Stock> stockList = new ArrayList<>();
        String query = "SELECT * FROM stock";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Item item = new ItemRepository(connection).getItemByCode(rs.getString("item_code"));
                stockList.add(
                        new Stock(item, rs.getInt("quantity"), rs.getDate("purchase_date"), rs.getDate("expiry_date")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stockList;
    }

    public void reduceStock(String itemCode, int quantity) {
        String query = "UPDATE stock SET quantity = quantity - ? WHERE item_code = ? AND quantity >= ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, quantity);
            stmt.setString(2, itemCode);
            stmt.setInt(3, quantity);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Stock getStockByItemCode(String code) {
        String query = "SELECT * FROM stock WHERE item_code = ? LIMIT 1";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, code);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Item item = new ItemRepository(connection).getItemByCode(rs.getString("item_code"));
                return new Stock(item, rs.getInt("quantity"), rs.getDate("purchase_date"), rs.getDate("expiry_date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}