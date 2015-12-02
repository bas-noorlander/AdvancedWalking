package scripts.AdvancedWalking.Game.Path.Steps;

import org.tribot.api2007.types.RSTile;
import scripts.AdvancedWalking.Core.Logging.LogProxy;
import scripts.AdvancedWalking.Game.Path.AbstractStep;
import scripts.AdvancedWalking.Game.World.Teleports.Teleports.AbstractItemTeleport;
import scripts.AdvancedWalking.Game.World.Teleports.Teleports.AbstractMagicTeleport;
import scripts.AdvancedWalking.Game.World.Teleports.Teleports.ITeleport;

/**
 * A TeleportStep is a teleport in a path.
 *
 * @author Laniax
 */
public class TeleportStep extends AbstractStep {

    LogProxy log = new LogProxy("TeleportStep");

    private RSTile _destination;

    private ITeleport _teleport;

    public TeleportStep(ITeleport teleport) {
        this._teleport = teleport;
    }

    @Override
    protected RSTile destination() {
        return this._destination;
    }

    @Override
    protected ITeleport getTeleport() {
        return _teleport;
    }

    @Override
    protected boolean run() {

        if (getTeleport() instanceof AbstractMagicTeleport) {

            AbstractMagicTeleport teleport = (AbstractMagicTeleport) getTeleport();

            //todo: do spell logic

        } else if(getTeleport() instanceof AbstractItemTeleport) {

            AbstractItemTeleport teleport = (AbstractItemTeleport) getTeleport();

            //todo: do item logic

        }

        return false;
    }
}
