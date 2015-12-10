package scripts.AdvancedWalking.Pathfinding.Pathfinders;

import org.tribot.api.interfaces.Positionable;
import scripts.AdvancedWalking.Game.Path.Path;
import scripts.AdvancedWalking.Game.Path.Steps.TileStep;
import scripts.AdvancedWalking.Pathfinding.IPathfinder;

/**
 * @author Laniax
 */
public class WebPathfinder implements IPathfinder {

    @Override
    public boolean isInitialized() {
        return true;
    }

    /** WebWalking does not support pathfinding, so we return a path with only the destination **/

    @Override
    public boolean init() {
        return true;
    }

    @Override
    public Path findPath(Positionable destination, boolean useTeleports, boolean useAgilityShortcuts) {
        Path path = new Path();
        path.append(new TileStep(destination.getPosition()));
        return path;
    }

    @Override
    public Path findPath(Positionable start, Positionable destination, boolean useTeleports, boolean useAgilityShortcuts) {
        Path path = new Path();
        path.append(new TileStep(destination.getPosition()));
        return path;
    }
}
