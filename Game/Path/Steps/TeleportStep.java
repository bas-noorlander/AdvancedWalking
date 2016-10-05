package scripts.AdvancedWalking.Game.Path.Steps;

import org.tribot.api2007.types.RSTile;
import scripts.AdvancedWalking.Game.Path.PathStep;
import scripts.AdvancedWalking.Game.World.Teleports.ItemTeleport;
import scripts.AdvancedWalking.Game.World.Teleports.MagicTeleport;
import scripts.AdvancedWalking.Game.World.Teleports.Teleport;

/**
 * A TeleportStep is a teleport in a path.
 *
 * @author Laniax
 */
public class TeleportStep implements PathStep {

    private RSTile _destination;

    private Teleport _teleport;

    public TeleportStep(Teleport teleport) {
        this._teleport = teleport;
    }

    @Override
    public RSTile destination() {
        return _destination;
    }

    @Override
    public Teleport getTeleport() {
        return _teleport;
    }

    @Override
    public boolean run() {

        if (getTeleport() instanceof MagicTeleport) {

            MagicTeleport teleport = (MagicTeleport) getTeleport();

            //todo: do spell logic

        } else if(getTeleport() instanceof ItemTeleport) {

            ItemTeleport teleport = (ItemTeleport) getTeleport();

            //todo: do item logic

        }

        return false;
    }
}
