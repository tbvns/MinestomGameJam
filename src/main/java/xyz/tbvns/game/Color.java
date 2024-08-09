package xyz.tbvns.game;

import net.minestom.server.instance.block.Block;

public enum Color {

    RED(255, 0, 0, Block.RED_WOOL),
    ;

    private final int r;
    private final int g;
    private final int b;
    private final Block block;

    Color(int r, int g, int b, Block block) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.block = block;
    }

    public net.minestom.server.color.Color asColor() {
        return new net.minestom.server.color.Color(r, g, b);
    }

    public Block getBlock() {
        return block;
    }
}
