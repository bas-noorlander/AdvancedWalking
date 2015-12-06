package scripts.AdvancedWalking.Tests.Generator.NavMesh;

import org.junit.Before;
import org.junit.Test;
import org.tribot.api.interfaces.Positionable;
import scripts.AdvancedWalking.Generator.Generator;
import scripts.AdvancedWalking.Generator.NavMesh.AbstractShape;
import scripts.AdvancedWalking.Generator.NavMesh.NavMesh;
import scripts.AdvancedWalking.Generator.Tiles.MeshTile;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author Laniax
 */
public class NavMeshTest {

    NavMesh _mesh;

    AbstractShape testShape;

    @Before
    public void initialize() {
        _mesh = new NavMesh(new HashSet<>());

        testShape = new AbstractShape() {
            @Override
            public boolean accept() {
                return false;
            }

            @Override
            public int getSize() {
                return 0;
            }

            @Override
            public void grow(Generator generator, Set<AbstractShape> shapeList) {}

            @Override
            public boolean contains(Positionable tile) {
                return false;
            }

            @Override
            public MeshTile getClosestTile(Positionable pos) {
                return null;
            }

            @Override
            public void calculatePolygon(Generator generator) {}

            @Override
            public int hashCode() {
                return 0;
            }

            @Override
            public boolean equals(Object obj) {
                return false;
            }
        };
    }

    @Test
    public void testGetShapeCount() throws Exception {

        assertTrue(_mesh.getShapeCount() == 0);

        _mesh.addShape(testShape);

        assertTrue(_mesh.getShapeCount() == 1);

    }

    @Test
    public void testGetAllShapes() throws Exception {

        assertTrue(_mesh.getAllShapes().size() == 0);

        _mesh.addShape(testShape);

        assertTrue(_mesh.getAllShapes().contains(testShape));

    }

    @Test
    public void testAddShape() throws Exception {

        assertTrue(_mesh.getAllShapes().size() == 0);

        _mesh.addShape(testShape);

        assertTrue(_mesh.getAllShapes().size() == 1);
        assertNotNull(_mesh.getAllShapes().contains(testShape));

        assertFalse(_mesh.addShape(testShape));

    }

    @Test
    public void testFindShape() throws Exception {

    }

    @Test
    public void testFindPath() throws Exception {

    }
}