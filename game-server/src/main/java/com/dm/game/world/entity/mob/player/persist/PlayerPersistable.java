package com.dm.game.world.entity.mob.player.persist;

import com.dm.game.world.entity.mob.player.Player;
import com.dm.net.codec.login.LoginResponse;

public interface PlayerPersistable {

    void save(Player player);

    LoginResponse load(Player player, String expectedPassword);

}
