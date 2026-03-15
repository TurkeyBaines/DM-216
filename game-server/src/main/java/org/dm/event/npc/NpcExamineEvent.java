package org.dm.event.npc;

import com.dm.game.world.entity.mob.player.Player;
import com.dm.net.packet.out.SendMessage;
import org.dm.defs.MonsterDef;
import org.dm.defs.MonsterDefLoader;

/**
 * @author Jire
 */
public final class NpcExamineEvent implements NpcEvent {

    private final int npcId;

    public NpcExamineEvent(int npcId) {
        this.npcId = npcId;
    }

    @Override
    public void handle(Player player) {
        MonsterDef monsterDef = MonsterDefLoader.map.get(npcId);
        if (monsterDef == null) {
            return;
        }

        String examine = monsterDef.getExamine();
        if (!examine.equals("null")) {
            player.send(new SendMessage(examine));
        }
    }

    public int getNpcId() {
        return npcId;
    }

}