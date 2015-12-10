package scripts.AdvancedWalking.Game.Path;

import org.tribot.api2007.types.RSTile;
import scripts.AdvancedWalking.Game.Path.Steps.ShortcutStep;
import scripts.AdvancedWalking.Game.Path.Steps.StairStep;
import scripts.AdvancedWalking.Game.Path.Steps.TeleportStep;
import scripts.AdvancedWalking.Game.World.Teleports.Teleports.ITeleport;

/**
 * @author Laniax
 */
public abstract class AbstractStep {

    /**
     * The step's destination.
     * @return
     */
    protected abstract RSTile destination();

	protected abstract ITeleport getTeleport();

	protected boolean isStairs() {
		return this instanceof StairStep;
	}

	protected boolean isTeleport() {
		return this instanceof TeleportStep;
	}

	protected boolean isShortcut() {
		return this instanceof ShortcutStep;
	}

	protected abstract boolean run();

	public RSTile getDestination() {
		return this.destination();
	}

}
