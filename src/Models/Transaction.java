package Models;

import java.util.Date;
import java.util.List;

public class Transaction {
    private int transactionId;
    private List<Item> items;
    private double totalAmount;
    private Date transactionDate;
    private boolean isOnline;

    public Transaction(int transactionId, List<Item> items, double totalAmount, Date transactionDate,
            boolean isOnline) {
        this.transactionId = transactionId;
        this.items = items;
        this.totalAmount = totalAmount;
        this.transactionDate = transactionDate;
        this.isOnline = isOnline;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public List<Item> getItems() {
        return items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public boolean isOnline() {
        return isOnline;
    }
}
