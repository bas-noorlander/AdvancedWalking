package scripts.AdvancedWalking.Game.Path.Steps;

import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.types.RSTile;
import scripts.AdvancedWalking.Core.Logging.LogProxy;
import scripts.AdvancedWalking.Game.Path.AbstractStep;
import scripts.AdvancedWalking.Game.World.Teleports.Teleports.ITeleport;

/**
 * A CustomStep is a special-case in a path. Think a boat ride or something similar.
 *
 * @author Laniax
 */
public class CustomStep extends AbstractStep {

    LogProxy log = new LogProxy("CustomStep");

    private RSTile _destination;

    public CustomStep(Positionable destination) {
        this._destination = destination.getPosition();
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
