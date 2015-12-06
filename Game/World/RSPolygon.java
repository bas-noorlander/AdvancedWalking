package scripts.AdvancedWalking.Game.World;

import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.types.RSTile;

import java.awt.*;

/**
 * A java.awt.Polygon with plane support.
 * @author Laniax
 */
public class RSPolygon extends Polygon {

    private int _plane;

    public RSPolygon(int plane) {
        super();
        this._plane = plane;
    }

    public int getPlane() {
        return _plane;
    }

    public boolean contains(Positionable pos) {

        RSTile tile = pos.getPosition();

        if (tile == null)
            return false;

        return tile.getPlane() == getPlane() && super.contains(tile.getX(), tile.getY());
    }
}
