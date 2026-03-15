package org.dm.event;

import com.dm.game.world.entity.mob.player.Player;

/**
 * @author Jire
 */
public interface Event {

    /**
     * Determines if the player is in a state where they can handle this event.
     * * @param player The player to check.
     * @return true if the event can be handled, false otherwise.
     */
    default boolean canHandle(Player player) {
        return !player.isDead();
    }

    /**
     * Handles the logic for this event.
     * * @param player The player involved in the event.
     */
    default void handle(Player player) {
        // Default implementation does nothing
    }

}