package scripts.advancedwalking.game.spells;

import org.tribot.api2007.Constants;
import org.tribot.api2007.types.RSTile;
import scripts.advancedwalking.core.collections.Pair;
import scripts.advancedwalking.game.runes.Rune;
import scripts.advancedwalking.game.runes.RuneSet;
import scripts.advancedwalking.game.teleports.MagicTeleport;

/**
 * @author Laniax
 */
public class VarrockTeleport extends MagicTeleport {

    private final RuneSet _set = new RuneSet(
            new Pair<>(Rune.LAW, 1),
            new Pair<>(Rune.AIR, 3),
            new Pair<>(Rune.FIRE, 1)
    );

    private final RSTile _destination = new RSTile(3212, 3426, 0);

    @Override
    public RuneSet getRunes() {
        return _set;
    }

    @Override
    public RSTile getDestination() {
        return _destination;
    }

    @Override
    public int getTeletabId() {
        return 0; //todo
    }

    @Override
    public int getSpellId() {
        return Constants.IDs.Spells.tele_varrock;
    }

    @Override
    public int getMagicLevel() {
        return 25;
    }

    @Override
    public boolean accept() {

        // here you can check any special uses. For example: quest requirements/equipment checks etc.
        // runes are checked elsewhere.

        return true;
    }

    @Override
    public String name() {
        return "Varrock Teleport";
    }
}
