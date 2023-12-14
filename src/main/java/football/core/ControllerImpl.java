package football.core;


import football.entities.field.ArtificialTurf;
import football.entities.field.Field;
import football.entities.field.NaturalGrass;
import football.entities.player.Men;
import football.entities.player.Player;
import football.entities.player.Women;
import football.entities.supplement.Liquid;
import football.entities.supplement.Powdered;
import football.entities.supplement.Supplement;
import football.repositories.SupplementRepository;
import football.repositories.SupplementRepositoryImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import static football.common.ConstantMessages.*;
import static football.common.ExceptionMessages.*;

public class ControllerImpl implements Controller {
    private SupplementRepository supplement;
    private Collection<Field> fields;

    public ControllerImpl() {
        this.supplement = new SupplementRepositoryImpl();
        this.fields = new ArrayList<>();
    }

    @Override
    public String addField(String fieldType, String fieldName) {
        Field field;
        switch (fieldType) {
            case "ArtificialTurf":
                field = new ArtificialTurf(fieldName);
                break;
            case "NaturalGrass":
                field = new NaturalGrass(fieldName);
                break;
            default:
                throw new NullPointerException(INVALID_FIELD_TYPE);
        }

        this.fields.add(field);

        return String.format(SUCCESSFULLY_ADDED_FIELD_TYPE, fieldType);
    }


    @Override
    public String deliverySupplement(String type) {
        Supplement supplementToAdd;
        switch (type) {
            case "Powdered":
                supplementToAdd = new Powdered();
                break;
            case "Liquid":
                supplementToAdd = new Liquid();
                break;
            default:
                throw new IllegalArgumentException(INVALID_SUPPLEMENT_TYPE);

        }
        this.supplement.add(supplementToAdd);

        return String.format(SUCCESSFULLY_ADDED_SUPPLEMENT_TYPE, type);
    }

    @Override
    public String supplementForField(String fieldName, String supplementType) {
        Supplement supplementToFind = this.supplement.findByType(supplementType);
        if (supplementToFind == null) {
            throw new IllegalArgumentException(String.format(NO_SUPPLEMENT_FOUND, supplementType));
        }

        Field field = this.fields.stream().filter(f -> f.getName().equals(fieldName)).findFirst().orElse(null);
        if (field == null) {
            return "";
        }
        field.addSupplement(supplementToFind);
        this.supplement.remove(supplementToFind);

        return String.format(SUCCESSFULLY_ADDED_SUPPLEMENT_IN_FIELD, supplementType, fieldName);
    }

    @Override
    public String addPlayer(String fieldName, String playerType, String playerName, String nationality, int strength) {
        Player player;
        switch (playerType) {
            case "Men":
                player = new Men(playerName, nationality, strength);
                break;
            case "Women":
                player = new Women(playerName, nationality, strength);
                break;
            default:
                throw new IllegalArgumentException(INVALID_PLAYER_TYPE);
        }
        Field field = this.fields.stream().filter(f -> f.getName().equals(fieldName)).findFirst().orElse(null);
        if (field == null) {
            throw new IllegalStateException("Filed does not exist.");
        }
        String fieldType = field.getClass().getSimpleName();

        if ((playerType.equals("Men") && fieldType.equals("ArtificialTurf")) ||
                (playerType.equals("Women") && fieldType.equals("NaturalGrass"))) {
            return FIELD_NOT_SUITABLE;
        }
        field.addPlayer(player);

        return String.format(SUCCESSFULLY_ADDED_PLAYER_IN_FIELD, playerType, fieldName);
    }

    @Override
    public String dragPlayer(String fieldName) {
        Field field = this.fields.stream().filter(f -> f.getName().equals(fieldName)).findFirst().orElse(null);
        if (field == null) {
            throw new IllegalStateException("Filed does not exist.");
        }

        field.drag();

        return String.format(PLAYER_DRAG, field.getPlayers().size());
    }

    @Override
    public String calculateStrength(String fieldName) {
        Field field = this.fields.stream().filter(f -> f.getName().equals(fieldName)).findFirst().orElse(null);
        if (field == null) {
            throw new IllegalStateException("Filed does not exist.");
        }
        int sumStrength = field.getPlayers().stream().mapToInt(Player::getStrength).sum();

        return String.format(STRENGTH_FIELD,fieldName, sumStrength);
    }

    @Override
    public String getStatistics() {
        return this.fields.stream().map(Field::getInfo).collect(Collectors.joining(System.lineSeparator()));
    }
}
