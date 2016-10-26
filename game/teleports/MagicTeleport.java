package scripts.advancedwalking.game.teleports;

import org.tribot.api2007.Inventory;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSTile;
import scripts.advancedwalking.core.collections.Pair;
import scripts.advancedwalking.game.runes.Rune;
import scripts.advancedwalking.game.runes.RuneSet;

/**
 * Contains all spell-based teleports.
 *
 * @author Laniax
 */
public abstract class MagicTeleport implements Teleport {

    public abstract RuneSet getRunes();

    public abstract RSTile getDestination();

    public abstract int getTeletabId();

    public abstract int getSpellId();

    public abstract int getMagicLevel();

    public abstract boolean accept();

    protected boolean hasRunes() {

        for (Pair<Rune, Integer> pair : getRunes().get()) {
            if (Inventory.getCount(pair.getKey().getID()) < pair.getValue()) {
                return false;
            }
        }

        return true;
    }

    protected boolean hasTeletab() {

        return Inventory.find(getTeletabId()).length > 0;
    }

    public boolean isValid() {
        return accept() && Skills.getActualLevel(Skills.SKILLS.MAGIC) >= getMagicLevel() && (hasTeletab() || hasRunes());
    }
}
