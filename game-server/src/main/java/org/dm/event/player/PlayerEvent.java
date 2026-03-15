package org.dm.event.player;

import com.dm.game.world.World;
import com.dm.game.world.entity.mob.data.PacketType;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.position.Position;
import org.dm.event.Event;

import java.util.function.Consumer;

/**
 * @author Jire
 */
public interface PlayerEvent extends Event {

    int index();

    @Override
    default boolean canHandle(Player player) {
        return Event.super.canHandle(player)
                && !player.locking.locked(PacketType.INTERACT);
    }

    @Override
    default void handle(Player player) {
        Player other = World.getPlayerBySlot(index()).orElse(null);
        if (other == null || !other.isValid()) {
            return;
        }

        Position position = other.getPosition();
        var region = World.getRegions().getRegion(position);
        if (!region.containsPlayer(other.getHeight(), other)) {
            return;
        }

        handlePlayer(player, other);
    }

    void handlePlayer(Player player, Player other);

    static PlayerEvent walkTo(int index, Consumer<Player> handle) {
        return new PlayerEvent() {
            @Override
            public int index() {
                return index;
            }

            @Override
            public void handlePlayer(Player player, Player other) {
                player.walkTo(other, () -> handle.accept(other));
            }
        };
    }

}