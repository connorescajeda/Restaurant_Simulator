package simulation.sim;

import javafx.collections.ObservableList;

public enum Inventory {
    BURGERS(5.00, 50, "meals", 3.00,5.00, 3.00),
    NUGGETS(4.00, 50, "meals", 2.50,4.00, 3.00),
    SANDWICHES(5.00, 50, "meals", 3.00,5.00, 3.00),
    CHIPS(2.00, 35, "sides", 1.50,2.00,1.50),
    FRIES(2.50, 35, "sides", 1.50,2.50, 1.50),
    FRUITS(1.00, 45, "sides", 1.50,1.00,1.50),
    BARBEQUE(.50, 60,"sauces", 0.10,.50,0.10),
    KETCHUP(.50, 100, "sauces", .15,.50,0.15),
    MUSTARD(.50, 100, "sauces", 0.10,.50,0.10),
    BUFFALO(.50, 50, "sauces", 0.30,.50,0.30),
    SODAS(2.00, 50, "drinks", 1.25,2.00,1.25),
    WATERS(1.00, 50, "drinks", 0.75,1.00,0.75),
    CAKES(3.50, 25, "dessert", 1.50,3.50,1.50),
    BROWNIES(2.50, 25, "dessert", 1.25,2.50,1.25),
    PIES(4.50, 25, "dessert", 2.00,4.50,2.00);

    // Fields
    private double value;
    private int quantity;
    private String type;
    private double marketValue;
    private double ogValue;
    private double ogMValue;

    //Constructor
    private Inventory(double value, int quantity, String type, double marketValue, double ogValue, double ogMValue){
        this.value = value;
        this.quantity = quantity;
        this.type = type;
        this.marketValue = marketValue;
        this.ogValue = ogValue;
        this.ogMValue = ogMValue;
    }
    //Methods
    public double getValue(){
        return value;
    }
    public int getQuantity(){
        return quantity;
    }
    public String getType(){
        return type;
    }
    public double getMarketValue() { return marketValue; }
    public double getOgValue() { return ogValue; }
    public double getOgMValue() {
        return ogMValue;
    }
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
    public void setValue(double value) {this.value = value; }
    public void setMarketValue(double mValue) {this.marketValue = mValue; }


    public ObservableList<Inventory> listValue(){
        Inventory[] items = Inventory.values();
        ObservableList<Inventory> newItems = null;
        for (int i = 0; i < items.length; i++){
            newItems.add(items[i]);
        }
        return newItems;
    }


}
