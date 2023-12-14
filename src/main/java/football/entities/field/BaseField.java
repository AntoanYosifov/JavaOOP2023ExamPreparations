package football.entities.field;

import football.ValidationUtils;
import football.entities.player.Player;
import football.entities.supplement.Supplement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import static football.common.ConstantMessages.NOT_ENOUGH_CAPACITY;
import static football.common.ExceptionMessages.FIELD_NAME_NULL_OR_EMPTY;

public abstract class BaseField implements Field {
    private String name;
    private int capacity;
    private Collection<Supplement> supplements;
    private Collection<Player> players;

    public BaseField(String name, int capacity) {
        this.setName(name);
        this.setCapacity(capacity);
        this.supplements = new ArrayList<>();
        this.players = new ArrayList<>();
    }

    private void setName(String name) {
        ValidationUtils.validateString(name, FIELD_NAME_NULL_OR_EMPTY);
        this.name = name;
    }

    private void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public int sumEnergy() {
        return this.supplements.stream().mapToInt(Supplement::getEnergy).sum();
    }

    @Override
    public void addPlayer(Player player) {
        if (this.capacity == this.players.size()) {
            throw new IllegalStateException(NOT_ENOUGH_CAPACITY);
        }
        this.players.add(player);

    }

    @Override
    public void removePlayer(Player player) {
        this.players.remove(player);
    }

    @Override
    public void addSupplement(Supplement supplement) {
        this.supplements.add(supplement);
    }

    @Override
    public void drag() {
        this.players.forEach(Player::stimulation);
    }

    @Override
    public String getInfo() {
        // "{fieldName} ({fieldType}):
        //Player: {playerName1} {playerName2} {playerName3} (…) / Player: none
        //Supplement: {supplementsCount}
        //Energy: {sumEnergy}"

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s (%s):", getName(), this.getClass().getSimpleName()));
        sb.append(System.lineSeparator());
        sb.append("Player: ");
        if (this.players.isEmpty()) {
            sb.append("none");
        } else {
            String playersNames = this.players.stream().map(Player::getName).collect(Collectors.joining(" "));
            sb.append(playersNames);
        }
        sb.append(System.lineSeparator());
        sb.append(String.format("Supplement: %d", this.supplements.size()));
        sb.append(System.lineSeparator());
        sb.append(String.format("Energy: %d", sumEnergy()));

        return sb.toString();
    }

    @Override
    public Collection<Player> getPlayers() {
        return this.players;
    }

    @Override
    public Collection<Supplement> getSupplements() {
        return this.supplements;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
