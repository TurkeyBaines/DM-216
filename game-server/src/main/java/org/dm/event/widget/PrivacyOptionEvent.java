package org.dm.event.widget;

import com.dm.game.world.entity.mob.player.Player;

/**
 * @author Jire
 */
public final class PrivacyOptionEvent implements WidgetEvent {

    private final int publicMode;
    private final int privateMode;
    private final int tradeMode;
    private final int clanMode;

    public PrivacyOptionEvent(int publicMode, int privateMode, int tradeMode, int clanMode) {
        this.publicMode = publicMode;
        this.privateMode = privateMode;
        this.tradeMode = tradeMode;
        this.clanMode = clanMode;
    }

    @Override
    public void handle(Player player) {
        player.relations.setPrivacyChatModes(publicMode, privateMode, clanMode, tradeMode);
    }

    public int getPublicMode() {
        return publicMode;
    }

    public int getPrivateMode() {
        return privateMode;
    }

    public int getTradeMode() {
        return tradeMode;
    }

    public int getClanMode() {
        return clanMode;
    }

}