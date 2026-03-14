package com.dm.content.skill.impl.hunter.birdhouse.action;

import com.dm.content.skill.impl.hunter.birdhouse.PlayerBirdHouseData;
import com.dm.game.action.Action;
import com.dm.game.action.policy.WalkablePolicy;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.entity.mob.player.persist.PlayerSerializer;
import com.dm.game.world.items.Item;
import com.dm.game.world.object.CustomGameObject;
import com.dm.game.world.object.ObjectDirection;
import com.dm.game.world.object.ObjectType;
import com.dm.net.packet.out.SendAddObject;

public class FillBirdhouse extends Action<Player> {

    private PlayerBirdHouseData playerBirdHouseData;
    private Item itemUsed;
    private int itemAmount;
    public FillBirdhouse(Player player, PlayerBirdHouseData playerBirdHouseData, Item itemUsed, int itemAmount) {
        super(player, 1);
        this.playerBirdHouseData = playerBirdHouseData;
        this.itemUsed = itemUsed;
        this.itemAmount = itemAmount;
    }

    @Override
    public WalkablePolicy getWalkablePolicy() {
        return WalkablePolicy.WALKABLE;
    }

    @Override
    public String getName() {
        return "Fill birdhouse";
    }

    @Override
    public void execute() {
        getMob().inventory.remove(new Item(itemUsed.getId(), itemAmount));
        getMob().inventory.refresh();

        if(itemAmount >= 10 || playerBirdHouseData.seedAmount + itemAmount >= 10) {
            getMob().dialogueFactory.sendStatement("Your birdhouse trap is now full of seed and will start to catch birds").execute();
            getMob().message("Your birdhouse trap is now full of seed and will start to catch birds");

            playerBirdHouseData.seedAmount += itemAmount;
            playerBirdHouseData.birdhouseTimer = System.currentTimeMillis() + 3_300_000;
            getMob().send(new SendAddObject(new CustomGameObject(playerBirdHouseData.birdhouseData.objectData[playerBirdHouseData.seedAmount >= 10 ? 1 : 0], playerBirdHouseData.birdhousePosition, ObjectDirection.valueOf(playerBirdHouseData.rotation).get(), ObjectType.valueOf(playerBirdHouseData.type).get())));
        } else {
            getMob().dialogueFactory.sendStatement("You add " + itemAmount + " x " + itemUsed.getDefinition().getName().toLowerCase() + " to the birdhouse.").execute();
            getMob().message("You add " + itemAmount + " x " + itemUsed.getDefinition().getName().toLowerCase() + " to the birdhouse.");

            playerBirdHouseData.seedAmount += itemAmount;
        }

        PlayerSerializer.save(getMob());
        getMob().action.getCurrentAction().cancel();
    }

}
