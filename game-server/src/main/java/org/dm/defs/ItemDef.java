package org.dm.defs;

import com.google.gson.annotations.SerializedName;

/**
 * @author Jire
 */
public final class ItemDef {

    private final int id;
    private final String name;
    @SerializedName("last_updated")
    private final String lastUpdated;
    private final boolean incomplete;
    private final boolean members;
    private final boolean tradeable;
    @SerializedName("tradeable_on_ge")
    private final boolean tradeableOnGe;
    private final boolean stackable;
    private final int stacked;
    private final boolean noted;
    private final boolean noteable;
    @SerializedName("linked_id_item")
    private final int linkedIdItem;
    @SerializedName("linked_id_noted")
    private final int linkedIdNoted;
    @SerializedName("linked_id_placeholder")
    private final int linkedIdPlaceholder;
    private final boolean placeholder;
    private final boolean equipable;
    @SerializedName("equipable_by_player")
    private final boolean equipableByPlayer;
    @SerializedName("equipable_weapon")
    private final boolean equipableWeapon;
    private final int cost;
    @SerializedName("lowalch")
    private final int lowAlch;
    @SerializedName("highalch")
    private final int highAlch;
    private final double weight;
    @SerializedName("buy_limit")
    private final int buyLimit;
    @SerializedName("quest_item")
    private final boolean questItem;
    @SerializedName("release_date")
    private final String releaseDate;
    private final boolean duplicate;
    private final String examine;
    private final String icon;
    @SerializedName("wiki_name")
    private final String wikiName;
    @SerializedName("wiki_url")
    private final String wikiURL;
    private final ItemEquipDef equipment;
    private final ItemEquipWeaponDef weapon;

    public ItemDef(int id, String name, String lastUpdated, boolean incomplete, boolean members,
                   boolean tradeable, boolean tradeableOnGe, boolean stackable, int stacked,
                   boolean noted, boolean noteable, int linkedIdItem, int linkedIdNoted,
                   int linkedIdPlaceholder, boolean placeholder, boolean equipable,
                   boolean equipableByPlayer, boolean equipableWeapon, int cost, int lowAlch,
                   int highAlch, double weight, int buyLimit, boolean questItem,
                   String releaseDate, boolean duplicate, String examine, String icon,
                   String wikiName, String wikiURL, ItemEquipDef equipment,
                   ItemEquipWeaponDef weapon) {
        this.id = id;
        this.name = name;
        this.lastUpdated = lastUpdated;
        this.incomplete = incomplete;
        this.members = members;
        this.tradeable = tradeable;
        this.tradeableOnGe = tradeableOnGe;
        this.stackable = stackable;
        this.stacked = stacked;
        this.noted = noted;
        this.noteable = noteable;
        this.linkedIdItem = linkedIdItem;
        this.linkedIdNoted = linkedIdNoted;
        this.linkedIdPlaceholder = linkedIdPlaceholder;
        this.placeholder = placeholder;
        this.equipable = equipable;
        this.equipableByPlayer = equipableByPlayer;
        this.equipableWeapon = equipableWeapon;
        this.cost = cost;
        this.lowAlch = lowAlch;
        this.highAlch = highAlch;
        this.weight = weight;
        this.buyLimit = buyLimit;
        this.questItem = questItem;
        this.releaseDate = releaseDate;
        this.duplicate = duplicate;
        this.examine = examine;
        this.icon = icon;
        this.wikiName = wikiName;
        this.wikiURL = wikiURL;
        this.equipment = equipment;
        this.weapon = weapon;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getLastUpdated() { return lastUpdated; }
    public boolean isIncomplete() { return incomplete; }
    public boolean isMembers() { return members; }
    public boolean isTradeable() { return tradeable; }
    public boolean isTradeableOnGe() { return tradeableOnGe; }
    public boolean isStackable() { return stackable; }
    public int getStacked() { return stacked; }
    public boolean isNoted() { return noted; }
    public boolean isNoteable() { return noteable; }
    public int getLinkedIdItem() { return linkedIdItem; }
    public int getLinkedIdNoted() { return linkedIdNoted; }
    public int getLinkedIdPlaceholder() { return linkedIdPlaceholder; }
    public boolean isPlaceholder() { return placeholder; }
    public boolean isEquipable() { return equipable; }
    public boolean isEquipableByPlayer() { return equipableByPlayer; }
    public boolean isEquipableWeapon() { return equipableWeapon; }
    public int getCost() { return cost; }
    public int getLowAlch() { return lowAlch; }
    public int getHighAlch() { return highAlch; }
    public double getWeight() { return weight; }
    public int getBuyLimit() { return buyLimit; }
    public boolean isQuestItem() { return questItem; }
    public String getReleaseDate() { return releaseDate; }
    public boolean isDuplicate() { return duplicate; }
    public String getExamine() { return examine; }
    public String getIcon() { return icon; }
    public String getWikiName() { return wikiName; }
    public String getWikiURL() { return wikiURL; }
    public ItemEquipDef getEquipment() { return equipment; }
    public ItemEquipWeaponDef getWeapon() { return weapon; }

    @Override
    public String toString() {
        return "ItemDef{id=" + id + ", name='" + name + "'}";
    }
}