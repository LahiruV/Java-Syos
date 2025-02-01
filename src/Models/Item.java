package Models;

public class Item {
    private String code;
    private String name;
    private double price;
    private int stockQuantity;

    public Item(String code, String name, double price, int stockQuantity) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void reduceStock(int quantity) {
        if (quantity <= stockQuantity) {
            stockQuantity -= quantity;
        }
    }
}
