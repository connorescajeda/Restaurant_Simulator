package simulation.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import simulation.sim.AutoBuy;
import simulation.sim.Inventory;
import simulation.sim.Kitchen;
import simulation.sim.Supply;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;

public class SimController {
    @FXML
    private BorderPane pane;
    @FXML
    private ObservableList<Node> og;
    @FXML
    private ListView<Text> type;
    @FXML
    private ListView<Text> sellPrice;
    @FXML
    private ListView<Text> buyPrice;
    @FXML
    private ListView<Text> priceEdit1;
    @FXML
    private ListView<TextField> priceEdit2;
    @FXML
    private ListView<Text> Threshold1;
    @FXML
    private ListView<TextField> Threshold2;
    @FXML
    private ListView<TextField> Threshold3;
    @FXML
    private AnchorPane screen2;
    @FXML
    private Text textS1;
    @FXML
    private Text textS11;
    @FXML
    private Text profitForDay;
    @FXML
    private Text money;
    @FXML
    private Text day;
    @FXML
    private ToggleButton enableAB;
    @FXML
    private Text abText;
    @FXML
    private Button playAgain;


    private boolean clicked = false;
    private boolean autobuy = false;
    private boolean loser = false;
    private Kitchen kitchen = new Kitchen(30);
    private DecimalFormat df = new DecimalFormat("0.00");
    private HashMap<Inventory, Integer> ogQuantity = new HashMap<>();

    public void init(){
        mainScreen();
        moneyChange();
        if (!clicked) {
            clicked = true;
            for (Inventory item : Inventory.values()){
                ogQuantity.put(item, item.getQuantity());
            }
        }

        og = pane.getChildren();
    }

    public void simulate(){
        if (clicked && !loser) {
            kitchen.running();
            mainScreen();
            moneyChange();
            pane.getChildren();
       }

    }

    public void mainScreen(){
        buttonTime();
        editPriceList();
        listSetup();
        loserCheck();
        editThresholdList();
        thresholdCheck();
        textS1.setText(kitchen.getAfter());
        textS11.setText(kitchen.getEmpty() + "You lost " + kitchen.getLosses()[0] * 5 + " dollars for not having certain items in stock" + "\n" + "You lost " + kitchen.getLosses()[1] * 5 + " dollars from losing customers over prices");
        day.setText("Day " + kitchen.getDay());
        if (kitchen.getDayProfit() >= 0) {
            profitForDay.setText("Money made today : " + df.format(kitchen.getDayProfit()) + "\n" + "Customers served today : " + kitchen.getCustomers());
        } else {
            profitForDay.setText("Money made today : -$" + df.format(Math.abs(kitchen.getDayProfit())) + "\n" + "Customers served today : " + kitchen.getCustomers());
        }
        if (loser){
            textS1.setText("You're a LOSER!!!!");
        }
    }

    public void thresholdCheck(){
        Inventory[] items = Inventory.values();
        AutoBuy[] things = AutoBuy.values();
        if (autobuy) {
            for (int i = 0; i < items.length; i++) {
                if (things[i].getThreshold() >= items[i].getQuantity()) {
                    Supply order = new Supply(items[i], things[i].getBuyAmount(), kitchen.getMoney());
                    kitchen.setMoney(order.addSupply());
                    listSetup();
                    moneyChange();
                }
            }
        }
    }

