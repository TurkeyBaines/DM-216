package org.dm.event.object;

import com.dm.content.combat.cannon.CannonManager;
import com.dm.content.event.EventDispatcher;
import com.dm.content.event.impl.ObjectInteractionEvent;
import com.dm.game.event.impl.ObjectClickEvent;
import com.dm.game.plugin.PluginManager;
import com.dm.game.world.World;
import com.dm.game.world.entity.mob.data.PacketType;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.object.GameObject;
import com.dm.game.world.object.GameObjectDefinition;
import com.dm.game.world.position.Position;
import org.dm.WorldTask;

import java.util.function.Function;

/**
 * @author Jire
 */
public final class ObjectOptionEvent implements ObjectEvent {

    private final int option;
    private final int id;
    private final int x;
    private final int y;
    private final Function<GameObject, ObjectInteractionEvent> createInteractionEvent;

    public ObjectOptionEvent(int option, int id, int x, int y,
                             Function<GameObject, ObjectInteractionEvent> createInteractionEvent) {
        this.option = option;
        this.id = id;
        this.x = x;
        this.y = y;
        this.createInteractionEvent = createInteractionEvent;
    }

    @Override
    public boolean canHandle(Player player) {
        return ObjectEvent.super.canHandle(player)
                && !player.locking.locked(PacketType.CLICK_OBJECT);
    }

    @Override
    public void handle(Player player) {
        if (GameObjectDefinition.forId(id) == null) {
            return;
        }

        Position position = new Position(x, y, player.getHeight());

        int objectId = id;
        for (var birdHouseData : player.birdHouseData) {
            if (birdHouseData.birdhousePosition.equals(position)) {
                objectId = birdHouseData.oldObjectId;
                break;
            }
        }

        // Cleanup dialogues and interfaces
        if (player.dialogue.isPresent()) {
            //player.dialogueFactory.sendDialogue(Optional.empty());
        }

        if (!player.dialogueFactory.getChain().isEmpty()) {
            player.dialogueFactory.clear();
        }

        if (player.optionDialogue.isPresent()) {
            //player.setOptionDialogue(Optional.empty());
        }

        if (!player.interfaceManager.isMainClear()) {
            player.interfaceManager.close();
        }

        if (!player.interfaceManager.isDialogueClear()) {
            player.dialogueFactory.clear();
        }

        // Special handling (e.g., Cannon)
        if (objectId == 6) {
            switch (option) {
                case 1 -> CannonManager.load(player);
                case 2 -> CannonManager.pickup(player);
                case 3 -> CannonManager.empty(player);
            }
        }

        var region = World.getRegions().getRegion(position);
        GameObject obj = region.getGameObject(objectId, position);
        if (obj == null) {
            obj = region.getCustomObject(objectId, position);
        }

        if (obj == null) {
            return;
        }

        handleObj(player, obj);
    }

    private void handleObj(Player player, GameObject obj) {
        player.walkTo(obj, () -> {
            player.movement.reset();
            player.locking.lock(1);
            player.face(obj);

            WorldTask.schedule(() -> {
                player.face(obj);
                if (!EventDispatcher.execute(player, createInteractionEvent.apply(obj))) {
                    PluginManager.getDataBus().publish(player, new ObjectClickEvent(option, obj));
                }
            });
        });
    }

    // Getters
    public int getOption() { return option; }
    public int getId() { return id; }
    public int getX() { return x; }
    public int getY() { return y; }

}