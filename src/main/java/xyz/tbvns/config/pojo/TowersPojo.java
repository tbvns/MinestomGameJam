package xyz.tbvns.config.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.minestom.server.entity.EntityType;
import xyz.tbvns.config.objects.TowerArmor;
import xyz.tbvns.config.objects.TowerLevel;
import xyz.tbvns.config.objects.TowerType;

@Data
@NoArgsConstructor
public class TowersPojo {
    TowerType[] towers = new TowerType[] {
            new TowerType("<green>Skeleton",
                    "<white>Its a skeleton, what do you want me to say ?",
                    EntityType.SKELETON,
                    new TowerLevel[]{
                            new TowerLevel(10f, 1f, 1000f, 3, TowerArmor.LEATHER, "minecraft:bow", 10),
                            new TowerLevel(50f, 2f, 900f, 4, TowerArmor.IRON, "minecraft:bow", 19),
                            new TowerLevel(100f, 4f, 800f, 5, TowerArmor.GOLD, "minecraft:bow", 28),
                            new TowerLevel(200f, 8f, 700f, 6, TowerArmor.DIAMOND, "minecraft:bow", 37)
                    },
                    new boolean[]{false, true, true, true},
                    false,
                    4,
                    "minecraft:bow"
            )
    };
}
