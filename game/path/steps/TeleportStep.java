package scripts.advancedwalking.game.path.steps;

import org.tribot.api2007.types.RSTile;
import scripts.advancedwalking.game.path.PathStep;
import scripts.advancedwalking.game.teleports.ItemTeleport;
import scripts.advancedwalking.game.teleports.MagicTeleport;
import scripts.advancedwalking.game.teleports.Teleport;

/**
 * A TeleportStep is a teleport in a path.
 *
 * @author Laniax
 */
public class TeleportStep implements PathStep {

    private RSTile _destination;

    private Teleport _teleport;

    public TeleportStep(Teleport teleport) {
        _teleport = teleport;
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
