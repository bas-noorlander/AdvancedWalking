package scripts.AdvancedWalking.Game.World.Teleports;

import org.tribot.api2007.Inventory;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSTile;
import scripts.AdvancedWalking.Core.Collections.Pair;
import scripts.AdvancedWalking.Game.Items.Runes.Rune;
import scripts.AdvancedWalking.Game.Items.Runes.RuneSet;

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
