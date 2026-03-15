package org.dm.event.widget;

import com.dm.content.DropDisplay;
import com.dm.content.DropDisplay.DropType;
import com.dm.content.ProfileViewer;
import com.dm.content.famehall.FameHandler;
import com.dm.content.simulator.DropSimulator;
import com.dm.content.store.impl.PersonalStore;
import com.dm.game.world.World;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.entity.mob.player.PlayerRight;
import com.dm.net.packet.out.SendMessage;
import com.dm.util.MessageColor;

/**
 * @author Jire
 */
public final class InputFieldEvent implements WidgetEvent {

    private final int component;
    private final String context;

    public InputFieldEvent(int component, String context) {
        this.component = component;
        this.context = context;
    }

    @Override
    public void handle(Player player) {
        if (component < 0) return;

        if (PlayerRight.isDeveloper(player)) {
            player.send(new SendMessage(
                    "[InputField] - Text: " + context + " Component: " + component,
                    MessageColor.DEVELOPER
            ));
        }

        switch (component) {
            case 42102 -> player.forClan(clan -> {
                if (clan.canManage(clan.getMember(player.getName()).orElse(null))) {
                    clan.setName(player, context);
                }
            });

            case 42104 -> player.forClan(clan -> {
                if (clan.canManage(clan.getMember(player.getName()).orElse(null))) {
                    clan.setTag(player, context);
                }
            });

            case 42106 -> {
                player.forClan(clan -> {
                    if (clan.canManage(clan.getMember(player.getName()).orElse(null))) {
                        clan.setSlogan(player, context);
                    }
                });
                player.forClan(clan -> {
                    if (clan.canManage(clan.getMember(player.getName()).orElse(null))) {
                        clan.getManagement().password = context;
                        if (context.isEmpty()) {
                            player.message("Your clan will no longer use a password.");
                        } else {
                            player.message("The new clan password is: " + context + ".");
                        }
                    }
                });
            }

            case 42108 -> player.forClan(clan -> {
                if (clan.canManage(clan.getMember(player.getName()).orElse(null))) {
                    clan.getManagement().password = context;
                    if (context.isEmpty()) {
                        player.message("Your clan will no longer use a password.");
                    } else {
                        player.message("The new clan password is: " + context + ".");
                    }
                }
            });

            case 38307 -> PersonalStore.changeName(player, context, false);
            case 38309 -> PersonalStore.changeName(player, context, true);
            case 26810 -> DropSimulator.drawList(player, context);
            case 48508 -> player.priceChecker.searchItem(context);
            case 58506 -> FameHandler.search(player, context);
            case 57021 -> player.presetManager.name(context);
            case 54506 -> DropDisplay.search(player, context, DropType.ITEM);
            case 54507 -> DropDisplay.search(player, context, DropType.NPC);

            case 353 -> {
                if (World.search(context).isPresent()) {
                    ProfileViewer.open(player, World.search(context).get());
                    return;
                }
                player.send(new SendMessage("You can not view " + context + "'profile as they are currently offline."));
            }

            case 354 -> {
                if (PlayerRight.isModerator(player)) {
                    if (World.search(context).isPresent()) {
                        //StaffPanel.search(player, context);
                        return;
                    }
                    player.send(new SendMessage("You can not manage " + context + " as they are currently offline."));
                }
            }
        }
    }

    public int getComponent() { return component; }
    public String getContext() { return context; }

}