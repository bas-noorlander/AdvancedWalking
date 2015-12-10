package scripts.AdvancedWalking.Walking.Walkers;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Game;
import org.tribot.api2007.Walking;
import scripts.AdvancedWalking.Game.Path.Path;
import scripts.AdvancedWalking.Walking.IWalker;

/**
 * @author Laniax
 */
public class AdvancedWalker implements IWalker {

    @Override
    public boolean walk(Path path) {

        //todo: lots of things

        for (int i = 0; i < path.getLength(); i++) {

            if (!Walking.blindWalkTo(path.getStep(i).getDestination())){
                if (i > 0)
                    i--;
                continue;
            }
        }

        return Timing.waitCondition(new Condition() {
            public boolean active() {
                General.sleep(100);
                return Game.getDestination() == null;
            }
        }, 8000);
    }
}
