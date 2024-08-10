package xyz.tbvns.projectils;

public interface Projectile {
    void spawn();
    void tick();
    void onCollideBlock();
    void onCollideEntity();
}
