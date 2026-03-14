package com.dm.content.skill.impl.hunter.birdhouse.action;

import com.dm.content.skill.impl.hunter.birdhouse.BirdhouseData;
import com.dm.content.skill.impl.hunter.birdhouse.PlayerBirdHouseData;
import com.dm.game.action.Action;
import com.dm.game.action.policy.WalkablePolicy;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.entity.mob.player.persist.PlayerSerializer;
import com.dm.game.world.items.Item;
import com.dm.game.world.object.CustomGameObject;
import com.dm.game.world.object.GameObject;
import com.dm.net.packet.out.SendAddObject;

public class PlaceBirdhouse extends Action<Player> {

    private BirdhouseData birdhouseData;
    private GameObject gameObject;
    public PlaceBirdhouse(Player player, BirdhouseData birdhouseData, GameObject gameObject) {
        super(player, 1);
        this.birdhouseData = birdhouseData;
        this.gameObject = gameObject;
    }

    @Override
    public WalkablePolicy getWalkablePolicy() {
        return WalkablePolicy.WALKABLE;
    }

    @Override
    public String getName() {
        return "Place birdhouse";
    }

    @Override
    public void execute() {
        getMob().inventory.remove(new Item(birdhouseData.birdHouseId));
        getMob().inventory.refresh();
        getMob().birdHouseData.add(new PlayerBirdHouseData(birdhouseData, gameObject.getId(), gameObject.getPosition(), gameObject.getDirection().getId(), gameObject.getObjectType().getId()));
        getMob().send(new SendAddObject(new CustomGameObject(birdhouseData.objectData[0], gameObject.getPosition(), gameObject.getDirection(), gameObject.getObjectType())));
        PlayerSerializer.save(getMob());
        getMob().action.getCurrentAction().cancel();
    }

}
