import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ElectronicStoreApp extends Application {

    public void start(Stage primaryStage) {
        ElectronicStore store_instance = ElectronicStore.createStore();
        ElectronicStoreView view = new ElectronicStoreView(store_instance);
        Scene newscene = new Scene(view,800,400);

        view.getStore_Stock().setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                int selectedOrNot = view.getStore_Stock().getSelectionModel().getSelectedIndex();
                if (selectedOrNot >= 0) {
                    view.setAdd_to_Cart(false);
                } else {
                    view.setAdd_to_Cart(true);
                }
                view.update();
            }
        });

        view.getCurrent_Cart().setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                int selectedOrNot = view.getCurrent_Cart().getSelectionModel().getSelectedIndex();
                if (selectedOrNot >= 0) {
                    view.setRemove_from_Cart(false);
                } else {
                    view.setRemove_from_Cart(true);
                }
                view.update();
            }
        });

        view.getAdd_to_Cart().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                Product selecteditems = view.getStore_Stock().getSelectionModel().getSelectedItem();
                if (selecteditems != null) {
                    store_instance.updateCart(selecteditems,1);
                }
                view.update();
            }
        });

        view.getRemove_from_Cart().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String selecteditems = view.getCurrent_Cart().getSelectionModel().getSelectedItem();
                if (selecteditems != null) {
                    for (Product product: store_instance.getShopping_items().keySet()) {
                        String Product_information = store_instance.getShopping_items().get(product) + " x " + product.toString();
                        if (Product_information.equals(selecteditems)) {
                            store_instance.removeItemFromCart(product);
                            break;
                        }
                    }
                }
                view.update();
            }
        });

        view.getComplete_Sales().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                store_instance.completeSales();
                view.update();
            }
        });

        view.getReset_Store().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                store_instance.restoreStore();
                view.update();
            }
        });

        primaryStage.setResizable(false);
        primaryStage.setTitle("Electronic Store Application - Watts Up Electronics");
        primaryStage.setScene(newscene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
