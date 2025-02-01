package Models;

import java.util.Date;
import java.util.List;

public class Bill {
    private int billId;
    private List<Item> items;
    private double totalPrice;
    private double discount;
    private double cashTendered;
    private double changeAmount;
    private Date billDate;

    public Bill(int billId, List<Item> items, double totalPrice, double discount, double cashTendered,
            double changeAmount, Date billDate) {
        this.billId = billId;
        this.items = items;
        this.totalPrice = totalPrice;
        this.discount = discount;
        this.cashTendered = cashTendered;
        this.changeAmount = changeAmount;
        this.billDate = billDate;
    }

    public int getBillId() {
        return billId;
    }

    public List<Item> getItems() {
        return items;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public double getDiscount() {
        return discount;
    }

    public double getCashTendered() {
        return cashTendered;
    }

    public double getChangeAmount() {
        return changeAmount;
    }

    public Date getBillDate() {
        return billDate;
    }
}
