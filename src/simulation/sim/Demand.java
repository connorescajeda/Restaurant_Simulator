package simulation.sim;

import java.util.ArrayList;
import java.util.HashMap;

public class Demand {
    ArrayList<Inventory> types;
    ArrayList<Double> bounds = new ArrayList<>();
    HashMap<Inventory, Boolean> overpriced = new HashMap<>();
    boolean ruined = false;


    public Demand(ArrayList<Inventory> types){
        this.types = types;
    }

    public void bounds(){
        for (int i = 0; i < types.size() + 1; i++) {
            bounds.add((double) (i/ types.size()));
        }
    }


    public void changeDemand() {
        double sum = 0;
        bounds();
        for (Inventory item : types) {
            sum += 1 / item.getValue();
        }
        int count  = 1;
        for (Inventory item : types){
            if (item.getValue() > item.getOgValue() * 2.25){
                overpriced.put(item, true);
            }
            if (overpriced.get(item) != null && overpriced.get(item)){
                bounds.add(count, bounds.get(count - 1));
            }
            else{
                bounds.add(count, (1 / item.getValue()) / sum + bounds.get(count - 1));
            }
            count++;
        }
        if (overpriced.size() == types.size()){
            ruined = true;
        } else{
            ruined = false;
        }

    }
    public Inventory pick(double choice){
        for (int i = 0; i < types.size(); i++) {
            if (bounds.get(i) < choice && choice < bounds.get(i + 1)){
                return types.get(i);
            }
        }
        return null;
    }

    public boolean isRuined() {
        return ruined;
    }
}
