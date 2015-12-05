package scripts.AdvancedWalking;

import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.WebWalking;
import scripts.AdvancedWalking.Core.Events.EventManager;
import scripts.AdvancedWalking.Core.Logging.LogProxy;
import scripts.AdvancedWalking.Game.Path.Path;
import scripts.AdvancedWalking.Pathfinding.IPathfinder;
import scripts.AdvancedWalking.Pathfinding.Pathfinders.AdvancedPathfinder;
import scripts.AdvancedWalking.Pathfinding.Pathfinders.WebPathfinder;
import scripts.AdvancedWalking.Walking.IWalker;
import scripts.AdvancedWalking.Walking.Walkers.AdvancedWalker;
import scripts.AdvancedWalking.Walking.Walkers.WebWalker;

/**
 * @author Laniax
 */
public final class AdvancedWalking {

    private static LogProxy log = new LogProxy("AdvancedWalking");

    private static boolean _useTeleports = true;
    private static boolean _useAgilityShortcuts = true;
    private static boolean _useWebWalkingFallback = true;

    private static IPathfinder _pathfinder = new AdvancedPathfinder();
    private static IWalker _walker = new AdvancedWalker();

    /**
     * Prevent instantiation
     */
    private AdvancedWalking() {}

    /**
     * Change the pathfinder, this allows you to switch between pathfinding algorithms during runtime.
     * @param pathfinder
     */
    public static void setPathfinder(IPathfinder pathfinder) {
        _pathfinder = pathfinder;

        _pathfinder.init();
    }

    /**
     * Change the walker, this allows you to switch between walking methods during runtime.
     * @param walker
     */
    public static void setWalker(IWalker walker) {
        _walker = walker;
    }

    /**
     * Gets if we should use teleports or not.
     *
     * @return True if we should use teleports, false if not.
     */
    public static boolean useTeleports() {
        return _useTeleports;
    }

    /**
     * Sets if we should use teleports or not.
     *
     * @param value
     */
    public static void useTeleports(final boolean value) {
        _useTeleports = value;
    }

    /**
     * Gets if we should fall back to {@link WebWalking} if {@link AdvancedWalking} fails to {@link #initialize()}.
     *
     * @return True if we use the fallback, false if not.
     */
    public static boolean useWebWalkingFallback() {
        return _useWebWalkingFallback;
    }

    /**
     * Sets if we should fall back to {@link WebWalking} if {@link AdvancedWalking} fails to {@link #initialize()}.
     *
     * @param value
     */
    public static void useWebWalkingFallback(final boolean value) {
        _useWebWalkingFallback = value;
    }

    /**
     * Gets if we should use agility shortcuts or not.
     *
     * @return True if we should use agility shortcuts, false if not.
     */
    public static boolean useAgilityShortcuts() {
        return _useAgilityShortcuts;
    }

    /**
     * Sets if we should use agility shortcuts or not.
     *
     * @param value
     */
    public static void useAgilityShortcuts(final boolean value) {
        _useAgilityShortcuts = value;
    }


    /**
     * Find a path from the players' position to the destination
     * Will search for teleports or agility shortcuts.
     *
     * @param destination
     * @return a {@link Path} object. Empty if no path was found.
     */
    public static Path findPath(final Positionable destination) {

        if (!_pathfinder.isInitialized())
            initialize();

        Path path = _pathfinder.findPath(destination, true, true);

        EventManager.triggerPathFound(path);

        return path;
    }

    /**
     * Find a path to the destination
     *
     * @param destination
     * @param useTeleports
     * @param useAgilityShortcuts
     * @return a {@link Path} object. Empty if no path was found.
     */
    public static Path findPath(final Positionable destination, final boolean useTeleports, final boolean useAgilityShortcuts) {

        if (!_pathfinder.isInitialized())
            initialize();

        Path path = _pathfinder.findPath(destination, useTeleports, useAgilityShortcuts);

        EventManager.triggerPathFound(path);

        return path;
    }

    /**
     * Find a path from the start to the destination
     * Will search for teleports or agility shortcuts.
     *
     * @param start
     * @param destination
     * @return a {@link Path} object. Empty if no path was found.
     */
    public static Path findPath(final Positionable start, final Positionable destination) {

        if (!_pathfinder.isInitialized())
            initialize();

        Path path = _pathfinder.findPath(start, destination, true, true);

        EventManager.triggerPathFound(path);

        return path;
    }

    /**
     * Find a path from the start to the destination
     *
     * @param start
     * @param destination
     * @param useTeleports
     * @param useAgilityShortcuts
     * @return a {@link Path} object. Empty if no path was found.
     */
    public static Path findPath(final Positionable start, final Positionable destination, final boolean useTeleports, final boolean useAgilityShortcuts) {

        if (!_pathfinder.isInitialized())
            initialize();

        Path path = _pathfinder.findPath(start, destination, useTeleports, useAgilityShortcuts);

        EventManager.triggerPathFound(path);

        return path;
    }

    /**
     * Allows you to prepare AdvancedWalking when you like, instead of the first time when a walking/pathfinding method is called.
     */
    public static void prepare() {
        if (!_pathfinder.isInitialized())
            initialize();
    }

    /**
     * Walk to the destination without using teleports or agility shortcuts.
     * This method reverts to {@link WebWalking} if {@link AdvancedWalking} failed to {@link #initialize()}
     * you can change this behaviour using {@link #useWebWalkingFallback}
     *
     * @param destination
     * @return true if successfully reached the destination, false otherwise.
     */
    public static boolean walk(final Positionable destination) {

        boolean oldAgilityShortcuts = useAgilityShortcuts();
        boolean oldTeleports = useTeleports();

        useAgilityShortcuts(false);
        useTeleports(false);

        boolean result = travel(findPath(destination));

        useAgilityShortcuts(oldAgilityShortcuts);
        useTeleports(oldTeleports);

        return result;
    }

    /**
     * Travels to the destination, using teleports or agility shortcuts if available
     * This method reverts to {@link WebWalking} if {@link AdvancedWalking} failed to {@link #initialize()}
     * you can change this behaviour using {@link #useWebWalkingFallback}
     *
     * @param destination
     * @return true if successfully reached the destination, false otherwise.
     */
    public static boolean travel(final Positionable destination) {

        return travel(findPath(destination));
    }

    /**
     * Travels the {@link Path}
     * This method reverts to {@link WebWalking} if {@link AdvancedWalking} failed to {@link #initialize()}
     * you can change this behaviour using {@link #useWebWalkingFallback}
     *
     * @param p - the path to walk
     * @return true if successfully reached the destination, false otherwise.
     */
    public static boolean travel(final Path p) {

        if (p.getLength() == 0)
            return false;

        return _walker.walk(p);
    }

    private static void initialize() {

        log.info("Initializing");

        if (_pathfinder.init()) {

            log.info("Pathfinder successfully initialized!");

        } else if (useWebWalkingFallback()) {

            log.warn("Pathfinder failed to initialize, we will use WebWalking instead.");

            // these don't need initialization.
            setPathfinder(new WebPathfinder());
            setWalker(new WebWalker());

        } else {
            log.error("Pathfinder failed to initialize and the scripter disabled the fallback to WebWalking. We can not continue.");
            EventManager.triggerInitializationError();
        }
    }
}