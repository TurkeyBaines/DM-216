package org.jire.tarnishps.event.`object`

import com.dm.content.event.EventDispatcher
import com.dm.content.event.impl.ItemOnObjectInteractionEvent
import com.dm.game.event.impl.ItemOnObjectEvent
import com.dm.game.plugin.PluginManager
import com.dm.game.world.InterfaceConstants
import com.dm.game.world.World
import com.dm.game.world.entity.mob.player.Player
import com.dm.game.world.position.Position
import com.dm.net.packet.out.SendMessage

/**
 * @author Jire
 */
class ItemOnObjectEvent(
    val interfaceType: Int,
    val itemId: Int,
    val slot: Int,
    val objectId: Int,
    val x: Int, val y: Int
) : ObjectEvent {

    override fun handle(player: Player) {
        val used = player.inventory[slot] ?: return
        if (!used.matchesId(itemId)) return

        val position = Position(x, y, player.height)

        var id = objectId

        for (playerBirdHouseData in player.birdHouseData) {
            if (playerBirdHouseData.birdhousePosition == position) {
                id = playerBirdHouseData.oldObjectId
                break
            }
        }

        val region = World.getRegions().getRegion(position)
        val obj = region.getGameObject(id, position) ?: return

        player.walkTo(obj) {
            if (interfaceType == InterfaceConstants.INVENTORY_INTERFACE) {
                player.face(obj)
                if (EventDispatcher.execute(player, ItemOnObjectInteractionEvent(used, obj))) return@walkTo
                if (!PluginManager.getDataBus().publish(player, ItemOnObjectEvent(used, slot, obj))) {
                    player.send(SendMessage("Nothing interesting happens."))
                }
            }
        }
    }

}