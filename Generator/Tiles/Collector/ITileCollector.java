package scripts.AdvancedWalking.Generator.Tiles.Collector;

import scripts.AdvancedWalking.Generator.Tiles.MeshTile;

import java.util.ArrayList;

/**
 * @author Laniax
 */
public interface ITileCollector {

    void collect();

    ArrayList<MeshTile> getTiles();
}
