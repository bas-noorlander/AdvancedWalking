package scripts.AdvancedWalking.Game.World.Teleports;

import org.tribot.api2007.types.RSTile;
import scripts.AdvancedWalking.Core.Collections.Pair;

/**
 * Contains all item-based teleports.
 * @author Laniax
 */
public enum ItemTeleports implements ITeleport {

    RING_OF_DUELING(new int[]{0}, new Pair<>("Al Kharid Duel Arena", new RSTile(0,0,0)), new Pair<>("Castle Wars Arena", new RSTile(0,0,0))),
    AMULET_OF_GLORY(new int[]{0}, new Pair<>("Edgeville", new RSTile(0,0,0)), new Pair<>("Karamja", new RSTile(0,0,0)), new Pair<>("Draynor Village", new RSTile(0,0,0)), new Pair<>("Al-Kharid", new RSTile(0,0,0)));
    // ectofunctus etc

    private final int[] _itemid;
    private final Pair<String, RSTile>[] _options;

    ItemTeleports(int[] itemId, Pair<String, RSTile>... options) {
        this._itemid = itemId;

        this._options = options;
    }
}
