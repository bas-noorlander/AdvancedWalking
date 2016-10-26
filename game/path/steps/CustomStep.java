package scripts.advancedwalking.game.path.steps;

import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.types.RSTile;
import scripts.advancedwalking.game.path.PathStep;
import scripts.advancedwalking.game.teleports.Teleport;

/**
 * A CustomStep is a special-case in a path. Think a boat ride or something similar.
 *
 * @author Laniax
 */
public class CustomStep implements PathStep {

    private RSTile _destination;

    public CustomStep(Positionable destination) {
        this._destination = destination.getPosition();
    }

    /**
     * The step's fina destination.
     *
     * @return
     */
    @Override
    public RSTile destination() {
        return this._destination;
    }

    @Override
    public Teleport getTeleport() {
        return null;
    }

    @Override
    public boolean run() {
        return false;
    }
}
