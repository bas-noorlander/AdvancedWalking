package scripts.AdvancedWalking.Generator.NavMesh;


import scripts.AdvancedWalking.Generator.Tiles.MeshTile;

/**
 * @author Laniax
 */
public interface IShapeFactory<T> {

    /**
     * Creates a new shape object.
     * @param tile - Since everything is based on a 'growing' shape. this is the tile to start growing from.
     * @return
     */
    AbstractShape newShape(MeshTile tile);
}
