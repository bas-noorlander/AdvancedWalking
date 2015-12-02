package scripts.AdvancedWalking.Game.Path.Steps;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.interfaces.Positionable;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSObjectDefinition;
import org.tribot.api2007.types.RSTile;
import scripts.AdvancedWalking.Core.Logging.LogProxy;
import scripts.AdvancedWalking.Game.Path.AbstractStep;
import scripts.AdvancedWalking.Game.World.Teleports.Teleports.ITeleport;

/**
 * A StairStep is a stair in a path.
 *
 * @author Laniax
 */
public class StairStep extends AbstractStep {

    LogProxy log = new LogProxy("StairStep");

    private final Condition planeCondition = new Condition() {
        public boolean active() {
            General.sleep(50);
            RSTile playerPos = Player.getPosition();
            RSTile destPos = destination();

            if (playerPos == null || destPos == null)
                return false;

            return playerPos.getPlane() == destPos.getPlane();
        }
    };

    private RSTile _destination;

    private RSTile _stairPos;

    public StairStep(Positionable destination, Positionable stairPosition) {
        this._destination = destination.getPosition();
        this._stairPos = stairPosition.getPosition();
    }

    @Override
    protected RSTile destination() {
        return this._destination;
    }

    @Override
    protected ITeleport getTeleport() {
        return null;
    }

    @Override
    protected boolean run() {

        RSObject[] res = Objects.find(15, Filters.Objects.tileEquals(this._stairPos).combine(Filters.Objects.actionsContains("Climb"), false));

        // Some stairs can go both up and down we have to determine what to do.
        if (res.length > 0 && res[0] != null) {

            RSObjectDefinition objDef = res[0].getDefinition();

            int climbCount = 0;
            if (objDef != null) {
                for (String s : objDef.getActions()) {
                    if (s.contains("Climb"))
                        climbCount++;
                }
            }

            if (climbCount == 0) {
                log.info("Tried to use stairs but couldn't find a climb option!");
                return false;
            }

            String option = null; // note that null can be safely passed (and should be if we only have 1 climbing action).

            if (climbCount > 1) {
                // If the stairs has more then one action, check which one we need by checking the difference in planes between source and destination tiles.

                if (destination() != null) {
                    boolean up = destination().getPlane() > this._stairPos.getPlane();
                    option = up ? "Climb-up" : "Climb-down";
                }
            }

            // Now failsafe some clicking
            int failsafes = General.random(3, 5);

            while (!Clicking.click(option, res[0]) && failsafes > 0) {
                General.sleep(100, 250); //todo: delay - abc
                failsafes--;
            }

            return Timing.waitCondition(planeCondition, General.random(4500, 5200)); //todo: delay - abc
        }
        return false;
    }
}
