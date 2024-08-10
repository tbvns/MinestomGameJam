package xyz.tbvns.game;

import lombok.Getter;
import net.minestom.server.instance.block.Block;

public enum Color {

    RED(255, 0, 0, Block.RED_WOOL, 1.0f, 2.0f),
    GREEN(0, 255, 0, Block.GREEN_WOOL, 2.0f, 0.5f),
    BLUE(0, 0, 255, Block.BLUE_WOOL, 0.5f, 5.0f),
    ;

    private final int r;
    private final int g;
    private final int b;
    @Getter
    private final Block block;
    @Getter
    private final float projectileSpeed;
    @Getter
    private final float projectileDamage;

    Color(int r, int g, int b, Block block, float projectileSpeed, float projectileDamage) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.block = block;
        this.projectileSpeed = projectileSpeed;
        this.projectileDamage = projectileDamage;
    }

    public net.minestom.server.color.Color asColor() {
        return new net.minestom.server.color.Color(r, g, b);
    }

}
