package scripts.AdvancedWalking.Generator.NavMesh.Algorithms;

import org.tribot.api2007.types.RSTile;
import scripts.AdvancedWalking.Game.World.Direction;
import scripts.AdvancedWalking.Generator.Generator;
import scripts.AdvancedWalking.Generator.Tiles.MeshTile;

import java.util.*;
import java.util.List;

/**
 * Sorts the tiles on a shape's boundary.
 *
 * @author Laniax
 */
public class BoundarySort {

    public static List<MeshTile> run(List<MeshTile> tiles, Generator generator) {

        List<MeshTile> result = new ArrayList<>();

        MeshTile check = tiles.get(0);

        while (check != null) {

            MeshTile closest = null;
            double distance = Double.MAX_VALUE;

            for (Direction dir : Direction.getAll()) {

                RSTile rstile = check.getAdjacentTile(dir);

                if (rstile != null) {

                    MeshTile checkTile = generator.findTile(rstile);

                    if (checkTile == null || !tiles.contains(checkTile) || result.contains(checkTile))
                        continue;

                    double dist = check.distanceToDouble(checkTile);

                    if (closest == null || distance > dist) {
                        closest = checkTile;
                        distance = dist;
                    }
                }
            }

            if (closest != null) {
                result.add(closest);
            }

            check = closest;
        }

        return result;
    }
}
