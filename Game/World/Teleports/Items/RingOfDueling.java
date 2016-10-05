package scripts.AdvancedWalking.Game.World.Teleports.Items;

import org.tribot.api2007.types.RSTile;
import scripts.AdvancedWalking.Core.Collections.Pair;
import scripts.AdvancedWalking.Game.World.Teleports.ItemTeleport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Laniax
 */
public class RingOfDueling extends ItemTeleport {

    private static List<Pair<String, RSTile>> _destinations = new ArrayList<>(Arrays.asList(
            new Pair<>("Al Kharid Duel Arena", new RSTile(3313, 3092, 0)),
            new Pair<>("Castle Wars Arena", new RSTile(2441, 3092, 0))
    ));

    @Override
    public int[] getItemIds() {
        return new int[]{
                2552, // 8
                2554, // 7
                2556, // 6
                2558, // 5
                2560, // 4
                2562, // 3
                2564, // 2
                2566, // 1
        };
    }

    @Override
    public List<Pair<String, RSTile>> getDialogDestination() {
        return _destinations;
    }

    @Override
    public boolean accept() {
        return true;
    }

    @Override
    public String name() {
        return "Ring of Dueling";
    }

}
