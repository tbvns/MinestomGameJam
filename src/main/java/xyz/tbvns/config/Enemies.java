package xyz.tbvns.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import xyz.tbvns.config.objects.EnemieObject;
import xyz.tbvns.config.pojo.EnemiesPojo;

import java.io.File;
import java.io.FileWriter;

public class Enemies {
    public static EnemiesPojo enemiesData;

    public static String getPath() {
        return new File(Enemies.class.getProtectionDomain().getCodeSource().getLocation().getFile()).getParent() + "/enemies.yml";
    }

    public static void read() {
        try {
            File file = new File(getPath());
            if (!file.exists()) {
                create();
            }
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            enemiesData = mapper.readValue(file, EnemiesPojo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            File file = new File(getPath());
            if (!file.exists()) {
                create();
            }

            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            mapper.writer().writeValue(new FileWriter(file), enemiesData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void create() {
        try {
            File file = new File(getPath());
            if (!file.exists()) {
                file.createNewFile();
            }

            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            mapper.writer().writeValue(new FileWriter(file), new EnemiesPojo());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static EnemieObject getFromUISlot(int slot) {
        for (EnemieObject enemieObject : enemiesData.getEnemies()) {
            if (enemieObject.getGUIpos() == slot) {
                return enemieObject;
            }
        }
        return null;
    }

    public static EnemieObject getFromName(String name) {
        for (EnemieObject enemieObject : enemiesData.getEnemies()) {
            if (enemieObject.getName().equalsIgnoreCase(name)) {
                return enemieObject;
            }
        }
        return null;
    }
}
