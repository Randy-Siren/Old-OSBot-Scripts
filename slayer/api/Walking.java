package slayer.api;

import java.util.LinkedList;
import java.util.List;

import org.osbot.script.MethodProvider;
import org.osbot.script.Script;
import org.osbot.script.rs2.def.ObjectDefinition;
import org.osbot.script.rs2.map.Position;
import org.osbot.script.rs2.model.Entity;
import org.osbot.script.rs2.model.RS2Object;
import org.osbot.utility.PathFinder;

public class Walking extends MethodProvider {

	private Script scriptInstance;

	public Walking(Script scriptInstance) {
		this.scriptInstance = scriptInstance;
	}

	public boolean noObstacleBlocking(Position p) throws InterruptedException {
		RS2Object obstacle = getNextObstacle(p);
		if (obstacle != null) {
			obstacle.interact("open");
			return false;
		}
		return true;
	}

	public boolean noObstacleBlocking(Entity e) throws InterruptedException {
		RS2Object obstacle = getNextObstacle(e);
		if (obstacle != null) {
			obstacle.interact("open");
			return false;
		}
		return true;
	}

	public boolean walkPathMM(Position[] path) throws InterruptedException {
		return walkPathMM(path, 3);
	}

	public boolean walkPathMM(Position[] path, int distance)
			throws InterruptedException {
		Position next = nextTile(path, 17);
		if (next != null && noObstacleBlocking(next))
			return scriptInstance.walkMiniMap(next);
		Position lastNode = path[path.length - 1];
		return lastNode != null && scriptInstance.distance(lastNode) < distance;
	}

	public boolean walkPathScreen(Position[] path) throws InterruptedException {
		return walkPathScreen(path, 3);
	}

	public boolean walk(Position p, Entity e) throws InterruptedException {
		if (noObstacleBlocking(e)) {
			return scriptInstance.walkMainScreen(e.getPosition(), true);
		}
		return false;
	}

	public boolean walkPathScreen(Position[] path, int distance)
			throws InterruptedException {
		Position next = nextTile(path, 17);
		if (next != null && noObstacleBlocking(next))
			return scriptInstance.walkMainScreen(next, true);
		Position lastNode = path[path.length - 1];
		return lastNode != null && scriptInstance.distance(lastNode) < distance;
	}

	public Position nextTile(Position path[], int skipDist) {
		int dist = -1, closest = -1;
		for (int i = path.length - 1; i >= 0; i--) {
			Position tile = path[i];
			int d = scriptInstance.distance(tile);
			if (d < dist || dist == -1) {
				dist = d;
				closest = i;
			}
		}

		int feasibleTileIndex = -1;

		for (int i = closest; i < path.length; i++) {

			if (scriptInstance.distance(path[i]) <= skipDist) {
				feasibleTileIndex = i;
			} else {
				break;
			}
		}

		if (feasibleTileIndex == -1) {
			return null;
		} else {
			return path[feasibleTileIndex];
		}
	}

	public RS2Object getNextObstacle(Entity e) {
		List<RS2Object> obstacles = getObstacles();
		List<Position> path = generatePath(e);
		if (path == null)
			return null;

		for (RS2Object obj : obstacles) {
			for (Position pos : path) {
				if (obj.getPosition().equals(pos)) {
					return obj;
				}
			}
		}
		return null;
	}

	public RS2Object getNextObstacle(Position p) {
		List<RS2Object> obstacles = getObstacles();
		List<Position> path = generatePath(p);
		if (path == null)
			return null;

		for (RS2Object obj : obstacles) {
			for (Position pos : path) {
				if (obj.getPosition().equals(pos)) {
					return obj;
				}
			}
		}
		return null;
	}

	public List<RS2Object> getObstacles() {
		List<RS2Object> list = new LinkedList<RS2Object>();
		for (RS2Object obj : scriptInstance.client.getCurrentRegion()
				.getObjects()) {
			if (obj.getType() == 0 && obj.getDefinition() != null
					&& obj.getDefinition().getActions() != null
					&& obj.getDefinition().getModelIds() != null
					&& obj.getDefinition().getModelIds().length < 3) {
				search: {
					for (String action : obj.getDefinition().getActions()) {
						if (action != null
								&& action.equalsIgnoreCase("open")) {
							list.add(obj);
							break search;
						}
					}
				}
			}
		}
		return list;
	}

	private List<Position> generatePath(Position p) {
		PathFinder pf = new PathFinder();
		int[][] flags = generateModifiedClippingData();
		List<Position> path = pf.findPath(scriptInstance.bot, p, flags);
		if (path == null)
			return null;
		return path;
	}

	private List<Position> generatePath(Entity e) {
		PathFinder pf = new PathFinder();
		int[][] flags = generateModifiedClippingData();
		List<Position> path = pf.findPath(scriptInstance.bot, e, flags);
		if (path == null)
			return null;
		return path;
	}

	private int[][] generateModifiedClippingData() {
		int[][] origFlags = scriptInstance.client.getClippingPlanes()[scriptInstance.client
				.getPlane()].getTileFlags();
		int[][] flags = new int[origFlags.length][origFlags.length];
		for (int x = 0; x < flags.length; x++) {
			for (int y = 0; y < flags.length; y++) {
				flags[x][y] = origFlags[x][y];
			}
		}

		for (RS2Object obj : getObstacles()) {
			int lx = obj.getLocalX();
			int ly = obj.getLocalY();
			ObjectDefinition def = obj.getDefinition();
			if (def.isClipping1()) {
				switch (obj.getOrientation()) {
				case 0:
				case 2:
					flags[lx][ly] &= ~585;
					break;
				case 1:
				case 3:
					flags[lx][ly] &= ~1170;
					break;
				}
			}

			if (def.getClipping2() != 0) {
				if (0 == obj.getOrientation()) {
					flags[lx][ly] &= ~128;
					flags[lx - 1][ly] &= ~8;
				}

				if (1 == obj.getOrientation()) {
					flags[lx][ly] &= ~2;
					flags[lx][ly + 1] &= ~32;
				}

				if (2 == obj.getOrientation()) {
					flags[lx][ly] &= ~8;
					flags[lx + 1][ly] &= ~128;
				}

				if (3 == obj.getOrientation()) {
					flags[lx][ly] &= ~32;
					flags[lx][ly - 1] &= ~2;
				}

				if (def.isClipping3()) {
					if (0 == obj.getOrientation()) {
						flags[lx][ly] &= ~65536;
						flags[lx - 1][ly] &= ~4096;
					}

					if (obj.getOrientation() == 1) {
						flags[lx][ly] &= ~1024;
						flags[lx][ly + 1] &= ~16384;
					}

					if (2 == obj.getOrientation()) {
						flags[lx][ly] &= ~4096;
						flags[lx + 1][ly] &= ~65536;
					}

					if (3 == obj.getOrientation()) {
						flags[lx][ly] &= ~16384;
						flags[lx][ly - 1] &= ~1024;
					}
				}
			}
		}
		return flags;
	}

	public Position[] reversePath(Position[] path) {
		Position[] t = new Position[path.length];
		for (int i = 0; i < t.length; i++) {
			t[i] = path[path.length - i - 1];
		}
		return t;
	}
}
