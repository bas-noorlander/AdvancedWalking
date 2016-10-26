package scripts.advancedwalking.core.types;

import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import scripts.advancedwalking.game.path.Path;
import scripts.advancedwalking.game.path.steps.TileStep;
import scripts.advancedwalking.game.teleports.Teleport;

import java.util.*;

/**
 * This is the 'client-side' fragment of a navmesh. It only contains pathfinding functions and has nothing related to generating.
 * @author Laniax
 */
public class Fragment {

    private final List<RSTile> boundaryTiles = new ArrayList<>();
    private final Set<Fragment> adjacentFragments = new LinkedHashSet<>();
    private final Set<Teleport> teleports = new HashSet<>();

    private final transient RSArea area;
    private transient int cost = Integer.MAX_VALUE;

    /**
     * Creates a new fragment of the navmesh.
     *
     * The boundaryTiles will be used to create a RSArea, so if you wrote a custom shape (instead of the Polytope), make sure they are ordered clockwise.
     *
     * @param boundaryTiles
     * @param teleports
     * @param adjacentFragments
     */
    public Fragment(RSTile[] boundaryTiles, Teleport[] teleports, Fragment[] adjacentFragments) {

        Collections.addAll(this.boundaryTiles, boundaryTiles);
        Collections.addAll(this.teleports, teleports);
        Collections.addAll(this.adjacentFragments, adjacentFragments);

        area = new RSArea(boundaryTiles);

    }

    /**
     * Return the bare minimum tiles that define the boundary.
     * @return
     */
    public List<RSTile> getBoundaryTiles() {
        return this.boundaryTiles;
    }

    /**
     * Gets all the teleports inside this shape, this will usually be a agility shortcut or stair.
     * This will never contain player teleports or item teleports. They will be calculated at runtime.
     * @return
     */
    public Set<Teleport> getTeleports() {
        return teleports;
    }


    /**
     * Clear the cost of this shape.
     */
    public void clearCost() {
        cost = Integer.MAX_VALUE;
    }

    /**
     * Returns the cost to get to this shape.
     * @return the cost, Integer.MAX_VALUE if it hasn't been calculated yet.
     */
    public int getCost() {
        return this.cost;
    }

    /**
     * Recursively set the cost of all the (adjacent) shapes until we reach the target.
     * @param target Target shape we want reach.
     * @param start Start to calculate from
     * @param cost
     */
    public void calculateCost(Positionable target, Positionable start, int cost) {

        if (cost > this.cost)
            return;

//        RSTile closestToStart = this.getClosestTile(start);
//        RSTile closestToTarget = this.getClosestTile(target);

//        int distanceToStart = closestToStart.distanceTo(start);
//        int distanceToTarget = closestToTarget.distanceTo(target);
//
//        int innerDistance = closestToStart.distanceTo(closestToTarget);
//
//        cost += (distanceToStart + distanceToTarget + innerDistance);
//        this.cost = cost;
//
//        for (Fragment adjacent : this.adjacentFragments) {
//
//            adjacent.calculateCost(target, start, cost);
//        }
    }

    /**
     * Recursively calculates the cheapest cost route from this shape to another.
     * @param target The target we want to reach
     * @param path The path to add the steps to
     * @return true if a path was found to the target, false otherwise.
     */
    public boolean calculateCheapest(Fragment target, Path path, List<Fragment> closedList) {

        // Arrived on target shape.
        if (target.equals(this))
            return true;

        // This shape is not linked to anything so we cannot travel anywhere.
        if (this.adjacentFragments.size() == 0)
            return false;

        //todo: check teleports.

        Fragment best = null;
        for (Fragment link : adjacentFragments) {
            if (closedList.contains(link))
                continue;

            if (best == null || (best.getCost() > link.getCost()))
                best = link;
        }

        if (best == null)
            return false;

        closedList.add(this);

        RSTile from = path.getLength() == 0 ? Player.getPosition() : path.getLast().getDestination();
//        path.append(new TileStep(best.getClosestTile(from))); //TODO: add more path steps

        return best.calculateCheapest(target, path, closedList);
    }

    public RSArea getArea() {
        return area;
    }

    /**
     * Checks if a tile is inside this fragment.
     * @param positionable
     * @return
     */
    public boolean contains(Positionable positionable) {
        return area.contains(positionable);
    }

//    /**
//     * Returns if this shape is allowed to be added to the navmesh.
//     * @return
//     */
//    public abstract boolean accept();
//
//    public abstract int getSize();

//    public abstract void grow(Generator generator, Set<AbstractShape> shapeList);

//    public abstract boolean contains(Positionable tile);
//
//    public abstract MeshTile getClosestTile(Positionable pos);

//    public abstract void calculatePolygon(Generator generator);
}
