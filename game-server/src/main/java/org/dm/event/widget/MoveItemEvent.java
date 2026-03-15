package org.dm.event.widget;

import com.dm.game.world.InterfaceConstants;
import com.dm.game.world.entity.mob.player.Player;

/**
 * @author Jire
 */
public final class MoveItemEvent implements WidgetEvent {

    private final int interfaceId;
    private final int inserting;
    private final int fromSlot;
    private final int toSlot;

    public MoveItemEvent(int interfaceId, int inserting, int fromSlot, int toSlot) {
        this.interfaceId = interfaceId;
        this.inserting = inserting;
        this.fromSlot = fromSlot;
        this.toSlot = toSlot;
    }

    @Override
    public void handle(Player player) {
        switch (interfaceId) {
            case InterfaceConstants.INVENTORY_INTERFACE, InterfaceConstants.INVENTORY_STORE ->
                    player.inventory.swap(fromSlot, toSlot);

            case InterfaceConstants.WITHDRAW_BANK ->
                    player.bank.moveItem(inserting, fromSlot, toSlot);

            default ->
                    System.out.println("Unkown Item movement itemcontainer id: " + interfaceId);
        }
    }

    public int getInterfaceId() { return interfaceId; }
    public int getInserting() { return inserting; }
    public int getFromSlot() { return fromSlot; }
    public int getToSlot() { return toSlot; }

}