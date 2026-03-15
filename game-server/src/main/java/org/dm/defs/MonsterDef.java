package org.dm.defs;

import com.google.gson.annotations.SerializedName;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author Jire
 */
public final class MonsterDef {

    private final int id;
    private final String name;
    @SerializedName("last_updated")
    private final String lastUpdated;
    private final boolean incomplete;
    private final boolean members;
    @SerializedName("release_date")
    private final String releaseDate;
    @SerializedName("combat_level")
    private final int combatLevel;
    private final int size;
    private final int hitpoints;
    @SerializedName("max_hit")
    private final int maxHit;
    @SerializedName("attack_type")
    private final String[] attackType;
    @SerializedName("attack_speed")
    private final int attackSpeed;
    private final boolean aggressive;
    private final boolean poisonous;
    private final boolean venomous;
    @SerializedName("immune_poison")
    private final boolean immunePoison;
    @SerializedName("immune_venom")
    private final boolean immuneVenom;
    private final String[] attributes;
    private final String[] category;
    @SerializedName("slayer_monster")
    private final boolean slayerMonster;
    @SerializedName("slayer_level")
    private final int slayerLevel;
    @SerializedName("slayer_xp")
    private final double slayerXp;
    @SerializedName("slayer_masters")
    private final String[] slayerMasters;
    private final boolean duplicate;
    private final String examine;
    @SerializedName("wiki_name")
    private final String wikiName;
    @SerializedName("wiki_url")
    private final String wikiURL;
    @SerializedName("attack_level")
    private final int attackLevel;
    @SerializedName("strength_level")
    private final int strengthLevel;
    @SerializedName("defence_level")
    private final int defenceLevel;
    @SerializedName("magic_level")
    private final int magicLevel;
    @SerializedName("ranged_level")
    private final int rangedLevel;
    @SerializedName("attack_bonus")
    private final int attackBonus;
    @SerializedName("strength_bonus")
    private final int strengthBonus;
    @SerializedName("attack_magic")
    private final int attackMagic;
    @SerializedName("magic_bonus")
    private final int magicBonus;
    @SerializedName("attack_ranged")
    private final int attackRanged;
    @SerializedName("ranged_bonus")
    private final int rangedBonus;
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
    private final DropDef[] drops;

