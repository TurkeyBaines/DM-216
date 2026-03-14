package com.dm.game.task.impl;

import com.dm.game.task.Task;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.net.packet.out.SendMessage;
import com.dm.net.packet.out.SendWidget;

/**
 * This randomevent handles the teleblock counter.
 *
 * @author Daniel | Obey
 */
public class TeleblockTask extends Task {

    private final Player player;

    public TeleblockTask(Player player) {
        super(false, 0);
        this.player = player;
    }

    @Override
    public void execute() {
        if (player == null || !player.isValid()) {
            cancel();
            return;
        }

        if (player.teleblockTimer.decrementAndGet() <= 0) {
            cancel();
        }
    }

    @Override
    public void onCancel(boolean logout) {
        player.send(new SendWidget(SendWidget.WidgetType.TELEBLOCK, 0));
        player.send(new SendMessage("You feel the effects of the tele-block spell go away."));
    }

}