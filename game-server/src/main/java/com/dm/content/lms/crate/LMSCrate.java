package com.dm.content.lms.crate;

import com.dm.content.lms.LMSGame;
import com.dm.game.world.object.CustomGameObject;
import com.dm.game.world.position.Position;
import com.dm.util.Utility;

import java.util.Objects;

public class LMSCrate {

    private CustomGameObject lootCrate;

    public CustomGameObject getLootCrate() { return lootCrate; }

    public LMSCrate() {
        spawn();
    }

    public void spawn() {
        LMSCrateLocation loc = LMSCrateLocation.values()[Utility.random(LMSCrateLocation.values().length - 1)];
        System.out.println("spawned at: "+loc.tip+" x="+loc.location.getX()+" y="+loc.location.getY());
        lootCrate = new CustomGameObject(29081, loc.location);
        lootCrate.register();

        LMSGame.gamePlayers.stream().filter(Objects::nonNull).forEach(player -> {
            player.message("@red@A loot crate has just appeared " + loc.tip + "!");
            if(player.getUsername().equalsIgnoreCase("Nighel")) player.move(getLocation());
        });

        /*LMSGame.gamePlayers.stream().forEach(player -> {
            player.message("@red@A loot crate has just appeared " + loc.tip + "!");
            }
        });*/
    }

    public Position getLocation() {
        return lootCrate.getPosition();
    }

    public void destroy() {
        lootCrate.unregister();
    }
}