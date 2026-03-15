package org.dm.event.widget;

import com.dm.game.event.impl.log.ChatLogEvent;
import com.dm.game.world.World;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.entity.mob.player.relations.ChatColor;
import com.dm.game.world.entity.mob.player.relations.ChatEffect;
import com.dm.game.world.entity.mob.player.relations.ChatMessage;
import com.dm.util.ChatCodec;

import java.util.Locale;

/**
 * @author Jire
 */
public final class ChatMessageEvent implements WidgetEvent {

    private final int effect;
    private final int color;
    private final int size;
    private final byte[] bytes;

    public ChatMessageEvent(int effect, int color, int size, byte[] bytes) {
        this.effect = effect;
        this.color = color;
        this.size = size;
        this.bytes = bytes;
    }

    @Override
    public void handle(Player player) {
        String decoded = ChatCodec.decode(bytes);

        player.chat(ChatMessage.create(
                decoded,
                ChatColor.values[color],
                ChatEffect.values[effect]
        ));

        World.getDataBus().publish(new ChatLogEvent(player, decoded));

        System.out.println("User: " + player.getUsername().toUpperCase(Locale.getDefault()) + " said: " + decoded);
    }

    public int getEffect() { return effect; }
    public int getColor() { return color; }
    public int getSize() { return size; }
    public byte[] getBytes() { return bytes; }

}