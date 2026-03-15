package org.dm.event.object;

import com.dm.game.world.entity.mob.player.Player;
import com.dm.net.packet.out.SendMessage;
import org.dm.objectexamines.ObjectExamines;

/**
 * @author Jire
 */
public final class ObjectExamineEvent implements ObjectEvent {

    private final int objectId;

    public ObjectExamineEvent(int objectId) {
        this.objectId = objectId;
    }

    @Override
    public void handle(Player player) {
        String examine = ObjectExamines.map.get(objectId);
        if (examine == null) {
            return;
        }

        if (!"null".equals(examine)) {
            player.send(new SendMessage(examine));
        }
    }

    public int getObjectId() {
        return objectId;
    }

}