package scripts.AdvancedWalking.Core.Logging.Loggers;

import org.tribot.api.General;
import scripts.AdvancedWalking.Core.Logging.Logger;

/**
 * @author Laniax
 */
public class ClientDebugLogger implements Logger {

    @Override
    public void writeInformation(String message) {
        General.println(message);
    }

    @Override
    public void writeWarning(String message) {
        General.println(message);
    }

    @Override
    public void writeError(String message) {
        General.println(message);
    }

    @Override
    public void writeDebug(String message) {
        General.println(message);
    }
}
