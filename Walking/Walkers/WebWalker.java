package scripts.AdvancedWalking.Walking.Walkers;

import org.tribot.api2007.WebWalking;
import scripts.AdvancedWalking.Game.Path.Path;
import scripts.AdvancedWalking.Walking.IWalker;

/**
 * @author Laniax
 */
public class WebWalker implements IWalker{

    @Override
    public boolean walk(Path path) {

        if (path.getLength() > 0)
            return WebWalking.walkTo(path.getLast());

        return false;
    }
}
