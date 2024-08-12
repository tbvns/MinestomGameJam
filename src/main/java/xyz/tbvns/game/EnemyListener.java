package xyz.tbvns.game;

import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.ai.goal.RandomStrollGoal;
import net.minestom.server.entity.ai.target.ClosestEntityTarget;
import net.minestom.server.event.EventFilter;
import net.minestom.server.event.EventNode;
import net.minestom.server.event.entity.EntitySpawnEvent;
import net.minestom.server.event.trait.EntityEvent;

import java.util.List;

public class EnemyListener {

	// Doing this outside of the Enemy.class for now for testing purposes
	public EnemyListener(EventNode<? super EntityEvent> root) {
		EventNode<EntityEvent> node = EventNode.type("enemy-listener", EventFilter.ENTITY);
		node.addListener(EntitySpawnEvent.class, event -> {
			Entity entity = event.getEntity();
			if (!(entity instanceof Enemy enemy)) return;
			System.out.println(enemy.getPosition());
			// If the entity is in the (-125, -125) or (125, 125), they will choose a random direction
			if (Math.signum(enemy.getPosition().x()) == Math.signum(enemy.getPosition().z())) {
				enemy.addAIGroup(
						List.of(
								new RandomStrollGoal(enemy, 32)
						),
						List.of(
								new ClosestEntityTarget(enemy, 32, e -> e instanceof Player)
						)
				);
			} else { /* If the entity is in the other intersection, they will go towards the castle (0, 0)*/
				enemy.getNavigator().setPathTo(Pos.ZERO);
			}
		});

		root.addChild(node);
	}
}
