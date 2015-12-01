package scripts.AdvancedWalking.Generator.Tiles.Collector.Collectors;

import org.tribot.api2007.Game;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import scripts.AdvancedWalking.Game.World.CollisionFlags;
import scripts.AdvancedWalking.Generator.Tiles.Collector.ITileCollector;
import scripts.AdvancedWalking.Generator.Tiles.MeshTile;

import java.util.ArrayList;

/**
 * @author Laniax
 */
public class RegionCollector implements ITileCollector {

    ArrayList<RSTile> scannedList = new ArrayList<>();

    ArrayList<MeshTile> result = new ArrayList<>();

    /**
     * Removes all the tiles from the closed list so they can be scanned again.
     */
    public void clear() {
        scannedList.clear();
    }

    @Override
    public void collect() {
        final RSTile base = new RSTile(Game.getBaseX(), Game.getBaseY());
        final RSArea chunk = new RSArea(base, new RSTile(base.getX() + 95, base.getY() + 95));

        for (RSTile scanTile : chunk.getAllTiles()) {

            if (scannedList.contains(scanTile)) {
                // We have already scanned this tile before.
                continue;
            }

            MeshTile meshTile = new MeshTile(scanTile);
            if (!meshTile.isBlocked(CollisionFlags.BLOCKED)) {
                result.add(meshTile);
            }

            scannedList.add(scanTile);
        }
    }

    @Override
    public ArrayList<MeshTile> getTiles() {

        return result;

    }
}
