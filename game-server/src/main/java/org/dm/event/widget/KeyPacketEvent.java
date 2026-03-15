package org.dm.event.widget;

import com.dm.game.world.entity.mob.data.PacketType;
import com.dm.game.world.entity.mob.player.Player;
import java.awt.event.KeyEvent;

/**
 * @author Jire
 */
public class KeyPacketEvent implements WidgetEvent {

    private final int key;

    public KeyPacketEvent(int key) {
        this.key = key;
    }

    @Override
    public boolean canHandle(Player player) {
        return key >= 0 && !player.locking.locked(PacketType.KEY);
    }

    @Override
    public void handle(Player player) {
        switch (key) {
            case KeyEvent.VK_ESCAPE -> {
                if (player.settings.ESC_CLOSE) {
                    player.interfaceManager.close();
                }
            }

            case KeyEvent.VK_SPACE -> {
                if (player.dialogueFactory.isActive()) {
                    player.dialogueFactory.execute();
                }
            }

            case KeyEvent.VK_1, KeyEvent.VK_NUMPAD1 -> {
                if (player.dialogueFactory.isActive()) {
                    if (player.optionDialogue.isPresent()) {
                        player.dialogueFactory.executeOption(0, player.optionDialogue);
                    }
                }
            }

            case KeyEvent.VK_2, KeyEvent.VK_NUMPAD2 -> {
                if (player.dialogueFactory.isActive()) {
                    if (player.optionDialogue.isPresent()) {
                        player.dialogueFactory.executeOption(1, player.optionDialogue);
                    }
                }
            }

            case KeyEvent.VK_3, KeyEvent.VK_NUMPAD3 -> {
                if (player.dialogueFactory.isActive()) {
                    if (player.optionDialogue.isPresent()) {
                        player.dialogueFactory.executeOption(2, player.optionDialogue);
                    }
                }
            }

            case KeyEvent.VK_4, KeyEvent.VK_NUMPAD4 -> {
                if (player.dialogueFactory.isActive()) {
                    if (player.optionDialogue.isPresent()) {
                        player.dialogueFactory.executeOption(3, player.optionDialogue);
                    }
                }
            }

            case KeyEvent.VK_5, KeyEvent.VK_NUMPAD5 -> {
                if (player.dialogueFactory.isActive()) {
                    if (player.optionDialogue.isPresent()) {
                        player.dialogueFactory.executeOption(4, player.optionDialogue);
                    }
                }
            }
        }
    }

    public int getKey() {
        return key;
    }

}