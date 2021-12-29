package simulation.sim;

public enum AutoBuy {
    BURGERS(65,80),
    NUGGETS(65,80),
    SANDWICHES(65,80),
    CHIPS(65,80),
    FRIES(65,80),
    FRUITS(65,80),
    BARBEQUE(65,80),
    KETCHUP(65,80),
    MUSTARD(65,80),
    BUFFALO(65,80),
    SODAS(65,80),
    WATERS(65,80),
    CAKES(65,80),
    BROWNIES(65,80),
    PIES(65,80);

    private int threshold;
    private int buyAmount;

    private AutoBuy(int threshold, int buyAmount){
        this.threshold = threshold;
        this.buyAmount = buyAmount;
    }

    public int getBuyAmount() {
        return buyAmount;
    }
    public int getThreshold() {
        return threshold;
    }
    public void setBuyAmount(int buyAmount) {
        this.buyAmount = buyAmount;
    }
    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }
}
