package scripts.advancedwalking.pathfinding.pathfinders;

import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.Player;
import scripts.advancedwalking.core.io.IOExtensions;
import scripts.advancedwalking.core.logging.LogProxy;
import scripts.advancedwalking.core.types.NavMesh;
import scripts.advancedwalking.game.path.Path;
import scripts.advancedwalking.network.CommonFiles;
import scripts.advancedwalking.network.updater.Updaters.NavMeshUpdater;
import scripts.advancedwalking.pathfinding.Pathfinder;

/**
 * @author Laniax
 */
public class AdvancedPathfinder implements Pathfinder {

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

            mesh = (NavMesh) IOExtensions.deserialize(CommonFiles.localMeshFile);

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
