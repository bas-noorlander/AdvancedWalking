package scripts.AdvancedWalking.Game.World.Teleports.Teleports.Items;

import org.tribot.api2007.types.RSTile;
import scripts.AdvancedWalking.Core.Collections.Pair;
import scripts.AdvancedWalking.Game.World.Teleports.Teleports.AbstractItemTeleport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Laniax
 */
public class AmuletOfGlory extends AbstractItemTeleport {

    public static List<Pair<String, RSTile>> _destinations = new ArrayList<>(Arrays.asList(
            new Pair<>("Edgeville", new RSTile(0,0,0)),
            new Pair<>("Karamja", new RSTile(2543, 3091, 0)),
            new Pair<>("Draynor Village", new RSTile(3105, 3251, 0)),
            new Pair<>("Al-Kharid", new RSTile(0,0,0))
    ));

    @Override
    public int[] getItemIds() {
        return new int[] {
                111978, // 6
                111976, // 5
                1712, // 4
                1710, // 3
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
        return "Amulet of Glory";
    }
}
