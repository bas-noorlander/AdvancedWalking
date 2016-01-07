package scripts.AdvancedWalking.Game.World.Teleports.Teleports;

import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSTile;
import scripts.AdvancedWalking.Core.Collections.Pair;

import java.util.List;

/**
 * Abstract class for all the item-based teleports.
 *
 * @author Laniax
 */
public abstract class AbstractItemTeleport implements ITeleport {

    public abstract int[] getItemIds();

    /**
     * Returns the destination of the given dialog index.
     * @return the rstile, null if not found
     */
    public RSTile getDestinationForDialog(int index) {

        List<Pair<String, RSTile>> destinations = getDialogDestination();

        if (index > destinations.size())
            return null;

        return destinations.get(index).getValue();
    }

    public abstract List<Pair<String, RSTile>> getDialogDestination();

    public abstract boolean accept();

    /**
     * Returns if we have the item & its requirements to do this teleport.
     * @return
     */
    public boolean isValid() {
        //todo: banking for item
        return accept() && (Inventory.find(getItemIds()).length > 0 || Equipment.find(getItemIds()).length > 0);
    }

    public Pair<String, RSTile> getDialogClosestTo(Positionable pos) {

        Pair<String, RSTile> result = null;
        for (Pair<String, RSTile> destination : getDialogDestination()) {

            if (result == null || destination.getValue().distanceTo(pos) < result.getValue().distanceTo(pos))
                result = destination;
        }

        return result;
    }
}
