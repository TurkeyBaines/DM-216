package com.dm.content.mysterybox;

import com.dm.content.mysterybox.impl.MagicMysteryBox;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The mystery box class.
 *
 * @author Daniel
 */
public abstract class MysteryBox {

    /** The map containing all the mystery boxes. */
    private static Map<Integer, MysteryBox> MYSTERY_BOXES = new HashMap<>();

    /** Handles loading the mystery boxes. */
    public static void load() {
        MysteryBox MAGIC_MYSTERY_BOX = new MagicMysteryBox();

        MYSTERY_BOXES.put(MAGIC_MYSTERY_BOX.item(), MAGIC_MYSTERY_BOX);
    }

    /** Handles getting the mystery box. */
    static Optional<MysteryBox> getMysteryBox(int item) {
        return MYSTERY_BOXES.containsKey(item) ? Optional.of(MYSTERY_BOXES.get(item)) : Optional.empty();
    }

    /** The name of the mystery box. */
    protected abstract String name();

    /** The item identification of the mystery box. */
    protected abstract int item();

    /** The rewards for the mystery box. */
    protected abstract MysteryItem[] rewards();
}
