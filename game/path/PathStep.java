package scripts.advancedwalking.game.path;

import org.tribot.api2007.types.RSTile;
import scripts.advancedwalking.game.path.steps.ShortcutStep;
import scripts.advancedwalking.game.path.steps.StairStep;
import scripts.advancedwalking.game.path.steps.TeleportStep;
import scripts.advancedwalking.game.teleports.Teleport;

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
