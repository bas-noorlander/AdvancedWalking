package scripts.advancedwalking.core.logging.loggers;

import org.tribot.api.General;
import scripts.advancedwalking.core.logging.Logger;

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
