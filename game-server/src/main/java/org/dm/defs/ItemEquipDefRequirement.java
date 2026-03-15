package org.dm.defs;

/**
 * @author Jire
 */
public final class ItemEquipDefRequirement {

    private final int attack;
    private final int defence;
    private final int strength;
    private final int hitpoints;
    private final int ranged;
    private final int prayer;
    private final int magic;
    private final int cooking;
    private final int woodcutting;
    private final int fletching;
    private final int fishing;
    private final int firemaking;
    private final int crafting;
    private final int smithing;
    private final int mining;
    private final int herblore;
    private final int agility;
    private final int thieving;
    private final int slayer;
    private final int farming;
    private final int runecrafting;
    private final int hunter;

    public ItemEquipDefRequirement(int attack, int defence, int strength, int hitpoints, int ranged,
                                   int prayer, int magic, int cooking, int woodcutting, int fletching,
                                   int fishing, int firemaking, int crafting, int smithing, int mining,
                                   int herblore, int agility, int thieving, int slayer, int farming,
                                   int runecrafting, int hunter) {
        this.attack = attack;
        this.defence = defence;
        this.strength = strength;
        this.hitpoints = hitpoints;
        this.ranged = ranged;
        this.prayer = prayer;
        this.magic = magic;
        this.cooking = cooking;
        this.woodcutting = woodcutting;
        this.fletching = fletching;
        this.fishing = fishing;
        this.firemaking = firemaking;
        this.crafting = crafting;
        this.smithing = smithing;
        this.mining = mining;
        this.herblore = herblore;
        this.agility = agility;
        this.thieving = thieving;
        this.slayer = slayer;
        this.farming = farming;
        this.runecrafting = runecrafting;
        this.hunter = hunter;
    }

    public int[] toArray() {
        return new int[]{
                attack,
                defence,
                strength,
                hitpoints,
                ranged,
                prayer,
                magic,
                cooking,
                woodcutting,
                fletching,
                fishing,
                firemaking,
                crafting,
                smithing,
                mining,
                herblore,
                agility,
                thieving,
                slayer,
                farming,
                runecrafting,
                hunter
        };
    }

    // Getters
    public int getAttack() { return attack; }
    public int getDefence() { return defence; }
    public int getStrength() { return strength; }
    public int getHitpoints() { return hitpoints; }
    public int getRanged() { return ranged; }
    public int getPrayer() { return prayer; }
    public int getMagic() { return magic; }
    public int getCooking() { return cooking; }
    public int getWoodcutting() { return woodcutting; }
    public int getFletching() { return fletching; }
    public int getFishing() { return fishing; }
    public int getFiremaking() { return firemaking; }
    public int getCrafting() { return crafting; }
    public int getSmithing() { return smithing; }
    public int getMining() { return mining; }
    public int getHerblore() { return herblore; }
    public int getAgility() { return agility; }
    public int getThieving() { return thieving; }
    public int getSlayer() { return slayer; }
    public int getFarming() { return farming; }
    public int getRunecrafting() { return runecrafting; }
    public int getHunter() { return hunter; }
}