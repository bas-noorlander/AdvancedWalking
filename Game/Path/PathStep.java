package scripts.AdvancedWalking.Game.Path;

import org.tribot.api2007.types.RSTile;
import scripts.AdvancedWalking.Game.Path.Steps.ShortcutStep;
import scripts.AdvancedWalking.Game.Path.Steps.StairStep;
import scripts.AdvancedWalking.Game.Path.Steps.TeleportStep;
import scripts.AdvancedWalking.Game.World.Teleports.Teleport;

/**
 * @author Laniax
 */
public interface PathStep {

    /**
     * The step's final destination.
     *
     * @return
     */
    RSTile destination();

    Teleport getTeleport();

    default boolean isStairs() {
        return this instanceof StairStep;
    }

    default boolean isTeleport() {
        return this instanceof TeleportStep;
    }

    default boolean isShortcut() {
        return this instanceof ShortcutStep;
    }

    boolean run();

    default RSTile getDestination() {
        return this.destination();
    }

}
