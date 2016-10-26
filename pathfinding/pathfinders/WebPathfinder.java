package scripts.advancedwalking.pathfinding.pathfinders;

import org.tribot.api.interfaces.Positionable;
import scripts.advancedwalking.game.path.Path;
import scripts.advancedwalking.game.path.steps.TileStep;
import scripts.advancedwalking.pathfinding.Pathfinder;

/**
 * @author Laniax
 */
public class WebPathfinder implements Pathfinder {

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
