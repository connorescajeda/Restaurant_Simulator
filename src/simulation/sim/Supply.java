package simulation.sim;

public class Supply{
    Inventory item;
    int amount;
    double money;

    public Supply(Inventory item, int amount, double money){
        this.item = item;
        this.amount = amount;
        this.money = money;
    }

    public double addSupply(){
        double cost = item.getMarketValue() * amount;
        if (money - cost > 0) {
            item.setQuantity(item.getQuantity() + amount);
            money -= cost;
        }
        else{
            System.out.println("You don't have enough money for that");
        }
        return money;
    }
}
