package scripts.AdvancedWalking.Generator.NavMesh;

import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSTile;
import scripts.AdvancedWalking.Game.Path.Path;
import scripts.AdvancedWalking.Game.Path.Steps.TileStep;
import scripts.AdvancedWalking.Game.World.RSPolygon;
import scripts.AdvancedWalking.Generator.Generator;
import scripts.AdvancedWalking.Generator.Tiles.MeshTile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a fragment of the {@link NavMesh}, this could be a polygon, rectangle or anything you can set your mind to :-)
 *
 * @author Laniax
 */
public abstract class AbstractShape implements Serializable {

    private Set<AbstractShape> adjacentShapes = new LinkedHashSet<>();

    private RSPolygon polygon;

    private transient List<MeshTile> shapeTiles = new ArrayList<>();
    private transient List<MeshTile> boundaryTiles = new ArrayList<>();

    private transient int cost = Integer.MAX_VALUE;

    public void setBoundaryTiles(List<MeshTile> boundaryTiles) {
        this.boundaryTiles = boundaryTiles;
    }
    public List<MeshTile> getBoundaryTiles() {
        return this.boundaryTiles;
    }

    /**
     * Gets the polygon.
     * Make sure it has been calculated first.
     * @return
     */
    public RSPolygon getPolygon() {
        return polygon;
    }

    /**
     * Sets the polygon.
     */
    public void setPolygon(RSPolygon polygon) {
        this.polygon = polygon;
    }

    /**
     * Returns how many tiles are in this shape.
     * @return
     */
    public int getTileCount() {
        return shapeTiles.size();
    }

    /**
     * Get all the {@link MeshTile}'s that are inside this shape.
     * @return
     */
    public List<MeshTile> getAllTiles() {
        return shapeTiles;
    }

    /**
     * Adds a tile to this shape.
     * @return
     */
    public boolean addTile(MeshTile tile) {
        return shapeTiles.add(tile);
    }

    /**
     * Removes a tile from this shape.
     * @return
     */
    public boolean removeTile(MeshTile tile) {
        return shapeTiles.remove(tile);
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
     * Add another shape to indicate that this shape lies adjacent to it.
     * ie. the player is able to navigate between these two shapes.
     * @param shape
     * @return
     */
    public boolean addAdjacent(AbstractShape shape) {
        return adjacentShapes.add(shape);
    }

    /**
     * Returns how many adjacent shapes this shape is connected to.
     * @return
     */
    public int getAdjacentCount() {
        return adjacentShapes.size();
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

        MeshTile closestToStart = this.getClosestTile(start);
        MeshTile closestToTarget = this.getClosestTile(target);

        int distanceToStart = closestToStart.distanceTo(start);
        int distanceToTarget = closestToTarget.distanceTo(target);

        int innerDistance = closestToStart.distanceTo(closestToTarget);

        cost += (distanceToStart + distanceToTarget + innerDistance);
        this.cost = cost;

        for (AbstractShape adjacent : this.adjacentShapes) {

            adjacent.calculateCost(target, start, cost);
        }
    }

    /**
     * Recursively calculates the cheapest cost route from this shape to another.
     * @param target The target we want to reach
     * @param path The path to add the steps to
     * @return true if a path was found to the target, false otherwise.
     */
    public boolean calculateCheapest(AbstractShape target, Path path, List<AbstractShape> closedList) {

        // Arrived on target shape.
        if (target.equals(this))
            return true;

        // This shape is not linked to anything so we cannot travel anywhere.
        if (getAdjacentCount() == 0)
            return false;

        AbstractShape best = null;
        for (AbstractShape link : adjacentShapes) {
            if (closedList.contains(link))
                continue;

            if (best == null || (best.getCost() > link.getCost()))
                best = link;
        }

        if (best == null)
            return false;

        closedList.add(this);

        RSTile from = path.getLength() == 0 ? Player.getPosition() : path.getLast().getDestination();
        path.append(new TileStep(best.getClosestTile(from))); //TODO: add more path steps

        return best.calculateCheapest(target, path, closedList);
    }

    /**
     * Returns if this shape is allowed to be added to the navmesh.
     * @return
     */
    public abstract boolean accept();

    public abstract int getSize();

    public abstract void grow(Generator generator, Set<AbstractShape> shapeList);

    public abstract boolean contains(Positionable tile);

    public abstract MeshTile getClosestTile(Positionable pos);

    public abstract void calculatePolygon(Generator generator);

}
