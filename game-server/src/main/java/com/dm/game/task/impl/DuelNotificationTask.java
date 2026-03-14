package com.dm.game.task.impl;

import com.dm.game.task.Task;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.entity.mob.player.exchange.ExchangeSession;
import com.dm.game.world.entity.mob.player.exchange.ExchangeSessionType;
import com.dm.net.packet.out.SendConfig;

public class DuelNotificationTask extends Task {

    private final Player player;
    private int time = 20;

    public DuelNotificationTask(Player player) {
        super(true, 0);
        this.player = player;
    }

    @Override
    public void execute() {
        if (!ExchangeSession.inSession(player, ExchangeSessionType.DUEL)) {
            cancel();
            return;
        }

        if (time <= 0) {
            cancel();
            return;
        }

        time--;

        if (time % 2 == 0) {
            player.send(new SendConfig(655, 1));
        } else {
            player.send(new SendConfig(655, 0));
        }

    }

    @Override
    public void onCancel(boolean logout) {
        player.send(new SendConfig(655, 0));
    }

}