    public void buttonTime(){
        int x = 478;
        int y = 14;
        ObservableList<Button> myButtons = FXCollections.observableArrayList();
        ObservableList<ToggleButton> myToggleButtons = FXCollections.observableArrayList();
        ToggleGroup toggles = new ToggleGroup();
        for (int i = 0; i < 5; i++) {
            String[] words = new String[]{"1" , "10", "50", "100", "MAX"};
            myToggleButtons.add(makeToggleButton(x, y, words[i]));
            y += 37;
        }
        y = 14;
        x= 14;
        for (Inventory item : Inventory.values()){
            String value = item.toString().substring(0, 1) + item.toString().substring(1).toLowerCase(Locale.ROOT);
            if (item.getType() == "meals"){
                Button b = makeButton(x,y,value, myToggleButtons);
                if (item.getQuantity() < 25){
                    b.setTextFill(Color.RED);
                }
                myButtons.add(b);
                y += 37;
            } else if (item.getType() == "sides"){
                if (x == 14){
                    x+= 86;
                    y = 14;
                }
                Button b = makeButton(x,y,value, myToggleButtons);
                if (item.getQuantity() < 25){
                    b.setTextFill(Color.RED);
                }
                myButtons.add(b);
                y += 37;
            }else if (item.getType() == "sauces"){
                if (x == 100){
                    x+= 86;
                    y = 14;
                }
                Button b = makeButton(x,y,value, myToggleButtons);
                if (item.getQuantity() < 25){
                    b.setTextFill(Color.RED);
                }
                myButtons.add(b);
                y += 37;
            }else if (item.getType() == "drinks"){
                if (x == 186){
                    x+= 86;
                    y = 14;
                }
                Button b = makeButton(x,y,value, myToggleButtons);
                if (item.getQuantity() < 25){
                    b.setTextFill(Color.RED);
                }
                myButtons.add(b);
                y += 37;
            } else{
                if (x == 272){
                    x+= 86;
                    y = 14;
                }
                Button b = makeButton(x,y,value, myToggleButtons);
                if (item.getQuantity() < 25){
                    b.setTextFill(Color.RED);
                }
                myButtons.add(b);
                y += 37;
            }
        }

        for (Button b: myButtons){
            screen2.getChildren().add(b);
        }

        for (ToggleButton tb: myToggleButtons) {
            tb.setToggleGroup(toggles);
            screen2.getChildren().add(tb);
        }
    }

