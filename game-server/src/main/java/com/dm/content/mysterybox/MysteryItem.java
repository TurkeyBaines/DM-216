package com.dm.content.mysterybox;

import com.dm.game.world.items.Item;

import static com.dm.content.mysterybox.MysteryRarity.COMMON;

public class MysteryItem extends Item {

    final MysteryRarity rarity;

    public MysteryItem(int id, int amount, MysteryRarity rarity) {
        super(id, amount);
        this.rarity = rarity;
    }

    public MysteryItem(int id, int amount) {
        this(id, amount, COMMON);
    }
}
