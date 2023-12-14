package football.entities.player;

import football.ValidationUtils;

import static football.common.ExceptionMessages.*;

public abstract class BasePlayer implements Player {
    private String name;
    private String nationality;
    private double kg;
    private int strength;

    public BasePlayer(String name, String nationality, double kg, int strength) {
        this.setName(name);
        this.setNationality(nationality);
        this.setKg(kg);
        this.setStrength(strength);
    }

    private void setNationality(String nationality) {
        ValidationUtils.validateString(nationality, PLAYER_NATIONALITY_NULL_OR_EMPTY);
        this.nationality = nationality;
    }

    private void setKg(double kg) {
        this.kg = kg;
    }

    private void setStrength(int strength) {
        ValidationUtils.validateInt(strength, PLAYER_STRENGTH_BELOW_OR_EQUAL_ZERO);
        this.strength = strength;
    }

    @Override
    public void setName(String name) {
        ValidationUtils.validateString(name, PLAYER_NAME_NULL_OR_EMPTY);
        this.name = name;
    }

    @Override
    public abstract void stimulation();


    @Override
    public double getKg() {
        return this.kg;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getStrength() {
        return this.strength;
    }

    protected void doStimulation(int units) {
        this.strength += units;
    }
}
