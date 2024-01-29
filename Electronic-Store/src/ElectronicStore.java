//Class representing an electronic store
//Has an array of products that represent the items the store can sell

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ElectronicStore {
    private String name;
    private ArrayList<Product> stock; //Array to hold all products
    private double revenue;
    private int NoOfSales;
    private Map<Product, Integer> Shopping_items;

    public ElectronicStore(String initName) {
        revenue = 0.0;
        name = initName;
        stock = new ArrayList<>();
        NoOfSales = 0;
        Shopping_items = new HashMap<>();
    }

    public void completeSales() {
        double revenueForOneSell = 0;
        for (Map.Entry<Product,Integer> entry : Shopping_items.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();

            //make use of the existing method in product class.
            revenueForOneSell += product.sellUnits(quantity);
        }

        if (revenueForOneSell > 0) {
            revenue += revenueForOneSell;
            NoOfSales += 1;
        }

        Shopping_items.clear();
    }

    public void updateCart(Product item, int quantity) {
        if (item.getStockQuantity() >= quantity) {
            if (Shopping_items.containsKey(item)) {
                int currentQuantity = Shopping_items.get(item);
                Shopping_items.put(item, currentQuantity + 1);
            } else {
                Shopping_items.put(item, 1);
            }
        }
        //update the stock
        int Newquantity = item.getStockQuantity() - 1;
        item.setStockQuantity(Newquantity);
    }

    public void removeItemFromCart(Product item) {
        if (Shopping_items.containsKey(item)) {
            int QuantityofSelecteditem = Shopping_items.get(item);
            if (QuantityofSelecteditem >= 2) {
                Shopping_items.put(item, QuantityofSelecteditem -1);
            } else {
                Shopping_items.remove(item);
            }
            for (int i =0 ; i < stock.size() ; i++) {
                if (stock.get(i).equals(item)) {
                    stock.get(i).setStockQuantity(stock.get(i).getStockQuantity() +1);
                    break;
                }
            }
        }
    }

    public Map<Product, Integer> getShopping_items() {return Shopping_items;}
    public String getName() {
        return name;
    }
    public ArrayList<Product> getStock() {return stock;}

    public int getNoOfSales() {return NoOfSales;}

    public double getRevenue() {return revenue;}

    //Adds a product and returns true if there is space in the array
    //Returns false otherwise
    public boolean addProduct(Product newProduct){
        for (Product products : stock) {
            if(products.toString().equals(newProduct.toString())){
                return false;
            }
        }
        stock.add(newProduct);
        return true;
    }

    public void restoreStore() {
        ElectronicStore restore = createStore();
        revenue = 0;
        NoOfSales = 0;
        stock = restore.getStock();
        Shopping_items.clear();
    }

    public static ElectronicStore createStore() {
        ElectronicStore store1 = new ElectronicStore("Watts Up Electronics");
        Desktop d1 = new Desktop(100, 10, 3.0, 16, false, 250, "Compact");
        Desktop d2 = new Desktop(200, 10, 4.0, 32, true, 500, "Server");
        Laptop l1 = new Laptop(150, 10, 2.5, 16, true, 250, 15);
        Laptop l2 = new Laptop(250, 10, 3.5, 24, true, 500, 16);
        Fridge f1 = new Fridge(500, 10, 250, "White", "Sub Zero", false);
        Fridge f2 = new Fridge(750, 10, 125, "Stainless Steel", "Sub Zero", true);
        ToasterOven t1 = new ToasterOven(25, 10, 50, "Black", "Danby", false);
        ToasterOven t2 = new ToasterOven(75, 10, 50, "Silver", "Toasty", true);
        store1.addProduct(d1);
        store1.addProduct(d2);
        store1.addProduct(l1);
        store1.addProduct(l2);
        store1.addProduct(f1);
        store1.addProduct(f2);
        store1.addProduct(t1);
        store1.addProduct(t2);
        return store1;
    }
} 