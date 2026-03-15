package org.dm.event.widget;

import com.dm.content.achievement.AchievementHandler;
import com.dm.content.achievement.AchievementKey;
import com.dm.game.world.entity.mob.UpdateFlag;
import com.dm.game.world.entity.mob.player.Player;
import com.dm.game.world.entity.mob.player.appearance.Appearance;
import com.dm.game.world.entity.mob.player.appearance.Gender;
import com.dm.net.packet.in.AppearanceChangePacketListener;

/**
 * @author Jire
 */
public final class AppearanceChangeEvent implements WidgetEvent {

    private final int gender;
    private final int head;
    private final int jaw;
    private final int torso;
    private final int arms;
    private final int hands;
    private final int legs;
    private final int feet;
    private final int hairColor;
    private final int torsoColor;
    private final int legsColor;
    private final int feetColor;
    private final int skinColor;

    public AppearanceChangeEvent(int gender, int head, int jaw, int torso, int arms, int hands,
                                 int legs, int feet, int hairColor, int torsoColor,
                                 int legsColor, int feetColor, int skinColor) {
        this.gender = gender;
        this.head = head;
        this.jaw = jaw;
        this.torso = torso;
        this.arms = arms;
        this.hands = hands;
        this.legs = legs;
        this.feet = feet;
        this.hairColor = hairColor;
        this.torsoColor = torsoColor;
        this.legsColor = legsColor;
        this.feetColor = feetColor;
        this.skinColor = skinColor;
    }

    @Override
    public void handle(Player player) {
        Appearance appearance = new Appearance(
                gender == 0 ? Gender.MALE : Gender.FEMALE,
                head,
                gender == 0 ? jaw : -1,
                torso,
                arms,
                hands,
                legs,
                feet,
                hairColor,
                torsoColor,
                legsColor,
                feetColor,
                skinColor
        );

        if (AppearanceChangePacketListener.isValid(player, appearance)) {
            player.appearance = appearance;
            player.updateFlags.add(UpdateFlag.APPEARANCE);
            player.interfaceManager.close();

            AchievementHandler.activate(player, AchievementKey.CHANGE_APPEARANCE, 1);
        }
    }

}