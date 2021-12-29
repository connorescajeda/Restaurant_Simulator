package simulation.sim;

import javafx.scene.layout.Pane;

import java.util.*;

public class Order{
    String meal;
    Random rand = new Random();
    private double profit = 0;
    ArrayList<Inventory> meals = types("meals");
    ArrayList<Inventory> sides = types("sides");
    ArrayList<Inventory> drinks = types("drinks");
    ArrayList<Inventory> sauces = types("sauces");
    ArrayList<Inventory> dessert = types("dessert");
    HashSet<Inventory> empty = new HashSet<>();
    Deque<ArrayList<Inventory>> stock = new ArrayDeque<>();

    //Creates the type of order
    public Order(int type){
        if (type < 3){
            meal = "only";
        } else if (3 <= type && type <= 5){
            meal = "dinner";
        } else if (5 < type && type <= 8){
            meal = "meal";
        } else{
            meal = "full";
        }
        stock.add(meals);
        stock.add(sauces);
        stock.add(sides);
        stock.add(drinks);
        stock.add(dessert);
    }
    //Creates a list of the different categories of items sold in the establishment based on what type you put in
    public ArrayList<Inventory> types(String type){
        ArrayList<Inventory> items = new ArrayList<>();
        for (int i = 0; i < Inventory.values().length; i++){
            if (Inventory.values()[i].getType().equals(type)){
                items.add((Inventory.values()[i]));
            }
        }
        return items;
    }
    //Removes the items needed from our existing inventory
    public Integer[] removeInventory(){
        Integer[] s;
        if (meal == "only"){
          s = choiceMaker(2);
        } else if (meal == "dinner"){
            s = choiceMaker(3);
        } else if (meal == "meal"){
            s = choiceMaker(4);
        }
        else{
            s = choiceMaker(5);
        }
        return s;
    }

    //Makes choices for what items is in an order
    public Integer[] choiceMaker(int numberOfChoices){
        ArrayList<Inventory> items;
        Inventory item;
        Integer[] losses = new Integer[]{0,0};
        if (mealCheck()) {
            for (int i = 0; i < numberOfChoices; i++) {
                items = stock.remove();
                Demand demand = new Demand(items);
                demand.changeDemand();
                item = demand.pick(rand.nextDouble());
                if (demand.isRuined()) {
                    losses[1] += 1;
                    profit -= 5;
                } else {
                    while (item == null) {
                        item = demand.pick(rand.nextDouble());
                    }
                }
                if (demand.isRuined() == false) {
                    if (item.getQuantity() <= 0) {
                        profit -= 5;
                        losses[0] += 1;
                    }
                    if (item.getQuantity() > 0) {
                        item.setQuantity(item.getQuantity() - 1);
                        profit += item.getValue();
                    }
                }
            }
        } else{
            losses[1] += numberOfChoices;
            profit -= (5 * numberOfChoices);
        }
        return losses;
    }

    public boolean mealCheck(){
        int count = 0;
        for (Inventory item : meals){
            if (item.getQuantity() == 0){
                count++;
            }
            if (count == meals.size()){
                return false;
            }
        }
        return true;
    }

    public HashSet<Inventory> checkQuantity(){
        for (Inventory item : Inventory.values()){
            if (item.getQuantity() == 0){
                empty.add(item);
            }
        }
        return empty;
    }
    public double getProfit(){return profit;}

}
