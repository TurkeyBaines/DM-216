package org.dm.event.widget;

import com.dm.game.world.World;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.entity.mob.player.relations.PrivateChatMessage;
import com.dm.net.packet.ClientPackets;
import com.dm.util.ChatCodec;
import com.dm.util.Utility;

/**
 * @author Jire
 */
public final class PlayerRelationEvent implements WidgetEvent {

    private final int opcode;
    private final long username;
    private final byte[] input;

    public PlayerRelationEvent(int opcode, long username, byte[] input) {
        this.opcode = opcode;
        this.username = username;
        this.input = input;
    }

    public PlayerRelationEvent(int opcode, long username) {
        this(opcode, username, null);
    }

    @Override
    public void handle(Player player) {
        switch (opcode) {
            case ClientPackets.ADD_FRIEND -> player.relations.addFriend(username);
            case ClientPackets.REMOVE_FRIEND -> player.relations.deleteFriend(username);
            case ClientPackets.ADD_IGNORE -> player.relations.addIgnore(username);
            case ClientPackets.REMOVE_IGNORE -> player.relations.deleteIgnore(username);
            case ClientPackets.PRIVATE_MESSAGE -> {
                Player other = World.search(
                        Utility.formatText(
                                Utility.longToString(username)
                        ).replace('_', ' ')
                ).orElse(null);

                if (other == null) {
                    return;
                }

                String decoded = ChatCodec.decode(input);
                byte[] compressed = ChatCodec.encode(decoded);

                player.relations.message(other, new PrivateChatMessage(decoded, compressed));
            }
        }
    }

    public int getOpcode() { return opcode; }
    public long getUsername() { return username; }
    public byte[] getInput() { return input; }

}