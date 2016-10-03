package scripts.AdvancedWalking.Generator.NavMesh.Algorithms;

import org.tribot.api2007.PathFinding;
import org.tribot.api2007.types.RSTile;
import scripts.AdvancedWalking.Game.World.Direction;
import scripts.AdvancedWalking.Generator.Generator;
import scripts.AdvancedWalking.Generator.Tiles.MeshTile;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author Laniax
 */
public class BoundaryFloodFill {

    private static List<MeshTile> closedList = new ArrayList<>();
    private static Stack<MeshTile> tileStack = new Stack<>();

    public static List<MeshTile> run(List<MeshTile> tiles, Generator generator) {

        tileStack.clear();
        closedList.clear();

        List<MeshTile> result = new ArrayList<>();

        if (tiles.size() < 1)
            return result;

        // Add all the tiles from this shape to the list.
        tileStack.addAll(tiles);

        while (!tileStack.isEmpty()) {

            MeshTile curTile = tileStack.pop();

            if (closedList.contains(curTile))
                continue;

            // we will check every direction if the tile is still in our shape, if not, we add this tile as a boundary tile.
            boolean isBoundary = false;

            for (Direction direction : Direction.getAllCardinal()) {

                RSTile adjTile = curTile.getAdjacentTile(direction);

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

                    if (!allInShape)
                        isBoundary = true;
                }
            }

            if (isBoundary) {
                result.add(curTile);
            }

            closedList.add(curTile);
        }

        return result;
    }
}
