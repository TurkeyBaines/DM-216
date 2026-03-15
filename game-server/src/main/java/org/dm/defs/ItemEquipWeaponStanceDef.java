package org.dm.defs;

import com.google.gson.annotations.SerializedName;

/**
 * @author Jire
 */
public final class ItemEquipWeaponStanceDef {

    @SerializedName("combat_style")
    private final String combatStyle;

    @SerializedName("attack_type")
    private final String attackType;

    @SerializedName("attack_style")
    private final String attackStyle;

    private final String experience;
    private final String boosts;

    public ItemEquipWeaponStanceDef(
            String combatStyle,
            String attackType,
            String attackStyle,
            String experience,
            String boosts
    ) {
        this.combatStyle = combatStyle;
        this.attackType = attackType;
        this.attackStyle = attackStyle;
        this.experience = experience;
        this.boosts = boosts;
    }

    public String getCombatStyle() {
        return combatStyle;
    }

    public String getAttackType() {
        return attackType;
    }

    public String getAttackStyle() {
        return attackStyle;
    }

    public String getExperience() {
        return experience;
    }

    public String getBoosts() {
        return boosts;
    }

    @Override
    public String toString() {
        return "ItemEquipWeaponStanceDef{" +
                "combatStyle='" + combatStyle + '\'' +
                ", attackType='" + attackType + '\'' +
                ", attackStyle='" + attackStyle + '\'' +
                ", experience='" + experience + '\'' +
                ", boosts='" + boosts + '\'' +
                '}';
    }
}