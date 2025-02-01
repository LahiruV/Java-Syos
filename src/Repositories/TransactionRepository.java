package Repositories;

import java.sql.*;
import java.util.*;
import Models.Transaction;
import Models.Item;

public class TransactionRepository {
    private Connection connection;

    public TransactionRepository(Connection connection) {
        this.connection = connection;
    }

    public void addTransaction(Transaction transaction) {
        String query = "INSERT INTO transactions (transaction_id, total_amount, transaction_date, is_online) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, transaction.getTransactionId());
            stmt.setDouble(2, transaction.getTotalAmount());
            stmt.setTimestamp(3, new Timestamp(transaction.getTransactionDate().getTime()));
            stmt.setBoolean(4, transaction.isOnline());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> transactionList = new ArrayList<>();
        String query = "SELECT * FROM transactions";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                transactionList.add(new Transaction(
                        rs.getInt("transaction_id"),
                        new ArrayList<Item>(), // Items will be retrieved separately
                        rs.getDouble("total_amount"),
                        rs.getTimestamp("transaction_date"),
                        rs.getBoolean("is_online")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactionList;
    }

    public Transaction getTransactionById(int transactionId) {
        String query = "SELECT * FROM transactions WHERE transaction_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, transactionId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Transaction(
                        rs.getInt("transaction_id"),
                        new ArrayList<Item>(), // Items will be retrieved separately
                        rs.getDouble("total_amount"),
                        rs.getTimestamp("transaction_date"),
                        rs.getBoolean("is_online"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
