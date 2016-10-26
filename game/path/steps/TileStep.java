package scripts.advancedwalking.game.path.steps;

import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.types.RSTile;
import scripts.advancedwalking.game.path.PathStep;
import scripts.advancedwalking.game.teleports.Teleport;

/**
 * A TileStep is a 'simple' step inside a path. We can simply walk there.
 * @author Laniax
 */
public class TileStep implements PathStep {

    private RSTile _destination;

    public TileStep(Positionable destination) {
        this._destination = destination.getPosition();
    }

    @Override
    public RSTile destination() {
        return _destination;
    }

    @Override
    public Teleport getTeleport() {
        return null;
    }

    @Override
    public boolean run() {
        return true;
    }
}
