package scripts.AdvancedWalking.Pathfinding;

import org.tribot.api.interfaces.Positionable;
import scripts.AdvancedWalking.Game.Path.Path;

/**
 * @author Laniax
 */
public interface IPathfinder {

    boolean isInitialized();

    boolean init();

    /**
     * Finds a path to the destination.
     * @param destination
     * @return a Path object, which is empty if no path was found.
     */
    Path findPath(final Positionable destination);


    /**
     * Finds a path from the start to the destination.
     * @param start
     * @param destination
     * @return a Path object, which is empty if no path was found.
     */
    Path findPath(final Positionable start, final Positionable destination);

}
