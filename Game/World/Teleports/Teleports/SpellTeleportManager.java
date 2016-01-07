package scripts.AdvancedWalking.Game.World.Teleports.Teleports;

import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.types.RSTile;
import scripts.AdvancedWalking.Core.Collections.Pair;
import scripts.AdvancedWalking.Game.World.Teleports.Teleports.Items.AmuletOfGlory;
import scripts.AdvancedWalking.Game.World.Teleports.Teleports.Items.RingOfDueling;
import scripts.AdvancedWalking.Game.World.Teleports.Teleports.Spells.LumbridgeTeleport;
import scripts.AdvancedWalking.Game.World.Teleports.Teleports.Spells.VarrockTeleport;

import java.util.*;

/**
 * @author Laniax
 */
public class SpellTeleportManager {

    public static List<AbstractMagicTeleport> _spellTeleports = new ArrayList<>(Arrays.asList(
            //TODO: make this more customizable
            new LumbridgeTeleport(),
            new VarrockTeleport()
    ));

    public static List<AbstractMagicTeleport> getAllTeleports() {

        return _spellTeleports;
    }

    /**
     * Returns a list of magic teleports which are relevant to pathfind on.
     * This is calculated by checking the distance of the teleport to the destination.
     *
     * @param origin      - The start to calculate from, usualy the player location
     * @param destination - the destination we want to reach
     * @return a list with all the teleports we are able to do.
     */
    public static List<AbstractMagicTeleport> getValidTeleports(Positionable origin, Positionable destination) {

        List<AbstractMagicTeleport> result = new ArrayList<>();

        RSTile org = origin.getPosition();
        if (org == null)
            return result;

        int distanceToDestination = org.distanceTo(destination);

        List<AbstractMagicTeleport> allTeleports = getAllTeleports();

        for (AbstractMagicTeleport teleport : allTeleports) {

            if (!teleport.isValid()) //todo: banking runes / teletab
                continue;

            RSTile dest = teleport.getDestination();

            if (dest != null) {
                if (dest.distanceTo(destination) < distanceToDestination)
                    result.add(teleport);
            }
        }

        return result;
    }

    /**
     * Gets the best, valid magic teleport we can do at this point.
     * Note that it still might not be worth it to do so, cost calculation should still be done.
     * @param origin
     * @param destination
     * @return
     */
    public static AbstractMagicTeleport getBestTeleport(Positionable origin, Positionable destination) {

        int distance = Integer.MAX_VALUE;
        AbstractMagicTeleport result = null;

        for (AbstractMagicTeleport teleport : getValidTeleports(origin, destination)) {

            RSTile teleportDestination = teleport.getDestination();
            if (teleportDestination != null) {

                int dist = teleportDestination.distanceTo(destination);

                if (dist < distance) {
                    result = teleport;
                    distance = dist;
                }
            }

        }

        return result;
    }


}
