package scripts.AdvancedWalking.Generator.NavMesh;

import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.types.RSTile;
import scripts.AdvancedWalking.Core.Logging.LogProxy;
import scripts.AdvancedWalking.Game.Path.Path;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * A collection of shapes that together form a mesh.
 * @author Laniax
 */
public class NavMesh implements Serializable {

    private static final long serialVersionUID = 6719558983869898358L;

    transient LogProxy log = new LogProxy("NavMesh");

    private Set<AbstractShape> shapes = new HashSet<>();

    /**
     * Create a new NavMesh object consisting of all the shapes in the list.
     * @param shapes
     */
    public NavMesh(Set<AbstractShape> shapes) {
        this.shapes = shapes;
    }

    /**
     * Gets the number of shapes in the mesh.
     * @return
     */
    public int getShapeCount() {
        return shapes.size();
    }

    /**
     * Gets all the shapes in the mesh.
     * @return
     */
    public Set<AbstractShape> getAllShapes() {
        return shapes;
    }

    /**
     * Add a single shape to the list.
     * @param shape
     * @return true if succesfully added
     */
    public boolean addShape(final AbstractShape shape) {
        return shapes.add(shape);
    }

    /**
     * Finds the shape that contains the given position.
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
     * @param start
     * @param target
     * @return a path object, empty if no path was found.
     */
    public Path findPath(final Positionable start, final Positionable target) {

        return findPath(start, target, true, true);

    }

    /**
     * Returns a path from the start to the target positions.
     * @param start
     * @param target
     * @param useTeleports
     * @param useShortcuts
     * @return a path object, empty if no path was found.
     */
    public Path findPath(final Positionable start, final Positionable target, final boolean useTeleports, final boolean useShortcuts) {

        Path path = new Path();

        if (start == null || target == null) {
            log.info("Cannot generate a path, start or target positions are null.");
            return path;
        }

        AbstractShape startShape = findShape(start);
        AbstractShape targetShape = findShape(target);

        if (startShape == null) {
            log.info("Sorry! You are trying to walk FROM a position not yet mapped with a navmesh generator.");
            return path;
        }

        if (targetShape == null) {
            log.info("Sorry! You are trying to walk TO a position not yet mapped with a navmesh generator.");
            return path;
        }

        return findPath(path, startShape, targetShape, start, target, useTeleports, useShortcuts);

    }

    private Path findPath(final Path path, final AbstractShape start, final AbstractShape target, final Positionable origin, final Positionable destination, final boolean useTeleports, final boolean useShortcuts ) {

        // Check if we are already inside the same shape, if so, we can walk without obstacles.
        if (start.equals(target)) {
            RSTile tar = destination.getPosition();
            if (tar != null) {
                path.appendStep(tar);
            }
            return path;
        }

        //todo: check teleports

        for (AbstractShape shape : shapes) {
            shape.clearCost();
        }

        target.calculateCost(origin, destination, 0);

        if (start.getCost() == Integer.MAX_VALUE) {
            log.info("Cannot generate path, target shape wasn't reached with cost calculations.");
            return path;
        }

        if (target.getCost() == Integer.MAX_VALUE) {
            log.info("Cannot generate path, start shape wasn't reached with cost calculations.");
            return path;
        }

        if (start.calculateCheapest(target, path, new ArrayList<>())) {

            // found a path!

            // append destination to result.
            RSTile tar = destination.getPosition();
            if (tar != null) {
                path.appendStep(tar);
            }

            return path;

        }

        log.info("No path was found..");

        return path;
    }
}
