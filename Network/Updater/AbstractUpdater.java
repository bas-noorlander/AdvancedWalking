package scripts.AdvancedWalking.Network.Updater;

/**
 * @author Laniax
 */
public abstract class AbstractUpdater {

    /**
     * Checks if the local mesh data is up to date.
     * @return
     */
    public abstract boolean checkForUpdates();

    /**
     * Updates the local mesh data.
     * @return
     */
    public abstract boolean update();

    /**
     * Checks for updates and updates if needed.
     * @return true if successfully updated/didn't have to update. returns false if update failed.
     */
    public boolean run() {
        if (checkForUpdates()) {
            if (!update())
                return false;
        }

        return true;
    }

}
