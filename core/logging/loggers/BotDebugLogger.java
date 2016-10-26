package scripts.advancedwalking.core.logging.loggers;


import scripts.advancedwalking.core.logging.Logger;

/**
 * @author Laniax
 */
public class BotDebugLogger implements Logger {

    @Override
    public void writeInformation(String message) {
        System.out.println(message);
    }

    @Override
    public void writeWarning(String message) {
        System.out.println(message);
    }

    @Override
    public void writeError(String message) {
        System.out.println(message);
    }

    @Override
    public void writeDebug(String message) {
        System.out.println(message);
    }
}
