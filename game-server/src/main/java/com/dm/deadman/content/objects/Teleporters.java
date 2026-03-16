package com.dm.deadman.content.objects;

import com.dm.game.world.object.CustomGameObject;
import com.dm.game.world.object.ObjectDirection;
import com.dm.game.world.object.ObjectType;
import com.dm.game.world.position.Position;

public class Teleporters {
    private static CustomGameObject teleporter;
    private static Position[] positions = {
            new Position(2968, 3337, 0)
    };
    private static ObjectDirection[] directions = {
            ObjectDirection.EAST
    };

    public static void register() {
        for (int i = 0; i < positions.length; i++) {
            teleporter = new CustomGameObject(33181, positions[i], directions[i], ObjectType.GENERAL_PROP);
            teleporter.register();
            System.out.println("Registered!");
            System.out.println(teleporter.getDefinition().getName() + " (" + teleporter.getDefinition().getId() + ")");
            System.out.println("x:" + teleporter.getX() + ", y:" + teleporter.getY() + ", h:" + teleporter.getHeight());
            System.out.println("active: " + teleporter.active());
        }
    }

}
