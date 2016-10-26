package scripts.advancedwalking.network.updater.Updaters;


import scripts.advancedwalking.core.io.IOExtensions;
import scripts.advancedwalking.core.logging.LogProxy;
import scripts.advancedwalking.network.CommonFiles;
import scripts.advancedwalking.network.Transmission;
import scripts.advancedwalking.network.updater.AbstractUpdater;

/**
 * @author Laniax
 */
public class NavMeshUpdater extends AbstractUpdater {

    LogProxy log = new LogProxy("NavMeshUpdater");

    public boolean checkForUpdates() {

        // Check if local file exists
        if (CommonFiles.localMeshFile.isFile()) {

            String localHash = IOExtensions.getSHA1Hash(CommonFiles.localMeshFile);
            String remoteHash = Transmission.downloadTextFile(CommonFiles.remoteHashURL);

            if (localHash.isEmpty() || remoteHash.isEmpty() || !localHash.equals(remoteHash)) {
                log.info("Mesh update has been found!");
                return true;
            }

            log.info("Mesh already up-to-date!");
            return false;

        } else {
            // File does not exist yet! so we go straight to downloading.
            log.info("You don't have any mesh data yet!");
            return true;
        }
    }

    public boolean update() {

        log.info("Downloading..");
        if (Transmission.download(CommonFiles.remoteMeshURL, CommonFiles.localMeshFile)) {
            log.info("Successfully updated mesh data.");
            return true;
        }
        else {
            log.warn("Download failed! We use WebWalking instead if the scripter hasn't disabled it.");
            return false;
        }
    }
}
