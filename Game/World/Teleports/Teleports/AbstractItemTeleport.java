package scripts.AdvancedWalking.Game.World.Teleports.Teleports;

import org.tribot.api2007.types.RSTile;
import scripts.AdvancedWalking.Core.Collections.Pair;

import java.util.List;

/**
 * Contains all spell-based teleports.
 *
 * @author Laniax
 */
public abstract class AbstractItemTeleport implements ITeleport {

    public abstract int[] getItemIds();

    public abstract List<Pair<String, RSTile>> getDialogDestination();

    public abstract boolean accept();
}
