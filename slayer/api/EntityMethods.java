package slayer.api;

import java.util.ArrayList;
import java.util.List;

import org.osbot.script.Script;
import org.osbot.script.rs2.def.NPCDefinition;
import org.osbot.script.rs2.def.ObjectDefinition;
import org.osbot.script.rs2.model.Entity;
import org.osbot.script.rs2.model.NPC;
import org.osbot.script.rs2.model.RS2Object;

public class EntityMethods {
	private Script scriptInstance;

	public EntityMethods(Script scriptInstance) {
		this.scriptInstance = scriptInstance;
	}

	public RS2Object closestObjectWithAction(String action) {
		List<RS2Object> allObjects = scriptInstance.client.getCurrentRegion()
				.getObjects();
		List<RS2Object> objects = new ArrayList<RS2Object>();
		if (allObjects != null && allObjects.size() > 0) {
			for (RS2Object object : allObjects) {
				if (object != null && hasAction(object, action))
					objects.add(object);
			}
		}
		return (RS2Object) closest(objects);
	}

	public NPC closestNPCWithAction(String action) {
		List<NPC> allNPCs = scriptInstance.client.getLocalNPCs();
		List<NPC> NPCs = new ArrayList<NPC>();
		if (allNPCs != null && allNPCs.size() > 0) {
			for (NPC npc : allNPCs) {
				if (npc != null && hasAction(npc, action))
					NPCs.add(npc);
			}
		}
		return (NPC) closest(NPCs);
	}

	public Entity closest(List<? extends Entity> entities) {
		Entity closest = null;
		for (Entity entity : entities) {
			if (closest == null)
				closest = entity;
			if (scriptInstance.distance(entity) < scriptInstance
					.distance(closest))
				closest = entity;
		}
		return closest;
	}

	public boolean hasAction(NPC npc, String action) {
		NPCDefinition i = npc.getDefinition();
		return hasAction(i.getActions(), action);
	}

	public boolean hasAction(RS2Object object, String action) {
		ObjectDefinition i = object.getDefinition();
		return hasAction(i.getActions(), action);
	}

	public boolean hasAction(String[] actions, String action) {
		for (String a : actions) {
			if (action.equals(a))
				return true;
		}
		return false;
	}
}
