package scripts.AdvancedWalking.Generator.NavMesh.Factories;

import scripts.AdvancedWalking.Generator.NavMesh.AbstractShape;
import scripts.AdvancedWalking.Generator.NavMesh.IShapeFactory;
import scripts.AdvancedWalking.Generator.NavMesh.Shapes.Polytope;
import scripts.AdvancedWalking.Generator.Tiles.MeshTile;

/**
 * @author Laniax
 */
public class PolytopeFactory implements IShapeFactory {

    @Override
    public AbstractShape newShape(MeshTile tile) {
        return new Polytope(tile);
    }
}