    public Button makeButton(int x, int y, String value, ObservableList<ToggleButton> myToggleButtons){
        Button b = new Button();
        b.setPrefWidth(80);
        b.setLayoutX(x);
        b.setLayoutY(y);
        b.setText(value);
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int index = 5;
                for (int i = 0; i < myToggleButtons.size(); i++) {
                    if (myToggleButtons.get(i).isSelected()){
                        index = i;
                    }
                }
                if (index != 5) {
                    String amount = myToggleButtons.get(index).getText();
                    Inventory item = (Inventory.valueOf(b.getText().toUpperCase()));
                    Double maxAmount = kitchen.getMoney() / item.getMarketValue();
                    Supply order;
                    if (amount == "MAX") {
                        order = new Supply(item, Math.round(maxAmount.longValue()), kitchen.getMoney());
                    } else {
                        order = new Supply(item, Integer.parseInt(amount), kitchen.getMoney());
                    }
                    kitchen.setMoney(order.addSupply());
                    listSetup();
                    moneyChange();
                }
                if (Inventory.valueOf(b.getText().toUpperCase()).getQuantity() >25){
                    b.setTextFill(Color.BLACK);
                }
            }
        });
        return b;
    }

    public ToggleButton makeToggleButton(int x, int y, String value){
        ToggleButton b = new ToggleButton();
        b.setPrefWidth(80);
        b.setLayoutX(x);
        b.setLayoutY(y);
        b.setText(value);
        return b;
    }

    public void listSetup(){
        Inventory[] items = Inventory.values();
        ObservableList<Text> inventory = FXCollections.observableArrayList();
        ObservableList<Text> sellPrices = FXCollections.observableArrayList();
        ObservableList<Text> buyPrices = FXCollections.observableArrayList();
        for (Inventory item : items) {
            String thing = item.toString().substring(0, 1) + item.toString().substring(1, item.toString().length()).toLowerCase(Locale.ROOT);
            Text t1 = new Text();
            Text t2 = new Text();
            Text t3 = new Text();
            t1.setText(thing + " : " + item.getQuantity());
            t1.setFont(Font.font("Arial", 16));
            t2.setText(thing + " sell price: $" + df.format(item.getValue()));
            t2.setFont(Font.font("Arial", 16));
            t3.setText(thing + " buy price: $" + df.format(item.getMarketValue()));
            t3.setFont(Font.font("Arial", 16));
            if (item.getQuantity() < 25) {
                t1.setFill(Color.RED);
            }
            inventory.add(t1);
            sellPrices.add(t2);
            buyPrices.add(t3);
        }
        type.setItems(inventory);
        sellPrice.setItems(sellPrices);
        buyPrice.setItems(buyPrices);



    }

    public void editPriceList(){
        Inventory[] items = Inventory.values();
        ObservableList<Text> things = FXCollections.observableArrayList();
        ObservableList<TextField> priceChanges = FXCollections.observableArrayList();
        for (Inventory item: items){
            String thing = item.toString().substring(0, 1) + item.toString().substring(1, item.toString().length()).toLowerCase(Locale.ROOT);
            Text t1 = new Text();
            TextField t2 = new TextField();
            t1.setText(thing + " sell price: ");
            t1.setFont(Font.font("Arial", 16));
            t2.setText("$" + df.format(item.getValue()));
            t2.setFont(Font.font("Arial", 12));
            t2.setPrefHeight(18);
            t2.setEditable(true);
            t2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    double price;
                    if (t2.getText().charAt(0) == '$'){
                        price = Double.parseDouble(t2.getText().substring(1));
                    }else{
                        price = Double.parseDouble(t2.getText());
                    }
                    item.setValue(Double.parseDouble(df.format(price)));
                    t2.setText("$" + df.format(price));
                    listSetup();
                }
            });
            things.add(t1);
            priceChanges.add(t2);
        }
        priceEdit1.setItems(things);
        priceEdit2.setItems(priceChanges);
    }

    public void editThresholdList(){
        AutoBuy[] items = AutoBuy.values();
        ObservableList<Text> things = FXCollections.observableArrayList();
        ObservableList<TextField> thresholds = FXCollections.observableArrayList();
        ObservableList<TextField> buyAmount = FXCollections.observableArrayList();
        for (AutoBuy item : items){
            String thing = item.toString().substring(0, 1) + item.toString().substring(1, item.toString().length()).toLowerCase(Locale.ROOT);
            Text t1 = new Text();
            TextField t2 = new TextField();
            TextField t3 = new TextField();
            t1.setText(thing + " threshold: ");
            t1.setFont(Font.font("Arial", 16));
            t2.setText(String.valueOf(item.getThreshold()));
            t2.setFont(Font.font("Arial", 12));
            t2.setPrefHeight(18);
            t2.setEditable(true);
            t2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    item.setThreshold(Integer.parseInt(t2.getText()));
                }
            });
            t3.setText(String.valueOf(item.getBuyAmount()));
            t3.setFont(Font.font("Arial", 12));
            t3.setPrefHeight(18);
            t3.setEditable(true);
            t3.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                   item.setBuyAmount(Integer.parseInt(t3.getText()));
                }
            });
            things.add(t1);
            thresholds.add(t2);
            buyAmount.add(t3);
        }
        Threshold1.setItems(things);
        Threshold2.setItems(thresholds);
        Threshold3.setItems(buyAmount);
    }

    public void buyAutoBuy(){
        if (kitchen.getMoney() > 1500 && autobuy == false){
            autobuy = true;
            enableAB.setDisable(false);
            kitchen.setMoney(kitchen.getMoney() - 1500);
            moneyChange();
            abText.setText("");
        }
    }

    public void enableAB(){
        if (!enableAB.isSelected()){
            autobuy = false;
        }
    }

    public void moneyChange(){
        money.setText("Money Available : $" + df.format(kitchen.getMoney()));
    }


    public void reset() {
        kitchen = new Kitchen(30);
        for (Inventory item : Inventory.values()){
            item.setQuantity(ogQuantity.get(item));
            item.setValue(item.getOgValue());
        }
        autobuy = false;
        loser = false;
        mainScreen();
        moneyChange();
        enableAB.setDisable(true);
        playAgain.setDisable(true);
    }

    public void loserCheck(){
        if (kitchen.getMoney() < 0){
            loser = true;
            playAgain.setDisable(false);
            textS1.setText("You're a LOSER!");
        }
    }
}
