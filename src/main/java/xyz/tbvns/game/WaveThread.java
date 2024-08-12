package xyz.tbvns.game;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.title.Title;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.Instance;
import xyz.tbvns.Utils;
import xyz.tbvns.config.Enemies;
import xyz.tbvns.config.Waves;
import xyz.tbvns.config.objects.EnemieObject;
import xyz.tbvns.config.objects.WaveObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WaveThread implements Runnable {
    Instance instance;
    int waveID;
    public WaveThread(int waveID, Instance instance) {
        this.waveID = waveID;
        this.instance = instance;
    }

    @Override
    public void run() {
        Utils.sleep(15 * 1000);
        List<Enemy> enemies = new ArrayList<>();
        List<String> enemyTypes = new ArrayList<>();
        WaveObject[] waves = Waves.waveData.getWaves();
        for (WaveObject wave : waves) {
            if (waveID >= wave.getStartingWave() && (waveID <= wave.getEndingWaves() || wave.getEndingWaves() == -1) && ((float) waveID / wave.getTimeBetweenSpawn()) % 1 == 0) {
                long entityCount = wave.getPerWavesCount();
                if (waveID - wave.getStartingWave() != 0) {
                    entityCount+= Math.round(((double) (waveID - wave.getStartingWave()) / wave.getTimeBetweenSpawn() * wave.getPerSpawnAddition()));
                }
                if (entityCount > wave.getPerWavesMaxCount()) entityCount = wave.getPerWavesMaxCount();
                for (int i = 0; i < entityCount; i++) {
                    EnemieObject obj = Enemies.getFromName(wave.getEnemyType());
                    if (obj != null) {
                        enemies.add(new Enemy(obj));
                        if (!enemyTypes.contains(obj.getName())) enemyTypes.add(obj.getName());
                    }
                }
            }
        }

        instance.getPlayers().forEach(p -> {
            p.showTitle(Title.title(Component.text("Wave " + (waveID + 1)).color(TextColor.color(255, 0, 0)),
                    Utils.format("<gray>" + enemyTypes.stream().reduce("Invaders: ", (s1, s2) -> {
                        if (s1.equals("Invaders: ")) return s1 + s2;
                        return s1 + ", " + s2;
                    }))));
        });

        for (int i = enemies.size() - 1; i >= 0; i--) {
            Random random = new Random();
            int id = 0;
            if (i != 0) {
                id = random.nextInt(0, i);
            }
            Pos spawn = new Pos(125, 0, 125);
            Random spwnRNG = new Random();
            int spwnID = spwnRNG.nextInt(1, 4);
            if (spwnID == 1) {
                spawn = new Pos(125, 0, 125);
            } else if (spwnID == 2) {
                spawn = new Pos(-125, 0, 125);
            } else if (spwnID == 3) {
                spawn = new Pos(-125, 0, -125);
            } else if (spwnID == 4) {
                spawn = new Pos(125, 0, -125);
            }

            enemies.get(id).setInstance(instance, spawn.add(new Pos(0.5, 0, 0.5).add(enemies.get(id).getShift())));
            enemies.get(id).getEntityMeta().setHasNoGravity(true);
            enemies.remove(id);
            Utils.sleep(500);
        }

        waveID++;
        Thread thread = new Thread(new WaveThread(waveID, instance));
        thread.start();
    }
}
