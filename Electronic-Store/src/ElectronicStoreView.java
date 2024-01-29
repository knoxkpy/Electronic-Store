import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class ElectronicStoreView extends Pane {
    private ElectronicStore model;
    private ListView<Product> Most_Popular_Items;
    private ListView<Product> Store_Stock;
    private ListView<String> Current_Cart;
    private Button Reset_Store;
    private Button Add_to_Cart;
    private Button Remove_from_Cart;
    private Button Complete_Sales;
    private TextField textField_Sales;
    private TextField textField_revenue;
    private TextField textField_MoneytoSalesRatio;
    private Label label_CurrentCart;

    public ListView<Product> getStore_Stock() {return Store_Stock;}
    public Button getComplete_Sales() {return Complete_Sales;}
    public Button getAdd_to_Cart() {return Add_to_Cart;}
    public ListView<String> getCurrent_Cart() {return Current_Cart;}
    public Button getRemove_from_Cart() {return Remove_from_Cart;}
    public Button getReset_Store() {return Reset_Store;}

    public void setAdd_to_Cart(Boolean able_disable) {
        Add_to_Cart.setDisable(able_disable);}

    public void setRemove_from_Cart(Boolean able_disable) {
        Remove_from_Cart.setDisable(able_disable);}

    public ElectronicStoreView(ElectronicStore m ) {
        model = m;

        Label label_StoreSummary = new Label("Store Summary:");
        label_StoreSummary.relocate(50, 20);
        label_StoreSummary.setPrefSize(90, 0);

        Label label_StoreStock = new Label("Store Stock:");
        label_StoreStock.relocate(295,20);
        label_StoreStock.setPrefSize(90, 0);


        label_CurrentCart = new Label();
        label_CurrentCart.relocate(585,20);
        label_CurrentCart.setPrefSize(140,0);

        Label label_Sales = new Label("# Sales:");
        label_Sales.relocate(40,50);
        label_Sales.setPrefSize(90, 0);

        textField_Sales = new TextField();
        textField_Sales.relocate(85,45);
        textField_Sales.setPrefSize(85,25);

        Label label_Revenue = new Label("Revenue:");
        label_Revenue.relocate(30,80);
        label_Revenue.setPrefSize(90,0);

        textField_revenue = new TextField();
        textField_revenue.relocate(85,75);
        textField_revenue.setPrefSize(85,25);

        Label label_MoneytoSalesRatio = new Label("$ / Sale:");
        label_MoneytoSalesRatio.relocate(37, 110);
        label_MoneytoSalesRatio.setPrefSize(90,0);

        textField_MoneytoSalesRatio = new TextField();
        textField_MoneytoSalesRatio.relocate(85,105);
        textField_MoneytoSalesRatio.setPrefSize(85,25);

        Label label_MostPopularItems = new Label("Most Popular Items:");
        label_MostPopularItems.relocate(40, 140);
        label_MostPopularItems.setPrefSize(120,0);

        Reset_Store = new Button("Reset Store");
        Reset_Store.setAlignment(Pos.CENTER);
        Reset_Store.relocate(30,345);
        Reset_Store.setPrefSize(130, 40);

        Add_to_Cart = new Button("Add to Cart");
        Add_to_Cart.setAlignment(Pos.CENTER);
        Add_to_Cart.setDisable(true);
        Add_to_Cart.relocate(265,345);
        Add_to_Cart.setPrefSize(130, 40);

        Remove_from_Cart = new Button("Remove from Cart");
        Remove_from_Cart.setAlignment(Pos.CENTER);
        Remove_from_Cart.setDisable(true);
        Remove_from_Cart.relocate(500,345);
        Remove_from_Cart.setPrefSize(142.5, 40);

        Complete_Sales = new Button("Complete Sales");
        Complete_Sales.setAlignment(Pos.CENTER);
        Complete_Sales.setDisable(true);
        Complete_Sales.relocate(642.5,345);
        Complete_Sales.setPrefSize(142.5, 40);

        Most_Popular_Items = new ListView<Product>();
        Product[] MostPopularItem = new Product[3];
        for (int i = 0; i<3; i++) {
            MostPopularItem[i] = model.getStock().get(i);
        }
        Most_Popular_Items.setItems(FXCollections.observableArrayList(MostPopularItem));
        Most_Popular_Items.relocate(15,160);
        Most_Popular_Items.setPrefSize(155,180);

        Store_Stock = new ListView<Product>();
        Store_Stock.setItems(FXCollections.observableArrayList());
        Store_Stock.relocate(180,45);
        Store_Stock.setPrefSize(310,295);

        Current_Cart = new ListView<String>();
        Current_Cart.setItems(FXCollections.observableArrayList());
        Current_Cart.relocate(500,45);
        Current_Cart.setPrefSize(285,295);


        getChildren().addAll(label_StoreSummary,label_StoreStock,label_CurrentCart,label_Sales,label_Revenue,
                label_MoneytoSalesRatio,label_MostPopularItems,Reset_Store,Add_to_Cart,Remove_from_Cart,Complete_Sales,
                textField_Sales,textField_revenue,textField_MoneytoSalesRatio,Most_Popular_Items, Store_Stock,Current_Cart);
        update();
    }

    public void update(){

        //get the products in the model and set the listview
        ArrayList<Product> StoreStock = new ArrayList<>();
        for (int i = 0; i<model.getStock().size(); i++) {
            if (model.getStock().get(i).getStockQuantity() > 0) {
                StoreStock.add(model.getStock().get(i));
            }
        }
        Store_Stock.setItems(FXCollections.observableArrayList(StoreStock));

        Product[] MostPopularItem = new Product[3];

        //get the respectively values from model and set the textfield.
        textField_Sales.setText("" + model.getNoOfSales());
        textField_revenue.setText(String.format("%.2f",model.getRevenue()));

        if (model.getRevenue() == 0 || model.getNoOfSales() == 0) {
            textField_MoneytoSalesRatio.setText("N/A");
        } else {
            textField_MoneytoSalesRatio.setText("" + model.getRevenue()/model.getNoOfSales());
        }

        //update the current cart.

        String[] currentcart_item = new String[model.getShopping_items().size()];

        int arrayindex = 0;
        for (Map.Entry<Product, Integer> entry : model.getShopping_items().entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            String productInfo = quantity + " x " + product.toString();
            currentcart_item[arrayindex++] = productInfo;
        }
        Current_Cart.setItems(FXCollections.observableArrayList(currentcart_item));



        update_CompleteSalesButton();
        update_label_current_cart();
        update_MostPopularItems();

    }

    public void update_CompleteSalesButton() {
        if (model.getShopping_items().isEmpty()) {
            Complete_Sales.setDisable(true);
        } else {
            Complete_Sales.setDisable(false);
        }
    }

    public void update_label_current_cart() {

        double current_cart_price = 0;
        for (Product product : model.getShopping_items().keySet()) {
            current_cart_price += product.getPrice() * model.getShopping_items().get(product);
        }

        String format = String.format("%.2f", current_cart_price);
        label_CurrentCart.setText("Current Cart ($" + format +"):");
    }

    public void update_MostPopularItems() {
        ArrayList<Product> mostPopular = new ArrayList<>();
        for (Product product : model.getStock()) {
            mostPopular.add(product);
        }

        //sort by sold quantity.
        Collections.sort(mostPopular);

        ArrayList<Product> Top3 = new ArrayList<>();
        for (int i=0; i<3 ; i++) {
            Top3.add(mostPopular.get(i));
        }

        Most_Popular_Items.setItems(FXCollections.observableArrayList(Top3));
    }
}
