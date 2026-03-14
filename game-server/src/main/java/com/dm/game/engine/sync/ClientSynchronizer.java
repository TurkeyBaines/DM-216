package com.dm.game.engine.sync;

import com.dm.game.world.entity.MobList;
import com.dm.game.world.entity.mob.npc.Npc;
import com.dm.game.world.entity.mob.player.Player;

public interface ClientSynchronizer {

    void synchronize(MobList<Player> players, MobList<Npc> npcs);

}
