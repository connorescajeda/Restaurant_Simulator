package simulation.sim;


import java.text.DecimalFormat;
import java.util.*;

public class Kitchen{

    private int otherC = 40;
    private int customers = 40;
    private int totalServed = customers;
    private int days;
    private int day = 0;
    private Random rand = new Random();
    private double start;
    private double money = 100;
    private double profit;
    private Integer[] losses = new Integer[]{0,0};
    private HashSet<Inventory> empty;
    private ArrayList<Integer> before;
    private ArrayList<String> after;

    public Kitchen(int days){
        this.days = days;
    }

    public void running() {
        if (day < days) {
            day++;
            beforeQuantity();
            losses = new Integer[]{0,0};
            start = money;
            otherC = customers;
            for (int j = 0; j < customers; j++) {
                int typeOrder = rand.nextInt(11);
                Order order = new Order(typeOrder);
                Integer[] loss = order.removeInventory();
                losses[0] += loss[0];
                losses[1] += loss[1];
                money += order.getProfit();
                empty = order.checkQuantity();
                totalServed += 1;
            }
            moreCustomers();
            varyMarketvalue();
            profit += getDayProfit();
            afterQuantity();
        }
    }
    public void varyMarketvalue(){
        double min = 0;
        double max;
        Boolean[] type = new Boolean[]{true, false};
        int sign;
        double random;
        for (Inventory item : Inventory.values()){
            if (item.getType() == "meals"){
                max = 1.50;
            } else if (item.getType() == "sides") {
                max = 0.75;
            }else if (item.getType() == "sauces") {
                max = 0.05;
            }else if (item.getType() == "drinks") {
                max = 0.25;
            }else{
                max = 0.75;
            }
            random = Math.random() * max;
            if (type[rand.nextInt(2)]){
                sign = 1;
            } else{
                sign = -1;
            }
            double cost = item.getOgMValue() + (sign * random);
            DecimalFormat df = new DecimalFormat("#.00");
            item.setMarketValue(Double.parseDouble(df.format(cost)));
        }

    }

    public void moreCustomers(){
        int amount = customers + (int) Math.round(profit / 100) * 2;
        customers = rand.nextInt(amount) + 40;
    }

    public void beforeQuantity(){
        Inventory[] items = Inventory.values();
        ArrayList<Integer> aDay = new ArrayList<>();
        for (Inventory item : items){
            aDay.add(item.getQuantity());
        }
        before = aDay;
    }

    public void afterQuantity(){
        Inventory[] items = Inventory.values();
        ArrayList<String> wholeDay = new ArrayList<>();
        for (int i = 0; i < items.length; i++){
            if(before.get(i) > 0) {
                wholeDay.add("You used " + (before.get(i) - items[i].getQuantity()) + " " + items[i].toString().toLowerCase(Locale.ROOT) + " today.");
            }
        }
        after = wholeDay;
    }

    public String getEmpty(){
        String emptyThings = "";
        if (empty != null){
            for (Inventory item : empty) {
                emptyThings += "You ran out of " + item.toString().toLowerCase(Locale.ROOT) + "\n";
            }
        }
        return emptyThings;
    }

    public String getAfter() {
        String thing = "";
        if (day > 0) {
            for (String phrase : after) {
                thing += phrase + "\n";
            }
        }
        return thing;
    }

    public double getDayProfit(){
        if (day > 0) {
            return money - start;
        }
        return 0.00;
    }

    public double getProfit() { return profit; }
    public double getMoney(){ return money; }
    public Integer[] getLosses() { return losses; }
    public int getDay(){return day;}
    public int getCustomers(){
        if (day == 0){
            return 0;
        }
        return otherC;
    }
    public void setMoney(double amount){ money = amount;}
    public void setCustomers(int customers) {
        this.customers = customers;
    }

    public ArrayList<Integer> getBefore(){ return before;}



}


