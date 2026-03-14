package com.dm.content.preloads;


import com.dm.content.skill.impl.magic.Spellbook;
import com.dm.game.world.items.Item;

public interface Preload {

    String title();

    Spellbook spellbook();

    Item[] equipment();

    Item[] inventory();

    int[] skills();

}
