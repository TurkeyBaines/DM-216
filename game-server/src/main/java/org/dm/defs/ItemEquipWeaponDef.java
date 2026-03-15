package org.dm.defs;

import com.google.gson.annotations.SerializedName;
import java.util.Arrays;

/**
 * @author Jire
 */
public final class ItemEquipWeaponDef {

    @SerializedName("attack_speed")
    private final int attackSpeed;

    @SerializedName("weapon_type")
    private final String weaponType;

    private final ItemEquipWeaponStanceDef[] stances;

    public ItemEquipWeaponDef(int attackSpeed, String weaponType, ItemEquipWeaponStanceDef[] stances) {
        this.attackSpeed = attackSpeed;
        this.weaponType = weaponType;
        this.stances = stances;
    }

    public int getAttackSpeed() {
        return attackSpeed;
    }

    public String getWeaponType() {
        return weaponType;
    }

    public ItemEquipWeaponStanceDef[] getStances() {
        return stances;
    }

    @Override
    public String toString() {
        return "ItemEquipWeaponDef{" +
                "attackSpeed=" + attackSpeed +
                ", weaponType='" + weaponType + '\'' +
                ", stances=" + Arrays.toString(stances) +
                '}';
    }
}