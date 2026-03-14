package com.dm.game.action.impl;

import com.dm.game.Animation;
import com.dm.game.action.Action;
import com.dm.game.action.policy.WalkablePolicy;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.items.Item;
import com.dm.game.world.object.GameObject;

/**
 * Handles picking up a flax.
 * @author Daniel
 */
public final class FlaxPickingAction extends Action<Player> {

    /** The flax game object. */
    private final GameObject object;

    /** The ticks. */
    private boolean pickup;

    /**
     * Constructs a new <code>FlaxPickingAction</code>.
     *
     * @param player The player instance.
     * @param object The flax game object.
     */
    public FlaxPickingAction(Player player, GameObject object) {
        super(player, 2, true);
        this.object = object;
    }

    @Override
    public void execute() {
        Player player = getMob().getPlayer();

        if (pickup) {
            player.inventory.add(new Item(1779, 1));
//            if (Utility.random(6) == 1) {
//                World.submit(new ObjectReplacementEvent(object, 20));
//            }
            cancel();
        } else {
            player.animate(new Animation(827));
            pickup = true;
            setDelay(1);
        }
    }

    @Override
    public String getName() {
        return "Flax picking";
    }

    @Override
    public boolean prioritized() {
        return false;
    }

    @Override
    public WalkablePolicy getWalkablePolicy() {
        return WalkablePolicy.NON_WALKABLE;
    }
}