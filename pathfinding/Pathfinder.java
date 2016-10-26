package scripts.advancedwalking.pathfinding;

import org.tribot.api.interfaces.Positionable;
import scripts.advancedwalking.game.path.Path;

/**
 * @author Laniax
 */
public interface Pathfinder {

    boolean isInitialized();

    boolean init();

    /**
     * Finds a path to the destination.
     * @param destination
     * @param useTeleports
     * @param useAgilityShortcuts
     * @return a Path object, which is empty if no path was found.
     */
    Path findPath(final Positionable destination, final boolean useTeleports, final boolean useAgilityShortcuts);


    /**
     * Finds a path from the start to the destination.
     * @param start
     * @param destination
     * @param useTeleports
     * @param useAgilityShortcuts
     * @return a Path object, which is empty if no path was found.
     */
    Path findPath(final Positionable start, final Positionable destination, final boolean useTeleports, final boolean useAgilityShortcuts);

}
