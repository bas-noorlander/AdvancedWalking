package scripts.AdvancedWalking.Game.Path.Steps;

import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.types.RSTile;
import scripts.AdvancedWalking.Game.Path.AbstractStep;
import scripts.AdvancedWalking.Game.World.Teleports.Teleports.ITeleport;

/**
 * A TileStep is a 'simple' step inside a path. We can simply walk there.
 * @author Laniax
 */
public class TileStep extends AbstractStep {

    private RSTile _destination;

    public TileStep(Positionable destination) {
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
        return true;
    }
}
