package scripts.AdvancedWalking.Game.World.Teleports;

import org.tribot.api2007.Constants;
import org.tribot.api2007.types.RSTile;
import scripts.AdvancedWalking.Core.Collections.Pair;
import scripts.AdvancedWalking.Game.Items.Runes.Rune;
import scripts.AdvancedWalking.Game.Items.Runes.RuneSet;

/**
 * Contains all spell-based teleports.
 *
 * @author Laniax
 */
public abstract class AbstractMagicTeleport implements ITeleport {

    public abstract RuneSet getRunes();

    public abstract RSTile getDestination();

    public abstract int getTeletabId();

    public abstract int getSpellId();

    public abstract int getMagicLevel();

    public abstract boolean accept();
}
