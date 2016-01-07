package scripts.AdvancedWalking.Game.World.Teleports.Teleports;

import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.types.RSTile;
import scripts.AdvancedWalking.Core.Collections.Pair;
import scripts.AdvancedWalking.Game.World.Teleports.Teleports.Items.AmuletOfGlory;
import scripts.AdvancedWalking.Game.World.Teleports.Teleports.Items.RingOfDueling;

import java.util.*;

/**
 * @author Laniax
 */
public class ItemTeleportManager {

    public static List<AbstractItemTeleport> _itemTeleports = new ArrayList<>(Arrays.asList(
            //TODO: make this more customizable
            new RingOfDueling(),
            new AmuletOfGlory()
    ));

    public static List<AbstractItemTeleport> getAllTeleports() {

        return _itemTeleports;
    }

    /**
     * Returns a list of item teleports which are relevant to pathfind on.
     * This is calculated by checking the distance of the teleport to the destination.
     *
     * @param origin      - The start to calculate from, usualy the player location
     * @param destination - the destination we want to reach
     * @return a hashmap, with the teleport as key, and the dialog option index as value
     */
    public static HashMap<AbstractItemTeleport, Integer> getValidTeleports(Positionable origin, Positionable destination) {

        HashMap<AbstractItemTeleport, Integer> result = new HashMap<>();

        RSTile org = origin.getPosition();
        if (org == null)
            return result;

        int distanceToDestination = org.distanceTo(destination);

        List<AbstractItemTeleport> allItemTeleports = getAllTeleports();

        for (int i = 0; i < allItemTeleports.size(); i++) {

            AbstractItemTeleport teleport = allItemTeleports.get(i);

            if (!teleport.isValid()) //todo: banking for item
                continue;

            Pair<String, RSTile> dest = teleport.getDialogClosestTo(destination);

            if (dest != null) {
                if (dest.getValue().distanceTo(destination) < distanceToDestination)
                    result.put(teleport, i);
            }

        }

        return result;
    }

    /**
     * Gets the best, valid item teleport we can do at this point.
     * Note that it still might not be worth it to do so, cost calculation should still be done.
     * @param origin
     * @param destination
     * @return
     */
    public static Pair<AbstractItemTeleport, Integer> getBestTeleport(Positionable origin, Positionable destination) {

        int distance = Integer.MAX_VALUE;
        Map.Entry<AbstractItemTeleport, Integer> best = null;

        for (Map.Entry<AbstractItemTeleport, Integer> itemTeleport : getValidTeleports(origin, destination).entrySet()) {

            RSTile teleportDestination = itemTeleport.getKey().getDestinationForDialog(itemTeleport.getValue());
            if (teleportDestination != null) {

                int dist = teleportDestination.distanceTo(destination);

                if (dist < distance) {
                    best = itemTeleport;
                    distance = dist;
                }
            }

        }

        if (best == null)
            return null;

        return new Pair<>(best.getKey(), best.getValue());
    }


}
