package scripts.advancedwalking.walking.walkers;

import org.tribot.api2007.WebWalking;
import scripts.advancedwalking.game.path.Path;
import scripts.advancedwalking.walking.Walker;

/**
 * @author Laniax
 */
public class WebWalker implements Walker {

    @Override
    public boolean walk(Path path) {

        if (path.getLength() > 0)
            return WebWalking.walkTo(path.getLast().getDestination());

        return false;
    }
}
