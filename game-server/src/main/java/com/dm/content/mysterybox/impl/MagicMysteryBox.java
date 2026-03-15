package com.dm.content.mysterybox.impl;

import com.dm.content.mysterybox.MysteryBox;
import com.dm.content.mysterybox.MysteryItem;
import com.dm.util.Items;

import static com.dm.content.mysterybox.MysteryRarity.*;

public class MagicMysteryBox extends MysteryBox {

    @Override
    protected String name() { return "Magic mystery box"; }

    @Override
    protected int item() { return 12955; }

    @Override
    protected MysteryItem[] rewards() {
        return new MysteryItem[] {
                new MysteryItem(Items.COINS, 500000, COMMON)
        };
    }

}
