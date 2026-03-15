package com.dm.net.packet.in;

import com.dm.game.world.entity.mob.data.PacketType;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.net.codec.ByteModification;
import com.dm.net.codec.ByteOrder;
import com.dm.net.packet.ClientPackets;
import com.dm.net.packet.GamePacket;
import com.dm.net.packet.PacketListener;
import com.dm.net.packet.PacketListenerMeta;
import org.dm.event.npc.*;

/**
 * The {@link GamePacket} responsible for the different options while clicking
 * an npc.
 *
 * @author Daniel | Obey
 * @author Jire
 */
@PacketListenerMeta({
        ClientPackets.ATTACK_NPC,
        ClientPackets.MAGIC_ON_NPC,
        ClientPackets.NPC_ACTION_1,
        ClientPackets.NPC_ACTION_2,
        ClientPackets.NPC_ACTION_3,
        ClientPackets.NPC_ACTION_4
})
public final class NpcInteractionPacketListener implements PacketListener {

    @Override
    public void handlePacket(Player player, GamePacket packet) {
        if (player.locking.locked(PacketType.CLICK_NPC)) {
            return;
        }

        NpcClickEvent event = switch (packet.getOpcode()) {
            case ClientPackets.ATTACK_NPC -> new AttackNpcEvent(
                    packet.readShort(false, ByteModification.ADD)
            );

            case ClientPackets.MAGIC_ON_NPC -> new MagicOnNpcEvent(
                    packet.readShort(ByteOrder.LE, ByteModification.ADD),
                    packet.readShort(ByteModification.ADD)
            );

            case ClientPackets.NPC_ACTION_1 -> new FirstNpcOptionEvent(
                    packet.readShort(ByteOrder.LE)
            );

            case ClientPackets.NPC_ACTION_2 -> new SecondNpcOptionEvent(
                    packet.readShort(ByteOrder.LE, ByteModification.ADD)
            );

            case ClientPackets.NPC_ACTION_3 -> new NpcOptionEvent(
                    packet.readShort(), 3
            );

            case ClientPackets.NPC_ACTION_4 -> new NpcOptionEvent(
                    packet.readShort(ByteOrder.LE), 4
            );

            default -> null;
        };

        if (event != null) {
            player.getEvents().interact(player, event);
        }
    }

}