package com.dm.game.action.impl;

import com.dm.content.skill.impl.magic.teleport.Teleportation;
import com.dm.content.skill.impl.magic.teleport.TeleportationData;
import com.dm.game.Animation;
import com.dm.game.UpdatePriority;
import com.dm.game.action.Action;
import com.dm.game.action.policy.WalkablePolicy;
import com.dm.game.world.entity.mob.Direction;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.object.GameObject;
import com.dm.game.world.position.Position;
import com.dm.net.packet.out.SendMessage;
import com.dm.util.MessageColor;

import java.util.function.Predicate;

public class LeverAction extends Action<Player> {
    private int count;
    private final GameObject lever;
    private final Position position;
    private final Direction face;
    private final Predicate<Player> condition;
    private final String message;

    public LeverAction(Player mob, GameObject lever, Position position, Direction face) {
        this(mob, lever, position, face, null, null);
    }

    private LeverAction(Player mob, GameObject lever, Position position, Direction face, Predicate<Player> condition, String message) {
        super(mob, 1, false);
        this.lever = lever;
        this.position = position;
        this.face = face;
        this.condition = condition;
        this.message = message;
    }

    @Override
    public boolean canSchedule() {
        if (condition != null && !condition.test(getMob())) {
            getMob().send(new SendMessage(message, MessageColor.RED));
            return false;
        }
        return true;
    }

    @Override
    public void onSchedule() {
        getMob().locking.lock();
        getMob().face(position);
        getMob().getCombat().reset();
        getMob().damageImmunity.reset(3_000);
    }

    @Override
    public void execute() {
        if (count == 0) {
            getMob().send(new SendMessage("You pull the lever..."));
            getMob().animate(new Animation(2140, UpdatePriority.VERY_HIGH));
        } else if (count == 1) {
            Teleportation.activateOverride(getMob(), position, TeleportationData.MODERN);
            cancel();
        }
        count++;
    }

    @Override
    public void onCancel(boolean logout) {
        getMob().locking.unlock();
    }

    @Override
    public WalkablePolicy getWalkablePolicy() {
        return WalkablePolicy.NON_WALKABLE;
    }

    @Override
    public String getName() {
        return "Lever action";
    }

    @Override
    public boolean prioritized() {
        return false;
    }
}
