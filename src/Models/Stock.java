package Models;

import java.util.Date;

public class Stock {
    private Item item;
    private int quantity;
    private Date purchaseDate;
    private Date expiryDate;

    public Stock(Item item, int quantity, Date purchaseDate, Date expiryDate) {
        this.item = item;
        this.quantity = quantity;
        this.purchaseDate = purchaseDate;
        this.expiryDate = expiryDate;
    }

    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void reduceQuantity(int amount) {
        if (amount <= quantity) {
            quantity -= amount;
        }
    }
}
