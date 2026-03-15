package org.dm.objectexamines;

import com.dm.Config;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Jire
 */
public class ObjectExamines {

    private static final Path defaultFilePath = Paths.get("data", "def", "object_examines.json");

    public static final Int2ObjectMap<String> map = new Int2ObjectOpenHashMap<>();

    public static void loadObjectExamines(Path filePath, Gson gson) throws IOException {
        if (filePath == null) {
            filePath = defaultFilePath;
        }
        if (gson == null) {
            gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
        }

        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            ObjectExamine[] examines = gson.fromJson(reader, ObjectExamine[].class);
            for (ObjectExamine examine : examines) {
                map.put(examine.id(), examine.examine().replace("%SERVER_NAME%", Config.SERVER_NAME));
            }
        }
    }

    public static void loadObjectExamines() throws IOException {
        loadObjectExamines(defaultFilePath, new GsonBuilder().setPrettyPrinting().create());
    }
}