    public MonsterDef(int id, String name, String lastUpdated, boolean incomplete, boolean members,
                      String releaseDate, int combatLevel, int size, int hitpoints, int maxHit,
                      String[] attackType, int attackSpeed, boolean aggressive, boolean poisonous,
                      boolean venomous, boolean immunePoison, boolean immuneVenom, String[] attributes,
                      String[] category, boolean slayerMonster, int slayerLevel, double slayerXp,
                      String[] slayerMasters, boolean duplicate, String examine, String wikiName,
                      String wikiURL, int attackLevel, int strengthLevel, int defenceLevel,
                      int magicLevel, int rangedLevel, int attackBonus, int strengthBonus,
                      int attackMagic, int magicBonus, int attackRanged, int rangedBonus,
                      int defenceStab, int defenceSlash, int defenceCrush, int defenceMagic,
                      int defenceRanged, DropDef[] drops) {
        this.id = id;
        this.name = name;
        this.lastUpdated = lastUpdated;
        this.incomplete = incomplete;
        this.members = members;
        this.releaseDate = releaseDate;
        this.combatLevel = combatLevel;
        this.size = size;
        this.hitpoints = hitpoints;
        this.maxHit = maxHit;
        this.attackType = attackType;
        this.attackSpeed = attackSpeed;
        this.aggressive = aggressive;
        this.poisonous = poisonous;
        this.venomous = venomous;
        this.immunePoison = immunePoison;
        this.immuneVenom = immuneVenom;
        this.attributes = attributes;
        this.category = category;
        this.slayerMonster = slayerMonster;
        this.slayerLevel = slayerLevel;
        this.slayerXp = slayerXp;
        this.slayerMasters = slayerMasters;
        this.duplicate = duplicate;
        this.examine = examine;
        this.wikiName = wikiName;
        this.wikiURL = wikiURL;
        this.attackLevel = attackLevel;
        this.strengthLevel = strengthLevel;
        this.defenceLevel = defenceLevel;
        this.magicLevel = magicLevel;
        this.rangedLevel = rangedLevel;
        this.attackBonus = attackBonus;
        this.strengthBonus = strengthBonus;
        this.attackMagic = attackMagic;
        this.magicBonus = magicBonus;
        this.attackRanged = attackRanged;
        this.rangedBonus = rangedBonus;
        this.defenceStab = defenceStab;
        this.defenceSlash = defenceSlash;
        this.defenceCrush = defenceCrush;
        this.defenceMagic = defenceMagic;
        this.defenceRanged = defenceRanged;
        this.drops = drops;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getLastUpdated() { return lastUpdated; }
    public boolean isIncomplete() { return incomplete; }
    public boolean isMembers() { return members; }
    public String getReleaseDate() { return releaseDate; }
    public int getCombatLevel() { return combatLevel; }
    public int getSize() { return size; }
    public int getHitpoints() { return hitpoints; }
    public int getMaxHit() { return maxHit; }
    public String[] getAttackType() { return attackType; }
    public int getAttackSpeed() { return attackSpeed; }
    public boolean isAggressive() { return aggressive; }
    public boolean isPoisonous() { return poisonous; }
    public boolean isVenomous() { return venomous; }
    public boolean isImmunePoison() { return immunePoison; }
    public boolean isImmuneVenom() { return immuneVenom; }
    public String[] getAttributes() { return attributes; }
    public String[] getCategory() { return category; }
    public boolean isSlayerMonster() { return slayerMonster; }
    public int getSlayerLevel() { return slayerLevel; }
    public double getSlayerXp() { return slayerXp; }
    public String[] getSlayerMasters() { return slayerMasters; }
    public boolean isDuplicate() { return duplicate; }
    public String getExamine() { return examine; }
    public String getWikiName() { return wikiName; }
    public String getWikiURL() { return wikiURL; }
    public int getAttackLevel() { return attackLevel; }
    public int getStrengthLevel() { return strengthLevel; }
    public int getDefenceLevel() { return defenceLevel; }
    public int getMagicLevel() { return magicLevel; }
    public int getRangedLevel() { return rangedLevel; }
    public int getAttackBonus() { return attackBonus; }
    public int getStrengthBonus() { return strengthBonus; }
    public int getAttackMagic() { return attackMagic; }
    public int getMagicBonus() { return magicBonus; }
    public int getAttackRanged() { return attackRanged; }
    public int getRangedBonus() { return rangedBonus; }
    public int getDefenceStab() { return defenceStab; }
    public int getDefenceSlash() { return defenceSlash; }
    public int getDefenceCrush() { return defenceCrush; }
    public int getDefenceMagic() { return defenceMagic; }
    public int getDefenceRanged() { return defenceRanged; }
    public DropDef[] getDrops() { return drops; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MonsterDef that = (MonsterDef) o;
        return id == that.id && incomplete == that.incomplete && members == that.members &&
                combatLevel == that.combatLevel && size == that.size && hitpoints == that.hitpoints &&
                maxHit == that.maxHit && attackSpeed == that.attackSpeed && aggressive == that.aggressive &&
                poisonous == that.poisonous && venomous == that.venomous && immunePoison == that.immunePoison &&
                immuneVenom == that.immuneVenom && slayerMonster == that.slayerMonster &&
                slayerLevel == that.slayerLevel && Double.compare(that.slayerXp, slayerXp) == 0 &&
                duplicate == that.duplicate && attackLevel == that.attackLevel &&
                strengthLevel == that.strengthLevel && defenceLevel == that.defenceLevel &&
                magicLevel == that.magicLevel && rangedLevel == that.rangedLevel &&
                attackBonus == that.attackBonus && strengthBonus == that.strengthBonus &&
                attackMagic == that.attackMagic && magicBonus == that.magicBonus &&
                attackRanged == that.attackRanged && rangedBonus == that.rangedBonus &&
                defenceStab == that.defenceStab && defenceSlash == that.defenceSlash &&
                defenceCrush == that.defenceCrush && defenceMagic == that.defenceMagic &&
                defenceRanged == that.defenceRanged && Objects.equals(name, that.name) &&
                Objects.equals(lastUpdated, that.lastUpdated) && Objects.equals(releaseDate, that.releaseDate) &&
                Arrays.equals(attackType, that.attackType) && Arrays.equals(attributes, that.attributes) &&
                Arrays.equals(category, that.category) && Arrays.equals(slayerMasters, that.slayerMasters) &&
                Objects.equals(examine, that.examine) && Objects.equals(wikiName, that.wikiName) &&
                Objects.equals(wikiURL, that.wikiURL) && Arrays.equals(drops, that.drops);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, name, lastUpdated, incomplete, members, releaseDate, combatLevel,
                size, hitpoints, maxHit, attackSpeed, aggressive, poisonous, venomous, immunePoison,
                immuneVenom, slayerMonster, slayerLevel, slayerXp, duplicate, examine, wikiName,
                wikiURL, attackLevel, strengthLevel, defenceLevel, magicLevel, rangedLevel,
                attackBonus, strengthBonus, attackMagic, magicBonus, attackRanged, rangedBonus,
                defenceStab, defenceSlash, defenceCrush, defenceMagic, defenceRanged);
        result = 31 * result + Arrays.hashCode(attackType);
        result = 31 * result + Arrays.hashCode(attributes);
        result = 31 * result + Arrays.hashCode(category);
        result = 31 * result + Arrays.hashCode(slayerMasters);
        result = 31 * result + Arrays.hashCode(drops);
        return result;
    }
}