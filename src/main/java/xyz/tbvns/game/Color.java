package xyz.tbvns.game;

import lombok.Getter;
import net.minestom.server.instance.block.Block;

@Getter
public enum Color {

    RED("<red>", Block.RED_WOOL, 1.0f, 2.0f, 0.9f),
    GREEN("<green>", Block.GREEN_WOOL, 2.0f, 0.5f, 0.7f),
    BLUE("<blue>", Block.BLUE_WOOL, 0.5f, 5.0f, 1.2f),
    YELLOW("<yellow>", Block.YELLOW_WOOL, 0.35f, 10.0f, 1.4f, true, 1.3f)
    ;

    /**
     * @return The Adventure-compatible color code for this color.
     */
    private final String colorCode;
    private final Block block;
    private final float projectileSpeed;
    private final float projectileDamage;
    private final float projectileScale;
    private final boolean hasGravity;
    private final float initialYVel;

    Color(String colorCode, Block block, float projectileSpeed, float projectileDamage, float projectileScale) {
        this.colorCode = colorCode;
        this.block = block;
        this.projectileSpeed = projectileSpeed;
        this.projectileDamage = projectileDamage;
        this.projectileScale = projectileScale;
        this.hasGravity = false;
        this.initialYVel = 0;
    }

    Color(String colorCode, Block block, float projectileSpeed, float projectileDamage, float projectileScale, boolean hasGravity, float initialYVel) {
        this.colorCode = colorCode;
        this.block = block;
        this.projectileSpeed = projectileSpeed;
        this.projectileDamage = projectileDamage;
        this.projectileScale = projectileScale;
        this.hasGravity = hasGravity;
        this.initialYVel = initialYVel;
    }

}
