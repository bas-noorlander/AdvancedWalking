package scripts.AdvancedWalking.Game.Path.Steps;

import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.types.RSTile;
import scripts.AdvancedWalking.Core.Logging.LogProxy;
import scripts.AdvancedWalking.Game.Path.AbstractStep;
import scripts.AdvancedWalking.Game.World.Teleports.ITeleport;

/**
 * A ShortcutStep is a agility shortcut in a path.
 *
 * @author Laniax
 */
public class ShortcutStep extends AbstractStep {

    LogProxy log = new LogProxy("ShortcutStep");

    private RSTile _destination;

    private RSTile _shortcutPos;

    public ShortcutStep(Positionable destination, Positionable objPosition) {
        this._destination = destination.getPosition();
        this._shortcutPos = objPosition.getPosition();
    }

    @Override
    protected RSTile destination() {
        return this._destination;
    }

    @Override
    protected ITeleport getTeleport() {
        return null;
    }

    @Override
    protected boolean run() {
        return false;
    }
}
