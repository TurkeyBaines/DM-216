package com.dm.game.world.entity.mob.player;

import java.util.Arrays;
import java.util.Optional;

/**
 * Holds all the player right data.
 *
 * @author Daniel
 */
public enum PlayerRight {
    PLAYER("Player", "000000", 0, -1, 4111),
    MODERATOR("Moderator", "245EFF", 1, -1, 4116),
    ADMINISTRATOR("Administrator", "D17417", 2, -1, 4116),
    OWNER("Owner", "ED0C0C", 2, -1, 4117);


    /**
     * The rank name.
     */
    private final String name;

    /**
     * The crown identification.
     */
    private final int crown;

    private final int moneyRequired;

    /**
     * The rank color.
     */
    private final String color;

    /**
     * The rank rest animation.
     */
    private final int restAnimation;

    /**
     * Constructs a new <code>PlayerRight</code>.
     */
    PlayerRight(String name, String color, int crown, int moneyRequired, int restAnimation) {
        this.name = name;
        this.color = color;
        this.crown = crown;
        this.moneyRequired = moneyRequired;
        this.restAnimation = restAnimation;
    }

    public static Optional<PlayerRight> lookup(int id) {
        return Arrays.stream(values()).filter(it -> it.crown == id).findFirst();
    }

    public static boolean isOwner(Player player) {
        return player.right.equals(OWNER);
    }

    /**
     * Checks if the player is a privileged member.
     */
    public static boolean isAdministrator(Player player) {
        return isOwner(player) || player.right.equals(ADMINISTRATOR);
    }

    /**
     * Checks if the player is a management member.
     */
    public static boolean isModerator(Player player) {
        return isOwner(player) || isAdministrator(player) || player.right.equals(MODERATOR);
    }

    /**
     * Gets the crown display.
     */
    public static String getCrown(Player player) {
        return getCrown(player.right);
    }

    /**
     * Gets the crown display.
     */
    public static String getCrown(PlayerRight right) {
        return right.equals(PLAYER) ? "" : "<img=" + (right.getCrown() - 1) + ">";
    }

    public static String getColor(PlayerRight right) {
        return "<col=" + right.getColor() + "><img=" + (right.getCrown() - 1) + ">";
    }

    public String getCrownText() {
        return this == PLAYER ? "" : "<img=" + (crown - 1) + "> ";
    }


    /**
     * Gets the name of the rank.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the crown of the rank.
     */
    public int getCrown() {
        return crown;
    }

    public int getMoneyRequired() {
        return moneyRequired;
    }

    /**
     * Gets the color of the rank.
     */
    public String getColor() {
        return color;
    }

    /**
     * Gets the rest animation of the rank.
     */
    public int getRestAnimation() {
        return restAnimation;
    }

    public final boolean greater(PlayerRight other) {
        return ordinal() > other.ordinal();
    }

    public final boolean greaterOrEqual(PlayerRight other) {
        return ordinal() >= other.ordinal();
    }

    public final boolean less(PlayerRight other) {
        return ordinal() < other.ordinal();
    }

    public final boolean lessOrEqual(PlayerRight other) {
        return ordinal() <= other.ordinal();
    }
}
