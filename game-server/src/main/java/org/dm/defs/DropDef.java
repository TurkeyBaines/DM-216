package org.dm.defs;

import java.util.Objects;

/**
 * @author Jire
 */
public final class DropDef {

    private final int id;
    private final String name;
    private final boolean members;
    private final String quantity;
    private final boolean noted;
    private final double rarity;
    private final int rolls;

    public DropDef(
            int id,
            String name,
            boolean members,
            String quantity,
            boolean noted,
            double rarity,
            int rolls
    ) {
        this.id = id;
        this.name = name;
        this.members = members;
        this.quantity = quantity;
        this.noted = noted;
        this.rarity = rarity;
        this.rolls = rolls;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isMembers() {
        return members;
    }

    public String getQuantity() {
        return quantity;
    }

    public boolean isNoted() {
        return noted;
    }

    public double getRarity() {
        return rarity;
    }

    public int getRolls() {
        return rolls;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DropDef dropDef = (DropDef) o;
        return id == dropDef.id &&
                members == dropDef.members &&
                noted == dropDef.noted &&
                Double.compare(dropDef.rarity, rarity) == 0 &&
                rolls == dropDef.rolls &&
                Objects.equals(name, dropDef.name) &&
                Objects.equals(quantity, dropDef.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, members, quantity, noted, rarity, rolls);
    }

    @Override
    public String toString() {
        return "DropDef{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", members=" + members +
                ", quantity='" + quantity + '\'' +
                ", noted=" + noted +
                ", rarity=" + rarity +
                ", rolls=" + rolls +
                '}';
    }
}