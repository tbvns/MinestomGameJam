package xyz.tbvns.game;

import net.minestom.server.coordinate.Pos;
import net.minestom.server.event.EventFilter;
import net.minestom.server.event.EventNode;
import net.minestom.server.event.entity.EntitySpawnEvent;
import net.minestom.server.event.entity.EntityTickEvent;
import net.minestom.server.event.trait.EntityEvent;

public class EnemyListener {

	// Doing this outside of the Enemy.class for now for testing purposes
	public EnemyListener(EventNode<? super EntityEvent> root) {
		EventNode<EntityEvent> node = EventNode.type("enemy-listener", EventFilter.ENTITY);
		node.addListener(EntitySpawnEvent.class, event -> {
			if (!(event.getEntity() instanceof Enemy enemy)) return;
			enemy.lookAt(Pos.ZERO);
		});
		node.addListener(EntityTickEvent.class, event -> {
			if (!(event.getEntity() instanceof Enemy enemy)) return;
			Pos pos = enemy.getPosition().add(enemy.getShift().mul(-1)); // Remove the shift to get raw enemy position
			if (Math.abs(pos.z()) < 35 || Math.abs(pos.x()) < 35) return;
			Pos teleport = pos.add((Math.signum(pos.x()) * -1.0) / 20.0, 0, (Math.signum(pos.z() * -1.0)) / 20.0);
			enemy.teleport(teleport.add(enemy.getShift()));
		});

		root.addChild(node);
	}
}
