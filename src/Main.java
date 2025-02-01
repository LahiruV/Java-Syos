import java.sql.*;
import java.util.*;
import java.util.Date;

import Models.Bill;
import Models.Item;
import Models.Stock;
import Models.Transaction;
import Repositories.BillRepository;
import Repositories.ItemRepository;
import Repositories.StockRepository;
import Repositories.TransactionRepository;

public class Main {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/syos";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "mysql@2025";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            Scanner scanner = new Scanner(System.in);
            ItemRepository itemRepository = new ItemRepository(connection);
            StockRepository stockRepository = new StockRepository(connection);
            BillRepository billRepository = new BillRepository(connection);
            TransactionRepository transactionRepository = new TransactionRepository(connection);

            System.out.println("Welcome to Synex Outlet Store (SYOS)!");
            boolean running = true;

            while (running) {
                System.out.println("\n1. Add Item\n2. Stock Item\n3. Make a Sale\n4. Generate Reports\n5. Exit");
                System.out.print("Select an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1: // Add new item
                        System.out.print("Enter item code: ");
                        String code = scanner.nextLine();
                        System.out.print("Enter item name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter item price: ");
                        double price = scanner.nextDouble();
                        System.out.print("Enter initial stock quantity: ");
                        int quantity = scanner.nextInt();
                        itemRepository.addItem(new Item(code, name, price, quantity));
                        System.out.println("Item added successfully!");
                        break;

                    case 2: // Stock an item
                        System.out.print("Enter item code: ");
                        String stockCode = scanner.nextLine();
                        Item stockItem = itemRepository.getItemByCode(stockCode);
                        if (stockItem != null) {
                            System.out.print("Enter quantity: ");
                            int stockQuantity = scanner.nextInt();
                            stockRepository.addStock(new Stock(stockItem, stockQuantity, new Date(), new Date()));
                            System.out.println("Stock updated successfully!");
                        } else {
                            System.out.println("Item not found!");
                        }
                        break;

                    case 3: // Make a sale
                        List<Item> purchasedItems = new ArrayList<>();
                        double totalPrice = 0;
                        System.out.print("Enter item code: ");
                        String saleCode = scanner.nextLine();
                        Item saleItem = itemRepository.getItemByCode(saleCode);
                        if (saleItem != null) {
                            System.out.print("Enter quantity: ");
                            int saleQuantity = scanner.nextInt();
                            scanner.nextLine();
                            if (saleQuantity <= saleItem.getStockQuantity()) {
                                saleItem.reduceStock(saleQuantity);
                                purchasedItems.add(saleItem);
                                totalPrice = saleItem.getPrice() * saleQuantity;
                                System.out.print("Enter cash tendered: ");
                                double cashTendered = scanner.nextDouble();
                                double change = cashTendered - totalPrice;

                                Bill bill = new Bill(billRepository.getAllBills().size() + 1, purchasedItems,
                                        totalPrice, 0,
                                        cashTendered, change, new Date());
                                billRepository.saveBill(bill);

                                transactionRepository.addTransaction(
                                        new Transaction(transactionRepository.getAllTransactions().size() + 1,
                                                purchasedItems, totalPrice, new Date(), false));

                                System.out.println("Bill and transaction recorded successfully!");
                            } else {
                                System.out.println("Not enough stock available!");
                            }
                        } else {
                            System.out.println("Item not found!");
                        }
                        break;

                    case 4: // Generate reports
                        System.out.println("Generating reports...");
                        System.out.println("Total sales for the day: ");
                        for (Transaction transaction : transactionRepository.getAllTransactions()) {
                            System.out.println("Transaction ID: " + transaction.getTransactionId());
                            System.out.println("Items Sold: " + transaction.getItems());
                            System.out.println("Total Amount: " + transaction.getTotalAmount());
                        }
                        System.out.println("Reorder levels:");
                        for (Stock stock : stockRepository.getStockList()) {
                            if (stock.getQuantity() < 50) {
                                System.out.println("Item Code: " + stock.getItem().getCode() + ", Name: "
                                        + stock.getItem().getName() + ", Quantity: " + stock.getQuantity());
                            }
                        }
                        System.out.println("Total transactions: " + transactionRepository.getAllTransactions().size());
                        System.out.println("Total bills: " + billRepository.getAllBills().size());
                        break;

                    case 5: // Exit
                        running = false;
                        System.out.println("Exiting system. Thank you!");
                        break;

                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
            scanner.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
