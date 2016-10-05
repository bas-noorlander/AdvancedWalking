package scripts.AdvancedWalking.Generator.NavMesh;

import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.types.RSTile;
import scripts.AdvancedWalking.Core.Collections.Pair;
import scripts.AdvancedWalking.Core.Logging.LogProxy;
import scripts.AdvancedWalking.Game.Path.Path;
import scripts.AdvancedWalking.Game.Path.Steps.TileStep;
import scripts.AdvancedWalking.Game.World.Teleports.ItemTeleport;
import scripts.AdvancedWalking.Game.World.Teleports.MagicTeleport;
import scripts.AdvancedWalking.Game.World.Teleports.ItemTeleportManager;
import scripts.AdvancedWalking.Game.World.Teleports.SpellTeleportManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * A collection of shapes that together form a mesh.
 *
 * @author Laniax
 */
public class NavMesh implements Serializable {

    private static final long serialVersionUID = 6719558983869898358L;

    transient LogProxy log = new LogProxy("NavMesh");

    private Set<AbstractShape> shapes = new HashSet<>();

    /**
     * Create a new NavMesh object consisting of all the shapes in the list.
     *
     * @param shapes
     */
    public NavMesh(Set<AbstractShape> shapes) {
        this.shapes = shapes;
    }

    /**
     * Gets the number of shapes in the mesh.
     *
     * @return
     */
    public int getShapeCount() {
        return shapes.size();
    }

    /**
     * Gets all the shapes in the mesh.
     *
     * @return
     */
    public Set<AbstractShape> getAllShapes() {
        return shapes;
    }

    /**
     * Add a single shape to the list.
     *
     * @param shape
     * @return true if succesfully added
     */
    public boolean addShape(final AbstractShape shape) {
        return shapes.add(shape);
    }

    /**
     * Finds the shape that contains the given position.
     *
     * @param pos
     * @return the shape that contains it, or null if not found.
     */
    public AbstractShape findShape(final Positionable pos) {

        for (AbstractShape shape : shapes) {
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

        AbstractShape startShape = findShape(start);
        AbstractShape targetShape = findShape(target);

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

    private ItemTeleport processItemTeleports(AbstractShape start, Positionable origin, Positionable destination) {

        // Get the best and relevant item teleport that we can do, and include it in the cost calculations.
        Pair<ItemTeleport, Integer> itemTeleport = ItemTeleportManager.getBestTeleport(origin, destination);

        if (itemTeleport != null) {

            log.debug("The best item teleport that we are able to do is: %s", itemTeleport.getKey().name());

            RSTile destinationTile = itemTeleport.getKey().getDestinationForDialog(itemTeleport.getValue());

            if (destinationTile != null) {

                AbstractShape destinationShape = findShape(destinationTile);

                if (destinationShape != null) {
                    // finally, add the teleport to the shape we are currently on.
                    //todo: dialog option solution
                    start.addTeleport(itemTeleport.getKey());
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

    private MagicTeleport processSpellTeleports(AbstractShape start, Positionable origin, Positionable destination) {

        // Get the best and relevant spell teleport that we can do, and include it in the cost calculations.
        MagicTeleport spellTeleport = SpellTeleportManager.getBestTeleport(origin, destination);

        if (spellTeleport != null) {

            log.debug("The best spell teleport that we are able to do is: %s", spellTeleport.name());

            RSTile destinationTile = spellTeleport.getDestination();

            if (destinationTile != null) {

                AbstractShape destinationShape = findShape(destinationTile);

                if (destinationShape != null) {
                    // finally, add the teleport to the shape we are currently on.
                    //todo: runes or teletab without extra checks?
                    start.addTeleport(spellTeleport);
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

    private Path findPath(final Path path, final AbstractShape start, final AbstractShape target, final Positionable origin, final Positionable destination, final boolean useTeleports, final boolean useShortcuts) {

        // Check if we are already inside the same shape, if so, we can walk without obstacles.
        if (start.equals(target)) {
            RSTile tar = destination.getPosition();
            if (tar != null) {
                path.append(new TileStep(tar));
            }
            return path;
        }

        for (AbstractShape shape : shapes) {
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

        if (item != null)
            start.removeTeleport(item);

        if (spell != null)
            start.removeTeleport(spell);

        return path;
    }
}
