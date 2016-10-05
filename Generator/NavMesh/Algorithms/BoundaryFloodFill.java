package scripts.AdvancedWalking.Generator.NavMesh.Algorithms;

import org.tribot.api2007.PathFinding;
import org.tribot.api2007.types.RSTile;
import scripts.AdvancedWalking.Game.World.Direction;
import scripts.AdvancedWalking.Generator.Generator;
import scripts.AdvancedWalking.Generator.Tiles.MeshTile;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Laniax
 */
public class BoundaryFloodFill {

    public static List<MeshTile> run(List<MeshTile> tiles, Generator generator) {

        List<MeshTile> result = new ArrayList<>();

        if (tiles.size() < 1)
            return result;

        for (MeshTile tile : tiles) {

            boolean isBoundary = false;

            for (Direction direction : Direction.getAll()) {

                RSTile adjTile = tile.getAdjacentTile(direction);

                MeshTile chkTile;
                if ((chkTile = generator.findTile(adjTile)) == null)
                    chkTile = new MeshTile(adjTile);

                if (!tiles.contains(chkTile)) {
                    // this adjacent is NOT in our shape, so the current tile is a boundary tile.
                    // But we check for 1x1 holes first.
                    boolean allInShape = true;
                    // Check if its a single hole.
                    if (!PathFinding.isTileWalkable(chkTile)) {

                        for (Direction dir : Direction.getAllCardinal()) {

                            RSTile adjTile2 = chkTile.getAdjacentTile(dir);

                            MeshTile chkTile2;
                            if ((chkTile2 = generator.findTile(adjTile2)) == null)
                                chkTile2 = new MeshTile(adjTile2);

                            if (!tiles.contains(chkTile2)) {
                                allInShape = false;
                                break;
                            }
                        }
                    } else {
                        allInShape = false;
                    }

                    if (!allInShape) {
                        isBoundary = true;
                        break;
                    }
                }
            }

            if (isBoundary) {
                result.add(tile);
            }

        }

        return result;
    }
}
