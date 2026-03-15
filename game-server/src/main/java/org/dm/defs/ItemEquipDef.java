package org.dm.defs;

import com.google.gson.annotations.SerializedName;

/**
 * @author Jire
 */
public final class ItemEquipDef {

    @SerializedName("attack_stab")
    private final int attackStab;
    @SerializedName("attack_slash")
    private final int attackSlash;
    @SerializedName("attack_crush")
    private final int attackCrush;
    @SerializedName("attack_magic")
    private final int attackMagic;
    @SerializedName("attack_ranged")
    private final int attackRanged;

    @SerializedName("defence_stab")
    private final int defenceStab;
    @SerializedName("defence_slash")
    private final int defenceSlash;
    @SerializedName("defence_crush")
    private final int defenceCrush;
    @SerializedName("defence_magic")
    private final int defenceMagic;
    @SerializedName("defence_ranged")
    private final int defenceRanged;

    @SerializedName("melee_strength")
    private final int meleeStrength;
    @SerializedName("ranged_strength")
    private final int rangedStrength;

    @SerializedName("magic_damage")
    private final int magicDamage;

    private final int prayer;
    private final String slot;
    private final ItemEquipDefRequirement requirements;

    public ItemEquipDef(int attackStab, int attackSlash, int attackCrush, int attackMagic, int attackRanged,
                        int defenceStab, int defenceSlash, int defenceCrush, int defenceMagic, int defenceRanged,
                        int meleeStrength, int rangedStrength, int magicDamage, int prayer, String slot,
                        ItemEquipDefRequirement requirements) {
        this.attackStab = attackStab;
        this.attackSlash = attackSlash;
        this.attackCrush = attackCrush;
        this.attackMagic = attackMagic;
        this.attackRanged = attackRanged;
        this.defenceStab = defenceStab;
        this.defenceSlash = defenceSlash;
        this.defenceCrush = defenceCrush;
        this.defenceMagic = defenceMagic;
        this.defenceRanged = defenceRanged;
        this.meleeStrength = meleeStrength;
        this.rangedStrength = rangedStrength;
        this.magicDamage = magicDamage;
        this.prayer = prayer;
        this.slot = slot;
        this.requirements = requirements;
    }

    public int[] toBonusArray() {
        return new int[]{
                attackStab,
                attackSlash,
                attackCrush,
                attackMagic,
                attackRanged,

                defenceStab,
                defenceSlash,
                defenceCrush,
                defenceMagic,
                defenceRanged,

                meleeStrength,
                rangedStrength,
                magicDamage,
                prayer
        };
    }

    public int getAttackStab() { return attackStab; }
    public int getAttackSlash() { return attackSlash; }
    public int getAttackCrush() { return attackCrush; }
    public int getAttackMagic() { return attackMagic; }
    public int getAttackRanged() { return attackRanged; }
    public int getDefenceStab() { return defenceStab; }
    public int getDefenceSlash() { return defenceSlash; }
    public int getDefenceCrush() { return defenceCrush; }
    public int getDefenceMagic() { return defenceMagic; }
    public int getDefenceRanged() { return defenceRanged; }
    public int getMeleeStrength() { return meleeStrength; }
    public int getRangedStrength() { return rangedStrength; }
    public int getMagicDamage() { return magicDamage; }
    public int getPrayer() { return prayer; }
    public String getSlot() { return slot; }
    public ItemEquipDefRequirement getRequirements() { return requirements; }
}