//Base class for all products the store will sell
public abstract class Product implements Comparable<Product> {
    private double price;
    private int stockQuantity;
    private int soldQuantity;

    public Product(double initPrice, int initQuantity) {
        price = initPrice;
        stockQuantity = initQuantity;
    }

    public void setStockQuantity(int quantity) {
        stockQuantity = quantity;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }

    public double getPrice() {
        return price;
    }

    //Returns the total revenue (price * amount) if there are at least amount items in stock
    //Return 0 otherwise (i.e., there is no sale completed)
    public double sellUnits(int amount) {
        if (amount > 0) {
            soldQuantity += amount;
            return price * amount;
        }
        return 0.0;
    }


    public int compareTo(Product other) {
        if (this.soldQuantity > other.soldQuantity) {
            return -1;
        } else if (this.soldQuantity < other.soldQuantity) {
            return 1;
        } else {
            return 0;
        }
    }
}