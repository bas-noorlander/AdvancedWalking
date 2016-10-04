package scripts.AdvancedWalking.Generator.Tiles;

import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.Objects;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSObjectDefinition;
import org.tribot.api2007.types.RSTile;
import scripts.AdvancedWalking.Core.Mathematics.FastMath;
import scripts.AdvancedWalking.Game.World.CollisionFlags;
import scripts.AdvancedWalking.Game.World.Direction;
import scripts.AdvancedWalking.Generator.Generator;

/**
 * The MeshTile is a internal object used by the generator. Do not use directly.
 * @author Laniax
 */
public class MeshTile implements Positionable {

    public int X;
    public int Y;
    public int Plane;
    private int CollisionData;

    private boolean isBlockedNorth = false, isBlockedEast = false, isBlockedSouth = false, isBlockedWest = false;
    private boolean isBlockedNorthEast = false, isBlockedSouthEast = false, isBlockedSouthWest = false, isBlockedNorthWest = false;

    private transient RSObject[] objects;
    private transient RSTile RSTile;

    /**
     * Creates a MeshTile from a RSTile.
     * @param rstile
     */
    public MeshTile(RSTile rstile) {
        this.X = rstile.getX();
        this.Y = rstile.getY();
        this.Plane = rstile.getPlane();
        this.RSTile = rstile;
        this.CollisionData = getCollisionData();
        this.objects = Objects.getAt(rstile);

        calculateCollision();
    }

    public RSTile getAdjacentTile(Direction direction) {

        return this.RSTile.translate(direction.getOffsetX(), direction.getOffsetY());

    }

    /**
     * Checks if the given tile is adjacent to this one.
     * @param pos
     * @return
     */
    public boolean isAdjacentTile(Positionable pos) {

        return distanceToDouble(pos) <= 1.4;

    }

    public RSObject[] getObjects() {
        return this.objects;
    }

    /**
     * Gets the tile that we can grow into.
     *
     * @param generator
     * @param direction the direction we should check
     * @return the tile, or null if nothing found
     */
    public MeshTile getGrowTile(Generator generator, Direction direction) {

        final RSTile growTile = getAdjacentTile(direction);

        if (growTile == null)
            return null;

        final MeshTile result = generator.findTile(growTile);

        if (result == null)
            return null;

        if (result.isBlocked(CollisionFlags.BLOCKED))
            return null;

//        if (!PathFinding.isTileWalkable(result))
//            return null;

        if (isBlocked(direction)) {

            // The tile is blocked in this direction, however it might be because of a closed door.
            // If so, ignore the blockage
            boolean isDoor = false;

            for (RSObject object : this.objects) {
                RSObjectDefinition def = object.getDefinition();
                if (def != null) {
                    for (String action : def.getActions()) {
                        if (action.contains("Open")) {
                            isDoor = true;
                            break;
                        }
                    }
                    if (isDoor)
                        break;
                }
            }

            if (!isDoor)
                return null;
        }

        return result;
    }

    /**
     * Gets the tiles Collision Data as returned by {@link PathFinding#getCollisionData()}
     *
     * @return the flag, -1 if unsuccessful.
     */
    public int getCollisionData() {

        final RSTile t = this.RSTile.toLocalTile();

        final int[][] data = PathFinding.getCollisionData();

        int X = FastMath.minMax(t.getX(), 0, 103);
        int Y = FastMath.minMax(t.getY(), 0, 103);

        return data[X][Y];
    }

    private void calculateCollision() {
        // it's fastest to store these in a boolean

        if (isBlocked(CollisionFlags.NORTH) || isBlocked(CollisionFlags.SOLID))
            isBlockedNorth = true;

        if (isBlocked(CollisionFlags.EAST) || isBlocked(CollisionFlags.SOLID))
            isBlockedEast = true;

        if (isBlocked(CollisionFlags.SOUTH) || isBlocked(CollisionFlags.SOLID))
            isBlockedSouth = true;

        if (isBlocked(CollisionFlags.WEST) || isBlocked(CollisionFlags.SOLID))
            isBlockedWest = true;

        if (isBlocked(CollisionFlags.NORTHEAST) || isBlocked(CollisionFlags.SOLID))
            isBlockedNorthEast = true;

        if (isBlocked(CollisionFlags.SOUTHEAST) || isBlocked(CollisionFlags.SOLID))
            isBlockedSouthEast = true;

        if (isBlocked(CollisionFlags.SOUTHWEST) || isBlocked(CollisionFlags.SOLID))
            isBlockedSouthWest = true;

        if (isBlocked(CollisionFlags.NORTHWEST) || isBlocked(CollisionFlags.SOLID))
            isBlockedNorthWest = true;
    }

    /**
     * Checks if the tile is blocked in a directional bit.
     * @param bit
     * @return
     */
    public boolean isBlocked(final int bit) {

        return (this.CollisionData & bit) == bit;
    }

    /**
     * Checks if the tile is blocked a direction.
     * @param direction
     * @return
     */
    public boolean isBlocked(Direction direction) {
        switch (direction) {
            case EAST: return isBlockedEast || isBlockedNorthEast || isBlockedSouthEast;
            case NORTH: return isBlockedNorth || isBlockedNorthEast || isBlockedNorthWest;
            case SOUTH: return isBlockedSouth || isBlockedSouthEast || isBlockedSouthWest;
            case WEST: return isBlockedWest || isBlockedSouthWest || isBlockedNorthWest;
            case NORTHEAST: return isBlockedNorthEast;
            case SOUTHEAST: return isBlockedSouthEast;
            case SOUTHWEST: return isBlockedSouthWest;
            case NORTHWEST: return isBlockedNorthWest;
            default:
                return false;
        }
    }

    /**
     * A non-diagonal distanceTo
     * @param pos
     * @return
     */
    public int distanceTo(Positionable pos) {

        RSTile tile = pos.getPosition();

        if (tile != null) {
            int x = Math.abs(tile.getX() - this.X);
            int y = Math.abs(tile.getY() - this.Y);

            return x + y;
        }

        return Integer.MAX_VALUE;
    }

    /**
     * A diagonal distanceTo
     * @param pos
     * @return
     */
    public double distanceToDouble(Positionable pos) {

        RSTile tile = pos.getPosition();

        if (tile != null) {
            return tile.distanceToDouble(pos);
        }

        return Double.MIN_VALUE;
    }

    @Override
    public RSTile getPosition() {
        return this.RSTile;
    }

    @Override
    public RSTile getAnimablePosition() {
        return this.RSTile.getAnimablePosition();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Plane;
        result = prime * result + X;
        result = prime * result + Y;
        return result;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (obj instanceof MeshTile) {

            MeshTile o = (MeshTile) obj;

            return X == o.X && Y == o.Y && Plane == o.Plane;
        }

        if (obj instanceof RSTile) {

            RSTile o = (RSTile) obj;

            return X == o.getX() && Y == o.getY() && Plane == o.getPlane();
        }

        return false;
    }
}
