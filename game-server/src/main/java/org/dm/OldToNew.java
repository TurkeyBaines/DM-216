package org.dm;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Jire
 */
public class OldToNew {

    private static Int2IntMap map;

    public static void load() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("data/def/npc/oldtonew.txt"));
        map = new Int2IntOpenHashMap(lines.size());
        for (String line : lines) {
            if (line.contains("=")) {
                String[] split = line.split("=");
                int oldId = Integer.parseInt(split[0]);
                int newId = Integer.parseInt(split[1]);
                map.put(oldId, newId);
            }
        }
    }

    //315=308
    public static int get(int oldId) {
        return map.getOrDefault(oldId, -1);
    }
}