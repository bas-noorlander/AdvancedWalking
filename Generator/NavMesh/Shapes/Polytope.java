package scripts.AdvancedWalking.Generator.NavMesh.Shapes;

import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.types.RSTile;
import scripts.AdvancedWalking.Game.World.Direction;
import scripts.AdvancedWalking.Game.World.RSPolygon;
import scripts.AdvancedWalking.Generator.Generator;
import scripts.AdvancedWalking.Generator.NavMesh.AbstractShape;
import scripts.AdvancedWalking.Generator.NavMesh.Algorithms.BoundaryFloodFill;
import scripts.AdvancedWalking.Generator.NavMesh.Algorithms.BoundarySort;
import scripts.AdvancedWalking.Generator.Tiles.MeshTile;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Laniax
 */
public class Polytope extends AbstractShape {

    private MeshTile startTile;

    public Polytope(MeshTile tile) {

        this.startTile = tile;

        addTile(tile);
    }

    @Override
    public boolean accept() {
        // don't add polys that are only 1x1
        return getSize() > 1;
    }

    @Override
    public int getSize() {
        return getTileCount();
    }

    private void growTile(MeshTile tile, Generator generator, Set<AbstractShape> shapeList) {

        for (Direction cardinal : Direction.getAllCardinal()) {

            final MeshTile growTile = tile.getGrowTile(generator, cardinal);

            if (growTile != null) {

                // Is not a walkable/valid tile.
                if (!generator.validTiles.contains(growTile))
                    continue;

                // Already in other polygon
                if (Generator.isInShape(shapeList, growTile) != null)
                    continue;

                // Already inside this polygon
                if (this.contains(growTile))
                    continue;

                this.addTile(growTile);

                if (!isAllowedSize()) {
                    this.removeTile(growTile);
                }
            }
        }
    }

    private boolean isAllowedSize() {

        if (this.getTileCount() < 20)
            return true;

        int lowestX = Integer.MAX_VALUE;
        int lowestY = Integer.MAX_VALUE;

        int highestX = Integer.MIN_VALUE;
        int highestY = Integer.MIN_VALUE;

        for (MeshTile tile : this.getAllTiles()) {

            if (tile.X < lowestX)
                lowestX = tile.X;
            else if (tile.X > highestX)
                highestX = tile.X;

            if (tile.Y < lowestY)
                lowestY = tile.Y;
            else if (tile.Y > highestY)
                highestY = tile.Y;
        }

        return (highestX - lowestX) < 20 && (highestY - lowestY) < 20;
    }

    @Override
    public void grow(Generator generator, Set<AbstractShape> shapeList) {

        int preSize = 1;

        growTile(this.startTile, generator, shapeList);

        int postSize = this.getTileCount();

        // We keep growing until we dont change size anymore..
        while (preSize != postSize) {

            preSize = this.getTileCount();

            List<MeshTile> polyTiles = this.getAllTiles();
            for (int i = 0; i < polyTiles.size(); i++) {
                MeshTile t = polyTiles.get(i);
                growTile(t, generator, shapeList);
            }

            postSize = this.getTileCount();
        }
    }

    private boolean isPointInPoly(Positionable pos) {
        RSTile test = pos.getPosition();
        int X = test.getX();
        int Y = test.getY();
        List<MeshTile> points = getBoundaryTiles();
        int i;
        int j;
        boolean result = false;
        for (i = 0, j = points.size() - 1; i < points.size(); j = i++) {
            if ((points.get(i).Y > Y) != (points.get(j).Y > Y) &&
                    (X < (points.get(j).X - points.get(i).X) * (Y - points.get(i).Y) / (points.get(j).Y - points.get(i).Y) + points.get(i).X)) {
                result = !result;
            }
        }
        return result;
    }

    @Override
    public boolean contains(Positionable tile) {
        if (getBoundaryTiles().size() > 0) {
            return isPointInPoly(tile);
        }

        //todo: new polygon approach
        if (tile instanceof MeshTile)
            return getAllTiles().contains(tile);
        else {
            return getAllTiles().contains(new MeshTile(tile.getPosition()));
        }
    }

    @Override
    public MeshTile getClosestTile(Positionable pos) {

        RSTile tile = pos.getPosition();

        if (tile == null)
            return null;

        MeshTile res = null;

        for (MeshTile t : getAllTiles()) {

            if (res == null)
                res = t;

            if (res.distanceTo(pos) >= t.distanceTo(pos))
                res = t;
        }

        return res;
    }

    private List<MeshTile> removeUnneededBoundaryTiles(List<MeshTile> boundaryTiles) {

        MeshTile previous = null;
        List<MeshTile> removeableTiles = new ArrayList<>();

        List<MeshTile> verticals = new ArrayList<>();
        List<MeshTile> horizontals = new ArrayList<>();

        for (MeshTile t : boundaryTiles) {

            if (previous != null) {

                if (t.X == previous.X) {
                    horizontals.add(t);
                } else {
                    horizontals.clear();
                }

                if (t.Y == previous.Y) {
                    verticals.add(t);
                } else {
                    verticals.clear();
                }
            }

            if (verticals.size() > 2) {
                removeableTiles.addAll(verticals);
                removeableTiles.remove(verticals.get(0));
                removeableTiles.remove(verticals.get(verticals.size() -1));
            } else if (horizontals.size() > 2) {
                removeableTiles.addAll(horizontals);
                removeableTiles.remove(horizontals.get(0));
                removeableTiles.remove(horizontals.get(horizontals.size() -1));
            }

            previous = t;
        }

        boundaryTiles.removeAll(removeableTiles);
        return boundaryTiles;
    }

    @Override
    public void calculatePolygon(Generator generator) {

        // get(0) is null safe here, since it is called after accept()
        RSPolygon result = new RSPolygon(getAllTiles().get(0).Plane);

        List<MeshTile> boundaryTiles = BoundaryFloodFill.run(this.getAllTiles(), generator);

        if (boundaryTiles.size() > 0) {

            // Even though we have the boundary tiles now, we should remove points that are not necessary..
            // For that, we first have to sort them.

            // Sorting the tiles not only allows for easier painting,
            // it is also required for the point in poly checks and general performance increases.
            boundaryTiles = BoundarySort.run(boundaryTiles, generator);

            // Now we can remove unneeded points.
            boundaryTiles = removeUnneededBoundaryTiles(boundaryTiles);

            // and finally create the polygon.
            for (MeshTile t : boundaryTiles) {
                result.addPoint(t.X, t.Y);
            }
        }

        setBoundaryTiles(boundaryTiles);
        setPolygon(result);
    }

    @Override
    public int hashCode() {
        return startTile.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (obj instanceof Polytope) {

            Polytope o = (Polytope) obj;

            return getAllTiles().equals(o.getAllTiles());
        }

        return false;
    }
}
