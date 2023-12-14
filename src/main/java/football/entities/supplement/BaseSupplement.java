package football.entities.supplement;

public abstract class BaseSupplement implements Supplement{
    public BaseSupplement(int energy, double price) {
        this.energy = energy;
        this.price = price;
    }

    private int energy;
    private double price;
    @Override
    public int getEnergy() {
        return this.energy;
    }

    @Override
    public double getPrice() {
        return this.price;
    }
}
