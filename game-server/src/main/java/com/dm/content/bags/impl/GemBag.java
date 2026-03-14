package com.dm.content.bags.impl;

import com.dm.content.bags.ItemBag;
import com.dm.game.world.items.Item;
import com.dm.game.world.items.ItemDefinition;
import com.dm.game.world.items.containers.ItemContainer;

import java.util.Objects;
import java.util.function.Predicate;

public class GemBag extends ItemBag {

    public static final int SIZE = 9;

    public GemBag() {
        super(new ItemContainer(SIZE, ItemContainer.StackPolicy.ALWAYS));
    }

    @Override
    public String getItem() {
        return "uncut(s)";
    }

    @Override
    public String getName() {
        return "gem bag";
    }

    @Override
    public Predicate<Item> isAllowed() {
        return uncut -> Objects.nonNull(uncut) && ItemDefinition.get(uncut.getId()).getName().startsWith("Uncut") && !ItemDefinition.get(uncut.getId()).isStackable();
    }

    @Override
    public String getIndication() {
        return "are";
    }

}