package plugin.click.object;

import com.dm.game.event.impl.ItemContainerContextMenuEvent;
import com.dm.game.event.impl.ObjectClickEvent;
import com.dm.game.plugin.PluginContext;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.object.GameObject;
import com.dm.net.packet.out.SendInventoryInterface;
import com.dm.net.packet.out.SendItemOnInterface;
import com.dm.net.packet.out.SendString;

public class DepositBoxPlugin extends PluginContext {

    @Override
    protected boolean firstClickObject(Player player, ObjectClickEvent event) {
        GameObject gameObject = event.getObject();

        if(gameObject == null || gameObject.getDefinition() == null || gameObject.getDefinition().getName() == null || !gameObject.getDefinition().getName().equalsIgnoreCase("Bank Deposit Box")) return false;


        System.out.println(gameObject.getDefinition().getName());
        player.interfaceManager.setMain(4465);
        player.send(new SendString("The Bank of Dead Men - Deposit Box", 7421));
        player.send(new SendInventoryInterface(4465, 197));
        player.send(new SendItemOnInterface(7423, player.inventory.toArray()));

        return true;
    }

    @Override
    protected boolean firstClickItemContainer(Player player, ItemContainerContextMenuEvent event) {
        if(event.getInterfaceId() != 7423) return false;

        player.bank.deposit(event.getRemoveSlot(), 1);
        player.send(new SendItemOnInterface(7423, player.inventory.toArray()));

        return true;
    }

    @Override
    protected boolean secondClickItemContainer(Player player, ItemContainerContextMenuEvent event) {
        if(event.getInterfaceId() != 7423) return false;

        player.bank.deposit(event.getRemoveSlot(), 5);
        player.send(new SendItemOnInterface(7423, player.inventory.toArray()));

        return true;
    }

    @Override
    protected boolean thirdClickItemContainer(Player player, ItemContainerContextMenuEvent event) {
        if(event.getInterfaceId() != 7423) return false;

        player.bank.deposit(event.getRemoveSlot(), 10);
        player.send(new SendItemOnInterface(7423, player.inventory.toArray()));

        return true;
    }

    @Override
    protected boolean fourthClickItemContainer(Player player, ItemContainerContextMenuEvent event) {
        if(event.getInterfaceId() != 7423) return false;

        player.bank.deposit(event.getRemoveSlot(), player.inventory.computeAmountForId(event.getRemoveId()));
        player.send(new SendItemOnInterface(7423, player.inventory.toArray()));

        return true;
    }

    public static void deposit(Player player, int removeSlot, int removeAmount) {
        player.bank.deposit(removeSlot, removeAmount);
        player.send(new SendItemOnInterface(7423, player.inventory.toArray()));
    }
}
