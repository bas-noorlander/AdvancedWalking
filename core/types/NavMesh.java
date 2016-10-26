package scripts.advancedwalking.core.types;

import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.types.RSTile;
import scripts.advancedwalking.core.collections.Pair;
import scripts.advancedwalking.core.logging.LogProxy;
import scripts.advancedwalking.game.path.Path;
import scripts.advancedwalking.game.path.steps.TileStep;
import scripts.advancedwalking.game.teleports.ItemTeleport;
import scripts.advancedwalking.game.teleports.ItemTeleportManager;
import scripts.advancedwalking.game.teleports.MagicTeleport;
import scripts.advancedwalking.game.teleports.SpellTeleportManager;

import java.util.ArrayList;
import java.util.Set;

/**
 * This is the 'client-side' navmesh. It only contains pathfinding functions and has nothing related to generating.
 * @author Laniax
 */
public class NavMesh {

    private transient LogProxy log = new LogProxy("NavMesh");

    private final Set<Fragment> shapes;

    /**
     * Create a new NavMesh object consisting of all the shapes in the list.
     *
     * @param shapes
     */
    public NavMesh(Set<Fragment> shapes) {
        this.shapes = shapes;
    }

    /**
     * Finds the shape that contains the given position.
     *
     * @param pos
     * @return the shape that contains it, or null if not found.
     */
    public Fragment findShape(final Positionable pos) {

        for (Fragment shape : shapes) {
            if (shape.contains(pos))
                return shape;
        }

        return null;
    }

    /**
     * Returns a path from the start to the target positions.
     *
     * @param start
     * @param target
     * @return a path object, empty if no path was found.
     */
    public Path findPath(final Positionable start, final Positionable target) {

        return findPath(start, target, true, true);

    }

    /**
     * Returns a path from the start to the target positions.
     *
     * @param start
     * @param target
     * @param useTeleports
     * @param useShortcuts
     * @return a path object, empty if no path was found.
     */
    public Path findPath(final Positionable start, final Positionable target, final boolean useTeleports, final boolean useShortcuts) {

        Path path = new Path();

        if (start == null || target == null) {
            log.error("Cannot generate a path, start or target positions are null.");
            return path;
        }

        Fragment startShape = findShape(start);
        Fragment targetShape = findShape(target);

        if (startShape == null) {
            log.error("Sorry! You are trying to walk FROM a position not yet mapped with a navmesh generator.");
            return path;
        }

        if (targetShape == null) {
            log.error("Sorry! You are trying to walk TO a position not yet mapped with a navmesh generator.");
            return path;
        }

        return findPath(path, startShape, targetShape, start, target, useTeleports, useShortcuts);

    }

    private ItemTeleport processItemTeleports(Fragment start, Positionable origin, Positionable destination) {

        // Get the best and relevant item teleport that we can do, and include it in the cost calculations.
        Pair<ItemTeleport, Integer> itemTeleport = ItemTeleportManager.getBestTeleport(origin, destination);

        if (itemTeleport != null) {

            log.debug("The best item teleport that we are able to do is: %s", itemTeleport.getKey().name());

            RSTile destinationTile = itemTeleport.getKey().getDestinationForDialog(itemTeleport.getValue());

            if (destinationTile != null) {

                Fragment destinationShape = findShape(destinationTile);

                if (destinationShape != null) {
                    // finally, add the teleport to the shape we are currently on.
                    //todo: dialog option solution
                    //start.addTeleport(itemTeleport.getKey()); todo: fix this
                    return itemTeleport.getKey();
                } else {
                    // Honestly, this is something we can avoid, simply dont script teleports if the mesh doesn't have that area data.
                    // Thats why i wont write a failsafe to use the 2nd best teleport etc.
                    log.error("Wanted to use %d, but the destination area of the teleport is not mapped yet.", itemTeleport.getKey().name());
                }
            }
        }
        return null;
    }

    private MagicTeleport processSpellTeleports(Fragment start, Positionable origin, Positionable destination) {

        // Get the best and relevant spell teleport that we can do, and include it in the cost calculations.
        MagicTeleport spellTeleport = SpellTeleportManager.getBestTeleport(origin, destination);

        if (spellTeleport != null) {

            log.debug("The best spell teleport that we are able to do is: %s", spellTeleport.name());

            RSTile destinationTile = spellTeleport.getDestination();

            if (destinationTile != null) {

                Fragment destinationShape = findShape(destinationTile);

                if (destinationShape != null) {
                    // finally, add the teleport to the shape we are currently on.
                    //todo: runes or teletab without extra checks?
                 //   start.addTeleport(spellTeleport); todo: fix this
                    return spellTeleport;
                } else {
                    // Honestly, this is something we can avoid, simply dont script teleports if the mesh doesn't have that area data.
                    // Thats why i wont write a failsafe to use the 2nd best teleport etc.
                    log.error("Wanted to use %d, but the destination area of the teleport is not mapped yet.", spellTeleport.name());
                }
            }
        }

        return null;
    }

    private Path findPath(final Path path, final Fragment start, final Fragment target, final Positionable origin, final Positionable destination, final boolean useTeleports, final boolean useShortcuts) {

        // Check if we are already inside the same shape, if so, we can walk without obstacles.
        if (start.equals(target)) {
            RSTile tar = destination.getPosition();
            if (tar != null) {
                path.append(new TileStep(tar));
            }
            return path;
        }

        for (Fragment shape : shapes) {
            shape.clearCost();
        }

        boolean failed = false;

        ItemTeleport item = null;
        MagicTeleport spell = null;

        if (useTeleports) {
            item = processItemTeleports(start, origin, destination);
            spell = processSpellTeleports(start, origin, destination);
        }

        target.calculateCost(origin, destination, 0);

        if (start.getCost() == Integer.MAX_VALUE) {
            log.error("Cannot generate path, target shape wasn't reached with cost calculations.");
            failed = true;
        }

        if (!failed && target.getCost() == Integer.MAX_VALUE) {
            log.error("Cannot generate path, start shape wasn't reached with cost calculations.");
            failed = true;
        }

        if (!failed && start.calculateCheapest(target, path, new ArrayList<>())) {

            // found a path!

            // append destination to result.
            RSTile tar = destination.getPosition();
            if (tar != null) {
                path.append(new TileStep(tar));
            }
        } else {
            log.warn("No path was found..");
        }

//        if (item != null) todo: fix this
//            start.removeTeleport(item);
//
//        if (spell != null)
//            start.removeTeleport(spell);

        return path;
    }
}
