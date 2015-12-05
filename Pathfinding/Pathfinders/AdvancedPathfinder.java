package scripts.AdvancedWalking.Pathfinding.Pathfinders;

import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.Player;
import scripts.AdvancedWalking.Core.IO.IOExtensions;
import scripts.AdvancedWalking.Core.Logging.LogProxy;
import scripts.AdvancedWalking.Game.Path.Path;
import scripts.AdvancedWalking.Generator.NavMesh.NavMesh;
import scripts.AdvancedWalking.Network.CommonFiles;
import scripts.AdvancedWalking.Network.Updater.Updaters.NavMeshUpdater;
import scripts.AdvancedWalking.Pathfinding.IPathfinder;

/**
 * @author Laniax
 */
public class AdvancedPathfinder implements IPathfinder {

    LogProxy log = new LogProxy("AdvancedPathfinder");

    private NavMesh mesh = null;

    private boolean _isInitialized = false;

    @Override
    public boolean isInitialized() {
        return _isInitialized;
    }

    @Override
    public boolean init() {

        if (isInitialized())
            return true;

        if (new NavMeshUpdater().run()) { //todo: make customizable

            long sTime = System.currentTimeMillis();

            mesh = (NavMesh) IOExtensions.Deserialize(CommonFiles.localMeshFile);

            if (mesh != null) {

                log.info("Successfully set up in %dms!", System.currentTimeMillis() - sTime);

                return _isInitialized = true;
            }
        }

        return false;
    }

    @Override
    public Path findPath(Positionable destination, boolean useTeleports, boolean useAgilityShortcuts) {
        return mesh.findPath(Player.getPosition(), destination, useTeleports, useAgilityShortcuts);
    }

    @Override
    public Path findPath(Positionable start, Positionable destination, boolean useTeleports, boolean useAgilityShortcuts) {
        return mesh.findPath(start, destination, useTeleports, useAgilityShortcuts);
    }
}
