package Repositories;

import java.sql.*;
import java.util.*;
import Models.Bill;
import Models.Item;

public class BillRepository {
    private Connection connection;

    public BillRepository(Connection connection) {
        this.connection = connection;
    }

    public void saveBill(Bill bill) {
        String query = "INSERT INTO bills (bill_id, total_price, discount, cash_tendered, change_amount, bill_date) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, bill.getBillId());
            stmt.setDouble(2, bill.getTotalPrice());
            stmt.setDouble(3, bill.getDiscount());
            stmt.setDouble(4, bill.getCashTendered());
            stmt.setDouble(5, bill.getChangeAmount());
            stmt.setTimestamp(6, new Timestamp(bill.getBillDate().getTime()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Bill> getAllBills() {
        List<Bill> billList = new ArrayList<>();
        String query = "SELECT * FROM bills";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                billList.add(new Bill(
                        rs.getInt("bill_id"),
                        new ArrayList<Item>(), // Items will be retrieved separately
                        rs.getDouble("total_price"),
                        rs.getDouble("discount"),
                        rs.getDouble("cash_tendered"),
                        rs.getDouble("change_amount"),
                        rs.getTimestamp("bill_date")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return billList;
    }

    public Bill getBillById(int billId) {
        String query = "SELECT * FROM bills WHERE bill_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, billId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Bill(
                        rs.getInt("bill_id"),
                        new ArrayList<Item>(), // Items will be retrieved separately
                        rs.getDouble("total_price"),
                        rs.getDouble("discount"),
                        rs.getDouble("cash_tendered"),
                        rs.getDouble("change_amount"),
                        rs.getTimestamp("bill_date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
