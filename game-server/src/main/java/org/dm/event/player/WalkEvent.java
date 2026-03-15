package org.dm.event.player;

import com.dm.content.activity.Activity;
import com.dm.content.activity.impl.duelarena.DuelArenaActivity;
import com.dm.content.activity.impl.duelarena.DuelRule;
import com.dm.game.Animation;
import com.dm.game.world.entity.mob.data.LockType;
import com.dm.game.world.entity.mob.data.PacketType;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.entity.mob.player.PlayerRight;
import com.dm.game.world.position.Position;
import com.dm.net.packet.out.SendMessage;
import org.dm.event.Event;

import java.util.Optional;

/**
 * @author Jire
 */
public final class WalkEvent implements Event {

    private final int targetX;
    private final int targetY;
    private final boolean runQueue;

    public WalkEvent(int targetX, int targetY, boolean runQueue) {
        this.targetX = targetX;
        this.targetY = targetY;
        this.runQueue = runQueue;
    }

    @Override
    public void handle(Player player) {
        if (player.isGambleLocked()) return;

        if (player.locking.locked(PacketType.WALKING)) {
            if (player.locking.locked(LockType.STUN)) {
                player.send(new SendMessage("You are currently stunned."));
                player.getCombat().reset();
            }
            if (player.locking.locked(LockType.FREEZE)) {
                player.send(new SendMessage("A magical force stops you from moving!", true));
                player.getCombat().reset();
            }
            return;
        }

        Optional<DuelArenaActivity> duelActivity = Activity.search(player, DuelArenaActivity.class);
        if (duelActivity.isPresent()) {
            DuelArenaActivity activity = duelActivity.get();
            if (activity.rules.contains(DuelRule.NO_MOVEMENT)) {
                player.send(new SendMessage("You cannot move in the duel arena."));
                player.getCombat().reset();
                return;
            }
        }

        player.skills.resetSkilling();

        if (player.resting) {
            player.animate(Animation.RESET, true);
            player.resting = false;
        }

        /* Dialogues */
        if (player.dialogue.isPresent()) {
            player.dialogue = Optional.empty();
        }

        /* Idle */
        if (player.idle) {
            player.idle = false;
        }

        /* Dialogue factory */
        if (!player.dialogueFactory.getChain().isEmpty()) {
            player.dialogueFactory.clear();
        }

        /* Dialogue options */
        if (player.optionDialogue.isPresent()) {
            player.optionDialogue = Optional.empty();
        }

        if (!player.interfaceManager.isMainClear()) {
            player.interfaceManager.close();
        }

        if (!player.interfaceManager.isDialogueClear()) {
            player.dialogueFactory.clear();
        }

        /* Reset the face. */
        player.resetFace();

        /* Clear non walkable actions */
        player.action.clearNonWalkableActions();
        player.resetWaypoint();
        player.getCombat().reset();

        // the tile the player is trying to get to
        Position destination = Position.create(targetX, targetY, player.getHeight());

        // prevents the player from hacking the client to make the player walk really far distances.
        if (player.getPosition().getDistance(destination) > 32) {
            return;
        }

        if (runQueue && PlayerRight.isDeveloper(player)) {
            player.move(destination);
            return;
        }

        player.movement.runQueue = runQueue;
        player.movement.dijkstraPath(destination);
    }

}