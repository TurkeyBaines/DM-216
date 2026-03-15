package org.dm.defs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.dm.game.world.entity.mob.npc.definition.NpcDefinition;
import com.dm.game.world.entity.skill.Skill;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Jire
 */
public final class MonsterDefLoader {

    private MonsterDefLoader() {
        // Private constructor for utility class
    }

    public static final Int2ObjectMap<MonsterDef> map = new Int2ObjectOpenHashMap<>();

    public static void load() {
        load(new GsonBuilder().setPrettyPrinting().create());
    }

    public static void load(Gson gson) {
        File directory = new File("data/def/monsters-json/");
        File[] files = directory.listFiles();

        if (files == null) return;

        for (File file : files) {
            if (!file.getName().endsWith(".json")) continue;

            String nameWithoutExt = file.getName().substring(0, file.getName().lastIndexOf('.'));
            int id;
            try {
                id = Integer.parseInt(nameWithoutExt);
            } catch (NumberFormatException e) {
                continue;
            }

            try (FileReader reader = new FileReader(file)) {
                MonsterDef monsterDef = gson.fromJson(reader, MonsterDef.class);
                map.put(id, monsterDef);

                NpcDefinition npcDef = NpcDefinition.DEFINITIONS[id];
                if (npcDef != null) {
                    npcDef.setCombatLevel(monsterDef.getCombatLevel());

                    npcDef.getSkills()[Skill.HITPOINTS] = monsterDef.getHitpoints();
                    npcDef.getSkills()[Skill.ATTACK] = monsterDef.getAttackLevel();
                    npcDef.getSkills()[Skill.STRENGTH] = monsterDef.getStrengthLevel();
                    npcDef.getSkills()[Skill.DEFENCE] = monsterDef.getDefenceLevel();
                    npcDef.getSkills()[Skill.MAGIC] = monsterDef.getMagicLevel();
                    npcDef.getSkills()[Skill.RANGED] = monsterDef.getRangedLevel();

                    npcDef.setAttackDelay(monsterDef.getAttackSpeed());
                    npcDef.setAggressive(monsterDef.isAggressive());
                    npcDef.setPoisonImmunity(monsterDef.isImmunePoison());
                    npcDef.setVenomImmunity(monsterDef.isImmuneVenom());

                    npcDef.setBonuses(new int[]{
                            0, 0, 0, 0, 0, // Attack bonuses (often 0 for NPCs in this source)

                            monsterDef.getDefenceStab(),
                            monsterDef.getDefenceSlash(),
                            monsterDef.getDefenceCrush(),
                            monsterDef.getDefenceMagic(),
                            monsterDef.getDefenceRanged(),

                            monsterDef.getStrengthBonus(),
                            monsterDef.getRangedBonus(),
                            monsterDef.getMagicBonus(),

                            0 // Prayer bonus
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        load();
    }

}