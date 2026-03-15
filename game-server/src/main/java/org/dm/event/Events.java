package org.dm.event;

import com.dm.game.world.entity.mob.player.Player;

/**
 * @author Jire
 */
public final class Events {

    private boolean logOut = false;
    private Event widget = null;
    private Event interact = null;
    private boolean loadRegion = false;

    public void widget(Player player, Event event) {
        if (event.canHandle(player)) {
            this.widget = event;
        }
    }

    public void interact(Player player, Event event) {
        if (event.canHandle(player)) {
            this.interact = event;
        }
    }

    public void process(Player player) {
        if (logOut) {
            player.logout();
            return;
        }

        if (loadRegion) {
            player.loadRegion();
        }

        if (widget != null) {
            widget.handle(player);
        }

        if (interact != null) {
            interact.handle(player);
        }
    }

    public void reset() {
        logOut = false;
        widget = null;
        interact = null;
        loadRegion = false;
    }

    // Getters and Setters

    public boolean isLogOut() {
        return logOut;
    }

    public void setLogOut(boolean logOut) {
        this.logOut = logOut;
    }

    public boolean isLoadRegion() {
        return loadRegion;
    }

    public void setLoadRegion(boolean loadRegion) {
        this.loadRegion = loadRegion;
    }

    public Event getWidget() {
        return widget;
    }

    public Event getInteract() {
        return interact;
    }

}